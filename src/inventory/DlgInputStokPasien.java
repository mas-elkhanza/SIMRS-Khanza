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

package inventory;
import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import simrskhanza.DlgCariBangsal;

public class DlgInputStokPasien extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jur=new Jurnal();
    private PreparedStatement pstampil,psrekening;
    private ResultSet rstampil,rsrekening;
    private WarnaTable2 warna=new WarnaTable2();
    private riwayatobat Trackobat=new riwayatobat();
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    private DecimalFormat df3 = new DecimalFormat("###");
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private DlgCariAturanPakai aturan=new DlgCariAturanPakai(null,false);
    private double ttl=0,y=0,ttlhpp=0,ttljual=0,ppnobat=0,stokobat,kenaikan=0;
    private int jml=0,i=0,index=0;
    private String Suspen_Piutang_Obat_Ranap="",Obat_Ranap="",HPP_Obat_Rawat_Inap="",Persediaan_Obat_Rawat_Inap="",
                   tampilkan_ppnobat_ranap="",aktifkanbatch="no",nopermintaan="",pilihanetiket="",hppfarmasi="";
    private String[] keranap,kodebarang,namabarang,kategori,satuan,nobatch,nofaktur,aturanpakai;
    private Double[] kapasitas,stok,harga,hargabeli,subtotal;
    private boolean[] jam00,jam01,jam02,jam03,jam04,jam05,jam06,jam07,jam08,jam09,jam10,jam11,jam12,jam13,jam14,jam15,jam16,jam17,jam18,jam19,jam20,jam21,jam22,jam23;
    private boolean sukses=false;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgInputStokPasien(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Jml","Kode Barang","Nama Barang","Kategori","Satuan","Kps","Stok","Harga","HargaBeli","Subtotal","No.Batch","No.Faktur","Aturan Pakai",
                "00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
                if ((colIndex==1)||(colIndex==2)||(colIndex==3)||(colIndex==4)||(colIndex==5)||(colIndex==6)||(colIndex==7)||(colIndex==8)||(colIndex==9)) {
                    a=false;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, 
                java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 37; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(40);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(75);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(100);
            }else if(i==13){
                column.setPreferredWidth(20);
            }else if(i==14){
                column.setPreferredWidth(20);
            }else if(i==15){
                column.setPreferredWidth(20);
            }else if(i==16){
                column.setPreferredWidth(20);
            }else if(i==17){
                column.setPreferredWidth(20);
            }else if(i==18){
                column.setPreferredWidth(20);
            }else if(i==19){
                column.setPreferredWidth(20);
            }else if(i==20){
                column.setPreferredWidth(20);
            }else if(i==21){
                column.setPreferredWidth(20);
            }else if(i==22){
                column.setPreferredWidth(20);
            }else if(i==23){
                column.setPreferredWidth(20);
            }else if(i==24){
                column.setPreferredWidth(20);
            }else if(i==25){
                column.setPreferredWidth(20);
            }else if(i==26){
                column.setPreferredWidth(20);
            }else if(i==27){
                column.setPreferredWidth(20);
            }else if(i==28){
                column.setPreferredWidth(20);
            }else if(i==29){
                column.setPreferredWidth(20);
            }else if(i==30){
                column.setPreferredWidth(20);
            }else if(i==31){
                column.setPreferredWidth(20);
            }else if(i==32){
                column.setPreferredWidth(20);
            }else if(i==33){
                column.setPreferredWidth(20);
            }else if(i==34){
                column.setPreferredWidth(20);
            }else if(i==35){
                column.setPreferredWidth(20);
            }else if(i==36){
                column.setPreferredWidth(20);
            }
        }
        warna.kolom=0;
        tbDokter.setDefaultRenderer(Object.class,warna);

        kdgudang.setDocument(new batasInput((byte)5).getKata(kdgudang));
        catatan.setDocument(new batasInput((byte)60).getKata(catatan));  
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
        
        TCari.requestFocus();
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){                   
                    kdgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());
                    nmgudang.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                }   
                kdgudang.requestFocus();
                tampil();
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
        
        aturan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(aturan.getTable().getSelectedRow()!= -1){  
                    tbDokter.setValueAt(aturan.getTable().getValueAt(aturan.getTable().getSelectedRow(),0).toString(),tbDokter.getSelectedRow(),12);
                }   
                tbDokter.requestFocus();
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
        
        jam();
           
        try {
            psrekening=koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Obat_Ranap=rsrekening.getString("Suspen_Piutang_Obat_Ranap");
                    Obat_Ranap=rsrekening.getString("Obat_Ranap");
                    HPP_Obat_Rawat_Inap=rsrekening.getString("HPP_Obat_Rawat_Inap");
                    Persediaan_Obat_Rawat_Inap=rsrekening.getString("Persediaan_Obat_Rawat_Inap");
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
            tampilkan_ppnobat_ranap=Sequel.cariIsi("select set_nota.tampilkan_ppnobat_ranap from set_nota"); 
            
            try {
                aktifkanbatch = koneksiDB.AKTIFKANBATCHOBAT();
            } catch (Exception e) {
                System.out.println("E : "+e);
                aktifkanbatch = "no";
            }
        } catch (Exception e) {
            System.out.println(e);
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
        kelas = new widget.TextBox();
        KdPj = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label18 = new widget.Label();
        catatan = new widget.TextBox();
        label11 = new widget.Label();
        Tgl = new widget.Tanggal();
        label21 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();
        label22 = new widget.Label();
        norawat = new widget.TextBox();
        nm_pasien = new widget.TextBox();
        jLabel5 = new widget.Label();
        LTotal = new widget.Label();
        jLabel6 = new widget.Label();
        LPpn = new widget.Label();
        jLabel7 = new widget.Label();
        LTotalTagihan = new widget.Label();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        panelisi1 = new widget.panelisi();
        label12 = new widget.Label();
        Jeniskelas = new widget.ComboBox();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        label10 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();

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
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        kelas.setHighlighter(null);
        kelas.setName("kelas"); // NOI18N
        kelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelasKeyPressed(evt);
            }
        });

        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Stok Obat & BHP Medis Pasien Di Ranap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
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
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDokterPropertyChange(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDokterKeyReleased(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(89, 104));
        panelisi3.setLayout(null);

        label18.setText("Keterangan :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label18);
        label18.setBounds(0, 40, 80, 23);

        catatan.setText("-");
        catatan.setName("catatan"); // NOI18N
        catatan.setPreferredSize(new java.awt.Dimension(207, 23));
        catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                catatanKeyPressed(evt);
            }
        });
        panelisi3.add(catatan);
        catatan.setBounds(84, 40, 311, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 10, 80, 23);

        Tgl.setDisplayFormat("dd-MM-yyyy");
        Tgl.setName("Tgl"); // NOI18N
        Tgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKeyPressed(evt);
            }
        });
        panelisi3.add(Tgl);
        Tgl.setBounds(84, 10, 90, 23);

        label21.setText("Asal Stok :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label21);
        label21.setBounds(406, 10, 70, 23);

        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        panelisi3.add(kdgudang);
        kdgudang.setBounds(479, 10, 91, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmgudang);
        nmgudang.setBounds(572, 10, 210, 23);

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
        BtnGudang.setBounds(784, 10, 28, 23);

        label22.setText("No.Rawat :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label22);
        label22.setBounds(406, 40, 70, 23);

        norawat.setName("norawat"); // NOI18N
        norawat.setPreferredSize(new java.awt.Dimension(100, 23));
        norawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                norawatKeyPressed(evt);
            }
        });
        panelisi3.add(norawat);
        norawat.setBounds(479, 40, 121, 23);

        nm_pasien.setEditable(false);
        nm_pasien.setName("nm_pasien"); // NOI18N
        nm_pasien.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nm_pasien);
        nm_pasien.setBounds(602, 40, 210, 23);

        jLabel5.setText("Total :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi3.add(jLabel5);
        jLabel5.setBounds(0, 70, 80, 23);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(LTotal);
        LTotal.setBounds(84, 70, 100, 23);

        jLabel6.setText("PPN :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(jLabel6);
        jLabel6.setBounds(200, 70, 35, 23);

        LPpn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPpn.setText("0");
        LPpn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LPpn.setName("LPpn"); // NOI18N
        LPpn.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(LPpn);
        LPpn.setBounds(240, 70, 65, 23);

        jLabel7.setText("Total+PPN :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(jLabel7);
        jLabel7.setBounds(320, 70, 65, 23);

        LTotalTagihan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotalTagihan.setText("0");
        LTotalTagihan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotalTagihan.setName("LTotalTagihan"); // NOI18N
        LTotalTagihan.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(LTotalTagihan);
        LTotalTagihan.setBounds(390, 70, 130, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        panelisi3.add(cmbJam);
        cmbJam.setBounds(177, 10, 62, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        panelisi3.add(cmbMnt);
        cmbMnt.setBounds(242, 10, 62, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        panelisi3.add(cmbDtk);
        cmbDtk.setBounds(307, 10, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        panelisi3.add(ChkJln);
        ChkJln.setBounds(373, 10, 22, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi1.add(label12);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kelas 1", "Kelas 2", "Kelas 3", "Utama/BPJS", "VIP", "VVIP", "Beli Luar", "Karyawan" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(130, 23));
        Jeniskelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JeniskelasItemStateChanged(evt);
            }
        });
        Jeniskelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JeniskelasKeyPressed(evt);
            }
        });
        panelisi1.add(Jeniskelas);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
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

        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(15, 23));
        panelisi1.add(label10);

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

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('C');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+C");
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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
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
            index=0;
            jml=tbDokter.getRowCount();
            for(i=0;i<jml;i++){
                if((Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0)&&(tbDokter.getValueAt(i,10).toString().trim().equals("")||tbDokter.getValueAt(i,11).toString().trim().equals(""))){
                    index++;
                }
            }
        }
        
        if(nmgudang.getText().trim().equals("")||kdgudang.getText().trim().equals("")){
            Valid.textKosong(kdgudang,"Gudang");
        }else if(catatan.getText().trim().equals("")){
            Valid.textKosong(catatan,"Keterangan");
        }else if(aktifkanbatch.equals("yes")&&(index>0)){
            Valid.textKosong(TCari,"No.Batch/No.Faktur");
        }else if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data kosong..!!!!");
            tbDokter.requestFocus();
        }else{            
            if(Sequel.cariRegistrasi(norawat.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                if(ttl>0){
                    i= JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                    if (i == JOptionPane.YES_OPTION) {
                        ChkJln.setSelected(false);
                        Sequel.AutoComitFalse();
                        sukses=true;
                        ttlhpp=0;ttljual=0;
                        for(i=0;i<tbDokter.getRowCount();i++){  
                           if(Valid.SetAngka(tbDokter.getValueAt(i,0).toString())>0){
                                if(Sequel.menyimpantf2("stok_obat_pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Stok Obat Pasien",33,new String[]{                            
                                    Valid.SetTgl(Tgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),norawat.getText(),tabMode.getValueAt(i,1).toString(),
                                    tabMode.getValueAt(i,0).toString(),kdgudang.getText(),tabMode.getValueAt(i,10).toString(),tabMode.getValueAt(i,11).toString(),tabMode.getValueAt(i,12).toString(),
                                    tabMode.getValueAt(i,13).toString(),tabMode.getValueAt(i,14).toString(),tabMode.getValueAt(i,15).toString(),tabMode.getValueAt(i,16).toString(),tabMode.getValueAt(i,17).toString(),
                                    tabMode.getValueAt(i,18).toString(),tabMode.getValueAt(i,19).toString(),tabMode.getValueAt(i,20).toString(),tabMode.getValueAt(i,21).toString(),tabMode.getValueAt(i,22).toString(),
                                    tabMode.getValueAt(i,23).toString(),tabMode.getValueAt(i,24).toString(),tabMode.getValueAt(i,25).toString(),tabMode.getValueAt(i,26).toString(),tabMode.getValueAt(i,27).toString(),
                                    tabMode.getValueAt(i,28).toString(),tabMode.getValueAt(i,29).toString(),tabMode.getValueAt(i,30).toString(),tabMode.getValueAt(i,31).toString(),tabMode.getValueAt(i,32).toString(),
                                    tabMode.getValueAt(i,33).toString(),tabMode.getValueAt(i,34).toString(),tabMode.getValueAt(i,35).toString(),tabMode.getValueAt(i,36).toString()
                                })==true){
                                    ttlhpp=ttlhpp+(Valid.SetAngka(tabMode.getValueAt(i,0).toString())*Valid.SetAngka(tabMode.getValueAt(i,8).toString()));
                                    ttljual=ttljual+(Valid.SetAngka(tabMode.getValueAt(i,0).toString())*Valid.SetAngka(tabMode.getValueAt(i,7).toString()));

                                    if(aktifkanbatch.equals("yes")){
                                        Sequel.mengedit3("data_batch","no_batch=? and kode_brng=? and no_faktur=?","sisa=sisa-?",4,new String[]{
                                            ""+tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,10).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,11).toString()
                                        });
                                        Trackobat.catatRiwayat(tabMode.getValueAt(i,1).toString(),0,Valid.SetAngka(tabMode.getValueAt(i,0).toString()),"Stok Pasien Ranap",akses.getkode(),kdgudang.getText(),"Simpan",tabMode.getValueAt(i,10).toString(),tabMode.getValueAt(i,11).toString(),norawat.getText()+" "+nm_pasien.getText());
                                        Sequel.menyimpan("gudangbarang","'"+tabMode.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','-"+tabMode.getValueAt(i,0).toString()+"','"+tabMode.getValueAt(i,10).toString()+"','"+tabMode.getValueAt(i,11).toString()+"'", 
                                                        "stok=stok-'"+tabMode.getValueAt(i,0).toString()+"'","kode_brng='"+tabMode.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='"+tabMode.getValueAt(i,10).toString()+"' and no_faktur='"+tabMode.getValueAt(i,11).toString()+"'");
                                    }else{
                                        Trackobat.catatRiwayat(tabMode.getValueAt(i,1).toString(),0,Valid.SetAngka(tabMode.getValueAt(i,0).toString()),"Stok Pasien Ranap",akses.getkode(),kdgudang.getText(),"Simpan","","",norawat.getText()+" "+nm_pasien.getText());
                                        Sequel.menyimpan("gudangbarang","'"+tabMode.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','-"+tabMode.getValueAt(i,0).toString()+"','',''", 
                                                        "stok=stok-'"+tabMode.getValueAt(i,0).toString()+"'","kode_brng='"+tabMode.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"' and no_batch='' and no_faktur=''");
                                    }   
                                }else{
                                    sukses=false;
                                }                            
                           }
                        }  
                        
                        if(!nopermintaan.equals("")){
                            Sequel.mengedit("permintaan_stok_obat_pasien","no_permintaan='"+nopermintaan+"'","tgl_validasi='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"',jam_validasi='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"',status='Sudah'");
                        }

                        if(sukses==true){
                            Sequel.queryu("delete from tampjurnal");    
                            if(ttljual>0){
                                Sequel.menyimpan2("tampjurnal","'"+Suspen_Piutang_Obat_Ranap+"','Suspen Piutang Obat Ranap','"+ttljual+"','0'","Rekening");    
                                Sequel.menyimpan2("tampjurnal","'"+Obat_Ranap+"','Pendapatan Obat Rawat Inap','0','"+ttljual+"'","Rekening");                              
                            }
                            if(ttlhpp>0){
                                Sequel.menyimpan2("tampjurnal","'"+HPP_Obat_Rawat_Inap+"','HPP Persediaan Obat Rawat Inap','"+ttlhpp+"','0'","Rekening");    
                                Sequel.menyimpan2("tampjurnal","'"+Persediaan_Obat_Rawat_Inap+"','Persediaan Obat Rawat Inap','0','"+ttlhpp+"'","Rekening");                              
                            }
                            sukses=jur.simpanJurnal(norawat.getText(),"U","PEMBERIAN OBAT RAWAT INAP PASIEN, DIPOSTING OLEH "+akses.getkode());  
                        }                                                 

                        if(sukses==true){
                            Sequel.Commit();
                            pilihancetak();
                        }else{
                            sukses=false;
                            JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }   
                        Sequel.AutoComitTrue();

                        if(sukses==true){
                            for(index=0;index<tbDokter.getRowCount();index++){   
                                tbDokter.setValueAt("",index,0); 
                                tbDokter.setValueAt(0,index,9);  
                                tbDokter.setValueAt(false,index,13);
                                tbDokter.setValueAt(false,index,14);
                                tbDokter.setValueAt(false,index,15);
                                tbDokter.setValueAt(false,index,16);  
                                tbDokter.setValueAt(false,index,17);  
                                tbDokter.setValueAt(false,index,18);  
                                tbDokter.setValueAt(false,index,19);  
                                tbDokter.setValueAt(false,index,20);  
                                tbDokter.setValueAt(false,index,21);  
                                tbDokter.setValueAt(false,index,22);  
                                tbDokter.setValueAt(false,index,23);  
                                tbDokter.setValueAt(false,index,24);  
                                tbDokter.setValueAt(false,index,25);  
                                tbDokter.setValueAt(false,index,26);  
                                tbDokter.setValueAt(false,index,27);  
                                tbDokter.setValueAt(false,index,28);  
                                tbDokter.setValueAt(false,index,29);  
                                tbDokter.setValueAt(false,index,30);  
                                tbDokter.setValueAt(false,index,31);  
                                tbDokter.setValueAt(false,index,32);  
                                tbDokter.setValueAt(false,index,33);  
                                tbDokter.setValueAt(false,index,34);  
                                tbDokter.setValueAt(false,index,35);  
                                tbDokter.setValueAt(false,index,36);  
                            }
                            ttl=0;
                            ttlhpp=0;
                            ttljual=0;
                            LTotal.setText("0");
                            LPpn.setText("0");
                            LTotalTagihan.setText("0");
                        }
                        ChkJln.setSelected(true);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Silahkan masukkan obat yang mau diberikan...!!!");
                    TCari.requestFocus();
                }
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

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
        }else{
            Valid.pindah(evt, TCari, BtnSimpan);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void catatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_catatanKeyPressed
        Valid.pindah(evt, kdgudang,BtnSimpan);
}//GEN-LAST:event_catatanKeyPressed

private void TglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKeyPressed
        Valid.pindah(evt,TCari,kdgudang);
}//GEN-LAST:event_TglKeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
            int row2=tbDokter.getRowCount();
            for(int r=0;r<row2;r++){ 
                tbDokter.setValueAt("",r,0);
            }
            
            LTotal.setText("0");
            LPpn.setText("0");
            LTotalTagihan.setText("0");
}//GEN-LAST:event_ppBersihkanActionPerformed

private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        tampil();
    }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        tampil();
        Tgl.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
        tampil();
        BtnSimpan.requestFocus();
    }else if(evt.getKeyCode()==KeyEvent.VK_UP){
        BtnGudangActionPerformed(null);
    }
}//GEN-LAST:event_kdgudangKeyPressed

private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
    bangsal.isCek();
    bangsal.emptTeks();
    bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
    bangsal.setLocationRelativeTo(internalFrame1);
    bangsal.setAlwaysOnTop(false);
    bangsal.setVisible(true);
}//GEN-LAST:event_BtnGudangActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari1ActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgStokPasien opname=new DlgStokPasien(null,false);
        opname.isCek();
        opname.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        opname.setLocationRelativeTo(internalFrame1);
        opname.setAlwaysOnTop(false);
        opname.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void norawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_norawatKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_norawatKeyPressed

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                int row=tbDokter.getSelectedRow();
                if(row!= -1){
                    tabMode.setValueAt("", row,0);
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                TCari.setText("");
                TCari.requestFocus();
            }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbDokter.getSelectedColumn();
                if(i==12){
                    aturan.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    aturan.setLocationRelativeTo(internalFrame1);
                    aturan.setVisible(true);
                }
            } 
        }
    }//GEN-LAST:event_tbDokterKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {                  
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void JeniskelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JeniskelasItemStateChanged
        tampil();
    }//GEN-LAST:event_JeniskelasItemStateChanged

    private void JeniskelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JeniskelasKeyPressed
        Valid.pindah(evt, norawat,TCari);
    }//GEN-LAST:event_JeniskelasKeyPressed

    private void kelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelasKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kelasKeyPressed

    private void tbDokterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyReleased
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {                                     
                    getData();                     
                    TCari.setText("");
                    TCari.requestFocus();
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_RIGHT)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {                                     
                    getData();           
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyReleased

    private void tbDokterPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDokterPropertyChange
        if(this.isVisible()==true){
              getData();
        }
    }//GEN-LAST:event_tbDokterPropertyChange

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,Tgl,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,Jeniskelas);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgInputStokPasien dialog = new DlgInputStokPasien(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnGudang;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkJln;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox Kd2;
    private widget.TextBox KdPj;
    private widget.Label LPpn;
    private widget.Label LTotal;
    private widget.Label LTotalTagihan;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl;
    private widget.TextBox catatan;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.TextBox kdgudang;
    private widget.TextBox kelas;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label18;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label9;
    private widget.TextBox nm_pasien;
    private widget.TextBox nmgudang;
    private widget.TextBox norawat;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        jml=0;
        for(i=0;i<tbDokter.getRowCount();i++){
            if(!tbDokter.getValueAt(i,0).toString().equals("")){
                jml++;
            }
        }
        keranap=null;
        keranap=new String[jml];
        kodebarang=null;
        kodebarang=new String[jml];
        namabarang=null;
        namabarang=new String[jml];
        kategori=null;
        kategori=new String[jml];
        satuan=null;
        satuan=new String[jml];
        kapasitas=null;
        kapasitas=new Double[jml];
        stok=null;
        stok=new Double[jml];
        harga=null;
        harga=new Double[jml];
        hargabeli=null;
        hargabeli=new Double[jml];
        subtotal=null;
        subtotal=new Double[jml];
        nobatch=null;
        nobatch=new String[jml];
        nofaktur=null;
        nofaktur=new String[jml];
        aturanpakai=null;
        aturanpakai=new String[jml];
        jam00=null;
        jam00=new boolean[jml];
        jam01=null;
        jam01=new boolean[jml];
        jam02=null;
        jam02=new boolean[jml];
        jam03=null;
        jam03=new boolean[jml];
        jam04=null;
        jam04=new boolean[jml];
        jam05=null;
        jam05=new boolean[jml];
        jam06=null;
        jam06=new boolean[jml];
        jam07=null;
        jam07=new boolean[jml];
        jam08=null;
        jam08=new boolean[jml];
        jam09=null;
        jam09=new boolean[jml];
        jam10=null;
        jam10=new boolean[jml];
        jam11=null;
        jam11=new boolean[jml];
        jam12=null;
        jam12=new boolean[jml];
        jam13=null;
        jam13=new boolean[jml];
        jam14=null;
        jam14=new boolean[jml];
        jam15=null;
        jam15=new boolean[jml];
        jam16=null;
        jam16=new boolean[jml];
        jam17=null;
        jam17=new boolean[jml];
        jam18=null;
        jam18=new boolean[jml];
        jam19=null;
        jam19=new boolean[jml];
        jam20=null;
        jam20=new boolean[jml];
        jam21=null;
        jam21=new boolean[jml];
        jam22=null;
        jam22=new boolean[jml];
        jam23=null;
        jam23=new boolean[jml];
        
        index=0;        
        for(i=0;i<tbDokter.getRowCount();i++){
            if(!tbDokter.getValueAt(i,0).toString().equals("")){
                keranap[index]=tbDokter.getValueAt(i,0).toString();
                kodebarang[index]=tbDokter.getValueAt(i,1).toString();
                namabarang[index]=tbDokter.getValueAt(i,2).toString();
                kategori[index]=tbDokter.getValueAt(i,3).toString();
                satuan[index]=tbDokter.getValueAt(i,4).toString();
                kapasitas[index]=Double.parseDouble(tbDokter.getValueAt(i,5).toString());
                stok[index]=Double.parseDouble(tbDokter.getValueAt(i,6).toString());
                harga[index]=Double.parseDouble(tbDokter.getValueAt(i,7).toString());
                hargabeli[index]=Double.parseDouble(tbDokter.getValueAt(i,8).toString());
                subtotal[index]=Double.parseDouble(tbDokter.getValueAt(i,9).toString());
                nobatch[index]=tbDokter.getValueAt(i,10).toString();
                nofaktur[index]=tbDokter.getValueAt(i,11).toString();
                aturanpakai[index]=tbDokter.getValueAt(i,12).toString();
                jam00[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,13).toString());
                jam01[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,14).toString());
                jam02[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,15).toString());
                jam03[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,16).toString());
                jam04[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,17).toString());
                jam05[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,18).toString());
                jam06[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,19).toString());
                jam07[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,20).toString());
                jam08[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,21).toString());
                jam09[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,22).toString());
                jam10[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,23).toString());
                jam11[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,24).toString());
                jam12[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,25).toString());
                jam13[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,26).toString());
                jam14[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,27).toString());
                jam15[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,28).toString());
                jam16[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,29).toString());
                jam17[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,30).toString());
                jam18[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,31).toString());
                jam19[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,32).toString());
                jam20[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,33).toString());
                jam21[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,34).toString());
                jam22[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,35).toString());
                jam23[index]=Boolean.parseBoolean(tbDokter.getValueAt(i,36).toString());
                index++;
            }
        }
        
        Valid.tabelKosong(tabMode);
        for(i=0;i<jml;i++){
            tabMode.addRow(new Object[]{
                keranap[i],kodebarang[i],namabarang[i],kategori[i],satuan[i],kapasitas[i],stok[i],harga[i],hargabeli[i],subtotal[i],nobatch[i],nofaktur[i],aturanpakai[i],
                jam00[i],jam01[i],jam02[i],jam03[i],jam04[i],jam05[i],jam06[i],jam07[i],jam08[i],jam09[i],jam10[i],jam11[i],jam12[i],jam13[i],jam14[i],
                jam15[i],jam16[i],jam17[i],jam18[i],jam19[i],jam20[i],jam21[i],jam22[i],jam23[i]});
        }
        try{  
            if(kenaikan>0){
                if(aktifkanbatch.equals("yes")){
                    pstampil=koneksi.prepareStatement("select data_batch.kode_brng, databarang.nama_brng,jenis.nama,"+
                            " databarang.kapasitas,databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,"+
                            " data_batch."+hppfarmasi+" as dasar,gudangbarang.stok,data_batch.no_batch,data_batch.no_faktur  "+
                            " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "+
                            " inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_sat like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");  
                    try{
                        pstampil.setDouble(1,kenaikan);
                        pstampil.setString(2,kdgudang.getText());
                        pstampil.setString(3,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(4,kdgudang.getText());
                        pstampil.setString(5,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(6,kdgudang.getText());
                        pstampil.setString(7,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(8,kdgudang.getText());
                        pstampil.setString(9,"%"+TCari.getText().trim()+"%");
                        rstampil=pstampil.executeQuery();
                        while(rstampil.next()){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("harga"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),
                                   rstampil.getString("no_faktur"),"",false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                        }                  
                    }catch(Exception e){
                        System.out.println("Notif Data Barang : "+e);
                    }finally{
                        if(rstampil!=null){
                            rstampil.close();
                        }
                        if(pstampil!=null){
                            pstampil.close();
                        }
                    } 
                }else{
                    pstampil=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama,"+
                            " databarang.kapasitas,databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                            " databarang."+hppfarmasi+" as dasar,gudangbarang.stok  "+
                            " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng where"+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.kode_sat like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");  
                    try{
                        pstampil.setDouble(1,kenaikan);
                        pstampil.setString(2,kdgudang.getText());
                        pstampil.setString(3,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(4,kdgudang.getText());
                        pstampil.setString(5,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(6,kdgudang.getText());
                        pstampil.setString(7,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(8,kdgudang.getText());
                        pstampil.setString(9,"%"+TCari.getText().trim()+"%");
                        rstampil=pstampil.executeQuery();
                        while(rstampil.next()){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("harga"),100),
                                   rstampil.getDouble("dasar"),0,"","","",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                        }                  
                    }catch(Exception e){
                        System.out.println("Notif Data Barang : "+e);
                    }finally{
                        if(rstampil!=null){
                            rstampil.close();
                        }
                        if(pstampil!=null){
                            pstampil.close();
                        }
                    }
                }
            }else{
                if(aktifkanbatch.equals("yes")){
                    pstampil=koneksi.prepareStatement("select data_batch.kode_brng, databarang.nama_brng,jenis.nama,"+
                            " databarang.kapasitas,databarang.kode_sat,data_batch.kelas1,data_batch.kelas2,data_batch.kelas3,"+
                            " data_batch.utama,data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.karyawan,"+
                            " data_batch."+hppfarmasi+" as dasar,gudangbarang.stok,data_batch.no_batch,data_batch.no_faktur  "+
                            " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "+
                            " inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " where gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and databarang.kode_sat like ? or "+
                            " gudangbarang.stok>0 and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");  
                    try{
                        pstampil.setString(1,kdgudang.getText());
                        pstampil.setString(2,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(3,kdgudang.getText());
                        pstampil.setString(4,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(5,kdgudang.getText());
                        pstampil.setString(6,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(7,kdgudang.getText());
                        pstampil.setString(8,"%"+TCari.getText().trim()+"%");
                        rstampil=pstampil.executeQuery();
                        while(rstampil.next()){
                            if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("kelas1"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),
                                   rstampil.getString("no_faktur"),"",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("kelas2"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),
                                   rstampil.getString("no_faktur"),"",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("kelas3"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),
                                   rstampil.getString("no_faktur"),"",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("utama"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),
                                   rstampil.getString("no_faktur"),"",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("vip"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),
                                   rstampil.getString("no_faktur"),"",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("vvip"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),
                                   rstampil.getString("no_faktur"),"",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("beliluar"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),
                                   rstampil.getString("no_faktur"),"",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("karyawan"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),
                                   rstampil.getString("no_faktur"),"",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }
                        }                  
                    }catch(Exception e){
                        System.out.println("Notif Data Barang : "+e);
                    }finally{
                        if(rstampil!=null){
                            rstampil.close();
                        }
                        if(pstampil!=null){
                            pstampil.close();
                        }
                    } 
                }else{
                    pstampil=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama,"+
                            " databarang.kapasitas,databarang.kode_sat,databarang.kelas1,"+
                            " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,"+
                            " databarang.vvip,databarang.beliluar,databarang.karyawan,databarang."+hppfarmasi+" as dasar,gudangbarang.stok  "+
                            " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng where"+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.kode_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.nama_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and databarang.kode_sat like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.stok>0 and databarang.status='1' and gudangbarang.kd_bangsal=? and jenis.nama like ? order by databarang.nama_brng");                
                    try{
                        pstampil.setString(1,kdgudang.getText());
                        pstampil.setString(2,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(3,kdgudang.getText());
                        pstampil.setString(4,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(5,kdgudang.getText());
                        pstampil.setString(6,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(7,kdgudang.getText());
                        pstampil.setString(8,"%"+TCari.getText().trim()+"%");
                        rstampil=pstampil.executeQuery();
                        while(rstampil.next()){
                            if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("kelas1"),100),
                                   rstampil.getDouble("dasar"),0,"","","",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("kelas2"),100),
                                   rstampil.getDouble("dasar"),0,"","","",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("kelas3"),100),
                                   rstampil.getDouble("dasar"),0,"","","",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("utama"),100),
                                   rstampil.getDouble("dasar"),0,"","","",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("vip"),100),
                                   rstampil.getDouble("dasar"),0,"","","",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("vvip"),100),
                                   rstampil.getDouble("dasar"),0,"","","",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("beliluar"),100),
                                   rstampil.getDouble("dasar"),0,"","","",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                                tabMode.addRow(new Object[]{"",rstampil.getString("kode_brng"),
                                   rstampil.getString("nama_brng"),
                                   rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),
                                   rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                                   Valid.roundUp(rstampil.getDouble("karyawan"),100),
                                   rstampil.getDouble("dasar"),0,"","","",false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false,
                                   false,false,false,false,false,false,false,false,false,false
                                });
                            }
                        }                  
                    }catch(Exception e){
                        System.out.println("Notif Data Barang : "+e);
                    }finally{
                        if(rstampil!=null){
                            rstampil.close();
                        }
                        if(pstampil!=null){
                            pstampil.close();
                        }
                    }   
                }
            }           
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
    public void tampil2(String nopermintaan) {
        this.nopermintaan=nopermintaan;
        Valid.tabelKosong(tabMode);
        try{  
            if(kenaikan>0){
                if(aktifkanbatch.equals("yes")){
                    pstampil=koneksi.prepareStatement("select data_batch.kode_brng, databarang.nama_brng,jenis.nama,"+
                            " databarang.kapasitas,databarang.kode_sat,(data_batch.h_beli+(data_batch.h_beli*?)) as harga,"+
                            " data_batch."+hppfarmasi+" as dasar,gudangbarang.stok,data_batch.no_batch,data_batch.no_faktur,"+
                            " detail_permintaan_stok_obat_pasien.jml,detail_permintaan_stok_obat_pasien.aturan_pakai, "+
                            " if(gudangbarang.stok>detail_permintaan_stok_obat_pasien.jml,detail_permintaan_stok_obat_pasien.jml,gudangbarang.stok) as sisa,"+
                            " detail_permintaan_stok_obat_pasien.jam00,detail_permintaan_stok_obat_pasien.jam01,detail_permintaan_stok_obat_pasien.jam02,"+
                            " detail_permintaan_stok_obat_pasien.jam03,detail_permintaan_stok_obat_pasien.jam04,detail_permintaan_stok_obat_pasien.jam05,"+
                            " detail_permintaan_stok_obat_pasien.jam06,detail_permintaan_stok_obat_pasien.jam07,detail_permintaan_stok_obat_pasien.jam08,"+
                            " detail_permintaan_stok_obat_pasien.jam09,detail_permintaan_stok_obat_pasien.jam10,detail_permintaan_stok_obat_pasien.jam11,"+
                            " detail_permintaan_stok_obat_pasien.jam12,detail_permintaan_stok_obat_pasien.jam13,detail_permintaan_stok_obat_pasien.jam14,"+
                            " detail_permintaan_stok_obat_pasien.jam15,detail_permintaan_stok_obat_pasien.jam16,detail_permintaan_stok_obat_pasien.jam17,"+
                            " detail_permintaan_stok_obat_pasien.jam18,detail_permintaan_stok_obat_pasien.jam19,detail_permintaan_stok_obat_pasien.jam20,"+
                            " detail_permintaan_stok_obat_pasien.jam21,detail_permintaan_stok_obat_pasien.jam22,detail_permintaan_stok_obat_pasien.jam23 "+
                            " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "+
                            " inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join detail_permintaan_stok_obat_pasien on detail_permintaan_stok_obat_pasien.kode_brng=databarang.kode_brng "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " where gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.kode_brng like ? or "+
                            " gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.nama_brng like ? or "+
                            " gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.kode_sat like ? or "+
                            " gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and jenis.nama like ? "+
                            " group by databarang.kode_brng order by data_batch.tgl_kadaluarsa desc");  
                    try{
                        pstampil.setDouble(1,kenaikan);
                        pstampil.setString(2,kdgudang.getText());
                        pstampil.setString(3,nopermintaan);
                        pstampil.setString(4,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(5,kdgudang.getText());
                        pstampil.setString(6,nopermintaan);
                        pstampil.setString(7,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(8,kdgudang.getText());
                        pstampil.setString(9,nopermintaan);
                        pstampil.setString(10,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(11,kdgudang.getText());
                        pstampil.setString(12,nopermintaan);
                        pstampil.setString(13,"%"+TCari.getText().trim()+"%");
                        rstampil=pstampil.executeQuery();
                        while(rstampil.next()){
                            if(rstampil.getDouble("jml")>rstampil.getDouble("stok")){
                                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                            }
                            tabMode.addRow(new Object[]{
                               rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                               rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("harga"),100),
                               rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),rstampil.getString("no_faktur"),rstampil.getString("aturan_pakai"),
                               rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                               rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                               rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                               rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                               rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                               rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                               rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                               rstampil.getBoolean("jam23")
                            });
                        }                  
                    }catch(Exception e){
                        System.out.println("Notif Data Barang : "+e);
                    }finally{
                        if(rstampil!=null){
                            rstampil.close();
                        }
                        if(pstampil!=null){
                            pstampil.close();
                        }
                    } 
                }else{
                    pstampil=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama,"+
                            " databarang.kapasitas,databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                            " databarang."+hppfarmasi+" as dasar,gudangbarang.stok,detail_permintaan_stok_obat_pasien.jml,detail_permintaan_stok_obat_pasien.aturan_pakai, "+
                            " if(gudangbarang.stok>detail_permintaan_stok_obat_pasien.jml,detail_permintaan_stok_obat_pasien.jml,gudangbarang.stok) as sisa,"+
                            " detail_permintaan_stok_obat_pasien.jam00,detail_permintaan_stok_obat_pasien.jam01,detail_permintaan_stok_obat_pasien.jam02,"+
                            " detail_permintaan_stok_obat_pasien.jam03,detail_permintaan_stok_obat_pasien.jam04,detail_permintaan_stok_obat_pasien.jam05,"+
                            " detail_permintaan_stok_obat_pasien.jam06,detail_permintaan_stok_obat_pasien.jam07,detail_permintaan_stok_obat_pasien.jam08,"+
                            " detail_permintaan_stok_obat_pasien.jam09,detail_permintaan_stok_obat_pasien.jam10,detail_permintaan_stok_obat_pasien.jam11,"+
                            " detail_permintaan_stok_obat_pasien.jam12,detail_permintaan_stok_obat_pasien.jam13,detail_permintaan_stok_obat_pasien.jam14,"+
                            " detail_permintaan_stok_obat_pasien.jam15,detail_permintaan_stok_obat_pasien.jam16,detail_permintaan_stok_obat_pasien.jam17,"+
                            " detail_permintaan_stok_obat_pasien.jam18,detail_permintaan_stok_obat_pasien.jam19,detail_permintaan_stok_obat_pasien.jam20,"+
                            " detail_permintaan_stok_obat_pasien.jam21,detail_permintaan_stok_obat_pasien.jam22,detail_permintaan_stok_obat_pasien.jam23 "+
                            " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join detail_permintaan_stok_obat_pasien on detail_permintaan_stok_obat_pasien.kode_brng=databarang.kode_brng "+
                            " inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng where"+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and databarang.status='1' and gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.kode_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and databarang.status='1' and gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.nama_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and databarang.status='1' and gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.kode_sat like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and databarang.status='1' and gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and jenis.nama like ? order by databarang.nama_brng");  
                    try{
                        pstampil.setDouble(1,kenaikan);
                        pstampil.setString(2,kdgudang.getText());
                        pstampil.setString(3,nopermintaan);
                        pstampil.setString(4,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(5,kdgudang.getText());
                        pstampil.setString(6,nopermintaan);
                        pstampil.setString(7,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(8,kdgudang.getText());
                        pstampil.setString(9,nopermintaan);
                        pstampil.setString(10,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(11,kdgudang.getText());
                        pstampil.setString(12,nopermintaan);
                        pstampil.setString(13,"%"+TCari.getText().trim()+"%");
                        rstampil=pstampil.executeQuery();
                        while(rstampil.next()){
                            if(rstampil.getDouble("jml")>rstampil.getDouble("stok")){
                                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                            }
                            tabMode.addRow(new Object[]{
                               rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                               rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),
                               Valid.roundUp(rstampil.getDouble("harga"),100),rstampil.getDouble("dasar"),0,"","",rstampil.getString("aturan_pakai"),
                               rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                               rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                               rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                               rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                               rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                               rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                               rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                               rstampil.getBoolean("jam23")
                            });
                        }                  
                    }catch(Exception e){
                        System.out.println("Notif Data Barang : "+e);
                    }finally{
                        if(rstampil!=null){
                            rstampil.close();
                        }
                        if(pstampil!=null){
                            pstampil.close();
                        }
                    }
                }
            }else{
                if(aktifkanbatch.equals("yes")){
                    pstampil=koneksi.prepareStatement("select data_batch.kode_brng, databarang.nama_brng,jenis.nama,"+
                            " databarang.kapasitas,databarang.kode_sat,data_batch.kelas1,data_batch.kelas2,data_batch.kelas3,"+
                            " data_batch.utama,data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.karyawan,"+
                            " data_batch."+hppfarmasi+" as dasar,gudangbarang.stok,data_batch.no_batch,data_batch.no_faktur,"+
                            " detail_permintaan_stok_obat_pasien.jml,detail_permintaan_stok_obat_pasien.aturan_pakai, "+
                            " if(gudangbarang.stok>detail_permintaan_stok_obat_pasien.jml,detail_permintaan_stok_obat_pasien.jml,gudangbarang.stok) as sisa,"+
                            " detail_permintaan_stok_obat_pasien.jam00,detail_permintaan_stok_obat_pasien.jam01,detail_permintaan_stok_obat_pasien.jam02,"+
                            " detail_permintaan_stok_obat_pasien.jam03,detail_permintaan_stok_obat_pasien.jam04,detail_permintaan_stok_obat_pasien.jam05,"+
                            " detail_permintaan_stok_obat_pasien.jam06,detail_permintaan_stok_obat_pasien.jam07,detail_permintaan_stok_obat_pasien.jam08,"+
                            " detail_permintaan_stok_obat_pasien.jam09,detail_permintaan_stok_obat_pasien.jam10,detail_permintaan_stok_obat_pasien.jam11,"+
                            " detail_permintaan_stok_obat_pasien.jam12,detail_permintaan_stok_obat_pasien.jam13,detail_permintaan_stok_obat_pasien.jam14,"+
                            " detail_permintaan_stok_obat_pasien.jam15,detail_permintaan_stok_obat_pasien.jam16,detail_permintaan_stok_obat_pasien.jam17,"+
                            " detail_permintaan_stok_obat_pasien.jam18,detail_permintaan_stok_obat_pasien.jam19,detail_permintaan_stok_obat_pasien.jam20,"+
                            " detail_permintaan_stok_obat_pasien.jam21,detail_permintaan_stok_obat_pasien.jam22,detail_permintaan_stok_obat_pasien.jam23 "+
                            " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "+
                            " inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join detail_permintaan_stok_obat_pasien on detail_permintaan_stok_obat_pasien.kode_brng=databarang.kode_brng "+
                            " inner join gudangbarang on gudangbarang.kode_brng=data_batch.kode_brng and gudangbarang.no_batch=data_batch.no_batch and gudangbarang.no_faktur=data_batch.no_faktur "+
                            " where gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.kode_brng like ? or "+
                            " gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.nama_brng like ? or "+
                            " gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.kode_sat like ? or "+
                            " gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and jenis.nama like ? "+
                            " group by databarang.kode_brng order by data_batch.tgl_kadaluarsa desc");  
                    try{
                        pstampil.setString(1,kdgudang.getText());
                        pstampil.setString(2,nopermintaan);
                        pstampil.setString(3,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(4,kdgudang.getText());
                        pstampil.setString(5,nopermintaan);
                        pstampil.setString(6,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(7,kdgudang.getText());
                        pstampil.setString(8,nopermintaan);
                        pstampil.setString(9,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(10,kdgudang.getText());
                        pstampil.setString(11,nopermintaan);
                        pstampil.setString(12,"%"+TCari.getText().trim()+"%");
                        rstampil=pstampil.executeQuery();
                        while(rstampil.next()){
                            if(rstampil.getDouble("jml")>rstampil.getDouble("stok")){
                                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                            }
                            if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("kelas1"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),rstampil.getString("no_faktur"),rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("kelas2"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),rstampil.getString("no_faktur"),rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("kelas3"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),rstampil.getString("no_faktur"),rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("utama"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),rstampil.getString("no_faktur"),rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("vip"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),rstampil.getString("no_faktur"),rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("vvip"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),rstampil.getString("no_faktur"),rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("beliluar"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),rstampil.getString("no_faktur"),rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("karyawan"),100),
                                   rstampil.getDouble("dasar"),0,rstampil.getString("no_batch"),rstampil.getString("no_faktur"),rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }
                        }                  
                    }catch(Exception e){
                        System.out.println("Notif Data Barang : "+e);
                    }finally{
                        if(rstampil!=null){
                            rstampil.close();
                        }
                        if(pstampil!=null){
                            pstampil.close();
                        }
                    } 
                }else{
                    pstampil=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama,"+
                            " databarang.kapasitas,databarang.kode_sat,databarang.kelas1,"+
                            " databarang.kelas2,databarang.kelas3,databarang.utama,databarang.vip,"+
                            " databarang.vvip,databarang.beliluar,databarang.karyawan,databarang."+hppfarmasi+" as dasar,gudangbarang.stok,"+
                            " detail_permintaan_stok_obat_pasien.jml,detail_permintaan_stok_obat_pasien.aturan_pakai, "+
                            " if(gudangbarang.stok>detail_permintaan_stok_obat_pasien.jml,detail_permintaan_stok_obat_pasien.jml,gudangbarang.stok) as sisa,"+
                            " detail_permintaan_stok_obat_pasien.jam00,detail_permintaan_stok_obat_pasien.jam01,detail_permintaan_stok_obat_pasien.jam02,"+
                            " detail_permintaan_stok_obat_pasien.jam03,detail_permintaan_stok_obat_pasien.jam04,detail_permintaan_stok_obat_pasien.jam05,"+
                            " detail_permintaan_stok_obat_pasien.jam06,detail_permintaan_stok_obat_pasien.jam07,detail_permintaan_stok_obat_pasien.jam08,"+
                            " detail_permintaan_stok_obat_pasien.jam09,detail_permintaan_stok_obat_pasien.jam10,detail_permintaan_stok_obat_pasien.jam11,"+
                            " detail_permintaan_stok_obat_pasien.jam12,detail_permintaan_stok_obat_pasien.jam13,detail_permintaan_stok_obat_pasien.jam14,"+
                            " detail_permintaan_stok_obat_pasien.jam15,detail_permintaan_stok_obat_pasien.jam16,detail_permintaan_stok_obat_pasien.jam17,"+
                            " detail_permintaan_stok_obat_pasien.jam18,detail_permintaan_stok_obat_pasien.jam19,detail_permintaan_stok_obat_pasien.jam20,"+
                            " detail_permintaan_stok_obat_pasien.jam21,detail_permintaan_stok_obat_pasien.jam22,detail_permintaan_stok_obat_pasien.jam23 "+
                            " from databarang inner join jenis on databarang.kdjns=jenis.kdjns "+
                            " inner join detail_permintaan_stok_obat_pasien on detail_permintaan_stok_obat_pasien.kode_brng=databarang.kode_brng "+
                            " inner join gudangbarang on databarang.kode_brng=gudangbarang.kode_brng where"+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and databarang.status='1' and gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.kode_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and databarang.status='1' and gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.nama_brng like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and databarang.status='1' and gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and databarang.kode_sat like ? or "+
                            " gudangbarang.no_batch='' and gudangbarang.no_faktur='' and databarang.status='1' and gudangbarang.kd_bangsal=? and detail_permintaan_stok_obat_pasien.no_permintaan=? and jenis.nama like ? order by databarang.nama_brng");                
                    try{
                        pstampil.setString(1,kdgudang.getText());
                        pstampil.setString(2,nopermintaan);
                        pstampil.setString(3,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(4,kdgudang.getText());
                        pstampil.setString(5,nopermintaan);
                        pstampil.setString(6,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(7,kdgudang.getText());
                        pstampil.setString(8,nopermintaan);
                        pstampil.setString(9,"%"+TCari.getText().trim()+"%");
                        pstampil.setString(10,kdgudang.getText());
                        pstampil.setString(11,nopermintaan);
                        pstampil.setString(12,"%"+TCari.getText().trim()+"%");
                        rstampil=pstampil.executeQuery();
                        while(rstampil.next()){
                            if(rstampil.getDouble("jml")>rstampil.getDouble("stok")){
                                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                            }
                            if(Jeniskelas.getSelectedItem().equals("Kelas 1")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("kelas1"),100),
                                   rstampil.getDouble("dasar"),0,"","",rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Kelas 2")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("kelas2"),100),
                                   rstampil.getDouble("dasar"),0,"","",rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Kelas 3")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("kelas3"),100),
                                   rstampil.getDouble("dasar"),0,"","",rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("utama"),100),
                                   rstampil.getDouble("dasar"),0,"","",rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("VIP")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("vip"),100),
                                   rstampil.getDouble("dasar"),0,"","",rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("VVIP")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("vvip"),100),
                                   rstampil.getDouble("dasar"),0,"","",rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("beliluar"),100),
                                   rstampil.getDouble("dasar"),0,"","",rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }else if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                                tabMode.addRow(new Object[]{
                                   rstampil.getString("sisa"),rstampil.getString("kode_brng"),rstampil.getString("nama_brng"),rstampil.getString("nama"),
                                   rstampil.getString("kode_sat"),rstampil.getDouble("kapasitas"),rstampil.getDouble("stok"),Valid.roundUp(rstampil.getDouble("karyawan"),100),
                                   rstampil.getDouble("dasar"),0,"","",rstampil.getString("aturan_pakai"),
                                   rstampil.getBoolean("jam00"),rstampil.getBoolean("jam01"),rstampil.getBoolean("jam02"),rstampil.getBoolean("jam03"),rstampil.getBoolean("jam04"),
                                   rstampil.getBoolean("jam05"),rstampil.getBoolean("jam06"),rstampil.getBoolean("jam07"),
                                   rstampil.getBoolean("jam08"),rstampil.getBoolean("jam09"),rstampil.getBoolean("jam10"),
                                   rstampil.getBoolean("jam11"),rstampil.getBoolean("jam12"),rstampil.getBoolean("jam13"),
                                   rstampil.getBoolean("jam14"),rstampil.getBoolean("jam15"),rstampil.getBoolean("jam16"),
                                   rstampil.getBoolean("jam17"),rstampil.getBoolean("jam18"),rstampil.getBoolean("jam19"),
                                   rstampil.getBoolean("jam20"),rstampil.getBoolean("jam21"),rstampil.getBoolean("jam22"),
                                   rstampil.getBoolean("jam23")
                                });
                            }
                        }                  
                    }catch(Exception e){
                        System.out.println("Notif Data Barang : "+e);
                    }finally{
                        if(rstampil!=null){
                            rstampil.close();
                        }
                        if(pstampil!=null){
                            pstampil.close();
                        }
                    }   
                }
            } 
            
            ttl=0;
            for(int r=0;r<tabMode.getRowCount();r++){ 
                 y=0;
                 try {
                     y=Double.parseDouble(tabMode.getValueAt(r,0).toString())*
                       Double.parseDouble(tabMode.getValueAt(r,7).toString());
                 } catch (Exception e) {
                     y=0;
                 }
                 tbDokter.setValueAt(y,r,9);
                 ttl=ttl+y;
            }
            LTotal.setText(Valid.SetAngka(ttl));
            ppnobat=0;
            if(tampilkan_ppnobat_ranap.equals("Yes")){
                 ppnobat=ttl*0.11;
                 ttl=ttl+ppnobat;
                 LPpn.setText(Valid.SetAngka(ppnobat));
            }
            LTotalTagihan.setText(Valid.SetAngka(ttl));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
     
    public void isCek(){
        BtnSimpan.setEnabled(akses.getstok_obat_pasien());   
        TCari.setText("");
        TCari.requestFocus();
    }

    public void setNoRm(String no_rawat,String pasien){
        norawat.setText(no_rawat);
        nm_pasien.setText(pasien);
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",no_rawat));
        kelas.setText(Sequel.cariIsi(
                "select kamar.kelas from kamar inner join kamar_inap on kamar.kd_kamar=kamar_inap.kd_kamar "+
                "where kamar_inap.no_rawat=? and kamar_inap.stts_pulang='-' order by STR_TO_DATE(concat(kamar_inap.tgl_masuk,' ',kamar_inap.jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",no_rawat));
        if(kelas.getText().equals("Kelas 1")){
            Jeniskelas.setSelectedItem("Kelas 1");
        }else if(kelas.getText().equals("Kelas 2")){
            Jeniskelas.setSelectedItem("Kelas 2");
        }else if(kelas.getText().equals("Kelas 3")){
            Jeniskelas.setSelectedItem("Kelas 3");
        }else if(kelas.getText().equals("Kelas Utama")){
            Jeniskelas.setSelectedItem("Utama/BPJS");
        }else if(kelas.getText().equals("Kelas VIP")){
            Jeniskelas.setSelectedItem("VIP");
        }else if(kelas.getText().equals("Kelas VVIP")){
            Jeniskelas.setSelectedItem("VVIP");
        }    
        
        this.nopermintaan="";
        kenaikan=Sequel.cariIsiAngka2("select (set_harga_obat_ranap.hargajual/100) from set_harga_obat_ranap where set_harga_obat_ranap.kd_pj=? and set_harga_obat_ranap.kelas=?",KdPj.getText(),kelas.getText());
        kdgudang.setText(akses.getkdbangsal());
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
    }

    private void getData() {
        int row=tbDokter.getSelectedRow();
        if(nmgudang.getText().trim().equals("")){
             Valid.textKosong(kdgudang,"Asal Stok");
        }else if(row!= -1){            
             if(!tabMode.getValueAt(row,0).toString().equals("")){
                try {
                    if(Double.parseDouble(tabMode.getValueAt(row,0).toString())>0){
                        stokobat=0;   
                        if(aktifkanbatch.equals("yes")){
                            pstampil=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch=? and gudangbarang.no_faktur=?");
                            try {
                                pstampil.setString(1,kdgudang.getText());
                                pstampil.setString(2,tbDokter.getValueAt(row,1).toString());
                                pstampil.setString(3,tbDokter.getValueAt(row,10).toString());
                                pstampil.setString(4,tbDokter.getValueAt(row,11).toString());
                                rstampil=pstampil.executeQuery();
                                if(rstampil.next()){
                                    stokobat=rstampil.getDouble(1);
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
                        }else{
                            pstampil=koneksi.prepareStatement("select ifnull(gudangbarang.stok,'0') from gudangbarang where gudangbarang.kd_bangsal=? and gudangbarang.kode_brng=? and gudangbarang.no_batch='' and gudangbarang.no_faktur=''");
                            try {
                                pstampil.setString(1,kdgudang.getText());
                                pstampil.setString(2,tbDokter.getValueAt(row,1).toString());
                                rstampil=pstampil.executeQuery();
                                if(rstampil.next()){
                                    stokobat=rstampil.getDouble(1);
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
                        }

                        tbDokter.setValueAt(stokobat,row,6);
                        y=0;
                        try {
                            y=Double.parseDouble(tabMode.getValueAt(row,0).toString());
                        } catch (Exception e) {
                            y=0;
                        }
                        
                        if(stokobat<y){
                              JOptionPane.showMessageDialog(null,"Maaf, Stok tidak cukup....!!!");
                              TCari.requestFocus();
                              tabMode.setValueAt("", row,0); 
                              tabMode.setValueAt(0, row,9); 
                        }else{
                              y=Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),0).toString())*
                                  Double.parseDouble(tabMode.getValueAt(tbDokter.getSelectedRow(),7).toString());
                              tbDokter.setValueAt(y,tbDokter.getSelectedRow(),9);
                              TCari.setText("");
                        } 
                    }
                } catch (Exception e) {
                    tabMode.setValueAt("", row,0);
                    tabMode.setValueAt(0, row,9);
                }                                       
             } 
             
             ttl=0;
             for(int r=0;r<tabMode.getRowCount();r++){ 
                 y=0;
                 try {
                     y=Double.parseDouble(tabMode.getValueAt(r,0).toString())*
                       Double.parseDouble(tabMode.getValueAt(r,7).toString());
                 } catch (Exception e) {
                     y=0;
                 }
                 ttl=ttl+y;
             }
             LTotal.setText(Valid.SetAngka(ttl));
             ppnobat=0;
             if(tampilkan_ppnobat_ranap.equals("Yes")){
                 ppnobat=ttl*0.11;
                 ttl=ttl+ppnobat;
                 LPpn.setText(Valid.SetAngka(ppnobat));
             }
             LTotalTagihan.setText(Valid.SetAngka(ttl));
        }
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
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
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void pilihancetak() {
        pilihanetiket = (String)JOptionPane.showInputDialog(null,"Silahkan pilih cetak aturan pakai..!!","Cetak Aturan Pakai",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Cetak Aturan Pakai Model 1","Cetak Aturan Pakai Model 2","Cetak Aturan Pakai Model 3","Cetak Label Obat","Cetak Label Obat 2","Cetak Aturan Pakai Model 1 & Cetak Label Obat","Cetak Aturan Pakai Model 2 & Cetak Label Obat","Cetak Aturan Pakai Model 3 & Cetak Label Obat","Cetak Aturan Pakai Model 1 & Cetak Label Obat 2","Cetak Aturan Pakai Model 2 & Cetak Label Obat 2","Cetak Aturan Pakai Model 3 & Cetak Label Obat 2"},"Cetak Aturan Pakai Model 1");
        switch (pilihanetiket) {
            case "Cetak Aturan Pakai Model 1": 
                aturanpakai1();
                break;
            case "Cetak Aturan Pakai Model 2": 
                aturanpakai2();
                break;
            case "Cetak Aturan Pakai Model 3": 
                aturanpakai3();
                break;
            case "Cetak Label Obat": 
                cetaklabel1();
                break;
            case "Cetak Aturan Pakai Model 1 & Cetak Label Obat":
                aturanpakai1();
                cetaklabel1();
                break;
            case "Cetak Aturan Pakai Model 2 & Cetak Label Obat": 
                aturanpakai2();
                cetaklabel1();
                break;
            case "Cetak Aturan Pakai Model 3 & Cetak Label Obat": 
                aturanpakai3();
                cetaklabel1();
                break;
            case "Cetak Label Obat 2" :
                cetaklabel2();
                break;
            case "Cetak Aturan Pakai Model 1 & Cetak Label Obat 2" :
                aturanpakai1();
                cetaklabel2();
                break;
            case "Cetak Aturan Pakai Model 2 & Cetak Label Obat 2" : 
                aturanpakai2();
                cetaklabel2();
                break;
            case "Cetak Aturan Pakai Model 3 & Cetak Label Obat 2" : 
                aturanpakai3();
                cetaklabel2();
                break;
            default:
                break;
        }
    }

    private void aturanpakai1() {
        Map<String, Object> param = new HashMap<>();  
        param.put("namars",akses.getnamars());
        param.put("alamatrs",akses.getalamatrs());
        param.put("kotars",akses.getkabupatenrs());
        param.put("propinsirs",akses.getpropinsirs());
        param.put("kontakrs",akses.getkontakrs());
        param.put("emailrs",akses.getemailrs());   

        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'")>0){
            param.put("waktu","JAM 00");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'",param);
        }   
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'")>0){
            param.put("waktu","JAM 01");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'")>0){
            param.put("waktu","JAM 02");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'")>0){
            param.put("waktu","JAM 03");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'")>0){
            param.put("waktu","JAM 04");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'")>0){
            param.put("waktu","JAM 05");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'")>0){
            param.put("waktu","JAM 06");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'")>0){
            param.put("waktu","JAM 07");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'")>0){
            param.put("waktu","JAM 08");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'")>0){
            param.put("waktu","JAM 09");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'")>0){
            param.put("waktu","JAM 10");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'")>0){
            param.put("waktu","JAM 11");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'")>0){
            param.put("waktu","JAM 12");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'")>0){
            param.put("waktu","JAM 13");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'")>0){
            param.put("waktu","JAM 14");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'")>0){
            param.put("waktu","JAM 15");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'")>0){
            param.put("waktu","JAM 16");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'")>0){
            param.put("waktu","JAM 17");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'")>0){
            param.put("waktu","JAM 18");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'")>0){
            param.put("waktu","JAM 19");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'")>0){
            param.put("waktu","JAM 20");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'")>0){
            param.put("waktu","JAM 21");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'")>0){
            param.put("waktu","JAM 22");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'")>0){
            param.put("waktu","JAM 23");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void aturanpakai2() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Map<String, Object> param = new HashMap<>();  
        param.put("namars",akses.getnamars());
        param.put("alamatrs",akses.getalamatrs());
        param.put("kotars",akses.getkabupatenrs());
        param.put("propinsirs",akses.getpropinsirs());
        param.put("kontakrs",akses.getkontakrs());
        param.put("emailrs",akses.getemailrs()); 
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'")>0){
            param.put("waktu","JAM 00");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'",param);
        }   
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'")>0){
            param.put("waktu","JAM 01");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'")>0){
            param.put("waktu","JAM 02");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'")>0){
            param.put("waktu","JAM 03");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'")>0){
            param.put("waktu","JAM 04");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'")>0){
            param.put("waktu","JAM 05");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'")>0){
            param.put("waktu","JAM 06");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'")>0){
            param.put("waktu","JAM 07");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'")>0){
            param.put("waktu","JAM 08");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'")>0){
            param.put("waktu","JAM 09");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'")>0){
            param.put("waktu","JAM 10");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'")>0){
            param.put("waktu","JAM 11");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'")>0){
            param.put("waktu","JAM 12");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'")>0){
            param.put("waktu","JAM 13");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'")>0){
            param.put("waktu","JAM 14");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'")>0){
            param.put("waktu","JAM 15");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'")>0){
            param.put("waktu","JAM 16");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'")>0){
            param.put("waktu","JAM 17");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'")>0){
            param.put("waktu","JAM 18");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'")>0){
            param.put("waktu","JAM 19");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'")>0){
            param.put("waktu","JAM 20");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'")>0){
            param.put("waktu","JAM 21");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'")>0){
            param.put("waktu","JAM 22");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'")>0){
            param.put("waktu","JAM 23");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void aturanpakai3() {
        Map<String, Object> param = new HashMap<>();  
        param.put("namars",akses.getnamars());
        param.put("alamatrs",akses.getalamatrs());
        param.put("kotars",akses.getkabupatenrs());
        param.put("propinsirs",akses.getpropinsirs());
        param.put("kontakrs",akses.getkontakrs());
        param.put("emailrs",akses.getemailrs()); 
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'")>0){
            param.put("waktu","JAM 00");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'",param);
        }   
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'")>0){
            param.put("waktu","JAM 01");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'")>0){
            param.put("waktu","JAM 02");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'")>0){
            param.put("waktu","JAM 03");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'")>0){
            param.put("waktu","JAM 04");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'")>0){
            param.put("waktu","JAM 05");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'")>0){
            param.put("waktu","JAM 06");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'")>0){
            param.put("waktu","JAM 07");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'")>0){
            param.put("waktu","JAM 08");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'")>0){
            param.put("waktu","JAM 09");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'")>0){
            param.put("waktu","JAM 10");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'")>0){
            param.put("waktu","JAM 11");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'")>0){
            param.put("waktu","JAM 12");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'")>0){
            param.put("waktu","JAM 13");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'")>0){
            param.put("waktu","JAM 14");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'")>0){
            param.put("waktu","JAM 15");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'")>0){
            param.put("waktu","JAM 16");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'")>0){
            param.put("waktu","JAM 17");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'")>0){
            param.put("waktu","JAM 18");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'")>0){
            param.put("waktu","JAM 19");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'")>0){
            param.put("waktu","JAM 20");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'")>0){
            param.put("waktu","JAM 21");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'")>0){
            param.put("waktu","JAM 22");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'")>0){
            param.put("waktu","JAM 23");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptItemStokPasien3.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void cetaklabel1() {
        Map<String, Object> param = new HashMap<>();  
        param.put("namars",akses.getnamars());
        param.put("alamatrs",akses.getalamatrs());
        param.put("kotars",akses.getkabupatenrs());
        param.put("propinsirs",akses.getpropinsirs());
        param.put("kontakrs",akses.getkontakrs());
        param.put("emailrs",akses.getemailrs()); 
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>''")>0){
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien.jasper","report","::[ Label Obat Stok Pasien ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai,"+
                "stok_obat_pasien.jam00,stok_obat_pasien.jam01,stok_obat_pasien.jam02,stok_obat_pasien.jam03,stok_obat_pasien.jam04,stok_obat_pasien.jam05,"+
                "stok_obat_pasien.jam06,stok_obat_pasien.jam07,stok_obat_pasien.jam08,stok_obat_pasien.jam09,stok_obat_pasien.jam10,stok_obat_pasien.jam11,"+
                "stok_obat_pasien.jam12,stok_obat_pasien.jam13,stok_obat_pasien.jam14,stok_obat_pasien.jam15,stok_obat_pasien.jam16,stok_obat_pasien.jam17,"+
                "stok_obat_pasien.jam18,stok_obat_pasien.jam19,stok_obat_pasien.jam20,stok_obat_pasien.jam21,stok_obat_pasien.jam22,stok_obat_pasien.jam23 "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>''",param);
        }           
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void cetaklabel2() {
        Map<String, Object> param = new HashMap<>();  
        param.put("namars",akses.getnamars());
        param.put("alamatrs",akses.getalamatrs());
        param.put("kotars",akses.getkabupatenrs());
        param.put("propinsirs",akses.getpropinsirs());
        param.put("kontakrs",akses.getkontakrs());
        param.put("emailrs",akses.getemailrs()); 
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'")>0){
            param.put("waktu","JAM 00");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam00='true'",param);
        }   
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'")>0){
            param.put("waktu","JAM 01");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam01='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'")>0){
            param.put("waktu","JAM 02");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam02='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'")>0){
            param.put("waktu","JAM 03");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam03='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'")>0){
            param.put("waktu","JAM 04");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam04='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'")>0){
            param.put("waktu","JAM 05");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam05='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'")>0){
            param.put("waktu","JAM 06");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam06='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'")>0){
            param.put("waktu","JAM 07");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam07='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'")>0){
            param.put("waktu","JAM 08");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam08='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'")>0){
            param.put("waktu","JAM 09");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam09='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'")>0){
            param.put("waktu","JAM 10");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam10='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'")>0){
            param.put("waktu","JAM 11");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam11='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'")>0){
            param.put("waktu","JAM 12");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam12='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'")>0){
            param.put("waktu","JAM 13");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam13='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'")>0){
            param.put("waktu","JAM 14");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam14='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'")>0){
            param.put("waktu","JAM 15");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam15='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'")>0){
            param.put("waktu","JAM 16");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam16='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'")>0){
            param.put("waktu","JAM 17");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam17='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'")>0){
            param.put("waktu","JAM 18");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam18='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'")>0){
            param.put("waktu","JAM 19");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam19='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'")>0){
            param.put("waktu","JAM 20");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam20='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'")>0){
            param.put("waktu","JAM 21");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam21='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'")>0){
            param.put("waktu","JAM 22");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam22='true'",param);
        }
        if(Sequel.cariInteger("select count(*) from stok_obat_pasien where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'")>0){
            param.put("waktu","JAM 23");  
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptLabelDaftarObatStokPasien2.jasper","report","::[ Aturan Pakai Obat ]::",
                "select stok_obat_pasien.tanggal,stok_obat_pasien.jam, stok_obat_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "stok_obat_pasien.kode_brng,databarang.nama_brng, stok_obat_pasien.jumlah,stok_obat_pasien.kd_bangsal,"+
                "stok_obat_pasien.no_batch,stok_obat_pasien.no_faktur,stok_obat_pasien.aturan_pakai "+
                "from stok_obat_pasien inner join reg_periksa on stok_obat_pasien.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join databarang on stok_obat_pasien.kode_brng=databarang.kode_brng "+
                "where stok_obat_pasien.no_rawat='"+norawat.getText()+"' and stok_obat_pasien.tanggal='"+Valid.SetTgl(Tgl.getSelectedItem()+"")+"' and stok_obat_pasien.jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"' and stok_obat_pasien.aturan_pakai<>'' and stok_obat_pasien.jam23='true'",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
 
}

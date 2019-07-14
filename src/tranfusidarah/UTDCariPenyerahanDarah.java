package tranfusidarah;
import inventory.riwayatobat;
import inventory.*;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import kepegawaian.DlgCariPetugas;

public class UTDCariPenyerahanDarah extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement pspenyerahan,psdarah,pscekmedis,psceknonmedis;
    private ResultSet rs,rs2;
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private riwayatobat Trackobat=new riwayatobat();
    private int i=0,no=1,pilih=0;
    private double pendapatan=0,subtotalpendapatan=0,subtotalmedis=0,subtotalnonmedis=0;
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private UTDKomponenDarah komponen=new UTDKomponenDarah(null,true);
    private String verifikasi_penyerahan_darah_di_kasir=Sequel.cariIsi(
            "select verifikasi_penyerahan_darah_di_kasir from set_nota");
    private String aktifkan="",
            sqlpscekmedis="select utd_penggunaan_medis_penyerahan_darah.kode_brng,databarang.nama_brng,utd_penggunaan_medis_penyerahan_darah.jml,utd_penggunaan_medis_penyerahan_darah.harga,"+
                            "utd_penggunaan_medis_penyerahan_darah.total,databarang.kode_sat from utd_penggunaan_medis_penyerahan_darah inner join databarang "+
                            "on utd_penggunaan_medis_penyerahan_darah.kode_brng=databarang.kode_brng where utd_penggunaan_medis_penyerahan_darah.no_penyerahan=?",
            sqlpsceknonmedis="select utd_penggunaan_penunjang_penyerahan_darah.kode_brng,ipsrsbarang.nama_brng,utd_penggunaan_penunjang_penyerahan_darah.jml,utd_penggunaan_penunjang_penyerahan_darah.harga,"+
                            "utd_penggunaan_penunjang_penyerahan_darah.total,ipsrsbarang.kode_sat from utd_penggunaan_penunjang_penyerahan_darah inner join ipsrsbarang "+
                            "on utd_penggunaan_penunjang_penyerahan_darah.kode_brng=ipsrsbarang.kode_brng where utd_penggunaan_penunjang_penyerahan_darah.no_penyerahan=?";
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public UTDCariPenyerahanDarah(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new String[]{
            "No.Penyerahan","Tanggal","Dinas","Petugas Cross","Keterangan","Status",
            "Pengambil Darah","Alamat Pengambil Darah","Petugas P.J."
         }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(40);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(130);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        nopenyerahan.setDocument(new batasInput((byte)17).getKata(nopenyerahan));
        kdptgcross.setDocument(new batasInput((byte)20).getKata(kdptgcross));
        kdptgpj.setDocument(new batasInput((byte)20).getKata(kdptgpj));
        kdkomponen.setDocument(new batasInput((byte)5).getKata(kdkomponen));
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
        
        komponen.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(komponen.getTable().getSelectedRow()!= -1){ 
                    kdkomponen.setText(komponen.getTable().getValueAt(komponen.getTable().getSelectedRow(),0).toString());
                    nmkomponen.setText(komponen.getTable().getValueAt(komponen.getTable().getSelectedRow(),1).toString());
                    kdkomponen.requestFocus();
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
        
        komponen.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        komponen.dispose();
                    }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    if(pilih==1){
                        kdptgcross.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmptgcross.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdptgcross.requestFocus();
                    }else if(pilih==2){
                        kdptgpj.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmptgpj.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdptgpj.requestFocus();
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
        
        if(verifikasi_penyerahan_darah_di_kasir.equals("Yes")){
            ppVerif.setVisible(true);
        }else{
            ppVerif.setVisible(false);
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
        ppCetakNota = new javax.swing.JMenuItem();
        ppHapus = new javax.swing.JMenuItem();
        ppVerif = new javax.swing.JMenuItem();
        ppTampilkanBHPMedis = new javax.swing.JMenuItem();
        ppTampilkanBHPPenunjang = new javax.swing.JMenuItem();
        ppTampilkanBHPPenunjangDanMedis = new javax.swing.JMenuItem();
        ppHapusBHPMedis = new javax.swing.JMenuItem();
        ppHapusBHPNonMedis = new javax.swing.JMenuItem();
        ppHapusBHPMedisDanNonMedis = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdkomponen = new widget.TextBox();
        nmkomponen = new widget.TextBox();
        btnKomponen = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        nopenyerahan = new widget.TextBox();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdptgcross = new widget.TextBox();
        kdptgpj = new widget.TextBox();
        nmptgcross = new widget.TextBox();
        nmptgpj = new widget.TextBox();
        btnPetugasCros = new widget.Button();
        btnPetugasPJ = new widget.Button();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppCetakNota.setBackground(new java.awt.Color(255, 255, 254));
        ppCetakNota.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCetakNota.setForeground(new java.awt.Color(70, 70, 70));
        ppCetakNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppCetakNota.setText("Cetak Ulang Nota");
        ppCetakNota.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCetakNota.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCetakNota.setName("ppCetakNota"); // NOI18N
        ppCetakNota.setPreferredSize(new java.awt.Dimension(150, 25));
        ppCetakNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCetakNotaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppCetakNota);

        ppHapus.setBackground(new java.awt.Color(255, 255, 254));
        ppHapus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapus.setForeground(new java.awt.Color(70, 70, 70));
        ppHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapus.setText("Hapus Penyerahan Darah");
        ppHapus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapus.setName("ppHapus"); // NOI18N
        ppHapus.setPreferredSize(new java.awt.Dimension(150, 25));
        ppHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapus);

        ppVerif.setBackground(new java.awt.Color(255, 255, 254));
        ppVerif.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppVerif.setForeground(new java.awt.Color(70, 70, 70));
        ppVerif.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppVerif.setText("Verifikasi");
        ppVerif.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppVerif.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppVerif.setName("ppVerif"); // NOI18N
        ppVerif.setPreferredSize(new java.awt.Dimension(150, 25));
        ppVerif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppVerifActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppVerif);

        ppTampilkanBHPMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPMedis.setText("Tampilkan Penggunaan BHP Medis");
        ppTampilkanBHPMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPMedis.setName("ppTampilkanBHPMedis"); // NOI18N
        ppTampilkanBHPMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPMedis);

        ppTampilkanBHPPenunjang.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPPenunjang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPPenunjang.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPPenunjang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPPenunjang.setText("Tampilkan Penggunaan BHP Non Medis");
        ppTampilkanBHPPenunjang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPPenunjang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPPenunjang.setName("ppTampilkanBHPPenunjang"); // NOI18N
        ppTampilkanBHPPenunjang.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPPenunjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPPenunjangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPPenunjang);

        ppTampilkanBHPPenunjangDanMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanBHPPenunjangDanMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppTampilkanBHPPenunjangDanMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setText("Tampilkan Penggunaan BHP Medis & Non Medis");
        ppTampilkanBHPPenunjangDanMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanBHPPenunjangDanMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanBHPPenunjangDanMedis.setName("ppTampilkanBHPPenunjangDanMedis"); // NOI18N
        ppTampilkanBHPPenunjangDanMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppTampilkanBHPPenunjangDanMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanBHPPenunjangDanMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanBHPPenunjangDanMedis);

        ppHapusBHPMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPMedis.setText("Hapus Penggunaan BHP Medis");
        ppHapusBHPMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPMedis.setName("ppHapusBHPMedis"); // NOI18N
        ppHapusBHPMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPMedis);

        ppHapusBHPNonMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPNonMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPNonMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPNonMedis.setText("Hapus Penggunaan BHP Non Medis");
        ppHapusBHPNonMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPNonMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPNonMedis.setName("ppHapusBHPNonMedis"); // NOI18N
        ppHapusBHPNonMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPNonMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPNonMedis);

        ppHapusBHPMedisDanNonMedis.setBackground(new java.awt.Color(255, 255, 254));
        ppHapusBHPMedisDanNonMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppHapusBHPMedisDanNonMedis.setForeground(new java.awt.Color(70, 70, 70));
        ppHapusBHPMedisDanNonMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppHapusBHPMedisDanNonMedis.setText("Hapus Penggunaan BHP Medis & Non Medis");
        ppHapusBHPMedisDanNonMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppHapusBHPMedisDanNonMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppHapusBHPMedisDanNonMedis.setName("ppHapusBHPMedisDanNonMedis"); // NOI18N
        ppHapusBHPMedisDanNonMedis.setPreferredSize(new java.awt.Dimension(280, 25));
        ppHapusBHPMedisDanNonMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppHapusBHPMedisDanNonMedisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppHapusBHPMedisDanNonMedis);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Penyerahan Darah UTD ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        label17.setText("Komponen Darah :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(label17);

        kdkomponen.setName("kdkomponen"); // NOI18N
        kdkomponen.setPreferredSize(new java.awt.Dimension(80, 23));
        kdkomponen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdkomponenKeyPressed(evt);
            }
        });
        panelisi4.add(kdkomponen);

        nmkomponen.setEditable(false);
        nmkomponen.setName("nmkomponen"); // NOI18N
        nmkomponen.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmkomponen);

        btnKomponen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKomponen.setMnemonic('4');
        btnKomponen.setToolTipText("Alt+4");
        btnKomponen.setName("btnKomponen"); // NOI18N
        btnKomponen.setPreferredSize(new java.awt.Dimension(28, 23));
        btnKomponen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKomponenActionPerformed(evt);
            }
        });
        panelisi4.add(btnKomponen);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(97, 23));
        panelisi4.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi4.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Total Pendapatan Penyerahan Darah UTD(BHP tidak dihitung) : Rp.");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(330, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(150, 30));
        panelisi1.add(LTotal);

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

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Penyerahan :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 95, 23);

        nopenyerahan.setName("nopenyerahan"); // NOI18N
        nopenyerahan.setPreferredSize(new java.awt.Dimension(207, 23));
        nopenyerahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nopenyerahanKeyPressed(evt);
            }
        });
        panelisi3.add(nopenyerahan);
        nopenyerahan.setBounds(98, 10, 212, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 95, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(98, 40, 90, 23);

        label16.setText("Petugas Cross :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(330, 10, 100, 23);

        label13.setText("Petugas P.J. :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(330, 40, 100, 23);

        kdptgcross.setName("kdptgcross"); // NOI18N
        kdptgcross.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptgcross.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgcrossKeyPressed(evt);
            }
        });
        panelisi3.add(kdptgcross);
        kdptgcross.setBounds(433, 10, 95, 23);

        kdptgpj.setName("kdptgpj"); // NOI18N
        kdptgpj.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptgpj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgpjKeyPressed(evt);
            }
        });
        panelisi3.add(kdptgpj);
        kdptgpj.setBounds(433, 40, 95, 23);

        nmptgcross.setEditable(false);
        nmptgcross.setName("nmptgcross"); // NOI18N
        nmptgcross.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptgcross);
        nmptgcross.setBounds(531, 10, 240, 23);

        nmptgpj.setEditable(false);
        nmptgpj.setName("nmptgpj"); // NOI18N
        nmptgpj.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptgpj);
        nmptgpj.setBounds(531, 40, 240, 23);

        btnPetugasCros.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasCros.setMnemonic('1');
        btnPetugasCros.setToolTipText("Alt+1");
        btnPetugasCros.setName("btnPetugasCros"); // NOI18N
        btnPetugasCros.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugasCros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasCrosActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugasCros);
        btnPetugasCros.setBounds(774, 10, 28, 23);

        btnPetugasPJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasPJ.setMnemonic('2');
        btnPetugasPJ.setToolTipText("Alt+2");
        btnPetugasPJ.setName("btnPetugasPJ"); // NOI18N
        btnPetugasPJ.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugasPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasPJActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugasPJ);
        btnPetugasPJ.setBounds(774, 40, 28, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(190, 40, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(220, 40, 90, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPetugasCrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasCrosActionPerformed
        pilih=1;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasCrosActionPerformed

    private void btnPetugasPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasPJActionPerformed
        pilih=2;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasPJActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,kdptgcross,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void kdptgcrossKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgcrossKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptgcross,kdptgcross.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasCrosActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,kdptgpj);
        }
    }//GEN-LAST:event_kdptgcrossKeyPressed

    private void nopenyerahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nopenyerahanKeyPressed
        Valid.pindah(evt, BtnKeluar, kdptgpj);
    }//GEN-LAST:event_nopenyerahanKeyPressed

    private void kdptgpjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgpjKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmptgpj,kdptgpj.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasPJActionPerformed(null);
        }else{
            Valid.pindah(evt,kdptgcross,kdkomponen);
        }
    }//GEN-LAST:event_kdptgpjKeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdptgpj);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void kdkomponenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdkomponenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from utd_komponen_darah where kode=?",nmkomponen,kdkomponen.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKomponenActionPerformed(null);
        }else{
            Valid.pindah(evt,kdptgpj,TCari);
        }
    }//GEN-LAST:event_kdkomponenKeyPressed

    private void btnKomponenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKomponenActionPerformed
        komponen.emptTeks();
        komponen.isCek();
        komponen.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
        komponen.setLocationRelativeTo(internalFrame1);        
        komponen.setVisible(true);
    }//GEN-LAST:event_btnKomponenActionPerformed

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
        aktifkan="";
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
        nopenyerahan.setText("");
        kdkomponen.setText("");
        nmkomponen.setText("");
        kdptgcross.setText("");
        nmptgcross.setText("");
        kdptgpj.setText("");
        nmptgpj.setText("");
        aktifkan="";
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penjualan"); 
            }
            Sequel.menyimpan("temporary","'0','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penjualan"); 
            Sequel.menyimpan("temporary","'0','Jml.Total :','','','','','','','','','','','','','','','"+LTotal.getText()+"','','','','','','','','','','','','','','','','','','','','',''","Transaksi Pembelian"); 
            
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptPenjualanDarah.jasper","report","::[ Transaksi Penjualan Barang ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,kdkomponen);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void ppCetakNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCetakNotaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
            Valid.textKosong(TCari,"No.Nota");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            kdkomponen.requestFocus();
        }else {
            Valid.panggilUrl("billing/NotaDarah2.php?nopenyerahan="+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim());          
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppCetakNotaActionPerformed

private void ppHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusActionPerformed
    if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        TCari.requestFocus();
    }else if(tbDokter.getSelectedRow()<= -1){
        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!!");
    }else{
        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
            Valid.textKosong(TCari,"No.Penyerahan");
        }else{
            if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("")){
                int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, Yakin mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    
                    try {
                        psdarah=koneksi.prepareStatement(
                            "select no_kantong from utd_penyerahan_darah_detail where no_penyerahan=? ");                    
                        try {
                            psdarah.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            rs2=psdarah.executeQuery();
                            while(rs2.next()){
                                Sequel.mengedit("utd_stok_darah","no_kantong=?","status='Ada'",1,new String[]{rs2.getString(1)});
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Darah : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(psdarah!=null){
                                psdarah.close();
                            }
                        } 
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("Sudah Dibayar")){
                            pscekmedis=koneksi.prepareStatement(sqlpscekmedis);
                            try {
                                pscekmedis.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                rs=pscekmedis.executeQuery();
                                while(rs.next()){
                                    Sequel.menyimpan("utd_stok_medis","'"+rs.getString("kode_brng")+"','"+rs.getString("jml")+"','"+rs.getDouble("harga")+"'",
                                        "stok=stok+"+rs.getString("jml")+"","kode_brng='"+rs.getString("kode_brng")+"'");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(pscekmedis!=null){
                                    pscekmedis.close();
                                }
                            }
                            psceknonmedis=koneksi.prepareStatement(sqlpsceknonmedis);
                            try {
                                psceknonmedis.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                rs2=psceknonmedis.executeQuery();
                                while(rs2.next()){
                                    Sequel.menyimpan("utd_stok_penunjang","'"+rs2.getString("kode_brng")+"','"+rs2.getString("jml")+"','"+rs2.getDouble("harga")+"'",
                                        "stok=stok+"+rs2.getString("jml")+"","kode_brng='"+rs2.getString("kode_brng")+"'");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(psceknonmedis!=null){
                                    psceknonmedis.close();
                                }
                            }
                            subtotalpendapatan=Sequel.cariIsiAngka("select sum(total) from utd_penyerahan_darah_detail where no_penyerahan=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            Sequel.queryu("delete from tampjurnal");                    
                            Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select Penyerahan_Darah from set_akun")+"','PENJUALAN DARAH UTD','"+subtotalpendapatan+"','0'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select kd_rek from utd_penyerahan_darah where no_penyerahan=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())+"','CARA BAYAR','0','"+subtotalpendapatan+"'","Rekening"); 
                            jur.simpanJurnal(nopenyerahan.getText(),tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),"U","PEMBATALAN PENJUALAN DARAH DI UTD"+", OLEH "+akses.getkode());                                                
                            Sequel.queryu("delete from tagihan_sadewa where no_nota='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim()+"'");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Hapus Penyerahan : "+e);
                    }  
                    Sequel.meghapus("utd_penyerahan_darah","no_penyerahan",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                    
                    tampil();
                }
            }
        }
    }
}//GEN-LAST:event_ppHapusActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void ppVerifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppVerifActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!!");
        }else{
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
                Valid.textKosong(TCari,"No.Penyerahan");
            }else{
                                             
                try {
                    if(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("Belum Dibayar")){                                
                        psdarah=koneksi.prepareStatement(
                            "select no_kantong from utd_penyerahan_darah_detail where no_penyerahan=? ");                    
                        try {
                            psdarah.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            rs2=psdarah.executeQuery();
                            while(rs2.next()){
                                Sequel.mengedit("utd_stok_darah","no_kantong=?","status='Diambil'",1,new String[]{rs2.getString(1)});
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Darah : "+e);
                        }finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(psdarah!=null){
                                psdarah.close();
                            }
                        }
                        pscekmedis=koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            pscekmedis.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            rs=pscekmedis.executeQuery();
                            while(rs.next()){
                                Sequel.menyimpan("utd_stok_medis","'"+rs.getString("kode_brng")+"','-"+rs.getString("jml")+"','"+rs.getDouble("harga")+"'",
                                    "stok=stok-"+rs.getString("jml")+"","kode_brng='"+rs.getString("kode_brng")+"'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(pscekmedis!=null){
                                pscekmedis.close();
                            }
                        }
                        psceknonmedis=koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            psceknonmedis.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            rs2=psceknonmedis.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("utd_stok_penunjang","'"+rs2.getString("kode_brng")+"','-"+rs2.getString("jml")+"','"+rs2.getDouble("harga")+"'",
                                    "stok=stok-"+rs2.getString("jml")+"","kode_brng='"+rs2.getString("kode_brng")+"'");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(psceknonmedis!=null){
                                psceknonmedis.close();
                            }
                        }
                        subtotalpendapatan=Sequel.cariIsiAngka("select sum(total) from utd_penyerahan_darah_detail where no_penyerahan=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        Sequel.queryu("delete from tampjurnal");                    
                        Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select Penyerahan_Darah from set_akun")+"','PENJUALAN DARAH UTD','0','"+subtotalpendapatan+"'","Rekening");    
                        Sequel.menyimpan("tampjurnal","'"+Sequel.cariIsi("select kd_rek from utd_penyerahan_darah where no_penyerahan=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())+"','CARA BAYAR','"+subtotalpendapatan+"','0'","Rekening"); 
                        jur.simpanJurnal(nopenyerahan.getText(),tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),"U","PENJUALAN DARAH DI UTD"+", OLEH "+akses.getkode());                                                                        
                        Sequel.menyimpan("tagihan_sadewa","'"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()+"','-','"+tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString()+"','-',concat('"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()+
                                "',' ',CURTIME()),'Pelunasan','"+subtotalpendapatan+"','"+subtotalpendapatan+"','Sudah','"+akses.getkode()+"'","No.Nota");
                        Sequel.mengedit("utd_penyerahan_darah","no_penyerahan=?","status='Sudah Dibayar'",1,new String[]{tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()});
                        JOptionPane.showMessageDialog(rootPane,"Proses verifikasi selesai ...!!");
                    }else if(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("Sudah Dibayar")){
                        JOptionPane.showMessageDialog(rootPane,"Maaf transaksi sudah diverifikasi..!!");
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi Hapus Penyerahan : "+e);
                } 
                
                tampil();
            }
        }
    }//GEN-LAST:event_ppVerifActionPerformed

    private void ppTampilkanBHPMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPMedisActionPerformed
        aktifkan="medis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPMedisActionPerformed

    private void ppTampilkanBHPPenunjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPPenunjangActionPerformed
        aktifkan="nonmedis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPPenunjangActionPerformed

    private void ppTampilkanBHPPenunjangDanMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanBHPPenunjangDanMedisActionPerformed
        aktifkan="medis&nonmedis";
        tampil();
    }//GEN-LAST:event_ppTampilkanBHPPenunjangDanMedisActionPerformed

    private void ppHapusBHPMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPMedisActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!!");
        }else{
            if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("")){
                int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, Yakin mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("Sudah Dibayar")){
                            pscekmedis=koneksi.prepareStatement(sqlpscekmedis);
                            try {
                                pscekmedis.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                rs=pscekmedis.executeQuery();
                                while(rs.next()){
                                    Sequel.menyimpan("utd_stok_medis","'"+rs.getString("kode_brng")+"','"+rs.getString("jml")+"','"+rs.getDouble("harga")+"'",
                                        "stok=stok+"+rs.getString("jml")+"","kode_brng='"+rs.getString("kode_brng")+"'");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(pscekmedis!=null){
                                    pscekmedis.close();
                                }
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_medis_penyerahan_darah","no_penyerahan",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        
                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPMedisActionPerformed

    private void ppHapusBHPNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPNonMedisActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!!");
        }else{
            if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("")){
                int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, Yakin mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("Sudah Dibayar")){
                            psceknonmedis=koneksi.prepareStatement(sqlpsceknonmedis);
                            try {
                                psceknonmedis.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                rs2=psceknonmedis.executeQuery();
                                while(rs2.next()){
                                    Sequel.menyimpan("utd_stok_penunjang","'"+rs2.getString("kode_brng")+"','"+rs2.getString("jml")+"','"+rs2.getDouble("harga")+"'",
                                        "stok=stok+"+rs2.getString("jml")+"","kode_brng='"+rs2.getString("kode_brng")+"'");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(psceknonmedis!=null){
                                    psceknonmedis.close();
                                }
                            }
                        }
                        Sequel.meghapus("utd_penggunaan_penunjang_penyerahan_darah","no_penyerahan",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        
                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPNonMedisActionPerformed

    private void ppHapusBHPMedisDanNonMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppHapusBHPMedisDanNonMedisActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data yang mau dihapus..!!");
        }else{
            if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("")){
                int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, Yakin mau dihapus..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    try {
                        
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),5).toString().equals("Sudah Dibayar")){
                            pscekmedis=koneksi.prepareStatement(sqlpscekmedis);
                            try {
                                pscekmedis.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                rs=pscekmedis.executeQuery();
                                while(rs.next()){
                                    Sequel.menyimpan("utd_stok_medis","'"+rs.getString("kode_brng")+"','"+rs.getString("jml")+"','"+rs.getDouble("harga")+"'",
                                        "stok=stok+"+rs.getString("jml")+"","kode_brng='"+rs.getString("kode_brng")+"'");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs!=null){
                                    rs.close();
                                }
                                if(pscekmedis!=null){
                                    pscekmedis.close();
                                }
                            }
                            psceknonmedis=koneksi.prepareStatement(sqlpsceknonmedis);
                            try {
                                psceknonmedis.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                rs2=psceknonmedis.executeQuery();
                                while(rs2.next()){
                                    Sequel.menyimpan("utd_stok_penunjang","'"+rs2.getString("kode_brng")+"','"+rs2.getString("jml")+"','"+rs2.getDouble("harga")+"'",
                                        "stok=stok+"+rs2.getString("jml")+"","kode_brng='"+rs2.getString("kode_brng")+"'");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(psceknonmedis!=null){
                                    psceknonmedis.close();
                                }
                            }
                        }                            
                        
                        Sequel.meghapus("utd_penggunaan_medis_penyerahan_darah","no_penyerahan",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());                        
                        Sequel.meghapus("utd_penggunaan_penunjang_penyerahan_darah","no_penyerahan",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        
                        tampil();
                        emptTeks();
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf,Pilih pada nomor donor...!!!!");
            }
        }
    }//GEN-LAST:event_ppHapusBHPMedisDanNonMedisActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            UTDCariPenyerahanDarah dialog = new UTDCariPenyerahanDarah(new javax.swing.JFrame(), true);
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
    private widget.Label LTotal;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnKomponen;
    private widget.Button btnPetugasCros;
    private widget.Button btnPetugasPJ;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdkomponen;
    private widget.TextBox kdptgcross;
    private widget.TextBox kdptgpj;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmkomponen;
    private widget.TextBox nmptgcross;
    private widget.TextBox nmptgpj;
    private widget.TextBox nopenyerahan;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppCetakNota;
    private javax.swing.JMenuItem ppHapus;
    private javax.swing.JMenuItem ppHapusBHPMedis;
    private javax.swing.JMenuItem ppHapusBHPMedisDanNonMedis;
    private javax.swing.JMenuItem ppHapusBHPNonMedis;
    private javax.swing.JMenuItem ppTampilkanBHPMedis;
    private javax.swing.JMenuItem ppTampilkanBHPPenunjang;
    private javax.swing.JMenuItem ppTampilkanBHPPenunjangDanMedis;
    private javax.swing.JMenuItem ppVerif;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {        
        Valid.tabelKosong(tabMode);
        try {
            pendapatan=0;
            pspenyerahan=koneksi.prepareStatement(
                    "select utd_penyerahan_darah.no_penyerahan,utd_penyerahan_darah.tanggal,"+
                    "utd_penyerahan_darah.dinas,utd_penyerahan_darah.nip_cross,"+
                    "utd_penyerahan_darah.keterangan,utd_penyerahan_darah.status,"+
                    "utd_penyerahan_darah.pengambil_darah,utd_penyerahan_darah.alamat_pengambil_darah,"+
                    "utd_penyerahan_darah.nip_pj from utd_penyerahan_darah where "+
                    "utd_penyerahan_darah.tanggal between ? and ? and utd_penyerahan_darah.no_penyerahan like ? and utd_penyerahan_darah.nip_cross like ? and utd_penyerahan_darah.nip_pj like ? and utd_penyerahan_darah.no_penyerahan like ? or "+
                    "utd_penyerahan_darah.tanggal between ? and ? and utd_penyerahan_darah.no_penyerahan like ? and utd_penyerahan_darah.nip_cross like ? and utd_penyerahan_darah.nip_pj like ? and utd_penyerahan_darah.dinas like ? or "+
                    "utd_penyerahan_darah.tanggal between ? and ? and utd_penyerahan_darah.no_penyerahan like ? and utd_penyerahan_darah.nip_cross like ? and utd_penyerahan_darah.nip_pj like ? and utd_penyerahan_darah.keterangan like ? or "+
                    "utd_penyerahan_darah.tanggal between ? and ? and utd_penyerahan_darah.no_penyerahan like ? and utd_penyerahan_darah.nip_cross like ? and utd_penyerahan_darah.nip_pj like ? and utd_penyerahan_darah.status like ? or "+
                    "utd_penyerahan_darah.tanggal between ? and ? and utd_penyerahan_darah.no_penyerahan like ? and utd_penyerahan_darah.nip_cross like ? and utd_penyerahan_darah.nip_pj like ? and utd_penyerahan_darah.pengambil_darah like ? or "+
                    "utd_penyerahan_darah.tanggal between ? and ? and utd_penyerahan_darah.no_penyerahan like ? and utd_penyerahan_darah.nip_cross like ? and utd_penyerahan_darah.nip_pj like ? and utd_penyerahan_darah.alamat_pengambil_darah like ? order by utd_penyerahan_darah.tanggal");
            try {
                pspenyerahan.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                pspenyerahan.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                pspenyerahan.setString(3,"%"+nopenyerahan.getText().trim()+"%");
                pspenyerahan.setString(4,"%"+kdptgcross.getText().trim()+"%");
                pspenyerahan.setString(5,"%"+kdptgpj.getText().trim()+"%");
                pspenyerahan.setString(6,"%"+TCari.getText().trim()+"%");
                pspenyerahan.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                pspenyerahan.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                pspenyerahan.setString(9,"%"+nopenyerahan.getText().trim()+"%");
                pspenyerahan.setString(10,"%"+kdptgcross.getText().trim()+"%");
                pspenyerahan.setString(11,"%"+kdptgpj.getText().trim()+"%");
                pspenyerahan.setString(12,"%"+TCari.getText().trim()+"%");
                pspenyerahan.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                pspenyerahan.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                pspenyerahan.setString(15,"%"+nopenyerahan.getText().trim()+"%");
                pspenyerahan.setString(16,"%"+kdptgcross.getText().trim()+"%");
                pspenyerahan.setString(17,"%"+kdptgpj.getText().trim()+"%");
                pspenyerahan.setString(18,"%"+TCari.getText().trim()+"%");
                pspenyerahan.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                pspenyerahan.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                pspenyerahan.setString(21,"%"+nopenyerahan.getText().trim()+"%");
                pspenyerahan.setString(22,"%"+kdptgcross.getText().trim()+"%");
                pspenyerahan.setString(23,"%"+kdptgpj.getText().trim()+"%");
                pspenyerahan.setString(24,"%"+TCari.getText().trim()+"%");
                pspenyerahan.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                pspenyerahan.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                pspenyerahan.setString(27,"%"+nopenyerahan.getText().trim()+"%");
                pspenyerahan.setString(28,"%"+kdptgcross.getText().trim()+"%");
                pspenyerahan.setString(29,"%"+kdptgpj.getText().trim()+"%");
                pspenyerahan.setString(30,"%"+TCari.getText().trim()+"%");
                pspenyerahan.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                pspenyerahan.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                pspenyerahan.setString(33,"%"+nopenyerahan.getText().trim()+"%");
                pspenyerahan.setString(34,"%"+kdptgcross.getText().trim()+"%");
                pspenyerahan.setString(35,"%"+kdptgpj.getText().trim()+"%");
                pspenyerahan.setString(36,"%"+TCari.getText().trim()+"%");
                rs=pspenyerahan.executeQuery();
                while(rs.next()){
                    //data penyerahan
                    tabMode.addRow(new Object[]{
                        rs.getString("no_penyerahan"),rs.getString("tanggal"),rs.getString("dinas"),
                        Sequel.cariIsi("select nama from petugas where nip=?",rs.getString("nip_cross")),
                        rs.getString("keterangan"),rs.getString("status"),rs.getString("pengambil_darah"),
                        rs.getString("alamat_pengambil_darah"),
                        Sequel.cariIsi("select nama from petugas where nip=?",rs.getString("nip_pj"))
                    });
                    //data darah
                    tabMode.addRow(new Object[]{
                        "","","No.","No.Kantung","Komponen","G.D. & Rhesus","Asal Darah","Aftap & Kadaluarsa","Biaya"
                    });
                    psdarah=koneksi.prepareStatement(
                        "select utd_stok_darah.no_kantong,utd_komponen_darah.nama as darah,"+
                        "utd_stok_darah.golongan_darah,utd_stok_darah.resus,"+
                        "utd_stok_darah.tanggal_aftap,utd_stok_darah.tanggal_kadaluarsa,"+
                        "utd_stok_darah.asal_darah,utd_komponen_darah.total "+
                        "from utd_komponen_darah inner join utd_stok_darah inner join utd_penyerahan_darah_detail "+
                        "on utd_stok_darah.kode_komponen=utd_komponen_darah.kode "+
                        "and utd_penyerahan_darah_detail.no_kantong=utd_stok_darah.no_kantong where "+
                        "utd_komponen_darah.nama like ? and utd_penyerahan_darah_detail.no_penyerahan=? order by utd_komponen_darah.nama");                    
                    try {
                        subtotalpendapatan=0;
                        no=1;
                        psdarah.setString(1,"%"+nmkomponen.getText().trim()+"%");
                        psdarah.setString(2,rs.getString("no_penyerahan"));
                        rs2=psdarah.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new Object[]{
                                "","",no+".",rs2.getString("no_kantong"),rs2.getString("darah"),
                                rs2.getString("golongan_darah")+" "+rs2.getString("resus"),
                                rs2.getString("asal_darah"),rs2.getString("tanggal_aftap")+" | "+rs2.getString("tanggal_kadaluarsa"),
                                Valid.SetAngka(rs2.getDouble("total"))
                            });
                            subtotalpendapatan=subtotalpendapatan+rs2.getDouble("total");
                            pendapatan=pendapatan+rs2.getDouble("total");
                            no++;
                        }
                        if(subtotalpendapatan>0){
                            tabMode.addRow(new Object[]{
                                "","","","","Total Biaya",":","","",Valid.SetAngka(subtotalpendapatan)
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Darah : "+e);
                    }finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(psdarah!=null){
                            psdarah.close();
                        }
                    }  
                    //data penggunaan BHP
                    if(aktifkan.equals("medis")){
                        pscekmedis=koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            subtotalmedis=0;
                            pscekmedis.setString(1,rs.getString("no_penyerahan"));
                            rs2=pscekmedis.executeQuery();
                            if(rs2.next()){
                                no=1;
                                tabMode.addRow(new String[]{
                                    "","","No","Kode Barang","Penggunaan BHP Medis","Jumlah","Satuan","Harga","Total"
                                });
                            }
                            rs2.beforeFirst();
                            while(rs2.next()){
                                tabMode.addRow(new String[]{
                                    "","",no+". ",rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                    rs2.getString("jml"),rs2.getString("kode_sat"),Valid.SetAngka(rs2.getDouble("harga")),
                                    Valid.SetAngka(rs2.getDouble("total"))
                                });
                                subtotalmedis=subtotalmedis+rs2.getDouble("total");
                                no++;
                            }
                            if(subtotalmedis>0){
                                tabMode.addRow(new Object[]{
                                    "","","","","Total BHP Medis",":","","",Valid.SetAngka(subtotalmedis)
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi BHP Medis : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(pscekmedis!=null){
                                pscekmedis.close();
                            }
                        }
                    }else if(aktifkan.equals("nonmedis")){
                        psceknonmedis=koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            subtotalnonmedis=0;
                            psceknonmedis.setString(1,rs.getString("no_penyerahan"));
                            rs2=psceknonmedis.executeQuery();
                            if(rs2.next()){
                                no=1;                                
                                tabMode.addRow(new String[]{
                                    "","","No","Kode Barang","Penggunaan BHP Non Medis","Jumlah","Satuan","Harga","Total"
                                });
                            }
                            rs2.beforeFirst();
                            while(rs2.next()){
                                tabMode.addRow(new String[]{
                                    "","",no+". ",rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                    rs2.getString("jml"),rs2.getString("kode_sat"),Valid.SetAngka(rs2.getDouble("harga")),
                                    Valid.SetAngka(rs2.getDouble("total"))
                                });
                                subtotalnonmedis=subtotalnonmedis+rs2.getDouble("total");
                                no++;
                            }
                            if(subtotalnonmedis>0){
                                tabMode.addRow(new Object[]{
                                    "","","","","Total BHP Non Medis",":","","",Valid.SetAngka(subtotalnonmedis)
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(psceknonmedis!=null){
                                psceknonmedis.close();
                            }
                        }
                    }else if(aktifkan.equals("medis&nonmedis")){
                        pscekmedis=koneksi.prepareStatement(sqlpscekmedis);
                        try {
                            subtotalmedis=0;
                            pscekmedis.setString(1,rs.getString("no_penyerahan"));
                            rs2=pscekmedis.executeQuery();
                            if(rs2.next()){
                                no=1;                                
                                tabMode.addRow(new String[]{
                                    "","","No","Kode Barang","Penggunaan BHP Medis","Jumlah","Satuan","Harga","Total"
                                });
                            }
                            rs2.beforeFirst();
                            while(rs2.next()){
                                tabMode.addRow(new String[]{
                                    "","",no+". ",rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                    rs2.getString("jml"),rs2.getString("kode_sat"),Valid.SetAngka(rs2.getDouble("harga")),
                                    Valid.SetAngka(rs2.getDouble("total"))
                                });
                                subtotalmedis=subtotalmedis+rs2.getDouble("total");
                                no++;
                            }
                            if(subtotalmedis>0){
                                tabMode.addRow(new Object[]{
                                    "","","","","Total BHP Medis",":","","",Valid.SetAngka(subtotalmedis)
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(pscekmedis!=null){
                                pscekmedis.close();
                            }
                        }
                        
                        psceknonmedis=koneksi.prepareStatement(sqlpsceknonmedis);
                        try {
                            subtotalnonmedis=0;
                            psceknonmedis.setString(1,rs.getString("no_penyerahan"));
                            rs2=psceknonmedis.executeQuery();
                            if(rs2.next()){
                                no=1;                                
                                tabMode.addRow(new String[]{
                                    "","","No","Kode Barang","Penggunaan BHP Non Medis","Jumlah","Satuan","Harga","Total"
                                });
                            }
                            rs2.beforeFirst();
                            while(rs2.next()){
                                tabMode.addRow(new String[]{
                                    "","",no+". ",rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                    rs2.getString("jml"),rs2.getString("kode_sat"),Valid.SetAngka(rs2.getDouble("harga")),
                                    Valid.SetAngka(rs2.getDouble("total"))
                                });
                                subtotalnonmedis=subtotalnonmedis+rs2.getDouble("total");
                                no++;
                            }
                            if(subtotalnonmedis>0){
                                tabMode.addRow(new Object[]{
                                    "","","","","Total BHP Non Medis",":","","",Valid.SetAngka(subtotalnonmedis)
                                });
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(psceknonmedis!=null){
                                psceknonmedis.close();
                            }
                        }                        
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi Penyerahan : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pspenyerahan!=null){
                    pspenyerahan.close();
                }
            }      
            LTotal.setText(Valid.SetAngka(pendapatan));
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }

    public void emptTeks() {
        kdkomponen.setText("");
        nmkomponen.setText("");
        kdkomponen.requestFocus();        
    }   
     
    public void isCek(){
        BtnPrint.setEnabled(akses.getpenjualan_obat());
        ppCetakNota.setEnabled(akses.getpenjualan_obat());
        if(akses.getkode().equals("Admin Utama")){
            ppHapus.setEnabled(true);
        }else{
            ppHapus.setEnabled(false);
        }  
    }
}

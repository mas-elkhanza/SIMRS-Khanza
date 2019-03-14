/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgAdmin.java
 *
 * Created on 21 Jun 10, 20:53:44
 */

package setting;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public class DlgSetRM extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2,tabMode3,tabMode4,tabMode5,tabMode6,tabMode7;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,ps3,ps4,ps5,ps6;
    private ResultSet rs;

    /** Creates new form DlgAdmin
     * @param parent
     * @param modal */
    public DlgSetRM(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{"No.Rekam Medis Terakhir Digunakan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbNoRM.setModel(tabMode);
        tbNoRM.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbNoRM.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 1; i++) {
            TableColumn column = tbNoRM.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(350);
            }
        }

        tbNoRM.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new Object[]{"Model Pengurutan No.R.M","Gunakan Tahun","Gunakan Bulan","Tahun & Bulan"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbNoUrut.setModel(tabMode2);
        tbNoUrut.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbNoUrut.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 4; i++) {
            TableColumn column = tbNoUrut.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(200);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(100);
            }
        }

        tbNoUrut.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode3=new DefaultTableModel(null,new Object[]{"Kelurahan","Kecamatan","Kabupaten","Propinsi"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbAlamat.setModel(tabMode3);
        tbAlamat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbAlamat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 4; i++) {
            TableColumn column = tbAlamat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(150);
            }else if(i==1){
                column.setPreferredWidth(150);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(150);
            }
        }

        tbAlamat.setDefaultRenderer(Object.class, new WarnaTable());

        tabMode4=new DefaultTableModel(null,new Object[]{"Parameter","Nilai"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbKelengkapan.setModel(tabMode4);
        tbKelengkapan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKelengkapan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 2; i++) {
            TableColumn column = tbKelengkapan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(420);
            }else{
                column.setPreferredWidth(50);
            }
        }

        tbKelengkapan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode5=new DefaultTableModel(null,new Object[]{"Wajib closing kasir terlebih dahulu untuk perawatan sebelumnya"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbValidasiRegistrasi.setModel(tabMode5);
        tbValidasiRegistrasi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbValidasiRegistrasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 1; i++) {
            TableColumn column = tbValidasiRegistrasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(350);
            }
        }

        tbValidasiRegistrasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode6=new DefaultTableModel(null,new Object[]{"Tampilkan catatan pasien saat Registrasi Poli/Unit/IGD"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbValidasiCatatan.setModel(tabMode6);
        tbValidasiCatatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbValidasiCatatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 1; i++) {
            TableColumn column = tbValidasiCatatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(350);
            }
        }

        tbValidasiCatatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode7=new DefaultTableModel(null,new Object[]{"Tampilkan pilihan data pasien untuk RS TNI dan POLRI"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbTniPolri.setModel(tabMode7);
        tbTniPolri.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTniPolri.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 1; i++) {
            TableColumn column = tbTniPolri.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(350);
            }
        }

        tbTniPolri.setDefaultRenderer(Object.class, new WarnaTable());
        
        norm.setDocument(new batasInput((byte)10).getKata(norm));
        PanjangKTP.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangKTP));
        PanjangTmpLahir.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangTmpLahir));
        PanjangNamaIbu.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangNamaIbu));
        PanjangAlamat.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangAlamat));
        PanjangPekerjaan.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangPekerjaan));
        PanjangTelp.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangTelp));
        PanjangUmur.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangUmur));
        PanjangNamaKeluarga.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangNamaKeluarga));
        PanjangNoPeserta.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangNoPeserta));
        PanjangKelurahan.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangKelurahan));
        PanjangKecamatan.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangKecamatan));
        PanjangKabupaten.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangKabupaten));
        PanjangPekerjaanPJ.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangPekerjaanPJ));
        PanjangAlamatPJ.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangAlamatPJ));
        PanjangKelurahanPJ.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangKelurahanPJ));
        PanjangKecamatanPJ.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangKecamatanPJ));
        PanjangKabupatenPJ.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangKabupatenPJ));
        PanjangPropinsi.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangPropinsi));
        PanjangPropinsiPJ.setDocument(new batasInput((byte)3).getOnlyAngka(PanjangPropinsiPJ));
        ChkInput.setSelected(false);
        isForm();
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
        panelGlass5 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnHapus = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        panelGlass7 = new widget.panelisi();
        jLabel4 = new widget.Label();
        norm = new widget.TextBox();
        Scroll = new widget.ScrollPane();
        tbNoRM = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        tbNoUrut = new widget.Table();
        panelGlass8 = new widget.panelisi();
        jLabel5 = new widget.Label();
        cmburut = new widget.ComboBox();
        cmbYesTahun = new widget.ComboBox();
        jLabel6 = new widget.Label();
        jLabel7 = new widget.Label();
        cmbYesBulan = new widget.ComboBox();
        jLabel8 = new widget.Label();
        cmbPosisi = new widget.ComboBox();
        internalFrame4 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbAlamat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel9 = new widget.Label();
        Kelurahan = new widget.ComboBox();
        Kecamatan = new widget.ComboBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        Kabupaten = new widget.ComboBox();
        jLabel32 = new widget.Label();
        Propinsi = new widget.ComboBox();
        internalFrame5 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        tbKelengkapan = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        jLabel15 = new widget.Label();
        PanjangKTP = new widget.TextBox();
        jLabel33 = new widget.Label();
        YesNoKTP = new widget.ComboBox();
        PanjangTmpLahir = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel34 = new widget.Label();
        YesNoTmpLahir = new widget.ComboBox();
        PanjangNamaIbu = new widget.TextBox();
        YesNoNamaIbu = new widget.ComboBox();
        jLabel35 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        YesNoAlamat = new widget.ComboBox();
        jLabel36 = new widget.Label();
        PanjangAlamat = new widget.TextBox();
        jLabel19 = new widget.Label();
        jLabel37 = new widget.Label();
        YesNoPekerjaan = new widget.ComboBox();
        PanjangPekerjaan = new widget.TextBox();
        jLabel38 = new widget.Label();
        YesNoTelp = new widget.ComboBox();
        PanjangTelp = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel39 = new widget.Label();
        YesNoUmur = new widget.ComboBox();
        PanjangUmur = new widget.TextBox();
        jLabel21 = new widget.Label();
        jLabel40 = new widget.Label();
        YesNoNamaKeluarga = new widget.ComboBox();
        PanjangNamaKeluarga = new widget.TextBox();
        jLabel22 = new widget.Label();
        jLabel41 = new widget.Label();
        YesNoNoPeserta = new widget.ComboBox();
        PanjangNoPeserta = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel42 = new widget.Label();
        YesNoKelurahan = new widget.ComboBox();
        PanjangKelurahan = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel44 = new widget.Label();
        jLabel26 = new widget.Label();
        jLabel45 = new widget.Label();
        jLabel27 = new widget.Label();
        jLabel46 = new widget.Label();
        jLabel28 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel29 = new widget.Label();
        YesNoKecamatan = new widget.ComboBox();
        PanjangKecamatan = new widget.TextBox();
        YesNoKabupaten = new widget.ComboBox();
        PanjangKabupaten = new widget.TextBox();
        YesNoPekerjaanPJ = new widget.ComboBox();
        PanjangPekerjaanPJ = new widget.TextBox();
        YesNoAlamatPJ = new widget.ComboBox();
        PanjangAlamatPJ = new widget.TextBox();
        YesNoKelurahanPJ = new widget.ComboBox();
        PanjangKelurahanPJ = new widget.TextBox();
        jLabel48 = new widget.Label();
        YesNoKecamatanPJ = new widget.ComboBox();
        PanjangKecamatanPJ = new widget.TextBox();
        jLabel30 = new widget.Label();
        jLabel49 = new widget.Label();
        YesNoKabupatenPJ = new widget.ComboBox();
        jLabel31 = new widget.Label();
        PanjangKabupatenPJ = new widget.TextBox();
        jLabel50 = new widget.Label();
        YesNoPropinsi = new widget.ComboBox();
        PanjangPropinsi = new widget.TextBox();
        jLabel51 = new widget.Label();
        YesNoPropinsiPJ = new widget.ComboBox();
        PanjangPropinsiPJ = new widget.TextBox();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        internalFrame6 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        tbValidasiRegistrasi = new widget.Table();
        panelGlass10 = new widget.panelisi();
        jLabel12 = new widget.Label();
        ValidasiRegistrasi = new widget.ComboBox();
        internalFrame7 = new widget.InternalFrame();
        Scroll6 = new widget.ScrollPane();
        tbValidasiCatatan = new widget.Table();
        panelGlass11 = new widget.panelisi();
        jLabel13 = new widget.Label();
        ValidasiCatatan = new widget.ComboBox();
        internalFrame8 = new widget.InternalFrame();
        Scroll7 = new widget.ScrollPane();
        tbTniPolri = new widget.Table();
        panelGlass12 = new widget.panelisi();
        jLabel14 = new widget.Label();
        TampilkanTNI = new widget.ComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Setup Rekam Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass5.add(BtnSimpan);

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
        panelGlass5.add(BtnHapus);

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
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
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

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 47));
        panelGlass7.setLayout(null);

        jLabel4.setText("No.RM Terakhir Digunakan :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass7.add(jLabel4);
        jLabel4.setBounds(0, 12, 150, 23);

        norm.setHighlighter(null);
        norm.setName("norm"); // NOI18N
        norm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                normKeyPressed(evt);
            }
        });
        panelGlass7.add(norm);
        norm.setBounds(153, 12, 150, 23);

        internalFrame2.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbNoRM.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbNoRM.setName("tbNoRM"); // NOI18N
        tbNoRM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNoRMMouseClicked(evt);
            }
        });
        tbNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbNoRMKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbNoRM);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("No.R.M. Terakhir", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbNoUrut.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbNoUrut.setName("tbNoUrut"); // NOI18N
        tbNoUrut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbNoUrutMouseClicked(evt);
            }
        });
        tbNoUrut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbNoUrutKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbNoUrut);

        internalFrame3.add(Scroll2, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 47));
        panelGlass8.setLayout(null);

        jLabel5.setText("Pengurutan No.R.M. :");
        jLabel5.setName("jLabel5"); // NOI18N
        panelGlass8.add(jLabel5);
        jLabel5.setBounds(0, 12, 120, 23);

        cmburut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Straight", "Middle", "Terminal" }));
        cmburut.setName("cmburut"); // NOI18N
        cmburut.setPreferredSize(new java.awt.Dimension(55, 28));
        cmburut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmburutKeyPressed(evt);
            }
        });
        panelGlass8.add(cmburut);
        cmburut.setBounds(123, 12, 100, 23);

        cmbYesTahun.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        cmbYesTahun.setName("cmbYesTahun"); // NOI18N
        cmbYesTahun.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbYesTahun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbYesTahunKeyPressed(evt);
            }
        });
        panelGlass8.add(cmbYesTahun);
        cmbYesTahun.setBounds(323, 12, 70, 23);

        jLabel6.setText("Gunakan Tahun :");
        jLabel6.setName("jLabel6"); // NOI18N
        panelGlass8.add(jLabel6);
        jLabel6.setBounds(230, 12, 90, 23);

        jLabel7.setText("Gunakan Bulan :");
        jLabel7.setName("jLabel7"); // NOI18N
        panelGlass8.add(jLabel7);
        jLabel7.setBounds(395, 12, 90, 23);

        cmbYesBulan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        cmbYesBulan.setName("cmbYesBulan"); // NOI18N
        cmbYesBulan.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbYesBulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbYesBulanKeyPressed(evt);
            }
        });
        panelGlass8.add(cmbYesBulan);
        cmbYesBulan.setBounds(488, 12, 70, 23);

        jLabel8.setText("Tahun & Bulan :");
        jLabel8.setName("jLabel8"); // NOI18N
        panelGlass8.add(jLabel8);
        jLabel8.setBounds(560, 12, 87, 23);

        cmbPosisi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Depan", "Belakang" }));
        cmbPosisi.setName("cmbPosisi"); // NOI18N
        cmbPosisi.setPreferredSize(new java.awt.Dimension(55, 28));
        cmbPosisi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPosisiKeyPressed(evt);
            }
        });
        panelGlass8.add(cmbPosisi);
        cmbPosisi.setBounds(650, 12, 100, 23);

        internalFrame3.add(panelGlass8, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pengurutan No.R.M", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame4.setBorder(null);
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbAlamat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbAlamat.setName("tbAlamat"); // NOI18N
        tbAlamat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAlamatMouseClicked(evt);
            }
        });
        tbAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbAlamatKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbAlamat);

        internalFrame4.add(Scroll3, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 47));
        panelGlass9.setLayout(null);

        jLabel9.setText("Aktifkan Kelurahan :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelGlass9.add(jLabel9);
        jLabel9.setBounds(0, 12, 110, 23);

        Kelurahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        Kelurahan.setName("Kelurahan"); // NOI18N
        Kelurahan.setPreferredSize(new java.awt.Dimension(55, 28));
        Kelurahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelurahanKeyPressed(evt);
            }
        });
        panelGlass9.add(Kelurahan);
        Kelurahan.setBounds(113, 12, 70, 23);

        Kecamatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        Kecamatan.setName("Kecamatan"); // NOI18N
        Kecamatan.setPreferredSize(new java.awt.Dimension(55, 28));
        Kecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KecamatanKeyPressed(evt);
            }
        });
        panelGlass9.add(Kecamatan);
        Kecamatan.setBounds(303, 12, 70, 23);

        jLabel10.setText("Aktifkan Kecamatan :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelGlass9.add(jLabel10);
        jLabel10.setBounds(180, 12, 120, 23);

        jLabel11.setText("Aktifkan Kabupaten :");
        jLabel11.setName("jLabel11"); // NOI18N
        panelGlass9.add(jLabel11);
        jLabel11.setBounds(365, 12, 120, 23);

        Kabupaten.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        Kabupaten.setName("Kabupaten"); // NOI18N
        Kabupaten.setPreferredSize(new java.awt.Dimension(55, 28));
        Kabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KabupatenKeyPressed(evt);
            }
        });
        panelGlass9.add(Kabupaten);
        Kabupaten.setBounds(488, 12, 70, 23);

        jLabel32.setText("Aktifkan Propinsi :");
        jLabel32.setName("jLabel32"); // NOI18N
        panelGlass9.add(jLabel32);
        jLabel32.setBounds(550, 12, 107, 23);

        Propinsi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        Propinsi.setName("Propinsi"); // NOI18N
        Propinsi.setPreferredSize(new java.awt.Dimension(55, 28));
        Propinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PropinsiKeyPressed(evt);
            }
        });
        panelGlass9.add(Propinsi);
        Propinsi.setBounds(660, 12, 70, 23);

        internalFrame4.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pengaturan Alamat", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);

        tbKelengkapan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbKelengkapan.setName("tbKelengkapan"); // NOI18N
        tbKelengkapan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKelengkapanMouseClicked(evt);
            }
        });
        tbKelengkapan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbKelengkapanKeyPressed(evt);
            }
        });
        Scroll4.setViewportView(tbKelengkapan);

        internalFrame5.add(Scroll4, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(44, 318));
        FormInput.setLayout(null);

        jLabel15.setText("Minimal No.KTP/SIM :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 40, 120, 23);

        PanjangKTP.setText("0");
        PanjangKTP.setHighlighter(null);
        PanjangKTP.setName("PanjangKTP"); // NOI18N
        PanjangKTP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangKTPKeyPressed(evt);
            }
        });
        FormInput.add(PanjangKTP);
        PanjangKTP.setBounds(123, 40, 70, 23);

        jLabel33.setText("No.KTP/SIM :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(0, 10, 120, 23);

        YesNoKTP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoKTP.setName("YesNoKTP"); // NOI18N
        YesNoKTP.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoKTP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoKTPKeyPressed(evt);
            }
        });
        FormInput.add(YesNoKTP);
        YesNoKTP.setBounds(123, 10, 70, 23);

        PanjangTmpLahir.setText("0");
        PanjangTmpLahir.setHighlighter(null);
        PanjangTmpLahir.setName("PanjangTmpLahir"); // NOI18N
        PanjangTmpLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangTmpLahirKeyPressed(evt);
            }
        });
        FormInput.add(PanjangTmpLahir);
        PanjangTmpLahir.setBounds(123, 100, 70, 23);

        jLabel16.setText("Minimal Tmp.Lahir :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 100, 120, 23);

        jLabel34.setText("Tmp.Lahir :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(0, 70, 120, 23);

        YesNoTmpLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoTmpLahir.setName("YesNoTmpLahir"); // NOI18N
        YesNoTmpLahir.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoTmpLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoTmpLahirKeyPressed(evt);
            }
        });
        FormInput.add(YesNoTmpLahir);
        YesNoTmpLahir.setBounds(123, 70, 70, 23);

        PanjangNamaIbu.setText("0");
        PanjangNamaIbu.setHighlighter(null);
        PanjangNamaIbu.setName("PanjangNamaIbu"); // NOI18N
        PanjangNamaIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangNamaIbuKeyPressed(evt);
            }
        });
        FormInput.add(PanjangNamaIbu);
        PanjangNamaIbu.setBounds(123, 160, 70, 23);

        YesNoNamaIbu.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoNamaIbu.setName("YesNoNamaIbu"); // NOI18N
        YesNoNamaIbu.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoNamaIbu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoNamaIbuKeyPressed(evt);
            }
        });
        FormInput.add(YesNoNamaIbu);
        YesNoNamaIbu.setBounds(123, 130, 70, 23);

        jLabel35.setText("Nama Ibu :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(0, 130, 120, 23);

        jLabel17.setText("Minimal Nama Ibu :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 160, 120, 23);

        jLabel18.setText("Minimal Alamat :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 220, 120, 23);

        YesNoAlamat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoAlamat.setName("YesNoAlamat"); // NOI18N
        YesNoAlamat.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoAlamatKeyPressed(evt);
            }
        });
        FormInput.add(YesNoAlamat);
        YesNoAlamat.setBounds(123, 190, 70, 23);

        jLabel36.setText("Alamat :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 190, 120, 23);

        PanjangAlamat.setText("0");
        PanjangAlamat.setHighlighter(null);
        PanjangAlamat.setName("PanjangAlamat"); // NOI18N
        PanjangAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangAlamatKeyPressed(evt);
            }
        });
        FormInput.add(PanjangAlamat);
        PanjangAlamat.setBounds(123, 220, 70, 23);

        jLabel19.setText("Minimal Pekerjaan :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(0, 280, 120, 23);

        jLabel37.setText("Pekerjaan :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 250, 120, 23);

        YesNoPekerjaan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoPekerjaan.setName("YesNoPekerjaan"); // NOI18N
        YesNoPekerjaan.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoPekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoPekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(YesNoPekerjaan);
        YesNoPekerjaan.setBounds(123, 250, 70, 23);

        PanjangPekerjaan.setText("0");
        PanjangPekerjaan.setHighlighter(null);
        PanjangPekerjaan.setName("PanjangPekerjaan"); // NOI18N
        PanjangPekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangPekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(PanjangPekerjaan);
        PanjangPekerjaan.setBounds(123, 280, 70, 23);

        jLabel38.setText("No.Telp :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(210, 10, 130, 23);

        YesNoTelp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoTelp.setName("YesNoTelp"); // NOI18N
        YesNoTelp.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoTelpKeyPressed(evt);
            }
        });
        FormInput.add(YesNoTelp);
        YesNoTelp.setBounds(343, 10, 70, 23);

        PanjangTelp.setText("0");
        PanjangTelp.setHighlighter(null);
        PanjangTelp.setName("PanjangTelp"); // NOI18N
        PanjangTelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangTelpKeyPressed(evt);
            }
        });
        FormInput.add(PanjangTelp);
        PanjangTelp.setBounds(343, 40, 70, 23);

        jLabel20.setText("Minimal No.Telp :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(210, 40, 130, 23);

        jLabel39.setText("Umur :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(210, 70, 130, 23);

        YesNoUmur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoUmur.setName("YesNoUmur"); // NOI18N
        YesNoUmur.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoUmur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoUmurKeyPressed(evt);
            }
        });
        FormInput.add(YesNoUmur);
        YesNoUmur.setBounds(343, 70, 70, 23);

        PanjangUmur.setText("0");
        PanjangUmur.setHighlighter(null);
        PanjangUmur.setName("PanjangUmur"); // NOI18N
        PanjangUmur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangUmurKeyPressed(evt);
            }
        });
        FormInput.add(PanjangUmur);
        PanjangUmur.setBounds(343, 100, 70, 23);

        jLabel21.setText("Minimal Umur :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(210, 100, 130, 23);

        jLabel40.setText("Nama Png.Jawab :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(210, 130, 130, 23);

        YesNoNamaKeluarga.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoNamaKeluarga.setName("YesNoNamaKeluarga"); // NOI18N
        YesNoNamaKeluarga.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoNamaKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoNamaKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(YesNoNamaKeluarga);
        YesNoNamaKeluarga.setBounds(343, 130, 70, 23);

        PanjangNamaKeluarga.setText("0");
        PanjangNamaKeluarga.setHighlighter(null);
        PanjangNamaKeluarga.setName("PanjangNamaKeluarga"); // NOI18N
        PanjangNamaKeluarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangNamaKeluargaKeyPressed(evt);
            }
        });
        FormInput.add(PanjangNamaKeluarga);
        PanjangNamaKeluarga.setBounds(343, 160, 70, 23);

        jLabel22.setText("Minimal Nama Png.Jawab :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(200, 160, 140, 23);

        jLabel41.setText("No.Peserta :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(210, 190, 130, 23);

        YesNoNoPeserta.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoNoPeserta.setName("YesNoNoPeserta"); // NOI18N
        YesNoNoPeserta.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoNoPeserta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoNoPesertaKeyPressed(evt);
            }
        });
        FormInput.add(YesNoNoPeserta);
        YesNoNoPeserta.setBounds(343, 190, 70, 23);

        PanjangNoPeserta.setText("0");
        PanjangNoPeserta.setHighlighter(null);
        PanjangNoPeserta.setName("PanjangNoPeserta"); // NOI18N
        PanjangNoPeserta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangNoPesertaKeyPressed(evt);
            }
        });
        FormInput.add(PanjangNoPeserta);
        PanjangNoPeserta.setBounds(343, 220, 70, 23);

        jLabel23.setText("Minimal No.Peserta :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(210, 220, 130, 23);

        jLabel42.setText("Kelurahan :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(210, 250, 130, 23);

        YesNoKelurahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoKelurahan.setName("YesNoKelurahan"); // NOI18N
        YesNoKelurahan.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoKelurahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoKelurahanKeyPressed(evt);
            }
        });
        FormInput.add(YesNoKelurahan);
        YesNoKelurahan.setBounds(343, 250, 70, 23);

        PanjangKelurahan.setText("0");
        PanjangKelurahan.setHighlighter(null);
        PanjangKelurahan.setName("PanjangKelurahan"); // NOI18N
        PanjangKelurahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangKelurahanKeyPressed(evt);
            }
        });
        FormInput.add(PanjangKelurahan);
        PanjangKelurahan.setBounds(343, 280, 70, 23);

        jLabel24.setText("Minimal Kelurahan :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(210, 280, 130, 23);

        jLabel43.setText("Kecamatan :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(415, 10, 130, 23);

        jLabel25.setText("Minimal Kecamatan :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(415, 40, 130, 23);

        jLabel44.setText("Kabupaten :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(415, 70, 130, 23);

        jLabel26.setText("Minimal Kabupaten :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(415, 100, 130, 23);

        jLabel45.setText("Pekerjaan P.J. :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(415, 130, 130, 23);

        jLabel27.setText("Minimal Pekerjaan P.J. :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(415, 160, 130, 23);

        jLabel46.setText("Alamat P.J. :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(415, 190, 130, 23);

        jLabel28.setText("Minimal Alamat P.J. :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(415, 220, 130, 23);

        jLabel47.setText("Kelurahan P.J.:");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(415, 250, 130, 23);

        jLabel29.setText("Minimal Kelurahan P.J. :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(415, 280, 130, 23);

        YesNoKecamatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoKecamatan.setName("YesNoKecamatan"); // NOI18N
        YesNoKecamatan.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoKecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoKecamatanKeyPressed(evt);
            }
        });
        FormInput.add(YesNoKecamatan);
        YesNoKecamatan.setBounds(548, 10, 70, 23);

        PanjangKecamatan.setText("0");
        PanjangKecamatan.setHighlighter(null);
        PanjangKecamatan.setName("PanjangKecamatan"); // NOI18N
        PanjangKecamatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangKecamatanKeyPressed(evt);
            }
        });
        FormInput.add(PanjangKecamatan);
        PanjangKecamatan.setBounds(548, 40, 70, 23);

        YesNoKabupaten.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoKabupaten.setName("YesNoKabupaten"); // NOI18N
        YesNoKabupaten.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoKabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoKabupatenKeyPressed(evt);
            }
        });
        FormInput.add(YesNoKabupaten);
        YesNoKabupaten.setBounds(548, 70, 70, 23);

        PanjangKabupaten.setText("0");
        PanjangKabupaten.setHighlighter(null);
        PanjangKabupaten.setName("PanjangKabupaten"); // NOI18N
        PanjangKabupaten.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangKabupatenKeyPressed(evt);
            }
        });
        FormInput.add(PanjangKabupaten);
        PanjangKabupaten.setBounds(548, 100, 70, 23);

        YesNoPekerjaanPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoPekerjaanPJ.setName("YesNoPekerjaanPJ"); // NOI18N
        YesNoPekerjaanPJ.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoPekerjaanPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoPekerjaanPJKeyPressed(evt);
            }
        });
        FormInput.add(YesNoPekerjaanPJ);
        YesNoPekerjaanPJ.setBounds(548, 130, 70, 23);

        PanjangPekerjaanPJ.setText("0");
        PanjangPekerjaanPJ.setHighlighter(null);
        PanjangPekerjaanPJ.setName("PanjangPekerjaanPJ"); // NOI18N
        PanjangPekerjaanPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangPekerjaanPJKeyPressed(evt);
            }
        });
        FormInput.add(PanjangPekerjaanPJ);
        PanjangPekerjaanPJ.setBounds(548, 160, 70, 23);

        YesNoAlamatPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoAlamatPJ.setName("YesNoAlamatPJ"); // NOI18N
        YesNoAlamatPJ.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoAlamatPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoAlamatPJKeyPressed(evt);
            }
        });
        FormInput.add(YesNoAlamatPJ);
        YesNoAlamatPJ.setBounds(548, 190, 70, 23);

        PanjangAlamatPJ.setText("0");
        PanjangAlamatPJ.setHighlighter(null);
        PanjangAlamatPJ.setName("PanjangAlamatPJ"); // NOI18N
        PanjangAlamatPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangAlamatPJKeyPressed(evt);
            }
        });
        FormInput.add(PanjangAlamatPJ);
        PanjangAlamatPJ.setBounds(548, 220, 70, 23);

        YesNoKelurahanPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoKelurahanPJ.setName("YesNoKelurahanPJ"); // NOI18N
        YesNoKelurahanPJ.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoKelurahanPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoKelurahanPJKeyPressed(evt);
            }
        });
        FormInput.add(YesNoKelurahanPJ);
        YesNoKelurahanPJ.setBounds(548, 250, 70, 23);

        PanjangKelurahanPJ.setText("0");
        PanjangKelurahanPJ.setHighlighter(null);
        PanjangKelurahanPJ.setName("PanjangKelurahanPJ"); // NOI18N
        PanjangKelurahanPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangKelurahanPJKeyPressed(evt);
            }
        });
        FormInput.add(PanjangKelurahanPJ);
        PanjangKelurahanPJ.setBounds(548, 280, 70, 23);

        jLabel48.setText("Kecamatan P.J. :");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(620, 10, 135, 23);

        YesNoKecamatanPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoKecamatanPJ.setName("YesNoKecamatanPJ"); // NOI18N
        YesNoKecamatanPJ.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoKecamatanPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoKecamatanPJKeyPressed(evt);
            }
        });
        FormInput.add(YesNoKecamatanPJ);
        YesNoKecamatanPJ.setBounds(758, 10, 70, 23);

        PanjangKecamatanPJ.setText("0");
        PanjangKecamatanPJ.setHighlighter(null);
        PanjangKecamatanPJ.setName("PanjangKecamatanPJ"); // NOI18N
        PanjangKecamatanPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangKecamatanPJKeyPressed(evt);
            }
        });
        FormInput.add(PanjangKecamatanPJ);
        PanjangKecamatanPJ.setBounds(758, 40, 70, 23);

        jLabel30.setText("Minimal Kecamatan P.J. :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(620, 40, 135, 23);

        jLabel49.setText("Kabupaten P.J. :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(620, 70, 135, 23);

        YesNoKabupatenPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoKabupatenPJ.setName("YesNoKabupatenPJ"); // NOI18N
        YesNoKabupatenPJ.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoKabupatenPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoKabupatenPJKeyPressed(evt);
            }
        });
        FormInput.add(YesNoKabupatenPJ);
        YesNoKabupatenPJ.setBounds(758, 70, 70, 23);

        jLabel31.setText("Minimal Kabupaten P.J. :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(620, 100, 135, 23);

        PanjangKabupatenPJ.setText("0");
        PanjangKabupatenPJ.setHighlighter(null);
        PanjangKabupatenPJ.setName("PanjangKabupatenPJ"); // NOI18N
        PanjangKabupatenPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangKabupatenPJKeyPressed(evt);
            }
        });
        FormInput.add(PanjangKabupatenPJ);
        PanjangKabupatenPJ.setBounds(758, 100, 70, 23);

        jLabel50.setText("Propinsi :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(620, 130, 135, 23);

        YesNoPropinsi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoPropinsi.setName("YesNoPropinsi"); // NOI18N
        YesNoPropinsi.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoPropinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoPropinsiKeyPressed(evt);
            }
        });
        FormInput.add(YesNoPropinsi);
        YesNoPropinsi.setBounds(758, 130, 70, 23);

        PanjangPropinsi.setText("0");
        PanjangPropinsi.setHighlighter(null);
        PanjangPropinsi.setName("PanjangPropinsi"); // NOI18N
        PanjangPropinsi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangPropinsiKeyPressed(evt);
            }
        });
        FormInput.add(PanjangPropinsi);
        PanjangPropinsi.setBounds(758, 160, 70, 23);

        jLabel51.setText("Minimal Propinsi :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(620, 160, 135, 23);

        YesNoPropinsiPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        YesNoPropinsiPJ.setName("YesNoPropinsiPJ"); // NOI18N
        YesNoPropinsiPJ.setPreferredSize(new java.awt.Dimension(55, 28));
        YesNoPropinsiPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                YesNoPropinsiPJKeyPressed(evt);
            }
        });
        FormInput.add(YesNoPropinsiPJ);
        YesNoPropinsiPJ.setBounds(758, 190, 70, 23);

        PanjangPropinsiPJ.setText("0");
        PanjangPropinsiPJ.setHighlighter(null);
        PanjangPropinsiPJ.setName("PanjangPropinsiPJ"); // NOI18N
        PanjangPropinsiPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PanjangPropinsiPJKeyPressed(evt);
            }
        });
        FormInput.add(PanjangPropinsiPJ);
        PanjangPropinsiPJ.setBounds(758, 220, 70, 23);

        jLabel52.setText("Propinsi P.J. :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(620, 190, 135, 23);

        jLabel53.setText("Minimal Propinsi P.J. :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(620, 220, 135, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame5.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pengaturan Kelengkapan Data", internalFrame5);

        internalFrame6.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);

        tbValidasiRegistrasi.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbValidasiRegistrasi.setName("tbValidasiRegistrasi"); // NOI18N
        tbValidasiRegistrasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbValidasiRegistrasiMouseClicked(evt);
            }
        });
        tbValidasiRegistrasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbValidasiRegistrasiKeyPressed(evt);
            }
        });
        Scroll5.setViewportView(tbValidasiRegistrasi);

        internalFrame6.add(Scroll5, java.awt.BorderLayout.CENTER);

        panelGlass10.setName("panelGlass10"); // NOI18N
        panelGlass10.setPreferredSize(new java.awt.Dimension(44, 47));
        panelGlass10.setLayout(null);

        jLabel12.setText("Wajib closing kasir terlebih dahulu untuk perawatan sebelumnya :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelGlass10.add(jLabel12);
        jLabel12.setBounds(0, 12, 330, 23);

        ValidasiRegistrasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        ValidasiRegistrasi.setName("ValidasiRegistrasi"); // NOI18N
        ValidasiRegistrasi.setPreferredSize(new java.awt.Dimension(55, 28));
        ValidasiRegistrasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ValidasiRegistrasiKeyPressed(evt);
            }
        });
        panelGlass10.add(ValidasiRegistrasi);
        ValidasiRegistrasi.setBounds(333, 12, 70, 23);

        internalFrame6.add(panelGlass10, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pengaturan Registrasi", internalFrame6);

        internalFrame7.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame7.setBorder(null);
        internalFrame7.setName("internalFrame7"); // NOI18N
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll6.setName("Scroll6"); // NOI18N
        Scroll6.setOpaque(true);

        tbValidasiCatatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbValidasiCatatan.setName("tbValidasiCatatan"); // NOI18N
        tbValidasiCatatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbValidasiCatatanMouseClicked(evt);
            }
        });
        tbValidasiCatatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbValidasiCatatanKeyPressed(evt);
            }
        });
        Scroll6.setViewportView(tbValidasiCatatan);

        internalFrame7.add(Scroll6, java.awt.BorderLayout.CENTER);

        panelGlass11.setName("panelGlass11"); // NOI18N
        panelGlass11.setPreferredSize(new java.awt.Dimension(44, 47));
        panelGlass11.setLayout(null);

        jLabel13.setText("Tampilkan catatan pasien saat Registrasi Poli/Unit/IGD :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelGlass11.add(jLabel13);
        jLabel13.setBounds(0, 12, 280, 23);

        ValidasiCatatan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        ValidasiCatatan.setName("ValidasiCatatan"); // NOI18N
        ValidasiCatatan.setPreferredSize(new java.awt.Dimension(55, 28));
        ValidasiCatatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ValidasiCatatanKeyPressed(evt);
            }
        });
        panelGlass11.add(ValidasiCatatan);
        ValidasiCatatan.setBounds(283, 12, 70, 23);

        internalFrame7.add(panelGlass11, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pengaturan Catatan Pasien", internalFrame7);

        internalFrame8.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame8.setBorder(null);
        internalFrame8.setName("internalFrame8"); // NOI18N
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll7.setName("Scroll7"); // NOI18N
        Scroll7.setOpaque(true);

        tbTniPolri.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTniPolri.setName("tbTniPolri"); // NOI18N
        tbTniPolri.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTniPolriMouseClicked(evt);
            }
        });
        tbTniPolri.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTniPolriKeyPressed(evt);
            }
        });
        Scroll7.setViewportView(tbTniPolri);

        internalFrame8.add(Scroll7, java.awt.BorderLayout.CENTER);

        panelGlass12.setName("panelGlass12"); // NOI18N
        panelGlass12.setPreferredSize(new java.awt.Dimension(44, 47));
        panelGlass12.setLayout(null);

        jLabel14.setText("Tampilkan pilihan data pasien untuk RS TNI dan POLRI :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelGlass12.add(jLabel14);
        jLabel14.setBounds(0, 12, 280, 23);

        TampilkanTNI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Yes", "No" }));
        TampilkanTNI.setName("TampilkanTNI"); // NOI18N
        TampilkanTNI.setPreferredSize(new java.awt.Dimension(55, 28));
        TampilkanTNI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TampilkanTNIKeyPressed(evt);
            }
        });
        panelGlass12.add(TampilkanTNI);
        TampilkanTNI.setBounds(283, 12, 70, 23);

        internalFrame8.add(panelGlass12, java.awt.BorderLayout.PAGE_START);

        TabRawat.addTab("Pengaturan TNI POLRI", internalFrame8);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                if(norm.getText().trim().equals("")){
                    Valid.textKosong(norm,"No.RM Terakhir");
                }else if(tabMode.getRowCount()==0){
                    Sequel.menyimpan("set_no_rkm_medis","'"+norm.getText()+"'","No.RM Terakhir");
                    tampilnorm();
                    emptTeks();
                }else if(tabMode.getRowCount()>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu pengaturan ...!!!!");
                    norm.requestFocus();
                }   break;
            case 1:
                if(tabMode2.getRowCount()==0){
                    Sequel.menyimpan("set_urut_no_rkm_medis","'"+cmburut.getSelectedItem()+"','"+cmbYesTahun.getSelectedItem()+"','"+cmbYesBulan.getSelectedItem()+"','"+cmbPosisi.getSelectedItem()+"'","Pengurutan");
                    tampilurut();
                }else if(tabMode2.getRowCount()>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu pengaturan ...!!!!");
                    cmburut.requestFocus();
                }   break;
            case 2:
                if(tabMode3.getRowCount()==0){
                    Sequel.menyimpan("set_alamat_pasien",
                            "'"+Kelurahan.getSelectedItem().toString().replaceAll("Yes","true").replaceAll("No","false")+"',"+
                            "'"+Kecamatan.getSelectedItem().toString().replaceAll("Yes","true").replaceAll("No","false")+"',"+
                            "'"+Kabupaten.getSelectedItem().toString().replaceAll("Yes","true").replaceAll("No","false")+"',"+
                            "'"+Propinsi.getSelectedItem().toString().replaceAll("Yes","true").replaceAll("No","false")+"'","Pengaturan Alamat"
                    );
                    tampilalamat();
                }else if(tabMode3.getRowCount()>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu pengaturan ...!!!!");
                    cmburut.requestFocus();
                }   break;
            case 3:
                if(PanjangKTP.getText().trim().equals("")){
                    Valid.textKosong(PanjangKTP,"Panjang KTP");
                }else if(PanjangTmpLahir.getText().trim().equals("")){
                    Valid.textKosong(PanjangTmpLahir,"Panjang Tempat Lahir");
                }else if(PanjangNamaIbu.getText().trim().equals("")){
                    Valid.textKosong(PanjangNamaIbu,"Panjang Nama Ibu");
                }else if(PanjangAlamat.getText().trim().equals("")){
                    Valid.textKosong(PanjangAlamat,"Panjang Alamat");
                }else if(PanjangPekerjaan.getText().trim().equals("")){
                    Valid.textKosong(PanjangPekerjaan,"Panjang Pekerjaan");
                }else if(PanjangTelp.getText().trim().equals("")){
                    Valid.textKosong(PanjangTelp,"Panjang Telp");
                }else if(PanjangUmur.getText().trim().equals("")){
                    Valid.textKosong(PanjangUmur,"Panjang Umur");
                }else if(PanjangNamaKeluarga.getText().trim().equals("")){
                    Valid.textKosong(PanjangNamaKeluarga,"Panjang Nama Penanggung Jawab");
                }else if(PanjangNoPeserta.getText().trim().equals("")){
                    Valid.textKosong(PanjangNoPeserta,"Panjang No Peserta");
                }else if(PanjangKelurahan.getText().trim().equals("")){
                    Valid.textKosong(PanjangKelurahan,"Panjang Kelurahan");
                }else if(PanjangKecamatan.getText().trim().equals("")){
                    Valid.textKosong(PanjangKecamatan,"Panjang Kecamatan");
                }else if(PanjangKabupaten.getText().trim().equals("")){
                    Valid.textKosong(PanjangKabupaten,"Panjang Kabupaten");
                }else if(PanjangPekerjaanPJ.getText().trim().equals("")){
                    Valid.textKosong(PanjangPekerjaanPJ,"Panjang Pekerjaan P.J.");
                }else if(PanjangAlamatPJ.getText().trim().equals("")){
                    Valid.textKosong(PanjangAlamatPJ,"Panjang Alamat P.J.");
                }else if(PanjangKelurahanPJ.getText().trim().equals("")){
                    Valid.textKosong(PanjangKelurahanPJ,"Panjang Kelurahan P.J.");
                }else if(PanjangKecamatanPJ.getText().trim().equals("")){
                    Valid.textKosong(PanjangKecamatanPJ,"Panjang Kecamatan P.J.");
                }else if(PanjangKabupatenPJ.getText().trim().equals("")){
                    Valid.textKosong(PanjangKabupatenPJ,"Panjang Kabupaten P.J.");
                }else if(PanjangPropinsi.getText().trim().equals("")){
                    Valid.textKosong(PanjangPropinsi,"Panjang Propinsi");
                }else if(PanjangPropinsiPJ.getText().trim().equals("")){
                    Valid.textKosong(PanjangPropinsiPJ,"Panjang Propinsi P.J.");
                }else if(tabMode4.getRowCount()==0){
                    if(Sequel.menyimpantf("set_kelengkapan_data_pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Set Kelengkapan",38,new String[]{
                        YesNoKTP.getSelectedItem().toString(), PanjangKTP.getText(),YesNoTmpLahir.getSelectedItem().toString(),PanjangTmpLahir.getText(),
                        YesNoNamaIbu.getSelectedItem().toString(),PanjangNamaIbu.getText(),YesNoAlamat.getSelectedItem().toString(),PanjangAlamat.getText(),
                        YesNoPekerjaan.getSelectedItem().toString(),PanjangPekerjaan.getText(),YesNoTelp.getSelectedItem().toString(),PanjangTelp.getText(),
                        YesNoUmur.getSelectedItem().toString(),PanjangUmur.getText(),YesNoNamaKeluarga.getSelectedItem().toString(),PanjangNamaKeluarga.getText(),
                        YesNoNoPeserta.getSelectedItem().toString(),PanjangNoPeserta.getText(),YesNoKelurahan.getSelectedItem().toString(),PanjangKelurahan.getText(),
                        YesNoKecamatan.getSelectedItem().toString(),PanjangKecamatan.getText(),YesNoKabupaten.getSelectedItem().toString(),PanjangKabupaten.getText(),
                        YesNoPekerjaanPJ.getSelectedItem().toString(),PanjangPekerjaanPJ.getText(),YesNoAlamatPJ.getSelectedItem().toString(),PanjangAlamatPJ.getText(),
                        YesNoKelurahanPJ.getSelectedItem().toString(), PanjangKelurahanPJ.getText(),YesNoKecamatanPJ.getSelectedItem().toString(),PanjangKecamatanPJ.getText(),
                        YesNoKabupatenPJ.getSelectedItem().toString(), PanjangKabupatenPJ.getText(),YesNoPropinsi.getSelectedItem().toString(), PanjangPropinsi.getText(),
                        YesNoPropinsiPJ.getSelectedItem().toString(), PanjangPropinsiPJ.getText()
                    })==true){
                        tampilkelengkapan();
                        emptTeks2();
                    }
                }else if(tabMode4.getRowCount()>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu pengaturan ...!!!!");
                    norm.requestFocus();
                }   break;
            case 4:
                if(tabMode5.getRowCount()==0){
                    Sequel.menyimpan("set_validasi_registrasi",
                            "'"+ValidasiRegistrasi.getSelectedItem().toString()+"'","Pengaturan Validasi Registrasi"
                    );
                    tampilvalidasiregistrasi();
                }else if(tabMode5.getRowCount()>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu pengaturan ...!!!!");
                    ValidasiRegistrasi.requestFocus();
                }   break;
            case 5:
                if(tabMode6.getRowCount()==0){
                    Sequel.menyimpan("set_validasi_catatan",
                            "'"+ValidasiCatatan.getSelectedItem().toString()+"'","Pengaturan Validasi Catatan"
                    );
                    tampilvalidasicatatan();
                }else if(tabMode6.getRowCount()>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu pengaturan ...!!!!");
                    ValidasiCatatan.requestFocus();
                }   break;
            case 6:
                if(tabMode7.getRowCount()==0){
                    Sequel.menyimpan("set_tni_polri",
                            "'"+TampilkanTNI.getSelectedItem().toString()+"'","Pengaturan Validasi Catatan"
                    );
                    tampiltni();
                }else if(tabMode7.getRowCount()>0){
                    JOptionPane.showMessageDialog(null,"Maaf, Hanya diijinkan satu pengaturan ...!!!!");
                    TampilkanTNI.requestFocus();
                }   break;
            default:
                break;
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            if(TabRawat.getSelectedIndex()==0){
                Valid.pindah(evt,norm,BtnHapus);
            }else if(TabRawat.getSelectedIndex()==1){
                Valid.pindah(evt,cmbPosisi,BtnHapus);
            }else if(TabRawat.getSelectedIndex()==2){
                Valid.pindah(evt,Kabupaten,BtnHapus);
            }else if(TabRawat.getSelectedIndex()==3){
                Valid.pindah(evt,PanjangPropinsiPJ,BtnHapus);
            }else if(TabRawat.getSelectedIndex()==4){
                Valid.pindah(evt,ValidasiRegistrasi,BtnHapus);
            }else if(TabRawat.getSelectedIndex()==5){
                Valid.pindah(evt,ValidasiCatatan,BtnHapus);
            }else if(TabRawat.getSelectedIndex()==6){
                Valid.pindah(evt,TampilkanTNI,BtnHapus);
            }             
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                Sequel.queryu("delete from set_no_rkm_medis");
                tampilnorm();
                emptTeks();
                break;
            case 1:
                Sequel.queryu("delete from set_urut_no_rkm_medis");
                tampilurut();
                break;
            case 2:
                Sequel.queryu("delete from set_alamat_pasien");
                tampilalamat();
                break;
            case 3:
                Sequel.queryu("delete from set_kelengkapan_data_pasien");
                tampilkelengkapan();
                break;
            case 4:
                Sequel.queryu("delete from set_validasi_registrasi");
                tampilvalidasiregistrasi();
                break;
            case 5:
                Sequel.queryu("delete from set_validasi_catatan");
                tampilvalidasicatatan();
                break;
            case 6:
                Sequel.queryu("delete from set_tni_polri");
                tampiltni();
                break;
            default:
                break;
        }
        
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data No.RM Terakhir tidak boleh kosong ...!!!!");
            norm.requestFocus();
        }else if(! (tabMode.getRowCount()==0)) {
            dispose();
        }
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnHapus,BtnSimpan);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void tbNoRMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNoRMMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getDatanorm();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbNoRMMouseClicked

    private void tbNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbNoRMKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDatanorm();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbNoRMKeyPressed

private void normKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_normKeyPressed
     Valid.pindah(evt, BtnKeluar,BtnSimpan);

}//GEN-LAST:event_normKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(TabRawat.getSelectedIndex()==0){
            tampilnorm();
        }else if(TabRawat.getSelectedIndex()==1){
            tampilurut();
        }else if(TabRawat.getSelectedIndex()==2){
            tampilalamat();
        }else if(TabRawat.getSelectedIndex()==3){
            tampilkelengkapan();
        }else if(TabRawat.getSelectedIndex()==4){
            tampilvalidasiregistrasi();
        }else if(TabRawat.getSelectedIndex()==5){
            tampilvalidasicatatan();
        }else if(TabRawat.getSelectedIndex()==6){
            tampiltni();
        }
    }//GEN-LAST:event_formWindowOpened

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
           tampilnorm();
        }else if(TabRawat.getSelectedIndex()==1){
           tampilurut();
        }else if(TabRawat.getSelectedIndex()==2){
           tampilalamat();
        }else if(TabRawat.getSelectedIndex()==3){
           tampilkelengkapan();
        }else if(TabRawat.getSelectedIndex()==4){
            tampilvalidasiregistrasi();
        }else if(TabRawat.getSelectedIndex()==5){
            tampilvalidasicatatan();
        }else if(TabRawat.getSelectedIndex()==6){
            tampiltni();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void cmburutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmburutKeyPressed
        Valid.pindah(evt, BtnKeluar,cmbYesTahun);
    }//GEN-LAST:event_cmburutKeyPressed

    private void cmbYesTahunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbYesTahunKeyPressed
        Valid.pindah(evt, cmburut,BtnSimpan);
    }//GEN-LAST:event_cmbYesTahunKeyPressed

    private void tbNoUrutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbNoUrutKeyPressed
        if(tabMode2.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataurut();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbNoUrutKeyPressed

    private void tbNoUrutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbNoUrutMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getDataurut();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbNoUrutMouseClicked

    private void cmbYesBulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbYesBulanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbYesBulanKeyPressed

    private void cmbPosisiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPosisiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPosisiKeyPressed

    private void tbAlamatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAlamatMouseClicked
        if(tabMode3.getRowCount()!=0){
            try {
                getDataAlamat();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbAlamatMouseClicked

    private void tbAlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbAlamatKeyPressed
        if(tabMode3.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataAlamat();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbAlamatKeyPressed

    private void KelurahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelurahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KelurahanKeyPressed

    private void KecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KecamatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KecamatanKeyPressed

    private void KabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KabupatenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KabupatenKeyPressed

    private void tbKelengkapanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKelengkapanMouseClicked
        if(tabMode4.getRowCount()!=0){
            try {
                getDataKelengkapan();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbKelengkapanMouseClicked

    private void tbKelengkapanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbKelengkapanKeyPressed
        if(tabMode4.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataKelengkapan();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbKelengkapanKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void PanjangKTPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangKTPKeyPressed
        Valid.pindah(evt, YesNoKTP,YesNoTmpLahir);
    }//GEN-LAST:event_PanjangKTPKeyPressed

    private void YesNoKTPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoKTPKeyPressed
        Valid.pindah(evt, BtnSimpan,PanjangKTP);
    }//GEN-LAST:event_YesNoKTPKeyPressed

    private void PanjangTmpLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangTmpLahirKeyPressed
        Valid.pindah(evt, YesNoTmpLahir,YesNoNamaIbu);
    }//GEN-LAST:event_PanjangTmpLahirKeyPressed

    private void YesNoTmpLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoTmpLahirKeyPressed
        Valid.pindah(evt, PanjangKTP,PanjangTmpLahir);
    }//GEN-LAST:event_YesNoTmpLahirKeyPressed

    private void PanjangNamaIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangNamaIbuKeyPressed
        Valid.pindah(evt, YesNoNamaIbu,YesNoAlamat);
    }//GEN-LAST:event_PanjangNamaIbuKeyPressed

    private void YesNoNamaIbuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoNamaIbuKeyPressed
        Valid.pindah(evt, PanjangTmpLahir,PanjangNamaIbu);
    }//GEN-LAST:event_YesNoNamaIbuKeyPressed

    private void YesNoAlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoAlamatKeyPressed
        Valid.pindah(evt, PanjangNamaIbu,PanjangAlamat);
    }//GEN-LAST:event_YesNoAlamatKeyPressed

    private void PanjangAlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangAlamatKeyPressed
        Valid.pindah(evt, YesNoAlamat,YesNoPekerjaan);
    }//GEN-LAST:event_PanjangAlamatKeyPressed

    private void YesNoPekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoPekerjaanKeyPressed
        Valid.pindah(evt, PanjangAlamat,PanjangPekerjaan);
    }//GEN-LAST:event_YesNoPekerjaanKeyPressed

    private void PanjangPekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangPekerjaanKeyPressed
        Valid.pindah(evt, YesNoPekerjaan,YesNoTelp);
    }//GEN-LAST:event_PanjangPekerjaanKeyPressed

    private void YesNoTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoTelpKeyPressed
        Valid.pindah(evt,PanjangPekerjaan,PanjangTelp);
    }//GEN-LAST:event_YesNoTelpKeyPressed

    private void PanjangTelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangTelpKeyPressed
        Valid.pindah(evt, YesNoTelp,YesNoUmur);
    }//GEN-LAST:event_PanjangTelpKeyPressed

    private void YesNoUmurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoUmurKeyPressed
        Valid.pindah(evt, PanjangTelp,PanjangUmur);
    }//GEN-LAST:event_YesNoUmurKeyPressed

    private void PanjangUmurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangUmurKeyPressed
        Valid.pindah(evt, YesNoUmur,YesNoNamaKeluarga);
    }//GEN-LAST:event_PanjangUmurKeyPressed

    private void YesNoNamaKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoNamaKeluargaKeyPressed
        Valid.pindah(evt, PanjangUmur,PanjangNamaKeluarga);
    }//GEN-LAST:event_YesNoNamaKeluargaKeyPressed

    private void PanjangNamaKeluargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangNamaKeluargaKeyPressed
        Valid.pindah(evt, YesNoNamaKeluarga,YesNoNoPeserta);
    }//GEN-LAST:event_PanjangNamaKeluargaKeyPressed

    private void YesNoNoPesertaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoNoPesertaKeyPressed
        Valid.pindah(evt, PanjangNamaKeluarga,PanjangNoPeserta);
    }//GEN-LAST:event_YesNoNoPesertaKeyPressed

    private void PanjangNoPesertaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangNoPesertaKeyPressed
        Valid.pindah(evt, YesNoNoPeserta,YesNoKelurahan);
    }//GEN-LAST:event_PanjangNoPesertaKeyPressed

    private void YesNoKelurahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoKelurahanKeyPressed
        Valid.pindah(evt, PanjangNoPeserta,PanjangKelurahan);
    }//GEN-LAST:event_YesNoKelurahanKeyPressed

    private void PanjangKelurahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangKelurahanKeyPressed
        Valid.pindah(evt, YesNoKelurahan,YesNoKecamatan);
    }//GEN-LAST:event_PanjangKelurahanKeyPressed

    private void YesNoKecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoKecamatanKeyPressed
        Valid.pindah(evt, PanjangKelurahan,PanjangKecamatan);
    }//GEN-LAST:event_YesNoKecamatanKeyPressed

    private void PanjangKecamatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangKecamatanKeyPressed
        Valid.pindah(evt, YesNoKecamatan,YesNoKabupaten);
    }//GEN-LAST:event_PanjangKecamatanKeyPressed

    private void YesNoKabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoKabupatenKeyPressed
        Valid.pindah(evt, PanjangKecamatan,PanjangKabupaten);
    }//GEN-LAST:event_YesNoKabupatenKeyPressed

    private void PanjangKabupatenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangKabupatenKeyPressed
        Valid.pindah(evt, YesNoKabupaten,YesNoPekerjaanPJ);
    }//GEN-LAST:event_PanjangKabupatenKeyPressed

    private void YesNoPekerjaanPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoPekerjaanPJKeyPressed
        Valid.pindah(evt, PanjangKabupaten,PanjangPekerjaanPJ);
    }//GEN-LAST:event_YesNoPekerjaanPJKeyPressed

    private void PanjangPekerjaanPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangPekerjaanPJKeyPressed
        Valid.pindah(evt, YesNoPekerjaanPJ,YesNoAlamatPJ);
    }//GEN-LAST:event_PanjangPekerjaanPJKeyPressed

    private void YesNoAlamatPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoAlamatPJKeyPressed
        Valid.pindah(evt, PanjangPekerjaanPJ,PanjangAlamatPJ);
    }//GEN-LAST:event_YesNoAlamatPJKeyPressed

    private void PanjangAlamatPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangAlamatPJKeyPressed
        Valid.pindah(evt, YesNoAlamatPJ,YesNoKelurahanPJ);
    }//GEN-LAST:event_PanjangAlamatPJKeyPressed

    private void YesNoKelurahanPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoKelurahanPJKeyPressed
        Valid.pindah(evt, PanjangAlamatPJ,PanjangKelurahanPJ);
    }//GEN-LAST:event_YesNoKelurahanPJKeyPressed

    private void PanjangKelurahanPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangKelurahanPJKeyPressed
        Valid.pindah(evt, YesNoKelurahanPJ,YesNoKecamatanPJ);
    }//GEN-LAST:event_PanjangKelurahanPJKeyPressed

    private void YesNoKecamatanPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoKecamatanPJKeyPressed
        Valid.pindah(evt, PanjangKelurahanPJ,PanjangKecamatanPJ);
    }//GEN-LAST:event_YesNoKecamatanPJKeyPressed

    private void PanjangKecamatanPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangKecamatanPJKeyPressed
        Valid.pindah(evt, YesNoKecamatanPJ,YesNoKabupatenPJ);
    }//GEN-LAST:event_PanjangKecamatanPJKeyPressed

    private void YesNoKabupatenPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoKabupatenPJKeyPressed
        Valid.pindah(evt, PanjangKecamatanPJ,PanjangKabupatenPJ);
    }//GEN-LAST:event_YesNoKabupatenPJKeyPressed

    private void PanjangKabupatenPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangKabupatenPJKeyPressed
        Valid.pindah(evt, YesNoKabupatenPJ,YesNoPropinsi);
    }//GEN-LAST:event_PanjangKabupatenPJKeyPressed

    private void tbValidasiRegistrasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbValidasiRegistrasiMouseClicked
        if(tabMode5.getRowCount()!=0){
            try {
                getDataValidasiRegistrasi();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbValidasiRegistrasiMouseClicked

    private void tbValidasiRegistrasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbValidasiRegistrasiKeyPressed
        if(tabMode5.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataValidasiRegistrasi();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbValidasiRegistrasiKeyPressed

    private void ValidasiRegistrasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ValidasiRegistrasiKeyPressed
        Valid.pindah(evt, BtnKeluar,BtnSimpan);
    }//GEN-LAST:event_ValidasiRegistrasiKeyPressed

    private void tbValidasiCatatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbValidasiCatatanMouseClicked
        if(tabMode6.getRowCount()!=0){
            try {
                getDataValidasiCatatan();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbValidasiCatatanMouseClicked

    private void tbValidasiCatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbValidasiCatatanKeyPressed
        if(tabMode6.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataValidasiCatatan();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbValidasiCatatanKeyPressed

    private void ValidasiCatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ValidasiCatatanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ValidasiCatatanKeyPressed

    private void tbTniPolriMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTniPolriMouseClicked
        if(tabMode7.getRowCount()!=0){
            try {
                getDataTampilkanTNI();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbTniPolriMouseClicked

    private void tbTniPolriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTniPolriKeyPressed
        if(tabMode7.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataTampilkanTNI();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbTniPolriKeyPressed

    private void TampilkanTNIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TampilkanTNIKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TampilkanTNIKeyPressed

    private void PropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PropinsiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PropinsiKeyPressed

    private void YesNoPropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoPropinsiKeyPressed
        Valid.pindah(evt, PanjangKabupatenPJ,PanjangPropinsi);
    }//GEN-LAST:event_YesNoPropinsiKeyPressed

    private void PanjangPropinsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangPropinsiKeyPressed
        Valid.pindah(evt, YesNoPropinsi,YesNoPropinsiPJ);
    }//GEN-LAST:event_PanjangPropinsiKeyPressed

    private void YesNoPropinsiPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_YesNoPropinsiPJKeyPressed
        Valid.pindah(evt, PanjangPropinsi,PanjangPropinsiPJ);
    }//GEN-LAST:event_YesNoPropinsiPJKeyPressed

    private void PanjangPropinsiPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PanjangPropinsiPJKeyPressed
        Valid.pindah(evt, YesNoPropinsiPJ,BtnSimpan);
    }//GEN-LAST:event_PanjangPropinsiPJKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgSetRM dialog = new DlgSetRM(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.ComboBox Kabupaten;
    private widget.ComboBox Kecamatan;
    private widget.ComboBox Kelurahan;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox PanjangAlamat;
    private widget.TextBox PanjangAlamatPJ;
    private widget.TextBox PanjangKTP;
    private widget.TextBox PanjangKabupaten;
    private widget.TextBox PanjangKabupatenPJ;
    private widget.TextBox PanjangKecamatan;
    private widget.TextBox PanjangKecamatanPJ;
    private widget.TextBox PanjangKelurahan;
    private widget.TextBox PanjangKelurahanPJ;
    private widget.TextBox PanjangNamaIbu;
    private widget.TextBox PanjangNamaKeluarga;
    private widget.TextBox PanjangNoPeserta;
    private widget.TextBox PanjangPekerjaan;
    private widget.TextBox PanjangPekerjaanPJ;
    private widget.TextBox PanjangPropinsi;
    private widget.TextBox PanjangPropinsiPJ;
    private widget.TextBox PanjangTelp;
    private widget.TextBox PanjangTmpLahir;
    private widget.TextBox PanjangUmur;
    private widget.ComboBox Propinsi;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private javax.swing.JTabbedPane TabRawat;
    private widget.ComboBox TampilkanTNI;
    private widget.ComboBox ValidasiCatatan;
    private widget.ComboBox ValidasiRegistrasi;
    private widget.ComboBox YesNoAlamat;
    private widget.ComboBox YesNoAlamatPJ;
    private widget.ComboBox YesNoKTP;
    private widget.ComboBox YesNoKabupaten;
    private widget.ComboBox YesNoKabupatenPJ;
    private widget.ComboBox YesNoKecamatan;
    private widget.ComboBox YesNoKecamatanPJ;
    private widget.ComboBox YesNoKelurahan;
    private widget.ComboBox YesNoKelurahanPJ;
    private widget.ComboBox YesNoNamaIbu;
    private widget.ComboBox YesNoNamaKeluarga;
    private widget.ComboBox YesNoNoPeserta;
    private widget.ComboBox YesNoPekerjaan;
    private widget.ComboBox YesNoPekerjaanPJ;
    private widget.ComboBox YesNoPropinsi;
    private widget.ComboBox YesNoPropinsiPJ;
    private widget.ComboBox YesNoTelp;
    private widget.ComboBox YesNoTmpLahir;
    private widget.ComboBox YesNoUmur;
    private widget.ComboBox cmbPosisi;
    private widget.ComboBox cmbYesBulan;
    private widget.ComboBox cmbYesTahun;
    private widget.ComboBox cmburut;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
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
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel5;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.TextBox norm;
    private widget.panelisi panelGlass10;
    private widget.panelisi panelGlass11;
    private widget.panelisi panelGlass12;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbAlamat;
    private widget.Table tbKelengkapan;
    private widget.Table tbNoRM;
    private widget.Table tbNoUrut;
    private widget.Table tbTniPolri;
    private widget.Table tbValidasiCatatan;
    private widget.Table tbValidasiRegistrasi;
    // End of variables declaration//GEN-END:variables

    private void tampilnorm() {
        Valid.tabelKosong(tabMode);
        try{   
            ps=koneksi.prepareStatement("select * from set_no_rkm_medis ");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{rs.getString(1)});
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
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void getDatanorm() {
        int row=tbNoRM.getSelectedRow();
        if(row!= -1){
            norm.setText(tbNoRM.getValueAt(row,0).toString());
        }
    }

    public void emptTeks() {
        norm.setText("");
        norm.requestFocus();
    }
    
    public void emptTeks2() {
        PanjangKTP.setText("0");
        YesNoKTP.setSelectedIndex(0);
        PanjangTmpLahir.setText("0");
        YesNoTmpLahir.setSelectedIndex(0);
        PanjangNamaIbu.setText("0");
        YesNoNamaIbu.setSelectedIndex(0);
        PanjangAlamat.setText("0");
        YesNoAlamat.setSelectedIndex(0);
        PanjangPekerjaan.setText("0");
        YesNoPekerjaan.setSelectedIndex(0);
        PanjangTelp.setText("0");
        YesNoTelp.setSelectedIndex(0);
        PanjangUmur.setText("0");
        YesNoUmur.setSelectedIndex(0);
        PanjangNamaKeluarga.setText("0");
        YesNoNamaKeluarga.setSelectedIndex(0);
        PanjangNoPeserta.setText("0");
        YesNoNoPeserta.setSelectedIndex(0);
        PanjangKelurahan.setText("0");
        YesNoKelurahan.setSelectedIndex(0);
        PanjangKecamatan.setText("0");
        YesNoKecamatan.setSelectedIndex(0);
        PanjangKabupaten.setText("0");
        YesNoKabupaten.setSelectedIndex(0);
        PanjangPekerjaanPJ.setText("0");
        YesNoPekerjaanPJ.setSelectedIndex(0);
        PanjangAlamatPJ.setText("0");
        YesNoAlamatPJ.setSelectedIndex(0);
        PanjangKelurahanPJ.setText("0");
        YesNoKelurahanPJ.setSelectedIndex(0);
        PanjangKecamatanPJ.setText("0");
        YesNoKecamatanPJ.setSelectedIndex(0);
        PanjangKabupatenPJ.setText("0");
        YesNoKabupatenPJ.setSelectedIndex(0);
        PanjangPropinsi.setText("0");
        YesNoPropinsi.setSelectedIndex(0);
        PanjangPropinsiPJ.setText("0");
        YesNoPropinsiPJ.setSelectedIndex(0);
        YesNoKTP.requestFocus();
    }
    
    private void tampilurut() {
        Valid.tabelKosong(tabMode2);
        try{
            ps2=koneksi.prepareStatement("select * from set_urut_no_rkm_medis ");
            try {
                rs=ps2.executeQuery();
                while(rs.next()){
                    tabMode2.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getDataurut() {
        int row=tbNoUrut.getSelectedRow();
        if(row!= -1){
            cmburut.setSelectedItem(tbNoUrut.getValueAt(row,0).toString());
            cmbYesTahun.setSelectedItem(tbNoUrut.getValueAt(row,1).toString());
            cmbYesBulan.setSelectedItem(tbNoUrut.getValueAt(row,2).toString());
            cmbPosisi.setSelectedItem(tbNoUrut.getValueAt(row,3).toString());
        }
    }
    
    private void tampilalamat() {
        Valid.tabelKosong(tabMode3);
        try{
            ps3=koneksi.prepareStatement("select * from set_alamat_pasien ");
            try {
                rs=ps3.executeQuery();
                while(rs.next()){
                    tabMode3.addRow(new Object[]{
                        rs.getString(1).replaceAll("true","Yes").replaceAll("false","No"),
                        rs.getString(2).replaceAll("true","Yes").replaceAll("false","No"),
                        rs.getString(3).replaceAll("true","Yes").replaceAll("false","No"),
                        rs.getString(4).replaceAll("true","Yes").replaceAll("false","No")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps3!=null){
                    ps3.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,340));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    private void getDataAlamat() {
        int row=tbAlamat.getSelectedRow();
        if(row!= -1){
            Kelurahan.setSelectedItem(tbAlamat.getValueAt(row,0).toString());
            Kecamatan.setSelectedItem(tbAlamat.getValueAt(row,1).toString());
            Kabupaten.setSelectedItem(tbAlamat.getValueAt(row,2).toString());
            Propinsi.setSelectedItem(tbAlamat.getValueAt(row,3).toString());
        }
    }
    
    public void tampilkelengkapan() {
        Valid.tabelKosong(tabMode4);
        try{    
            ps4=koneksi.prepareStatement("select * from set_kelengkapan_data_pasien ");
            try {
                rs=ps4.executeQuery();
                while(rs.next()){
                    tabMode4.addRow(new Object[]{" Kelengkapan Data No.KTP/SIM Pasien",rs.getString(1)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Karakter KTP/SIM",rs.getString(2)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Tempat Lahir",rs.getString(3)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Tempat Lahir",rs.getString(4)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Nama Ibu",rs.getString(5)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Nama Ibu",rs.getString(6)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Alamat Pasien",rs.getString(7)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Alamat Pasien",rs.getString(8)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Pekerjaan Pasien",rs.getString(9)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Pekerjaan Pasien",rs.getString(10)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data No.Telp",rs.getString(11)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang No.Telp",rs.getString(12)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Umur Pasien",rs.getString(13)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Umur Pasien",rs.getString(14)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Nama Penanggung Jawab Pasien",rs.getString(15)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Nama Penanggung Jawab Pasien",rs.getString(16)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data No.Peserta",rs.getString(17)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang No.Peserta",rs.getString(18)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Kelurahan",rs.getString(19)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Kelurahan",rs.getString(20)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Kecamatan",rs.getString(21)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Kecamatan",rs.getString(22)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Kabupaten",rs.getString(23)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Kabupaten",rs.getString(24)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Pekerjaan Penanggung Jawab Pasien",rs.getString(25)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Pekerjaan Penanggung Jawab Pasien",rs.getString(26)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Alamat Penanggung Jawab Pasien",rs.getString(27)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Alamat Penanggung Jawab Pasien",rs.getString(28)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Kelurahan Penanggung Jawab Pasien",rs.getString(29)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Kelurahan Penanggung Jawab Pasien",rs.getString(30)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Kecamatan Penanggung Jawab Pasien",rs.getString(31)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Kecamatan Penanggung Jawab Pasien",rs.getString(32)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Kabupaten Penanggung Jawab Pasien",rs.getString(33)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Kabupaten Penanggung Jawab Pasien",rs.getString(34)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Propinsi Pasien",rs.getString(35)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Propinsi Pasien",rs.getString(36)});
                    tabMode4.addRow(new Object[]{" Kelengkapan Data Propinsi Penanggung Jawab Pasien",rs.getString(37)});
                    tabMode4.addRow(new Object[]{" Minimal Panjang Propinsi Penanggung Jawab Pasien",rs.getString(38)});
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
    }
    
    private void getDataKelengkapan() {
        int row=tbKelengkapan.getSelectedRow();
        if(row!= -1){
            YesNoKTP.setSelectedItem(tbKelengkapan.getValueAt(0,1).toString());            
            PanjangKTP.setText(tbKelengkapan.getValueAt(1,1).toString()); 
            YesNoTmpLahir.setSelectedItem(tbKelengkapan.getValueAt(2,1).toString());            
            PanjangTmpLahir.setText(tbKelengkapan.getValueAt(3,1).toString()); 
            YesNoNamaIbu.setSelectedItem(tbKelengkapan.getValueAt(4,1).toString());            
            PanjangNamaIbu.setText(tbKelengkapan.getValueAt(5,1).toString()); 
            YesNoAlamat.setSelectedItem(tbKelengkapan.getValueAt(6,1).toString());            
            PanjangAlamat.setText(tbKelengkapan.getValueAt(7,1).toString()); 
            YesNoPekerjaan.setSelectedItem(tbKelengkapan.getValueAt(8,1).toString());            
            PanjangPekerjaan.setText(tbKelengkapan.getValueAt(9,1).toString()); 
            YesNoTelp.setSelectedItem(tbKelengkapan.getValueAt(10,1).toString());            
            PanjangTelp.setText(tbKelengkapan.getValueAt(11,1).toString()); 
            YesNoUmur.setSelectedItem(tbKelengkapan.getValueAt(12,1).toString());            
            PanjangUmur.setText(tbKelengkapan.getValueAt(13,1).toString()); 
            YesNoNamaKeluarga.setSelectedItem(tbKelengkapan.getValueAt(14,1).toString());            
            PanjangNamaKeluarga.setText(tbKelengkapan.getValueAt(15,1).toString()); 
            YesNoNoPeserta.setSelectedItem(tbKelengkapan.getValueAt(16,1).toString());            
            PanjangNoPeserta.setText(tbKelengkapan.getValueAt(17,1).toString()); 
            YesNoKelurahan.setSelectedItem(tbKelengkapan.getValueAt(18,1).toString());            
            PanjangKelurahan.setText(tbKelengkapan.getValueAt(19,1).toString()); 
            YesNoKecamatan.setSelectedItem(tbKelengkapan.getValueAt(20,1).toString());            
            PanjangKecamatan.setText(tbKelengkapan.getValueAt(21,1).toString()); 
            YesNoKabupaten.setSelectedItem(tbKelengkapan.getValueAt(22,1).toString());            
            PanjangKabupaten.setText(tbKelengkapan.getValueAt(23,1).toString()); 
            YesNoPekerjaanPJ.setSelectedItem(tbKelengkapan.getValueAt(24,1).toString());            
            PanjangPekerjaanPJ.setText(tbKelengkapan.getValueAt(25,1).toString()); 
            YesNoAlamatPJ.setSelectedItem(tbKelengkapan.getValueAt(26,1).toString());            
            PanjangAlamatPJ.setText(tbKelengkapan.getValueAt(27,1).toString()); 
            YesNoKelurahanPJ.setSelectedItem(tbKelengkapan.getValueAt(28,1).toString());            
            PanjangKelurahanPJ.setText(tbKelengkapan.getValueAt(29,1).toString()); 
            YesNoKecamatanPJ.setSelectedItem(tbKelengkapan.getValueAt(30,1).toString());            
            PanjangKecamatanPJ.setText(tbKelengkapan.getValueAt(31,1).toString()); 
            YesNoKabupatenPJ.setSelectedItem(tbKelengkapan.getValueAt(32,1).toString());            
            PanjangKabupatenPJ.setText(tbKelengkapan.getValueAt(33,1).toString()); 
            YesNoPropinsi.setSelectedItem(tbKelengkapan.getValueAt(34,1).toString());            
            PanjangPropinsi.setText(tbKelengkapan.getValueAt(35,1).toString()); 
            YesNoPropinsiPJ.setSelectedItem(tbKelengkapan.getValueAt(36,1).toString());            
            PanjangPropinsiPJ.setText(tbKelengkapan.getValueAt(37,1).toString()); 
        }
    }
    
    private void tampilvalidasiregistrasi() {
        Valid.tabelKosong(tabMode5);
        try{   
            ps5=koneksi.prepareStatement("select * from set_validasi_registrasi ");
            try {
                rs=ps5.executeQuery();
                while(rs.next()){
                    tabMode5.addRow(new Object[]{rs.getString(1)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps5!=null){
                    ps5.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void getDataValidasiRegistrasi() {
        int row=tbValidasiRegistrasi.getSelectedRow();
        if(row!= -1){
            ValidasiRegistrasi.setSelectedItem(tbValidasiRegistrasi.getValueAt(row,0).toString());
        }
    }

    private void tampilvalidasicatatan() {
        Valid.tabelKosong(tabMode6);
        try{   
            ps5=koneksi.prepareStatement("select * from set_validasi_catatan ");
            try {
                rs=ps5.executeQuery();
                while(rs.next()){
                    tabMode6.addRow(new Object[]{rs.getString(1)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps5!=null){
                    ps5.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getDataValidasiCatatan() {
        int row=tbValidasiCatatan.getSelectedRow();
        if(row!= -1){
            ValidasiCatatan.setSelectedItem(tbValidasiCatatan.getValueAt(row,0).toString());
        }
    }
    
    private void tampiltni() {
        Valid.tabelKosong(tabMode7);
        try{   
            ps6=koneksi.prepareStatement("select * from set_tni_polri ");
            try {
                rs=ps6.executeQuery();
                while(rs.next()){
                    tabMode7.addRow(new Object[]{rs.getString(1)});
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps6!=null){
                    ps6.close();
                }
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void getDataTampilkanTNI() {
        int row=tbTniPolri.getSelectedRow();
        if(row!= -1){
            TampilkanTNI.setSelectedItem(tbTniPolri.getValueAt(row,0).toString());
        }
    }
    
}

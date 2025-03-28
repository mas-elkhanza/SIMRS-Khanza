/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMEdukasiPasienKeluargaRawatJalan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private String finger="";
    private StringBuilder htmlContent;
    private String TANGGALMUNDUR="yes",pilihan="";
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMEdukasiPasienKeluargaRawatJalan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.R.M.","Nama Pasien","Tgl.Lahir","JK","Tanggal","Bicara","Keterangan Bicara","Bahasa Sehari-hari","Keterangan Bahasa Sehari-hari",
            "Perlu Penerjemah","Keterangan Penerjemah","Bahasa Isyarat","Cara Belajar","Hambatan Belajar","Keterangan Hambatan Belajar","Kemampuan Belajar", 
            "Keterangan Kemampuan Belajar","Pendidikan Pasien","Penyakitnya Merupakan","Keterangan Penyakitnya Merupakan","Keputusan Memilih Layanan", 
            "Keterangan Keputusan Memilih Layanan","Keyakinan Terhadap Terapi","Keterangan Keyakinan Terhadap Terapi","Aspek Keyakinan Dipertimbangkan", 
            "Keterangan Aspek Keyakinan Dipertimbangkan","Kesediaan Menerima Informasi","Topik Edukasi Penyakit Diderita","Topik Edukasi Rencana Tindakan/Terapi", 
            "Topik Edukasi Pengobatan/Prosedur Diperlukan","Topik Edukasi Hasil Pelayanan","NIP","Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 34; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(122);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(160);
            }else if(i==10){
                column.setPreferredWidth(96);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(110);
            }else if(i==14){
                column.setPreferredWidth(104);
            }else if(i==15){
                column.setPreferredWidth(156);
            }else if(i==16){
                column.setPreferredWidth(170);
            }else if(i==17){
                column.setPreferredWidth(170);
            }else if(i==18){
                column.setPreferredWidth(98);
            }else if(i==19){
                column.setPreferredWidth(122);
            }else if(i==20){
                column.setPreferredWidth(182);
            }else if(i==21){
                column.setPreferredWidth(143);
            }else if(i==22){
                column.setPreferredWidth(204);
            }else if(i==23){
                column.setPreferredWidth(197);
            }else if(i==24){
                column.setPreferredWidth(198);
            }else if(i==25){
                column.setPreferredWidth(175);
            }else if(i==26){
                column.setPreferredWidth(235);
            }else if(i==27){
                column.setPreferredWidth(160);
            }else if(i==28){
                column.setPreferredWidth(160);
            }else if(i==29){
                column.setPreferredWidth(200);
            }else if(i==30){
                column.setPreferredWidth(236);
            }else if(i==31){
                column.setPreferredWidth(155);
            }else if(i==32){
                column.setPreferredWidth(90);
            }else if(i==33){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));
        KeteranganBicara.setDocument(new batasInput((byte)50).getKata(KeteranganBicara));
        KeteranganPenerjemah.setDocument(new batasInput((byte)50).getKata(KeteranganPenerjemah));
        KeteranganBahasa.setDocument(new batasInput((byte)50).getKata(KeteranganBahasa));
        KeteranganHambatanBelajar.setDocument(new batasInput((byte)50).getKata(KeteranganHambatanBelajar));
        KeteranganKemampuanBelajar.setDocument(new batasInput((byte)50).getKata(KeteranganKemampuanBelajar));
        KeteranganPenyakitnyaMerupakan.setDocument(new batasInput((byte)50).getKata(KeteranganPenyakitnyaMerupakan));
        KeteranganKeputusanMemilihLayanan.setDocument(new batasInput((byte)50).getKata(KeteranganKeputusanMemilihLayanan));
        KeteranganKeyakinanTerhadapHasil.setDocument(new batasInput((byte)50).getKata(KeteranganKeyakinanTerhadapHasil));
        KeteranganAspekKeyakinan.setDocument(new batasInput((byte)50).getKata(KeteranganAspekKeyakinan));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    NIP.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                NIP.requestFocus();
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
        
        ChkInput.setSelected(false);
        isForm();
        jam();
        
        try {
            TANGGALMUNDUR=koneksiDB.TANGGALMUNDUR();
        } catch (Exception e) {
            TANGGALMUNDUR="yes";
        }
        
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnEdukasiPasienKeluarga = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        JK = new widget.TextBox();
        TanggalRegistrasi = new widget.TextBox();
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
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Jam = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Detik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jSeparator2 = new javax.swing.JSeparator();
        Bicara = new widget.ComboBox();
        KeteranganBicara = new widget.TextBox();
        jLabel45 = new widget.Label();
        jLabel99 = new widget.Label();
        jLabel46 = new widget.Label();
        KeteranganBahasa = new widget.TextBox();
        Bahasa = new widget.TextBox();
        jLabel47 = new widget.Label();
        Penerjemah = new widget.ComboBox();
        KeteranganPenerjemah = new widget.TextBox();
        jLabel48 = new widget.Label();
        BahasaIsyarat = new widget.ComboBox();
        jLabel49 = new widget.Label();
        CaraBelajar = new widget.ComboBox();
        jLabel50 = new widget.Label();
        HambatanBelajar = new widget.ComboBox();
        KeteranganHambatanBelajar = new widget.TextBox();
        jLabel51 = new widget.Label();
        KemampuanBelajar = new widget.ComboBox();
        KeteranganKemampuanBelajar = new widget.TextBox();
        jLabel52 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel56 = new widget.Label();
        jLabel57 = new widget.Label();
        PenyakitnyaMerupakan = new widget.ComboBox();
        KeteranganPenyakitnyaMerupakan = new widget.TextBox();
        jLabel58 = new widget.Label();
        KeputusanMemilihLayanan = new widget.ComboBox();
        KeteranganKeputusanMemilihLayanan = new widget.TextBox();
        KeteranganKeyakinanTerhadapHasil = new widget.TextBox();
        KeyakinanTerhadapHasil = new widget.ComboBox();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        AspekKeyakinan = new widget.ComboBox();
        KeteranganAspekKeyakinan = new widget.TextBox();
        jLabel61 = new widget.Label();
        jLabel62 = new widget.Label();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        Pendidikan = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel65 = new widget.Label();
        KesediaanInformasi = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel100 = new widget.Label();
        jLabel67 = new widget.Label();
        jLabel66 = new widget.Label();
        jLabel68 = new widget.Label();
        PenyakitYangDiderita = new widget.ComboBox();
        RencanaTindakan = new widget.ComboBox();
        jLabel69 = new widget.Label();
        jLabel70 = new widget.Label();
        PengobatanProsedur = new widget.ComboBox();
        jLabel71 = new widget.Label();
        HasilLayanan = new widget.ComboBox();
        jLabel72 = new widget.Label();
        jLabel73 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnEdukasiPasienKeluarga.setBackground(new java.awt.Color(255, 255, 254));
        MnEdukasiPasienKeluarga.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnEdukasiPasienKeluarga.setForeground(new java.awt.Color(50, 50, 50));
        MnEdukasiPasienKeluarga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnEdukasiPasienKeluarga.setText("Formulir Edukasi Pasien & Keluarga Terintegrasi");
        MnEdukasiPasienKeluarga.setName("MnEdukasiPasienKeluarga"); // NOI18N
        MnEdukasiPasienKeluarga.setPreferredSize(new java.awt.Dimension(300, 26));
        MnEdukasiPasienKeluarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnEdukasiPasienKeluargaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnEdukasiPasienKeluarga);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Edukasi Pasien & Keluarga Terintegrasi Rawat Jalan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(310, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 506));
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

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 483));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 80, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(84, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "23-04-2024" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(84, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 80, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(178, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(243, 40, 62, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(308, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(373, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(400, 40, 70, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        NIP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NIPKeyPressed(evt);
            }
        });
        FormInput.add(NIP);
        NIP.setBounds(474, 40, 94, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(570, 40, 187, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        btnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(761, 40, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 810, 1);

        Bicara.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Normal", "Gangguan Bicara" }));
        Bicara.setName("Bicara"); // NOI18N
        Bicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BicaraKeyPressed(evt);
            }
        });
        FormInput.add(Bicara);
        Bicara.setBounds(84, 90, 135, 23);

        KeteranganBicara.setFocusTraversalPolicyProvider(true);
        KeteranganBicara.setName("KeteranganBicara"); // NOI18N
        KeteranganBicara.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganBicaraKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganBicara);
        KeteranganBicara.setBounds(222, 90, 180, 23);

        jLabel45.setText(":");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(0, 90, 80, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("A. PENGKAJIAN KEBUTUHAN EDUKASI");
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(10, 70, 290, 23);

        jLabel46.setText("Bahasa Sehari-hari :");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(210, 120, 120, 23);

        KeteranganBahasa.setFocusTraversalPolicyProvider(true);
        KeteranganBahasa.setName("KeteranganBahasa"); // NOI18N
        KeteranganBahasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganBahasaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganBahasa);
        KeteranganBahasa.setBounds(499, 120, 290, 23);

        Bahasa.setEditable(false);
        Bahasa.setFocusTraversalPolicyProvider(true);
        Bahasa.setName("Bahasa"); // NOI18N
        Bahasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BahasaKeyPressed(evt);
            }
        });
        FormInput.add(Bahasa);
        Bahasa.setBounds(334, 120, 162, 23);

        jLabel47.setText("Perlu Penerjemah :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(402, 90, 120, 23);

        Penerjemah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        Penerjemah.setName("Penerjemah"); // NOI18N
        Penerjemah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenerjemahKeyPressed(evt);
            }
        });
        FormInput.add(Penerjemah);
        Penerjemah.setBounds(526, 90, 80, 23);

        KeteranganPenerjemah.setFocusTraversalPolicyProvider(true);
        KeteranganPenerjemah.setName("KeteranganPenerjemah"); // NOI18N
        KeteranganPenerjemah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPenerjemahKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPenerjemah);
        KeteranganPenerjemah.setBounds(609, 90, 180, 23);

        jLabel48.setText(":");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(0, 120, 122, 23);

        BahasaIsyarat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        BahasaIsyarat.setName("BahasaIsyarat"); // NOI18N
        BahasaIsyarat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BahasaIsyaratKeyPressed(evt);
            }
        });
        FormInput.add(BahasaIsyarat);
        BahasaIsyarat.setBounds(126, 120, 80, 23);

        jLabel49.setText(":");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 150, 175, 23);

        CaraBelajar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Menulis", "Audio-Visual/Gambar", "Diskusi", "Simulasi" }));
        CaraBelajar.setName("CaraBelajar"); // NOI18N
        CaraBelajar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CaraBelajarKeyPressed(evt);
            }
        });
        FormInput.add(CaraBelajar);
        CaraBelajar.setBounds(179, 150, 158, 23);

        jLabel50.setText("Hambatan Belajar :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(342, 150, 110, 23);

        HambatanBelajar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Ada", "Takut/Gelisah", "Tidak Tertarik", "Nyeri Tidak Nyaman", "Buta Huruf", "Gangguan Kognitif", "Lain-lain" }));
        HambatanBelajar.setName("HambatanBelajar"); // NOI18N
        HambatanBelajar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HambatanBelajarKeyPressed(evt);
            }
        });
        FormInput.add(HambatanBelajar);
        HambatanBelajar.setBounds(456, 150, 150, 23);

        KeteranganHambatanBelajar.setFocusTraversalPolicyProvider(true);
        KeteranganHambatanBelajar.setName("KeteranganHambatanBelajar"); // NOI18N
        KeteranganHambatanBelajar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganHambatanBelajarKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganHambatanBelajar);
        KeteranganHambatanBelajar.setBounds(609, 150, 180, 23);

        jLabel51.setText(":");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(0, 180, 147, 23);

        KemampuanBelajar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mampu Menerima Informasi", "Tidak Mampu Menerima Informasi" }));
        KemampuanBelajar.setName("KemampuanBelajar"); // NOI18N
        KemampuanBelajar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KemampuanBelajarKeyPressed(evt);
            }
        });
        FormInput.add(KemampuanBelajar);
        KemampuanBelajar.setBounds(151, 180, 220, 23);

        KeteranganKemampuanBelajar.setFocusTraversalPolicyProvider(true);
        KeteranganKemampuanBelajar.setName("KeteranganKemampuanBelajar"); // NOI18N
        KeteranganKemampuanBelajar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKemampuanBelajarKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKemampuanBelajar);
        KeteranganKemampuanBelajar.setBounds(374, 180, 205, 23);

        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel52.setText("Bicara");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(43, 90, 40, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("Bahasa Isyarat");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(43, 120, 90, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Cara Belajar Yang Disukai");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(43, 150, 140, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Kemampuan Belajar");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(43, 180, 110, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Nilai Dan Keyakinan :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(43, 210, 160, 23);

        jLabel57.setText(":");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(0, 230, 200, 23);

        PenyakitnyaMerupakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ujian/Cobaan", "Kutukan", "Lain-lain" }));
        PenyakitnyaMerupakan.setName("PenyakitnyaMerupakan"); // NOI18N
        PenyakitnyaMerupakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitnyaMerupakanKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitnyaMerupakan);
        PenyakitnyaMerupakan.setBounds(204, 230, 120, 23);

        KeteranganPenyakitnyaMerupakan.setFocusTraversalPolicyProvider(true);
        KeteranganPenyakitnyaMerupakan.setName("KeteranganPenyakitnyaMerupakan"); // NOI18N
        KeteranganPenyakitnyaMerupakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPenyakitnyaMerupakanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPenyakitnyaMerupakan);
        KeteranganPenyakitnyaMerupakan.setBounds(327, 230, 462, 23);

        jLabel58.setText(":");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 260, 275, 23);

        KeputusanMemilihLayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sendiri", "Keluarga", "Lain-lain" }));
        KeputusanMemilihLayanan.setName("KeputusanMemilihLayanan"); // NOI18N
        KeputusanMemilihLayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeputusanMemilihLayananKeyPressed(evt);
            }
        });
        FormInput.add(KeputusanMemilihLayanan);
        KeputusanMemilihLayanan.setBounds(279, 260, 95, 23);

        KeteranganKeputusanMemilihLayanan.setFocusTraversalPolicyProvider(true);
        KeteranganKeputusanMemilihLayanan.setName("KeteranganKeputusanMemilihLayanan"); // NOI18N
        KeteranganKeputusanMemilihLayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeputusanMemilihLayananKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKeputusanMemilihLayanan);
        KeteranganKeputusanMemilihLayanan.setBounds(377, 260, 412, 23);

        KeteranganKeyakinanTerhadapHasil.setFocusTraversalPolicyProvider(true);
        KeteranganKeyakinanTerhadapHasil.setName("KeteranganKeyakinanTerhadapHasil"); // NOI18N
        KeteranganKeyakinanTerhadapHasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyakinanTerhadapHasilKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganKeyakinanTerhadapHasil);
        KeteranganKeyakinanTerhadapHasil.setBounds(497, 290, 292, 23);

        KeyakinanTerhadapHasil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pasrah", "Yakin Sembuh Jika Kontrol Teratur", "Yakin Sembuh Jika Minum Obat Teratur", "Lain-lain" }));
        KeyakinanTerhadapHasil.setName("KeyakinanTerhadapHasil"); // NOI18N
        KeyakinanTerhadapHasil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeyakinanTerhadapHasilKeyPressed(evt);
            }
        });
        FormInput.add(KeyakinanTerhadapHasil);
        KeyakinanTerhadapHasil.setBounds(249, 290, 245, 23);

        jLabel59.setText(":");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 290, 245, 23);

        jLabel60.setText(":");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(0, 320, 430, 23);

        AspekKeyakinan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak" }));
        AspekKeyakinan.setName("AspekKeyakinan"); // NOI18N
        AspekKeyakinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AspekKeyakinanKeyPressed(evt);
            }
        });
        FormInput.add(AspekKeyakinan);
        AspekKeyakinan.setBounds(434, 320, 80, 23);

        KeteranganAspekKeyakinan.setFocusTraversalPolicyProvider(true);
        KeteranganAspekKeyakinan.setName("KeteranganAspekKeyakinan"); // NOI18N
        KeteranganAspekKeyakinan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAspekKeyakinanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAspekKeyakinan);
        KeteranganAspekKeyakinan.setBounds(517, 320, 272, 23);

        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel61.setText("Penyakitnya Merupakan");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(79, 230, 130, 23);

        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel62.setText("Keputusan Memilih Layanan Kesehatan");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(79, 260, 200, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("Keyakinan Terhadap Hasil Terapi");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(79, 290, 170, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel64.setText("Aspek Keyakinan Yang Perlu Dipertimbangkan Selama Masa Perawatan");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(79, 320, 360, 23);

        Pendidikan.setEditable(false);
        Pendidikan.setFocusTraversalPolicyProvider(true);
        Pendidikan.setName("Pendidikan"); // NOI18N
        Pendidikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PendidikanKeyPressed(evt);
            }
        });
        FormInput.add(Pendidikan);
        Pendidikan.setBounds(669, 180, 120, 23);

        jLabel9.setText("Pendidikan :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(575, 180, 90, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Kesediaan Menerima Informasi");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(43, 350, 170, 23);

        KesediaanInformasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        KesediaanInformasi.setName("KesediaanInformasi"); // NOI18N
        KesediaanInformasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesediaanInformasiKeyPressed(evt);
            }
        });
        FormInput.add(KesediaanInformasi);
        KesediaanInformasi.setBounds(205, 350, 80, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 380, 810, 1);

        jLabel100.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel100.setText("B. PERENCANAAN KEBUTUHAN EDUKASI");
        jLabel100.setName("jLabel100"); // NOI18N
        FormInput.add(jLabel100);
        jLabel100.setBounds(10, 380, 290, 23);

        jLabel67.setText(":");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(1, 350, 200, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel66.setText("Topik Edukasi Yang Harus Diberikan Kepada Pasien Dan Keluarga Antara Lain :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(43, 400, 410, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel68.setText("Penyakit Yang Diderita Pasien");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(79, 420, 160, 23);

        PenyakitYangDiderita.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PenyakitYangDiderita.setName("PenyakitYangDiderita"); // NOI18N
        PenyakitYangDiderita.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitYangDideritaKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitYangDiderita);
        PenyakitYangDiderita.setBounds(235, 420, 80, 23);

        RencanaTindakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        RencanaTindakan.setName("RencanaTindakan"); // NOI18N
        RencanaTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaTindakanKeyPressed(evt);
            }
        });
        FormInput.add(RencanaTindakan);
        RencanaTindakan.setBounds(709, 450, 80, 23);

        jLabel69.setText("Rencana Tindakan/Terapi :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(550, 450, 155, 23);

        jLabel70.setText("Pengobatan Dan Prosedur Yang Diberikan/Diperlukan :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(415, 420, 290, 23);

        PengobatanProsedur.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PengobatanProsedur.setName("PengobatanProsedur"); // NOI18N
        PengobatanProsedur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PengobatanProsedurKeyPressed(evt);
            }
        });
        FormInput.add(PengobatanProsedur);
        PengobatanProsedur.setBounds(709, 420, 80, 23);

        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel71.setText("Hasil Pelayanan, Termasuk Kejadian Yang Diharapkan & Tidak Diharapkan");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(79, 450, 370, 23);

        HasilLayanan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        HasilLayanan.setName("HasilLayanan"); // NOI18N
        HasilLayanan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilLayananKeyPressed(evt);
            }
        });
        FormInput.add(HasilLayanan);
        HasilLayanan.setBounds(447, 450, 80, 23);

        jLabel72.setText(":");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(0, 420, 231, 23);

        jLabel73.setText(":");
        jLabel73.setName("jLabel73"); // NOI18N
        FormInput.add(jLabel73);
        jLabel73.setBounds(0, 450, 443, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else{
            if(akses.getkode().equals("Admin Utama")){
                simpan();
            }else{
                if(TanggalRegistrasi.getText().equals("")){
                    TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                }
                if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                    simpan();
                }
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,RencanaTindakan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
        ChkInput.setSelected(true);
        isForm(); 
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(akses.getkode().equals("Admin Utama")){
                hapus();
            }else{
                if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString())){
                    if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
                        hapus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Hanya bisa dihapus oleh petugas yang bersangkutan..!!");
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
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(NIP.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NIP,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString())){
                        if(Sequel.cekTanggal48jam(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),Sequel.ambiltanggalsekarang())==true){
                            if(TanggalRegistrasi.getText().equals("")){
                                TanggalRegistrasi.setText(Sequel.cariIsi("select concat(reg_periksa.tgl_registrasi,' ',reg_periksa.jam_reg) from reg_periksa where reg_periksa.no_rawat=?",TNoRw.getText()));
                            }
                            if(Sequel.cekTanggalRegistrasi(TanggalRegistrasi.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem())==true){
                                ganti();
                            }
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Hanya bisa diganti oleh petugas yang bersangkutan..!!");
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
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
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
                bg.close();

                File f;            
                BufferedWriter bw;
                
                pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.R.M.</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>JK</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bicara</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Bicara</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bahasa Sehari-hari</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Bahasa Sehari-hari</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perlu Penerjemah</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Penerjemah</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bahasa Isyarat</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Belajar</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hambatan Belajar</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Hambatan Belajar</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kemampuan Belajar</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kemampuan Belajar</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pendidikan Pasien</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyakitnya Merupakan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Penyakitnya Merupakan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keputusan Memilih Layanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Keputusan Memilih Layanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keyakinan Terhadap Terapi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Keyakinan Terhadap Terapi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Aspek Keyakinan Dipertimbangkan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Aspek Keyakinan Dipertimbangkan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesediaan Menerima Informasi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Topik Edukasi Penyakit Diderita</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Topik Edukasi Rencana Tindakan/Terapi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Topik Edukasi Pengobatan/Prosedur Diperlukan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Topik Edukasi Hasil Pelayanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas</b></td>"+
                                "</tr>"
                            );
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+ 
                                        "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,31).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='4200px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataEdukasiPasienKeluargaRawatJalan.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='4200px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA EDUKASI PASIEN DAN KELUARGA TERINTEGRASI RAWAT JALAN<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.R.M.</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>JK</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bicara</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Bicara</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bahasa Sehari-hari</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Bahasa Sehari-hari</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perlu Penerjemah</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Penerjemah</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bahasa Isyarat</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Belajar</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Hambatan Belajar</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Hambatan Belajar</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kemampuan Belajar</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Kemampuan Belajar</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pendidikan Pasien</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Penyakitnya Merupakan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Penyakitnya Merupakan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keputusan Memilih Layanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Keputusan Memilih Layanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keyakinan Terhadap Terapi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Keyakinan Terhadap Terapi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Aspek Keyakinan Dipertimbangkan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Aspek Keyakinan Dipertimbangkan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kesediaan Menerima Informasi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Topik Edukasi Penyakit Diderita</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Topik Edukasi Rencana Tindakan/Terapi</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Topik Edukasi Pengobatan/Prosedur Diperlukan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Topik Edukasi Hasil Pelayanan</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP</b></td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas</b></td>"+
                                "</tr>"
                            );
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "<tr class='isi'>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+ 
                                        "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,31).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+
                                        "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
                                    "</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='4200px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataEdukasiPasienKeluargaRawatJalan.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='4200px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA EDUKASI PASIEN DAN KELUARGA TERINTEGRASI RAWAT JALAN<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"No.Rawat\";\"No.R.M.\";\"Nama Pasien\";\"Tgl.Lahir\";\"JK\";\"Tanggal\";\"Bicara\";\"Keterangan Bicara\";\"Bahasa Sehari-hari\";\"Keterangan Bahasa Sehari-hari\";\"Perlu Penerjemah\";\"Keterangan Penerjemah\";\"Bahasa Isyarat\";\"Cara Belajar\";\"Hambatan Belajar\";\"Keterangan Hambatan Belajar\";\"Kemampuan Belajar\";\"Keterangan Kemampuan Belajar\";\"Pendidikan Pasien\";\"Penyakitnya Merupakan\";\"Keterangan Penyakitnya Merupakan\";\"Keputusan Memilih Layanan\";\"Keterangan Keputusan Memilih Layanan\";\"Keyakinan Terhadap Terapi\";\"Keterangan Keyakinan Terhadap Terapi\";\"Aspek Keyakinan Dipertimbangkan\";\"Keterangan Aspek Keyakinan Dipertimbangkan\";\"Kesediaan Menerima Informasi\";\"Topik Edukasi Penyakit Diderita\";\"Topik Edukasi Rencana Tindakan/Terapi\";\"Topik Edukasi Pengobatan/Prosedur Diperlukan\";\"Topik Edukasi Hasil Pelayanan\";\"NIP\";\"Petugas\"\n"
                            ); 
                            for (i = 0; i < tabMode.getRowCount(); i++) {
                                htmlContent.append(
                                    "\""+tbObat.getValueAt(i,0).toString()+"\";\""+tbObat.getValueAt(i,1).toString()+"\";\""+tbObat.getValueAt(i,2).toString()+"\";\""+tbObat.getValueAt(i,3).toString()+"\";\""+tbObat.getValueAt(i,4).toString()+"\";\""+tbObat.getValueAt(i,5).toString()+"\";\""+tbObat.getValueAt(i,6).toString()+"\";\""+tbObat.getValueAt(i,7).toString()+"\";\""+tbObat.getValueAt(i,8).toString()+"\";\""+tbObat.getValueAt(i,9).toString()+"\";\""+tbObat.getValueAt(i,10).toString()+"\";\""+tbObat.getValueAt(i,11).toString()+"\";\""+tbObat.getValueAt(i,12).toString()+"\";\""+tbObat.getValueAt(i,13).toString()+"\";\""+tbObat.getValueAt(i,14).toString()+"\";\""+tbObat.getValueAt(i,15).toString()+"\";\""+tbObat.getValueAt(i,16).toString()+"\";\""+tbObat.getValueAt(i,17).toString()+"\";\""+tbObat.getValueAt(i,18).toString()+"\";\""+tbObat.getValueAt(i,19).toString()+"\";\""+tbObat.getValueAt(i,20).toString()+"\";\""+tbObat.getValueAt(i,21).toString()+"\";\""+tbObat.getValueAt(i,22).toString()+"\";\""+tbObat.getValueAt(i,23).toString()+"\";\""+tbObat.getValueAt(i,24).toString()+"\";\""+tbObat.getValueAt(i,25).toString()+"\";\""+tbObat.getValueAt(i,26).toString()+"\";\""+tbObat.getValueAt(i,27).toString()+"\";\""+tbObat.getValueAt(i,28).toString()+"\";\""+tbObat.getValueAt(i,29).toString()+"\";\""+tbObat.getValueAt(i,30).toString()+"\";\""+tbObat.getValueAt(i,31).toString()+"\";\""+tbObat.getValueAt(i,32).toString()+"\";\""+tbObat.getValueAt(i,33).toString()+"\"\n"
                                );
                            }
                            f = new File("DataEdukasiPasienKeluargaRawatJalan.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
                }   
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
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
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
}//GEN-LAST:event_TanggalKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        // Valid.pindah(evt, TNm, BtnSimpan);
}//GEN-LAST:event_TNoRMKeyPressed

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

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void NIPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NIPKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Detik.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            //GCS.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_NIPKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        //Valid.pindah(evt,Detik,RPS);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void MnEdukasiPasienKeluargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnEdukasiPasienKeluargaActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),33).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),32).toString():finger)+"\n"+Tanggal.getSelectedItem());
            Valid.MyReportqry("rptFormulirEdukasiPasienRJ.jasper","report","::[ Formulir Edukasi Pasien & Keluarga Terintegrasi Rawat Jalan ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,edukasi_pasien_keluarga_rj.tanggal,"+
                    "edukasi_pasien_keluarga_rj.bicara,edukasi_pasien_keluarga_rj.keterangan_bicara,bahasa_pasien.nama_bahasa,edukasi_pasien_keluarga_rj.bahasa_sehari,"+
                    "edukasi_pasien_keluarga_rj.perlu_penerjemah,edukasi_pasien_keluarga_rj.keterangan_penerjemah,edukasi_pasien_keluarga_rj.bahasa_isyarat,"+
                    "edukasi_pasien_keluarga_rj.cara_belajar,edukasi_pasien_keluarga_rj.hambatan_belajar,edukasi_pasien_keluarga_rj.keterangan_hambatan_belajar,"+
                    "edukasi_pasien_keluarga_rj.kemampuan_belajar,edukasi_pasien_keluarga_rj.keterangan_kemampuan_belajar,pasien.pnd,"+
                    "edukasi_pasien_keluarga_rj.penyakitnya_merupakan,edukasi_pasien_keluarga_rj.keterangan_penyakitnya_merupakan,edukasi_pasien_keluarga_rj.keputusan_memilih_layanan,"+
                    "edukasi_pasien_keluarga_rj.keterangan_keputusan_memilih_layanan,edukasi_pasien_keluarga_rj.keyakinan_terhadap_terapi,"+
                    "edukasi_pasien_keluarga_rj.keterangan_keyakinan_terhadap_terapi,edukasi_pasien_keluarga_rj.aspek_keyakinan_dipertimbangkan,"+
                    "edukasi_pasien_keluarga_rj.keterangan_aspek_keyakinan_dipertimbangkan,edukasi_pasien_keluarga_rj.kesediaan_menerima_informasi,"+
                    "edukasi_pasien_keluarga_rj.topik_edukasi_penyakit,edukasi_pasien_keluarga_rj.topik_edukasi_rencana_tindakan,edukasi_pasien_keluarga_rj.topik_edukasi_pengobatan,"+
                    "edukasi_pasien_keluarga_rj.topik_edukasi_hasil_layanan,edukasi_pasien_keluarga_rj.nip,petugas.nama "+
                    "from edukasi_pasien_keluarga_rj inner join reg_periksa on edukasi_pasien_keluarga_rj.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join petugas on edukasi_pasien_keluarga_rj.nip=petugas.nip where reg_periksa.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
    }//GEN-LAST:event_MnEdukasiPasienKeluargaActionPerformed

    private void BicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BicaraKeyPressed
       Valid.pindah(evt,btnPetugas,KeteranganBicara);
    }//GEN-LAST:event_BicaraKeyPressed

    private void KeteranganBicaraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganBicaraKeyPressed
        Valid.pindah(evt,Bicara,Penerjemah);
    }//GEN-LAST:event_KeteranganBicaraKeyPressed

    private void KeteranganBahasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganBahasaKeyPressed
        Valid.pindah(evt,BahasaIsyarat,CaraBelajar);
    }//GEN-LAST:event_KeteranganBahasaKeyPressed

    private void BahasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BahasaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BahasaKeyPressed

    private void PenerjemahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenerjemahKeyPressed
        Valid.pindah(evt,KeteranganBicara,KeteranganPenerjemah);
    }//GEN-LAST:event_PenerjemahKeyPressed

    private void KeteranganPenerjemahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPenerjemahKeyPressed
        Valid.pindah(evt,Penerjemah,BahasaIsyarat);
    }//GEN-LAST:event_KeteranganPenerjemahKeyPressed

    private void BahasaIsyaratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BahasaIsyaratKeyPressed
        Valid.pindah(evt,KeteranganPenerjemah,KeteranganBahasa);
    }//GEN-LAST:event_BahasaIsyaratKeyPressed

    private void CaraBelajarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CaraBelajarKeyPressed
        Valid.pindah(evt,KeteranganBahasa,HambatanBelajar);
    }//GEN-LAST:event_CaraBelajarKeyPressed

    private void HambatanBelajarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HambatanBelajarKeyPressed
        Valid.pindah(evt,CaraBelajar,KeteranganHambatanBelajar);
    }//GEN-LAST:event_HambatanBelajarKeyPressed

    private void KeteranganHambatanBelajarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganHambatanBelajarKeyPressed
        Valid.pindah(evt,HambatanBelajar,KemampuanBelajar);
    }//GEN-LAST:event_KeteranganHambatanBelajarKeyPressed

    private void KemampuanBelajarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KemampuanBelajarKeyPressed
        Valid.pindah(evt,KeteranganHambatanBelajar,KeteranganKemampuanBelajar);
    }//GEN-LAST:event_KemampuanBelajarKeyPressed

    private void KeteranganKemampuanBelajarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKemampuanBelajarKeyPressed
        Valid.pindah(evt,KemampuanBelajar,PenyakitnyaMerupakan);
    }//GEN-LAST:event_KeteranganKemampuanBelajarKeyPressed

    private void PenyakitnyaMerupakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitnyaMerupakanKeyPressed
        Valid.pindah(evt,KeteranganKemampuanBelajar,KeteranganPenyakitnyaMerupakan);
    }//GEN-LAST:event_PenyakitnyaMerupakanKeyPressed

    private void KeteranganPenyakitnyaMerupakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPenyakitnyaMerupakanKeyPressed
        Valid.pindah(evt,PenyakitnyaMerupakan,KeputusanMemilihLayanan);
    }//GEN-LAST:event_KeteranganPenyakitnyaMerupakanKeyPressed

    private void KeputusanMemilihLayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeputusanMemilihLayananKeyPressed
        Valid.pindah(evt,KeteranganPenyakitnyaMerupakan,KeteranganKeputusanMemilihLayanan);
    }//GEN-LAST:event_KeputusanMemilihLayananKeyPressed

    private void KeteranganKeputusanMemilihLayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeputusanMemilihLayananKeyPressed
        Valid.pindah(evt,KeputusanMemilihLayanan,KeyakinanTerhadapHasil);
    }//GEN-LAST:event_KeteranganKeputusanMemilihLayananKeyPressed

    private void KeteranganKeyakinanTerhadapHasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyakinanTerhadapHasilKeyPressed
        Valid.pindah(evt,KeyakinanTerhadapHasil,AspekKeyakinan);
    }//GEN-LAST:event_KeteranganKeyakinanTerhadapHasilKeyPressed

    private void KeyakinanTerhadapHasilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeyakinanTerhadapHasilKeyPressed
        Valid.pindah(evt,KeteranganKeputusanMemilihLayanan,KeteranganKeyakinanTerhadapHasil);
    }//GEN-LAST:event_KeyakinanTerhadapHasilKeyPressed

    private void AspekKeyakinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AspekKeyakinanKeyPressed
        Valid.pindah(evt,KeteranganKeyakinanTerhadapHasil,KeteranganAspekKeyakinan);
    }//GEN-LAST:event_AspekKeyakinanKeyPressed

    private void KeteranganAspekKeyakinanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAspekKeyakinanKeyPressed
        Valid.pindah(evt,AspekKeyakinan,KesediaanInformasi);
    }//GEN-LAST:event_KeteranganAspekKeyakinanKeyPressed

    private void PendidikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PendidikanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PendidikanKeyPressed

    private void KesediaanInformasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesediaanInformasiKeyPressed
        Valid.pindah(evt,KeteranganAspekKeyakinan,PenyakitYangDiderita);
    }//GEN-LAST:event_KesediaanInformasiKeyPressed

    private void PenyakitYangDideritaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitYangDideritaKeyPressed
        Valid.pindah(evt,KesediaanInformasi,PengobatanProsedur);
    }//GEN-LAST:event_PenyakitYangDideritaKeyPressed

    private void RencanaTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaTindakanKeyPressed
        Valid.pindah(evt,HasilLayanan,BtnSimpan);
    }//GEN-LAST:event_RencanaTindakanKeyPressed

    private void PengobatanProsedurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PengobatanProsedurKeyPressed
        Valid.pindah(evt,PenyakitYangDiderita,HasilLayanan);
    }//GEN-LAST:event_PengobatanProsedurKeyPressed

    private void HasilLayananKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilLayananKeyPressed
        Valid.pindah(evt,PengobatanProsedur,RencanaTindakan);
    }//GEN-LAST:event_HasilLayananKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMEdukasiPasienKeluargaRawatJalan dialog = new RMEdukasiPasienKeluargaRawatJalan(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AspekKeyakinan;
    private widget.TextBox Bahasa;
    private widget.ComboBox BahasaIsyarat;
    private widget.ComboBox Bicara;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CaraBelajar;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox HambatanBelajar;
    private widget.ComboBox HasilLayanan;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.ComboBox KemampuanBelajar;
    private widget.ComboBox KeputusanMemilihLayanan;
    private widget.ComboBox KesediaanInformasi;
    private widget.TextBox KeteranganAspekKeyakinan;
    private widget.TextBox KeteranganBahasa;
    private widget.TextBox KeteranganBicara;
    private widget.TextBox KeteranganHambatanBelajar;
    private widget.TextBox KeteranganKemampuanBelajar;
    private widget.TextBox KeteranganKeputusanMemilihLayanan;
    private widget.TextBox KeteranganKeyakinanTerhadapHasil;
    private widget.TextBox KeteranganPenerjemah;
    private widget.TextBox KeteranganPenyakitnyaMerupakan;
    private widget.ComboBox KeyakinanTerhadapHasil;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnEdukasiPasienKeluarga;
    private widget.TextBox NIP;
    private widget.TextBox NamaPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pendidikan;
    private widget.ComboBox Penerjemah;
    private widget.ComboBox PengobatanProsedur;
    private widget.ComboBox PenyakitYangDiderita;
    private widget.ComboBox PenyakitnyaMerupakan;
    private widget.ComboBox RencanaTindakan;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TanggalRegistrasi;
    private widget.TextBox TglLahir;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel100;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel71;
    private widget.Label jLabel72;
    private widget.Label jLabel73;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().toString().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,edukasi_pasien_keluarga_rj.tanggal,"+
                    "edukasi_pasien_keluarga_rj.bicara,edukasi_pasien_keluarga_rj.keterangan_bicara,bahasa_pasien.nama_bahasa,edukasi_pasien_keluarga_rj.bahasa_sehari,"+
                    "edukasi_pasien_keluarga_rj.perlu_penerjemah,edukasi_pasien_keluarga_rj.keterangan_penerjemah,edukasi_pasien_keluarga_rj.bahasa_isyarat,"+
                    "edukasi_pasien_keluarga_rj.cara_belajar,edukasi_pasien_keluarga_rj.hambatan_belajar,edukasi_pasien_keluarga_rj.keterangan_hambatan_belajar,"+
                    "edukasi_pasien_keluarga_rj.kemampuan_belajar,edukasi_pasien_keluarga_rj.keterangan_kemampuan_belajar,pasien.pnd,"+
                    "edukasi_pasien_keluarga_rj.penyakitnya_merupakan,edukasi_pasien_keluarga_rj.keterangan_penyakitnya_merupakan,edukasi_pasien_keluarga_rj.keputusan_memilih_layanan,"+
                    "edukasi_pasien_keluarga_rj.keterangan_keputusan_memilih_layanan,edukasi_pasien_keluarga_rj.keyakinan_terhadap_terapi,"+
                    "edukasi_pasien_keluarga_rj.keterangan_keyakinan_terhadap_terapi,edukasi_pasien_keluarga_rj.aspek_keyakinan_dipertimbangkan,"+
                    "edukasi_pasien_keluarga_rj.keterangan_aspek_keyakinan_dipertimbangkan,edukasi_pasien_keluarga_rj.kesediaan_menerima_informasi,"+
                    "edukasi_pasien_keluarga_rj.topik_edukasi_penyakit,edukasi_pasien_keluarga_rj.topik_edukasi_rencana_tindakan,edukasi_pasien_keluarga_rj.topik_edukasi_pengobatan,"+
                    "edukasi_pasien_keluarga_rj.topik_edukasi_hasil_layanan,edukasi_pasien_keluarga_rj.nip,petugas.nama "+
                    "from edukasi_pasien_keluarga_rj inner join reg_periksa on edukasi_pasien_keluarga_rj.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join petugas on edukasi_pasien_keluarga_rj.nip=petugas.nip where "+
                    "edukasi_pasien_keluarga_rj.tanggal between ? and ? order by edukasi_pasien_keluarga_rj.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,edukasi_pasien_keluarga_rj.tanggal,"+
                    "edukasi_pasien_keluarga_rj.bicara,edukasi_pasien_keluarga_rj.keterangan_bicara,bahasa_pasien.nama_bahasa,edukasi_pasien_keluarga_rj.bahasa_sehari,"+
                    "edukasi_pasien_keluarga_rj.perlu_penerjemah,edukasi_pasien_keluarga_rj.keterangan_penerjemah,edukasi_pasien_keluarga_rj.bahasa_isyarat,"+
                    "edukasi_pasien_keluarga_rj.cara_belajar,edukasi_pasien_keluarga_rj.hambatan_belajar,edukasi_pasien_keluarga_rj.keterangan_hambatan_belajar,"+
                    "edukasi_pasien_keluarga_rj.kemampuan_belajar,edukasi_pasien_keluarga_rj.keterangan_kemampuan_belajar,pasien.pnd,"+
                    "edukasi_pasien_keluarga_rj.penyakitnya_merupakan,edukasi_pasien_keluarga_rj.keterangan_penyakitnya_merupakan,edukasi_pasien_keluarga_rj.keputusan_memilih_layanan,"+
                    "edukasi_pasien_keluarga_rj.keterangan_keputusan_memilih_layanan,edukasi_pasien_keluarga_rj.keyakinan_terhadap_terapi,"+
                    "edukasi_pasien_keluarga_rj.keterangan_keyakinan_terhadap_terapi,edukasi_pasien_keluarga_rj.aspek_keyakinan_dipertimbangkan,"+
                    "edukasi_pasien_keluarga_rj.keterangan_aspek_keyakinan_dipertimbangkan,edukasi_pasien_keluarga_rj.kesediaan_menerima_informasi,"+
                    "edukasi_pasien_keluarga_rj.topik_edukasi_penyakit,edukasi_pasien_keluarga_rj.topik_edukasi_rencana_tindakan,edukasi_pasien_keluarga_rj.topik_edukasi_pengobatan,"+
                    "edukasi_pasien_keluarga_rj.topik_edukasi_hasil_layanan,edukasi_pasien_keluarga_rj.nip,petugas.nama "+
                    "from edukasi_pasien_keluarga_rj inner join reg_periksa on edukasi_pasien_keluarga_rj.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "inner join petugas on edukasi_pasien_keluarga_rj.nip=petugas.nip where "+
                    "edukasi_pasien_keluarga_rj.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or edukasi_pasien_keluarga_rj.nip like ? or petugas.nama like ?) "+
                    "order by edukasi_pasien_keluarga_rj.tanggal ");
            }
                
            try {
                if(TCari.getText().toString().trim().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" 00:00:00");
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+"")+" 23:59:59");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }
                
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getDate("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal"),
                        rs.getString("bicara"),rs.getString("keterangan_bicara"),rs.getString("nama_bahasa"),rs.getString("bahasa_sehari"),rs.getString("perlu_penerjemah"),
                        rs.getString("keterangan_penerjemah"),rs.getString("bahasa_isyarat"),rs.getString("cara_belajar"),rs.getString("hambatan_belajar"),
                        rs.getString("keterangan_hambatan_belajar"),rs.getString("kemampuan_belajar"),rs.getString("keterangan_kemampuan_belajar"),rs.getString("pnd"),
                        rs.getString("penyakitnya_merupakan"),rs.getString("keterangan_penyakitnya_merupakan"),rs.getString("keputusan_memilih_layanan"),
                        rs.getString("keterangan_keputusan_memilih_layanan"),rs.getString("keyakinan_terhadap_terapi"),rs.getString("keterangan_keyakinan_terhadap_terapi"),
                        rs.getString("aspek_keyakinan_dipertimbangkan"),rs.getString("keterangan_aspek_keyakinan_dipertimbangkan"),rs.getString("kesediaan_menerima_informasi"),
                        rs.getString("topik_edukasi_penyakit"),rs.getString("topik_edukasi_rencana_tindakan"),rs.getString("topik_edukasi_pengobatan"),
                        rs.getString("topik_edukasi_hasil_layanan"),rs.getString("nip"),rs.getString("nama")
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
        Tanggal.setDate(new Date());
        Bicara.setSelectedIndex(0);
        KeteranganBicara.setText("");
        Penerjemah.setSelectedIndex(0);
        KeteranganPenerjemah.setText("");
        BahasaIsyarat.setSelectedIndex(0);
        KeteranganBahasa.setText("");
        CaraBelajar.setSelectedItem(0);
        HambatanBelajar.setSelectedIndex(0);
        KeteranganHambatanBelajar.setText("");
        KemampuanBelajar.setSelectedIndex(0);
        KeteranganKemampuanBelajar.setText("");
        PenyakitnyaMerupakan.setSelectedIndex(0);
        KeteranganPenyakitnyaMerupakan.setText("");
        KeputusanMemilihLayanan.setSelectedIndex(0);
        KeteranganKeputusanMemilihLayanan.setText("");
        KeyakinanTerhadapHasil.setSelectedIndex(0);
        KeteranganKeyakinanTerhadapHasil.setText("");
        AspekKeyakinan.setSelectedIndex(0);
        KeteranganAspekKeyakinan.setText("");
        KesediaanInformasi.setSelectedIndex(0);
        PenyakitYangDiderita.setSelectedIndex(0);
        PengobatanProsedur.setSelectedIndex(0);
        HasilLayanan.setSelectedIndex(0);
        RencanaTindakan.setSelectedIndex(0);
        Bicara.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Bicara.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            KeteranganBicara.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Bahasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            KeteranganBahasa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Penerjemah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KeteranganPenerjemah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            BahasaIsyarat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            CaraBelajar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            HambatanBelajar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            KeteranganHambatanBelajar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            KemampuanBelajar.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            KeteranganKemampuanBelajar.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Pendidikan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            PenyakitnyaMerupakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KeteranganPenyakitnyaMerupakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            KeputusanMemilihLayanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KeteranganKeputusanMemilihLayanan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            KeyakinanTerhadapHasil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            KeteranganKeyakinanTerhadapHasil.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            AspekKeyakinan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            KeteranganAspekKeyakinan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            KesediaanInformasi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            PenyakitYangDiderita.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            RencanaTindakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            PengobatanProsedur.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            HasilLayanan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            NIP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            NamaPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(14,16));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(17,19));
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }
    
    private void isRawat() {
         try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,"+
                    "bahasa_pasien.nama_bahasa,pasien.pnd,reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join bahasa_pasien on bahasa_pasien.id=pasien.bahasa_pasien "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Pendidikan.setText(rs.getString("pnd"));
                    JK.setText(rs.getString("jk"));
                    Bahasa.setText(rs.getString("nama_bahasa"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
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
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>678){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,506));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }else{
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-175));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getedukasi_pasien_keluarga_rj());
        BtnHapus.setEnabled(akses.getedukasi_pasien_keluarga_rj());
        BtnEdit.setEnabled(akses.getedukasi_pasien_keluarga_rj());
        BtnPrint.setEnabled(akses.getedukasi_pasien_keluarga_rj()); 
        if(akses.getjml2()>=1){
            NIP.setEditable(false);
            btnPetugas.setEnabled(false);
            NIP.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(NIP.getText()));
            if(NamaPetugas.getText().equals("")){
                NIP.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        } 
        
        if(TANGGALMUNDUR.equals("no")){
            if(!akses.getkode().equals("Admin Utama")){
                Tanggal.setEditable(false);
                Tanggal.setEnabled(false);
                ChkKejadian.setEnabled(false);
                Jam.setEnabled(false);
                Menit.setEnabled(false);
                Detik.setEnabled(false);
            }
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
                if(ChkKejadian.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected()==false){
                    nilai_jam =Jam.getSelectedIndex();
                    nilai_menit =Menit.getSelectedIndex();
                    nilai_detik =Detik.getSelectedIndex();
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
                Jam.setSelectedItem(jam);
                Menit.setSelectedItem(menit);
                Detik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1240, taskPerformer).start();
    }

    private void ganti() {
        if(Sequel.mengedittf("edukasi_pasien_keluarga_rj","no_rawat=?","no_rawat=?,tanggal=?,nip=?,bicara=?,keterangan_bicara=?,bahasa_sehari=?,perlu_penerjemah=?,"+
            "keterangan_penerjemah=?,bahasa_isyarat=?,cara_belajar=?,hambatan_belajar=?,keterangan_hambatan_belajar=?,kemampuan_belajar=?,keterangan_kemampuan_belajar=?,"+
            "penyakitnya_merupakan=?,keterangan_penyakitnya_merupakan=?,keputusan_memilih_layanan=?,keterangan_keputusan_memilih_layanan=?,keyakinan_terhadap_terapi=?,"+
            "keterangan_keyakinan_terhadap_terapi=?,aspek_keyakinan_dipertimbangkan=?,keterangan_aspek_keyakinan_dipertimbangkan=?,kesediaan_menerima_informasi=?,"+
            "topik_edukasi_penyakit=?,topik_edukasi_rencana_tindakan=?,topik_edukasi_pengobatan=?,topik_edukasi_hasil_layanan=?",28,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            NIP.getText(),Bicara.getSelectedItem().toString(),KeteranganBicara.getText(),KeteranganBahasa.getText(),Penerjemah.getSelectedItem().toString(),
            KeteranganPenerjemah.getText(),BahasaIsyarat.getSelectedItem().toString(),CaraBelajar.getSelectedItem().toString(),HambatanBelajar.getSelectedItem().toString(),
            KeteranganHambatanBelajar.getText(),KemampuanBelajar.getSelectedItem().toString(),KeteranganKemampuanBelajar.getText(),
            PenyakitnyaMerupakan.getSelectedItem().toString(),KeteranganPenyakitnyaMerupakan.getText(),KeputusanMemilihLayanan.getSelectedItem().toString(),
            KeteranganKeputusanMemilihLayanan.getText(),KeyakinanTerhadapHasil.getSelectedItem().toString(),KeteranganKeyakinanTerhadapHasil.getText(),
            AspekKeyakinan.getSelectedItem().toString(),KeteranganAspekKeyakinan.getText(),KesediaanInformasi.getSelectedItem().toString(),
            PenyakitYangDiderita.getSelectedItem().toString(),RencanaTindakan.getSelectedItem().toString(),PengobatanProsedur.getSelectedItem().toString(),
            HasilLayanan.getSelectedItem().toString(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText().substring(0,1),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(Bicara.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(KeteranganBicara.getText(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(Bahasa.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(KeteranganBahasa.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(Penerjemah.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(KeteranganPenerjemah.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(BahasaIsyarat.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(CaraBelajar.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(HambatanBelajar.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(KeteranganHambatanBelajar.getText(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(KemampuanBelajar.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(KeteranganKemampuanBelajar.getText(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(Pendidikan.getText(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(PenyakitnyaMerupakan.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(KeteranganPenyakitnyaMerupakan.getText(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(KeputusanMemilihLayanan.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(KeteranganKeputusanMemilihLayanan.getText(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(KeyakinanTerhadapHasil.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(KeteranganKeyakinanTerhadapHasil.getText(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(AspekKeyakinan.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(KeteranganAspekKeyakinan.getText(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(KesediaanInformasi.getSelectedItem().toString(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(PenyakitYangDiderita.getSelectedItem().toString(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(RencanaTindakan.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
            tbObat.setValueAt(PengobatanProsedur.getSelectedItem().toString(),tbObat.getSelectedRow(),30);
            tbObat.setValueAt(HasilLayanan.getSelectedItem().toString(),tbObat.getSelectedRow(),31);
            tbObat.setValueAt(NIP.getText(),tbObat.getSelectedRow(),32);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),33);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from edukasi_pasien_keluarga_rj where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void simpan() {
        if(Sequel.menyimpantf("edukasi_pasien_keluarga_rj","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",27,new String[]{
            TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
            NIP.getText(),Bicara.getSelectedItem().toString(),KeteranganBicara.getText(),KeteranganBahasa.getText(),Penerjemah.getSelectedItem().toString(),
            KeteranganPenerjemah.getText(),BahasaIsyarat.getSelectedItem().toString(),CaraBelajar.getSelectedItem().toString(),HambatanBelajar.getSelectedItem().toString(),
            KeteranganHambatanBelajar.getText(),KemampuanBelajar.getSelectedItem().toString(),KeteranganKemampuanBelajar.getText(),
            PenyakitnyaMerupakan.getSelectedItem().toString(),KeteranganPenyakitnyaMerupakan.getText(),KeputusanMemilihLayanan.getSelectedItem().toString(),
            KeteranganKeputusanMemilihLayanan.getText(),KeyakinanTerhadapHasil.getSelectedItem().toString(),KeteranganKeyakinanTerhadapHasil.getText(),
            AspekKeyakinan.getSelectedItem().toString(),KeteranganAspekKeyakinan.getText(),KesediaanInformasi.getSelectedItem().toString(),
            PenyakitYangDiderita.getSelectedItem().toString(),RencanaTindakan.getSelectedItem().toString(),PengobatanProsedur.getSelectedItem().toString(),
            HasilLayanan.getSelectedItem().toString()
        })==true){
            tabMode.addRow(new Object[]{
                TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText().substring(0,1),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                Bicara.getSelectedItem().toString(),KeteranganBicara.getText(),Bahasa.getText(),KeteranganBahasa.getText(),Penerjemah.getSelectedItem().toString(),KeteranganPenerjemah.getText(),BahasaIsyarat.getSelectedItem().toString(),
                CaraBelajar.getSelectedItem().toString(),HambatanBelajar.getSelectedItem().toString(),KeteranganHambatanBelajar.getText(),KemampuanBelajar.getSelectedItem().toString(),KeteranganKemampuanBelajar.getText(),
                Pendidikan.getText(),PenyakitnyaMerupakan.getSelectedItem().toString(),KeteranganPenyakitnyaMerupakan.getText(),KeputusanMemilihLayanan.getSelectedItem().toString(),KeteranganKeputusanMemilihLayanan.getText(),
                KeyakinanTerhadapHasil.getSelectedItem().toString(),KeteranganKeyakinanTerhadapHasil.getText(),AspekKeyakinan.getSelectedItem().toString(),KeteranganAspekKeyakinan.getText(),KesediaanInformasi.getSelectedItem().toString(),
                PenyakitYangDiderita.getSelectedItem().toString(),RencanaTindakan.getSelectedItem().toString(),PengobatanProsedur.getSelectedItem().toString(),HasilLayanan.getSelectedItem().toString(),NIP.getText(),NamaPetugas.getText()
            });
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        } 
    }
    
}

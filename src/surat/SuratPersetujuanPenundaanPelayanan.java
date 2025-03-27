/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package surat;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;


/**
 * 
 * @author windiartohugroho
 */
public final class SuratPersetujuanPenundaanPelayanan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private StringBuilder htmlContent;
    private String finger="",lokasifile="";
    
    public SuratPersetujuanPenundaanPelayanan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Pernyataan","No.Rawat","No.R.M.","Nama Pasien","Umur","J.K.","Tgl.Lahir","Tanggal","Nama Penanggung Jawab","Umur PJ","No.KTP PJ",
            "Alamat Penanggung Jawab","No.Telp/HP PJ","Hubungan","Poli/Ruangan","Dokter Pengirim","Pelayanan Yang Akan Dilakukan", 
            "Penundaan Pelayanan Dikarenakan","Keterangan Penundaan Pelayanan","Alternatif Yang Diberikan","Keterangan Alternatif Yang Diberikan", 
            "NIP","Petugas Pemberi Informasi","Kode Dokter","Dokter Penanggung Jawab"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 25; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(45);
            }else if(i==5){
                column.setPreferredWidth(25);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(160);
            }else if(i==9){
                column.setPreferredWidth(48);
            }else if(i==10){
                column.setPreferredWidth(110);
            }else if(i==11){
                column.setPreferredWidth(160);
            }else if(i==12){
                column.setPreferredWidth(110);
            }else if(i==13){
                column.setPreferredWidth(60);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(180);
            }else if(i==17){
                column.setPreferredWidth(180);
            }else if(i==18){
                column.setPreferredWidth(180);
            }else if(i==19){
                column.setPreferredWidth(190);
            }else if(i==20){
                column.setPreferredWidth(190);
            }else if(i==21){
                column.setPreferredWidth(90);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(90);
            }else if(i==24){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));    
        NIP.setDocument(new batasInput((byte)20).getKata(NIP));  
        NoSurat.setDocument(new batasInput((byte)20).getKata(NoSurat));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        NamaPJ.setDocument(new batasInput((byte)50).getKata(NamaPJ));
        UmurPJ.setDocument(new batasInput((byte)3).getKata(UmurPJ));
        NoKTP.setDocument(new batasInput((int)20).getKata(NoKTP));  
        AlamatPj.setDocument(new batasInput((int)100).getKata(AlamatPj));  
        NoTelpPJ.setDocument(new batasInput((byte)30).getKata(NoTelpPJ));   
        PoliRuangan.setDocument(new batasInput((byte)40).getKata(PoliRuangan));   
        DokterPengirim.setDocument(new batasInput((byte)40).getKata(DokterPengirim));   
        PelayananDilakukan.setDocument(new batasInput((int)120).getKata(PelayananDilakukan));   
        KeteranganAlternatifDiberikan.setDocument(new batasInput((byte)50).getKata(KeteranganAlternatifDiberikan));   
        KeteranganPenundaanPelayananKarena.setDocument(new batasInput((byte)50).getKata(KeteranganPenundaanPelayananKarena));   
        
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
                btnPetugas.requestFocus();
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                   
                    KodeDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NamaDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }  
                btnDokter.requestFocus();
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
        
        ChkAccor.setSelected(false);
        isPhoto();
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML2.setEditable(true);
        LoadHTML2.setEditorKit(kit);
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
        LoadHTML2.setDocument(doc);
        
    }
    
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Jk = new widget.TextBox();
        Umur = new widget.TextBox();
        LoadHTML = new widget.editorpane();
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
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel8 = new widget.Label();
        NamaPJ = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        Hubungan = new widget.ComboBox();
        AlamatPj = new widget.TextBox();
        jLabel17 = new widget.Label();
        LahirPasien = new widget.TextBox();
        jLabel18 = new widget.Label();
        NIP = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel16 = new widget.Label();
        jLabel44 = new widget.Label();
        UmurPJ = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel14 = new widget.Label();
        jLabel3 = new widget.Label();
        NoSurat = new widget.TextBox();
        jLabel15 = new widget.Label();
        NoKTP = new widget.TextBox();
        NoTelpPJ = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        KodeDokter = new widget.TextBox();
        NamaDokter = new widget.TextBox();
        btnDokter = new widget.Button();
        jLabel45 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        PoliRuangan = new widget.TextBox();
        jLabel12 = new widget.Label();
        DokterPengirim = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel26 = new widget.Label();
        PelayananDilakukan = new widget.TextBox();
        jLabel9 = new widget.Label();
        PenundaanPelayananKarena = new widget.ComboBox();
        KeteranganPenundaanPelayananKarena = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel13 = new widget.Label();
        AlternatifDiberikan = new widget.ComboBox();
        KeteranganAlternatifDiberikan = new widget.TextBox();
        jLabel28 = new widget.Label();
        ChkInput = new widget.CekBox();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhoto = new widget.PanelBiasa();
        FormPass3 = new widget.PanelBiasa();
        btnAmbil = new widget.Button();
        BtnRefreshPhoto1 = new widget.Button();
        BtnPrint1 = new widget.Button();
        Scroll5 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Persetujuan Penundaan Pelayanan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbObatKeyReleased(evt);
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

        jLabel19.setText("Tgl. Surat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 325));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 165));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 70, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 136, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(325, 10, 255, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(212, 10, 111, 23);

        jLabel8.setText("Hubungan :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(317, 120, 70, 23);

        NamaPJ.setName("NamaPJ"); // NOI18N
        NamaPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaPJKeyPressed(evt);
            }
        });
        FormInput.add(NamaPJ);
        NamaPJ.setBounds(84, 120, 220, 23);

        jLabel10.setText("Nama :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 120, 80, 23);

        jLabel11.setText("Alamat :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(364, 150, 55, 23);

        Hubungan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Suami", "Istri", "Anak", "Ayah", "Ibu", "Saudara", "Keponakan", "Diri Sendiri" }));
        Hubungan.setName("Hubungan"); // NOI18N
        Hubungan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HubunganKeyPressed(evt);
            }
        });
        FormInput.add(Hubungan);
        Hubungan.setBounds(391, 120, 110, 23);

        AlamatPj.setName("AlamatPj"); // NOI18N
        AlamatPj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatPjKeyPressed(evt);
            }
        });
        FormInput.add(AlamatPj);
        AlamatPj.setBounds(423, 150, 310, 23);

        jLabel17.setText("Tgl.Lahir :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(584, 10, 60, 23);

        LahirPasien.setHighlighter(null);
        LahirPasien.setName("LahirPasien"); // NOI18N
        FormInput.add(LahirPasien);
        LahirPasien.setBounds(648, 10, 85, 23);

        jLabel18.setText("Pemberi Informasi :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(205, 40, 120, 23);

        NIP.setEditable(false);
        NIP.setHighlighter(null);
        NIP.setName("NIP"); // NOI18N
        FormInput.add(NIP);
        NIP.setBounds(329, 40, 120, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        FormInput.add(NamaPetugas);
        NamaPetugas.setBounds(451, 40, 252, 23);

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
        btnPetugas.setBounds(705, 40, 28, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 70, 23);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel44.setText("Th");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(333, 150, 30, 23);

        UmurPJ.setName("UmurPJ"); // NOI18N
        UmurPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UmurPJKeyPressed(evt);
            }
        });
        FormInput.add(UmurPJ);
        UmurPJ.setBounds(290, 150, 40, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-06-2023 17:50:59" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(74, 40, 130, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Penanggung Jawab Pasien :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(17, 100, 154, 23);

        jLabel3.setText("Nomor :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 70, 70, 23);

        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(74, 70, 130, 23);

        jLabel15.setText("No.KTP :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(0, 150, 80, 23);

        NoKTP.setName("NoKTP"); // NOI18N
        NoKTP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKTPKeyPressed(evt);
            }
        });
        FormInput.add(NoKTP);
        NoKTP.setBounds(84, 150, 140, 23);

        NoTelpPJ.setName("NoTelpPJ"); // NOI18N
        NoTelpPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoTelpPJKeyPressed(evt);
            }
        });
        FormInput.add(NoTelpPJ);
        NoTelpPJ.setBounds(593, 120, 140, 23);

        jLabel20.setText("Telp/No.HP :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(519, 120, 70, 23);

        jLabel22.setText("Dokter D.P.J.P :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(205, 70, 120, 23);

        KodeDokter.setEditable(false);
        KodeDokter.setHighlighter(null);
        KodeDokter.setName("KodeDokter"); // NOI18N
        FormInput.add(KodeDokter);
        KodeDokter.setBounds(329, 70, 120, 23);

        NamaDokter.setEditable(false);
        NamaDokter.setName("NamaDokter"); // NOI18N
        FormInput.add(NamaDokter);
        NamaDokter.setBounds(451, 70, 252, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('2');
        btnDokter.setToolTipText("ALt+2");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(705, 70, 28, 23);

        jLabel45.setText("Umur :");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(236, 150, 50, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Poli/Ruangan");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(17, 180, 80, 23);

        jLabel24.setText(":");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(0, 180, 89, 23);

        PoliRuangan.setName("PoliRuangan"); // NOI18N
        PoliRuangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PoliRuanganKeyPressed(evt);
            }
        });
        FormInput.add(PoliRuangan);
        PoliRuangan.setBounds(93, 180, 260, 23);

        jLabel12.setText("Dokter Pengirim :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(369, 180, 100, 23);

        DokterPengirim.setName("DokterPengirim"); // NOI18N
        DokterPengirim.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DokterPengirimKeyPressed(evt);
            }
        });
        FormInput.add(DokterPengirim);
        DokterPengirim.setBounds(473, 180, 260, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Pelayanan Yang Akan Dilakukan");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(17, 210, 170, 23);

        jLabel26.setText(":");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(0, 210, 178, 23);

        PelayananDilakukan.setName("PelayananDilakukan"); // NOI18N
        PelayananDilakukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PelayananDilakukanKeyPressed(evt);
            }
        });
        FormInput.add(PelayananDilakukan);
        PelayananDilakukan.setBounds(182, 210, 551, 23);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("Penundaan Pelayanan Dikarenakan");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(17, 240, 180, 23);

        PenundaanPelayananKarena.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Kerusakan Alat", "Kondisi Umum Pasien", "Penundaan Penjadwalan", "Pemadaman Instalasi Listrik", "Lain-lain" }));
        PenundaanPelayananKarena.setName("PenundaanPelayananKarena"); // NOI18N
        PenundaanPelayananKarena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenundaanPelayananKarenaKeyPressed(evt);
            }
        });
        FormInput.add(PenundaanPelayananKarena);
        PenundaanPelayananKarena.setBounds(198, 240, 190, 23);

        KeteranganPenundaanPelayananKarena.setName("KeteranganPenundaanPelayananKarena"); // NOI18N
        KeteranganPenundaanPelayananKarena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganPenundaanPelayananKarenaKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganPenundaanPelayananKarena);
        KeteranganPenundaanPelayananKarena.setBounds(391, 240, 342, 23);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 240, 194, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Alternatif Yang Diberikan");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(17, 270, 150, 23);

        AlternatifDiberikan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dijadwalkan Ulang", "Dirujuk Ke Layanan Kesehatan Lain", "Dikembalikan Kepada Dokter Pengirim", "Lain-lain" }));
        AlternatifDiberikan.setName("AlternatifDiberikan"); // NOI18N
        AlternatifDiberikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlternatifDiberikanKeyPressed(evt);
            }
        });
        FormInput.add(AlternatifDiberikan);
        AlternatifDiberikan.setBounds(151, 270, 237, 23);

        KeteranganAlternatifDiberikan.setName("KeteranganAlternatifDiberikan"); // NOI18N
        KeteranganAlternatifDiberikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganAlternatifDiberikanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganAlternatifDiberikan);
        KeteranganAlternatifDiberikan.setBounds(391, 270, 342, 23);

        jLabel28.setText(":");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 270, 147, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

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

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

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

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Bukti Pengambilan Persetujuan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass3.setBackground(new java.awt.Color(255, 255, 255));
        FormPass3.setBorder(null);
        FormPass3.setName("FormPass3"); // NOI18N
        FormPass3.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbil.setMnemonic('U');
        btnAmbil.setText("Ambil");
        btnAmbil.setToolTipText("Alt+U");
        btnAmbil.setName("btnAmbil"); // NOI18N
        btnAmbil.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilActionPerformed(evt);
            }
        });
        FormPass3.add(btnAmbil);

        BtnRefreshPhoto1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto1.setMnemonic('U');
        BtnRefreshPhoto1.setText("Refresh");
        BtnRefreshPhoto1.setToolTipText("Alt+U");
        BtnRefreshPhoto1.setName("BtnRefreshPhoto1"); // NOI18N
        BtnRefreshPhoto1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhoto1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnRefreshPhoto1);

        BtnPrint1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnPrint1.setMnemonic('T');
        BtnPrint1.setText("Surat");
        BtnPrint1.setToolTipText("Alt+T");
        BtnPrint1.setName("BtnPrint1"); // NOI18N
        BtnPrint1.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint1ActionPerformed(evt);
            }
        });
        FormPass3.add(BtnPrint1);

        FormPhoto.add(FormPass3, java.awt.BorderLayout.PAGE_END);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML2.setBorder(null);
        LoadHTML2.setName("LoadHTML2"); // NOI18N
        Scroll5.setViewportView(LoadHTML2);

        FormPhoto.add(Scroll5, java.awt.BorderLayout.CENTER);

        PanelAccor.add(FormPhoto, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelAccor, java.awt.BorderLayout.EAST);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);
        internalFrame1.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NamaPJ.getText().trim().equals("")){
            Valid.textKosong(NamaPJ,"Nama Penanggung Jawab");
        }else if(AlamatPj.getText().trim().equals("")){
            Valid.textKosong(AlamatPj,"Alamat Penanggung Jawab");
        }else if(UmurPJ.getText().trim().equals("")){
            Valid.textKosong(UmurPJ,"Umur Penanggung Jawab");
        }else if(NoTelpPJ.getText().trim().equals("")){
            Valid.textKosong(NoTelpPJ,"Nomor Telp/HP Penanggung Jawab");
        }else if(NoKTP.getText().trim().equals("")){
            Valid.textKosong(NoKTP,"Nomor KTP Penanggung Jawab");
        }else if(NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"Petugas");
        }else if(NamaDokter.getText().trim().equals("")){
            Valid.textKosong(btnDokter,"Dokter Penanggung Jawab");
        }else if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Persetujuan");
        }else{
            if(Sequel.menyimpantf("persetujuan_penundaan_pelayanan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",18,new String[]{
                    NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                    NamaPJ.getText(),UmurPJ.getText(),NoKTP.getText(),AlamatPj.getText(),NoTelpPJ.getText(),Hubungan.getSelectedItem().toString(),PoliRuangan.getText(),
                    DokterPengirim.getText(),PelayananDilakukan.getText(),PenundaanPelayananKarena.getSelectedItem().toString(),KeteranganPenundaanPelayananKarena.getText(),
                    AlternatifDiberikan.getSelectedItem().toString(),KeteranganAlternatifDiberikan.getText(),NIP.getText(),KodeDokter.getText()
                })==true){
                tabMode.addRow(new Object[]{
                    NoSurat.getText(),TNoRw.getText(),TNoRM.getText(),TPasien.getText(),Umur.getText(),Jk.getText(),LahirPasien.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                    NamaPJ.getText(),UmurPJ.getText(),NoKTP.getText(),AlamatPj.getText(),NoTelpPJ.getText(),Hubungan.getSelectedItem().toString(),PoliRuangan.getText(),DokterPengirim.getText(),PelayananDilakukan.getText(), 
                    PenundaanPelayananKarena.getSelectedItem().toString(),KeteranganPenundaanPelayananKarena.getText(),AlternatifDiberikan.getSelectedItem().toString(),KeteranganAlternatifDiberikan.getText(),NIP.getText(),
                    NamaPetugas.getText(),KodeDokter.getText(),NamaDokter.getText()
                });
                LCount.setText(""+tabMode.getRowCount());
                emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KeteranganAlternatifDiberikan,BtnBatal);
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
                if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString())){
                    hapus();
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
            Valid.textKosong(TNoRw,"Pasien");
        }else if(NamaPJ.getText().trim().equals("")){
            Valid.textKosong(NamaPJ,"Nama Penanggung Jawab");
        }else if(AlamatPj.getText().trim().equals("")){
            Valid.textKosong(AlamatPj,"Alamat Penanggung Jawab");
        }else if(UmurPJ.getText().trim().equals("")){
            Valid.textKosong(UmurPJ,"Tempat Lahir");
        }else if(NoTelpPJ.getText().trim().equals("")){
            Valid.textKosong(NoTelpPJ,"Nomor Telp");
        }else if(NoKTP.getText().trim().equals("")){
            Valid.textKosong(NoKTP,"Nomor KTP");
        }else if(NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(NamaPetugas,"Petugas");
        }else if(NoSurat.getText().trim().equals("")){
            Valid.textKosong(NoSurat,"No.Pernyataan");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(NIP.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString())){
                        ganti();
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
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Pernyataan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.R.M.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Umur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Penanggung Jawab</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Umur PJ</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.KTP PJ</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Alamat Penanggung Jawab</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Telp/HP PJ</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Hubungan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Poli/Ruangan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dokter Pengirim</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Pelayanan Yang Akan Dilakukan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Penundaan Pelayanan Dikarenakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Penundaan Pelayanan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Alternatif Yang Diberikan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Keterangan Alternatif Yang Diberikan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Pemberi Informasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Dokter Penanggung Jawab</b></td>"+
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
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='2500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

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

                File f = new File("DataPersetujuanPenundaanPelayanan.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='2500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PERSETUJUAN PENUNDAAN PELAYANAN<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
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
   
                                  
    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            try {
                isPhoto();
                panggilPhoto();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
       isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void tbObatKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbObatKeyReleased

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,Tanggal,btnDokter);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void AlamatPjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatPjKeyPressed
        Valid.pindah(evt,UmurPJ,PoliRuangan);
    }//GEN-LAST:event_AlamatPjKeyPressed

    private void HubunganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HubunganKeyPressed
        Valid.pindah(evt,NamaPJ,NoTelpPJ);
    }//GEN-LAST:event_HubunganKeyPressed

    private void NamaPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaPJKeyPressed
        Valid.pindah(evt,btnDokter,Hubungan);
    }//GEN-LAST:event_NamaPJKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
    // Valid.pindah(evt, TNm, BtnSimpan);
    }//GEN-LAST:event_TNoRMKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
    }//GEN-LAST:event_TPasienKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
        }else{
            Valid.pindah(evt,TCari,Tanggal);
        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah2(evt,TCari,btnPetugas);
    }//GEN-LAST:event_TanggalKeyPressed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt,btnPetugas,btnDokter);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void NoKTPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKTPKeyPressed
        Valid.pindah(evt,NoTelpPJ,UmurPJ);
    }//GEN-LAST:event_NoKTPKeyPressed

    private void UmurPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UmurPJKeyPressed
        Valid.pindah(evt,NoKTP,AlamatPj);
    }//GEN-LAST:event_UmurPJKeyPressed

    private void NoTelpPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoTelpPJKeyPressed
        Valid.pindah(evt,Hubungan,NoKTP);
    }//GEN-LAST:event_NoTelpPJKeyPressed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbObat.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pernyataan..!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(tbObat.getSelectedRow()>-1){
                Sequel.queryu("delete from antripenundaanpelayanan");
                Sequel.queryu("insert into antripenundaanpelayanan values('"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"','"+tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()+"')");
                Sequel.queryu("delete from bukti_persetujuan_penundaan_pelayanan where no_surat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'");
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_btnAmbilActionPerformed

    private void BtnRefreshPhoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhoto1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            panggilPhoto();
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih No.Pernyataan terlebih dahulu..!!");
        }
    }//GEN-LAST:event_BtnRefreshPhoto1ActionPerformed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        Valid.pindah(evt,btnPetugas,NamaPJ);
    }//GEN-LAST:event_btnDokterKeyPressed

    private void PoliRuanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PoliRuanganKeyPressed
        Valid.pindah(evt,AlamatPj,DokterPengirim);
    }//GEN-LAST:event_PoliRuanganKeyPressed

    private void DokterPengirimKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DokterPengirimKeyPressed
        Valid.pindah(evt,PoliRuangan,PelayananDilakukan);
    }//GEN-LAST:event_DokterPengirimKeyPressed

    private void PelayananDilakukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PelayananDilakukanKeyPressed
        Valid.pindah(evt,DokterPengirim,PenundaanPelayananKarena);
    }//GEN-LAST:event_PelayananDilakukanKeyPressed

    private void PenundaanPelayananKarenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenundaanPelayananKarenaKeyPressed
        Valid.pindah(evt,PelayananDilakukan,KeteranganPenundaanPelayananKarena);
    }//GEN-LAST:event_PenundaanPelayananKarenaKeyPressed

    private void KeteranganPenundaanPelayananKarenaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganPenundaanPelayananKarenaKeyPressed
        Valid.pindah(evt,PenundaanPelayananKarena,AlternatifDiberikan);
    }//GEN-LAST:event_KeteranganPenundaanPelayananKarenaKeyPressed

    private void AlternatifDiberikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlternatifDiberikanKeyPressed
        Valid.pindah(evt,KeteranganPenundaanPelayananKarena,KeteranganAlternatifDiberikan);
    }//GEN-LAST:event_AlternatifDiberikanKeyPressed

    private void KeteranganAlternatifDiberikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganAlternatifDiberikanKeyPressed
        Valid.pindah(evt,AlternatifDiberikan,BtnSimpan);
    }//GEN-LAST:event_KeteranganAlternatifDiberikanKeyPressed

    private void BtnPrint1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint1ActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(lokasifile.equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan ambil photo bukti persetujuan penundaan pelayanan terlebih dahulu..!!!!");
            }else{
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                param.put("photo","http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penundaanpelayanan/"+lokasifile);
                finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
                param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),22).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),21).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString())+" "+tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(11,19));
                Valid.MyReportqry("rptSuratPenundaanPelayanan.jasper","report","::[ Surat Penundaan Pelayanan ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,pasien.pekerjaan,pasien.tmp_lahir,reg_periksa.umurdaftar,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.sttsumur,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab,', ',propinsi.nm_prop) as alamat_pasien,persetujuan_penundaan_pelayanan.no_surat,persetujuan_penundaan_pelayanan.tanggal,persetujuan_penundaan_pelayanan.nama_pj,"+
		    "persetujuan_penundaan_pelayanan.umur_pj,persetujuan_penundaan_pelayanan.no_ktppj,persetujuan_penundaan_pelayanan.alamatpj,persetujuan_penundaan_pelayanan.no_telppj,persetujuan_penundaan_pelayanan.hubungan,persetujuan_penundaan_pelayanan.ruang,persetujuan_penundaan_pelayanan.dokter_pengirim,persetujuan_penundaan_pelayanan.pelayanan_dilakukan,persetujuan_penundaan_pelayanan.ditunda_karena,persetujuan_penundaan_pelayanan.keterangan_ditunda,"+
                    "persetujuan_penundaan_pelayanan.alternatif_diberikan,persetujuan_penundaan_pelayanan.keterangan_alternatif_diberikan,persetujuan_penundaan_pelayanan.nip,petugas.nama,persetujuan_penundaan_pelayanan.kd_dokter,dokter.nm_dokter from persetujuan_penundaan_pelayanan inner join reg_periksa on persetujuan_penundaan_pelayanan.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
		    "inner join petugas on persetujuan_penundaan_pelayanan.nip=petugas.nip inner join dokter on persetujuan_penundaan_pelayanan.kd_dokter=dokter.kd_dokter inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab inner join propinsi on pasien.kd_prop=propinsi.kd_prop where persetujuan_penundaan_pelayanan.no_surat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data terlebih dahulu..!!!!");
        }
    }//GEN-LAST:event_BtnPrint1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SuratPersetujuanPenundaanPelayanan dialog = new SuratPersetujuanPenundaanPelayanan(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlamatPj;
    private widget.ComboBox AlternatifDiberikan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint1;
    private widget.Button BtnRefreshPhoto1;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkAccor;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DokterPengirim;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormPass3;
    private widget.PanelBiasa FormPhoto;
    private widget.ComboBox Hubungan;
    private widget.TextBox Jk;
    private widget.TextBox KeteranganAlternatifDiberikan;
    private widget.TextBox KeteranganPenundaanPelayananKarena;
    private widget.TextBox KodeDokter;
    private widget.Label LCount;
    private widget.TextBox LahirPasien;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML2;
    private widget.TextBox NIP;
    private widget.TextBox NamaDokter;
    private widget.TextBox NamaPJ;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NoKTP;
    private widget.TextBox NoSurat;
    private widget.TextBox NoTelpPJ;
    private widget.PanelBiasa PanelAccor;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox PelayananDilakukan;
    private widget.ComboBox PenundaanPelayananKarena;
    private widget.TextBox PoliRuangan;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox Umur;
    private widget.TextBox UmurPJ;
    private widget.Button btnAmbil;
    private widget.Button btnDokter;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select persetujuan_penundaan_pelayanan.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,persetujuan_penundaan_pelayanan.tanggal,persetujuan_penundaan_pelayanan.nama_pj,"+
                    "persetujuan_penundaan_pelayanan.umur_pj,persetujuan_penundaan_pelayanan.no_ktppj,persetujuan_penundaan_pelayanan.alamatpj,"+
                    "persetujuan_penundaan_pelayanan.no_telppj,persetujuan_penundaan_pelayanan.hubungan,persetujuan_penundaan_pelayanan.ruang,"+
                    "persetujuan_penundaan_pelayanan.dokter_pengirim,persetujuan_penundaan_pelayanan.pelayanan_dilakukan,persetujuan_penundaan_pelayanan.ditunda_karena,"+
                    "persetujuan_penundaan_pelayanan.keterangan_ditunda,persetujuan_penundaan_pelayanan.alternatif_diberikan,"+
                    "persetujuan_penundaan_pelayanan.keterangan_alternatif_diberikan,persetujuan_penundaan_pelayanan.nip,petugas.nama,"+
                    "persetujuan_penundaan_pelayanan.kd_dokter,dokter.nm_dokter from persetujuan_penundaan_pelayanan "+
                    "inner join reg_periksa on persetujuan_penundaan_pelayanan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on persetujuan_penundaan_pelayanan.nip=petugas.nip "+
                    "inner join dokter on persetujuan_penundaan_pelayanan.kd_dokter=dokter.kd_dokter where "+
                    "persetujuan_penundaan_pelayanan.tanggal between ? and ? order by persetujuan_penundaan_pelayanan.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                    "select persetujuan_penundaan_pelayanan.no_surat,reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,"+
                    "reg_periksa.sttsumur,pasien.jk,pasien.tgl_lahir,persetujuan_penundaan_pelayanan.tanggal,persetujuan_penundaan_pelayanan.nama_pj,"+
                    "persetujuan_penundaan_pelayanan.umur_pj,persetujuan_penundaan_pelayanan.no_ktppj,persetujuan_penundaan_pelayanan.alamatpj,"+
                    "persetujuan_penundaan_pelayanan.no_telppj,persetujuan_penundaan_pelayanan.hubungan,persetujuan_penundaan_pelayanan.ruang,"+
                    "persetujuan_penundaan_pelayanan.dokter_pengirim,persetujuan_penundaan_pelayanan.pelayanan_dilakukan,persetujuan_penundaan_pelayanan.ditunda_karena,"+
                    "persetujuan_penundaan_pelayanan.keterangan_ditunda,persetujuan_penundaan_pelayanan.alternatif_diberikan,"+
                    "persetujuan_penundaan_pelayanan.keterangan_alternatif_diberikan,persetujuan_penundaan_pelayanan.nip,petugas.nama,"+
                    "persetujuan_penundaan_pelayanan.kd_dokter,dokter.nm_dokter from persetujuan_penundaan_pelayanan "+
                    "inner join reg_periksa on persetujuan_penundaan_pelayanan.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on persetujuan_penundaan_pelayanan.nip=petugas.nip "+
                    "inner join dokter on persetujuan_penundaan_pelayanan.kd_dokter=dokter.kd_dokter where "+
                    "persetujuan_penundaan_pelayanan.tanggal between ? and ? and "+
                    "(reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                    "persetujuan_penundaan_pelayanan.alamatpj like ? or persetujuan_penundaan_pelayanan.nama_pj like ? or "+
                    "persetujuan_penundaan_pelayanan.nip like ? or petugas.nama like ?) "+
                    "order by persetujuan_penundaan_pelayanan.tanggal");
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
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                }
                  
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_surat"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),rs.getString("jk"),rs.getString("tgl_lahir"),
                        rs.getString("tanggal"),rs.getString("nama_pj"),rs.getString("umur_pj"),rs.getString("no_ktppj"),
                        rs.getString("alamatpj"),rs.getString("no_telppj"),rs.getString("hubungan"),rs.getString("ruang"),
                        rs.getString("dokter_pengirim"),rs.getString("pelayanan_dilakukan"),rs.getString("ditunda_karena"),
                        rs.getString("keterangan_ditunda"),rs.getString("alternatif_diberikan"),rs.getString("keterangan_alternatif_diberikan"),
                        rs.getString("nip"),rs.getString("nama"),rs.getString("kd_dokter"),rs.getString("nm_dokter")
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
        KodeDokter.setText("");
        NamaDokter.setText("");
        NamaPJ.setText("");
        Hubungan.setSelectedIndex(0);
        NoTelpPJ.setText("");
        NoKTP.setText("");
        UmurPJ.setText("");
        AlamatPj.setText("");
        PoliRuangan.setText("");
        DokterPengirim.setText("");
        PelayananDilakukan.setText("");
        PenundaanPelayananKarena.setSelectedIndex(0);
        KeteranganPenundaanPelayananKarena.setText("");
        AlternatifDiberikan.setSelectedIndex(0);
        KeteranganAlternatifDiberikan.setText("");
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(persetujuan_penundaan_pelayanan.no_surat,3),signed)),0) from persetujuan_penundaan_pelayanan where persetujuan_penundaan_pelayanan.tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "PPP"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoSurat);
        btnDokter.requestFocus();
    }

 
    private void getData() {
         if(tbObat.getSelectedRow()!= -1){
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            LahirPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            NamaPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            UmurPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            NoKTP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            AlamatPj.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            NoTelpPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Hubungan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            PoliRuangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            DokterPengirim.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            PelayananDilakukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            PenundaanPelayananKarena.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KeteranganPenundaanPelayananKarena.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            AlternatifDiberikan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KeteranganAlternatifDiberikan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            KodeDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            NamaDokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi, "+
                    "reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.tgl_lahir from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    LahirPasien.setText(rs.getString("tgl_lahir"));
                    Umur.setText(rs.getString("umurdaftar")+" "+rs.getString("sttsumur"));
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
    
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,325));
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
        BtnSimpan.setEnabled(akses.getpersetujuan_penundaan_pelayanan());
        BtnHapus.setEnabled(akses.getpersetujuan_penundaan_pelayanan());
        BtnEdit.setEnabled(akses.getpersetujuan_penundaan_pelayanan());
        BtnPrint.setEnabled(akses.getpersetujuan_penundaan_pelayanan()); 
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
    }
  
    private void ganti() {
        if(Sequel.mengedittf("persetujuan_penundaan_pelayanan","no_surat=?","no_surat=?,no_rawat=?,tanggal=?,nama_pj=?,umur_pj=?,no_ktppj=?,alamatpj=?,no_telppj=?,hubungan=?,ruang=?,"+
            "dokter_pengirim=?,pelayanan_dilakukan=?,ditunda_karena=?,keterangan_ditunda=?,alternatif_diberikan=?,keterangan_alternatif_diberikan=?,nip=?,kd_dokter=?",19,new String[]{
            NoSurat.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
            NamaPJ.getText(),UmurPJ.getText(),NoKTP.getText(),AlamatPj.getText(),NoTelpPJ.getText(),Hubungan.getSelectedItem().toString(),PoliRuangan.getText(),
            DokterPengirim.getText(),PelayananDilakukan.getText(),PenundaanPelayananKarena.getSelectedItem().toString(),KeteranganPenundaanPelayananKarena.getText(),
            AlternatifDiberikan.getSelectedItem().toString(),KeteranganAlternatifDiberikan.getText(),NIP.getText(),KodeDokter.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tbObat.setValueAt(NoSurat.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(Umur.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Jk.getText(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(LahirPasien.getText(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(NamaPJ.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(UmurPJ.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(NoKTP.getText(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(AlamatPj.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(NoTelpPJ.getText(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(Hubungan.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(PoliRuangan.getText(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(DokterPengirim.getText(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(PelayananDilakukan.getText(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt( PenundaanPelayananKarena.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(KeteranganPenundaanPelayananKarena.getText(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(AlternatifDiberikan.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(KeteranganAlternatifDiberikan.getText(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(NIP.getText(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(KodeDokter.getText(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(NamaDokter.getText(),tbObat.getSelectedRow(),24);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from persetujuan_penundaan_pelayanan where no_surat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(480,HEIGHT));
            FormPhoto.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            FormPhoto.setVisible(false);  
            ChkAccor.setVisible(true);
        }
    }

    private void panggilPhoto() {
        if(FormPhoto.isVisible()==true){
            lokasifile="";
            try {
                ps=koneksi.prepareStatement("select bukti_persetujuan_penundaan_pelayanan.photo from bukti_persetujuan_penundaan_pelayanan where bukti_persetujuan_penundaan_pelayanan.no_surat=?");
                try {
                    ps.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                            lokasifile="";
                            LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }else{
                            lokasifile=rs.getString("photo");
                            LoadHTML2.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penundaanpelayanan/"+rs.getString("photo")+"' alt='photo' width='500' height='500'/></center></body></html>");
                        }  
                    }else{
                        lokasifile="";
                        LoadHTML2.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                    }
                } catch (Exception e) {
                    lokasifile="";
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
    }
}




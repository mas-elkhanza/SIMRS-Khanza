/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgDataSkriningGiziLanjut.java
 * Kontribusi Haris Rochmatullah RS Bhayangkara Nganjuk
 * Created on 11 November 2020, 20:19:56
 */

package inventory;

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
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class InventoryTelaahResep extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private StringBuilder htmlContent;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public InventoryTelaahResep(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Resep","Tanggal","Jam","No.Rawat","No.R.M.","Nama Pasien","Umur","J.K.","Tgl.Lahir","Kode Dokter","Nama Dokter",
            "Status","T.R.Tepat Iden Pasien","Ket T.R.Tepat Iden Pasien","T.R.Tepat Obat","Ket T.R.Tepat Obat",
            "T.R.Tepat Dosis","Ket T.R.Tepat Dosis","T.R.Tepat Cara Pemberian","Ket T.R.Tepat Cara Pemberian",
            "T.R.Tepat Waktu Pemberian","Ket T.R.Tepat Waktu Pemberian","T.R.Ada/Tidak Duplikasi","Ket T.R.Ada/Tidak Duplikasi",
            "T.R.Interaksi Obat","Ket T.R.Interaksi Obat","T.R.Kontra Indikasi","Ket T.R.Kontra Indikasi","T.O.Tepat Pasien",
            "T.O.Tepat Obat","T.O.Tepat Dosis","T.O.Tepat Cara Pemberian","T.O.Tepat Waktu Pemberian","NIP","Petugas Farmasi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 35; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(85);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(55);
            }else if(i==3){
                column.setPreferredWidth(105);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(50);
            }else if(i==7){
                column.setPreferredWidth(25);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(45);
            }else if(i==12){
                column.setPreferredWidth(115);
            }else if(i==13){
                column.setPreferredWidth(134);
            }else if(i==14){
                column.setPreferredWidth(81);
            }else if(i==15){
                column.setPreferredWidth(100);
            }else if(i==16){
                column.setPreferredWidth(84);
            }else if(i==17){
                column.setPreferredWidth(103);
            }else if(i==18){
                column.setPreferredWidth(135);
            }else if(i==19){
                column.setPreferredWidth(154);
            }else if(i==20){
                column.setPreferredWidth(144);
            }else if(i==21){
                column.setPreferredWidth(163);
            }else if(i==22){
                column.setPreferredWidth(123);
            }else if(i==23){
                column.setPreferredWidth(141);
            }else if(i==24){
                column.setPreferredWidth(97);
            }else if(i==25){
                column.setPreferredWidth(115);
            }else if(i==26){
                column.setPreferredWidth(99);
            }else if(i==27){
                column.setPreferredWidth(118);
            }else if(i==28){
                column.setPreferredWidth(91);
            }else if(i==29){
                column.setPreferredWidth(82);
            }else if(i==30){
                column.setPreferredWidth(84);
            }else if(i==31){
                column.setPreferredWidth(136);
            }else if(i==32){
                column.setPreferredWidth(144);
            }else if(i==33){
                column.setPreferredWidth(80);
            }else if(i==34){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        Nip.setDocument(new batasInput((byte)20).getKata(Nip));
        KetResepTepatIdetifikasiPasien.setDocument(new batasInput((byte)30).getKata(KetResepTepatIdetifikasiPasien));
        KetResepTepatObat.setDocument(new batasInput((byte)30).getKata(KetResepTepatObat));
        KetResepTepatDosis.setDocument(new batasInput((byte)30).getKata(KetResepTepatDosis));
        KetResepTepatCaraPemberian.setDocument(new batasInput((byte)30).getKata(KetResepTepatCaraPemberian));
        KetResepTepatWaktuPemberian.setDocument(new batasInput((byte)30).getKata(KetResepTepatWaktuPemberian));
        KetResepTidakDuplikasiObat.setDocument(new batasInput((byte)30).getKata(KetResepTidakDuplikasiObat));
        KetResepInteraksiObat.setDocument(new batasInput((byte)30).getKata(KetResepInteraksiObat));
        KetResepKontraIndikasiObat.setDocument(new batasInput((byte)30).getKata(KetResepKontraIndikasiObat));
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
                    Nip.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                Nip.requestFocus();
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

        internalFrame1 = new widget.InternalFrame();
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
        TabData = new javax.swing.JTabbedPane();
        FormValidasi1 = new widget.PanelBiasa();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel18 = new widget.Label();
        Nip = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Alamat = new widget.TextBox();
        jLabel14 = new widget.Label();
        ResepTepatIdentifikasiPasien = new widget.ComboBox();
        jLabel17 = new widget.Label();
        ResepTepatObat = new widget.ComboBox();
        KetResepTepatIdetifikasiPasien = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel22 = new widget.Label();
        KetResepTepatObat = new widget.TextBox();
        jLabel23 = new widget.Label();
        ResepTepatDosis = new widget.ComboBox();
        jLabel26 = new widget.Label();
        KetResepTepatDosis = new widget.TextBox();
        jLabel28 = new widget.Label();
        jLabel29 = new widget.Label();
        JK = new widget.TextBox();
        jLabel24 = new widget.Label();
        ResepTepatCaraPemberian = new widget.ComboBox();
        jLabel27 = new widget.Label();
        KetResepTepatCaraPemberian = new widget.TextBox();
        ResepTepatWaktuPemberian = new widget.ComboBox();
        ResepTidakDuplikasiObat = new widget.ComboBox();
        ResepInteraksiObat = new widget.ComboBox();
        ResepKontraIndikasiObat = new widget.ComboBox();
        KetResepTepatWaktuPemberian = new widget.TextBox();
        KetResepTidakDuplikasiObat = new widget.TextBox();
        KetResepInteraksiObat = new widget.TextBox();
        KetResepKontraIndikasiObat = new widget.TextBox();
        jLabel25 = new widget.Label();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel15 = new widget.Label();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        ObatTepatPasien = new widget.ComboBox();
        ObatTepatObat = new widget.ComboBox();
        jLabel38 = new widget.Label();
        ObatTepatDosis = new widget.ComboBox();
        jLabel39 = new widget.Label();
        ObatTepatCaraPemberian = new widget.ComboBox();
        jLabel40 = new widget.Label();
        ObatTepatWaktuPemberian = new widget.ComboBox();
        jLabel41 = new widget.Label();
        NoResep = new widget.TextBox();
        jLabel42 = new widget.Label();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        Scroll5 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Telaah Resep & Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(90, 23));
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "14-02-2022" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "14-02-2022" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(340, 23));
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

        TabData.setBackground(new java.awt.Color(254, 255, 254));
        TabData.setForeground(new java.awt.Color(50, 50, 50));
        TabData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabData.setName("TabData"); // NOI18N
        TabData.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabDataMouseClicked(evt);
            }
        });

        FormValidasi1.setBackground(new java.awt.Color(255, 255, 255));
        FormValidasi1.setBorder(null);
        FormValidasi1.setName("FormValidasi1"); // NOI18N
        FormValidasi1.setPreferredSize(new java.awt.Dimension(115, 73));
        FormValidasi1.setLayout(new java.awt.BorderLayout(1, 1));

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 354));
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

        Scroll1.setBorder(null);
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(452, 200));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 332));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 75, 23);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(336, 10, 285, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(222, 10, 112, 23);

        jLabel16.setText("Alamat :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 75, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(410, 40, 60, 23);

        Nip.setEditable(false);
        Nip.setHighlighter(null);
        Nip.setName("Nip"); // NOI18N
        Nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NipKeyPressed(evt);
            }
        });
        FormInput.add(Nip);
        Nip.setBounds(474, 40, 94, 23);

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

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Alamat.setEditable(false);
        Alamat.setFocusTraversalPolicyProvider(true);
        Alamat.setName("Alamat"); // NOI18N
        FormInput.add(Alamat);
        Alamat.setBounds(79, 40, 255, 23);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("1. Tepat Identifikasi Pasien");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(50, 90, 140, 23);

        ResepTepatIdentifikasiPasien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ResepTepatIdentifikasiPasien.setName("ResepTepatIdentifikasiPasien"); // NOI18N
        ResepTepatIdentifikasiPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResepTepatIdentifikasiPasienKeyPressed(evt);
            }
        });
        FormInput.add(ResepTepatIdentifikasiPasien);
        ResepTepatIdentifikasiPasien.setBounds(189, 90, 80, 23);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel17.setText("2. Tepat Obat");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(50, 150, 140, 23);

        ResepTepatObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ResepTepatObat.setName("ResepTepatObat"); // NOI18N
        ResepTepatObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResepTepatObatKeyPressed(evt);
            }
        });
        FormInput.add(ResepTepatObat);
        ResepTepatObat.setBounds(189, 150, 80, 23);

        KetResepTepatIdetifikasiPasien.setFocusTraversalPolicyProvider(true);
        KetResepTepatIdetifikasiPasien.setName("KetResepTepatIdetifikasiPasien"); // NOI18N
        KetResepTepatIdetifikasiPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetResepTepatIdetifikasiPasienKeyPressed(evt);
            }
        });
        FormInput.add(KetResepTepatIdetifikasiPasien);
        KetResepTepatIdetifikasiPasien.setBounds(129, 120, 140, 23);

        jLabel20.setText("Keterangan :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(45, 120, 80, 23);

        jLabel22.setText("Keterangan :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(45, 180, 80, 23);

        KetResepTepatObat.setFocusTraversalPolicyProvider(true);
        KetResepTepatObat.setName("KetResepTepatObat"); // NOI18N
        KetResepTepatObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetResepTepatObatKeyPressed(evt);
            }
        });
        FormInput.add(KetResepTepatObat);
        KetResepTepatObat.setBounds(129, 180, 140, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("3. Tepat Dosis");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(50, 210, 140, 23);

        ResepTepatDosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ResepTepatDosis.setName("ResepTepatDosis"); // NOI18N
        ResepTepatDosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResepTepatDosisKeyPressed(evt);
            }
        });
        FormInput.add(ResepTepatDosis);
        ResepTepatDosis.setBounds(189, 210, 80, 23);

        jLabel26.setText("Keterangan :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(45, 240, 80, 23);

        KetResepTepatDosis.setFocusTraversalPolicyProvider(true);
        KetResepTepatDosis.setName("KetResepTepatDosis"); // NOI18N
        KetResepTepatDosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetResepTepatDosisKeyPressed(evt);
            }
        });
        FormInput.add(KetResepTepatDosis);
        KetResepTepatDosis.setBounds(129, 240, 140, 23);

        jLabel28.setText("Telaah Obat :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(528, 100, 80, 23);

        jLabel29.setText("J.K. :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(337, 40, 30, 23);

        JK.setEditable(false);
        JK.setFocusTraversalPolicyProvider(true);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(371, 40, 40, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("4. Tepat Cara Pemberian");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(50, 270, 140, 23);

        ResepTepatCaraPemberian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ResepTepatCaraPemberian.setName("ResepTepatCaraPemberian"); // NOI18N
        ResepTepatCaraPemberian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResepTepatCaraPemberianKeyPressed(evt);
            }
        });
        FormInput.add(ResepTepatCaraPemberian);
        ResepTepatCaraPemberian.setBounds(189, 270, 80, 23);

        jLabel27.setText("Keterangan :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(45, 300, 80, 23);

        KetResepTepatCaraPemberian.setFocusTraversalPolicyProvider(true);
        KetResepTepatCaraPemberian.setName("KetResepTepatCaraPemberian"); // NOI18N
        KetResepTepatCaraPemberian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetResepTepatCaraPemberianKeyPressed(evt);
            }
        });
        FormInput.add(KetResepTepatCaraPemberian);
        KetResepTepatCaraPemberian.setBounds(129, 300, 140, 23);

        ResepTepatWaktuPemberian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ResepTepatWaktuPemberian.setName("ResepTepatWaktuPemberian"); // NOI18N
        ResepTepatWaktuPemberian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResepTepatWaktuPemberianKeyPressed(evt);
            }
        });
        FormInput.add(ResepTepatWaktuPemberian);
        ResepTepatWaktuPemberian.setBounds(434, 90, 80, 23);

        ResepTidakDuplikasiObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ResepTidakDuplikasiObat.setSelectedIndex(1);
        ResepTidakDuplikasiObat.setName("ResepTidakDuplikasiObat"); // NOI18N
        ResepTidakDuplikasiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResepTidakDuplikasiObatKeyPressed(evt);
            }
        });
        FormInput.add(ResepTidakDuplikasiObat);
        ResepTidakDuplikasiObat.setBounds(434, 150, 80, 23);

        ResepInteraksiObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ResepInteraksiObat.setSelectedIndex(1);
        ResepInteraksiObat.setName("ResepInteraksiObat"); // NOI18N
        ResepInteraksiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResepInteraksiObatKeyPressed(evt);
            }
        });
        FormInput.add(ResepInteraksiObat);
        ResepInteraksiObat.setBounds(434, 210, 80, 23);

        ResepKontraIndikasiObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ResepKontraIndikasiObat.setSelectedIndex(1);
        ResepKontraIndikasiObat.setName("ResepKontraIndikasiObat"); // NOI18N
        ResepKontraIndikasiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ResepKontraIndikasiObatKeyPressed(evt);
            }
        });
        FormInput.add(ResepKontraIndikasiObat);
        ResepKontraIndikasiObat.setBounds(434, 270, 80, 23);

        KetResepTepatWaktuPemberian.setFocusTraversalPolicyProvider(true);
        KetResepTepatWaktuPemberian.setName("KetResepTepatWaktuPemberian"); // NOI18N
        KetResepTepatWaktuPemberian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetResepTepatWaktuPemberianKeyPressed(evt);
            }
        });
        FormInput.add(KetResepTepatWaktuPemberian);
        KetResepTepatWaktuPemberian.setBounds(374, 120, 140, 23);

        KetResepTidakDuplikasiObat.setFocusTraversalPolicyProvider(true);
        KetResepTidakDuplikasiObat.setName("KetResepTidakDuplikasiObat"); // NOI18N
        KetResepTidakDuplikasiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetResepTidakDuplikasiObatKeyPressed(evt);
            }
        });
        FormInput.add(KetResepTidakDuplikasiObat);
        KetResepTidakDuplikasiObat.setBounds(374, 180, 140, 23);

        KetResepInteraksiObat.setFocusTraversalPolicyProvider(true);
        KetResepInteraksiObat.setName("KetResepInteraksiObat"); // NOI18N
        KetResepInteraksiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetResepInteraksiObatKeyPressed(evt);
            }
        });
        FormInput.add(KetResepInteraksiObat);
        KetResepInteraksiObat.setBounds(374, 240, 140, 23);

        KetResepKontraIndikasiObat.setFocusTraversalPolicyProvider(true);
        KetResepKontraIndikasiObat.setName("KetResepKontraIndikasiObat"); // NOI18N
        KetResepKontraIndikasiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KetResepKontraIndikasiObatKeyPressed(evt);
            }
        });
        FormInput.add(KetResepKontraIndikasiObat);
        KetResepKontraIndikasiObat.setBounds(374, 300, 140, 23);

        jLabel25.setText("Keterangan :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(290, 120, 80, 23);

        jLabel30.setText("Keterangan :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(290, 180, 80, 23);

        jLabel31.setText("Keterangan :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(290, 240, 80, 23);

        jLabel32.setText("Keterangan :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(290, 300, 80, 23);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel15.setText("5. Tepat Waktu Pemberian");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(295, 90, 140, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("6. Ada Tidak Duplikasi Obat");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(295, 150, 140, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("7. Interaksi Obat");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(295, 210, 140, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("8. Kontra Indikasi Obat");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(295, 270, 140, 23);

        jLabel36.setText("Telaah Resep :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 70, 94, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("1. Tepat Pasien");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(570, 120, 140, 23);

        ObatTepatPasien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ObatTepatPasien.setName("ObatTepatPasien"); // NOI18N
        ObatTepatPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatTepatPasienKeyPressed(evt);
            }
        });
        FormInput.add(ObatTepatPasien);
        ObatTepatPasien.setBounds(709, 120, 80, 23);

        ObatTepatObat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ObatTepatObat.setName("ObatTepatObat"); // NOI18N
        ObatTepatObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatTepatObatKeyPressed(evt);
            }
        });
        FormInput.add(ObatTepatObat);
        ObatTepatObat.setBounds(709, 150, 80, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("2. Tepat Obat");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(570, 150, 140, 23);

        ObatTepatDosis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ObatTepatDosis.setName("ObatTepatDosis"); // NOI18N
        ObatTepatDosis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatTepatDosisKeyPressed(evt);
            }
        });
        FormInput.add(ObatTepatDosis);
        ObatTepatDosis.setBounds(709, 180, 80, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("3. Tepat Dosis");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(570, 180, 140, 23);

        ObatTepatCaraPemberian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ObatTepatCaraPemberian.setName("ObatTepatCaraPemberian"); // NOI18N
        ObatTepatCaraPemberian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatTepatCaraPemberianKeyPressed(evt);
            }
        });
        FormInput.add(ObatTepatCaraPemberian);
        ObatTepatCaraPemberian.setBounds(709, 210, 80, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("4. Tepat Cara Pemberian");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(570, 210, 140, 23);

        ObatTepatWaktuPemberian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        ObatTepatWaktuPemberian.setName("ObatTepatWaktuPemberian"); // NOI18N
        ObatTepatWaktuPemberian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ObatTepatWaktuPemberianKeyPressed(evt);
            }
        });
        FormInput.add(ObatTepatWaktuPemberian);
        ObatTepatWaktuPemberian.setBounds(709, 240, 80, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText("5. Tepat Waktu Pemberian");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(570, 240, 140, 23);

        NoResep.setEditable(false);
        NoResep.setFocusTraversalPolicyProvider(true);
        NoResep.setName("NoResep"); // NOI18N
        FormInput.add(NoResep);
        NoResep.setBounds(598, 70, 159, 23);

        jLabel42.setText("No.Resep :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(514, 70, 80, 23);

        Scroll1.setViewportView(FormInput);

        PanelInput.add(Scroll1, java.awt.BorderLayout.CENTER);

        FormValidasi1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        FormValidasi1.add(Scroll, java.awt.BorderLayout.CENTER);

        TabData.addTab("Telaah Resep & Obat", FormValidasi1);

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setName("Scroll5"); // NOI18N
        Scroll5.setOpaque(true);
        Scroll5.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll5.setViewportView(LoadHTML);

        TabData.addTab("Verifikasi Resep Dokter", Scroll5);

        internalFrame1.add(TabData, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoResep.getText().trim().equals("")||TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"resep/pasien");
        }else if(Nip.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(Nip,"Petugas");
        }else{
            if(Sequel.menyimpantf("telaah_farmasi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",23,new String[]{
                NoResep.getText(),ResepTepatIdentifikasiPasien.getSelectedItem().toString(),KetResepTepatIdetifikasiPasien.getText(), 
                ResepTepatObat.getSelectedItem().toString(),KetResepTepatObat.getText(),ResepTepatDosis.getSelectedItem().toString(), 
                KetResepTepatDosis.getText(),ResepTepatCaraPemberian.getSelectedItem().toString(),KetResepTepatCaraPemberian.getText(), 
                ResepTepatWaktuPemberian.getSelectedItem().toString(),KetResepTepatWaktuPemberian.getText(),ResepTidakDuplikasiObat.getSelectedItem().toString(), 
                KetResepTidakDuplikasiObat.getText(),ResepInteraksiObat.getSelectedItem().toString(),KetResepInteraksiObat.getText(), 
                ResepKontraIndikasiObat.getSelectedItem().toString(),KetResepKontraIndikasiObat.getText(),ObatTepatPasien.getSelectedItem().toString(),
                ObatTepatObat.getSelectedItem().toString(),ObatTepatDosis.getSelectedItem().toString(),ObatTepatCaraPemberian.getSelectedItem().toString(), 
                ObatTepatWaktuPemberian.getSelectedItem().toString(),Nip.getText()
            })==true){
                tampil();
                emptTeks();
            }   
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,ObatTepatWaktuPemberian,BtnBatal);
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
                if(Nip.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString())){
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
        if(NoResep.getText().trim().equals("")||TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"resep/pasien");
        }else if(Nip.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(Nip,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else{
                    if(Nip.getText().equals(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString())){
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
        if(TabData.getSelectedIndex()==0){
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

                Valid.MyReportqry("rptDataTelaahResep.jasper","report","::[ Data Telaah Resep ]::",
                    "select telaah_farmasi.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,resep_obat.no_rawat,reg_periksa.no_rkm_medis,"+
                    "pasien.nm_pasien,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.jk,pasien.tgl_lahir,resep_obat.kd_dokter,dokter.nm_dokter,resep_obat.status,"+
                    "telaah_farmasi.resep_identifikasi_pasien,telaah_farmasi.resep_ket_identifikasi_pasien,telaah_farmasi.resep_tepat_obat,"+
                    "telaah_farmasi.resep_ket_tepat_obat,telaah_farmasi.resep_tepat_dosis,telaah_farmasi.resep_ket_tepat_dosis,"+
                    "telaah_farmasi.resep_tepat_cara_pemberian,telaah_farmasi.resep_ket_tepat_cara_pemberian,telaah_farmasi.resep_tepat_waktu_pemberian,"+
                    "telaah_farmasi.resep_ket_tepat_waktu_pemberian,telaah_farmasi.resep_ada_tidak_duplikasi_obat,telaah_farmasi.resep_ket_ada_tidak_duplikasi_obat,"+
                    "telaah_farmasi.resep_interaksi_obat,telaah_farmasi.resep_ket_interaksi_obat,telaah_farmasi.resep_kontra_indikasi_obat,"+
                    "telaah_farmasi.resep_ket_kontra_indikasi_obat,telaah_farmasi.obat_tepat_pasien,telaah_farmasi.obat_tepat_obat,"+
                    "telaah_farmasi.obat_tepat_dosis,telaah_farmasi.obat_tepat_cara_pemberian,telaah_farmasi.obat_tepat_waktu_pemberian,"+
                    "telaah_farmasi.nip,petugas.nama "+
                    "from telaah_farmasi inner join resep_obat on telaah_farmasi.no_resep=resep_obat.no_resep "+
                    "inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                    "inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                    "inner join petugas on telaah_farmasi.nip=petugas.nip "+
                    "where resep_obat.tgl_perawatan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+
                    (TCari.getText().equals("")?"":"and (telaah_farmasi.no_resep like '%"+TCari.getText().trim()+"%' or resep_obat.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                    "reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or resep_obat.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                    "dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or resep_obat.status like '%"+TCari.getText().trim()+"%' or telaah_farmasi.nip like '%"+TCari.getText().trim()+"%' or "+
                    "petugas.nama like '%"+TCari.getText().trim()+"%') ")+"order by resep_obat.tgl_perawatan",param);
            }
        }else if(TabData.getSelectedIndex()==1){
            try {
                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                        ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"+                    
                        ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                        ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f = new File("VerifikasiResep.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));   
                bw.write(LoadHTML.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA VERIFIKASI RESEP<br>PERIODE "+DTPCari1.getSelectedItem()+" s.d. "+DTPCari2.getSelectedItem()+"<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();
                Desktop.getDesktop().browse(f.toURI());
            } catch (Exception e) {
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
        TabDataMouseClicked(null);
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TabDataMouseClicked(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        TabDataMouseClicked(null);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            TabDataMouseClicked(null);
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

    private void NipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NipKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NamaPetugas.setText(petugas.tampil3(Nip.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_NipKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        //Valid.pindah(evt,Detik,BB);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void ResepTepatIdentifikasiPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResepTepatIdentifikasiPasienKeyPressed
        Valid.pindah(evt,btnPetugas,KetResepTepatIdetifikasiPasien);
    }//GEN-LAST:event_ResepTepatIdentifikasiPasienKeyPressed

    private void ResepTepatObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResepTepatObatKeyPressed
        Valid.pindah(evt, KetResepTepatIdetifikasiPasien, KetResepTepatObat);
    }//GEN-LAST:event_ResepTepatObatKeyPressed

    private void ResepTepatDosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResepTepatDosisKeyPressed
        Valid.pindah(evt, KetResepTepatObat,KetResepTepatDosis);
    }//GEN-LAST:event_ResepTepatDosisKeyPressed

    private void TabDataMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabDataMouseClicked
        if(TabData.getSelectedIndex()==0){
            tampil();
        }else if(TabData.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_TabDataMouseClicked

    private void ResepTepatCaraPemberianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResepTepatCaraPemberianKeyPressed
        Valid.pindah(evt,KetResepTepatDosis,KetResepTepatCaraPemberian);
    }//GEN-LAST:event_ResepTepatCaraPemberianKeyPressed

    private void ResepTepatWaktuPemberianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResepTepatWaktuPemberianKeyPressed
        Valid.pindah(evt,KetResepTepatCaraPemberian,KetResepTepatWaktuPemberian);
    }//GEN-LAST:event_ResepTepatWaktuPemberianKeyPressed

    private void ResepTidakDuplikasiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResepTidakDuplikasiObatKeyPressed
        Valid.pindah(evt,KetResepTepatWaktuPemberian,KetResepTidakDuplikasiObat);
    }//GEN-LAST:event_ResepTidakDuplikasiObatKeyPressed

    private void ResepInteraksiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResepInteraksiObatKeyPressed
        Valid.pindah(evt,KetResepTidakDuplikasiObat,KetResepInteraksiObat);
    }//GEN-LAST:event_ResepInteraksiObatKeyPressed

    private void ResepKontraIndikasiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ResepKontraIndikasiObatKeyPressed
        Valid.pindah(evt,KetResepInteraksiObat,KetResepKontraIndikasiObat);
    }//GEN-LAST:event_ResepKontraIndikasiObatKeyPressed

    private void ObatTepatPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatTepatPasienKeyPressed
        Valid.pindah(evt,KetResepKontraIndikasiObat,ObatTepatObat);
    }//GEN-LAST:event_ObatTepatPasienKeyPressed

    private void ObatTepatObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatTepatObatKeyPressed
        Valid.pindah(evt,ObatTepatPasien,ObatTepatDosis);
    }//GEN-LAST:event_ObatTepatObatKeyPressed

    private void ObatTepatDosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatTepatDosisKeyPressed
        Valid.pindah(evt,ObatTepatObat,ObatTepatCaraPemberian);
    }//GEN-LAST:event_ObatTepatDosisKeyPressed

    private void ObatTepatCaraPemberianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatTepatCaraPemberianKeyPressed
        Valid.pindah(evt,ObatTepatDosis,ObatTepatWaktuPemberian);
    }//GEN-LAST:event_ObatTepatCaraPemberianKeyPressed

    private void ObatTepatWaktuPemberianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ObatTepatWaktuPemberianKeyPressed
        Valid.pindah(evt,ObatTepatCaraPemberian,BtnSimpan);
    }//GEN-LAST:event_ObatTepatWaktuPemberianKeyPressed

    private void KetResepTepatIdetifikasiPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetResepTepatIdetifikasiPasienKeyPressed
        Valid.pindah(evt,ResepTepatIdentifikasiPasien,ResepTepatObat);
    }//GEN-LAST:event_KetResepTepatIdetifikasiPasienKeyPressed

    private void KetResepTepatObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetResepTepatObatKeyPressed
        Valid.pindah(evt,ResepTepatObat,ResepTepatDosis);
    }//GEN-LAST:event_KetResepTepatObatKeyPressed

    private void KetResepTepatDosisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetResepTepatDosisKeyPressed
        Valid.pindah(evt,ResepTepatDosis,ResepTepatCaraPemberian);
    }//GEN-LAST:event_KetResepTepatDosisKeyPressed

    private void KetResepTepatCaraPemberianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetResepTepatCaraPemberianKeyPressed
        Valid.pindah(evt,ResepTepatCaraPemberian,ResepTepatWaktuPemberian);
    }//GEN-LAST:event_KetResepTepatCaraPemberianKeyPressed

    private void KetResepTepatWaktuPemberianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetResepTepatWaktuPemberianKeyPressed
        Valid.pindah(evt,ResepTepatWaktuPemberian,ResepTidakDuplikasiObat);
    }//GEN-LAST:event_KetResepTepatWaktuPemberianKeyPressed

    private void KetResepTidakDuplikasiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetResepTidakDuplikasiObatKeyPressed
        Valid.pindah(evt,ResepTidakDuplikasiObat,ResepInteraksiObat);
    }//GEN-LAST:event_KetResepTidakDuplikasiObatKeyPressed

    private void KetResepInteraksiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetResepInteraksiObatKeyPressed
        Valid.pindah(evt,ResepInteraksiObat,ResepKontraIndikasiObat);
    }//GEN-LAST:event_KetResepInteraksiObatKeyPressed

    private void KetResepKontraIndikasiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KetResepKontraIndikasiObatKeyPressed
        Valid.pindah(evt,ResepKontraIndikasiObat,ObatTepatPasien);
    }//GEN-LAST:event_KetResepKontraIndikasiObatKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventoryTelaahResep dialog = new InventoryTelaahResep(new javax.swing.JFrame(), true);
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
    private widget.TextBox Alamat;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.PanelBiasa FormValidasi1;
    private widget.TextBox JK;
    private widget.TextBox KetResepInteraksiObat;
    private widget.TextBox KetResepKontraIndikasiObat;
    private widget.TextBox KetResepTepatCaraPemberian;
    private widget.TextBox KetResepTepatDosis;
    private widget.TextBox KetResepTepatIdetifikasiPasien;
    private widget.TextBox KetResepTepatObat;
    private widget.TextBox KetResepTepatWaktuPemberian;
    private widget.TextBox KetResepTidakDuplikasiObat;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextBox NamaPetugas;
    private widget.TextBox Nip;
    private widget.TextBox NoResep;
    private widget.ComboBox ObatTepatCaraPemberian;
    private widget.ComboBox ObatTepatDosis;
    private widget.ComboBox ObatTepatObat;
    private widget.ComboBox ObatTepatPasien;
    private widget.ComboBox ObatTepatWaktuPemberian;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox ResepInteraksiObat;
    private widget.ComboBox ResepKontraIndikasiObat;
    private widget.ComboBox ResepTepatCaraPemberian;
    private widget.ComboBox ResepTepatDosis;
    private widget.ComboBox ResepTepatIdentifikasiPasien;
    private widget.ComboBox ResepTepatObat;
    private widget.ComboBox ResepTepatWaktuPemberian;
    private widget.ComboBox ResepTidakDuplikasiObat;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll5;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabData;
    private widget.TextBox TglLahir;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                "select telaah_farmasi.no_resep,resep_obat.tgl_perawatan,resep_obat.jam,resep_obat.no_rawat,reg_periksa.no_rkm_medis,"+
                "pasien.nm_pasien,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.jk,pasien.tgl_lahir,resep_obat.kd_dokter,dokter.nm_dokter,resep_obat.status,"+
                "telaah_farmasi.resep_identifikasi_pasien,telaah_farmasi.resep_ket_identifikasi_pasien,telaah_farmasi.resep_tepat_obat,"+
                "telaah_farmasi.resep_ket_tepat_obat,telaah_farmasi.resep_tepat_dosis,telaah_farmasi.resep_ket_tepat_dosis,"+
                "telaah_farmasi.resep_tepat_cara_pemberian,telaah_farmasi.resep_ket_tepat_cara_pemberian,telaah_farmasi.resep_tepat_waktu_pemberian,"+
                "telaah_farmasi.resep_ket_tepat_waktu_pemberian,telaah_farmasi.resep_ada_tidak_duplikasi_obat,telaah_farmasi.resep_ket_ada_tidak_duplikasi_obat,"+
                "telaah_farmasi.resep_interaksi_obat,telaah_farmasi.resep_ket_interaksi_obat,telaah_farmasi.resep_kontra_indikasi_obat,"+
                "telaah_farmasi.resep_ket_kontra_indikasi_obat,telaah_farmasi.obat_tepat_pasien,telaah_farmasi.obat_tepat_obat,"+
                "telaah_farmasi.obat_tepat_dosis,telaah_farmasi.obat_tepat_cara_pemberian,telaah_farmasi.obat_tepat_waktu_pemberian,"+
                "telaah_farmasi.nip,petugas.nama "+
                "from telaah_farmasi inner join resep_obat on telaah_farmasi.no_resep=resep_obat.no_resep "+
                "inner join reg_periksa on resep_obat.no_rawat=reg_periksa.no_rawat "+
                "inner join pasien on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                "inner join dokter on resep_obat.kd_dokter=dokter.kd_dokter "+
                "inner join petugas on telaah_farmasi.nip=petugas.nip "+
                "where resep_obat.tgl_perawatan between ? and ? "+
                (TCari.getText().equals("")?"":"and (telaah_farmasi.no_resep like ? or resep_obat.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "+
                "pasien.nm_pasien like ? or resep_obat.kd_dokter like ? or dokter.nm_dokter like ? or resep_obat.status like ? or telaah_farmasi.nip like ? or "+
                "petugas.nama like ?) ")+"order by resep_obat.tgl_perawatan");
                
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().toString().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_resep"),rs.getString("tgl_perawatan"),rs.getString("jam"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),
                        rs.getString("nm_pasien"),rs.getString("umur"),rs.getString("jk"),rs.getString("tgl_lahir"),rs.getString("kd_dokter"),
                        rs.getString("nm_dokter"),rs.getString("status").replace("r","R"),rs.getString("resep_identifikasi_pasien"),rs.getString("resep_ket_identifikasi_pasien"),
                        rs.getString("resep_tepat_obat"),rs.getString("resep_ket_tepat_obat"),rs.getString("resep_tepat_dosis"),rs.getString("resep_ket_tepat_dosis"),
                        rs.getString("resep_tepat_cara_pemberian"),rs.getString("resep_ket_tepat_cara_pemberian"),rs.getString("resep_tepat_waktu_pemberian"),
                        rs.getString("resep_ket_tepat_waktu_pemberian"),rs.getString("resep_ada_tidak_duplikasi_obat"),rs.getString("resep_ket_ada_tidak_duplikasi_obat"),
                        rs.getString("resep_interaksi_obat"),rs.getString("resep_ket_interaksi_obat"),rs.getString("resep_kontra_indikasi_obat"),
                        rs.getString("resep_ket_kontra_indikasi_obat"),rs.getString("obat_tepat_pasien"),rs.getString("obat_tepat_obat"),rs.getString("obat_tepat_dosis"),
                        rs.getString("obat_tepat_cara_pemberian"),rs.getString("obat_tepat_waktu_pemberian"),rs.getString("nip"),rs.getString("nama")
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
        ResepTepatIdentifikasiPasien.setSelectedIndex(0);
        KetResepTepatIdetifikasiPasien.setText("");
        ResepTepatObat.setSelectedIndex(0);
        KetResepTepatObat.setText("");
        ResepTepatDosis.setSelectedIndex(0);
        KetResepTepatDosis.setText("");
        ResepTepatCaraPemberian.setSelectedIndex(0);
        KetResepTepatCaraPemberian.setText("");
        ResepTepatWaktuPemberian.setSelectedIndex(0);
        KetResepTepatWaktuPemberian.setText("");
        ResepTidakDuplikasiObat.setSelectedIndex(1);
        KetResepTidakDuplikasiObat.setText("");
        ResepInteraksiObat.setSelectedIndex(1);
        KetResepInteraksiObat.setText("");
        ResepKontraIndikasiObat.setSelectedIndex(1);
        KetResepKontraIndikasiObat.setText("");
        ObatTepatPasien.setSelectedIndex(0);
        ObatTepatObat.setSelectedIndex(0);
        ObatTepatDosis.setSelectedIndex(0);
        ObatTepatCaraPemberian.setSelectedIndex(0);
        ObatTepatWaktuPemberian.setSelectedIndex(0);
        ResepTepatIdentifikasiPasien.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoResep.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            ResepTepatIdentifikasiPasien.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            KetResepTepatIdetifikasiPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            ResepTepatObat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            KetResepTepatObat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            ResepTepatDosis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            KetResepTepatDosis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            ResepTepatCaraPemberian.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            KetResepTepatCaraPemberian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            ResepTepatWaktuPemberian.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            KetResepTepatWaktuPemberian.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            ResepTidakDuplikasiObat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            KetResepTidakDuplikasiObat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            ResepInteraksiObat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            KetResepInteraksiObat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            ResepKontraIndikasiObat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            KetResepKontraIndikasiObat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            ObatTepatPasien.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            ObatTepatObat.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            ObatTepatDosis.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            ObatTepatCaraPemberian.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            ObatTepatWaktuPemberian.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Nip.setText(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            NamaPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
        }
    }
    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
        Sequel.cariIsi("select date_format(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",TglLahir,TNoRM.getText());
        Sequel.cariIsi("select pasien.jk from pasien where pasien.no_rkm_medis=? ",JK,TNoRM.getText());
        Sequel.cariIsi("select concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) from pasien "+
                       "inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                       "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis=? ",Alamat,TNoRM.getText());
    }
    
    public void setNoRm(String noresep,String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        NoResep.setText(noresep);
        TCari.setText(noresep);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            if(this.getHeight()>600){
                PanelInput.setPreferredSize(new Dimension(WIDTH,354));
            }else{
                PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-230));
            }   
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
        BtnSimpan.setEnabled(akses.gettelaah_resep());
        BtnHapus.setEnabled(akses.gettelaah_resep());
        BtnEdit.setEnabled(akses.gettelaah_resep());
        BtnPrint.setEnabled(akses.gettelaah_resep()); 
        if(akses.getjml2()>=1){
            Nip.setEditable(false);
            btnPetugas.setEnabled(false);
            Nip.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(Nip.getText()));
            if(NamaPetugas.getText().equals("")){
                Nip.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }           
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from telaah_farmasi where no_resep=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tampil();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }

    private void ganti() {
        if(Sequel.mengedittf("telaah_farmasi","no_resep=?","no_resep=?,resep_identifikasi_pasien=?,resep_ket_identifikasi_pasien=?,resep_tepat_obat=?,resep_ket_tepat_obat=?,"+
                "resep_tepat_dosis=?,resep_ket_tepat_dosis=?,resep_tepat_cara_pemberian=?,resep_ket_tepat_cara_pemberian=?,resep_tepat_waktu_pemberian=?,resep_ket_tepat_waktu_pemberian=?,"+
                "resep_ada_tidak_duplikasi_obat=?,resep_ket_ada_tidak_duplikasi_obat=?,resep_interaksi_obat=?,resep_ket_interaksi_obat=?,resep_kontra_indikasi_obat=?,"+
                "resep_ket_kontra_indikasi_obat=?,obat_tepat_pasien=?,obat_tepat_obat=?,obat_tepat_dosis=?,obat_tepat_cara_pemberian=?,obat_tepat_waktu_pemberian=?,nip=?",24,new String[]{
                NoResep.getText(),ResepTepatIdentifikasiPasien.getSelectedItem().toString(),KetResepTepatIdetifikasiPasien.getText(), 
                ResepTepatObat.getSelectedItem().toString(),KetResepTepatObat.getText(),ResepTepatDosis.getSelectedItem().toString(), 
                KetResepTepatDosis.getText(),ResepTepatCaraPemberian.getSelectedItem().toString(),KetResepTepatCaraPemberian.getText(), 
                ResepTepatWaktuPemberian.getSelectedItem().toString(),KetResepTepatWaktuPemberian.getText(),ResepTidakDuplikasiObat.getSelectedItem().toString(), 
                KetResepTidakDuplikasiObat.getText(),ResepInteraksiObat.getSelectedItem().toString(),KetResepInteraksiObat.getText(), 
                ResepKontraIndikasiObat.getSelectedItem().toString(),KetResepKontraIndikasiObat.getText(),ObatTepatPasien.getSelectedItem().toString(),
                ObatTepatObat.getSelectedItem().toString(),ObatTepatDosis.getSelectedItem().toString(),ObatTepatCaraPemberian.getSelectedItem().toString(), 
                ObatTepatWaktuPemberian.getSelectedItem().toString(),Nip.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
               tampil();
               emptTeks();
        }
    }

    private void tampil2() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            htmlContent.append(                             
                "<tr class='isi'>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='9%'>No.Resep</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='8%'>Tgl.Resep</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='6%'>Jam Resep</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='12%'>No.Rawat</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='8%'>No.RM</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='21%'>Pasien</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='10%'>Kode Dokter</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='21%'>Dokter Peresep</td>"+
                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='5%'>Status</td>"+
                "</tr>");
            ps=koneksi.prepareStatement("select resep_obat.no_resep,resep_obat.tgl_peresepan,resep_obat.jam_peresepan,"+
                    " resep_obat.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_obat.kd_dokter,dokter.nm_dokter,"+
                    " resep_obat.status,resep_obat.tgl_perawatan,resep_obat.jam "+
                    " from resep_obat inner join reg_periksa inner join pasien inner join dokter on resep_obat.no_rawat=reg_periksa.no_rawat  "+
                    " and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and resep_obat.kd_dokter=dokter.kd_dokter where "+
                    " resep_obat.tgl_peresepan<>'0000-00-00' and resep_obat.tgl_perawatan<>'0000-00-00' and resep_obat.tgl_peresepan between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (resep_obat.no_resep like ? or resep_obat.no_rawat like ? or "+
                    "pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or resep_obat.kd_dokter like ? or dokter.nm_dokter like ? or "+
                    "resep_obat.status like ?) ")+"order by resep_obat.tgl_perawatan,resep_obat.jam desc");
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
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    htmlContent.append( 
                        "<tr class='isi'>"+
                            "<td align='center'>"+rs.getString("no_resep")+"</td>"+
                            "<td align='center'>"+rs.getString("tgl_peresepan")+"</td>"+
                            "<td align='center'>"+rs.getString("jam_peresepan")+"</td>"+
                            "<td align='center'>"+rs.getString("no_rawat")+"</td>"+
                            "<td align='left'>"+rs.getString("no_rkm_medis")+"</td>"+
                            "<td align='left'>"+rs.getString("nm_pasien")+"</td>"+
                            "<td align='left'>"+rs.getString("kd_dokter")+"</td>"+
                            "<td align='left'>"+rs.getString("nm_dokter")+"</td>"+
                            "<td align='center'>"+rs.getString("status").replace("r","R")+"</td>"+
                        "</tr>"
                    );
                    htmlContent.append( 
                        "<tr class='isi'>"+
                            "<td align='left' valign='top'></td>"+
                            "<td align='left' valign='top' colspan='8'>"+
                                "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                        "<td colspan='5'>Obat Yang Diresepkan : </td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td width='10%' bgcolor='#FFFBFC' align='center'>Jumlah</td>"+
                                        "<td width='10%' bgcolor='#FFFBFC' align='center'>Satuan</td>"+
                                        "<td width='25%' bgcolor='#FFFBFC' align='center'>Aturan Pakai</td>"+
                                        "<td width='15%' bgcolor='#FFFBFC' align='center'>Kode/No</td>"+
                                        "<td width='40%' bgcolor='#FFFBFC' align='center'>Nama Obat/Racikan</td>"+
                                    "</tr>"
                    );
                    ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter.jml,"+
                        "databarang.kode_sat,resep_dokter.aturan_pakai from resep_dokter inner join databarang on "+
                        "resep_dokter.kode_brng=databarang.kode_brng where resep_dokter.no_resep=? order by databarang.kode_brng");
                    try {
                        ps2.setString(1,rs.getString("no_resep"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append( 
                                "<tr class='isi'>"+
                                    "<td align='left'>"+rs2.getString("jml")+"</td>"+
                                    "<td align='center'>"+rs2.getString("kode_sat")+"</td>"+
                                    "<td align='left'>"+rs2.getString("aturan_pakai")+"</td>"+
                                    "<td align='left'>"+rs2.getString("kode_brng")+"</td>"+
                                    "<td align='left'>"+rs2.getString("nama_brng")+"</td>"+
                                "</tr>"
                            );
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement(
                            "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                            "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                            "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                            "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                            "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                            "resep_dokter_racikan.no_resep=? ");
                    try {
                        ps2.setString(1,rs.getString("no_resep"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append( 
                                "<tr class='isi'>"+
                                    "<td align='left'>"+rs2.getString("jml_dr")+"</td>"+
                                    "<td align='center'>"+rs2.getString("metode")+"</td>"+
                                    "<td align='left'>"+rs2.getString("aturan_pakai")+"</td>"+
                                    "<td align='left'>No.Racik : "+rs2.getString("no_racik")+"</td>"+
                                    "<td align='left'>"+rs2.getString("nama_racik")+"</td>"+
                                "</tr>"
                            );
                            ps3=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,"+
                                "databarang.kode_sat from resep_dokter_racikan_detail inner join databarang on resep_dokter_racikan_detail.kode_brng=databarang.kode_brng "+
                                "where resep_dokter_racikan_detail.no_resep=? and resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                            try {
                                ps3.setString(1,rs.getString("no_resep"));
                                ps3.setString(2,rs2.getString("no_racik"));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    htmlContent.append( 
                                        "<tr class='isi'>"+
                                            "<td align='left'>&nbsp;&nbsp;"+rs3.getString("jml")+"</td>"+
                                            "<td align='center'>&nbsp;&nbsp;"+rs3.getString("kode_sat")+"</td>"+
                                            "<td align='left'></td>"+
                                            "<td align='left'>"+"&nbsp;&nbsp;"+rs3.getString("kode_brng")+"</td>"+
                                            "<td align='left'>"+"&nbsp;&nbsp;"+rs3.getString("nama_brng")+"</td>"+
                                        "</tr>"
                                    );
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi 3 : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    htmlContent.append( 
                                "</table>"+
                            "</td>"+
                        "</tr>"
                    );
                    
                    htmlContent.append( 
                        "<tr class='isi'>"+
                            "<td align='left' valign='top'></td>"+
                            "<td align='left' valign='top' colspan='8'>"+
                                "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                        "<td colspan='5'>Obat Yang Diberikan : </td>"+
                                    "</tr>"+
                                    "<tr class='isi'>"+
                                        "<td width='10%' bgcolor='#FFFBFC' align='center'>Jumlah</td>"+
                                        "<td width='10%' bgcolor='#FFFBFC' align='center'>Satuan</td>"+
                                        "<td width='25%' bgcolor='#FFFBFC' align='center'>Aturan Pakai</td>"+
                                        "<td width='15%' bgcolor='#FFFBFC' align='center'>Kode/No</td>"+
                                        "<td width='40%' bgcolor='#FFFBFC' align='center'>Nama Obat/Racikan</td>"+
                                    "</tr>"
                    );
                    ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,detail_pemberian_obat.jml,databarang.kode_sat,"+
                        "detail_pemberian_obat.biaya_obat,detail_pemberian_obat.embalase,detail_pemberian_obat.tuslah,detail_pemberian_obat.total from "+
                        "detail_pemberian_obat inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng "+
                        "where detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.jam=? and detail_pemberian_obat.no_rawat=? "+
                        "and databarang.kode_brng not in (select detail_obat_racikan.kode_brng from detail_obat_racikan where "+
                        "detail_obat_racikan.tgl_perawatan=? and detail_obat_racikan.jam=? and detail_obat_racikan.no_rawat=?) "+
                        "order by databarang.kode_brng");
                    try {
                        ps2.setString(1,rs.getString("tgl_perawatan"));
                        ps2.setString(2,rs.getString("jam"));
                        ps2.setString(3,rs.getString("no_rawat"));
                        ps2.setString(4,rs.getString("tgl_perawatan"));
                        ps2.setString(5,rs.getString("jam"));
                        ps2.setString(6,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append( 
                                "<tr class='isi'>"+
                                    "<td align='left'>"+rs2.getString("jml")+"</td>"+
                                    "<td align='center'>"+rs2.getString("kode_sat")+"</td>"+
                                    "<td align='left'>"+
                                        Sequel.cariIsi("select aturan_pakai.aturan from aturan_pakai where aturan_pakai.tgl_perawatan='"+rs.getString("tgl_perawatan")+"' and "+
                                        "aturan_pakai.jam='"+rs.getString("jam")+"' and aturan_pakai.no_rawat='"+rs.getString("no_rawat")+"' and aturan_pakai.kode_brng='"+rs2.getString("kode_brng")+"'")+
                                    "</td>"+
                                    "<td align='left'>"+rs2.getString("kode_brng")+"</td>"+
                                    "<td align='left'>"+rs2.getString("nama_brng")+"</td>"+
                                "</tr>"
                            );
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    ps2=koneksi.prepareStatement(
                            "select obat_racikan.no_racik,obat_racikan.nama_racik,"+
                            "obat_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                            "obat_racikan.jml_dr,obat_racikan.aturan_pakai,"+
                            "obat_racikan.keterangan from obat_racikan inner join metode_racik "+
                            "on obat_racikan.kd_racik=metode_racik.kd_racik where "+
                            "obat_racikan.tgl_perawatan=? and obat_racikan.jam=? "+
                            "and obat_racikan.no_rawat=? ");
                    try {
                        ps2.setString(1,rs.getString("tgl_perawatan"));
                        ps2.setString(2,rs.getString("jam"));
                        ps2.setString(3,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append( 
                                "<tr class='isi'>"+
                                    "<td align='left'>"+rs2.getString("jml_dr")+"</td>"+
                                    "<td align='center'>"+rs2.getString("metode")+"</td>"+
                                    "<td align='left'>"+rs2.getString("aturan_pakai")+"</td>"+
                                    "<td align='left'>No.Racik : "+rs2.getString("no_racik")+"</td>"+
                                    "<td align='left'>"+rs2.getString("nama_racik")+"</td>"+
                                "</tr>"
                            );
                            
                            ps3=koneksi.prepareStatement(
                                "select databarang.kode_brng,databarang.nama_brng,detail_pemberian_obat.jml,databarang.kode_sat,"+
                                "detail_pemberian_obat.biaya_obat,detail_pemberian_obat.embalase,detail_pemberian_obat.tuslah,detail_pemberian_obat.total from "+
                                "detail_pemberian_obat inner join databarang inner join detail_obat_racikan "+
                                "on detail_pemberian_obat.kode_brng=databarang.kode_brng and "+
                                "detail_pemberian_obat.kode_brng=detail_obat_racikan.kode_brng and "+
                                "detail_pemberian_obat.tgl_perawatan=detail_obat_racikan.tgl_perawatan and "+
                                "detail_pemberian_obat.jam=detail_obat_racikan.jam and "+
                                "detail_pemberian_obat.no_rawat=detail_obat_racikan.no_rawat "+
                                "where detail_pemberian_obat.tgl_perawatan=? and detail_pemberian_obat.jam=? and "+
                                "detail_pemberian_obat.no_rawat=? and detail_obat_racikan.no_racik=? order by databarang.kode_brng");
                            try {
                                ps3.setString(1,rs.getString("tgl_perawatan"));
                                ps3.setString(2,rs.getString("jam"));
                                ps3.setString(3,rs.getString("no_rawat"));
                                ps3.setString(4,rs2.getString("no_racik"));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    htmlContent.append( 
                                        "<tr class='isi'>"+
                                            "<td align='left'>&nbsp;&nbsp;"+rs3.getString("jml")+"</td>"+
                                            "<td align='center'>&nbsp;&nbsp;"+rs3.getString("kode_sat")+"</td>"+
                                            "<td align='left'></td>"+
                                            "<td align='left'>"+"&nbsp;&nbsp;"+rs3.getString("kode_brng")+"</td>"+
                                            "<td align='left'>"+"&nbsp;&nbsp;"+rs3.getString("nama_brng")+"</td>"+
                                        "</tr>"
                                    );
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi Detail Racikan : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif Racikan : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    htmlContent.append( 
                                "</table>"+
                            "</td>"+
                        "</tr>"
                    );
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
            LoadHTML.setText(
                "<html>"+
                  "<table width='100%' border='0' align='left' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                   htmlContent.toString()+
                  "</table>"+
                "</html>"
            );
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
}

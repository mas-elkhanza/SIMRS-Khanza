/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


package rekammedis;

import fungsi.WarnaTable;
import fungsi.akses;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
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
 * @author perpustakaan
 */
public final class RMSignOutSebelumMenutupLuka extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private String finger="",finger2="",finger3="";
    private StringBuilder htmlContent;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMSignOutSebelumMenutupLuka(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","SN/CN","Tindakan","Kode Dokter Bedah","Nama Dokter Bedah",
            "Kode Dokter Anest","Nama Dokter Anestesi","Verbal Tindakan","Verbal Kasa","Verbal Instrumen","Verbal Alat Tajam","Kelengkapan Spesimen Label",
            "Kelengkapan Spesimen Formulir","P.K.Dokter Bedah","P.K.Dokter Anestesi","P.K.Perawat OK","Perhatian Utama Fase Pemulihan",
            "NIP OK","Petugas Ruang OK"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(115);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(160);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(160);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(87);
            }else if(i==13){
                column.setPreferredWidth(67);
            }else if(i==14){
                column.setPreferredWidth(94);
            }else if(i==15){
                column.setPreferredWidth(96);
            }else if(i==16){
                column.setPreferredWidth(167);
            }else if(i==17){
                column.setPreferredWidth(167);
            }else if(i==18){
                column.setPreferredWidth(91);
            }else if(i==19){
                column.setPreferredWidth(103);
            }else if(i==20){
                column.setPreferredWidth(84);
            }else if(i==21){
                column.setPreferredWidth(200);
            }else if(i==22){
                column.setPreferredWidth(90);
            }else if(i==23){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        SNCN.setDocument(new batasInput((byte)25).getKata(SNCN));
        Tindakan.setDocument(new batasInput((byte)50).getKata(Tindakan));
        PerhatianUtamaFasePemulihan.setDocument(new batasInput((byte)100).getKata(PerhatianUtamaFasePemulihan));
        
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
                    KdPetugasOK.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugasOK.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    btnPetugasOK.requestFocus();
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
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){      
                    if(pilihan==1){
                        KodeDokterBedah.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NamaDokterBedah.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        btnDokterBedah.requestFocus();
                    }else if(pilihan==2){
                        KodeDokterAnestesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        NamaDokterAnestesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        btnDokterAnestesi.requestFocus();
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnSignOutSebelumMenutupLuka = new javax.swing.JMenuItem();
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
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel20 = new widget.Label();
        SNCN = new widget.TextBox();
        jLabel22 = new widget.Label();
        jLabel23 = new widget.Label();
        KodeDokterBedah = new widget.TextBox();
        NamaDokterBedah = new widget.TextBox();
        btnDokterBedah = new widget.Button();
        btnDokterAnestesi = new widget.Button();
        NamaDokterAnestesi = new widget.TextBox();
        KodeDokterAnestesi = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        Tindakan = new widget.TextBox();
        jLabel26 = new widget.Label();
        KdPetugasOK = new widget.TextBox();
        NmPetugasOK = new widget.TextBox();
        btnPetugasOK = new widget.Button();
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel27 = new widget.Label();
        jLabel54 = new widget.Label();
        jLabel28 = new widget.Label();
        VerbalTindakan = new widget.ComboBox();
        jLabel30 = new widget.Label();
        VerbalKasa = new widget.ComboBox();
        jLabel31 = new widget.Label();
        VerbalInstrumen = new widget.ComboBox();
        jLabel32 = new widget.Label();
        VerbalAlatTajam = new widget.ComboBox();
        jLabel55 = new widget.Label();
        jLabel29 = new widget.Label();
        KelengkapanSpesimenLabel = new widget.ComboBox();
        KelengkapanSpesimenFormulir = new widget.ComboBox();
        jLabel33 = new widget.Label();
        jLabel56 = new widget.Label();
        jLabel34 = new widget.Label();
        PeninjauanKembaliDokterBedah = new widget.ComboBox();
        jLabel35 = new widget.Label();
        PeninjauanKembaliDokterAnestesi = new widget.ComboBox();
        jLabel36 = new widget.Label();
        PeninjauanKembaliPerawatKamarOK = new widget.ComboBox();
        jLabel57 = new widget.Label();
        jLabel58 = new widget.Label();
        PerhatianUtamaFasePemulihan = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnSignOutSebelumMenutupLuka.setBackground(new java.awt.Color(255, 255, 254));
        MnSignOutSebelumMenutupLuka.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSignOutSebelumMenutupLuka.setForeground(new java.awt.Color(50, 50, 50));
        MnSignOutSebelumMenutupLuka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSignOutSebelumMenutupLuka.setText("Formulir Sign-Out Sebelum Menutup Luka");
        MnSignOutSebelumMenutupLuka.setName("MnSignOutSebelumMenutupLuka"); // NOI18N
        MnSignOutSebelumMenutupLuka.setPreferredSize(new java.awt.Dimension(270, 26));
        MnSignOutSebelumMenutupLuka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSignOutSebelumMenutupLukaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSignOutSebelumMenutupLuka);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Sign-Out Sebelum Menutup Luka ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-01-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-01-2023" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 366));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 313));
        FormInput.setLayout(null);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("No.Rawat");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(21, 10, 75, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(79, 10, 141, 23);

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
        jLabel16.setBounds(0, 40, 75, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "17-01-2023 19:06:59" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 130, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Sebelum Menutup Luka & Meninggalkan Kamar Operasi :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(21, 100, 580, 23);

        SNCN.setHighlighter(null);
        SNCN.setName("SNCN"); // NOI18N
        SNCN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SNCNKeyPressed(evt);
            }
        });
        FormInput.add(SNCN);
        SNCN.setBounds(264, 40, 120, 23);

        jLabel22.setText("SN/CN :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(210, 40, 50, 23);

        jLabel23.setText("Dokter Bedah :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(390, 40, 91, 23);

        KodeDokterBedah.setEditable(false);
        KodeDokterBedah.setHighlighter(null);
        KodeDokterBedah.setName("KodeDokterBedah"); // NOI18N
        FormInput.add(KodeDokterBedah);
        KodeDokterBedah.setBounds(485, 40, 97, 23);

        NamaDokterBedah.setEditable(false);
        NamaDokterBedah.setName("NamaDokterBedah"); // NOI18N
        FormInput.add(NamaDokterBedah);
        NamaDokterBedah.setBounds(584, 40, 175, 23);

        btnDokterBedah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokterBedah.setMnemonic('2');
        btnDokterBedah.setToolTipText("ALt+2");
        btnDokterBedah.setName("btnDokterBedah"); // NOI18N
        btnDokterBedah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterBedahActionPerformed(evt);
            }
        });
        btnDokterBedah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterBedahKeyPressed(evt);
            }
        });
        FormInput.add(btnDokterBedah);
        btnDokterBedah.setBounds(761, 40, 28, 23);

        btnDokterAnestesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokterAnestesi.setMnemonic('2');
        btnDokterAnestesi.setToolTipText("ALt+2");
        btnDokterAnestesi.setName("btnDokterAnestesi"); // NOI18N
        btnDokterAnestesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterAnestesiActionPerformed(evt);
            }
        });
        btnDokterAnestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterAnestesiKeyPressed(evt);
            }
        });
        FormInput.add(btnDokterAnestesi);
        btnDokterAnestesi.setBounds(761, 70, 28, 23);

        NamaDokterAnestesi.setEditable(false);
        NamaDokterAnestesi.setName("NamaDokterAnestesi"); // NOI18N
        FormInput.add(NamaDokterAnestesi);
        NamaDokterAnestesi.setBounds(584, 70, 175, 23);

        KodeDokterAnestesi.setEditable(false);
        KodeDokterAnestesi.setHighlighter(null);
        KodeDokterAnestesi.setName("KodeDokterAnestesi"); // NOI18N
        FormInput.add(KodeDokterAnestesi);
        KodeDokterAnestesi.setBounds(485, 70, 97, 23);

        jLabel24.setText("Dokter Anestesi :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(390, 70, 91, 23);

        jLabel25.setText("Tindakan :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(0, 70, 75, 23);

        Tindakan.setHighlighter(null);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        FormInput.add(Tindakan);
        Tindakan.setBounds(79, 70, 305, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText("Perawat Kamar Operasi");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(21, 310, 130, 23);

        KdPetugasOK.setEditable(false);
        KdPetugasOK.setHighlighter(null);
        KdPetugasOK.setName("KdPetugasOK"); // NOI18N
        FormInput.add(KdPetugasOK);
        KdPetugasOK.setBounds(147, 310, 110, 23);

        NmPetugasOK.setEditable(false);
        NmPetugasOK.setName("NmPetugasOK"); // NOI18N
        FormInput.add(NmPetugasOK);
        NmPetugasOK.setBounds(259, 310, 300, 23);

        btnPetugasOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasOK.setMnemonic('2');
        btnPetugasOK.setToolTipText("ALt+2");
        btnPetugasOK.setName("btnPetugasOK"); // NOI18N
        btnPetugasOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasOKActionPerformed(evt);
            }
        });
        btnPetugasOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasOKKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugasOK);
        btnPetugasOK.setBounds(561, 310, 28, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 75, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 100, 810, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 100, 810, 1);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 300, 810, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 300, 810, 1);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 310, 143, 23);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("Perawat Melakukan Konfirmasi Secara Verbal :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(40, 120, 290, 23);

        jLabel28.setText("Tindakan :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(67, 140, 60, 23);

        VerbalTindakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        VerbalTindakan.setName("VerbalTindakan"); // NOI18N
        VerbalTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VerbalTindakanKeyPressed(evt);
            }
        });
        FormInput.add(VerbalTindakan);
        VerbalTindakan.setBounds(131, 140, 80, 23);

        jLabel30.setText("Kelengkapan Kasa :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(237, 140, 110, 23);

        VerbalKasa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        VerbalKasa.setName("VerbalKasa"); // NOI18N
        VerbalKasa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VerbalKasaKeyPressed(evt);
            }
        });
        FormInput.add(VerbalKasa);
        VerbalKasa.setBounds(351, 140, 80, 23);

        jLabel31.setText("Instrumen :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(459, 140, 67, 23);

        VerbalInstrumen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        VerbalInstrumen.setName("VerbalInstrumen"); // NOI18N
        VerbalInstrumen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VerbalInstrumenKeyPressed(evt);
            }
        });
        FormInput.add(VerbalInstrumen);
        VerbalInstrumen.setBounds(530, 140, 80, 23);

        jLabel32.setText("Alat Tajam :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(638, 140, 67, 23);

        VerbalAlatTajam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        VerbalAlatTajam.setName("VerbalAlatTajam"); // NOI18N
        VerbalAlatTajam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VerbalAlatTajamKeyPressed(evt);
            }
        });
        FormInput.add(VerbalAlatTajam);
        VerbalAlatTajam.setBounds(709, 140, 80, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Kelengkapan Spesimen Jika Ada :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(40, 170, 180, 23);

        jLabel29.setText("Label :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(87, 190, 60, 23);

        KelengkapanSpesimenLabel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lengkap", "Tidak Lengkap", "Tidak Ada Pemeriksaan Spesimen" }));
        KelengkapanSpesimenLabel.setName("KelengkapanSpesimenLabel"); // NOI18N
        KelengkapanSpesimenLabel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelengkapanSpesimenLabelKeyPressed(evt);
            }
        });
        FormInput.add(KelengkapanSpesimenLabel);
        KelengkapanSpesimenLabel.setBounds(151, 190, 245, 23);

        KelengkapanSpesimenFormulir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lengkap", "Tidak Lengkap", "Tidak Ada Pemeriksaan Spesimen" }));
        KelengkapanSpesimenFormulir.setName("KelengkapanSpesimenFormulir"); // NOI18N
        KelengkapanSpesimenFormulir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KelengkapanSpesimenFormulirKeyPressed(evt);
            }
        });
        FormInput.add(KelengkapanSpesimenFormulir);
        KelengkapanSpesimenFormulir.setBounds(544, 190, 245, 23);

        jLabel33.setText("Formulir :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(450, 190, 90, 23);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("Peninjauan Kembali Kegiatan :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(40, 220, 290, 23);

        jLabel34.setText("Dokter Bedah :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(47, 240, 100, 23);

        PeninjauanKembaliDokterBedah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PeninjauanKembaliDokterBedah.setName("PeninjauanKembaliDokterBedah"); // NOI18N
        PeninjauanKembaliDokterBedah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PeninjauanKembaliDokterBedahKeyPressed(evt);
            }
        });
        FormInput.add(PeninjauanKembaliDokterBedah);
        PeninjauanKembaliDokterBedah.setBounds(151, 240, 80, 23);

        jLabel35.setText("Dokter Anestesi :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(290, 240, 110, 23);

        PeninjauanKembaliDokterAnestesi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PeninjauanKembaliDokterAnestesi.setName("PeninjauanKembaliDokterAnestesi"); // NOI18N
        PeninjauanKembaliDokterAnestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PeninjauanKembaliDokterAnestesiKeyPressed(evt);
            }
        });
        FormInput.add(PeninjauanKembaliDokterAnestesi);
        PeninjauanKembaliDokterAnestesi.setBounds(404, 240, 80, 23);

        jLabel36.setText("Perawat Kamar Operasi :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(575, 240, 130, 23);

        PeninjauanKembaliPerawatKamarOK.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        PeninjauanKembaliPerawatKamarOK.setName("PeninjauanKembaliPerawatKamarOK"); // NOI18N
        PeninjauanKembaliPerawatKamarOK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PeninjauanKembaliPerawatKamarOKKeyPressed(evt);
            }
        });
        FormInput.add(PeninjauanKembaliPerawatKamarOK);
        PeninjauanKembaliPerawatKamarOK.setBounds(709, 240, 80, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("Perhatian Utama Fase Pemulihan");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(40, 270, 180, 23);

        jLabel58.setText(":");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(0, 270, 209, 23);

        PerhatianUtamaFasePemulihan.setHighlighter(null);
        PerhatianUtamaFasePemulihan.setName("PerhatianUtamaFasePemulihan"); // NOI18N
        PerhatianUtamaFasePemulihan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerhatianUtamaFasePemulihanKeyPressed(evt);
            }
        });
        FormInput.add(PerhatianUtamaFasePemulihan);
        PerhatianUtamaFasePemulihan.setBounds(213, 270, 576, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            //Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(KodeDokterBedah.getText().trim().equals("")||NamaDokterBedah.getText().trim().equals("")){
            Valid.textKosong(btnDokterBedah,"Dokter Bedah");
        }else if(KodeDokterAnestesi.getText().trim().equals("")||NamaDokterAnestesi.getText().trim().equals("")){
            Valid.textKosong(KodeDokterAnestesi,"Dokter Anestesi");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan");
        }else if(SNCN.getText().trim().equals("")){
            Valid.textKosong(SNCN,"SN/CN");
        }else{
            if(Sequel.menyimpantf("signout_sebelum_menutup_luka","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",17,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),SNCN.getText(),Tindakan.getText(),
                KodeDokterBedah.getText(),KodeDokterAnestesi.getText(),VerbalTindakan.getSelectedItem().toString(),VerbalKasa.getSelectedItem().toString(), 
                VerbalInstrumen.getSelectedItem().toString(),VerbalAlatTajam.getSelectedItem().toString(),KelengkapanSpesimenLabel.getSelectedItem().toString(), 
                KelengkapanSpesimenFormulir.getSelectedItem().toString(),PeninjauanKembaliDokterBedah.getSelectedItem().toString(),PeninjauanKembaliDokterAnestesi.getSelectedItem().toString(), 
                PeninjauanKembaliPerawatKamarOK.getSelectedItem().toString(),PerhatianUtamaFasePemulihan.getText(),KdPetugasOK.getText()
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
            Valid.pindah(evt,btnPetugasOK,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
        emptTeks();
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
            }else {
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"User Login harus Petugas OK/Dokter Anestesi..!!");
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
        }else if(KodeDokterBedah.getText().trim().equals("")||NamaDokterBedah.getText().trim().equals("")){
            Valid.textKosong(btnDokterBedah,"Dokter Bedah");
        }else if(KodeDokterAnestesi.getText().trim().equals("")||NamaDokterAnestesi.getText().trim().equals("")){
            Valid.textKosong(KodeDokterAnestesi,"Dokter Anestesi");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan");
        }else if(SNCN.getText().trim().equals("")){
            Valid.textKosong(SNCN,"SN/CN");
        }else{  
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"User Login harus Petugas OK/Dokter Anestesi..!!");
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
                if(TCari.getText().trim().equals("")){
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,signout_sebelum_menutup_luka.tanggal,"+
                        "signout_sebelum_menutup_luka.sncn,signout_sebelum_menutup_luka.tindakan,signout_sebelum_menutup_luka.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                        "signout_sebelum_menutup_luka.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,signout_sebelum_menutup_luka.verbal_tindakan,"+
                        "signout_sebelum_menutup_luka.verbal_kelengkapan_kasa,signout_sebelum_menutup_luka.verbal_instrumen,signout_sebelum_menutup_luka.verbal_alat_tajam,"+
                        "signout_sebelum_menutup_luka.kelengkapan_specimen_label,signout_sebelum_menutup_luka.kelengkapan_specimen_formulir,"+
                        "signout_sebelum_menutup_luka.peninjauan_kegiatan_dokter_bedah,signout_sebelum_menutup_luka.peninjauan_kegiatan_dokter_anestesi,"+
                        "signout_sebelum_menutup_luka.peninjauan_kegiatan_perawat_kamar_ok,signout_sebelum_menutup_luka.perhatian_utama_fase_pemulihan,"+
                        "signout_sebelum_menutup_luka.nip_perawat_ok,petugas.nama from signout_sebelum_menutup_luka inner join reg_periksa "+
                        "on signout_sebelum_menutup_luka.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join dokter as dokterbedah on dokterbedah.kd_dokter=signout_sebelum_menutup_luka.kd_dokter_bedah "+
                        "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=signout_sebelum_menutup_luka.kd_dokter_anestesi "+
                        "inner join petugas on petugas.nip=signout_sebelum_menutup_luka.nip_perawat_ok "+
                        "where signout_sebelum_menutup_luka.tanggal between ? and ? order by signout_sebelum_menutup_luka.tanggal ");
                }else{
                    ps=koneksi.prepareStatement(
                        "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,signout_sebelum_menutup_luka.tanggal,"+
                        "signout_sebelum_menutup_luka.sncn,signout_sebelum_menutup_luka.tindakan,signout_sebelum_menutup_luka.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                        "signout_sebelum_menutup_luka.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,signout_sebelum_menutup_luka.verbal_tindakan,"+
                        "signout_sebelum_menutup_luka.verbal_kelengkapan_kasa,signout_sebelum_menutup_luka.verbal_instrumen,signout_sebelum_menutup_luka.verbal_alat_tajam,"+
                        "signout_sebelum_menutup_luka.kelengkapan_specimen_label,signout_sebelum_menutup_luka.kelengkapan_specimen_formulir,"+
                        "signout_sebelum_menutup_luka.peninjauan_kegiatan_dokter_bedah,signout_sebelum_menutup_luka.peninjauan_kegiatan_dokter_anestesi,"+
                        "signout_sebelum_menutup_luka.peninjauan_kegiatan_perawat_kamar_ok,signout_sebelum_menutup_luka.perhatian_utama_fase_pemulihan,"+
                        "signout_sebelum_menutup_luka.nip_perawat_ok,petugas.nama from signout_sebelum_menutup_luka inner join reg_periksa "+
                        "on signout_sebelum_menutup_luka.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join dokter as dokterbedah on dokterbedah.kd_dokter=signout_sebelum_menutup_luka.kd_dokter_bedah "+
                        "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=signout_sebelum_menutup_luka.kd_dokter_anestesi "+
                        "inner join petugas on petugas.nip=signout_sebelum_menutup_luka.nip_perawat_ok "+
                        "where signout_sebelum_menutup_luka.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                        "pasien.nm_pasien like ? or dokterbedah.nm_dokter like ? or dokteranestesi.nm_dokter like ? or petugas.nama like ?) "+
                        "order by signout_sebelum_menutup_luka.tanggal ");
                }

                try {
                    if(TCari.getText().trim().equals("")){
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
                    }
                    rs=ps.executeQuery();
                    htmlContent = new StringBuilder();
                    htmlContent.append(                             
                        "<tr class='isi'>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.Rawat</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>No.RM</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Pasien</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tgl.Lahir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>J.K.</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tanggal</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>SN/CN</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Tindakan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter Bedah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter Bedah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kode Dokter Anest</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Nama Dokter Anestesi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Verbal Tindakan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Verbal Kasa</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Verbal Instrumen</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Verbal Alat Tajam</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kelengkapan Spesimen Label</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Kelengkapan Spesimen Formulir</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>P.K.Dokter Bedah</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>P.K.Dokter Anestesi</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>P.K.Perawat OK</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Perhatian Utama Fase Pemulihan</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>NIP OK</b></td>"+
                            "<td valign='middle' bgcolor='#FFFAF8' align='center'><b>Petugas Ruang OK</b></td>"+
                        "</tr>"
                    );
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                               "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("tgl_lahir")+"</td>"+
                               "<td valign='top'>"+rs.getString("jk")+"</td>"+
                               "<td valign='top'>"+rs.getString("tanggal")+"</td>"+
                               "<td valign='top'>"+rs.getString("sncn")+"</td>"+
                               "<td valign='top'>"+rs.getString("tindakan")+"</td>"+
                               "<td valign='top'>"+rs.getString("kd_dokter_bedah")+"</td>"+
                               "<td valign='top'>"+rs.getString("dokterbedah")+"</td>"+
                               "<td valign='top'>"+rs.getString("kd_dokter_anestesi")+"</td>"+
                               "<td valign='top'>"+rs.getString("dokteranestesi")+"</td>"+
                               "<td valign='top'>"+rs.getString("verbal_tindakan")+"</td>"+
                               "<td valign='top'>"+rs.getString("verbal_kelengkapan_kasa")+"</td>"+
                               "<td valign='top'>"+rs.getString("verbal_instrumen")+"</td>"+
                               "<td valign='top'>"+rs.getString("verbal_alat_tajam")+"</td>"+
                               "<td valign='top'>"+rs.getString("kelengkapan_specimen_label")+"</td>"+
                               "<td valign='top'>"+rs.getString("kelengkapan_specimen_formulir")+"</td>"+
                               "<td valign='top'>"+rs.getString("peninjauan_kegiatan_dokter_bedah")+"</td>"+
                               "<td valign='top'>"+rs.getString("peninjauan_kegiatan_dokter_anestesi")+"</td>"+
                               "<td valign='top'>"+rs.getString("peninjauan_kegiatan_perawat_kamar_ok")+"</td>"+
                               "<td valign='top'>"+rs.getString("perhatian_utama_fase_pemulihan")+"</td>"+
                               "<td valign='top'>"+rs.getString("nip_perawat_ok")+"</td>"+
                               "<td valign='top'>"+rs.getString("nama")+"</td>"+
                            "</tr>");
                    }
                    LoadHTML.setText(
                        "<html>"+
                          "<table width='2200px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                    File f = new File("DataSignOutSebelumMenutupLuka.html");            
                    BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                    bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                "<table width='2200px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi2'>"+
                                        "<td valign='top' align='center'>"+
                                            "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                            akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                            akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                            "<font size='2' face='Tahoma'>DATA SIGN-OUT SEBELUM MENUTUP LUKA/MENINGGALKAN KAMAR OPERASI<br><br></font>"+        
                                        "</td>"+
                                   "</tr>"+
                                "</table>")
                    );
                    bw.close();                         
                    Desktop.getDesktop().browse(f.toURI());
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

    private void MnSignOutSebelumMenutupLukaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSignOutSebelumMenutupLukaActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),23).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),22).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            finger2=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()+"\nID "+(finger2.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),10).toString():finger2)+"\n"+Tanggal.getSelectedItem()); 
            finger3=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            param.put("finger3","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()+"\nID "+(finger3.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),8).toString():finger3)+"\n"+Tanggal.getSelectedItem());
            Valid.MyReportqry("rptFormulirSignOutSebelumMenutupLuka.jasper","report","::[ Formulir Sign-Out Sebelum Menutup Luka ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,signout_sebelum_menutup_luka.tanggal,"+
                    "signout_sebelum_menutup_luka.sncn,signout_sebelum_menutup_luka.tindakan,signout_sebelum_menutup_luka.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "signout_sebelum_menutup_luka.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,signout_sebelum_menutup_luka.verbal_tindakan,"+
                    "signout_sebelum_menutup_luka.verbal_kelengkapan_kasa,signout_sebelum_menutup_luka.verbal_instrumen,signout_sebelum_menutup_luka.verbal_alat_tajam,"+
                    "signout_sebelum_menutup_luka.kelengkapan_specimen_label,signout_sebelum_menutup_luka.kelengkapan_specimen_formulir,"+
                    "signout_sebelum_menutup_luka.peninjauan_kegiatan_dokter_bedah,signout_sebelum_menutup_luka.peninjauan_kegiatan_dokter_anestesi,"+
                    "signout_sebelum_menutup_luka.peninjauan_kegiatan_perawat_kamar_ok,signout_sebelum_menutup_luka.perhatian_utama_fase_pemulihan,"+
                    "signout_sebelum_menutup_luka.nip_perawat_ok,petugas.nama from signout_sebelum_menutup_luka inner join reg_periksa "+
                    "on signout_sebelum_menutup_luka.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=signout_sebelum_menutup_luka.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=signout_sebelum_menutup_luka.kd_dokter_anestesi "+
                    "inner join petugas on petugas.nip=signout_sebelum_menutup_luka.nip_perawat_ok  "+
                    "where signout_sebelum_menutup_luka.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and signout_sebelum_menutup_luka.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnSignOutSebelumMenutupLukaActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
       // Valid.pindah(evt,Rencana,Informasi);
    }//GEN-LAST:event_TanggalKeyPressed

    private void btnDokterBedahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterBedahActionPerformed
        pilihan=1;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterBedahActionPerformed

    private void btnDokterBedahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterBedahKeyPressed
        Valid.pindah(evt,Tindakan,btnDokterAnestesi);
    }//GEN-LAST:event_btnDokterBedahKeyPressed

    private void btnDokterAnestesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterAnestesiActionPerformed
        pilihan=2;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterAnestesiActionPerformed

    private void btnDokterAnestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterAnestesiKeyPressed
        Valid.pindah(evt,btnDokterBedah,VerbalTindakan);
    }//GEN-LAST:event_btnDokterAnestesiKeyPressed

    private void btnPetugasOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasOKActionPerformed
        pilihan=2;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasOKActionPerformed

    private void btnPetugasOKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasOKKeyPressed
        Valid.pindah(evt,PerhatianUtamaFasePemulihan,BtnSimpan);
    }//GEN-LAST:event_btnPetugasOKKeyPressed

    private void SNCNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SNCNKeyPressed
        Valid.pindah(evt,Tanggal,Tindakan);
    }//GEN-LAST:event_SNCNKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah(evt,SNCN,btnDokterBedah);
    }//GEN-LAST:event_TindakanKeyPressed

    private void VerbalTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VerbalTindakanKeyPressed
        Valid.pindah(evt,btnDokterAnestesi,VerbalKasa);
    }//GEN-LAST:event_VerbalTindakanKeyPressed

    private void VerbalKasaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VerbalKasaKeyPressed
        Valid.pindah(evt,VerbalTindakan,VerbalInstrumen);
    }//GEN-LAST:event_VerbalKasaKeyPressed

    private void VerbalInstrumenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VerbalInstrumenKeyPressed
        Valid.pindah(evt,VerbalTindakan,VerbalAlatTajam);
    }//GEN-LAST:event_VerbalInstrumenKeyPressed

    private void VerbalAlatTajamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VerbalAlatTajamKeyPressed
        Valid.pindah(evt,VerbalInstrumen,KelengkapanSpesimenLabel);
    }//GEN-LAST:event_VerbalAlatTajamKeyPressed

    private void KelengkapanSpesimenLabelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelengkapanSpesimenLabelKeyPressed
        Valid.pindah(evt,VerbalAlatTajam,KelengkapanSpesimenFormulir);
    }//GEN-LAST:event_KelengkapanSpesimenLabelKeyPressed

    private void KelengkapanSpesimenFormulirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KelengkapanSpesimenFormulirKeyPressed
        Valid.pindah(evt,KelengkapanSpesimenLabel,PeninjauanKembaliDokterBedah);
    }//GEN-LAST:event_KelengkapanSpesimenFormulirKeyPressed

    private void PeninjauanKembaliDokterBedahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PeninjauanKembaliDokterBedahKeyPressed
        Valid.pindah(evt,KelengkapanSpesimenFormulir,PeninjauanKembaliDokterAnestesi);
    }//GEN-LAST:event_PeninjauanKembaliDokterBedahKeyPressed

    private void PeninjauanKembaliDokterAnestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PeninjauanKembaliDokterAnestesiKeyPressed
        Valid.pindah(evt,PeninjauanKembaliDokterBedah,PeninjauanKembaliPerawatKamarOK);
    }//GEN-LAST:event_PeninjauanKembaliDokterAnestesiKeyPressed

    private void PeninjauanKembaliPerawatKamarOKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PeninjauanKembaliPerawatKamarOKKeyPressed
        Valid.pindah(evt,PeninjauanKembaliDokterAnestesi,PerhatianUtamaFasePemulihan);
    }//GEN-LAST:event_PeninjauanKembaliPerawatKamarOKKeyPressed

    private void PerhatianUtamaFasePemulihanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerhatianUtamaFasePemulihanKeyPressed
        Valid.pindah(evt,PeninjauanKembaliPerawatKamarOK,btnPetugasOK);
    }//GEN-LAST:event_PerhatianUtamaFasePemulihanKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMSignOutSebelumMenutupLuka dialog = new RMSignOutSebelumMenutupLuka(new javax.swing.JFrame(), true);
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
    private widget.TextBox KdPetugasOK;
    private widget.ComboBox KelengkapanSpesimenFormulir;
    private widget.ComboBox KelengkapanSpesimenLabel;
    private widget.TextBox KodeDokterAnestesi;
    private widget.TextBox KodeDokterBedah;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private javax.swing.JMenuItem MnSignOutSebelumMenutupLuka;
    private widget.TextBox NamaDokterAnestesi;
    private widget.TextBox NamaDokterBedah;
    private widget.TextBox NmPetugasOK;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox PeninjauanKembaliDokterAnestesi;
    private widget.ComboBox PeninjauanKembaliDokterBedah;
    private widget.ComboBox PeninjauanKembaliPerawatKamarOK;
    private widget.TextBox PerhatianUtamaFasePemulihan;
    private widget.TextBox SNCN;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TglLahir;
    private widget.TextBox Tindakan;
    private widget.ComboBox VerbalAlatTajam;
    private widget.ComboBox VerbalInstrumen;
    private widget.ComboBox VerbalKasa;
    private widget.ComboBox VerbalTindakan;
    private widget.Button btnDokterAnestesi;
    private widget.Button btnDokterBedah;
    private widget.Button btnPetugasOK;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
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
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables
    
    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,signout_sebelum_menutup_luka.tanggal,"+
                    "signout_sebelum_menutup_luka.sncn,signout_sebelum_menutup_luka.tindakan,signout_sebelum_menutup_luka.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "signout_sebelum_menutup_luka.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,signout_sebelum_menutup_luka.verbal_tindakan,"+
                    "signout_sebelum_menutup_luka.verbal_kelengkapan_kasa,signout_sebelum_menutup_luka.verbal_instrumen,signout_sebelum_menutup_luka.verbal_alat_tajam,"+
                    "signout_sebelum_menutup_luka.kelengkapan_specimen_label,signout_sebelum_menutup_luka.kelengkapan_specimen_formulir,"+
                    "signout_sebelum_menutup_luka.peninjauan_kegiatan_dokter_bedah,signout_sebelum_menutup_luka.peninjauan_kegiatan_dokter_anestesi,"+
                    "signout_sebelum_menutup_luka.peninjauan_kegiatan_perawat_kamar_ok,signout_sebelum_menutup_luka.perhatian_utama_fase_pemulihan,"+
                    "signout_sebelum_menutup_luka.nip_perawat_ok,petugas.nama from signout_sebelum_menutup_luka inner join reg_periksa "+
                    "on signout_sebelum_menutup_luka.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=signout_sebelum_menutup_luka.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=signout_sebelum_menutup_luka.kd_dokter_anestesi "+
                    "inner join petugas on petugas.nip=signout_sebelum_menutup_luka.nip_perawat_ok "+
                    "where signout_sebelum_menutup_luka.tanggal between ? and ? order by signout_sebelum_menutup_luka.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,signout_sebelum_menutup_luka.tanggal,"+
                    "signout_sebelum_menutup_luka.sncn,signout_sebelum_menutup_luka.tindakan,signout_sebelum_menutup_luka.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "signout_sebelum_menutup_luka.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,signout_sebelum_menutup_luka.verbal_tindakan,"+
                    "signout_sebelum_menutup_luka.verbal_kelengkapan_kasa,signout_sebelum_menutup_luka.verbal_instrumen,signout_sebelum_menutup_luka.verbal_alat_tajam,"+
                    "signout_sebelum_menutup_luka.kelengkapan_specimen_label,signout_sebelum_menutup_luka.kelengkapan_specimen_formulir,"+
                    "signout_sebelum_menutup_luka.peninjauan_kegiatan_dokter_bedah,signout_sebelum_menutup_luka.peninjauan_kegiatan_dokter_anestesi,"+
                    "signout_sebelum_menutup_luka.peninjauan_kegiatan_perawat_kamar_ok,signout_sebelum_menutup_luka.perhatian_utama_fase_pemulihan,"+
                    "signout_sebelum_menutup_luka.nip_perawat_ok,petugas.nama from signout_sebelum_menutup_luka inner join reg_periksa "+
                    "on signout_sebelum_menutup_luka.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=signout_sebelum_menutup_luka.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=signout_sebelum_menutup_luka.kd_dokter_anestesi "+
                    "inner join petugas on petugas.nip=signout_sebelum_menutup_luka.nip_perawat_ok "+
                    "where signout_sebelum_menutup_luka.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or dokterbedah.nm_dokter like ? or dokteranestesi.nm_dokter like ? or petugas.nama like ?) "+
                    "order by signout_sebelum_menutup_luka.tanggal ");
            }
                
            try {
                if(TCari.getText().trim().equals("")){
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
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),
                        rs.getString("tanggal"),rs.getString("sncn"),rs.getString("tindakan"),rs.getString("kd_dokter_bedah"),rs.getString("dokterbedah"),
                        rs.getString("kd_dokter_anestesi"),rs.getString("dokteranestesi"),rs.getString("verbal_tindakan"),rs.getString("verbal_kelengkapan_kasa"),
                        rs.getString("verbal_instrumen"),rs.getString("verbal_alat_tajam"),rs.getString("kelengkapan_specimen_label"),
                        rs.getString("kelengkapan_specimen_formulir"),rs.getString("peninjauan_kegiatan_dokter_bedah"),rs.getString("peninjauan_kegiatan_dokter_anestesi"),
                        rs.getString("peninjauan_kegiatan_perawat_kamar_ok"),rs.getString("perhatian_utama_fase_pemulihan"),rs.getString("nip_perawat_ok"),
                        rs.getString("nama")
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
        SNCN.setText("");
        Tindakan.setText("");
        KodeDokterBedah.setText("");
        NamaDokterBedah.setText("");
        KodeDokterAnestesi.setText("");
        NamaDokterAnestesi.setText("");
        Tanggal.setDate(new Date());
        VerbalTindakan.setSelectedIndex(0);
        VerbalKasa.setSelectedIndex(0);
        VerbalInstrumen.setSelectedIndex(0);
        VerbalAlatTajam.setSelectedIndex(0);
        KelengkapanSpesimenLabel.setSelectedIndex(0);
        KelengkapanSpesimenFormulir.setSelectedIndex(0);
        PeninjauanKembaliDokterBedah.setSelectedIndex(0);
        PeninjauanKembaliDokterAnestesi.setSelectedIndex(0);
        PeninjauanKembaliPerawatKamarOK.setSelectedIndex(0);
        PerhatianUtamaFasePemulihan.setText("");
        SNCN.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            SNCN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KodeDokterBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NamaDokterBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KodeDokterAnestesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NamaDokterAnestesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            VerbalTindakan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            VerbalKasa.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            VerbalInstrumen.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            VerbalAlatTajam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            KelengkapanSpesimenLabel.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            KelengkapanSpesimenFormulir.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            PeninjauanKembaliDokterBedah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            PeninjauanKembaliDokterAnestesi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            PeninjauanKembaliPerawatKamarOK.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            PerhatianUtamaFasePemulihan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KdPetugasOK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            NmPetugasOK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
        }
    }
    private void isRawat() {
         Sequel.cariIsi("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
        Sequel.cariIsi("select DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') from pasien where pasien.no_rkm_medis=? ",TglLahir,TNoRM.getText());
    }
    
    public void setNoRm(String norwt, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
    }
    
    public void setNoRm(String norwt, Date tgl2,String KodeDokter,String NamaDokter,String Operasi) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        Sequel.cariIsi("select reg_periksa.tgl_registrasi from reg_periksa where reg_periksa.no_rawat='"+norwt+"'", DTPCari1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();
        ChkInput.setSelected(true);
        isForm();
        KodeDokterBedah.setText(KodeDokter);
        NamaDokterBedah.setText(NamaDokter);
        Tindakan.setText(Operasi);
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>538){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,366));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }else{
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-172));
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
        BtnSimpan.setEnabled(akses.getsignout_sebelum_menutup_luka());
        BtnHapus.setEnabled(akses.getsignout_sebelum_menutup_luka());
        BtnEdit.setEnabled(akses.getsignout_sebelum_menutup_luka());
        BtnPrint.setEnabled(akses.getsignout_sebelum_menutup_luka()); 
    }

    private void ganti() {
        Sequel.mengedit("signout_sebelum_menutup_luka","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,sncn=?,tindakan=?,kd_dokter_bedah=?,kd_dokter_anestesi=?,"+
                "verbal_tindakan=?,verbal_kelengkapan_kasa=?,verbal_instrumen=?,verbal_alat_tajam=?,kelengkapan_specimen_label=?,kelengkapan_specimen_formulir=?,"+
                "peninjauan_kegiatan_dokter_bedah=?,peninjauan_kegiatan_dokter_anestesi=?,peninjauan_kegiatan_perawat_kamar_ok=?,perhatian_utama_fase_pemulihan=?,"+
                "nip_perawat_ok=?",19,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),SNCN.getText(),Tindakan.getText(),
                KodeDokterBedah.getText(),KodeDokterAnestesi.getText(),VerbalTindakan.getSelectedItem().toString(),VerbalKasa.getSelectedItem().toString(), 
                VerbalInstrumen.getSelectedItem().toString(),VerbalAlatTajam.getSelectedItem().toString(),KelengkapanSpesimenLabel.getSelectedItem().toString(), 
                KelengkapanSpesimenFormulir.getSelectedItem().toString(),PeninjauanKembaliDokterBedah.getSelectedItem().toString(),PeninjauanKembaliDokterAnestesi.getSelectedItem().toString(), 
                PeninjauanKembaliPerawatKamarOK.getSelectedItem().toString(),PerhatianUtamaFasePemulihan.getText(),KdPetugasOK.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        });
            
        if(tabMode.getRowCount()!=0){tampil();}
        emptTeks();
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from signout_sebelum_menutup_luka where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
}

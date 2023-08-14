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
public final class RMChecklistPostOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private String finger="",finger2="";
    private StringBuilder htmlContent;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMChecklistPostOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","SN/CN","Tindakan","Kode Dokter Bedah","Nama Dokter Bedah",
            "Kode Dokter Anest","Nama Dokter Anestesi","Keadaan Umum","Radiologi","Keterangan Radiologi","EKG","Keterangan EKG","USG",
            "Keterangan USG","CT Scan","Keterangan CT Scan","MRI","Keterangan MRI","Jenis Cairan Infus","Kateter Urine","Tgl.Pemasangan",
            "Warna Urine","Jml.Urine","Area Luka Operasi","Drain","Jml.Drain","Letak Drain","Warna Drain","Jaringan PA","NIP OK",
            "Petugas Ruang OK","NIP Anestesi","Petugas Anestesi"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 38; i++) {
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
                column.setPreferredWidth(85);
            }else if(i==13){
                column.setPreferredWidth(58);
            }else if(i==14){
                column.setPreferredWidth(115);
            }else if(i==15){
                column.setPreferredWidth(58);
            }else if(i==16){
                column.setPreferredWidth(100);
            }else if(i==17){
                column.setPreferredWidth(58);
            }else if(i==18){
                column.setPreferredWidth(100);
            }else if(i==19){
                column.setPreferredWidth(58);
            }else if(i==20){
                column.setPreferredWidth(110);
            }else if(i==21){
                column.setPreferredWidth(58);
            }else if(i==22){
                column.setPreferredWidth(100);
            }else if(i==23){
                column.setPreferredWidth(110);
            }else if(i==24){
                column.setPreferredWidth(75);
            }else if(i==25){
                column.setPreferredWidth(115);
            }else if(i==26){
                column.setPreferredWidth(68);
            }else if(i==27){
                column.setPreferredWidth(55);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else if(i==29){
                column.setPreferredWidth(58);
            }else if(i==30){
                column.setPreferredWidth(57);
            }else if(i==31){
                column.setPreferredWidth(120);
            }else if(i==32){
                column.setPreferredWidth(90);
            }else if(i==33){
                column.setPreferredWidth(70);
            }else if(i==34){
                column.setPreferredWidth(90);
            }else if(i==35){
                column.setPreferredWidth(150);
            }else if(i==36){
                column.setPreferredWidth(90);
            }else if(i==37){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        SNCN.setDocument(new batasInput((byte)25).getKata(SNCN));
        Tindakan.setDocument(new batasInput((byte)50).getKata(Tindakan));
        JumlahDrain.setDocument(new batasInput((byte)20).getKata(JumlahDrain));
        KeteranganRadiologi.setDocument(new batasInput((byte)20).getKata(KeteranganRadiologi));
        KeteranganEKG.setDocument(new batasInput((byte)20).getKata(KeteranganEKG));
        KeteranganUSG.setDocument(new batasInput((byte)20).getKata(KeteranganUSG));
        KeteranganCTScan.setDocument(new batasInput((byte)20).getKata(KeteranganCTScan));
        KeteranganMRI.setDocument(new batasInput((byte)20).getKata(KeteranganMRI));
        CairanInfus.setDocument(new batasInput((byte)40).getKata(CairanInfus));
        JumlahDrain.setDocument(new batasInput((byte)2).getKata(JumlahDrain));
        LetakDrain.setDocument(new batasInput((byte)40).getKata(LetakDrain));
        WarnaDrain.setDocument(new batasInput((byte)30).getKata(WarnaDrain));
        AreaLukaOperasi.setDocument(new batasInput((int)120).getKata(AreaLukaOperasi));
        JumlahUrine.setDocument(new batasInput((byte)4).getKata(JumlahUrine));
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
                    if(pilihan==1){
                        KdPetugasAnest.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasAnest.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        btnPetugasRuangan.requestFocus();
                    }else if(pilihan==2){
                        KdPetugasOK.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasOK.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        btnPetugasOK.requestFocus();
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
        MnPostOperasi = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        JK = new widget.TextBox();
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
        jLabel18 = new widget.Label();
        KdPetugasAnest = new widget.TextBox();
        NmPetugasAnest = new widget.TextBox();
        btnPetugasRuangan = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel20 = new widget.Label();
        JumlahDrain = new widget.TextBox();
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
        KeadaanUmum = new widget.ComboBox();
        jLabel52 = new widget.Label();
        Drain = new widget.ComboBox();
        Tindakan = new widget.TextBox();
        jLabel57 = new widget.Label();
        jLabel59 = new widget.Label();
        Radiologi = new widget.ComboBox();
        KeteranganRadiologi = new widget.TextBox();
        jLabel60 = new widget.Label();
        KeteranganEKG = new widget.TextBox();
        EKG = new widget.ComboBox();
        jLabel61 = new widget.Label();
        CTScan = new widget.ComboBox();
        KeteranganCTScan = new widget.TextBox();
        jLabel62 = new widget.Label();
        USG = new widget.ComboBox();
        KeteranganUSG = new widget.TextBox();
        jLabel63 = new widget.Label();
        MRI = new widget.ComboBox();
        KeteranganMRI = new widget.TextBox();
        jLabel26 = new widget.Label();
        KdPetugasOK = new widget.TextBox();
        NmPetugasOK = new widget.TextBox();
        btnPetugasOK = new widget.Button();
        jLabel5 = new widget.Label();
        jLabel65 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        AreaLukaOperasi = new widget.TextBox();
        KateterUrine = new widget.ComboBox();
        jLabel53 = new widget.Label();
        TanggalKateter = new widget.Tanggal();
        jLabel17 = new widget.Label();
        jLabel54 = new widget.Label();
        WarnaUrine = new widget.ComboBox();
        jLabel29 = new widget.Label();
        JumlahUrine = new widget.TextBox();
        jLabel30 = new widget.Label();
        jLabel55 = new widget.Label();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        jLabel33 = new widget.Label();
        LetakDrain = new widget.TextBox();
        WarnaDrain = new widget.TextBox();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        CairanInfus = new widget.TextBox();
        jLabel64 = new widget.Label();
        JaringanPA = new widget.ComboBox();
        jLabel36 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPostOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnPostOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPostOperasi.setForeground(new java.awt.Color(50, 50, 50));
        MnPostOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPostOperasi.setText("Formulir Checklist Post Operasi");
        MnPostOperasi.setName("MnPostOperasi"); // NOI18N
        MnPostOperasi.setPreferredSize(new java.awt.Dimension(260, 26));
        MnPostOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPostOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPostOperasi);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        JK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JKKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Check List Post Operasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-06-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-06-2023" }));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 386));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 363));
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

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Petugas Anestesi");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(21, 330, 103, 23);

        KdPetugasAnest.setEditable(false);
        KdPetugasAnest.setHighlighter(null);
        KdPetugasAnest.setName("KdPetugasAnest"); // NOI18N
        FormInput.add(KdPetugasAnest);
        KdPetugasAnest.setBounds(117, 330, 95, 23);

        NmPetugasAnest.setEditable(false);
        NmPetugasAnest.setName("NmPetugasAnest"); // NOI18N
        FormInput.add(NmPetugasAnest);
        NmPetugasAnest.setBounds(214, 330, 165, 23);

        btnPetugasRuangan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasRuangan.setMnemonic('2');
        btnPetugasRuangan.setToolTipText("ALt+2");
        btnPetugasRuangan.setName("btnPetugasRuangan"); // NOI18N
        btnPetugasRuangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasRuanganActionPerformed(evt);
            }
        });
        btnPetugasRuangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasRuanganKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugasRuangan);
        btnPetugasRuangan.setBounds(381, 330, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 100, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-06-2023 20:02:36" }));
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
        jLabel20.setText("Serah Terima Perawat Kamar Operasi Dengan Anestesi / Intensif / Ruangan. Perawat Melakukan Serah Terima Secara Verbal :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(21, 100, 660, 23);

        JumlahDrain.setHighlighter(null);
        JumlahDrain.setName("JumlahDrain"); // NOI18N
        JumlahDrain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahDrainKeyPressed(evt);
            }
        });
        FormInput.add(JumlahDrain);
        JumlahDrain.setBounds(356, 180, 40, 23);

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

        KeadaanUmum.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sadar", "Tidur", "Terintubasi" }));
        KeadaanUmum.setName("KeadaanUmum"); // NOI18N
        KeadaanUmum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeadaanUmumKeyPressed(evt);
            }
        });
        FormInput.add(KeadaanUmum);
        KeadaanUmum.setBounds(144, 120, 110, 23);

        jLabel52.setText(":");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(0, 120, 140, 23);

        Drain.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        Drain.setName("Drain"); // NOI18N
        Drain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DrainKeyPressed(evt);
            }
        });
        FormInput.add(Drain);
        Drain.setBounds(144, 180, 110, 23);

        Tindakan.setHighlighter(null);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        FormInput.add(Tindakan);
        Tindakan.setBounds(79, 70, 305, 23);

        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel57.setText("Kelengkapan Penunjang :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(56, 210, 210, 23);

        jLabel59.setText("Radiologi :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(66, 230, 74, 23);

        Radiologi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        Radiologi.setName("Radiologi"); // NOI18N
        Radiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RadiologiKeyPressed(evt);
            }
        });
        FormInput.add(Radiologi);
        Radiologi.setBounds(144, 230, 100, 23);

        KeteranganRadiologi.setHighlighter(null);
        KeteranganRadiologi.setName("KeteranganRadiologi"); // NOI18N
        KeteranganRadiologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganRadiologiKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganRadiologi);
        KeteranganRadiologi.setBounds(247, 230, 75, 23);

        jLabel60.setText("EKG :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(329, 230, 55, 23);

        KeteranganEKG.setHighlighter(null);
        KeteranganEKG.setName("KeteranganEKG"); // NOI18N
        KeteranganEKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganEKGKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganEKG);
        KeteranganEKG.setBounds(491, 230, 75, 23);

        EKG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        EKG.setName("EKG"); // NOI18N
        EKG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EKGKeyPressed(evt);
            }
        });
        FormInput.add(EKG);
        EKG.setBounds(388, 230, 100, 23);

        jLabel61.setText("CT Scan :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(329, 260, 55, 23);

        CTScan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        CTScan.setName("CTScan"); // NOI18N
        CTScan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTScanKeyPressed(evt);
            }
        });
        FormInput.add(CTScan);
        CTScan.setBounds(388, 260, 100, 23);

        KeteranganCTScan.setHighlighter(null);
        KeteranganCTScan.setName("KeteranganCTScan"); // NOI18N
        KeteranganCTScan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganCTScanKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganCTScan);
        KeteranganCTScan.setBounds(491, 260, 75, 23);

        jLabel62.setText("USG :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(66, 260, 74, 23);

        USG.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        USG.setName("USG"); // NOI18N
        USG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                USGKeyPressed(evt);
            }
        });
        FormInput.add(USG);
        USG.setBounds(144, 260, 100, 23);

        KeteranganUSG.setHighlighter(null);
        KeteranganUSG.setName("KeteranganUSG"); // NOI18N
        KeteranganUSG.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganUSGKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganUSG);
        KeteranganUSG.setBounds(247, 260, 75, 23);

        jLabel63.setText("MRI :");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(567, 230, 40, 23);

        MRI.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        MRI.setName("MRI"); // NOI18N
        MRI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MRIKeyPressed(evt);
            }
        });
        FormInput.add(MRI);
        MRI.setBounds(611, 230, 100, 23);

        KeteranganMRI.setHighlighter(null);
        KeteranganMRI.setName("KeteranganMRI"); // NOI18N
        KeteranganMRI.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganMRIKeyPressed(evt);
            }
        });
        FormInput.add(KeteranganMRI);
        KeteranganMRI.setBounds(714, 230, 75, 23);

        jLabel26.setText("Petugas OK :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(423, 330, 70, 23);

        KdPetugasOK.setEditable(false);
        KdPetugasOK.setHighlighter(null);
        KdPetugasOK.setName("KdPetugasOK"); // NOI18N
        FormInput.add(KdPetugasOK);
        KdPetugasOK.setBounds(497, 330, 95, 23);

        NmPetugasOK.setEditable(false);
        NmPetugasOK.setName("NmPetugasOK"); // NOI18N
        FormInput.add(NmPetugasOK);
        NmPetugasOK.setBounds(594, 330, 165, 23);

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
        btnPetugasOK.setBounds(761, 330, 28, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 75, 23);

        jLabel65.setText("Drain :");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(0, 180, 140, 23);

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
        jSeparator3.setBounds(0, 320, 810, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 320, 810, 1);

        jLabel27.setText(":");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(3, 330, 110, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText("Area Luka Operasi");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(56, 290, 100, 23);

        AreaLukaOperasi.setHighlighter(null);
        AreaLukaOperasi.setName("AreaLukaOperasi"); // NOI18N
        AreaLukaOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AreaLukaOperasiKeyPressed(evt);
            }
        });
        FormInput.add(AreaLukaOperasi);
        AreaLukaOperasi.setBounds(156, 290, 633, 23);

        KateterUrine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        KateterUrine.setName("KateterUrine"); // NOI18N
        KateterUrine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KateterUrineKeyPressed(evt);
            }
        });
        FormInput.add(KateterUrine);
        KateterUrine.setBounds(144, 150, 110, 23);

        jLabel53.setText("Kateter Urine :");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(0, 150, 140, 23);

        TanggalKateter.setForeground(new java.awt.Color(50, 70, 50));
        TanggalKateter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-06-2023 20:02:37" }));
        TanggalKateter.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        TanggalKateter.setName("TanggalKateter"); // NOI18N
        TanggalKateter.setOpaque(false);
        TanggalKateter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKateterKeyPressed(evt);
            }
        });
        FormInput.add(TanggalKateter);
        TanggalKateter.setBounds(401, 150, 130, 23);

        jLabel17.setText("Jika Ada, Tgl.Pemasangan :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel17);
        jLabel17.setBounds(247, 150, 150, 23);

        jLabel54.setText(", Warna :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(529, 150, 52, 23);

        WarnaUrine.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Jernih", "Keruh" }));
        WarnaUrine.setName("WarnaUrine"); // NOI18N
        WarnaUrine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaUrineKeyPressed(evt);
            }
        });
        FormInput.add(WarnaUrine);
        WarnaUrine.setBounds(585, 150, 83, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("buah, ");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(399, 180, 40, 23);

        JumlahUrine.setHighlighter(null);
        JumlahUrine.setName("JumlahUrine"); // NOI18N
        JumlahUrine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahUrineKeyPressed(evt);
            }
        });
        FormInput.add(JumlahUrine);
        JumlahUrine.setBounds(727, 150, 45, 23);

        jLabel30.setText(", Jumlah :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(653, 150, 70, 23);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("Keadaan Umum");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(56, 120, 100, 23);

        jLabel31.setText("Jika Ada, Jumlah :");
        jLabel31.setName("jLabel31"); // NOI18N
        jLabel31.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel31);
        jLabel31.setBounds(247, 180, 105, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("cc");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(775, 150, 20, 23);

        jLabel33.setText("Letak :");
        jLabel33.setName("jLabel33"); // NOI18N
        jLabel33.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel33);
        jLabel33.setBounds(420, 180, 44, 23);

        LetakDrain.setHighlighter(null);
        LetakDrain.setName("LetakDrain"); // NOI18N
        LetakDrain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LetakDrainKeyPressed(evt);
            }
        });
        FormInput.add(LetakDrain);
        LetakDrain.setBounds(468, 180, 112, 23);

        WarnaDrain.setHighlighter(null);
        WarnaDrain.setName("WarnaDrain"); // NOI18N
        WarnaDrain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WarnaDrainKeyPressed(evt);
            }
        });
        FormInput.add(WarnaDrain);
        WarnaDrain.setBounds(677, 180, 112, 23);

        jLabel34.setText(", Warna/Produksi :");
        jLabel34.setName("jLabel34"); // NOI18N
        jLabel34.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel34);
        jLabel34.setBounds(583, 180, 90, 23);

        jLabel35.setText("Jenis Cairan Infus :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(256, 120, 110, 23);

        CairanInfus.setHighlighter(null);
        CairanInfus.setName("CairanInfus"); // NOI18N
        CairanInfus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CairanInfusKeyPressed(evt);
            }
        });
        FormInput.add(CairanInfus);
        CairanInfus.setBounds(370, 120, 150, 23);

        jLabel64.setText("Jaringan/Organ Tubuh PA/VC :");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(525, 120, 160, 23);

        JaringanPA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ada", "Tidak Ada" }));
        JaringanPA.setName("JaringanPA"); // NOI18N
        JaringanPA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JaringanPAKeyPressed(evt);
            }
        });
        FormInput.add(JaringanPA);
        JaringanPA.setBounds(689, 120, 100, 23);

        jLabel36.setText(":");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 290, 152, 23);

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
        }else if(KodeDokterBedah.getText().trim().equals("")||NamaDokterBedah.getText().trim().equals("")){
            Valid.textKosong(btnDokterBedah,"Dokter Bedah");
        }else if(KodeDokterAnestesi.getText().trim().equals("")||NamaDokterAnestesi.getText().trim().equals("")){
            Valid.textKosong(KodeDokterAnestesi,"Dokter Anestesi");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan");
        }else if(SNCN.getText().trim().equals("")){
            Valid.textKosong(SNCN,"SN/CN");
        }else{
            if(Sequel.menyimpantf("checklist_post_operasi","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",30,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),SNCN.getText(),Tindakan.getText(),
                KodeDokterBedah.getText(),KodeDokterAnestesi.getText(),KeadaanUmum.getSelectedItem().toString(),Radiologi.getSelectedItem().toString(),KeteranganRadiologi.getText(), 
                EKG.getSelectedItem().toString(),KeteranganEKG.getText(),USG.getSelectedItem().toString(),KeteranganUSG.getText(),CTScan.getSelectedItem().toString(),
                KeteranganCTScan.getText(),MRI.getSelectedItem().toString(),KeteranganMRI.getText(),CairanInfus.getText(),KateterUrine.getSelectedItem().toString(), 
                (KateterUrine.getSelectedIndex()==0?Valid.SetTgl(TanggalKateter.getSelectedItem()+"")+" "+TanggalKateter.getSelectedItem().toString().substring(11,19):"0000-00-00 00:00:0"),
                WarnaUrine.getSelectedItem().toString(),JumlahUrine.getText(),AreaLukaOperasi.getText(),Drain.getSelectedItem().toString(),JumlahDrain.getText(),LetakDrain.getText(),
                WarnaDrain.getText(),JaringanPA.getSelectedItem().toString(),KdPetugasOK.getText(),KdPetugasAnest.getText()
            })==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),
                    SNCN.getText(),Tindakan.getText(),KodeDokterBedah.getText(),NamaDokterBedah.getText(),KodeDokterAnestesi.getText(),NamaDokterAnestesi.getText(),KeadaanUmum.getSelectedItem().toString(),
                    Radiologi.getSelectedItem().toString(),KeteranganRadiologi.getText(),EKG.getSelectedItem().toString(),KeteranganEKG.getText(),USG.getSelectedItem().toString(),KeteranganUSG.getText(),
                    CTScan.getSelectedItem().toString(),KeteranganCTScan.getText(),MRI.getSelectedItem().toString(),KeteranganMRI.getText(),CairanInfus.getText(),KateterUrine.getSelectedItem().toString(),
                    (KateterUrine.getSelectedIndex()==0?Valid.SetTgl(TanggalKateter.getSelectedItem()+"")+" "+TanggalKateter.getSelectedItem().toString().substring(11,19):""),
                    WarnaUrine.getSelectedItem().toString(),JumlahUrine.getText(),AreaLukaOperasi.getText(),Drain.getSelectedItem().toString(),JumlahDrain.getText(),LetakDrain.getText(),WarnaDrain.getText(),
                    JaringanPA.getSelectedItem().toString(),KdPetugasOK.getText(),NmPetugasOK.getText(),KdPetugasAnest.getText(),NmPetugasAnest.getText()
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
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString())){
                    hapus();
                }else{
                    JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString())||akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString())){
                        ganti();
                    }else{
                        JOptionPane.showMessageDialog(null,"Harus salah satu petugas sesuai user login..!!");
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
        dokter.dispose();
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>SN/CN</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tindakan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Bedah</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Dokter Anest</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Dokter Anestesi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keadaan Umum</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Radiologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan Radiologi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>EKG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan EKG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>USG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan USG</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>CT Scan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan CT Scan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>MRI</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keterangan MRI</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jenis Cairan Infus</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kateter Urine</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tgl.Pemasangan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Warna Urine</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml.Urine</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Aarea Luka Operasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Drain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml.Drain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Letak Drain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Warna Drain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jaringan PA</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP OK</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas Ruang OK</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>NIP Anestesi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Petugas Anestesi</b></td>"+
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
                            "<td valign='top'>"+tbObat.getValueAt(i,34).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,35).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,36).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,37).toString()+"</td>"+
                        "</tr>");
                }
                
                LoadHTML.setText(
                    "<html>"+
                      "<table width='3500px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
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

                File f = new File("DataChecklistPostOperasi.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CHECK LIST POST OPERASI<br><br></font>"+        
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

    private void btnPetugasRuanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasRuanganActionPerformed
        pilihan=1;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasRuanganActionPerformed

    private void btnPetugasRuanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasRuanganKeyPressed
        Valid.pindah(evt,KeteranganMRI,btnPetugasOK);
    }//GEN-LAST:event_btnPetugasRuanganKeyPressed

    private void MnPostOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPostOperasiActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),35).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),34).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            finger2=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),37).toString()+"\nID "+(finger2.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),36).toString():finger2)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirChecklistPostOperasi.jasper","report","::[ Formulir Check List Post Operasi ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_post_operasi.tanggal,"+
                    "checklist_post_operasi.sncn,checklist_post_operasi.tindakan,checklist_post_operasi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "checklist_post_operasi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,checklist_post_operasi.keadaan_umum,"+
                    "checklist_post_operasi.pemeriksaan_penunjang_rontgen,checklist_post_operasi.keterangan_pemeriksaan_penunjang_rontgen,"+
                    "checklist_post_operasi.pemeriksaan_penunjang_ekg,checklist_post_operasi.keterangan_pemeriksaan_penunjang_ekg,checklist_post_operasi.pemeriksaan_penunjang_usg,"+
                    "checklist_post_operasi.keterangan_pemeriksaan_penunjang_usg,checklist_post_operasi.pemeriksaan_penunjang_ctscan,"+
                    "checklist_post_operasi.keterangan_pemeriksaan_penunjang_ctscan,checklist_post_operasi.pemeriksaan_penunjang_mri,"+
                    "checklist_post_operasi.keterangan_pemeriksaan_penunjang_mri,checklist_post_operasi.jenis_cairan_infus,checklist_post_operasi.kateter_urine,"+
                    "checklist_post_operasi.tanggal_pemasangan_kateter,checklist_post_operasi.warna_kateter,checklist_post_operasi.jumlah_kateter,"+
                    "checklist_post_operasi.area_luka_operasi,checklist_post_operasi.drain,checklist_post_operasi.jumlah_drain,checklist_post_operasi.letak_drain,"+
                    "checklist_post_operasi.warna_drain,checklist_post_operasi.jaringan_pa,checklist_post_operasi.nip_perawat_ok,petugasok.nama as petugasok,"+
                    "checklist_post_operasi.nip_perawat_anestesi,petugasanestesi.nama as petugasanestesi "+
                    "from checklist_post_operasi inner join reg_periksa on checklist_post_operasi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=checklist_post_operasi.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=checklist_post_operasi.kd_dokter_anestesi "+
                    "inner join petugas as petugasanestesi on petugasanestesi.nip=checklist_post_operasi.nip_perawat_anestesi "+
                    "inner join petugas as petugasok on petugasok.nip=checklist_post_operasi.nip_perawat_ok "+
                    "where checklist_post_operasi.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and checklist_post_operasi.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnPostOperasiActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
       Valid.pindah(evt,TCari,SNCN);
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
        Valid.pindah(evt,btnDokterBedah,KeadaanUmum);
    }//GEN-LAST:event_btnDokterAnestesiKeyPressed

    private void KeadaanUmumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeadaanUmumKeyPressed
        Valid.pindah(evt,btnDokterAnestesi,CairanInfus);
    }//GEN-LAST:event_KeadaanUmumKeyPressed

    private void DrainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DrainKeyPressed
       Valid.pindah(evt,JumlahUrine,JumlahDrain);
    }//GEN-LAST:event_DrainKeyPressed

    private void RadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RadiologiKeyPressed
        Valid.pindah(evt,WarnaDrain,KeteranganRadiologi);
    }//GEN-LAST:event_RadiologiKeyPressed

    private void EKGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EKGKeyPressed
        Valid.pindah(evt,KeteranganRadiologi,KeteranganEKG);
    }//GEN-LAST:event_EKGKeyPressed

    private void CTScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTScanKeyPressed
        Valid.pindah(evt,KeteranganUSG,KeteranganCTScan);
    }//GEN-LAST:event_CTScanKeyPressed

    private void USGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_USGKeyPressed
        Valid.pindah(evt,KeteranganEKG,KeteranganUSG);
    }//GEN-LAST:event_USGKeyPressed

    private void MRIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MRIKeyPressed
        Valid.pindah(evt,KeteranganCTScan,KeteranganMRI);
    }//GEN-LAST:event_MRIKeyPressed

    private void btnPetugasOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasOKActionPerformed
        pilihan=2;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasOKActionPerformed

    private void btnPetugasOKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasOKKeyPressed
        Valid.pindah(evt,btnPetugasRuangan,BtnSimpan);
    }//GEN-LAST:event_btnPetugasOKKeyPressed

    private void SNCNKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SNCNKeyPressed
        Valid.pindah(evt,Tanggal,Tindakan);
    }//GEN-LAST:event_SNCNKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah(evt,SNCN,btnDokterBedah);
    }//GEN-LAST:event_TindakanKeyPressed

    private void JumlahDrainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahDrainKeyPressed
        Valid.pindah(evt,Drain,LetakDrain);
    }//GEN-LAST:event_JumlahDrainKeyPressed

    private void KeteranganRadiologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganRadiologiKeyPressed
        Valid.pindah(evt,Radiologi,EKG);
    }//GEN-LAST:event_KeteranganRadiologiKeyPressed

    private void KeteranganEKGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganEKGKeyPressed
        Valid.pindah(evt,EKG,USG);
    }//GEN-LAST:event_KeteranganEKGKeyPressed

    private void KeteranganUSGKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganUSGKeyPressed
        Valid.pindah(evt,USG,CTScan);
    }//GEN-LAST:event_KeteranganUSGKeyPressed

    private void KeteranganCTScanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganCTScanKeyPressed
        Valid.pindah(evt,CTScan,MRI);
    }//GEN-LAST:event_KeteranganCTScanKeyPressed

    private void KeteranganMRIKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganMRIKeyPressed
        Valid.pindah(evt,MRI,AreaLukaOperasi);
    }//GEN-LAST:event_KeteranganMRIKeyPressed

    private void AreaLukaOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AreaLukaOperasiKeyPressed
        Valid.pindah(evt,KeteranganMRI,btnPetugasRuangan);
    }//GEN-LAST:event_AreaLukaOperasiKeyPressed

    private void KateterUrineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KateterUrineKeyPressed
        Valid.pindah(evt,JaringanPA,TanggalKateter);
    }//GEN-LAST:event_KateterUrineKeyPressed

    private void TanggalKateterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKateterKeyPressed
        Valid.pindah2(evt,KateterUrine,WarnaUrine);
    }//GEN-LAST:event_TanggalKateterKeyPressed

    private void WarnaUrineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WarnaUrineKeyPressed
        Valid.pindah(evt,TanggalKateter,JumlahUrine);
    }//GEN-LAST:event_WarnaUrineKeyPressed

    private void JumlahUrineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahUrineKeyPressed
        Valid.pindah(evt,WarnaUrine,Drain);
    }//GEN-LAST:event_JumlahUrineKeyPressed

    private void LetakDrainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LetakDrainKeyPressed
        Valid.pindah(evt,JumlahDrain,WarnaDrain);
    }//GEN-LAST:event_LetakDrainKeyPressed

    private void WarnaDrainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WarnaDrainKeyPressed
        Valid.pindah(evt,LetakDrain,Radiologi);
    }//GEN-LAST:event_WarnaDrainKeyPressed

    private void CairanInfusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CairanInfusKeyPressed
        Valid.pindah(evt,KeadaanUmum,JaringanPA);
    }//GEN-LAST:event_CairanInfusKeyPressed

    private void JaringanPAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JaringanPAKeyPressed
        Valid.pindah(evt,CairanInfus,KateterUrine);
    }//GEN-LAST:event_JaringanPAKeyPressed

    private void JKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JKKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JKKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMChecklistPostOperasi dialog = new RMChecklistPostOperasi(new javax.swing.JFrame(), true);
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
    private widget.TextBox AreaLukaOperasi;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CTScan;
    private widget.TextBox CairanInfus;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Drain;
    private widget.ComboBox EKG;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.ComboBox JaringanPA;
    private widget.TextBox JumlahDrain;
    private widget.TextBox JumlahUrine;
    private widget.ComboBox KateterUrine;
    private widget.TextBox KdPetugasAnest;
    private widget.TextBox KdPetugasOK;
    private widget.ComboBox KeadaanUmum;
    private widget.TextBox KeteranganCTScan;
    private widget.TextBox KeteranganEKG;
    private widget.TextBox KeteranganMRI;
    private widget.TextBox KeteranganRadiologi;
    private widget.TextBox KeteranganUSG;
    private widget.TextBox KodeDokterAnestesi;
    private widget.TextBox KodeDokterBedah;
    private widget.Label LCount;
    private widget.TextBox LetakDrain;
    private widget.editorpane LoadHTML;
    private widget.ComboBox MRI;
    private javax.swing.JMenuItem MnPostOperasi;
    private widget.TextBox NamaDokterAnestesi;
    private widget.TextBox NamaDokterBedah;
    private widget.TextBox NmPetugasAnest;
    private widget.TextBox NmPetugasOK;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Radiologi;
    private widget.TextBox SNCN;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.Tanggal TanggalKateter;
    private widget.TextBox TglLahir;
    private widget.TextBox Tindakan;
    private widget.ComboBox USG;
    private widget.TextBox WarnaDrain;
    private widget.ComboBox WarnaUrine;
    private widget.Button btnDokterAnestesi;
    private widget.Button btnDokterBedah;
    private widget.Button btnPetugasOK;
    private widget.Button btnPetugasRuangan;
    private widget.InternalFrame internalFrame1;
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
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel52;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel57;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_post_operasi.tanggal,"+
                    "checklist_post_operasi.sncn,checklist_post_operasi.tindakan,checklist_post_operasi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "checklist_post_operasi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,checklist_post_operasi.keadaan_umum,"+
                    "checklist_post_operasi.pemeriksaan_penunjang_rontgen,checklist_post_operasi.keterangan_pemeriksaan_penunjang_rontgen,"+
                    "checklist_post_operasi.pemeriksaan_penunjang_ekg,checklist_post_operasi.keterangan_pemeriksaan_penunjang_ekg,checklist_post_operasi.pemeriksaan_penunjang_usg,"+
                    "checklist_post_operasi.keterangan_pemeriksaan_penunjang_usg,checklist_post_operasi.pemeriksaan_penunjang_ctscan,"+
                    "checklist_post_operasi.keterangan_pemeriksaan_penunjang_ctscan,checklist_post_operasi.pemeriksaan_penunjang_mri,"+
                    "checklist_post_operasi.keterangan_pemeriksaan_penunjang_mri,checklist_post_operasi.jenis_cairan_infus,checklist_post_operasi.kateter_urine,"+
                    "checklist_post_operasi.tanggal_pemasangan_kateter,checklist_post_operasi.warna_kateter,checklist_post_operasi.jumlah_kateter,"+
                    "checklist_post_operasi.area_luka_operasi,checklist_post_operasi.drain,checklist_post_operasi.jumlah_drain,checklist_post_operasi.letak_drain,"+
                    "checklist_post_operasi.warna_drain,checklist_post_operasi.jaringan_pa,checklist_post_operasi.nip_perawat_ok,petugasok.nama as petugasok,"+
                    "checklist_post_operasi.nip_perawat_anestesi,petugasanestesi.nama as petugasanestesi "+
                    "from checklist_post_operasi inner join reg_periksa on checklist_post_operasi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=checklist_post_operasi.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=checklist_post_operasi.kd_dokter_anestesi "+
                    "inner join petugas as petugasanestesi on petugasanestesi.nip=checklist_post_operasi.nip_perawat_anestesi "+
                    "inner join petugas as petugasok on petugasok.nip=checklist_post_operasi.nip_perawat_ok "+
                    "where checklist_post_operasi.tanggal between ? and ? order by checklist_post_operasi.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,checklist_post_operasi.tanggal,"+
                    "checklist_post_operasi.sncn,checklist_post_operasi.tindakan,checklist_post_operasi.kd_dokter_bedah,dokterbedah.nm_dokter as dokterbedah,"+
                    "checklist_post_operasi.kd_dokter_anestesi,dokteranestesi.nm_dokter as dokteranestesi,checklist_post_operasi.keadaan_umum,"+
                    "checklist_post_operasi.pemeriksaan_penunjang_rontgen,checklist_post_operasi.keterangan_pemeriksaan_penunjang_rontgen,"+
                    "checklist_post_operasi.pemeriksaan_penunjang_ekg,checklist_post_operasi.keterangan_pemeriksaan_penunjang_ekg,checklist_post_operasi.pemeriksaan_penunjang_usg,"+
                    "checklist_post_operasi.keterangan_pemeriksaan_penunjang_usg,checklist_post_operasi.pemeriksaan_penunjang_ctscan,"+
                    "checklist_post_operasi.keterangan_pemeriksaan_penunjang_ctscan,checklist_post_operasi.pemeriksaan_penunjang_mri,"+
                    "checklist_post_operasi.keterangan_pemeriksaan_penunjang_mri,checklist_post_operasi.jenis_cairan_infus,checklist_post_operasi.kateter_urine,"+
                    "checklist_post_operasi.tanggal_pemasangan_kateter,checklist_post_operasi.warna_kateter,checklist_post_operasi.jumlah_kateter,"+
                    "checklist_post_operasi.area_luka_operasi,checklist_post_operasi.drain,checklist_post_operasi.jumlah_drain,checklist_post_operasi.letak_drain,"+
                    "checklist_post_operasi.warna_drain,checklist_post_operasi.jaringan_pa,checklist_post_operasi.nip_perawat_ok,petugasok.nama as petugasok,"+
                    "checklist_post_operasi.nip_perawat_anestesi,petugasanestesi.nama as petugasanestesi "+
                    "from checklist_post_operasi inner join reg_periksa on checklist_post_operasi.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join dokter as dokterbedah on dokterbedah.kd_dokter=checklist_post_operasi.kd_dokter_bedah "+
                    "inner join dokter as dokteranestesi on dokteranestesi.kd_dokter=checklist_post_operasi.kd_dokter_anestesi "+
                    "inner join petugas as petugasanestesi on petugasanestesi.nip=checklist_post_operasi.nip_perawat_anestesi "+
                    "inner join petugas as petugasok on petugasok.nip=checklist_post_operasi.nip_perawat_ok "+
                    "where checklist_post_operasi.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or dokterbedah.nm_dokter like ? or dokteranestesi.nm_dokter like ? or petugasanestesi.nama like ?) "+
                    "order by checklist_post_operasi.tanggal ");
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
                        rs.getString("kd_dokter_anestesi"),rs.getString("dokteranestesi"),rs.getString("keadaan_umum"),rs.getString("pemeriksaan_penunjang_rontgen"),
                        rs.getString("keterangan_pemeriksaan_penunjang_rontgen"),rs.getString("pemeriksaan_penunjang_ekg"),rs.getString("keterangan_pemeriksaan_penunjang_ekg"),
                        rs.getString("pemeriksaan_penunjang_usg"),rs.getString("keterangan_pemeriksaan_penunjang_usg"),rs.getString("pemeriksaan_penunjang_ctscan"),
                        rs.getString("keterangan_pemeriksaan_penunjang_ctscan"),rs.getString("pemeriksaan_penunjang_mri"),rs.getString("keterangan_pemeriksaan_penunjang_mri"),
                        rs.getString("jenis_cairan_infus"),rs.getString("kateter_urine"),rs.getString("tanggal_pemasangan_kateter"),rs.getString("warna_kateter"),
                        rs.getString("jumlah_kateter"),rs.getString("area_luka_operasi"),rs.getString("drain"),rs.getString("jumlah_drain"),rs.getString("letak_drain"),
                        rs.getString("warna_drain"),rs.getString("jaringan_pa"),rs.getString("nip_perawat_ok"),rs.getString("petugasok"),rs.getString("nip_perawat_anestesi"),
                        rs.getString("petugasanestesi")
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
        KeadaanUmum.setSelectedIndex(0);
        CairanInfus.setText("");
        JaringanPA.setSelectedIndex(0);
        KateterUrine.setSelectedIndex(0);
        TanggalKateter.setDate(new Date());
        WarnaUrine.setSelectedIndex(0);
        JumlahUrine.setText("");
        Drain.setSelectedIndex(0);
        JumlahDrain.setText("");
        LetakDrain.setText("");
        WarnaDrain.setText("");
        AreaLukaOperasi.setText("");
        Radiologi.setSelectedIndex(0);
        KeteranganRadiologi.setText("");
        EKG.setSelectedIndex(0);
        KeteranganEKG.setText("");
        USG.setSelectedIndex(0);
        KeteranganUSG.setText("");
        CTScan.setSelectedIndex(0);
        KeteranganCTScan.setText("");
        MRI.setSelectedIndex(0);
        KeteranganMRI.setText("");
        SNCN.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            SNCN.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KodeDokterBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NamaDokterBedah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            KodeDokterAnestesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            NamaDokterAnestesi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            KeadaanUmum.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Radiologi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            KeteranganRadiologi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            EKG.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            KeteranganEKG.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            USG.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            KeteranganUSG.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            CTScan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            KeteranganCTScan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            MRI.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            KeteranganMRI.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            CairanInfus.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            KateterUrine.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            WarnaUrine.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            JumlahUrine.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            AreaLukaOperasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Drain.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            JumlahDrain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            LetakDrain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            WarnaDrain.setText(tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            JaringanPA.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            KdPetugasOK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            NmPetugasOK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            KdPetugasAnest.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            NmPetugasAnest.setText(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            if(!tbObat.getValueAt(tbObat.getSelectedRow(),25).toString().equals("")){
                Valid.SetTgl2(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            }
        }
    }
    
    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    JK.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
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
    
    public void setNoRm(String norwt, Date tgl2,String KodeDokter,String NamaDokter,String Operasi) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);
        isRawat();
        ChkInput.setSelected(true);
        isForm();
        KodeDokterBedah.setText(KodeDokter);
        NamaDokterBedah.setText(NamaDokter);
        Tindakan.setText(Operasi);
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>558){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,386));
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
        BtnSimpan.setEnabled(akses.getchecklist_post_operasi());
        BtnHapus.setEnabled(akses.getchecklist_post_operasi());
        BtnEdit.setEnabled(akses.getchecklist_post_operasi());
        BtnPrint.setEnabled(akses.getchecklist_post_operasi()); 
    }

    private void ganti() {
        if(Sequel.mengedittf("checklist_post_operasi","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,sncn=?,tindakan=?,kd_dokter_bedah=?,kd_dokter_anestesi=?,keadaan_umum=?,"+
                "pemeriksaan_penunjang_rontgen=?,keterangan_pemeriksaan_penunjang_rontgen=?,pemeriksaan_penunjang_ekg=?,keterangan_pemeriksaan_penunjang_ekg=?,"+
                "pemeriksaan_penunjang_usg=?,keterangan_pemeriksaan_penunjang_usg=?,pemeriksaan_penunjang_ctscan=?,keterangan_pemeriksaan_penunjang_ctscan=?,pemeriksaan_penunjang_mri=?,"+
                "keterangan_pemeriksaan_penunjang_mri=?,jenis_cairan_infus=?,kateter_urine=?,tanggal_pemasangan_kateter=?,warna_kateter=?,jumlah_kateter=?,area_luka_operasi=?,"+
                "drain=?,jumlah_drain=?,letak_drain=?,warna_drain=?,jaringan_pa=?,nip_perawat_ok=?,nip_perawat_anestesi=?",32,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),SNCN.getText(),Tindakan.getText(),
                KodeDokterBedah.getText(),KodeDokterAnestesi.getText(),KeadaanUmum.getSelectedItem().toString(),Radiologi.getSelectedItem().toString(),KeteranganRadiologi.getText(), 
                EKG.getSelectedItem().toString(),KeteranganEKG.getText(),USG.getSelectedItem().toString(),KeteranganUSG.getText(),CTScan.getSelectedItem().toString(),
                KeteranganCTScan.getText(),MRI.getSelectedItem().toString(),KeteranganMRI.getText(),CairanInfus.getText(),KateterUrine.getSelectedItem().toString(), 
                (KateterUrine.getSelectedIndex()==0?Valid.SetTgl(TanggalKateter.getSelectedItem()+"")+" "+TanggalKateter.getSelectedItem().toString().substring(11,19):"0000-00-00 00:00:0"),
                WarnaUrine.getSelectedItem().toString(),JumlahUrine.getText(),AreaLukaOperasi.getText(),Drain.getSelectedItem().toString(),JumlahDrain.getText(),LetakDrain.getText(),
                WarnaDrain.getText(),JaringanPA.getSelectedItem().toString(),KdPetugasOK.getText(),KdPetugasAnest.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Tanggal.getSelectedItem().toString().substring(11,19),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(SNCN.getText(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Tindakan.getText(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(KodeDokterBedah.getText(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(NamaDokterBedah.getText(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(KodeDokterAnestesi.getText(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(NamaDokterAnestesi.getText(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(KeadaanUmum.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(Radiologi.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(KeteranganRadiologi.getText(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(EKG.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(KeteranganEKG.getText(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(USG.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(KeteranganUSG.getText(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(CTScan.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(KeteranganCTScan.getText(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(MRI.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(KeteranganMRI.getText(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(CairanInfus.getText(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(KateterUrine.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt((KateterUrine.getSelectedIndex()==0?Valid.SetTgl(TanggalKateter.getSelectedItem()+"")+" "+TanggalKateter.getSelectedItem().toString().substring(11,19):""),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(WarnaUrine.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(JumlahUrine.getText(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(AreaLukaOperasi.getText(),tbObat.getSelectedRow(),28);
            tbObat.setValueAt(Drain.getSelectedItem().toString(),tbObat.getSelectedRow(),29);
            tbObat.setValueAt(JumlahDrain.getText(),tbObat.getSelectedRow(),30);
            tbObat.setValueAt(LetakDrain.getText(),tbObat.getSelectedRow(),31);
            tbObat.setValueAt(WarnaDrain.getText(),tbObat.getSelectedRow(),32);
            tbObat.setValueAt(JaringanPA.getSelectedItem().toString(),tbObat.getSelectedRow(),33);
            tbObat.setValueAt(KdPetugasOK.getText(),tbObat.getSelectedRow(),34);
            tbObat.setValueAt(NmPetugasOK.getText(),tbObat.getSelectedRow(),35);
            tbObat.setValueAt(KdPetugasAnest.getText(),tbObat.getSelectedRow(),36);
            tbObat.setValueAt(NmPetugasAnest.getText(),tbObat.getSelectedRow(),37);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from checklist_post_operasi where no_rawat=? and tanggal=?",2,new String[]{
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

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
public final class RMPenilaianRisikoJatuhNeonatus extends javax.swing.JDialog {
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
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPenilaianRisikoJatuhNeonatus(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Tanggal","Pasang Gelang Risiko Jatuh","Edukasi Orang Tua/Keluarga","Dekatkan Box Bayi Dengan Ibu","Orentasi Ruangan Pada Orang Tua/Keluarga",
            "Pastikan Lantai & Alas Kaki Tidak Licin","Pastikan Selalu Ada Pendamping","Bila Dirawat Dalam Inkubator, Pastikan Semua Jendela Terkunci","Kontrol Rutin Oleh Perawat/Bidan","Pasang Tanda Risiko Jatuh Pada Box/Inkubator",
            "Tempatkan Bayi Pada Tempat Yang Aman","Segera Istirahat Apabila Merasa Lelah & Tempatkan Bayi Pada Boxnya","Cara Membedong Bayi","Libatkan Keluarga Untuk Mendampingi/Segera Panggil Perawat/Bidan Jika Diperlukan",
            "Teknip Menggendong Bayi","Bapak","Ibu","Keluarga","Wali Lainnya","Memahami & Mampu Menjelaskan Kembali","Perlu Edukasi Ulang","Mampu Mendemonstrasikan","Kode Petugas","Nama Petugas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 29; i++) {
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
            }else if(i==27){
                column.setPreferredWidth(90);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else{
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
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
                    KodePetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    btnPetugas.requestFocus();
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
        jam();
        
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
        MnPenilaianRisikoJatuh = new javax.swing.JMenuItem();
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
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel23 = new widget.Label();
        KodePetugas = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        Intervensi6 = new widget.ComboBox();
        jLabel58 = new widget.Label();
        jLabel5 = new widget.Label();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel53 = new widget.Label();
        jLabel59 = new widget.Label();
        jLabel60 = new widget.Label();
        Intervensi5 = new widget.ComboBox();
        jLabel61 = new widget.Label();
        Intervensi9 = new widget.ComboBox();
        jLabel62 = new widget.Label();
        Intervensi4 = new widget.ComboBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        Intervensi1 = new widget.ComboBox();
        jLabel66 = new widget.Label();
        Intervensi3 = new widget.ComboBox();
        jLabel68 = new widget.Label();
        Intervensi8 = new widget.ComboBox();
        jLabel67 = new widget.Label();
        jLabel69 = new widget.Label();
        Intervensi7 = new widget.ComboBox();
        jLabel70 = new widget.Label();
        Intervensi2 = new widget.ComboBox();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel54 = new widget.Label();
        jLabel71 = new widget.Label();
        Edukasi1 = new widget.ComboBox();
        jLabel72 = new widget.Label();
        Edukasi5 = new widget.ComboBox();
        Edukasi3 = new widget.ComboBox();
        jLabel74 = new widget.Label();
        jLabel75 = new widget.Label();
        Edukasi2 = new widget.ComboBox();
        jLabel76 = new widget.Label();
        Edukasi4 = new widget.ComboBox();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel55 = new widget.Label();
        jLabel77 = new widget.Label();
        Sasaran2 = new widget.ComboBox();
        jLabel78 = new widget.Label();
        Sasaran1 = new widget.ComboBox();
        Sasaran3 = new widget.ComboBox();
        jLabel79 = new widget.Label();
        jLabel80 = new widget.Label();
        Sasaran4 = new widget.ComboBox();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel56 = new widget.Label();
        jLabel81 = new widget.Label();
        Evaluasi1 = new widget.ComboBox();
        jLabel82 = new widget.Label();
        jLabel83 = new widget.Label();
        Evaluasi3 = new widget.ComboBox();
        jLabel84 = new widget.Label();
        Evaluasi2 = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        Detik = new widget.ComboBox();
        Menit = new widget.ComboBox();
        Jam = new widget.ComboBox();
        Tanggal = new widget.Tanggal();
        jLabel86 = new widget.Label();
        jLabel87 = new widget.Label();
        jLabel88 = new widget.Label();
        jLabel89 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPenilaianRisikoJatuh.setBackground(new java.awt.Color(255, 255, 254));
        MnPenilaianRisikoJatuh.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPenilaianRisikoJatuh.setForeground(new java.awt.Color(50, 50, 50));
        MnPenilaianRisikoJatuh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPenilaianRisikoJatuh.setText("Formulir Penilaian Risiko Jatuh Neonatus");
        MnPenilaianRisikoJatuh.setName("MnPenilaianRisikoJatuh"); // NOI18N
        MnPenilaianRisikoJatuh.setPreferredSize(new java.awt.Dimension(270, 26));
        MnPenilaianRisikoJatuh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPenilaianRisikoJatuhActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnPenilaianRisikoJatuh);

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Penilaian Lanjutan Risiko Jatuh Neonatus ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-07-2023" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-07-2023" }));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 423));
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

        jLabel23.setText("Petugas :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(400, 40, 70, 23);

        KodePetugas.setEditable(false);
        KodePetugas.setHighlighter(null);
        KodePetugas.setName("KodePetugas"); // NOI18N
        FormInput.add(KodePetugas);
        KodePetugas.setBounds(474, 40, 94, 23);

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

        Intervensi6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Intervensi6.setName("Intervensi6"); // NOI18N
        Intervensi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Intervensi6KeyPressed(evt);
            }
        });
        FormInput.add(Intervensi6);
        Intervensi6.setBounds(210, 150, 80, 23);

        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel58.setText("Pastikan Selalu Ada Pendamping");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(40, 150, 210, 23);

        jLabel5.setText(":");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 10, 75, 23);

        jSeparator1.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator1.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator1.setName("jSeparator1"); // NOI18N
        FormInput.add(jSeparator1);
        jSeparator1.setBounds(0, 70, 810, 1);

        jSeparator2.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator2.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator2.setName("jSeparator2"); // NOI18N
        FormInput.add(jSeparator2);
        jSeparator2.setBounds(0, 70, 810, 1);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel53.setText("I. INTERVENSI");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(10, 70, 180, 23);

        jLabel59.setText(":");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(0, 150, 206, 23);

        jLabel60.setText("Pastikan Lantai & Alas Kaki Tidak Licin :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(445, 120, 260, 23);

        Intervensi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Intervensi5.setName("Intervensi5"); // NOI18N
        Intervensi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Intervensi5KeyPressed(evt);
            }
        });
        FormInput.add(Intervensi5);
        Intervensi5.setBounds(709, 120, 80, 23);

        jLabel61.setText("Pasang Tanda Risiko Jatuh Pada Box/Inkubator :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(345, 180, 360, 23);

        Intervensi9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Intervensi9.setName("Intervensi9"); // NOI18N
        Intervensi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Intervensi9KeyPressed(evt);
            }
        });
        FormInput.add(Intervensi9);
        Intervensi9.setBounds(709, 180, 80, 23);

        jLabel62.setText(":");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(0, 120, 263, 23);

        Intervensi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Intervensi4.setName("Intervensi4"); // NOI18N
        Intervensi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Intervensi4KeyPressed(evt);
            }
        });
        FormInput.add(Intervensi4);
        Intervensi4.setBounds(267, 120, 80, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel63.setText("Orentasi Ruangan Pada Orang Tua/Keluarga");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(40, 120, 260, 23);

        jLabel64.setText(":");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(0, 90, 181, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel65.setText("Pasang Gelang Risiko Jatuh");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(40, 90, 160, 23);

        Intervensi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Intervensi1.setName("Intervensi1"); // NOI18N
        Intervensi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Intervensi1KeyPressed(evt);
            }
        });
        FormInput.add(Intervensi1);
        Intervensi1.setBounds(185, 90, 80, 23);

        jLabel66.setText("Dekatkan Box Bayi Dengan Ibu :");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(535, 90, 170, 23);

        Intervensi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Intervensi3.setName("Intervensi3"); // NOI18N
        Intervensi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Intervensi3KeyPressed(evt);
            }
        });
        FormInput.add(Intervensi3);
        Intervensi3.setBounds(709, 90, 80, 23);

        jLabel68.setText(":");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(0, 180, 211, 23);

        Intervensi8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Intervensi8.setName("Intervensi8"); // NOI18N
        Intervensi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Intervensi8KeyPressed(evt);
            }
        });
        FormInput.add(Intervensi8);
        Intervensi8.setBounds(215, 180, 80, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel67.setText("Kontrol Rutin Oleh Perawat/Bidan");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(40, 180, 190, 23);

        jLabel69.setText("Bila Dirawat Dalam Inkubator, Pastikan Semua Jendela Terkunci :");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(365, 150, 340, 23);

        Intervensi7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Intervensi7.setName("Intervensi7"); // NOI18N
        Intervensi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Intervensi7KeyPressed(evt);
            }
        });
        FormInput.add(Intervensi7);
        Intervensi7.setBounds(709, 150, 80, 23);

        jLabel70.setText("Edukasi Orang Tua/Keluarga :");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(270, 90, 170, 23);

        Intervensi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Intervensi2.setName("Intervensi2"); // NOI18N
        Intervensi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Intervensi2KeyPressed(evt);
            }
        });
        FormInput.add(Intervensi2);
        Intervensi2.setBounds(444, 90, 80, 23);

        jSeparator3.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator3.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator3.setName("jSeparator3"); // NOI18N
        FormInput.add(jSeparator3);
        jSeparator3.setBounds(0, 210, 810, 1);

        jSeparator4.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator4.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator4.setName("jSeparator4"); // NOI18N
        FormInput.add(jSeparator4);
        jSeparator4.setBounds(0, 210, 810, 1);

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel54.setText("II. EDUKASI YANG DIBERIKAN");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(10, 210, 180, 23);

        jLabel71.setText(":");
        jLabel71.setName("jLabel71"); // NOI18N
        FormInput.add(jLabel71);
        jLabel71.setBounds(0, 230, 250, 23);

        Edukasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Edukasi1.setName("Edukasi1"); // NOI18N
        Edukasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi1KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi1);
        Edukasi1.setBounds(254, 230, 80, 23);

        jLabel72.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel72.setText("Tempatkan Bayi Pada Tempat Yang Aman");
        jLabel72.setName("jLabel72"); // NOI18N
        FormInput.add(jLabel72);
        jLabel72.setBounds(40, 230, 220, 23);

        Edukasi5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Edukasi5.setName("Edukasi5"); // NOI18N
        Edukasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi5KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi5);
        Edukasi5.setBounds(176, 290, 80, 23);

        Edukasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Edukasi3.setName("Edukasi3"); // NOI18N
        Edukasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi3KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi3);
        Edukasi3.setBounds(159, 260, 80, 23);

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel74.setText("Cara Membedong Bayi");
        jLabel74.setName("jLabel74"); // NOI18N
        FormInput.add(jLabel74);
        jLabel74.setBounds(40, 260, 130, 23);

        jLabel75.setText("Segera Istirahat Apabila Merasa Lelah & Tempatkan Bayi Pada Boxnya :");
        jLabel75.setName("jLabel75"); // NOI18N
        FormInput.add(jLabel75);
        jLabel75.setBounds(345, 230, 360, 23);

        Edukasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Edukasi2.setName("Edukasi2"); // NOI18N
        Edukasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi2KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi2);
        Edukasi2.setBounds(709, 230, 80, 23);

        jLabel76.setText("Libatkan Keluarga Untuk Mendampingi/Segera Panggil Perawat/Bidan Jika Diperlukan :");
        jLabel76.setName("jLabel76"); // NOI18N
        FormInput.add(jLabel76);
        jLabel76.setBounds(255, 260, 450, 23);

        Edukasi4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Edukasi4.setName("Edukasi4"); // NOI18N
        Edukasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Edukasi4KeyPressed(evt);
            }
        });
        FormInput.add(Edukasi4);
        Edukasi4.setBounds(709, 260, 80, 23);

        jSeparator5.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator5.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator5.setName("jSeparator5"); // NOI18N
        FormInput.add(jSeparator5);
        jSeparator5.setBounds(0, 320, 810, 1);

        jSeparator6.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator6.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator6.setName("jSeparator6"); // NOI18N
        FormInput.add(jSeparator6);
        jSeparator6.setBounds(0, 320, 810, 1);

        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel55.setText("III. SASARAN EDUKASI");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(10, 320, 180, 23);

        jLabel77.setText("Ibu :");
        jLabel77.setName("jLabel77"); // NOI18N
        FormInput.add(jLabel77);
        jLabel77.setBounds(227, 340, 40, 23);

        Sasaran2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Sasaran2.setName("Sasaran2"); // NOI18N
        Sasaran2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sasaran2KeyPressed(evt);
            }
        });
        FormInput.add(Sasaran2);
        Sasaran2.setBounds(271, 340, 80, 23);

        jLabel78.setText(":");
        jLabel78.setName("jLabel78"); // NOI18N
        FormInput.add(jLabel78);
        jLabel78.setBounds(0, 340, 77, 23);

        Sasaran1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Sasaran1.setSelectedIndex(1);
        Sasaran1.setName("Sasaran1"); // NOI18N
        Sasaran1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sasaran1KeyPressed(evt);
            }
        });
        FormInput.add(Sasaran1);
        Sasaran1.setBounds(81, 340, 80, 23);

        Sasaran3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Sasaran3.setSelectedIndex(1);
        Sasaran3.setName("Sasaran3"); // NOI18N
        Sasaran3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sasaran3KeyPressed(evt);
            }
        });
        FormInput.add(Sasaran3);
        Sasaran3.setBounds(480, 340, 80, 23);

        jLabel79.setText("Keluarga :");
        jLabel79.setName("jLabel79"); // NOI18N
        FormInput.add(jLabel79);
        jLabel79.setBounds(386, 340, 90, 23);

        jLabel80.setText("Wali Lainnya :");
        jLabel80.setName("jLabel80"); // NOI18N
        FormInput.add(jLabel80);
        jLabel80.setBounds(615, 340, 90, 23);

        Sasaran4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Sasaran4.setSelectedIndex(1);
        Sasaran4.setName("Sasaran4"); // NOI18N
        Sasaran4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sasaran4KeyPressed(evt);
            }
        });
        FormInput.add(Sasaran4);
        Sasaran4.setBounds(709, 340, 80, 23);

        jSeparator7.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator7.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator7.setName("jSeparator7"); // NOI18N
        FormInput.add(jSeparator7);
        jSeparator7.setBounds(0, 370, 810, 1);

        jSeparator8.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator8.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator8.setName("jSeparator8"); // NOI18N
        FormInput.add(jSeparator8);
        jSeparator8.setBounds(0, 370, 810, 1);

        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel56.setText("IV. EVALUASI");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(10, 370, 180, 23);

        jLabel81.setText(":");
        jLabel81.setName("jLabel81"); // NOI18N
        FormInput.add(jLabel81);
        jLabel81.setBounds(0, 390, 253, 23);

        Evaluasi1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Evaluasi1.setName("Evaluasi1"); // NOI18N
        Evaluasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi1KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi1);
        Evaluasi1.setBounds(257, 390, 80, 23);

        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel82.setText("Memahami & Mampu Menjelaskan Kembali");
        jLabel82.setName("jLabel82"); // NOI18N
        FormInput.add(jLabel82);
        jLabel82.setBounds(40, 390, 230, 23);

        jLabel83.setText("Mampu Mendemonstrasikan :");
        jLabel83.setName("jLabel83"); // NOI18N
        FormInput.add(jLabel83);
        jLabel83.setBounds(545, 390, 160, 23);

        Evaluasi3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Evaluasi3.setName("Evaluasi3"); // NOI18N
        Evaluasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi3KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi3);
        Evaluasi3.setBounds(709, 390, 80, 23);

        jLabel84.setText("Perlu Edukasi Ulang :");
        jLabel84.setName("jLabel84"); // NOI18N
        FormInput.add(jLabel84);
        jLabel84.setBounds(340, 390, 120, 23);

        Evaluasi2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        Evaluasi2.setName("Evaluasi2"); // NOI18N
        Evaluasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Evaluasi2KeyPressed(evt);
            }
        });
        FormInput.add(Evaluasi2);
        Evaluasi2.setBounds(464, 390, 80, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(368, 40, 23, 23);

        Detik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Detik.setName("Detik"); // NOI18N
        Detik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKeyPressed(evt);
            }
        });
        FormInput.add(Detik);
        Detik.setBounds(303, 40, 62, 23);

        Menit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        Menit.setName("Menit"); // NOI18N
        Menit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKeyPressed(evt);
            }
        });
        FormInput.add(Menit);
        Menit.setBounds(238, 40, 62, 23);

        Jam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        Jam.setName("Jam"); // NOI18N
        Jam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKeyPressed(evt);
            }
        });
        FormInput.add(Jam);
        Jam.setBounds(173, 40, 62, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "09-07-2023" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(79, 40, 90, 23);

        jLabel86.setText(":");
        jLabel86.setName("jLabel86"); // NOI18N
        FormInput.add(jLabel86);
        jLabel86.setBounds(0, 290, 172, 23);

        jLabel87.setText(":");
        jLabel87.setName("jLabel87"); // NOI18N
        FormInput.add(jLabel87);
        jLabel87.setBounds(0, 260, 155, 23);

        jLabel88.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel88.setText("Teknik Menggendong Bayi");
        jLabel88.setName("jLabel88"); // NOI18N
        FormInput.add(jLabel88);
        jLabel88.setBounds(40, 290, 140, 23);

        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel89.setText("Bapak");
        jLabel89.setName("jLabel89"); // NOI18N
        FormInput.add(jLabel89);
        jLabel89.setBounds(40, 340, 70, 23);

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
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"Petugas");
        }else{
            if(Sequel.menyimpantf("penilaian_risiko_jatuh_neonatus","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",24,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),Intervensi1.getSelectedItem().toString(),
                Intervensi2.getSelectedItem().toString(),Intervensi3.getSelectedItem().toString(),Intervensi4.getSelectedItem().toString(),Intervensi5.getSelectedItem().toString(),
                Intervensi6.getSelectedItem().toString(),Intervensi7.getSelectedItem().toString(),Intervensi8.getSelectedItem().toString(),Intervensi9.getSelectedItem().toString(),
                Edukasi1.getSelectedItem().toString(),Edukasi2.getSelectedItem().toString(),Edukasi3.getSelectedItem().toString(),Edukasi4.getSelectedItem().toString(),Edukasi5.getSelectedItem().toString(),
                Sasaran1.getSelectedItem().toString(),Sasaran2.getSelectedItem().toString(),Sasaran3.getSelectedItem().toString(),Sasaran4.getSelectedItem().toString(),Evaluasi1.getSelectedItem().toString(),
                Evaluasi2.getSelectedItem().toString(),Evaluasi3.getSelectedItem().toString(),KodePetugas.getText()
            })==true){
                tabMode.addRow(new String[]{
                    TNoRw.getText(),TNoRM.getText(),TPasien.getText(),TglLahir.getText(),JK.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),
                    Intervensi1.getSelectedItem().toString(),Intervensi2.getSelectedItem().toString(),Intervensi3.getSelectedItem().toString(),Intervensi4.getSelectedItem().toString(),Intervensi5.getSelectedItem().toString(),
                    Intervensi6.getSelectedItem().toString(),Intervensi7.getSelectedItem().toString(),Intervensi8.getSelectedItem().toString(),Intervensi9.getSelectedItem().toString(),
                    Edukasi1.getSelectedItem().toString(),Edukasi2.getSelectedItem().toString(),Edukasi3.getSelectedItem().toString(),Edukasi4.getSelectedItem().toString(),Edukasi5.getSelectedItem().toString(),
                    Sasaran1.getSelectedItem().toString(),Sasaran2.getSelectedItem().toString(),Sasaran3.getSelectedItem().toString(),Sasaran4.getSelectedItem().toString(),Evaluasi1.getSelectedItem().toString(),
                    Evaluasi2.getSelectedItem().toString(),Evaluasi3.getSelectedItem().toString(),KodePetugas.getText(),NamaPetugas.getText()
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
            Valid.pindah(evt,Evaluasi3,BtnBatal);
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
                if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString())){
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
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(btnPetugas,"Petugas");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(akses.getkode().equals("Admin Utama")){
                    ganti();
                }else {
                    if(akses.getkode().equals(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString())){
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
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasang Gelang Risiko Jatuh</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Edukasi Orang Tua/Keluarga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dekatkan Box Bayi Dengan Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Orentasi Ruangan Pada Orang Tua/Keluarga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pastikan Lantai & Alas Kaki Tidak Licin</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pastikan Selalu Ada Pendamping</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bila Dirawat Dalam Inkubator, Pastikan Semua Jendela Terkunci</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kontrol Rutin Oleh Perawat/Bidan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Pasang Tanda Risiko Jatuh Pada Box/Inkubator</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tempatkan Bayi Pada Tempat Yang Aman</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Segera Istirahat Apabila Merasa Lelah & Tempatkan Bayi Pada Boxnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Cara Membedong Bayi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Libatkan Keluarga Untuk Mendampingi/Segera Panggil Perawat/Bidan Jika Diperlukan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Teknip Menggendong Bayi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Bapak</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Ibu</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Keluarga</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Wali Lainnya</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Memahami & Mampu Menjelaskan Kembali</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Perlu Edukasi Ulang</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Mampu Mendemonstrasikan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Petugas</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Petugas</b></td>"+
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

                File f = new File("DataChecklistKriteriaMasukHCU.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3500px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA CHECK LIST KRITERIA MASUK HCU<br><br></font>"+        
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

    private void MnPenilaianRisikoJatuhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPenilaianRisikoJatuhActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronip oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),28).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),27).toString():finger)+"\n"+Tanggal.getSelectedItem()); 
            Valid.MyReportqry("rptFormulirRisikoJatuhNeonatus.jasper","report","::[ Formulir Risiko Jatuh Neonatus ]::",
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,penilaian_risiko_jatuh_neonatus.tanggal,"+
                    "penilaian_risiko_jatuh_neonatus.intervensi1,penilaian_risiko_jatuh_neonatus.intervensi2,penilaian_risiko_jatuh_neonatus.intervensi3,"+
                    "penilaian_risiko_jatuh_neonatus.intervensi4,penilaian_risiko_jatuh_neonatus.intervensi5,penilaian_risiko_jatuh_neonatus.intervensi6,"+
                    "penilaian_risiko_jatuh_neonatus.intervensi7,penilaian_risiko_jatuh_neonatus.intervensi8,penilaian_risiko_jatuh_neonatus.intervensi9,"+
                    "penilaian_risiko_jatuh_neonatus.edukasi1,penilaian_risiko_jatuh_neonatus.edukasi2,penilaian_risiko_jatuh_neonatus.edukasi3,"+
                    "penilaian_risiko_jatuh_neonatus.edukasi4,penilaian_risiko_jatuh_neonatus.edukasi5,penilaian_risiko_jatuh_neonatus.sasaran1,"+
                    "penilaian_risiko_jatuh_neonatus.sasaran2,penilaian_risiko_jatuh_neonatus.sasaran3,penilaian_risiko_jatuh_neonatus.sasaran4,"+
                    "penilaian_risiko_jatuh_neonatus.evaluasi1,penilaian_risiko_jatuh_neonatus.evaluasi2,penilaian_risiko_jatuh_neonatus.evaluasi3,"+
                    "penilaian_risiko_jatuh_neonatus.nip,petugas.nama "+
                    "from penilaian_risiko_jatuh_neonatus inner join reg_periksa on penilaian_risiko_jatuh_neonatus.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on petugas.nip=penilaian_risiko_jatuh_neonatus.nip "+
                    "where penilaian_risiko_jatuh_neonatus.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"' and penilaian_risiko_jatuh_neonatus.tanggal='"+tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()+"' ",param);
        }
    }//GEN-LAST:event_MnPenilaianRisikoJatuhActionPerformed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
       Valid.pindah(evt,Tanggal,Intervensi1);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void Intervensi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Intervensi6KeyPressed
       Valid.pindah(evt,Intervensi5,Intervensi7);
    }//GEN-LAST:event_Intervensi6KeyPressed

    private void Intervensi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Intervensi5KeyPressed
       Valid.pindah(evt,Intervensi4,Intervensi6);
    }//GEN-LAST:event_Intervensi5KeyPressed

    private void Intervensi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Intervensi9KeyPressed
        Valid.pindah(evt,Intervensi8,Edukasi1);
    }//GEN-LAST:event_Intervensi9KeyPressed

    private void Intervensi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Intervensi4KeyPressed
        Valid.pindah(evt,Intervensi3,Intervensi5);
    }//GEN-LAST:event_Intervensi4KeyPressed

    private void Intervensi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Intervensi1KeyPressed
        Valid.pindah(evt,btnPetugas,Intervensi2);
    }//GEN-LAST:event_Intervensi1KeyPressed

    private void Intervensi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Intervensi3KeyPressed
        Valid.pindah(evt,Intervensi2,Intervensi4);
    }//GEN-LAST:event_Intervensi3KeyPressed

    private void Intervensi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Intervensi8KeyPressed
        Valid.pindah(evt,Intervensi7,Intervensi9);
    }//GEN-LAST:event_Intervensi8KeyPressed

    private void Intervensi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Intervensi7KeyPressed
        Valid.pindah(evt,Intervensi6,Intervensi8);
    }//GEN-LAST:event_Intervensi7KeyPressed

    private void Intervensi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Intervensi2KeyPressed
        Valid.pindah(evt,Intervensi1,Intervensi3);
    }//GEN-LAST:event_Intervensi2KeyPressed

    private void Edukasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi1KeyPressed
        Valid.pindah(evt,Intervensi9,Edukasi2);
    }//GEN-LAST:event_Edukasi1KeyPressed

    private void Edukasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi5KeyPressed
        Valid.pindah(evt,Edukasi4,Sasaran1);
    }//GEN-LAST:event_Edukasi5KeyPressed

    private void Edukasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi3KeyPressed
        Valid.pindah(evt,Edukasi2,Edukasi4);
    }//GEN-LAST:event_Edukasi3KeyPressed

    private void Edukasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi2KeyPressed
        Valid.pindah(evt,Edukasi1,Edukasi3);
    }//GEN-LAST:event_Edukasi2KeyPressed

    private void Edukasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Edukasi4KeyPressed
        Valid.pindah(evt,Edukasi3,Edukasi5);
    }//GEN-LAST:event_Edukasi4KeyPressed

    private void Sasaran2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sasaran2KeyPressed
        Valid.pindah(evt,Sasaran1,Sasaran3);
    }//GEN-LAST:event_Sasaran2KeyPressed

    private void Sasaran1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sasaran1KeyPressed
        Valid.pindah(evt,Edukasi5,Sasaran2);
    }//GEN-LAST:event_Sasaran1KeyPressed

    private void Sasaran3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sasaran3KeyPressed
        Valid.pindah(evt,Sasaran2,Sasaran4);
    }//GEN-LAST:event_Sasaran3KeyPressed

    private void Sasaran4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sasaran4KeyPressed
        Valid.pindah(evt,Sasaran3,Evaluasi1);
    }//GEN-LAST:event_Sasaran4KeyPressed

    private void Evaluasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi1KeyPressed
        Valid.pindah(evt,Sasaran4,Evaluasi2);
    }//GEN-LAST:event_Evaluasi1KeyPressed

    private void Evaluasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi3KeyPressed
        Valid.pindah(evt,Evaluasi2,BtnSimpan);
    }//GEN-LAST:event_Evaluasi3KeyPressed

    private void Evaluasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Evaluasi2KeyPressed
        Valid.pindah(evt,Evaluasi1,Evaluasi3);
    }//GEN-LAST:event_Evaluasi2KeyPressed

    private void DetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKeyPressed
        Valid.pindah(evt,Menit,btnPetugas);
    }//GEN-LAST:event_DetikKeyPressed

    private void MenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKeyPressed
        Valid.pindah(evt,Jam,Detik);
    }//GEN-LAST:event_MenitKeyPressed

    private void JamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKeyPressed
        Valid.pindah(evt,Tanggal,Menit);
    }//GEN-LAST:event_JamKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Jam);
    }//GEN-LAST:event_TanggalKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPenilaianRisikoJatuhNeonatus dialog = new RMPenilaianRisikoJatuhNeonatus(new javax.swing.JFrame(), true);
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
    private widget.CekBox ChkKejadian;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.ComboBox Detik;
    private widget.ComboBox Edukasi1;
    private widget.ComboBox Edukasi2;
    private widget.ComboBox Edukasi3;
    private widget.ComboBox Edukasi4;
    private widget.ComboBox Edukasi5;
    private widget.ComboBox Evaluasi1;
    private widget.ComboBox Evaluasi2;
    private widget.ComboBox Evaluasi3;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Intervensi1;
    private widget.ComboBox Intervensi2;
    private widget.ComboBox Intervensi3;
    private widget.ComboBox Intervensi4;
    private widget.ComboBox Intervensi5;
    private widget.ComboBox Intervensi6;
    private widget.ComboBox Intervensi7;
    private widget.ComboBox Intervensi8;
    private widget.ComboBox Intervensi9;
    private widget.TextBox JK;
    private widget.ComboBox Jam;
    private widget.TextBox KodePetugas;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ComboBox Menit;
    private javax.swing.JMenuItem MnPenilaianRisikoJatuh;
    private widget.TextBox NamaPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Sasaran1;
    private widget.ComboBox Sasaran2;
    private widget.ComboBox Sasaran3;
    private widget.ComboBox Sasaran4;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TglLahir;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
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
    private widget.Label jLabel74;
    private widget.Label jLabel75;
    private widget.Label jLabel76;
    private widget.Label jLabel77;
    private widget.Label jLabel78;
    private widget.Label jLabel79;
    private widget.Label jLabel8;
    private widget.Label jLabel80;
    private widget.Label jLabel81;
    private widget.Label jLabel82;
    private widget.Label jLabel83;
    private widget.Label jLabel84;
    private widget.Label jLabel86;
    private widget.Label jLabel87;
    private widget.Label jLabel88;
    private widget.Label jLabel89;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
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
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,penilaian_risiko_jatuh_neonatus.tanggal,"+
                    "penilaian_risiko_jatuh_neonatus.intervensi1,penilaian_risiko_jatuh_neonatus.intervensi2,penilaian_risiko_jatuh_neonatus.intervensi3,"+
                    "penilaian_risiko_jatuh_neonatus.intervensi4,penilaian_risiko_jatuh_neonatus.intervensi5,penilaian_risiko_jatuh_neonatus.intervensi6,"+
                    "penilaian_risiko_jatuh_neonatus.intervensi7,penilaian_risiko_jatuh_neonatus.intervensi8,penilaian_risiko_jatuh_neonatus.intervensi9,"+
                    "penilaian_risiko_jatuh_neonatus.edukasi1,penilaian_risiko_jatuh_neonatus.edukasi2,penilaian_risiko_jatuh_neonatus.edukasi3,"+
                    "penilaian_risiko_jatuh_neonatus.edukasi4,penilaian_risiko_jatuh_neonatus.edukasi5,penilaian_risiko_jatuh_neonatus.sasaran1,"+
                    "penilaian_risiko_jatuh_neonatus.sasaran2,penilaian_risiko_jatuh_neonatus.sasaran3,penilaian_risiko_jatuh_neonatus.sasaran4,"+
                    "penilaian_risiko_jatuh_neonatus.evaluasi1,penilaian_risiko_jatuh_neonatus.evaluasi2,penilaian_risiko_jatuh_neonatus.evaluasi3,"+
                    "penilaian_risiko_jatuh_neonatus.nip,petugas.nama "+
                    "from penilaian_risiko_jatuh_neonatus inner join reg_periksa on penilaian_risiko_jatuh_neonatus.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on petugas.nip=penilaian_risiko_jatuh_neonatus.nip "+
                    "where penilaian_risiko_jatuh_neonatus.tanggal between ? and ? order by penilaian_risiko_jatuh_neonatus.tanggal ");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,penilaian_risiko_jatuh_neonatus.tanggal,"+
                    "penilaian_risiko_jatuh_neonatus.intervensi1,penilaian_risiko_jatuh_neonatus.intervensi2,penilaian_risiko_jatuh_neonatus.intervensi3,"+
                    "penilaian_risiko_jatuh_neonatus.intervensi4,penilaian_risiko_jatuh_neonatus.intervensi5,penilaian_risiko_jatuh_neonatus.intervensi6,"+
                    "penilaian_risiko_jatuh_neonatus.intervensi7,penilaian_risiko_jatuh_neonatus.intervensi8,penilaian_risiko_jatuh_neonatus.intervensi9,"+
                    "penilaian_risiko_jatuh_neonatus.edukasi1,penilaian_risiko_jatuh_neonatus.edukasi2,penilaian_risiko_jatuh_neonatus.edukasi3,"+
                    "penilaian_risiko_jatuh_neonatus.edukasi4,penilaian_risiko_jatuh_neonatus.edukasi5,penilaian_risiko_jatuh_neonatus.sasaran1,"+
                    "penilaian_risiko_jatuh_neonatus.sasaran2,penilaian_risiko_jatuh_neonatus.sasaran3,penilaian_risiko_jatuh_neonatus.sasaran4,"+
                    "penilaian_risiko_jatuh_neonatus.evaluasi1,penilaian_risiko_jatuh_neonatus.evaluasi2,penilaian_risiko_jatuh_neonatus.evaluasi3,"+
                    "penilaian_risiko_jatuh_neonatus.nip,petugas.nama "+
                    "from penilaian_risiko_jatuh_neonatus inner join reg_periksa on penilaian_risiko_jatuh_neonatus.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on petugas.nip=penilaian_risiko_jatuh_neonatus.nip "+
                    "where penilaian_risiko_jatuh_neonatus.tanggal between ? and ? and (reg_periksa.no_rawat like ? or pasien.no_rkm_medis like ? or "+
                    "pasien.nm_pasien like ? or petugas.nama like ? or penilaian_risiko_jatuh_neonatus.nip like ?) order by penilaian_risiko_jatuh_neonatus.tanggal ");
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
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("tanggal"),
                        rs.getString("intervensi1"),rs.getString("intervensi2"),rs.getString("intervensi3"),rs.getString("intervensi4"),rs.getString("intervensi5"),rs.getString("intervensi6"),
                        rs.getString("intervensi7"),rs.getString("intervensi8"),rs.getString("intervensi9"),rs.getString("edukasi1"),rs.getString("edukasi2"),rs.getString("edukasi3"),
                        rs.getString("edukasi4"),rs.getString("edukasi5"),rs.getString("sasaran1"),rs.getString("sasaran2"),rs.getString("sasaran3"),rs.getString("sasaran4"),
                        rs.getString("evaluasi1"),rs.getString("evaluasi2"),rs.getString("evaluasi3"),rs.getString("nip"),rs.getString("nama")
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
        Intervensi1.setSelectedItem("Ya");
        Intervensi2.setSelectedItem("Ya");
        Intervensi3.setSelectedItem("Ya");
        Intervensi4.setSelectedItem("Ya");
        Intervensi5.setSelectedItem("Ya");
        Intervensi6.setSelectedItem("Ya");
        Intervensi7.setSelectedItem("Ya");
        Intervensi8.setSelectedItem("Ya");
        Intervensi9.setSelectedItem("Ya");
        Edukasi1.setSelectedItem("Ya");
        Edukasi2.setSelectedItem("Ya");
        Edukasi3.setSelectedItem("Ya");
        Edukasi4.setSelectedItem("Ya");
        Edukasi5.setSelectedItem("Ya");
        Sasaran1.setSelectedItem("Tidak");
        Sasaran2.setSelectedItem("Ya");
        Sasaran3.setSelectedItem("Tidak");
        Sasaran4.setSelectedItem("Tidak");
        Evaluasi1.setSelectedItem("Ya");
        Evaluasi2.setSelectedItem("Ya");
        Evaluasi3.setSelectedItem("Ya");
        Tanggal.setDate(new Date());
        Intervensi1.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            JK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Intervensi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Intervensi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            Intervensi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Intervensi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Intervensi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Intervensi6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Intervensi7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Intervensi8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Intervensi9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Edukasi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Edukasi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Edukasi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Edukasi4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Edukasi5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Sasaran1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Sasaran2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Sasaran3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Sasaran4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Evaluasi1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Evaluasi2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Evaluasi3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Jam.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(11,13));
            Menit.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(14,16));
            Detik.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(17,19));
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
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
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            if(internalFrame1.getHeight()>613){
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,446));
                FormInput.setVisible(true);      
                ChkInput.setVisible(true);
            }else{
                ChkInput.setVisible(false);
                PanelInput.setPreferredSize(new Dimension(WIDTH,internalFrame1.getHeight()-182));
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
        BtnSimpan.setEnabled(akses.getpenilaian_risiko_jatuh_neonatus());
        BtnHapus.setEnabled(akses.getpenilaian_risiko_jatuh_neonatus());
        BtnEdit.setEnabled(akses.getpenilaian_risiko_jatuh_neonatus());
        BtnPrint.setEnabled(akses.getpenilaian_risiko_jatuh_neonatus()); 
        if(akses.getjml2()>=1){
            KodePetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KodePetugas.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(KodePetugas.getText()));
            if(NamaPetugas.getText().equals("")){
                KodePetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        } 
    }

    private void ganti() {
        if(Sequel.mengedittf("penilaian_risiko_jatuh_neonatus","no_rawat=? and tanggal=?","no_rawat=?,tanggal=?,intervensi1=?,intervensi2=?,intervensi3=?,intervensi4=?,intervensi5=?,intervensi6=?,"+
                "intervensi7=?,intervensi8=?,intervensi9=?,edukasi1=?,edukasi2=?,edukasi3=?,edukasi4=?,edukasi5=?,sasaran1=?,sasaran2=?,sasaran3=?,sasaran4=?,evaluasi1=?,evaluasi2=?,evaluasi3=?,"+
                "nip=?",26,new String[]{
                TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),Intervensi1.getSelectedItem().toString(),
                Intervensi2.getSelectedItem().toString(),Intervensi3.getSelectedItem().toString(),Intervensi4.getSelectedItem().toString(),Intervensi5.getSelectedItem().toString(),
                Intervensi6.getSelectedItem().toString(),Intervensi7.getSelectedItem().toString(),Intervensi8.getSelectedItem().toString(),Intervensi9.getSelectedItem().toString(),
                Edukasi1.getSelectedItem().toString(),Edukasi2.getSelectedItem().toString(),Edukasi3.getSelectedItem().toString(),Edukasi4.getSelectedItem().toString(),Edukasi5.getSelectedItem().toString(),
                Sasaran1.getSelectedItem().toString(),Sasaran2.getSelectedItem().toString(),Sasaran3.getSelectedItem().toString(),Sasaran4.getSelectedItem().toString(),Evaluasi1.getSelectedItem().toString(),
                Evaluasi2.getSelectedItem().toString(),Evaluasi3.getSelectedItem().toString(),KodePetugas.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tbObat.setValueAt(TNoRw.getText(),tbObat.getSelectedRow(),0);
            tbObat.setValueAt(TNoRM.getText(),tbObat.getSelectedRow(),1);
            tbObat.setValueAt(TPasien.getText(),tbObat.getSelectedRow(),2);
            tbObat.setValueAt(TglLahir.getText(),tbObat.getSelectedRow(),3);
            tbObat.setValueAt(JK.getText(),tbObat.getSelectedRow(),4);
            tbObat.setValueAt(Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+Jam.getSelectedItem()+":"+Menit.getSelectedItem()+":"+Detik.getSelectedItem(),tbObat.getSelectedRow(),5);
            tbObat.setValueAt(Intervensi1.getSelectedItem().toString(),tbObat.getSelectedRow(),6);
            tbObat.setValueAt(Intervensi2.getSelectedItem().toString(),tbObat.getSelectedRow(),7);
            tbObat.setValueAt(Intervensi3.getSelectedItem().toString(),tbObat.getSelectedRow(),8);
            tbObat.setValueAt(Intervensi4.getSelectedItem().toString(),tbObat.getSelectedRow(),9);
            tbObat.setValueAt(Intervensi5.getSelectedItem().toString(),tbObat.getSelectedRow(),10);
            tbObat.setValueAt(Intervensi6.getSelectedItem().toString(),tbObat.getSelectedRow(),11);
            tbObat.setValueAt(Intervensi7.getSelectedItem().toString(),tbObat.getSelectedRow(),12);
            tbObat.setValueAt(Intervensi8.getSelectedItem().toString(),tbObat.getSelectedRow(),13);
            tbObat.setValueAt(Intervensi9.getSelectedItem().toString(),tbObat.getSelectedRow(),14);
            tbObat.setValueAt(Edukasi1.getSelectedItem().toString(),tbObat.getSelectedRow(),15);
            tbObat.setValueAt(Edukasi2.getSelectedItem().toString(),tbObat.getSelectedRow(),16);
            tbObat.setValueAt(Edukasi3.getSelectedItem().toString(),tbObat.getSelectedRow(),17);
            tbObat.setValueAt(Edukasi4.getSelectedItem().toString(),tbObat.getSelectedRow(),18);
            tbObat.setValueAt(Edukasi5.getSelectedItem().toString(),tbObat.getSelectedRow(),19);
            tbObat.setValueAt(Sasaran1.getSelectedItem().toString(),tbObat.getSelectedRow(),20);
            tbObat.setValueAt(Sasaran2.getSelectedItem().toString(),tbObat.getSelectedRow(),21);
            tbObat.setValueAt(Sasaran3.getSelectedItem().toString(),tbObat.getSelectedRow(),22);
            tbObat.setValueAt(Sasaran4.getSelectedItem().toString(),tbObat.getSelectedRow(),23);
            tbObat.setValueAt(Evaluasi1.getSelectedItem().toString(),tbObat.getSelectedRow(),24);
            tbObat.setValueAt(Evaluasi2.getSelectedItem().toString(),tbObat.getSelectedRow(),25);
            tbObat.setValueAt(Evaluasi3.getSelectedItem().toString(),tbObat.getSelectedRow(),26);
            tbObat.setValueAt(KodePetugas.getText(),tbObat.getSelectedRow(),27);
            tbObat.setValueAt(NamaPetugas.getText(),tbObat.getSelectedRow(),28);
            emptTeks();
        }
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_risiko_jatuh_neonatus where no_rawat=? and tanggal=?",2,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            emptTeks();
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
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
}

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

package simrskhanza;

import laporan.DlgCariPenyakit;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


/**
 *
 * @author perpustakaan
 */
public final class DlgRujukMasuk extends javax.swing.JDialog {
    private DefaultTableModel tabMode,tabMode2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement pstampil,pskamin,psperujuk,psobat,psreseppulang,psdiagnosapulang;
    private ResultSet rs,rs2;
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private int i=0;
    private DlgCariPenyakit penyakit=new DlgCariPenyakit(null,false);
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public DlgRujukMasuk(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"P","Perujuk/Rujukan","Alamat Perujuk","No.Rujuk","No.Rawat","No.R.M.",
                  "Nama Pasien","Alamat","Umur","Tgl.Masuk","Tgl.Keluar","JM.Perujuk","Diagnosa Awal",
                  "Diagnosa Akhir","Status","Dokter Perujuk","Kategori Rujuk","Poli Rujukan","No.Balasan","Keterangan","Kode Penyakit","Nama Penyakit"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 22; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(40);
            }else if(i==9){
                column.setPreferredWidth(75);
            }else if(i==10){
                column.setPreferredWidth(75);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(180);
            }else if(i==13){
                column.setPreferredWidth(180);
            }else if(i==14){
                column.setPreferredWidth(180);
            }else if(i==15){
                column.setPreferredWidth(100);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(150);
            }else if(i==19){
                column.setPreferredWidth(150);
            }else{
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        //Perujuk
        Object[] row2={"Perujuk","Alamat Perujuk"};
        tabMode2=new DefaultTableModel(null,row2){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPerujuk.setModel(tabMode2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPerujuk.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPerujuk.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 2; i++) {
            TableColumn column = tbPerujuk.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(200);
            }else if(i==1){
                column.setPreferredWidth(400);
            }
        }
        tbPerujuk.setDefaultRenderer(Object.class, new WarnaTable());

        TRujukan.setDocument(new batasInput((byte)60).getKata(TRujukan));
        TAlamat.setDocument(new batasInput((byte)70).getKata(TAlamat));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NoRujuk.setDocument(new batasInput((byte)20).getKata(NoRujuk));
        JMPerujuk.setDocument(new batasInput((byte)14).getOnlyAngka(JMPerujuk));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        TCariPerujuk.setDocument(new batasInput((byte)100).getKata(TCariPerujuk));           
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
        
        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if( penyakit.getTable().getSelectedRow()!= -1){                   
                    kdDiagnosa.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),0).toString());
                    NmDiagnosa.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                }  
                kdDiagnosa.requestFocus();
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
        
        ChkInput.setSelected(true);
        isForm();
    }
    private String diagnosa="",diagnosa2="",keluar="",status="",diagnosapulang="";

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WindowPerujuk = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        tbPerujuk = new widget.Table();
        panelisi2 = new widget.panelisi();
        label9 = new widget.Label();
        TCariPerujuk = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnAll1 = new widget.Button();
        label10 = new widget.Label();
        LCount1 = new widget.Label();
        BtnKeluar1 = new widget.Button();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnBalasanRujukan = new javax.swing.JMenuItem();
        MnBalasanRujukan1 = new javax.swing.JMenuItem();
        MnBalasanRujukan2 = new javax.swing.JMenuItem();
        DTPReg = new widget.Tanggal();
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
        ChkInput = new widget.CekBox();
        FormInput = new widget.PanelBiasa();
        TRujukan = new widget.TextBox();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel12 = new widget.Label();
        TNoRM = new widget.TextBox();
        TAlamat = new widget.TextBox();
        jLabel13 = new widget.Label();
        btnCariRujuk = new widget.Button();
        jLabel5 = new widget.Label();
        NoRujuk = new widget.TextBox();
        JMPerujuk = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel14 = new widget.Label();
        Dokter = new widget.TextBox();
        btnDokter = new widget.Button();
        NmDiagnosa = new widget.TextBox();
        kdDiagnosa = new widget.TextBox();
        jLabel9 = new widget.Label();
        jLabel15 = new widget.Label();
        KategoriRujuk = new widget.ComboBox();
        jLabel16 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel10 = new widget.Label();
        NoBalasan = new widget.TextBox();

        WindowPerujuk.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowPerujuk.setName("WindowPerujuk"); // NOI18N
        WindowPerujuk.setUndecorated(true);
        WindowPerujuk.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Perujuk ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbPerujuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbPerujuk.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPerujuk.setName("tbPerujuk"); // NOI18N
        tbPerujuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPerujukMouseClicked(evt);
            }
        });
        tbPerujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPerujukKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbPerujuk);

        internalFrame6.add(scrollPane2, java.awt.BorderLayout.CENTER);

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi2.add(label9);

        TCariPerujuk.setName("TCariPerujuk"); // NOI18N
        TCariPerujuk.setPreferredSize(new java.awt.Dimension(312, 23));
        TCariPerujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariPerujukKeyPressed(evt);
            }
        });
        panelisi2.add(TCariPerujuk);

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
        panelisi2.add(BtnCari1);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll1.setMnemonic('2');
        BtnAll1.setToolTipText("2Alt+2");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        BtnAll1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAll1KeyPressed(evt);
            }
        });
        panelisi2.add(BtnAll1);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(label10);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi2.add(LCount1);

        BtnKeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar1.setMnemonic('4');
        BtnKeluar1.setToolTipText("Alt+4");
        BtnKeluar1.setName("BtnKeluar1"); // NOI18N
        BtnKeluar1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar1ActionPerformed(evt);
            }
        });
        panelisi2.add(BtnKeluar1);

        internalFrame6.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        WindowPerujuk.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnBalasanRujukan.setBackground(new java.awt.Color(255, 255, 254));
        MnBalasanRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBalasanRujukan.setForeground(java.awt.Color.darkGray);
        MnBalasanRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBalasanRujukan.setText("Balasan Rujukan");
        MnBalasanRujukan.setName("MnBalasanRujukan"); // NOI18N
        MnBalasanRujukan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnBalasanRujukan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBalasanRujukanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBalasanRujukan);

        MnBalasanRujukan1.setBackground(new java.awt.Color(255, 255, 254));
        MnBalasanRujukan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBalasanRujukan1.setForeground(java.awt.Color.darkGray);
        MnBalasanRujukan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBalasanRujukan1.setText("Balasan Rujukan 2");
        MnBalasanRujukan1.setName("MnBalasanRujukan1"); // NOI18N
        MnBalasanRujukan1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnBalasanRujukan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBalasanRujukan1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBalasanRujukan1);

        MnBalasanRujukan2.setBackground(new java.awt.Color(255, 255, 254));
        MnBalasanRujukan2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBalasanRujukan2.setForeground(java.awt.Color.darkGray);
        MnBalasanRujukan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBalasanRujukan2.setText("Balasan Rujukan 3");
        MnBalasanRujukan2.setName("MnBalasanRujukan2"); // NOI18N
        MnBalasanRujukan2.setPreferredSize(new java.awt.Dimension(250, 28));
        MnBalasanRujukan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBalasanRujukan2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBalasanRujukan2);

        DTPReg.setEditable(false);
        DTPReg.setForeground(new java.awt.Color(50, 70, 50));
        DTPReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2019" }));
        DTPReg.setDisplayFormat("dd-MM-yyyy");
        DTPReg.setName("DTPReg"); // NOI18N
        DTPReg.setOpaque(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Rujukan Masuk ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
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

        jLabel19.setText("Tgl.Rujuk :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2019" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "26-02-2019" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 186));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 167));
        FormInput.setLayout(null);

        TRujukan.setHighlighter(null);
        TRujukan.setName("TRujukan"); // NOI18N
        TRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TRujukanKeyPressed(evt);
            }
        });
        FormInput.add(TRujukan);
        TRujukan.setBounds(83, 70, 190, 23);

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
        TNoRw.setBounds(83, 10, 152, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(359, 10, 368, 23);

        jLabel12.setText("Rujukan dari :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 70, 80, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(237, 10, 120, 23);

        TAlamat.setHighlighter(null);
        TAlamat.setName("TAlamat"); // NOI18N
        TAlamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlamatKeyPressed(evt);
            }
        });
        FormInput.add(TAlamat);
        TAlamat.setBounds(407, 70, 320, 23);

        jLabel13.setText("Alamat Perujuk :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(304, 70, 100, 23);

        btnCariRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnCariRujuk.setMnemonic('2');
        btnCariRujuk.setToolTipText("Alt+2");
        btnCariRujuk.setName("btnCariRujuk"); // NOI18N
        btnCariRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariRujukActionPerformed(evt);
            }
        });
        btnCariRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCariRujukKeyPressed(evt);
            }
        });
        FormInput.add(btnCariRujuk);
        btnCariRujuk.setBounds(276, 70, 28, 23);

        jLabel5.setText("No.Rujuk :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 40, 80, 23);

        NoRujuk.setHighlighter(null);
        NoRujuk.setName("NoRujuk"); // NOI18N
        NoRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRujukKeyPressed(evt);
            }
        });
        FormInput.add(NoRujuk);
        NoRujuk.setBounds(83, 40, 152, 23);

        JMPerujuk.setText("0");
        JMPerujuk.setHighlighter(null);
        JMPerujuk.setName("JMPerujuk"); // NOI18N
        JMPerujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JMPerujukKeyPressed(evt);
            }
        });
        FormInput.add(JMPerujuk);
        JMPerujuk.setBounds(617, 100, 110, 23);

        jLabel8.setText("JM.Perujuk : Rp.");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(525, 100, 90, 23);

        jLabel14.setText("Dokter Perujuk :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(434, 40, 100, 23);

        Dokter.setHighlighter(null);
        Dokter.setName("Dokter"); // NOI18N
        Dokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DokterKeyPressed(evt);
            }
        });
        FormInput.add(Dokter);
        Dokter.setBounds(537, 40, 190, 23);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('2');
        btnDokter.setToolTipText("Alt+2");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        FormInput.add(btnDokter);
        btnDokter.setBounds(477, 100, 28, 23);

        NmDiagnosa.setEditable(false);
        NmDiagnosa.setHighlighter(null);
        NmDiagnosa.setName("NmDiagnosa"); // NOI18N
        NmDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(NmDiagnosa);
        NmDiagnosa.setBounds(135, 100, 340, 23);

        kdDiagnosa.setHighlighter(null);
        kdDiagnosa.setName("kdDiagnosa"); // NOI18N
        kdDiagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdDiagnosaKeyPressed(evt);
            }
        });
        FormInput.add(kdDiagnosa);
        kdDiagnosa.setBounds(83, 100, 50, 23);

        jLabel9.setText("Diagnosa :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 100, 80, 23);

        jLabel15.setText(" Kategori Rujuk :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(240, 40, 85, 23);

        KategoriRujuk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Bedah", "Non-Bedah", "Kebidanan", "Anak" }));
        KategoriRujuk.setName("KategoriRujuk"); // NOI18N
        KategoriRujuk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KategoriRujukKeyPressed(evt);
            }
        });
        FormInput.add(KategoriRujuk);
        KategoriRujuk.setBounds(328, 40, 115, 23);

        jLabel16.setText("Keterangan :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(304, 130, 100, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(407, 130, 320, 23);

        jLabel10.setText("No.Balasan :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 130, 80, 23);

        NoBalasan.setHighlighter(null);
        NoBalasan.setName("NoBalasan"); // NOI18N
        NoBalasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoBalasanKeyPressed(evt);
            }
        });
        FormInput.add(NoBalasan);
        NoBalasan.setBounds(83, 130, 152, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TRujukanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnCariRujukActionPerformed(null);
        }else{Valid.pindah(evt,Dokter,TAlamat);}
}//GEN-LAST:event_TRujukanKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,NoRujuk);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TRujukan.getText().trim().equals("")){
            Valid.textKosong(TRujukan,"Rujukan dari");
        }else if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(TAlamat.getText().trim().equals("")){
            Valid.textKosong(TAlamat,"Alamat");
        }else if(Dokter.getText().trim().equals("")){
            Valid.textKosong(Dokter,"Dokter");
        }else if(kdDiagnosa.getText().trim().equals("")||NmDiagnosa.getText().trim().equals("")){
            Valid.textKosong(kdDiagnosa,"Diagnosa Masuk");
        }else if(NoBalasan.getText().trim().equals("")){
            Valid.textKosong(NoBalasan,"No.Balasan");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"No.Keterangan");
        }else if(JMPerujuk.getText().trim().equals("")){
            Valid.textKosong(JMPerujuk,"J.M. Perujuk");
        }else{
            Sequel.menyimpan("rujuk_masuk","'"+TNoRw.getText()+"','"+TRujukan.getText()+"','"+TAlamat.getText()+"',"+
                             "'"+NoRujuk.getText()+"','"+JMPerujuk.getText()+"','"+Dokter.getText()+"','"+kdDiagnosa.getText()+"',"+
                             "'"+KategoriRujuk.getSelectedItem().toString()+"','"+Keterangan.getText()+"','"+NoBalasan.getText()+"'","No.Rujuk");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,Keterangan,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        for(int i=0;i<tbObat.getRowCount();i++){ 
            if(tbObat.getValueAt(i,0).toString().equals("true")){
                Sequel.meghapus("rujuk_masuk","no_rawat",tbObat.getValueAt(i,4).toString());
            }
        }
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TRujukan.getText().trim().equals("")){
            Valid.textKosong(TRujukan,"Rujukan dari");
        }else if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(TAlamat.getText().trim().equals("")){
            Valid.textKosong(TAlamat,"Alamat");
        }else if(Dokter.getText().trim().equals("")){
            Valid.textKosong(Dokter,"Dokter");
        }else if(kdDiagnosa.getText().trim().equals("")||NmDiagnosa.getText().trim().equals("")){
            Valid.textKosong(kdDiagnosa,"Diagnosa Masuk");
        }else if(NoBalasan.getText().trim().equals("")){
            Valid.textKosong(NoBalasan,"No.Balasan");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"No.Keterangan");
        }else if(JMPerujuk.getText().trim().equals("")){
            Valid.textKosong(JMPerujuk,"J.M. Perujuk");
        }else{    
            Sequel.mengedit("rujuk_masuk","no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()+"'",
                    "perujuk='"+TRujukan.getText()+"',alamat='"+TAlamat.getText()+"',no_rujuk='"+NoRujuk.getText()+"',"+
                    "jm_perujuk='"+JMPerujuk.getText()+"',dokter_perujuk='"+Dokter.getText()+"',kd_penyakit='"+kdDiagnosa.getText()+"',"+
                    "kategori_rujuk='"+KategoriRujuk.getSelectedItem().toString()+"',keterangan='"+Keterangan.getText()+"',"+
                    "no_balasan='"+NoBalasan.getText()+"'");
            if(tabMode.getRowCount()!=0){tampil();}
            emptTeks();
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
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.queryu("truncate table temporary");
            for(int i=0;i<tabMode.getRowCount();i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','"+
                                tabMode.getValueAt(i,8).toString()+"','"+
                                tabMode.getValueAt(i,9).toString()+"','"+
                                tabMode.getValueAt(i,10).toString()+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,11).toString()))+"','"+
                                tabMode.getValueAt(i,12).toString()+"','"+
                                tabMode.getValueAt(i,13).toString()+"','"+
                                tabMode.getValueAt(i,14).toString()+"','"+
                                tabMode.getValueAt(i,15).toString()+"','"+
                                tabMode.getValueAt(i,16).toString()+"','"+
                                tabMode.getValueAt(i,17).toString()+"','"+
                                tabMode.getValueAt(i,18).toString()+"','"+
                                tabMode.getValueAt(i,19).toString()+"','','','','','','','','','','','','','','','','','','','',''","Rujukan Masuk"); 
            }
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReportqry("rptRujukMasuk.jasper","report","::[ Data Rujukan Yang Masuk ]::",
                "select * from temporary order by no asc",param);
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

private void TAlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlamatKeyPressed
    Valid.pindah(evt,TRujukan,kdDiagnosa);
}//GEN-LAST:event_TAlamatKeyPressed

    private void TCariPerujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariPerujukKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari1ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar1.requestFocus();
        }
    }//GEN-LAST:event_TCariPerujukKeyPressed

    private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        tampil2();
    }//GEN-LAST:event_BtnCari1ActionPerformed

    private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCari1KeyPressed

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        TCariPerujuk.setText("");
        tampil2();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void BtnAll1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAll1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAll1KeyPressed

    private void BtnKeluar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar1ActionPerformed
        WindowPerujuk.dispose();
    }//GEN-LAST:event_BtnKeluar1ActionPerformed

    private void btnCariRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariRujukActionPerformed
        akses.setform("DlgRujukMasuk");
        tampil2();
        TCariPerujuk.requestFocus();
        WindowPerujuk.setSize(this.getWidth()-20,this.getHeight()-20);
        WindowPerujuk.setLocationRelativeTo(internalFrame1);
        WindowPerujuk.setVisible(true);
    }//GEN-LAST:event_btnCariRujukActionPerformed

    private void btnCariRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariRujukKeyPressed
        
    }//GEN-LAST:event_btnCariRujukKeyPressed

    private void tbPerujukMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPerujukMouseClicked
        if(tabMode2.getRowCount()!=0){
            if(akses.getform().equals("DlgRujukMasuk")){
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }  
            }
            WindowPerujuk.dispose();
        }       
    }//GEN-LAST:event_tbPerujukMouseClicked

    private void tbPerujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPerujukKeyPressed
        if(tabMode2.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData2();
                } catch (java.lang.NullPointerException e) {
                }
            }                       
        }
    }//GEN-LAST:event_tbPerujukKeyPressed

    private void NoRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRujukKeyPressed
        Valid.pindah(evt,TNoRw,KategoriRujuk);
    }//GEN-LAST:event_NoRujukKeyPressed

    private void JMPerujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JMPerujukKeyPressed
        Valid.pindah(evt,kdDiagnosa,NoBalasan);
    }//GEN-LAST:event_JMPerujukKeyPressed

    private void MnBalasanRujukanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBalasanRujukanActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Map<String, Object> param = new HashMap<>();
            diagnosa="";
            try {           
                psobat=koneksi.prepareStatement("select databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                   "on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat=? group by databarang.nama_brng ");
                try {
                    psobat.setString(1,TNoRw.getText());
                    rs=psobat.executeQuery();
                    while(rs.next()){
                        if(diagnosa.equals("")){
                            diagnosa=rs.getString(1);
                        }else {
                            diagnosa=diagnosa+", "+rs.getString(1);
                        }                    
                    }
                } catch (Exception e) {
                    System.out.println("simrskhanza.DlgRujukMasuk.MnBalasanRujukanActionPerformed() : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psobat!=null){
                        psobat.close();
                    }  
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            
            diagnosa2="";
            i=Sequel.cariInteger("select count(no_rawat) from rawat_inap_dr where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="rawat inap";
                }else {
                    diagnosa2=diagnosa2+", rawat inap";
                }                 
            }
            
            i=Sequel.cariInteger("select count(no_rawat) from periksa_lab where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan laboratorium";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan laboratorium";
                }                 
            }
            
            i=Sequel.cariInteger("select count(no_rawat) from periksa_radiologi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan radiologi";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan radiologi";
                }                 
            }
            
            i=Sequel.cariInteger("select count(no_rawat) from operasi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="operasi";
                }else {
                    diagnosa2=diagnosa2+", operasi";
                }                 
            }
            
            keluar=Sequel.cariIsi("select stts_pulang from kamar_inap where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());
            
            status="";
            try {          
                psreseppulang=koneksi.prepareStatement("select databarang.nama_brng from resep_pulang inner join databarang "+
                   "on resep_pulang.kode_brng=databarang.kode_brng where resep_pulang.no_rawat=? group by databarang.nama_brng ");
                try {
                    psreseppulang.setString(1,TNoRw.getText());
                    rs=psreseppulang.executeQuery();
                    while(rs.next()){
                        if(status.equals("")){
                            status=rs.getString(1);
                        }else {
                            status=status+", "+rs.getString(1);
                        }                    
                    }
                } catch (Exception e) {
                    System.out.println("simrskhanza.DlgRujukMasuk.MnBalasanRujukanActionPerformed() : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psreseppulang!=null){
                        psreseppulang.close();
                    }
                }                    
            } catch (Exception e) {
                System.out.println(e);
            }
            if(status.equals("")){
                status="......................................................";
            }
            
            param.put("html","Pada tanggal "+tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()+" s/d "+tbObat.getValueAt(tbObat.getSelectedRow(),9).toString()+
                    " telah kami rawat/beri pengobatan "+diagnosa+". Sudah kami beri tindakan "+diagnosa2+
                    ". Penderita meninggal/telah sembuh kami pulangkan dengan keadaan "+keluar+". Terapi waktu pulang "+status+
                    ". Pemeriksaan lanjutan .........................................................\n\nSekian, "+
                    "terima kasih atas kerja samanya yang baik.\nWassalamu’alaikum wr.wb.");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs()); 
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReportqry("rptBalasanRujukan.jasper","report","::[ Surat Balasan ]::",
                    "select rujuk_masuk.perujuk,rujuk_masuk.no_rujuk,reg_periksa.no_rawat,pasien.alamat,dokter.nm_dokter, "+
                    "reg_periksa.no_rkm_medis,pasien.jk,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.nm_pasien,"+
                    "reg_periksa.almt_pj,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,reg_periksa.tgl_registrasi,rujuk_masuk.jm_perujuk from reg_periksa "+
                    "inner join pasien inner join rujuk_masuk inner join dokter  on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and reg_periksa.no_rawat=rujuk_masuk.no_rawat and reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='"+TNoRw.getText()+"'",param);            
        }
    }//GEN-LAST:event_MnBalasanRujukanActionPerformed

    private void MnBalasanRujukan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBalasanRujukan1ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Map<String, Object> param = new HashMap<>();
            diagnosa="";
            keluar="";
            try {           
                psobat=koneksi.prepareStatement("select databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                   "on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat=? group by databarang.nama_brng ");
                try {
                    psobat.setString(1,TNoRw.getText());
                    rs=psobat.executeQuery();
                    while(rs.next()){
                        if(diagnosa.equals("")){
                            diagnosa=rs.getString(1);
                        }else {
                            diagnosa=diagnosa+", "+rs.getString(1);
                        }                    
                    }
                } catch (Exception e) {
                    System.out.println("simrskhanza.DlgRujukMasuk.MnBalasanRujukanActionPerformed() : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psobat!=null){
                        psobat.close();
                    }  
                }
            } catch (Exception e) {
                System.out.println(e);
            }           
            
            diagnosa2="";
            i=Sequel.cariInteger("select count(no_rawat) from rawat_inap_dr where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="rawat inap";
                }else {
                    diagnosa2=diagnosa2+", rawat inap";
                }                 
            }
            
            i=Sequel.cariInteger("select count(no_rawat) from periksa_lab where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan laboratorium";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan laboratorium";
                }                 
            }
            
            i=Sequel.cariInteger("select count(no_rawat) from periksa_radiologi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan radiologi";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan radiologi";
                }                 
            }
            
            i=Sequel.cariInteger("select count(no_rawat) from operasi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="operasi";
                }else {
                    diagnosa2=diagnosa2+", operasi";
                }                 
            }
            
            keluar=Sequel.cariIsi("select stts_pulang from kamar_inap where no_rawat=? and stts_pulang='-' order by STR_TO_DATE(concat(tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());
            
            param.put("html","Setelah penderita kami rawat di "+akses.getnamars()+" pulang dengan keadaan :\n"+
                    "     "+keluar+" dengan diagnosa akhir "+tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()+"\n"+
                    "Atas kerjasamanya kami ucapkan terima kasih");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("diagnosa",tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            param.put("tindakan",diagnosa2);
            param.put("terpi",diagnosa);
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs()); 
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReportqry("rptBalasanRujukan2.jasper","report","::[ Surat Balasan ]::",
                    "select rujuk_masuk.perujuk,rujuk_masuk.no_rujuk,reg_periksa.no_rawat,pasien.alamat,dokter.nm_dokter, "+
                    "reg_periksa.no_rkm_medis,pasien.jk,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.nm_pasien,"+
                    "reg_periksa.almt_pj,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,reg_periksa.tgl_registrasi,rujuk_masuk.jm_perujuk from reg_periksa "+
                    "inner join pasien inner join rujuk_masuk inner join dokter  on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and reg_periksa.no_rawat=rujuk_masuk.no_rawat and reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='"+TNoRw.getText()+"'",param);            
        }
    }//GEN-LAST:event_MnBalasanRujukan1ActionPerformed

    private void DokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DokterKeyPressed
        Valid.pindah(evt,KategoriRujuk,TRujukan);
    }//GEN-LAST:event_DokterKeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        penyakit.isCek();
        penyakit.emptTeks();
        penyakit.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void NmDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmDiagnosaKeyPressed
        //Valid.pindah(evt,TKd,TSpek);
    }//GEN-LAST:event_NmDiagnosaKeyPressed

    private void kdDiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdDiagnosaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=?",NmDiagnosa,kdDiagnosa.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnDokterActionPerformed(null);
        }else{
            Valid.pindah(evt,TAlamat,JMPerujuk);
        }
    }//GEN-LAST:event_kdDiagnosaKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KategoriRujukKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KategoriRujukKeyPressed
        Valid.pindah(evt,NoRujuk,Dokter);
    }//GEN-LAST:event_KategoriRujukKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,NoBalasan,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void NoBalasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBalasanKeyPressed
        Valid.pindah(evt,JMPerujuk,Keterangan);
    }//GEN-LAST:event_NoBalasanKeyPressed

    private void MnBalasanRujukan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBalasanRujukan2ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Map<String, Object> param = new HashMap<>();
            diagnosa="";
            keluar="";
            try {          
                psreseppulang=koneksi.prepareStatement("select databarang.nama_brng from resep_pulang inner join databarang "+
                   "on resep_pulang.kode_brng=databarang.kode_brng where resep_pulang.no_rawat=? group by databarang.nama_brng ");
                try {
                    psreseppulang.setString(1,TNoRw.getText());
                    rs=psreseppulang.executeQuery();
                    while(rs.next()){
                        if(status.equals("")){
                            status=rs.getString(1);
                        }else {
                            status=status+", "+rs.getString(1);
                        }                    
                    }
                } catch (Exception e) {
                    System.out.println("simrskhanza.DlgRujukMasuk.MnBalasanRujukanActionPerformed() : "+e);
                }finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psreseppulang!=null){
                        psreseppulang.close();
                    }
                }                    
            } catch (Exception e) {
                System.out.println(e);
            } 
            
            
            diagnosapulang="";
            try {    
                psdiagnosapulang=koneksi.prepareStatement(
                        "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit from diagnosa_pasien inner join penyakit "+
                        "on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where diagnosa_pasien.no_rawat=?");
                try {
                    psdiagnosapulang.setString(1,TNoRw.getText());
                    rs=psdiagnosapulang.executeQuery();
                    while(rs.next()){
                        if(diagnosapulang.equals("")){
                            diagnosapulang=rs.getString(1)+" "+rs.getString(2);
                        }else {
                            diagnosapulang=diagnosapulang+", "+rs.getString(1)+" "+rs.getString(2);
                        }                    
                    }
                } catch (Exception e) {
                    System.out.println("simrskhanza.DlgRujukMasuk.MnBalasanRujukan2ActionPerformed() : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(psdiagnosapulang!=null){
                        psdiagnosapulang.close();
                    }
                }                    
            } catch (Exception e) {
                System.out.println(e);
            }
            
            diagnosa2="";
            i=Sequel.cariInteger("select count(no_rawat) from rawat_inap_dr where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="rawat inap";
                }else {
                    diagnosa2=diagnosa2+", rawat inap";
                }                 
            }
            
            i=Sequel.cariInteger("select count(no_rawat) from periksa_lab where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan laboratorium";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan laboratorium";
                }                 
            }
            
            i=Sequel.cariInteger("select count(no_rawat) from periksa_radiologi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="pemeriksaan radiologi";
                }else {
                    diagnosa2=diagnosa2+", pemeriksaan radiologi";
                }                 
            }
            
            i=Sequel.cariInteger("select count(no_rawat) from operasi where no_rawat=?",TNoRw.getText());
            if(i>0){
                if(diagnosa2.equals("")){
                    diagnosa2="operasi";
                }else {
                    diagnosa2=diagnosa2+", operasi";
                }                 
            }
            
            keluar=Sequel.cariIsi("select stts_pulang from kamar_inap where no_rawat=? and stts_pulang<>'-' order by STR_TO_DATE(concat(tgl_masuk,' ',jam_masuk),'%Y-%m-%d %H:%i:%s') desc limit 1",TNoRw.getText());
            
            param.put("html","Setelah penderita kami rawat di "+akses.getnamars()+" pulang dengan keadaan :\n"+
                    "     "+keluar+" dengan diagnosa akhir "+diagnosapulang+"\n"+
                    "Atas kerjasamanya kami ucapkan terima kasih");
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("diagnosa",tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            param.put("tindakan",diagnosa2);
            param.put("terpi",diagnosa);
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs()); 
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReportqry("rptBalasanRujukan3.jasper","report","::[ Surat Balasan ]::",
                    "select rujuk_masuk.perujuk,rujuk_masuk.alamat,rujuk_masuk.no_rujuk,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                    "pasien.nm_pasien,reg_periksa.almt_pj,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,reg_periksa.tgl_registrasi,"+
                    "rujuk_masuk.jm_perujuk,rujuk_masuk.dokter_perujuk,rujuk_masuk.kd_penyakit,penyakit.nm_penyakit,rujuk_masuk.kategori_rujuk,"+
                    "rujuk_masuk.keterangan,rujuk_masuk.no_balasan,poliklinik.nm_poli,pasien.jk,dokter.nm_dokter "+
                    "from reg_periksa inner join pasien inner join rujuk_masuk inner join penyakit inner join poliklinik inner join dokter "+
                    "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=rujuk_masuk.no_rawat "+
                    "and penyakit.kd_penyakit=rujuk_masuk.kd_penyakit and reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "and reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='"+TNoRw.getText()+"'",param);            
        }
    }//GEN-LAST:event_MnBalasanRujukan2ActionPerformed

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

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgRujukMasuk dialog = new DlgRujukMasuk(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAll1;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar1;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPReg;
    private widget.TextBox Dokter;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JMPerujuk;
    private widget.ComboBox KategoriRujuk;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LCount1;
    private javax.swing.JMenuItem MnBalasanRujukan;
    private javax.swing.JMenuItem MnBalasanRujukan1;
    private javax.swing.JMenuItem MnBalasanRujukan2;
    private widget.TextBox NmDiagnosa;
    private widget.TextBox NoBalasan;
    private widget.TextBox NoRujuk;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAlamat;
    private widget.TextBox TCari;
    public widget.TextBox TCariPerujuk;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TRujukan;
    public javax.swing.JDialog WindowPerujuk;
    private widget.Button btnCariRujuk;
    private widget.Button btnDokter;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel10;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdDiagnosa;
    private widget.Label label10;
    private widget.Label label9;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi2;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbObat;
    public widget.Table tbPerujuk;
    // End of variables declaration//GEN-END:variables

    public void tampil() {        
        Valid.tabelKosong(tabMode);
        try{
            pstampil=koneksi.prepareStatement(
                    "select rujuk_masuk.perujuk,rujuk_masuk.alamat,rujuk_masuk.no_rujuk,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                    "pasien.nm_pasien,reg_periksa.almt_pj,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,reg_periksa.tgl_registrasi,"+
                    "rujuk_masuk.jm_perujuk,rujuk_masuk.dokter_perujuk,rujuk_masuk.kd_penyakit,penyakit.nm_penyakit,rujuk_masuk.kategori_rujuk,"+
                    "rujuk_masuk.keterangan,rujuk_masuk.no_balasan,poliklinik.nm_poli "+
                    "from reg_periksa inner join pasien inner join rujuk_masuk inner join penyakit inner join poliklinik "+
                    "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=rujuk_masuk.no_rawat "+
                    "and penyakit.kd_penyakit=rujuk_masuk.kd_penyakit and reg_periksa.kd_poli=poliklinik.kd_poli where "+
                    "reg_periksa.tgl_registrasi between ? and ? and rujuk_masuk.perujuk like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rawat like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and pasien.nm_pasien like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.almt_pj like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and rujuk_masuk.no_rujuk like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and rujuk_masuk.dokter_perujuk like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and penyakit.nm_penyakit like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and rujuk_masuk.kategori_rujuk like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and rujuk_masuk.keterangan like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and rujuk_masuk.no_balasan like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and rujuk_masuk.kd_penyakit like ? "+
                    "order by reg_periksa.tgl_registrasi ");
            try {
                pstampil.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(3,"%"+TCari.getText().trim()+"%");
                pstampil.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(6,"%"+TCari.getText().trim()+"%");
                pstampil.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(9,"%"+TCari.getText().trim()+"%");
                pstampil.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(12,"%"+TCari.getText().trim()+"%");
                pstampil.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(15,"%"+TCari.getText().trim()+"%");
                pstampil.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(18,"%"+TCari.getText().trim()+"%");
                pstampil.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(21,"%"+TCari.getText().trim()+"%");
                pstampil.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(24,"%"+TCari.getText().trim()+"%");
                pstampil.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(27,"%"+TCari.getText().trim()+"%");
                pstampil.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(30,"%"+TCari.getText().trim()+"%");
                pstampil.setString(31,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(32,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(33,"%"+TCari.getText().trim()+"%");
                pstampil.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                pstampil.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                pstampil.setString(36,"%"+TCari.getText().trim()+"%");
                rs=pstampil.executeQuery();
                while(rs.next()){
                    keluar="";diagnosa="";status="";diagnosa2="";
                    pskamin=koneksi.prepareStatement("select ifnull(kamar_inap.tgl_keluar,''),ifnull(kamar_inap.diagnosa_awal,''),ifnull(kamar_inap.diagnosa_akhir,''),ifnull(kamar_inap.stts_pulang,'') from kamar_inap where no_rawat=? "+
                                "order by kamar_inap.tgl_keluar desc limit 1");            
                    try {
                        pskamin.setString(1,rs.getString("no_rawat"));
                        rs2=pskamin.executeQuery();
                        if(rs2.next()){
                            keluar=rs2.getString(1);
                            diagnosa=rs2.getString(2);
                            diagnosa2=rs2.getString(3);
                            status=rs2.getString(4);
                        }
                    } catch (Exception e) {
                        System.out.println("simrskhanza.DlgRujukMasuk.tampil() : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(pskamin!=null){
                            pskamin.close();
                        }
                    }
                        
                    tabMode.addRow(new Object[]{
                        false,rs.getString("perujuk"),rs.getString("alamat"),rs.getString("no_rujuk"),
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("almt_pj"),rs.getString("umur"),rs.getString("tgl_registrasi"),keluar,
                        rs.getDouble("jm_perujuk"),rs.getString("kd_penyakit")+" "+rs.getString("nm_penyakit"),
                        diagnosa2,status,rs.getString("dokter_perujuk"),rs.getString("kategori_rujuk"),
                        rs.getString("nm_poli"),rs.getString("no_balasan"),rs.getString("keterangan"),
                        rs.getString("kd_penyakit"),rs.getString("nm_penyakit")
                    });
                }
            } catch (Exception e) {
                System.out.println("simrskhanza.DlgRujukMasuk.tampil() : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pstampil!=null){
                    pstampil.close();
                }
            }
                
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TNoRw.setText("");
        TRujukan.setText("");
        TNoRM.setText("");
        NoRujuk.setText("");
        JMPerujuk.setText("0");
        KategoriRujuk.setSelectedItem("-");
        kdDiagnosa.setText("");
        NmDiagnosa.setText("");
        Keterangan.setText("");
        Dokter.setText("");
        TPasien.setText("");
        NoRujuk.requestFocus();
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rujuk_masuk.no_balasan,4),signed)),0) from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat where reg_periksa.tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"' ","BR/"+dateformat.format(DTPReg.getDate())+"/",4,NoBalasan); 
    }


    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TRujukan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TAlamat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            NoRujuk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            JMPerujuk.setText(Valid.SetAngka2(Double.parseDouble(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString())));
            Dokter.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            KategoriRujuk.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            NoBalasan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());  
            kdDiagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());  
            NmDiagnosa.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());            
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
    }
    
    public void setNoRm(String norwt, Date tgl1, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        isRawat();
        isPsien();              
    }
      
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getrujukan_masuk());
        BtnHapus.setEnabled(akses.getrujukan_masuk());
        BtnPrint.setEnabled(akses.getrujukan_masuk());
        BtnEdit.setEnabled(akses.getrujukan_masuk());
        
    }
    
    public void tampil2() {        
        Valid.tabelKosong(tabMode2);
        try{
            psperujuk=koneksi.prepareStatement("select rujuk_masuk.perujuk,rujuk_masuk.alamat from rujuk_masuk where "+
                    "rujuk_masuk.perujuk like ? or rujuk_masuk.alamat like ? group by rujuk_masuk.perujuk order by rujuk_masuk.perujuk");
            try {
                psperujuk.setString(1,"%"+TCariPerujuk.getText()+"%");
                psperujuk.setString(2,"%"+TCariPerujuk.getText()+"%");
                rs=psperujuk.executeQuery();
                while(rs.next()){                              
                    tabMode2.addRow(new Object[]{rs.getString(1),rs.getString(2)});
                }
            } catch (Exception e) {
                System.out.println("simrskhanza.DlgRujukMasuk.tampil2() : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(psperujuk!=null){
                    psperujuk.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount1.setText(""+tabMode2.getRowCount());
    }
    
    private void getData2() {
        if(tbPerujuk.getSelectedRow()!= -1){
            TRujukan.setText(tbPerujuk.getValueAt(tbPerujuk.getSelectedRow(),0).toString());
            TAlamat.setText(tbPerujuk.getValueAt(tbPerujuk.getSelectedRow(),1).toString());
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,186));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }

}

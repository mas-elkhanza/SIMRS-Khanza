/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

package rekammedis;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;


/**
 *
 * @author perpustakaan
 */
public final class RMDataAsuhanGizi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    public  DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMDataAsuhanGizi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "Tgl.Rawat","Status","No.Rawat","No.RM","Nama Pasien","Kode Dokter","Dokter Penanggung Jawab","Pulang","Keluhan utama riwayat penyakit yang postif",
            "Jalannya penyakit selama perawatan","Pemeriksaan penunjang yang positif","Hasil laboratorium yang positif","Diagnosa Utama","ICD10 Utama","Diagnosa Sekunder 1",
            "ICD10 Sek 1","Diagnosa Sekunder 2","ICD10 Sek 2","Diagnosa Sekunder 3","ICD10 Sek 3","Diagnosa Sekunder 4","ICD10 Sek 4","Prosedur Utama","ICD9 Utama",
            "Prosedur Sekunder 1","ICD9 Sek1","Prosedur Sekunder 2","ICD9 Sek2","Prosedur Sekunder 3","ICD9 Sek3","Obat-obatan waktu pulang/nasihat"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 31; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(65);
            }else if(i==1){
                column.setPreferredWidth(40);
            }else if(i==2){
                column.setPreferredWidth(105);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(60);
            }else if(i==8){
                column.setPreferredWidth(250);
            }else if(i==9){
                column.setPreferredWidth(250);
            }else if(i==10){
                column.setPreferredWidth(250);
            }else if(i==11){
                column.setPreferredWidth(250);
            }else if(i==12){
                column.setPreferredWidth(170);
            }else if(i==13){
                column.setPreferredWidth(75);
            }else if(i==14){
                column.setPreferredWidth(170);
            }else if(i==15){
                column.setPreferredWidth(75);
            }else if(i==16){
                column.setPreferredWidth(170);
            }else if(i==17){
                column.setPreferredWidth(75);
            }else if(i==18){
                column.setPreferredWidth(170);
            }else if(i==19){
                column.setPreferredWidth(75);
            }else if(i==20){
                column.setPreferredWidth(170);
            }else if(i==21){
                column.setPreferredWidth(75);
            }else if(i==22){
                column.setPreferredWidth(170);
            }else if(i==23){
                column.setPreferredWidth(75);
            }else if(i==24){
                column.setPreferredWidth(170);
            }else if(i==25){
                column.setPreferredWidth(75);
            }else if(i==26){
                column.setPreferredWidth(170);
            }else if(i==27){
                column.setPreferredWidth(75);
            }else if(i==28){
                column.setPreferredWidth(170);
            }else if(i==29){
                column.setPreferredWidth(75);
            }else if(i==30){
                column.setPreferredWidth(250);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        TB.setDocument(new batasInput((byte)5).getKata(TB));
        IMT.setDocument(new batasInput((byte)5).getKata(IMT));
        LiLA.setDocument(new batasInput((byte)5).getKata(LiLA));
        TL.setDocument(new batasInput((byte)5).getKata(TL));
        ULNA.setDocument(new batasInput((byte)5).getKata(ULNA));
        BBIdeal.setDocument(new batasInput((byte)5).getKata(BBIdeal));
        BBPerU.setDocument(new batasInput((byte)5).getKata(BBPerU));
        TBPerU.setDocument(new batasInput((byte)5).getKata(TBPerU));
        BBPerTB.setDocument(new batasInput((byte)5).getKata(BBPerTB));
        LiLAPerU.setDocument(new batasInput((byte)5).getKata(LiLAPerU));
        Biokimia.setDocument(new batasInput((int)100).getKata(Biokimia));
        FisikKlinis.setDocument(new batasInput((int)100).getKata(FisikKlinis));
        RiwayatPersonal.setDocument(new batasInput((int)100).getKata(RiwayatPersonal));
        PolaMakan.setDocument(new batasInput((int)100).getKata(PolaMakan));
        DiagnosisGizi.setDocument(new batasInput((int)100).getKata(DiagnosisGizi));
        IntervensiGizi.setDocument(new batasInput((int)100).getKata(IntervensiGizi));
        Monitoring.setDocument(new batasInput((int)100).getKata(Monitoring));
        
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
                    KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());   
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
        MnLaporanResume = new javax.swing.JMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        buttonGroup5 = new javax.swing.ButtonGroup();
        buttonGroup6 = new javax.swing.ButtonGroup();
        buttonGroup7 = new javax.swing.ButtonGroup();
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
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        jLabel5 = new widget.Label();
        label14 = new widget.Label();
        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        DiagnosaMasukRanap = new widget.TextBox();
        label11 = new widget.Label();
        TglAsuhan = new widget.Tanggal();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        jLabel13 = new widget.Label();
        TB = new widget.TextBox();
        jLabel15 = new widget.Label();
        jLabel16 = new widget.Label();
        IMT = new widget.TextBox();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        LiLA = new widget.TextBox();
        jLabel22 = new widget.Label();
        TL = new widget.TextBox();
        jLabel20 = new widget.Label();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        ULNA = new widget.TextBox();
        jLabel26 = new widget.Label();
        jLabel14 = new widget.Label();
        BBIdeal = new widget.TextBox();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        BBPerU = new widget.TextBox();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        TBPerU = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        BBPerTB = new widget.TextBox();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        LiLAPerU = new widget.TextBox();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        Biokimia = new widget.TextBox();
        jLabel37 = new widget.Label();
        FisikKlinis = new widget.TextBox();
        PolaMakan = new widget.TextBox();
        jLabel38 = new widget.Label();
        DiagnosisGizi = new widget.TextBox();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        IntervensiGizi = new widget.TextBox();
        jLabel42 = new widget.Label();
        Monitoring = new widget.TextBox();
        jLabel39 = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        TelurYa = new widget.RadioButton();
        TelurTidak = new widget.RadioButton();
        SusuTidak = new widget.RadioButton();
        SusuYa = new widget.RadioButton();
        jLabel45 = new widget.Label();
        jLabel46 = new widget.Label();
        KacangYa = new widget.RadioButton();
        KacangTidak = new widget.RadioButton();
        jLabel47 = new widget.Label();
        GlutenYa = new widget.RadioButton();
        GlutenNo = new widget.RadioButton();
        jLabel48 = new widget.Label();
        UdangYa = new widget.RadioButton();
        UdangTidak = new widget.RadioButton();
        jLabel49 = new widget.Label();
        IkanYa = new widget.RadioButton();
        IkanTidak = new widget.RadioButton();
        HazelnutYa = new widget.RadioButton();
        HazelnutTidak = new widget.RadioButton();
        jLabel51 = new widget.Label();
        jLabel50 = new widget.Label();
        RiwayatPersonal = new widget.TextBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnLaporanResume.setBackground(new java.awt.Color(255, 255, 254));
        MnLaporanResume.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLaporanResume.setForeground(new java.awt.Color(50, 50, 50));
        MnLaporanResume.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnLaporanResume.setText("Laporan Asuhan Gizi Pasien");
        MnLaporanResume.setName("MnLaporanResume"); // NOI18N
        MnLaporanResume.setPreferredSize(new java.awt.Dimension(220, 26));
        MnLaporanResume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLaporanResumeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnLaporanResume);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Data Asuhan Gizi Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

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

        jLabel19.setText("Tgl.Rawat :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-01-2020" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "02-01-2020" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Keyword :");
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 448));
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

        FormInput.setBackground(new java.awt.Color(255, 253, 247));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 555));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(331, 10, 280, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(217, 10, 112, 23);

        jLabel5.setText("Diagnosa :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(200, 40, 70, 23);

        label14.setText("Petugas :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 520, 130, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(134, 520, 130, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(266, 520, 300, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        BtnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDokterKeyPressed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(569, 520, 28, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(625, 10, 60, 23);

        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        TglLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglLahirKeyPressed(evt);
            }
        });
        FormInput.add(TglLahir);
        TglLahir.setBounds(689, 10, 90, 23);

        jLabel9.setText("Biokimia :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 180, 130, 23);

        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        Jk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JkKeyPressed(evt);
            }
        });
        FormInput.add(Jk);
        Jk.setBounds(74, 40, 100, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        DiagnosaMasukRanap.setHighlighter(null);
        DiagnosaMasukRanap.setName("DiagnosaMasukRanap"); // NOI18N
        DiagnosaMasukRanap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaMasukRanapKeyPressed(evt);
            }
        });
        FormInput.add(DiagnosaMasukRanap);
        DiagnosaMasukRanap.setBounds(274, 40, 337, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(625, 40, 60, 23);

        TglAsuhan.setDisplayFormat("dd-MM-yyyy");
        TglAsuhan.setName("TglAsuhan"); // NOI18N
        TglAsuhan.setPreferredSize(new java.awt.Dimension(95, 23));
        TglAsuhan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglAsuhanKeyPressed(evt);
            }
        });
        FormInput.add(TglAsuhan);
        TglAsuhan.setBounds(689, 40, 90, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 40, 70, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(0, 90, 160, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(164, 90, 60, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(227, 90, 30, 23);

        TB.setFocusTraversalPolicyProvider(true);
        TB.setName("TB"); // NOI18N
        TB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TBKeyPressed(evt);
            }
        });
        FormInput.add(TB);
        TB.setBounds(344, 90, 60, 23);

        jLabel15.setText("TB :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(290, 90, 50, 23);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Kg/Cm");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(583, 90, 50, 23);

        IMT.setFocusTraversalPolicyProvider(true);
        IMT.setName("IMT"); // NOI18N
        IMT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IMTKeyPressed(evt);
            }
        });
        FormInput.add(IMT);
        IMT.setBounds(520, 90, 60, 23);

        jLabel17.setText("IMT :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(456, 90, 60, 23);

        jLabel18.setText("LilA :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(649, 90, 40, 23);

        LiLA.setFocusTraversalPolicyProvider(true);
        LiLA.setName("LiLA"); // NOI18N
        LiLA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LiLAKeyPressed(evt);
            }
        });
        FormInput.add(LiLA);
        LiLA.setBounds(693, 90, 60, 23);

        jLabel22.setText("TL :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 120, 160, 23);

        TL.setFocusTraversalPolicyProvider(true);
        TL.setName("TL"); // NOI18N
        TL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TLKeyPressed(evt);
            }
        });
        FormInput.add(TL);
        TL.setBounds(164, 120, 60, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("Cm");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(756, 90, 30, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Cm");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(227, 120, 30, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText("Cm");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(407, 90, 30, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText("Cm");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(407, 120, 30, 23);

        ULNA.setFocusTraversalPolicyProvider(true);
        ULNA.setName("ULNA"); // NOI18N
        ULNA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ULNAKeyPressed(evt);
            }
        });
        FormInput.add(ULNA);
        ULNA.setBounds(344, 120, 60, 23);

        jLabel26.setText("ULNA :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(290, 120, 50, 23);

        jLabel14.setText("BB Ideal :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(456, 120, 60, 23);

        BBIdeal.setFocusTraversalPolicyProvider(true);
        BBIdeal.setName("BBIdeal"); // NOI18N
        BBIdeal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBIdealKeyPressed(evt);
            }
        });
        FormInput.add(BBIdeal);
        BBIdeal.setBounds(520, 120, 60, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("Kg");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(583, 120, 50, 23);

        jLabel28.setText("BB/U :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(649, 120, 40, 23);

        BBPerU.setFocusTraversalPolicyProvider(true);
        BBPerU.setName("BBPerU"); // NOI18N
        FormInput.add(BBPerU);
        BBPerU.setBounds(693, 120, 60, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("%");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(756, 120, 30, 23);

        jLabel30.setText("TB/U :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 150, 160, 23);

        TBPerU.setFocusTraversalPolicyProvider(true);
        TBPerU.setName("TBPerU"); // NOI18N
        FormInput.add(TBPerU);
        TBPerU.setBounds(164, 150, 60, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("%");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(227, 150, 30, 23);

        jLabel32.setText("BB/TB :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(290, 150, 50, 23);

        BBPerTB.setFocusTraversalPolicyProvider(true);
        BBPerTB.setName("BBPerTB"); // NOI18N
        FormInput.add(BBPerTB);
        BBPerTB.setBounds(344, 150, 60, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("%");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(407, 150, 30, 23);

        jLabel34.setText("LiLA/U :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(456, 150, 60, 23);

        LiLAPerU.setFocusTraversalPolicyProvider(true);
        LiLAPerU.setName("LiLAPerU"); // NOI18N
        FormInput.add(LiLAPerU);
        LiLAPerU.setBounds(520, 150, 60, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("%");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(583, 150, 50, 23);

        jLabel36.setText("Antropometri :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(0, 70, 130, 23);

        Biokimia.setFocusTraversalPolicyProvider(true);
        Biokimia.setName("Biokimia"); // NOI18N
        FormInput.add(Biokimia);
        Biokimia.setBounds(134, 180, 645, 23);

        jLabel37.setText("Fisik/Klinis :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(0, 210, 130, 23);

        FisikKlinis.setFocusTraversalPolicyProvider(true);
        FisikKlinis.setName("FisikKlinis"); // NOI18N
        FormInput.add(FisikKlinis);
        FisikKlinis.setBounds(134, 210, 645, 23);

        PolaMakan.setFocusTraversalPolicyProvider(true);
        PolaMakan.setName("PolaMakan"); // NOI18N
        FormInput.add(PolaMakan);
        PolaMakan.setBounds(164, 370, 615, 23);

        jLabel38.setText("Riwayat Personal :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(0, 400, 130, 23);

        DiagnosisGizi.setFocusTraversalPolicyProvider(true);
        DiagnosisGizi.setName("DiagnosisGizi"); // NOI18N
        FormInput.add(DiagnosisGizi);
        DiagnosisGizi.setBounds(134, 430, 645, 23);

        jLabel40.setText("Diagnosis Gizi :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(0, 430, 130, 23);

        jLabel41.setText("Intervensi Gizi :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(0, 460, 130, 23);

        IntervensiGizi.setFocusTraversalPolicyProvider(true);
        IntervensiGizi.setName("IntervensiGizi"); // NOI18N
        FormInput.add(IntervensiGizi);
        IntervensiGizi.setBounds(134, 460, 645, 23);

        jLabel42.setText("Monitoring & Evaluasi :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(0, 490, 130, 23);

        Monitoring.setFocusTraversalPolicyProvider(true);
        Monitoring.setName("Monitoring"); // NOI18N
        FormInput.add(Monitoring);
        Monitoring.setBounds(134, 490, 645, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("Telur");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(165, 280, 170, 23);

        jLabel43.setText("Riwayat Gizi :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(0, 240, 130, 23);

        jLabel44.setText("Pola Makan :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 370, 160, 23);

        buttonGroup1.add(TelurYa);
        TelurYa.setText("Ya");
        TelurYa.setName("TelurYa"); // NOI18N
        TelurYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(TelurYa);
        TelurYa.setBounds(340, 280, 45, 23);

        buttonGroup1.add(TelurTidak);
        TelurTidak.setText("Tidak");
        TelurTidak.setName("TelurTidak"); // NOI18N
        FormInput.add(TelurTidak);
        TelurTidak.setBounds(390, 280, 60, 23);

        buttonGroup2.add(SusuTidak);
        SusuTidak.setText("Tidak");
        SusuTidak.setName("SusuTidak"); // NOI18N
        FormInput.add(SusuTidak);
        SusuTidak.setBounds(390, 300, 60, 23);

        buttonGroup2.add(SusuYa);
        SusuYa.setText("Ya");
        SusuYa.setName("SusuYa"); // NOI18N
        SusuYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(SusuYa);
        SusuYa.setBounds(340, 300, 45, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Susu sapi dan produk olahannya");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(165, 300, 170, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("Kacang kedelai / tanah");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(165, 320, 170, 23);

        buttonGroup3.add(KacangYa);
        KacangYa.setText("Ya");
        KacangYa.setName("KacangYa"); // NOI18N
        KacangYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(KacangYa);
        KacangYa.setBounds(340, 320, 45, 23);

        buttonGroup3.add(KacangTidak);
        KacangTidak.setText("Tidak");
        KacangTidak.setName("KacangTidak"); // NOI18N
        FormInput.add(KacangTidak);
        KacangTidak.setBounds(390, 320, 60, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("Gluten / gandum");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(165, 340, 170, 23);

        buttonGroup4.add(GlutenYa);
        GlutenYa.setText("Ya");
        GlutenYa.setName("GlutenYa"); // NOI18N
        GlutenYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(GlutenYa);
        GlutenYa.setBounds(340, 340, 45, 23);

        buttonGroup4.add(GlutenNo);
        GlutenNo.setText("Tidak");
        GlutenNo.setName("GlutenNo"); // NOI18N
        FormInput.add(GlutenNo);
        GlutenNo.setBounds(390, 340, 60, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText("Udang");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(545, 280, 100, 23);

        buttonGroup5.add(UdangYa);
        UdangYa.setText("Ya");
        UdangYa.setName("UdangYa"); // NOI18N
        UdangYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(UdangYa);
        UdangYa.setBounds(650, 280, 45, 23);

        buttonGroup5.add(UdangTidak);
        UdangTidak.setText("Tidak");
        UdangTidak.setName("UdangTidak"); // NOI18N
        FormInput.add(UdangTidak);
        UdangTidak.setBounds(700, 280, 60, 23);

        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel49.setText("Ikan");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(545, 300, 100, 23);

        buttonGroup6.add(IkanYa);
        IkanYa.setText("Ya");
        IkanYa.setName("IkanYa"); // NOI18N
        IkanYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(IkanYa);
        IkanYa.setBounds(650, 300, 45, 23);

        buttonGroup6.add(IkanTidak);
        IkanTidak.setText("Tidak");
        IkanTidak.setName("IkanTidak"); // NOI18N
        FormInput.add(IkanTidak);
        IkanTidak.setBounds(700, 300, 60, 23);

        buttonGroup7.add(HazelnutYa);
        HazelnutYa.setText("Ya");
        HazelnutYa.setName("HazelnutYa"); // NOI18N
        HazelnutYa.setPreferredSize(new java.awt.Dimension(40, 20));
        FormInput.add(HazelnutYa);
        HazelnutYa.setBounds(650, 320, 45, 23);

        buttonGroup7.add(HazelnutTidak);
        HazelnutTidak.setText("Tidak");
        HazelnutTidak.setName("HazelnutTidak"); // NOI18N
        FormInput.add(HazelnutTidak);
        HazelnutTidak.setBounds(700, 320, 60, 23);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel51.setText("Hazelnut / almont");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(545, 320, 100, 23);

        jLabel50.setText("Alergi Makanan :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(0, 260, 160, 23);

        RiwayatPersonal.setFocusTraversalPolicyProvider(true);
        RiwayatPersonal.setName("RiwayatPersonal"); // NOI18N
        FormInput.add(RiwayatPersonal);
        RiwayatPersonal.setBounds(134, 400, 645, 23);

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
            //Valid.pindah(evt,TCari,Kejadian);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            //Valid.pindah(evt,Obat2an,BtnBatal);
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
            if(Sequel.queryu2tf("delete from resume_pasien where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()
            })==true){
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
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
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
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
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                if(TCari.getText().equals("")){
                    Valid.MyReportqry("rptDataResumePasien.jasper","report","::[ Data Resume Pasien ]::",
                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                        "resume_pasien.kd_dokter,dokter.nm_dokter,resume_pasien.kondisi_pulang,resume_pasien.keluhan_utama,resume_pasien.jalannya_penyakit, "+
                        "resume_pasien.pemeriksaan_penunjang,resume_pasien.hasil_laborat,resume_pasien.diagnosa_utama,resume_pasien.kd_diagnosa_utama, "+
                        "resume_pasien.diagnosa_sekunder,resume_pasien.kd_diagnosa_sekunder,resume_pasien.diagnosa_sekunder2,resume_pasien.kd_diagnosa_sekunder2, "+
                        "resume_pasien.diagnosa_sekunder3,resume_pasien.kd_diagnosa_sekunder3,resume_pasien.diagnosa_sekunder4,resume_pasien.kd_diagnosa_sekunder4, "+
                        "resume_pasien.prosedur_utama,resume_pasien.kd_prosedur_utama,resume_pasien.prosedur_sekunder,resume_pasien.kd_prosedur_sekunder, "+
                        "resume_pasien.prosedur_sekunder2,resume_pasien.kd_prosedur_sekunder2,resume_pasien.prosedur_sekunder3,resume_pasien.kd_prosedur_sekunder3, "+
                        "resume_pasien.obat_pulang from resume_pasien inner join reg_periksa on resume_pasien.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on resume_pasien.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut",param);
                }else{
                    Valid.MyReportqry("rptDataResumePasien.jasper","report","::[ Data Resume Pasien ]::",
                        "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                        "resume_pasien.kd_dokter,dokter.nm_dokter,resume_pasien.kondisi_pulang,resume_pasien.keluhan_utama,resume_pasien.jalannya_penyakit, "+
                        "resume_pasien.pemeriksaan_penunjang,resume_pasien.hasil_laborat,resume_pasien.diagnosa_utama,resume_pasien.kd_diagnosa_utama, "+
                        "resume_pasien.diagnosa_sekunder,resume_pasien.kd_diagnosa_sekunder,resume_pasien.diagnosa_sekunder2,resume_pasien.kd_diagnosa_sekunder2, "+
                        "resume_pasien.diagnosa_sekunder3,resume_pasien.kd_diagnosa_sekunder3,resume_pasien.diagnosa_sekunder4,resume_pasien.kd_diagnosa_sekunder4, "+
                        "resume_pasien.prosedur_utama,resume_pasien.kd_prosedur_utama,resume_pasien.prosedur_sekunder,resume_pasien.kd_prosedur_sekunder, "+
                        "resume_pasien.prosedur_sekunder2,resume_pasien.kd_prosedur_sekunder2,resume_pasien.prosedur_sekunder3,resume_pasien.kd_prosedur_sekunder3, "+
                        "resume_pasien.obat_pulang from resume_pasien inner join reg_periksa on resume_pasien.no_rawat=reg_periksa.no_rawat  "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on resume_pasien.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.status_lanjut like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien.kondisi_pulang like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien.kd_diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien.diagnosa_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien.prosedur_utama like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and resume_pasien.kd_prosedur_utama like '%"+TCari.getText().trim()+"%' "+
                        "order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut",param);
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

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
        
    }//GEN-LAST:event_KdPetugasKeyPressed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDokterKeyPressed
       
    }//GEN-LAST:event_BtnDokterKeyPressed

    private void MnLaporanResumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLaporanResumeActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            param.put("norawat",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),5).toString())); 
            if(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString().equals("Ralan")){
                param.put("ruang",Sequel.cariIsi("select poliklinik.nm_poli from poliklinik inner join reg_periksa on reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_registrasi, '%d-%m-%Y') from reg_periksa where no_rawat=?",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
            }else{
                param.put("ruang",Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
                param.put("tanggalkeluar",Sequel.cariIsi("select DATE_FORMAT(tgl_keluar, '%d-%m-%Y') from kamar_inap where no_rawat=? order by tgl_keluar desc limit 1 ",tbObat.getValueAt(tbObat.getSelectedRow(),2).toString()));
            }
            Valid.MyReport("rptLaporanResume.jasper","report","::[ Laporan Resume Pasien ]::",param);
        }
    }//GEN-LAST:event_MnLaporanResumeActionPerformed

    private void TglLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglLahirKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglLahirKeyPressed

    private void JkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JkKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_JkKeyPressed

    private void DiagnosaMasukRanapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaMasukRanapKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DiagnosaMasukRanapKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
        Valid.pindah(evt,TglAsuhan,TB);
    }//GEN-LAST:event_BBKeyPressed

    private void TglAsuhanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglAsuhanKeyPressed
        Valid.pindah(evt,TCari,BB);
    }//GEN-LAST:event_TglAsuhanKeyPressed

    private void TBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TBKeyPressed
        Valid.pindah(evt,BB,IMT);
    }//GEN-LAST:event_TBKeyPressed

    private void IMTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IMTKeyPressed
        Valid.pindah(evt,TB,LiLA);
    }//GEN-LAST:event_IMTKeyPressed

    private void LiLAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LiLAKeyPressed
        Valid.pindah(evt,IMT,TL);
    }//GEN-LAST:event_LiLAKeyPressed

    private void TLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TLKeyPressed
        Valid.pindah(evt,LiLA,ULNA);
    }//GEN-LAST:event_TLKeyPressed

    private void ULNAKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ULNAKeyPressed
        Valid.pindah(evt,TL,BBIdeal);
    }//GEN-LAST:event_ULNAKeyPressed

    private void BBIdealKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBIdealKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BBIdealKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDataAsuhanGizi dialog = new RMDataAsuhanGizi(new javax.swing.JFrame(), true);
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
    private widget.TextBox BB;
    private widget.TextBox BBIdeal;
    private widget.TextBox BBPerTB;
    private widget.TextBox BBPerU;
    private widget.TextBox Biokimia;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox DiagnosaMasukRanap;
    private widget.TextBox DiagnosisGizi;
    private widget.TextBox FisikKlinis;
    private widget.PanelBiasa FormInput;
    private widget.RadioButton GlutenNo;
    private widget.RadioButton GlutenYa;
    private widget.RadioButton HazelnutTidak;
    private widget.RadioButton HazelnutYa;
    private widget.TextBox IMT;
    private widget.RadioButton IkanTidak;
    private widget.RadioButton IkanYa;
    private widget.TextBox IntervensiGizi;
    private widget.TextBox Jk;
    private widget.RadioButton KacangTidak;
    private widget.RadioButton KacangYa;
    private widget.TextBox KdPetugas;
    private widget.Label LCount;
    private widget.TextBox LiLA;
    private widget.TextBox LiLAPerU;
    private javax.swing.JMenuItem MnLaporanResume;
    private widget.TextBox Monitoring;
    private widget.TextBox NmPetugas;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox PolaMakan;
    private widget.TextBox RiwayatPersonal;
    private widget.ScrollPane Scroll;
    private widget.RadioButton SusuTidak;
    private widget.RadioButton SusuYa;
    private widget.TextBox TB;
    private widget.TextBox TBPerU;
    private widget.TextBox TCari;
    private widget.TextBox TL;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.RadioButton TelurTidak;
    private widget.RadioButton TelurYa;
    private widget.Tanggal TglAsuhan;
    private widget.TextBox TglLahir;
    private widget.TextBox ULNA;
    private widget.RadioButton UdangTidak;
    private widget.RadioButton UdangYa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.ButtonGroup buttonGroup5;
    private javax.swing.ButtonGroup buttonGroup6;
    private javax.swing.ButtonGroup buttonGroup7;
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
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label11;
    private widget.Label label14;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "resume_pasien.kd_dokter,dokter.nm_dokter,resume_pasien.kondisi_pulang,resume_pasien.keluhan_utama,resume_pasien.jalannya_penyakit, "+
                    "resume_pasien.pemeriksaan_penunjang,resume_pasien.hasil_laborat,resume_pasien.diagnosa_utama,resume_pasien.kd_diagnosa_utama, "+
                    "resume_pasien.diagnosa_sekunder,resume_pasien.kd_diagnosa_sekunder,resume_pasien.diagnosa_sekunder2,resume_pasien.kd_diagnosa_sekunder2, "+
                    "resume_pasien.diagnosa_sekunder3,resume_pasien.kd_diagnosa_sekunder3,resume_pasien.diagnosa_sekunder4,resume_pasien.kd_diagnosa_sekunder4, "+
                    "resume_pasien.prosedur_utama,resume_pasien.kd_prosedur_utama,resume_pasien.prosedur_sekunder,resume_pasien.kd_prosedur_sekunder, "+
                    "resume_pasien.prosedur_sekunder2,resume_pasien.kd_prosedur_sekunder2,resume_pasien.prosedur_sekunder3,resume_pasien.kd_prosedur_sekunder3, "+
                    "resume_pasien.obat_pulang from resume_pasien inner join reg_periksa on resume_pasien.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on resume_pasien.kd_dokter=dokter.kd_dokter "+
                    "where reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.tgl_registrasi,reg_periksa.no_rawat,reg_periksa.status_lanjut,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                    "resume_pasien.kd_dokter,dokter.nm_dokter,resume_pasien.kondisi_pulang,resume_pasien.keluhan_utama,resume_pasien.jalannya_penyakit, "+
                    "resume_pasien.pemeriksaan_penunjang,resume_pasien.hasil_laborat,resume_pasien.diagnosa_utama,resume_pasien.kd_diagnosa_utama, "+
                    "resume_pasien.diagnosa_sekunder,resume_pasien.kd_diagnosa_sekunder,resume_pasien.diagnosa_sekunder2,resume_pasien.kd_diagnosa_sekunder2, "+
                    "resume_pasien.diagnosa_sekunder3,resume_pasien.kd_diagnosa_sekunder3,resume_pasien.diagnosa_sekunder4,resume_pasien.kd_diagnosa_sekunder4, "+
                    "resume_pasien.prosedur_utama,resume_pasien.kd_prosedur_utama,resume_pasien.prosedur_sekunder,resume_pasien.kd_prosedur_sekunder, "+
                    "resume_pasien.prosedur_sekunder2,resume_pasien.kd_prosedur_sekunder2,resume_pasien.prosedur_sekunder3,resume_pasien.kd_prosedur_sekunder3, "+
                    "resume_pasien.obat_pulang from resume_pasien inner join reg_periksa on resume_pasien.no_rawat=reg_periksa.no_rawat  "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join dokter on resume_pasien.kd_dokter=dokter.kd_dokter "+
                    "where reg_periksa.tgl_registrasi between ? and ? and reg_periksa.status_lanjut like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and pasien.nm_pasien like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien.kd_dokter like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and dokter.nm_dokter like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien.kondisi_pulang like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien.kd_diagnosa_utama like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien.diagnosa_utama like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien.prosedur_utama like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rawat like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and resume_pasien.kd_prosedur_utama like ? "+
                    "order by reg_periksa.tgl_registrasi,reg_periksa.status_lanjut");
            }
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(15,"%"+TCari.getText()+"%");
                    ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(18,"%"+TCari.getText()+"%");
                    ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(21,"%"+TCari.getText()+"%");
                    ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(24,"%"+TCari.getText()+"%");
                    ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(27,"%"+TCari.getText()+"%");
                    ps.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(30,"%"+TCari.getText()+"%");
                    ps.setString(31,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(32,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(33,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("tgl_registrasi"),rs.getString("status_lanjut"),rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("kondisi_pulang"),rs.getString("keluhan_utama"),
                        rs.getString("jalannya_penyakit"),rs.getString("pemeriksaan_penunjang"),rs.getString("hasil_laborat"),rs.getString("diagnosa_utama"),
                        rs.getString("kd_diagnosa_utama"),rs.getString("diagnosa_sekunder"),rs.getString("kd_diagnosa_sekunder"),rs.getString("diagnosa_sekunder2"),
                        rs.getString("kd_diagnosa_sekunder2"),rs.getString("diagnosa_sekunder3"),rs.getString("kd_diagnosa_sekunder3"),rs.getString("diagnosa_sekunder4"),
                        rs.getString("kd_diagnosa_sekunder4"),rs.getString("prosedur_utama"),rs.getString("kd_prosedur_utama"),rs.getString("prosedur_sekunder"),
                        rs.getString("kd_prosedur_sekunder"),rs.getString("prosedur_sekunder2"),rs.getString("kd_prosedur_sekunder2"),rs.getString("prosedur_sekunder3"),
                        rs.getString("kd_prosedur_sekunder3"),rs.getString("obat_pulang") 
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
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        int b=tabMode.getRowCount();
        LCount.setText(""+b);
    }

    public void emptTeks() {
        BB.setText("");
        TB.setText("");
        IMT.setText("");
        LiLA.setText("");
        TL.setText("");
        ULNA.setText("");
        BBIdeal.setText("");
        BBPerU.setText("");
        TBPerU.setText("");
        BBPerTB.setText("");
        LiLAPerU.setText("");
        Biokimia.setText("");
        FisikKlinis.setText("");
        RiwayatPersonal.setText("");
        PolaMakan.setText("");
        DiagnosisGizi.setText("");
        IntervensiGizi.setText("");
        Monitoring.setText("");
        BB.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());  
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat=? ",TNoRM,TNoRw.getText());
         Sequel.cariIsi("select diagnosa_awal from kamar_inap where diagnosa_awal<>'' and no_rawat=? ",DiagnosaMasukRanap,TNoRw.getText());
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=? ",TPasien,TNoRM.getText());
        Sequel.cariIsi("select if(jk='L','Laki-Laki','Perempuan') from pasien where no_rkm_medis=? ",Jk,TNoRM.getText());
        Sequel.cariIsi("select tgl_lahir from pasien where no_rkm_medis=? ",TglLahir,TNoRM.getText());
    }
    
    public void setNoRm(String norwt, Date tgl1, Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);    
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-122));
            scrollInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            scrollInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getasuhan_gizi());
        BtnHapus.setEnabled(akses.getasuhan_gizi());
        BtnEdit.setEnabled(akses.getasuhan_gizi());
        BtnPrint.setEnabled(akses.getasuhan_gizi());   
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            BtnDokter.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", NmPetugas,KdPetugas.getText());
            if(NmPetugas.getText().equals("")){
                KdPetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        }            
    }

    
}

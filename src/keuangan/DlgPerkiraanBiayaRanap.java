/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package keuangan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;

/**
 *
 * @author perpustakaan
 */
public final class DlgPerkiraanBiayaRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabModeDiagnosa,tabModeNilaiRS,tabModeNilaiINACBG;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,pspenyakit;
    private ResultSet rs,rs2;
    private DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    private double all=0,Laborat=0,Radiologi=0,Operasi=0,Obat=0,Ranap_Dokter=0,Ranap_Paramedis=0,Ranap_Dokter_Paramedis=0,Ralan_Dokter=0,
             Ralan_Paramedis=0,Ralan_Dokter_Paramedis=0,Tambahan=0,Potongan=0,Kamar=0,Registrasi=0,Harian=0,Retur_Obat=0,Resep_Pulang=0,
             Deposit=0,Jumlah,ttlLaborat=0,ttlRadiologi=0,ttlOperasi=0,ttlObat=0,ttlRanap_Dokter=0,ttlRanap_Paramedis=0,ttlRalan_Dokter=0,
             ttlRalan_Paramedis=0,ttlTambahan=0,ttlPotongan=0,ttlKamar=0,ttlRegistrasi=0,ttlHarian=0,ttlRetur_Obat=0,ttlResep_Pulang=0,
             ttlDeposit,perkiraantarif;
    private String namakamar,diag,pros;
    private int i=0;
    private WebEngine engine;
    
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgPerkiraanBiayaRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.RM","Nama Pasien","Kamar/Bangsal","Perujuk","Registrasi","Tindakan","Obt+Emb+Tsl","Retur Obat",
                "Resep Pulang","Laborat","Radiologi","Potongan","Tambahan","Kamar","Operasi","Harian","Total","Deposit","Kekurangan",
                "Diagnosa Awal","ICD 10","Perkiraan Tarif","Limit"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 24; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(130);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else if(i==18){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(100);
            }else if(i==21){
                column.setPreferredWidth(45);
            }else if(i==22){
                column.setPreferredWidth(85);
            }else if(i==23){
                column.setPreferredWidth(65);
            }else{
                column.setPreferredWidth(75);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDiagnosa=new DefaultTableModel(null,new Object[]{
            "Kode","Nama Penyakit","Ciri-ciri Penyakit","Keterangan","Ktg.Penyakit","Ciri-ciri Umum"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDiagnosa.setModel(tabModeDiagnosa);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbDiagnosa.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDiagnosa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i= 0; i < 6; i++) {
            TableColumn column = tbDiagnosa.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(280);
            }else if(i==2){
                column.setPreferredWidth(250);
            }else if(i==3){
                column.setPreferredWidth(75);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(200);
            }
        }
        tbDiagnosa.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeNilaiRS=new DefaultTableModel(null,new Object[]{
                "Keterangan","Lama Inap","Biaya","Diagnosa Penyerta","Prosedur"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbNilaiRS.setModel(tabModeNilaiRS);
        tbNilaiRS.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbNilaiRS.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbNilaiRS.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(260);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(190);
            }else if(i==4){
                column.setPreferredWidth(190);
            }
        }
        tbNilaiRS.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeNilaiINACBG=new DefaultTableModel(null,new Object[]{
                "Keterangan","Lama Inap","Biaya","Diagnosa Penyerta","Prosedur"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class,java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbNilaiINACBG.setModel(tabModeNilaiINACBG);
        tbNilaiINACBG.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbNilaiINACBG.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbNilaiINACBG.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(260);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(190);
            }else if(i==4){
                column.setPreferredWidth(190);
            }
        }
        tbNilaiINACBG.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        Diagnosa.setDocument(new batasInput((int)100).getKata(Diagnosa));
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){
                    NmBangsal.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                }      
                TCari.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {bangsal.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        bangsal.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    bangsal.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        ChkCari.setSelected(false);
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
        MnJadikanPerkiraan = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        MnJadikanPerkiraan1 = new javax.swing.JMenuItem();
        jPopupMenu3 = new javax.swing.JPopupMenu();
        MnJadikanPerkiraan2 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        FormCari = new javax.swing.JPanel();
        panelCari = new widget.panelisi();
        Scroll3 = new widget.ScrollPane();
        tbNilaiRS = new widget.Table();
        Scroll4 = new widget.ScrollPane();
        tbNilaiINACBG = new widget.Table();
        ChkCari = new widget.CekBox();
        panelGlass5 = new widget.panelisi();
        label17 = new widget.Label();
        NmBangsal = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        BtnAll = new widget.Button();
        label10 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelDiagnosa = new widget.PanelBiasa();
        jLabel13 = new widget.Label();
        Diagnosa = new widget.TextBox();
        BtnCariPenyakit = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbDiagnosa = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnJadikanPerkiraan.setBackground(new java.awt.Color(255, 255, 254));
        MnJadikanPerkiraan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadikanPerkiraan.setForeground(java.awt.Color.darkGray);
        MnJadikanPerkiraan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJadikanPerkiraan.setText("Jadikan Perkiraan Biaya Pasien");
        MnJadikanPerkiraan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadikanPerkiraan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadikanPerkiraan.setName("MnJadikanPerkiraan"); // NOI18N
        MnJadikanPerkiraan.setPreferredSize(new java.awt.Dimension(250, 28));
        MnJadikanPerkiraan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJadikanPerkiraanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnJadikanPerkiraan);

        jPopupMenu2.setName("jPopupMenu2"); // NOI18N

        MnJadikanPerkiraan1.setBackground(new java.awt.Color(255, 255, 254));
        MnJadikanPerkiraan1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadikanPerkiraan1.setForeground(java.awt.Color.darkGray);
        MnJadikanPerkiraan1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJadikanPerkiraan1.setText("Jadikan Perkiraan Biaya Pasien");
        MnJadikanPerkiraan1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadikanPerkiraan1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadikanPerkiraan1.setName("MnJadikanPerkiraan1"); // NOI18N
        MnJadikanPerkiraan1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnJadikanPerkiraan1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJadikanPerkiraan1ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(MnJadikanPerkiraan1);

        jPopupMenu3.setName("jPopupMenu3"); // NOI18N

        MnJadikanPerkiraan2.setBackground(new java.awt.Color(255, 255, 254));
        MnJadikanPerkiraan2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadikanPerkiraan2.setForeground(java.awt.Color.darkGray);
        MnJadikanPerkiraan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnJadikanPerkiraan2.setText("Ambil Perkiraan Simulasi Inacbg");
        MnJadikanPerkiraan2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadikanPerkiraan2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadikanPerkiraan2.setName("MnJadikanPerkiraan2"); // NOI18N
        MnJadikanPerkiraan2.setPreferredSize(new java.awt.Dimension(250, 28));
        MnJadikanPerkiraan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJadikanPerkiraan2ActionPerformed(evt);
            }
        });
        jPopupMenu3.add(MnJadikanPerkiraan2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Perkiraan Biaya Ranap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        FormCari.setName("FormCari"); // NOI18N
        FormCari.setOpaque(false);
        FormCari.setPreferredSize(new java.awt.Dimension(100, 412));
        FormCari.setLayout(new java.awt.BorderLayout(1, 1));

        panelCari.setBorder(null);
        panelCari.setName("panelCari"); // NOI18N
        panelCari.setPreferredSize(new java.awt.Dimension(44, 44));
        panelCari.setLayout(new java.awt.GridLayout(2, 0, 0, 1));

        Scroll3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)), "Berdasar Data Tarif Rumah Sakit 2 Tahun Terakhir", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11))); // NOI18N
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);
        Scroll3.setPreferredSize(new java.awt.Dimension(452, 100));

        tbNilaiRS.setComponentPopupMenu(jPopupMenu1);
        tbNilaiRS.setName("tbNilaiRS"); // NOI18N
        Scroll3.setViewportView(tbNilaiRS);

        panelCari.add(Scroll3);

        Scroll4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)), "Berdasar Data Nilai Balik INACBG 2 Tahun Terakhir", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11))); // NOI18N
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(452, 100));

        tbNilaiINACBG.setComponentPopupMenu(jPopupMenu2);
        tbNilaiINACBG.setName("tbNilaiINACBG"); // NOI18N
        Scroll4.setViewportView(tbNilaiINACBG);

        panelCari.add(Scroll4);

        FormCari.add(panelCari, java.awt.BorderLayout.CENTER);

        ChkCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkCari.setMnemonic('I');
        ChkCari.setText(".: Simulasi Biaya Berdasarkan Diagnosa");
        ChkCari.setToolTipText("Alt+I");
        ChkCari.setBorderPainted(true);
        ChkCari.setBorderPaintedFlat(true);
        ChkCari.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkCari.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkCari.setIconTextGap(2);
        ChkCari.setName("ChkCari"); // NOI18N
        ChkCari.setPreferredSize(new java.awt.Dimension(632, 22));
        ChkCari.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkCari.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkCari.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkCariActionPerformed(evt);
            }
        });
        FormCari.add(ChkCari, java.awt.BorderLayout.PAGE_START);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("Kamar/Bangsal :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass5.add(label17);

        NmBangsal.setEditable(false);
        NmBangsal.setName("NmBangsal"); // NOI18N
        NmBangsal.setPreferredSize(new java.awt.Dimension(200, 23));
        panelGlass5.add(NmBangsal);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        panelGlass5.add(BtnSeek2);

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelGlass5.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
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
        panelGlass5.add(BtnCari1);

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
        panelGlass5.add(BtnAll);

        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(28, 23));
        panelGlass5.add(label10);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(28, 23));
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
        panelGlass5.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
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

        FormCari.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        panelDiagnosa.setBorder(null);
        panelDiagnosa.setName("panelDiagnosa"); // NOI18N
        panelDiagnosa.setPreferredSize(new java.awt.Dimension(500, 217));
        panelDiagnosa.setLayout(null);

        jLabel13.setText("Diagnosa :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelDiagnosa.add(jLabel13);
        jLabel13.setBounds(0, 10, 60, 23);

        Diagnosa.setHighlighter(null);
        Diagnosa.setName("Diagnosa"); // NOI18N
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        panelDiagnosa.add(Diagnosa);
        Diagnosa.setBounds(63, 10, 384, 23);

        BtnCariPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit.setMnemonic('1');
        BtnCariPenyakit.setToolTipText("Alt+1");
        BtnCariPenyakit.setName("BtnCariPenyakit"); // NOI18N
        BtnCariPenyakit.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakitActionPerformed(evt);
            }
        });
        panelDiagnosa.add(BtnCariPenyakit);
        BtnCariPenyakit.setBounds(450, 10, 28, 23);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(452, 100));

        tbDiagnosa.setComponentPopupMenu(jPopupMenu3);
        tbDiagnosa.setName("tbDiagnosa"); // NOI18N
        tbDiagnosa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDiagnosaMouseClicked(evt);
            }
        });
        Scroll1.setViewportView(tbDiagnosa);

        panelDiagnosa.add(Scroll1);
        Scroll1.setBounds(2, 36, 497, 295);

        FormCari.add(panelDiagnosa, java.awt.BorderLayout.WEST);

        internalFrame1.add(FormCari, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            for(int r=0;r<tabMode.getRowCount();r++){  
                    Sequel.menyimpan("temporary","'"+r+"','"+
                                    tabMode.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                    tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,9).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,10).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,11).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,12).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,13).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,14).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,15).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,16).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,17).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,18).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,19).toString().replaceAll("'","`")+"','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Perkiraan Ranap");
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptPerkiraanBiayaRanap.jasper","report","::[ Perkiraan Biaya Rawat Inap ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        NmBangsal.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnSeek2.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void ChkCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkCariActionPerformed
        isForm();
    }//GEN-LAST:event_ChkCariActionPerformed

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampildiagnosa();   
            Valid.tabelKosong(tabModeNilaiRS);
            Valid.tabelKosong(tabModeNilaiINACBG);
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDiagnosa.requestFocus();
        }
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void BtnCariPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakitActionPerformed
        tampildiagnosa();
        Valid.tabelKosong(tabModeNilaiRS);
        Valid.tabelKosong(tabModeNilaiINACBG);
    }//GEN-LAST:event_BtnCariPenyakitActionPerformed

    private void tbDiagnosaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDiagnosaMouseClicked
        if(tbDiagnosa.getSelectedRow()!= -1){
            try {
                Valid.tabelKosong(tabModeNilaiRS);
                ps=koneksi.prepareStatement(
                        "select diagnosa_pasien.no_rawat,sum(billing.totalbiaya) as biaya from diagnosa_pasien inner join reg_periksa on diagnosa_pasien.no_rawat=reg_periksa.no_rawat "+
                        "inner join billing on diagnosa_pasien.no_rawat=billing.no_rawat "+
                        "where reg_periksa.tgl_registrasi between date_sub(CURRENT_DATE(),INTERVAL 2 YEAR) and CURRENT_DATE() and reg_periksa.status_lanjut='Ranap' "+
                        "and diagnosa_pasien.prioritas='1' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.kd_penyakit=? group by diagnosa_pasien.no_rawat order by sum(billing.totalbiaya) desc limit 1");
                try {
                    ps.setString(1,tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        diag="";
                        ps2=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.no_rawat=? and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas>'1'");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                diag=rs2.getString("kd_penyakit")+", "+diag;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        
                        pros="";
                        ps2=koneksi.prepareStatement("select prosedur_pasien.kode from prosedur_pasien where prosedur_pasien.no_rawat=? and prosedur_pasien.status='Ranap'");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                pros=rs2.getString("kode")+", "+pros;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        
                        tabModeNilaiRS.addRow(new Object[]{
                            "Tarif rawat inap tertinggi diagnosa "+tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString(),Sequel.cariIsi("select sum(lama) from kamar_inap where no_rawat=?",rs.getString("no_rawat")),rs.getDouble("biaya"),diag,pros
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
                
                ps=koneksi.prepareStatement(
                        "select diagnosa_pasien.no_rawat,sum(billing.totalbiaya) as biaya from diagnosa_pasien inner join reg_periksa on diagnosa_pasien.no_rawat=reg_periksa.no_rawat "+
                        "inner join billing on diagnosa_pasien.no_rawat=billing.no_rawat "+
                        "where reg_periksa.tgl_registrasi between date_sub(CURRENT_DATE(),INTERVAL 2 YEAR) and CURRENT_DATE() and reg_periksa.status_lanjut='Ranap' "+
                        "and diagnosa_pasien.prioritas='1' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.kd_penyakit=? group by diagnosa_pasien.no_rawat order by sum(billing.totalbiaya) asc limit 1");
                try {
                    ps.setString(1,tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        diag="";
                        ps2=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.no_rawat=? and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas>'1'");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                diag=rs2.getString("kd_penyakit")+", "+diag;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        
                        pros="";
                        ps2=koneksi.prepareStatement("select prosedur_pasien.kode from prosedur_pasien where prosedur_pasien.no_rawat=? and prosedur_pasien.status='Ranap'");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                pros=rs2.getString("kode")+", "+pros;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        
                        tabModeNilaiRS.addRow(new Object[]{
                            "Tarif rawat inap terendah diagnosa "+tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString(),Sequel.cariIsi("select sum(lama) from kamar_inap where no_rawat=?",rs.getString("no_rawat")),rs.getDouble("biaya"),diag,pros
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
                
                Valid.tabelKosong(tabModeNilaiINACBG);
                ps=koneksi.prepareStatement(
                        "select diagnosa_pasien.no_rawat,inacbg_grouping_stage1.tarif as biaya from diagnosa_pasien inner join reg_periksa on diagnosa_pasien.no_rawat=reg_periksa.no_rawat "+
                        "inner join bridging_sep on reg_periksa.no_rawat=bridging_sep.no_rawat inner join inacbg_grouping_stage1 on inacbg_grouping_stage1.no_sep=bridging_sep.no_sep "+
                        "where reg_periksa.tgl_registrasi between date_sub(CURRENT_DATE(),INTERVAL 2 YEAR) and CURRENT_DATE() and reg_periksa.status_lanjut='Ranap' "+
                        "and diagnosa_pasien.prioritas='1' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.kd_penyakit=? group by diagnosa_pasien.no_rawat order by inacbg_grouping_stage1.tarif desc limit 1");
                try {
                    ps.setString(1,tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        diag="";
                        ps2=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.no_rawat=? and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas>'1'");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                diag=rs2.getString("kd_penyakit")+", "+diag;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        
                        pros="";
                        ps2=koneksi.prepareStatement("select prosedur_pasien.kode from prosedur_pasien where prosedur_pasien.no_rawat=? and prosedur_pasien.status='Ranap'");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                pros=rs2.getString("kode")+", "+pros;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        
                        tabModeNilaiINACBG.addRow(new Object[]{
                            "Tarif rawat inap tertinggi diagnosa "+tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString(),Sequel.cariIsi("select sum(lama) from kamar_inap where no_rawat=?",rs.getString("no_rawat")),rs.getDouble("biaya"),diag,pros
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
                
                ps=koneksi.prepareStatement(
                        "select diagnosa_pasien.no_rawat,inacbg_grouping_stage1.tarif as biaya from diagnosa_pasien inner join reg_periksa on diagnosa_pasien.no_rawat=reg_periksa.no_rawat "+
                        "inner join bridging_sep on reg_periksa.no_rawat=bridging_sep.no_rawat inner join inacbg_grouping_stage1 on inacbg_grouping_stage1.no_sep=bridging_sep.no_sep "+
                        "where reg_periksa.tgl_registrasi between date_sub(CURRENT_DATE(),INTERVAL 2 YEAR) and CURRENT_DATE() and reg_periksa.status_lanjut='Ranap' "+
                        "and diagnosa_pasien.prioritas='1' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.kd_penyakit=? group by diagnosa_pasien.no_rawat order by inacbg_grouping_stage1.tarif asc limit 1");
                try {
                    ps.setString(1,tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        diag="";
                        ps2=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.no_rawat=? and diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas>'1'");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                diag=rs2.getString("kd_penyakit")+", "+diag;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        
                        pros="";
                        ps2=koneksi.prepareStatement("select prosedur_pasien.kode from prosedur_pasien where prosedur_pasien.no_rawat=? and prosedur_pasien.status='Ranap'");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                pros=rs2.getString("kode")+", "+pros;
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                        
                        tabModeNilaiINACBG.addRow(new Object[]{
                            "Tarif rawat inap terendah diagnosa "+tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString(),Sequel.cariIsi("select sum(lama) from kamar_inap where no_rawat=?",rs.getString("no_rawat")),rs.getDouble("biaya"),diag,pros
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
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            }
        }
    }//GEN-LAST:event_tbDiagnosaMouseClicked

    private void MnJadikanPerkiraanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJadikanPerkiraanActionPerformed
        if(tbBangsal.getSelectedRow()!= -1){
            if(tbDiagnosa.getSelectedRow()!= -1){
                if(tbNilaiRS.getSelectedRow()!= -1){
                    Sequel.meghapus("perkiraan_biaya_ranap","no_rawat",tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString());
                    if(Sequel.menyimpantf2("perkiraan_biaya_ranap","?,?,?","data",3,new String[]{tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString(),tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString(),tbNilaiRS.getValueAt(tbNilaiRS.getSelectedRow(),2).toString()})==true){
                        tampil();
                    }else{
                        JOptionPane.showMessageDialog(null,"Silahkan Anda pilih dulu pasien yang mau dimasukkan perkiraannya ...!!");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Silahkan Anda pilih dulu perkiraan biaya pasien yang mau dimasukkan perkiraannya ...!!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan Anda pilih dulu diagnosa pasien yang mau dimasukkan perkiraannya ...!!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan Anda pilih dulu pasien yang mau dimasukkan perkiraannya ...!!");
        }
    }//GEN-LAST:event_MnJadikanPerkiraanActionPerformed

    private void MnJadikanPerkiraan1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJadikanPerkiraan1ActionPerformed
        if(tbBangsal.getSelectedRow()!= -1){
            if(tbDiagnosa.getSelectedRow()!= -1){
                if(tbNilaiINACBG.getSelectedRow()!= -1){
                    Sequel.meghapus("perkiraan_biaya_ranap","no_rawat",tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString());
                    if(Sequel.menyimpantf2("perkiraan_biaya_ranap","?,?,?","data",3,new String[]{tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString(),tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString(),tbNilaiINACBG.getValueAt(tbNilaiINACBG.getSelectedRow(),2).toString()})==true){
                        tampil();
                    }else{
                        JOptionPane.showMessageDialog(null,"Silahkan Anda pilih dulu pasien yang mau dimasukkan perkiraannya ...!!");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Silahkan Anda pilih dulu perkiraan biaya pasien yang mau dimasukkan perkiraannya ...!!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan Anda pilih dulu diagnosa pasien yang mau dimasukkan perkiraannya ...!!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan Anda pilih dulu pasien yang mau dimasukkan perkiraannya ...!!");
        }
    }//GEN-LAST:event_MnJadikanPerkiraan1ActionPerformed

    private void MnJadikanPerkiraan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJadikanPerkiraan2ActionPerformed
        if(tbBangsal.getSelectedRow()!= -1){
            if(tbDiagnosa.getSelectedRow()!= -1){
                loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"inacbg/pages/perkiraantarif.php?norawat="+tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString()+"&penyakit="+tbDiagnosa.getValueAt(tbDiagnosa.getSelectedRow(),0).toString());
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan Anda pilih dulu diagnosa pasien yang mau dimasukkan perkiraannya ...!!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan Anda pilih dulu pasien yang mau dimasukkan perkiraannya ...!!");
        }
    }//GEN-LAST:event_MnJadikanPerkiraan2ActionPerformed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tbBangsal.getSelectedRow()!= -1){
            Diagnosa.setText(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),20).toString());
        }
    }//GEN-LAST:event_tbBangsalMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPerkiraanBiayaRanap dialog = new DlgPerkiraanBiayaRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnCariPenyakit;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private widget.CekBox ChkCari;
    public widget.TextBox Diagnosa;
    private javax.swing.JPanel FormCari;
    private javax.swing.JMenuItem MnJadikanPerkiraan;
    private javax.swing.JMenuItem MnJadikanPerkiraan1;
    private javax.swing.JMenuItem MnJadikanPerkiraan2;
    private widget.TextBox NmBangsal;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel13;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JPopupMenu jPopupMenu3;
    private widget.Label label10;
    private widget.Label label17;
    private widget.Label label9;
    private widget.panelisi panelCari;
    public widget.PanelBiasa panelDiagnosa;
    private widget.panelisi panelGlass5;
    private widget.Table tbBangsal;
    public widget.Table tbDiagnosa;
    public widget.Table tbNilaiINACBG;
    public widget.Table tbNilaiRS;
    // End of variables declaration//GEN-END:variables

    public void tampil(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        Valid.tabelKosong(tabMode);
        try{      
            ps= koneksi.prepareStatement(
                "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,bangsal.nm_bangsal,kamar.kd_kamar,reg_periksa.biaya_reg, "+
                "kamar_inap.diagnosa_awal from kamar_inap inner join reg_periksa inner join pasien inner join bangsal inner join kamar "+
                "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "+
                "where kamar_inap.stts_pulang='-' and bangsal.nm_bangsal like ? and kamar_inap.no_rawat like ? or "+
                "kamar_inap.stts_pulang='-' and bangsal.nm_bangsal like ? and reg_periksa.no_rkm_medis like ? or "+
                "kamar_inap.stts_pulang='-' and bangsal.nm_bangsal like ? and pasien.nm_pasien like ? or "+
                "kamar_inap.stts_pulang='-' and bangsal.nm_bangsal like ? and kamar.kd_kamar like ? "+
                "order by bangsal.nm_bangsal");
            try {
                ps.setString(1,"%"+NmBangsal.getText()+"%");
                ps.setString(2,"%"+TCari.getText()+"%");
                ps.setString(3,"%"+NmBangsal.getText()+"%");
                ps.setString(4,"%"+TCari.getText()+"%");
                ps.setString(5,"%"+NmBangsal.getText()+"%");
                ps.setString(6,"%"+TCari.getText()+"%");
                ps.setString(7,"%"+NmBangsal.getText()+"%");
                ps.setString(8,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                all=0;ttlLaborat=0;ttlRadiologi=0;ttlOperasi=0;ttlObat=0;
                ttlRanap_Dokter=0;ttlRanap_Paramedis=0;ttlRalan_Dokter=0;
                ttlRalan_Paramedis=0;ttlTambahan=0;ttlPotongan=0;ttlKamar=0;
                ttlRegistrasi=0;ttlHarian=0;ttlRetur_Obat=0;ttlResep_Pulang=0;
                ttlDeposit=0;
                while(rs.next()){
                    Registrasi=0;
                    Registrasi=rs.getDouble("biaya_reg");
                    ttlRegistrasi=ttlRegistrasi+Registrasi;
                    
                    Laborat=0;
                    Laborat=Sequel.cariIsiAngka("select sum(biaya) from periksa_lab where no_rawat=?",rs.getString("no_rawat"))+Sequel.cariIsiAngka("select sum(biaya_item) from detail_periksa_lab where no_rawat=?",rs.getString("no_rawat"));
                    ttlLaborat=ttlLaborat+Laborat;
                    
                    Radiologi=0;
                    Radiologi=Sequel.cariIsiAngka("select sum(biaya) from periksa_radiologi where no_rawat=?",rs.getString("no_rawat"));
                    ttlRadiologi=ttlRadiologi+Radiologi;
                    
                    Operasi=0;
                    Operasi=Sequel.cariIsiAngka("select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayadokter_anak+biayaperawaat_resusitas+biayadokter_anestesi+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biayaalat+biayasewaok+akomodasi+bagian_rs+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5+biayasarpras+biaya_dokter_pjanak+biaya_dokter_umum) from operasi where no_rawat=?",rs.getString("no_rawat"));
                    ttlOperasi=ttlOperasi+Operasi;
                    
                    Obat=0;
                    Obat=Sequel.cariIsiAngka("select sum(total) from detail_pemberian_obat where no_rawat=?",rs.getString("no_rawat"))+Sequel.cariIsiAngka("select sum(besar_tagihan) from tagihan_obat_langsung where no_rawat=?",rs.getString("no_rawat"))+Sequel.cariIsiAngka("select sum(hargasatuan*jumlah) from beri_obat_operasi where no_rawat=?",rs.getString("no_rawat"));
                    ttlObat=ttlObat+Obat;
                    
                    Ranap_Dokter=0;
                    Ranap_Dokter=Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_inap_dr where no_rawat=?",rs.getString("no_rawat"));
                    ttlRanap_Dokter=ttlRanap_Dokter+Ranap_Dokter;
                    
                    Ranap_Dokter_Paramedis=0;
                    Ranap_Dokter_Paramedis=Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_inap_drpr where no_rawat=?",rs.getString("no_rawat"));
                    ttlRanap_Dokter=ttlRanap_Dokter+Ranap_Dokter_Paramedis;
                    
                    Ranap_Paramedis=0;
                    Ranap_Paramedis=Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_inap_pr where no_rawat=?",rs.getString("no_rawat"));
                    ttlRanap_Paramedis=ttlRanap_Paramedis+Ranap_Paramedis;
                    
                    Ralan_Dokter=0;
                    Ralan_Dokter=Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_jl_dr where no_rawat=?",rs.getString("no_rawat"));
                    ttlRalan_Dokter=ttlRalan_Dokter+Ralan_Dokter;
                    
                    Ralan_Dokter_Paramedis=0;
                    Ralan_Dokter_Paramedis=Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_jl_drpr where no_rawat=?",rs.getString("no_rawat"));
                    ttlRalan_Dokter=ttlRalan_Dokter+Ralan_Dokter_Paramedis;
                    
                    Ralan_Paramedis=0;
                    Ralan_Paramedis=Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_jl_pr where no_rawat=?",rs.getString("no_rawat"));
                    ttlRalan_Paramedis=ttlRalan_Paramedis+Ralan_Paramedis;
                    
                    Tambahan=0;
                    Tambahan=Sequel.cariIsiAngka("select sum(besar_biaya) from tambahan_biaya where no_rawat=?",rs.getString("no_rawat"));
                    ttlTambahan=ttlTambahan+Tambahan;
                    
                    Potongan=0;
                    Potongan=Sequel.cariIsiAngka("select sum(besar_pengurangan) from pengurangan_biaya where no_rawat=?",rs.getString("no_rawat"));
                    ttlPotongan=ttlPotongan+Potongan;
                    
                    Kamar=0;
                    Kamar=Sequel.cariIsiAngka("select sum(ttl_biaya) from kamar_inap where no_rawat=?",rs.getString("no_rawat"))+Sequel.cariIsiAngka("select sum(biaya_sekali.besar_biaya) from biaya_sekali inner join kamar_inap on kamar_inap.kd_kamar=biaya_sekali.kd_kamar where kamar_inap.no_rawat=?",rs.getString("no_rawat"));
                    ttlKamar=ttlKamar+Kamar;
                    
                    Harian=0;
                    Harian=Sequel.cariIsiAngka("select sum(biaya_harian.jml*biaya_harian.besar_biaya*kamar_inap.lama) from kamar_inap inner join biaya_harian on kamar_inap.kd_kamar=biaya_harian.kd_kamar where kamar_inap.no_rawat=?",rs.getString("no_rawat"));
                    ttlHarian=ttlHarian+Harian;
                    
                    Retur_Obat=0;
                    Retur_Obat=(-1)*Sequel.cariIsiAngka("select sum(subtotal) from detreturjual where no_retur_jual like ? ","%"+rs.getString("no_rawat")+"%");
                    ttlRetur_Obat=ttlRetur_Obat+Retur_Obat;
                    
                    Resep_Pulang=0;
                    Resep_Pulang=Sequel.cariIsiAngka("select sum(total) from resep_pulang where no_rawat=? ",rs.getString("no_rawat"));
                    ttlResep_Pulang=ttlResep_Pulang+Resep_Pulang;
                    
                    Deposit=0;
                    Deposit=Sequel.cariIsiAngka("select sum(besar_deposit) from deposit where no_rawat=? ",rs.getString("no_rawat"));
                    ttlDeposit=ttlDeposit+Deposit;
                    
                    ps2=koneksi.prepareStatement(
                        "select no_rawat2 from ranap_gabung where no_rawat=?");   
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            Laborat=Laborat+Sequel.cariIsiAngka("select sum(biaya) from periksa_lab where no_rawat=?",rs2.getString("no_rawat2"))+Sequel.cariIsiAngka("select sum(biaya_item) from detail_periksa_lab where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlLaborat=ttlLaborat+Laborat;

                            Radiologi=Radiologi+Sequel.cariIsiAngka("select sum(biaya) from periksa_radiologi where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlRadiologi=ttlRadiologi+Radiologi;

                            Operasi=Operasi+Sequel.cariIsiAngka("select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+biayainstrumen+biayadokter_anak+biayaperawaat_resusitas+biayadokter_anestesi+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+biayabidan3+biayaperawat_luar+biayaalat+biayasewaok+akomodasi+bagian_rs+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5+biayasarpras+biaya_dokter_pjanak+biaya_dokter_umum) from operasi where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlOperasi=ttlOperasi+Operasi;

                            Obat=Obat+Sequel.cariIsiAngka("select sum(total) from detail_pemberian_obat where no_rawat=?",rs2.getString("no_rawat2"))+Sequel.cariIsiAngka("select sum(besar_tagihan) from tagihan_obat_langsung where no_rawat=?",rs2.getString("no_rawat2"))+Sequel.cariIsiAngka("select sum(hargasatuan*jumlah) from beri_obat_operasi where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlObat=ttlObat+Obat;

                            Ranap_Dokter=Ranap_Dokter+Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_inap_dr where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlRanap_Dokter=ttlRanap_Dokter+Ranap_Dokter;

                            Ranap_Dokter_Paramedis=Ranap_Dokter_Paramedis+Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_inap_drpr where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlRanap_Dokter=ttlRanap_Dokter+Ranap_Dokter_Paramedis;

                            Ranap_Paramedis=Ranap_Paramedis+Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_inap_pr where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlRanap_Paramedis=ttlRanap_Paramedis+Ranap_Paramedis;

                            Ralan_Dokter=Ralan_Dokter+Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_jl_dr where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlRalan_Dokter=ttlRalan_Dokter+Ralan_Dokter;

                            Ralan_Dokter_Paramedis=Ralan_Dokter_Paramedis+Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_jl_drpr where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlRalan_Dokter=ttlRalan_Dokter+Ralan_Dokter_Paramedis;

                            Ralan_Paramedis=Ralan_Paramedis+Sequel.cariIsiAngka("select sum(biaya_rawat) from rawat_jl_pr where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlRalan_Paramedis=ttlRalan_Paramedis+Ralan_Paramedis;

                            Tambahan=Tambahan+Sequel.cariIsiAngka("select sum(besar_biaya) from tambahan_biaya where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlTambahan=ttlTambahan+Tambahan;

                            Potongan=Potongan+Sequel.cariIsiAngka("select sum(besar_pengurangan) from pengurangan_biaya where no_rawat=?",rs2.getString("no_rawat2"));
                            ttlPotongan=ttlPotongan+Potongan;

                            Kamar=Kamar+Sequel.cariIsiAngka("select sum(ttl_biaya) from kamar_inap where no_rawat=?",rs2.getString("no_rawat2"))+Sequel.cariIsiAngka("select sum(biaya_sekali.besar_biaya) from biaya_sekali inner join kamar_inap on kamar_inap.kd_kamar=biaya_sekali.kd_kamar where kamar_inap.no_rawat=?",rs2.getString("no_rawat2"));
                            ttlKamar=ttlKamar+Kamar;

                            Harian=Harian+Sequel.cariIsiAngka("select sum(biaya_harian.jml*biaya_harian.besar_biaya*kamar_inap.lama) from kamar_inap inner join biaya_harian on kamar_inap.kd_kamar=biaya_harian.kd_kamar where kamar_inap.no_rawat=?",rs2.getString("no_rawat2"));
                            ttlHarian=ttlHarian+Harian;

                            Retur_Obat=Retur_Obat+(-1)*Sequel.cariIsiAngka("select sum(subtotal) from detreturjual where no_retur_jual like ? ","%"+rs2.getString("no_rawat2")+"%");
                            ttlRetur_Obat=ttlRetur_Obat+Retur_Obat;

                            Resep_Pulang=Resep_Pulang+Sequel.cariIsiAngka("select sum(total) from resep_pulang where no_rawat=? ",rs2.getString("no_rawat2"));
                            ttlResep_Pulang=ttlResep_Pulang+Resep_Pulang;
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    Jumlah=Laborat+Radiologi+Operasi+Obat+Ranap_Dokter+Ranap_Dokter_Paramedis+Ranap_Paramedis+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis+Tambahan+Potongan+Kamar+Registrasi+Harian+Retur_Obat+Resep_Pulang;
                    
                    diag="";
                    perkiraantarif=0;
                    pros="Aman";
                    ps2=koneksi.prepareStatement(
                        "select * from perkiraan_biaya_ranap where no_rawat=?");  
                    try{
                        ps2.setString(1,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            diag=rs2.getString("kd_penyakit");
                            perkiraantarif=rs2.getDouble("tarif");
                            if(perkiraantarif<=Jumlah){
                                pros="Tidak Aman";  
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("kd_kamar")+" "+rs.getString("nm_bangsal"),
                        Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?",rs.getString("no_rawat")),Valid.SetAngka(Registrasi),
                        Valid.SetAngka(Ranap_Dokter+Ranap_Dokter_Paramedis+Ranap_Paramedis+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis),
                        Valid.SetAngka(Obat),Valid.SetAngka(Retur_Obat),Valid.SetAngka(Resep_Pulang),Valid.SetAngka(Laborat),Valid.SetAngka(Radiologi),Valid.SetAngka(Potongan),
                        Valid.SetAngka(Tambahan),Valid.SetAngka(Kamar),Valid.SetAngka(Operasi),Valid.SetAngka(Harian),Valid.SetAngka(Jumlah),
                        Valid.SetAngka(Deposit),Valid.SetAngka(Deposit-Jumlah),rs.getString("diagnosa_awal"),diag,Valid.SetAngka(perkiraantarif),pros
                    });
                    all=all+Laborat+Radiologi+Operasi+Obat+Ranap_Dokter+Ranap_Dokter_Paramedis+Ranap_Paramedis+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis+Tambahan+Potongan+Kamar+Registrasi+Harian+Retur_Obat+Resep_Pulang;
                }
                tabMode.addRow(new Object[]{
                    ">> Total ",":","","","",Valid.SetAngka(ttlRegistrasi),Valid.SetAngka(ttlRanap_Dokter+ttlRanap_Paramedis+ttlRalan_Dokter+ttlRalan_Paramedis),
                    Valid.SetAngka(ttlObat),Valid.SetAngka(ttlRetur_Obat),Valid.SetAngka(ttlResep_Pulang),Valid.SetAngka(ttlLaborat),Valid.SetAngka(ttlRadiologi),Valid.SetAngka(ttlPotongan),
                    Valid.SetAngka(ttlTambahan),Valid.SetAngka(ttlKamar),Valid.SetAngka(ttlOperasi),Valid.SetAngka(ttlHarian),Valid.SetAngka(all),Valid.SetAngka(ttlDeposit),
                    Valid.SetAngka(ttlDeposit-all),"","","",""
                });
            } catch (Exception e) {
                System.out.println("Notif 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void isCek(){
        try {
            namakamar=koneksiDB.KAMARAKTIFRANAP();
        } catch (Exception ex) {
            namakamar="";
        }
        
        if(!namakamar.equals("")){
            if(akses.getkode().equals("Admin Utama")){
                NmBangsal.setText("");
                BtnSeek2.setEnabled(true);
                NmBangsal.setEditable(true);
            }else{
                NmBangsal.setText(namakamar);
                BtnSeek2.setEnabled(false);
                NmBangsal.setEditable(false);
            }                
        }else{
            BtnSeek2.setEnabled(true);
            NmBangsal.setEditable(true);
        }
    }


    private void isForm(){
        if(ChkCari.isSelected()==true){
            ChkCari.setVisible(false);
            FormCari.setPreferredSize(new Dimension(WIDTH,412));
            panelDiagnosa.setVisible(true);
            panelCari.setVisible(true);      
            ChkCari.setVisible(true);
        }else if(ChkCari.isSelected()==false){           
            ChkCari.setVisible(false);            
            FormCari.setPreferredSize(new Dimension(WIDTH,78));
            panelCari.setVisible(false);   
            panelDiagnosa.setVisible(false);
            ChkCari.setVisible(true);
        }
    }
    
    private void tampildiagnosa() {
        try{
            Valid.tabelKosong(tabModeDiagnosa);
            pspenyakit=koneksi.prepareStatement("select penyakit.kd_penyakit,penyakit.nm_penyakit,penyakit.ciri_ciri,penyakit.keterangan, "+
                    "kategori_penyakit.nm_kategori,kategori_penyakit.ciri_umum "+
                    "from kategori_penyakit inner join penyakit "+
                    "on penyakit.kd_ktg=kategori_penyakit.kd_ktg where  "+
                    " penyakit.kd_penyakit like ? or "+
                    " penyakit.nm_penyakit like ? or "+
                    " penyakit.ciri_ciri like ? or "+
                    " penyakit.keterangan like ? or "+
                    " kategori_penyakit.nm_kategori like ? or "+
                    " kategori_penyakit.ciri_umum like ? "+
                    "order by penyakit.kd_penyakit  LIMIT 1000");
            try {
                pspenyakit.setString(1,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(2,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(3,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(4,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(5,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(6,"%"+Diagnosa.getText().trim()+"%");  
                rs=pspenyakit.executeQuery();
                while(rs.next()){
                    tabModeDiagnosa.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6)
                    });
                } 
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pspenyakit!=null){
                    pspenyakit.close();
                }
            }           
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void loadURL(String url) {
        try {
            createScene();
        } catch (Exception e) {
        }
        
        Platform.runLater(() -> {
            try {
                engine.load(url);
            }catch (Exception exception) {
                engine.load(url);
            }
        });   
    } 
    
    private void createScene() {        
        Platform.runLater(new Runnable() {

            public void run() {
                WebView view = new WebView();
                
                engine = view.getEngine();
                engine.setJavaScriptEnabled(true);
                
                engine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
                    @Override
                    public WebEngine call(PopupFeatures p) {
                        Stage stage = new Stage(StageStyle.TRANSPARENT);
                        return view.getEngine();
                    }
                });
                
                engine.getLoadWorker().exceptionProperty().addListener((ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) -> {
                    if (engine.getLoadWorker().getState() == FAILED) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(
                                    null,
                                    (value != null) ?
                                            engine.getLocation() + "\n" + value.getMessage() :
                                            engine.getLocation() + "\nUnexpected Catatan.",
                                    "Loading Catatan...",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    }
                });
                
                engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            try {
                                if(engine.getLocation().contains("Tampil")){
                                    tampil();
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : "+ex);
                            }
                        }
                    }
                });
            }
        });
    }

}

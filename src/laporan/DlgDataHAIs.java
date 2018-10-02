/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

package laporan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
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


/**
 *
 * @author perpustakaan
 */
public final class DlgDataHAIs extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private Date date = new Date();
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public DlgDataHAIs(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);


        Object[] row={
            "Tanggal","No.Rawat","No.R.M.","Nama Pasien","ETT","CVL","IVL","UC","VAP",
            "IAD","PLEB","ISK","Deku","Sputum","Darah","Urine","Antibiotik"
        };
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(75);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(180);
            }else if(i==4){
                column.setPreferredWidth(35);
            }else if(i==5){
                column.setPreferredWidth(35);
            }else if(i==6){
                column.setPreferredWidth(35);
            }else if(i==7){
                column.setPreferredWidth(35);
            }else if(i==8){
                column.setPreferredWidth(35);
            }else if(i==9){
                column.setPreferredWidth(35);
            }else if(i==10){
                column.setPreferredWidth(35);
            }else if(i==11){
                column.setPreferredWidth(35);
            }else if(i==12){
                column.setPreferredWidth(70);
            }else if(i==13){
                column.setPreferredWidth(130);
            }else if(i==14){
                column.setPreferredWidth(130);
            }else if(i==15){
                column.setPreferredWidth(130);
            }else if(i==16){
                column.setPreferredWidth(130);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        ETT.setDocument(new batasInput((byte)2).getOnlyAngka(ETT));
        CVL.setDocument(new batasInput((byte)2).getOnlyAngka(CVL));
        IVL.setDocument(new batasInput((byte)2).getOnlyAngka(IVL));
        UC.setDocument(new batasInput((byte)2).getOnlyAngka(UC));
        VAP.setDocument(new batasInput((byte)2).getOnlyAngka(VAP));
        IAD.setDocument(new batasInput((byte)2).getOnlyAngka(IAD));
        PLEB.setDocument(new batasInput((byte)2).getOnlyAngka(PLEB));
        ISK.setDocument(new batasInput((byte)2).getOnlyAngka(ISK));
        Sputum.setDocument(new batasInput((int)200).getKata(Sputum));
        Darah.setDocument(new batasInput((int)200).getKata(Darah));
        Urine.setDocument(new batasInput((int)200).getKata(Urine));
        Antibiotik.setDocument(new batasInput((int)200).getKata(Antibiotik));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        }
        
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
        jLabel3 = new widget.Label();
        CVL = new widget.TextBox();
        jLabel8 = new widget.Label();
        ETT = new widget.TextBox();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Tanggal = new widget.Tanggal();
        jLabel12 = new widget.Label();
        TNoRM = new widget.TextBox();
        jLabel13 = new widget.Label();
        Deku = new widget.ComboBox();
        jLabel5 = new widget.Label();
        IVL = new widget.TextBox();
        jLabel9 = new widget.Label();
        UC = new widget.TextBox();
        jLabel10 = new widget.Label();
        VAP = new widget.TextBox();
        jLabel11 = new widget.Label();
        IAD = new widget.TextBox();
        jLabel14 = new widget.Label();
        PLEB = new widget.TextBox();
        jLabel15 = new widget.Label();
        ISK = new widget.TextBox();
        jLabel16 = new widget.Label();
        jLabel17 = new widget.Label();
        jLabel18 = new widget.Label();
        Sputum = new widget.TextBox();
        Urine = new widget.TextBox();
        jLabel20 = new widget.Label();
        Antibiotik = new widget.TextBox();
        jLabel22 = new widget.Label();
        Darah = new widget.TextBox();
        jLabel23 = new widget.Label();
        ChkInput = new widget.CekBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data HAIs ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(130, 100, 100))); // NOI18N
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

        jLabel19.setText("Tanggal :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setEditable(false);
        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-05-2018" }));
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

        DTPCari2.setEditable(false);
        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-05-2018" }));
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
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 185));
        FormInput.setLayout(null);

        jLabel3.setText("CVL :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(416, 50, 40, 23);

        CVL.setHighlighter(null);
        CVL.setName("CVL"); // NOI18N
        CVL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CVLKeyPressed(evt);
            }
        });
        FormInput.add(CVL);
        CVL.setBounds(460, 50, 40, 23);

        jLabel8.setText("Infeksi Rumah Sakit :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel8);
        jLabel8.setBounds(190, 80, 130, 23);

        ETT.setHighlighter(null);
        ETT.setName("ETT"); // NOI18N
        ETT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ETTKeyPressed(evt);
            }
        });
        FormInput.add(ETT);
        ETT.setBounds(369, 50, 40, 23);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 71, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(75, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(340, 10, 340, 23);

        Tanggal.setEditable(false);
        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-05-2018" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(75, 40, 100, 23);

        jLabel12.setText("ETT :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(330, 50, 35, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(218, 10, 120, 23);

        jLabel13.setText("Deku :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 80, 71, 23);

        Deku.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "IYA", "TIDAK" }));
        Deku.setName("Deku"); // NOI18N
        Deku.setOpaque(false);
        Deku.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DekuKeyPressed(evt);
            }
        });
        FormInput.add(Deku);
        Deku.setBounds(75, 80, 100, 23);

        jLabel5.setText("IVL :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(511, 50, 35, 23);

        IVL.setHighlighter(null);
        IVL.setName("IVL"); // NOI18N
        IVL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IVLKeyPressed(evt);
            }
        });
        FormInput.add(IVL);
        IVL.setBounds(550, 50, 40, 23);

        jLabel9.setText("UC :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(601, 50, 35, 23);

        UC.setHighlighter(null);
        UC.setName("UC"); // NOI18N
        UC.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UCKeyPressed(evt);
            }
        });
        FormInput.add(UC);
        UC.setBounds(640, 50, 40, 23);

        jLabel10.setText("VAP :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(330, 90, 35, 23);

        VAP.setHighlighter(null);
        VAP.setName("VAP"); // NOI18N
        VAP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VAPKeyPressed(evt);
            }
        });
        FormInput.add(VAP);
        VAP.setBounds(369, 90, 40, 23);

        jLabel11.setText("IAD :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(416, 90, 40, 23);

        IAD.setHighlighter(null);
        IAD.setName("IAD"); // NOI18N
        IAD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IADKeyPressed(evt);
            }
        });
        FormInput.add(IAD);
        IAD.setBounds(460, 90, 40, 23);

        jLabel14.setText("PLEB :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(511, 90, 35, 23);

        PLEB.setHighlighter(null);
        PLEB.setName("PLEB"); // NOI18N
        PLEB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PLEBKeyPressed(evt);
            }
        });
        FormInput.add(PLEB);
        PLEB.setBounds(550, 90, 40, 23);

        jLabel15.setText("ISK :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(601, 90, 35, 23);

        ISK.setHighlighter(null);
        ISK.setName("ISK"); // NOI18N
        ISK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ISKKeyPressed(evt);
            }
        });
        FormInput.add(ISK);
        ISK.setBounds(640, 90, 40, 23);

        jLabel16.setText("Tanggal :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 71, 23);

        jLabel17.setText("Hari Pemasangan Alat :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel17);
        jLabel17.setBounds(195, 40, 130, 23);

        jLabel18.setText("Sputum :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 120, 71, 23);

        Sputum.setHighlighter(null);
        Sputum.setName("Sputum"); // NOI18N
        Sputum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SputumKeyPressed(evt);
            }
        });
        FormInput.add(Sputum);
        Sputum.setBounds(75, 120, 260, 23);

        Urine.setHighlighter(null);
        Urine.setName("Urine"); // NOI18N
        Urine.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrineKeyPressed(evt);
            }
        });
        FormInput.add(Urine);
        Urine.setBounds(420, 120, 260, 23);

        jLabel20.setText("Urine :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(345, 120, 71, 23);

        Antibiotik.setHighlighter(null);
        Antibiotik.setName("Antibiotik"); // NOI18N
        Antibiotik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AntibiotikKeyPressed(evt);
            }
        });
        FormInput.add(Antibiotik);
        Antibiotik.setBounds(420, 150, 260, 23);

        jLabel22.setText("Antibiotik :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(345, 150, 71, 23);

        Darah.setHighlighter(null);
        Darah.setName("Darah"); // NOI18N
        Darah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DarahKeyPressed(evt);
            }
        });
        FormInput.add(Darah);
        Darah.setBounds(75, 150, 260, 23);

        jLabel23.setText("Darah :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(0, 150, 71, 23);

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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CVLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CVLKeyPressed
        Valid.pindah(evt,ETT,IVL);
}//GEN-LAST:event_CVLKeyPressed

    private void ETTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ETTKeyPressed
        Valid.pindah(evt,Tanggal,CVL);
}//GEN-LAST:event_ETTKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            //Valid.pindah(evt,TTmpRujuk,TDiagnosa);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        //Valid.pindah(evt,TDokter,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(ETT.getText().trim().equals("")){
            Valid.textKosong(ETT,"ETT");
        }else if(CVL.getText().trim().equals("")){
            Valid.textKosong(CVL,"CVL");
        }else if(IVL.getText().trim().equals("")){
            Valid.textKosong(IVL,"IVL");
        }else if(UC.getText().trim().equals("")){
            Valid.textKosong(UC,"UC");
        }else if(VAP.getText().trim().equals("")){
            Valid.textKosong(VAP,"VAP");
        }else if(IAD.getText().trim().equals("")){
            Valid.textKosong(IAD,"IAD");
        }else if(PLEB.getText().trim().equals("")){
            Valid.textKosong(PLEB,"PLEB");
        }else if(ISK.getText().trim().equals("")){
            Valid.textKosong(ISK,"ISK");
        }else{
            if(Sequel.menyimpantf("data_HAIs","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",15,new String[]{
                    Valid.SetTgl(Tanggal.getSelectedItem()+""),TNoRw.getText(),ETT.getText(),CVL.getText(),
                    IVL.getText(),UC.getText(),VAP.getText(),IAD.getText(),PLEB.getText(),ISK.getText(),
                    Deku.getSelectedItem().toString(),Sputum.getText(),Darah.getText(),Urine.getText(),Antibiotik.getText()
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
            Valid.pindah(evt,Antibiotik,BtnBatal);
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
        if(tbObat.getSelectedRow()!= -1){
            if(Sequel.queryu2tf("delete from data_HAIs where tanggal=? and no_rawat=?",2,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()
            })==true){
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
            }
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
        }else if(ETT.getText().trim().equals("")){
            Valid.textKosong(ETT,"ETT");
        }else if(CVL.getText().trim().equals("")){
            Valid.textKosong(CVL,"CVL");
        }else if(IVL.getText().trim().equals("")){
            Valid.textKosong(IVL,"IVL");
        }else if(UC.getText().trim().equals("")){
            Valid.textKosong(UC,"UC");
        }else if(VAP.getText().trim().equals("")){
            Valid.textKosong(VAP,"VAP");
        }else if(IAD.getText().trim().equals("")){
            Valid.textKosong(IAD,"IAD");
        }else if(PLEB.getText().trim().equals("")){
            Valid.textKosong(PLEB,"PLEB");
        }else if(ISK.getText().trim().equals("")){
            Valid.textKosong(ISK,"ISK");
        }else{         
            Sequel.mengedit("data_HAIs","tanggal=? and no_rawat=?","tanggal=?,no_rawat=?,ETT=?,CVL=?,IVL=?,UC=?,VAP=?,IAD=?,PLEB=?,ISK=?,DEKU=?,SPUTUM=?,DARAH=?,URINE=?,ANTIBIOTIK=?",17,new String[]{
                Valid.SetTgl(Tanggal.getSelectedItem()+""),TNoRw.getText(),ETT.getText(),CVL.getText(),
                IVL.getText(),UC.getText(),VAP.getText(),IAD.getText(),PLEB.getText(),ISK.getText(),
                Deku.getSelectedItem().toString(),Sputum.getText(),Darah.getText(),Urine.getText(),Antibiotik.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),1).toString()
            });
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
        if(! TCari.getText().trim().equals("")){
            BtnCariActionPerformed(evt);
        }
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>(); 
                param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("tanggal1",Valid.SetTgl(DTPCari1.getSelectedItem()+""));   
                param.put("tanggal2",Valid.SetTgl(DTPCari2.getSelectedItem()+""));   
                param.put("parameter","%"+TCari.getText().trim()+"%");   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptDataHAIs.jrxml",param,"::[ Data HAIs Pasien ]::");
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
        Valid.pindah(evt,TNoRw,ETT);
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

    private void DekuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DekuKeyPressed
        Valid.pindah(evt,UC,VAP);
    }//GEN-LAST:event_DekuKeyPressed

    private void IVLKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IVLKeyPressed
        Valid.pindah(evt,CVL,UC);
    }//GEN-LAST:event_IVLKeyPressed

    private void UCKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UCKeyPressed
        Valid.pindah(evt,IVL,Deku);
    }//GEN-LAST:event_UCKeyPressed

    private void VAPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VAPKeyPressed
        Valid.pindah(evt,Deku,IAD);
    }//GEN-LAST:event_VAPKeyPressed

    private void IADKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IADKeyPressed
        Valid.pindah(evt,VAP,PLEB);
    }//GEN-LAST:event_IADKeyPressed

    private void PLEBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PLEBKeyPressed
        Valid.pindah(evt,IAD,ISK);
    }//GEN-LAST:event_PLEBKeyPressed

    private void ISKKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ISKKeyPressed
       Valid.pindah(evt,PLEB,Sputum);
    }//GEN-LAST:event_ISKKeyPressed

    private void SputumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SputumKeyPressed
        Valid.pindah(evt,ISK,Urine);
    }//GEN-LAST:event_SputumKeyPressed

    private void UrineKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrineKeyPressed
        Valid.pindah(evt,Sputum,Darah);
    }//GEN-LAST:event_UrineKeyPressed

    private void AntibiotikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AntibiotikKeyPressed
        Valid.pindah(evt,Darah,BtnSimpan);
    }//GEN-LAST:event_AntibiotikKeyPressed

    private void DarahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DarahKeyPressed
        Valid.pindah(evt,Urine,Antibiotik);
    }//GEN-LAST:event_DarahKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDataHAIs dialog = new DlgDataHAIs(new javax.swing.JFrame(), true);
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
    private widget.TextBox Antibiotik;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox CVL;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Darah;
    private widget.ComboBox Deku;
    private widget.TextBox ETT;
    private widget.PanelBiasa FormInput;
    private widget.TextBox IAD;
    private widget.TextBox ISK;
    private widget.TextBox IVL;
    private widget.Label LCount;
    private widget.TextBox PLEB;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox Sputum;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox UC;
    private widget.TextBox Urine;
    private widget.TextBox VAP;
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
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
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
            ps=koneksi.prepareStatement(
                    "select data_HAIs.tanggal,data_HAIs.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "data_HAIs.ETT,data_HAIs.CVL,data_HAIs.IVL,data_HAIs.UC,data_HAIs.VAP,data_HAIs.IAD,"+
                    "data_HAIs.PLEB,data_HAIs.ISK,data_HAIs.DEKU,data_HAIs.SPUTUM,data_HAIs.DARAH,data_HAIs.URINE,"+
                    "data_HAIs.ANTIBIOTIK from data_HAIs inner join reg_periksa inner join pasien on "+
                    "data_HAIs.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                    "data_HAIs.tanggal between ? and ? and data_HAIs.no_rawat like ? or "+
                    "data_HAIs.tanggal between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "data_HAIs.tanggal between ? and ? and  pasien.nm_pasien like ? order by data_HAIs.tanggal ");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),
                        rs.getString(10),rs.getString(11),rs.getString(12),
                        rs.getString(13),rs.getString(14),rs.getString(15),
                        rs.getString(16),rs.getString(17)
                    });
                }
            } catch (Exception e) {
                System.out.println("simrskhanza.DlgRujuk.tampil() : "+e);
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
        Tanggal.requestFocus();
        ETT.setText("0");
        CVL.setText("0");
        IVL.setText("0");
        UC.setText("0");
        VAP.setText("0");
        IAD.setText("0");
        PLEB.setText("0");
        ISK.setText("0");
        Sputum.setText("");
        Urine.setText("");
        Darah.setText("");
        Antibiotik.setText("");
        Tanggal.setDate(new Date());
    }

   

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            ETT.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            CVL.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            IVL.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            UC.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            VAP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            IAD.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            PLEB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            ISK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Deku.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Sputum.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Darah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Urine.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Antibiotik.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
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
            PanelInput.setPreferredSize(new Dimension(WIDTH,206));
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
        BtnSimpan.setEnabled(var.getdata_HAIs());
        BtnHapus.setEnabled(var.getdata_HAIs());
        BtnPrint.setEnabled(var.getdata_HAIs());
    }

   
}

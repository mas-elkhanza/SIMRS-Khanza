/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laporan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author khanzamedia
 */
public class PanelDiagnosa extends widget.panelisi {
    private final DefaultTableModel TabModeDiagnosaPasien,tabModeDiagnosa,tabModeProsedur,TabModeTindakanPasien;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement pspenyakit,psdiagnosapasien,psprosedur,pstindakanpasien;
    private ResultSet rs;
    private int jml=0,i=0,index=0;
    private String[] kode,nama,ciripny,keterangan,kategori,cirium,kode2,panjang,pendek;
    private boolean[] pilih,pilih2;
    public String norawat="",status="",norm="",tanggal1="",tanggal2="",keyword="";
    /**
     * Creates new form panelDiagnosa
     */
    public PanelDiagnosa() {
        initComponents();
        TabModeDiagnosaPasien=new DefaultTableModel(null,new Object[]{
            "P","Tgl.Rawat","No.Rawat","No.R.M.","Nama Pasien","Kode","Nama Penyakit",
            "Status","Kasus"}){
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
                java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDiagnosaPasien.setModel(TabModeDiagnosaPasien);
        tbDiagnosaPasien.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDiagnosaPasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbDiagnosaPasien.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(160);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(350);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(50);
            }
        }
        tbDiagnosaPasien.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeDiagnosa=new DefaultTableModel(null,new Object[]{
            "P","Kode","Nama Penyakit","Ciri-ciri Penyakit","Keterangan","Ktg.Penyakit","Ciri-ciri Umum"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDiagnosa.setModel(tabModeDiagnosa);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbDiagnosa.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDiagnosa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i= 0; i < 7; i++) {
            TableColumn column = tbDiagnosa.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(40);
            }else if(i==2){
                column.setPreferredWidth(280);
            }else if(i==3){
                column.setPreferredWidth(285);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(75);
            }else if(i==6){
                column.setPreferredWidth(75);
            }
        }
        tbDiagnosa.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeProsedur=new DefaultTableModel(null,new Object[]{
            "P","Kode","Deskripsi Panjang","Deskripsi Pendek"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbProsedur.setModel(tabModeProsedur);
        //tbPenyakit.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPenyakit.getBackground()));
        tbProsedur.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbProsedur.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbProsedur.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(50);
            }else if(i==2){
                column.setPreferredWidth(350);
            }else if(i==3){
                column.setPreferredWidth(350);
            }
        }
        tbProsedur.setDefaultRenderer(Object.class, new WarnaTable());
        
        TabModeTindakanPasien=new DefaultTableModel(null,new Object[]{
            "P","Tgl.Rawat","No.Rawat","No.R.M.","Nama Pasien","Kode","Nama Prosedur","Status"}){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbTindakanPasien.setModel(TabModeTindakanPasien);
        tbTindakanPasien.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTindakanPasien.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbTindakanPasien.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(110);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(160);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(300);
            }else if(i==7){
                column.setPreferredWidth(50);
            }
        }
        tbTindakanPasien.setDefaultRenderer(Object.class, new WarnaTable());
           
        Diagnosa.setDocument(new batasInput((byte)100).getKata(Diagnosa));
        Prosedur.setDocument(new batasInput((byte)100).getKata(Prosedur));
        
        if(koneksiDB.cariCepat().equals("aktif")){
            Diagnosa.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(Diagnosa.getText().length()>2){
                        tampildiagnosa();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(Diagnosa.getText().length()>2){
                        tampildiagnosa();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(Diagnosa.getText().length()>2){
                        tampildiagnosa();
                    }
                }
            });
        } 
        
        if(koneksiDB.cariCepat().equals("aktif")){
            Prosedur.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(Prosedur.getText().length()>2){
                        tampilprosedure();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(Prosedur.getText().length()>2){
                        tampilprosedure();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(Prosedur.getText().length()>2){
                        tampilprosedure();
                    }
                }
            });
        } 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnStatusBaru = new javax.swing.JMenuItem();
        MnStatusLama = new javax.swing.JMenuItem();
        TabRawat = new javax.swing.JTabbedPane();
        ScrollInput = new widget.ScrollPane();
        FormData = new widget.PanelBiasa();
        jLabel13 = new widget.Label();
        Diagnosa = new widget.TextBox();
        BtnCariPenyakit = new widget.Button();
        btnTambahPenyakit = new widget.Button();
        Scroll1 = new widget.ScrollPane();
        tbDiagnosa = new widget.Table();
        jLabel15 = new widget.Label();
        Prosedur = new widget.TextBox();
        btnTambahProsedur = new widget.Button();
        BtnCariProsedur = new widget.Button();
        Scroll2 = new widget.ScrollPane();
        tbProsedur = new widget.Table();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbDiagnosaPasien = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbTindakanPasien = new widget.Table();

        MnStatusBaru.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusBaru.setForeground(new java.awt.Color(70, 70, 70));
        MnStatusBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusBaru.setText("Status Penyakit Baru");
        MnStatusBaru.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusBaru.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusBaru.setPreferredSize(new java.awt.Dimension(170, 26));
        MnStatusBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusBaruActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnStatusBaru);

        MnStatusLama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusLama.setForeground(new java.awt.Color(70, 70, 70));
        MnStatusLama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnStatusLama.setText("Status Penyakit Lama");
        MnStatusLama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusLama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusLama.setIconTextGap(5);
        MnStatusLama.setPreferredSize(new java.awt.Dimension(170, 26));
        MnStatusLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusLamaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnStatusLama);

        setLayout(new java.awt.BorderLayout(1, 1));

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        ScrollInput.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ScrollInput.setOpaque(true);

        FormData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        FormData.setPreferredSize(new java.awt.Dimension(865, 217));
        FormData.setLayout(null);

        jLabel13.setText("Diagnosa :");
        FormData.add(jLabel13);
        jLabel13.setBounds(0, 10, 68, 23);

        Diagnosa.setHighlighter(null);
        Diagnosa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKeyPressed(evt);
            }
        });
        FormData.add(Diagnosa);
        Diagnosa.setBounds(71, 10, 687, 23);

        BtnCariPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariPenyakit.setMnemonic('1');
        BtnCariPenyakit.setToolTipText("Alt+1");
        BtnCariPenyakit.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPenyakitActionPerformed(evt);
            }
        });
        FormData.add(BtnCariPenyakit);
        BtnCariPenyakit.setBounds(761, 10, 28, 23);

        btnTambahPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnTambahPenyakit.setMnemonic('2');
        btnTambahPenyakit.setToolTipText("Alt+2");
        btnTambahPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahPenyakitActionPerformed(evt);
            }
        });
        FormData.add(btnTambahPenyakit);
        btnTambahPenyakit.setBounds(792, 10, 28, 23);

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll1.setOpaque(true);
        Scroll1.setViewportView(tbDiagnosa);

        FormData.add(Scroll1);
        Scroll1.setBounds(11, 36, 810, 165);

        jLabel15.setText("Prosedur :");
        FormData.add(jLabel15);
        jLabel15.setBounds(0, 211, 68, 23);

        Prosedur.setHighlighter(null);
        Prosedur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProsedurKeyPressed(evt);
            }
        });
        FormData.add(Prosedur);
        Prosedur.setBounds(71, 211, 687, 23);

        btnTambahProsedur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnTambahProsedur.setMnemonic('2');
        btnTambahProsedur.setToolTipText("Alt+2");
        btnTambahProsedur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahProsedurActionPerformed(evt);
            }
        });
        FormData.add(btnTambahProsedur);
        btnTambahProsedur.setBounds(792, 211, 28, 23);

        BtnCariProsedur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCariProsedur.setMnemonic('1');
        BtnCariProsedur.setToolTipText("Alt+1");
        BtnCariProsedur.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCariProsedur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariProsedurActionPerformed(evt);
            }
        });
        FormData.add(BtnCariProsedur);
        BtnCariProsedur.setBounds(761, 211, 28, 23);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)));
        Scroll2.setOpaque(true);

        tbProsedur.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        Scroll2.setViewportView(tbProsedur);

        FormData.add(Scroll2);
        Scroll2.setBounds(11, 237, 810, 165);

        ScrollInput.setViewportView(FormData);

        TabRawat.addTab("Input Data", ScrollInput);

        internalFrame2.setBorder(null);
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setOpaque(true);

        tbDiagnosaPasien.setAutoCreateRowSorter(true);
        tbDiagnosaPasien.setComponentPopupMenu(jPopupMenu1);
        Scroll.setViewportView(tbDiagnosaPasien);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Data Diagnosa", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setOpaque(true);

        tbTindakanPasien.setAutoCreateRowSorter(true);
        tbTindakanPasien.setComponentPopupMenu(jPopupMenu1);
        Scroll3.setViewportView(tbTindakanPasien);

        internalFrame3.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Data Prosedur", internalFrame3);

        add(TabRawat, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void DiagnosaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampildiagnosa();            
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            if(akses.getpenyakit()==true){
                btnTambahPenyakitActionPerformed(null);
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDiagnosa.requestFocus();
        }
    }//GEN-LAST:event_DiagnosaKeyPressed

    private void BtnCariPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPenyakitActionPerformed
        tampildiagnosa();
    }//GEN-LAST:event_BtnCariPenyakitActionPerformed

    private void btnTambahPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPenyakitActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgPenyakit tariflab=new DlgPenyakit(null,false);
        tariflab.emptTeks();
        tariflab.isCek();
        tariflab.setSize(this.getWidth(),this.getHeight());
        tariflab.setLocationRelativeTo(this);
        tariflab.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTambahPenyakitActionPerformed

    private void ProsedurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProsedurKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampilprosedure();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            if(akses.geticd9()==true){
                btnTambahProsedurActionPerformed(null);
            }
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbProsedur.requestFocus();
        }
    }//GEN-LAST:event_ProsedurKeyPressed

    private void btnTambahProsedurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahProsedurActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgICD9 tariflab=new DlgICD9(null,false);
        tariflab.emptTeks();
        tariflab.isCek();
        tariflab.setSize(this.getWidth(),this.getHeight()-20);
        tariflab.setLocationRelativeTo(this);
        tariflab.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_btnTambahProsedurActionPerformed

    private void BtnCariProsedurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariProsedurActionPerformed
        tampilprosedure();
    }//GEN-LAST:event_BtnCariProsedurActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        pilihTab();
    }//GEN-LAST:event_TabRawatMouseClicked

    private void MnStatusBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusBaruActionPerformed
        if(norawat.equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Sequel.queryu2("update diagnosa_pasien set status_penyakit='Baru' where no_rawat=? and kd_penyakit=?",2,new String[]{
                tbDiagnosaPasien.getValueAt(tbDiagnosaPasien.getSelectedRow(),2).toString(),tbDiagnosaPasien.getValueAt(tbDiagnosaPasien.getSelectedRow(),5).toString()
            });
            tampil();
        }
    }//GEN-LAST:event_MnStatusBaruActionPerformed

    private void MnStatusLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusLamaActionPerformed
        if(norawat.equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            Sequel.queryu2("update diagnosa_pasien set status_penyakit='Lama' where no_rawat=? and kd_penyakit=?",2,new String[]{
                tbDiagnosaPasien.getValueAt(tbDiagnosaPasien.getSelectedRow(),2).toString(),tbDiagnosaPasien.getValueAt(tbDiagnosaPasien.getSelectedRow(),5).toString()
            });
            tampil();
        }
    }//GEN-LAST:event_MnStatusLamaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnCariPenyakit;
    private widget.Button BtnCariProsedur;
    public widget.TextBox Diagnosa;
    public widget.PanelBiasa FormData;
    private javax.swing.JMenuItem MnStatusBaru;
    private javax.swing.JMenuItem MnStatusLama;
    private widget.TextBox Prosedur;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    public widget.ScrollPane ScrollInput;
    public javax.swing.JTabbedPane TabRawat;
    public widget.Button btnTambahPenyakit;
    public widget.Button btnTambahProsedur;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel13;
    private widget.Label jLabel15;
    private javax.swing.JPopupMenu jPopupMenu1;
    public widget.Table tbDiagnosa;
    public widget.Table tbDiagnosaPasien;
    public widget.Table tbProsedur;
    public widget.Table tbTindakanPasien;
    // End of variables declaration//GEN-END:variables
    public void tampil() {
        Valid.tabelKosong(TabModeDiagnosaPasien);
        try{            
            psdiagnosapasien=koneksi.prepareStatement("select reg_periksa.tgl_registrasi,diagnosa_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, diagnosa_pasien.status,diagnosa_pasien.status_penyakit "+
                    "from diagnosa_pasien inner join reg_periksa inner join pasien inner join penyakit "+
                    "on diagnosa_pasien.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                    "where reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.tgl_registrasi like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and diagnosa_pasien.no_rawat like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and diagnosa_pasien.kd_penyakit like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and penyakit.nm_penyakit like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and diagnosa_pasien.status_penyakit like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and diagnosa_pasien.status like ? "+
                    "order by reg_periksa.tgl_registrasi,diagnosa_pasien.prioritas ");
            try {
                psdiagnosapasien.setString(1,tanggal1);
                psdiagnosapasien.setString(2,tanggal2);
                psdiagnosapasien.setString(3,"%"+norm+"%");          
                psdiagnosapasien.setString(4,"%"+keyword+"%");   
                psdiagnosapasien.setString(5,tanggal1);
                psdiagnosapasien.setString(6,tanggal2);
                psdiagnosapasien.setString(7,"%"+norm+"%");          
                psdiagnosapasien.setString(8,"%"+keyword+"%");  
                psdiagnosapasien.setString(9,tanggal1);
                psdiagnosapasien.setString(10,tanggal2);
                psdiagnosapasien.setString(11,"%"+norm+"%");          
                psdiagnosapasien.setString(12,"%"+keyword+"%");  
                psdiagnosapasien.setString(13,tanggal1);
                psdiagnosapasien.setString(14,tanggal2);
                psdiagnosapasien.setString(15,"%"+norm+"%");          
                psdiagnosapasien.setString(16,"%"+keyword+"%");  
                psdiagnosapasien.setString(17,tanggal1);
                psdiagnosapasien.setString(18,tanggal2);
                psdiagnosapasien.setString(19,"%"+norm+"%");          
                psdiagnosapasien.setString(20,"%"+keyword+"%");  
                psdiagnosapasien.setString(21,tanggal1);
                psdiagnosapasien.setString(22,tanggal2);
                psdiagnosapasien.setString(23,"%"+norm+"%");          
                psdiagnosapasien.setString(24,"%"+keyword+"%");  
                psdiagnosapasien.setString(25,tanggal1);
                psdiagnosapasien.setString(26,tanggal2);
                psdiagnosapasien.setString(27,"%"+norm+"%");          
                psdiagnosapasien.setString(28,"%"+keyword+"%"); 
                psdiagnosapasien.setString(29,tanggal1);
                psdiagnosapasien.setString(30,tanggal2);
                psdiagnosapasien.setString(31,"%"+norm+"%");          
                psdiagnosapasien.setString(32,"%"+keyword+"%"); 
                rs=psdiagnosapasien.executeQuery();
                while(rs.next()){
                    TabModeDiagnosaPasien.addRow(new Object[]{false,rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6),
                                   rs.getString(7),
                                   rs.getString(8)});
                }            
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(psdiagnosapasien!=null){
                    psdiagnosapasien.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public int getRecord(){
        if(TabRawat.getSelectedIndex()==0){
            i=0;
        }else if(TabRawat.getSelectedIndex()==1){
            i=tbDiagnosaPasien.getRowCount();
        }else if(TabRawat.getSelectedIndex()==2){
            i=tbTindakanPasien.getRowCount();
        }
        return i;
    }
    
    private void tampildiagnosa() {
        try{
            jml=0;
            for(i=0;i<tbDiagnosa.getRowCount();i++){
                if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode=null;
            kode=new String[jml];
            nama=null;
            nama=new String[jml];
            ciripny=null;
            ciripny=new String[jml];
            keterangan=null;
            keterangan=new String[jml];
            kategori=null;
            kategori=new String[jml];
            cirium=null;
            cirium=new String[jml];

            index=0; 
            for(i=0;i<tbDiagnosa.getRowCount();i++){
                if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode[index]=tbDiagnosa.getValueAt(i,1).toString();
                    nama[index]=tbDiagnosa.getValueAt(i,2).toString();
                    ciripny[index]=tbDiagnosa.getValueAt(i,3).toString();
                    keterangan[index]=tbDiagnosa.getValueAt(i,4).toString();
                    kategori[index]=tbDiagnosa.getValueAt(i,5).toString();
                    cirium[index]=tbDiagnosa.getValueAt(i,6).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeDiagnosa);
            for(i=0;i<jml;i++){
                tabModeDiagnosa.addRow(new Object[] {pilih[i],kode[i],nama[i],ciripny[i],keterangan[i],kategori[i],cirium[i]});
            }       

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
                    "order by penyakit.kd_penyakit  LIMIT 100");
            try {
                pspenyakit.setString(1,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(2,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(3,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(4,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(5,"%"+Diagnosa.getText().trim()+"%");
                pspenyakit.setString(6,"%"+Diagnosa.getText().trim()+"%");  
                rs=pspenyakit.executeQuery();
                while(rs.next()){
                    tabModeDiagnosa.addRow(new Object[]{false,rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6)});
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
    
    private void tampilprosedure() {
        try{
            jml=0;
            for(i=0;i<tbProsedur.getRowCount();i++){
                if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                    jml++;
                }
            }

            pilih=null;
            pilih=new boolean[jml];
            kode2=null;
            kode2=new String[jml];
            panjang=null;
            panjang=new String[jml];
            pendek=null;
            pendek=new String[jml];

            index=0; 
            for(i=0;i<tbProsedur.getRowCount();i++){
                if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                    pilih[index]=true;
                    kode2[index]=tbProsedur.getValueAt(i,1).toString();
                    panjang[index]=tbProsedur.getValueAt(i,2).toString();
                    pendek[index]=tbProsedur.getValueAt(i,3).toString();
                    index++;
                }
            }

            Valid.tabelKosong(tabModeProsedur);
            for(i=0;i<jml;i++){
                tabModeProsedur.addRow(new Object[] {pilih[i],kode2[i],panjang[i],pendek[i]});
            }
            
            psprosedur=koneksi.prepareStatement("select * from icd9 where kode like ? or "+
                    " deskripsi_panjang like ? or  deskripsi_pendek like ? order by kode");
            try{
                psprosedur.setString(1,"%"+Prosedur.getText().trim()+"%");
                psprosedur.setString(2,"%"+Prosedur.getText().trim()+"%");
                psprosedur.setString(3,"%"+Prosedur.getText().trim()+"%");
                rs=psprosedur.executeQuery();
                while(rs.next()){
                    tabModeProsedur.addRow(new Object[]{
                        false,rs.getString(1),rs.getString(2),rs.getString(3)});
                }
            }catch(Exception ex){
                System.out.println(ex);
            }finally{
                if(rs != null){
                    rs.close();
                }
                if(psprosedur != null){
                    psprosedur.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil2() {
        Valid.tabelKosong(TabModeTindakanPasien);
        try{            
            pstindakanpasien=koneksi.prepareStatement("select reg_periksa.tgl_registrasi,prosedur_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.status "+
                    "from prosedur_pasien inner join reg_periksa inner join pasien inner join icd9 "+
                    "on prosedur_pasien.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and prosedur_pasien.kode=icd9.kode "+
                    "where reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.tgl_registrasi like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and prosedur_pasien.no_rawat like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and reg_periksa.no_rkm_medis like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and pasien.nm_pasien like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and prosedur_pasien.kode like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and icd9.deskripsi_panjang like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.no_rkm_medis like ? and prosedur_pasien.status like ? "+
                    "order by reg_periksa.tgl_registrasi,prosedur_pasien.prioritas ");
            try {
                pstindakanpasien.setString(1,tanggal1);
                pstindakanpasien.setString(2,tanggal2);
                pstindakanpasien.setString(3,"%"+norm+"%");          
                pstindakanpasien.setString(4,"%"+keyword+"%");   
                pstindakanpasien.setString(5,tanggal1);
                pstindakanpasien.setString(6,tanggal2);
                pstindakanpasien.setString(7,"%"+norm+"%");          
                pstindakanpasien.setString(8,"%"+keyword+"%");  
                pstindakanpasien.setString(9,tanggal1);
                pstindakanpasien.setString(10,tanggal2);
                pstindakanpasien.setString(11,"%"+norm+"%");          
                pstindakanpasien.setString(12,"%"+keyword+"%");  
                pstindakanpasien.setString(13,tanggal1);
                pstindakanpasien.setString(14,tanggal2);
                pstindakanpasien.setString(15,"%"+norm+"%");          
                pstindakanpasien.setString(16,"%"+keyword+"%");  
                pstindakanpasien.setString(17,tanggal1);
                pstindakanpasien.setString(18,tanggal2);
                pstindakanpasien.setString(19,"%"+norm+"%");          
                pstindakanpasien.setString(20,"%"+keyword+"%");  
                pstindakanpasien.setString(21,tanggal1);
                pstindakanpasien.setString(22,tanggal2);
                pstindakanpasien.setString(23,"%"+norm+"%");          
                pstindakanpasien.setString(24,"%"+keyword+"%");  
                pstindakanpasien.setString(25,tanggal1);
                pstindakanpasien.setString(26,tanggal2);
                pstindakanpasien.setString(27,"%"+norm+"%");          
                pstindakanpasien.setString(28,"%"+keyword+"%");  
                rs=pstindakanpasien.executeQuery();
                while(rs.next()){
                    TabModeTindakanPasien.addRow(new Object[]{false,rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6),
                                   rs.getString(7)});
                }            
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(pstindakanpasien!=null){
                    pstindakanpasien.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void setRM(String norawat,String norm,String tanggal1,String tanggal2,String status,String keyword){
        this.norawat=norawat;
        this.norm=norm;
        this.tanggal1=tanggal1;
        this.tanggal2=tanggal2;
        this.status=status;
        this.keyword=keyword;
    }
    
    public void simpan(){
        try {
            koneksi.setAutoCommit(false);
            for(i=0;i<tbDiagnosa.getRowCount();i++){ 
                if(tbDiagnosa.getValueAt(i,0).toString().equals("true")){
                    if(Sequel.cariInteger(
                            "select count(diagnosa_pasien.kd_penyakit) from diagnosa_pasien "+
                            "inner join reg_periksa inner join pasien on "+
                            "diagnosa_pasien.no_rawat=reg_periksa.no_rawat and "+
                            "reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                            "pasien.no_rkm_medis='"+norm+"' and diagnosa_pasien.kd_penyakit='"+tbDiagnosa.getValueAt(i,1).toString()+"'")>0){
                        Sequel.menyimpan("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{
                            norawat,tbDiagnosa.getValueAt(i,1).toString(),status,
                            Sequel.cariIsi("select ifnull(MAX(prioritas)+1,1) from diagnosa_pasien where no_rawat=? and status='"+status+"'",norawat),"Lama"
                        });
                    }else{
                        Sequel.menyimpan("diagnosa_pasien","?,?,?,?,?","Penyakit",5,new String[]{
                            norawat,tbDiagnosa.getValueAt(i,1).toString(),status,
                            Sequel.cariIsi("select ifnull(MAX(prioritas)+1,1) from diagnosa_pasien where no_rawat=? and status='"+status+"'",norawat),"Baru"
                        });
                    }                            
                }                    
            }
            koneksi.setAutoCommit(true);  
            for(i=0;i<tbDiagnosa.getRowCount();i++){ 
               tbDiagnosa.setValueAt(false,i,0);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Kemungkinan ada data diagnosa yang sama dimasukkan sebelumnya...!");
        }

        try {
            koneksi.setAutoCommit(false);
            for(i=0;i<tbProsedur.getRowCount();i++){ 
                if(tbProsedur.getValueAt(i,0).toString().equals("true")){
                    Sequel.menyimpan("prosedur_pasien","?,?,?,?","ICD 9",4,new String[]{
                        norawat,tbProsedur.getValueAt(i,1).toString(),status,Sequel.cariIsi("select ifnull(MAX(prioritas)+1,1) from prosedur_pasien where no_rawat=? and status='"+status+"'",norawat)
                    });
                }                    
            }
            koneksi.setAutoCommit(true);  
            for(i=0;i<tbProsedur.getRowCount();i++){ 
               tbProsedur.setValueAt(false,i,0);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"Maaf, gagal menyimpan data. Kemungkinan ada data prosedur/ICD9 yang sama dimasukkan sebelumnya...!");
        }
        pilihTab();
    }

    public void pilihTab() {
        if(TabRawat.getSelectedIndex()==0){
            tampildiagnosa();
            tampilprosedure();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil();
        }else if(TabRawat.getSelectedIndex()==2){
            tampil2();
        }
    }
    
    public void batal(){
        Diagnosa.setText("");
        for(i=0;i<tbDiagnosa.getRowCount();i++){ 
            tbDiagnosa.setValueAt(false,i,0);
        }
        for(i=0;i<tbProsedur.getRowCount();i++){ 
            tbProsedur.setValueAt(false,i,0);
        }
        Prosedur.setText("");
    }
    
    public void hapus(){
        if(TabRawat.getSelectedIndex()==1){
            if(TabModeDiagnosaPasien.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            }else{
                for(i=0;i<tbDiagnosaPasien.getRowCount();i++){ 
                    if(tbDiagnosaPasien.getValueAt(i,0).toString().equals("true")){
                        Sequel.queryu2("delete from diagnosa_pasien where no_rawat=? and kd_penyakit=?",2,new String[]{
                            tbDiagnosaPasien.getValueAt(i,2).toString(),tbDiagnosaPasien.getValueAt(i,5).toString()
                        });
                    }
                }
            }                     
        }else if(TabRawat.getSelectedIndex()==2){
            if(TabModeTindakanPasien.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            }else{
                for(i=0;i<tbTindakanPasien.getRowCount();i++){ 
                    if(tbTindakanPasien.getValueAt(i,0).toString().equals("true")){
                        Sequel.queryu2("delete from prosedur_pasien where no_rawat=? and kode=?",2,new String[]{
                            tbTindakanPasien.getValueAt(i,2).toString(),tbTindakanPasien.getValueAt(i,5).toString()
                        });
                    }
                }
            }
        }
        pilihTab();
    }
    
    public void cetak(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(TabRawat.getSelectedIndex()==1){
            if(TabModeDiagnosaPasien.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            }else if(TabModeDiagnosaPasien.getRowCount()!=0){
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReportqry("rptDiagnosa.jasper","report","::[ Data Diagnosa Pasien ]::",
                        "select reg_periksa.tgl_registrasi,diagnosa_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                        "diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, diagnosa_pasien.status,diagnosa_pasien.status_penyakit "+
                        "from diagnosa_pasien inner join reg_periksa inner join pasien inner join penyakit "+
                        "on diagnosa_pasien.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "and diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                        "where reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and reg_periksa.tgl_registrasi like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and diagnosa_pasien.no_rawat like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and reg_periksa.no_rkm_medis like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and pasien.nm_pasien like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and diagnosa_pasien.kd_penyakit like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and penyakit.nm_penyakit like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and diagnosa_pasien.status_penyakit like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and diagnosa_pasien.status like '%"+keyword+"%' "+
                        "order by reg_periksa.tgl_registrasi,diagnosa_pasien.prioritas ",param);
            }
        }else if(TabRawat.getSelectedIndex()==2){
            if(TabModeTindakanPasien.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            }else if(TabModeTindakanPasien.getRowCount()!=0){
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReportqry("rptProsedur.jasper","report","::[ Data Prosedur Tindakan Pasien ]::",
                        "select reg_periksa.tgl_registrasi,prosedur_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                        "prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.status "+
                        "from prosedur_pasien inner join reg_periksa inner join pasien inner join icd9 "+
                        "on prosedur_pasien.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "and prosedur_pasien.kode=icd9.kode "+
                        "where reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and reg_periksa.tgl_registrasi like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and prosedur_pasien.no_rawat like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and reg_periksa.no_rkm_medis like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and pasien.nm_pasien like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and prosedur_pasien.kode like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and icd9.deskripsi_panjang like '%"+keyword+"%' or "+
                        "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' and reg_periksa.no_rkm_medis like '%"+norm+"%' and prosedur_pasien.status like '%"+keyword+"%' "+
                        "order by reg_periksa.tgl_registrasi,prosedur_pasien.prioritas ",param);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
}

package viabarcode;
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
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPetugas;

public class LabKeslingRekapPelayanan extends javax.swing.JDialog {
    private final DefaultTableModel tabModeRekapPelayanan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public LabKeslingRekapPelayanan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabModeRekapPelayanan=new DefaultTableModel(null,new Object[]{
                "Waktu Diterima","No.Permintaan","No.Pelanggan","Nama Pelanggan","Alamat Pelanggan","Kegiatan Usaha","Personal Dihubungi","Kontak Pelanggan",
                "NIP","Sampel Diterima Oleh","Waktu Sampling","Lokasi Sampling","Deskripsi Sampel","Jenis Sampel","Jml.Sampel","Sampling Dilakukan Oleh",
                "Volume Sampel","Wadah Sampel","Kondisi Wadah Sampel","Kode Sampel","Nama Sampel","Baku Mutu","Tanggal Penugasan","Nomor Penugasan",
                "Catatan Penugasan","Mulai Pengujian","Selesai Pengujian","Tanggal Verifikasi","No.Verifikasi","Catatan Verifikasi","NIP PJ Verif",
                "Nama PJ Verifikasi","Tanggal Validasi","No.Validasi","Catatan Validasi","NIP PJ Validasi"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRekapPelayanan.setModel(tabModeRekapPelayanan);

        tbRekapPelayanan.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRekapPelayanan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 36; i++) {
            TableColumn column = tbRekapPelayanan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(118);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(76);
            }else if(i==3){
                column.setPreferredWidth(150);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(100);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(118);
            }else if(i==11){
                column.setPreferredWidth(160);
            }else if(i==12){
                column.setPreferredWidth(160);
            }else if(i==13){
                column.setPreferredWidth(120);
            }else if(i==14){
                column.setPreferredWidth(64);
            }else if(i==15){
                column.setPreferredWidth(129);
            }else if(i==16){
                column.setPreferredWidth(84);
            }else if(i==17){
                column.setPreferredWidth(110);
            }else if(i==18){
                column.setPreferredWidth(130);
            }else if(i==19){
                column.setPreferredWidth(70);
            }else if(i==20){
                column.setPreferredWidth(130);
            }else if(i==21){
                column.setPreferredWidth(200);
            }else if(i==22){
                column.setPreferredWidth(115);
            }else if(i==23){
                column.setPreferredWidth(120);
            }else{
                column.setPreferredWidth(150);
            }
        }
        tbRekapPelayanan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));          
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

        KodeSampel = new widget.TextBox();
        KodePelanggan = new widget.TextBox();
        KodeParameter = new widget.TextBox();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        WaktuPencarian = new widget.ComboBox();
        Tanggal1 = new widget.Tanggal();
        label12 = new widget.Label();
        Tanggal2 = new widget.Tanggal();
        jLabel19 = new widget.Label();
        KategoriParameter = new widget.ComboBox();
        panelisi3 = new widget.panelisi();
        label17 = new widget.Label();
        NamaPelanggan = new widget.TextBox();
        btnPelanggan = new widget.Button();
        label7 = new widget.Label();
        NamaSampel = new widget.TextBox();
        btnSampel = new widget.Button();
        label13 = new widget.Label();
        NamaParameter = new widget.TextBox();
        btnPetugas = new widget.Button();
        scrollPane3 = new widget.ScrollPane();
        tbRekapPelayanan = new widget.Table();

        KodeSampel.setName("KodeSampel"); // NOI18N
        KodeSampel.setPreferredSize(new java.awt.Dimension(207, 23));

        KodePelanggan.setName("KodePelanggan"); // NOI18N
        KodePelanggan.setPreferredSize(new java.awt.Dimension(80, 23));

        KodeParameter.setName("KodeParameter"); // NOI18N
        KodeParameter.setPreferredSize(new java.awt.Dimension(80, 23));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Pelayanan Laboratorium Kesehatan Lingkungan ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(205, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
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
        panelisi1.add(BtnCari);

        label9.setText("Record :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi1.add(LTotal);

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
        panelisi1.add(BtnAll);

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
        panelisi1.add(BtnPrint);

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
        panelisi1.add(BtnKeluar);

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal Pencarian :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(104, 23));
        panelisi4.add(label11);

        WaktuPencarian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Waktu Sampel Diambil", "Waktu Sampel Diterima", "Waktu Penugasan Pengujian", "Waktu Mulai Pengujian", "Waktu Selesai Pengujian", "Waktu Verifikasi Hasil Pengujian", "Waktu Validasi Hasil Pengujian" }));
        WaktuPencarian.setName("WaktuPencarian"); // NOI18N
        WaktuPencarian.setPreferredSize(new java.awt.Dimension(215, 23));
        panelisi4.add(WaktuPencarian);

        Tanggal1.setDisplayFormat("dd-MM-yyyy");
        Tanggal1.setName("Tanggal1"); // NOI18N
        Tanggal1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal1KeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal1);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(26, 23));
        panelisi4.add(label12);

        Tanggal2.setDisplayFormat("dd-MM-yyyy");
        Tanggal2.setName("Tanggal2"); // NOI18N
        Tanggal2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal2KeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal2);

        jLabel19.setText("Kategori Parameter :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(130, 23));
        panelisi4.add(jLabel19);

        KategoriParameter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Fisika", "Kimia", "Mikrobiologi" }));
        KategoriParameter.setName("KategoriParameter"); // NOI18N
        KategoriParameter.setPreferredSize(new java.awt.Dimension(117, 23));
        panelisi4.add(KategoriParameter);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label17.setText("Pelanggan :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(label17);

        NamaPelanggan.setEditable(false);
        NamaPelanggan.setName("NamaPelanggan"); // NOI18N
        NamaPelanggan.setPreferredSize(new java.awt.Dimension(190, 23));
        panelisi3.add(NamaPelanggan);

        btnPelanggan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPelanggan.setMnemonic('4');
        btnPelanggan.setToolTipText("Alt+4");
        btnPelanggan.setName("btnPelanggan"); // NOI18N
        btnPelanggan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPelanggan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPelangganActionPerformed(evt);
            }
        });
        panelisi3.add(btnPelanggan);

        label7.setText("Sampel :");
        label7.setName("label7"); // NOI18N
        label7.setPreferredSize(new java.awt.Dimension(54, 23));
        panelisi3.add(label7);

        NamaSampel.setEditable(false);
        NamaSampel.setName("NamaSampel"); // NOI18N
        NamaSampel.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi3.add(NamaSampel);

        btnSampel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnSampel.setMnemonic('1');
        btnSampel.setToolTipText("Alt+1");
        btnSampel.setName("btnSampel"); // NOI18N
        btnSampel.setPreferredSize(new java.awt.Dimension(28, 23));
        btnSampel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSampelActionPerformed(evt);
            }
        });
        panelisi3.add(btnSampel);

        label13.setText("Parameter :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(69, 23));
        panelisi3.add(label13);

        NamaParameter.setEditable(false);
        NamaParameter.setName("NamaParameter"); // NOI18N
        NamaParameter.setPreferredSize(new java.awt.Dimension(150, 23));
        panelisi3.add(NamaParameter);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        scrollPane3.setName("scrollPane3"); // NOI18N
        scrollPane3.setOpaque(true);

        tbRekapPelayanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbRekapPelayanan.setToolTipText("Silahkan klik untuk memilih data yang mau dihapus");
        tbRekapPelayanan.setName("tbRekapPelayanan"); // NOI18N
        scrollPane3.setViewportView(tbRekapPelayanan);

        internalFrame1.add(scrollPane3, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,KodePelanggan);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        DlgCariPetugas petugas=new DlgCariPetugas(null,false);
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){                   
                    KodeParameter.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaParameter.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
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
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void Tanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal1KeyPressed
        //Valid.pindah(evt,NoPermintaan,TCari);
    }//GEN-LAST:event_Tanggal1KeyPressed

    private void btnPelangganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPelangganActionPerformed
        LabKeslingPelanggan pelanggan=new LabKeslingPelanggan(null,false);
        pelanggan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pelanggan.getTable().getSelectedRow()!= -1){
                    KodePelanggan.setText(pelanggan.getTable().getValueAt(pelanggan.getTable().getSelectedRow(),0).toString());
                    NamaPelanggan.setText(pelanggan.getTable().getValueAt(pelanggan.getTable().getSelectedRow(),1).toString());
                }  
                btnPelanggan.requestFocus();
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
        
        pelanggan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pelanggan.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        pelanggan.isCek();
        pelanggan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pelanggan.setLocationRelativeTo(internalFrame1);
        pelanggan.setVisible(true);
    }//GEN-LAST:event_btnPelangganActionPerformed

    private void Tanggal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal2KeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tampil();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            Tanggal1.requestFocus();
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
        KodeSampel.setText("");
        NamaSampel.setText("");
        KodePelanggan.setText("");
        NamaPelanggan.setText("");
        KodeParameter.setText("");
        NamaParameter.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabModeRekapPelayanan.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabModeRekapPelayanan.getRowCount()!=0){
            try{
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

                File f;            
                BufferedWriter bw;
                StringBuilder htmlContent;

                String pilihan =(String) JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Diterima</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Unsur Kaji Ulang/Abnornalitas Sampel/Keterangan Lainnya</b></td>").
                                        append("</tr>");
                            for (int i = 0; i < tabModeRekapPelayanan.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,6).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataPermintaanPengujianSampelTidakDilayani.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PERMINTAAN PENGUJIAN SAMPEL TIDAK DAPAT DILAYANI<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                            htmlContent.append("<tr class='isi'>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Waktu Diterima</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Permintaan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pelanggan</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Sampel</b></td>").
                                            append("<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Unsur Kaji Ulang/Abnornalitas Sampel/Keterangan Lainnya</b></td>").
                                        append("</tr>");
                            for (int i = 0; i < tabModeRekapPelayanan.getRowCount(); i++) {
                                htmlContent.append("<tr class='isi'>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,0).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,1).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,2).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,3).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,4).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,5).toString()).append("</td>").
                                                append("<td valign='top'>").append(tbRekapPelayanan.getValueAt(i,6).toString()).append("</td>").
                                            append("</tr>");
                            }
                            LoadHTML.setText(
                                "<html>"+
                                  "<table width='100%' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                                   htmlContent.toString()+
                                  "</table>"+
                                "</html>"
                            );

                            f = new File("DataPermintaanPengujianSampelTidakDilayani.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                                        "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                                        "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DATA PERMINTAAN PENGUJIAN SAMPEL TIDAK DAPAT DILAYANI<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>")
                            );
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"Waktu Diterima\";\"No.Permintaan\";\"No.Pelanggan\";\"Nama Pelanggan\";\"Kode Sampel\";\"Nama Sampel\";\"Unsur Kaji Ulang/Abnornalitas Sampel/Keterangan Lainnya\"\n"
                            ); 
                            for (int i = 0; i < tabModeRekapPelayanan.getRowCount(); i++) {
                                htmlContent.append("\"").append(tbRekapPelayanan.getValueAt(i,0).toString()).append("\";\"").append(tbRekapPelayanan.getValueAt(i,1).toString()).append("\";\"").append(tbRekapPelayanan.getValueAt(i,2).toString()).append("\";\"").append(tbRekapPelayanan.getValueAt(i,3).toString()).append("\";\"").append(tbRekapPelayanan.getValueAt(i,4).toString()).append("\";\"").append(tbRekapPelayanan.getValueAt(i,5).toString()).append("\";\"").append(tbRekapPelayanan.getValueAt(i,6).toString()).append("\"\n");
                            }
                            f = new File("DataPermintaanPengujianSampelTidakDilayani.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());
                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
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
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void btnSampelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSampelActionPerformed
        LabKeslingCariMasterSampelBakuMutu sampel=new LabKeslingCariMasterSampelBakuMutu(null,false);
        sampel.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(sampel.getTable().getSelectedRow()!= -1){
                    KodeSampel.setText(sampel.getTable().getValueAt(sampel.getTable().getSelectedRow(),0).toString());
                    NamaSampel.setText(sampel.getTable().getValueAt(sampel.getTable().getSelectedRow(),1).toString());
                }  
                btnSampel.requestFocus();
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
        
        sampel.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    sampel.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        sampel.isCek();
        sampel.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        sampel.setLocationRelativeTo(internalFrame1);
        sampel.setVisible(true);
    }//GEN-LAST:event_btnSampelActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            LabKeslingRekapPelayanan dialog = new LabKeslingRekapPelayanan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.ComboBox KategoriParameter;
    private widget.TextBox KodeParameter;
    private widget.TextBox KodePelanggan;
    private widget.TextBox KodeSampel;
    private widget.Label LTotal;
    private widget.editorpane LoadHTML;
    private widget.TextBox NamaParameter;
    private widget.TextBox NamaPelanggan;
    private widget.TextBox NamaSampel;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal1;
    private widget.Tanggal Tanggal2;
    private widget.ComboBox WaktuPencarian;
    private widget.Button btnPelanggan;
    private widget.Button btnPetugas;
    private widget.Button btnSampel;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel19;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label17;
    private widget.Label label7;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbRekapPelayanan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabModeRekapPelayanan);
        try{  
            ps=koneksi.prepareStatement(
                "select labkesling_permintaan_pengujian_sampel.no_permintaan,labkesling_permintaan_pengujian_sampel.kode_pelanggan,labkesling_pelanggan.nama_pelanggan,labkesling_pelanggan.alamat,labkesling_pelanggan.no_telp,"+
                "labkesling_pelanggan.kegiatan_usaha,labkesling_pelanggan.personal_dihubungi,labkesling_permintaan_pengujian_sampel.nip,petugas.nama,labkesling_permintaan_pengujian_sampel.waktu_sampling,"+
                "labkesling_permintaan_pengujian_sampel.waktu_diterima,labkesling_permintaan_pengujian_sampel.lokasi_sampling,labkesling_permintaan_pengujian_sampel.deskripsi_sampel,labkesling_permintaan_pengujian_sampel.jenis_sampel,"+
                "labkesling_permintaan_pengujian_sampel.jumlah_sampel,labkesling_permintaan_pengujian_sampel.sampling_dilakukan_oleh,labkesling_permintaan_pengujian_sampel.volume_sampel,labkesling_permintaan_pengujian_sampel.wadah_sampel,"+
                "labkesling_permintaan_pengujian_sampel.kondisi_wadah_sampel,labkesling_permintaan_pengujian_sampel.kode_sampel,labkesling_master_sampel.nama_sampel,labkesling_master_sampel.baku_mutu,"+
                "labkesling_penugasan_pengujian_sampel.tanggal as tanggalpenugasan,labkesling_penugasan_pengujian_sampel.no_penugasan,labkesling_penugasan_pengujian_sampel.catatan as catatanpenugasan,"+
                "labkesling_verifikasi_pengujian_sampel.tanggal as tanggalverifikasi,labkesling_verifikasi_pengujian_sampel.no_verifikasi,labkesling_verifikasi_pengujian_sampel.catatan as catatanverifikasi,"+
                "labkesling_verifikasi_pengujian_sampel.mulai_pengujian,labkesling_verifikasi_pengujian_sampel.selesai_pengujian,labkesling_validasi_pengujian_sampel.no_validasi,labkesling_validasi_pengujian_sampel.nip_pj as nippjvalidasi,"+
                "labkesling_validasi_pengujian_sampel.nip_verifikator,pjverifikator.nama as pjverifikator,labkesling_validasi_pengujian_sampel.tanggal as tanggalvalidasi,labkesling_validasi_pengujian_sampel.catatan as catatanvalidasi "+
                "from labkesling_permintaan_pengujian_sampel inner join labkesling_pelanggan on labkesling_permintaan_pengujian_sampel.kode_pelanggan=labkesling_pelanggan.kode_pelanggan "+
                "inner join petugas on labkesling_permintaan_pengujian_sampel.nip=petugas.nip inner join labkesling_master_sampel on labkesling_permintaan_pengujian_sampel.kode_sampel=labkesling_master_sampel.kode_sampel "+
                "inner join labkesling_penugasan_pengujian_sampel on labkesling_permintaan_pengujian_sampel.no_permintaan=labkesling_penugasan_pengujian_sampel.no_permintaan "+
                "inner join labkesling_verifikasi_pengujian_sampel on labkesling_verifikasi_pengujian_sampel.no_permintaan=labkesling_permintaan_pengujian_sampel.no_permintaan "+
                "inner join labkesling_validasi_pengujian_sampel on labkesling_validasi_pengujian_sampel.no_permintaan=labkesling_permintaan_pengujian_sampel.no_permintaan "+
                "inner join petugas as pjverifikator on labkesling_validasi_pengujian_sampel.nip_verifikator=pjverifikator.nip"
            );
                
            try {
                /*ps.setString(1,Valid.SetTgl(Tanggal1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(Tanggal2.getSelectedItem()+"")+" 23:59:59");
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                }*/
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabModeRekapPelayanan.addRow(new Object[]{
                        rs.getString("waktu_diterima"),rs.getString("no_permintaan"),rs.getString("kode_pelanggan"),rs.getString("nama_pelanggan"),rs.getString("alamat"),rs.getString("kegiatan_usaha"),
                        rs.getString("personal_dihubungi"),rs.getString("no_telp"),rs.getString("nip"),rs.getString("nama"),rs.getString("waktu_sampling"),rs.getString("lokasi_sampling"),rs.getString("deskripsi_sampel"),
                        rs.getString("jenis_sampel"),rs.getString("jumlah_sampel"),rs.getString("sampling_dilakukan_oleh"),rs.getString("volume_sampel"),rs.getString("wadah_sampel"),rs.getString("kondisi_wadah_sampel"),
                        rs.getString("kode_sampel"),rs.getString("nama_sampel"),rs.getString("baku_mutu"),rs.getString("tanggalpenugasan"),rs.getString("no_penugasan"),rs.getString("catatanpenugasan"),
                        rs.getString("mulai_pengujian"),rs.getString("selesai_pengujian"),rs.getString("tanggalverifikasi"),rs.getString("no_verifikasi"),rs.getString("catatanverifikasi"),rs.getString("nip_verifikator"),
                        rs.getString("pjverifikator"),rs.getString("tanggalvalidasi"),
                        rs.getString("no_validasi"),rs.getString("catatanvalidasi"),rs.getString("nippjvalidasi")
                    }); 
                }        
                LTotal.setText(tabModeRekapPelayanan.getRowCount()+"");
            } catch (Exception e) {
                System.out.println("Note : "+e);
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
    
    
    public void isCek(){
        TCari.requestFocus();
        BtnPrint.setEnabled(akses.getpermintaan_pengujian_sampel_lab_kesehatan_lingkungan());
    }
}

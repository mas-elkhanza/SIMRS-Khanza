package kepegawaian;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class SKPCariPenilaianPegawai extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private DlgCariSKPKategoriPenilaian kategori=new DlgCariSKPKategoriPenilaian(null,false);
    private PreparedStatement ps,ps2;
    private ResultSet rs,rs2;
    private int i;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public SKPCariPenilaianPegawai(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Tanggal","No.Permintaan","Asal Permintaan","Pegawai","Ditujukan Ke"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 5; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(400);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){    
                    if(i==1){
                        KdPenilai.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                        NmPenilai.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                    }else if(i==2){
                        KdDInilai.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                        NmDinilai.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
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
        
        pegawai.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pegawai.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(kategori.getTable().getSelectedRow()!= -1){
                    KdKategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(),0).toString());
                    NmKategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(),1).toString());
                    btnKategori.requestFocus();
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
        
        kategori.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    kategori.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
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

        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
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
        jLabel9 = new widget.Label();
        KdKategori = new widget.TextBox();
        btnKategori = new widget.Button();
        NmKategori = new widget.TextBox();
        jLabel10 = new widget.Label();
        Sasaran = new widget.ComboBox();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label11 = new widget.Label();
        Tanggal1 = new widget.Tanggal();
        label12 = new widget.Label();
        Tanggal2 = new widget.Tanggal();
        label13 = new widget.Label();
        KdPenilai = new widget.TextBox();
        NmPenilai = new widget.TextBox();
        btnPenilai = new widget.Button();
        label17 = new widget.Label();
        KdDInilai = new widget.TextBox();
        NmDinilai = new widget.TextBox();
        btnDinilai = new widget.Button();
        NoPenilaian = new widget.TextBox();
        label15 = new widget.Label();
        jLabel11 = new widget.Label();
        Sasaran1 = new widget.ComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Data Penilaian Petugas/Dokter Dalam Implementasi Sasaran Keselamatan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setToolTipText("Silahkan klik pada nomor permintaan untuk verifikasi pilihan");
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(235, 23));
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
        label9.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(50, 23));
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
        panelisi4.setLayout(null);

        jLabel9.setText("Kategori :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelisi4.add(jLabel9);
        jLabel9.setBounds(0, 10, 62, 23);

        KdKategori.setEditable(false);
        KdKategori.setHighlighter(null);
        KdKategori.setName("KdKategori"); // NOI18N
        panelisi4.add(KdKategori);
        KdKategori.setBounds(66, 10, 70, 23);

        btnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKategori.setMnemonic('1');
        btnKategori.setToolTipText("Alt+1");
        btnKategori.setName("btnKategori"); // NOI18N
        btnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKategoriActionPerformed(evt);
            }
        });
        btnKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKategoriKeyPressed(evt);
            }
        });
        panelisi4.add(btnKategori);
        btnKategori.setBounds(340, 10, 28, 23);

        NmKategori.setEditable(false);
        NmKategori.setName("NmKategori"); // NOI18N
        panelisi4.add(NmKategori);
        NmKategori.setBounds(138, 10, 200, 23);

        jLabel10.setText("Sasaran :");
        jLabel10.setName("jLabel10"); // NOI18N
        panelisi4.add(jLabel10);
        jLabel10.setBounds(370, 10, 60, 23);

        Sasaran.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "1. Mengidentifikasi Pasien Dengan Benar", "2. Meningkatkan Komunikasi Yang Efektif", "3. Meningkatkan Keamanan Obat-obatan Yang Harus Diwaspadai", "4. Memastikan Lokasi Pembedahan Yang Benar, Prosedur Yang Benar, Pembedahan Pada Pasien Yang Benar", "5. Mengurangi Risiko Infeksi Akibat Perawatan Kesehatan", "6. Mengurangi Risiko Cidera Pasien Akibat Terjatuh" }));
        Sasaran.setName("Sasaran"); // NOI18N
        Sasaran.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                SasaranItemStateChanged(evt);
            }
        });
        Sasaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SasaranKeyPressed(evt);
            }
        });
        panelisi4.add(Sasaran);
        Sasaran.setBounds(434, 10, 282, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 95));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput.setLayout(null);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(0, 40, 82, 23);

        Tanggal1.setDisplayFormat("dd-MM-yyyy");
        Tanggal1.setName("Tanggal1"); // NOI18N
        Tanggal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal1KeyPressed(evt);
            }
        });
        FormInput.add(Tanggal1);
        Tanggal1.setBounds(86, 40, 90, 23);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(180, 40, 27, 23);

        Tanggal2.setDisplayFormat("dd-MM-yyyy");
        Tanggal2.setName("Tanggal2"); // NOI18N
        Tanggal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal2KeyPressed(evt);
            }
        });
        FormInput.add(Tanggal2);
        Tanggal2.setBounds(211, 40, 90, 23);

        label13.setText("Yang Menilai :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label13);
        label13.setBounds(360, 10, 90, 23);

        KdPenilai.setEditable(false);
        KdPenilai.setName("KdPenilai"); // NOI18N
        KdPenilai.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPenilai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPenilaiKeyPressed(evt);
            }
        });
        FormInput.add(KdPenilai);
        KdPenilai.setBounds(457, 10, 100, 23);

        NmPenilai.setEditable(false);
        NmPenilai.setName("NmPenilai"); // NOI18N
        NmPenilai.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPenilai);
        NmPenilai.setBounds(558, 10, 168, 23);

        btnPenilai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenilai.setMnemonic('2');
        btnPenilai.setToolTipText("Alt+2");
        btnPenilai.setName("btnPenilai"); // NOI18N
        btnPenilai.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPenilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenilaiActionPerformed(evt);
            }
        });
        FormInput.add(btnPenilai);
        btnPenilai.setBounds(728, 10, 28, 23);

        label17.setText("Yang Dinilai :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(360, 40, 90, 23);

        KdDInilai.setEditable(false);
        KdDInilai.setName("KdDInilai"); // NOI18N
        KdDInilai.setPreferredSize(new java.awt.Dimension(80, 23));
        KdDInilai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdDInilaiKeyPressed(evt);
            }
        });
        FormInput.add(KdDInilai);
        KdDInilai.setBounds(457, 40, 100, 23);

        NmDinilai.setEditable(false);
        NmDinilai.setName("NmDinilai"); // NOI18N
        NmDinilai.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmDinilai);
        NmDinilai.setBounds(558, 40, 168, 23);

        btnDinilai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDinilai.setMnemonic('2');
        btnDinilai.setToolTipText("Alt+2");
        btnDinilai.setName("btnDinilai"); // NOI18N
        btnDinilai.setPreferredSize(new java.awt.Dimension(28, 23));
        btnDinilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDinilaiActionPerformed(evt);
            }
        });
        FormInput.add(btnDinilai);
        btnDinilai.setBounds(728, 40, 28, 23);

        NoPenilaian.setName("NoPenilaian"); // NOI18N
        NoPenilaian.setPreferredSize(new java.awt.Dimension(207, 23));
        NoPenilaian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoPenilaianKeyPressed(evt);
            }
        });
        FormInput.add(NoPenilaian);
        NoPenilaian.setBounds(86, 10, 90, 23);

        label15.setText("Nomor :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(label15);
        label15.setBounds(0, 10, 82, 23);

        jLabel11.setText("Status :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(180, 10, 60, 23);

        Sasaran1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Proses Penilaian", "Keluar Hasil" }));
        Sasaran1.setName("Sasaran1"); // NOI18N
        Sasaran1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Sasaran1ItemStateChanged(evt);
            }
        });
        Sasaran1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sasaran1KeyPressed(evt);
            }
        });
        FormInput.add(Sasaran1);
        Sasaran1.setBounds(240, 10, 100, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        pegawai.dispose();
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,kdbar);}*/
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
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
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'"+i+"','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Pembelian"); 
            }
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptPermintaan.jasper","report","::[ Data Permintaan Obat/Alkes/BHP Medis ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void Tanggal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal2KeyPressed

    private void Tanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal1KeyPressed
        //Valid.pindah(evt,NoPermintaan,KdBangsal);
    }//GEN-LAST:event_Tanggal1KeyPressed

    private void KdPenilaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPenilaiKeyPressed
        /*if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            NmPenilai.setText(pegawai.tampil3(KdPenilai.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kdgudangTujuan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }*/
    }//GEN-LAST:event_KdPenilaiKeyPressed

    private void btnPenilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenilaiActionPerformed
        //i=1;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnPenilaiActionPerformed

    private void KdDInilaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdDInilaiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdDInilaiKeyPressed

    private void btnDinilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDinilaiActionPerformed
        //i=2;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnDinilaiActionPerformed

    private void NoPenilaianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoPenilaianKeyPressed
        //Valid.pindah(evt, BtnSimpan, kdgudangTujuan);
    }//GEN-LAST:event_NoPenilaianKeyPressed

    private void btnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKategoriActionPerformed
        /*kategori.isCek();
        kategori.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setVisible(true);*/
    }//GEN-LAST:event_btnKategoriActionPerformed

    private void btnKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKategoriKeyPressed
        //Valid.pindah(evt,KdKategori,BtnSimpan);
    }//GEN-LAST:event_btnKategoriKeyPressed

    private void SasaranItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_SasaranItemStateChanged
        //tampil2();
    }//GEN-LAST:event_SasaranItemStateChanged

    private void SasaranKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SasaranKeyPressed
        //Valid.pindah(evt,Kode,Kategori);
    }//GEN-LAST:event_SasaranKeyPressed

    private void Sasaran1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Sasaran1ItemStateChanged
        //tampil2();
    }//GEN-LAST:event_Sasaran1ItemStateChanged

    private void Sasaran1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sasaran1KeyPressed
        //Valid.pindah(evt,Kode,Kategori);
    }//GEN-LAST:event_Sasaran1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SKPCariPenilaianPegawai dialog = new SKPCariPenilaianPegawai(new javax.swing.JFrame(), true);
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
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox KdDInilai;
    private widget.TextBox KdKategori;
    private widget.TextBox KdPenilai;
    private widget.Label LTotal;
    private widget.TextBox NmDinilai;
    private widget.TextBox NmKategori;
    private widget.TextBox NmPenilai;
    private widget.TextBox NoPenilaian;
    private javax.swing.JPanel PanelInput;
    private widget.ComboBox Sasaran;
    private widget.ComboBox Sasaran1;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal1;
    private widget.Tanggal Tanggal2;
    private widget.Button btnDinilai;
    private widget.Button btnKategori;
    private widget.Button btnPenilai;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel1;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label17;
    private widget.Label label9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
             
    }

    public void emptTeks() {
        /*kdbar.setText("");
        nmbar.setText("");
        kdbar.requestFocus();  */      
    }
    
    public void isCek(){
        TCari.requestFocus();
        
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,126));
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

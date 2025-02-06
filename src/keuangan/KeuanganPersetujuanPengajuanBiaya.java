

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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;

/**
 *
 * @author perpustakaan
 */
public final class KeuanganPersetujuanPengajuanBiaya extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private int i;
    private double belumdisetujui=0;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public KeuanganPersetujuanPengajuanBiaya(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
        
        DlgPersetujuan.setSize(532,106);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Pengajuan","Tanggal","NIP","Diajukan Oleh","Bidang","Departemen","Urgensi","Uraian","Tujuan",
                "Target Sasaran","Lokasi","Jml","Harga", "Total", "Keterangan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                  java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                  java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                  java.lang.String.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBangsal.setModel(tabMode);

        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 15; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(85);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(85);
            }else if(i==3){
                column.setPreferredWidth(160);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(180);
            }else if(i==8){
                column.setPreferredWidth(180);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(35);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(140);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
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
                    kdpegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    nmpegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                    tampil();
                }      
                kdpegawai.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {pegawai.emptTeks();}
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
        
        Harga.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isHitung();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isHitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isHitung();
            }
        });
        
        Jumlah.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                isHitung();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isHitung();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isHitung();
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        DlgPersetujuan = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        NoPengajuan = new widget.TextBox();
        jLabel12 = new widget.Label();
        Jumlah = new widget.TextBox();
        Harga = new widget.TextBox();
        jLabel13 = new widget.Label();
        jLabel14 = new widget.Label();
        Total = new widget.TextBox();
        BtnSimpanRekon = new widget.Button();
        BtnKeluarRekon = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label19 = new widget.Label();
        kdpegawai = new widget.TextBox();
        nmpegawai = new widget.TextBox();
        BtnPegawai = new widget.Button();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        panelisi1 = new widget.panelisi();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        BtnSetujui = new widget.Button();
        BtnTolak = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        DlgPersetujuan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgPersetujuan.setName("DlgPersetujuan"); // NOI18N
        DlgPersetujuan.setUndecorated(true);
        DlgPersetujuan.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Pengajuan Biaya/Anggaran Yang Disetujui ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        jLabel3.setText("Nomor :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelBiasa2.add(jLabel3);
        jLabel3.setBounds(0, 10, 55, 23);

        NoPengajuan.setEditable(false);
        NoPengajuan.setHighlighter(null);
        NoPengajuan.setName("NoPengajuan"); // NOI18N
        panelBiasa2.add(NoPengajuan);
        NoPengajuan.setBounds(59, 10, 130, 23);

        jLabel12.setText("Volume :");
        jLabel12.setName("jLabel12"); // NOI18N
        panelBiasa2.add(jLabel12);
        jLabel12.setBounds(206, 10, 52, 23);

        Jumlah.setHighlighter(null);
        Jumlah.setName("Jumlah"); // NOI18N
        Jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahKeyPressed(evt);
            }
        });
        panelBiasa2.add(Jumlah);
        Jumlah.setBounds(262, 10, 55, 23);

        Harga.setHighlighter(null);
        Harga.setName("Harga"); // NOI18N
        Harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HargaKeyPressed(evt);
            }
        });
        panelBiasa2.add(Harga);
        Harga.setBounds(388, 10, 120, 23);

        jLabel13.setText("Harga :");
        jLabel13.setName("jLabel13"); // NOI18N
        panelBiasa2.add(jLabel13);
        jLabel13.setBounds(334, 10, 50, 23);

        jLabel14.setText("Total :");
        jLabel14.setName("jLabel14"); // NOI18N
        panelBiasa2.add(jLabel14);
        jLabel14.setBounds(0, 40, 55, 23);

        Total.setEditable(false);
        Total.setHighlighter(null);
        Total.setName("Total"); // NOI18N
        panelBiasa2.add(Total);
        Total.setBounds(59, 40, 160, 23);

        BtnSimpanRekon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanRekon.setMnemonic('S');
        BtnSimpanRekon.setText("Simpan");
        BtnSimpanRekon.setToolTipText("Alt+S");
        BtnSimpanRekon.setName("BtnSimpanRekon"); // NOI18N
        BtnSimpanRekon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanRekonActionPerformed(evt);
            }
        });
        BtnSimpanRekon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanRekonKeyPressed(evt);
            }
        });
        panelBiasa2.add(BtnSimpanRekon);
        BtnSimpanRekon.setBounds(300, 40, 100, 30);

        BtnKeluarRekon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnKeluarRekon.setMnemonic('U');
        BtnKeluarRekon.setText("Tutup");
        BtnKeluarRekon.setToolTipText("Alt+U");
        BtnKeluarRekon.setName("BtnKeluarRekon"); // NOI18N
        BtnKeluarRekon.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarRekon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarRekonActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluarRekon);
        BtnKeluarRekon.setBounds(408, 40, 100, 30);

        internalFrame4.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgPersetujuan.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Persetujuan Pengajuan Biaya/Anggaran ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        tbBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbBangsalKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 101));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label19.setText("P.J.Terkait :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label19);

        kdpegawai.setEditable(false);
        kdpegawai.setName("kdpegawai"); // NOI18N
        kdpegawai.setPreferredSize(new java.awt.Dimension(80, 23));
        kdpegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpegawaiKeyPressed(evt);
            }
        });
        panelisi3.add(kdpegawai);

        nmpegawai.setEditable(false);
        nmpegawai.setName("nmpegawai"); // NOI18N
        nmpegawai.setPreferredSize(new java.awt.Dimension(190, 23));
        panelisi3.add(nmpegawai);

        BtnPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPegawai.setMnemonic('3');
        BtnPegawai.setToolTipText("Alt+3");
        BtnPegawai.setName("BtnPegawai"); // NOI18N
        BtnPegawai.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPegawaiActionPerformed(evt);
            }
        });
        BtnPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPegawaiKeyPressed(evt);
            }
        });
        panelisi3.add(BtnPegawai);

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi3.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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
        panelisi3.add(BtnCari);

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
        panelisi3.add(BtnAll);

        jPanel1.add(panelisi3, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(55, 55));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Belum Disetujui :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(95, 23));
        panelisi1.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount.setForeground(new java.awt.Color(50, 50, 50));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(180, 23));
        panelisi1.add(LCount);

        BtnSetujui.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnSetujui.setMnemonic('s');
        BtnSetujui.setText("Setujui");
        BtnSetujui.setToolTipText("Alt+S");
        BtnSetujui.setName("BtnSetujui"); // NOI18N
        BtnSetujui.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSetujui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSetujuiActionPerformed(evt);
            }
        });
        BtnSetujui.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSetujuiKeyPressed(evt);
            }
        });
        panelisi1.add(BtnSetujui);

        BtnTolak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnTolak.setMnemonic('s');
        BtnTolak.setText("Tolak");
        BtnTolak.setToolTipText("Alt+S");
        BtnTolak.setName("BtnTolak"); // NOI18N
        BtnTolak.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTolak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTolakActionPerformed(evt);
            }
        });
        BtnTolak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTolakKeyPressed(evt);
            }
        });
        panelisi1.add(BtnTolak);

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

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>(); 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptPersetujuanPengajuanBiaya.jasper","report","::[ Data Pengajuan Biaya ]::",
                   "select pengajuan_biaya.no_pengajuan,pengajuan_biaya.tanggal,pengajuan_biaya.nik,pegawai.nama as namapengaju,"+
                   "pegawai.bidang,pegawai.departemen,pengajuan_biaya.urgensi,pengajuan_biaya.uraian_latar_belakang,pengajuan_biaya.tujuan_pengajuan,"+
                   "pengajuan_biaya.target_sasaran,pengajuan_biaya.lokasi_kegiatan,pengajuan_biaya.jumlah,pengajuan_biaya.harga,"+
                   "pengajuan_biaya.total,pengajuan_biaya.keterangan from pengajuan_biaya inner join pegawai on pengajuan_biaya.nik=pegawai.nik "+
                   "where pengajuan_biaya.status='Proses Pengajuan' and pengajuan_biaya.nik_pj='"+kdpegawai.getText()+"' "+
                   (TCari.getText().trim().equals("")?"":"and (pengajuan_biaya.no_pengajuan like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_biaya.nik like '%"+TCari.getText().trim()+"%' or pegawai.nama like '%"+TCari.getText().trim()+"%' or "+
                   "pegawai.bidang like '%"+TCari.getText().trim()+"%' or pegawai.departemen like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_biaya.urgensi like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.uraian_latar_belakang like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_biaya.tujuan_pengajuan like '%"+TCari.getText().trim()+"%' or pengajuan_biaya.lokasi_kegiatan like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_biaya.keterangan like '%"+TCari.getText().trim()+"%')")+" order by pengajuan_biaya.tanggal",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSetujui, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        DlgPersetujuan.dispose();
        pegawai.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdpegawai.setText("");
        nmpegawai.setText("");
        tampil();

}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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
            tampil();
        }else{
            Valid.pindah(evt, TKd, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void kdpegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpegawaiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPegawaiActionPerformed(null);
        }
    }//GEN-LAST:event_kdpegawaiKeyPressed

    private void BtnPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPegawaiActionPerformed
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnPegawaiActionPerformed

    private void BtnPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPegawaiKeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnPegawaiKeyPressed

    private void BtnSetujuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSetujuiActionPerformed
        if(tbBangsal.getSelectedRow()> -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgPersetujuan.setLocationRelativeTo(internalFrame1);
            DlgPersetujuan.setVisible(true);
            Jumlah.requestFocus();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pengajuan yang diajukan..!!");
        }
    }//GEN-LAST:event_BtnSetujuiActionPerformed

    private void BtnSetujuiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSetujuiKeyPressed
        Valid.pindah(evt,TCari,BtnTolak);
    }//GEN-LAST:event_BtnSetujuiKeyPressed

    private void BtnTolakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTolakActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        }else{
            if(tbBangsal.getSelectedRow()> -1){
                if(Sequel.mengedittf("pengajuan_biaya","no_pengajuan=?","status='Ditolak'",1,new String[]{
                        tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString()
                    })==true){
                        tabMode.removeRow(tbBangsal.getSelectedRow());
                        hitung();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data terlebih dahulu..!!!!");
            }
        }
    }//GEN-LAST:event_BtnTolakActionPerformed

    private void BtnTolakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTolakKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnTolakKeyPressed

    private void BtnSimpanRekonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanRekonKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanRekonActionPerformed(null);
        }else{
            Valid.pindah(evt,Harga,BtnKeluarRekon);
        }
    }//GEN-LAST:event_BtnSimpanRekonKeyPressed

    private void BtnSimpanRekonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanRekonActionPerformed
        if(NoPengajuan.getText().trim().equals("")){
            Valid.textKosong(NoPengajuan,"No.Pengajuan");
        }else if(Jumlah.getText().trim().equals("")||Jumlah.getText().trim().equals("0")){
            Valid.textKosong(Jumlah,"Jumlah");
        }else if(Harga.getText().trim().equals("")||Harga.getText().trim().equals("0")){
            Valid.textKosong(Harga,"Harga");
        }else if(Total.getText().trim().equals("")||Total.getText().trim().equals("0")){
            Valid.textKosong(Harga,"Total");
        }else{
            if(tbBangsal.getSelectedRow()> -1){
                if(Sequel.menyimpantf("pengajuan_biaya_disetujui","?,?,?,?","Data",4,new String[]{
                    NoPengajuan.getText(),Jumlah.getText(),Harga.getText(),Double.toString(Double.parseDouble(Harga.getText())*Double.parseDouble(Jumlah.getText()))
                })==true){
                    if(Sequel.mengedittf("pengajuan_biaya","no_pengajuan=?","status='Disetujui'",1,new String[]{
                            tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString()
                        })==true){
                            tabMode.removeRow(tbBangsal.getSelectedRow());
                            hitung();
                    }
                    DlgPersetujuan.dispose();
                }   
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih No.Pengajuan yang diajukan..!!");
            }
        }
    }//GEN-LAST:event_BtnSimpanRekonActionPerformed

    private void BtnKeluarRekonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarRekonActionPerformed
        DlgPersetujuan.dispose();
    }//GEN-LAST:event_BtnKeluarRekonActionPerformed

    private void JumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahKeyPressed
        Valid.pindah(evt,BtnKeluarRekon,Harga);
    }//GEN-LAST:event_JumlahKeyPressed

    private void HargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HargaKeyPressed
        Valid.pindah(evt,Jumlah,BtnSimpanRekon);
    }//GEN-LAST:event_HargaKeyPressed

    private void tbBangsalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbBangsalKeyReleased

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbBangsalMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganPersetujuanPengajuanBiaya dialog = new KeuanganPersetujuanPengajuanBiaya(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluarRekon;
    private widget.Button BtnPegawai;
    private widget.Button BtnPrint;
    private widget.Button BtnSetujui;
    private widget.Button BtnSimpanRekon;
    private widget.Button BtnTolak;
    private javax.swing.JDialog DlgPersetujuan;
    private widget.TextBox Harga;
    private widget.TextBox Jumlah;
    private javax.swing.JLabel LCount;
    private widget.TextBox NoPengajuan;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.TextBox Total;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private javax.swing.JLabel jLabel10;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel3;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdpegawai;
    private widget.Label label17;
    private widget.Label label19;
    private widget.TextBox nmpegawai;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    private void tampil(){
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                   "select pengajuan_biaya.no_pengajuan,pengajuan_biaya.tanggal,pengajuan_biaya.nik,pegawai.nama as namapengaju,"+
                   "pegawai.bidang,pegawai.departemen,pengajuan_biaya.urgensi,pengajuan_biaya.uraian_latar_belakang,pengajuan_biaya.tujuan_pengajuan,"+
                   "pengajuan_biaya.target_sasaran,pengajuan_biaya.lokasi_kegiatan,pengajuan_biaya.jumlah,pengajuan_biaya.harga,"+
                   "pengajuan_biaya.total,pengajuan_biaya.keterangan from pengajuan_biaya inner join pegawai on pengajuan_biaya.nik=pegawai.nik "+
                   "where pengajuan_biaya.status='Proses Pengajuan' and pengajuan_biaya.nik_pj=? "+
                   (TCari.getText().trim().equals("")?"":"and (pengajuan_biaya.no_pengajuan like ? or pengajuan_biaya.nik like ? or pegawai.nama like ? or "+
                   "pegawai.bidang like ? or pegawai.departemen like ? or pengajuan_biaya.urgensi like ? or pengajuan_biaya.uraian_latar_belakang like ? or "+
                   "pengajuan_biaya.tujuan_pengajuan like ? or pengajuan_biaya.lokasi_kegiatan like ? or pengajuan_biaya.keterangan like ?)")+
                   " order by pengajuan_biaya.tanggal");
            try {
                ps.setString(1,kdpegawai.getText());
                if(!TCari.getText().trim().equals("")){
                    ps.setString(2,"%"+TCari.getText().trim()+"%");
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_pengajuan"),rs.getString("tanggal"),rs.getString("nik"),rs.getString("namapengaju"),rs.getString("bidang"),
                        rs.getString("departemen"),rs.getString("urgensi"),rs.getString("uraian_latar_belakang"),rs.getString("tujuan_pengajuan"),
                        rs.getString("target_sasaran"),rs.getString("lokasi_kegiatan"),rs.getDouble("jumlah"),rs.getDouble("harga"),rs.getDouble("total"),
                        rs.getString("keterangan")
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
            hitung();
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void hitung(){
        belumdisetujui=0;
        for(i=0;i<tabMode.getRowCount();i++){
            belumdisetujui=belumdisetujui+Valid.SetAngka(tbBangsal.getValueAt(i,13).toString());
        }
        LCount.setText(""+Valid.SetAngka(belumdisetujui));
    }
    
    public void isCek(){
        if(akses.getjml2()>=1){
            kdpegawai.setEditable(false);
            BtnPegawai.setEnabled(false);
            kdpegawai.setText(akses.getkode());
            nmpegawai.setText(pegawai.tampil3(kdpegawai.getText()));
        }  
    }
    
    private void getData() {
        if(tbBangsal.getSelectedRow()!= -1){
            NoPengajuan.setText(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString());
            Jumlah.setText(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString());
            Harga.setText(Valid.SetAngka(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString())+"");
        }
    }
    
    private void isHitung(){
        if((!Harga.getText().equals(""))&&(!Jumlah.getText().equals(""))){
            Total.setText(Valid.SetAngka(Double.parseDouble(Harga.getText())*Double.parseDouble(Jumlah.getText())));
        }
    }
}

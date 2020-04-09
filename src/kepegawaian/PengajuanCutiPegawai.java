/*
 * Kontribusi dari Mas Haris, RS Bhayangkara nganjuk
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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


/**
 *
 * @author perpustakaan
 */
public final class PengajuanCutiPegawai extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public PengajuanCutiPegawai(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Pengajuan","Pengajuan","Tgl Awal","Tgl Akhir","Jenis Cuti","Alamat Tujuan",
                "Jml Cuti","Kepentingan Cuti","NIK P.J.","P.J. Terkait", "Status"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 11; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(85);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(65);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(210);
            }else if(i==6){
                column.setPreferredWidth(80);
            }else if(i==7){
                column.setPreferredWidth(210);
            }else if(i==8){
                column.setPreferredWidth(85);
            }else if(i==9){
                column.setPreferredWidth(170);
            }else if(i==10){
                column.setPreferredWidth(100);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        Kepentingan.setDocument(new batasInput((int)70).getKata(Kepentingan));
        NoPengajuan.setDocument(new batasInput((int)17).getKata(NoPengajuan));
        Alamat.setDocument(new batasInput((int)100).getKata(Alamat));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        Jumlah.setDocument(new batasInput((byte)3).getOnlyAngka(Jumlah));
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
                    KdPetugasPJ.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NmPetugasPJ.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
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

    private DlgCariPegawai petugas=new DlgCariPegawai(null,false);

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        KdPetugas = new widget.TextBox();
        NmPetugas = new widget.TextBox();
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
        jLabel18 = new widget.Label();
        LCount1 = new widget.Label();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel8 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel5 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        Alamat = new widget.TextArea();
        jLabel3 = new widget.Label();
        NoPengajuan = new widget.TextBox();
        jLabel20 = new widget.Label();
        Urgensi = new widget.ComboBox();
        jLabel4 = new widget.Label();
        Kepentingan = new widget.TextBox();
        jLabel11 = new widget.Label();
        Jumlah = new widget.TextBox();
        jLabel17 = new widget.Label();
        KdPetugasPJ = new widget.TextBox();
        NmPetugasPJ = new widget.TextBox();
        btnPetugasPJ = new widget.Button();
        jLabel14 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        jLabel22 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel12 = new widget.Label();
        ChkInput = new widget.CekBox();

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });

        NmPetugas.setEditable(false);
        NmPetugas.setHighlighter(null);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmPetugasKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengajuan Cuti Pegawai ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(70, 23));
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
        jLabel19.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-02-2020" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-02-2020" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(190, 23));
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

        jLabel18.setText("Pengajuan :");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel18);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount1);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(72, 155));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(55, 165));
        FormInput.setLayout(null);

        jLabel8.setText("Tanggal Pengajuan :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(220, 10, 110, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-02-2020" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(334, 10, 90, 23);

        jLabel5.setText("Alamat Tujuan :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(435, 10, 95, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        Alamat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Alamat.setColumns(20);
        Alamat.setRows(5);
        Alamat.setName("Alamat"); // NOI18N
        Alamat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlamatKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(Alamat);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(534, 10, 235, 52);

        jLabel3.setText("No.Pengajuan :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 86, 23);

        NoPengajuan.setHighlighter(null);
        NoPengajuan.setName("NoPengajuan"); // NOI18N
        FormInput.add(NoPengajuan);
        NoPengajuan.setBounds(90, 10, 125, 23);

        jLabel20.setText("Jenis Cuti :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(521, 70, 85, 23);

        Urgensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tahunan", "Besar", "Sakit", "Bersalin", "Alasan Penting", "Keterangan Lainnya" }));
        Urgensi.setName("Urgensi"); // NOI18N
        Urgensi.setPreferredSize(new java.awt.Dimension(55, 28));
        Urgensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrgensiKeyPressed(evt);
            }
        });
        FormInput.add(Urgensi);
        Urgensi.setBounds(610, 70, 159, 23);

        jLabel4.setText("Kepentingan/Alasan Cuti :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 100, 145, 23);

        Kepentingan.setHighlighter(null);
        Kepentingan.setName("Kepentingan"); // NOI18N
        Kepentingan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KepentinganKeyPressed(evt);
            }
        });
        FormInput.add(Kepentingan);
        Kepentingan.setBounds(149, 100, 620, 23);

        jLabel11.setText("Jml Cuti :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(303, 70, 70, 23);

        Jumlah.setHighlighter(null);
        Jumlah.setName("Jumlah"); // NOI18N
        Jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahKeyPressed(evt);
            }
        });
        FormInput.add(Jumlah);
        Jumlah.setBounds(377, 70, 45, 23);

        jLabel17.setText("P.J.Terkait :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 40, 86, 23);

        KdPetugasPJ.setEditable(false);
        KdPetugasPJ.setHighlighter(null);
        KdPetugasPJ.setName("KdPetugasPJ"); // NOI18N
        KdPetugasPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasPJKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugasPJ);
        KdPetugasPJ.setBounds(90, 40, 110, 23);

        NmPetugasPJ.setEditable(false);
        NmPetugasPJ.setHighlighter(null);
        NmPetugasPJ.setName("NmPetugasPJ"); // NOI18N
        NmPetugasPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmPetugasPJKeyPressed(evt);
            }
        });
        FormInput.add(NmPetugasPJ);
        NmPetugasPJ.setBounds(202, 40, 220, 23);

        btnPetugasPJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugasPJ.setMnemonic('2');
        btnPetugasPJ.setToolTipText("Alt+2");
        btnPetugasPJ.setName("btnPetugasPJ"); // NOI18N
        btnPetugasPJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasPJActionPerformed(evt);
            }
        });
        btnPetugasPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPetugasPJKeyPressed(evt);
            }
        });
        FormInput.add(btnPetugasPJ);
        btnPetugasPJ.setBounds(425, 40, 28, 23);

        jLabel14.setText("Tanggal Cuti :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 70, 86, 23);

        Tgl1.setForeground(new java.awt.Color(50, 70, 50));
        Tgl1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-02-2020" }));
        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setOpaque(false);
        Tgl1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Tgl1ItemStateChanged(evt);
            }
        });
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        FormInput.add(Tgl1);
        Tgl1.setBounds(90, 70, 90, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("s/d");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(182, 70, 25, 23);

        Tgl2.setForeground(new java.awt.Color(50, 70, 50));
        Tgl2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16-02-2020" }));
        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setOpaque(false);
        Tgl2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Tgl2ItemStateChanged(evt);
            }
        });
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        FormInput.add(Tgl2);
        Tgl2.setBounds(209, 70, 90, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Hari");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(425, 70, 40, 23);

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

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoPengajuan.getText().trim().equals("")){
            Valid.textKosong(NoPengajuan,"No.Pengajuan");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Yang Mengajukan");
        }else if(Jumlah.getText().trim().equals("")||Jumlah.getText().trim().equals("0")){
            Valid.textKosong(Jumlah,"Jml Cuti");
        }else if(Alamat.getText().trim().equals("")){
            Valid.textKosong(Alamat,"Alamat Tujuan");
        }else if(Kepentingan.getText().trim().equals("")){
            Valid.textKosong(Kepentingan,"Kepentingan Cuti");
        }else if(NmPetugasPJ.getText().trim().equals("")){
            Valid.textKosong(KdPetugasPJ,"P.J. terkait pengajuan");
        }else{
            if(Sequel.menyimpantf("pengajuan_cuti","?,?,?,?,?,?,?,?,?,?,?","Data",11,new String[]{
                    NoPengajuan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Valid.SetTgl(Tgl1.getSelectedItem()+""),Valid.SetTgl(Tgl2.getSelectedItem()+""),
                    KdPetugas.getText(),Urgensi.getSelectedItem().toString(),Alamat.getText(),Jumlah.getText(),Kepentingan.getText(),KdPetugasPJ.getText(),
                    "Proses Pengajuan"
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
            Valid.pindah(evt,Kepentingan,BtnBatal);
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
        if(tbObat.getSelectedRow()> -1){
            if(!tbObat.getValueAt(tbObat.getSelectedRow(),10).toString().equals("Disetujui")){
                Sequel.meghapus("pengajuan_cuti","no_pengajuan",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
                tampil();
                emptTeks();    
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, sudah disetujui. Tidak boleh dihapus/dirubah... !");
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
        if(NoPengajuan.getText().trim().equals("")){
            Valid.textKosong(NoPengajuan,"No.Pengajuan");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Yang Mengajukan");
        }else if(Alamat.getText().trim().equals("")){
            Valid.textKosong(Alamat,"Alamat Tujuan");
        }else if(Jumlah.getText().trim().equals("")||Jumlah.getText().trim().equals("0")){
            Valid.textKosong(Jumlah,"Jml Cuti");
        }else if(Kepentingan.getText().trim().equals("")){
            Valid.textKosong(Kepentingan,"Kepentingan Cuti");
        }else if(NmPetugasPJ.getText().trim().equals("")){
            Valid.textKosong(KdPetugasPJ,"P.J. terkait pengajuan");
        }else{
            if(tbObat.getSelectedRow()> -1){
                if(!tbObat.getValueAt(tbObat.getSelectedRow(),10).toString().equals("Disetujui")){
                    if(Sequel.mengedittf("pengajuan_cuti","no_pengajuan=?","no_pengajuan=?,tanggal=?,tanggal_awal=?,tanggal_akhir=?,nik=?,urgensi=?,alamat=?,jumlah=?,kepentingan=?,nik_pj=?",11,new String[]{
                            NoPengajuan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),Valid.SetTgl(Tgl1.getSelectedItem()+""),Valid.SetTgl(Tgl2.getSelectedItem()+""),KdPetugas.getText(),
                            Urgensi.getSelectedItem().toString(),Alamat.getText(),Jumlah.getText(),Kepentingan.getText(),KdPetugasPJ.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                        })==true){
                            tampil();
                            emptTeks();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, sudah disetujui. Tidak boleh dihapus/dirubah... !");
                }
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
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
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
                Valid.MyReportqry("rptPengajuanCutiPegawai.jasper","report","::[ Data Pengajuan Cuti ]::",
                        "select pengajuan_cuti.no_pengajuan,pengajuan_cuti.tanggal,pengajuan_cuti.tanggal_awal,pengajuan_cuti.tanggal_akhir,"+
                        "pengajuan_cuti.urgensi,pengajuan_cuti.alamat,pengajuan_cuti.jumlah,"+
                        "pengajuan_cuti.kepentingan,pengajuan_cuti.nik_pj,pegawai.nama,pengajuan_cuti.status "+
                        "from pengajuan_cuti inner join pegawai on pengajuan_cuti.nik_pj=pegawai.nik where "+
                        "pengajuan_cuti.nik='"+KdPetugas.getText()+"' and pengajuan_cuti.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' order by pengajuan_cuti.tanggal",param);
            }else{
                Valid.MyReportqry("rptPengajuanCutiPegawai.jasper","report","::[ Data Pengajuan Cuti ]::",
                        "select pengajuan_cuti.no_pengajuan,pengajuan_cuti.tanggal,pengajuan_cuti.tanggal_awal,pengajuan_cuti.tanggal_akhir,"+
                        "pengajuan_cuti.urgensi,pengajuan_cuti.alamat,pengajuan_cuti.jumlah,"+
                        "pengajuan_cuti.kepentingan,pengajuan_cuti.nik_pj,pegawai.nama,pengajuan_cuti.status "+
                        "from pengajuan_cuti inner join pegawai on pengajuan_cuti.nik_pj=pegawai.nik where "+
                        "pengajuan_cuti.nik='"+KdPetugas.getText()+"' and pengajuan_cuti.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pengajuan_cuti.no_pengajuan like '%"+TCari.getText().trim()+"%' or "+
                        "pengajuan_cuti.nik='"+KdPetugas.getText()+"' and pengajuan_cuti.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pengajuan_cuti.nik_pj like '%"+TCari.getText().trim()+"%' or "+
                        "pengajuan_cuti.nik='"+KdPetugas.getText()+"' and pengajuan_cuti.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pegawai.nama like '%"+TCari.getText().trim()+"%' or "+
                        "pengajuan_cuti.nik='"+KdPetugas.getText()+"' and pengajuan_cuti.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pengajuan_cuti.urgensi like '%"+TCari.getText().trim()+"%' or "+
                        "pengajuan_cuti.nik='"+KdPetugas.getText()+"' and pengajuan_cuti.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pengajuan_cuti.alamat like '%"+TCari.getText().trim()+"%' or "+
                        "pengajuan_cuti.nik='"+KdPetugas.getText()+"' and pengajuan_cuti.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pengajuan_cuti.kepentingan like '%"+TCari.getText().trim()+"%' or "+
                        "pengajuan_cuti.nik='"+KdPetugas.getText()+"' and pengajuan_cuti.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pengajuan_cuti.status like '%"+TCari.getText().trim()+"%' "+
                        "order by pengajuan_cuti.tanggal",param);
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari,BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,Tgl1);
}//GEN-LAST:event_TanggalKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

private void KdPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasKeyPressed
     Valid.pindah(evt,TCari,Tanggal);
        
}//GEN-LAST:event_KdPetugasKeyPressed

private void NmPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmPetugasKeyPressed
        //Valid.pindah(evt,TKd,TSpek);
}//GEN-LAST:event_NmPetugasKeyPressed

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

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

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        emptTeks();
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void KdPetugasPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasPJKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugasPJKeyPressed

    private void NmPetugasPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmPetugasPJKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPetugasPJKeyPressed

    private void btnPetugasPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasPJActionPerformed
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasPJActionPerformed

    private void KepentinganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KepentinganKeyPressed
        Valid.pindah(evt,Urgensi,BtnSimpan);
    }//GEN-LAST:event_KepentinganKeyPressed

    private void UrgensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrgensiKeyPressed
        Valid.pindah(evt,Jumlah,Kepentingan);
    }//GEN-LAST:event_UrgensiKeyPressed

    private void JumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahKeyPressed
        Valid.pindah(evt,Tgl2,Urgensi);
    }//GEN-LAST:event_JumlahKeyPressed

    private void AlamatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlamatKeyPressed
        Valid.pindah(evt,Tanggal,Tgl1);
    }//GEN-LAST:event_AlamatKeyPressed

    private void btnPetugasPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasPJKeyPressed
        Valid.pindah(evt,Tgl2,Urgensi);
    }//GEN-LAST:event_btnPetugasPJKeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,Alamat,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt,Tgl1,Jumlah);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void Tgl2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Tgl2ItemStateChanged
        Sequel.cariIsi("select to_days('"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"')-to_days('"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"')",Jumlah);   
    }//GEN-LAST:event_Tgl2ItemStateChanged

    private void Tgl1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Tgl1ItemStateChanged
        Sequel.cariIsi("select to_days('"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"')-to_days('"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"')",Jumlah); 
    }//GEN-LAST:event_Tgl1ItemStateChanged

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PengajuanCutiPegawai dialog = new PengajuanCutiPegawai(new javax.swing.JFrame(), true);
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
    private widget.TextArea Alamat;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Jumlah;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugasPJ;
    private widget.TextBox Kepentingan;
    private widget.Label LCount;
    private widget.Label LCount1;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugasPJ;
    private widget.TextBox NoPengajuan;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.ComboBox Urgensi;
    private widget.Button btnPetugasPJ;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel14;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                   "select pengajuan_cuti.no_pengajuan,pengajuan_cuti.tanggal,pengajuan_cuti.tanggal_awal,pengajuan_cuti.tanggal_akhir,"+
                   "pengajuan_cuti.urgensi,pengajuan_cuti.alamat,pengajuan_cuti.jumlah,"+
                   "pengajuan_cuti.kepentingan,pengajuan_cuti.nik_pj,pegawai.nama,pengajuan_cuti.status "+
                   "from pengajuan_cuti inner join pegawai on pengajuan_cuti.nik_pj=pegawai.nik where "+
                   "pengajuan_cuti.nik=? and pengajuan_cuti.tanggal between ? and ? order by pengajuan_cuti.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                   "select pengajuan_cuti.no_pengajuan,pengajuan_cuti.tanggal,pengajuan_cuti.tanggal_awal,pengajuan_cuti.tanggal_akhir,"+
                   "pengajuan_cuti.urgensi,pengajuan_cuti.alamat,pengajuan_cuti.jumlah,"+
                   "pengajuan_cuti.kepentingan,pengajuan_cuti.nik_pj,pegawai.nama,pengajuan_cuti.status "+
                   "from pengajuan_cuti inner join pegawai on pengajuan_cuti.nik_pj=pegawai.nik where "+
                   "pengajuan_cuti.nik=? and pengajuan_cuti.tanggal between ? and ? and pengajuan_cuti.no_pengajuan like ? or "+
                   "pengajuan_cuti.nik=? and pengajuan_cuti.tanggal between ? and ? and pengajuan_cuti.nik_pj like ? or "+
                   "pengajuan_cuti.nik=? and pengajuan_cuti.tanggal between ? and ? and pegawai.nama like ? or "+
                   "pengajuan_cuti.nik=? and pengajuan_cuti.tanggal between ? and ? and pengajuan_cuti.urgensi like ? or "+
                   "pengajuan_cuti.nik=? and pengajuan_cuti.tanggal between ? and ? and pengajuan_cuti.alamat like ? or "+
                   "pengajuan_cuti.nik=? and pengajuan_cuti.tanggal between ? and ? and pengajuan_cuti.kepentingan like ? or "+
                   "pengajuan_cuti.nik=? and pengajuan_cuti.tanggal between ? and ? and pengajuan_cuti.status like ? "+
                   "order by pengajuan_cuti.tanggal");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,KdPetugas.getText());
                    ps.setString(2,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(3,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1,KdPetugas.getText());
                    ps.setString(2,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(3,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,KdPetugas.getText());
                    ps.setString(6,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(7,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,KdPetugas.getText());
                    ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,KdPetugas.getText());
                    ps.setString(14,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(15,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(16,"%"+TCari.getText().trim()+"%");
                    ps.setString(17,KdPetugas.getText());
                    ps.setString(18,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(19,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(20,"%"+TCari.getText().trim()+"%");
                    ps.setString(21,KdPetugas.getText());
                    ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(24,"%"+TCari.getText().trim()+"%");
                    ps.setString(25,KdPetugas.getText());
                    ps.setString(26,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(27,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(28,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                i=0;
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_pengajuan"),rs.getString("tanggal"),rs.getString("tanggal_awal"),rs.getString("tanggal_akhir"),
                        rs.getString("urgensi"),rs.getString("alamat"),rs.getString("jumlah"),rs.getString("kepentingan"),rs.getString("nik_pj"),
                        rs.getString("nama"),rs.getString("status")
                    });
                    i=i+rs.getInt("jumlah");
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
        LCount1.setText(Valid.SetAngka(i));
    }

    private void emptTeks() {
        Tanggal.setDate(new Date());
        Tgl1.setDate(new Date());
        Tgl2.setDate(new Date());
        Alamat.setText("");
        Jumlah.setText("0");
        Kepentingan.setText("");
        autoNomor();
        Alamat.requestFocus();
    }
    
    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoPengajuan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            Valid.SetTgl(Tgl1,tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            Valid.SetTgl(Tgl2,tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Urgensi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Alamat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Jumlah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Kepentingan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            KdPetugasPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            NmPetugasPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
        }
    }

    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,155));
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
        KdPetugas.setText(akses.getkode());
        Sequel.cariIsi("select nama from pegawai where nik=?", NmPetugas,KdPetugas.getText());
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_pengajuan,3),signed)),0) from pengajuan_cuti where tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "PC"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoPengajuan); 
    }
    
    
}

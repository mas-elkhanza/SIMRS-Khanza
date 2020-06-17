/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgRujuk.java
 *
 * Created on 31 Mei 10, 20:19:56
 */

package inventaris;

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
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;


/**
 *
 * @author perpustakaan
 */
public final class PengajuanInventaris extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,pilihan=0;
    private double total=0;
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public PengajuanInventaris(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Pengajuan","Tanggal","NIK","Diajukan Oleh","Bidang","Departemen","Urgensi","Latar Belakang",
                "Nama Barang","Spesifikasi","Jml","Harga", "Total", "Keterangan", "NIK P.J.","P.J. Terkait", "Status"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 17; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
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
                column.setPreferredWidth(170);
            }else if(i==8){
                column.setPreferredWidth(160);
            }else if(i==9){
                column.setPreferredWidth(170);
            }else if(i==10){
                column.setPreferredWidth(35);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(90);
            }else if(i==13){
                column.setPreferredWidth(140);
            }else if(i==14){
                column.setPreferredWidth(85);
            }else if(i==15){
                column.setPreferredWidth(160);
            }else if(i==16){
                column.setPreferredWidth(95);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        NamaBarang.setDocument(new batasInput((int)70).getKata(NamaBarang));
        LatarBelakang.setDocument(new batasInput((int)200).getKata(LatarBelakang));
        Spesifikasi.setDocument(new batasInput((int)200).getKata(Spesifikasi));
        Keterangan.setDocument(new batasInput((int)70).getKata(Keterangan));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        Jumlah.setDocument(new batasInput((byte)4).getOnlyAngka(Jumlah));
        Harga.setDocument(new batasInput((byte)20).getOnlyAngka(Harga));
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
                    if(pilihan==1){
                        KdPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        Bidang.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),6).toString());
                        Departemen.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),5).toString());
                        btnPetugas.requestFocus();
                    }else if(pilihan==2){
                        KdPetugasPJ.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NmPetugasPJ.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        btnPetugasPJ.requestFocus();
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
        
        ChkInput.setSelected(false);
        isForm();
        
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

    private DlgCariPegawai petugas=new DlgCariPegawai(null,false);

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppProses = new javax.swing.JMenuItem();
        ppDosetujui = new javax.swing.JMenuItem();
        ppDitolak = new javax.swing.JMenuItem();
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
        KdPetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        NmPetugas = new widget.TextBox();
        scrollPane1 = new widget.ScrollPane();
        LatarBelakang = new widget.TextArea();
        jLabel9 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        Spesifikasi = new widget.TextArea();
        jLabel10 = new widget.Label();
        jLabel3 = new widget.Label();
        NoPengajuan = new widget.TextBox();
        jLabel20 = new widget.Label();
        Urgensi = new widget.ComboBox();
        jLabel4 = new widget.Label();
        NamaBarang = new widget.TextBox();
        jLabel11 = new widget.Label();
        Jumlah = new widget.TextBox();
        Harga = new widget.TextBox();
        jLabel12 = new widget.Label();
        jLabel13 = new widget.Label();
        Total = new widget.TextBox();
        jLabel14 = new widget.Label();
        Keterangan = new widget.TextBox();
        jLabel15 = new widget.Label();
        Bidang = new widget.TextBox();
        jLabel16 = new widget.Label();
        Departemen = new widget.TextBox();
        jLabel17 = new widget.Label();
        KdPetugasPJ = new widget.TextBox();
        NmPetugasPJ = new widget.TextBox();
        btnPetugasPJ = new widget.Button();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setForeground(new java.awt.Color(50,50,50));
        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppProses.setBackground(new java.awt.Color(255, 255, 254));
        ppProses.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppProses.setForeground(new java.awt.Color(50,50,50));
        ppProses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppProses.setText("Set Status Proses Pengajuan");
        ppProses.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppProses.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppProses.setName("ppProses"); // NOI18N
        ppProses.setPreferredSize(new java.awt.Dimension(200, 26));
        ppProses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppProsesBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppProses);

        ppDosetujui.setBackground(new java.awt.Color(255, 255, 254));
        ppDosetujui.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDosetujui.setForeground(new java.awt.Color(50,50,50));
        ppDosetujui.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDosetujui.setText("Set Status Disetujui");
        ppDosetujui.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDosetujui.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDosetujui.setName("ppDosetujui"); // NOI18N
        ppDosetujui.setPreferredSize(new java.awt.Dimension(200, 26));
        ppDosetujui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDosetujuiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppDosetujui);

        ppDitolak.setBackground(new java.awt.Color(255, 255, 254));
        ppDitolak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDitolak.setForeground(new java.awt.Color(50,50,50));
        ppDitolak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppDitolak.setText("Set Status Ditolak");
        ppDitolak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDitolak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDitolak.setName("ppDitolak"); // NOI18N
        ppDitolak.setPreferredSize(new java.awt.Dimension(200, 26));
        ppDitolak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDitolakBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppDitolak);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengajuan Pembelian Aset/Inventaris ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
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
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-08-2019" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-08-2019" }));
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
        TCari.setPreferredSize(new java.awt.Dimension(180, 23));
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

        jLabel18.setText("Total :");
        jLabel18.setName("jLabel18"); // NOI18N
        jLabel18.setPreferredSize(new java.awt.Dimension(45, 23));
        panelGlass9.add(jLabel18);

        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(110, 23));
        panelGlass9.add(LCount1);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(72, 205));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(70, 165));
        FormInput.setLayout(null);

        jLabel8.setText("Tanggal :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(238, 10, 55, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-08-2019" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        FormInput.add(Tanggal);
        Tanggal.setBounds(297, 10, 90, 23);

        jLabel5.setText("Latar Belakang :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 100, 95, 23);

        KdPetugas.setEditable(false);
        KdPetugas.setHighlighter(null);
        KdPetugas.setName("KdPetugas"); // NOI18N
        KdPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugas);
        KdPetugas.setBounds(99, 40, 110, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
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
        btnPetugas.setBounds(400, 40, 28, 23);

        NmPetugas.setEditable(false);
        NmPetugas.setHighlighter(null);
        NmPetugas.setName("NmPetugas"); // NOI18N
        NmPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmPetugasKeyPressed(evt);
            }
        });
        FormInput.add(NmPetugas);
        NmPetugas.setBounds(212, 40, 185, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        LatarBelakang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        LatarBelakang.setColumns(20);
        LatarBelakang.setRows(5);
        LatarBelakang.setName("LatarBelakang"); // NOI18N
        LatarBelakang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LatarBelakangKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(LatarBelakang);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(99, 100, 286, 42);

        jLabel9.setText("Diajukan Oleh :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 40, 95, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Spesifikasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Spesifikasi.setColumns(20);
        Spesifikasi.setRows(5);
        Spesifikasi.setName("Spesifikasi"); // NOI18N
        Spesifikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SpesifikasiKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Spesifikasi);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(484, 100, 286, 42);

        jLabel10.setText("Spesifikasi :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(390, 100, 90, 23);

        jLabel3.setText("No.Pengajuan :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 10, 95, 23);

        NoPengajuan.setHighlighter(null);
        NoPengajuan.setName("NoPengajuan"); // NOI18N
        FormInput.add(NoPengajuan);
        NoPengajuan.setBounds(99, 10, 130, 23);

        jLabel20.setText("Urgensi :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 70, 95, 23);

        Urgensi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Cito", "Emergensi", "Biasa" }));
        Urgensi.setName("Urgensi"); // NOI18N
        Urgensi.setPreferredSize(new java.awt.Dimension(55, 28));
        Urgensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UrgensiKeyPressed(evt);
            }
        });
        FormInput.add(Urgensi);
        Urgensi.setBounds(99, 70, 115, 23);

        jLabel4.setText("Nama Barang :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(396, 10, 84, 23);

        NamaBarang.setHighlighter(null);
        NamaBarang.setName("NamaBarang"); // NOI18N
        NamaBarang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaBarangKeyPressed(evt);
            }
        });
        FormInput.add(NamaBarang);
        NamaBarang.setBounds(484, 10, 286, 23);

        jLabel11.setText("Jumlah :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(219, 70, 50, 23);

        Jumlah.setHighlighter(null);
        Jumlah.setName("Jumlah"); // NOI18N
        Jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JumlahKeyPressed(evt);
            }
        });
        FormInput.add(Jumlah);
        Jumlah.setBounds(273, 70, 55, 23);

        Harga.setHighlighter(null);
        Harga.setName("Harga"); // NOI18N
        Harga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HargaKeyPressed(evt);
            }
        });
        FormInput.add(Harga);
        Harga.setBounds(385, 70, 120, 23);

        jLabel12.setText("Harga :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(331, 70, 50, 23);

        jLabel13.setText("Total Pengajuan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(516, 70, 90, 23);

        Total.setEditable(false);
        Total.setHighlighter(null);
        Total.setName("Total"); // NOI18N
        FormInput.add(Total);
        Total.setBounds(610, 70, 160, 23);

        jLabel14.setText("Keterangan :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(428, 150, 75, 23);

        Keterangan.setHighlighter(null);
        Keterangan.setName("Keterangan"); // NOI18N
        Keterangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeteranganKeyPressed(evt);
            }
        });
        FormInput.add(Keterangan);
        Keterangan.setBounds(507, 150, 263, 23);

        jLabel15.setText("Bidang :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(430, 40, 50, 23);

        Bidang.setEditable(false);
        Bidang.setHighlighter(null);
        Bidang.setName("Bidang"); // NOI18N
        Bidang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BidangKeyPressed(evt);
            }
        });
        FormInput.add(Bidang);
        Bidang.setBounds(484, 40, 100, 23);

        jLabel16.setText("Departemen :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(586, 40, 80, 23);

        Departemen.setEditable(false);
        Departemen.setHighlighter(null);
        Departemen.setName("Departemen"); // NOI18N
        Departemen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DepartemenKeyPressed(evt);
            }
        });
        FormInput.add(Departemen);
        Departemen.setBounds(670, 40, 100, 23);

        jLabel17.setText("P.J.Terkait :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 150, 95, 23);

        KdPetugasPJ.setEditable(false);
        KdPetugasPJ.setHighlighter(null);
        KdPetugasPJ.setName("KdPetugasPJ"); // NOI18N
        KdPetugasPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPetugasPJKeyPressed(evt);
            }
        });
        FormInput.add(KdPetugasPJ);
        KdPetugasPJ.setBounds(99, 150, 110, 23);

        NmPetugasPJ.setEditable(false);
        NmPetugasPJ.setHighlighter(null);
        NmPetugasPJ.setName("NmPetugasPJ"); // NOI18N
        NmPetugasPJ.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmPetugasPJKeyPressed(evt);
            }
        });
        FormInput.add(NmPetugasPJ);
        NmPetugasPJ.setBounds(212, 150, 185, 23);

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
        btnPetugasPJ.setBounds(400, 150, 28, 23);

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
        if(Spesifikasi.getText().trim().equals("")){
            Valid.textKosong(Spesifikasi,"Spesifikasi");
        }else if(LatarBelakang.getText().trim().equals("")){
            Valid.textKosong(LatarBelakang,"Latar Belakang");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Yang Mengajukan");
        }else if(NamaBarang.getText().trim().equals("")){
            Valid.textKosong(NamaBarang,"Nama Barang");
        }else if(NoPengajuan.getText().trim().equals("")){
            Valid.textKosong(NoPengajuan,"No.Pengajuan");
        }else if(NmPetugasPJ.getText().trim().equals("")){
            Valid.textKosong(KdPetugasPJ,"P.J. terkait pengajuan");
        }else if(Jumlah.getText().trim().equals("")||Jumlah.getText().trim().equals("0")){
            Valid.textKosong(Jumlah,"Jumlah");
        }else if(Harga.getText().trim().equals("")||Harga.getText().trim().equals("0")){
            Valid.textKosong(Harga,"Harga");
        }else if(Total.getText().trim().equals("")||Total.getText().trim().equals("0")){
            Valid.textKosong(Harga,"Total");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else{
            if(Sequel.menyimpantf("pengajuan_inventaris","?,?,?,?,?,?,?,?,?,?,?,?,?","Data",13,new String[]{
                    NoPengajuan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),KdPetugas.getText(),Urgensi.getSelectedItem().toString(),
                    LatarBelakang.getText(),NamaBarang.getText(),Spesifikasi.getText(),Jumlah.getText(),Harga.getText(),
                    Double.toString(Double.parseDouble(Harga.getText())*Double.parseDouble(Jumlah.getText())),Keterangan.getText(),
                    KdPetugasPJ.getText(),"Proses Pengajuan"
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
            Valid.pindah(evt,Spesifikasi,BtnBatal);
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
            Sequel.meghapus("pengajuan_inventaris","no_pengajuan",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            tampil();
            emptTeks();
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
        if(Spesifikasi.getText().trim().equals("")){
            Valid.textKosong(Spesifikasi,"Spesifikasi");
        }else if(LatarBelakang.getText().trim().equals("")){
            Valid.textKosong(LatarBelakang,"Latar Belakang");
        }else if(NmPetugas.getText().trim().equals("")){
            Valid.textKosong(KdPetugas,"Yang Mengajukan");
        }else if(NamaBarang.getText().trim().equals("")){
            Valid.textKosong(NamaBarang,"Nama Barang");
        }else if(NoPengajuan.getText().trim().equals("")){
            Valid.textKosong(NoPengajuan,"No.Pengajuan");
        }else if(NmPetugasPJ.getText().trim().equals("")){
            Valid.textKosong(KdPetugasPJ,"P.J. terkait pengajuan");
        }else if(Jumlah.getText().trim().equals("")||Jumlah.getText().trim().equals("0")){
            Valid.textKosong(Jumlah,"Jumlah");
        }else if(Harga.getText().trim().equals("")||Harga.getText().trim().equals("0")){
            Valid.textKosong(Harga,"Harga");
        }else if(Total.getText().trim().equals("")||Total.getText().trim().equals("0")){
            Valid.textKosong(Harga,"Total");
        }else if(Keterangan.getText().trim().equals("")){
            Valid.textKosong(Keterangan,"Keterangan");
        }else{
            if(tbObat.getSelectedRow()> -1){
                if(Sequel.mengedittf("pengajuan_inventaris","no_pengajuan=?","no_pengajuan=?,tanggal=?,nik=?,urgensi=?,latar_belakang=?,nama_barang=?,spesifikasi=?,jumlah=?,harga=?,total=?,keterangan=?,nik_pj=?",13,new String[]{
                        NoPengajuan.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),KdPetugas.getText(),Urgensi.getSelectedItem().toString(),
                        LatarBelakang.getText(),NamaBarang.getText(),Spesifikasi.getText(),Jumlah.getText(),Harga.getText(),
                        Double.toString(Double.parseDouble(Harga.getText())*Double.parseDouble(Jumlah.getText())),Keterangan.getText(),
                        KdPetugasPJ.getText(),tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                    })==true){
                        tampil();
                        emptTeks();
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
            Valid.MyReportqry("rptPengajuanInventaris.jasper","report","::[ Data Pengajuan Inventaris ]::",
                   "select pengajuan_inventaris.no_pengajuan,pengajuan_inventaris.tanggal,pengajuan_inventaris.nik,peg1.nama as namapengaju,"+
                   "peg1.bidang,peg1.departemen,pengajuan_inventaris.urgensi,pengajuan_inventaris.latar_belakang,pengajuan_inventaris.nama_barang,"+
                   "pengajuan_inventaris.spesifikasi,pengajuan_inventaris.jumlah,pengajuan_inventaris.harga,pengajuan_inventaris.total,"+
                   "pengajuan_inventaris.keterangan,pengajuan_inventaris.nik_pj,peg2.nama as namapj,pengajuan_inventaris.status "+
                   "from pengajuan_inventaris inner join pegawai as peg1 inner join pegawai as peg2 on pengajuan_inventaris.nik=peg1.nik "+
                   "and pengajuan_inventaris.nik_pj=peg2.nik where "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and pengajuan_inventaris.no_pengajuan like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and pengajuan_inventaris.nik like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and peg1.nama like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and peg1.bidang like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and peg1.departemen like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and pengajuan_inventaris.urgensi like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and pengajuan_inventaris.latar_belakang like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and pengajuan_inventaris.nama_barang like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and pengajuan_inventaris.spesifikasi like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and pengajuan_inventaris.keterangan like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and pengajuan_inventaris.nik_pj like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and peg2.nama like '%"+TCari.getText().trim()+"%' or "+
                   "pengajuan_inventaris.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and pengajuan_inventaris.status like '%"+TCari.getText().trim()+"%' order by pengajuan_inventaris.tanggal",param);
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
        Valid.pindah(evt,TCari,NamaBarang);
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
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",NmPetugas,KdPetugas.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else{            
            Valid.pindah(evt,TCari,Tanggal);
        }
}//GEN-LAST:event_KdPetugasKeyPressed

private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        pilihan=1;
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
}//GEN-LAST:event_btnPetugasActionPerformed

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

    private void BidangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BidangKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BidangKeyPressed

    private void DepartemenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DepartemenKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_DepartemenKeyPressed

    private void KdPetugasPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPetugasPJKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KdPetugasPJKeyPressed

    private void NmPetugasPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmPetugasPJKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NmPetugasPJKeyPressed

    private void btnPetugasPJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasPJActionPerformed
        pilihan=2;
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasPJActionPerformed

    private void NamaBarangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaBarangKeyPressed
        Valid.pindah(evt,Tanggal,btnPetugas);
    }//GEN-LAST:event_NamaBarangKeyPressed

    private void btnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasKeyPressed
        Valid.pindah(evt,NamaBarang,Urgensi);
    }//GEN-LAST:event_btnPetugasKeyPressed

    private void UrgensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UrgensiKeyPressed
        Valid.pindah(evt,btnPetugas,Jumlah);
    }//GEN-LAST:event_UrgensiKeyPressed

    private void JumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JumlahKeyPressed
        Valid.pindah(evt,Urgensi,Harga);
    }//GEN-LAST:event_JumlahKeyPressed

    private void HargaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HargaKeyPressed
        Valid.pindah(evt,Jumlah,LatarBelakang);
    }//GEN-LAST:event_HargaKeyPressed

    private void LatarBelakangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LatarBelakangKeyPressed
        Valid.pindah(evt,Harga,Spesifikasi);
    }//GEN-LAST:event_LatarBelakangKeyPressed

    private void SpesifikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SpesifikasiKeyPressed
        Valid.pindah(evt,LatarBelakang,btnPetugasPJ);
    }//GEN-LAST:event_SpesifikasiKeyPressed

    private void btnPetugasPJKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPetugasPJKeyPressed
        Valid.pindah(evt,Spesifikasi,Keterangan);
    }//GEN-LAST:event_btnPetugasPJKeyPressed

    private void KeteranganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KeteranganKeyPressed
        Valid.pindah(evt,btnPetugasPJ,BtnSimpan);
    }//GEN-LAST:event_KeteranganKeyPressed

    private void ppProsesBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppProsesBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        }else{
            if(tbObat.getSelectedRow()> -1){
                if(Sequel.mengedittf("pengajuan_inventaris","no_pengajuan=?","status='Proses Pengajuan'",1,new String[]{
                        tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                    })==true){
                        tampil();
                        emptTeks();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data terlebih dahulu..!!!!");
            }
        }
    }//GEN-LAST:event_ppProsesBtnPrintActionPerformed

    private void ppDosetujuiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDosetujuiBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        }else{
            if(tbObat.getSelectedRow()> -1){
                if(Sequel.mengedittf("pengajuan_inventaris","no_pengajuan=?","status='Disetujui'",1,new String[]{
                        tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                    })==true){
                        tampil();
                        emptTeks();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data terlebih dahulu..!!!!");
            }
        }
    }//GEN-LAST:event_ppDosetujuiBtnPrintActionPerformed

    private void ppDitolakBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDitolakBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        }else{
            if(tbObat.getSelectedRow()> -1){
                if(Sequel.mengedittf("pengajuan_inventaris","no_pengajuan=?","status='Ditolak'",1,new String[]{
                        tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                    })==true){
                        tampil();
                        emptTeks();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data terlebih dahulu..!!!!");
            }
        }
    }//GEN-LAST:event_ppDitolakBtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PengajuanInventaris dialog = new PengajuanInventaris(new javax.swing.JFrame(), true);
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
    private widget.TextBox Bidang;
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
    private widget.TextBox Departemen;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Harga;
    private widget.TextBox Jumlah;
    private widget.TextBox KdPetugas;
    private widget.TextBox KdPetugasPJ;
    private widget.TextBox Keterangan;
    private widget.Label LCount;
    private widget.Label LCount1;
    private widget.TextArea LatarBelakang;
    private widget.TextBox NamaBarang;
    private widget.TextBox NmPetugas;
    private widget.TextBox NmPetugasPJ;
    private widget.TextBox NoPengajuan;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextArea Spesifikasi;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private widget.TextBox Total;
    private widget.ComboBox Urgensi;
    private widget.Button btnPetugas;
    private widget.Button btnPetugasPJ;
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
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppDitolak;
    private javax.swing.JMenuItem ppDosetujui;
    private javax.swing.JMenuItem ppProses;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                   "select pengajuan_inventaris.no_pengajuan,pengajuan_inventaris.tanggal,pengajuan_inventaris.nik,peg1.nama as namapengaju,"+
                   "peg1.bidang,peg1.departemen,pengajuan_inventaris.urgensi,pengajuan_inventaris.latar_belakang,pengajuan_inventaris.nama_barang,"+
                   "pengajuan_inventaris.spesifikasi,pengajuan_inventaris.jumlah,pengajuan_inventaris.harga,pengajuan_inventaris.total,"+
                   "pengajuan_inventaris.keterangan,pengajuan_inventaris.nik_pj,peg2.nama as namapj,pengajuan_inventaris.status "+
                   "from pengajuan_inventaris inner join pegawai as peg1 inner join pegawai as peg2 on pengajuan_inventaris.nik=peg1.nik "+
                   "and pengajuan_inventaris.nik_pj=peg2.nik where "+
                   "pengajuan_inventaris.tanggal between ? and ? and pengajuan_inventaris.no_pengajuan like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and pengajuan_inventaris.nik like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and peg1.nama like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and peg1.bidang like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and peg1.departemen like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and pengajuan_inventaris.urgensi like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and pengajuan_inventaris.latar_belakang like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and pengajuan_inventaris.nama_barang like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and pengajuan_inventaris.spesifikasi like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and pengajuan_inventaris.keterangan like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and pengajuan_inventaris.nik_pj like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and peg2.nama like ? or "+
                   "pengajuan_inventaris.tanggal between ? and ? and pengajuan_inventaris.status like ? order by pengajuan_inventaris.tanggal");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                ps.setString(31,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(32,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(33,"%"+TCari.getText().trim()+"%");
                ps.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(36,"%"+TCari.getText().trim()+"%");
                ps.setString(37,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(38,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(39,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                i=1;
                total=0;
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_pengajuan"),rs.getString("tanggal"),rs.getString("nik"),rs.getString("namapengaju"),rs.getString("bidang"),
                        rs.getString("departemen"),rs.getString("urgensi"),rs.getString("latar_belakang"),rs.getString("nama_barang"),rs.getString("spesifikasi"),
                        rs.getString("jumlah"),Valid.SetAngka(rs.getDouble("harga")),Valid.SetAngka(rs.getDouble("total")),rs.getString("keterangan"),
                        rs.getString("nik_pj"),rs.getString("namapj"),rs.getString("status")
                    });
                    total=total+rs.getDouble("total");
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
        LCount1.setText(Valid.SetAngka(total));
    }

    private void emptTeks() {
        Tanggal.setDate(new Date());
        Spesifikasi.setText("");
        LatarBelakang.setText("");
        NamaBarang.setText("");
        Jumlah.setText("0");
        Harga.setText("0");
        Total.setText("0");
        Keterangan.setText("");
        KdPetugasPJ.setText("");
        NmPetugasPJ.setText("");
        autoNomor();
        NamaBarang.requestFocus();
    }


    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            NoPengajuan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            Valid.SetTgl(Tanggal,tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            KdPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            NmPetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Bidang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Departemen.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Urgensi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            LatarBelakang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NamaBarang.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Spesifikasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Jumlah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            Harga.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            Total.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Keterangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            KdPetugasPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            NmPetugasPJ.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
        }
    }

    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,205));
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
        BtnSimpan.setEnabled(akses.getpengajuan_asetinventaris());
        BtnHapus.setEnabled(akses.getpengajuan_asetinventaris());
        BtnEdit.setEnabled(akses.getpengajuan_asetinventaris());
        ppDitolak.setEnabled(akses.getpengajuan_asetinventaris());
        ppDosetujui.setEnabled(akses.getpengajuan_asetinventaris());
        ppProses.setEnabled(akses.getpengajuan_asetinventaris());
        if(akses.getjml2()>=1){
            KdPetugas.setEditable(false);
            btnPetugas.setEnabled(false);
            KdPetugas.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", NmPetugas,KdPetugas.getText());
        }  
    }
    
    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_pengajuan,3),signed)),0) from pengajuan_inventaris where tanggal='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",
                "PI"+Tanggal.getSelectedItem().toString().substring(6,10)+Tanggal.getSelectedItem().toString().substring(3,5)+Tanggal.getSelectedItem().toString().substring(0,2),3,NoPengajuan); 
    }
    
    private void isHitung(){
        if((!Harga.getText().equals(""))&&(!Jumlah.getText().equals(""))){
            Total.setText(Valid.SetAngka(Double.parseDouble(Harga.getText())*Double.parseDouble(Jumlah.getText())));
        }
    }
}

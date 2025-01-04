/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgPerawatan.java
 *
 * Created on May 23, 2010, 6:36:30 PM
 */

package rekammedis;

import kepegawaian.DlgCariPetugas;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.Cursor;

/**
 *
 * @author dosen
 */
public final class RMRekonsiliasiObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private PreparedStatement ps;
    private ResultSet rs;
    private int i,reply;
    

    /** Creates new form DlgPerawatan
     * @param parent
     * @param modal */
    public RMRekonsiliasiObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        DlgTambahObatRekonsiliasi.setSize(650,163);
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "Nama Obat","Dosis Obat","Frekuensi","Cara Pemberian/Aturan Pakai","Waktu Pemberian Terakhir","Tindak Lanjut","Perubahan Aturan Pakai"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPemeriksaan.setModel(tabMode);        
        
        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbPemeriksaan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPemeriksaan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for(i = 0; i < 7; i++) {
            TableColumn column = tbPemeriksaan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(150);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(80);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(140);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(200);
            }
        }
        tbPemeriksaan.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        NamaObat.setDocument(new batasInput((int)100).getKata(NamaObat));
        DosisObat.setDocument(new batasInput((byte)20).getKata(DosisObat));
        Frekuensi.setDocument(new batasInput((byte)10).getKata(Frekuensi));
        AturanPakai.setDocument(new batasInput((int)150).getKata(AturanPakai));
        PemberianTerakhir.setDocument(new batasInput((byte)20).getKata(PemberianTerakhir));
        PerubahanAturanPakai.setDocument(new batasInput((int)150).getKata(PerubahanAturanPakai));
        TNoRekonsialiasi.setDocument(new batasInput((byte)20).getKata(TNoRekonsialiasi));
        AlergiObat.setDocument(new batasInput((int)70).getKata(AlergiObat));
        ManifestasiAlergi.setDocument(new batasInput((int)70).getKata(ManifestasiAlergi));
        
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){ 
                    KodePetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());   
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
        
        ChkJln.setSelected(true);
        jam();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DlgTambahObatRekonsiliasi = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        jLabel99 = new widget.Label();
        BtnKeluarRekon = new widget.Button();
        BtnSimpanRekon = new widget.Button();
        NamaObat = new widget.TextBox();
        jLabel105 = new widget.Label();
        DosisObat = new widget.TextBox();
        jLabel108 = new widget.Label();
        TindakLanjut = new widget.ComboBox();
        jLabel109 = new widget.Label();
        Frekuensi = new widget.TextBox();
        jLabel111 = new widget.Label();
        AturanPakai = new widget.TextBox();
        jLabel112 = new widget.Label();
        PemberianTerakhir = new widget.TextBox();
        jLabel113 = new widget.Label();
        PerubahanAturanPakai = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        Scroll2 = new widget.ScrollPane();
        tbPemeriksaan = new widget.Table();
        panelGlass8 = new widget.panelisi();
        BtnTambah = new widget.Button();
        BtnHapus = new widget.Button();
        label9 = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        TNoRw = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TPasien = new widget.TextBox();
        jLabel9 = new widget.Label();
        Tanggal = new widget.Tanggal();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        KodePetugas = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        jLabel15 = new widget.Label();
        jLabel4 = new widget.Label();
        TNoRekonsialiasi = new widget.TextBox();
        jLabel5 = new widget.Label();
        RekonsiliasiSaat = new widget.ComboBox();
        jLabel55 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        AlergiObat = new widget.TextArea();
        jLabel56 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        ManifestasiAlergi = new widget.TextArea();
        jLabel6 = new widget.Label();
        DampakAlergi = new widget.ComboBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel11 = new widget.Label();
        Jk = new widget.TextBox();

        DlgTambahObatRekonsiliasi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgTambahObatRekonsiliasi.setName("DlgTambahObatRekonsiliasi"); // NOI18N
        DlgTambahObatRekonsiliasi.setUndecorated(true);
        DlgTambahObatRekonsiliasi.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Obat Yang Mau Direkonsiliasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        jLabel99.setText("Nama Obat :");
        jLabel99.setName("jLabel99"); // NOI18N
        panelBiasa2.add(jLabel99);
        jLabel99.setBounds(0, 10, 85, 23);

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
        BtnKeluarRekon.setBounds(520, 100, 100, 30);

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
        BtnSimpanRekon.setBounds(415, 100, 100, 30);

        NamaObat.setHighlighter(null);
        NamaObat.setName("NamaObat"); // NOI18N
        NamaObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NamaObatKeyPressed(evt);
            }
        });
        panelBiasa2.add(NamaObat);
        NamaObat.setBounds(89, 10, 532, 23);

        jLabel105.setText("Dosis Obat :");
        jLabel105.setName("jLabel105"); // NOI18N
        panelBiasa2.add(jLabel105);
        jLabel105.setBounds(0, 40, 85, 23);

        DosisObat.setHighlighter(null);
        DosisObat.setName("DosisObat"); // NOI18N
        DosisObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DosisObatKeyPressed(evt);
            }
        });
        panelBiasa2.add(DosisObat);
        DosisObat.setBounds(89, 40, 90, 23);

        jLabel108.setText("Tindak Lanjut :");
        jLabel108.setName("jLabel108"); // NOI18N
        panelBiasa2.add(jLabel108);
        jLabel108.setBounds(367, 70, 150, 23);

        TindakLanjut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Stop", "Lanjut" }));
        TindakLanjut.setName("TindakLanjut"); // NOI18N
        TindakLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutKeyPressed(evt);
            }
        });
        panelBiasa2.add(TindakLanjut);
        TindakLanjut.setBounds(521, 70, 100, 23);

        jLabel109.setText("Frekuensi :");
        jLabel109.setName("jLabel109"); // NOI18N
        panelBiasa2.add(jLabel109);
        jLabel109.setBounds(190, 40, 70, 23);

        Frekuensi.setHighlighter(null);
        Frekuensi.setName("Frekuensi"); // NOI18N
        Frekuensi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                FrekuensiKeyPressed(evt);
            }
        });
        panelBiasa2.add(Frekuensi);
        Frekuensi.setBounds(264, 40, 90, 23);

        jLabel111.setText("Aturan Pakai :");
        jLabel111.setName("jLabel111"); // NOI18N
        panelBiasa2.add(jLabel111);
        jLabel111.setBounds(0, 70, 85, 23);

        AturanPakai.setHighlighter(null);
        AturanPakai.setName("AturanPakai"); // NOI18N
        AturanPakai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AturanPakaiKeyPressed(evt);
            }
        });
        panelBiasa2.add(AturanPakai);
        AturanPakai.setBounds(89, 70, 260, 23);

        jLabel112.setText("Waktu Pemberian Terakhir :");
        jLabel112.setName("jLabel112"); // NOI18N
        panelBiasa2.add(jLabel112);
        jLabel112.setBounds(367, 40, 150, 23);

        PemberianTerakhir.setHighlighter(null);
        PemberianTerakhir.setName("PemberianTerakhir"); // NOI18N
        PemberianTerakhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PemberianTerakhirKeyPressed(evt);
            }
        });
        panelBiasa2.add(PemberianTerakhir);
        PemberianTerakhir.setBounds(521, 40, 100, 23);

        jLabel113.setText("Perubahan Aturan Pakai :");
        jLabel113.setName("jLabel113"); // NOI18N
        panelBiasa2.add(jLabel113);
        jLabel113.setBounds(0, 100, 140, 23);

        PerubahanAturanPakai.setHighlighter(null);
        PerubahanAturanPakai.setName("PerubahanAturanPakai"); // NOI18N
        PerubahanAturanPakai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PerubahanAturanPakaiKeyPressed(evt);
            }
        });
        panelBiasa2.add(PerubahanAturanPakai);
        PerubahanAturanPakai.setBounds(144, 100, 260, 23);

        internalFrame4.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgTambahObatRekonsiliasi.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekonsiliasi Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 102));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbPemeriksaan.setName("tbPemeriksaan"); // NOI18N
        Scroll2.setViewportView(tbPemeriksaan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.CENTER);

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnTambah.setMnemonic('T');
        BtnTambah.setText("Tambah");
        BtnTambah.setToolTipText("Alt+T");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        BtnTambah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnTambahKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnTambah);

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

        label9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass8.add(label9);

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
        BtnBatal.setText("Clear");
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

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('E');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+E");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(100, 30));
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
        panelGlass8.add(BtnCari);

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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setPreferredSize(new java.awt.Dimension(560, 185));
        PanelInput.setLayout(null);

        jLabel3.setText("No.Rawat :");
        jLabel3.setName("jLabel3"); // NOI18N
        PanelInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 94, 23);

        TNoRw.setEditable(false);
        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        PanelInput.add(TNoRw);
        TNoRw.setBounds(98, 12, 128, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        PanelInput.add(TNoRM);
        TNoRM.setBounds(228, 12, 100, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        PanelInput.add(TPasien);
        TPasien.setBounds(330, 12, 225, 23);

        jLabel9.setText("Petugas :");
        jLabel9.setName("jLabel9"); // NOI18N
        PanelInput.add(jLabel9);
        jLabel9.setBounds(197, 40, 69, 23);

        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "13-07-2023" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TanggalItemStateChanged(evt);
            }
        });
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        PanelInput.add(Tanggal);
        Tanggal.setBounds(395, 70, 90, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        PanelInput.add(CmbJam);
        CmbJam.setBounds(489, 70, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        PanelInput.add(CmbMenit);
        CmbMenit.setBounds(555, 70, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        PanelInput.add(CmbDetik);
        CmbDetik.setBounds(621, 70, 62, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        PanelInput.add(ChkJln);
        ChkJln.setBounds(687, 70, 23, 23);

        KodePetugas.setEditable(false);
        KodePetugas.setName("KodePetugas"); // NOI18N
        PanelInput.add(KodePetugas);
        KodePetugas.setBounds(270, 40, 128, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setHighlighter(null);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        PanelInput.add(NamaPetugas);
        NamaPetugas.setBounds(400, 40, 280, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('4');
        BtnPetugas.setToolTipText("ALt+4");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        PanelInput.add(BtnPetugas);
        BtnPetugas.setBounds(682, 40, 28, 23);

        jLabel15.setText("Tanggal Wawancara :");
        jLabel15.setName("jLabel15"); // NOI18N
        PanelInput.add(jLabel15);
        jLabel15.setBounds(261, 70, 130, 23);

        jLabel4.setText("No.Rekonsiliasi :");
        jLabel4.setName("jLabel4"); // NOI18N
        PanelInput.add(jLabel4);
        jLabel4.setBounds(0, 70, 94, 23);

        TNoRekonsialiasi.setHighlighter(null);
        TNoRekonsialiasi.setName("TNoRekonsialiasi"); // NOI18N
        TNoRekonsialiasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRekonsialiasiKeyPressed(evt);
            }
        });
        PanelInput.add(TNoRekonsialiasi);
        TNoRekonsialiasi.setBounds(98, 70, 150, 23);

        jLabel5.setText("Rekonsiliasi Saat :");
        jLabel5.setName("jLabel5"); // NOI18N
        PanelInput.add(jLabel5);
        jLabel5.setBounds(346, 150, 120, 23);

        RekonsiliasiSaat.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Admisi", "Transfer Antar Ruang", "Pindah Faskes Lain", "Pulang" }));
        RekonsiliasiSaat.setName("RekonsiliasiSaat"); // NOI18N
        RekonsiliasiSaat.setPreferredSize(new java.awt.Dimension(45, 23));
        RekonsiliasiSaat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RekonsiliasiSaatKeyPressed(evt);
            }
        });
        PanelInput.add(RekonsiliasiSaat);
        RekonsiliasiSaat.setBounds(470, 150, 160, 23);

        jLabel55.setText("Alergi Obat :");
        jLabel55.setName("jLabel55"); // NOI18N
        PanelInput.add(jLabel55);
        jLabel55.setBounds(0, 100, 94, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        AlergiObat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        AlergiObat.setColumns(20);
        AlergiObat.setRows(5);
        AlergiObat.setName("AlergiObat"); // NOI18N
        AlergiObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlergiObatKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(AlergiObat);

        PanelInput.add(scrollPane2);
        scrollPane2.setBounds(98, 100, 240, 43);

        jLabel56.setText("Manifestasi Alergi :");
        jLabel56.setName("jLabel56"); // NOI18N
        PanelInput.add(jLabel56);
        jLabel56.setBounds(346, 100, 120, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        ManifestasiAlergi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        ManifestasiAlergi.setColumns(20);
        ManifestasiAlergi.setRows(5);
        ManifestasiAlergi.setName("ManifestasiAlergi"); // NOI18N
        ManifestasiAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ManifestasiAlergiKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(ManifestasiAlergi);

        PanelInput.add(scrollPane3);
        scrollPane3.setBounds(470, 100, 240, 43);

        jLabel6.setText("Dampak Alergi :");
        jLabel6.setName("jLabel6"); // NOI18N
        PanelInput.add(jLabel6);
        jLabel6.setBounds(0, 150, 94, 23);

        DampakAlergi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ringan", "Sedang", "Berat" }));
        DampakAlergi.setName("DampakAlergi"); // NOI18N
        DampakAlergi.setPreferredSize(new java.awt.Dimension(45, 23));
        DampakAlergi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DampakAlergiKeyPressed(evt);
            }
        });
        PanelInput.add(DampakAlergi);
        DampakAlergi.setBounds(98, 150, 90, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        PanelInput.add(jLabel8);
        jLabel8.setBounds(566, 10, 60, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        PanelInput.add(TglLahir);
        TglLahir.setBounds(630, 10, 80, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        PanelInput.add(jLabel11);
        jLabel11.setBounds(0, 40, 94, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        PanelInput.add(Jk);
        Jk.setBounds(98, 40, 80, 23);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt, TNoRekonsialiasi, AlergiObat);
    }//GEN-LAST:event_TanggalKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void TNoRekonsialiasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRekonsialiasiKeyPressed
        Valid.pindah(evt, BtnPetugas, AlergiObat);
    }//GEN-LAST:event_TNoRekonsialiasiKeyPressed

    private void TanggalItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TanggalItemStateChanged
        try {
            autoNomor();
        } catch (Exception e) {
        }
    }//GEN-LAST:event_TanggalItemStateChanged

    private void RekonsiliasiSaatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RekonsiliasiSaatKeyPressed
        Valid.pindah2(evt, DampakAlergi, BtnTambah);
    }//GEN-LAST:event_RekonsiliasiSaatKeyPressed

    private void AlergiObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlergiObatKeyPressed
        Valid.pindah2(evt, TNoRekonsialiasi, ManifestasiAlergi);
    }//GEN-LAST:event_AlergiObatKeyPressed

    private void ManifestasiAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ManifestasiAlergiKeyPressed
        Valid.pindah2(evt, AlergiObat, DampakAlergi);
    }//GEN-LAST:event_ManifestasiAlergiKeyPressed

    private void DampakAlergiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DampakAlergiKeyPressed
        Valid.pindah(evt, ManifestasiAlergi, RekonsiliasiSaat);
    }//GEN-LAST:event_DampakAlergiKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        if(TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pilih terlebih dahulu pasien yang mau dimasukkan data rekonsiliasinya...");
            BtnTambah.requestFocus();
        }else{
            DlgTambahObatRekonsiliasi.setLocationRelativeTo(internalFrame1);
            DlgTambahObatRekonsiliasi.setVisible(true);
            emptTeksTambahRekon();
        }
    }//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTambahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnTambahActionPerformed(null);
        }else{
            Valid.pindah(evt,RekonsiliasiSaat,BtnBatal);
        }
    }//GEN-LAST:event_BtnTambahKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbPemeriksaan.getSelectedRow()>-1){
            tabMode.removeRow(tbPemeriksaan.getSelectedRow());
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
        }   
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnTambah, BtnCari);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        DlgTambahObatRekonsiliasi.dispose();
        petugas.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnCari,BtnTambah);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));  
        RMCariRekonsiliasiObat form=new RMCariRekonsiliasiObat(null,false);
        form.isCek();
        form.SetNoRw(TNoRw.getText());
        form.setSize(this.getWidth(),this.getHeight());
        form.setLocationRelativeTo(this);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        Valid.pindah(evt, BtnBatal, BtnKeluar);
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        Valid.tabelKosong(tabMode);
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Pasien");
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");
        }else if(TNoRekonsialiasi.getText().trim().equals("")||TNoRekonsialiasi.getText().trim().equals("-")){
            Valid.textKosong(TNoRekonsialiasi,"No.Rekonsiliasi");
        }else if(tabMode.getRowCount()==0){
            Valid.textKosong(TNoRekonsialiasi,"Data Obat Rekonsiliasi");
        }else{
            reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                if(Sequel.menyimpantf("rekonsiliasi_obat","?,?,?,?,?,?,?,?","No.Rawat",8,new String[]{
                        TNoRekonsialiasi.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+"")+" "+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(), 
                        RekonsiliasiSaat.getSelectedItem().toString(),AlergiObat.getText(),ManifestasiAlergi.getText(),DampakAlergi.getSelectedItem().toString(),KodePetugas.getText(),
                    })==true){
                        for (i = 0; i < tbPemeriksaan.getRowCount(); i++) {
                           Sequel.menyimpan2("rekonsiliasi_obat_detail_obat","?,?,?,?,?,?,?,?",8,new String[]{
                               TNoRekonsialiasi.getText(),tbPemeriksaan.getValueAt(i,0).toString(),tbPemeriksaan.getValueAt(i,1).toString(),tbPemeriksaan.getValueAt(i,2).toString(),
                               tbPemeriksaan.getValueAt(i,3).toString(),tbPemeriksaan.getValueAt(i,4).toString(),tbPemeriksaan.getValueAt(i,5).toString(),tbPemeriksaan.getValueAt(i,6).toString()
                           });
                        }
                        Valid.tabelKosong(tabMode);
                        emptTeks();
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{Valid.pindah(evt, BtnHapus, BtnBatal);}
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnKeluarRekonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarRekonActionPerformed
        DlgTambahObatRekonsiliasi.dispose();
    }//GEN-LAST:event_BtnKeluarRekonActionPerformed

    private void BtnSimpanRekonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanRekonActionPerformed
        if(NamaObat.getText().trim().equals("")){
            Valid.textKosong(NamaObat,"Nama Obat");
        }else if(DosisObat.getText().trim().equals("")){
            Valid.textKosong(DosisObat,"Dosis Obat");
        }else if(Frekuensi.getText().trim().equals("")){
            Valid.textKosong(Frekuensi,"Frekuensi");
        }else if(AturanPakai.getText().trim().equals("")){
            Valid.textKosong(AturanPakai,"Aturan Pakai");
        }else if(PemberianTerakhir.getText().trim().equals("")){
            Valid.textKosong(PemberianTerakhir,"Waktu Pemberian Terakhir");
        }else{
            tabMode.addRow(new String[]{
                NamaObat.getText(),DosisObat.getText(),Frekuensi.getText(),AturanPakai.getText(),PemberianTerakhir.getText(),TindakLanjut.getSelectedItem().toString(),PerubahanAturanPakai.getText()
            });
            emptTeksTambahRekon();
        }
    }//GEN-LAST:event_BtnSimpanRekonActionPerformed

    private void NamaObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NamaObatKeyPressed
        Valid.pindah(evt,BtnKeluarRekon,DosisObat);
    }//GEN-LAST:event_NamaObatKeyPressed

    private void DosisObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DosisObatKeyPressed
        Valid.pindah(evt,NamaObat,Frekuensi);
    }//GEN-LAST:event_DosisObatKeyPressed

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutKeyPressed
        Valid.pindah(evt,AturanPakai,PerubahanAturanPakai);
    }//GEN-LAST:event_TindakLanjutKeyPressed

    private void FrekuensiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_FrekuensiKeyPressed
        Valid.pindah(evt,DosisObat,PemberianTerakhir);
    }//GEN-LAST:event_FrekuensiKeyPressed

    private void AturanPakaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AturanPakaiKeyPressed
        Valid.pindah(evt,PemberianTerakhir,TindakLanjut);
    }//GEN-LAST:event_AturanPakaiKeyPressed

    private void PemberianTerakhirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PemberianTerakhirKeyPressed
        Valid.pindah(evt,Frekuensi,AturanPakai);
    }//GEN-LAST:event_PemberianTerakhirKeyPressed

    private void PerubahanAturanPakaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PerubahanAturanPakaiKeyPressed
        Valid.pindah(evt,TindakLanjut,BtnSimpanRekon);
    }//GEN-LAST:event_PerubahanAturanPakaiKeyPressed

    private void BtnSimpanRekonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanRekonKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanRekonActionPerformed(null);
        }else{
            Valid.pindah(evt,PerubahanAturanPakai,BtnKeluarRekon);
        }
    }//GEN-LAST:event_BtnSimpanRekonKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMRekonsiliasiObat dialog = new RMRekonsiliasiObat(new javax.swing.JFrame(), true);
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
    private widget.TextArea AlergiObat;
    private widget.TextBox AturanPakai;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarRekon;
    private widget.Button BtnPetugas;
    private widget.Button BtnSimpan;
    private widget.Button BtnSimpanRekon;
    private widget.Button BtnTambah;
    private widget.CekBox ChkJln;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.ComboBox DampakAlergi;
    private javax.swing.JDialog DlgTambahObatRekonsiliasi;
    private widget.TextBox DosisObat;
    private widget.TextBox Frekuensi;
    private widget.TextBox Jk;
    private widget.TextBox KodePetugas;
    private widget.TextArea ManifestasiAlergi;
    private widget.TextBox NamaObat;
    private widget.TextBox NamaPetugas;
    private widget.PanelBiasa PanelInput;
    private widget.TextBox PemberianTerakhir;
    private widget.TextBox PerubahanAturanPakai;
    private widget.ComboBox RekonsiliasiSaat;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRekonsialiasi;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.Tanggal Tanggal;
    private widget.TextBox TglLahir;
    private widget.ComboBox TindakLanjut;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel105;
    private widget.Label jLabel108;
    private widget.Label jLabel109;
    private widget.Label jLabel11;
    private widget.Label jLabel111;
    private widget.Label jLabel112;
    private widget.Label jLabel113;
    private widget.Label jLabel15;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel5;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel6;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private widget.Label label9;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbPemeriksaan;
    // End of variables declaration//GEN-END:variables

    public void emptTeks() {
        Tanggal.setDate(new Date());
        AlergiObat.setText("");
        ManifestasiAlergi.setText("");
        DampakAlergi.setSelectedIndex(0);
        
        autoNomor();
        TNoRekonsialiasi.requestFocus();
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =CmbJam.getSelectedIndex();
                    nilai_menit =CmbMenit.getSelectedIndex();
                    nilai_detik =CmbDetik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    } 
    
    public void setNoRm(String norwt) {
        TNoRw.setText(norwt);   
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,reg_periksa.tgl_registrasi "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
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
    
    public void isCek(){        
        BtnSimpan.setEnabled(akses.getrekonsiliasi_obat());
        if(akses.getjml2()>=1){
            KodePetugas.setEditable(false);
            BtnPetugas.setEnabled(false);
            KodePetugas.setText(akses.getkode());
            NamaPetugas.setText(petugas.tampil3(KodePetugas.getText()));
            if(NamaPetugas.getText().equals("")){
                KodePetugas.setText("");
                JOptionPane.showMessageDialog(null,"User login bukan petugas...!!");
            }
        } 
    }

    private void autoNomor() {
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rekonsiliasi_obat.no_rekonsiliasi,4),signed)),0) from rekonsiliasi_obat where left(rekonsiliasi_obat.tanggal_wawancara,10)='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ","RO"+Valid.SetTgl(Tanggal.getSelectedItem()+"").replaceAll("-",""),4,TNoRekonsialiasi);           
    }

    private void emptTeksTambahRekon() {
        NamaObat.setText("");
        DosisObat.setText("");
        Frekuensi.setText("");
        PemberianTerakhir.setText("");
        AturanPakai.setText("");
        PerubahanAturanPakai.setText("");
        TindakLanjut.setSelectedIndex(0);
        NamaObat.requestFocus();
    }

}

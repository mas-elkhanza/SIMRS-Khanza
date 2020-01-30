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
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPetugas;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


/**
 *
 * @author perpustakaan
 */
public final class DlgDataInsidenKeselamatan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;    
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private DlgInsidenKeselamatan insiden=new DlgInsidenKeselamatan(null,false);
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public DlgDataInsidenKeselamatan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);


        Object[] row={
            "No.Rawat","No.R.M.","Nama Pasien","Umur","Tgl.Kejadian","Jam Kejadian",
            "Tgl.Lapor","Jam Lapor","NIP","Petugas","Lokasi Insiden","Kode Insiden",
            "Nama Insiden","Jenis Insiden","Dampak/Severity","Unit Terkait","Kronologis","Akibat",
            "Tindakan Saat Insiden","Identifikasi Masalah","Rekomendasi & Tindak Lanjut"
        };
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(40);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(70);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(80);
            }else if(i==9){
                column.setPreferredWidth(150);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(300);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(140);
            }else if(i==15){
                column.setPreferredWidth(140);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(150);
            }else if(i==19){
                column.setPreferredWidth(150);
            }else if(i==20){
                column.setPreferredWidth(150);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        nip.setDocument(new batasInput((byte)20).getKata(nip));
        KodeInsiden.setDocument(new batasInput((byte)5).getKata(KodeInsiden));
        Lokasi.setDocument(new batasInput((int)60).getKata(Lokasi));
        UnitTerkait.setDocument(new batasInput((int)60).getKata(UnitTerkait));
        Akibat.setDocument(new batasInput((int)150).getKata(Akibat));
        Tindakan.setDocument(new batasInput((int)150).getKata(Tindakan));
        Identifikasi.setDocument(new batasInput((int)150).getKata(Identifikasi));
        TindakLanjut.setDocument(new batasInput((int)150).getKata(TindakLanjut));
        Kronologis.setDocument(new batasInput((int)200).getKata(Kronologis));
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
                    nip.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    namapetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                nip.requestFocus();
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
        
        insiden.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(insiden.getTabel().getSelectedRow()!= -1){  
                    KodeInsiden.setText(insiden.getTabel().getValueAt(insiden.getTabel().getSelectedRow(),1).toString());
                    NmInsiden.setText(insiden.getTabel().getValueAt(insiden.getTabel().getSelectedRow(),2).toString());
                    Jenis.setText(insiden.getTabel().getValueAt(insiden.getTabel().getSelectedRow(),3).toString());
                    Dampak.setText(insiden.getTabel().getValueAt(insiden.getTabel().getSelectedRow(),4).toString());
                }  
                KodeInsiden.requestFocus();
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
        
        insiden.getTabel().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    insiden.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        ChkInput.setSelected(false);
        isForm();
        
        jam();
        jam2();
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
        ppGrafikBatangKejadianIKPPerDampak = new javax.swing.JMenuItem();
        ppGrafikPieKejadianIKPPerDampak = new javax.swing.JMenuItem();
        ppGrafikBatangKejadianIKPPerJenis = new javax.swing.JMenuItem();
        ppGrafikPieKejadianIKPPerJenis = new javax.swing.JMenuItem();
        ppGrafikBatangKejadianIKPPerTanggal = new javax.swing.JMenuItem();
        ppGrafikBatangKejadianIKPPerbulan = new javax.swing.JMenuItem();
        ppGrafikBatangKejadianIKPPerTahun = new javax.swing.JMenuItem();
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
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        Kejadian = new widget.Tanggal();
        TNoRM = new widget.TextBox();
        jLabel16 = new widget.Label();
        Lokasi = new widget.TextBox();
        jLabel23 = new widget.Label();
        JamKejadian = new widget.ComboBox();
        MenitKejadian = new widget.ComboBox();
        DetikKejadian = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        jLabel17 = new widget.Label();
        Lapor = new widget.Tanggal();
        JamLapor = new widget.ComboBox();
        MenitLapor = new widget.ComboBox();
        DetikLapor = new widget.ComboBox();
        ChkLapor = new widget.CekBox();
        jLabel18 = new widget.Label();
        nip = new widget.TextBox();
        namapetugas = new widget.TextBox();
        btnPetugas = new widget.Button();
        jLabel24 = new widget.Label();
        UnitTerkait = new widget.TextBox();
        jLabel25 = new widget.Label();
        Akibat = new widget.TextBox();
        btnInsiden = new widget.Button();
        NmInsiden = new widget.TextBox();
        KodeInsiden = new widget.TextBox();
        jLabel20 = new widget.Label();
        Dampak = new widget.TextBox();
        Jenis = new widget.TextBox();
        jLabel26 = new widget.Label();
        Tindakan = new widget.TextBox();
        jLabel27 = new widget.Label();
        Identifikasi = new widget.TextBox();
        jLabel28 = new widget.Label();
        TindakLanjut = new widget.TextBox();
        Kronologis = new widget.TextBox();
        jLabel29 = new widget.Label();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppGrafikBatangKejadianIKPPerDampak.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangKejadianIKPPerDampak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangKejadianIKPPerDampak.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikBatangKejadianIKPPerDampak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangKejadianIKPPerDampak.setText("Grafik Batang Kejadian IKP Per Dampak");
        ppGrafikBatangKejadianIKPPerDampak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangKejadianIKPPerDampak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangKejadianIKPPerDampak.setName("ppGrafikBatangKejadianIKPPerDampak"); // NOI18N
        ppGrafikBatangKejadianIKPPerDampak.setPreferredSize(new java.awt.Dimension(280, 26));
        ppGrafikBatangKejadianIKPPerDampak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangKejadianIKPPerDampakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangKejadianIKPPerDampak);

        ppGrafikPieKejadianIKPPerDampak.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPieKejadianIKPPerDampak.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPieKejadianIKPPerDampak.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPieKejadianIKPPerDampak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPieKejadianIKPPerDampak.setText("Grafik Pie Kejadian IKP Per Dampak");
        ppGrafikPieKejadianIKPPerDampak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPieKejadianIKPPerDampak.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPieKejadianIKPPerDampak.setName("ppGrafikPieKejadianIKPPerDampak"); // NOI18N
        ppGrafikPieKejadianIKPPerDampak.setPreferredSize(new java.awt.Dimension(280, 26));
        ppGrafikPieKejadianIKPPerDampak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPieKejadianIKPPerDampakActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPieKejadianIKPPerDampak);

        ppGrafikBatangKejadianIKPPerJenis.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangKejadianIKPPerJenis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangKejadianIKPPerJenis.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikBatangKejadianIKPPerJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangKejadianIKPPerJenis.setText("Grafik Batang Kejadian IKP Per Jenis");
        ppGrafikBatangKejadianIKPPerJenis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangKejadianIKPPerJenis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangKejadianIKPPerJenis.setName("ppGrafikBatangKejadianIKPPerJenis"); // NOI18N
        ppGrafikBatangKejadianIKPPerJenis.setPreferredSize(new java.awt.Dimension(280, 26));
        ppGrafikBatangKejadianIKPPerJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangKejadianIKPPerJenisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangKejadianIKPPerJenis);

        ppGrafikPieKejadianIKPPerJenis.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPieKejadianIKPPerJenis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPieKejadianIKPPerJenis.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikPieKejadianIKPPerJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPieKejadianIKPPerJenis.setText("Grafik Pie Kejadian IKP Per Jenis");
        ppGrafikPieKejadianIKPPerJenis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPieKejadianIKPPerJenis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPieKejadianIKPPerJenis.setName("ppGrafikPieKejadianIKPPerJenis"); // NOI18N
        ppGrafikPieKejadianIKPPerJenis.setPreferredSize(new java.awt.Dimension(280, 26));
        ppGrafikPieKejadianIKPPerJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPieKejadianIKPPerJenisActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPieKejadianIKPPerJenis);

        ppGrafikBatangKejadianIKPPerTanggal.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangKejadianIKPPerTanggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangKejadianIKPPerTanggal.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikBatangKejadianIKPPerTanggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangKejadianIKPPerTanggal.setText("Grafik Batang Kejadian IKP Per Tanggal");
        ppGrafikBatangKejadianIKPPerTanggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangKejadianIKPPerTanggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangKejadianIKPPerTanggal.setName("ppGrafikBatangKejadianIKPPerTanggal"); // NOI18N
        ppGrafikBatangKejadianIKPPerTanggal.setPreferredSize(new java.awt.Dimension(280, 26));
        ppGrafikBatangKejadianIKPPerTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangKejadianIKPPerTanggalActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangKejadianIKPPerTanggal);

        ppGrafikBatangKejadianIKPPerbulan.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangKejadianIKPPerbulan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangKejadianIKPPerbulan.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikBatangKejadianIKPPerbulan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangKejadianIKPPerbulan.setText("Grafik Batang Kejadian IKP Per Bulan");
        ppGrafikBatangKejadianIKPPerbulan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangKejadianIKPPerbulan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangKejadianIKPPerbulan.setName("ppGrafikBatangKejadianIKPPerbulan"); // NOI18N
        ppGrafikBatangKejadianIKPPerbulan.setPreferredSize(new java.awt.Dimension(280, 26));
        ppGrafikBatangKejadianIKPPerbulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangKejadianIKPPerbulanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangKejadianIKPPerbulan);

        ppGrafikBatangKejadianIKPPerTahun.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangKejadianIKPPerTahun.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangKejadianIKPPerTahun.setForeground(new java.awt.Color(50, 50, 50));
        ppGrafikBatangKejadianIKPPerTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangKejadianIKPPerTahun.setText("Grafik Batang Kejadian IKP Per Tahun");
        ppGrafikBatangKejadianIKPPerTahun.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangKejadianIKPPerTahun.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangKejadianIKPPerTahun.setName("ppGrafikBatangKejadianIKPPerTahun"); // NOI18N
        ppGrafikBatangKejadianIKPPerTahun.setPreferredSize(new java.awt.Dimension(280, 26));
        ppGrafikBatangKejadianIKPPerTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangKejadianIKPPerTahunActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangKejadianIKPPerTahun);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Insiden Keselamatan Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
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

        jLabel19.setText("Kejadian :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-09-2019" }));
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
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-09-2019" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
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
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setBackground(new java.awt.Color(250, 255, 245));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 225));
        FormInput.setLayout(null);

        jLabel4.setText("No.Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 100, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(104, 10, 141, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        TPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPasienKeyPressed(evt);
            }
        });
        FormInput.add(TPasien);
        TPasien.setBounds(361, 10, 424, 23);

        Kejadian.setForeground(new java.awt.Color(50, 70, 50));
        Kejadian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-09-2019" }));
        Kejadian.setDisplayFormat("dd-MM-yyyy");
        Kejadian.setName("Kejadian"); // NOI18N
        Kejadian.setOpaque(false);
        Kejadian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KejadianKeyPressed(evt);
            }
        });
        FormInput.add(Kejadian);
        Kejadian.setBounds(104, 40, 90, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(247, 10, 112, 23);

        jLabel16.setText("Kejadian :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 40, 100, 23);

        Lokasi.setHighlighter(null);
        Lokasi.setName("Lokasi"); // NOI18N
        Lokasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LokasiKeyPressed(evt);
            }
        });
        FormInput.add(Lokasi);
        Lokasi.setBounds(465, 70, 320, 23);

        jLabel23.setText("Lokasi :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(401, 70, 60, 23);

        JamKejadian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        JamKejadian.setName("JamKejadian"); // NOI18N
        JamKejadian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamKejadianKeyPressed(evt);
            }
        });
        FormInput.add(JamKejadian);
        JamKejadian.setBounds(197, 40, 62, 23);

        MenitKejadian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        MenitKejadian.setName("MenitKejadian"); // NOI18N
        MenitKejadian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitKejadianKeyPressed(evt);
            }
        });
        FormInput.add(MenitKejadian);
        MenitKejadian.setBounds(262, 40, 62, 23);

        DetikKejadian.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        DetikKejadian.setName("DetikKejadian"); // NOI18N
        DetikKejadian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikKejadianKeyPressed(evt);
            }
        });
        FormInput.add(DetikKejadian);
        DetikKejadian.setBounds(327, 40, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        FormInput.add(ChkKejadian);
        ChkKejadian.setBounds(392, 40, 23, 23);

        jLabel17.setText("Lapor :");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setVerifyInputWhenFocusTarget(false);
        FormInput.add(jLabel17);
        jLabel17.setBounds(420, 40, 50, 23);

        Lapor.setForeground(new java.awt.Color(50, 70, 50));
        Lapor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "04-09-2019" }));
        Lapor.setDisplayFormat("dd-MM-yyyy");
        Lapor.setName("Lapor"); // NOI18N
        Lapor.setOpaque(false);
        Lapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LaporKeyPressed(evt);
            }
        });
        FormInput.add(Lapor);
        Lapor.setBounds(474, 40, 90, 23);

        JamLapor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        JamLapor.setName("JamLapor"); // NOI18N
        JamLapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamLaporKeyPressed(evt);
            }
        });
        FormInput.add(JamLapor);
        JamLapor.setBounds(567, 40, 62, 23);

        MenitLapor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        MenitLapor.setName("MenitLapor"); // NOI18N
        MenitLapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitLaporKeyPressed(evt);
            }
        });
        FormInput.add(MenitLapor);
        MenitLapor.setBounds(632, 40, 62, 23);

        DetikLapor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        DetikLapor.setName("DetikLapor"); // NOI18N
        DetikLapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikLaporKeyPressed(evt);
            }
        });
        FormInput.add(DetikLapor);
        DetikLapor.setBounds(697, 40, 62, 23);

        ChkLapor.setBorder(null);
        ChkLapor.setSelected(true);
        ChkLapor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkLapor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkLapor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkLapor.setName("ChkLapor"); // NOI18N
        FormInput.add(ChkLapor);
        ChkLapor.setBounds(762, 40, 23, 23);

        jLabel18.setText("Petugas :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(0, 70, 100, 23);

        nip.setEditable(false);
        nip.setHighlighter(null);
        nip.setName("nip"); // NOI18N
        nip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                nipKeyPressed(evt);
            }
        });
        FormInput.add(nip);
        nip.setBounds(104, 70, 70, 23);

        namapetugas.setEditable(false);
        namapetugas.setName("namapetugas"); // NOI18N
        FormInput.add(namapetugas);
        namapetugas.setBounds(176, 70, 184, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("ALt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        FormInput.add(btnPetugas);
        btnPetugas.setBounds(362, 70, 28, 23);

        jLabel24.setText("Unit Terkait :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(0, 130, 100, 23);

        UnitTerkait.setHighlighter(null);
        UnitTerkait.setName("UnitTerkait"); // NOI18N
        UnitTerkait.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                UnitTerkaitKeyPressed(evt);
            }
        });
        FormInput.add(UnitTerkait);
        UnitTerkait.setBounds(104, 130, 280, 23);

        jLabel25.setText("Akibat :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(390, 160, 91, 23);

        Akibat.setHighlighter(null);
        Akibat.setName("Akibat"); // NOI18N
        Akibat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkibatKeyPressed(evt);
            }
        });
        FormInput.add(Akibat);
        Akibat.setBounds(485, 160, 300, 23);

        btnInsiden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnInsiden.setMnemonic('2');
        btnInsiden.setToolTipText("ALt+2");
        btnInsiden.setName("btnInsiden"); // NOI18N
        btnInsiden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsidenActionPerformed(evt);
            }
        });
        FormInput.add(btnInsiden);
        btnInsiden.setBounds(757, 100, 28, 23);

        NmInsiden.setEditable(false);
        NmInsiden.setName("NmInsiden"); // NOI18N
        FormInput.add(NmInsiden);
        NmInsiden.setBounds(176, 100, 349, 23);

        KodeInsiden.setEditable(false);
        KodeInsiden.setHighlighter(null);
        KodeInsiden.setName("KodeInsiden"); // NOI18N
        KodeInsiden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeInsidenKeyPressed(evt);
            }
        });
        FormInput.add(KodeInsiden);
        KodeInsiden.setBounds(104, 100, 70, 23);

        jLabel20.setText("Insiden :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 100, 100, 23);

        Dampak.setEditable(false);
        Dampak.setName("Dampak"); // NOI18N
        FormInput.add(Dampak);
        Dampak.setBounds(619, 100, 135, 23);

        Jenis.setEditable(false);
        Jenis.setName("Jenis"); // NOI18N
        FormInput.add(Jenis);
        Jenis.setBounds(527, 100, 90, 23);

        jLabel26.setText("Identifikasi :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(0, 190, 100, 23);

        Tindakan.setHighlighter(null);
        Tindakan.setName("Tindakan"); // NOI18N
        Tindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanKeyPressed(evt);
            }
        });
        FormInput.add(Tindakan);
        Tindakan.setBounds(104, 160, 280, 23);

        jLabel27.setText("Tindakan Insiden :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 160, 100, 23);

        Identifikasi.setHighlighter(null);
        Identifikasi.setName("Identifikasi"); // NOI18N
        Identifikasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IdentifikasiKeyPressed(evt);
            }
        });
        FormInput.add(Identifikasi);
        Identifikasi.setBounds(104, 190, 280, 23);

        jLabel28.setText("Tindak Lanjut :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(390, 190, 91, 23);

        TindakLanjut.setHighlighter(null);
        TindakLanjut.setName("TindakLanjut"); // NOI18N
        TindakLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutKeyPressed(evt);
            }
        });
        FormInput.add(TindakLanjut);
        TindakLanjut.setBounds(485, 190, 300, 23);

        Kronologis.setHighlighter(null);
        Kronologis.setName("Kronologis"); // NOI18N
        Kronologis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KronologisKeyPressed(evt);
            }
        });
        FormInput.add(Kronologis);
        Kronologis.setBounds(485, 130, 300, 23);

        jLabel29.setText("Kronologis :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(390, 130, 91, 23);

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

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isRawat();
            isPsien();
        }else{            
            Valid.pindah(evt,TCari,Kejadian);
        }
}//GEN-LAST:event_TNoRwKeyPressed

    private void TPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPasienKeyPressed
        Valid.pindah(evt,TCari,BtnSimpan);
}//GEN-LAST:event_TPasienKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"pasien");
        }else if(nip.getText().trim().equals("")||namapetugas.getText().trim().equals("")){
            Valid.textKosong(nip,"Petugas");
        }else if(KodeInsiden.getText().trim().equals("")||NmInsiden.getText().trim().equals("")){
            Valid.textKosong(KodeInsiden,"Insiden");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Lokasi");
        }else if(UnitTerkait.getText().trim().equals("")){
            Valid.textKosong(UnitTerkait,"Unit Terkait");
        }else if(Akibat.getText().trim().equals("")){
            Valid.textKosong(Akibat,"Akibat");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan saat insiden");
        }else if(Identifikasi.getText().trim().equals("")){
            Valid.textKosong(Identifikasi,"Identifikasi Masalah");
        }else if(TindakLanjut.getText().trim().equals("")){
            Valid.textKosong(TindakLanjut,"Rekomendasi & Tindak Lanjut");
        }else if(Kronologis.getText().trim().equals("")){
            Valid.textKosong(Kronologis,"Kronologis");
        }else{
            if(Sequel.menyimpantf("insiden_keselamatan_pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?","Data",14,new String[]{
                TNoRw.getText(),Valid.SetTgl(Kejadian.getSelectedItem()+""),JamKejadian.getSelectedItem()+":"+MenitKejadian.getSelectedItem()+":"+DetikKejadian.getSelectedItem(),
                Valid.SetTgl(Lapor.getSelectedItem()+""),JamLapor.getSelectedItem()+":"+MenitLapor.getSelectedItem()+":"+DetikLapor.getSelectedItem(),KodeInsiden.getText(),
                nip.getText(),Lokasi.getText(),Kronologis.getText(),UnitTerkait.getText(),Akibat.getText(),Tindakan.getText(),Identifikasi.getText(),TindakLanjut.getText()
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
            Valid.pindah(evt,TindakLanjut,BtnBatal);
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
            if(Sequel.queryu2tf("delete from insiden_keselamatan_pasien where tgl_kejadian=? and jam_kejadian=? and no_rawat=?",3,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),4).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
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
        }else if(nip.getText().trim().equals("")||namapetugas.getText().trim().equals("")){
            Valid.textKosong(nip,"Petugas");
        }else if(KodeInsiden.getText().trim().equals("")||NmInsiden.getText().trim().equals("")){
            Valid.textKosong(KodeInsiden,"Insiden");
        }else if(Lokasi.getText().trim().equals("")){
            Valid.textKosong(Lokasi,"Lokasi");
        }else if(UnitTerkait.getText().trim().equals("")){
            Valid.textKosong(UnitTerkait,"Unit Terkait");
        }else if(Akibat.getText().trim().equals("")){
            Valid.textKosong(Akibat,"Akibat");
        }else if(Tindakan.getText().trim().equals("")){
            Valid.textKosong(Tindakan,"Tindakan saat insiden");
        }else if(Identifikasi.getText().trim().equals("")){
            Valid.textKosong(Identifikasi,"Identifikasi Masalah");
        }else if(TindakLanjut.getText().trim().equals("")){
            Valid.textKosong(TindakLanjut,"Rekomendasi & Tindak Lanjut");
        }else if(Kronologis.getText().trim().equals("")){
            Valid.textKosong(Kronologis,"Kronologis");
        }else{         
            Sequel.mengedit("insiden_keselamatan_pasien","tgl_kejadian=? and jam_kejadian=? and no_rawat=?","no_rawat=?,tgl_kejadian=?,jam_kejadian=?,tgl_lapor=?,jam_lapor=?,kode_insiden=?,nip=?,lokasi=?,unit_terkait=?,akibat=?,tindakan_insiden=?,identifikasi_masalah=?,rtl=?,kronologis=?",17,new String[]{
                TNoRw.getText(),Valid.SetTgl(Kejadian.getSelectedItem()+""),JamKejadian.getSelectedItem()+":"+MenitKejadian.getSelectedItem()+":"+DetikKejadian.getSelectedItem(),
                Valid.SetTgl(Lapor.getSelectedItem()+""),JamLapor.getSelectedItem()+":"+MenitLapor.getSelectedItem()+":"+DetikLapor.getSelectedItem(),KodeInsiden.getText(),
                nip.getText(),Lokasi.getText(),UnitTerkait.getText(),Akibat.getText(),Tindakan.getText(),Identifikasi.getText(),TindakLanjut.getText(),Kronologis.getText(),
                tbObat.getValueAt(tbObat.getSelectedRow(),4).toString(),tbObat.getValueAt(tbObat.getSelectedRow(),5).toString(),
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
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
        petugas.dispose();
        insiden.dispose();
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
                param.put("tanggal1",Valid.SetTgl(DTPCari1.getSelectedItem()+""));   
                param.put("tanggal2",Valid.SetTgl(DTPCari2.getSelectedItem()+""));   
                param.put("parameter","%"+TCari.getText().trim()+"%");   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptDataInsidenKeselamatanPasien.jasper",param,"::[ Data Insiden Keselamatan Pasien ]::");
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

    private void KejadianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KejadianKeyPressed
        Valid.pindah(evt,TCari,JamKejadian);
}//GEN-LAST:event_KejadianKeyPressed

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

    private void LokasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LokasiKeyPressed
        Valid.pindah(evt,nip,KodeInsiden);
    }//GEN-LAST:event_LokasiKeyPressed

    private void JamKejadianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamKejadianKeyPressed
        Valid.pindah(evt,Kejadian,MenitKejadian);
    }//GEN-LAST:event_JamKejadianKeyPressed

    private void MenitKejadianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitKejadianKeyPressed
        Valid.pindah(evt,JamKejadian,DetikKejadian);
    }//GEN-LAST:event_MenitKejadianKeyPressed

    private void DetikKejadianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikKejadianKeyPressed
        Valid.pindah(evt,MenitKejadian,Lapor);
    }//GEN-LAST:event_DetikKejadianKeyPressed

    private void LaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LaporKeyPressed
        Valid.pindah(evt,DetikKejadian,JamLapor);
    }//GEN-LAST:event_LaporKeyPressed

    private void JamLaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamLaporKeyPressed
        Valid.pindah(evt,Lapor,MenitLapor);
    }//GEN-LAST:event_JamLaporKeyPressed

    private void MenitLaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitLaporKeyPressed
        Valid.pindah(evt,JamLapor,DetikLapor);
    }//GEN-LAST:event_MenitLaporKeyPressed

    private void DetikLaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikLaporKeyPressed
        Valid.pindah(evt,MenitLapor,nip);
    }//GEN-LAST:event_DetikLaporKeyPressed

    private void nipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nipKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",namapetugas,nip.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            DetikLapor.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Lokasi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }
    }//GEN-LAST:event_nipKeyPressed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void UnitTerkaitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UnitTerkaitKeyPressed
        Valid.pindah(evt,KodeInsiden,Kronologis);
    }//GEN-LAST:event_UnitTerkaitKeyPressed

    private void AkibatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AkibatKeyPressed
        Valid.pindah(evt,Tindakan,Identifikasi);
    }//GEN-LAST:event_AkibatKeyPressed

    private void btnInsidenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsidenActionPerformed
        insiden.emptTeks();
        insiden.isCek();
        insiden.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        insiden.setLocationRelativeTo(internalFrame1);
        insiden.setVisible(true);
    }//GEN-LAST:event_btnInsidenActionPerformed

    private void KodeInsidenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeInsidenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama_insiden from insiden_keselamatan where kode_insiden=?",NmInsiden,KodeInsiden.getText());
            Sequel.cariIsi("select jenis_insiden from insiden_keselamatan where kode_insiden=?",Jenis,KodeInsiden.getText());
            Sequel.cariIsi("select dampak from insiden_keselamatan where kode_insiden=?",Dampak,KodeInsiden.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Lokasi.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            UnitTerkait.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnInsidenActionPerformed(null);
        }
    }//GEN-LAST:event_KodeInsidenKeyPressed

    private void TindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanKeyPressed
        Valid.pindah(evt,Kronologis,Akibat);
    }//GEN-LAST:event_TindakanKeyPressed

    private void IdentifikasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IdentifikasiKeyPressed
        Valid.pindah(evt,Akibat,TindakLanjut);
    }//GEN-LAST:event_IdentifikasiKeyPressed

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutKeyPressed
        Valid.pindah(evt,Identifikasi,BtnSimpan);
    }//GEN-LAST:event_TindakLanjutKeyPressed

    private void ppGrafikBatangKejadianIKPPerbulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangKejadianIKPPerbulanActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select DATE_FORMAT(insiden_keselamatan_pasien.tgl_kejadian, '%y-%m'),count(DATE_FORMAT(insiden_keselamatan_pasien.tgl_kejadian, '%y-%m')) as jumlah "+
                "from insiden_keselamatan_pasien where tgl_kejadian between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by DATE_FORMAT(insiden_keselamatan_pasien.tgl_kejadian, '%y-%m')").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Kejadian IKP Per Bulan Tanggal "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Bulan","Jumlah Pasien", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Kejadian IKP Per Bulan",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setLocationRelativeTo(internalFrame1);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  

    }//GEN-LAST:event_ppGrafikBatangKejadianIKPPerbulanActionPerformed

    private void ppGrafikBatangKejadianIKPPerDampakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangKejadianIKPPerDampakActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select insiden_keselamatan.dampak,count(insiden_keselamatan.dampak) as jumlah "+
                   "from insiden_keselamatan_pasien inner join insiden_keselamatan on insiden_keselamatan_pasien.kode_insiden=insiden_keselamatan.kode_insiden "+
                   "where tgl_kejadian between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by insiden_keselamatan.dampak").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        
       JFreeChart freeChart = ChartFactory.createBarChart("Grafik Kejadian IKP Per Dampak Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Dampak IKP","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Kejadian IKP Per Dampak",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikBatangKejadianIKPPerDampakActionPerformed

    private void ppGrafikPieKejadianIKPPerDampakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPieKejadianIKPPerDampakActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select insiden_keselamatan.dampak,count(insiden_keselamatan.dampak) as jumlah "+
                   "from insiden_keselamatan_pasien inner join insiden_keselamatan on insiden_keselamatan_pasien.kode_insiden=insiden_keselamatan.kode_insiden "+
                   "where tgl_kejadian between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by insiden_keselamatan.dampak").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Kejadian IKP Per Dampak Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false);
        ChartFrame cf = new ChartFrame("Grafik Kejadian IKP Per Dampak",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);    
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setLocationRelativeTo(internalFrame1);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);
    }//GEN-LAST:event_ppGrafikPieKejadianIKPPerDampakActionPerformed

    private void ppGrafikBatangKejadianIKPPerJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangKejadianIKPPerJenisActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select insiden_keselamatan.jenis_insiden,count(insiden_keselamatan.jenis_insiden) as jumlah "+
                   "from insiden_keselamatan_pasien inner join insiden_keselamatan on insiden_keselamatan_pasien.kode_insiden=insiden_keselamatan.kode_insiden "+
                   "where tgl_kejadian between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by insiden_keselamatan.jenis_insiden").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        
       JFreeChart freeChart = ChartFactory.createBarChart("Grafik Kejadian IKP Per Jenis Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Jenis IKP","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Kejadian IKP Per Jenis",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);   
    }//GEN-LAST:event_ppGrafikBatangKejadianIKPPerJenisActionPerformed

    private void ppGrafikPieKejadianIKPPerJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPieKejadianIKPPerJenisActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select insiden_keselamatan.jenis_insiden,count(insiden_keselamatan.jenis_insiden) as jumlah "+
                   "from insiden_keselamatan_pasien inner join insiden_keselamatan on insiden_keselamatan_pasien.kode_insiden=insiden_keselamatan.kode_insiden "+
                   "where tgl_kejadian between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by insiden_keselamatan.jenis_insiden").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Kejadian IKP Per Jenis Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false);
        ChartFrame cf = new ChartFrame("Grafik Kejadian IKP Per Jenis",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setLocationRelativeTo(internalFrame1);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);
    }//GEN-LAST:event_ppGrafikPieKejadianIKPPerJenisActionPerformed

    private void ppGrafikBatangKejadianIKPPerTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangKejadianIKPPerTanggalActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select DATE_FORMAT(insiden_keselamatan_pasien.tgl_kejadian, '%y-%m-%d'),count(DATE_FORMAT(insiden_keselamatan_pasien.tgl_kejadian, '%y-%m-%d')) as jumlah "+
                "from insiden_keselamatan_pasien where tgl_kejadian between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by DATE_FORMAT(insiden_keselamatan_pasien.tgl_kejadian, '%y-%m-%d')").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Kejadian IKP Per Tanggal Tanggal "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Tanggal","Jumlah Pasien", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Kejadian IKP Per Tanggal",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setLocationRelativeTo(internalFrame1);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikBatangKejadianIKPPerTanggalActionPerformed

    private void ppGrafikBatangKejadianIKPPerTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangKejadianIKPPerTahunActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select year(insiden_keselamatan_pasien.tgl_kejadian),count(year(insiden_keselamatan_pasien.tgl_kejadian)) as jumlah "+
                "from insiden_keselamatan_pasien where tgl_kejadian between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by year(insiden_keselamatan_pasien.tgl_kejadian)").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Kejadian IKP Per Tahun Tanggal "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" S.D. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Tahun","Jumlah Pasien", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Kejadian IKP Per Tahun",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setLocationRelativeTo(internalFrame1);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true); 
    }//GEN-LAST:event_ppGrafikBatangKejadianIKPPerTahunActionPerformed

    private void KronologisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KronologisKeyPressed
        Valid.pindah(evt,UnitTerkait,Tindakan);
    }//GEN-LAST:event_KronologisKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDataInsidenKeselamatan dialog = new DlgDataInsidenKeselamatan(new javax.swing.JFrame(), true);
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
    private widget.TextBox Akibat;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKejadian;
    private widget.CekBox ChkLapor;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Dampak;
    private widget.ComboBox DetikKejadian;
    private widget.ComboBox DetikLapor;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Identifikasi;
    private widget.ComboBox JamKejadian;
    private widget.ComboBox JamLapor;
    private widget.TextBox Jenis;
    private widget.Tanggal Kejadian;
    private widget.TextBox KodeInsiden;
    private widget.TextBox Kronologis;
    private widget.Label LCount;
    private widget.Tanggal Lapor;
    private widget.TextBox Lokasi;
    private widget.ComboBox MenitKejadian;
    private widget.ComboBox MenitLapor;
    private widget.TextBox NmInsiden;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextBox TindakLanjut;
    private widget.TextBox Tindakan;
    private widget.TextBox UnitTerkait;
    private widget.Button btnInsiden;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox namapetugas;
    private widget.TextBox nip;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppGrafikBatangKejadianIKPPerDampak;
    private javax.swing.JMenuItem ppGrafikBatangKejadianIKPPerJenis;
    private javax.swing.JMenuItem ppGrafikBatangKejadianIKPPerTahun;
    private javax.swing.JMenuItem ppGrafikBatangKejadianIKPPerTanggal;
    private javax.swing.JMenuItem ppGrafikBatangKejadianIKPPerbulan;
    private javax.swing.JMenuItem ppGrafikPieKejadianIKPPerDampak;
    private javax.swing.JMenuItem ppGrafikPieKejadianIKPPerJenis;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                "select insiden_keselamatan_pasien.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                "reg_periksa.umurdaftar,reg_periksa.sttsumur,insiden_keselamatan_pasien.tgl_kejadian,"+
                "insiden_keselamatan_pasien.jam_kejadian,insiden_keselamatan_pasien.tgl_lapor,insiden_keselamatan_pasien.jam_lapor,"+
                "insiden_keselamatan_pasien.nip,petugas.nama,insiden_keselamatan_pasien.lokasi,"+
                "insiden_keselamatan_pasien.kode_insiden,insiden_keselamatan.nama_insiden,"+
                "insiden_keselamatan.jenis_insiden,insiden_keselamatan.dampak,insiden_keselamatan_pasien.unit_terkait,"+
                "insiden_keselamatan_pasien.kronologis,insiden_keselamatan_pasien.akibat,insiden_keselamatan_pasien.tindakan_insiden,"+
                "insiden_keselamatan_pasien.identifikasi_masalah,insiden_keselamatan_pasien.rtl "+
                "from insiden_keselamatan_pasien inner join reg_periksa inner join pasien "+
                "inner join insiden_keselamatan inner join petugas on insiden_keselamatan_pasien.no_rawat=reg_periksa.no_rawat "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and insiden_keselamatan_pasien.kode_insiden="+
                "insiden_keselamatan.kode_insiden and insiden_keselamatan_pasien.nip=petugas.nip where "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan_pasien.no_rawat like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and pasien.nm_pasien like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan_pasien.nip like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and petugas.nama like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan_pasien.lokasi like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan_pasien.kode_insiden like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan.nama_insiden like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan.jenis_insiden like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan.dampak like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan_pasien.unit_terkait like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan_pasien.kronologis like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan_pasien.akibat like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan_pasien.tindakan_insiden like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan_pasien.identifikasi_masalah like ? or "+
                "insiden_keselamatan_pasien.tgl_kejadian between ? and ? and insiden_keselamatan_pasien.rtl like ? order by insiden_keselamatan_pasien.tgl_kejadian ");
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
                ps.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(36,"%"+TCari.getText()+"%");
                ps.setString(37,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(38,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(39,"%"+TCari.getText()+"%");
                ps.setString(40,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(41,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(42,"%"+TCari.getText()+"%");
                ps.setString(43,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(44,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(45,"%"+TCari.getText()+"%");
                ps.setString(46,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(47,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(48,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString(2),rs.getString(3),
                        rs.getString(4)+" "+rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),
                        rs.getString(10),rs.getString(11),rs.getString(12),
                        rs.getString(13),rs.getString(14),rs.getString(15),
                        rs.getString(16),rs.getString(17),rs.getString(18),
                        rs.getString(19),rs.getString(20),rs.getString(21),
                        rs.getString(22)
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
        nip.setText("");
        namapetugas.setText("");
        KodeInsiden.setText("");
        NmInsiden.setText("");
        Jenis.setText("");
        Dampak.setText("");
        Lokasi.setText("");
        UnitTerkait.setText("");
        Kronologis.setText("");
        Akibat.setText("");
        Tindakan.setText("");
        TindakLanjut.setText("");
        Identifikasi.setText("");
        Kejadian.setDate(new Date());
        Lapor.setDate(new Date());
        Kejadian.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            JamKejadian.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(0,2));
            MenitKejadian.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(3,5));
            DetikKejadian.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString().substring(6,8));
            JamLapor.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(0,2));
            MenitLapor.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(3,5));
            DetikLapor.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString().substring(6,8));
            nip.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            namapetugas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Lokasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KodeInsiden.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            NmInsiden.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            Jenis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            Dampak.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            UnitTerkait.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Kronologis.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Akibat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            Tindakan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Identifikasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            TindakLanjut.setText(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Valid.SetTgl(Kejadian,tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            Valid.SetTgl(Lapor,tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());            
        }
    }

    private void isRawat() {
         Sequel.cariIsi("select no_rkm_medis from reg_periksa where no_rawat='"+TNoRw.getText()+"' ",TNoRM);
    }

    private void isPsien() {
        Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis='"+TNoRM.getText()+"' ",TPasien);
    }
    
    public void setNoRm(String norwt, Date tgl1, Date tgl2,String unit) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
        Lokasi.setText(unit);       
        isRawat();
        isPsien();              
        ChkInput.setSelected(true);
        isForm();
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,246));
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
        BtnSimpan.setEnabled(akses.getinsiden_keselamatan_pasien());
        BtnHapus.setEnabled(akses.getinsiden_keselamatan_pasien());
        BtnPrint.setEnabled(akses.getinsiden_keselamatan_pasien()); 
        if(akses.getjml2()>=1){
            nip.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?",namapetugas,nip.getText());
        }            
    }

    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkKejadian.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkKejadian.isSelected()==false){
                    nilai_jam =JamKejadian.getSelectedIndex();
                    nilai_menit =MenitKejadian.getSelectedIndex();
                    nilai_detik =DetikKejadian.getSelectedIndex();
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
                JamKejadian.setSelectedItem(jam);
                MenitKejadian.setSelectedItem(menit);
                DetikKejadian.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    private void jam2(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkLapor.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkLapor.isSelected()==false){
                    nilai_jam =JamLapor.getSelectedIndex();
                    nilai_menit =MenitLapor.getSelectedIndex();
                    nilai_detik =DetikLapor.getSelectedIndex();
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
                JamLapor.setSelectedItem(jam);
                MenitLapor.setSelectedItem(menit);
                DetikLapor.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
}

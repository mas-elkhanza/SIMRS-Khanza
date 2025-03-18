/*
  Dilarang keras memperjualbelikan/mengambil keuntungan dari Software 
  ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package permintaan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.akses;
import fungsi.sekuel;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariCaraBayar;
import simrskhanza.DlgCariPerusahaan;
import simrskhanza.DlgCariPoli;

public class DlgBookingMCUPerusahaan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private sekuel Sequel=new sekuel();
    private int i=0;
    private DlgCariPerusahaan perusahaan=new DlgCariPerusahaan(null,false);
    private DlgCariCaraBayar penjab=new DlgCariCaraBayar(null,false);
    private DlgCariPoli poli=new DlgCariPoli(null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgPasienBaruMCUPerusahaan pasienbaru=new DlgPasienBaruMCUPerusahaan(null,false);
    private String status="",BASENOREG="",URUTNOREG="",umur="0",sttsumur="Th";
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgBookingMCUPerusahaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "P","No.RM","Nama Pasien","JK","No.KTP","No.Pegawai","No.Booking","Tgl.Booking","Jam Booking","Status","Tahun","Bulan","Hari"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 13; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(25);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCari1ActionPerformed(null);
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCari1ActionPerformed(null);
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCari1ActionPerformed(null);
                    }
                }
            });
        }
        
        perusahaan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(perusahaan.getTable().getSelectedRow()!= -1){
                    KdPerusahaan.setText(perusahaan.getTable().getValueAt(perusahaan.getTable().getSelectedRow(),0).toString());
                    NmPerusahaan.setText(perusahaan.getTable().getValueAt(perusahaan.getTable().getSelectedRow(),1).toString());
                    BtnPerusahaan.requestFocus();
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
        
        perusahaan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    perusahaan.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    KdCaraBayar.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    NmCaraBayar.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                    BtnCaraBayar.requestFocus();
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
        
        penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penjab.dispose();
                } 
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){
                    KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                    BtnPoli.requestFocus();
                }      
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {poli.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    KdDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    NmDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    BtnDokter.requestFocus();
                }      
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {dokter.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        dokter.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    dokter.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pasienbaru.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pasienbaru.status=="Selesai"){
                    tampil();
                }      
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {dokter.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });  
        
        try {
            URUTNOREG=koneksiDB.URUTNOREG();
        } catch (Exception e) {
            URUTNOREG="dokter";
        }
        
        try {
            BASENOREG=koneksiDB.BASENOREG();
        } catch (Exception e) {
            BASENOREG="registrasi";
        }
        
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

        Popup = new javax.swing.JPopupMenu();
        ppPilih = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        ppPengajuanPasienBaru = new javax.swing.JMenuItem();
        TNoReg = new widget.TextBox();
        TNoRw = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label13 = new widget.Label();
        KdDokter = new widget.TextBox();
        NmDokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        label17 = new widget.Label();
        KdPoli = new widget.TextBox();
        NmPoli = new widget.TextBox();
        BtnPoli = new widget.Button();
        jLabel16 = new widget.Label();
        Tanggal = new widget.Tanggal();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        ChkKejadian = new widget.CekBox();
        label18 = new widget.Label();
        KdCaraBayar = new widget.TextBox();
        NmCaraBayar = new widget.TextBox();
        BtnCaraBayar = new widget.Button();
        jLabel9 = new widget.Label();
        NmPerusahaan = new widget.TextBox();
        BtnPerusahaan = new widget.Button();
        KdPerusahaan = new widget.TextBox();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppPilih.setBackground(new java.awt.Color(255, 255, 254));
        ppPilih.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPilih.setForeground(new java.awt.Color(50, 50, 50));
        ppPilih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPilih.setText("Pilih Semua");
        ppPilih.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPilih.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilih.setName("ppPilih"); // NOI18N
        ppPilih.setPreferredSize(new java.awt.Dimension(180, 25));
        ppPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPilihActionPerformed(evt);
            }
        });
        Popup.add(ppPilih);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(180, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppPengajuanPasienBaru.setBackground(new java.awt.Color(255, 255, 254));
        ppPengajuanPasienBaru.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPengajuanPasienBaru.setForeground(new java.awt.Color(50, 50, 50));
        ppPengajuanPasienBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPengajuanPasienBaru.setText("Pengajuan Pasien Baru");
        ppPengajuanPasienBaru.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPengajuanPasienBaru.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPengajuanPasienBaru.setName("ppPengajuanPasienBaru"); // NOI18N
        ppPengajuanPasienBaru.setPreferredSize(new java.awt.Dimension(180, 25));
        ppPengajuanPasienBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPengajuanPasienBaruActionPerformed(evt);
            }
        });
        Popup.add(ppPengajuanPasienBaru);

        TNoReg.setName("TNoReg"); // NOI18N
        TNoReg.setPreferredSize(new java.awt.Dimension(170, 23));

        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.setPreferredSize(new java.awt.Dimension(170, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Booking MCU Perusahaan/Instansi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
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
        tbDokter.setToolTipText("Silahkan pilih penilaian Ya / Tidak");
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 104));
        panelisi3.setLayout(null);

        label13.setText("Dokter MCU :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(0, 40, 85, 23);

        KdDokter.setEditable(false);
        KdDokter.setName("KdDokter"); // NOI18N
        KdDokter.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(KdDokter);
        KdDokter.setBounds(89, 40, 101, 23);

        NmDokter.setEditable(false);
        NmDokter.setName("NmDokter"); // NOI18N
        NmDokter.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmDokter);
        NmDokter.setBounds(192, 40, 180, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDokter.setMnemonic('2');
        BtnDokter.setToolTipText("Alt+2");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        panelisi3.add(BtnDokter);
        BtnDokter.setBounds(375, 40, 28, 23);

        label17.setText("Unit/Poli MCU :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label17);
        label17.setBounds(420, 10, 110, 23);

        KdPoli.setEditable(false);
        KdPoli.setName("KdPoli"); // NOI18N
        KdPoli.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(KdPoli);
        KdPoli.setBounds(534, 10, 61, 23);

        NmPoli.setEditable(false);
        NmPoli.setName("NmPoli"); // NOI18N
        NmPoli.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmPoli);
        NmPoli.setBounds(597, 10, 170, 23);

        BtnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli.setMnemonic('2');
        BtnPoli.setToolTipText("Alt+2");
        BtnPoli.setName("BtnPoli"); // NOI18N
        BtnPoli.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliActionPerformed(evt);
            }
        });
        panelisi3.add(BtnPoli);
        BtnPoli.setBounds(770, 10, 28, 23);

        jLabel16.setText("Tanggal MCU :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setVerifyInputWhenFocusTarget(false);
        panelisi3.add(jLabel16);
        jLabel16.setBounds(0, 10, 85, 23);

        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "29-08-2024" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi3.add(Tanggal);
        Tanggal.setBounds(89, 10, 90, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        CmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJamKeyPressed(evt);
            }
        });
        panelisi3.add(CmbJam);
        CmbJam.setBounds(183, 10, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        CmbMenit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbMenitKeyPressed(evt);
            }
        });
        panelisi3.add(CmbMenit);
        CmbMenit.setBounds(249, 10, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        CmbDetik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbDetikKeyPressed(evt);
            }
        });
        panelisi3.add(CmbDetik);
        CmbDetik.setBounds(315, 10, 62, 23);

        ChkKejadian.setBorder(null);
        ChkKejadian.setSelected(true);
        ChkKejadian.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkKejadian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkKejadian.setName("ChkKejadian"); // NOI18N
        panelisi3.add(ChkKejadian);
        ChkKejadian.setBounds(380, 10, 23, 23);

        label18.setText("Jenis/Cara Bayar :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(420, 40, 110, 23);

        KdCaraBayar.setEditable(false);
        KdCaraBayar.setName("KdCaraBayar"); // NOI18N
        KdCaraBayar.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi3.add(KdCaraBayar);
        KdCaraBayar.setBounds(534, 40, 61, 23);

        NmCaraBayar.setEditable(false);
        NmCaraBayar.setName("NmCaraBayar"); // NOI18N
        NmCaraBayar.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(NmCaraBayar);
        NmCaraBayar.setBounds(597, 40, 170, 23);

        BtnCaraBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayar.setMnemonic('2');
        BtnCaraBayar.setToolTipText("Alt+2");
        BtnCaraBayar.setName("BtnCaraBayar"); // NOI18N
        BtnCaraBayar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnCaraBayar);
        BtnCaraBayar.setBounds(770, 40, 28, 23);

        jLabel9.setText("Instansi MCU :");
        jLabel9.setName("jLabel9"); // NOI18N
        jLabel9.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi3.add(jLabel9);
        jLabel9.setBounds(0, 70, 85, 23);

        NmPerusahaan.setEditable(false);
        NmPerusahaan.setName("NmPerusahaan"); // NOI18N
        NmPerusahaan.setPreferredSize(new java.awt.Dimension(150, 24));
        panelisi3.add(NmPerusahaan);
        NmPerusahaan.setBounds(162, 70, 210, 24);

        BtnPerusahaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPerusahaan.setMnemonic('1');
        BtnPerusahaan.setToolTipText("Alt+1");
        BtnPerusahaan.setName("BtnPerusahaan"); // NOI18N
        BtnPerusahaan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPerusahaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerusahaanActionPerformed(evt);
            }
        });
        BtnPerusahaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPerusahaanKeyPressed(evt);
            }
        });
        panelisi3.add(BtnPerusahaan);
        BtnPerusahaan.setBounds(375, 70, 28, 23);

        KdPerusahaan.setEditable(false);
        KdPerusahaan.setHighlighter(null);
        KdPerusahaan.setName("KdPerusahaan"); // NOI18N
        panelisi3.add(KdPerusahaan);
        KdPerusahaan.setBounds(89, 70, 71, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnBatal.setMnemonic('H');
        BtnBatal.setText("Hapus");
        BtnBatal.setToolTipText("Alt+H");
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
        panelisi1.add(BtnBatal);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
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
        panelisi1.add(BtnCari1);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi1.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi1.add(LCount);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnCari.setMnemonic('C');
        BtnCari.setText("Cari");
        BtnCari.setToolTipText("Alt+C");
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
        panelisi1.add(BtnCari);

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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariBookingMCUPerusahaan form=new DlgCariBookingMCUPerusahaan(null,false);
        form.isCek();
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dokter.dispose();
        penjab.dispose();
        poli.dispose();
        perusahaan.dispose();
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnSimpan,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NmPerusahaan.getText().trim().equals("")||KdPerusahaan.getText().trim().equals("")){
            Valid.textKosong(BtnPerusahaan,"Perusahaan/Instansi MCU");
        }else if(KdDokter.getText().trim().equals("")||NmDokter.getText().trim().equals("")){
            Valid.textKosong(BtnDokter,"Dokter MCU");
        }else if(KdPoli.getText().trim().equals("")||NmPoli.getText().trim().equals("")){
            Valid.textKosong(BtnPoli,"Unit/Poli MCU");
        }else if(KdCaraBayar.getText().trim().equals("")||NmCaraBayar.getText().trim().equals("")){
            Valid.textKosong(BtnCaraBayar,"Jenis/Cara Bayar MCU");
        }else{
            for(i=0;i<tbDokter.getRowCount();i++){ 
                if(tbDokter.getValueAt(i,0).toString().equals("true")){
                    status="Baru";
                    if(Sequel.cariInteger("select count(reg_periksa.no_rkm_medis) from reg_periksa where reg_periksa.no_rkm_medis=? and reg_periksa.kd_poli=?",tbDokter.getValueAt(i,1).toString(),KdPoli.getText())>0){
                        status="Lama";
                    }

                    umur="0";
                    sttsumur="Th";
                    if(Integer.parseInt(tbDokter.getValueAt(i,10).toString())>0){
                        umur=tbDokter.getValueAt(i,10).toString();
                        sttsumur="Th";
                    }else if(Integer.parseInt(tbDokter.getValueAt(i,10).toString())==0){
                        if(Integer.parseInt(tbDokter.getValueAt(i,11).toString())>0){
                            umur=tbDokter.getValueAt(i,11).toString();
                            sttsumur="Bl";
                        }else if(Integer.parseInt(tbDokter.getValueAt(i,11).toString())==0){
                            umur=tbDokter.getValueAt(i,12).toString();
                            sttsumur="Hr";
                        }
                    }

                    isNumber();
                    if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                            new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                            KdDokter.getText(),tbDokter.getValueAt(i,1).toString(),KdPoli.getText(),NmPerusahaan.getText(),"","Tempat Kerja","0","Belum",
                            tbDokter.getValueAt(i,9).toString(),"Ralan",KdCaraBayar.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                        Sequel.mengedit("pasien","no_rkm_medis=?","umur='"+tbDokter.getValueAt(i,10).toString()+" Th "+tbDokter.getValueAt(i,11).toString()+" Bl "+tbDokter.getValueAt(i,12).toString()+" Hr'",1,new String[]{tbDokter.getValueAt(i,1).toString()});
                        if(Sequel.menyimpantf2("booking_mcu_perusahaan_berhasil_registrasi","?,?",2,new String[]{
                            tbDokter.getValueAt(i,6).toString(),TNoRw.getText()
                        })==true){
                            if(Sequel.mengedittf2("booking_mcu_perusahaan","no_mcu=?","status='Menunggu Hasil'",1,new String[]{
                                    tbDokter.getValueAt(i,6).toString()
                                })==true){
                                tabMode.removeRow(i);
                                i--;
                            } 
                        }
                    }else{
                        isNumber();
                        if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                                new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                                KdDokter.getText(),tbDokter.getValueAt(i,1).toString(),KdPoli.getText(),NmPerusahaan.getText(),"","Tempat Kerja","0","Belum",
                                tbDokter.getValueAt(i,9).toString(),"Ralan",KdCaraBayar.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                            Sequel.mengedit("pasien","no_rkm_medis=?","umur='"+tbDokter.getValueAt(i,10).toString()+" Th "+tbDokter.getValueAt(i,11).toString()+" Bl "+tbDokter.getValueAt(i,12).toString()+" Hr'",1,new String[]{tbDokter.getValueAt(i,1).toString()});
                            if(Sequel.menyimpantf2("booking_mcu_perusahaan_berhasil_registrasi","?,?",2,new String[]{
                                tbDokter.getValueAt(i,6).toString(),TNoRw.getText()
                            })==true){
                                if(Sequel.mengedittf2("booking_mcu_perusahaan","no_mcu=?","status='Menunggu Hasil'",1,new String[]{
                                        tbDokter.getValueAt(i,6).toString()
                                    })==true){
                                    tabMode.removeRow(i);
                                    i--;
                                } 
                            }
                        }else{
                            isNumber();
                            if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                                    new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                                    KdDokter.getText(),tbDokter.getValueAt(i,1).toString(),KdPoli.getText(),NmPerusahaan.getText(),"","Tempat Kerja","0","Belum",
                                    tbDokter.getValueAt(i,9).toString(),"Ralan",KdCaraBayar.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                                Sequel.mengedit("pasien","no_rkm_medis=?","umur='"+tbDokter.getValueAt(i,10).toString()+" Th "+tbDokter.getValueAt(i,11).toString()+" Bl "+tbDokter.getValueAt(i,12).toString()+" Hr'",1,new String[]{tbDokter.getValueAt(i,1).toString()});
                                if(Sequel.menyimpantf2("booking_mcu_perusahaan_berhasil_registrasi","?,?",2,new String[]{
                                    tbDokter.getValueAt(i,6).toString(),TNoRw.getText()
                                })==true){
                                    if(Sequel.mengedittf2("booking_mcu_perusahaan","no_mcu=?","status='Menunggu Hasil'",1,new String[]{
                                            tbDokter.getValueAt(i,6).toString()
                                        })==true){
                                        tabMode.removeRow(i);
                                        i--;
                                    } 
                                }
                            }else{
                                isNumber();
                                if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                                        new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                                        KdDokter.getText(),tbDokter.getValueAt(i,1).toString(),KdPoli.getText(),NmPerusahaan.getText(),"","Tempat Kerja","0","Belum",
                                        tbDokter.getValueAt(i,9).toString(),"Ralan",KdCaraBayar.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                                    Sequel.mengedit("pasien","no_rkm_medis=?","umur='"+tbDokter.getValueAt(i,10).toString()+" Th "+tbDokter.getValueAt(i,11).toString()+" Bl "+tbDokter.getValueAt(i,12).toString()+" Hr'",1,new String[]{tbDokter.getValueAt(i,1).toString()});
                                    if(Sequel.menyimpantf2("booking_mcu_perusahaan_berhasil_registrasi","?,?",2,new String[]{
                                        tbDokter.getValueAt(i,6).toString(),TNoRw.getText()
                                    })==true){
                                        if(Sequel.mengedittf2("booking_mcu_perusahaan","no_mcu=?","status='Menunggu Hasil'",1,new String[]{
                                                tbDokter.getValueAt(i,6).toString()
                                            })==true){
                                            tabMode.removeRow(i);
                                            i--;
                                        } 
                                    }
                                }else{
                                    isNumber();
                                    if(Sequel.menyimpantf("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                                            new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(Tanggal.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                                            KdDokter.getText(),tbDokter.getValueAt(i,1).toString(),KdPoli.getText(),NmPerusahaan.getText(),"","Tempat Kerja","0","Belum",
                                            tbDokter.getValueAt(i,9).toString(),"Ralan",KdCaraBayar.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                                        Sequel.mengedit("pasien","no_rkm_medis=?","umur='"+tbDokter.getValueAt(i,10).toString()+" Th "+tbDokter.getValueAt(i,11).toString()+" Bl "+tbDokter.getValueAt(i,12).toString()+" Hr'",1,new String[]{tbDokter.getValueAt(i,1).toString()});
                                        if(Sequel.menyimpantf2("booking_mcu_perusahaan_berhasil_registrasi","?,?",2,new String[]{
                                            tbDokter.getValueAt(i,6).toString(),TNoRw.getText()
                                        })==true){
                                            if(Sequel.mengedittf2("booking_mcu_perusahaan","no_mcu=?","status='Menunggu Hasil'",1,new String[]{
                                                    tbDokter.getValueAt(i,6).toString()
                                                })==true){
                                                tabMode.removeRow(i);
                                                i--;
                                            } 
                                        }
                                    }
                                } 
                            } 
                        }                
                    } 
                }
            }

            LCount.setText(""+tabMode.getRowCount());
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan,BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari1ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnBatal.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
    if(NmPerusahaan.getText().trim().equals("")){
        Valid.textKosong(BtnPerusahaan,"Perusahaan/Instansi MCU");
    }else{
        tampil();
    }
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari1ActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    for(i=0;i<tbDokter.getRowCount();i++){ 
        tbDokter.setValueAt(false,i,0);
    }
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnDokterActionPerformed

    private void BtnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliActionPerformed
        poli.isCek();
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setAlwaysOnTop(false);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnPoliActionPerformed

    private void BtnPerusahaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerusahaanActionPerformed
        perusahaan.isCek();
        perusahaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        perusahaan.setLocationRelativeTo(internalFrame1);
        perusahaan.setVisible(true);
    }//GEN-LAST:event_BtnPerusahaanActionPerformed

    private void BtnPerusahaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPerusahaanKeyPressed
        Valid.pindah(evt,KdPerusahaan,BtnSimpan);
    }//GEN-LAST:event_BtnPerusahaanKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                if(tbDokter.getSelectedRow()!= -1){
                    if(tbDokter.getSelectedColumn()==2){
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString().equals("true")){
                            tbDokter.setValueAt(false,tbDokter.getSelectedRow(),3);
                        }
                    }
                    if(tbDokter.getSelectedColumn()==3){
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString().equals("true")){
                            tbDokter.setValueAt(false,tbDokter.getSelectedRow(),2);
                        }
                    }
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void CmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJamKeyPressed
        Valid.pindah(evt,Tanggal,CmbMenit);
    }//GEN-LAST:event_CmbJamKeyPressed

    private void CmbMenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbMenitKeyPressed
        Valid.pindah(evt,CmbJam,CmbDetik);
    }//GEN-LAST:event_CmbMenitKeyPressed

    private void CmbDetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbDetikKeyPressed
        Valid.pindah(evt,CmbMenit,BtnPoli);
    }//GEN-LAST:event_CmbDetikKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,CmbJam);
    }//GEN-LAST:event_TanggalKeyPressed

    private void BtnCaraBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarActionPerformed
        penjab.isCek();
        penjab.onCari();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnCaraBayarActionPerformed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){ 
            if(tbDokter.getValueAt(i,0).toString().equals("true")){
                if(Sequel.meghapustf("booking_mcu_perusahaan","no_mcu",tbDokter.getValueAt(i,6).toString())==true){
                    tabMode.removeRow(i);
                    i--;
                } 
            }
        }
        
        LCount.setText(""+tabMode.getRowCount());
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
       Valid.pindah(evt,BtnSimpan,TCari); 
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void ppPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){ 
            tbDokter.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppPilihActionPerformed

    private void ppPengajuanPasienBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPengajuanPasienBaruActionPerformed
        if(NmPerusahaan.getText().trim().equals("")||KdPerusahaan.getText().trim().equals("")){
            Valid.textKosong(BtnPerusahaan,"Perusahaan/Instansi MCU");
        }else if(KdCaraBayar.getText().trim().equals("")||NmCaraBayar.getText().trim().equals("")){
            Valid.textKosong(BtnCaraBayar,"Jenis/Cara Bayar MCU");
        }else{
            pasienbaru.KodePerusahaan=KdPerusahaan.getText();
            pasienbaru.KodeCaraBayar=KdCaraBayar.getText();
            pasienbaru.TanggalMCU=Valid.SetTgl(Tanggal.getSelectedItem()+"");
            pasienbaru.status="";
            pasienbaru.tampil();
            pasienbaru.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            pasienbaru.setLocationRelativeTo(internalFrame1);
            pasienbaru.setVisible(true);
        }
    }//GEN-LAST:event_ppPengajuanPasienBaruActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBookingMCUPerusahaan dialog = new DlgBookingMCUPerusahaan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCaraBayar;
    private widget.Button BtnCari;
    private widget.Button BtnCari1;
    private widget.Button BtnDokter;
    private widget.Button BtnKeluar;
    private widget.Button BtnPerusahaan;
    private widget.Button BtnPoli;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkKejadian;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.TextBox KdCaraBayar;
    private widget.TextBox KdDokter;
    private widget.TextBox KdPerusahaan;
    private widget.TextBox KdPoli;
    private widget.Label LCount;
    private widget.TextBox NmCaraBayar;
    private widget.TextBox NmDokter;
    private widget.TextBox NmPerusahaan;
    private widget.TextBox NmPoli;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.TextBox TNoReg;
    private widget.TextBox TNoRw;
    private widget.Tanggal Tanggal;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel16;
    private widget.Label jLabel7;
    private widget.Label jLabel9;
    private widget.Label label10;
    private widget.Label label13;
    private widget.Label label17;
    private widget.Label label18;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppPengajuanPasienBaru;
    private javax.swing.JMenuItem ppPilih;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.no_ktp,pasien.nip,booking_mcu_perusahaan.tanggal_booking,booking_mcu_perusahaan.jam_booking," +
                        "booking_mcu_perusahaan.no_mcu,if(pasien.tgl_daftar=booking_mcu_perusahaan.tanggal_mcu,'Baru','Lama') as daftar,TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun,"+
                        "(TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12)) as bulan,"+
                        "TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(pasien.tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari "+
                        "from pasien inner join booking_mcu_perusahaan on booking_mcu_perusahaan.no_rkm_medis=pasien.no_rkm_medis where booking_mcu_perusahaan.status='Terdaftar' and "+
                        "booking_mcu_perusahaan.kode_perusahaan=? and booking_mcu_perusahaan.tanggal_mcu=? order by pasien.nip");
            }else{
                ps=koneksi.prepareStatement(
                        "select pasien.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.no_ktp,pasien.nip,booking_mcu_perusahaan.tanggal_booking,booking_mcu_perusahaan.jam_booking," +
                        "booking_mcu_perusahaan.no_mcu,if(pasien.tgl_daftar=booking_mcu_perusahaan.tanggal_mcu,'Baru','Lama') as daftar,TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) as tahun,"+
                        "(TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12)) as bulan,"+
                        "TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(pasien.tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, pasien.tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, pasien.tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()) as hari "+
                        "from pasien inner join booking_mcu_perusahaan on booking_mcu_perusahaan.no_rkm_medis=pasien.no_rkm_medis where booking_mcu_perusahaan.status='Terdaftar' and "+
                        "booking_mcu_perusahaan.kode_perusahaan=? and booking_mcu_perusahaan.tanggal_mcu=? and (pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or pasien.no_ktp like ? "+
                        "or pasien.nip like ? or booking_mcu_perusahaan.no_mcu like ?) order by pasien.nip");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,KdPerusahaan.getText());
                    ps.setString(2,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                }else{
                    ps.setString(1,KdPerusahaan.getText());
                    ps.setString(2,Valid.SetTgl(Tanggal.getSelectedItem()+""));
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("jk"),rs.getString("no_ktp"),
                        rs.getString("nip"),rs.getString("no_mcu"),rs.getString("tanggal_booking"),rs.getString("jam_booking"),
                        rs.getString("daftar"),rs.getString("tahun"),rs.getString("bulan"),rs.getString("hari")
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
            LCount.setText(""+tabMode.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    
    public void isCek(){
        TCari.requestFocus();
        BtnSimpan.setEnabled(akses.getbooking_mcu_perusahaan());
        BtnBatal.setEnabled(akses.getbooking_mcu_perusahaan());
        BtnCari.setEnabled(akses.getbooking_mcu_perusahaan());
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
    
    private void isNumber(){         
        if(BASENOREG.equals("booking")){
            switch (URUTNOREG) {
                case "poli":
                    if(Sequel.cariInteger("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_poli='"+KdPoli.getText()+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'")>=
                            Sequel.cariInteger("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_poli='"+KdPoli.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'")){
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_poli='"+KdPoli.getText()+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    }else{
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_poli='"+KdPoli.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    }
                    break;
                case "dokter":
                    if(Sequel.cariInteger("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_dokter='"+KdDokter.getText()+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'")>=
                            Sequel.cariInteger("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_dokter='"+KdDokter.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'")){
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_dokter='"+KdDokter.getText()+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    }else{
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_dokter='"+KdDokter.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    }
                    break;
                case "dokter + poli":  
                    if(Sequel.cariInteger("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_dokter='"+KdDokter.getText()+"' and booking_registrasi.kd_poli='"+KdPoli.getText()+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'")>=
                            Sequel.cariInteger("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_dokter='"+KdDokter.getText()+"' and reg_periksa.kd_poli='"+KdPoli.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'")){
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_dokter='"+KdDokter.getText()+"' and booking_registrasi.kd_poli='"+KdPoli.getText()+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    }else{
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where kd_dokter='"+KdDokter.getText()+"' and reg_periksa.kd_poli='"+KdPoli.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    }                    
                    break;
                default:
                    if(Sequel.cariInteger("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_poli='"+KdPoli.getText()+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'")>=
                            Sequel.cariInteger("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_poli='"+KdPoli.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'")){
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(booking_registrasi.no_reg,signed)),0) from booking_registrasi where booking_registrasi.kd_poli='"+KdPoli.getText()+"' and booking_registrasi.tanggal_periksa='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    }else{
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_poli='"+KdPoli.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    }
                    break;
            }
        }else{
            switch (URUTNOREG) {
                case "poli":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_poli='"+KdPoli.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
                case "dokter":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_dokter='"+KdDokter.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
                case "dokter + poli":             
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_dokter='"+KdDokter.getText()+"' and reg_periksa.kd_poli='"+KdPoli.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
                default:
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(reg_periksa.no_reg,signed)),0) from reg_periksa where reg_periksa.kd_dokter='"+KdDokter.getText()+"' and reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"'","",3,TNoReg);
                    break;
            }
        }    
        
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(reg_periksa.no_rawat,6),signed)),0) from reg_periksa where reg_periksa.tgl_registrasi='"+Valid.SetTgl(Tanggal.getSelectedItem()+"")+"' ",dateformat.format(Tanggal.getDate())+"/",6,TNoRw);             
    }
}

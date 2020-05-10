package rekammedis;

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
import kepegawaian.DlgCariPetugas;
import simrskhanza.DlgKamarInap;
import simrskhanza.DlgRujuk;

/**
 *
 * @author dosen
 */
public class RMDeteksiDiniCorona extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0,gejala1=0,gejala2=0,gejala3=0,resiko1=0,resiko2a=0,resiko2b=0,resiko2c=0,resiko2d=0,resiko2e=0,gejala=0,resiko=0;
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    

    /** Creates new form DlgPemberianInfus
     * @param parent
     * @param modal */
    public RMDeteksiDiniCorona(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","No.R.M.","Nama Pasien","Alamat","No.KTP","No.Telp","Pekerjaan","Tgl.Lahir","Skrining","NIP","Nama Petugas",
                "Demam","Batuk","Sesak Napas","Timbul Gejala","Riwayat Sakit Sebelumnya","Riwayat Periksa Sebelumnya","Perjalanan LN",
                "Asal Daerah","Kedatangan","Kontak Positif","Kontak PDP","Faskes Positif","Perjalanan LN","Pasar Hewan","Kesimpulan",
                "Tindak Lanjut"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbObat.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 27; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(65);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(170);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(65);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(50);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(75);
            }else if(i==15){
                column.setPreferredWidth(150);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(75);
            }else if(i==18){
                column.setPreferredWidth(120);
            }else if(i==19){
                column.setPreferredWidth(65);
            }else if(i==20){
                column.setPreferredWidth(75);
            }else if(i==21){
                column.setPreferredWidth(65);
            }else if(i==22){
                column.setPreferredWidth(75);
            }else if(i==23){
                column.setPreferredWidth(75);
            }else if(i==24){
                column.setPreferredWidth(73);
            }else if(i==25){
                column.setPreferredWidth(85);
            }else if(i==26){
                column.setPreferredWidth(80);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        RiwayatSakitSebelumnya.setDocument(new batasInput((byte)50).getKata(RiwayatSakitSebelumnya));
        RiwayatPeriksaSebelumnya.setDocument(new batasInput((byte)50).getKata(RiwayatPeriksaSebelumnya));
        AsalDaerah.setDocument(new batasInput((byte)50).getKata(AsalDaerah));

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
                    kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                    nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                }  
                kdptg.requestFocus();
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
 
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakDeteksiDini = new javax.swing.JMenuItem();
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
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        NoRM = new widget.TextBox();
        NamaPasien = new widget.TextBox();
        jLabel7 = new widget.Label();
        NoRawat = new widget.TextBox();
        A1 = new widget.ComboBox();
        jLabel12 = new widget.Label();
        jLabel8 = new widget.Label();
        RiwayatSakitSebelumnya = new widget.TextBox();
        jLabel9 = new widget.Label();
        NIK = new widget.TextBox();
        jLabel11 = new widget.Label();
        NoHP = new widget.TextBox();
        jLabel13 = new widget.Label();
        Pekerjaan = new widget.TextBox();
        TglLahir = new widget.TextBox();
        jLabel14 = new widget.Label();
        jLabel16 = new widget.Label();
        Alamat = new widget.TextBox();
        jLabel18 = new widget.Label();
        TglGejala = new widget.Tanggal();
        jLabel19 = new widget.Label();
        jLabel20 = new widget.Label();
        A2 = new widget.ComboBox();
        jLabel21 = new widget.Label();
        A3 = new widget.ComboBox();
        TglSkrining = new widget.Tanggal();
        jLabel22 = new widget.Label();
        jLabel23 = new widget.Label();
        AsalDaerah = new widget.TextBox();
        jLabel24 = new widget.Label();
        jLabel25 = new widget.Label();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        jLabel28 = new widget.Label();
        jLabel29 = new widget.Label();
        jLabel30 = new widget.Label();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        B1 = new widget.ComboBox();
        jLabel33 = new widget.Label();
        jLabel34 = new widget.Label();
        RiwayatPeriksaSebelumnya = new widget.TextBox();
        jLabel35 = new widget.Label();
        TglKedatangan = new widget.Tanggal();
        jLabel36 = new widget.Label();
        jLabel37 = new widget.Label();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        Kesimpulan = new widget.ComboBox();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        B2b = new widget.ComboBox();
        jLabel42 = new widget.Label();
        jLabel43 = new widget.Label();
        B2c = new widget.ComboBox();
        jLabel45 = new widget.Label();
        B2d = new widget.ComboBox();
        jLabel46 = new widget.Label();
        jLabel47 = new widget.Label();
        jLabel48 = new widget.Label();
        B2e = new widget.ComboBox();
        jLabel49 = new widget.Label();
        B2a = new widget.ComboBox();
        TindakLanjut = new widget.ComboBox();
        jLabel50 = new widget.Label();
        jLabel51 = new widget.Label();
        kdptg = new widget.TextBox();
        nmptg = new widget.TextBox();
        BtnPtg = new widget.Button();
        scrollPane5 = new widget.ScrollPane();
        HasilLaborat = new widget.TextArea();
        jLabel44 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        HasilRadiologi = new widget.TextArea();
        jLabel52 = new widget.Label();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakDeteksiDini.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakDeteksiDini.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakDeteksiDini.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakDeteksiDini.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakDeteksiDini.setText("Cetak Form Deteksi Dini");
        MnCetakDeteksiDini.setName("MnCetakDeteksiDini"); // NOI18N
        MnCetakDeteksiDini.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakDeteksiDiniActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakDeteksiDini);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Deteksi Dini Pasien Corona ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setAutoCreateRowSorter(true);
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
        panelGlass8.setPreferredSize(new java.awt.Dimension(55, 55));
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

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(95, 23));
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

        jPanel3.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Tgl.Masuk :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(63, 23));
        panelGlass7.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-05-2020" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass7.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-05-2020" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(360, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
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
        panelGlass7.add(BtnCari);

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
        panelGlass7.add(BtnAll);

        jPanel3.add(panelGlass7, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 306));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Input Data");
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

        scrollInput.setName("scrollInput"); // NOI18N

        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(830, 713));
        FormInput.setLayout(null);

        NoRM.setEditable(false);
        NoRM.setHighlighter(null);
        NoRM.setName("NoRM"); // NOI18N
        FormInput.add(NoRM);
        NoRM.setBounds(219, 10, 100, 23);

        NamaPasien.setEditable(false);
        NamaPasien.setHighlighter(null);
        NamaPasien.setName("NamaPasien"); // NOI18N
        FormInput.add(NamaPasien);
        NamaPasien.setBounds(321, 10, 497, 23);

        jLabel7.setText("No.Rawat :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(0, 10, 72, 23);

        NoRawat.setEditable(false);
        NoRawat.setHighlighter(null);
        NoRawat.setName("NoRawat"); // NOI18N
        FormInput.add(NoRawat);
        NoRawat.setBounds(76, 10, 141, 23);

        A1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        A1.setName("A1"); // NOI18N
        A1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                A1ItemStateChanged(evt);
            }
        });
        A1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                A1KeyPressed(evt);
            }
        });
        FormInput.add(A1);
        A1.setBounds(733, 120, 85, 23);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("B. FAKTOR RESIKO");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(15, 290, 140, 23);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Riwayat sakit sebelumnya");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(90, 230, 200, 23);

        RiwayatSakitSebelumnya.setHighlighter(null);
        RiwayatSakitSebelumnya.setName("RiwayatSakitSebelumnya"); // NOI18N
        RiwayatSakitSebelumnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatSakitSebelumnyaKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatSakitSebelumnya);
        RiwayatSakitSebelumnya.setBounds(320, 230, 350, 23);

        jLabel9.setText("NIK :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(0, 40, 72, 23);

        NIK.setEditable(false);
        NIK.setHighlighter(null);
        NIK.setName("NIK"); // NOI18N
        FormInput.add(NIK);
        NIK.setBounds(76, 40, 141, 23);

        jLabel11.setText("No.Telp/HP :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(220, 40, 72, 23);

        NoHP.setEditable(false);
        NoHP.setHighlighter(null);
        NoHP.setName("NoHP"); // NOI18N
        FormInput.add(NoHP);
        NoHP.setBounds(296, 40, 110, 23);

        jLabel13.setText("Pekerjaan :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(408, 40, 70, 23);

        Pekerjaan.setEditable(false);
        Pekerjaan.setHighlighter(null);
        Pekerjaan.setName("Pekerjaan"); // NOI18N
        FormInput.add(Pekerjaan);
        Pekerjaan.setBounds(482, 40, 151, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(728, 40, 90, 23);

        jLabel14.setText("Tgl.Lahir :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(644, 40, 80, 23);

        jLabel16.setText("Alamat :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 70, 72, 23);

        Alamat.setEditable(false);
        Alamat.setHighlighter(null);
        Alamat.setName("Alamat"); // NOI18N
        FormInput.add(Alamat);
        Alamat.setBounds(76, 70, 557, 23);

        jLabel18.setText("Tgl.Skrining :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(644, 70, 80, 23);

        TglGejala.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-05-2020" }));
        TglGejala.setDisplayFormat("dd-MM-yyyy");
        TglGejala.setName("TglGejala"); // NOI18N
        TglGejala.setOpaque(false);
        TglGejala.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglGejalaKeyPressed(evt);
            }
        });
        FormInput.add(TglGejala);
        TglGejala.setBounds(320, 200, 90, 23);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel19.setText("1. Demam > 37,5 / riwayat demam");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(70, 120, 460, 23);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel20.setText("2. Batuk / Pilek / Nyeri Tenggorokan");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(70, 150, 470, 23);

        A2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        A2.setName("A2"); // NOI18N
        A2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                A2ItemStateChanged(evt);
            }
        });
        A2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                A2KeyPressed(evt);
            }
        });
        FormInput.add(A2);
        A2.setBounds(733, 150, 85, 23);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel21.setText("3. Sesak Napas,");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(70, 180, 460, 23);

        A3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        A3.setName("A3"); // NOI18N
        A3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                A3ItemStateChanged(evt);
            }
        });
        A3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                A3KeyPressed(evt);
            }
        });
        FormInput.add(A3);
        A3.setBounds(733, 180, 85, 23);

        TglSkrining.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-05-2020" }));
        TglSkrining.setDisplayFormat("dd-MM-yyyy");
        TglSkrining.setName("TglSkrining"); // NOI18N
        TglSkrining.setOpaque(false);
        TglSkrining.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglSkriningKeyPressed(evt);
            }
        });
        FormInput.add(TglSkrining);
        TglSkrining.setBounds(728, 70, 90, 23);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel22.setText("Tanggal pertama kali timbul gejala");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(90, 200, 200, 23);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel23.setText("Riwayat periksa sebelumnya");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(90, 260, 200, 23);

        AsalDaerah.setHighlighter(null);
        AsalDaerah.setName("AsalDaerah"); // NOI18N
        AsalDaerah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalDaerahKeyPressed(evt);
            }
        });
        FormInput.add(AsalDaerah);
        AsalDaerah.setBounds(320, 330, 350, 23);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel24.setText(":");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(310, 260, 10, 23);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel25.setText(":");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(310, 200, 10, 23);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel26.setText(":");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(310, 230, 10, 23);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel27.setText("A. GEJALA ");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(15, 100, 120, 23);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel28.setText(":");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(722, 180, 10, 23);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText(":");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(722, 120, 10, 23);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText(":");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(722, 150, 10, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("2. Memiliki riwayat paparan satu atau lebih :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(70, 390, 585, 23);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText(":");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(722, 310, 10, 23);

        B1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        B1.setName("B1"); // NOI18N
        B1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                B1ItemStateChanged(evt);
            }
        });
        B1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                B1KeyPressed(evt);
            }
        });
        FormInput.add(B1);
        B1.setBounds(733, 310, 85, 23);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Asal daerah terjangkit");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(90, 330, 200, 23);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText(":");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(310, 330, 10, 23);

        RiwayatPeriksaSebelumnya.setHighlighter(null);
        RiwayatPeriksaSebelumnya.setName("RiwayatPeriksaSebelumnya"); // NOI18N
        RiwayatPeriksaSebelumnya.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RiwayatPeriksaSebelumnyaKeyPressed(evt);
            }
        });
        FormInput.add(RiwayatPeriksaSebelumnya);
        RiwayatPeriksaSebelumnya.setBounds(320, 260, 350, 23);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText(":");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(310, 360, 10, 23);

        TglKedatangan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-05-2020" }));
        TglKedatangan.setDisplayFormat("dd-MM-yyyy");
        TglKedatangan.setName("TglKedatangan"); // NOI18N
        TglKedatangan.setOpaque(false);
        TglKedatangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglKedatanganKeyPressed(evt);
            }
        });
        FormInput.add(TglKedatangan);
        TglKedatangan.setBounds(320, 360, 90, 23);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Tanggal kedatangan di kota ini");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(90, 360, 200, 23);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("1. Riwayat perjalanan ke Luar Negeri atau kota-kota terjangkit di Indonesia dalam waktu 14 hari sebelum timbul gejala");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(70, 310, 585, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText(":");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(722, 410, 10, 23);

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel39.setText("a. Riwayat kontak erat dengan kasus konfirmasi COVID-19 ATAU");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(90, 410, 560, 23);

        Kesimpulan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ODP", "PDP", "OTG", "Bukan ketiganya" }));
        Kesimpulan.setSelectedIndex(3);
        Kesimpulan.setName("Kesimpulan"); // NOI18N
        Kesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanKeyPressed(evt);
            }
        });
        FormInput.add(Kesimpulan);
        Kesimpulan.setBounds(81, 560, 130, 23);

        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel40.setText("b. Riwayat kontak dengan pasien dalam pengawasan (PDP)");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(90, 440, 560, 23);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel41.setText(":");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(722, 440, 10, 23);

        B2b.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        B2b.setName("B2b"); // NOI18N
        B2b.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                B2bItemStateChanged(evt);
            }
        });
        B2b.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                B2bKeyPressed(evt);
            }
        });
        FormInput.add(B2b);
        B2b.setBounds(733, 440, 85, 23);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel42.setText("c. Bekerja atau mengunjungi fasilitas kesehatan yang berhubungan dengan pasien konfirmasi COVID-19 ATAU");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(90, 470, 560, 23);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel43.setText(":");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(722, 470, 10, 23);

        B2c.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        B2c.setName("B2c"); // NOI18N
        B2c.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                B2cItemStateChanged(evt);
            }
        });
        B2c.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                B2cKeyPressed(evt);
            }
        });
        FormInput.add(B2c);
        B2c.setBounds(733, 470, 85, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText(":");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(722, 500, 10, 23);

        B2d.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        B2d.setName("B2d"); // NOI18N
        B2d.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                B2dItemStateChanged(evt);
            }
        });
        B2d.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                B2dKeyPressed(evt);
            }
        });
        FormInput.add(B2d);
        B2d.setBounds(733, 500, 85, 23);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel46.setText("d. Memiliki riwayat perjalanan ke luar negeri atau wilayah terjangkit yang telah mengkonfirmasi adanya kasus positif COVID-19");
        jLabel46.setName("jLabel46"); // NOI18N
        FormInput.add(jLabel46);
        jLabel46.setBounds(90, 500, 630, 23);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel47.setText("e. Dalam 14 hari terakhir berkunjung di pasar hewan");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(90, 530, 560, 23);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel48.setText(":");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(722, 530, 10, 23);

        B2e.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        B2e.setName("B2e"); // NOI18N
        B2e.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                B2eItemStateChanged(evt);
            }
        });
        B2e.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                B2eKeyPressed(evt);
            }
        });
        FormInput.add(B2e);
        B2e.setBounds(733, 530, 85, 23);

        jLabel49.setText("Kesimpulan :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(0, 560, 77, 23);

        B2a.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya" }));
        B2a.setName("B2a"); // NOI18N
        B2a.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                B2aItemStateChanged(evt);
            }
        });
        B2a.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                B2aKeyPressed(evt);
            }
        });
        FormInput.add(B2a);
        B2a.setBounds(733, 410, 85, 23);

        TindakLanjut.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rujuk", "Rawat Inap", "Rawat Jalan" }));
        TindakLanjut.setName("TindakLanjut"); // NOI18N
        TindakLanjut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakLanjutKeyPressed(evt);
            }
        });
        FormInput.add(TindakLanjut);
        TindakLanjut.setBounds(307, 560, 110, 23);

        jLabel50.setText("Tindak Lanjut :");
        jLabel50.setName("jLabel50"); // NOI18N
        FormInput.add(jLabel50);
        jLabel50.setBounds(218, 560, 85, 23);

        jLabel51.setText("Petugas :");
        jLabel51.setName("jLabel51"); // NOI18N
        FormInput.add(jLabel51);
        jLabel51.setBounds(411, 560, 70, 23);

        kdptg.setEditable(false);
        kdptg.setHighlighter(null);
        kdptg.setName("kdptg"); // NOI18N
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        FormInput.add(kdptg);
        kdptg.setBounds(485, 560, 110, 23);

        nmptg.setEditable(false);
        nmptg.setHighlighter(null);
        nmptg.setName("nmptg"); // NOI18N
        FormInput.add(nmptg);
        nmptg.setBounds(597, 560, 190, 23);

        BtnPtg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPtg.setMnemonic('X');
        BtnPtg.setToolTipText("Alt+X");
        BtnPtg.setName("BtnPtg"); // NOI18N
        BtnPtg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPtgActionPerformed(evt);
            }
        });
        BtnPtg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPtgKeyPressed(evt);
            }
        });
        FormInput.add(BtnPtg);
        BtnPtg.setBounds(790, 560, 28, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        HasilLaborat.setEditable(false);
        HasilLaborat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        HasilLaborat.setColumns(20);
        HasilLaborat.setRows(5);
        HasilLaborat.setName("HasilLaborat"); // NOI18N
        scrollPane5.setViewportView(HasilLaborat);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(81, 590, 300, 110);

        jLabel44.setText("Hasil Lab :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(0, 590, 77, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        HasilRadiologi.setEditable(false);
        HasilRadiologi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        HasilRadiologi.setColumns(20);
        HasilRadiologi.setRows(5);
        HasilRadiologi.setName("HasilRadiologi"); // NOI18N
        scrollPane6.setViewportView(HasilRadiologi);

        FormInput.add(scrollPane6);
        scrollPane6.setBounds(518, 590, 300, 110);

        jLabel52.setText("Hasil Radiologi :");
        jLabel52.setName("jLabel52"); // NOI18N
        FormInput.add(jLabel52);
        jLabel52.setBounds(394, 590, 120, 23);

        scrollInput.setViewportView(FormInput);

        PanelInput.add(scrollInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoRM.getText().trim().equals("")||NamaPasien.getText().trim().equals("")){
            Valid.textKosong(NoRM,"Pasien");
        }else if(kdptg.getText().trim().equals("")||nmptg.getText().trim().equals("")){
            Valid.textKosong(BtnPtg,"Petugas");
        }else{
            if(Sequel.menyimpantf("deteksi_dini_corona","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,new String[]{
                    NoRawat.getText(),Valid.SetTgl(TglSkrining.getSelectedItem()+""),kdptg.getText(), A1.getSelectedItem().toString(),A2.getSelectedItem().toString(), 
                    A3.getSelectedItem().toString(),(A3.getSelectedIndex()==0?"0000-00-00":Valid.SetTgl(TglGejala.getSelectedItem()+"")),(A3.getSelectedIndex()==0?"":RiwayatSakitSebelumnya.getText()),
                    (A3.getSelectedIndex()==0?"":RiwayatPeriksaSebelumnya.getText()),B1.getSelectedItem().toString(),(B1.getSelectedIndex()==0?"":AsalDaerah.getText()),
                    (B1.getSelectedIndex()==0?"0000-00-00":Valid.SetTgl(TglKedatangan.getSelectedItem()+"")),B2a.getSelectedItem().toString(),B2b.getSelectedItem().toString(),
                    B2c.getSelectedItem().toString(),B2d.getSelectedItem().toString(),B2e.getSelectedItem().toString(),Kesimpulan.getSelectedItem().toString(),TindakLanjut.getSelectedItem().toString()
                })==true){
                if(TindakLanjut.getSelectedIndex()==0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    DlgRujuk dlgrjk=new DlgRujuk(null,false);
                    dlgrjk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    dlgrjk.setLocationRelativeTo(internalFrame1);
                    dlgrjk.emptTeks();
                    dlgrjk.isCek();
                    dlgrjk.setNoRm(NoRawat.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                    dlgrjk.tampil();
                    dlgrjk.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }else if(TindakLanjut.getSelectedIndex()==0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    akses.setstatus(true);
                    DlgKamarInap dlgki=new DlgKamarInap(null,false);
                    dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    dlgki.setLocationRelativeTo(internalFrame1);
                    dlgki.emptTeks();
                    dlgki.isCek();
                    dlgki.setNoRm(NoRawat.getText(),NoRM.getText(),NamaPasien.getText());   
                    dlgki.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
                emptTeks();
                tampil();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
           //Valid.pindah(evt,Comorbid,BtnBatal);
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
            Sequel.meghapus("deteksi_dini_corona","no_rawat",tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            tampil();
        }else{
            JOptionPane.showMessageDialog(null,"Maaf silahkan pilih data terlebih dahulu..!!");
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnPrint);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
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
            Valid.MyReportqry("rptDeteksiDiniCorona.jasper","report","::[ Deteksi Dini Pasien Corona ]::",
                    "select deteksi_dini_corona.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,"+
                    "pasien.no_ktp,pasien.no_tlp,pasien.pekerjaan,pasien.tgl_lahir,deteksi_dini_corona.tanggal,deteksi_dini_corona.nip,petugas.nama,deteksi_dini_corona.gejala_demam,deteksi_dini_corona.gejala_batuk,"+
                    "deteksi_dini_corona.gejala_sesak,deteksi_dini_corona.gejala_tanggal_pertama,deteksi_dini_corona.gejala_riwayat_sakit,deteksi_dini_corona.gejala_riwayat_periksa,"+
                    "deteksi_dini_corona.faktor_riwayat_perjalanan,deteksi_dini_corona.faktor_asal_daerah,deteksi_dini_corona.faktor_tanggal_kedatangan,deteksi_dini_corona.faktor_paparan_kontakpositif,"+
                    "deteksi_dini_corona.faktor_paparan_kontakpdp,deteksi_dini_corona.faktor_paparan_faskespositif,deteksi_dini_corona.faktor_paparan_perjalananln,deteksi_dini_corona.faktor_paparan_pasarhewan,"+
                    "deteksi_dini_corona.kesimpulan,deteksi_dini_corona.tindak_lanjut from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join deteksi_dini_corona on deteksi_dini_corona.no_rawat=reg_periksa.no_rawat "+
                    "inner join petugas on deteksi_dini_corona.nip=petugas.nip "+
                    "where deteksi_dini_corona.tanggal between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' "+
                    (TCari.getText().trim().equals("")?"":"and (deteksi_dini_corona.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                    "pasien.no_rkm_medis like '%"+TCari.getText().trim()+"%' or pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                    "pasien.no_tlp like '%"+TCari.getText().trim()+"%' or deteksi_dini_corona.kesimpulan like '%"+TCari.getText().trim()+"%' or "+
                    "deteksi_dini_corona.tindak_lanjut like '%"+TCari.getText().trim()+"%')")+" order by deteksi_dini_corona.tanggal",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnCari, NamaPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

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

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(NoRM.getText().trim().equals("")||NamaPasien.getText().trim().equals("")){
            Valid.textKosong(NoRM,"Pasien");
        }else if(kdptg.getText().trim().equals("")||nmptg.getText().trim().equals("")){
            Valid.textKosong(BtnPtg,"Petugas");
        }else{
            if(tbObat.getSelectedRow()> -1){ 
                if(Sequel.mengedittf("deteksi_dini_corona","no_rawat=?","no_rawat=?,tanggal=?,nip=?,gejala_demam=?,gejala_batuk=?,gejala_sesak=?,gejala_tanggal_pertama=?,gejala_riwayat_sakit=?,gejala_riwayat_periksa=?,faktor_riwayat_perjalanan=?,faktor_asal_daerah=?,faktor_tanggal_kedatangan=?,faktor_paparan_kontakpositif=?,faktor_paparan_kontakpdp=?,faktor_paparan_faskespositif=?,faktor_paparan_perjalananln=?,faktor_paparan_pasarhewan=?,kesimpulan=?,tindak_lanjut=?",20,new String[]{
                        NoRawat.getText(),Valid.SetTgl(TglSkrining.getSelectedItem()+""),kdptg.getText(), A1.getSelectedItem().toString(),A2.getSelectedItem().toString(), 
                        A3.getSelectedItem().toString(),(A3.getSelectedIndex()==0?"0000-00-00":Valid.SetTgl(TglGejala.getSelectedItem()+"")),(A3.getSelectedIndex()==0?"":RiwayatSakitSebelumnya.getText()),
                        (A3.getSelectedIndex()==0?"":RiwayatPeriksaSebelumnya.getText()),B1.getSelectedItem().toString(),(B1.getSelectedIndex()==0?"":AsalDaerah.getText()),
                        (B1.getSelectedIndex()==0?"0000-00-00":Valid.SetTgl(TglKedatangan.getSelectedItem()+"")),B2a.getSelectedItem().toString(),B2b.getSelectedItem().toString(),
                        B2c.getSelectedItem().toString(),B2d.getSelectedItem().toString(),B2e.getSelectedItem().toString(),Kesimpulan.getSelectedItem().toString(),TindakLanjut.getSelectedItem().toString(),
                        tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
                    })==true){
                    emptTeks();
                    tampil();
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf silahkan pilih data terlebih dahulu..!!");
            }
        }
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void A1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_A1KeyPressed
        Valid.pindah(evt,TglSkrining,A2);
    }//GEN-LAST:event_A1KeyPressed

    private void RiwayatSakitSebelumnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatSakitSebelumnyaKeyPressed
        Valid.pindah(evt,TglGejala,RiwayatPeriksaSebelumnya);
    }//GEN-LAST:event_RiwayatSakitSebelumnyaKeyPressed

    private void TglGejalaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglGejalaKeyPressed
        Valid.pindah(evt,A3,RiwayatSakitSebelumnya);
    }//GEN-LAST:event_TglGejalaKeyPressed

    private void A2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_A2KeyPressed
        Valid.pindah(evt,A1,A3);
    }//GEN-LAST:event_A2KeyPressed

    private void A3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_A3KeyPressed
        Valid.pindah(evt,A2,TglGejala);
    }//GEN-LAST:event_A3KeyPressed

    private void TglSkriningKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglSkriningKeyPressed
        Valid.pindah(evt,TCari,A1);
    }//GEN-LAST:event_TglSkriningKeyPressed

    private void AsalDaerahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalDaerahKeyPressed
        Valid.pindah(evt,B1,TglKedatangan);
    }//GEN-LAST:event_AsalDaerahKeyPressed

    private void B1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_B1KeyPressed
        Valid.pindah(evt,RiwayatPeriksaSebelumnya,AsalDaerah);
    }//GEN-LAST:event_B1KeyPressed

    private void RiwayatPeriksaSebelumnyaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RiwayatPeriksaSebelumnyaKeyPressed
        Valid.pindah(evt,RiwayatSakitSebelumnya,B1);
    }//GEN-LAST:event_RiwayatPeriksaSebelumnyaKeyPressed

    private void TglKedatanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglKedatanganKeyPressed
        Valid.pindah(evt,AsalDaerah,B2a);
    }//GEN-LAST:event_TglKedatanganKeyPressed

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        Valid.pindah(evt,B2e,TindakLanjut);
    }//GEN-LAST:event_KesimpulanKeyPressed

    private void B2bKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_B2bKeyPressed
        Valid.pindah(evt,B2a,B2c);
    }//GEN-LAST:event_B2bKeyPressed

    private void B2cKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_B2cKeyPressed
        Valid.pindah(evt,B2b,B2d);
    }//GEN-LAST:event_B2cKeyPressed

    private void B2dKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_B2dKeyPressed
        Valid.pindah(evt,B2c,B2e);
    }//GEN-LAST:event_B2dKeyPressed

    private void B2eKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_B2eKeyPressed
        Valid.pindah(evt,B2d,Kesimpulan);
    }//GEN-LAST:event_B2eKeyPressed

    private void B2aKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_B2aKeyPressed
        Valid.pindah(evt,TglKedatangan,B2b);
    }//GEN-LAST:event_B2aKeyPressed

    private void TindakLanjutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakLanjutKeyPressed
        Valid.pindah(evt,Kesimpulan,BtnSimpan);
    }//GEN-LAST:event_TindakLanjutKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdptgKeyPressed

    private void BtnPtgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPtgActionPerformed
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPtgActionPerformed

    private void BtnPtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPtgKeyPressed
        Valid.pindah(evt,TindakLanjut,BtnSimpan);
    }//GEN-LAST:event_BtnPtgKeyPressed

    private void A1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_A1ItemStateChanged
        isHitung();
    }//GEN-LAST:event_A1ItemStateChanged

    private void A2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_A2ItemStateChanged
        isHitung();
    }//GEN-LAST:event_A2ItemStateChanged

    private void A3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_A3ItemStateChanged
        isHitung();
    }//GEN-LAST:event_A3ItemStateChanged

    private void B1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_B1ItemStateChanged
        isHitung();
    }//GEN-LAST:event_B1ItemStateChanged

    private void B2aItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_B2aItemStateChanged
        isHitung();
    }//GEN-LAST:event_B2aItemStateChanged

    private void B2bItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_B2bItemStateChanged
        isHitung();
    }//GEN-LAST:event_B2bItemStateChanged

    private void B2cItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_B2cItemStateChanged
        isHitung();
    }//GEN-LAST:event_B2cItemStateChanged

    private void B2dItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_B2dItemStateChanged
        isHitung();
    }//GEN-LAST:event_B2dItemStateChanged

    private void B2eItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_B2eItemStateChanged
        isHitung();
    }//GEN-LAST:event_B2eItemStateChanged

    private void MnCetakDeteksiDiniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakDeteksiDiniActionPerformed
        if(NamaPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());
            param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",kdptg.getText()));
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptDeteksiCorona.jasper","report","::[ Form Deteksi Dini Corona ]::",
                "select deteksi_dini_corona.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,pasien.umur,pasien.agama,"+
                "pasien.no_ktp,pasien.no_tlp,pasien.pekerjaan,date_format(pasien.tgl_lahir,'%d/%m/%Y')as tgl_lahir,date_format(deteksi_dini_corona.tanggal,'%d/%m/%Y')as tanggal,deteksi_dini_corona.nip,petugas.nama,deteksi_dini_corona.gejala_demam,deteksi_dini_corona.gejala_batuk,"+
                "deteksi_dini_corona.gejala_sesak,deteksi_dini_corona.gejala_tanggal_pertama,deteksi_dini_corona.gejala_riwayat_sakit,deteksi_dini_corona.gejala_riwayat_periksa,pasien.jk,pasien.stts_nikah,"+
                "deteksi_dini_corona.faktor_riwayat_perjalanan,deteksi_dini_corona.faktor_asal_daerah,deteksi_dini_corona.faktor_tanggal_kedatangan,deteksi_dini_corona.faktor_paparan_kontakpositif,"+
                "deteksi_dini_corona.faktor_paparan_kontakpdp,deteksi_dini_corona.faktor_paparan_faskespositif,deteksi_dini_corona.faktor_paparan_perjalananln,deteksi_dini_corona.faktor_paparan_pasarhewan,"+
                "deteksi_dini_corona.kesimpulan,deteksi_dini_corona.tindak_lanjut from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                "inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "inner join deteksi_dini_corona on deteksi_dini_corona.no_rawat=reg_periksa.no_rawat "+
                "inner join petugas on deteksi_dini_corona.nip=petugas.nip "+
                "where deteksi_dini_corona.no_rawat='"+NoRawat.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakDeteksiDiniActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMDeteksiDiniCorona dialog = new RMDeteksiDiniCorona(new javax.swing.JFrame(), true);
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
    private widget.ComboBox A1;
    private widget.ComboBox A2;
    private widget.ComboBox A3;
    private widget.TextBox Alamat;
    private widget.TextBox AsalDaerah;
    private widget.ComboBox B1;
    private widget.ComboBox B2a;
    private widget.ComboBox B2b;
    private widget.ComboBox B2c;
    private widget.ComboBox B2d;
    private widget.ComboBox B2e;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnPtg;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.PanelBiasa FormInput;
    private widget.TextArea HasilLaborat;
    private widget.TextArea HasilRadiologi;
    private widget.ComboBox Kesimpulan;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnCetakDeteksiDini;
    private widget.TextBox NIK;
    private widget.TextBox NamaPasien;
    private widget.TextBox NoHP;
    private widget.TextBox NoRM;
    private widget.TextBox NoRawat;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox Pekerjaan;
    private widget.TextBox RiwayatPeriksaSebelumnya;
    private widget.TextBox RiwayatSakitSebelumnya;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal TglGejala;
    private widget.Tanggal TglKedatangan;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglSkrining;
    private widget.ComboBox TindakLanjut;
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
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel50;
    private widget.Label jLabel51;
    private widget.Label jLabel52;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdptg;
    private widget.TextBox nmptg;
    private widget.panelisi panelGlass7;
    private widget.panelisi panelGlass8;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {     
        Valid.tabelKosong(tabMode);
        try{    
            ps=koneksi.prepareStatement(
                    "select deteksi_dini_corona.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,"+
                    "pasien.no_ktp,pasien.no_tlp,pasien.pekerjaan,pasien.tgl_lahir,deteksi_dini_corona.tanggal,deteksi_dini_corona.nip,petugas.nama,deteksi_dini_corona.gejala_demam,deteksi_dini_corona.gejala_batuk,"+
                    "deteksi_dini_corona.gejala_sesak,deteksi_dini_corona.gejala_tanggal_pertama,deteksi_dini_corona.gejala_riwayat_sakit,deteksi_dini_corona.gejala_riwayat_periksa,"+
                    "deteksi_dini_corona.faktor_riwayat_perjalanan,deteksi_dini_corona.faktor_asal_daerah,deteksi_dini_corona.faktor_tanggal_kedatangan,deteksi_dini_corona.faktor_paparan_kontakpositif,"+
                    "deteksi_dini_corona.faktor_paparan_kontakpdp,deteksi_dini_corona.faktor_paparan_faskespositif,deteksi_dini_corona.faktor_paparan_perjalananln,deteksi_dini_corona.faktor_paparan_pasarhewan,"+
                    "deteksi_dini_corona.kesimpulan,deteksi_dini_corona.tindak_lanjut from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join deteksi_dini_corona on deteksi_dini_corona.no_rawat=reg_periksa.no_rawat "+
                    "inner join petugas on deteksi_dini_corona.nip=petugas.nip "+
                    "where deteksi_dini_corona.tanggal between ? and ? "+
                    (TCari.getText().trim().equals("")?"":"and (deteksi_dini_corona.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or pasien.no_tlp like ? or "+
                    "deteksi_dini_corona.kesimpulan like ? or deteksi_dini_corona.tindak_lanjut like ?)")+"order by deteksi_dini_corona.tanggal");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().trim().equals("")){
                    ps.setString(3,"%"+TCari.getText().trim()+"%");
                    ps.setString(4,"%"+TCari.getText().trim()+"%");
                    ps.setString(5,"%"+TCari.getText().trim()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){    
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("asal"),rs.getString("no_ktp"),rs.getString("no_tlp"),
                        rs.getString("pekerjaan"),rs.getString("tgl_lahir"),rs.getString("tanggal"),rs.getString("nip"),rs.getString("nama"),rs.getString("gejala_demam"),
                        rs.getString("gejala_batuk"),rs.getString("gejala_sesak"),rs.getString("gejala_tanggal_pertama"),rs.getString("gejala_riwayat_sakit"),rs.getString("gejala_riwayat_periksa"),
                        rs.getString("faktor_riwayat_perjalanan"),rs.getString("faktor_asal_daerah"),rs.getString("faktor_tanggal_kedatangan"),rs.getString("faktor_paparan_kontakpositif"),
                        rs.getString("faktor_paparan_kontakpdp"),rs.getString("faktor_paparan_faskespositif"),rs.getString("faktor_paparan_perjalananln"),rs.getString("faktor_paparan_pasarhewan"),
                        rs.getString("kesimpulan"),rs.getString("tindak_lanjut")
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        NoRM.setText("");
        NamaPasien.setText("");
        NoRawat.setText("");
        NIK.setText("");
        NoHP.setText("");
        Pekerjaan.setText("");
        TglLahir.setText("");
        Alamat.setText("");
        TglSkrining.setDate(new Date());
        A1.setSelectedIndex(0);
        A2.setSelectedIndex(0);
        A3.setSelectedIndex(0);
        TglGejala.setDate(new Date());
        RiwayatPeriksaSebelumnya.setText("");
        RiwayatSakitSebelumnya.setText("");
        B1.setSelectedIndex(0);
        AsalDaerah.setText("");
        TglKedatangan.setDate(new Date());
        B2a.setSelectedIndex(0);
        B2b.setSelectedIndex(0);
        B2c.setSelectedIndex(0);
        B2d.setSelectedIndex(0);
        B2e.setSelectedIndex(0);
        Kesimpulan.setSelectedIndex(3);
        TindakLanjut.setSelectedIndex(0);
        HasilLaborat.setText("");
        HasilRadiologi.setText("");
        ChkInput.setSelected(true);
        isForm(); 
        A1.requestFocus();
    }

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){      
            NoRawat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString());
            NoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            NamaPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            Alamat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            NIK.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString());
            NoHP.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString());
            Pekerjaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            kdptg.setText(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            nmptg.setText(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            A1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            A2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString());
            A3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString());
            RiwayatSakitSebelumnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            RiwayatPeriksaSebelumnya.setText(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            B1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());
            AsalDaerah.setText(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            B2a.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            B2b.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            B2c.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            B2d.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            B2e.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            Kesimpulan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            TindakLanjut.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            isLabRad(NoRawat.getText());
            Valid.SetTgl(TglSkrining,tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Valid.SetTgl(TglGejala,tbObat.getValueAt(tbObat.getSelectedRow(),14).toString());
            Valid.SetTgl(TglKedatangan,tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
        }
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,this.getHeight()-122));
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
        BtnSimpan.setEnabled(akses.getdeteksi_corona());
        BtnHapus.setEnabled(akses.getdeteksi_corona());
        BtnEdit.setEnabled(akses.getdeteksi_corona());
        if(akses.getjml2()>=1){
            kdptg.setEditable(false);
            BtnPtg.setEnabled(false);
            kdptg.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        }
    }
    
    public void setNoRm(String norawat,Date tgl2){
        NoRawat.setText(norawat);
        TCari.setText(norawat);
        try {
            ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,"+
                    "pasien.no_ktp,pasien.no_tlp,pasien.pekerjaan,pasien.tgl_lahir "+
                    "from pasien inner join kelurahan on pasien.kd_kel=kelurahan.kd_kel "+
                    "inner join kecamatan on pasien.kd_kec=kecamatan.kd_kec "+
                    "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab "+
                    "inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.no_rawat=?");
            try {            
                ps.setString(1,norawat);
                rs=ps.executeQuery();
                while(rs.next()){
                    NoRM.setText(rs.getString("no_rkm_medis"));
                    NamaPasien.setText(rs.getString("nm_pasien"));
                    NIK.setText(rs.getString("no_ktp"));
                    NoHP.setText(rs.getString("no_tlp"));
                    Pekerjaan.setText(rs.getString("pekerjaan"));
                    Alamat.setText(rs.getString("asal"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                }
            } catch (Exception ex) {
                System.out.println(ex);
            }finally{
                if(rs != null ){
                    rs.close();
                }
                
                if(ps != null ){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        DTPCari2.setDate(tgl2);  
        isLabRad(norawat);
    }
    
    private void isLabRad(String norawat){
        try{
            HasilRadiologi.setText("");
            ps=koneksi.prepareStatement(
                    "select hasil from hasil_radiologi where "+
                    "no_rawat=? ");
            try{
                ps.setString(1,norawat);
                rs=ps.executeQuery();
                while(rs.next()){
                    HasilRadiologi.append(rs.getString("hasil")+"\n\n");
                }
            }catch(Exception ex){
                System.out.println(ex);
            }finally{
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
        
        try{
            HasilLaborat.setText("");
            ps=koneksi.prepareStatement(
                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai "+
                    "from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template where "+
                    "detail_periksa_lab.no_rawat=?");
            try{
                ps.setString(1,norawat);
                rs=ps.executeQuery();
                while(rs.next()){
                    HasilLaborat.append(rs.getString("Pemeriksaan")+" : "+rs.getString("nilai")+"\n");
                }
            }catch(Exception ex){
                System.out.println(ex);
            }finally{
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
    
    public JTable getTable(){
        return tbObat;
    }
    
    private void isHitung(){
        gejala1=0;gejala2=0;gejala3=0;resiko1=0;resiko2a=0;resiko2b=0;resiko2c=0;resiko2d=0;resiko2e=0;gejala=0;resiko=0;
        if(A1.getSelectedItem().toString().equals("Ya")){
            gejala1=2;
        }
        if(A2.getSelectedItem().toString().equals("Ya")){
            gejala2=2;
        }
        if(A3.getSelectedItem().toString().equals("Ya")){
            gejala3=1;
        }
        if(B1.getSelectedItem().toString().equals("Ya")){
            resiko1=1;
        }
        if(B2a.getSelectedItem().toString().equals("Ya")){
            resiko2a=2;
        }
        if(B2b.getSelectedItem().toString().equals("Ya")){
            resiko2b=2;
        }
        if(B2c.getSelectedItem().toString().equals("Ya")){
            resiko2c=2;
        }
        if(B2d.getSelectedItem().toString().equals("Ya")){
            resiko2d=2;
        }
        if(B2e.getSelectedItem().toString().equals("Ya")){
            resiko2e=2;
        }
        
        gejala=gejala1+gejala2+gejala3;
        resiko=resiko1+resiko2a+resiko2b+resiko2c+resiko2d+resiko2e;
        if((gejala==5)&&(resiko==1)){
            Kesimpulan.setSelectedItem("PDP");
        }else if((gejala==2)&&(resiko>1)){
            Kesimpulan.setSelectedItem("PDP");
        }else if((gejala==4)&&(resiko>1)){
            Kesimpulan.setSelectedItem("PDP");
        }else if((gejala==2)&&(resiko==1)){
            Kesimpulan.setSelectedItem("ODP");
        }else if((gejala==4)&&(resiko==1)){
            Kesimpulan.setSelectedItem("ODP");
        }else if((gejala==0)&&(resiko>1)){
            Kesimpulan.setSelectedItem("OTG");
        }else{
            Kesimpulan.setSelectedItem("Bukan ketiganya");
        }
    }

}

/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
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
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import restore.DlgRestorePetugas;
import simrskhanza.DlgCariJabatan;

/**
 *
 * @author dosen
 */
public final class DlgPetugas extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);

    /** Creates new form DlgPetugas
     * @param parent
     * @param modal */
    public DlgPetugas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] row={"NIP","Nama Petugas","J.K.","Tmp.Lahir","Tgl.Lahir","G.D.","Agama","Stts.Nikah","Alamat","Jabatan","No.Telp"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbPetugas.setModel(tabMode);


        //tbPetugas.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbPetugas.getBackground()));
        tbPetugas.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPetugas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 11; i++) {
            TableColumn column = tbPetugas.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(40);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(40);
            }else if(i==6){
                column.setPreferredWidth(110);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else if(i==9){
                column.setPreferredWidth(200);
            }else if(i==10){
                column.setPreferredWidth(100);
            }
        }
        tbPetugas.setDefaultRenderer(Object.class, new WarnaTable());

        TNip.setDocument(new batasInput((byte)20).getKata(TNip));
        KdJbtn.setDocument(new batasInput((byte)4).getKata(KdJbtn));
        TNm.setDocument(new batasInput((byte)50).getKata(TNm));
        TTmp.setDocument(new batasInput((byte)20).getKata(TTmp));
        TAlmt.setDocument(new batasInput((byte)60).getKata(TAlmt));
        TTlp.setDocument(new batasInput((byte)13).getOnlyAngka(TTlp));
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
        
        jabatan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jabatan.getTable().getSelectedRow()!= -1){                   
                    KdJbtn.setText(jabatan.getTable().getValueAt(jabatan.getTable().getSelectedRow(),0).toString());
                    TJbtn.setText(jabatan.getTable().getValueAt(jabatan.getTable().getSelectedRow(),1).toString());
                }   
                KdJbtn.requestFocus();
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
                
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){                   
                    TNip.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                    TNm.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                    CmbJk.setSelectedItem(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),2).toString().replaceAll("Wanita","PEREMPUAN").replaceAll("Pria","LAKI-LAKI"));
                    TTmp.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),11).toString());
                    TAlmt.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),13).toString());
                    Valid.SetTgl(DTPLahir,pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),12).toString());
                }   
                TNip.requestFocus();
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
        ChkInput.setSelected(false);
        isForm(); 
    }
    
    private DlgCariJabatan jabatan=new DlgCariJabatan(null,false);

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        MnRestore = new javax.swing.JMenuItem();
        MnKartu = new javax.swing.JMenuItem();
        MnKartu1 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPetugas = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel14 = new widget.Label();
        cmbCrJk = new widget.ComboBox();
        jLabel11 = new widget.Label();
        CmbCrGd = new widget.ComboBox();
        jLabel16 = new widget.Label();
        CmbCrStts = new widget.ComboBox();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TTmp = new widget.TextBox();
        CmbJk = new widget.ComboBox();
        TNm = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel9 = new widget.Label();
        CMbGd = new widget.ComboBox();
        jLabel13 = new widget.Label();
        DTPLahir = new widget.Tanggal();
        jLabel18 = new widget.Label();
        cmbAgama = new widget.ComboBox();
        jLabel19 = new widget.Label();
        CmbStts = new widget.ComboBox();
        jLabel20 = new widget.Label();
        jLabel21 = new widget.Label();
        jLabel12 = new widget.Label();
        TJbtn = new widget.TextBox();
        TAlmt = new widget.TextBox();
        TTlp = new widget.TextBox();
        TNip = new widget.TextBox();
        KdJbtn = new widget.TextBox();
        btnJabatan = new widget.Button();
        BtnCariPegawai = new widget.Button();
        ChkInput = new widget.CekBox();

        Popup.setName("Popup"); // NOI18N

        MnRestore.setBackground(new java.awt.Color(255, 255, 254));
        MnRestore.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRestore.setForeground(new java.awt.Color(50, 50, 50));
        MnRestore.setText("Data Sampah");
        MnRestore.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRestore.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRestore.setName("MnRestore"); // NOI18N
        MnRestore.setPreferredSize(new java.awt.Dimension(180, 28));
        MnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRestoreActionPerformed(evt);
            }
        });
        Popup.add(MnRestore);

        MnKartu.setBackground(new java.awt.Color(255, 255, 254));
        MnKartu.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu.setForeground(new java.awt.Color(50, 50, 50));
        MnKartu.setText("Cetak Kartu Petugas");
        MnKartu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKartu.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKartu.setName("MnKartu"); // NOI18N
        MnKartu.setPreferredSize(new java.awt.Dimension(180, 28));
        MnKartu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartuActionPerformed(evt);
            }
        });
        Popup.add(MnKartu);

        MnKartu1.setBackground(new java.awt.Color(255, 255, 254));
        MnKartu1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnKartu1.setForeground(new java.awt.Color(50, 50, 50));
        MnKartu1.setText("Cetak Semua Kartu Petugas");
        MnKartu1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnKartu1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnKartu1.setName("MnKartu1"); // NOI18N
        MnKartu1.setPreferredSize(new java.awt.Dimension(180, 28));
        MnKartu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnKartu1ActionPerformed(evt);
            }
        });
        Popup.add(MnKartu1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Petugas ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPetugas.setAutoCreateRowSorter(true);
        tbPetugas.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbPetugas.setComponentPopupMenu(Popup);
        tbPetugas.setName("tbPetugas"); // NOI18N
        tbPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPetugasMouseClicked(evt);
            }
        });
        tbPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPetugasKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbPetugas);

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
        panelGlass8.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
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
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 10));

        jLabel14.setText("J.K. :");
        jLabel14.setName("jLabel14"); // NOI18N
        jLabel14.setPreferredSize(new java.awt.Dimension(40, 23));
        panelGlass9.add(jLabel14);

        cmbCrJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "LAKI-LAKI", "PEREMPUAN" }));
        cmbCrJk.setLightWeightPopupEnabled(false);
        cmbCrJk.setName("cmbCrJk"); // NOI18N
        cmbCrJk.setPreferredSize(new java.awt.Dimension(120, 23));
        cmbCrJk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCrJkItemStateChanged(evt);
            }
        });
        cmbCrJk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbCrJkKeyPressed(evt);
            }
        });
        panelGlass9.add(cmbCrJk);

        jLabel11.setText("G.D. :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(42, 23));
        panelGlass9.add(jLabel11);

        CmbCrGd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "A", "B", "AB", "O", "-" }));
        CmbCrGd.setLightWeightPopupEnabled(false);
        CmbCrGd.setName("CmbCrGd"); // NOI18N
        CmbCrGd.setPreferredSize(new java.awt.Dimension(70, 23));
        CmbCrGd.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCrJkItemStateChanged(evt);
            }
        });
        CmbCrGd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbCrGdKeyPressed(evt);
            }
        });
        panelGlass9.add(CmbCrGd);

        jLabel16.setText("Stts.Nikah :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel16);

        CmbCrStts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "MENIKAH", "BELUM MENIKAH", "JANDA", "DUDHA", "JOMBLO" }));
        CmbCrStts.setLightWeightPopupEnabled(false);
        CmbCrStts.setName("CmbCrStts"); // NOI18N
        CmbCrStts.setPreferredSize(new java.awt.Dimension(130, 23));
        CmbCrStts.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCrJkItemStateChanged(evt);
            }
        });
        CmbCrStts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbCrSttsKeyPressed(evt);
            }
        });
        panelGlass9.add(CmbCrStts);

        jSeparator5.setBackground(new java.awt.Color(220, 225, 215));
        jSeparator5.setForeground(new java.awt.Color(220, 225, 215));
        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator5.setName("jSeparator5"); // NOI18N
        jSeparator5.setOpaque(true);
        jSeparator5.setPreferredSize(new java.awt.Dimension(1, 23));
        panelGlass9.add(jSeparator5);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(850, 137));
        FormInput.setLayout(null);

        jLabel3.setText("NIP :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 105, 23);

        jLabel4.setText("Nama Petugas :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 42, 105, 23);

        TTmp.setName("TTmp"); // NOI18N
        TTmp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTmpKeyPressed(evt);
            }
        });
        FormInput.add(TTmp);
        TTmp.setBounds(109, 102, 206, 23);

        CmbJk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "LAKI-LAKI", "PEREMPUAN" }));
        CmbJk.setName("CmbJk"); // NOI18N
        CmbJk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJkKeyPressed(evt);
            }
        });
        FormInput.add(CmbJk);
        CmbJk.setBounds(109, 72, 140, 23);

        TNm.setHighlighter(null);
        TNm.setName("TNm"); // NOI18N
        TNm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmKeyPressed(evt);
            }
        });
        FormInput.add(TNm);
        TNm.setBounds(109, 42, 310, 23);

        jLabel8.setText("Jenis Kelamin :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 72, 105, 23);

        jLabel9.setBackground(new java.awt.Color(120, 30, 120));
        jLabel9.setText("Gol. Darah :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(235, 72, 110, 23);

        CMbGd.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B", "AB", "O", "-" }));
        CMbGd.setName("CMbGd"); // NOI18N
        CMbGd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CMbGdKeyPressed(evt);
            }
        });
        FormInput.add(CMbGd);
        CMbGd.setBounds(349, 72, 70, 23);

        jLabel13.setText("Tmp/Tgl. Lahir :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 102, 105, 23);

        DTPLahir.setForeground(new java.awt.Color(50, 70, 50));
        DTPLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-12-2020" }));
        DTPLahir.setDisplayFormat("dd-MM-yyyy");
        DTPLahir.setName("DTPLahir"); // NOI18N
        DTPLahir.setOpaque(false);
        DTPLahir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPLahirKeyPressed(evt);
            }
        });
        FormInput.add(DTPLahir);
        DTPLahir.setBounds(319, 102, 100, 23);

        jLabel18.setText("Agama :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(431, 12, 80, 23);

        cmbAgama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ISLAM", "KRISTEN", "KATOLIK", "HINDU", "BUDHA", "KONG HU CHU", "-" }));
        cmbAgama.setLightWeightPopupEnabled(false);
        cmbAgama.setName("cmbAgama"); // NOI18N
        cmbAgama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbAgamaKeyPressed(evt);
            }
        });
        FormInput.add(cmbAgama);
        cmbAgama.setBounds(515, 12, 125, 23);

        jLabel19.setText("Stts. Nikah :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(636, 12, 100, 23);

        CmbStts.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MENIKAH", "BELUM MENIKAH", "JANDA", "DUDHA", "JOMBLO" }));
        CmbStts.setLightWeightPopupEnabled(false);
        CmbStts.setName("CmbStts"); // NOI18N
        CmbStts.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbSttsKeyPressed(evt);
            }
        });
        FormInput.add(CmbStts);
        CmbStts.setBounds(740, 12, 135, 23);

        jLabel20.setText("Alamat :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(431, 42, 80, 23);

        jLabel21.setText("No.Telp :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(431, 72, 80, 23);

        jLabel12.setText("Jabatan  :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(431, 102, 80, 23);

        TJbtn.setEditable(false);
        TJbtn.setName("TJbtn"); // NOI18N
        FormInput.add(TJbtn);
        TJbtn.setBounds(603, 102, 242, 23);

        TAlmt.setHighlighter(null);
        TAlmt.setName("TAlmt"); // NOI18N
        TAlmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlmtKeyPressed(evt);
            }
        });
        FormInput.add(TAlmt);
        TAlmt.setBounds(515, 42, 360, 23);

        TTlp.setHighlighter(null);
        TTlp.setName("TTlp"); // NOI18N
        TTlp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TTlpKeyPressed(evt);
            }
        });
        FormInput.add(TTlp);
        TTlp.setBounds(515, 72, 150, 23);

        TNip.setHighlighter(null);
        TNip.setName("TNip"); // NOI18N
        TNip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNipKeyPressed(evt);
            }
        });
        FormInput.add(TNip);
        TNip.setBounds(109, 12, 200, 23);

        KdJbtn.setHighlighter(null);
        KdJbtn.setName("KdJbtn"); // NOI18N
        KdJbtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdJbtnKeyPressed(evt);
            }
        });
        FormInput.add(KdJbtn);
        KdJbtn.setBounds(515, 102, 86, 23);

        btnJabatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnJabatan.setMnemonic('1');
        btnJabatan.setToolTipText("ALt+1");
        btnJabatan.setName("btnJabatan"); // NOI18N
        btnJabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJabatanActionPerformed(evt);
            }
        });
        FormInput.add(btnJabatan);
        btnJabatan.setBounds(847, 102, 28, 23);

        BtnCariPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCariPegawai.setMnemonic('1');
        BtnCariPegawai.setToolTipText("ALt+1");
        BtnCariPegawai.setName("BtnCariPegawai"); // NOI18N
        BtnCariPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariPegawaiActionPerformed(evt);
            }
        });
        FormInput.add(BtnCariPegawai);
        BtnCariPegawai.setBounds(312, 12, 28, 23);

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

    private void TTmpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTmpKeyPressed
        Valid.pindah(evt,CMbGd,DTPLahir);
}//GEN-LAST:event_TTmpKeyPressed

    private void CmbJkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJkKeyPressed
        Valid.pindah(evt,TNm,CMbGd);
}//GEN-LAST:event_CmbJkKeyPressed

    private void TNmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmKeyPressed
        Valid.pindah(evt,TNip,CmbJk);
}//GEN-LAST:event_TNmKeyPressed

    private void CMbGdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CMbGdKeyPressed
        Valid.pindah(evt,CmbJk,TTmp);
}//GEN-LAST:event_CMbGdKeyPressed

    private void DTPLahirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPLahirKeyPressed
        Valid.pindah(evt,TTmp,cmbAgama);
}//GEN-LAST:event_DTPLahirKeyPressed

    private void cmbAgamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbAgamaKeyPressed
        Valid.pindah(evt,DTPLahir,CmbStts);
}//GEN-LAST:event_cmbAgamaKeyPressed

    private void CmbSttsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbSttsKeyPressed
        Valid.pindah(evt,cmbAgama,TAlmt);
}//GEN-LAST:event_CmbSttsKeyPressed

    private void TAlmtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlmtKeyPressed
        Valid.pindah(evt,CmbStts,TTlp);
}//GEN-LAST:event_TAlmtKeyPressed

    private void TTlpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TTlpKeyPressed
        Valid.pindah(evt,TAlmt,KdJbtn);
}//GEN-LAST:event_TTlpKeyPressed

    private void TNipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNipKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){  
            BtnCariPegawaiActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,TNm,TCari);
        }
}//GEN-LAST:event_TNipKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNip.getText().trim().equals("")){
            Valid.textKosong(TNip,"NIP");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"nama petugas");
        }else if(TJbtn.getText().trim().equals("")||KdJbtn.getText().trim().equals("")){
            Valid.textKosong(KdJbtn,"jabatan");
        }else{
            try {
                Sequel.AutoComitFalse();
                Sequel.menyimpanignore("jnj_jabatan","?,?,?,?",4,new String[]{"-","-","0","0"});
                Sequel.menyimpanignore("departemen","?,?",2,new String[]{"-","-"});
                Sequel.menyimpanignore("bidang","?",1,new String[]{"-"});
                Sequel.menyimpanignore("bank","'T'");
                Sequel.menyimpanignore("stts_wp","?,?",2,new String[]{"-","-"});
                Sequel.menyimpanignore("stts_kerja","?,?,?",3,new String[]{"-","-","0"});
                Sequel.menyimpanignore("kelompok_jabatan","?,?,?",3,new String[]{"-","-","0"});
                Sequel.menyimpanignore("resiko_kerja","?,?,?",3,new String[]{"-","-","0"});
                Sequel.menyimpanignore("emergency_index","?,?,?",3,new String[]{"-","-","0"});
                Sequel.menyimpanignore("pendidikan","?,?,?,?,?",5,new String[]{"-","0","0","0","0"});
                Sequel.menyimpanignore("pegawai","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",34,new String[]{
                    "0",TNip.getText(),TNm.getText(),CmbJk.getSelectedItem().toString().replaceAll("PEREMPUAN","Wanita").replaceAll("LAKI-LAKI","Pria"),
                    "-","-","-","-","-","-","-","-","-","-","-","0",TTmp.getText(),Valid.SetTgl(DTPLahir.getSelectedItem()+""),TAlmt.getText(),"-","1900-01-01","<1",
                    "-","T","-","AKTIF","0","0","0","1900-01-01","0","0","pages/pegawai/photo/","-"
                });
                
                Sequel.menyimpan("petugas","?,?,?,?,?,?,?,?,?,?,?,?","NIP",12,new String[]{
                    TNip.getText(),TNm.getText(),CmbJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim(),
                    TTmp.getText(),Valid.SetTgl(DTPLahir.getSelectedItem()+""),CMbGd.getSelectedItem().toString(),
                    cmbAgama.getSelectedItem().toString(),CmbStts.getSelectedItem().toString(),
                    TAlmt.getText(),KdJbtn.getText(),TTlp.getText(),"1"
                });
                Sequel.Commit();
                Sequel.AutoComitTrue();
                tampil();
                emptTeks();
            } catch (Exception ex) {
                System.out.println("Notif : "+ex);
            }            
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KdJbtn,BtnBatal);
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
        try {
            Sequel.mengedit("petugas","nip='"+TNip.getText()+"'","status='0'");
            tampil();
            emptTeks();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        } 
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Map<String, Object> param = new HashMap<>();  
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                Valid.MyReportqry("rptPetugas.jasper","report","::[ Data Petugas ]::","select petugas.nip,petugas.nama,petugas.jk,petugas.tmp_lahir,petugas.tgl_lahir, "+
                    "petugas.gol_darah,petugas.agama,petugas.stts_nikah,petugas.alamat,jabatan.nm_jbtn,petugas.no_telp "+
                    "from petugas inner join jabatan on jabatan.kd_jbtn=petugas.kd_jbtn "+
                    "where petugas.status='1' and petugas.jk like '%"+cmbCrJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+"%' and petugas.gol_darah like '%"+CmbCrGd.getSelectedItem().toString().trim()+"%' and petugas.stts_nikah like '%"+CmbCrStts.getSelectedItem().toString().trim()+"%' and petugas.nip like '%"+TCari.getText().trim()+"%' or "+
                    " petugas.status='1' and petugas.jk like '%"+cmbCrJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+"%' and petugas.gol_darah like '%"+CmbCrGd.getSelectedItem().toString().trim()+"%' and petugas.stts_nikah like '%"+CmbCrStts.getSelectedItem().toString().trim()+"%' and petugas.nama like '%"+TCari.getText().trim()+"%' or "+
                    " petugas.status='1' and petugas.jk like '%"+cmbCrJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+"%' and petugas.gol_darah like '%"+CmbCrGd.getSelectedItem().toString().trim()+"%' and petugas.stts_nikah like '%"+CmbCrStts.getSelectedItem().toString().trim()+"%' and petugas.jk like '%"+TCari.getText().trim()+"%' or "+
                    " petugas.status='1' and petugas.jk like '%"+cmbCrJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+"%' and petugas.gol_darah like '%"+CmbCrGd.getSelectedItem().toString().trim()+"%' and petugas.stts_nikah like '%"+CmbCrStts.getSelectedItem().toString().trim()+"%' and petugas.tmp_lahir like '%"+TCari.getText().trim()+"%' or "+
                    " petugas.status='1' and petugas.jk like '%"+cmbCrJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+"%' and petugas.gol_darah like '%"+CmbCrGd.getSelectedItem().toString().trim()+"%' and petugas.stts_nikah like '%"+CmbCrStts.getSelectedItem().toString().trim()+"%' and petugas.tgl_lahir like '%"+TCari.getText().trim()+"%' or "+
                    " petugas.status='1' and petugas.jk like '%"+cmbCrJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+"%' and petugas.gol_darah like '%"+CmbCrGd.getSelectedItem().toString().trim()+"%' and petugas.stts_nikah like '%"+CmbCrStts.getSelectedItem().toString().trim()+"%' and petugas.gol_darah like '%"+TCari.getText().trim()+"%' or "+
                    " petugas.status='1' and petugas.jk like '%"+cmbCrJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+"%' and petugas.gol_darah like '%"+CmbCrGd.getSelectedItem().toString().trim()+"%' and petugas.stts_nikah like '%"+CmbCrStts.getSelectedItem().toString().trim()+"%' and petugas.agama like '%"+TCari.getText().trim()+"%' or "+
                    " petugas.status='1' and petugas.jk like '%"+cmbCrJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+"%' and petugas.gol_darah like '%"+CmbCrGd.getSelectedItem().toString().trim()+"%' and petugas.stts_nikah like '%"+CmbCrStts.getSelectedItem().toString().trim()+"%' and petugas.alamat like '%"+TCari.getText().trim()+"%' or "+
                    " petugas.status='1' and petugas.jk like '%"+cmbCrJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+"%' and petugas.gol_darah like '%"+CmbCrGd.getSelectedItem().toString().trim()+"%' and petugas.stts_nikah like '%"+CmbCrStts.getSelectedItem().toString().trim()+"%' and petugas.no_telp like '%"+TCari.getText().trim()+"%' or "+
                    " petugas.status='1' and petugas.jk like '%"+cmbCrJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+"%' and petugas.gol_darah like '%"+CmbCrGd.getSelectedItem().toString().trim()+"%' and petugas.stts_nikah like '%"+CmbCrStts.getSelectedItem().toString().trim()+"%' and jabatan.nm_jbtn like '%"+TCari.getText().trim()+"%' order by petugas.nip",param);
                this.setCursor(Cursor.getDefaultCursor());
        }
        
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        CmbCrStts.setSelectedIndex(0);
        cmbCrJk.setSelectedIndex(0);
        CmbCrGd.setSelectedIndex(0);
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

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNip.getText().trim().equals("")){
            Valid.textKosong(TNip,"NIP");
        }else if(TNm.getText().trim().equals("")){
            Valid.textKosong(TNm,"nama petugas");
        }else if(TJbtn.getText().trim().equals("")||KdJbtn.getText().trim().equals("")){
            Valid.textKosong(KdJbtn,"jabatan");
        }else{
            try {
                koneksi.setAutoCommit(false);
                Sequel.mengedit("pegawai","nik='"+tbPetugas.getValueAt(tbPetugas.getSelectedRow(),0).toString()+"'",
                    "nik='"+TNip.getText()+"',nama='"+TNm.getText()+"',jk='"+CmbJk.getSelectedItem().toString().replaceAll("PEREMPUAN","Wanita").replaceAll("LAKI-LAKI","Pria")+"',"+
                    "tmp_lahir='"+TTmp.getText()+"',tgl_lahir='"+Valid.SetTgl(DTPLahir.getSelectedItem()+"")+"',alamat='"+TAlmt.getText()+"'");
                Sequel.mengedit("petugas","nip='"+tbPetugas.getValueAt(tbPetugas.getSelectedRow(),0).toString()+"'",
                        "nip='"+TNip.getText()+"',nama='"+TNm.getText()+
                        "',jk='"+CmbJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+
                        "',tmp_lahir='"+TTmp.getText()+
                        "',tgl_lahir='"+Valid.SetTgl(DTPLahir.getSelectedItem()+"")+
                        "',gol_darah='"+CMbGd.getSelectedItem()+
                        "',agama='"+cmbAgama.getSelectedItem()+
                        "',stts_nikah='"+CmbStts.getSelectedItem()+
                        "',alamat='"+TAlmt.getText()+
                        "',kd_jbtn='"+KdJbtn.getText()+
                        "',no_telp='"+TTlp.getText()+"'");
                koneksi.setAutoCommit(true);
                if(tabMode.getRowCount()!=0){tampil();}
                emptTeks();
            } catch (SQLException ex) {
                return;
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

    private void cmbCrJkItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCrJkItemStateChanged
        tampil();
}//GEN-LAST:event_cmbCrJkItemStateChanged

    private void cmbCrJkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbCrJkKeyPressed
        Valid.pindah(evt,BtnAll, CmbCrGd);
}//GEN-LAST:event_cmbCrJkKeyPressed

    private void CmbCrGdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbCrGdKeyPressed
        Valid.pindah(evt,cmbCrJk, CmbCrStts);
}//GEN-LAST:event_CmbCrGdKeyPressed

    private void CmbCrSttsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbCrSttsKeyPressed
        Valid.pindah(evt,CmbCrGd,TCari);
}//GEN-LAST:event_CmbCrSttsKeyPressed

    private void tbPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPetugasMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbPetugasMouseClicked

private void KdJbtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdJbtnKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){            
                Sequel.cariIsi("select nm_jbtn from jabatan where kd_jbtn=?",TJbtn,KdJbtn.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){  
            btnJabatanActionPerformed(null);
        }else{            
            Valid.pindah(evt,TTlp,BtnSimpan);
        }
}//GEN-LAST:event_KdJbtnKeyPressed

private void btnJabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJabatanActionPerformed
        jabatan.isCek();
        jabatan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jabatan.setLocationRelativeTo(internalFrame1);
        jabatan.setVisible(true);
}//GEN-LAST:event_btnJabatanActionPerformed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

    private void BtnCariPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariPegawaiActionPerformed
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnCariPegawaiActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRestoreActionPerformed
        DlgRestorePetugas restore=new DlgRestorePetugas(null,true);
        restore.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        restore.setLocationRelativeTo(internalFrame1);
        restore.setVisible(true);
    }//GEN-LAST:event_MnRestoreActionPerformed

    private void tbPetugasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugasKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }            
        }
    }//GEN-LAST:event_tbPetugasKeyReleased

    private void MnKartuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartuActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            if(tbPetugas.getSelectedRow()> -1){
                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
                Valid.MyReportqry("rptKartuPetugas.jasper","report","::[ Kartu Petugas ]::",
                    "select petugas.nip,petugas.nama,petugas.jk,petugas.tmp_lahir,petugas.tgl_lahir,concat('http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penggajian/',pegawai.photo) as photo, "+
                    "petugas.gol_darah,petugas.agama,petugas.stts_nikah,petugas.alamat,jabatan.nm_jbtn,petugas.no_telp "+
                    "from petugas inner join jabatan on jabatan.kd_jbtn=petugas.kd_jbtn inner join pegawai on petugas.nip=pegawai.nik "+
                    "where petugas.nip='"+TNip.getText()+"' and photo<>'pages/pegawai/photo/'",param);
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan pilih data yang mau dicetak kartu anggotanya ..!!!");
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnKartuActionPerformed

    private void MnKartu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnKartu1ActionPerformed
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
            param.put("logo",Sequel.cariGambar("select setting.logo from setting"));
            Valid.MyReportqry("rptKartuPetugas.jasper","report","::[ Kartu Petugas ]::",
                "select petugas.nip,petugas.nama,petugas.jk,petugas.tmp_lahir,petugas.tgl_lahir,concat('http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/penggajian/',pegawai.photo) as photo, "+
                "petugas.gol_darah,petugas.agama,petugas.stts_nikah,petugas.alamat,jabatan.nm_jbtn,petugas.no_telp "+
                "from petugas inner join jabatan on jabatan.kd_jbtn=petugas.kd_jbtn inner join pegawai on petugas.nip=pegawai.nik "+
                "where petugas.status='1' and photo<>'pages/pegawai/photo/'",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnKartu1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPetugas dialog = new DlgPetugas(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnCariPegawai;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.ComboBox CMbGd;
    private widget.CekBox ChkInput;
    private widget.ComboBox CmbCrGd;
    private widget.ComboBox CmbCrStts;
    private widget.ComboBox CmbJk;
    private widget.ComboBox CmbStts;
    private widget.Tanggal DTPLahir;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KdJbtn;
    private widget.Label LCount;
    private javax.swing.JMenuItem MnKartu;
    private javax.swing.JMenuItem MnKartu1;
    private javax.swing.JMenuItem MnRestore;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAlmt;
    private widget.TextBox TCari;
    private widget.TextBox TJbtn;
    private widget.TextBox TNip;
    private widget.TextBox TNm;
    private widget.TextBox TTlp;
    private widget.TextBox TTmp;
    private widget.Button btnJabatan;
    private widget.ComboBox cmbAgama;
    private widget.ComboBox cmbCrJk;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel16;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator5;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbPetugas;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select petugas.nip,petugas.nama,petugas.jk,petugas.tmp_lahir,petugas.tgl_lahir, "+
                    "petugas.gol_darah,petugas.agama,petugas.stts_nikah,petugas.alamat,jabatan.nm_jbtn,petugas.no_telp "+
                    "from petugas inner join jabatan on jabatan.kd_jbtn=petugas.kd_jbtn "+
                    "where petugas.status='1' and petugas.jk like ? and petugas.gol_darah like ? and petugas.stts_nikah like ? and "+
                    "(petugas.nip like ? or petugas.nama like ? or petugas.tmp_lahir like ? or petugas.tgl_lahir like ? or "+
                    "petugas.gol_darah like ? or petugas.agama like ? or petugas.alamat like ? or jabatan.nm_jbtn like ?) order by petugas.nip");
            try {
                ps.setString(1,"%"+cmbCrJk.getSelectedItem().toString().replaceAll("LAKI-LAKI","L").replaceAll("PEREMPUAN","P").trim()+"%");
                ps.setString(2,"%"+CmbCrGd.getSelectedItem().toString().trim()+"%");
                ps.setString(3,"%"+CmbCrStts.getSelectedItem().toString().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,"%"+TCari.getText().trim()+"%");
                ps.setString(8,"%"+TCari.getText().trim()+"%");
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,"%"+TCari.getText().trim()+"%");
                ps.setString(11,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),
                        rs.getString(7),rs.getString(8),rs.getString(9),rs.getString(10),rs.getString(11)
                    });
                }
            } catch (Exception e) {
                System.out.println(e);
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
        TNip.setText("");
        TNm.setText("");
        CmbJk.setSelectedIndex(0);
        CMbGd.setSelectedIndex(0);
        TTmp.setText("");
        DTPLahir.setSelectedIndex(0);
        cmbAgama.setSelectedIndex(0);
        CmbStts.setSelectedIndex(0);
        TAlmt.setText("");
        TTlp.setText("");
        KdJbtn.setText("");
        TJbtn.setText("");
        DTPLahir.setDate(new Date());
        TNip.requestFocus();
    }

    private void getData() {
        int row=tbPetugas.getSelectedRow();
        if(row!= -1){
            TNip.setText(tbPetugas.getValueAt(row,0).toString());
            TNm.setText(tbPetugas.getValueAt(row,1).toString());

            switch (tbPetugas.getValueAt(row,2).toString()) {
                case "L":
                    CmbJk.setSelectedItem("LAKI-LAKI");
                    break;
                case "P":
                    CmbJk.setSelectedItem("PEREMPUAN");
                    break;
            }

            TTmp.setText(tbPetugas.getValueAt(row,3).toString());
            
            CMbGd.setSelectedItem(tbPetugas.getValueAt(row,5).toString());
            cmbAgama.setSelectedItem(tbPetugas.getValueAt(row,6).toString());
            CmbStts.setSelectedItem(tbPetugas.getValueAt(row,7).toString());
            TAlmt.setText(tbPetugas.getValueAt(row,8).toString());
            Sequel.cariIsi("select jabatan.kd_jbtn from jabatan where jabatan.nm_jbtn='"+tbPetugas.getValueAt(row,9).toString()+"'", KdJbtn);
            TJbtn.setText(tbPetugas.getValueAt(row,9).toString());
            TTlp.setText(tbPetugas.getValueAt(row,10).toString());
            Valid.SetTgl(DTPLahir,tbPetugas.getValueAt(row,4).toString());
        }
    }

    public void load() {
        CmbCrGd.setSelectedItem("");
        CmbCrStts.setSelectedItem("");
        cmbCrJk.setSelectedItem("");
    }

    public JTextField getTextField(){
        return TNip;
    }

    public JTable getTable(){
        return tbPetugas;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,158));
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
        BtnSimpan.setEnabled(akses.getpetugas());
        BtnHapus.setEnabled(akses.getpetugas());
        BtnEdit.setEnabled(akses.getpetugas());
        BtnPrint.setEnabled(akses.getpetugas());
        if(akses.getkode().equals("Admin Utama")){
            MnRestore.setEnabled(true);
        }else{
            MnRestore.setEnabled(false);
        } 
    }
}

package rekammedis;
import kepegawaian.DlgCariPetugas;
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
import simrskhanza.DlgCariPasien;

public class RMCariRekonsiliasiObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariPasien member=new DlgCariPasien(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private int i,pilihan=1;
    private PreparedStatement ps,ps2,ps3;
    private ResultSet rs,rs2,rs3;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public RMCariRekonsiliasiObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        DlgKonfirmasiObatRekonsiliasi.setSize(572,165);

        Object[] row={"No.Rekonsiliasi","Pasien","Tanggal Wawancara","Rekonsiliasi Saat","Alergi Obat","Dampak Alergi","Manifestasi Alergi","Petugas Rekonsiliasi"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 8; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(370);
            }else if(i==2){
                column.setPreferredWidth(115);
            }else if(i==3){
                column.setPreferredWidth(115);
            }else if(i==4){
                column.setPreferredWidth(160);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(160);
            }else if(i==7){
                column.setPreferredWidth(160);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoRawat.setDocument(new batasInput((byte)17).getKata(NoRawat));
        kdmem.setDocument(new batasInput((byte)8).getKata(kdmem));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
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
        
        member.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(member.getTable().getSelectedRow()!= -1){                   
                    kdmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),0).toString());
                    nmmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),1).toString());
                } 
                kdmem.requestFocus();
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
        
        member.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    member.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){  
                    if(pilihan==1){
                        kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }else{
                        KodePetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        NamaPetugas.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
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
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DlgKonfirmasiObatRekonsiliasi = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        jLabel99 = new widget.Label();
        BtnKeluarRekon = new widget.Button();
        BtnSimpanRekon = new widget.Button();
        NoRekonsiliasi = new widget.TextBox();
        DiterimaFarmasi = new widget.Tanggal();
        jLabel100 = new widget.Label();
        jLabel101 = new widget.Label();
        DikonfirmasiApoteker = new widget.Tanggal();
        jLabel102 = new widget.Label();
        DiserahkanPasien = new widget.Tanggal();
        jLabel9 = new widget.Label();
        KodePetugas = new widget.TextBox();
        NamaPetugas = new widget.TextBox();
        BtnPetugas = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoRawat = new widget.TextBox();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdmem = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmmem = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPasien = new widget.Button();
        btnPetugas = new widget.Button();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        BtnKonfirmasi = new widget.Button();
        BtnHapus = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        DlgKonfirmasiObatRekonsiliasi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgKonfirmasiObatRekonsiliasi.setName("DlgKonfirmasiObatRekonsiliasi"); // NOI18N
        DlgKonfirmasiObatRekonsiliasi.setUndecorated(true);
        DlgKonfirmasiObatRekonsiliasi.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Konfirmasi Rekonsiliasi Obat Oleh Petugas Farmasi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        jLabel99.setText("Diterima Farmasi :");
        jLabel99.setName("jLabel99"); // NOI18N
        panelBiasa2.add(jLabel99);
        jLabel99.setBounds(296, 10, 120, 23);

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
        BtnKeluarRekon.setBounds(450, 100, 100, 30);

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
        BtnSimpanRekon.setBounds(350, 100, 100, 30);

        NoRekonsiliasi.setHighlighter(null);
        NoRekonsiliasi.setName("NoRekonsiliasi"); // NOI18N
        NoRekonsiliasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRekonsiliasiKeyPressed(evt);
            }
        });
        panelBiasa2.add(NoRekonsiliasi);
        NoRekonsiliasi.setBounds(134, 10, 140, 23);

        DiterimaFarmasi.setForeground(new java.awt.Color(50, 70, 50));
        DiterimaFarmasi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-02-2023 11:12:22" }));
        DiterimaFarmasi.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DiterimaFarmasi.setName("DiterimaFarmasi"); // NOI18N
        DiterimaFarmasi.setOpaque(false);
        DiterimaFarmasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiterimaFarmasiKeyPressed(evt);
            }
        });
        panelBiasa2.add(DiterimaFarmasi);
        DiterimaFarmasi.setBounds(420, 10, 130, 23);

        jLabel100.setText("Nomor Rekonsiliasi :");
        jLabel100.setName("jLabel100"); // NOI18N
        panelBiasa2.add(jLabel100);
        jLabel100.setBounds(0, 10, 130, 23);

        jLabel101.setText("Dikonfirmasi Apoteker :");
        jLabel101.setName("jLabel101"); // NOI18N
        panelBiasa2.add(jLabel101);
        jLabel101.setBounds(0, 40, 130, 23);

        DikonfirmasiApoteker.setForeground(new java.awt.Color(50, 70, 50));
        DikonfirmasiApoteker.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-02-2023 11:13:31" }));
        DikonfirmasiApoteker.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DikonfirmasiApoteker.setName("DikonfirmasiApoteker"); // NOI18N
        DikonfirmasiApoteker.setOpaque(false);
        DikonfirmasiApoteker.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DikonfirmasiApotekerKeyPressed(evt);
            }
        });
        panelBiasa2.add(DikonfirmasiApoteker);
        DikonfirmasiApoteker.setBounds(134, 40, 130, 23);

        jLabel102.setText("Diserahkan Pasien :");
        jLabel102.setName("jLabel102"); // NOI18N
        panelBiasa2.add(jLabel102);
        jLabel102.setBounds(296, 40, 120, 23);

        DiserahkanPasien.setForeground(new java.awt.Color(50, 70, 50));
        DiserahkanPasien.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-02-2023 11:14:53" }));
        DiserahkanPasien.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DiserahkanPasien.setName("DiserahkanPasien"); // NOI18N
        DiserahkanPasien.setOpaque(false);
        DiserahkanPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiserahkanPasienKeyPressed(evt);
            }
        });
        panelBiasa2.add(DiserahkanPasien);
        DiserahkanPasien.setBounds(420, 40, 130, 23);

        jLabel9.setText("Petugas Farmasi :");
        jLabel9.setName("jLabel9"); // NOI18N
        panelBiasa2.add(jLabel9);
        jLabel9.setBounds(0, 70, 130, 23);

        KodePetugas.setEditable(false);
        KodePetugas.setName("KodePetugas"); // NOI18N
        panelBiasa2.add(KodePetugas);
        KodePetugas.setBounds(134, 70, 128, 23);

        NamaPetugas.setEditable(false);
        NamaPetugas.setHighlighter(null);
        NamaPetugas.setName("NamaPetugas"); // NOI18N
        panelBiasa2.add(NamaPetugas);
        NamaPetugas.setBounds(264, 70, 255, 23);

        BtnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPetugas.setMnemonic('4');
        BtnPetugas.setToolTipText("ALt+4");
        BtnPetugas.setName("BtnPetugas"); // NOI18N
        BtnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPetugasActionPerformed(evt);
            }
        });
        BtnPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPetugasKeyPressed(evt);
            }
        });
        panelBiasa2.add(BtnPetugas);
        BtnPetugas.setBounds(522, 70, 28, 23);

        internalFrame4.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgKonfirmasiObatRekonsiliasi.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Rekonsiliasi Obat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Rawat :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 70, 23);

        NoRawat.setName("NoRawat"); // NOI18N
        NoRawat.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRawatKeyPressed(evt);
            }
        });
        panelisi3.add(NoRawat);
        NoRawat.setBounds(74, 10, 206, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 70, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(74, 40, 90, 23);

        label16.setText("Pasien :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(365, 10, 60, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(365, 40, 60, 23);

        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisi3.add(kdmem);
        kdmem.setBounds(429, 10, 100, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(429, 40, 100, 23);

        nmmem.setEditable(false);
        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmmem);
        nmmem.setBounds(531, 10, 250, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(531, 40, 250, 23);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('1');
        btnPasien.setToolTipText("Alt+1");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        panelisi3.add(btnPasien);
        btnPasien.setBounds(784, 10, 28, 23);

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
        btnPetugas.setBounds(784, 40, 28, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(163, 40, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(190, 40, 90, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(10, 30));
        panelisi1.add(label9);

        BtnKonfirmasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnKonfirmasi.setMnemonic('K');
        BtnKonfirmasi.setText("Konfirm");
        BtnKonfirmasi.setToolTipText("Alt+K");
        BtnKonfirmasi.setName("BtnKonfirmasi"); // NOI18N
        BtnKonfirmasi.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKonfirmasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKonfirmasiActionPerformed(evt);
            }
        });
        BtnKonfirmasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKonfirmasiKeyPressed(evt);
            }
        });
        panelisi1.add(BtnKonfirmasi);

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
        panelisi1.add(BtnHapus);

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

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        member.emptTeks();
        member.isCek();
        member.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        member.setLocationRelativeTo(internalFrame1);
        member.setAlwaysOnTop(false);
        member.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        pilihan=1;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,kdmem,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void kdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmemKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", nmmem,kdmem.getText());      
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", nmmem,kdmem.getText());
            NoRawat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPasienActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select pasien.nm_pasien from pasien where pasien.no_rkm_medis=?", nmmem,kdmem.getText());
            Tgl1.requestFocus();      
        }
    }//GEN-LAST:event_kdmemKeyPressed

    private void NoRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRawatKeyPressed
        Valid.pindah(evt, BtnKeluar, kdptg);
    }//GEN-LAST:event_NoRawatKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            nmptg.setText(petugas.tampil3(kdptg.getText()));
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            nmptg.setText(petugas.tampil3(kdptg.getText()));
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            nmptg.setText(petugas.tampil3(kdptg.getText()));
            NoRawat.requestFocus();            
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdptg);
    }//GEN-LAST:event_Tgl2KeyPressed

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
        NoRawat.setText("");
        kdmem.setText("");
        nmmem.setText("");
        kdptg.setText("");
        nmptg.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','"+
                                tabMode.getValueAt(i,7).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi operasi"); 
            }
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptDataRekonsiliasiObat.jasper","report","::[ Data Rekonsiliasi Obat ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        member.dispose();
        DlgKonfirmasiObatRekonsiliasi.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,NoRawat);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
        TCari.requestFocus();
    }else if(tbDokter.getSelectedRow()<= -1){
        JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
    }else {
        if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("")){
            if(Sequel.cariInteger("select count(rekonsiliasi_obat_konfirmasi.no_rekonsiliasi) from rekonsiliasi_obat_konfirmasi where rekonsiliasi_obat_konfirmasi.no_rekonsiliasi=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())==0){
                Sequel.meghapus("rekonsiliasi_obat","no_rekonsiliasi",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                tampil();
            }else{
                if(akses.getkonfirmasi_rekonsiliasi_obat()==true){
                    Sequel.meghapus("rekonsiliasi_obat","no_rekonsiliasi",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                    tampil();
                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Rekonsiliasi sudah dikonfirmasi oleh apoteker..!!");
                }
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }     
    }
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void BtnKonfirmasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKonfirmasiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else {
            if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().equals("")){
                NoRekonsiliasi.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                DiterimaFarmasi.requestFocus();
                DlgKonfirmasiObatRekonsiliasi.setLocationRelativeTo(internalFrame1);
                DlgKonfirmasiObatRekonsiliasi.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
            }     
        }
    }//GEN-LAST:event_BtnKonfirmasiActionPerformed

    private void BtnKonfirmasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKonfirmasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnKonfirmasiKeyPressed

    private void BtnKeluarRekonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarRekonActionPerformed
        DlgKonfirmasiObatRekonsiliasi.dispose();
    }//GEN-LAST:event_BtnKeluarRekonActionPerformed

    private void BtnSimpanRekonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanRekonActionPerformed
        if(NoRekonsiliasi.getText().trim().equals("")){
            Valid.textKosong(NoRekonsiliasi,"Nomor Rekonsiliasi");
        }else if(KodePetugas.getText().trim().equals("")||NamaPetugas.getText().trim().equals("")){
            Valid.textKosong(BtnPetugas,"Petugas");
        }else{
            Sequel.meghapus("rekonsiliasi_obat_konfirmasi","no_rekonsiliasi",NoRekonsiliasi.getText());
            if(Sequel.menyimpantf("rekonsiliasi_obat_konfirmasi","?,?,?,?,?","Nomor Rekonsiliasi",5,new String[]{
                NoRekonsiliasi.getText(),Valid.SetTgl(DiterimaFarmasi.getSelectedItem()+"")+" "+DiterimaFarmasi.getSelectedItem().toString().substring(11,19),
                Valid.SetTgl(DikonfirmasiApoteker.getSelectedItem()+"")+" "+DikonfirmasiApoteker.getSelectedItem().toString().substring(11,19),
                KodePetugas.getText(),Valid.SetTgl(DiserahkanPasien.getSelectedItem()+"")+" "+DiserahkanPasien.getSelectedItem().toString().substring(11,19)
            })==true){
                tampil();
                NoRekonsiliasi.setText("");
                DlgKonfirmasiObatRekonsiliasi.dispose();
                petugas.dispose();
            }
        }
    }//GEN-LAST:event_BtnSimpanRekonActionPerformed

    private void BtnSimpanRekonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanRekonKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanRekonActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnPetugas,BtnKeluarRekon);
        }
    }//GEN-LAST:event_BtnSimpanRekonKeyPressed

    private void NoRekonsiliasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRekonsiliasiKeyPressed
        Valid.pindah(evt,BtnKeluarRekon,DiterimaFarmasi);
    }//GEN-LAST:event_NoRekonsiliasiKeyPressed

    private void DiterimaFarmasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiterimaFarmasiKeyPressed
        Valid.pindah2(evt,NoRekonsiliasi,DiterimaFarmasi);
    }//GEN-LAST:event_DiterimaFarmasiKeyPressed

    private void DikonfirmasiApotekerKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DikonfirmasiApotekerKeyPressed
        Valid.pindah2(evt,DiterimaFarmasi,DiserahkanPasien);
    }//GEN-LAST:event_DikonfirmasiApotekerKeyPressed

    private void DiserahkanPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiserahkanPasienKeyPressed
        Valid.pindah2(evt,DikonfirmasiApoteker,BtnPetugas);
    }//GEN-LAST:event_DiserahkanPasienKeyPressed

    private void BtnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPetugasActionPerformed
        pilihan=2;
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnPetugasActionPerformed

    private void BtnPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPetugasKeyPressed
        Valid.pindah(evt,DiserahkanPasien,BtnSimpanRekon);
    }//GEN-LAST:event_BtnPetugasKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMCariRekonsiliasiObat dialog = new RMCariRekonsiliasiObat(new javax.swing.JFrame(), true);
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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluarRekon;
    private widget.Button BtnKonfirmasi;
    private widget.Button BtnPetugas;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpanRekon;
    private widget.Tanggal DikonfirmasiApoteker;
    private widget.Tanggal DiserahkanPasien;
    private widget.Tanggal DiterimaFarmasi;
    private javax.swing.JDialog DlgKonfirmasiObatRekonsiliasi;
    private widget.TextBox KodePetugas;
    private widget.TextBox NamaPetugas;
    private widget.TextBox NoRawat;
    private widget.TextBox NoRekonsiliasi;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnPasien;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private widget.Label jLabel100;
    private widget.Label jLabel101;
    private widget.Label jLabel102;
    private widget.Label jLabel9;
    private widget.Label jLabel99;
    private widget.TextBox kdmem;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmmem;
    private widget.TextBox nmptg;
    private widget.PanelBiasa panelBiasa2;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try {
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                "select rekonsiliasi_obat.no_rekonsiliasi,rekonsiliasi_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,"+
                "reg_periksa.umurdaftar,reg_periksa.sttsumur,rekonsiliasi_obat.tanggal_wawancara,rekonsiliasi_obat.rekonsiliasi_obat_saat,"+
                "rekonsiliasi_obat.alergi_obat,rekonsiliasi_obat.manifestasi_alergi,rekonsiliasi_obat.dampak_alergi,rekonsiliasi_obat.nip,"+
                "petugas.nama from rekonsiliasi_obat inner join reg_periksa on reg_periksa.no_rawat=rekonsiliasi_obat.no_rawat "+
                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join petugas on rekonsiliasi_obat.nip=petugas.nip "+
                "where rekonsiliasi_obat.tanggal_wawancara between ? and ? "+(TCari.getText().trim().equals("")?"":" and "+
                "(rekonsiliasi_obat.no_rawat like ? or reg_periksa.no_rkm_medis like ? or pasien.nm_pasien like ? or "+
                "rekonsiliasi_obat.rekonsiliasi_obat_saat like ? or rekonsiliasi_obat.nip like ? or petugas.nama like ?) ")+
                "order by rekonsiliasi_obat.tanggal_wawancara");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
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
                        rs.getString("no_rekonsiliasi"),rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+rs.getString("jk")+") ("+rs.getString("umurdaftar")+rs.getString("sttsumur")+")",
                        rs.getString("tanggal_wawancara"),rs.getString("rekonsiliasi_obat_saat"),rs.getString("alergi_obat"),rs.getString("dampak_alergi"),rs.getString("manifestasi_alergi"),rs.getString("nip")+" "+rs.getString("nama")
                    });
                    tabMode.addRow(new String[]{
                        "","Nama Obat","Dosis Obat","Frekuensi","Cara Pemberian","Pemberian Terakhir","Tindak Lanjut","Perubahan Aturan Pakai"
                    });
                    ps2=koneksi.prepareStatement("select * from rekonsiliasi_obat_detail_obat where rekonsiliasi_obat_detail_obat.no_rekonsiliasi=?");
                    try {
                        ps2.setString(1,rs.getString("no_rekonsiliasi"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new String[]{
                                "",rs2.getString("nama_obat"),rs2.getString("dosis_obat"),rs2.getString("frekuensi"),rs2.getString("cara_pemberian"),rs2.getString("waktu_pemberian_terakhir"),rs2.getString("tindak_lanjut"),rs2.getString("perubahan_aturan_pakai")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 3 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    ps3=koneksi.prepareStatement(
                            "select rekonsiliasi_obat_konfirmasi.diterima_farmasi,rekonsiliasi_obat_konfirmasi.dikonfirmasi_apoteker,rekonsiliasi_obat_konfirmasi.nip,petugas.nama,"+
                            "rekonsiliasi_obat_konfirmasi.diserahkan_pasien from rekonsiliasi_obat_konfirmasi inner join petugas on rekonsiliasi_obat_konfirmasi.nip=petugas.nip "+
                            "where rekonsiliasi_obat_konfirmasi.no_rekonsiliasi=?");
                    try {
                        ps3.setString(1,rs.getString("no_rekonsiliasi"));
                        rs3=ps3.executeQuery();
                        if(rs3.next()){
                            tabMode.addRow(new String[]{
                                "","Diterima Farmasi : "+rs3.getString("diterima_farmasi"),"","","","","",""
                            });
                            tabMode.addRow(new String[]{
                                "","Dikonfirmasi Apoteker : "+rs3.getString("dikonfirmasi_apoteker"),"","","","","",""
                            });
                            tabMode.addRow(new String[]{
                                "","Petugas Farmasi/Apoteker : "+rs3.getString("nip")+" "+rs3.getString("nama"),"","","","","",""
                            });
                            tabMode.addRow(new String[]{
                                "","Kembali Ke Pasien : "+rs3.getString("diserahkan_pasien"),"","","","","",""
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 3 : "+e);
                    } finally{
                        if(rs3!=null){
                            rs3.close();
                        }
                        if(ps3!=null){
                            ps3.close();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Notif 2 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif 1 : "+e);
        }
        
    }
    
    public void SetNoRw(String norw){
        NoRawat.setText(norw);
        tampil();
    }
    
    public void isCek(){
        BtnHapus.setEnabled(akses.getrekonsiliasi_obat());
        BtnPrint.setEnabled(akses.getrekonsiliasi_obat());  
        BtnKonfirmasi.setEnabled(akses.getkonfirmasi_rekonsiliasi_obat());  
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
}

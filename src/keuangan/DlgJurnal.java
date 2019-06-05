package keuangan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class DlgJurnal extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private Jurnal jurnal=new Jurnal();

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgJurnal(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Kode Rekening",
                    "Nama Rekening",
                    "Debet(Rp)",
                    "Kredit(Rp)"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 4; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoJur.setDocument(new batasInput((byte)20).getKata(NoJur));
        NoBukti.setDocument(new batasInput((byte)20).getKata(NoBukti));
        Ktg.setDocument(new batasInput((byte)100).getKata(Ktg));
        kdrek.setDocument(new batasInput((byte)15).getKata(kdrek));
        debet.setDocument(new batasInput((byte)15).getKata(debet));
        kredit.setDocument(new batasInput((byte)15).getKata(kredit));     
        
        rekening.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgJurnal")){
                    if(rekening.getTabel().getSelectedRow()!= -1){      
                        kdrek.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString());
                        nmrek.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString());                        
                        tipe.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),3).toString());                        
                        balance.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),4).toString());                        
                        saldoawal.setText(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),5).toString());                        
                        kdrek.requestFocus();
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
        
        rekening.getTabel().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgJurnal")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        rekening.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        NoJur.setText(Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_jurnal,8),signed)),0) from jurnal","JR",10));
    }
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgRekeningTahun rekening=new DlgRekeningTahun(null,false);
    private DecimalFormat df2 = new DecimalFormat("###,###,###,###,###,###,###");
    private double ttldebet=0,ttlkredit=0;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        jPanel1 = new javax.swing.JPanel();
        panelisi1 = new widget.panelisi();
        BtnTambah = new widget.Button();
        BtnHapus = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnCari = new widget.Button();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label17 = new widget.Label();
        kdrek = new widget.TextBox();
        nmrek = new widget.TextBox();
        BtnCari6 = new widget.Button();
        label21 = new widget.Label();
        tipe = new widget.TextBox();
        label22 = new widget.Label();
        saldoawal = new widget.TextBox();
        label25 = new widget.Label();
        kredit = new widget.TextBox();
        label23 = new widget.Label();
        balance = new widget.TextBox();
        label26 = new widget.Label();
        debet = new widget.TextBox();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoJur = new widget.TextBox();
        label11 = new widget.Label();
        TglJurnal = new widget.Tanggal();
        label16 = new widget.Label();
        label32 = new widget.Label();
        Jenis = new widget.ComboBox();
        label18 = new widget.Label();
        NoBukti = new widget.TextBox();
        scrollPane2 = new widget.ScrollPane();
        Ktg = new widget.TextArea();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Transaksi Posting Jurnal ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
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
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(816, 130));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnTambah);

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

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(40, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi1.add(LTotal);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('L');
        BtnBatal.setText("Clear");
        BtnBatal.setToolTipText("Alt+L");
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

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(null);

        label17.setText("Rekening :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label17);
        label17.setBounds(0, 10, 70, 23);

        kdrek.setName("kdrek"); // NOI18N
        kdrek.setPreferredSize(new java.awt.Dimension(80, 23));
        kdrek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdrekKeyPressed(evt);
            }
        });
        panelisi4.add(kdrek);
        kdrek.setBounds(74, 10, 110, 23);

        nmrek.setEditable(false);
        nmrek.setName("nmrek"); // NOI18N
        nmrek.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi4.add(nmrek);
        nmrek.setBounds(186, 10, 300, 23);

        BtnCari6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCari6.setMnemonic('1');
        BtnCari6.setToolTipText("Alt+1");
        BtnCari6.setName("BtnCari6"); // NOI18N
        BtnCari6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari6ActionPerformed(evt);
            }
        });
        panelisi4.add(BtnCari6);
        BtnCari6.setBounds(488, 10, 28, 23);

        label21.setText("Tipe :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label21);
        label21.setBounds(0, 40, 70, 23);

        tipe.setEditable(false);
        tipe.setName("tipe"); // NOI18N
        tipe.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(tipe);
        tipe.setBounds(74, 40, 70, 23);

        label22.setText("Saldo Awal :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label22);
        label22.setBounds(286, 40, 80, 23);

        saldoawal.setEditable(false);
        saldoawal.setName("saldoawal"); // NOI18N
        saldoawal.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(saldoawal);
        saldoawal.setBounds(370, 40, 116, 23);

        label25.setText("Kredit : Rp.");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label25);
        label25.setBounds(555, 40, 90, 23);

        kredit.setText("0");
        kredit.setName("kredit"); // NOI18N
        kredit.setPreferredSize(new java.awt.Dimension(80, 23));
        kredit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kreditKeyPressed(evt);
            }
        });
        panelisi4.add(kredit);
        kredit.setBounds(650, 40, 117, 23);

        label23.setText("Balance :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label23);
        label23.setBounds(147, 40, 60, 23);

        balance.setEditable(false);
        balance.setName("balance"); // NOI18N
        balance.setPreferredSize(new java.awt.Dimension(80, 23));
        panelisi4.add(balance);
        balance.setBounds(211, 40, 70, 23);

        label26.setText("Debet : Rp.");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label26);
        label26.setBounds(555, 10, 90, 23);

        debet.setText("0");
        debet.setName("debet"); // NOI18N
        debet.setPreferredSize(new java.awt.Dimension(80, 23));
        debet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                debetKeyPressed(evt);
            }
        });
        panelisi4.add(debet);
        debet.setBounds(650, 10, 117, 23);

        jPanel1.add(panelisi4, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Jurnal :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 75, 23);

        NoJur.setName("NoJur"); // NOI18N
        NoJur.setPreferredSize(new java.awt.Dimension(207, 23));
        NoJur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoJurKeyPressed(evt);
            }
        });
        panelisi3.add(NoJur);
        NoJur.setBounds(79, 10, 110, 23);

        label11.setText("Tgl.Jurnal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(201, 10, 85, 23);

        TglJurnal.setDisplayFormat("dd-MM-yyyy");
        TglJurnal.setName("TglJurnal"); // NOI18N
        TglJurnal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglJurnalKeyPressed(evt);
            }
        });
        panelisi3.add(TglJurnal);
        TglJurnal.setBounds(290, 10, 110, 23);

        label16.setText("Keterangan :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(420, 10, 70, 23);

        label32.setText("Jenis Jurnal :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(35, 23));
        panelisi3.add(label32);
        label32.setBounds(201, 40, 85, 23);

        Jenis.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Umum", "Penyesuaian" }));
        Jenis.setName("Jenis"); // NOI18N
        Jenis.setPreferredSize(new java.awt.Dimension(45, 23));
        Jenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisKeyPressed(evt);
            }
        });
        panelisi3.add(Jenis);
        Jenis.setBounds(290, 40, 110, 23);

        label18.setText("No.Bukti :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label18);
        label18.setBounds(0, 40, 75, 23);

        NoBukti.setName("NoBukti"); // NOI18N
        NoBukti.setPreferredSize(new java.awt.Dimension(207, 23));
        NoBukti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoBuktiKeyPressed(evt);
            }
        });
        panelisi3.add(NoBukti);
        NoBukti.setBounds(79, 40, 110, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Ktg.setBorder(null);
        Ktg.setColumns(20);
        Ktg.setRows(5);
        Ktg.setName("Ktg"); // NOI18N
        Ktg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KtgKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Ktg);

        panelisi3.add(scrollPane2);
        scrollPane2.setBounds(494, 10, 280, 53);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        if(nmrek.getText().trim().equals("")){
            Valid.textKosong(kdrek,"Rekening");
        }else if(debet.getText().trim().equals("")){
            Valid.textKosong(debet,"Debet");
        }else if(kredit.getText().trim().equals("")){
            Valid.textKosong(kredit,"Kredit");
        }else if(kredit.getText().trim().equals("0")&&debet.getText().trim().equals("0")){
            Valid.textKosong(debet,"Debet atau kredit");
        }else if(Jenis.getSelectedItem().toString().equals("Umum")&&tipe.getText().trim().equals("R")&&balance.getText().trim().equals("K")&&(!debet.getText().equals("0"))){
            JOptionPane.showMessageDialog(null,"Maaf, Rekening memiliki tipe 'R' dan balance 'K'.\nDebet harus bernilai 0 !!!");
            debet.setText("0");
            debet.requestFocus();
        }else if(Jenis.getSelectedItem().toString().equals("Umum")&&tipe.getText().trim().equals("R")&&balance.getText().trim().equals("D")&&(!kredit.getText().equals("0"))){
            JOptionPane.showMessageDialog(null,"Maaf, Rekening memiliki tipe 'R' dan balance 'D'.\nKredit harus bernilai 0 !!!");
            kredit.setText("0");
            kredit.requestFocus();
        }else{
            Sequel.menyimpan("tampjurnal","'"+kdrek.getText()+"','"+nmrek.getText()+"','"+debet.getText()+"','"+kredit.getText()+"'",
                             "nm_rek='"+nmrek.getText()+"',debet='"+debet.getText()+"',kredit='"+kredit.getText()+"'","kd_rek='"+kdrek.getText()+"'"); 
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnTambahActionPerformed

    private void BtnTambahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnTambahKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnTambahActionPerformed(null);
        }else{
            Valid.pindah(evt,kredit, BtnHapus);
        }
}//GEN-LAST:event_BtnTambahKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(nmrek.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        }else{
            Valid.hapusTable(tabMode,kdrek,"tampjurnal","kd_rek");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnTambah, BtnCari);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        DlgCariJurnal form=new DlgCariJurnal(null,false);
        form.emptTeks();        
        form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        form.setLocationRelativeTo(internalFrame1);
        form.setAlwaysOnTop(false);
        form.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
            dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnBatal,kdrek);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void TglJurnalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglJurnalKeyPressed
        Valid.pindah(evt, NoBukti,Jenis);
    }//GEN-LAST:event_TglJurnalKeyPressed

    private void BtnCari6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari6ActionPerformed
        akses.setform("DlgJurnal");        
        rekening.emptTeks();
        rekening.tampil();
        rekening.isCek();
        rekening.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        rekening.setLocationRelativeTo(internalFrame1);
        rekening.setVisible(true);
    }//GEN-LAST:event_BtnCari6ActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoJur.getText().trim().equals("")){
            Valid.textKosong(NoJur,"No.Jurnal");
        }else if(NoBukti.getText().trim().equals("")){
            Valid.textKosong(NoBukti,"No.Bukti");
        }else if(Ktg.getText().trim().equals("")){
            Valid.textKosong(Ktg,"Keterangan");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            kdrek.requestFocus();
        }else if(ttldebet!=ttlkredit){
            JOptionPane.showMessageDialog(null,"Maaf, Antara total debet dan total kredit harus balance...!!!!");
            kdrek.requestFocus();
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {                    
                    jurnal.simpanJurnal(NoBukti.getText(),Valid.SetTgl(TglJurnal.getSelectedItem()+""), Jenis.getSelectedItem().toString().substring(0,1),Ktg.getText());                                    
                    tampil();
                } catch (Exception ex) {
                    System.out.print("Notifikasi : "+ex);
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,kdrek,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        Sequel.queryu("delete from tampjurnal");
        tampil();
        Valid.autoNomer("jurnal","JR",10,NoJur);
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void NoJurKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoJurKeyPressed
        Valid.pindah(evt, BtnTambah, NoBukti);
    }//GEN-LAST:event_NoJurKeyPressed

    private void kdrekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdrekKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            try {
                    Statement stat=koneksi.createStatement();
                    ResultSet rs=stat.executeQuery("select nm_rek, tipe, balance from rekening where kd_rek='"+kdrek.getText()+"'");
                    while(rs.next()){
                        nmrek.setText(rs.getString(1));
                        tipe.setText(rs.getString(2));
                        balance.setText(rs.getString(3));
                    }
                    saldoawal.setText(rekening.getSaldo().getText());
            } catch (SQLException ex) {
                    System.out.println("Catatan barang : "+ex);
            }           
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            try {
                    Statement stat=koneksi.createStatement();
                    ResultSet rs=stat.executeQuery("select nm_rek, tipe, balance from rekening where kd_rek='"+kdrek.getText()+"'");
                    while(rs.next()){
                        nmrek.setText(rs.getString(1));
                        tipe.setText(rs.getString(2));
                        balance.setText(rs.getString(3));
                    }
                    saldoawal.setText(rekening.getSaldo().getText());
            } catch (SQLException ex) {
                    System.out.println("Catatan barang : "+ex);
            }
            Ktg.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            try {
                    Statement stat=koneksi.createStatement();
                    ResultSet rs=stat.executeQuery("select nm_rek, tipe, balance from rekening where kd_rek='"+kdrek.getText()+"'");
                    while(rs.next()){
                        nmrek.setText(rs.getString(1));
                        tipe.setText(rs.getString(2));
                        balance.setText(rs.getString(3));
                    }
                    saldoawal.setText(rekening.getSaldo().getText());
            } catch (SQLException ex) {
                    System.out.println("Catatan barang : "+ex);
            }
            debet.requestFocus(); 
        }
    }//GEN-LAST:event_kdrekKeyPressed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            //BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, kdrek);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void JenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisKeyPressed
        Valid.pindah(evt, TglJurnal, Ktg);
    }//GEN-LAST:event_JenisKeyPressed

    private void NoBuktiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBuktiKeyPressed
        Valid.pindah(evt, NoJur, TglJurnal);
    }//GEN-LAST:event_NoBuktiKeyPressed

    private void KtgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KtgKeyPressed
        Valid.pindah(evt, Jenis, kdrek);
    }//GEN-LAST:event_KtgKeyPressed

    private void debetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_debetKeyPressed
        Valid.pindah(evt, kdrek, kredit);
    }//GEN-LAST:event_debetKeyPressed

    private void kreditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kreditKeyPressed
        Valid.pindah(evt, debet, BtnTambah);
    }//GEN-LAST:event_kreditKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgJurnal dialog = new DlgJurnal(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCari6;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.ComboBox Jenis;
    private widget.TextBox Kd2;
    private widget.TextArea Ktg;
    private widget.Label LTotal;
    private widget.TextBox NoBukti;
    private widget.TextBox NoJur;
    private widget.Tanggal TglJurnal;
    private widget.TextBox balance;
    private widget.TextBox debet;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel1;
    private widget.TextBox kdrek;
    private widget.TextBox kredit;
    private widget.Label label11;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label32;
    private widget.Label label9;
    private widget.TextBox nmrek;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private widget.TextBox saldoawal;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbDokter;
    private widget.TextBox tipe;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        String sql="select kd_rek, nm_rek, debet, kredit "+
                   "from tampjurnal ";
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
       Valid.tabelKosong(tabMode);
        try{
            java.sql.Statement stat=koneksi.createStatement();
            ResultSet rs=stat.executeQuery(sql);
            ttldebet=0;ttlkredit=0;
            while(rs.next()){
                String[] data={rs.getString(1),
                               rs.getString(2),
                               df2.format(rs.getDouble(3)),
                               df2.format(rs.getDouble(4))};
                ttldebet=ttldebet+rs.getDouble(3);
                ttlkredit=ttlkredit+rs.getDouble(4);
                tabMode.addRow(data);
            }       
            
            if(tabMode.getRowCount()>0){
                String[] data={"","<>> Total : ",df2.format(ttldebet),df2.format(ttlkredit)};
                tabMode.addRow(data);
            }   
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    public void emptTeks() {
        kdrek.setText("");
        nmrek.setText("");
        tipe.setText("");
        debet.setText("0");
        saldoawal.setText("");
        balance.setText("");
        kredit.setText("0");
        kdrek.requestFocus();        
    }

    private void getData() {
       int row=tbDokter.getSelectedRow();
        if(row!= -1){
            try {
                kdrek.setText(tabMode.getValueAt(row,0).toString());
                    Statement stat=koneksi.createStatement();
                    ResultSet rs=stat.executeQuery("select nm_rek, tipe, balance from rekening where kd_rek='"+kdrek.getText()+"'");
                    while(rs.next()){
                        nmrek.setText(rs.getString(1));
                        tipe.setText(rs.getString(2));
                        balance.setText(rs.getString(3));
                    }
                    Sequel.cariIsi("select saldo_awal from rekeningtahun where kd_rek='"+kdrek.getText()+
                                   "' order by thn desc limit 1",saldoawal);
                debet.setText(tabMode.getValueAt(row,2).toString());
                kredit.setText(tabMode.getValueAt(row,3).toString());
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    
    public void setData(String nobukti){
        NoBukti.setText(nobukti);
        Jenis.setSelectedItem("Penyesuaian");
        Ktg.setText("-");
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getposting_jurnal());
        BtnHapus.setEnabled(akses.getposting_jurnal());
        BtnBatal.setEnabled(akses.getposting_jurnal());
        BtnTambah.setEnabled(akses.getposting_jurnal());      
    }
   
 
}

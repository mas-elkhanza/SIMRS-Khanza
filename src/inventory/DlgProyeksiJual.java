package inventory;
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
import simrskhanza.DlgCariBangsal;

public class DlgProyeksiJual extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private DlgCariJenis jenis = new DlgCariJenis(null, false);
    private DlgCariKategori kategori = new DlgCariKategori(null, false);
    private DlgCariGolongan golongan = new DlgCariGolongan(null, false);
    private DlgCariBangsal asalstok=new DlgCariBangsal(null,false);

    /** Creates new form DlgProgramStudi */
    public DlgProyeksiJual(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Tgl.Jual","No.Nota","Barang","Satuan","Harga Jual","Jml.Jual",
                "Subtotal Jual","Disc(%)","Besar Disc(Rp)","Tambahan","Total Jual",
                "Harga Beli","Total Beli","Keuntungan"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 14; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(70);
            }else if(i==1){
                column.setPreferredWidth(85);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(90);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(50);
            }else if(i==6){
                column.setPreferredWidth(90);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(90);
            }else if(i==9){
                column.setPreferredWidth(90);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(90);
            }else if(i==12){
                column.setPreferredWidth(90);
            }else if(i==13){
                column.setPreferredWidth(90);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());         
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));        
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        prosesCari();
                    }
                }
            });
        }
        
        asalstok.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(asalstok.getTable().getSelectedRow()!= -1){                   
                    kdasal.setText(asalstok.getTable().getValueAt(asalstok.getTable().getSelectedRow(),0).toString());                    
                    nmasal.setText(asalstok.getTable().getValueAt(asalstok.getTable().getSelectedRow(),1).toString());
                }  
                kdasal.requestFocus();
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
        
        asalstok.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    asalstok.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });  
        
        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (jenis.getTable().getSelectedRow() != -1) {
                    kdjenis.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 0).toString());
                    nmjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
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
        
        golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (golongan.getTable().getSelectedRow() != -1) {
                    kdgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 0).toString());
                    nmgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {
                golongan.emptTeks();
            }
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if (kategori.getTable().getSelectedRow() != -1) {
                    kdkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 0).toString());
                    nmkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 1).toString());
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {
                kategori.emptTeks();
            }
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        ChkInput.setSelected(false);
        isForm();
        
    }
    private double total=0;
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        kdasal = new widget.TextBox();
        kdjenis = new widget.TextBox();
        kdkategori = new widget.TextBox();
        kdgolongan = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label21 = new widget.Label();
        nmasal = new widget.TextBox();
        BtnSeek4 = new widget.Button();
        label22 = new widget.Label();
        nmjns = new widget.TextBox();
        BtnJenis = new widget.Button();
        label23 = new widget.Label();
        nmkategori = new widget.TextBox();
        BtnKategori = new widget.Button();
        label24 = new widget.Label();
        nmgolongan = new widget.TextBox();
        BtnGolongan = new widget.Button();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label12 = new widget.Label();
        Jenisjual = new widget.ComboBox();

        Kd2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        kdasal.setEditable(false);
        kdasal.setName("kdasal"); // NOI18N
        kdasal.setPreferredSize(new java.awt.Dimension(75, 23));

        kdjenis.setEditable(false);
        kdjenis.setName("kdjenis"); // NOI18N
        kdjenis.setPreferredSize(new java.awt.Dimension(75, 23));

        kdkategori.setEditable(false);
        kdkategori.setName("kdkategori"); // NOI18N
        kdkategori.setPreferredSize(new java.awt.Dimension(75, 23));

        kdgolongan.setEditable(false);
        kdgolongan.setName("kdgolongan"); // NOI18N
        kdgolongan.setPreferredSize(new java.awt.Dimension(75, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Proyeksi Keuntungan Penjualan Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(69, 23));
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
        panelisi1.add(BtnCari);

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(55, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(155, 30));
        panelisi1.add(LTotal);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('A');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+A");
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
        BtnPrint.setMnemonic('P');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+P");
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

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('M');
        ChkInput.setText(".: Filter Data");
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

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 74));
        FormInput.setLayout(null);

        label21.setText("Asal Stok :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(label21);
        label21.setBounds(567, 10, 60, 23);

        nmasal.setEditable(false);
        nmasal.setName("nmasal"); // NOI18N
        nmasal.setPreferredSize(new java.awt.Dimension(215, 23));
        FormInput.add(nmasal);
        nmasal.setBounds(631, 10, 150, 23);

        BtnSeek4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek4.setMnemonic('3');
        BtnSeek4.setToolTipText("Alt+3");
        BtnSeek4.setName("BtnSeek4"); // NOI18N
        BtnSeek4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek4ActionPerformed(evt);
            }
        });
        BtnSeek4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek4KeyPressed(evt);
            }
        });
        FormInput.add(BtnSeek4);
        BtnSeek4.setBounds(784, 10, 28, 23);

        label22.setText("Jenis :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(40, 23));
        FormInput.add(label22);
        label22.setBounds(0, 40, 60, 23);

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmjns);
        nmjns.setBounds(64, 40, 150, 23);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        FormInput.add(BtnJenis);
        BtnJenis.setBounds(217, 40, 28, 23);

        label23.setText("Kategori :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label23);
        label23.setBounds(274, 40, 65, 23);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmkategori);
        nmkategori.setBounds(343, 40, 150, 23);

        BtnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKategori.setMnemonic('2');
        BtnKategori.setToolTipText("Alt+2");
        BtnKategori.setName("BtnKategori"); // NOI18N
        BtnKategori.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKategoriActionPerformed(evt);
            }
        });
        FormInput.add(BtnKategori);
        BtnKategori.setBounds(496, 40, 28, 23);

        label24.setText("Golongan :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label24);
        label24.setBounds(567, 40, 60, 23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(205, 23));
        FormInput.add(nmgolongan);
        nmgolongan.setBounds(631, 40, 150, 23);

        BtnGolongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolongan.setMnemonic('2');
        BtnGolongan.setToolTipText("Alt+2");
        BtnGolongan.setName("BtnGolongan"); // NOI18N
        BtnGolongan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganActionPerformed(evt);
            }
        });
        FormInput.add(BtnGolongan);
        BtnGolongan.setBounds(784, 40, 28, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(115, 23));
        FormInput.add(label11);
        label11.setBounds(0, 10, 60, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(110, 23));
        FormInput.add(Tgl1);
        Tgl1.setBounds(64, 10, 90, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        FormInput.add(label18);
        label18.setBounds(155, 10, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(110, 23));
        FormInput.add(Tgl2);
        Tgl2.setBounds(187, 10, 90, 23);

        label12.setText("Jns.Jual :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label12);
        label12.setBounds(330, 10, 55, 23);

        Jenisjual.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Jual Bebas", "Karyawan", "Beli Luar", "Rawat Jalan", "Kelas 1", "Kelas 2", "Kelas 3", "Utama/BPJS", "VIP", "VVIP" }));
        Jenisjual.setName("Jenisjual"); // NOI18N
        Jenisjual.setPreferredSize(new java.awt.Dimension(40, 23));
        FormInput.add(Jenisjual);
        Jenisjual.setBounds(389, 10, 135, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();  
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            if(Jenisjual.getSelectedItem().toString().equals("Semua")&nmasal.getText().equals("")&&nmjns.getText().equals("")&&nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&TCari.getText().equals("")){
                Valid.MyReportqry("rptProyeksi.jasper","report","::[ Proyeksi Keuntungan Penjualan ]::",
                        "select penjualan.tgl_jual,penjualan.nota_jual,detailjual.kode_brng,databarang.nama_brng,kodesatuan.satuan,detailjual.h_jual,detailjual.jumlah, "+
                        "detailjual.subtotal,detailjual.dis,detailjual.bsr_dis,detailjual.tambahan,detailjual.total, detailjual.h_beli,(detailjual.h_beli * detailjual.jumlah) as total_asal, "+
                        "(detailjual.total-(detailjual.h_beli * detailjual.jumlah)) as keuntungan "+
                        "from penjualan inner join detailjual on penjualan.nota_jual=detailjual.nota_jual "+
                        "inner join databarang on detailjual.kode_brng=databarang.kode_brng "+
                        "inner join kodesatuan on detailjual.kode_sat=kodesatuan.kode_sat "+
                        "inner join bangsal on penjualan.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join jenis on databarang.kdjns=jenis.kdjns "+
                        "inner join kategori_barang on kategori_barang.kode=databarang.kode_kategori "+
                        "inner join golongan_barang on golongan_barang.kode=databarang.kode_golongan "+
                        "where penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by penjualan.tgl_jual,penjualan.nota_jual",param);
            }else{
                Valid.MyReportqry("rptProyeksi.jasper","report","::[ Proyeksi Keuntungan Penjualan ]::",
                        "select penjualan.tgl_jual,penjualan.nota_jual,detailjual.kode_brng,databarang.nama_brng,kodesatuan.satuan,detailjual.h_jual,detailjual.jumlah, "+
                        "detailjual.subtotal,detailjual.dis,detailjual.bsr_dis,detailjual.tambahan,detailjual.total, detailjual.h_beli,(detailjual.h_beli * detailjual.jumlah) as total_asal, "+
                        "(detailjual.total-(detailjual.h_beli * detailjual.jumlah)) as keuntungan "+
                        "from penjualan inner join detailjual on penjualan.nota_jual=detailjual.nota_jual "+
                        "inner join databarang on detailjual.kode_brng=databarang.kode_brng "+
                        "inner join kodesatuan on detailjual.kode_sat=kodesatuan.kode_sat "+
                        "inner join bangsal on penjualan.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join jenis on databarang.kdjns=jenis.kdjns "+
                        "inner join kategori_barang on kategori_barang.kode=databarang.kode_kategori "+
                        "inner join golongan_barang on golongan_barang.kode=databarang.kode_golongan "+
                        "where penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and penjualan.jns_jual like '%"+Jenisjual.getSelectedItem().toString().replaceAll("Semua","")+"%' and concat(penjualan.kd_bangsal,bangsal.nm_bangsal) like '%"+kdasal.getText()+nmasal.getText()+"%' and concat(databarang.kdjns,jenis.nama) like '%"+kdjenis.getText()+nmjns.getText()+"%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%"+kdkategori.getText()+nmkategori.getText()+"%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%"+kdgolongan.getText()+nmgolongan.getText()+"%' and penjualan.nota_jual like '%"+TCari.getText().trim()+"%' or "+
                        "penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and penjualan.jns_jual like '%"+Jenisjual.getSelectedItem().toString().replaceAll("Semua","")+"%' and concat(penjualan.kd_bangsal,bangsal.nm_bangsal) like '%"+kdasal.getText()+nmasal.getText()+"%' and concat(databarang.kdjns,jenis.nama) like '%"+kdjenis.getText()+nmjns.getText()+"%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%"+kdkategori.getText()+nmkategori.getText()+"%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%"+kdgolongan.getText()+nmgolongan.getText()+"%' and detailjual.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                        "penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and penjualan.jns_jual like '%"+Jenisjual.getSelectedItem().toString().replaceAll("Semua","")+"%' and concat(penjualan.kd_bangsal,bangsal.nm_bangsal) like '%"+kdasal.getText()+nmasal.getText()+"%' and concat(databarang.kdjns,jenis.nama) like '%"+kdjenis.getText()+nmjns.getText()+"%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%"+kdkategori.getText()+nmkategori.getText()+"%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%"+kdgolongan.getText()+nmgolongan.getText()+"%' and databarang.nama_brng like '%"+TCari.getText().trim()+"%' or "+
                        "penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and penjualan.jns_jual like '%"+Jenisjual.getSelectedItem().toString().replaceAll("Semua","")+"%' and concat(penjualan.kd_bangsal,bangsal.nm_bangsal) like '%"+kdasal.getText()+nmasal.getText()+"%' and concat(databarang.kdjns,jenis.nama) like '%"+kdjenis.getText()+nmjns.getText()+"%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%"+kdkategori.getText()+nmkategori.getText()+"%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%"+kdgolongan.getText()+nmgolongan.getText()+"%' and kodesatuan.satuan like '%"+TCari.getText().trim()+"%' "+
                        " order by penjualan.tgl_jual,penjualan.nota_jual",param);
            }
                
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

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
        prosesCari();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        kdasal.setText("");
        nmasal.setText("");
        kdgolongan.setText("");
        nmgolongan.setText("");
        kdjenis.setText("");
        nmjns.setText("");
        kdkategori.setText("");
        nmkategori.setText("");
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnSeek4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek4ActionPerformed
        asalstok.isCek();
        asalstok.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        asalstok.setLocationRelativeTo(internalFrame1);
        asalstok.setAlwaysOnTop(false);
        asalstok.setVisible(true);
    }//GEN-LAST:event_BtnSeek4ActionPerformed

    private void BtnSeek4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnSeek4KeyPressed

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setAlwaysOnTop(false);
        kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        golongan.isCek();
        golongan.setSize(internalFrame1.getWidth() -20, internalFrame1.getHeight() -20);
        golongan.setLocationRelativeTo(internalFrame1);
        golongan.setAlwaysOnTop(false);
        golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DlgProyeksiJual dialog = new DlgProyeksiJual(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnGolongan;
    private widget.Button BtnJenis;
    private widget.Button BtnKategori;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek4;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.ComboBox Jenisjual;
    private widget.TextBox Kd2;
    private widget.Label LTotal;
    private javax.swing.JPanel PanelInput;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.TextBox kdasal;
    private widget.TextBox kdgolongan;
    private widget.TextBox kdjenis;
    private widget.TextBox kdkategori;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label18;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label9;
    private widget.TextBox nmasal;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjns;
    private widget.TextBox nmkategori;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{   
            if(Jenisjual.getSelectedItem().toString().equals("Semua")&nmasal.getText().equals("")&&nmjns.getText().equals("")&&nmkategori.getText().equals("")&&nmgolongan.getText().equals("")&&TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select penjualan.tgl_jual,penjualan.nota_jual,detailjual.kode_brng,databarang.nama_brng,kodesatuan.satuan,detailjual.h_jual,detailjual.jumlah, "+
                        "detailjual.subtotal,detailjual.dis,detailjual.bsr_dis,detailjual.tambahan,detailjual.total, detailjual.h_beli,(detailjual.h_beli * detailjual.jumlah) as total_asal, "+
                        "(detailjual.total-(detailjual.h_beli * detailjual.jumlah)) as keuntungan "+
                        "from penjualan inner join detailjual on penjualan.nota_jual=detailjual.nota_jual "+
                        "inner join databarang on detailjual.kode_brng=databarang.kode_brng "+
                        "inner join kodesatuan on detailjual.kode_sat=kodesatuan.kode_sat "+
                        "inner join bangsal on penjualan.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join jenis on databarang.kdjns=jenis.kdjns "+
                        "inner join kategori_barang on kategori_barang.kode=databarang.kode_kategori "+
                        "inner join golongan_barang on golongan_barang.kode=databarang.kode_golongan "+
                        "where penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' order by penjualan.tgl_jual,penjualan.nota_jual");
            }else{
                ps=koneksi.prepareStatement(
                        "select penjualan.tgl_jual,penjualan.nota_jual,detailjual.kode_brng,databarang.nama_brng,kodesatuan.satuan,detailjual.h_jual,detailjual.jumlah, "+
                        "detailjual.subtotal,detailjual.dis,detailjual.bsr_dis,detailjual.tambahan,detailjual.total, detailjual.h_beli,(detailjual.h_beli * detailjual.jumlah) as total_asal, "+
                        "(detailjual.total-(detailjual.h_beli * detailjual.jumlah)) as keuntungan "+
                        "from penjualan inner join detailjual on penjualan.nota_jual=detailjual.nota_jual "+
                        "inner join databarang on detailjual.kode_brng=databarang.kode_brng "+
                        "inner join kodesatuan on detailjual.kode_sat=kodesatuan.kode_sat "+
                        "inner join bangsal on penjualan.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join jenis on databarang.kdjns=jenis.kdjns "+
                        "inner join kategori_barang on kategori_barang.kode=databarang.kode_kategori "+
                        "inner join golongan_barang on golongan_barang.kode=databarang.kode_golongan "+
                        "where penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and penjualan.jns_jual like '%"+Jenisjual.getSelectedItem().toString().replaceAll("Semua","")+"%' and concat(penjualan.kd_bangsal,bangsal.nm_bangsal) like '%"+kdasal.getText()+nmasal.getText()+"%' and concat(databarang.kdjns,jenis.nama) like '%"+kdjenis.getText()+nmjns.getText()+"%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%"+kdkategori.getText()+nmkategori.getText()+"%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%"+kdgolongan.getText()+nmgolongan.getText()+"%' and penjualan.nota_jual like '%"+TCari.getText().trim()+"%' or "+
                        "penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and penjualan.jns_jual like '%"+Jenisjual.getSelectedItem().toString().replaceAll("Semua","")+"%' and concat(penjualan.kd_bangsal,bangsal.nm_bangsal) like '%"+kdasal.getText()+nmasal.getText()+"%' and concat(databarang.kdjns,jenis.nama) like '%"+kdjenis.getText()+nmjns.getText()+"%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%"+kdkategori.getText()+nmkategori.getText()+"%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%"+kdgolongan.getText()+nmgolongan.getText()+"%' and detailjual.kode_brng like '%"+TCari.getText().trim()+"%' or "+
                        "penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and penjualan.jns_jual like '%"+Jenisjual.getSelectedItem().toString().replaceAll("Semua","")+"%' and concat(penjualan.kd_bangsal,bangsal.nm_bangsal) like '%"+kdasal.getText()+nmasal.getText()+"%' and concat(databarang.kdjns,jenis.nama) like '%"+kdjenis.getText()+nmjns.getText()+"%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%"+kdkategori.getText()+nmkategori.getText()+"%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%"+kdgolongan.getText()+nmgolongan.getText()+"%' and databarang.nama_brng like '%"+TCari.getText().trim()+"%' or "+
                        "penjualan.tgl_jual between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+"' and penjualan.jns_jual like '%"+Jenisjual.getSelectedItem().toString().replaceAll("Semua","")+"%' and concat(penjualan.kd_bangsal,bangsal.nm_bangsal) like '%"+kdasal.getText()+nmasal.getText()+"%' and concat(databarang.kdjns,jenis.nama) like '%"+kdjenis.getText()+nmjns.getText()+"%' and concat(databarang.kode_kategori,kategori_barang.nama) like '%"+kdkategori.getText()+nmkategori.getText()+"%' and concat(databarang.kode_golongan,golongan_barang.nama) like '%"+kdgolongan.getText()+nmgolongan.getText()+"%' and kodesatuan.satuan like '%"+TCari.getText().trim()+"%' "+
                        " order by penjualan.tgl_jual,penjualan.nota_jual");
            }
            
                
            try {
                rs=ps.executeQuery();
                total=0;
                while(rs.next()){
                    total=total+rs.getDouble(15);
                    tabMode.addRow(new String[]{
                        rs.getString(1),rs.getString(2),rs.getString(3)+", "+rs.getString(4),rs.getString(5),
                        Valid.SetAngka(rs.getDouble(6)),Valid.SetAngka(rs.getDouble(7)),Valid.SetAngka(rs.getDouble(8)),
                        Valid.SetAngka(rs.getDouble(9)),Valid.SetAngka(rs.getDouble(10)),Valid.SetAngka(rs.getDouble(11)),
                        Valid.SetAngka(rs.getDouble(12)),Valid.SetAngka(rs.getDouble(13)),Valid.SetAngka(rs.getDouble(14)),
                        Valid.SetAngka(rs.getDouble(15))
                    });  
                }
            } catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
               if(rs!=null){
                   rs.close();
               }
               if(ps!=null){
                   ps.close();
               }
            }
              
            LTotal.setText(Valid.SetAngka(total));            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        
    }
    
    public void isCek(){
         BtnPrint.setEnabled(akses.getkeuntungan_penjualan());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,96));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
     
 
}

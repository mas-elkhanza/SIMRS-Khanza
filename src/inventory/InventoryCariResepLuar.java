/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgResepObat.java
 *
 * Created on 31 Mei 10, 11:27:40
 */

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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public final class InventoryCariResepLuar extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabmodeUbahRacikan,tabmodeUbahRacikan2;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,psracikan;
    private ResultSet rs,rs2,rsracikan;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date date = new Date();
    private String now=dateFormat.format(date),rincianobat="",finger="";
    private DlgCariAturanPakai aturanpakai=new DlgCariAturanPakai(null,false);
    private int i=0,pilihan=0;
    private DateFormat format=new SimpleDateFormat("yyyy-MM-dd");

    /** Creates new form DlgResepObat 
     *@param parent
     *@param modal*/
    public InventoryCariResepLuar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(628,674);

        Object[] row={"No.Resep","Tgl.Resep","Pasien","Dokter Peresep"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbResep.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbResep.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbResep.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbResep.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(350);
            }else if(i==3){
                column.setPreferredWidth(200);
            }
        }
        tbResep.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabmodeUbahRacikan=new DefaultTableModel(null,new Object[]{
                "Kode Barang","Nama Barang","Jumlah","Aturan Pakai"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==2)||(colIndex==3)) {
                    a=true;
                }
                return a;
             }
         };
        tbTambahan.setModel(tabmodeUbahRacikan);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbTambahan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTambahan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbTambahan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(90);
            }else if(i==1){
                column.setPreferredWidth(200);
            }else if(i==2){
                column.setPreferredWidth(50);
            }else if(i==3){
                column.setPreferredWidth(200);
            }
        }
        tbTambahan.setDefaultRenderer(Object.class, new WarnaTable());

        tabmodeUbahRacikan2=new DefaultTableModel(null,new Object[]{
                "No.Racik","Nama Racik","Aturan Pakai"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==1)||(colIndex==2)) {
                    a=true;
                }
                return a;
             }
         };
        tbTambahan1.setModel(tabmodeUbahRacikan2);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbTambahan1.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbTambahan1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbTambahan1.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(60);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setPreferredWidth(200);
            }
        }
        tbTambahan1.setDefaultRenderer(Object.class, new WarnaTable());
        
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
        
        aturanpakai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(aturanpakai.getTable().getSelectedRow()!= -1){ 
                    if(pilihan==1){
                        tbTambahan.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbTambahan.getSelectedRow(),5);
                        tbTambahan.requestFocus();
                    }else if(pilihan==2){
                        tbTambahan1.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbTambahan1.getSelectedRow(),5);
                        tbTambahan1.requestFocus();
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
        
        Valid.SetTgl2(DTPCari1,format.format(new Date())+" 00:00:00");
        Valid.SetTgl2(DTPCari2,format.format(new Date())+" 23:59:59"); 
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup2 = new javax.swing.JPopupMenu();
        ppUbahAturanPakai = new javax.swing.JMenuItem();
        ppUbahAturanPakai1 = new javax.swing.JMenuItem();
        WindowInput3 = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbTambahan = new widget.Table();
        panelisi1 = new widget.panelisi();
        label15 = new widget.Label();
        NoResepUbah3 = new widget.TextBox();
        BtnSimpanInput3 = new widget.Button();
        BtnKeluarInput3 = new widget.Button();
        WindowInput4 = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        tbTambahan1 = new widget.Table();
        panelisi2 = new widget.panelisi();
        label16 = new widget.Label();
        NoResepUbah4 = new widget.TextBox();
        BtnSimpanInput4 = new widget.Button();
        BtnKeluarInput4 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbResep = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnResep = new widget.Button();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
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

        Popup2.setName("Popup2"); // NOI18N

        ppUbahAturanPakai.setBackground(new java.awt.Color(255, 255, 254));
        ppUbahAturanPakai.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppUbahAturanPakai.setForeground(new java.awt.Color(50, 50, 50));
        ppUbahAturanPakai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppUbahAturanPakai.setText("Ubah Aturan Pakai Obat Umum");
        ppUbahAturanPakai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppUbahAturanPakai.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppUbahAturanPakai.setName("ppUbahAturanPakai"); // NOI18N
        ppUbahAturanPakai.setPreferredSize(new java.awt.Dimension(225, 25));
        ppUbahAturanPakai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppUbahAturanPakaiActionPerformed(evt);
            }
        });
        Popup2.add(ppUbahAturanPakai);

        ppUbahAturanPakai1.setBackground(new java.awt.Color(255, 255, 254));
        ppUbahAturanPakai1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppUbahAturanPakai1.setForeground(new java.awt.Color(50, 50, 50));
        ppUbahAturanPakai1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppUbahAturanPakai1.setText("Ubah Aturan Pakai Obat Racikan");
        ppUbahAturanPakai1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppUbahAturanPakai1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppUbahAturanPakai1.setName("ppUbahAturanPakai1"); // NOI18N
        ppUbahAturanPakai1.setPreferredSize(new java.awt.Dimension(225, 25));
        ppUbahAturanPakai1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppUbahAturanPakai1ActionPerformed(evt);
            }
        });
        Popup2.add(ppUbahAturanPakai1);

        WindowInput3.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput3.setName("WindowInput3"); // NOI18N
        WindowInput3.setUndecorated(true);
        WindowInput3.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ubah Aturan Pakai Obat Umum ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbTambahan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbTambahan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTambahan.setName("tbTambahan"); // NOI18N
        tbTambahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTambahanKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbTambahan);

        internalFrame4.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label15.setText("No.Resep :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label15);

        NoResepUbah3.setEditable(false);
        NoResepUbah3.setName("NoResepUbah3"); // NOI18N
        NoResepUbah3.setPreferredSize(new java.awt.Dimension(170, 23));
        NoResepUbah3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoResepUbah3KeyPressed(evt);
            }
        });
        panelisi1.add(NoResepUbah3);

        BtnSimpanInput3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanInput3.setMnemonic('S');
        BtnSimpanInput3.setText("Simpan");
        BtnSimpanInput3.setToolTipText("Alt+S");
        BtnSimpanInput3.setName("BtnSimpanInput3"); // NOI18N
        BtnSimpanInput3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpanInput3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanInput3ActionPerformed(evt);
            }
        });
        panelisi1.add(BtnSimpanInput3);

        BtnKeluarInput3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluarInput3.setMnemonic('K');
        BtnKeluarInput3.setText("Keluar");
        BtnKeluarInput3.setToolTipText("Alt+K");
        BtnKeluarInput3.setName("BtnKeluarInput3"); // NOI18N
        BtnKeluarInput3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarInput3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarInput3ActionPerformed(evt);
            }
        });
        panelisi1.add(BtnKeluarInput3);

        internalFrame4.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        WindowInput3.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        WindowInput4.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowInput4.setName("WindowInput4"); // NOI18N
        WindowInput4.setUndecorated(true);
        WindowInput4.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ubah Aturan Pakai Obat Racik ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbTambahan1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbTambahan1.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbTambahan1.setName("tbTambahan1"); // NOI18N
        tbTambahan1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbTambahan1KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbTambahan1);

        internalFrame5.add(scrollPane2, java.awt.BorderLayout.CENTER);

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label16.setText("No.Resep :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(label16);

        NoResepUbah4.setEditable(false);
        NoResepUbah4.setName("NoResepUbah4"); // NOI18N
        NoResepUbah4.setPreferredSize(new java.awt.Dimension(170, 23));
        NoResepUbah4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoResepUbah4KeyPressed(evt);
            }
        });
        panelisi2.add(NoResepUbah4);

        BtnSimpanInput4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanInput4.setMnemonic('S');
        BtnSimpanInput4.setText("Simpan");
        BtnSimpanInput4.setToolTipText("Alt+S");
        BtnSimpanInput4.setName("BtnSimpanInput4"); // NOI18N
        BtnSimpanInput4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpanInput4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanInput4ActionPerformed(evt);
            }
        });
        panelisi2.add(BtnSimpanInput4);

        BtnKeluarInput4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluarInput4.setMnemonic('K');
        BtnKeluarInput4.setText("Keluar");
        BtnKeluarInput4.setToolTipText("Alt+K");
        BtnKeluarInput4.setName("BtnKeluarInput4"); // NOI18N
        BtnKeluarInput4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluarInput4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarInput4ActionPerformed(evt);
            }
        });
        panelisi2.add(BtnKeluarInput4);

        internalFrame5.add(panelisi2, java.awt.BorderLayout.PAGE_END);

        WindowInput4.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Resep Yang Ditebus Keluar Oleh Pasien ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbResep.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbResep.setComponentPopupMenu(Popup2);
        tbResep.setName("tbResep"); // NOI18N
        Scroll.setViewportView(tbResep);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/item (copy).png"))); // NOI18N
        BtnResep.setMnemonic('R');
        BtnResep.setText("Resep");
        BtnResep.setToolTipText("Alt+R");
        BtnResep.setName("BtnResep"); // NOI18N
        BtnResep.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnResepActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnResep);

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

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(85, 30));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(100, 30));
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
        jLabel19.setPreferredSize(new java.awt.Dimension(53, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-09-2021 20:02:29" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(132, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "11-09-2021 20:02:29" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy HH:mm:ss");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(132, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(230, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('4');
        BtnCari.setToolTipText("Alt+4");
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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TCari.requestFocus();
        }else if(tbResep.getSelectedRow()>-1){
            if(!tbResep.getValueAt(tbResep.getSelectedRow(),0).toString().equals("")){
                Sequel.meghapus("resep_luar","no_resep",tbResep.getValueAt(tbResep.getSelectedRow(),0).toString());
                tampil();
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus resep. Pilih dulu data yang mau dihapus. Klik data Nomor Resep untuk memilih...!!!!");
            }   
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnResep,BtnPrint);
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
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
            Sequel.queryu("truncate table temporary_resep");
            
            for(int i=0;i<tabMode.getRowCount();i++){  
                Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                    "0",tabMode.getValueAt(i,0).toString(),tabMode.getValueAt(i,1).toString(),tabMode.getValueAt(i,2).toString(),
                    tabMode.getValueAt(i,3).toString(),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                });
            }
            
            Map<String, Object> param = new HashMap<>();  
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport2("rptResepLuar.jasper","report","::[ Daftar Resep Obat Yang Ditebus Keluar Oleh Pasien ]::",param);
            this.setCursor(Cursor.getDefaultCursor());
        }        
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
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
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void NoResepUbah3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoResepUbah3KeyPressed
        Valid.pindah(evt, BtnKeluarInput3, BtnSimpanInput3);
    }//GEN-LAST:event_NoResepUbah3KeyPressed

    private void BtnSimpanInput3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanInput3ActionPerformed
        if(NoResepUbah3.getText().trim().equals("")||(tbTambahan.getRowCount()<=0)){
            Valid.textKosong(NoResepUbah3,"Data");
        }else{
            Sequel.queryu2("delete from resep_luar_obat where no_resep=?",1,new String[]{NoResepUbah3.getText()});
            for(i=0;i<tbTambahan.getRowCount();i++){
                Sequel.menyimpan("resep_luar_obat","?,?,?,?",4,new String[]{
                    NoResepUbah3.getText(),tbTambahan.getValueAt(i,0).toString(),tbTambahan.getValueAt(i,2).toString(),tbTambahan.getValueAt(i,3).toString()
                });
            }
            
            tampil();
            WindowInput3.dispose();
        }
    }//GEN-LAST:event_BtnSimpanInput3ActionPerformed

    private void BtnKeluarInput3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarInput3ActionPerformed
        WindowInput3.dispose();
    }//GEN-LAST:event_BtnKeluarInput3ActionPerformed

    private void tbTambahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTambahanKeyPressed
        if(tbTambahan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbTambahan.getSelectedColumn();
                if(i==5){
                    pilihan=1;
                    aturanpakai.setSize(internalFrame4.getWidth()-20,internalFrame4.getHeight()-20);
                    aturanpakai.setLocationRelativeTo(internalFrame4);
                    aturanpakai.setVisible(true);
                }
            }
        }            
    }//GEN-LAST:event_tbTambahanKeyPressed

    private void tbTambahan1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbTambahan1KeyPressed
        if(tbTambahan1.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbTambahan1.getSelectedColumn();
                if(i==5){
                    pilihan=2;
                    aturanpakai.setSize(internalFrame5.getWidth()-20,internalFrame5.getHeight()-20);
                    aturanpakai.setLocationRelativeTo(internalFrame5);
                    aturanpakai.setVisible(true);
                }
            }
        } 
    }//GEN-LAST:event_tbTambahan1KeyPressed

    private void NoResepUbah4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoResepUbah4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoResepUbah4KeyPressed

    private void BtnSimpanInput4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanInput4ActionPerformed
       if(NoResepUbah4.getText().trim().equals("")||(tbTambahan1.getRowCount()<=0)){
            Valid.textKosong(NoResepUbah4,"Data");
        }else{
            for(i=0;i<tbTambahan1.getRowCount();i++){
                Sequel.queryu2("update resep_luar_racikan set aturan_pakai=?,nama_racik=? where no_resep=? and no_racik=?",4,new String[]{
                    tbTambahan1.getValueAt(i,2).toString(),tbTambahan1.getValueAt(i,1).toString(),NoResepUbah4.getText(),tbTambahan1.getValueAt(i,0).toString()
                });
            }
            
            tampil();
            WindowInput4.dispose();
        }
    }//GEN-LAST:event_BtnSimpanInput4ActionPerformed

    private void BtnKeluarInput4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarInput4ActionPerformed
        WindowInput4.dispose();
    }//GEN-LAST:event_BtnKeluarInput4ActionPerformed

    private void ppUbahAturanPakaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppUbahAturanPakaiActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TCari.requestFocus();
        }else if(tbResep.getSelectedRow()>-1){
            if(!tbResep.getValueAt(tbResep.getSelectedRow(),0).toString().equals("")){
                NoResepUbah3.setText(tbResep.getValueAt(tbResep.getSelectedRow(),0).toString());
                tampilresep();
                WindowInput3.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                WindowInput3.setLocationRelativeTo(internalFrame1);
                WindowInput3.setVisible(true);
            }
        }
    }//GEN-LAST:event_ppUbahAturanPakaiActionPerformed

    private void ppUbahAturanPakai1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppUbahAturanPakai1ActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TCari.requestFocus();
        }else if(tbResep.getSelectedRow()>-1){
            if(!tbResep.getValueAt(tbResep.getSelectedRow(),0).toString().equals("")){
                NoResepUbah4.setText(tbResep.getValueAt(tbResep.getSelectedRow(),0).toString());
                tampilresep2();
                WindowInput4.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                WindowInput4.setLocationRelativeTo(internalFrame1);
                WindowInput4.setVisible(true);
            }
        }
    }//GEN-LAST:event_ppUbahAturanPakai1ActionPerformed

    private void BtnResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnResepActionPerformed
        if(tabMode.getRowCount()==0){
             JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
             TCari.requestFocus();
        }else if(tbResep.getSelectedRow()>-1){
            if(!tbResep.getValueAt(tbResep.getSelectedRow(),0).toString().equals("")){
                try{  
                    ps=koneksi.prepareStatement("select resep_luar.no_resep,resep_luar.tgl_perawatan,resep_luar.jam,reg_periksa.kd_pj,"+
                            " resep_luar.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_luar.kd_dokter,dokter.nm_dokter "+
                            " from resep_luar inner join reg_periksa on resep_luar.no_rawat=reg_periksa.no_rawat "+
                            " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            " inner join dokter on resep_luar.kd_dokter=dokter.kd_dokter where resep_luar.no_resep=? ");
                    try{
                        ps.setString(1,tbResep.getValueAt(tbResep.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            Sequel.queryu("truncate table temporary_resep");
                            ps2=koneksi.prepareStatement(
                                "select databarang.kode_brng,databarang.nama_brng,resep_luar_obat.jml,kodesatuan.satuan,"+
                                "resep_luar_obat.aturan_pakai from resep_luar_obat inner join databarang on resep_luar_obat.kode_brng=databarang.kode_brng "+
                                "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat where resep_luar_obat.no_resep=? order by databarang.kode_brng");
                            try {
                                ps2.setString(1,rs.getString("no_resep"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                        "0",rs2.getString("nama_brng"),rs2.getString("aturan_pakai"),rs2.getString("jml"),rs2.getString("satuan"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }

                            psracikan=koneksi.prepareStatement(
                                    "select resep_luar_racikan.no_racik,resep_luar_racikan.nama_racik,"+
                                    "resep_luar_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                                    "resep_luar_racikan.jml_dr,resep_luar_racikan.aturan_pakai,"+
                                    "resep_luar_racikan.keterangan from resep_luar_racikan inner join metode_racik "+
                                    "on resep_luar_racikan.kd_racik=metode_racik.kd_racik where "+
                                    "resep_luar_racikan.no_resep=? order by resep_luar_racikan.no_racik");
                            try {
                                psracikan.setString(1,rs.getString("no_resep"));
                                rsracikan=psracikan.executeQuery();
                                while(rsracikan.next()){
                                    rincianobat="";
                                    ps2=koneksi.prepareStatement(
                                        "select databarang.kode_brng,databarang.nama_brng,resep_luar_racikan_detail.jml,kodesatuan.satuan from "+
                                        "resep_luar_racikan_detail inner join databarang on resep_luar_racikan_detail.kode_brng=databarang.kode_brng "+
                                        "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat where resep_luar_racikan_detail.no_resep=? and "+
                                        "resep_luar_racikan_detail.no_racik=? order by databarang.kode_brng");
                                    try{ 
                                        ps2.setString(1,rs.getString("no_resep"));
                                        ps2.setString(2,rsracikan.getString("no_racik"));
                                        rs2=ps2.executeQuery();
                                        while(rs2.next()){
                                            rincianobat=rs2.getString("nama_brng")+" "+rs2.getString("jml")+" "+rs2.getString("satuan")+","+rincianobat;
                                        }                                
                                    } catch (Exception e) {
                                        System.out.println("Notifikasi Detail Racikan : "+e);
                                    } finally{
                                        if(rs2!=null){
                                            rs2.close();
                                        }
                                        if(ps2!=null){
                                            ps2.close();
                                        }
                                    }
                                    
                                    rincianobat = rincianobat.substring(0,rincianobat.length() - 1);
                                    
                                    Sequel.menyimpan("temporary_resep","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                                        "0",rsracikan.getString("no_racik")+". "+rsracikan.getString("nama_racik")+" ("+rincianobat+")",rsracikan.getString("aturan_pakai"),rsracikan.getString("jml_dr"),rsracikan.getString("metode"),"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notif Racikan : "+e);
                            } finally{
                                if(rsracikan!=null){
                                    rsracikan.close();
                                }
                                if(psracikan!=null){
                                    psracikan.close();
                                }
                            }
                            Map<String, Object> param = new HashMap<>();  
                            param.put("namars",akses.getnamars());
                            param.put("alamatrs",akses.getalamatrs());
                            param.put("kotars",akses.getkabupatenrs());
                            param.put("propinsirs",akses.getpropinsirs());
                            param.put("emailrs",akses.getemailrs());
                            param.put("kontakrs",akses.getkontakrs());
                            param.put("penanggung",Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",rs.getString("kd_pj")));               
                            param.put("propinsirs",akses.getpropinsirs());
                            param.put("tanggal",Valid.SetTgl(rs.getString("tgl_perawatan")+""));
                            param.put("norawat",rs.getString("no_rawat"));
                            param.put("pasien",rs.getString("nm_pasien"));
                            param.put("norm",rs.getString("no_rkm_medis"));
                            param.put("peresep",rs.getString("nm_dokter"));
                            param.put("noresep",rs.getString("no_resep"));
                            finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter"));
                            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nm_dokter")+"\nID "+(finger.equals("")?rs.getString("kd_dokter"):finger)+"\n"+Valid.SetTgl(rs.getString("tgl_perawatan")+""));  
                            param.put("jam",rs.getString("jam"));
                            param.put("logo",Sequel.cariGambar("select logo from setting")); 

                            Valid.MyReport("rptLembarResepLuar.jasper",param,"::[ Lembar Resep Obat Yang Ditebus Keluar Oleh Pasien ]::");
                            this.setCursor(Cursor.getDefaultCursor());
                        }          
                    } catch(Exception ex){
                        System.out.println("Notifikasi : "+ex);
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
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Gagal mencetak resep. Pilih dulu data yang mau dicetak. Klik data Nomor Resep untuk memilih...!!!!");
            }   
        }
    }//GEN-LAST:event_BtnResepActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            InventoryCariResepLuar dialog = new InventoryCariResepLuar(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluarInput3;
    private widget.Button BtnKeluarInput4;
    private widget.Button BtnPrint;
    private widget.Button BtnResep;
    private widget.Button BtnSimpanInput3;
    private widget.Button BtnSimpanInput4;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private widget.TextBox NoResepUbah3;
    private widget.TextBox NoResepUbah4;
    private javax.swing.JPopupMenu Popup2;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private javax.swing.JDialog WindowInput3;
    private javax.swing.JDialog WindowInput4;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.Label label15;
    private widget.Label label16;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private javax.swing.JMenuItem ppUbahAturanPakai;
    private javax.swing.JMenuItem ppUbahAturanPakai1;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbResep;
    private widget.Table tbTambahan;
    private widget.Table tbTambahan1;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{  
            ps=koneksi.prepareStatement("select resep_luar.no_resep,resep_luar.tgl_perawatan,resep_luar.jam,"+
                    " resep_luar.no_rawat,pasien.no_rkm_medis,pasien.nm_pasien,resep_luar.kd_dokter,dokter.nm_dokter "+
                    " from resep_luar inner join reg_periksa on resep_luar.no_rawat=reg_periksa.no_rawat "+
                    " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    " inner join dokter on resep_luar.kd_dokter=dokter.kd_dokter where "+
                    " concat(resep_luar.tgl_perawatan,' ',resep_luar.jam) between ? and ? and "+
                    " (resep_luar.no_resep like ? or resep_luar.no_rawat like ? or pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or dokter.nm_dokter like ?) "+
                    " order by resep_luar.tgl_perawatan,resep_luar.jam");
            try{
                ps.setString(1,Valid.SetTglJam(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTglJam(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,"%"+TCari.getText().trim()+"%");
                ps.setString(5,"%"+TCari.getText().trim()+"%");
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_resep"),rs.getString("tgl_perawatan")+" "+rs.getString("jam"),
                        rs.getString("no_rawat")+" "+rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        rs.getString("nm_dokter")
                    });
                    tabMode.addRow(new String[]{"","Jumlah","Nama Obat","Aturan Pakai"});                
                    ps2=koneksi.prepareStatement(
                        "select databarang.kode_brng,databarang.nama_brng,resep_luar_obat.jml,kodesatuan.satuan,"+
                        "resep_luar_obat.aturan_pakai from resep_luar_obat inner join databarang on resep_luar_obat.kode_brng=databarang.kode_brng "+
                        "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat where resep_luar_obat.no_resep=? order by databarang.kode_brng");
                    try {
                        ps2.setString(1,rs.getString("no_resep"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            tabMode.addRow(new String[]{
                                "",rs2.getString("jml")+" "+rs2.getString("satuan"),rs2.getString("nama_brng"),rs2.getString("aturan_pakai")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    
                    psracikan=koneksi.prepareStatement(
                            "select resep_luar_racikan.no_racik,resep_luar_racikan.nama_racik,"+
                            "resep_luar_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                            "resep_luar_racikan.jml_dr,resep_luar_racikan.aturan_pakai,"+
                            "resep_luar_racikan.keterangan from resep_luar_racikan inner join metode_racik "+
                            "on resep_luar_racikan.kd_racik=metode_racik.kd_racik where "+
                            "resep_luar_racikan.no_resep=? ");
                    try {
                        psracikan.setString(1,rs.getString("no_resep"));
                        rsracikan=psracikan.executeQuery();
                        while(rsracikan.next()){
                            tabMode.addRow(new String[]{
                                "",rsracikan.getString("jml_dr")+" "+rsracikan.getString("metode"),rsracikan.getString("no_racik")+". "+rsracikan.getString("nama_racik")+", Keterangan : "+rsracikan.getString("keterangan"),rsracikan.getString("aturan_pakai")
                            });
                            
                            ps2=koneksi.prepareStatement(
                                "select databarang.kode_brng,databarang.nama_brng,resep_luar_racikan_detail.jml,kodesatuan.satuan from "+
                                "resep_luar_racikan_detail inner join databarang on resep_luar_racikan_detail.kode_brng=databarang.kode_brng "+
                                "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat where resep_luar_racikan_detail.no_resep=? and "+
                                "resep_luar_racikan_detail.no_racik=? order by databarang.kode_brng");
                            try{ 
                                ps2.setString(1,rs.getString("no_resep"));
                                ps2.setString(2,rsracikan.getString("no_racik"));
                                rs2=ps2.executeQuery();
                                while(rs2.next()){
                                    tabMode.addRow(new String[]{
                                        "","   "+rs2.getString("jml")+" "+rs2.getString("satuan"),"   "+rs2.getString("nama_brng"),""
                                    });
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi Detail Racikan : "+e);
                            } finally{
                                if(rs2!=null){
                                    rs2.close();
                                }
                                if(ps2!=null){
                                    ps2.close();
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Notif Racikan : "+e);
                    } finally{
                        if(rsracikan!=null){
                            rsracikan.close();
                        }
                        if(psracikan!=null){
                            psracikan.close();
                        }
                    }
                }   
                rs.last();
                LCount.setText(""+rs.getRow());
            } catch(Exception ex){
                System.out.println("Notifikasi : "+ex);
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
    }
    
    private void tampilresep() {
        Valid.tabelKosong(tabmodeUbahRacikan);
        try {
            try {
                ps2=koneksi.prepareStatement(
                        "select resep_luar_obat.kode_brng,databarang.nama_brng,resep_luar_obat.jml,resep_luar_obat.aturan_pakai from resep_luar_obat "+
                        "inner join databarang on resep_luar_obat.kode_brng=databarang.kode_brng where resep_luar_obat.no_resep=?");
                ps2.setString(1,NoResepUbah3.getText());
                rs2=ps2.executeQuery();
                while(rs2.next()){
                    tabmodeUbahRacikan.addRow(new String[]{
                        rs2.getString("kode_brng"),rs2.getString("nama_brng"),rs2.getString("jml"),rs2.getString("aturan_pakai")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
    }
    
    private void tampilresep2() {
        Valid.tabelKosong(tabmodeUbahRacikan2);
        try {
            try {
                ps2=koneksi.prepareStatement(
                        "select resep_luar_racikan.no_racik,resep_luar_racikan.nama_racik,resep_luar_racikan.aturan_pakai from resep_luar_racikan where resep_luar_racikan.no_resep=?");
                ps2.setString(1,NoResepUbah4.getText());
                rs2=ps2.executeQuery();
                while(rs2.next()){
                    tabmodeUbahRacikan2.addRow(new String[]{
                        rs2.getString("no_racik"),rs2.getString("nama_racik"),rs2.getString("aturan_pakai")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs2!=null){
                    rs2.close();
                }
                if(ps2!=null){
                    ps2.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }

    public void setNoRm(String norwt,Date tgl1) {
        TCari.setText(norwt);
        tampil();
        Valid.SetTgl2(DTPCari1,format.format(tgl1)+" 00:00:00");
        Valid.SetTgl2(DTPCari2,format.format(tgl1)+" 23:59:59");
    }
    
    public void isCek(){
        BtnHapus.setEnabled(akses.getresep_luar());
        BtnPrint.setEnabled(akses.getresep_luar());
    }
}

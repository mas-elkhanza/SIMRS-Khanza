

package keuangan;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.DlgCariPiutang;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

/**
 *
 * @author perpustakaan
 */
public final class KeuanganPiutangObatBelumLunas extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int row=0,i;
    private String koderekening="";
    private Jurnal jur=new Jurnal();
    private String akunpiutang="",Diskon_Piutang="",Piutang_Tidak_Terbayar="";
    private double total=0,sisapiutang=0;
    private boolean sukses=true;
    private File file;
    private FileWriter fileWriter;
    private String iyem;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private FileReader myObj;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public KeuanganPiutangObatBelumLunas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "P","No.Nota","Tgl.Piutang","Pasien","Catatan","Total Piutang","Uang Muka","Ogkos Kirim","Cicilan+Disk+T.Terbayar",
                "Sisa Piutang","Jatuh Tempo","Bayar","Diskon Bayar","Tidak Terbayar"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==11)||(colIndex==12)||(colIndex==13)||(colIndex==0)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(65);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(130);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(80);
            }else if(i==13){
                column.setPreferredWidth(80);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
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
        
        try {
            ps=koneksi.prepareStatement(
                    "select set_akun.Diskon_Piutang,set_akun.Piutang_Tidak_Terbayar,set_akun.Piutang_Obat from set_akun");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    Diskon_Piutang=rs.getString("Diskon_Piutang");
                    Piutang_Tidak_Terbayar=rs.getString("Piutang_Tidak_Terbayar");
                    akunpiutang=rs.getString("Piutang_Obat");
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
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TKd = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnDetailPiutang = new javax.swing.JMenuItem();
        ppPilihSemua = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelisi4 = new widget.panelisi();
        label32 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel11 = new widget.Label();
        AkunBayar = new widget.ComboBox();
        BtnAll1 = new widget.Button();
        jPanel1 = new javax.swing.JPanel();
        panelisi3 = new widget.panelisi();
        label17 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount2 = new widget.Label();
        panelisi1 = new widget.panelisi();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        LCount1 = new javax.swing.JLabel();
        BtnBayar = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnDetailPiutang.setBackground(new java.awt.Color(255, 255, 254));
        MnDetailPiutang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDetailPiutang.setForeground(new java.awt.Color(50, 50, 50));
        MnDetailPiutang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnDetailPiutang.setText("Detail Piutang");
        MnDetailPiutang.setName("MnDetailPiutang"); // NOI18N
        MnDetailPiutang.setPreferredSize(new java.awt.Dimension(150, 28));
        MnDetailPiutang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDetailPiutangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDetailPiutang);

        ppPilihSemua.setBackground(new java.awt.Color(255, 255, 254));
        ppPilihSemua.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPilihSemua.setForeground(new java.awt.Color(50, 50, 50));
        ppPilihSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPilihSemua.setText("Pilih Semua");
        ppPilihSemua.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPilihSemua.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilihSemua.setName("ppPilihSemua"); // NOI18N
        ppPilihSemua.setPreferredSize(new java.awt.Dimension(150, 26));
        ppPilihSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPilihSemuaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppPilihSemua);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Hilangkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(150, 26));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBersihkan);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Piutang Obat & BHP Belum Lunas ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setComponentPopupMenu(jPopupMenu1);
        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        tbBangsal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbBangsalPropertyChange(evt);
            }
        });
        tbBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBangsalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label32.setText("Tanggal Bayar :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi4.add(label32);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });
        panelisi4.add(Tanggal);

        jLabel11.setText("Akun Bayar :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(120, 23));
        panelisi4.add(jLabel11);

        AkunBayar.setName("AkunBayar"); // NOI18N
        AkunBayar.setPreferredSize(new java.awt.Dimension(320, 23));
        AkunBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AkunBayarKeyPressed(evt);
            }
        });
        panelisi4.add(AkunBayar);

        BtnAll1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnAll1.setMnemonic('M');
        BtnAll1.setToolTipText("Alt+M");
        BtnAll1.setName("BtnAll1"); // NOI18N
        BtnAll1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAll1ActionPerformed(evt);
            }
        });
        panelisi4.add(BtnAll1);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 101));
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label17.setText("Key Word :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(label17);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(400, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

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
        panelisi3.add(BtnCari);

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
        panelisi3.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(jLabel7);

        LCount2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount2.setText("0");
        LCount2.setName("LCount2"); // NOI18N
        LCount2.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi3.add(LCount2);

        jPanel1.add(panelisi3, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(55, 55));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(50, 50, 50));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Belum Dibayar :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(87, 23));
        panelisi1.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount.setForeground(new java.awt.Color(50, 50, 50));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(110, 23));
        panelisi1.add(LCount);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(50, 50, 50));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Dipilih :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi1.add(jLabel12);

        LCount1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        LCount1.setForeground(new java.awt.Color(50, 50, 50));
        LCount1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount1.setText("0");
        LCount1.setName("LCount1"); // NOI18N
        LCount1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi1.add(LCount1);

        BtnBayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnBayar.setMnemonic('B');
        BtnBayar.setText("Bayar");
        BtnBayar.setToolTipText("Alt+B");
        BtnBayar.setName("BtnBayar"); // NOI18N
        BtnBayar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBayarActionPerformed(evt);
            }
        });
        BtnBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBayarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBayar);

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

        jPanel1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            int row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                    Sequel.menyimpan("temporary","'"+i+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,5).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,6).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,7).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,8).toString()))+"','"+
                                Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,9).toString()))+"','"+
                                tabMode.getValueAt(i,10).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Piutang Pasien"); 
            }
            i++;
            Sequel.menyimpan("temporary","'"+i+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
            i++;
            Sequel.menyimpan("temporary","'"+i+"','TOTAL PIUTANG :','','','','','','','','"+LCount.getText()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Rekap Harian Tindakan Dokter"); 
            
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptPiutangObatBelumLunas.jasper","report","::[ Rekap Piutang Obat Belum lunas ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBayar, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
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

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                getdata();
            }
        }
}//GEN-LAST:event_tbBangsalKeyPressed

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
            tampil();
        }else{
            Valid.pindah(evt, TKd, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

private void MnDetailPiutangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDetailPiutangActionPerformed
     if(tabMode.getRowCount()==0){
        JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
        TCari.requestFocus();
    }else{
         if(tbBangsal.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgCariPiutang form=new DlgCariPiutang(null,false);
            form.emptTeks();   
            form.cariNoTagihan(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString(),Valid.SetTgl2(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),2).toString()));
            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            form.setLocationRelativeTo(internalFrame1);
            form.setAlwaysOnTop(false);
            form.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
         }else{
             JOptionPane.showMessageDialog(null,"Silahkan pilih data terlebih dahulu...!!");
         }                   
    } 
}//GEN-LAST:event_MnDetailPiutangActionPerformed

    private void BtnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBayarActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
        }else if(total<=0){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih piutang yang mau dibayar..!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.AutoComitFalse();
            sukses=true;
            
            koderekening="";
            try {
                myObj = new FileReader("./cache/akunbayar.iyem");
                root = mapper.readTree(myObj);
                response = root.path("akunbayar");
                if(response.isArray()){
                   for(JsonNode list:response){
                       if(list.path("NamaAkun").asText().equals(AkunBayar.getSelectedItem().toString())){
                            koderekening=list.path("KodeRek").asText();  
                       }
                   }
                }
                myObj.close();
            } catch (Exception e) {
                sukses=false;
            } 
            
            row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                if(tabMode.getValueAt(i,0).toString().equals("true")){
                    if(Double.parseDouble(tabMode.getValueAt(i,9).toString())<0){
                        JOptionPane.showMessageDialog(null,"Nilai pelunasan lebih besar dari sisa piutang...!!");
                        tbBangsal.setValueAt(false,i,0);
                        sukses=false;
                    }else{
                        if(Sequel.menyimpantf("bayar_piutang","?,?,?,?,?,?,?,?,?,?,?","Data",11,new String[]{
                            Valid.SetTgl(Tanggal.getSelectedItem()+""),Sequel.cariIsi("select piutang.no_rkm_medis from piutang where piutang.nota_piutang=?",tabMode.getValueAt(i,1).toString()),
                            tabMode.getValueAt(i,11).toString(),"diverifikasi oleh "+akses.getkode(),tabMode.getValueAt(i,1).toString(),koderekening,akunpiutang,tabMode.getValueAt(i,12).toString(),
                            Diskon_Piutang,tabMode.getValueAt(i,13).toString(),Piutang_Tidak_Terbayar
                        })==true){
                            Sequel.queryu("delete from tampjurnal");                    
                            Sequel.menyimpan("tampjurnal","'"+akunpiutang+"','BAYAR PIUTANG','0','"+(Double.parseDouble(tabMode.getValueAt(i,11).toString())+Double.parseDouble(tabMode.getValueAt(i,12).toString())+Double.parseDouble(tabMode.getValueAt(i,13).toString()))+"'","Rekening");    
                            if(Double.parseDouble(tabMode.getValueAt(i,11).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+koderekening+"','"+AkunBayar.getSelectedItem()+"','"+tabMode.getValueAt(i,11).toString()+"','0'","Rekening"); 
                            }
                            if(Double.parseDouble(tabMode.getValueAt(i,12).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Diskon_Piutang+"','DISKON BAYAR','"+tabMode.getValueAt(i,12).toString()+"','0'","Rekening");
                            }
                            if(Double.parseDouble(tabMode.getValueAt(i,13).toString())>0){
                                Sequel.menyimpan("tampjurnal","'"+Piutang_Tidak_Terbayar+"','PIUTANG TIDAK TERBAYAR','"+tabMode.getValueAt(i,13).toString()+"','0'","Rekening"); 
                            }
                            sukses=jur.simpanJurnal(tabMode.getValueAt(i,1).toString(),"U","BAYAR PIUTANG"+", OLEH "+akses.getkode());                   
                        }else{
                            sukses=false;
                        }
                    }
                }
            }
            
            if(sukses==true){
                Sequel.Commit();
            }else{
                sukses=false;
                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                Sequel.RollBack();
            }
            Sequel.AutoComitTrue();
            
            if(sukses==true){
                tampil();
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnBayarActionPerformed

    private void BtnBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBayarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnBayarActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnPrint);
        }
    }//GEN-LAST:event_BtnBayarKeyPressed

    private void AkunBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AkunBayarKeyPressed
        Valid.pindah(evt,Tanggal,TCari);
    }//GEN-LAST:event_AkunBayarKeyPressed

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        Valid.pindah(evt,TCari,AkunBayar);
    }//GEN-LAST:event_TanggalKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            if(tbBangsal.getSelectedColumn()==0){
                getdata();
            }
        }
    }//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbBangsalPropertyChange
        if(this.isVisible()==true){
            total=0;
            try {
                if(tbBangsal.getSelectedRow()!= -1){
                    if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("true")){
                        if(Double.parseDouble(tabMode.getValueAt(tbBangsal.getSelectedRow(),9).toString())<0){
                            JOptionPane.showMessageDialog(null,"Nilai pelunasan lebih besar dari sisa piutang...!!");
                            tbBangsal.setValueAt(false,tbBangsal.getSelectedRow(),0);
                        }else{
                            tbBangsal.setValueAt(
                                    (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString()))-
                                    (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),8).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString())+
                                    Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),13).toString())),
                                    tbBangsal.getSelectedRow(),9);
                        }
                    }else if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("false")){
                        tbBangsal.setValueAt(
                                (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString()))-
                                (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),8).toString())),
                                tbBangsal.getSelectedRow(),9);
                        tbBangsal.setValueAt(
                                (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString()))-
                                (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),8).toString())),
                                tbBangsal.getSelectedRow(),11);
                        tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),12);
                        tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),13);
                    }
                } 
            } catch (Exception e) {
            }
            row=tbBangsal.getRowCount();
            for(i=0;i<row;i++){  
                if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                    total=total+(Double.parseDouble(tbBangsal.getValueAt(i,5).toString())+
                         Double.parseDouble(tbBangsal.getValueAt(i,7).toString()))-
                        (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                        Double.parseDouble(tbBangsal.getValueAt(i,8).toString()));     
                }
            }
            LCount1.setText(Valid.SetAngka(total));
        }
    }//GEN-LAST:event_tbBangsalPropertyChange

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        try {
            if(Valid.daysOld("./cache/akunbayar.iyem")<8){
                tampilAkunBayar2();
            }else{
                tampilAkunBayar();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_formWindowOpened

    private void BtnAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAll1ActionPerformed
        tampilAkunBayar();
    }//GEN-LAST:event_BtnAll1ActionPerformed

    private void ppPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihSemuaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        total=0;
        for(i=0;i<tbBangsal.getRowCount();i++){
            tbBangsal.setValueAt(true,i,0);
            getdata(i);
        }
        row=tbBangsal.getRowCount();
        for(i=0;i<row;i++){  
            if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                 total=total+(Double.parseDouble(tbBangsal.getValueAt(i,5).toString())+
                         Double.parseDouble(tbBangsal.getValueAt(i,7).toString()))-
                        (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                        Double.parseDouble(tbBangsal.getValueAt(i,8).toString()));     
            }
        }
        LCount1.setText(Valid.SetAngka(total));
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppPilihSemuaActionPerformed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        total=0;
        for(i=0;i<tbBangsal.getRowCount();i++){
            tbBangsal.setValueAt(false,i,0);
            getdata(i);
        }
        row=tbBangsal.getRowCount();
        for(i=0;i<row;i++){  
            if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                 total=total+(Double.parseDouble(tbBangsal.getValueAt(i,5).toString())+
                         Double.parseDouble(tbBangsal.getValueAt(i,7).toString()))-
                        (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                        Double.parseDouble(tbBangsal.getValueAt(i,8).toString()));     
            }
        }
        LCount1.setText(Valid.SetAngka(total));
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppBersihkanActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganPiutangObatBelumLunas dialog = new KeuanganPiutangObatBelumLunas(new javax.swing.JFrame(), true);
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
    private widget.ComboBox AkunBayar;
    private widget.Button BtnAll;
    private widget.Button BtnAll1;
    private widget.Button BtnBayar;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private javax.swing.JLabel LCount;
    private javax.swing.JLabel LCount1;
    private widget.Label LCount2;
    private javax.swing.JMenuItem MnDetailPiutang;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.Tanggal Tanggal;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel10;
    private widget.Label jLabel11;
    private javax.swing.JLabel jLabel12;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label17;
    private widget.Label label32;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppPilihSemua;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    private void tampil(){
        Valid.tabelKosong(tabMode);
        try{
            sisapiutang=0;
            ps=koneksi.prepareStatement(
                    "select piutang.nota_piutang,piutang.tgl_piutang,piutang.no_rkm_medis,piutang.nm_pasien,piutang.catatan,piutang.ongkir,piutang.uangmuka,piutang.sisapiutang,"+
                    "piutang.tgltempo,(select ifnull(SUM(bayar_piutang.besar_cicilan)+SUM(bayar_piutang.diskon_piutang)+SUM(bayar_piutang.tidak_terbayar),0) from bayar_piutang where bayar_piutang.no_rawat=piutang.nota_piutang) as cicilan  "+
                    "from piutang "+(TCari.getText().trim().equals("")?"":"where piutang.nota_piutang like ? or petugas.nama like ? or "+
                    "piutang.no_rkm_medis like ? or piutang.nm_pasien like ?")+" having piutang.sisapiutang-cicilan>0 order by piutang.tgl_piutang");
            try {
                if(!TCari.getText().trim().equals("")){
                    ps.setString(1,"%"+TCari.getText()+"%");
                    ps.setString(2,"%"+TCari.getText()+"%");
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("nota_piutang"),rs.getString("tgl_piutang"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),rs.getString("catatan"),
                        (rs.getDouble("uangmuka")+rs.getDouble("sisapiutang")-rs.getDouble("ongkir")),rs.getDouble("uangmuka"),rs.getDouble("ongkir"),rs.getDouble("cicilan"),
                        (rs.getDouble("sisapiutang")-rs.getDouble("cicilan")),rs.getString("tgltempo"),(rs.getDouble("sisapiutang")-rs.getDouble("cicilan")),0,0
                    });
                    sisapiutang=sisapiutang+(rs.getDouble("sisapiutang")-rs.getDouble("cicilan"));
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
            
            LCount.setText(Valid.SetAngka(sisapiutang));
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void getdata() {
        total=0;
        try {
            if(tbBangsal.getSelectedRow()!= -1){
                if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("true")){
                    if(Double.parseDouble(tabMode.getValueAt(tbBangsal.getSelectedRow(),9).toString())<0){
                        JOptionPane.showMessageDialog(null,"Nilai pelunasan lebih besar dari sisa piutang...!!");
                       tbBangsal.setValueAt(false,tbBangsal.getSelectedRow(),0);
                    }else{
                        tbBangsal.setValueAt(
                                (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString()))-
                                (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),8).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),13).toString())),
                                tbBangsal.getSelectedRow(),9);
                    }
                }else if(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString().equals("false")){
                    tbBangsal.setValueAt(
                            (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString()))-
                            (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),8).toString())),
                            tbBangsal.getSelectedRow(),9);
                    tbBangsal.setValueAt(
                            (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString()))-
                            (Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),8).toString())),
                            tbBangsal.getSelectedRow(),11);
                    tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),12);
                    tbBangsal.setValueAt(0,tbBangsal.getSelectedRow(),13);
                }
            }  
        } catch (Exception e) {
        }
        row=tbBangsal.getRowCount();
        for(i=0;i<row;i++){  
            if(tbBangsal.getValueAt(i,0).toString().equals("true")){
                 total=total+(Double.parseDouble(tbBangsal.getValueAt(i,5).toString())+
                         Double.parseDouble(tbBangsal.getValueAt(i,7).toString()))-
                        (Double.parseDouble(tbBangsal.getValueAt(i,6).toString())+
                        Double.parseDouble(tbBangsal.getValueAt(i,8).toString()));     
            }
        }
        LCount1.setText(Valid.SetAngka(total));
    }
    
    private void getdata(int pilih) {
        try {
            if(pilih!= -1){
                if(tbBangsal.getValueAt(pilih,0).toString().equals("true")){
                    if(Double.parseDouble(tabMode.getValueAt(pilih,9).toString())<0){
                        JOptionPane.showMessageDialog(null,"Nilai pelunasan lebih besar dari sisa piutang...!!");
                       tbBangsal.setValueAt(false,tbBangsal.getSelectedRow(),0);
                    }else{
                        tbBangsal.setValueAt(
                                (Double.parseDouble(tbBangsal.getValueAt(pilih,5).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(pilih,7).toString()))-
                                (Double.parseDouble(tbBangsal.getValueAt(pilih,6).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(pilih,8).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(pilih,11).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(pilih,12).toString())+
                                Double.parseDouble(tbBangsal.getValueAt(pilih,13).toString())),
                                pilih,9);
                    }
                }else if(tbBangsal.getValueAt(pilih,0).toString().equals("false")){
                    tbBangsal.setValueAt(
                            (Double.parseDouble(tbBangsal.getValueAt(pilih,5).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(pilih,7).toString()))-
                            (Double.parseDouble(tbBangsal.getValueAt(pilih,6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(pilih,8).toString())),
                            pilih,9);
                    tbBangsal.setValueAt(
                            (Double.parseDouble(tbBangsal.getValueAt(pilih,5).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(pilih,7).toString()))-
                            (Double.parseDouble(tbBangsal.getValueAt(pilih,6).toString())+
                            Double.parseDouble(tbBangsal.getValueAt(pilih,8).toString())),
                            pilih,11);
                    tbBangsal.setValueAt(0,pilih,12);
                    tbBangsal.setValueAt(0,pilih,13);
                }
            }  
        } catch (Exception e) {
        }
    }
    
    public void isCek(){
        TCari.requestFocus();
        BtnBayar.setEnabled(akses.getbayar_piutang());
    }
    
    private void tampilAkunBayar() {         
         try{      
             file=new File("./cache/akunbayar.iyem");
             file.createNewFile();
             fileWriter = new FileWriter(file);
             iyem="";
             ps=koneksi.prepareStatement("select * from akun_bayar order by akun_bayar.nama_bayar");
             try{
                 rs=ps.executeQuery();
                 AkunBayar.removeAllItems();
                 while(rs.next()){    
                     AkunBayar.addItem(rs.getString(1).replaceAll("\"",""));
                     iyem=iyem+"{\"NamaAkun\":\""+rs.getString(1).replaceAll("\"","")+"\",\"KodeRek\":\""+rs.getString(2)+"\",\"PPN\":\""+rs.getDouble(3)+"\"},";
                 }
             }catch (Exception e) {
                 System.out.println("Notifikasi : "+e);
             } finally{
                 if(rs != null){
                     rs.close();
                 } 
                 if(ps != null){
                     ps.close();
                 } 
             }

             fileWriter.write("{\"akunbayar\":["+iyem.substring(0,iyem.length()-1)+"]}");
             fileWriter.flush();
             fileWriter.close();
             iyem=null;
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void tampilAkunBayar2() {
        try {
            myObj = new FileReader("./cache/akunbayar.iyem");
            root = mapper.readTree(myObj);
            response = root.path("akunbayar");
            if(response.isArray()){
                for(JsonNode list:response){
                    AkunBayar.addItem(list.path("NamaAkun").asText().replaceAll("\"",""));
                }
            }
            myObj.close();
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
        }
    } 
}

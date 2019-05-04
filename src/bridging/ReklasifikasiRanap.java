/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgLhtBiaya.java
 *
 * Created on 12 Jul 10, 16:21:34
 */

package bridging;

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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgBilingRanap;
import simrskhanza.DlgPenanggungJawab;

/**
 *
 * @author perpustakaan
 */
public final class ReklasifikasiRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps,ps2,pspenyakit,psprosedur;
    private ResultSet rs,rs2;
    private DlgPenanggungJawab penjab=new DlgPenanggungJawab(null,false);
    private double all=0,Laborat=0,Radiologi=0,Operasi=0,Obat=0,Ranap_Dokter=0,Ranap_Paramedis=0,Ranap_Dokter_Paramedis=0,Ralan_Dokter=0,
             Ralan_Paramedis=0,ttlRalan_Dokter_Paramedis=0,Ralan_Dokter_Paramedis=0,Tambahan=0,Potongan=0,Kamar=0,Registrasi=0,Harian=0,Retur_Obat=0,Resep_Pulang=0,
             ttlRanap_Dokter_Paramedis=0,Service=0,ttlLaborat=0,ttlRadiologi=0,ttlOperasi=0,ttlObat=0,ttlRanap_Dokter=0,ttlRanap_Paramedis=0,ttlRalan_Dokter=0,
             ttlRalan_Paramedis=0,ttlTambahan=0,ttlPotongan=0,ttlKamar=0,ttlRegistrasi=0,ttlHarian=0,ttlRetur_Obat=0,ttlResep_Pulang=0,
             ttlService=0,operasi2=0,kebidanan=0,tarifincabg=0,ttltarifincabg=0,ttluntungrugiinacbg=0,ttlkebidanan=0,untungrugiinacbg=0;
    private String sqlps2="select sum(totalbiaya) from billing where no_rawat=? and status=? ";
    private String status="",du="",ds1="",ds2="",ds3="",ds4="",ds5="",ds6="",
            ds7="",ds8="",ds9="",ds10="",ds11="",ds12="",ds13="",ds14="",ds15="",
            ds16="",ds17="",ds18="",ds19="",ds20="",ds21="",ds22="",ds23="",
            ds24="",ds25="",ds26="",ds27="",ds28="",ds29="",p1="",p2="",p3="",
            p4="",p5="",p6="",p7="",p8="",p9="",p10="",p11="",p12="",p13="",
            p14="",p15="",p16="",p17="",p18="",p19="",p20="",p21="",p22="",
            p23="",p24="",p25="",p26="",p27="",p28="",p29="",p30="";
    
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public ReklasifikasiRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);

        tabMode=new DefaultTableModel(null,new String[]{
            "No.Rawat","No.RM","Cara Bayar","Waktu Masuk","Waktu Keluar",
            "LOS(hari)","J.K.","Tgl.Lahir","Tipe Rawat","Kelas Rawat",
            "Stts Keluar","D.U","D.S 1","D.S 2","D.S 3","D.S 4","D.S 5",
            "D.S 6","D.S 7","D.S 8","D.S 9","D.S 10","D.S 11","D.S 12",
            "D.S 13","D.S 14","D.S 15","D.S 16","D.S 17","D.S 18","D.S 19",
            "D.S 20","D.S 21","D.S 22","D.S 23","D.S 24","D.S 25","D.S 26",
            "D.S 27","D.S 28","D.S 29","P 1","P 2","P 3","P 4","P 5","P 6",
            "P 7","P 8","P 9","P 10","P 11","P 12","P 13","P 14","P 15",
            "P 16","P 17","P 18","P 19","P 20","P 21","P 22","P 23","P 24",
            "P 25","P 26","P 27","P 28","P 29","P 30","INA-CBG","Total Trf RS",
            "Trf P. Non-Bedah","Trf P. Bedah","Trf Ksl/Vst Dokter",
            "Trf Ksl/Vst Tng. Ahli","Trf Tnd Keperawatan","Tarif Penunjang",
            "Trf Radioterapi","Trf Lab","Trf UTD","Trf Radiologi",
            "Trf Rehabilitasi","Trf Akomodasi","Trf Ruang Intensif",
            "Trf Obat","Trf Alkes","Trf BMHP","Trf Sewa Alat","Potongan",
            "Tarif InaCBG","Untung/Rugi InaCBG"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 93; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(130);
            }else if(i==3){
                column.setPreferredWidth(110);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                column.setPreferredWidth(55);
            }else if(i==6){
                column.setPreferredWidth(32);
            }else if(i==7){
                column.setPreferredWidth(75);
            }else if(i==8){
                column.setPreferredWidth(65);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(80);
            }else if((i==11)||(i==12)||(i==13)||(i==14)||(i==15)||(i==16)||
                    (i==17)||(i==18)||(i==19)||(i==20)||(i==21)||(i==22)||
                    (i==23)||(i==24)||(i==25)||(i==26)||(i==27)||(i==28)||
                    (i==29)||(i==30)||(i==31)||(i==32)||(i==33)||(i==34)||
                    (i==35)||(i==36)||(i==37)||(i==38)||(i==39)||(i==66)||
                    (i==40)||(i==41)||(i==42)||(i==43)||(i==44)||(i==45)||
                    (i==46)||(i==47)||(i==48)||(i==49)||(i==50)||(i==51)||
                    (i==52)||(i==53)||(i==54)||(i==55)||(i==56)||(i==57)||
                    (i==58)||(i==59)||(i==60)||(i==61)||(i==62)||(i==63)||
                    (i==64)||(i==65)||(i==67)||(i==68)||(i==69)||(i==70)){
                column.setPreferredWidth(47);
            }else if(i==71){
                column.setPreferredWidth(55);
            }else{
                column.setPreferredWidth(110);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)20).getKata(TKd));
        
        penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penjab.getTable().getSelectedRow()!= -1){
                    kdpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),1).toString());
                    nmpenjab.setText(penjab.getTable().getValueAt(penjab.getTable().getSelectedRow(),2).toString());
                    tampil();
                }      
                kdpenjab.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {penjab.emptTeks();}
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
        MnBilling = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        panelGlass5 = new widget.panelisi();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        jLabel10 = new javax.swing.JLabel();
        LCount = new javax.swing.JLabel();
        BtnKeluar = new widget.Button();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdpenjab = new widget.TextBox();
        nmpenjab = new widget.TextBox();
        BtnSeek2 = new widget.Button();
        BtnCari1 = new widget.Button();

        TKd.setForeground(new java.awt.Color(255, 255, 255));
        TKd.setName("TKd"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnBilling.setBackground(new java.awt.Color(255, 255, 254));
        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(java.awt.Color.darkGray);
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnBilling.setText("Billing/Pembayaran Pasien");
        MnBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(250, 28));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnBilling);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Reklasifikasi Ranap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbBangsal.setComponentPopupMenu(jPopupMenu1);
        tbBangsal.setName("tbBangsal"); // NOI18N
        tbBangsal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbBangsalMouseClicked(evt);
            }
        });
        tbBangsal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbBangsalKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbBangsal);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass5.add(BtnAll);

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
        panelGlass5.add(BtnPrint);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(70, 70, 70));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Total Reklasifikasi :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(125, 23));
        panelGlass5.add(jLabel10);

        LCount.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        LCount.setForeground(new java.awt.Color(70, 70, 70));
        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(300, 23));
        panelGlass5.add(LCount);

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
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Tagihan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi4.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(27, 23));
        panelisi4.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Tgl2);

        label17.setText("Jenis Bayar :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(75, 23));
        panelisi4.add(label17);

        kdpenjab.setName("kdpenjab"); // NOI18N
        kdpenjab.setPreferredSize(new java.awt.Dimension(70, 23));
        kdpenjab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenjabKeyPressed(evt);
            }
        });
        panelisi4.add(kdpenjab);

        nmpenjab.setEditable(false);
        nmpenjab.setName("nmpenjab"); // NOI18N
        nmpenjab.setPreferredSize(new java.awt.Dimension(203, 23));
        panelisi4.add(nmpenjab);

        BtnSeek2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek2.setMnemonic('3');
        BtnSeek2.setToolTipText("Alt+3");
        BtnSeek2.setName("BtnSeek2"); // NOI18N
        BtnSeek2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek2ActionPerformed(evt);
            }
        });
        BtnSeek2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek2KeyPressed(evt);
            }
        });
        panelisi4.add(BtnSeek2);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('2');
        BtnCari1.setToolTipText("Alt+2");
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
        panelisi4.add(BtnCari1);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("truncate table temporary2");
            for(int r=0;r<tabMode.getRowCount();r++){  
                    Sequel.menyimpan("temporary2","'0','"+
                                    tabMode.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                    tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,3).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,4).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,5).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,6).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,7).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,8).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,9).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,10).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,11).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,12).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,13).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,14).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,15).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,16).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,17).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,18).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,19).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,20).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,21).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,22).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,23).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,24).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,25).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,26).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,27).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,28).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,29).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,30).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,31).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,32).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,33).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,34).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,35).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,36).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,37).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,38).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,39).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,40).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,41).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,42).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,43).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,44).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,45).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,46).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,47).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,48).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,49).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,50).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,51).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,52).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,53).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,54).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,55).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,56).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,57).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,58).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,59).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,60).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,61).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,62).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,63).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,64).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,65).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,67).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,68).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,69).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,70).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,71).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,72).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,73).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,74).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,75).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,76).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,77).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,78).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,79).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,80).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,81).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,82).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,83).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,84).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,85).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,86).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,87).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,88).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,89).toString().replaceAll("'","`")+"','"+
                                    tabMode.getValueAt(r,90).toString().replaceAll("'","`")+"','','','','','','','','','',''","data");
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptReklasifikasiRalan.jasper","report","::[ Reklasifikasi Ranap ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            //Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnKeluar,TKd);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbBangsalMouseClicked

    private void tbBangsalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbBangsalKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbBangsalKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
        
        tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            Valid.pindah(evt, TKd, BtnPrint);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void kdpenjabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenjabKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText());
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?", nmpenjab,kdpenjab.getText());
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnSeek2ActionPerformed(null);
        }
    }//GEN-LAST:event_kdpenjabKeyPressed

    private void BtnSeek2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek2ActionPerformed
        penjab.isCek();
        penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penjab.setLocationRelativeTo(internalFrame1);
        penjab.setAlwaysOnTop(false);
        penjab.setVisible(true);
    }//GEN-LAST:event_BtnSeek2ActionPerformed

    private void BtnSeek2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek2KeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_BtnSeek2KeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        kdpenjab.setText("");
        nmpenjab.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, kdpenjab, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingActionPerformed
        if(TKd.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
        }else{
            DlgBilingRanap billing=new DlgBilingRanap(null,false);
            billing.TNoRw.setText(tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString());            
            billing.isCek();
            billing.isRawat();
            billing.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            billing.setLocationRelativeTo(internalFrame1);
            billing.setVisible(true); 
        }
    }//GEN-LAST:event_MnBillingActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ReklasifikasiRanap dialog = new ReklasifikasiRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSeek2;
    private javax.swing.JLabel LCount;
    private javax.swing.JMenuItem MnBilling;
    private widget.ScrollPane Scroll;
    private widget.TextBox TKd;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpenjab;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.TextBox nmpenjab;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelisi4;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
        Valid.tabelKosong(tabMode);
        try{      
            ps= koneksi.prepareStatement(
                "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,kamar_inap.tgl_keluar,penjab.png_jawab, "+
                "kamar_inap.tgl_masuk,kamar_inap.jam_masuk,kamar_inap.tgl_keluar,kamar_inap.jam_keluar,"+
                "kamar_inap.stts_pulang,kamar_inap.lama,pasien.jk,pasien.tgl_lahir,kamar.kelas "+
                "from kamar_inap inner join reg_periksa "+
                "inner join pasien inner join penjab inner join kamar inner join nota_inap "+
                "on kamar_inap.no_rawat=reg_periksa.no_rawat and reg_periksa.kd_pj=penjab.kd_pj "+
                "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and reg_periksa.no_rawat=nota_inap.no_rawat "+
                "and kamar_inap.kd_kamar=kamar.kd_kamar "+
                "where kamar_inap.tgl_keluar between ? and ? and reg_periksa.kd_pj like ? "+
                "order by kamar_inap.tgl_keluar,kamar_inap.jam_keluar");
            try {
                ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                ps.setString(3,"%"+kdpenjab.getText()+"%");
                rs=ps.executeQuery();
                all=0;ttlLaborat=0;ttlRadiologi=0;ttlOperasi=0;ttlObat=0;
                ttlRanap_Dokter=0;ttlRanap_Paramedis=0;ttlRalan_Dokter=0;
                ttlRalan_Paramedis=0;ttlTambahan=0;ttlPotongan=0;ttlKamar=0;
                ttlRegistrasi=0;ttlHarian=0;ttlRetur_Obat=0;ttlResep_Pulang=0;ttlRalan_Dokter_Paramedis=0;
                ttlRanap_Dokter_Paramedis=0;ttlService=0;ttltarifincabg=0;ttluntungrugiinacbg=0;
                ttlkebidanan=0;
                while(rs.next()){
                    if(!rs.getString("stts_pulang").equals("-")){
                    if(!rs.getString("stts_pulang").equals("Pindah Kamar")){
                        du="";ds1="";ds2="";ds3="";ds4="";ds5="";ds6="";
                        ds7="";ds8="";ds9="";ds10="";ds11="";ds12="";ds13="";ds14="";ds15="";
                        ds16="";ds17="";ds18="";ds19="";ds20="";ds21="";ds22="";ds23="";
                        ds24="";ds25="";ds26="";ds27="";ds28="";ds29="";

                        pspenyakit=koneksi.prepareStatement(
                                "select kd_penyakit,prioritas from diagnosa_pasien where "+
                                "no_rawat=? and status='Ranap' order by prioritas");
                        try {
                            pspenyakit.setString(1,rs.getString("no_rawat"));
                            rs2=pspenyakit.executeQuery();
                            while(rs2.next()){
                                if(rs2.getInt("prioritas")==1){
                                    du=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==2){
                                    ds1=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==3){
                                    ds2=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==4){
                                    ds3=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==5){
                                    ds4=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==6){
                                    ds5=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==7){
                                    ds6=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==8){
                                    ds7=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==9){
                                    ds8=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==10){
                                    ds9=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==11){
                                    ds10=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==12){
                                    ds11=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==13){
                                    ds12=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==14){
                                    ds13=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==15){
                                    ds14=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==16){
                                    ds15=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==17){
                                    ds16=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==18){
                                    ds17=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==19){
                                    ds18=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==20){
                                    ds19=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==21){
                                    ds20=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==22){
                                    ds21=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==23){
                                    ds22=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==24){
                                    ds23=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==25){
                                    ds24=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==26){
                                    ds25=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==27){
                                    ds26=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==28){
                                    ds27=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==29){
                                    ds28=rs2.getString("kd_penyakit");
                                }
                                if(rs2.getInt("prioritas")==30){
                                    ds29=rs2.getString("kd_penyakit");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif Penyakit : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(pspenyakit!=null){
                                pspenyakit.close();
                            }
                        }

                        p1="";p2="";p3="";p4="";p5="";p6="";p7="";p8="";p9="";
                        p10="";p11="";p12="";p13="";p14="";p15="";p16="";p17="";
                        p18="";p19="";p20="";p21="";p22="";p23="";p24="";p25="";
                        p26="";p27="";p28="";p29="";p30="";

                        psprosedur=koneksi.prepareStatement(
                                "select kode,prioritas from prosedur_pasien where "+
                                "no_rawat=? and status='Ranap' order by prioritas");
                        try {
                            psprosedur.setString(1,rs.getString("no_rawat"));
                            rs2=psprosedur.executeQuery();
                            while(rs2.next()){
                                if(rs2.getInt("prioritas")==1){
                                    p1=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==2){
                                    p2=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==3){
                                    p3=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==4){
                                    p4=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==5){
                                    p5=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==6){
                                    p6=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==7){
                                    p7=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==8){
                                    p8=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==9){
                                    p9=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==10){
                                    p10=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==11){
                                    p11=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==12){
                                    p12=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==13){
                                    p13=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==14){
                                    p14=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==15){
                                    p15=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==16){
                                    p16=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==17){
                                    p17=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==18){
                                    p18=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==19){
                                    p19=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==20){
                                    p20=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==21){
                                    p21=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==22){
                                    p22=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==23){
                                    p23=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==24){
                                    p24=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==25){
                                    p25=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==26){
                                    p26=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==27){
                                    p27=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==28){
                                    p28=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==29){
                                    p29=rs2.getString("kode");
                                }
                                if(rs2.getInt("prioritas")==30){
                                    p30=rs2.getString("kode");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif Penyakit : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(psprosedur!=null){
                                psprosedur.close();
                            }
                        }
                        Laborat=0;   
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Laborat");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlLaborat=ttlLaborat+rs2.getDouble(1);
                                Laborat=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Radiologi=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Radiologi");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRadiologi=ttlRadiologi+rs2.getDouble(1);
                                Radiologi=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Operasi=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Operasi");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlOperasi=ttlOperasi+rs2.getDouble(1);
                                Operasi=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Obat=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Obat");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlObat=ttlObat+rs2.getDouble(1);
                                Obat=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Ranap_Dokter=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ranap Dokter");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRanap_Dokter=ttlRanap_Dokter+rs2.getDouble(1);
                                Ranap_Dokter=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }                        

                        Ranap_Dokter_Paramedis=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ranap Dokter Paramedis");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRanap_Dokter_Paramedis=ttlRanap_Dokter_Paramedis+rs2.getDouble(1);
                                Ranap_Dokter_Paramedis=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Ranap_Paramedis=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ranap Paramedis");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRanap_Paramedis=ttlRanap_Paramedis+rs2.getDouble(1);
                                Ranap_Paramedis=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Ralan_Dokter=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ralan Dokter");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRalan_Dokter=ttlRalan_Dokter+rs2.getDouble(1);
                                Ralan_Dokter=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Ralan_Dokter_Paramedis=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ralan Dokter Paramedis");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRalan_Dokter_Paramedis=ttlRalan_Dokter_Paramedis+rs2.getDouble(1);
                                Ralan_Dokter_Paramedis=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Ralan_Paramedis=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Ralan Paramedis");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRalan_Paramedis=ttlRalan_Paramedis+rs2.getDouble(1);
                                Ralan_Paramedis=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Tambahan=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Tambahan");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlTambahan=ttlTambahan+rs2.getDouble(1);
                                Tambahan=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Potongan=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Potongan");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlPotongan=ttlPotongan+rs2.getDouble(1);
                                Potongan=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Kamar=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Kamar");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlKamar=ttlKamar+rs2.getDouble(1);
                                Kamar=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Registrasi=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Registrasi");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRegistrasi=ttlRegistrasi+rs2.getDouble(1);
                                Registrasi=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Harian=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Harian");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlHarian=ttlHarian+rs2.getDouble(1);
                                Harian=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Retur_Obat=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Retur Obat");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlRetur_Obat=ttlRetur_Obat+rs2.getDouble(1);
                                Retur_Obat=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Resep_Pulang=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Resep Pulang");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlResep_Pulang=ttlResep_Pulang+rs2.getDouble(1);
                                Resep_Pulang=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Service=0;
                        ps2=koneksi.prepareStatement(sqlps2);
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,"Service");
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ttlService=ttlService+rs2.getDouble(1);
                                Service=rs2.getDouble(1);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif 2: "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        all=all+Laborat+Radiologi+Operasi+Obat+Ranap_Dokter+Ranap_Dokter_Paramedis+Ranap_Paramedis+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis+Tambahan+Potongan+Kamar+Registrasi+Harian+Retur_Obat+Resep_Pulang+Service;
                        operasi2=0;kebidanan=0;
                        operasi2=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+" +
                                "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayainstrumen+" +
                                "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+" +
                                "operasi.biayaasisten_anestesi+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+" +
                                "operasi.biayaperawat_luar+operasi.biayaalat+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum+" +
                                "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+"+
                                "operasi.biaya_omloop3+operasi.biayasarpras) from operasi where kategori<>'-' and no_rawat=?",rs.getString("no_rawat"));
                        kebidanan=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+" +
                                "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayainstrumen+" +
                                "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+" +
                                "operasi.biayaasisten_anestesi+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+" +
                                "operasi.biayaperawat_luar+operasi.biayaalat+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum+" +
                                "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+"+
                                "operasi.biaya_omloop3+operasi.biayasarpras) from operasi where kategori='-' and no_rawat=?",rs.getString("no_rawat"));
                        tarifincabg=0;untungrugiinacbg=0;
                        ttlkebidanan=ttlkebidanan+kebidanan;
                        tarifincabg=Sequel.cariIsiAngka("select inacbg_grouping_stage1.tarif from inacbg_grouping_stage1 inner join bridging_sep on inacbg_grouping_stage1.no_sep=bridging_sep.no_sep where bridging_sep.no_rawat=?",rs.getString("no_rawat"))+
                                    Sequel.cariIsiAngka("select inacbg_grouping_stage12.tarif from inacbg_grouping_stage12 inner join inacbg_klaim_baru2 on inacbg_grouping_stage12.no_sep=inacbg_klaim_baru2.no_sep where inacbg_klaim_baru2.no_rawat=?",rs.getString("no_rawat"));
                        if(tarifincabg>0){
                            untungrugiinacbg=tarifincabg-(Laborat+Radiologi+Operasi+Obat+Ranap_Dokter+Ranap_Dokter_Paramedis+Ranap_Paramedis+Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis+Tambahan+Potongan+Kamar+Registrasi+Harian+Retur_Obat+Resep_Pulang+Service);
                        }else {
                            untungrugiinacbg=0;
                        }
                        ttltarifincabg=ttltarifincabg+tarifincabg;
                        ttluntungrugiinacbg=ttluntungrugiinacbg+untungrugiinacbg;   
                        tabMode.addRow(new Object[]{
                            rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("png_jawab"),
                            rs.getString("tgl_masuk")+" "+rs.getString("jam_masuk"),rs.getString("tgl_keluar")+" "+rs.getString("jam_keluar"),
                            rs.getString("lama"),rs.getString("jk"),rs.getString("tgl_lahir"),"Ranap",rs.getString("kelas"),
                            rs.getString("stts_pulang"),du,ds1,ds2,ds3,ds4,ds5,ds6,ds7,ds8,ds9,ds10,ds11,ds12,ds13,ds14,ds15,ds16,
                            ds17,ds18,ds19,ds20,ds21,ds22,ds23,ds24,ds25,ds26,ds27,ds28,ds29,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,
                            p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23,p24,p25,p26,p27,p28,p29,p30,
                            Sequel.cariIsi("select code_cbg from inacbg_grouping_stage1 inner join bridging_sep on bridging_sep.no_sep=inacbg_grouping_stage1.no_sep where bridging_sep.no_rawat=?",rs.getString("no_rawat")),
                            Valid.SetAngka(Laborat+Radiologi+Operasi+Obat+Ranap_Dokter+Ranap_Dokter_Paramedis+Ranap_Paramedis+
                            Ralan_Dokter+Ralan_Dokter_Paramedis+Ralan_Paramedis+Tambahan+Potongan+Kamar+Registrasi+Harian+Retur_Obat+Resep_Pulang+Service),
                            Valid.SetAngka(kebidanan),Valid.SetAngka(Operasi-kebidanan),Valid.SetAngka(Ranap_Dokter+Ralan_Dokter),
                            Valid.SetAngka(Ranap_Dokter_Paramedis+Ralan_Dokter_Paramedis),Valid.SetAngka(Ranap_Paramedis+Ralan_Paramedis),
                            Valid.SetAngka(Tambahan+Registrasi+Service),0,Valid.SetAngka(Laborat),0,Valid.SetAngka(Radiologi),0,
                            Valid.SetAngka(Kamar+Harian),0,Valid.SetAngka(Obat+Retur_Obat+Resep_Pulang),0,0,0,Valid.SetAngka(Potongan),
                            Valid.SetAngka(tarifincabg),Valid.SetAngka(untungrugiinacbg)
                        });                        
                    }}                
                } 
                if(all>0){
                    tabMode.addRow(new Object[]{
                        "Total",":","","","","","","","","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","","","","","","","","","","","",
                        "","","","","","","","","","","","","","","","","","","","","","","","",
                        "","",Valid.SetAngka(all),Valid.SetAngka(ttlkebidanan),Valid.SetAngka(ttlOperasi-ttlkebidanan),
                        Valid.SetAngka(ttlRanap_Dokter+ttlRalan_Dokter),Valid.SetAngka(ttlRanap_Dokter_Paramedis+ttlRalan_Dokter_Paramedis),
                        Valid.SetAngka(ttlRanap_Paramedis+ttlRalan_Paramedis),Valid.SetAngka(ttlTambahan+ttlRegistrasi+ttlService),
                        "",Valid.SetAngka(ttlLaborat),"",Valid.SetAngka(ttlRadiologi),"",Valid.SetAngka(ttlKamar+ttlHarian),"",
                        Valid.SetAngka(ttlObat+ttlRetur_Obat+ttlResep_Pulang),"","","",Valid.SetAngka(ttlPotongan),
                        Valid.SetAngka(ttltarifincabg),Valid.SetAngka(ttluntungrugiinacbg)
                    });    
                }
                LCount.setText(Valid.SetAngka(all));
            } catch (Exception e) {
                System.out.println("Notif 1 : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }            
            this.setCursor(Cursor.getDefaultCursor());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void getData() {
        int row=tbBangsal.getSelectedRow();
        if(row!= -1){
            TKd.setText(tabMode.getValueAt(row,1).toString());
        }
    }

}

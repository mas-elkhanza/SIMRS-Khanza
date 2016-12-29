package laporan;
import keuangan.*;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariPenyakit;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class DlgFrekuensiPenyakitRanap extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps,ps2,ps3,ps4;
    private ResultSet rs,rs2,rs3,rs4;
    private String diagnosa="";
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgFrekuensiPenyakitRanap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{"Kode Penyakit","Nama Penyakit","Diagnosa Lain","Jumlah"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, java.lang.Integer.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int m = 0; m < 4; m++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(m);
            if(m==0){
                column.setPreferredWidth(100);
            }else if(m==1){
                column.setPreferredWidth(300);
            }else if(m==2){
                column.setPreferredWidth(400);
            }else if(m==3){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        tabMode2=new DefaultTableModel(null,new Object[]{"Kode Penyakit","Nama Penyakit","Diagnosa Lain","Jumlah"}){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, java.lang.Integer.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter2.setModel(tabMode2);

        tbDokter2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int m = 0; m < 4; m++) {
            TableColumn column = tbDokter2.getColumnModel().getColumn(m);
            if(m==0){
                column.setPreferredWidth(100);
            }else if(m==1){
                column.setPreferredWidth(300);
            }else if(m==2){
                column.setPreferredWidth(400);
            }else if(m==3){
                column.setPreferredWidth(100);
            }
        }
        tbDokter2.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kdpenyakit.setDocument(new batasInput((byte)20).getKata(kdpenyakit));
                
        penyakit.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if( penyakit.getTable().getSelectedRow()!= -1){                   
                    kdpenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),0).toString());
                    nmpenyakit.setText(penyakit.getTable().getValueAt(penyakit.getTable().getSelectedRow(),1).toString());
                } 
                kdpenyakit.requestFocus();
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
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgCariPenyakit penyakit=new DlgCariPenyakit(null,false);
    private int i=0;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppGrafikTerbanyakBatang = new javax.swing.JMenuItem();
        ppGrafikTerbanyakPie = new javax.swing.JMenuItem();
        ppGrafikTerkecilBatang = new javax.swing.JMenuItem();
        ppGrafikTerkecilPie = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        kdpenyakit = new widget.TextBox();
        nmpenyakit = new widget.TextBox();
        btnPenyakit = new widget.Button();
        btnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        label9 = new widget.Label();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        internalFrame3 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        tbDokter2 = new widget.Table();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppGrafikTerbanyakBatang.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikTerbanyakBatang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppGrafikTerbanyakBatang.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikTerbanyakBatang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerbanyakBatang.setText("Grafik Batang 10 Penyakit Terbanyak");
        ppGrafikTerbanyakBatang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerbanyakBatang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerbanyakBatang.setIconTextGap(8);
        ppGrafikTerbanyakBatang.setName("ppGrafikTerbanyakBatang"); // NOI18N
        ppGrafikTerbanyakBatang.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikTerbanyakBatang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerbanyakBatangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerbanyakBatang);

        ppGrafikTerbanyakPie.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikTerbanyakPie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppGrafikTerbanyakPie.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikTerbanyakPie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerbanyakPie.setText("Grafik Pie 10 Penyakit Terbanyak");
        ppGrafikTerbanyakPie.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerbanyakPie.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerbanyakPie.setIconTextGap(8);
        ppGrafikTerbanyakPie.setName("ppGrafikTerbanyakPie"); // NOI18N
        ppGrafikTerbanyakPie.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikTerbanyakPie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerbanyakPieActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerbanyakPie);

        ppGrafikTerkecilBatang.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikTerkecilBatang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppGrafikTerkecilBatang.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikTerkecilBatang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerkecilBatang.setText("Grafik Batang 10 Penyakit Tersedikit");
        ppGrafikTerkecilBatang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerkecilBatang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerkecilBatang.setIconTextGap(8);
        ppGrafikTerkecilBatang.setName("ppGrafikTerkecilBatang"); // NOI18N
        ppGrafikTerkecilBatang.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikTerkecilBatang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerkecilBatangActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerkecilBatang);

        ppGrafikTerkecilPie.setBackground(new java.awt.Color(242, 242, 242));
        ppGrafikTerkecilPie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        ppGrafikTerkecilPie.setForeground(new java.awt.Color(102, 51, 0));
        ppGrafikTerkecilPie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikTerkecilPie.setText("Grafik Pie 10 Penyakit Tersedikit");
        ppGrafikTerkecilPie.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikTerkecilPie.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikTerkecilPie.setIconTextGap(8);
        ppGrafikTerkecilPie.setName("ppGrafikTerkecilPie"); // NOI18N
        ppGrafikTerkecilPie.setPreferredSize(new java.awt.Dimension(300, 25));
        ppGrafikTerkecilPie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikTerkecilPieActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikTerkecilPie);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Frekuensi Penyakit Di Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelisi4.add(label11);

        Tgl1.setEditable(false);
        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setEditable(false);
        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi4.add(Tgl2);

        label17.setText("Penyakit :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi4.add(label17);

        kdpenyakit.setName("kdpenyakit"); // NOI18N
        kdpenyakit.setPreferredSize(new java.awt.Dimension(70, 23));
        kdpenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpenyakitKeyPressed(evt);
            }
        });
        panelisi4.add(kdpenyakit);

        nmpenyakit.setEditable(false);
        nmpenyakit.setName("nmpenyakit"); // NOI18N
        nmpenyakit.setPreferredSize(new java.awt.Dimension(203, 23));
        panelisi4.add(nmpenyakit);

        btnPenyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPenyakit.setMnemonic('3');
        btnPenyakit.setToolTipText("Alt+3");
        btnPenyakit.setName("btnPenyakit"); // NOI18N
        btnPenyakit.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPenyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenyakitActionPerformed(evt);
            }
        });
        btnPenyakit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPenyakitKeyPressed(evt);
            }
        });
        panelisi4.add(btnPenyakit);

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        btnCari.setMnemonic('2');
        btnCari.setToolTipText("Alt+2");
        btnCari.setName("btnCari"); // NOI18N
        btnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });
        btnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnCariKeyPressed(evt);
            }
        });
        panelisi4.add(btnCari);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        label9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(415, 30));
        panelisi1.add(label9);

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

        TabRawat.setBackground(new java.awt.Color(250, 255, 245));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)));
        TabRawat.setForeground(new java.awt.Color(50, 70, 40));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setAutoCreateRowSorter(true);
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
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame2.add(scrollPane1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab(".: Berdasar Tanggal Masuk  ", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane2.setComponentPopupMenu(jPopupMenu1);
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbDokter2.setAutoCreateRowSorter(true);
        tbDokter2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter2.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDokter2.setComponentPopupMenu(jPopupMenu1);
        tbDokter2.setName("tbDokter2"); // NOI18N
        scrollPane2.setViewportView(tbDokter2);

        internalFrame3.add(scrollPane2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab(".: Berdasar Tanggal Keluar  ", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

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
        if(tbDokter.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tbDokter.getRowCount()!=0){
            Sequel.queryu("delete from temporary");
            if(TabRawat.getSelectedIndex()==0){
                int row=tbDokter.getRowCount();
                for(int r=0;r<row;r++){  
                    Sequel.menyimpan("temporary","'0','"+
                                    tbDokter.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                    tbDokter.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tbDokter.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tbDokter.getValueAt(r,3).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Frekuensi Penyakit"); 
                }
            }else if(TabRawat.getSelectedIndex()==1){
                int row=tbDokter2.getRowCount();
                for(int r=0;r<row;r++){  
                    Sequel.menyimpan("temporary","'0','"+
                                    tbDokter2.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                    tbDokter2.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                    tbDokter2.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                    tbDokter2.getValueAt(r,3).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Rekap Frekuensi Penyakit"); 
                }
            }
            Valid.MyReport("rptFrekuensiPenyakitRanap.jrxml","report","[ Frekuensi Penyakit Di Rawat Inap ]",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14 from temporary order by no asc");
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,Tgl2,BtnKeluar);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void kdpenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpenyakitKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",nmpenyakit,kdpenyakit.getText());  
           // TCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",nmpenyakit,kdpenyakit.getText());  
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_penyakit from penyakit where kd_penyakit=? ",nmpenyakit,kdpenyakit.getText());  
            BtnAll.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPenyakitActionPerformed(null);
        }
    }//GEN-LAST:event_kdpenyakitKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        //TCari.setText("");
        kdpenyakit.setText("");
        nmpenyakit.setText("");
        if(TabRawat.getSelectedIndex()==0){
            prosesCari();
        }else if(TabRawat.getSelectedIndex()==1){
            prosesCari2();
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void ppGrafikTerbanyakBatangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerbanyakBatangActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(8,3).toString())>=Integer.parseInt(tbDokter.getValueAt(9,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,3).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(9,3).toString()),tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));


                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,3).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);          

            }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);           

            }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);            

            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if((tbDokter2.getRowCount()>9)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(8,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(8,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(9,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(5,3).toString()),tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(6,3).toString()),tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(7,3).toString()),tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(8,3).toString()),tbDokter2.getValueAt(8,0).toString()+", "+tbDokter2.getValueAt(8,1).toString()+", "+tbDokter2.getValueAt(8,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(9,3).toString()),tbDokter2.getValueAt(9,0).toString()+", "+tbDokter2.getValueAt(9,1).toString()+", "+tbDokter2.getValueAt(9,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));


                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter2.getRowCount()>8)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(8,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(5,3).toString()),tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(6,3).toString()),tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(7,3).toString()),tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(8,3).toString()),tbDokter2.getValueAt(8,0).toString()+", "+tbDokter2.getValueAt(8,1).toString()+", "+tbDokter2.getValueAt(8,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter2.getRowCount()>7)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(5,3).toString()),tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(6,3).toString()),tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(7,3).toString()),tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter2.getRowCount()>6)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(5,3).toString()),tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(6,3).toString()),tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter2.getRowCount()>5)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(5,3).toString()),tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter2.getRowCount()>4)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter2.getRowCount()>3)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);          

            }else if((tbDokter2.getRowCount()>2)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);           

            }else if((tbDokter2.getRowCount()>1)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))){
                DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);            

            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
            }
        }        
        
    }//GEN-LAST:event_ppGrafikTerbanyakBatangActionPerformed

private void btnPenyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenyakitActionPerformed
        penyakit.isCek();
        penyakit.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        penyakit.setLocationRelativeTo(internalFrame1);
        penyakit.setVisible(true);
}//GEN-LAST:event_btnPenyakitActionPerformed

private void btnPenyakitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPenyakitKeyPressed
   //Valid.pindah(evt,DTPCari2,TCari);
}//GEN-LAST:event_btnPenyakitKeyPressed

private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            prosesCari();
        }else if(TabRawat.getSelectedIndex()==1){
            prosesCari2();
        }
}//GEN-LAST:event_btnCariActionPerformed

private void btnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            btnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, kdpenyakit, BtnAll);
        }
}//GEN-LAST:event_btnCariKeyPressed

private void ppGrafikTerbanyakPieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerbanyakPieActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(8,3).toString())>=Integer.parseInt(tbDokter.getValueAt(9,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Integer.parseInt(tbDokter.getValueAt(8,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,3).toString(),Integer.parseInt(tbDokter.getValueAt(9,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);
               }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Integer.parseInt(tbDokter.getValueAt(8,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);

               }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);

               }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);

               }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);

               }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);

               }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);       

               }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);          

               }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);        

               }else{
                   JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
               }
        }else if(TabRawat.getSelectedIndex()==1){
            if((tbDokter2.getRowCount()>9)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(8,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(8,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(9,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Integer.parseInt(tbDokter2.getValueAt(5,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Integer.parseInt(tbDokter2.getValueAt(6,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Integer.parseInt(tbDokter2.getValueAt(7,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(8,0).toString()+", "+tbDokter2.getValueAt(8,1).toString()+", "+tbDokter2.getValueAt(8,3).toString(),Integer.parseInt(tbDokter2.getValueAt(8,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(9,0).toString()+", "+tbDokter2.getValueAt(9,1).toString()+", "+tbDokter2.getValueAt(9,3).toString(),Integer.parseInt(tbDokter2.getValueAt(9,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);
               }else if((tbDokter2.getRowCount()>8)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(7,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(8,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Integer.parseInt(tbDokter2.getValueAt(5,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Integer.parseInt(tbDokter2.getValueAt(6,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Integer.parseInt(tbDokter2.getValueAt(7,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(8,0).toString()+", "+tbDokter2.getValueAt(8,1).toString()+", "+tbDokter2.getValueAt(8,3).toString(),Integer.parseInt(tbDokter2.getValueAt(8,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);

               }else if((tbDokter2.getRowCount()>7)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Integer.parseInt(tbDokter2.getValueAt(5,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Integer.parseInt(tbDokter2.getValueAt(6,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Integer.parseInt(tbDokter2.getValueAt(7,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);

               }else if((tbDokter2.getRowCount()>6)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Integer.parseInt(tbDokter2.getValueAt(5,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Integer.parseInt(tbDokter2.getValueAt(6,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);

               }else if((tbDokter2.getRowCount()>5)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Integer.parseInt(tbDokter2.getValueAt(5,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);

               }else if((tbDokter2.getRowCount()>4)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);

               }else if((tbDokter2.getRowCount()>3)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);       

               }else if((tbDokter2.getRowCount()>2)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                       && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);          

               }else if((tbDokter2.getRowCount()>1)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())>=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))){
                   DefaultPieDataset dpd = new DefaultPieDataset();
                   dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                   dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));

                   JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                   ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                   cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                   cf.setLocationRelativeTo(internalFrame1);
                   cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                   cf.setVisible(true);        

               }else{
                   JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
               }
        }
     
                
}//GEN-LAST:event_ppGrafikTerbanyakPieActionPerformed

private void ppGrafikTerkecilBatangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerkecilBatangActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(8,3).toString())<=Integer.parseInt(tbDokter.getValueAt(9,3).toString()))){
                    /*DefaultPieDataset dpd = new DefaultPieDataset();
                    dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                    dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                    dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                    dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                    dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
                    dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
                    dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
                    dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));
                    dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString(),Integer.parseInt(tbDokter.getValueAt(8,3).toString()));
                    dpd.setValue(tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString(),Integer.parseInt(tbDokter.getValueAt(9,3).toString()));

                    JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                    ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                    cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setVisible(true);*/
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,3).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(9,3).toString()),tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));


                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(8,3).toString()),tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(7,3).toString()),tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(6,3).toString()),tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(5,3).toString()),tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(4,3).toString()),tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(3,3).toString()),tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);          

                }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(2,3).toString()),tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);           

                }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(0,3).toString()),tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter.getValueAt(1,3).toString()),tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);            

                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
                }
        }else if(TabRawat.getSelectedIndex()==1){
            if((tbDokter2.getRowCount()>9)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(8,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(8,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(9,3).toString()))){
                    /*DefaultPieDataset dpd = new DefaultPieDataset();
                    dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                    dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                    dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                    dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                    dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));
                    dpd.setValue(tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString(),Integer.parseInt(tbDokter2.getValueAt(5,3).toString()));
                    dpd.setValue(tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString(),Integer.parseInt(tbDokter2.getValueAt(6,3).toString()));
                    dpd.setValue(tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString(),Integer.parseInt(tbDokter2.getValueAt(7,3).toString()));
                    dpd.setValue(tbDokter2.getValueAt(8,0).toString()+", "+tbDokter2.getValueAt(8,1).toString(),Integer.parseInt(tbDokter2.getValueAt(8,3).toString()));
                    dpd.setValue(tbDokter2.getValueAt(9,0).toString()+", "+tbDokter2.getValueAt(9,1).toString(),Integer.parseInt(tbDokter2.getValueAt(9,3).toString()));

                    JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                    ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                    cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setVisible(true);*/
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(5,3).toString()),tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(6,3).toString()),tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(7,3).toString()),tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(8,3).toString()),tbDokter2.getValueAt(8,0).toString()+", "+tbDokter2.getValueAt(8,1).toString()+", "+tbDokter2.getValueAt(8,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(9,3).toString()),tbDokter2.getValueAt(9,0).toString()+", "+tbDokter2.getValueAt(9,1).toString()+", "+tbDokter2.getValueAt(9,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));


                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter2.getRowCount()>8)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(8,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(5,3).toString()),tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(6,3).toString()),tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(7,3).toString()),tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(8,3).toString()),tbDokter2.getValueAt(8,0).toString()+", "+tbDokter2.getValueAt(8,1).toString()+", "+tbDokter2.getValueAt(8,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter2.getRowCount()>7)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(5,3).toString()),tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(6,3).toString()),tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(7,3).toString()),tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter2.getRowCount()>6)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(5,3).toString()),tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(6,3).toString()),tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter2.getRowCount()>5)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(5,3).toString()),tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter2.getRowCount()>4)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(4,3).toString()),tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);

                }else if((tbDokter2.getRowCount()>3)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(3,3).toString()),tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);          

                }else if((tbDokter2.getRowCount()>2)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                        && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(2,3).toString()),tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);           

                }else if((tbDokter2.getRowCount()>1)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))){
                    DefaultCategoryDataset dcd = new DefaultCategoryDataset();
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(0,3).toString()),tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    dcd.setValue(Integer.parseInt(tbDokter2.getValueAt(1,3).toString()),tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""));

                    JFreeChart freeChart = ChartFactory.createBarChart("10 Besar Penyakit Di Rawat Inap","Periode","Jumlah Penyakit", dcd, PlotOrientation.VERTICAL,true, true,true); //String arg0,String arg1,String arg2,Category Datasheet,Plot Orientation,boolean arg4,boolean arg5,boolean arg6
                    ChartFrame cf = new ChartFrame("Frafik 10 Besar Penyakit Di Rawat Inap",freeChart);

                    cf.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
                    cf.setLocationRelativeTo(internalFrame1);
                    cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                    cf.setVisible(true);            

                }else{
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
                }
        }
                     
}//GEN-LAST:event_ppGrafikTerkecilBatangActionPerformed

private void ppGrafikTerkecilPieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikTerkecilPieActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if((tbDokter.getRowCount()>9)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(8,3).toString())<=Integer.parseInt(tbDokter.getValueAt(9,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
                dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
                dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
                dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));
                dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Integer.parseInt(tbDokter.getValueAt(8,3).toString()));
                dpd.setValue(tbDokter.getValueAt(9,0).toString()+", "+tbDokter.getValueAt(9,1).toString()+", "+tbDokter.getValueAt(9,3).toString(),Integer.parseInt(tbDokter.getValueAt(9,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);
            }else if((tbDokter.getRowCount()>8)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter.getValueAt(8,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
                dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
                dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
                dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));
                dpd.setValue(tbDokter.getValueAt(8,0).toString()+", "+tbDokter.getValueAt(8,1).toString()+", "+tbDokter.getValueAt(8,3).toString(),Integer.parseInt(tbDokter.getValueAt(8,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter.getRowCount()>7)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter.getValueAt(7,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
                dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
                dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));
                dpd.setValue(tbDokter.getValueAt(7,0).toString()+", "+tbDokter.getValueAt(7,1).toString()+", "+tbDokter.getValueAt(7,3).toString(),Integer.parseInt(tbDokter.getValueAt(7,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter.getRowCount()>6)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter.getValueAt(6,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
                dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));
                dpd.setValue(tbDokter.getValueAt(6,0).toString()+", "+tbDokter.getValueAt(6,1).toString()+", "+tbDokter.getValueAt(6,3).toString(),Integer.parseInt(tbDokter.getValueAt(6,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter.getRowCount()>5)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter.getValueAt(5,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));
                dpd.setValue(tbDokter.getValueAt(5,0).toString()+", "+tbDokter.getValueAt(5,1).toString()+", "+tbDokter.getValueAt(5,3).toString(),Integer.parseInt(tbDokter.getValueAt(5,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter.getRowCount()>4)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter.getValueAt(4,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter.getValueAt(4,0).toString()+", "+tbDokter.getValueAt(4,1).toString()+", "+tbDokter.getValueAt(4,3).toString(),Integer.parseInt(tbDokter.getValueAt(4,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter.getRowCount()>3)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter.getValueAt(3,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter.getValueAt(3,0).toString()+", "+tbDokter.getValueAt(3,1).toString()+", "+tbDokter.getValueAt(3,3).toString(),Integer.parseInt(tbDokter.getValueAt(3,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);      

            }else if((tbDokter.getRowCount()>2)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter.getValueAt(2,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter.getValueAt(2,0).toString()+", "+tbDokter.getValueAt(2,1).toString()+", "+tbDokter.getValueAt(2,3).toString(),Integer.parseInt(tbDokter.getValueAt(2,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);          

            }else if((tbDokter.getRowCount()>1)&&(Integer.parseInt(tbDokter.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter.getValueAt(1,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter.getValueAt(0,0).toString()+", "+tbDokter.getValueAt(0,1).toString()+", "+tbDokter.getValueAt(0,3).toString(),Integer.parseInt(tbDokter.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter.getValueAt(1,0).toString()+", "+tbDokter.getValueAt(1,1).toString()+", "+tbDokter.getValueAt(1,3).toString(),Integer.parseInt(tbDokter.getValueAt(1,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);         

            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
            }
        }else if(TabRawat.getSelectedIndex()==1){
            if((tbDokter2.getRowCount()>9)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(8,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(8,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(9,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Integer.parseInt(tbDokter2.getValueAt(5,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Integer.parseInt(tbDokter2.getValueAt(6,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Integer.parseInt(tbDokter2.getValueAt(7,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(8,0).toString()+", "+tbDokter2.getValueAt(8,1).toString()+", "+tbDokter2.getValueAt(8,3).toString(),Integer.parseInt(tbDokter2.getValueAt(8,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(9,0).toString()+", "+tbDokter2.getValueAt(9,1).toString()+", "+tbDokter2.getValueAt(9,3).toString(),Integer.parseInt(tbDokter2.getValueAt(9,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);
            }else if((tbDokter2.getRowCount()>8)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(7,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(8,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Integer.parseInt(tbDokter2.getValueAt(5,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Integer.parseInt(tbDokter2.getValueAt(6,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Integer.parseInt(tbDokter2.getValueAt(7,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(8,0).toString()+", "+tbDokter2.getValueAt(8,1).toString()+", "+tbDokter2.getValueAt(8,3).toString(),Integer.parseInt(tbDokter2.getValueAt(8,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter2.getRowCount()>7)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(6,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(7,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Integer.parseInt(tbDokter2.getValueAt(5,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Integer.parseInt(tbDokter2.getValueAt(6,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(7,0).toString()+", "+tbDokter2.getValueAt(7,1).toString()+", "+tbDokter2.getValueAt(7,3).toString(),Integer.parseInt(tbDokter2.getValueAt(7,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter2.getRowCount()>6)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(5,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(6,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Integer.parseInt(tbDokter2.getValueAt(5,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(6,0).toString()+", "+tbDokter2.getValueAt(6,1).toString()+", "+tbDokter2.getValueAt(6,3).toString(),Integer.parseInt(tbDokter2.getValueAt(6,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter2.getRowCount()>5)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(4,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(5,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(5,0).toString()+", "+tbDokter2.getValueAt(5,1).toString()+", "+tbDokter2.getValueAt(5,3).toString(),Integer.parseInt(tbDokter2.getValueAt(5,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter2.getRowCount()>4)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(3,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(4,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(4,0).toString()+", "+tbDokter2.getValueAt(4,1).toString()+", "+tbDokter2.getValueAt(4,3).toString(),Integer.parseInt(tbDokter2.getValueAt(4,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);

            }else if((tbDokter2.getRowCount()>3)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(2,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(3,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(3,0).toString()+", "+tbDokter2.getValueAt(3,1).toString()+", "+tbDokter2.getValueAt(3,3).toString(),Integer.parseInt(tbDokter2.getValueAt(3,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);      

            }else if((tbDokter2.getRowCount()>2)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))
                    && (Integer.parseInt(tbDokter2.getValueAt(1,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(2,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(2,0).toString()+", "+tbDokter2.getValueAt(2,1).toString()+", "+tbDokter2.getValueAt(2,3).toString(),Integer.parseInt(tbDokter2.getValueAt(2,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);          

            }else if((tbDokter2.getRowCount()>1)&&(Integer.parseInt(tbDokter2.getValueAt(0,3).toString())<=Integer.parseInt(tbDokter2.getValueAt(1,3).toString()))){
                DefaultPieDataset dpd = new DefaultPieDataset();
                dpd.setValue(tbDokter2.getValueAt(0,0).toString()+", "+tbDokter2.getValueAt(0,1).toString()+", "+tbDokter2.getValueAt(0,3).toString(),Integer.parseInt(tbDokter2.getValueAt(0,3).toString()));
                dpd.setValue(tbDokter2.getValueAt(1,0).toString()+", "+tbDokter2.getValueAt(1,1).toString()+", "+tbDokter2.getValueAt(1,3).toString(),Integer.parseInt(tbDokter2.getValueAt(1,3).toString()));

                JFreeChart freeChart = ChartFactory.createPieChart("10 Besar Penyakit Di Rawat Inap Periode "+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(Tgl2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
                ChartFrame cf = new ChartFrame("Grafik 10 Besar Penyakit Di Rawat Inap ",freeChart);
                cf.setSize(internalFrame1.getWidth()-50, internalFrame1.getHeight()-50);
                cf.setLocationRelativeTo(internalFrame1);
                cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
                cf.setVisible(true);         

            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan urutkan secara Descanding/Besar Ke Kecil dengan menklik pada kolom jumlah..!!!!");
            }
        }    
}//GEN-LAST:event_ppGrafikTerkecilPieActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        prosesCari();
    }//GEN-LAST:event_formWindowOpened

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            prosesCari();
        }else if(TabRawat.getSelectedIndex()==1){
            prosesCari2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgFrekuensiPenyakitRanap dialog = new DlgFrekuensiPenyakitRanap(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Kd2;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnCari;
    private widget.Button btnPenyakit;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdpenyakit;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmpenyakit;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppGrafikTerbanyakBatang;
    private javax.swing.JMenuItem ppGrafikTerbanyakPie;
    private javax.swing.JMenuItem ppGrafikTerkecilBatang;
    private javax.swing.JMenuItem ppGrafikTerkecilPie;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbDokter;
    private widget.Table tbDokter2;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
       Valid.tabelKosong(tabMode);      
       try{  
            ps=koneksi.prepareStatement(
                    "select penyakit.kd_penyakit,SUBSTRING(penyakit.nm_penyakit,1,80) as penyakit "+
                    "from penyakit inner join diagnosa_pasien inner join reg_periksa "+
                    "on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat "+
                    "where diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='1' and "+
                    "penyakit.kd_penyakit like ? and reg_periksa.tgl_registrasi between ? and ? "+
                    "group by penyakit.kd_penyakit order by penyakit.kd_penyakit");
            try {
                ps.setString(1,"%"+kdpenyakit.getText()+"%");
                ps.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    i=0;
                    ps2=koneksi.prepareStatement(
                        "select diagnosa_pasien.no_rawat from pasien inner join reg_periksa inner join diagnosa_pasien "+
                        "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=diagnosa_pasien.no_rawat where "+
                        "diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='1' and reg_periksa.tgl_registrasi between ? and ? "+
                        "and diagnosa_pasien.kd_penyakit=? group by diagnosa_pasien.no_rawat");
                    try {
                        ps2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(3,rs.getString("kd_penyakit"));
                        rs2=ps2.executeQuery();
                        Sequel.queryu("delete from temporary_surveilens_penyakit");
                        while(rs2.next()){
                            ps3=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.prioritas<>'1' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.no_rawat=?");    
                            try {
                                ps3.setString(1,rs2.getString(1));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    Sequel.menyimpan("temporary_surveilens_penyakit","?,?",2,new String[]{
                                        rs.getString("kd_penyakit"),rs3.getString("kd_penyakit")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }                            
                        }
                        diagnosa="";
                        rs2.last();
                        if(rs2.getRow()>0){
                            ps4=koneksi.prepareStatement("select temporary_surveilens_penyakit.kd_penyakit2 from temporary_surveilens_penyakit where temporary_surveilens_penyakit.kd_penyakit=? group by temporary_surveilens_penyakit.kd_penyakit2");    
                            try {                                    
                                ps4.setString(1,rs.getString("kd_penyakit"));
                                rs4=ps4.executeQuery();
                                while(rs4.next()){
                                    if(diagnosa.equals("")){
                                        diagnosa=rs4.getString(1);
                                    }else{
                                        diagnosa=diagnosa+", "+rs4.getString(1);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs4!=null){
                                    rs4.close();
                                }
                                if(ps4!=null){
                                    ps4.close();
                                }
                            }                                                        
                        }
                        i=rs2.getRow();
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
                    tabMode.addRow(new Object[]{rs.getString("kd_penyakit"),rs.getString("penyakit"),diagnosa,i});                  
                } 
                label9.setText("      Record : "+tabMode.getRowCount());   
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }         
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }
        
    }
    
    private void prosesCari2() {
       Valid.tabelKosong(tabMode2);      
       try{  
            ps=koneksi.prepareStatement(
                    "select penyakit.kd_penyakit,SUBSTRING(penyakit.nm_penyakit,1,80) as penyakit "+
                    "from penyakit inner join diagnosa_pasien inner join reg_periksa inner join kamar_inap "+
                    "on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit and reg_periksa.no_rawat=diagnosa_pasien.no_rawat "+
                    "and reg_periksa.no_rawat=kamar_inap.no_rawat where diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='1' and "+
                    "penyakit.kd_penyakit like ? and kamar_inap.tgl_keluar between ? and ? "+
                    "group by penyakit.kd_penyakit order by penyakit.kd_penyakit");
            try {
                ps.setString(1,"%"+kdpenyakit.getText()+"%");
                ps.setString(2,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                ps.setString(3,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                rs=ps.executeQuery();
                while(rs.next()){
                    i=0;
                    ps2=koneksi.prepareStatement(
                        "select diagnosa_pasien.no_rawat from pasien inner join reg_periksa inner join diagnosa_pasien inner join kamar_inap "+
                        "on pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=diagnosa_pasien.no_rawat and reg_periksa.no_rawat=kamar_inap.no_rawat where "+
                        "diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='1' and kamar_inap.tgl_keluar between ? and ? "+
                        "and diagnosa_pasien.kd_penyakit=? group by diagnosa_pasien.no_rawat");
                    try {
                        ps2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(3,rs.getString("kd_penyakit"));
                        rs2=ps2.executeQuery();
                        Sequel.queryu("delete from temporary_surveilens_penyakit");
                        while(rs2.next()){
                            ps3=koneksi.prepareStatement("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.prioritas<>'1' and diagnosa_pasien.status='Ranap' and diagnosa_pasien.no_rawat=?");    
                            try {
                                ps3.setString(1,rs2.getString(1));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    Sequel.menyimpan("temporary_surveilens_penyakit","?,?",2,new String[]{
                                        rs.getString("kd_penyakit"),rs3.getString("kd_penyakit")
                                    });
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }                            
                        }
                        diagnosa="";
                        rs2.last();
                        if(rs2.getRow()>0){
                            ps4=koneksi.prepareStatement("select temporary_surveilens_penyakit.kd_penyakit2 from temporary_surveilens_penyakit where temporary_surveilens_penyakit.kd_penyakit=? group by temporary_surveilens_penyakit.kd_penyakit2");    
                            try {                                    
                                ps4.setString(1,rs.getString("kd_penyakit"));
                                rs4=ps4.executeQuery();
                                while(rs4.next()){
                                    if(diagnosa.equals("")){
                                        diagnosa=rs4.getString(1);
                                    }else{
                                        diagnosa=diagnosa+", "+rs4.getString(1);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs4!=null){
                                    rs4.close();
                                }
                                if(ps4!=null){
                                    ps4.close();
                                }
                            }                                                        
                        }
                        i=rs2.getRow();
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
                    tabMode2.addRow(new Object[]{rs.getString("kd_penyakit"),rs.getString("penyakit"),diagnosa,i});                  
                } 
                label9.setText("      Record : "+tabMode2.getRowCount());   
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }         
        }catch(Exception e){
            System.out.println("Catatan  "+e);
        }
        
    }
    
    public void isCek(){
        BtnPrint.setEnabled(var.getpenyakit_ranap());
    }
    
}

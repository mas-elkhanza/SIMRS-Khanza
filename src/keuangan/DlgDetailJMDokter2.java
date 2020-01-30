package keuangan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgPenanggungJawab;

public class DlgDetailJMDokter2 extends javax.swing.JDialog {
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private final Jurnal jur=new Jurnal();
    private final Connection koneksi=koneksiDB.condb();
    private final Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgPenanggungJawab carabayar=new DlgPenanggungJawab(null,false);
    private int i=0,c=0;
    private String pilihancarabayar="",tglkeluar="",namaruangan="",dpjp="";    
    private PreparedStatement psreg,pskamar,pstindakan;
    private ResultSet rsreg,rskamar,rstindakan;
    private double totalsarana=0,totaljm=0,totalbayar=0;   
    private String totalsaranas="",totaljms="",totalbayars="",js="",jm="",tarif="",pilihan="";
    private final DefaultTableModel tabMode;
    private StringBuilder htmlContent;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgDetailJMDokter2(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Rawat","Nomor RM","Nama Pasien","Tgl.Masuk","Tgl.Keluar","DPJP",
                "Penindak","Dokter Anestesi","Dokter Anak","Kode","Kategori",
                "Nama Unit","Ruangan","Nama Tindakan","Jml",
                "J.S.","J.P.","Tarif","Total J.S.",
                "Total J.P.","Total","Tgl.Trans"
             }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDetail.setModel(tabMode);

        tbDetail.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDetail.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 22; i++) {
            TableColumn column = tbDetail.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(170);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(65);
            }else if(i==5){
                column.setPreferredWidth(170);
            }else if(i==6){
                column.setPreferredWidth(170);
            }else if(i==7){
                column.setPreferredWidth(170);
            }else if(i==8){
                column.setPreferredWidth(170);
            }else if(i==9){
                column.setPreferredWidth(40);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(180);
            }else if(i==14){
                column.setPreferredWidth(30);
            }else if(i==15){
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(90);
            }else if(i==17){
                column.setPreferredWidth(90);
            }else if(i==18){
                column.setPreferredWidth(90);
            }else if(i==19){
                column.setPreferredWidth(90);
            }else if(i==20){
                column.setPreferredWidth(90);
            }else if(i==21){
                column.setPreferredWidth(65);
            }
        }
        tbDetail.setDefaultRenderer(Object.class, new WarnaTable());   
        
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
        carabayar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carabayar.getTable().getSelectedRow()!= -1){
                    pilihancarabayar=carabayar.getTable().getValueAt(carabayar.getTable().getSelectedRow(),1).toString();
                }     
                prosesCari();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {carabayar.onCari();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });   
        
        carabayar.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    carabayar.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppTampilkanSeleksi = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelisi4 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelisi1 = new widget.panelisi();
        chkRalan = new widget.CekBox();
        chkRadiologi = new widget.CekBox();
        chkLaborat = new widget.CekBox();
        chkOperasi = new widget.CekBox();
        chkRanap = new widget.CekBox();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tbDetail = new widget.Table();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppTampilkanSeleksi.setBackground(new java.awt.Color(255, 255, 254));
        ppTampilkanSeleksi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppTampilkanSeleksi.setForeground(java.awt.Color.darkGray);
        ppTampilkanSeleksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppTampilkanSeleksi.setText("Tampilkan Per Jenis Bayar");
        ppTampilkanSeleksi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppTampilkanSeleksi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppTampilkanSeleksi.setName("ppTampilkanSeleksi"); // NOI18N
        ppTampilkanSeleksi.setPreferredSize(new java.awt.Dimension(360, 25));
        ppTampilkanSeleksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppTampilkanSeleksiBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppTampilkanSeleksi);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Detail J.M Dokter 2 ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi4.setName("panelisi4"); // NOI18N
        panelisi4.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Pasien Masuk :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(85, 23));
        panelisi4.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(95, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi4.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(30, 23));
        panelisi4.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(95, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi4.add(Tgl2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(90, 23));
        panelisi4.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(350, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TCariKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi4.add(TCari);

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
        panelisi4.add(BtnCari);

        internalFrame1.add(panelisi4, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        chkRalan.setSelected(true);
        chkRalan.setText("Ralan");
        chkRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRalan.setName("chkRalan"); // NOI18N
        chkRalan.setOpaque(false);
        chkRalan.setPreferredSize(new java.awt.Dimension(95, 30));
        chkRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRalanActionPerformed(evt);
            }
        });
        panelisi1.add(chkRalan);

        chkRadiologi.setSelected(true);
        chkRadiologi.setText("Radiologi");
        chkRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRadiologi.setName("chkRadiologi"); // NOI18N
        chkRadiologi.setOpaque(false);
        chkRadiologi.setPreferredSize(new java.awt.Dimension(95, 30));
        chkRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRadiologiActionPerformed(evt);
            }
        });
        panelisi1.add(chkRadiologi);

        chkLaborat.setSelected(true);
        chkLaborat.setText("Laboratorium");
        chkLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkLaborat.setName("chkLaborat"); // NOI18N
        chkLaborat.setOpaque(false);
        chkLaborat.setPreferredSize(new java.awt.Dimension(100, 30));
        chkLaborat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLaboratActionPerformed(evt);
            }
        });
        panelisi1.add(chkLaborat);

        chkOperasi.setSelected(true);
        chkOperasi.setText("Operasi");
        chkOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkOperasi.setName("chkOperasi"); // NOI18N
        chkOperasi.setOpaque(false);
        chkOperasi.setPreferredSize(new java.awt.Dimension(95, 30));
        chkOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOperasiActionPerformed(evt);
            }
        });
        panelisi1.add(chkOperasi);

        chkRanap.setSelected(true);
        chkRanap.setText("Ranap");
        chkRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRanap.setName("chkRanap"); // NOI18N
        chkRanap.setOpaque(false);
        chkRanap.setPreferredSize(new java.awt.Dimension(95, 30));
        chkRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRanapActionPerformed(evt);
            }
        });
        panelisi1.add(chkRanap);

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

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDetail.setToolTipText("");
        tbDetail.setComponentPopupMenu(jPopupMenu1);
        tbDetail.setName("tbDetail"); // NOI18N
        scrollPane1.setViewportView(tbDetail);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

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
        try {            
            File g = new File("file2.css");            
            BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 11px tahoma;height:12px;background: #ffffff;color:#323232;}"+                    
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
            );
            bg.close();
            
            File f;            
            BufferedWriter bw; 
            
            pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Laporan Jasa Medis",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (HTML)","Laporan 3 (WPS)","Laporan 4 (WPS)","Laporan 5 (CSV)","Laporan 6 (CSV)"},"Laporan 1 (HTML)");
            switch (pilihan) {
                case "Laporan 1 (HTML)":
                        htmlContent = new StringBuilder();
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>No.Rawat</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Nomor RM</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>Nama Pasien</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>&nbsp;&nbsp;Tgl.Masuk&nbsp;&nbsp;</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>&nbsp;&nbsp;Tgl.Keluar&nbsp;&nbsp;</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>DPJP</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>Penindak</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Dokter Anestesi</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Dokter Anak</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Kode</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>Kategori</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Nama Unit</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Ruangan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='12%'>Nama Tindakan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Frekuensi (Jumlah)</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Jasa Sarana</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Jasa Pelayanan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Tarif</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total Jasa Sarana</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total Jasa Pelayanan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Tgl.Transaksi</td>"+
                            "</tr>"
                        ); 
                        for(i=0;i<tabMode.getRowCount();i++){  
                            try {
                                js=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,15).toString()));
                            } catch (Exception e) {
                                js="";
                            }
                            try {
                                jm=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,16).toString()));
                            } catch (Exception e) {
                                jm="";
                            }
                            try {
                                tarif=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,17).toString()));
                            } catch (Exception e) {
                                tarif="";
                            }
                            try {
                                totalsaranas=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,18).toString()));
                            } catch (Exception e) {
                                totalsaranas="";
                            }
                            try {
                                totaljms=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,19).toString()));
                            } catch (Exception e) {
                                totaljms="";
                            }
                            try {
                                totalbayars=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,20).toString()));
                            } catch (Exception e) {
                                totalbayars="";
                            }

                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,0).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,4).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,5).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,6).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,7).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,8).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,9).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,10).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,11).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,12).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,13).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,14).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='right'>"+js+"</td>"+
                                    "<td valign='top' align='right'>"+jm+"</td>"+
                                    "<td valign='top' align='right'>"+tarif+"</td>"+
                                    "<td valign='top' align='right'>"+totalsaranas+"</td>"+
                                    "<td valign='top' align='right'>"+totaljms+"</td>"+
                                    "<td valign='top' align='right'>"+totalbayars+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,21).toString().replaceAll("'","`")+"</td>"+
                                "</tr>"
                            ); 
                        }            

                        f = new File("DetailJMDokter1.html");            
                        bw = new BufferedWriter(new FileWriter(f));            
                        bw.write("<html>"+
                                    "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                    "<body>"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DETAIL JM DOKTER PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            htmlContent.toString()+
                                        "</table>"+
                                    "</body>"+                   
                                 "</html>"
                        );

                        bw.close();                         
                        Desktop.getDesktop().browse(f.toURI());
                    break;
                case "Laporan 2 (HTML)":
                        htmlContent = new StringBuilder();
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<tr class='isi'>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>No.Rawat</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Nomor RM</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>Nama Pasien</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>&nbsp;&nbsp;Tgl.Masuk&nbsp;&nbsp;</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>&nbsp;&nbsp;Tgl.Keluar&nbsp;&nbsp;</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>DPJP</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>Penindak</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Dokter Anestesi</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Dokter Anak</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Kode</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>Kategori</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Nama Unit</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Ruangan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='12%'>Nama Tindakan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Frekuensi (Jumlah)</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Jasa Sarana</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Jasa Pelayanan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Tarif</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total Jasa Sarana</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total Jasa Pelayanan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Tgl.Transaksi</td>"+
                            "</tr>"
                        ); 
                        for(i=0;i<tabMode.getRowCount();i++){  
                            try {
                                js=tabMode.getValueAt(i,15).toString();
                            } catch (Exception e) {
                                js="";
                            }
                            try {
                                jm=tabMode.getValueAt(i,16).toString();
                            } catch (Exception e) {
                                jm="";
                            }
                            try {
                                tarif=tabMode.getValueAt(i,17).toString();
                            } catch (Exception e) {
                                tarif="";
                            }
                            try {
                                totalsaranas=tabMode.getValueAt(i,18).toString();
                            } catch (Exception e) {
                                totalsaranas="";
                            }
                            try {
                                totaljms=tabMode.getValueAt(i,19).toString();
                            } catch (Exception e) {
                                totaljms="";
                            }
                            try {
                                totalbayars=tabMode.getValueAt(i,20).toString();
                            } catch (Exception e) {
                                totalbayars="";
                            }

                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,0).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,4).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,5).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,6).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,7).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,8).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,9).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,10).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,11).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,12).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,13).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,14).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='right'>"+js+"</td>"+
                                    "<td valign='top' align='right'>"+jm+"</td>"+
                                    "<td valign='top' align='right'>"+tarif+"</td>"+
                                    "<td valign='top' align='right'>"+totalsaranas+"</td>"+
                                    "<td valign='top' align='right'>"+totaljms+"</td>"+
                                    "<td valign='top' align='right'>"+totalbayars+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,21).toString().replaceAll("'","`")+"</td>"+
                                "</tr>"
                            ); 
                        }            

                        f = new File("DetailJMDokter2.html");            
                        bw = new BufferedWriter(new FileWriter(f));            
                        bw.write("<html>"+
                                    "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                    "<body>"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DETAIL JM DOKTER PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            htmlContent.toString()+
                                        "</table>"+
                                    "</body>"+                   
                                 "</html>"
                        );

                        bw.close();                         
                        Desktop.getDesktop().browse(f.toURI());
                    break;  
                case "Laporan 3 (WPS)":
                        htmlContent = new StringBuilder();
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>No.Rawat</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Nomor RM</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>Nama Pasien</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>&nbsp;&nbsp;Tgl.Masuk&nbsp;&nbsp;</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>&nbsp;&nbsp;Tgl.Keluar&nbsp;&nbsp;</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>DPJP</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>Penindak</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Dokter Anestesi</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Dokter Anak</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Kode</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>Kategori</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Nama Unit</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Ruangan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='12%'>Nama Tindakan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Frekuensi (Jumlah)</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Jasa Sarana</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Jasa Pelayanan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Tarif</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total Jasa Sarana</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total Jasa Pelayanan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Tgl.Transaksi</td>"+
                            "</tr>"
                        ); 
                        for(i=0;i<tabMode.getRowCount();i++){  
                            try {
                                js=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,15).toString()));
                            } catch (Exception e) {
                                js="";
                            }
                            try {
                                jm=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,16).toString()));
                            } catch (Exception e) {
                                jm="";
                            }
                            try {
                                tarif=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,17).toString()));
                            } catch (Exception e) {
                                tarif="";
                            }
                            try {
                                totalsaranas=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,18).toString()));
                            } catch (Exception e) {
                                totalsaranas="";
                            }
                            try {
                                totaljms=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,19).toString()));
                            } catch (Exception e) {
                                totaljms="";
                            }
                            try {
                                totalbayars=Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,20).toString()));
                            } catch (Exception e) {
                                totalbayars="";
                            }

                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,0).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,4).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,5).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,6).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,7).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,8).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,9).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,10).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,11).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,12).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,13).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,14).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='right'>"+js+"</td>"+
                                    "<td valign='top' align='right'>"+jm+"</td>"+
                                    "<td valign='top' align='right'>"+tarif+"</td>"+
                                    "<td valign='top' align='right'>"+totalsaranas+"</td>"+
                                    "<td valign='top' align='right'>"+totaljms+"</td>"+
                                    "<td valign='top' align='right'>"+totalbayars+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,21).toString().replaceAll("'","`")+"</td>"+
                                "</tr>"
                            ); 
                        }            

                        f = new File("DetailJMDokter.wps");            
                        bw = new BufferedWriter(new FileWriter(f));            
                        bw.write("<html>"+
                                    "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                    "<body>"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DETAIL JM DOKTER PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            htmlContent.toString()+
                                        "</table>"+
                                    "</body>"+                   
                                 "</html>"
                        );

                        bw.close();                         
                        Desktop.getDesktop().browse(f.toURI());
                    break;
                case "Laporan 4 (WPS)":
                        htmlContent = new StringBuilder();
                        htmlContent.append(                             
                            "<tr class='isi'>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>No.Rawat</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Nomor RM</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>Nama Pasien</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>&nbsp;&nbsp;Tgl.Masuk&nbsp;&nbsp;</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>&nbsp;&nbsp;Tgl.Keluar&nbsp;&nbsp;</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>DPJP</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='8%'>Penindak</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Dokter Anestesi</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='7%'>Dokter Anak</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Kode</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='6%'>Kategori</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Nama Unit</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='9%'>Ruangan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='12%'>Nama Tindakan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Frekuensi (Jumlah)</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Jasa Sarana</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Jasa Pelayanan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Tarif</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total Jasa Sarana</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total Jasa Pelayanan</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='3%'>Total</td>"+
                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='2%'>Tgl.Transaksi</td>"+
                            "</tr>"
                        ); 
                        for(i=0;i<tabMode.getRowCount();i++){  
                            try {
                                js=tabMode.getValueAt(i,15).toString();
                            } catch (Exception e) {
                                js="";
                            }
                            try {
                                jm=tabMode.getValueAt(i,16).toString();
                            } catch (Exception e) {
                                jm="";
                            }
                            try {
                                tarif=tabMode.getValueAt(i,17).toString();
                            } catch (Exception e) {
                                tarif="";
                            }
                            try {
                                totalsaranas=tabMode.getValueAt(i,18).toString();
                            } catch (Exception e) {
                                totalsaranas="";
                            }
                            try {
                                totaljms=tabMode.getValueAt(i,19).toString();
                            } catch (Exception e) {
                                totaljms="";
                            }
                            try {
                                totalbayars=tabMode.getValueAt(i,20).toString();
                            } catch (Exception e) {
                                totalbayars="";
                            }

                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,0).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,4).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,5).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,6).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,7).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,8).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,9).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,10).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,11).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,12).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top'>"+tabMode.getValueAt(i,13).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,14).toString().replaceAll("'","`")+"</td>"+
                                    "<td valign='top' align='right'>"+js+"</td>"+
                                    "<td valign='top' align='right'>"+jm+"</td>"+
                                    "<td valign='top' align='right'>"+tarif+"</td>"+
                                    "<td valign='top' align='right'>"+totalsaranas+"</td>"+
                                    "<td valign='top' align='right'>"+totaljms+"</td>"+
                                    "<td valign='top' align='right'>"+totalbayars+"</td>"+
                                    "<td valign='top' align='center'>"+tabMode.getValueAt(i,21).toString().replaceAll("'","`")+"</td>"+
                                "</tr>"
                            ); 
                        }            

                        f = new File("DetailJMDokter2.wps");            
                        bw = new BufferedWriter(new FileWriter(f));            
                        bw.write("<html>"+
                                    "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                    "<body>"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi2'>"+
                                                "<td valign='top' align='center'>"+
                                                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                    "<font size='2' face='Tahoma'>DETAIL JM DOKTER PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                "</td>"+
                                           "</tr>"+
                                        "</table>"+
                                        "<table width='2000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            htmlContent.toString()+
                                        "</table>"+
                                    "</body>"+                   
                                 "</html>"
                        );

                        bw.close();                         
                        Desktop.getDesktop().browse(f.toURI());
                    break;  
                case "Laporan 5 (CSV)":
                        htmlContent = new StringBuilder();
                        htmlContent.append(                             
                            "\"No.Rawat\";\"Nomor RM\";\"Nama Pasien\";\"Tgl.Masuk\";\"Tgl.Keluar\";\"DPJP\";\"Penindak\";\"Dokter Anestesi\";\"Dokter Anak\";\"Kode\";\"Kategori\";\"Nama Unit\";\"Ruangan\";\"Nama Tindakan\";\"Frekuensi (Jumlah)\";\"Jasa Sarana\";\"Jasa Pelayanan\";\"Tarif\";\"Total Jasa Sarana\";\"Total Jasa Pelayanan\";\"Total\";\"Tgl.Transaksi\"\n"
                        ); 
                        for(i=0;i<tabMode.getRowCount();i++){  
                            try {
                                js=tabMode.getValueAt(i,15).toString();
                            } catch (Exception e) {
                                js="";
                            }
                            try {
                                jm=tabMode.getValueAt(i,16).toString();
                            } catch (Exception e) {
                                jm="";
                            }
                            try {
                                tarif=tabMode.getValueAt(i,17).toString();
                            } catch (Exception e) {
                                tarif="";
                            }
                            try {
                                totalsaranas=tabMode.getValueAt(i,18).toString();
                            } catch (Exception e) {
                                totalsaranas="";
                            }
                            try {
                                totaljms=tabMode.getValueAt(i,19).toString();
                            } catch (Exception e) {
                                totaljms="";
                            }
                            try {
                                totalbayars=tabMode.getValueAt(i,20).toString();
                            } catch (Exception e) {
                                totalbayars="";
                            }

                            htmlContent.append(                             
                                "\""+tabMode.getValueAt(i,0).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,1).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,2).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,3).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,4).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,5).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,6).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,7).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,8).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,9).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,10).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,11).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,12).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,13).toString().replaceAll("'","`")+"\";\""+tabMode.getValueAt(i,14).toString().replaceAll("'","`")+"\";\""+js+"\";\""+jm+"\";\""+tarif+"\";\""+totalsaranas+"\";\""+totaljms+"\";\""+totalbayars+"\";\""+tabMode.getValueAt(i,21).toString().replaceAll("'","`")+"\"\n"
                            ); 
                        }            

                        f = new File("DetailJMDokter.csv");            
                        bw = new BufferedWriter(new FileWriter(f));            
                        bw.write(htmlContent.toString());

                        bw.close();                         
                        Desktop.getDesktop().browse(f.toURI());
                    break; 
                case "Laporan 6 (CSV)":
                        htmlContent = new StringBuilder();
                        htmlContent.append(                             
                            "No.Rawat;Nomor RM;Nama Pasien;Tgl.Masuk;Tgl.Keluar;DPJP;Penindak;Dokter Anestesi;Dokter Anak;Kode;Kategori;Nama Unit;Ruangan;Nama Tindakan;Frekuensi (Jumlah);Jasa Sarana;Jasa Pelayanan;Tarif;Total Jasa Sarana;Total Jasa Pelayanan;Total;Tgl.Transaksi\n"
                        ); 
                        for(i=0;i<tabMode.getRowCount();i++){  
                            try {
                                js=tabMode.getValueAt(i,15).toString();
                            } catch (Exception e) {
                                js="";
                            }
                            try {
                                jm=tabMode.getValueAt(i,16).toString();
                            } catch (Exception e) {
                                jm="";
                            }
                            try {
                                tarif=tabMode.getValueAt(i,17).toString();
                            } catch (Exception e) {
                                tarif="";
                            }
                            try {
                                totalsaranas=tabMode.getValueAt(i,18).toString();
                            } catch (Exception e) {
                                totalsaranas="";
                            }
                            try {
                                totaljms=tabMode.getValueAt(i,19).toString();
                            } catch (Exception e) {
                                totaljms="";
                            }
                            try {
                                totalbayars=tabMode.getValueAt(i,20).toString();
                            } catch (Exception e) {
                                totalbayars="";
                            }

                            htmlContent.append(                             
                                ""+tabMode.getValueAt(i,0).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,1).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,2).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,3).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,4).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,5).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,6).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,7).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,8).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,9).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,10).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,11).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,12).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,13).toString().replaceAll("'","`")+";"+tabMode.getValueAt(i,14).toString().replaceAll("'","`")+";"+js+";"+jm+";"+tarif+";"+totalsaranas+";"+totaljms+";"+totalbayars+";"+tabMode.getValueAt(i,21).toString().replaceAll("'","`")+"\n"
                            ); 
                        }            

                        f = new File("DetailJMDokter2.csv");            
                        bw = new BufferedWriter(new FileWriter(f));            
                        bw.write(htmlContent.toString());

                        bw.close();                         
                        Desktop.getDesktop().browse(f.toURI());
                    break; 
            }                 
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }     
        
        this.setCursor(Cursor.getDefaultCursor());
               
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnKeluar);
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

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        pilihancarabayar="";
        prosesCari();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    pilihancarabayar="";
    prosesCari();       
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        
    }//GEN-LAST:event_Tgl2KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
        pilihancarabayar="";
    }//GEN-LAST:event_formWindowOpened

    private void chkRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRalanActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRalanActionPerformed

    private void chkRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRadiologiActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRadiologiActionPerformed

    private void chkLaboratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkLaboratActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkLaboratActionPerformed

    private void chkOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkOperasiActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkOperasiActionPerformed

    private void chkRanapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkRanapActionPerformed
        prosesCari();
    }//GEN-LAST:event_chkRanapActionPerformed

    private void ppTampilkanSeleksiBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppTampilkanSeleksiBtnPrintActionPerformed
        carabayar.isCek();
        carabayar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carabayar.setLocationRelativeTo(internalFrame1);
        carabayar.setVisible(true);
    }//GEN-LAST:event_ppTampilkanSeleksiBtnPrintActionPerformed

    private void TCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyTyped
       
    }//GEN-LAST:event_TCariKeyTyped

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            
        }
    }//GEN-LAST:event_TCariKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDetailJMDokter2 dialog = new DlgDetailJMDokter2(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.CekBox chkLaborat;
    private widget.CekBox chkOperasi;
    private widget.CekBox chkRadiologi;
    private widget.CekBox chkRalan;
    private widget.CekBox chkRanap;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.Label label11;
    private widget.Label label18;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi4;
    private javax.swing.JMenuItem ppTampilkanSeleksi;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDetail;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            Valid.tabelKosong(tabMode); 
            psreg=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.no_rkm_medis,dokter.nm_dokter,"+
                    "pasien.nm_pasien,penjab.png_jawab,reg_periksa.kd_poli,poliklinik.nm_poli,reg_periksa.status_lanjut,reg_periksa.kd_pj "+
                    "from reg_periksa inner join pasien inner join poliklinik inner join penjab "+
                    "inner join dokter on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and reg_periksa.kd_dokter=dokter.kd_dokter "+
                    "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "where reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_pj like ? and reg_periksa.no_rkm_medis like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_pj like ? and pasien.nm_pasien like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_pj like ? and dokter.nm_dokter like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_pj like ? and penjab.png_jawab like ? or "+
                    "reg_periksa.tgl_registrasi between ? and ? and reg_periksa.kd_pj like ? and poliklinik.nm_poli like ? order by reg_periksa.tgl_registrasi");
            try {
                psreg.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                psreg.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                psreg.setString(3,"%"+pilihancarabayar+"%");
                psreg.setString(4,"%"+TCari.getText().trim()+"%");
                psreg.setString(5,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                psreg.setString(6,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                psreg.setString(7,"%"+pilihancarabayar+"%");
                psreg.setString(8,"%"+TCari.getText().trim()+"%");
                psreg.setString(9,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                psreg.setString(10,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                psreg.setString(11,"%"+pilihancarabayar+"%");
                psreg.setString(12,"%"+TCari.getText().trim()+"%");
                psreg.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                psreg.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                psreg.setString(15,"%"+pilihancarabayar+"%");
                psreg.setString(16,"%"+TCari.getText().trim()+"%");
                psreg.setString(17,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                psreg.setString(18,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                psreg.setString(19,"%"+pilihancarabayar+"%");
                psreg.setString(20,"%"+TCari.getText().trim()+"%");
                rsreg=psreg.executeQuery();
                totalsarana=0;totaljm=0;totalbayar=0;
                while(rsreg.next()){
                    namaruangan="";
                    tglkeluar="";
                    dpjp="";
                    if(rsreg.getString("status_lanjut").equals("Ralan")){
                        namaruangan=rsreg.getString("nm_poli");
                        tglkeluar=rsreg.getString("tgl_registrasi");
                        dpjp=rsreg.getString("nm_dokter");
                    }else if(rsreg.getString("status_lanjut").equals("Ranap")){
                        pskamar=koneksi.prepareStatement(
                            "select kamar_inap.kd_kamar,bangsal.nm_bangsal,"+
                            "if(kamar_inap.tgl_keluar='0000-00-00','-',kamar_inap.tgl_keluar) as tgl_keluar,kamar.kelas "+
                            "from kamar_inap inner join kamar inner join bangsal on "+
                            "kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal "+
                            "where kamar_inap.no_rawat=? group by kamar_inap.no_rawat");
                        try {
                            pskamar.setString(1,rsreg.getString("no_rawat"));
                            rskamar=pskamar.executeQuery();
                            if(rskamar.next()){ 
                                namaruangan=rskamar.getString("nm_bangsal")+"("+rskamar.getString("kelas")+")";
                                tglkeluar=rskamar.getString("tgl_keluar");
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi Kamar : "+e);
                        } finally{
                            if(rskamar!=null){
                                rskamar.close();
                            } 
                            if(pskamar!=null){
                                pskamar.close();
                            } 
                        }
                        dpjp=Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat=?",rsreg.getString("no_rawat"));
                        if(dpjp.equals("")){
                            dpjp=rsreg.getString("nm_dokter");
                        }
                    }
                    if(chkRalan.isSelected()==true){
                        //tindakan dr
                        pstindakan=koneksi.prepareStatement(
                                "select jns_perawatan.nm_perawatan,dokter.nm_dokter, "+
                                "rawat_jl_dr.tgl_perawatan,count(rawat_jl_dr.no_rawat) as jumlah, "+
                                "rawat_jl_dr.tarif_tindakandr,sum(rawat_jl_dr.tarif_tindakandr) as totaljm,"+
                                "(rawat_jl_dr.material+rawat_jl_dr.bhp+rawat_jl_dr.kso+rawat_jl_dr.menejemen) as sarana,"+
                                "sum(rawat_jl_dr.material+rawat_jl_dr.bhp+rawat_jl_dr.kso+rawat_jl_dr.menejemen) as totalsarana "+
                                "from rawat_jl_dr inner join jns_perawatan inner join dokter on "+
                                "rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and "+
                                "rawat_jl_dr.kd_dokter=dokter.kd_dokter where rawat_jl_dr.no_rawat=? "+
                                "group by rawat_jl_dr.kd_jenis_prw,rawat_jl_dr.tgl_perawatan");
                        try {
                            pstindakan.setString(1,rsreg.getString("no_rawat"));
                            rstindakan=pstindakan.executeQuery();
                            while(rstindakan.next()){
                                totalsarana=totalsarana+rstindakan.getDouble("totalsarana");
                                totaljm=totaljm+rstindakan.getDouble("totaljm");
                                totalbayar=totalbayar+rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana");
                                tabMode.addRow(new Object[]{
                                    rsreg.getString("no_rawat"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                    rsreg.getString("tgl_registrasi"),tglkeluar,
                                    dpjp,rstindakan.getString("nm_dokter"),"","",
                                    rsreg.getString("kd_pj"),rsreg.getString("png_jawab"),"Ralan",
                                    namaruangan,rstindakan.getString("nm_perawatan"),
                                    rstindakan.getString("jumlah"),rstindakan.getDouble("sarana"),
                                    rstindakan.getDouble("tarif_tindakandr"),
                                    (rstindakan.getDouble("sarana")+rstindakan.getDouble("tarif_tindakandr")),
                                    rstindakan.getDouble("totalsarana"),rstindakan.getDouble("totaljm"),
                                    (rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana")),
                                    rstindakan.getString("tgl_perawatan")                                    
                                });
                            }                           
                        } catch (Exception e) {
                            System.out.println("Notif Ralan dr : "+e);
                        } finally{
                            if(rstindakan!=null){
                                rstindakan.close();
                            }
                            if(pstindakan!=null){
                                pstindakan.close();
                            }
                        }
                        
                        //tindakan drpr
                        pstindakan=koneksi.prepareStatement(
                                "select jns_perawatan.nm_perawatan,dokter.nm_dokter, "+
                                "rawat_jl_drpr.tgl_perawatan,count(rawat_jl_drpr.no_rawat) as jumlah, "+
                                "rawat_jl_drpr.tarif_tindakandr,sum(rawat_jl_drpr.tarif_tindakandr) as totaljm,"+
                                "(rawat_jl_drpr.material+rawat_jl_drpr.bhp+rawat_jl_drpr.kso+rawat_jl_drpr.menejemen+rawat_jl_drpr.tarif_tindakanpr) as sarana,"+
                                "sum(rawat_jl_drpr.material+rawat_jl_drpr.bhp+rawat_jl_drpr.kso+rawat_jl_drpr.menejemen+rawat_jl_drpr.tarif_tindakanpr) as totalsarana "+
                                "from rawat_jl_drpr inner join jns_perawatan inner join dokter on "+
                                "rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and "+
                                "rawat_jl_drpr.kd_dokter=dokter.kd_dokter where rawat_jl_drpr.no_rawat=? "+
                                "group by rawat_jl_drpr.kd_jenis_prw,rawat_jl_drpr.tgl_perawatan");
                        try {
                            pstindakan.setString(1,rsreg.getString("no_rawat"));
                            rstindakan=pstindakan.executeQuery();
                            while(rstindakan.next()){
                                totalsarana=totalsarana+rstindakan.getDouble("totalsarana");
                                totaljm=totaljm+rstindakan.getDouble("totaljm");
                                totalbayar=totalbayar+rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana");
                                tabMode.addRow(new Object[]{
                                    rsreg.getString("no_rawat"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                    rsreg.getString("tgl_registrasi"),tglkeluar,
                                    dpjp,rstindakan.getString("nm_dokter"),"","",
                                    rsreg.getString("kd_pj"),rsreg.getString("png_jawab"),"Ralan",
                                    namaruangan,rstindakan.getString("nm_perawatan"),
                                    rstindakan.getString("jumlah"),rstindakan.getDouble("sarana"),
                                    rstindakan.getDouble("tarif_tindakandr"),
                                    (rstindakan.getDouble("sarana")+rstindakan.getDouble("tarif_tindakandr")),
                                    rstindakan.getDouble("totalsarana"),rstindakan.getDouble("totaljm"),
                                    (rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana")),
                                    rstindakan.getString("tgl_perawatan")                                    
                                });
                            }                           
                        } catch (Exception e) {
                            System.out.println("Notif Ralan dr pr : "+e);
                        } finally{
                            if(rstindakan!=null){
                                rstindakan.close();
                            }
                            if(pstindakan!=null){
                                pstindakan.close();
                            }
                        }
                        
                        //tindakan perawat
                        pstindakan=koneksi.prepareStatement(
                                "select jns_perawatan.nm_perawatan,petugas.nama, "+
                                "rawat_jl_pr.tgl_perawatan,count(rawat_jl_pr.no_rawat) as jumlah, "+
                                "rawat_jl_pr.tarif_tindakanpr,sum(rawat_jl_pr.tarif_tindakanpr) as totaljm,"+
                                "(rawat_jl_pr.material+rawat_jl_pr.bhp+rawat_jl_pr.kso+rawat_jl_pr.menejemen) as sarana,"+
                                "sum(rawat_jl_pr.material+rawat_jl_pr.bhp+rawat_jl_pr.kso+rawat_jl_pr.menejemen) as totalsarana "+
                                "from rawat_jl_pr inner join jns_perawatan inner join petugas on "+
                                "rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and "+
                                "rawat_jl_pr.nip=petugas.nip where rawat_jl_pr.no_rawat=? "+
                                "group by rawat_jl_pr.kd_jenis_prw,rawat_jl_pr.tgl_perawatan");
                        try {
                            pstindakan.setString(1,rsreg.getString("no_rawat"));
                            rstindakan=pstindakan.executeQuery();
                            while(rstindakan.next()){
                                totalsarana=totalsarana+rstindakan.getDouble("totalsarana");
                                totaljm=totaljm+rstindakan.getDouble("totaljm");
                                totalbayar=totalbayar+rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana");
                                tabMode.addRow(new Object[]{
                                    rsreg.getString("no_rawat"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                    rsreg.getString("tgl_registrasi"),tglkeluar,
                                    dpjp,rstindakan.getString("nama"),"","",
                                    rsreg.getString("kd_pj"),rsreg.getString("png_jawab"),"Ralan",
                                    namaruangan,rstindakan.getString("nm_perawatan"),
                                    rstindakan.getString("jumlah"),rstindakan.getDouble("sarana"),
                                    rstindakan.getDouble("tarif_tindakanpr"),
                                    (rstindakan.getDouble("sarana")+rstindakan.getDouble("tarif_tindakanpr")),
                                    rstindakan.getDouble("totalsarana"),rstindakan.getDouble("totaljm"),
                                    (rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana")),
                                    rstindakan.getString("tgl_perawatan")                                    
                                });
                            }                           
                        } catch (Exception e) {
                            System.out.println("Notif Ralan dr : "+e);
                        } finally{
                            if(rstindakan!=null){
                                rstindakan.close();
                            }
                            if(pstindakan!=null){
                                pstindakan.close();
                            }
                        }
                    }
                    
                    if(chkRanap.isSelected()==true){
                        //tindakan dr
                        pstindakan=koneksi.prepareStatement(
                                "select jns_perawatan_inap.nm_perawatan,dokter.nm_dokter, "+
                                "rawat_inap_dr.tgl_perawatan,count(rawat_inap_dr.no_rawat) as jumlah, "+
                                "rawat_inap_dr.tarif_tindakandr,sum(rawat_inap_dr.tarif_tindakandr) as totaljm,"+
                                "(rawat_inap_dr.material+rawat_inap_dr.bhp+rawat_inap_dr.kso+rawat_inap_dr.menejemen) as sarana,"+
                                "sum(rawat_inap_dr.material+rawat_inap_dr.bhp+rawat_inap_dr.kso+rawat_inap_dr.menejemen) as totalsarana "+
                                "from rawat_inap_dr inner join jns_perawatan_inap inner join dokter on "+
                                "rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and "+
                                "rawat_inap_dr.kd_dokter=dokter.kd_dokter where rawat_inap_dr.no_rawat=? "+
                                "group by rawat_inap_dr.kd_jenis_prw,rawat_inap_dr.tgl_perawatan");
                        try {
                            pstindakan.setString(1,rsreg.getString("no_rawat"));
                            rstindakan=pstindakan.executeQuery();
                            while(rstindakan.next()){
                                totalsarana=totalsarana+rstindakan.getDouble("totalsarana");
                                totaljm=totaljm+rstindakan.getDouble("totaljm");
                                totalbayar=totalbayar+rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana");
                                tabMode.addRow(new Object[]{
                                    rsreg.getString("no_rawat"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                    rsreg.getString("tgl_registrasi"),tglkeluar,
                                    dpjp,rstindakan.getString("nm_dokter"),"","",
                                    rsreg.getString("kd_pj"),rsreg.getString("png_jawab"),"Ranap",
                                    namaruangan,rstindakan.getString("nm_perawatan"),
                                    rstindakan.getString("jumlah"),rstindakan.getDouble("sarana"),
                                    rstindakan.getDouble("tarif_tindakandr"),
                                    (rstindakan.getDouble("sarana")+rstindakan.getDouble("tarif_tindakandr")),
                                    rstindakan.getDouble("totalsarana"),rstindakan.getDouble("totaljm"),
                                    (rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana")),
                                    rstindakan.getString("tgl_perawatan")                                    
                                });
                            }                           
                        } catch (Exception e) {
                            System.out.println("Notif Inap dr : "+e);
                        } finally{
                            if(rstindakan!=null){
                                rstindakan.close();
                            }
                            if(pstindakan!=null){
                                pstindakan.close();
                            }
                        }
                        
                        //tindakan drpr
                        pstindakan=koneksi.prepareStatement(
                                "select jns_perawatan_inap.nm_perawatan,dokter.nm_dokter, "+
                                "rawat_inap_drpr.tgl_perawatan,count(rawat_inap_drpr.no_rawat) as jumlah, "+
                                "rawat_inap_drpr.tarif_tindakandr,sum(rawat_inap_drpr.tarif_tindakandr) as totaljm,"+
                                "(rawat_inap_drpr.material+rawat_inap_drpr.bhp+rawat_inap_drpr.kso+rawat_inap_drpr.menejemen+rawat_inap_drpr.tarif_tindakanpr) as sarana,"+
                                "sum(rawat_inap_drpr.material+rawat_inap_drpr.bhp+rawat_inap_drpr.kso+rawat_inap_drpr.menejemen+rawat_inap_drpr.tarif_tindakanpr) as totalsarana "+
                                "from rawat_inap_drpr inner join jns_perawatan_inap inner join dokter on "+
                                "rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and "+
                                "rawat_inap_drpr.kd_dokter=dokter.kd_dokter where rawat_inap_drpr.no_rawat=? "+
                                "group by rawat_inap_drpr.kd_jenis_prw,rawat_inap_drpr.tgl_perawatan");
                        try {
                            pstindakan.setString(1,rsreg.getString("no_rawat"));
                            rstindakan=pstindakan.executeQuery();
                            while(rstindakan.next()){
                                totalsarana=totalsarana+rstindakan.getDouble("totalsarana");
                                totaljm=totaljm+rstindakan.getDouble("totaljm");
                                totalbayar=totalbayar+rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana");
                                tabMode.addRow(new Object[]{
                                    rsreg.getString("no_rawat"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                    rsreg.getString("tgl_registrasi"),tglkeluar,
                                    dpjp,rstindakan.getString("nm_dokter"),"","",
                                    rsreg.getString("kd_pj"),rsreg.getString("png_jawab"),"Ranap",
                                    namaruangan,rstindakan.getString("nm_perawatan"),
                                    rstindakan.getString("jumlah"),rstindakan.getDouble("sarana"),
                                    rstindakan.getDouble("tarif_tindakandr"),
                                    (rstindakan.getDouble("sarana")+rstindakan.getDouble("tarif_tindakandr")),
                                    rstindakan.getDouble("totalsarana"),rstindakan.getDouble("totaljm"),
                                    (rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana")),
                                    rstindakan.getString("tgl_perawatan")                                    
                                });
                            }   
                            
                        } catch (Exception e) {
                            System.out.println("Notif Inap dr pr : "+e);
                        } finally{
                            if(rstindakan!=null){
                                rstindakan.close();
                            }
                            if(pstindakan!=null){
                                pstindakan.close();
                            }
                        }
                        
                        pstindakan=koneksi.prepareStatement(
                                "select jns_perawatan_inap.nm_perawatan,petugas.nama, "+
                                "rawat_inap_pr.tgl_perawatan,count(rawat_inap_pr.no_rawat) as jumlah, "+
                                "rawat_inap_pr.tarif_tindakanpr,sum(rawat_inap_pr.tarif_tindakanpr) as totaljm,"+
                                "(rawat_inap_pr.material+rawat_inap_pr.bhp+rawat_inap_pr.kso+rawat_inap_pr.menejemen) as sarana,"+
                                "sum(rawat_inap_pr.material+rawat_inap_pr.bhp+rawat_inap_pr.kso+rawat_inap_pr.menejemen) as totalsarana "+
                                "from rawat_inap_pr inner join jns_perawatan_inap inner join petugas on "+
                                "rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and "+
                                "rawat_inap_pr.nip=petugas.nip where rawat_inap_pr.no_rawat=? "+
                                "group by rawat_inap_pr.kd_jenis_prw,rawat_inap_pr.tgl_perawatan");
                        try {
                            pstindakan.setString(1,rsreg.getString("no_rawat"));
                            rstindakan=pstindakan.executeQuery();
                            while(rstindakan.next()){
                                totalsarana=totalsarana+rstindakan.getDouble("totalsarana");
                                totaljm=totaljm+rstindakan.getDouble("totaljm");
                                totalbayar=totalbayar+rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana");
                                tabMode.addRow(new Object[]{
                                    rsreg.getString("no_rawat"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                    rsreg.getString("tgl_registrasi"),tglkeluar,
                                    dpjp,rstindakan.getString("nama"),"","",
                                    rsreg.getString("kd_pj"),rsreg.getString("png_jawab"),"Ranap",
                                    namaruangan,rstindakan.getString("nm_perawatan"),
                                    rstindakan.getString("jumlah"),rstindakan.getDouble("sarana"),
                                    rstindakan.getDouble("tarif_tindakanpr"),
                                    (rstindakan.getDouble("sarana")+rstindakan.getDouble("tarif_tindakanpr")),
                                    rstindakan.getDouble("totalsarana"),rstindakan.getDouble("totaljm"),
                                    (rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana")),
                                    rstindakan.getString("tgl_perawatan")                                    
                                });
                            }                           
                        } catch (Exception e) {
                            System.out.println("Notif Inap dr : "+e);
                        } finally{
                            if(rstindakan!=null){
                                rstindakan.close();
                            }
                            if(pstindakan!=null){
                                pstindakan.close();
                            }
                        }
                    }
                    
                    if(chkRadiologi.isSelected()==true){
                        //tindakan radiologi
                        pstindakan=koneksi.prepareStatement(
                                "select jns_perawatan_radiologi.nm_perawatan,dokter.nm_dokter, "+
                                "periksa_radiologi.tgl_periksa,count(periksa_radiologi.no_rawat) as jumlah, "+
                                "periksa_radiologi.tarif_tindakan_dokter,sum(periksa_radiologi.tarif_tindakan_dokter) as totaljm,"+
                                "(periksa_radiologi.bagian_rs+periksa_radiologi.bhp+periksa_radiologi.kso+periksa_radiologi.menejemen+periksa_radiologi.tarif_perujuk+periksa_radiologi.tarif_tindakan_petugas) as sarana,"+
                                "sum(periksa_radiologi.bagian_rs+periksa_radiologi.bhp+periksa_radiologi.kso+periksa_radiologi.menejemen+periksa_radiologi.tarif_perujuk+periksa_radiologi.tarif_tindakan_petugas) as totalsarana "+
                                "from periksa_radiologi inner join jns_perawatan_radiologi inner join dokter on "+
                                "periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw and "+
                                "periksa_radiologi.kd_dokter=dokter.kd_dokter where periksa_radiologi.no_rawat=? "+
                                "group by periksa_radiologi.kd_jenis_prw,periksa_radiologi.tgl_periksa");
                        try {
                            pstindakan.setString(1,rsreg.getString("no_rawat"));
                            rstindakan=pstindakan.executeQuery();
                            while(rstindakan.next()){
                                totalsarana=totalsarana+rstindakan.getDouble("totalsarana");
                                totaljm=totaljm+rstindakan.getDouble("totaljm");
                                totalbayar=totalbayar+rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana");
                                tabMode.addRow(new Object[]{
                                    rsreg.getString("no_rawat"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                    rsreg.getString("tgl_registrasi"),tglkeluar,
                                    dpjp,rstindakan.getString("nm_dokter"),"","",
                                    rsreg.getString("kd_pj"),rsreg.getString("png_jawab"),
                                    "Radiologi",namaruangan,rstindakan.getString("nm_perawatan"),
                                    rstindakan.getString("jumlah"),rstindakan.getDouble("sarana"),
                                    rstindakan.getDouble("tarif_tindakan_dokter"),
                                    (rstindakan.getDouble("sarana")+rstindakan.getDouble("tarif_tindakan_dokter")),
                                    rstindakan.getDouble("totalsarana"),rstindakan.getDouble("totaljm"),
                                    (rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana")),
                                    rstindakan.getString("tgl_periksa")
                                });            
                            }                           
                        } catch (Exception e) {
                            System.out.println("Notif Ralan dr : "+e);
                        } finally{
                            if(rstindakan!=null){
                                rstindakan.close();
                            }
                            if(pstindakan!=null){
                                pstindakan.close();
                            }
                        }
                    }
                    
                    //operasi
                    if(chkOperasi.isSelected()==true){
                        pstindakan=koneksi.prepareStatement(
                                "select paket_operasi.nm_perawatan,dokter.nm_dokter,operasi.dokter_anestesi,operasi.dokter_anak,operasi.status, "+
                                "DATE_FORMAT(tgl_operasi, '%Y-%m-%d') as tgl_operasi,count(operasi.no_rawat) as jumlah, "+
                                "(operasi.biayaoperator1+biayadokter_anestesi+biayadokter_anak) as biayaoperator1,sum(operasi.biayaoperator1+biayadokter_anestesi+biayadokter_anak) as totaljm,"+
                                "(operasi.biayaoperator2+operasi.biayaoperator3+operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayaperawaat_resusitas+operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biayasarpras+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum) as sarana,"+
                                "sum(operasi.biayaoperator2+operasi.biayaoperator3+operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayaperawaat_resusitas+operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biayasarpras+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum) as totalsarana "+
                                "from operasi inner join paket_operasi inner join dokter on "+
                                "operasi.kode_paket=paket_operasi.kode_paket and "+
                                "operasi.operator1=dokter.kd_dokter where operasi.no_rawat=? "+
                                "group by operasi.kode_paket,DATE_FORMAT(tgl_operasi,'%Y-%m-%d')");
                        try {
                            pstindakan.setString(1,rsreg.getString("no_rawat"));
                            rstindakan=pstindakan.executeQuery();
                            while(rstindakan.next()){
                                totalsarana=totalsarana+rstindakan.getDouble("totalsarana");
                                totaljm=totaljm+rstindakan.getDouble("totaljm");
                                totalbayar=totalbayar+rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana");
                                if(rstindakan.getString("status").equals("Ralan")){
                                    tabMode.addRow(new Object[]{
                                        rsreg.getString("no_rawat"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                        rsreg.getString("tgl_registrasi"),tglkeluar,
                                        dpjp,rstindakan.getString("nm_dokter"),
                                        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rstindakan.getString("dokter_anestesi")),
                                        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rstindakan.getString("dokter_anak")),
                                        rsreg.getString("kd_pj"),rsreg.getString("png_jawab"),rsreg.getString("nm_poli"),
                                        namaruangan,rstindakan.getString("nm_perawatan"),
                                        rstindakan.getString("jumlah"),rstindakan.getDouble("sarana"),
                                        rstindakan.getDouble("biayaoperator1"),
                                        (rstindakan.getDouble("sarana")+rstindakan.getDouble("biayaoperator1")),
                                        rstindakan.getDouble("totalsarana"),rstindakan.getDouble("totaljm"),
                                        (rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana")),
                                        rstindakan.getString("tgl_operasi")
                                    });   
                                }else{
                                    tabMode.addRow(new Object[]{
                                        rsreg.getString("no_rawat"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                        rsreg.getString("tgl_registrasi"),tglkeluar,
                                        dpjp,rstindakan.getString("nm_dokter"),
                                        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rstindakan.getString("dokter_anestesi")),
                                        Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rstindakan.getString("dokter_anak")),
                                        rsreg.getString("kd_pj"),rsreg.getString("png_jawab"),"Operasi",
                                        namaruangan,rstindakan.getString("nm_perawatan"),
                                        rstindakan.getString("jumlah"),rstindakan.getDouble("sarana"),
                                        rstindakan.getDouble("biayaoperator1"),
                                        (rstindakan.getDouble("sarana")+rstindakan.getDouble("biayaoperator1")),
                                        rstindakan.getDouble("totalsarana"),rstindakan.getDouble("totaljm"),
                                        (rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana")),
                                        rstindakan.getString("tgl_operasi")
                                    });   
                                }                                     
                            }                           
                        } catch (Exception e) {
                            System.out.println("Notif Ralan dr : "+e);
                        } finally{
                            if(rstindakan!=null){
                                rstindakan.close();
                            }
                            if(pstindakan!=null){
                                pstindakan.close();
                            }
                        }
                    }
                    
                    if(chkLaborat.isSelected()==true){
                        //tindakan lab
                        pstindakan=koneksi.prepareStatement(
                                "select jns_perawatan_lab.nm_perawatan,dokter.nm_dokter, "+
                                "periksa_lab.tgl_periksa,count(periksa_lab.no_rawat) as jumlah, "+
                                "periksa_lab.tarif_tindakan_dokter,sum(periksa_lab.tarif_tindakan_dokter) as totaljm,"+
                                "(periksa_lab.bagian_rs+periksa_lab.bhp+periksa_lab.kso+periksa_lab.menejemen+periksa_lab.tarif_perujuk+periksa_lab.tarif_tindakan_petugas) as sarana,"+
                                "sum(periksa_lab.bagian_rs+periksa_lab.bhp+periksa_lab.kso+periksa_lab.menejemen+periksa_lab.tarif_perujuk+periksa_lab.tarif_tindakan_petugas) as totalsarana "+
                                "from periksa_lab inner join jns_perawatan_lab inner join dokter on "+
                                "periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw and "+
                                "periksa_lab.kd_dokter=dokter.kd_dokter where periksa_lab.no_rawat=? and periksa_lab.biaya>0 "+
                                "group by periksa_lab.kd_jenis_prw,periksa_lab.tgl_periksa");
                        try {
                            pstindakan.setString(1,rsreg.getString("no_rawat"));
                            rstindakan=pstindakan.executeQuery();
                            while(rstindakan.next()){
                                totalsarana=totalsarana+rstindakan.getDouble("totalsarana");
                                totaljm=totaljm+rstindakan.getDouble("totaljm");
                                totalbayar=totalbayar+rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana");
                                tabMode.addRow(new Object[]{
                                    rsreg.getString("no_rawat"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                    rsreg.getString("tgl_registrasi"),tglkeluar,
                                    dpjp,rstindakan.getString("nm_dokter"),"","",
                                    rsreg.getString("kd_pj"),rsreg.getString("png_jawab"),
                                    "Laborat",namaruangan,rstindakan.getString("nm_perawatan"),
                                    rstindakan.getString("jumlah"),rstindakan.getDouble("sarana"),
                                    rstindakan.getDouble("tarif_tindakan_dokter"),
                                    (rstindakan.getDouble("sarana")+rstindakan.getDouble("tarif_tindakan_dokter")),
                                    rstindakan.getDouble("totalsarana"),rstindakan.getDouble("totaljm"),
                                    (rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana")),
                                    rstindakan.getString("tgl_periksa")
                                });
                            }                        
                        } catch (Exception e) {
                            System.out.println("Notif Ralan dr : "+e);
                        } finally{
                            if(rstindakan!=null){
                                rstindakan.close();
                            }
                            if(pstindakan!=null){
                                pstindakan.close();
                            }
                        }
                        
                        pstindakan=koneksi.prepareStatement(
                                "select template_laboratorium.Pemeriksaan,dokter.nm_dokter, "+
                                "detail_periksa_lab.tgl_periksa,count(detail_periksa_lab.no_rawat) as jumlah, "+
                                "detail_periksa_lab.bagian_dokter,sum(detail_periksa_lab.bagian_dokter) as totaljm,"+
                                "(detail_periksa_lab.bagian_rs+detail_periksa_lab.bhp+detail_periksa_lab.kso+detail_periksa_lab.menejemen+detail_periksa_lab.bagian_perujuk+detail_periksa_lab.bagian_laborat) as sarana,"+
                                "sum(detail_periksa_lab.bagian_rs+detail_periksa_lab.bhp+detail_periksa_lab.kso+detail_periksa_lab.menejemen+detail_periksa_lab.bagian_perujuk+detail_periksa_lab.bagian_laborat) as totalsarana "+
                                "from detail_periksa_lab inner join template_laboratorium inner join dokter inner join periksa_lab on "+
                                "detail_periksa_lab.id_template=template_laboratorium.id_template and "+
                                "periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and "+
                                "periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and "+
                                "periksa_lab.jam=detail_periksa_lab.jam and periksa_lab.no_rawat=detail_periksa_lab.no_rawat and "+
                                "periksa_lab.kd_dokter=dokter.kd_dokter where detail_periksa_lab.no_rawat=? and detail_periksa_lab.biaya_item>0 "+
                                "group by detail_periksa_lab.id_template,detail_periksa_lab.tgl_periksa");
                        try {
                            pstindakan.setString(1,rsreg.getString("no_rawat"));
                            rstindakan=pstindakan.executeQuery();
                            while(rstindakan.next()){
                                totalsarana=totalsarana+rstindakan.getDouble("totalsarana");
                                totaljm=totaljm+rstindakan.getDouble("totaljm");
                                totalbayar=totalbayar+rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana");
                                tabMode.addRow(new Object[]{
                                    rsreg.getString("no_rawat"),rsreg.getString("no_rkm_medis"),rsreg.getString("nm_pasien"),
                                    rsreg.getString("tgl_registrasi"),tglkeluar,
                                    dpjp,rstindakan.getString("nm_dokter"),"","",
                                    rsreg.getString("kd_pj"),rsreg.getString("png_jawab"),
                                    "Laborat",namaruangan,rstindakan.getString("Pemeriksaan"),
                                    rstindakan.getString("jumlah"),rstindakan.getDouble("sarana"),
                                    rstindakan.getDouble("bagian_dokter"),
                                    (rstindakan.getDouble("sarana")+rstindakan.getDouble("bagian_dokter")),
                                    rstindakan.getDouble("totalsarana"),rstindakan.getDouble("totaljm"),
                                    (rstindakan.getDouble("totaljm")+rstindakan.getDouble("totalsarana")),
                                    rstindakan.getString("tgl_periksa")
                                });
                            }                           
                        } catch (Exception e) {
                            System.out.println("Notif Detail Laborat : "+e);
                        } finally{
                            if(rstindakan!=null){
                                rstindakan.close();
                            }
                            if(pstindakan!=null){
                                pstindakan.close();
                            }
                        }
                    }
                }      
                if(totalbayar>0){
                    tabMode.addRow(new Object[]{
                        "","","Total :","","","","","","","","","","","","",null,
                        null,null,totalsarana,totaljm,totalbayar,""
                    });
                }                    
            } catch (Exception e) {
                System.out.println("Notifikasi preg : "+e);
            } finally{
                if(rsreg!=null){
                    rsreg.close();
                }
                if(psreg!=null){
                    psreg.close();
                }
            }           
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        } 
       this.setCursor(Cursor.getDefaultCursor());
        
    }    
    
    public void isCek(){
        //BtnPrint.setEnabled(var.getjm_ranap_dokter());
    }
    
}

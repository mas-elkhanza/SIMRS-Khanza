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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import simrskhanza.DlgCariCaraBayar;

public class KeuanganRekapJmDokter extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private DlgCariCaraBayar carabayar=new DlgCariCaraBayar(null,false);
    private StringBuilder htmlContent;
    private int i=0;
    private String pilihan="";
    private double jm=0,totaljm=0;
    private PreparedStatement ps,psralandokter,psralandokterdrpr,psranapdokter,psranapdokterdrpr,psbiayaoperator1,psbiayaoperator2,psbiayaoperator3,psbiayadokter_anak,
            psbiayadokter_anestesi,psperiksa_lab,psdetaillab,psperiksa_lab2,psdetaillab2,psperiksa_radiologi,psperiksa_radiologi2,psbiaya_dokter_pjanak,
            psbiaya_dokter_umum;
    private ResultSet rs,rsralandokter,rsralandokterdrpr,rsranapdokter,rsranapdokterdrpr,rsbiayaoperator1,rsbiayaoperator2,rsbiayaoperator3,rsbiayadokter_anak,
            rsbiayadokter_anestesi,rsperiksa_lab,rsdetaillab,rsperiksa_radiologi,rsbiaya_dokter_pjanak,rsbiaya_dokter_umum;
    

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public KeuanganRekapJmDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.","Kode Dokter","Nama Dokter","Pendapatan Jasa"};
        tabMode=new DefaultTableModel(null,row){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 4; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(40);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(530);
            }else if(i==3){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        kddokter.setDocument(new batasInput((byte)10).getKata(kddokter));
                
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){
                    kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                    nmdokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                }   
                kddokter.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {dokter.emptTeks();}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        carabayar.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(carabayar.getTable().getSelectedRow()!= -1){
                    KdCaraBayar.setText(carabayar.getTable().getValueAt(carabayar.getTable().getSelectedRow(),1).toString());
                    NmCaraBayar.setText(carabayar.getTable().getValueAt(carabayar.getTable().getSelectedRow(),2).toString());
                }     
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
        ChkInput.setSelected(false);
        isForm();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        KdCaraBayar = new widget.TextBox();
        kddokter = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        chkRalan = new widget.CekBox();
        chkRanap = new widget.CekBox();
        chkOperasi = new widget.CekBox();
        chkLaborat = new widget.CekBox();
        chkRadiologi = new widget.CekBox();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label17 = new widget.Label();
        nmdokter = new widget.TextBox();
        btnDokter = new widget.Button();
        label19 = new widget.Label();
        NmCaraBayar = new widget.TextBox();
        BtnCaraBayarRalanDokter = new widget.Button();

        KdCaraBayar.setEditable(false);
        KdCaraBayar.setName("KdCaraBayar"); // NOI18N
        KdCaraBayar.setPreferredSize(new java.awt.Dimension(50, 23));

        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(70, 23));

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rekap Jasa Medis Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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
        panelisi1.setPreferredSize(new java.awt.Dimension(55, 44));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        chkRalan.setSelected(true);
        chkRalan.setText("Ralan");
        chkRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRalan.setName("chkRalan"); // NOI18N
        chkRalan.setOpaque(false);
        chkRalan.setPreferredSize(new java.awt.Dimension(85, 23));
        chkRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRalanActionPerformed(evt);
            }
        });
        panelisi1.add(chkRalan);

        chkRanap.setSelected(true);
        chkRanap.setText("Ranap");
        chkRanap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRanap.setName("chkRanap"); // NOI18N
        chkRanap.setOpaque(false);
        chkRanap.setPreferredSize(new java.awt.Dimension(85, 23));
        chkRanap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRanapActionPerformed(evt);
            }
        });
        panelisi1.add(chkRanap);

        chkOperasi.setSelected(true);
        chkOperasi.setText("Operasi");
        chkOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkOperasi.setName("chkOperasi"); // NOI18N
        chkOperasi.setOpaque(false);
        chkOperasi.setPreferredSize(new java.awt.Dimension(85, 23));
        chkOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkOperasiActionPerformed(evt);
            }
        });
        panelisi1.add(chkOperasi);

        chkLaborat.setSelected(true);
        chkLaborat.setText("Laboratorium");
        chkLaborat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkLaborat.setName("chkLaborat"); // NOI18N
        chkLaborat.setOpaque(false);
        chkLaborat.setPreferredSize(new java.awt.Dimension(95, 23));
        chkLaborat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkLaboratActionPerformed(evt);
            }
        });
        panelisi1.add(chkLaborat);

        chkRadiologi.setSelected(true);
        chkRadiologi.setText("Radiologi");
        chkRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        chkRadiologi.setName("chkRadiologi"); // NOI18N
        chkRadiologi.setOpaque(false);
        chkRadiologi.setPreferredSize(new java.awt.Dimension(85, 23));
        chkRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkRadiologiActionPerformed(evt);
            }
        });
        panelisi1.add(chkRadiologi);

        jLabel12.setText("Status :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi1.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Piutang Belum Lunas", "Piutang Sudah Lunas", "Sudah Bayar Non Piutang", "Belum Terclosing Kasir" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(175, 23));
        panelisi1.add(cmbStatus);

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
        panelisi1.add(BtnAll);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(28, 23));
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
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
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
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 66));
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
        FormInput.setPreferredSize(new java.awt.Dimension(100, 44));
        FormInput.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label11.setText("Tgl.Tindakan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label11);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        FormInput.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        FormInput.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        FormInput.add(Tgl2);

        label17.setText("Dokter :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(55, 23));
        FormInput.add(label17);

        nmdokter.setEditable(false);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(180, 23));
        FormInput.add(nmdokter);

        btnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokter.setMnemonic('3');
        btnDokter.setToolTipText("Alt+3");
        btnDokter.setName("btnDokter"); // NOI18N
        btnDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        btnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterActionPerformed(evt);
            }
        });
        btnDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterKeyPressed(evt);
            }
        });
        FormInput.add(btnDokter);

        label19.setText("Cara Bayar :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(75, 23));
        FormInput.add(label19);

        NmCaraBayar.setEditable(false);
        NmCaraBayar.setName("NmCaraBayar"); // NOI18N
        NmCaraBayar.setPreferredSize(new java.awt.Dimension(130, 23));
        FormInput.add(NmCaraBayar);

        BtnCaraBayarRalanDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCaraBayarRalanDokter.setMnemonic('3');
        BtnCaraBayarRalanDokter.setToolTipText("Alt+3");
        BtnCaraBayarRalanDokter.setName("BtnCaraBayarRalanDokter"); // NOI18N
        BtnCaraBayarRalanDokter.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCaraBayarRalanDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCaraBayarRalanDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnCaraBayarRalanDokter);

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
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnPrint.requestFocus();
        }else if(tabMode.getRowCount()!=0){
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

                pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih laporan..!","Pilihan Cetak",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Laporan 1 (HTML)","Laporan 2 (WPS)","Laporan 3 (CSV)","Laporan 4 (Jasper)"},"Laporan 1 (HTML)");
                switch (pilihan) {
                    case "Laporan 1 (HTML)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='5%'>No</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='20%'>Kode Dokter</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='55%'>Nama Dokter</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='20%'>Pendapatan Jasa</td>"+
                                "</tr>"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,0)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,1)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,2)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,3)+"</td>"+
                                    "</tr>"
                                ); 
                            }            

                            f = new File("RekapJasaDokter.html");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write("<html>"+
                                        "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                        "<body>"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>REKAP JASA MEDIS DOKTER "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                htmlContent.toString()+
                                            "</table>"+
                                        "</body>"+                   
                                     "</html>"
                            );

                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 2 (WPS)":
                            htmlContent = new StringBuilder();
                           htmlContent.append(                             
                                "<tr class='isi'>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='5%'>No</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='20%'>Kode Dokter</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='55%'>Nama Dokter</td>"+
                                    "<td valign='middle' bgcolor='#FFFAFA' align='center' width='20%'>Pendapatan Jasa</td>"+
                                "</tr>"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "<tr class='isi'>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,0)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,1)+"</td>"+
                                        "<td valign='top'>"+tabMode.getValueAt(i,2)+"</td>"+
                                        "<td valign='top' align='right'>"+tabMode.getValueAt(i,3)+"</td>"+
                                    "</tr>"
                                ); 
                            }             

                            f = new File("RekapJasaDokter.wps");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write("<html>"+
                                        "<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" /></head>"+
                                        "<body>"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                "<tr class='isi2'>"+
                                                    "<td valign='top' align='center'>"+
                                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                                        "<font size='2' face='Tahoma'>REKAP JASA MEDIS DOKTER "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+        
                                                    "</td>"+
                                               "</tr>"+
                                            "</table>"+
                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                htmlContent.toString()+
                                            "</table>"+
                                        "</body>"+                   
                                     "</html>"
                            );

                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break;
                    case "Laporan 3 (CSV)":
                            htmlContent = new StringBuilder();
                            htmlContent.append(                             
                                "\"No.\";\"Kode Dokter\";\"Nama Dokter\";\"Pendapatan Jasa\"\n"
                            ); 
                            for(i=0;i<tabMode.getRowCount();i++){  
                                htmlContent.append(                             
                                    "\""+tabMode.getValueAt(i,0)+"\";\""+tabMode.getValueAt(i,1)+"\";\""+tabMode.getValueAt(i,2)+"\";\""+tabMode.getValueAt(i,3)+"\"\n"
                                ); 
                            }            

                            f = new File("RekapJasaDokter.csv");            
                            bw = new BufferedWriter(new FileWriter(f));            
                            bw.write(htmlContent.toString());

                            bw.close();                         
                            Desktop.getDesktop().browse(f.toURI());
                        break; 
                    case "Laporan 4 (Jasper)":
                            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                            for(int r=0;r<tabMode.getRowCount();r++){  
                                    Sequel.menyimpan("temporary","'"+r+"','"+
                                            tabMode.getValueAt(r,0).toString().replaceAll("'","`") +"','"+
                                            tabMode.getValueAt(r,1).toString().replaceAll("'","`")+"','"+
                                            tabMode.getValueAt(r,2).toString().replaceAll("'","`")+"','"+
                                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(r,3).toString()))+
                                            "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","data");
                            }

                            Map<String, Object> param = new HashMap<>();                 
                            param.put("namars",akses.getnamars());
                            param.put("alamatrs",akses.getalamatrs());
                            param.put("kotars",akses.getkabupatenrs());
                            param.put("propinsirs",akses.getpropinsirs());
                            param.put("kontakrs",akses.getkontakrs());
                            param.put("emailrs",akses.getemailrs());   
                            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                            Valid.MyReportqry("rptRekapJasaMedisDokter.jasper","report","::[ Data Rekap Jasa Medis Dokter ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
                        break; 
                }                 
            } catch (Exception e) {
            }     
            this.setCursor(Cursor.getDefaultCursor());
        }
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
        kddokter.setText("");
        nmdokter.setText("");
        KdCaraBayar.setText("");
        NmCaraBayar.setText("");
        cmbStatus.setSelectedIndex(0);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        prosesCari();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, kddokter, BtnPrint);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    prosesCari();
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, kddokter, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        Tgl1.requestFocus();
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

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kddokter);
    }//GEN-LAST:event_Tgl2KeyPressed

    private void btnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterActionPerformed
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterActionPerformed

    private void btnDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterKeyPressed
        //Valid.pindah(evt,DTPCari2,TCari);
    }//GEN-LAST:event_btnDokterKeyPressed

    private void BtnCaraBayarRalanDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCaraBayarRalanDokterActionPerformed
        carabayar.isCek();
        carabayar.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        carabayar.setLocationRelativeTo(internalFrame1);
        carabayar.setAlwaysOnTop(false);
        carabayar.setVisible(true);
    }//GEN-LAST:event_BtnCaraBayarRalanDokterActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganRekapJmDokter dialog = new KeuanganRekapJmDokter(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCaraBayarRalanDokter;
    private widget.Button BtnCari;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.TextBox KdCaraBayar;
    private widget.TextBox NmCaraBayar;
    private javax.swing.JPanel PanelInput;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnDokter;
    private widget.CekBox chkLaborat;
    private widget.CekBox chkOperasi;
    private widget.CekBox chkRadiologi;
    private widget.CekBox chkRalan;
    private widget.CekBox chkRanap;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel12;
    private widget.TextBox kddokter;
    private widget.Label label11;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.TextBox nmdokter;
    private widget.panelisi panelisi1;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void prosesCari() {
        Valid.tabelKosong(tabMode);
        if(cmbStatus.getSelectedItem().equals("Semua")){
            prosesCariSemua();
        }else if(cmbStatus.getSelectedItem().equals("Piutang Belum Lunas")){
            prosesCariPiutangBelumLunas();
        }else if(cmbStatus.getSelectedItem().equals("Piutang Sudah Lunas")){
            prosesCariPiutangSudahLunas();
        }else if(cmbStatus.getSelectedItem().equals("Sudah Bayar Non Piutang")){
            prosesCariSudahBayarNonPiutang();
        }else if(cmbStatus.getSelectedItem().equals("Belum Terclosing Kasir")){
            prosesCariBelumTerclosing();
        }
    }
    
    private void prosesCariSemua() { 
        try{  
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    jm=0;
                    //rawat jalan dokter   
                    if(chkRalan.isSelected()==true){
                         psralandokter=koneksi.prepareStatement(
                             "select sum(rawat_jl_dr.tarif_tindakandr) as total from reg_periksa inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0");   
                         psralandokterdrpr=koneksi.prepareStatement(
                             "select sum(rawat_jl_drpr.tarif_tindakandr) as total from reg_periksa inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0");   
                         try {
                             psralandokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psralandokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psralandokter.setString(3,rs.getString("kd_dokter"));
                             psralandokter.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsralandokter=psralandokter.executeQuery();

                             psralandokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psralandokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psralandokterdrpr.setString(3,rs.getString("kd_dokter"));
                             psralandokterdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsralandokterdrpr=psralandokterdrpr.executeQuery();
                             
                             while(rsralandokter.next()){
                                 jm=jm+rsralandokter.getDouble("total");
                             }

                             while(rsralandokterdrpr.next()){
                                 jm=jm+rsralandokterdrpr.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ralan : "+e);
                         } finally{
                             if(rsralandokter!=null){
                                 rsralandokter.close();
                             }
                             if(psralandokter!=null){
                                 psralandokter.close();
                             }
                             if(rsralandokterdrpr!=null){
                                 rsralandokterdrpr.close();
                             }
                             if(psralandokterdrpr!=null){
                                 psralandokterdrpr.close();
                             }
                         }
                    }                    

                    //rawat inap dokter   
                    if(chkRanap.isSelected()==true){
                         psranapdokter=koneksi.prepareStatement(
                             "select sum(rawat_inap_dr.tarif_tindakandr) as total from rawat_inap_dr inner join reg_periksa on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0");
                         psranapdokterdrpr=koneksi.prepareStatement(
                             "select sum(rawat_inap_drpr.tarif_tindakandr) as total from rawat_inap_drpr inner join reg_periksa on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0");
                         try {
                             psranapdokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psranapdokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psranapdokter.setString(3,rs.getString("kd_dokter"));
                             psranapdokter.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsranapdokter=psranapdokter.executeQuery();

                             psranapdokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psranapdokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psranapdokterdrpr.setString(3,rs.getString("kd_dokter"));
                             psranapdokterdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsranapdokterdrpr=psranapdokterdrpr.executeQuery();
                             
                             while(rsranapdokter.next()){
                                 jm=jm+rsranapdokter.getDouble("total");
                             }
                             
                             while(rsranapdokterdrpr.next()){
                                 jm=jm+rsranapdokterdrpr.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsranapdokter!=null){
                                 rsranapdokter.close();
                             }
                             if(psranapdokter!=null){
                                 psranapdokter.close();
                             }
                             if(rsranapdokterdrpr!=null){
                                 rsranapdokterdrpr.close();
                             }
                             if(psranapdokterdrpr!=null){
                                 psranapdokterdrpr.close();
                             }
                         }
                    } 

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator1) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator2) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator3) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select sum(operasi.biayadokter_anak) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select sum(operasi.biaya_dokter_umum) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select sum(operasi.biaya_dokter_pjanak) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select sum(operasi.biayadokter_anestesi) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery(); 

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();   

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();  

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();  

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                             
                             while(rsbiayaoperator1.next()){
                                 jm=jm+rsbiayaoperator1.getDouble("total");
                             }

                             while(rsbiayaoperator2.next()){
                                 jm=jm+rsbiayaoperator2.getDouble("total");
                             }
                             
                             while(rsbiayaoperator3.next()){
                                 jm=jm+rsbiayaoperator3.getDouble("total");
                             }
                             
                             while(rsbiayadokter_anak.next()){
                                 jm=jm+rsbiayadokter_anak.getDouble("total");
                             }
                             
                             while(rsbiayadokter_anestesi.next()){
                                 jm=jm+rsbiayadokter_anestesi.getDouble("total");
                             }

                             while(rsbiaya_dokter_pjanak.next()){
                                 jm=jm+rsbiaya_dokter_pjanak.getDouble("total");
                             }
                             
                             while(rsbiaya_dokter_umum.next()){
                                 jm=jm+rsbiaya_dokter_umum.getDouble("total");
                             }
                             //rsbiaya_dokter_umum.close();
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }                            
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }                            
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                         psperiksa_lab=koneksi.prepareStatement(
                             "select sum(periksa_lab.tarif_tindakan_dokter) as total from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_tindakan_dokter>0"); 
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             while(rsperiksa_lab.next()){    
                                 jm=jm+rsperiksa_lab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Periksa Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         psdetaillab=koneksi.prepareStatement(
                             "select sum(detail_periksa_lab.bagian_dokter) as total from detail_periksa_lab inner join periksa_lab "+
                             "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                             "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             while(rsdetaillab.next()){
                                 jm=jm+rsdetaillab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //perujuk Lab
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select sum(periksa_lab.tarif_perujuk) as total from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_perujuk>0 "); 
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             while(rsperiksa_lab.next()){
                                 jm=jm+rsperiksa_lab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select sum(detail_periksa_lab.bagian_perujuk) as total from detail_periksa_lab inner join periksa_lab "+
                             "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                             "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             while(rsdetaillab.next()){
                                 jm=jm+rsdetaillab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }

                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement(
                             "select sum(periksa_radiologi.tarif_tindakan_dokter) as total from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.kd_dokter=? "+
                             " and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_tindakan_dokter>0 "); 
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 jm=jm+rsperiksa_radiologi.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //perujuk radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement(
                             "select sum(periksa_radiologi.tarif_perujuk) as total from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.dokter_perujuk=? "+
                             " and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? >0"); 
                         try{
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 jm=jm+rsperiksa_radiologi.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi Perujuk : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }    

                    totaljm=totaljm+jm;
                    if(jm>0){
                        tabMode.addRow(new Object[]{""+i,rs.getString("kd_dokter"),rs.getString("nm_dokter"),Math.round(jm)});  
                        i++;
                    }    
                        
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Perujuk Radiologi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }           

            if(totaljm>0){
                tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","",Math.round(totaljm)});
            }            
         }catch(Exception e){
            System.out.println("Catatan  "+e);
         }
    }
    
    public void isCek(){
       // BtnPrint.setEnabled(var.getbulanan_dokter());
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,65));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }

    private void prosesCariPiutangBelumLunas() {
        try{  
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    jm=0;
                    //rawat jalan dokter   
                    if(chkRalan.isSelected()==true){
                         psralandokter=koneksi.prepareStatement(
                             "select sum(rawat_jl_dr.tarif_tindakandr) as total from reg_periksa inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0");   
                         psralandokterdrpr=koneksi.prepareStatement(
                             "select sum(rawat_jl_drpr.tarif_tindakandr) as total from reg_periksa inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0");   
                         try {
                             psralandokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psralandokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psralandokter.setString(3,rs.getString("kd_dokter"));
                             psralandokter.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsralandokter=psralandokter.executeQuery();

                             psralandokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psralandokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psralandokterdrpr.setString(3,rs.getString("kd_dokter"));
                             psralandokterdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsralandokterdrpr=psralandokterdrpr.executeQuery();
                             
                             while(rsralandokter.next()){
                                 jm=jm+rsralandokter.getDouble("total");
                             }

                             while(rsralandokterdrpr.next()){
                                 jm=jm+rsralandokterdrpr.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ralan : "+e);
                         } finally{
                             if(rsralandokter!=null){
                                 rsralandokter.close();
                             }
                             if(psralandokter!=null){
                                 psralandokter.close();
                             }
                             if(rsralandokterdrpr!=null){
                                 rsralandokterdrpr.close();
                             }
                             if(psralandokterdrpr!=null){
                                 psralandokterdrpr.close();
                             }
                         }
                    }                    

                    //rawat inap dokter   
                    if(chkRanap.isSelected()==true){
                         psranapdokter=koneksi.prepareStatement(
                             "select sum(rawat_inap_dr.tarif_tindakandr) as total from rawat_inap_dr inner join reg_periksa on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0");
                         psranapdokterdrpr=koneksi.prepareStatement(
                             "select sum(rawat_inap_drpr.tarif_tindakandr) as total from rawat_inap_drpr inner join reg_periksa on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0");
                         try {
                             psranapdokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psranapdokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psranapdokter.setString(3,rs.getString("kd_dokter"));
                             psranapdokter.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsranapdokter=psranapdokter.executeQuery();

                             psranapdokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psranapdokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psranapdokterdrpr.setString(3,rs.getString("kd_dokter"));
                             psranapdokterdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsranapdokterdrpr=psranapdokterdrpr.executeQuery();
                             
                             while(rsranapdokter.next()){
                                 jm=jm+rsranapdokter.getDouble("total");
                             }
                             
                             while(rsranapdokterdrpr.next()){
                                 jm=jm+rsranapdokterdrpr.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsranapdokter!=null){
                                 rsranapdokter.close();
                             }
                             if(psranapdokter!=null){
                                 psranapdokter.close();
                             }
                             if(rsranapdokterdrpr!=null){
                                 rsranapdokterdrpr.close();
                             }
                             if(psranapdokterdrpr!=null){
                                 psranapdokterdrpr.close();
                             }
                         }
                    } 

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator1) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator2) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator3) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select sum(operasi.biayadokter_anak) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select sum(operasi.biaya_dokter_umum) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select sum(operasi.biaya_dokter_pjanak) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select sum(operasi.biayadokter_anestesi) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery(); 

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();   

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();  

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();  

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                             
                             while(rsbiayaoperator1.next()){
                                 jm=jm+rsbiayaoperator1.getDouble("total");
                             }

                             while(rsbiayaoperator2.next()){
                                 jm=jm+rsbiayaoperator2.getDouble("total");
                             }
                             
                             while(rsbiayaoperator3.next()){
                                 jm=jm+rsbiayaoperator3.getDouble("total");
                             }
                             
                             while(rsbiayadokter_anak.next()){
                                 jm=jm+rsbiayadokter_anak.getDouble("total");
                             }
                             
                             while(rsbiayadokter_anestesi.next()){
                                 jm=jm+rsbiayadokter_anestesi.getDouble("total");
                             }

                             while(rsbiaya_dokter_pjanak.next()){
                                 jm=jm+rsbiaya_dokter_pjanak.getDouble("total");
                             }
                             
                             while(rsbiaya_dokter_umum.next()){
                                 jm=jm+rsbiaya_dokter_umum.getDouble("total");
                             }
                             //rsbiaya_dokter_umum.close();
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }                            
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }                            
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                         psperiksa_lab=koneksi.prepareStatement(
                             "select sum(periksa_lab.tarif_tindakan_dokter) as total from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_tindakan_dokter>0"); 
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             while(rsperiksa_lab.next()){    
                                 jm=jm+rsperiksa_lab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Periksa Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         psdetaillab=koneksi.prepareStatement(
                             "select sum(detail_periksa_lab.bagian_dokter) as total from detail_periksa_lab inner join periksa_lab "+
                             "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                             "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and "+
                             "detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             while(rsdetaillab.next()){
                                 jm=jm+rsdetaillab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //perujuk Lab
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select sum(periksa_lab.tarif_perujuk) as total from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_perujuk>0 "); 
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             while(rsperiksa_lab.next()){
                                 jm=jm+rsperiksa_lab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select sum(detail_periksa_lab.bagian_perujuk) as total from detail_periksa_lab inner join periksa_lab "+
                             "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                             "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and "+
                             "detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             while(rsdetaillab.next()){
                                 jm=jm+rsdetaillab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }

                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement(
                             "select sum(periksa_radiologi.tarif_tindakan_dokter) as total from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_tindakan_dokter>0 "); 
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 jm=jm+rsperiksa_radiologi.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //perujuk radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement(
                             "select sum(periksa_radiologi.tarif_perujuk) as total from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Belum Lunas' and periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.dokter_perujuk=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? >0"); 
                         try{
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 jm=jm+rsperiksa_radiologi.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi Perujuk : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }    

                    totaljm=totaljm+jm;
                    if(jm>0){
                        tabMode.addRow(new Object[]{""+i,rs.getString("kd_dokter"),rs.getString("nm_dokter"),Math.round(jm)});  
                        i++;
                    }    
                        
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Perujuk Radiologi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }           

            if(totaljm>0){
                tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","",Math.round(totaljm)});
            }            
         }catch(Exception e){
            System.out.println("Catatan  "+e);
         }
    }

    private void prosesCariPiutangSudahLunas() {
        try{  
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    jm=0;
                    //rawat jalan dokter   
                    if(chkRalan.isSelected()==true){
                         psralandokter=koneksi.prepareStatement(
                             "select sum(rawat_jl_dr.tarif_tindakandr) as total from reg_periksa inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0");   
                         psralandokterdrpr=koneksi.prepareStatement(
                             "select sum(rawat_jl_drpr.tarif_tindakandr) as total from reg_periksa inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0");   
                         try {
                             psralandokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psralandokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psralandokter.setString(3,rs.getString("kd_dokter"));
                             psralandokter.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsralandokter=psralandokter.executeQuery();

                             psralandokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psralandokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psralandokterdrpr.setString(3,rs.getString("kd_dokter"));
                             psralandokterdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsralandokterdrpr=psralandokterdrpr.executeQuery();
                             
                             while(rsralandokter.next()){
                                 jm=jm+rsralandokter.getDouble("total");
                             }

                             while(rsralandokterdrpr.next()){
                                 jm=jm+rsralandokterdrpr.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ralan : "+e);
                         } finally{
                             if(rsralandokter!=null){
                                 rsralandokter.close();
                             }
                             if(psralandokter!=null){
                                 psralandokter.close();
                             }
                             if(rsralandokterdrpr!=null){
                                 rsralandokterdrpr.close();
                             }
                             if(psralandokterdrpr!=null){
                                 psralandokterdrpr.close();
                             }
                         }
                    }                    

                    //rawat inap dokter   
                    if(chkRanap.isSelected()==true){
                         psranapdokter=koneksi.prepareStatement(
                             "select sum(rawat_inap_dr.tarif_tindakandr) as total from rawat_inap_dr inner join reg_periksa on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0");
                         psranapdokterdrpr=koneksi.prepareStatement(
                             "select sum(rawat_inap_drpr.tarif_tindakandr) as total from rawat_inap_drpr inner join reg_periksa on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0");
                         try {
                             psranapdokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psranapdokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psranapdokter.setString(3,rs.getString("kd_dokter"));
                             psranapdokter.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsranapdokter=psranapdokter.executeQuery();

                             psranapdokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psranapdokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psranapdokterdrpr.setString(3,rs.getString("kd_dokter"));
                             psranapdokterdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsranapdokterdrpr=psranapdokterdrpr.executeQuery();
                             
                             while(rsranapdokter.next()){
                                 jm=jm+rsranapdokter.getDouble("total");
                             }
                             
                             while(rsranapdokterdrpr.next()){
                                 jm=jm+rsranapdokterdrpr.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsranapdokter!=null){
                                 rsranapdokter.close();
                             }
                             if(psranapdokter!=null){
                                 psranapdokter.close();
                             }
                             if(rsranapdokterdrpr!=null){
                                 rsranapdokterdrpr.close();
                             }
                             if(psranapdokterdrpr!=null){
                                 psranapdokterdrpr.close();
                             }
                         }
                    } 

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator1) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator2) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator3) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select sum(operasi.biayadokter_anak) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select sum(operasi.biaya_dokter_umum) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select sum(operasi.biaya_dokter_pjanak) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select sum(operasi.biayadokter_anestesi) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery(); 

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();   

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();  

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();  

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                             
                             while(rsbiayaoperator1.next()){
                                 jm=jm+rsbiayaoperator1.getDouble("total");
                             }

                             while(rsbiayaoperator2.next()){
                                 jm=jm+rsbiayaoperator2.getDouble("total");
                             }
                             
                             while(rsbiayaoperator3.next()){
                                 jm=jm+rsbiayaoperator3.getDouble("total");
                             }
                             
                             while(rsbiayadokter_anak.next()){
                                 jm=jm+rsbiayadokter_anak.getDouble("total");
                             }
                             
                             while(rsbiayadokter_anestesi.next()){
                                 jm=jm+rsbiayadokter_anestesi.getDouble("total");
                             }

                             while(rsbiaya_dokter_pjanak.next()){
                                 jm=jm+rsbiaya_dokter_pjanak.getDouble("total");
                             }
                             
                             while(rsbiaya_dokter_umum.next()){
                                 jm=jm+rsbiaya_dokter_umum.getDouble("total");
                             }
                             //rsbiaya_dokter_umum.close();
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }                            
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }                            
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                         psperiksa_lab=koneksi.prepareStatement(
                             "select sum(periksa_lab.tarif_tindakan_dokter) as total from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_tindakan_dokter>0"); 
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             while(rsperiksa_lab.next()){    
                                 jm=jm+rsperiksa_lab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Periksa Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         psdetaillab=koneksi.prepareStatement(
                             "select sum(detail_periksa_lab.bagian_dokter) as total from detail_periksa_lab inner join periksa_lab "+
                             "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                             "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and "+
                             "detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             while(rsdetaillab.next()){
                                 jm=jm+rsdetaillab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //perujuk Lab
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select sum(periksa_lab.tarif_perujuk) as total from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_perujuk>0 "); 
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             while(rsperiksa_lab.next()){
                                 jm=jm+rsperiksa_lab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select sum(detail_periksa_lab.bagian_perujuk) as total from detail_periksa_lab inner join periksa_lab "+
                             "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                             "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and "+
                             "detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             while(rsdetaillab.next()){
                                 jm=jm+rsdetaillab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }

                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement(
                             "select sum(periksa_radiologi.tarif_tindakan_dokter) as total from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_tindakan_dokter>0 "); 
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 jm=jm+rsperiksa_radiologi.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //perujuk radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement(
                             "select sum(periksa_radiologi.tarif_perujuk) as total from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj inner join piutang_pasien on reg_periksa.no_rawat=piutang_pasien.no_rawat "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and piutang_pasien.status='Lunas' and periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.dokter_perujuk=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? >0"); 
                         try{
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 jm=jm+rsperiksa_radiologi.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi Perujuk : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }    

                    totaljm=totaljm+jm;
                    if(jm>0){
                        tabMode.addRow(new Object[]{""+i,rs.getString("kd_dokter"),rs.getString("nm_dokter"),Math.round(jm)});  
                        i++;
                    }    
                        
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Perujuk Radiologi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }           

            if(totaljm>0){
                tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","",Math.round(totaljm)});
            }            
         }catch(Exception e){
            System.out.println("Catatan  "+e);
         }
    }

    private void prosesCariBelumTerclosing() {
        try{  
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    jm=0;
                    //rawat jalan dokter   
                    if(chkRalan.isSelected()==true){
                         psralandokter=koneksi.prepareStatement(
                             "select sum(rawat_jl_dr.tarif_tindakandr) as total from reg_periksa inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0");   
                         psralandokterdrpr=koneksi.prepareStatement(
                             "select sum(rawat_jl_drpr.tarif_tindakandr) as total from reg_periksa inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0");   
                         try {
                             psralandokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psralandokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psralandokter.setString(3,rs.getString("kd_dokter"));
                             psralandokter.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsralandokter=psralandokter.executeQuery();

                             psralandokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psralandokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psralandokterdrpr.setString(3,rs.getString("kd_dokter"));
                             psralandokterdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsralandokterdrpr=psralandokterdrpr.executeQuery();
                             
                             while(rsralandokter.next()){
                                 jm=jm+rsralandokter.getDouble("total");
                             }

                             while(rsralandokterdrpr.next()){
                                 jm=jm+rsralandokterdrpr.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ralan : "+e);
                         } finally{
                             if(rsralandokter!=null){
                                 rsralandokter.close();
                             }
                             if(psralandokter!=null){
                                 psralandokter.close();
                             }
                             if(rsralandokterdrpr!=null){
                                 rsralandokterdrpr.close();
                             }
                             if(psralandokterdrpr!=null){
                                 psralandokterdrpr.close();
                             }
                         }
                    }                    

                    //rawat inap dokter   
                    if(chkRanap.isSelected()==true){
                         psranapdokter=koneksi.prepareStatement(
                             "select sum(rawat_inap_dr.tarif_tindakandr) as total from rawat_inap_dr inner join reg_periksa on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0");
                         psranapdokterdrpr=koneksi.prepareStatement(
                             "select sum(rawat_inap_drpr.tarif_tindakandr) as total from rawat_inap_drpr inner join reg_periksa on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0");
                         try {
                             psranapdokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psranapdokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psranapdokter.setString(3,rs.getString("kd_dokter"));
                             psranapdokter.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsranapdokter=psranapdokter.executeQuery();

                             psranapdokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psranapdokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psranapdokterdrpr.setString(3,rs.getString("kd_dokter"));
                             psranapdokterdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsranapdokterdrpr=psranapdokterdrpr.executeQuery();
                             
                             while(rsranapdokter.next()){
                                 jm=jm+rsranapdokter.getDouble("total");
                             }
                             
                             while(rsranapdokterdrpr.next()){
                                 jm=jm+rsranapdokterdrpr.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsranapdokter!=null){
                                 rsranapdokter.close();
                             }
                             if(psranapdokter!=null){
                                 psranapdokter.close();
                             }
                             if(rsranapdokterdrpr!=null){
                                 rsranapdokterdrpr.close();
                             }
                             if(psranapdokterdrpr!=null){
                                 psranapdokterdrpr.close();
                             }
                         }
                    } 

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator1) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator2) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator3) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select sum(operasi.biayadokter_anak) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select sum(operasi.biaya_dokter_umum) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select sum(operasi.biaya_dokter_pjanak) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select sum(operasi.biayadokter_anestesi) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery(); 

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();   

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();  

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();  

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                             
                             while(rsbiayaoperator1.next()){
                                 jm=jm+rsbiayaoperator1.getDouble("total");
                             }

                             while(rsbiayaoperator2.next()){
                                 jm=jm+rsbiayaoperator2.getDouble("total");
                             }
                             
                             while(rsbiayaoperator3.next()){
                                 jm=jm+rsbiayaoperator3.getDouble("total");
                             }
                             
                             while(rsbiayadokter_anak.next()){
                                 jm=jm+rsbiayadokter_anak.getDouble("total");
                             }
                             
                             while(rsbiayadokter_anestesi.next()){
                                 jm=jm+rsbiayadokter_anestesi.getDouble("total");
                             }

                             while(rsbiaya_dokter_pjanak.next()){
                                 jm=jm+rsbiaya_dokter_pjanak.getDouble("total");
                             }
                             
                             while(rsbiaya_dokter_umum.next()){
                                 jm=jm+rsbiaya_dokter_umum.getDouble("total");
                             }
                             //rsbiaya_dokter_umum.close();
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }                            
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }                            
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                         psperiksa_lab=koneksi.prepareStatement(
                             "select sum(periksa_lab.tarif_tindakan_dokter) as total from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_tindakan_dokter>0"); 
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             while(rsperiksa_lab.next()){    
                                 jm=jm+rsperiksa_lab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Periksa Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         psdetaillab=koneksi.prepareStatement(
                             "select sum(detail_periksa_lab.bagian_dokter) as total from detail_periksa_lab inner join periksa_lab "+
                             "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                             "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             while(rsdetaillab.next()){
                                 jm=jm+rsdetaillab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //perujuk Lab
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select sum(periksa_lab.tarif_perujuk) as total from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_perujuk>0 "); 
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             while(rsperiksa_lab.next()){
                                 jm=jm+rsperiksa_lab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select sum(detail_periksa_lab.bagian_perujuk) as total from detail_periksa_lab inner join periksa_lab "+
                             "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                             "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Belum Bayar' and detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             while(rsdetaillab.next()){
                                 jm=jm+rsdetaillab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }

                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement(
                             "select sum(periksa_radiologi.tarif_tindakan_dokter) as total from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.kd_dokter=? "+
                             " and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_tindakan_dokter>0 "); 
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 jm=jm+rsperiksa_radiologi.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //perujuk radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement(
                             "select sum(periksa_radiologi.tarif_perujuk) as total from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Belum Bayar' and periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.dokter_perujuk=? "+
                             " and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? >0"); 
                         try{
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 jm=jm+rsperiksa_radiologi.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi Perujuk : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }    

                    totaljm=totaljm+jm;
                    if(jm>0){
                        tabMode.addRow(new Object[]{""+i,rs.getString("kd_dokter"),rs.getString("nm_dokter"),Math.round(jm)});  
                        i++;
                    }    
                        
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Perujuk Radiologi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }           

            if(totaljm>0){
                tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","",Math.round(totaljm)});
            }            
         }catch(Exception e){
            System.out.println("Catatan  "+e);
         }
    }

    private void prosesCariSudahBayarNonPiutang() {
        try{  
            ps=koneksi.prepareStatement("select kd_dokter,nm_dokter from dokter where status='1' and concat(kd_dokter,nm_dokter) like ? order by nm_dokter");
            try {
                 ps.setString(1,"%"+kddokter.getText()+nmdokter.getText()+"%");
                 rs=ps.executeQuery();
                 i=1;
                 totaljm=0;
                 while(rs.next()){
                    jm=0;
                    //rawat jalan dokter   
                    if(chkRalan.isSelected()==true){
                         psralandokter=koneksi.prepareStatement(
                             "select sum(rawat_jl_dr.tarif_tindakandr) as total from reg_periksa inner join rawat_jl_dr on rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and reg_periksa.tgl_registrasi between ? and ? and rawat_jl_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_dr.tarif_tindakandr>0");   
                         psralandokterdrpr=koneksi.prepareStatement(
                             "select sum(rawat_jl_drpr.tarif_tindakandr) as total from reg_periksa inner join rawat_jl_drpr on rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and reg_periksa.tgl_registrasi between ? and ? and rawat_jl_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_jl_drpr.tarif_tindakandr>0");   
                         try {
                             psralandokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psralandokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psralandokter.setString(3,rs.getString("kd_dokter"));
                             psralandokter.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsralandokter=psralandokter.executeQuery();

                             psralandokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psralandokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psralandokterdrpr.setString(3,rs.getString("kd_dokter"));
                             psralandokterdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsralandokterdrpr=psralandokterdrpr.executeQuery();
                             
                             while(rsralandokter.next()){
                                 jm=jm+rsralandokter.getDouble("total");
                             }

                             while(rsralandokterdrpr.next()){
                                 jm=jm+rsralandokterdrpr.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ralan : "+e);
                         } finally{
                             if(rsralandokter!=null){
                                 rsralandokter.close();
                             }
                             if(psralandokter!=null){
                                 psralandokter.close();
                             }
                             if(rsralandokterdrpr!=null){
                                 rsralandokterdrpr.close();
                             }
                             if(psralandokterdrpr!=null){
                                 psralandokterdrpr.close();
                             }
                         }
                    }                    

                    //rawat inap dokter   
                    if(chkRanap.isSelected()==true){
                         psranapdokter=koneksi.prepareStatement(
                             "select sum(rawat_inap_dr.tarif_tindakandr) as total from rawat_inap_dr inner join reg_periksa on rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and rawat_inap_dr.tgl_perawatan between ? and ? and rawat_inap_dr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_dr.tarif_tindakandr>0");
                         psranapdokterdrpr=koneksi.prepareStatement(
                             "select sum(rawat_inap_drpr.tarif_tindakandr) as total from rawat_inap_drpr inner join reg_periksa on rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and rawat_inap_drpr.tgl_perawatan between ? and ? and rawat_inap_drpr.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and rawat_inap_drpr.tarif_tindakandr>0");
                         try {
                             psranapdokter.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psranapdokter.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psranapdokter.setString(3,rs.getString("kd_dokter"));
                             psranapdokter.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsranapdokter=psranapdokter.executeQuery();

                             psranapdokterdrpr.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psranapdokterdrpr.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psranapdokterdrpr.setString(3,rs.getString("kd_dokter"));
                             psranapdokterdrpr.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsranapdokterdrpr=psranapdokterdrpr.executeQuery();
                             
                             while(rsranapdokter.next()){
                                 jm=jm+rsranapdokter.getDouble("total");
                             }
                             
                             while(rsranapdokterdrpr.next()){
                                 jm=jm+rsranapdokterdrpr.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Ranap : "+e);
                         } finally{
                             if(rsranapdokter!=null){
                                 rsranapdokter.close();
                             }
                             if(psranapdokter!=null){
                                 psranapdokter.close();
                             }
                             if(rsranapdokterdrpr!=null){
                                 rsranapdokterdrpr.close();
                             }
                             if(psranapdokterdrpr!=null){
                                 psranapdokterdrpr.close();
                             }
                         }
                    } 

                    if(chkOperasi.isSelected()==true){
                         psbiayaoperator1=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator1) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.operator1=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator1>0");
                         psbiayaoperator2=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator2) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.operator2=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator2>0");
                         psbiayaoperator3=koneksi.prepareStatement(
                             "select sum(operasi.biayaoperator3) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.operator3=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayaoperator3>0");
                         psbiayadokter_anak=koneksi.prepareStatement(
                             "select sum(operasi.biayadokter_anak) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_anak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anak>0");
                         psbiaya_dokter_umum=koneksi.prepareStatement(
                             "select sum(operasi.biaya_dokter_umum) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_umum=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_umum>0");
                         psbiaya_dokter_pjanak=koneksi.prepareStatement(
                             "select sum(operasi.biaya_dokter_pjanak) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_pjanak=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biaya_dokter_pjanak>0");
                         psbiayadokter_anestesi=koneksi.prepareStatement(
                             "select sum(operasi.biayadokter_anestesi) as total from operasi inner join reg_periksa on operasi.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and operasi.tgl_operasi between ? and ? and operasi.dokter_anestesi=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and operasi.biayadokter_anestesi>0");
                         try {
                             psbiayaoperator1.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator1.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator1.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator1.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator1=psbiayaoperator1.executeQuery();

                             psbiayaoperator2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator2.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator2=psbiayaoperator2.executeQuery();

                             psbiayaoperator3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayaoperator3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayaoperator3.setString(3,rs.getString("kd_dokter"));
                             psbiayaoperator3.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayaoperator3=psbiayaoperator3.executeQuery(); 

                             psbiayadokter_anak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayadokter_anak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayadokter_anak.setString(3,rs.getString("kd_dokter"));
                             psbiayadokter_anak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anak=psbiayadokter_anak.executeQuery();   

                             psbiaya_dokter_pjanak.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiaya_dokter_pjanak.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiaya_dokter_pjanak.setString(3,rs.getString("kd_dokter"));
                             psbiaya_dokter_pjanak.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_pjanak=psbiaya_dokter_pjanak.executeQuery();  

                             psbiaya_dokter_umum.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiaya_dokter_umum.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiaya_dokter_umum.setString(3,rs.getString("kd_dokter"));
                             psbiaya_dokter_umum.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiaya_dokter_umum=psbiaya_dokter_umum.executeQuery();  

                             psbiayadokter_anestesi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                             psbiayadokter_anestesi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                             psbiayadokter_anestesi.setString(3,rs.getString("kd_dokter"));
                             psbiayadokter_anestesi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsbiayadokter_anestesi=psbiayadokter_anestesi.executeQuery();
                             
                             while(rsbiayaoperator1.next()){
                                 jm=jm+rsbiayaoperator1.getDouble("total");
                             }

                             while(rsbiayaoperator2.next()){
                                 jm=jm+rsbiayaoperator2.getDouble("total");
                             }
                             
                             while(rsbiayaoperator3.next()){
                                 jm=jm+rsbiayaoperator3.getDouble("total");
                             }
                             
                             while(rsbiayadokter_anak.next()){
                                 jm=jm+rsbiayadokter_anak.getDouble("total");
                             }
                             
                             while(rsbiayadokter_anestesi.next()){
                                 jm=jm+rsbiayadokter_anestesi.getDouble("total");
                             }

                             while(rsbiaya_dokter_pjanak.next()){
                                 jm=jm+rsbiaya_dokter_pjanak.getDouble("total");
                             }
                             
                             while(rsbiaya_dokter_umum.next()){
                                 jm=jm+rsbiaya_dokter_umum.getDouble("total");
                             }
                             //rsbiaya_dokter_umum.close();
                         } catch (Exception e) {
                             System.out.println("Notifikasi Operasi : "+e);
                         } finally{
                             if(rsbiayaoperator1!=null){
                                 rsbiayaoperator1.close();
                             }
                             if(psbiayaoperator1!=null){
                                 psbiayaoperator1.close();
                             }
                             if(rsbiayaoperator2!=null){
                                 rsbiayaoperator2.close();
                             }
                             if(psbiayaoperator2!=null){
                                 psbiayaoperator2.close();
                             }
                             if(rsbiayaoperator3!=null){
                                 rsbiayaoperator3.close();
                             }
                             if(psbiayaoperator3!=null){
                                 psbiayaoperator3.close();
                             }                            
                             if(rsbiayadokter_anak!=null){
                                 rsbiayadokter_anak.close();
                             }
                             if(psbiayadokter_anak!=null){
                                 psbiayadokter_anak.close();
                             }                            
                             if(rsbiaya_dokter_pjanak!=null){
                                 rsbiaya_dokter_pjanak.close();
                             }
                             if(psbiaya_dokter_pjanak!=null){
                                 psbiaya_dokter_pjanak.close();
                             }
                             if(rsbiaya_dokter_umum!=null){
                                 rsbiaya_dokter_umum.close();
                             }
                             if(psbiaya_dokter_umum!=null){
                                 psbiaya_dokter_umum.close();
                             }
                             if(rsbiayadokter_anestesi!=null){
                                 rsbiayadokter_anestesi.close();
                             }
                             if(psbiayadokter_anestesi!=null){
                                 psbiayadokter_anestesi.close();
                             }
                         }   
                    }

                    if(chkLaborat.isSelected()==true){
                         psperiksa_lab=koneksi.prepareStatement(
                             "select sum(periksa_lab.tarif_tindakan_dokter) as total from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_tindakan_dokter>0"); 
                         try {
                             psperiksa_lab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_lab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_lab.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab.executeQuery();
                             while(rsperiksa_lab.next()){    
                                 jm=jm+rsperiksa_lab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Periksa Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab!=null){
                                 psperiksa_lab.close();
                             }
                         }

                         psdetaillab=koneksi.prepareStatement(
                             "select sum(detail_periksa_lab.bagian_dokter) as total from detail_periksa_lab inner join periksa_lab "+
                             "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                             "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.kd_dokter=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_dokter>0");
                         try {
                             psdetaillab.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psdetaillab.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psdetaillab.setString(3,rs.getString("kd_dokter"));
                             psdetaillab.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab.executeQuery();
                             while(rsdetaillab.next()){
                                 jm=jm+rsdetaillab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Lab : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab!=null){
                                 psdetaillab.close();
                             }
                         }

                         //perujuk Lab
                         psperiksa_lab2=koneksi.prepareStatement(
                             "select sum(periksa_lab.tarif_perujuk) as total from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                             "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk=? "+
                             "and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_lab.tarif_perujuk>0 "); 
                         try {
                             psperiksa_lab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_lab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_lab2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_lab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_lab=psperiksa_lab2.executeQuery();
                             while(rsperiksa_lab.next()){
                                 jm=jm+rsperiksa_lab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Perujuk Lab : "+e);
                         } finally{
                             if(rsperiksa_lab!=null){
                                 rsperiksa_lab.close();
                             }
                             if(psperiksa_lab2!=null){
                                 psperiksa_lab2.close();
                             }
                         }

                         psdetaillab2=koneksi.prepareStatement(
                             "select sum(detail_periksa_lab.bagian_perujuk) as total from detail_periksa_lab inner join periksa_lab "+
                             "on periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw "+
                             "and periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam "+
                             "inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                             "where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and detail_periksa_lab.tgl_periksa between ? and ? and periksa_lab.dokter_perujuk=? and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? "+
                             "and detail_periksa_lab.bagian_perujuk>0");
                         try {
                             psdetaillab2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psdetaillab2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psdetaillab2.setString(3,rs.getString("kd_dokter"));
                             psdetaillab2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsdetaillab=psdetaillab2.executeQuery();
                             while(rsdetaillab.next()){
                                 jm=jm+rsdetaillab.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Detail Perujuk : "+e);
                         } finally{
                             if(rsdetaillab!=null){
                                 rsdetaillab.close();
                             }
                             if(psdetaillab2!=null){
                                 psdetaillab2.close();
                             }
                         }
                    }

                    if(chkRadiologi.isSelected()==true){
                        //periksa radiologi
                         psperiksa_radiologi=koneksi.prepareStatement(
                             "select sum(periksa_radiologi.tarif_tindakan_dokter) as total from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.kd_dokter=? "+
                             " and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? and periksa_radiologi.tarif_tindakan_dokter>0 "); 
                         try {
                             psperiksa_radiologi.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_radiologi.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_radiologi.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 jm=jm+rsperiksa_radiologi.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi!=null){
                                 psperiksa_radiologi.close();
                             }
                         }

                         //perujuk radiologi
                         psperiksa_radiologi2=koneksi.prepareStatement(
                             "select sum(periksa_radiologi.tarif_perujuk) as total from periksa_radiologi inner join reg_periksa on periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                             " inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where reg_periksa.status_bayar='Sudah Bayar' and reg_periksa.no_rawat not in (select no_rawat from piutang_pasien) and periksa_radiologi.tgl_periksa between ? and ? and periksa_radiologi.dokter_perujuk=? "+
                             " and concat(reg_periksa.kd_pj,penjab.png_jawab) like ? >0"); 
                         try{
                             psperiksa_radiologi2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                             psperiksa_radiologi2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                             psperiksa_radiologi2.setString(3,rs.getString("kd_dokter"));
                             psperiksa_radiologi2.setString(4,"%"+KdCaraBayar.getText()+NmCaraBayar.getText()+"%");
                             rsperiksa_radiologi=psperiksa_radiologi2.executeQuery();
                             while(rsperiksa_radiologi.next()){
                                 jm=jm+rsperiksa_radiologi.getDouble("total");
                             }
                         } catch (Exception e) {
                             System.out.println("Notifikasi Radiologi Perujuk : "+e);
                         } finally{
                             if(rsperiksa_radiologi!=null){
                                 rsperiksa_radiologi.close();
                             }
                             if(psperiksa_radiologi2!=null){
                                 psperiksa_radiologi2.close();
                             }
                         }
                    }    

                    totaljm=totaljm+jm;
                    if(jm>0){
                        tabMode.addRow(new Object[]{""+i,rs.getString("kd_dokter"),rs.getString("nm_dokter"),Math.round(jm)});  
                        i++;
                    }    
                        
                 } 
            } catch (Exception e) {
                System.out.println("Notifikasi Perujuk Radiologi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }           

            if(totaljm>0){
                tabMode.addRow(new Object[]{">> ","Total Jasa Medis :","",Math.round(totaljm)});
            }            
         }catch(Exception e){
            System.out.println("Catatan  "+e);
         }
    }
}

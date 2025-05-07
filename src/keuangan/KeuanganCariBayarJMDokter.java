package keuangan;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;

public class KeuanganCariBayarJMDokter extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public DlgCariDokter dokter=new DlgCariDokter(null,false);
    private PreparedStatement ps,ps2,psrekening;
    private ResultSet rs,rs2,rsrekening;
    private String notagihan="",tanggal="",petugas="";
    private double totaltagihan=0;
    private boolean sukses=true,rincian=false;  
    public Jurnal jur=new Jurnal();
    public File file;
    public FileWriter fileWriter;
    public String iyem,Utang_Jasa_Medik_Dokter_Tindakan_Ralan="",Utang_Jasa_Medik_Dokter_Tindakan_Ranap="",Utang_Jasa_Medik_Dokter_Laborat_Ralan="",Utang_Jasa_Medik_Dokter_Laborat_Ranap="",
            Utang_Jasa_Medik_Dokter_Radiologi_Ralan="",Utang_Jasa_Medik_Dokter_Radiologi_Ranap="",Utang_Jasa_Medik_Dokter_Operasi_Ralan="",Utang_Jasa_Medik_Dokter_Operasi_Ranap="",
            koderekening="",Host_to_Host_Bank_Mandiri="",Akun_Biaya_Mandiri="",kodemcm="",norekening="";
    public ObjectMapper mapper = new ObjectMapper();
    public JsonNode root;
    public JsonNode response;
    public FileReader myObj;
    private int i=0;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public KeuanganCariBayarJMDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Nomor J.M.","Tanggal Bayar","Akun Bayar","Kode Dokter","Nama Dokter","Keterangan","Total J.M."};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(100);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(210);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(260);
            }else if(i==6){
                column.setPreferredWidth(100);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoBayar.setDocument(new batasInput((byte)20).getKata(NoBayar));
        kddokter.setDocument(new batasInput((byte)20).getKata(kddokter));
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("KeuanganCariBayarJMDokter")){
                    if(dokter.getTable().getSelectedRow()!= -1){
                        kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmdokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    }   
                    kddokter.requestFocus();
                }
                    
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
        
        try {
            psrekening=koneksi.prepareStatement(
                "select set_akun_ralan.Utang_Jasa_Medik_Dokter_Tindakan_Ralan,set_akun_ralan.Utang_Jasa_Medik_Dokter_Laborat_Ralan,"+
                "set_akun_ralan.Utang_Jasa_Medik_Dokter_Radiologi_Ralan,set_akun_ralan.Utang_Jasa_Medik_Dokter_Operasi_Ralan from set_akun_ralan");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Utang_Jasa_Medik_Dokter_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Utang_Jasa_Medik_Dokter_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Laborat_Ralan");
                    Utang_Jasa_Medik_Dokter_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ralan");
                    Utang_Jasa_Medik_Dokter_Operasi_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Operasi_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }    
            
            psrekening=koneksi.prepareStatement(
                "select set_akun_ranap.Utang_Jasa_Medik_Dokter_Tindakan_Ranap,set_akun_ranap.Utang_Jasa_Medik_Dokter_Laborat_Ranap,"+
                "set_akun_ranap.Utang_Jasa_Medik_Dokter_Radiologi_Ranap,set_akun_ranap.Utang_Jasa_Medik_Dokter_Operasi_Ranap from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Utang_Jasa_Medik_Dokter_Tindakan_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Utang_Jasa_Medik_Dokter_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Laborat_Ranap");
                    Utang_Jasa_Medik_Dokter_Radiologi_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ranap");
                    Utang_Jasa_Medik_Dokter_Operasi_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Operasi_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }  
        } catch (Exception e) {
            System.out.println(e);
        }
        ChkInput.setSelected(false);
        isForm();
        
        try {
            if(Valid.daysOld("./cache/akunbankmandiri.iyem")<30){
                tampilAkunBankMandiri2();
            }else{
                tampilAkunBankMandiri();
            }
        } catch (Exception e) {
        }
    }

    /** This method is called from within the constructor to
     * initialize the 
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppRincian = new javax.swing.JMenuItem();
        ppRincian2 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        ChkInput = new widget.CekBox();
        FormInput = new widget.panelisi();
        label15 = new widget.Label();
        NoBayar = new widget.TextBox();
        label16 = new widget.Label();
        kddokter = new widget.TextBox();
        nmdokter = new widget.TextBox();
        btnPegawai = new widget.Button();
        panelisi1 = new widget.panelisi();
        label11 = new widget.Label();
        Tanggal1 = new widget.Tanggal();
        label12 = new widget.Label();
        Tanggal2 = new widget.Tanggal();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnGaji = new widget.Button();
        BtnHapus = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppRincian.setBackground(new java.awt.Color(255, 255, 254));
        ppRincian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRincian.setForeground(new java.awt.Color(50, 50, 50));
        ppRincian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRincian.setText("Sembunyikan Rincian Jasa Medis");
        ppRincian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRincian.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRincian.setName("ppRincian"); // NOI18N
        ppRincian.setPreferredSize(new java.awt.Dimension(210, 26));
        ppRincian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRincianBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRincian);

        ppRincian2.setBackground(new java.awt.Color(255, 255, 254));
        ppRincian2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRincian2.setForeground(new java.awt.Color(50, 50, 50));
        ppRincian2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppRincian2.setText("Tampilkan Rincian Jasa Medis");
        ppRincian2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRincian2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRincian2.setName("ppRincian2"); // NOI18N
        ppRincian2.setPreferredSize(new java.awt.Dimension(210, 26));
        ppRincian2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRincian2BtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRincian2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Bayar Jasa Medis Dokter ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(jPopupMenu1);
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
        tbDokter.setToolTipText("Silahkan klik pada nomor tagihan untuk verifikasi pilihan");
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        PanelInput.setBackground(new java.awt.Color(255, 255, 255));
        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(192, 65));
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

        label15.setText("Nomor J.M. :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label15);
        label15.setBounds(0, 10, 81, 23);

        NoBayar.setName("NoBayar"); // NOI18N
        NoBayar.setPreferredSize(new java.awt.Dimension(207, 23));
        NoBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoBayarKeyPressed(evt);
            }
        });
        FormInput.add(NoBayar);
        NoBayar.setBounds(85, 10, 160, 23);

        label16.setText("Dokter :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label16);
        label16.setBounds(295, 10, 70, 23);

        kddokter.setEditable(false);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(kddokter);
        kddokter.setBounds(369, 10, 110, 23);

        nmdokter.setEditable(false);
        nmdokter.setName("nmdokter"); // NOI18N
        nmdokter.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmdokter);
        nmdokter.setBounds(481, 10, 240, 23);

        btnPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPegawai.setMnemonic('1');
        btnPegawai.setToolTipText("Alt+1");
        btnPegawai.setName("btnPegawai"); // NOI18N
        btnPegawai.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPegawaiActionPerformed(evt);
            }
        });
        FormInput.add(btnPegawai);
        btnPegawai.setBounds(724, 10, 28, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(53, 23));
        panelisi1.add(label11);

        Tanggal1.setDisplayFormat("dd-MM-yyyy");
        Tanggal1.setName("Tanggal1"); // NOI18N
        Tanggal1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal1KeyPressed(evt);
            }
        });
        panelisi1.add(Tanggal1);

        label12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label12.setText("s.d.");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(25, 23));
        panelisi1.add(label12);

        Tanggal2.setDisplayFormat("dd-MM-yyyy");
        Tanggal2.setMinimumSize(new java.awt.Dimension(90, 23));
        Tanggal2.setName("Tanggal2"); // NOI18N
        Tanggal2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tanggal2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tanggal2KeyPressed(evt);
            }
        });
        panelisi1.add(Tanggal2);

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

        BtnGaji.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/gaji.png"))); // NOI18N
        BtnGaji.setMnemonic('2');
        BtnGaji.setToolTipText("Alt+2");
        BtnGaji.setName("BtnGaji"); // NOI18N
        BtnGaji.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGajiActionPerformed(evt);
            }
        });
        BtnGaji.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnGajiKeyPressed(evt);
            }
        });
        panelisi1.add(BtnGaji);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(28, 23));
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

        label9.setText("Total :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(40, 23));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(105, 23));
        panelisi1.add(LTotal);

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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dokter.dispose();
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnPrint,BtnCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPegawaiActionPerformed
        akses.setform("KeuanganCariBayarJMDokter");
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setAlwaysOnTop(false);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnPegawaiActionPerformed

    private void Tanggal1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal1KeyPressed
        Valid.pindah(evt,NoBayar,kddokter);
    }//GEN-LAST:event_Tanggal1KeyPressed

    private void NoBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoBayarKeyPressed
        Valid.pindah(evt, BtnKeluar, kddokter);
    }//GEN-LAST:event_NoBayarKeyPressed

    private void Tanggal2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tanggal2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tanggal2KeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,btnPegawai, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        NoBayar.setText("");
        kddokter.setText("");
        nmdokter.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnHapus.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            
            int row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'"+i+"','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Penagihan Piutang Pasien"); 
            }
            Sequel.menyimpan("temporary","'"+i+"','','','TOTAL PEMBAYARAN :','','','','"+Valid.SetAngka(totaltagihan)+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Transaksi Penagihan Piutang Pasien"); 
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            Valid.MyReportqry("rptBayarJmDokter.jasper","report","::[ Data Rekap Pembayaran Jasa Medis Dokter ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
        isForm();
    }//GEN-LAST:event_ChkInputActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbDokter.getSelectedRow()>-1){
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
              Valid.textKosong(BtnAll,"Nomor J.M.");
            }else{
                try {
                    ps=koneksi.prepareStatement(
                        "select bayar_jm_dokter.besar_bayar,bayar_jm_dokter.nama_bayar,bayar_jm_dokter.rawatjalan,bayar_jm_dokter.rawatinap,"+
                        "bayar_jm_dokter.labrawatjalan,bayar_jm_dokter.labrawatinap,bayar_jm_dokter.radrawatjalan,bayar_jm_dokter.radrawatinap,"+
                        "bayar_jm_dokter.operasiralan,bayar_jm_dokter.operasiranap from bayar_jm_dokter where bayar_jm_dokter.no_bayar=?");
                    try {
                        ps.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            Sequel.AutoComitFalse();
                            sukses=true;
                            
                            koderekening="";
                            try {
                                myObj = new FileReader("./cache/akunbayarhutang.iyem");
                                root = mapper.readTree(myObj);
                                response = root.path("akunbayarhutang");
                                if(response.isArray()){
                                   for(JsonNode list:response){
                                       if(list.path("NamaAkun").asText().equals(rs.getString("nama_bayar"))){
                                            koderekening=list.path("KodeRek").asText();  
                                       }
                                   }
                                }
                                myObj.close();
                            } catch (Exception e) {
                                sukses=false;
                            } 
                            
                            if(sukses==true){
                                totaltagihan=0;
                                if(koderekening.equals(Host_to_Host_Bank_Mandiri)){
                                    totaltagihan=Sequel.cariIsiAngka("select metode_pembayaran_bankmandiri.biaya_transaksi from metode_pembayaran_bankmandiri inner join pembayaran_pihak_ke3_bankmandiri on pembayaran_pihak_ke3_bankmandiri.kode_metode=metode_pembayaran_bankmandiri.kode_metode where pembayaran_pihak_ke3_bankmandiri.nomor_pembayaran=?",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                    Sequel.meghapus("pembayaran_pihak_ke3_bankmandiri","nomor_pembayaran",tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                                }
                                Sequel.queryu("delete from tampjurnal"); 
                                if(rs.getDouble("rawatjalan")>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"','Utang Jasa Medik Dokter Tindakan Ralan','0','"+rs.getDouble("rawatjalan")+"'","kredit=kredit+'"+rs.getDouble("rawatjalan")+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ralan+"'")==false){
                                        sukses=false;
                                    }       
                                }
                                if(rs.getDouble("rawatinap")>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','0','"+rs.getDouble("rawatinap")+"'","kredit=kredit+'"+(rs.getDouble("rawatinap"))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"'")==false){
                                        sukses=false;
                                    }      
                                }
                                if(rs.getDouble("labrawatjalan")>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Utang Jasa Medik Dokter Laborat Ralan','0','"+rs.getDouble("labrawatjalan")+"'","kredit=kredit+'"+(rs.getDouble("labrawatjalan"))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'")==false){
                                        sukses=false;
                                    }                           
                                }
                                if(rs.getDouble("labrawatinap")>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"','Utang Jasa Medik Dokter Laborat Ranap','0','"+rs.getDouble("labrawatinap")+"'","kredit=kredit+'"+(rs.getDouble("labrawatinap"))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"'")==false){
                                        sukses=false;
                                    }                           
                                }
                                if(rs.getDouble("radrawatjalan")>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"','Utang Jasa Medik Dokter Radiologi Ralan','0','"+rs.getDouble("radrawatjalan")+"'","kredit=kredit+'"+(rs.getDouble("radrawatjalan"))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ralan+"'")==false){
                                        sukses=false;
                                    }                           
                                }
                                if(rs.getDouble("radrawatinap")>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"','Utang Jasa Medik Dokter Radiologi Ranap','0','"+rs.getDouble("radrawatinap")+"'","kredit=kredit+'"+(rs.getDouble("radrawatinap"))+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Radiologi_Ranap+"'")==false){
                                        sukses=false;
                                    }                           
                                }
                                if(rs.getDouble("operasiralan")>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"','Utang Jasa Medik Dokter Operasi Ralan','0','"+rs.getDouble("operasiralan")+"'","kredit=kredit+'"+rs.getDouble("operasiralan")+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ralan+"'")==false){
                                        sukses=false;
                                    }                             
                                }
                                if(rs.getDouble("operasiranap")>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"','Utang Jasa Medik Dokter Operasi Ranap','0','"+rs.getDouble("operasiranap")+"'","kredit=kredit+'"+rs.getDouble("operasiranap")+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"'")==false){
                                        sukses=false;
                                    }                             
                                }
                                if(totaltagihan>0){
                                    if(Sequel.menyimpantf2("tampjurnal","?,?,?,?","Rekening",4,new String[]{
                                        Akun_Biaya_Mandiri,"BIAYA TRANSAKSI","0",totaltagihan+""
                                    })==false){
                                        sukses=false;
                                    }
                                }
                                if(rs.getDouble("besar_bayar")>0){
                                    if(Sequel.menyimpantf("tampjurnal","'"+koderekening+"','"+rs.getString("nama_bayar")+"','"+(totaltagihan+rs.getDouble("besar_bayar"))+"','0'","debet=debet+'"+(totaltagihan+rs.getDouble("besar_bayar"))+"'","kd_rek='"+koderekening+"'")==false){
                                        sukses=false;
                                    }  
                                }
                                if(sukses==true){
                                    sukses=jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),"U","PEMBATALAN PEMBAYARAN JASA MEDIS DOKTER "+tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()+" "+tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString()+", OLEH "+akses.getkode());
                                }
                            }
                            
                            if(sukses==true){
                                Sequel.queryu2("delete from bayar_jm_dokter where no_bayar=?",1,new String[]{tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()});
                                tampil();
                                Sequel.Commit();
                            }else{
                                sukses=false;
                                JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                                Sequel.RollBack();
                            }  
                            Sequel.AutoComitTrue();
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
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih Nomor J.M. yang mau dihapus..!");
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnGaji,BtnAll);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGajiActionPerformed
        if(tbDokter.getSelectedRow()>-1){
            if(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().trim().equals("")){
              Valid.textKosong(BtnAll,"Nomor J.M.");
            }else{
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
                i=1;
                try {
                    //rawat jalan dr
                    ps2=koneksi.prepareStatement(
                        "select pasien.nm_pasien,bayar_rawat_jl_dr.tarif_tindakandr,jns_perawatan.nm_perawatan,bayar_rawat_jl_dr.tgl_perawatan,"+
                        "bayar_rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,bayar_rawat_jl_dr.kd_jenis_prw,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                        "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join bayar_rawat_jl_dr on bayar_rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                        "inner join jns_perawatan on bayar_rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                        "where bayar_rawat_jl_dr.no_bayar=? order by bayar_rawat_jl_dr.tgl_perawatan,bayar_rawat_jl_dr.jam_rawat");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kd_jenis_prw")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("tarif_tindakandr"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //rawat jalan drpr
                    ps2=koneksi.prepareStatement(
                        "select pasien.nm_pasien,bayar_rawat_jl_drpr.tarif_tindakandr,jns_perawatan.nm_perawatan,bayar_rawat_jl_drpr.tgl_perawatan,"+
                        "bayar_rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,bayar_rawat_jl_drpr.kd_jenis_prw,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                        "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join bayar_rawat_jl_drpr on bayar_rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                        "inner join jns_perawatan on bayar_rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                        "where bayar_rawat_jl_drpr.no_bayar=? order by bayar_rawat_jl_drpr.tgl_perawatan,bayar_rawat_jl_drpr.jam_rawat");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kd_jenis_prw")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("tarif_tindakandr"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //rawat inap dr
                    ps2=koneksi.prepareStatement(
                        "select pasien.nm_pasien,bayar_rawat_inap_dr.tarif_tindakandr,jns_perawatan_inap.nm_perawatan,bayar_rawat_inap_dr.tgl_perawatan,"+
                        "bayar_rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,bayar_rawat_inap_dr.kd_jenis_prw,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                        "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join bayar_rawat_inap_dr on bayar_rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                        "inner join jns_perawatan_inap on bayar_rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                        "where bayar_rawat_inap_dr.no_bayar=? order by bayar_rawat_inap_dr.tgl_perawatan,bayar_rawat_inap_dr.jam_rawat");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kd_jenis_prw")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("tarif_tindakandr"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //rawat inap drpr
                    ps2=koneksi.prepareStatement(
                        "select pasien.nm_pasien,bayar_rawat_inap_drpr.tarif_tindakandr,jns_perawatan_inap.nm_perawatan,bayar_rawat_inap_drpr.tgl_perawatan,"+
                        "bayar_rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,bayar_rawat_inap_drpr.kd_jenis_prw,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                        "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join bayar_rawat_inap_drpr on bayar_rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                        "inner join jns_perawatan_inap on bayar_rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                        "where bayar_rawat_inap_drpr.no_bayar=? order by bayar_rawat_inap_drpr.tgl_perawatan,bayar_rawat_inap_drpr.jam_rawat");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat")+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kd_jenis_prw")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("tarif_tindakandr"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //operasi operator 1
                    ps2=koneksi.prepareStatement(
                        "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_operator1.biayaoperator1,bayar_operasi_operator1.tgl_operasi,"+
                        "reg_periksa.kd_pj,bayar_operasi_operator1.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                        "from bayar_operasi_operator1 inner join reg_periksa on bayar_operasi_operator1.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join paket_operasi on bayar_operasi_operator1.kode_paket=paket_operasi.kode_paket "+
                        "where bayar_operasi_operator1.no_bayar=? order by bayar_operasi_operator1.tgl_operasi");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_operasi").substring(0,19)+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kode_paket")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("biayaoperator1"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //operasi operator 2
                    ps2=koneksi.prepareStatement(
                        "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_operator2.biayaoperator2,bayar_operasi_operator2.tgl_operasi,"+
                        "reg_periksa.kd_pj,bayar_operasi_operator2.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                        "from bayar_operasi_operator2 inner join reg_periksa on bayar_operasi_operator2.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join paket_operasi on bayar_operasi_operator2.kode_paket=paket_operasi.kode_paket "+
                        "where bayar_operasi_operator2.no_bayar=? order by bayar_operasi_operator2.tgl_operasi");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_operasi").substring(0,19)+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kode_paket")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("biayaoperator2"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //operasi operator 3
                    ps2=koneksi.prepareStatement(
                        "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_operator3.biayaoperator3,bayar_operasi_operator3.tgl_operasi,"+
                        "reg_periksa.kd_pj,bayar_operasi_operator3.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                        "from bayar_operasi_operator3 inner join reg_periksa on bayar_operasi_operator3.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join paket_operasi on bayar_operasi_operator3.kode_paket=paket_operasi.kode_paket "+
                        "where bayar_operasi_operator3.no_bayar=? order by bayar_operasi_operator3.tgl_operasi");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_operasi").substring(0,19)+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kode_paket")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("biayaoperator3"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //operasi dokter_anak
                    ps2=koneksi.prepareStatement(
                        "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_dokter_anak.biayadokter_anak,bayar_operasi_dokter_anak.tgl_operasi,"+
                        "reg_periksa.kd_pj,bayar_operasi_dokter_anak.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                        "from bayar_operasi_dokter_anak inner join reg_periksa on bayar_operasi_dokter_anak.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join paket_operasi on bayar_operasi_dokter_anak.kode_paket=paket_operasi.kode_paket "+
                        "where bayar_operasi_dokter_anak.no_bayar=? order by bayar_operasi_dokter_anak.tgl_operasi");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_operasi").substring(0,19)+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kode_paket")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("biayadokter_anak"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //operasi dokter_umum
                    ps2=koneksi.prepareStatement(
                        "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_dokter_umum.biaya_dokter_umum,bayar_operasi_dokter_umum.tgl_operasi,"+
                        "reg_periksa.kd_pj,bayar_operasi_dokter_umum.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                        "from bayar_operasi_dokter_umum inner join reg_periksa on bayar_operasi_dokter_umum.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join paket_operasi on bayar_operasi_dokter_umum.kode_paket=paket_operasi.kode_paket "+
                        "where bayar_operasi_dokter_umum.no_bayar=? order by bayar_operasi_dokter_umum.tgl_operasi");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_operasi").substring(0,19)+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kode_paket")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("biaya_dokter_umum"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //operasi dokter_pjanak
                    ps2=koneksi.prepareStatement(
                        "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_dokter_pjanak.biaya_dokter_pjanak,bayar_operasi_dokter_pjanak.tgl_operasi,"+
                        "reg_periksa.kd_pj,bayar_operasi_dokter_pjanak.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                        "from bayar_operasi_dokter_pjanak inner join reg_periksa on bayar_operasi_dokter_pjanak.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join paket_operasi on bayar_operasi_dokter_pjanak.kode_paket=paket_operasi.kode_paket "+
                        "where bayar_operasi_dokter_pjanak.no_bayar=? order by bayar_operasi_dokter_pjanak.tgl_operasi");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_operasi").substring(0,19)+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kode_paket")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("biaya_dokter_pjanak"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //operasi dokter_anestesi
                    ps2=koneksi.prepareStatement(
                        "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_dokter_anestesi.biayadokter_anestesi,bayar_operasi_dokter_anestesi.tgl_operasi,"+
                        "reg_periksa.kd_pj,bayar_operasi_dokter_anestesi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                        "from bayar_operasi_dokter_anestesi inner join reg_periksa on bayar_operasi_dokter_anestesi.no_rawat=reg_periksa.no_rawat "+
                        "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join paket_operasi on bayar_operasi_dokter_anestesi.kode_paket=paket_operasi.kode_paket "+
                        "where bayar_operasi_dokter_anestesi.no_bayar=? order by bayar_operasi_dokter_anestesi.tgl_operasi");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_operasi").substring(0,19)+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kode_paket")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("biayadokter_anestesi"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //dokter pj laborat
                    ps2=koneksi.prepareStatement(
                        "select bayar_periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                        "jns_perawatan_lab.nm_perawatan,bayar_periksa_lab.tgl_periksa,bayar_periksa_lab.jam,bayar_periksa_lab.no_rawat,bayar_periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                        " from bayar_periksa_lab inner join reg_periksa on bayar_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join jns_perawatan_lab on bayar_periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                        "where bayar_periksa_lab.no_bayar=? order by bayar_periksa_lab.tgl_periksa,bayar_periksa_lab.jam");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_periksa")+" "+rs2.getString("jam")+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kd_jenis_prw")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("tarif_tindakan_dokter"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //dokter detail laborat
                    ps2=koneksi.prepareStatement(
                        "select bayar_detail_periksa_lab.bagian_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                        "template_laboratorium.Pemeriksaan,bayar_detail_periksa_lab.tgl_periksa,bayar_detail_periksa_lab.jam,"+
                        "bayar_detail_periksa_lab.no_rawat,bayar_detail_periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                        " from bayar_detail_periksa_lab inner join reg_periksa on bayar_detail_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join template_laboratorium on bayar_detail_periksa_lab.id_template=template_laboratorium.id_template "+
                        "where bayar_detail_periksa_lab.no_bayar=? order by bayar_detail_periksa_lab.tgl_periksa,bayar_detail_periksa_lab.jam");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_periksa")+" "+rs2.getString("jam")+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kd_jenis_prw")+"','"+
                                rs2.getString("Pemeriksaan")+"','"+
                                Valid.SetAngka(rs2.getDouble("bagian_dokter"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //dokter perujuk laborat
                    ps2=koneksi.prepareStatement(
                        "select bayar_periksa_lab_perujuk.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                        "jns_perawatan_lab.nm_perawatan,bayar_periksa_lab_perujuk.tgl_periksa,bayar_periksa_lab_perujuk.jam,bayar_periksa_lab_perujuk.no_rawat,bayar_periksa_lab_perujuk.kd_jenis_prw,reg_periksa.kd_pj "+
                        " from bayar_periksa_lab_perujuk inner join reg_periksa on bayar_periksa_lab_perujuk.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join jns_perawatan_lab on bayar_periksa_lab_perujuk.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                        "where bayar_periksa_lab_perujuk.no_bayar=? order by bayar_periksa_lab_perujuk.tgl_periksa,bayar_periksa_lab_perujuk.jam");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_periksa")+" "+rs2.getString("jam")+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kd_jenis_prw")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("tarif_perujuk"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //dokter detail perujuk laborat
                    ps2=koneksi.prepareStatement(
                        "select bayar_detail_periksa_lab_perujuk.bagian_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                        "template_laboratorium.Pemeriksaan,bayar_detail_periksa_lab_perujuk.tgl_periksa,bayar_detail_periksa_lab_perujuk.jam,"+
                        "bayar_detail_periksa_lab_perujuk.no_rawat,bayar_detail_periksa_lab_perujuk.kd_jenis_prw,reg_periksa.kd_pj "+
                        " from bayar_detail_periksa_lab_perujuk inner join reg_periksa on bayar_detail_periksa_lab_perujuk.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join template_laboratorium on bayar_detail_periksa_lab_perujuk.id_template=template_laboratorium.id_template "+
                        "where bayar_detail_periksa_lab_perujuk.no_bayar=? order by bayar_detail_periksa_lab_perujuk.tgl_periksa,bayar_detail_periksa_lab_perujuk.jam");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_periksa")+" "+rs2.getString("jam")+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kd_jenis_prw")+"','"+
                                rs2.getString("Pemeriksaan")+"','"+
                                Valid.SetAngka(rs2.getDouble("bagian_perujuk"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //dokter pj radiologi
                    ps2=koneksi.prepareStatement(
                        "select bayar_periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                        "jns_perawatan_radiologi.nm_perawatan,bayar_periksa_radiologi.tgl_periksa,bayar_periksa_radiologi.jam,bayar_periksa_radiologi.no_rawat,bayar_periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                        " from bayar_periksa_radiologi inner join reg_periksa on bayar_periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join jns_perawatan_radiologi on bayar_periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                        "where bayar_periksa_radiologi.no_bayar=? order by bayar_periksa_radiologi.tgl_periksa,bayar_periksa_radiologi.jam");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_periksa")+" "+rs2.getString("jam")+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kd_jenis_prw")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("tarif_tindakan_dokter"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    //dokter perujuk radiologi
                    ps2=koneksi.prepareStatement(
                        "select bayar_periksa_radiologi_perujuk.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                        "jns_perawatan_radiologi.nm_perawatan,bayar_periksa_radiologi_perujuk.tgl_periksa,bayar_periksa_radiologi_perujuk.jam,bayar_periksa_radiologi_perujuk.no_rawat,bayar_periksa_radiologi_perujuk.kd_jenis_prw,reg_periksa.kd_pj "+
                        " from bayar_periksa_radiologi_perujuk inner join reg_periksa on bayar_periksa_radiologi_perujuk.no_rawat=reg_periksa.no_rawat "+
                        " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        " inner join jns_perawatan_radiologi on bayar_periksa_radiologi_perujuk.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                        "where bayar_periksa_radiologi_perujuk.no_bayar=? order by bayar_periksa_radiologi_perujuk.tgl_periksa,bayar_periksa_radiologi_perujuk.jam");
                    try {
                        ps2.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            Sequel.menyimpan("temporary","'"+i+"','"+
                                rs2.getString("tgl_periksa")+" "+rs2.getString("jam")+"','"+
                                rs2.getString("no_rawat")+"','"+
                                rs2.getString("no_rkm_medis")+"','"+
                                rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")"+"','"+
                                rs2.getString("kd_jenis_prw")+"','"+
                                rs2.getString("nm_perawatan")+"','"+
                                Valid.SetAngka(rs2.getDouble("tarif_perujuk"))+
                                "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                            );
                            i++;
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
                    Sequel.menyimpan("temporary","'"+i+"','>> Jumlah :','','','','','','"+tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString()+
                        "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","JM Dokter"
                    ); 

                    Map<String, Object> param = new HashMap<>();   
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());   
                    param.put("dokter",tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());   
                    param.put("bulan",Valid.SetTgl3(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()).substring(3,10));   
                    param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                    Valid.MyReportqry("rptSlipBayarJMDokter.jasper","report","[ Slip J.M. Dokter  ]","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
                this.setCursor(Cursor.getDefaultCursor());
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih Nomor J.M. yang mau dicetak slipnya..!");
        }
    }//GEN-LAST:event_BtnGajiActionPerformed

    private void BtnGajiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnGajiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnGajiActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnAll);
        }
    }//GEN-LAST:event_BtnGajiKeyPressed

    private void ppRincianBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRincianBtnPrintActionPerformed
        rincian=false;
        tampil();
    }//GEN-LAST:event_ppRincianBtnPrintActionPerformed

    private void ppRincian2BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRincian2BtnPrintActionPerformed
        rincian=true;
        tampil();
    }//GEN-LAST:event_ppRincian2BtnPrintActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            KeuanganCariBayarJMDokter dialog = new KeuanganCariBayarJMDokter(new javax.swing.JFrame(), true);
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
    private widget.Button BtnGaji;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.CekBox ChkInput;
    private widget.panelisi FormInput;
    private widget.Label LTotal;
    private widget.TextBox NoBayar;
    private javax.swing.JPanel PanelInput;
    private widget.Tanggal Tanggal1;
    private widget.Tanggal Tanggal2;
    private widget.Button btnPegawai;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label9;
    private widget.TextBox nmdokter;
    private widget.panelisi panelisi1;
    private javax.swing.JMenuItem ppRincian;
    private javax.swing.JMenuItem ppRincian2;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
         Valid.tabelKosong(tabMode);
         try{
             notagihan="";petugas="";
             tanggal=" bayar_jm_dokter.tanggal between '"+Valid.SetTgl(Tanggal1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(Tanggal2.getSelectedItem()+"")+"' ";
             if(!NoBayar.getText().trim().equals("")){
                 notagihan=" and bayar_jm_dokter.no_bayar='"+NoBayar.getText()+"' ";
             }
             if(!nmdokter.getText().trim().equals("")){
                 petugas=" and concat(bayar_jm_dokter.kd_dokter,dokter.nm_dokter) like '%"+kddokter.getText()+nmdokter.getText()+"%' ";
             }
             
             ps=koneksi.prepareStatement(
                     "select bayar_jm_dokter.no_bayar,bayar_jm_dokter.tanggal,bayar_jm_dokter.kd_dokter,dokter.nm_dokter,bayar_jm_dokter.besar_bayar,bayar_jm_dokter.nama_bayar,bayar_jm_dokter.keterangan "+
                     "from bayar_jm_dokter inner join dokter on bayar_jm_dokter.kd_dokter=dokter.kd_dokter where "+tanggal+notagihan+petugas+" order by bayar_jm_dokter.tanggal");
             try {
                rs=ps.executeQuery();
                totaltagihan=0;
                if(rincian==false){
                    while(rs.next()){
                        tabMode.addRow(new Object[]{
                            rs.getString("no_bayar"),rs.getString("tanggal"),rs.getString("nama_bayar"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("keterangan"),Valid.SetAngka(rs.getDouble("besar_bayar"))
                        });
                        totaltagihan=totaltagihan+rs.getDouble("besar_bayar");
                    }
                }else{
                    while(rs.next()){
                        totaltagihan=totaltagihan+rs.getDouble("besar_bayar");
                        tabMode.addRow(new Object[]{
                            rs.getString("no_bayar"),rs.getString("tanggal"),rs.getString("nama_bayar"),rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("keterangan"),Valid.SetAngka(rs.getDouble("besar_bayar"))
                        });
                        tabMode.addRow(new Object[]{
                            "","Tanggal & Jam","Pasien","No.Rawat","Kode/ID","Tindakan Medis","Besar J.M."
                        });
                        //rawat jalan dr
                        ps2=koneksi.prepareStatement(
                            "select pasien.nm_pasien,bayar_rawat_jl_dr.tarif_tindakandr,jns_perawatan.nm_perawatan,bayar_rawat_jl_dr.tgl_perawatan,"+
                            "bayar_rawat_jl_dr.jam_rawat,reg_periksa.kd_pj,bayar_rawat_jl_dr.kd_jenis_prw,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                            "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join bayar_rawat_jl_dr on bayar_rawat_jl_dr.no_rawat=reg_periksa.no_rawat "+
                            "inner join jns_perawatan on bayar_rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                            "where bayar_rawat_jl_dr.no_bayar=? order by bayar_rawat_jl_dr.tgl_perawatan,bayar_rawat_jl_dr.jam_rawat");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat"),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kd_jenis_prw")+" (Ralan)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("tarif_tindakandr"))
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
                        //rawat jalan drpr
                        ps2=koneksi.prepareStatement(
                            "select pasien.nm_pasien,bayar_rawat_jl_drpr.tarif_tindakandr,jns_perawatan.nm_perawatan,bayar_rawat_jl_drpr.tgl_perawatan,"+
                            "bayar_rawat_jl_drpr.jam_rawat,reg_periksa.kd_pj,bayar_rawat_jl_drpr.kd_jenis_prw,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                            "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join bayar_rawat_jl_drpr on bayar_rawat_jl_drpr.no_rawat=reg_periksa.no_rawat "+
                            "inner join jns_perawatan on bayar_rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                            "where bayar_rawat_jl_drpr.no_bayar=? order by bayar_rawat_jl_drpr.tgl_perawatan,bayar_rawat_jl_drpr.jam_rawat");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat"),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kd_jenis_prw")+" (Ralan)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("tarif_tindakandr"))
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
                        //rawat inap dr
                        ps2=koneksi.prepareStatement(
                            "select pasien.nm_pasien,bayar_rawat_inap_dr.tarif_tindakandr,jns_perawatan_inap.nm_perawatan,bayar_rawat_inap_dr.tgl_perawatan,"+
                            "bayar_rawat_inap_dr.jam_rawat,reg_periksa.kd_pj,bayar_rawat_inap_dr.kd_jenis_prw,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                            "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join bayar_rawat_inap_dr on bayar_rawat_inap_dr.no_rawat=reg_periksa.no_rawat "+
                            "inner join jns_perawatan_inap on bayar_rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                            "where bayar_rawat_inap_dr.no_bayar=? order by bayar_rawat_inap_dr.tgl_perawatan,bayar_rawat_inap_dr.jam_rawat");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat"),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kd_jenis_prw")+" (Ranap)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("tarif_tindakandr"))
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
                        //rawat inap drpr
                        ps2=koneksi.prepareStatement(
                            "select pasien.nm_pasien,bayar_rawat_inap_drpr.tarif_tindakandr,jns_perawatan_inap.nm_perawatan,bayar_rawat_inap_drpr.tgl_perawatan,"+
                            "bayar_rawat_inap_drpr.jam_rawat,reg_periksa.kd_pj,bayar_rawat_inap_drpr.kd_jenis_prw,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                            "from pasien inner join reg_periksa on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join bayar_rawat_inap_drpr on bayar_rawat_inap_drpr.no_rawat=reg_periksa.no_rawat "+
                            "inner join jns_perawatan_inap on bayar_rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                            "where bayar_rawat_inap_drpr.no_bayar=? order by bayar_rawat_inap_drpr.tgl_perawatan,bayar_rawat_inap_drpr.jam_rawat");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_perawatan")+" "+rs2.getString("jam_rawat"),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kd_jenis_prw")+" (Ranap)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("tarif_tindakandr"))
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
                        //operasi operator 1
                        ps2=koneksi.prepareStatement(
                            "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_operator1.biayaoperator1,bayar_operasi_operator1.tgl_operasi,"+
                            "reg_periksa.kd_pj,bayar_operasi_operator1.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                            "from bayar_operasi_operator1 inner join reg_periksa on bayar_operasi_operator1.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join paket_operasi on bayar_operasi_operator1.kode_paket=paket_operasi.kode_paket "+
                            "where bayar_operasi_operator1.no_bayar=? order by bayar_operasi_operator1.tgl_operasi");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_operasi").substring(0,19),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kode_paket")+" (Operasi)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("biayaoperator1"))
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
                        //operasi operator 2
                        ps2=koneksi.prepareStatement(
                            "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_operator2.biayaoperator2,bayar_operasi_operator2.tgl_operasi,"+
                            "reg_periksa.kd_pj,bayar_operasi_operator2.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                            "from bayar_operasi_operator2 inner join reg_periksa on bayar_operasi_operator2.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join paket_operasi on bayar_operasi_operator2.kode_paket=paket_operasi.kode_paket "+
                            "where bayar_operasi_operator2.no_bayar=? order by bayar_operasi_operator2.tgl_operasi");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_operasi").substring(0,19),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kode_paket")+" (Operasi)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("biayaoperator2"))
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
                        //operasi operator 3
                        ps2=koneksi.prepareStatement(
                            "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_operator3.biayaoperator3,bayar_operasi_operator3.tgl_operasi,"+
                            "reg_periksa.kd_pj,bayar_operasi_operator3.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                            "from bayar_operasi_operator3 inner join reg_periksa on bayar_operasi_operator3.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join paket_operasi on bayar_operasi_operator3.kode_paket=paket_operasi.kode_paket "+
                            "where bayar_operasi_operator3.no_bayar=? order by bayar_operasi_operator3.tgl_operasi");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_operasi").substring(0,19),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kode_paket")+" (Operasi)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("biayaoperator3"))
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
                        //operasi dokter_anak
                        ps2=koneksi.prepareStatement(
                            "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_dokter_anak.biayadokter_anak,bayar_operasi_dokter_anak.tgl_operasi,"+
                            "reg_periksa.kd_pj,bayar_operasi_dokter_anak.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                            "from bayar_operasi_dokter_anak inner join reg_periksa on bayar_operasi_dokter_anak.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join paket_operasi on bayar_operasi_dokter_anak.kode_paket=paket_operasi.kode_paket "+
                            "where bayar_operasi_dokter_anak.no_bayar=? order by bayar_operasi_dokter_anak.tgl_operasi");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_operasi").substring(0,19),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kode_paket")+" (Operasi)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("biayadokter_anak"))
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
                        //operasi dokter_umum
                        ps2=koneksi.prepareStatement(
                            "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_dokter_umum.biaya_dokter_umum,bayar_operasi_dokter_umum.tgl_operasi,"+
                            "reg_periksa.kd_pj,bayar_operasi_dokter_umum.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                            "from bayar_operasi_dokter_umum inner join reg_periksa on bayar_operasi_dokter_umum.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join paket_operasi on bayar_operasi_dokter_umum.kode_paket=paket_operasi.kode_paket "+
                            "where bayar_operasi_dokter_umum.no_bayar=? order by bayar_operasi_dokter_umum.tgl_operasi");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_operasi").substring(0,19),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kode_paket")+" (Operasi)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("biaya_dokter_umum"))
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
                        //operasi dokter_pjanak
                        ps2=koneksi.prepareStatement(
                            "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_dokter_pjanak.biaya_dokter_pjanak,bayar_operasi_dokter_pjanak.tgl_operasi,"+
                            "reg_periksa.kd_pj,bayar_operasi_dokter_pjanak.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                            "from bayar_operasi_dokter_pjanak inner join reg_periksa on bayar_operasi_dokter_pjanak.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join paket_operasi on bayar_operasi_dokter_pjanak.kode_paket=paket_operasi.kode_paket "+
                            "where bayar_operasi_dokter_pjanak.no_bayar=? order by bayar_operasi_dokter_pjanak.tgl_operasi");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_operasi").substring(0,19),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kode_paket")+" (Operasi)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("biaya_dokter_pjanak"))
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
                        //operasi dokter_anestesi
                        ps2=koneksi.prepareStatement(
                            "select pasien.nm_pasien,paket_operasi.nm_perawatan,bayar_operasi_dokter_anestesi.biayadokter_anestesi,bayar_operasi_dokter_anestesi.tgl_operasi,"+
                            "reg_periksa.kd_pj,bayar_operasi_dokter_anestesi.kode_paket,reg_periksa.no_rawat,reg_periksa.no_rkm_medis "+
                            "from bayar_operasi_dokter_anestesi inner join reg_periksa on bayar_operasi_dokter_anestesi.no_rawat=reg_periksa.no_rawat "+
                            "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            "inner join paket_operasi on bayar_operasi_dokter_anestesi.kode_paket=paket_operasi.kode_paket "+
                            "where bayar_operasi_dokter_anestesi.no_bayar=? order by bayar_operasi_dokter_anestesi.tgl_operasi");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_operasi").substring(0,19),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kode_paket")+" (Operasi)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("biayadokter_anestesi"))
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
                        //dokter pj laborat
                        ps2=koneksi.prepareStatement(
                            "select bayar_periksa_lab.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                            "jns_perawatan_lab.nm_perawatan,bayar_periksa_lab.tgl_periksa,bayar_periksa_lab.jam,bayar_periksa_lab.no_rawat,bayar_periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                            " from bayar_periksa_lab inner join reg_periksa on bayar_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                            " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            " inner join jns_perawatan_lab on bayar_periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where bayar_periksa_lab.no_bayar=? order by bayar_periksa_lab.tgl_periksa,bayar_periksa_lab.jam");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_periksa")+" "+rs2.getString("jam"),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kd_jenis_prw")+" (Laborat)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("tarif_tindakan_dokter"))
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
                        //dokter detail laborat
                        ps2=koneksi.prepareStatement(
                            "select bayar_detail_periksa_lab.bagian_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                            "template_laboratorium.Pemeriksaan,bayar_detail_periksa_lab.tgl_periksa,bayar_detail_periksa_lab.jam,"+
                            "bayar_detail_periksa_lab.no_rawat,bayar_detail_periksa_lab.kd_jenis_prw,reg_periksa.kd_pj "+
                            " from bayar_detail_periksa_lab inner join reg_periksa on bayar_detail_periksa_lab.no_rawat=reg_periksa.no_rawat "+
                            " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            " inner join template_laboratorium on bayar_detail_periksa_lab.id_template=template_laboratorium.id_template "+
                            "where bayar_detail_periksa_lab.no_bayar=? order by bayar_detail_periksa_lab.tgl_periksa,bayar_detail_periksa_lab.jam");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_periksa")+" "+rs2.getString("jam"),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kd_jenis_prw")+" (Laborat)",rs2.getString("Pemeriksaan"),Valid.SetAngka(rs2.getDouble("bagian_dokter"))
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
                        //dokter perujuk laborat
                        ps2=koneksi.prepareStatement(
                            "select bayar_periksa_lab_perujuk.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                            "jns_perawatan_lab.nm_perawatan,bayar_periksa_lab_perujuk.tgl_periksa,bayar_periksa_lab_perujuk.jam,bayar_periksa_lab_perujuk.no_rawat,bayar_periksa_lab_perujuk.kd_jenis_prw,reg_periksa.kd_pj "+
                            " from bayar_periksa_lab_perujuk inner join reg_periksa on bayar_periksa_lab_perujuk.no_rawat=reg_periksa.no_rawat "+
                            " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            " inner join jns_perawatan_lab on bayar_periksa_lab_perujuk.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                            "where bayar_periksa_lab_perujuk.no_bayar=? order by bayar_periksa_lab_perujuk.tgl_periksa,bayar_periksa_lab_perujuk.jam");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_periksa")+" "+rs2.getString("jam"),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kd_jenis_prw")+" (Laborat)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("tarif_perujuk"))
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
                        //dokter detail perujuk laborat
                        ps2=koneksi.prepareStatement(
                            "select bayar_detail_periksa_lab_perujuk.bagian_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                            "template_laboratorium.Pemeriksaan,bayar_detail_periksa_lab_perujuk.tgl_periksa,bayar_detail_periksa_lab_perujuk.jam,"+
                            "bayar_detail_periksa_lab_perujuk.no_rawat,bayar_detail_periksa_lab_perujuk.kd_jenis_prw,reg_periksa.kd_pj "+
                            " from bayar_detail_periksa_lab_perujuk inner join reg_periksa on bayar_detail_periksa_lab_perujuk.no_rawat=reg_periksa.no_rawat "+
                            " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            " inner join template_laboratorium on bayar_detail_periksa_lab_perujuk.id_template=template_laboratorium.id_template "+
                            "where bayar_detail_periksa_lab_perujuk.no_bayar=? order by bayar_detail_periksa_lab_perujuk.tgl_periksa,bayar_detail_periksa_lab_perujuk.jam");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_periksa")+" "+rs2.getString("jam"),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kd_jenis_prw")+" (Laborat)",rs2.getString("Pemeriksaan"),Valid.SetAngka(rs2.getDouble("bagian_perujuk"))
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
                        //dokter pj radiologi
                        ps2=koneksi.prepareStatement(
                            "select bayar_periksa_radiologi.tarif_tindakan_dokter,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                            "jns_perawatan_radiologi.nm_perawatan,bayar_periksa_radiologi.tgl_periksa,bayar_periksa_radiologi.jam,bayar_periksa_radiologi.no_rawat,bayar_periksa_radiologi.kd_jenis_prw,reg_periksa.kd_pj "+
                            " from bayar_periksa_radiologi inner join reg_periksa on bayar_periksa_radiologi.no_rawat=reg_periksa.no_rawat "+
                            " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            " inner join jns_perawatan_radiologi on bayar_periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                            "where bayar_periksa_radiologi.no_bayar=? order by bayar_periksa_radiologi.tgl_periksa,bayar_periksa_radiologi.jam");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_periksa")+" "+rs2.getString("jam"),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kd_jenis_prw")+" (Radiologi)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("tarif_tindakan_dokter"))
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
                        //dokter perujuk radiologi
                        ps2=koneksi.prepareStatement(
                            "select bayar_periksa_radiologi_perujuk.tarif_perujuk,pasien.nm_pasien,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,"+
                            "jns_perawatan_radiologi.nm_perawatan,bayar_periksa_radiologi_perujuk.tgl_periksa,bayar_periksa_radiologi_perujuk.jam,bayar_periksa_radiologi_perujuk.no_rawat,bayar_periksa_radiologi_perujuk.kd_jenis_prw,reg_periksa.kd_pj "+
                            " from bayar_periksa_radiologi_perujuk inner join reg_periksa on bayar_periksa_radiologi_perujuk.no_rawat=reg_periksa.no_rawat "+
                            " inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                            " inner join jns_perawatan_radiologi on bayar_periksa_radiologi_perujuk.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw "+
                            "where bayar_periksa_radiologi_perujuk.no_bayar=? order by bayar_periksa_radiologi_perujuk.tgl_periksa,bayar_periksa_radiologi_perujuk.jam");
                        try {
                            ps2.setString(1,rs.getString("no_bayar"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabMode.addRow(new Object[]{
                                    "",rs2.getString("tgl_periksa")+" "+rs2.getString("jam"),rs2.getString("no_rkm_medis")+" "+rs2.getString("nm_pasien")+" ("+rs2.getString("kd_pj")+")",
                                    rs2.getString("no_rawat"),rs2.getString("kd_jenis_prw")+" (Radiologi)",rs2.getString("nm_perawatan"),Valid.SetAngka(rs2.getDouble("tarif_perujuk"))
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
                    }
                }
                    
                LTotal.setText(Valid.SetAngka(totaltagihan));
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
         }catch(Exception e){
            System.out.println("Notifikasi : "+e);
         }    
    }

    public void emptTeks() {
        NoBayar.setText("");
        kddokter.setText("");
        nmdokter.setText("");
        BtnAll.requestFocus();        
    }
    
    public void isCek(){
        BtnHapus.setEnabled(akses.getbayar_jm_dokter());
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
    
    public void tampilAkunBankMandiri() { 
        try{     
            ps=koneksi.prepareStatement(
                    "select set_akun_mandiri.kd_rek,set_akun_mandiri.kd_rek_biaya,set_akun_mandiri.kode_mcm,set_akun_mandiri.no_rekening from set_akun_mandiri");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    file=new File("./cache/akunbankmandiri.iyem");
                    file.createNewFile();
                    fileWriter = new FileWriter(file);
                    Host_to_Host_Bank_Mandiri=rs.getString("kd_rek");
                    Akun_Biaya_Mandiri=rs.getString("kd_rek_biaya");
                    kodemcm=rs.getString("kode_mcm");
                    norekening=rs.getString("no_rekening");
                    fileWriter.write("{\"akunbankmandiri\":\""+Host_to_Host_Bank_Mandiri+"\",\"kodemcm\":\""+kodemcm+"\",\"akunbiayabankmandiri\":\""+Akun_Biaya_Mandiri+"\",\"norekening\":\""+norekening+"\"}");
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (Exception e) {
                Host_to_Host_Bank_Mandiri="";
                Akun_Biaya_Mandiri="";
                kodemcm="";
                norekening="";
                System.out.println("Notif Nota : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        } catch (Exception e) {
             Host_to_Host_Bank_Mandiri="";
             Akun_Biaya_Mandiri="";
             kodemcm="";
             norekening="";
        }
    }
    
    public void tampilAkunBankMandiri2() { 
        try{      
             myObj = new FileReader("./cache/akunbankmandiri.iyem");
             root = mapper.readTree(myObj);
             response = root.path("akunbankmandiri");
             Host_to_Host_Bank_Mandiri=response.asText();
             response = root.path("akunbiayabankmandiri");
             Akun_Biaya_Mandiri=response.asText();
             response = root.path("kodemcm");
             kodemcm=response.asText();
             response = root.path("norekening");
             norekening=response.asText();
             myObj.close();
        } catch (Exception e) {
             Host_to_Host_Bank_Mandiri="";
             Akun_Biaya_Mandiri="";
             kodemcm="";
             norekening="";
        }
    }
}

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
    private double nilaitagihan=0,totaltagihan=0;
    private boolean sukses=true;  
    public Jurnal jur=new Jurnal();
    public File file;
    public FileWriter fileWriter;
    public String iyem,Beban_Jasa_Medik_Dokter_Tindakan_Ralan="",Utang_Jasa_Medik_Dokter_Tindakan_Ralan="",Beban_Jasa_Medik_Dokter_Tindakan_Ranap="",Utang_Jasa_Medik_Dokter_Tindakan_Ranap="",
            Beban_Jasa_Medik_Dokter_Laborat_Ralan="",Utang_Jasa_Medik_Dokter_Laborat_Ralan="",Beban_Jasa_Medik_Dokter_Laborat_Ranap="",Utang_Jasa_Medik_Dokter_Laborat_Ranap="",
            Beban_Jasa_Medik_Dokter_Radiologi_Ralan="",Utang_Jasa_Medik_Dokter_Radiologi_Ralan="",Beban_Jasa_Medik_Dokter_Radiologi_Ranap="",Utang_Jasa_Medik_Dokter_Radiologi_Ranap="",
            Beban_Jasa_Medik_Dokter_Operasi_Ralan="",Utang_Jasa_Medik_Dokter_Operasi_Ralan="",Beban_Jasa_Medik_Dokter_Operasi_Ranap="",Utang_Jasa_Medik_Dokter_Operasi_Ranap="",
            Bayar_JM_Dokter=Sequel.cariIsi("select set_akun.Bayar_JM_Dokter from set_akun"),koderekening="";
    public ObjectMapper mapper = new ObjectMapper();
    public JsonNode root;
    public JsonNode response;
    public FileReader myObj;

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public KeuanganCariBayarJMDokter(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Tagihan","Tanggal Bayar","Akun Bayar","Kode Dokter","Nama Dokter","Keterangan","Total J.M."};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 7; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(80);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(380);
            }else if(i==4){
                column.setPreferredWidth(120);
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
        
        try {
            psrekening=koneksi.prepareStatement(
                "select set_akun_ralan.Beban_Jasa_Medik_Dokter_Tindakan_Ralan,set_akun_ralan.Utang_Jasa_Medik_Dokter_Tindakan_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Medik_Dokter_Laborat_Ralan,set_akun_ralan.Utang_Jasa_Medik_Dokter_Laborat_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Medik_Dokter_Radiologi_Ralan,set_akun_ralan.Utang_Jasa_Medik_Dokter_Radiologi_Ralan,"+
                "set_akun_ralan.Beban_Jasa_Medik_Dokter_Operasi_Ralan,set_akun_ralan.Utang_Jasa_Medik_Dokter_Operasi_Ralan from set_akun_ralan");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Beban_Jasa_Medik_Dokter_Tindakan_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Beban_Jasa_Medik_Dokter_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Laborat_Ralan");
                    Utang_Jasa_Medik_Dokter_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Laborat_Ralan");
                    Beban_Jasa_Medik_Dokter_Radiologi_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ralan");
                    Utang_Jasa_Medik_Dokter_Radiologi_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ralan");
                    Beban_Jasa_Medik_Dokter_Operasi_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Operasi_Ralan");
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
                "select set_akun_ranap.Beban_Jasa_Medik_Dokter_Tindakan_Ranap,set_akun_ranap.Utang_Jasa_Medik_Dokter_Tindakan_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Medik_Dokter_Laborat_Ranap,set_akun_ranap.Utang_Jasa_Medik_Dokter_Laborat_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Medik_Dokter_Radiologi_Ranap,set_akun_ranap.Utang_Jasa_Medik_Dokter_Radiologi_Ranap,"+
                "set_akun_ranap.Beban_Jasa_Medik_Dokter_Operasi_Ranap,set_akun_ranap.Utang_Jasa_Medik_Dokter_Operasi_Ranap from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Beban_Jasa_Medik_Dokter_Tindakan_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Beban_Jasa_Medik_Dokter_Laborat_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Laborat_Ranap");
                    Utang_Jasa_Medik_Dokter_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Laborat_Ranap");
                    Beban_Jasa_Medik_Dokter_Radiologi_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ranap");
                    Utang_Jasa_Medik_Dokter_Radiologi_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ranap");
                    Beban_Jasa_Medik_Dokter_Operasi_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Operasi_Ranap");
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
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
            Sequel.queryu("truncate table temporary");
            
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penagihan Piutang Pasien"); 
            }
            Sequel.menyimpan("temporary","'0','TOTAL TAGIHAN :','','','','"+Valid.SetAngka(totaltagihan)+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Penagihan Piutang Pasien"); 
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptTagihanAset.jasper","report","::[ Data Titip Faktur/Tagihan Aset/Inventaris ]::",param);
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
        
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnGaji,BtnAll);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGajiActionPerformed
        /*if(kddokter.getText().trim().equals("")||nmdokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"Dokter");
        }else{
            if(bayar==0){
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih jasa medis yang mau dibayar...!!!!");
            }else if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            }else if(tabMode.getRowCount()!=0){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Sequel.queryu("truncate table temporary");
                //"P","Tanggal","Jam","No.Rawat","No.RM","Nama Pasien","Kode","Tindakan Medis","Status","Jasa Medis","Id Detail"
                for(i=1;i<tabMode.getRowCount();i++){
                    if(tabMode.getValueAt(i,0).toString().equals("true")){
                        Sequel.menyimpan("temporary","'0','"+
                            tabMode.getValueAt(i,1).toString().replaceAll("'","")+" "+tabMode.getValueAt(i,2).toString().replaceAll("'","")+"','"+
                            tabMode.getValueAt(i,3).toString().replaceAll("'","")+"','"+
                            tabMode.getValueAt(i,4).toString().replaceAll("'","")+"','"+
                            tabMode.getValueAt(i,5).toString().replaceAll("'","")+"','"+
                            tabMode.getValueAt(i,6).toString().replaceAll("'","")+"','"+
                            tabMode.getValueAt(i,7).toString().replaceAll("'","")+"','"+
                            Valid.SetAngka(Double.parseDouble(tabMode.getValueAt(i,9).toString().replaceAll("'","")))+
                            "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","JM Dokter"
                        );
                    }
                }

                Sequel.menyimpan("temporary","'0','>> Jumlah :','','','','','','"+LCount1.getText()+
                    "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","JM Dokter"
                );

                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("dokter",nmdokter.getText());
                param.put("bulan",Tanggal.getSelectedItem().toString().substring(3,10));
                param.put("logo",Sequel.cariGambar("select logo from setting"));
                Valid.MyReport("rptSlipBayarJMDokter.jasper","report","[ Slip J.M. Dokter  ]",param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }*/
    }//GEN-LAST:event_BtnGajiActionPerformed

    private void BtnGajiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnGajiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnGajiActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnAll);
        }
    }//GEN-LAST:event_BtnGajiKeyPressed

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
    private widget.TextBox kddokter;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label9;
    private widget.TextBox nmdokter;
    private widget.panelisi panelisi1;
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
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("no_tagihan"),rs.getString("tanggal"),rs.getString("nip")+" "+rs.getString("petugas"),rs.getString("keterangan"),rs.getString("status")
                    });
                    tabMode.addRow(new Object[]{
                        "","Tgl.Tempo","No.Faktur","Nama Suplier","Sisa Hutang"
                    });
                    /*ps2=koneksi.prepareStatement(
                        "select inventaris_pemesanan.tgl_tempo,inventaris_pemesanan.no_faktur,inventaris_pemesanan.kode_suplier,inventaris_suplier.nama_suplier,inventaris_pemesanan.tagihan,"+
                        "(SELECT ifnull(SUM(besar_bayar),0) FROM bayar_pemesanan_inventaris where bayar_pemesanan_inventaris.no_faktur=inventaris_pemesanan.no_faktur) as bayar from inventaris_titip_faktur "+
                        "inner join petugas on petugas.nip=inventaris_titip_faktur.nip "+
                        "inner join inventaris_detail_titip_faktur on inventaris_detail_titip_faktur.no_tagihan=inventaris_titip_faktur.no_tagihan "+
                        "inner join inventaris_pemesanan on inventaris_detail_titip_faktur.no_faktur=inventaris_pemesanan.no_faktur "+
                        "inner join inventaris_suplier on inventaris_suplier.kode_suplier=inventaris_pemesanan.kode_suplier "+
                        "where inventaris_detail_titip_faktur.no_tagihan='"+rs.getString("no_tagihan")+"' and "+
                        tanggal+status+notagihan+petugas+cari+" order by inventaris_titip_faktur.tanggal");
                    try {
                        nilaitagihan=0;
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            nilaitagihan=nilaitagihan+(rs2.getDouble("tagihan")-rs2.getDouble("bayar"));
                            totaltagihan=totaltagihan+(rs2.getDouble("tagihan")-rs2.getDouble("bayar"));
                            tabMode.addRow(new Object[]{
                                "",rs2.getString("tgl_tempo"),rs2.getString("no_faktur"),rs2.getString("kode_suplier")+" "+rs2.getString("nama_suplier"),Valid.SetAngka((rs2.getDouble("tagihan")-rs2.getDouble("bayar")))
                            });
                        }
                        if(nilaitagihan>0){
                            tabMode.addRow(new Object[]{
                                "","","Jumlah Hutang : ","",Valid.SetAngka(nilaitagihan)
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
                    }*/
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
}

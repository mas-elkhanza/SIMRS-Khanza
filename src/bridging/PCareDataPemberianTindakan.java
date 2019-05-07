package bridging;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import inventory.riwayatobat;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

public class PCareDataPemberianTindakan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private int i,a;
    private PreparedStatement ps,psrekening;
    private ResultSet rs,rsrekening;
    private double ttljmdokter=0,ttljmperawat=0,ttlkso=0,ttlpendapatan=0;
    private String URL="",link="",otorisasi,Suspen_Piutang_Tindakan_Ranap="",Tindakan_Ranap="",
            Beban_Jasa_Medik_Dokter_Tindakan_Ranap="",Utang_Jasa_Medik_Dokter_Tindakan_Ranap="",Beban_Jasa_Medik_Paramedis_Tindakan_Ranap="",
            Utang_Jasa_Medik_Paramedis_Tindakan_Ranap="",Beban_KSO_Tindakan_Ranap="",Utang_KSO_Tindakan_Ranap="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private final Properties prop = new Properties();
    private PcareApi api=new PcareApi();
    private Jurnal jur=new Jurnal();
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public PCareDataPemberianTindakan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "Tanggal","Jam","No.Kunjungan","No.Rawat","No.R.M.","Nama Pasien","Kd Tindakan SK","Kode Tindakan",
                "Nama Tindakan","material", "bhp", "tarif_tindakandr", "tarif_tindakanpr", "kso", "menejemen", "biaya_rawat"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);
        //tampilPO("");

        tbDokter.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(125);
            }else if(i==3){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(170);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(240);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabMode2=new DefaultTableModel(null,new Object[]{
                "Tanggal","Jam","No.Kunjungan","No.Rawat","No.R.M.","Nama Pasien","Kd Tindakan SK","Kode Tindakan",
                "Nama Tindakan","material", "bhp", "tarif_tindakandr", "tarif_tindakanpr", "kso", "menejemen", "biaya_rawat"
            }){
                @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter2.setModel(tabMode2);
        //tampilPO("");

        tbDokter2.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDokter2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbDokter2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(80);
            }else if(i==1){
                column.setPreferredWidth(60);
            }else if(i==2){
                column.setPreferredWidth(125);
            }else if(i==3){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(170);
            }else if(i==6){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==7){
                column.setPreferredWidth(80);
            }else if(i==8){
                column.setPreferredWidth(240);
            }else if(i==9){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==10){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==11){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbDokter2.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
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
            psrekening=koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Tindakan_Ranap=rsrekening.getString("Suspen_Piutang_Tindakan_Ranap");
                    Tindakan_Ranap=rsrekening.getString("Tindakan_Ranap");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Beban_Jasa_Medik_Paramedis_Tindakan_Ranap=rsrekening.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ranap");
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ranap=rsrekening.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ranap");
                    Beban_KSO_Tindakan_Ranap=rsrekening.getString("Beban_KSO_Tindakan_Ranap");
                    Utang_KSO_Tindakan_Ranap=rsrekening.getString("Utang_KSO_Tindakan_Ranap");
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
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml")); 
            otorisasi=prop.getProperty("USERPCARE")+":"+prop.getProperty("PASSPCARE")+":095";
            link=prop.getProperty("URLAPIPCARE");
        } catch (Exception e) {
            System.out.println("E : "+e);
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

        Tanggal = new widget.Tanggal();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        label11 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        label18 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnHapus = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbDokter = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbDokter2 = new widget.Table();

        Tanggal.setEditable(false);
        Tanggal.setForeground(new java.awt.Color(50, 70, 50));
        Tanggal.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "28-02-2019" }));
        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setOpaque(false);
        Tanggal.setPreferredSize(new java.awt.Dimension(100, 23));
        Tanggal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalKeyPressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pemberian Tindakan PCare ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 3, 9));

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass8.add(label11);

        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari1KeyPressed(evt);
            }
        });
        panelGlass8.add(DTPCari1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass8.add(label18);

        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        DTPCari2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPCari2KeyPressed(evt);
            }
        });
        panelGlass8.add(DTPCari2);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(62, 23));
        panelGlass8.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(160, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass8.add(TCari);

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
        panelGlass8.add(BtnCari);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass8.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(100, 23));
        panelGlass8.add(LCount);

        jPanel2.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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

        jPanel2.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 254));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        TabRawat.setForeground(new java.awt.Color(70, 70, 70));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbDokter.setName("tbDokter"); // NOI18N
        Scroll.setViewportView(tbDokter);

        TabRawat.addTab("Tindakan Rawat Jalan", Scroll);

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbDokter2.setName("tbDokter2"); // NOI18N
        tbDokter2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokter2MouseClicked(evt);
            }
        });
        tbDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokter2KeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbDokter2);

        TabRawat.addTab("Tindakan Rawat Inap", Scroll2);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void DTPCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari1KeyPressed
        Valid.pindah(evt,BtnKeluar,DTPCari2);
    }//GEN-LAST:event_DTPCari1KeyPressed

    private void DTPCari2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPCari2KeyPressed
        Valid.pindah(evt, DTPCari1,TCari);
    }//GEN-LAST:event_DTPCari2KeyPressed

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
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
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
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
                Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptPCarePemberianObat.jasper","report","::[ Data Pemberian Obat PCare ]::",param);            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnHapus);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
    if(TabRawat.getSelectedIndex()==0){
        if(tbDokter.getSelectedRow()!= -1){
            if(Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                try {
                    URL = link+"/tindakan/"+tbDokter.getValueAt(tbDokter.getSelectedRow(),6).toString()+"/kunjungan/"+tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString();;
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                    headers.add("X-Signature",api.getHmac());
                    headers.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
                    requestEntity = new HttpEntity(headers);
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.DELETE, requestEntity, String.class).getBody());
                    nameNode = root.path("metaData");
                    System.out.println("code : "+nameNode.path("code").asText());
                    System.out.println("message : "+nameNode.path("message").asText());
                    if(nameNode.path("code").asText().equals("200")){
                        Sequel.queryu2("delete from pcare_tindakan_ralan_diberikan where "+
                            "no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()+"' "+
                            "and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString()+"' "+
                            "and tgl_perawatan='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()+"' "+
                            "and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()+"'");
                        Sequel.queryu2("delete from rawat_jl_dr where "+
                            "no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()+"' "+
                            "and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString()+"' "+
                            "and tgl_perawatan='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()+"' "+
                            "and jam_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()+"'");
                        Sequel.queryu2("delete from rawat_jl_pr where "+
                            "no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()+"' "+
                            "and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString()+"' "+
                            "and tgl_perawatan='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()+"' "+
                            "and jam_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()+"'");
                        Sequel.queryu2("delete from rawat_jl_drpr where "+
                            "no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()+"' "+
                            "and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),7).toString()+"' "+
                            "and tgl_perawatan='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()+"' "+
                            "and jam_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()+"'");
                        tampil();
                    }else{
                        JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                    }
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(null,"Koneksi ke server PCare terputus...!");
                    }else if(ex.toString().contains("500")){
                        JOptionPane.showMessageDialog(null,"Server PCare baru ngambek broooh...!");
                    }else if(ex.toString().contains("401")){
                        JOptionPane.showMessageDialog(null,"Username/Password salah. Lupa password? Wani piro...!");
                    }else if(ex.toString().contains("408")){
                        JOptionPane.showMessageDialog(null,"Time out, hayati lelah baaaang...!");
                    }else if(ex.toString().contains("424")){
                        JOptionPane.showMessageDialog(null,"Ambil data masternya yang bener dong coy...!");
                    }else if(ex.toString().contains("412")){
                        JOptionPane.showMessageDialog(null,"Tidak sesuai kondisi. Aku, kamu end...!");
                    }else if(ex.toString().contains("204")){
                        JOptionPane.showMessageDialog(null,"Data tidak ditemukan...!");
                    }
                } 
            }            
        }else{            
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data peserta...!!!!");
            TCari.requestFocus();
        }   
    }else if(TabRawat.getSelectedIndex()==1){
        if(tbDokter2.getSelectedRow()!= -1){
            if(Sequel.cariRegistrasi(tbDokter2.getValueAt(tbDokter2.getSelectedRow(),3).toString())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                try {
                    URL = link+"/tindakan/"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),6).toString()+"/kunjungan/"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),2).toString();;
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("X-cons-id",prop.getProperty("CONSIDAPIPCARE"));
                    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));            
                    headers.add("X-Signature",api.getHmac());
                    headers.add("X-Authorization","Basic "+Base64.encodeBase64String(otorisasi.getBytes()));
                    requestEntity = new HttpEntity(headers);
                    root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.DELETE, requestEntity, String.class).getBody());
                    nameNode = root.path("metaData");
                    System.out.println("code : "+nameNode.path("code").asText());
                    System.out.println("message : "+nameNode.path("message").asText());
                    if(nameNode.path("code").asText().equals("200")){
                        ttljmdokter=Double.parseDouble(tbDokter2.getValueAt(tbDokter2.getSelectedRow(),11).toString());
                        ttljmperawat=Double.parseDouble(tbDokter2.getValueAt(tbDokter2.getSelectedRow(),12).toString());
                        ttlkso=Double.parseDouble(tbDokter2.getValueAt(tbDokter2.getSelectedRow(),13).toString());
                        ttlpendapatan=Double.parseDouble(tbDokter2.getValueAt(tbDokter2.getSelectedRow(),15).toString());
                        Sequel.queryu("delete from tampjurnal");
                        if(ttlpendapatan>0){
                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Tindakan_Ranap+"','Suspen Piutang Tindakan Ranap','0','"+ttlpendapatan+"'","Rekening");
                            Sequel.menyimpan("tampjurnal","'"+Tindakan_Ranap+"','Pendapatan Tindakan Rawat Inap','"+ttlpendapatan+"','0'","Rekening");
                        }
                        if(ttljmdokter>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Tindakan_Ranap+"','Beban Jasa Medik Dokter Tindakan Ranap','0','"+ttljmdokter+"'","Rekening");
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Tindakan_Ranap+"','Utang Jasa Medik Dokter Tindakan Ranap','"+ttljmdokter+"','0'","Rekening");
                        }
                        if(ttljmperawat>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Beban Jasa Medik Paramedis Tindakan Ranap','0','"+ttljmperawat+"'","Rekening");
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Tindakan_Ranap+"','Utang Jasa Medik Paramedis Tindakan Ranap','"+ttljmperawat+"','0'","Rekening");
                        }
                        if(ttlkso>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_KSO_Tindakan_Ranap+"','Beban KSO Tindakan Ranap','0','"+ttlkso+"'","Rekening");
                            Sequel.menyimpan("tampjurnal","'"+Utang_KSO_Tindakan_Ranap+"','Utang KSO Tindakan Ranap','"+ttlkso+"','0'","Rekening");
                        }
                        jur.simpanJurnal(tbDokter2.getValueAt(tbDokter2.getSelectedRow(),3).toString(),Valid.SetTgl(Tanggal.getSelectedItem()+""),"U","PEMBATALAN PEMBERIAN TINDAKAN RAWAT INAP PASIEN, OLEH "+akses.getkode());

                        Sequel.queryu2("delete from pcare_tindakan_ranap_diberikan where "+
                            "no_rawat='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),3).toString()+"' "+
                            "and kd_jenis_prw='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),7).toString()+"' "+
                            "and tgl_perawatan='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),0).toString()+"' "+
                            "and jam='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),1).toString()+"'");
                        Sequel.queryu2("delete from rawat_inap_dr where "+
                            "no_rawat='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),3).toString()+"' "+
                            "and kd_jenis_prw='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),7).toString()+"' "+
                            "and tgl_perawatan='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),0).toString()+"' "+
                            "and jam_rawat='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),1).toString()+"'");
                        Sequel.queryu2("delete from rawat_inap_pr where "+
                            "no_rawat='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),3).toString()+"' "+
                            "and kd_jenis_prw='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),7).toString()+"' "+
                            "and tgl_perawatan='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),0).toString()+"' "+
                            "and jam_rawat='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),1).toString()+"'");
                        Sequel.queryu2("delete from rawat_inap_drpr where "+
                            "no_rawat='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),3).toString()+"' "+
                            "and kd_jenis_prw='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),7).toString()+"' "+
                            "and tgl_perawatan='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),0).toString()+"' "+
                            "and jam_rawat='"+tbDokter2.getValueAt(tbDokter2.getSelectedRow(),1).toString()+"'");
                        tampil2();
                    }else{
                        JOptionPane.showMessageDialog(null,nameNode.path("message").asText());
                    }
                }catch (Exception ex) {
                    System.out.println("Notifikasi Bridging : "+ex);
                    if(ex.toString().contains("UnknownHostException")){
                        JOptionPane.showMessageDialog(null,"Koneksi ke server PCare terputus...!");
                    }else if(ex.toString().contains("500")){
                        JOptionPane.showMessageDialog(null,"Server PCare baru ngambek broooh...!");
                    }else if(ex.toString().contains("401")){
                        JOptionPane.showMessageDialog(null,"Username/Password salah. Lupa password? Wani piro...!");
                    }else if(ex.toString().contains("408")){
                        JOptionPane.showMessageDialog(null,"Time out, hayati lelah baaaang...!");
                    }else if(ex.toString().contains("424")){
                        JOptionPane.showMessageDialog(null,"Ambil data masternya yang bener dong coy...!");
                    }else if(ex.toString().contains("412")){
                        JOptionPane.showMessageDialog(null,"Tidak sesuai kondisi. Aku, kamu end...!");
                    }else if(ex.toString().contains("204")){
                        JOptionPane.showMessageDialog(null,"Data tidak ditemukan...!");
                    }
                } 
            }  
        }else{            
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data peserta...!!!!");
            TCari.requestFocus();
        }
    }
         
}//GEN-LAST:event_BtnHapusActionPerformed

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void TanggalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalKeyPressed

    private void tbDokter2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokter2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDokter2MouseClicked

    private void tbDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDokter2KeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            PCareDataPemberianTindakan dialog = new PCareDataPemberianTindakan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnPrint;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tanggal;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private javax.swing.JPanel jPanel2;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelisi1;
    private widget.Table tbDokter;
    private widget.Table tbDokter2;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                    "select pcare_tindakan_ralan_diberikan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pcare_tindakan_ralan_diberikan.noKunjungan,pcare_tindakan_ralan_diberikan.kdTindakanSK,"+
                    "pcare_tindakan_ralan_diberikan.tgl_perawatan,pcare_tindakan_ralan_diberikan.jam,"+
                    "pcare_tindakan_ralan_diberikan.kd_jenis_prw,jns_perawatan.nm_perawatan,pcare_tindakan_ralan_diberikan.material,"+
                    "pcare_tindakan_ralan_diberikan.bhp,pcare_tindakan_ralan_diberikan.tarif_tindakandr,"+
                    "pcare_tindakan_ralan_diberikan.tarif_tindakanpr,pcare_tindakan_ralan_diberikan.kso,"+
                    "pcare_tindakan_ralan_diberikan.menejemen,pcare_tindakan_ralan_diberikan.biaya_rawat from "+
                    "pcare_tindakan_ralan_diberikan inner join reg_periksa inner join pasien inner join jns_perawatan on "+
                    "pcare_tindakan_ralan_diberikan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and pcare_tindakan_ralan_diberikan.kd_jenis_prw=jns_perawatan.kd_jenis_prw where "+
                    "pcare_tindakan_ralan_diberikan.tgl_perawatan between ? and ? and pcare_tindakan_ralan_diberikan.no_rawat like ? or "+
                    "pcare_tindakan_ralan_diberikan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "pcare_tindakan_ralan_diberikan.tgl_perawatan between ? and ? and pasien.nm_pasien like ? or "+
                    "pcare_tindakan_ralan_diberikan.tgl_perawatan between ? and ? and pcare_tindakan_ralan_diberikan.kd_jenis_prw like ? or "+
                    "pcare_tindakan_ralan_diberikan.tgl_perawatan between ? and ? and jns_perawatan.nm_perawatan like ? order by pcare_tindakan_ralan_diberikan.tgl_perawatan ");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("tgl_perawatan"),rs.getString("jam"),rs.getString("noKunjungan"),
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("kdTindakanSK"),rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan"),
                        rs.getString("material"),rs.getString("bhp"),rs.getString("tarif_tindakandr"),
                        rs.getString("tarif_tindakanpr"),rs.getString("kso"),rs.getString("menejemen"),
                        rs.getString("biaya_rawat")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif Ketersediaan : "+e);
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
        LCount.setText(""+tabMode.getRowCount());
    }
    
    private void tampil2() {
        Valid.tabelKosong(tabMode2);
        try{
            ps=koneksi.prepareStatement(
                    "select pcare_tindakan_ranap_diberikan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                    "pcare_tindakan_ranap_diberikan.noKunjungan,pcare_tindakan_ranap_diberikan.kdTindakanSK,"+
                    "pcare_tindakan_ranap_diberikan.tgl_perawatan,pcare_tindakan_ranap_diberikan.jam,"+
                    "pcare_tindakan_ranap_diberikan.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,pcare_tindakan_ranap_diberikan.material,"+
                    "pcare_tindakan_ranap_diberikan.bhp,pcare_tindakan_ranap_diberikan.tarif_tindakandr,"+
                    "pcare_tindakan_ranap_diberikan.tarif_tindakanpr,pcare_tindakan_ranap_diberikan.kso,"+
                    "pcare_tindakan_ranap_diberikan.menejemen,pcare_tindakan_ranap_diberikan.biaya_rawat from "+
                    "pcare_tindakan_ranap_diberikan inner join reg_periksa inner join pasien inner join jns_perawatan_inap on "+
                    "pcare_tindakan_ranap_diberikan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "and pcare_tindakan_ranap_diberikan.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw where "+
                    "pcare_tindakan_ranap_diberikan.tgl_perawatan between ? and ? and pcare_tindakan_ranap_diberikan.no_rawat like ? or "+
                    "pcare_tindakan_ranap_diberikan.tgl_perawatan between ? and ? and reg_periksa.no_rkm_medis like ? or "+
                    "pcare_tindakan_ranap_diberikan.tgl_perawatan between ? and ? and pasien.nm_pasien like ? or "+
                    "pcare_tindakan_ranap_diberikan.tgl_perawatan between ? and ? and pcare_tindakan_ranap_diberikan.kd_jenis_prw like ? or "+
                    "pcare_tindakan_ranap_diberikan.tgl_perawatan between ? and ? and jns_perawatan_inap.nm_perawatan like ? order by pcare_tindakan_ranap_diberikan.tgl_perawatan ");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode2.addRow(new Object[]{
                        rs.getString("tgl_perawatan"),rs.getString("jam"),rs.getString("noKunjungan"),
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("kdTindakanSK"),rs.getString("kd_jenis_prw"),rs.getString("nm_perawatan"),
                        rs.getString("material"),rs.getString("bhp"),rs.getString("tarif_tindakandr"),
                        rs.getString("tarif_tindakanpr"),rs.getString("kso"),rs.getString("menejemen"),
                        rs.getString("biaya_rawat")
                    });
                }
            } catch (Exception e) {
                System.out.println("Notif Ketersediaan : "+e);
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
        LCount.setText(""+tabMode2.getRowCount());
    }
    
    public void isCek(){
        BtnHapus.setEnabled(akses.getpcare_pemberian_tindakan());
        BtnPrint.setEnabled(akses.getpcare_pemberian_tindakan());
    }
 
    public void setNoRm(String norwt,Date tgl1,Date tgl2) {
        TCari.setText(norwt);
        DTPCari1.setDate(tgl1);
        DTPCari2.setDate(tgl2);
    }
}

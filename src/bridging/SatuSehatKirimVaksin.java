/*
  By Mas Elkhanza
 */

package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author dosen
 */
public final class SatuSehatKirimVaksin extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;   
    private int i=0;
    private String link="",json="",idpasien="",iddokter="";
    private ApiSatuSehat api=new ApiSatuSehat();
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;
    private SatuSehatCekNIK cekViaSatuSehat=new SatuSehatCekNIK();   
    private StringBuilder htmlContent;   
    
    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public SatuSehatKirimVaksin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new String[]{
                "P","Tanggal Registrasi","No.Rawat","No.RM","Nama Pasien","No.KTP Pasien","Stts Rawat","Stts Lanjut",
                "ID Encounter","Vaksin Code","Vaksin System","Kode Vaksin","Vaksin Display","Route Code","Route System",
                "Route Display","Dose Code","Dose System","Dose Unit","No.Batch","Tanggal & Jam Beri","Jml/Kps","Dosis/No",
                "ID Lokasi Satu Sehat","Nama Unit/Poli","Dokter Penanggung Jawab","No.KTP Praktisi","ID Imunisasi Satu Sehat",
                "No.Faktur"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                 java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbKamar.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 29; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(150);
            }else if(i==2){
                column.setPreferredWidth(105);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(150);
            }else if(i==5){
                column.setPreferredWidth(110);
            }else if(i==6){
                column.setPreferredWidth(63);
            }else if(i==7){
                column.setPreferredWidth(63);
            }else if(i==8){
                column.setPreferredWidth(210);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(170);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(170);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(170);
            }else if(i==15){
                column.setPreferredWidth(170);
            }else if(i==16){
                column.setPreferredWidth(60);
            }else if(i==17){
                column.setPreferredWidth(170);
            }else if(i==18){
                column.setPreferredWidth(60);
            }else if(i==19){
                column.setPreferredWidth(70);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(50);
            }else if(i==22){
                column.setPreferredWidth(50);
            }else if(i==23){
                column.setPreferredWidth(210);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(150);
            }else if(i==26){
                column.setPreferredWidth(105);
            }else if(i==27){
                column.setPreferredWidth(210);
            }else if(i==28){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
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
        
        try {
            link=koneksiDB.URLFHIRSATUSEHAT();
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }   
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppPilihSemua = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        LoadHTML = new widget.editorpane();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnAll = new widget.Button();
        BtnKirim = new widget.Button();
        BtnUpdate = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel16 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

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

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengiriman Data Imunisasi Satu Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setComponentPopupMenu(jPopupMenu1);
        tbKamar.setName("tbKamar"); // NOI18N
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(53, 23));
        panelGlass8.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(LCount);

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

        BtnKirim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/34.png"))); // NOI18N
        BtnKirim.setMnemonic('K');
        BtnKirim.setText("Kirim");
        BtnKirim.setToolTipText("Alt+K");
        BtnKirim.setName("BtnKirim"); // NOI18N
        BtnKirim.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKirim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKirimActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnKirim);

        BtnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/edit_f2.png"))); // NOI18N
        BtnUpdate.setMnemonic('U');
        BtnUpdate.setText("Update");
        BtnUpdate.setToolTipText("Alt+U");
        BtnUpdate.setName("BtnUpdate"); // NOI18N
        BtnUpdate.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnUpdateActionPerformed(evt);
            }
        });
        panelGlass8.add(BtnUpdate);

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
        panelGlass8.add(BtnPrint);

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

        jLabel15.setText("Tgl.Registrasi :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(85, 23));
        panelGlass9.add(jLabel15);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-01-2023" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d.");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel17);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "24-01-2023" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass9.add(DTPCari2);

        jLabel16.setText("Key Word :");
        jLabel16.setName("jLabel16"); // NOI18N
        jLabel16.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel16);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('6');
        BtnCari.setToolTipText("Alt+6");
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

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,BtnKeluar);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            try{
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal Registrasi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.KTP Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Stts Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Stts Lanjut</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID Encounter</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Vaksin Code</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Vaksin System</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Kode Vaksin</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Vaksin Display</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Route Code</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Route System</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Route Display</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dose Code</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dose System</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dose Unit</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.Batch</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Tanggal & Jam Beri</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Jml/Kps</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dosis/No</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID Lokasi Satu Sehat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Nama Unit/Poli</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>Dokter Penanggung Jawab</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>No.KTP Praktisi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center'><b>ID Imunisasi Satu Sehat</b></td>"+
                    "</tr>"
                );
                ps=koneksi.prepareStatement(
                       "select reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.no_ktp,"+
                       "reg_periksa.stts,reg_periksa.status_lanjut,satu_sehat_encounter.id_encounter,satu_sehat_mapping_vaksin.vaksin_code,satu_sehat_mapping_vaksin.vaksin_system,"+
                       "satu_sehat_mapping_vaksin.kode_brng,satu_sehat_mapping_vaksin.vaksin_display,satu_sehat_mapping_vaksin.route_code,satu_sehat_mapping_vaksin.route_system,"+
                       "satu_sehat_mapping_vaksin.route_display,satu_sehat_mapping_vaksin.dose_quantity_code,satu_sehat_mapping_vaksin.dose_quantity_system,"+
                       "satu_sehat_mapping_vaksin.dose_quantity_unit,detail_pemberian_obat.no_batch,detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,"+
                       "detail_pemberian_obat.jml,aturan_pakai.aturan,satu_sehat_mapping_lokasi_ralan.id_lokasi_satusehat,poliklinik.nm_poli,pegawai.nama,pegawai.no_ktp as ktppraktisi,"+
                       "ifnull(satu_sehat_immunization.id_immunization,'') as id_immunization,detail_pemberian_obat.no_faktur from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                       "inner join satu_sehat_encounter on satu_sehat_encounter.no_rawat=reg_periksa.no_rawat "+
                       "inner join detail_pemberian_obat on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                       "inner join satu_sehat_mapping_vaksin on satu_sehat_mapping_vaksin.kode_brng=detail_pemberian_obat.kode_brng "+
                       "inner join aturan_pakai on aturan_pakai.tgl_perawatan=detail_pemberian_obat.tgl_perawatan and aturan_pakai.jam=detail_pemberian_obat.jam and "+
                       "aturan_pakai.no_rawat=detail_pemberian_obat.no_rawat and aturan_pakai.kode_brng=detail_pemberian_obat.kode_brng "+
                       "inner join satu_sehat_mapping_lokasi_ralan on satu_sehat_mapping_lokasi_ralan.kd_poli=reg_periksa.kd_poli "+
                       "inner join poliklinik on poliklinik.kd_poli=satu_sehat_mapping_lokasi_ralan.kd_poli "+
                       "inner join pegawai on reg_periksa.kd_dokter=pegawai.nik "+
                       "left join satu_sehat_immunization on satu_sehat_immunization.no_rawat=detail_pemberian_obat.no_rawat and satu_sehat_immunization.tgl_perawatan=detail_pemberian_obat.tgl_perawatan and "+
                       "satu_sehat_immunization.jam=detail_pemberian_obat.jam and satu_sehat_immunization.kode_brng=detail_pemberian_obat.kode_brng and "+
                       "satu_sehat_immunization.no_batch=detail_pemberian_obat.no_batch and satu_sehat_immunization.no_faktur=detail_pemberian_obat.no_faktur "+
                       "where detail_pemberian_obat.no_batch<>'' and reg_periksa.tgl_registrasi between ? and ? "+
                       (TCari.getText().equals("")?"":"and (reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "+
                       "pasien.nm_pasien like ? or pasien.no_ktp like ? or satu_sehat_mapping_vaksin.kode_brng like ? or satu_sehat_mapping_vaksin.vaksin_display like ? or "+
                       "reg_periksa.stts like ? or reg_periksa.status_lanjut like ?)")+" order by detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam");
                try {
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    if(!TCari.getText().equals("")){
                        ps.setString(3,"%"+TCari.getText()+"%");
                        ps.setString(4,"%"+TCari.getText()+"%");
                        ps.setString(5,"%"+TCari.getText()+"%");
                        ps.setString(6,"%"+TCari.getText()+"%");
                        ps.setString(7,"%"+TCari.getText()+"%");
                        ps.setString(8,"%"+TCari.getText()+"%");
                        ps.setString(9,"%"+TCari.getText()+"%");
                        ps.setString(10,"%"+TCari.getText()+"%");
                    }
                    rs=ps.executeQuery();
                    while(rs.next()){
                        htmlContent.append(
                            "<tr class='isi'>"+
                               "<td valign='top'>"+rs.getString("tgl_registrasi")+"T"+rs.getString("jam_reg")+"+07:00"+"</td>"+
                               "<td valign='top'>"+rs.getString("no_rawat")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_rkm_medis")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_ktp")+"</td>"+
                               "<td valign='top'>"+rs.getString("stts")+"</td>"+
                               "<td valign='top'>"+rs.getString("status_lanjut")+"</td>"+
                               "<td valign='top'>"+rs.getString("id_encounter")+"</td>"+
                               "<td valign='top'>"+rs.getString("vaksin_code")+"</td>"+
                               "<td valign='top'>"+rs.getString("vaksin_system")+"</td>"+
                               "<td valign='top'>"+rs.getString("kode_brng")+"</td>"+
                               "<td valign='top'>"+rs.getString("vaksin_display")+"</td>"+
                               "<td valign='top'>"+rs.getString("route_code")+"</td>"+
                               "<td valign='top'>"+rs.getString("route_system")+"</td>"+
                               "<td valign='top'>"+rs.getString("route_display")+"</td>"+
                               "<td valign='top'>"+rs.getString("dose_quantity_code")+"</td>"+
                               "<td valign='top'>"+rs.getString("dose_quantity_system")+"</td>"+
                               "<td valign='top'>"+rs.getString("dose_quantity_unit")+"</td>"+
                               "<td valign='top'>"+rs.getString("no_batch")+"</td>"+
                               "<td valign='top'>"+rs.getString("tgl_perawatan")+"T"+rs.getString("jam")+"+07:00"+"</td>"+
                               "<td valign='top'>"+rs.getString("jml")+"</td>"+
                               "<td valign='top'>"+rs.getString("aturan")+"</td>"+
                               "<td valign='top'>"+rs.getString("id_lokasi_satusehat")+"</td>"+
                               "<td valign='top'>"+rs.getString("nm_poli")+"</td>"+
                               "<td valign='top'>"+rs.getString("nama")+"</td>"+
                               "<td valign='top'>"+rs.getString("ktppraktisi")+"</td>"+
                               "<td valign='top'>"+rs.getString("id_immunization")+"</td>"+
                            "</tr>");
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
                LoadHTML.setText(
                    "<html>"+
                      "<table width='3000px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                    ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                    ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                    ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                    ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
                );
                bg.close();

                File f = new File("DataSatuSehatImunisasi.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='3000px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA PENGIRIMAN SATU SEHAT IMUNISASIbr><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }           
        }
        this.setCursor(Cursor.getDefaultCursor());       
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbKamar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        tampil();
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,TCari,BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnKirimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKirimActionPerformed
        for(i=0;i<tbKamar.getRowCount();i++){
            if(tbKamar.getValueAt(i,0).toString().equals("true")&&(!tbKamar.getValueAt(i,5).toString().equals(""))&&(!tbKamar.getValueAt(i,8).toString().equals(""))&&tbKamar.getValueAt(i,27).toString().equals("")){
                try {
                    idpasien=cekViaSatuSehat.tampilIDPasien(tbKamar.getValueAt(i,5).toString());
                    iddokter=cekViaSatuSehat.tampilIDParktisi(tbKamar.getValueAt(i,26).toString());
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Immunization\"," +
                                    "\"status\": \"completed\"," +
                                    "\"vaccineCode\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \""+tbKamar.getValueAt(i,10).toString()+"\"," +
                                                "\"code\": \""+tbKamar.getValueAt(i,9).toString()+"\"," +
                                                "\"display\": \""+tbKamar.getValueAt(i,12).toString()+"\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"patient\": {" +
                                        "\"reference\": \"Patient/"+idpasien+"\"" +
                                    "}," +
                                    "\"encounter\": {" +
                                        "\"reference\": \"Encounter/"+tbKamar.getValueAt(i,8).toString()+"\"" +
                                    "}," +
                                    "\"occurrenceDateTime\": \""+tbKamar.getValueAt(i,20).toString()+"\"," +
                                    "\"recorded\": \""+tbKamar.getValueAt(i,20).toString()+"\"," +
                                    "\"primarySource\": true," +
                                    "\"location\": {" +
                                        "\"reference\": \"Location/"+tbKamar.getValueAt(i,23).toString()+"\"" +
                                    "}," +
                                    "\"lotNumber\": \""+tbKamar.getValueAt(i,19).toString()+"\"," +
                                    "\"route\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \""+tbKamar.getValueAt(i,14).toString()+"\"," +
                                                "\"code\": \""+tbKamar.getValueAt(i,13).toString()+"\"," +
                                                "\"display\": \""+tbKamar.getValueAt(i,15).toString()+"\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"doseQuantity\": {" +
                                        "\"value\": "+tbKamar.getValueAt(i,21).toString()+"," +
                                        "\"unit\": \""+tbKamar.getValueAt(i,18).toString()+"\"," +
                                        "\"system\": \""+tbKamar.getValueAt(i,17).toString()+"\"," +
                                        "\"code\": \""+tbKamar.getValueAt(i,16).toString()+"\"" +
                                    "}," +
                                    "\"performer\": [" +
                                        "{" +
                                            "\"actor\": {" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "}" +
                                    "]" +
                                    (tbKamar.getValueAt(i,22).toString().equals("")?"":",\"protocolApplied\" : [{\"doseNumberPositiveInt\" : "+tbKamar.getValueAt(i,22).toString().toLowerCase().replaceAll("dosis","").replaceAll(" ","")+"}]")+
                                "}";
                        System.out.println("URL : "+link+"/Immunization");
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Immunization", HttpMethod.POST, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                        root = mapper.readTree(json);
                        response = root.path("id");
                        if(!response.asText().equals("")){
                            Sequel.menyimpan("satu_sehat_immunization","?,?,?,?,?,?,?","Imunisasi/Vaksin",7,new String[]{
                                tbKamar.getValueAt(i,2).toString(),tbKamar.getValueAt(i,20).toString().substring(0,10),tbKamar.getValueAt(i,20).toString().substring(11,19), 
                                tbKamar.getValueAt(i,11).toString(),tbKamar.getValueAt(i,19).toString(),tbKamar.getValueAt(i,28).toString(),response.asText()
                            });
                        }
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
            }
        }
        tampil();
    }//GEN-LAST:event_BtnKirimActionPerformed

    private void ppPilihSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihSemuaActionPerformed
        for(i=0;i<tbKamar.getRowCount();i++){
            tbKamar.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppPilihSemuaActionPerformed

    private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
        for(i=0;i<tbKamar.getRowCount();i++){
            tbKamar.setValueAt(false,i,0);
        }
    }//GEN-LAST:event_ppBersihkanActionPerformed

    private void BtnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnUpdateActionPerformed
        for(i=0;i<tbKamar.getRowCount();i++){
            if(tbKamar.getValueAt(i,0).toString().equals("true")&&(!tbKamar.getValueAt(i,5).toString().equals(""))&&(!tbKamar.getValueAt(i,8).toString().equals(""))&&(!tbKamar.getValueAt(i,27).toString().equals(""))){
                try {
                    idpasien=cekViaSatuSehat.tampilIDPasien(tbKamar.getValueAt(i,5).toString());
                    try{
                        headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                        json = "{" +
                                    "\"resourceType\": \"Immunization\"," +
                                    "\"id\": \""+tbKamar.getValueAt(i,27).toString()+"\"," +
                                    "\"status\": \"completed\"," +
                                    "\"vaccineCode\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \""+tbKamar.getValueAt(i,10).toString()+"\"," +
                                                "\"code\": \""+tbKamar.getValueAt(i,9).toString()+"\"," +
                                                "\"display\": \""+tbKamar.getValueAt(i,12).toString()+"\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"patient\": {" +
                                        "\"reference\": \"Patient/"+idpasien+"\"" +
                                    "}," +
                                    "\"encounter\": {" +
                                        "\"reference\": \"Encounter/"+tbKamar.getValueAt(i,8).toString()+"\"" +
                                    "}," +
                                    "\"occurrenceDateTime\": \""+tbKamar.getValueAt(i,20).toString()+"\"," +
                                    "\"recorded\": \""+tbKamar.getValueAt(i,20).toString()+"\"," +
                                    "\"primarySource\": true," +
                                    "\"location\": {" +
                                        "\"reference\": \"Location/"+tbKamar.getValueAt(i,23).toString()+"\"" +
                                    "}," +
                                    "\"lotNumber\": \""+tbKamar.getValueAt(i,19).toString()+"\"," +
                                    "\"route\": {" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \""+tbKamar.getValueAt(i,14).toString()+"\"," +
                                                "\"code\": \""+tbKamar.getValueAt(i,13).toString()+"\"," +
                                                "\"display\": \""+tbKamar.getValueAt(i,15).toString()+"\"" +
                                            "}" +
                                        "]" +
                                    "}," +
                                    "\"doseQuantity\": {" +
                                        "\"value\": "+tbKamar.getValueAt(i,21).toString()+"," +
                                        "\"unit\": \""+tbKamar.getValueAt(i,18).toString()+"\"," +
                                        "\"system\": \""+tbKamar.getValueAt(i,17).toString()+"\"," +
                                        "\"code\": \""+tbKamar.getValueAt(i,16).toString()+"\"" +
                                    "}," +
                                    "\"performer\": [" +
                                        "{" +
                                            "\"actor\": {" +
                                                "\"reference\": \"Practitioner/"+iddokter+"\"" +
                                            "}" +
                                        "}" +
                                    "]" +
                                    (tbKamar.getValueAt(i,22).toString().equals("")?"":",\"protocolApplied\" : [{\"doseNumberPositiveInt\" : "+tbKamar.getValueAt(i,22).toString().toLowerCase().replaceAll("dosis","").replaceAll(" ","")+"}]")+
                                "}";
                        System.out.println("URL : "+link+"/Immunization/"+tbKamar.getValueAt(i,27).toString());
                        System.out.println("Request JSON : "+json);
                        requestEntity = new HttpEntity(json,headers);
                        json=api.getRest().exchange(link+"/Immunization/"+tbKamar.getValueAt(i,27).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                        System.out.println("Result JSON : "+json);
                    }catch(Exception e){
                        System.out.println("Notifikasi Bridging : "+e);
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                }
            }
        }
        tampil();
    }//GEN-LAST:event_BtnUpdateActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SatuSehatKirimVaksin dialog = new SatuSehatKirimVaksin(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKirim;
    private widget.Button BtnPrint;
    private widget.Button BtnUpdate;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel15;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppPilihSemua;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables
    
    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            ps=koneksi.prepareStatement(
                   "select reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.no_ktp,"+
                   "reg_periksa.stts,reg_periksa.status_lanjut,satu_sehat_encounter.id_encounter,satu_sehat_mapping_vaksin.vaksin_code,satu_sehat_mapping_vaksin.vaksin_system,"+
                   "satu_sehat_mapping_vaksin.kode_brng,satu_sehat_mapping_vaksin.vaksin_display,satu_sehat_mapping_vaksin.route_code,satu_sehat_mapping_vaksin.route_system,"+
                   "satu_sehat_mapping_vaksin.route_display,satu_sehat_mapping_vaksin.dose_quantity_code,satu_sehat_mapping_vaksin.dose_quantity_system,"+
                   "satu_sehat_mapping_vaksin.dose_quantity_unit,detail_pemberian_obat.no_batch,detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,"+
                   "detail_pemberian_obat.jml,aturan_pakai.aturan,satu_sehat_mapping_lokasi_ralan.id_lokasi_satusehat,poliklinik.nm_poli,pegawai.nama,pegawai.no_ktp as ktppraktisi,"+
                   "ifnull(satu_sehat_immunization.id_immunization,'') as id_immunization,detail_pemberian_obat.no_faktur from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "inner join satu_sehat_encounter on satu_sehat_encounter.no_rawat=reg_periksa.no_rawat "+
                   "inner join detail_pemberian_obat on detail_pemberian_obat.no_rawat=reg_periksa.no_rawat "+
                   "inner join satu_sehat_mapping_vaksin on satu_sehat_mapping_vaksin.kode_brng=detail_pemberian_obat.kode_brng "+
                   "inner join aturan_pakai on aturan_pakai.tgl_perawatan=detail_pemberian_obat.tgl_perawatan and aturan_pakai.jam=detail_pemberian_obat.jam and "+
                   "aturan_pakai.no_rawat=detail_pemberian_obat.no_rawat and aturan_pakai.kode_brng=detail_pemberian_obat.kode_brng "+
                   "inner join satu_sehat_mapping_lokasi_ralan on satu_sehat_mapping_lokasi_ralan.kd_poli=reg_periksa.kd_poli "+
                   "inner join poliklinik on poliklinik.kd_poli=satu_sehat_mapping_lokasi_ralan.kd_poli "+
                   "inner join pegawai on reg_periksa.kd_dokter=pegawai.nik "+
                   "left join satu_sehat_immunization on satu_sehat_immunization.no_rawat=detail_pemberian_obat.no_rawat and satu_sehat_immunization.tgl_perawatan=detail_pemberian_obat.tgl_perawatan and "+
                   "satu_sehat_immunization.jam=detail_pemberian_obat.jam and satu_sehat_immunization.kode_brng=detail_pemberian_obat.kode_brng and "+
                   "satu_sehat_immunization.no_batch=detail_pemberian_obat.no_batch and satu_sehat_immunization.no_faktur=detail_pemberian_obat.no_faktur "+
                   "where detail_pemberian_obat.no_batch<>'' and reg_periksa.tgl_registrasi between ? and ? "+
                   (TCari.getText().equals("")?"":"and (reg_periksa.no_rawat like ? or reg_periksa.no_rkm_medis like ? or "+
                   "pasien.nm_pasien like ? or pasien.no_ktp like ? or satu_sehat_mapping_vaksin.kode_brng like ? or satu_sehat_mapping_vaksin.vaksin_display like ? or "+
                   "reg_periksa.stts like ? or reg_periksa.status_lanjut like ?)")+" order by detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam");
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                if(!TCari.getText().equals("")){
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                    ps.setString(8,"%"+TCari.getText()+"%");
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("tgl_registrasi")+"T"+rs.getString("jam_reg")+"+07:00",rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),
                        rs.getString("no_ktp"),rs.getString("stts"),rs.getString("status_lanjut"),rs.getString("id_encounter"),rs.getString("vaksin_code"),rs.getString("vaksin_system"),
                        rs.getString("kode_brng"),rs.getString("vaksin_display"),rs.getString("route_code"),rs.getString("route_system"),rs.getString("route_display"),rs.getString("dose_quantity_code"),
                        rs.getString("dose_quantity_system"),rs.getString("dose_quantity_unit"),rs.getString("no_batch"),rs.getString("tgl_perawatan")+"T"+rs.getString("jam")+"+07:00",
                        rs.getString("jml"),rs.getString("aturan"),rs.getString("id_lokasi_satusehat"),rs.getString("nm_poli"),rs.getString("nama"),rs.getString("ktppraktisi"),
                        rs.getString("id_immunization"),rs.getString("no_faktur")
                    });
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
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void isCek(){
        BtnKirim.setEnabled(akses.getsatu_sehat_kirim_Immunization());
        BtnPrint.setEnabled(akses.getsatu_sehat_kirim_Immunization());
    }
    
    public JTable getTable(){
        return tbKamar;
    }
}

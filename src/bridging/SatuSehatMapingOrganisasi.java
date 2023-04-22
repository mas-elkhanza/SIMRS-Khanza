/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bridging;
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
import kepegawaian.DlgCariDepartemen;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author dosen
 */
public final class SatuSehatMapingOrganisasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;    
    private int i=0;
    private DlgCariDepartemen poli=new DlgCariDepartemen(null,false);
    private String link="",json="";
    private ApiSatuSehat api=new ApiSatuSehat();
    private HttpHeaders headers ;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode response;

    /** Creates new form DlgJnsPerawatanRalan
     * @param parent
     * @param modal */
    public SatuSehatMapingOrganisasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
            "Kode Departemen","Nama Departemen","ID Organisasi Satu Sehat"}){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 3; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(360);
            }else if(i==2){
                column.setPreferredWidth(230);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());

        KodeDepartemen.setDocument(new batasInput((byte)5).getKata(KodeDepartemen)); 
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
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){                    
                    KodeDepartemen.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),0).toString());
                    NamaDepartemen.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                }
                KodeDepartemen.requestFocus();
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
        
        try {
            link=koneksiDB.URLFHIRSATUSEHAT();
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

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        FormInput = new widget.PanelBiasa();
        jLabel4 = new widget.Label();
        KodeDepartemen = new widget.TextBox();
        NamaDepartemen = new widget.TextBox();
        btnDepartemenRS = new widget.Button();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Mapping Organisasi Satu Sehat ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnBatal);

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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass8.add(BtnEdit);

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

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(450, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 44));
        FormInput.setLayout(null);

        jLabel4.setText("Departemen :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 10, 86, 23);

        KodeDepartemen.setEditable(false);
        KodeDepartemen.setHighlighter(null);
        KodeDepartemen.setName("KodeDepartemen"); // NOI18N
        FormInput.add(KodeDepartemen);
        KodeDepartemen.setBounds(89, 10, 80, 23);

        NamaDepartemen.setEditable(false);
        NamaDepartemen.setHighlighter(null);
        NamaDepartemen.setName("NamaDepartemen"); // NOI18N
        FormInput.add(NamaDepartemen);
        NamaDepartemen.setBounds(171, 10, 530, 23);

        btnDepartemenRS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDepartemenRS.setMnemonic('1');
        btnDepartemenRS.setToolTipText("Alt+1");
        btnDepartemenRS.setName("btnDepartemenRS"); // NOI18N
        btnDepartemenRS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepartemenRSActionPerformed(evt);
            }
        });
        btnDepartemenRS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDepartemenRSKeyPressed(evt);
            }
        });
        FormInput.add(btnDepartemenRS);
        btnDepartemenRS.setBounds(700, 10, 28, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDepartemenRSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDepartemenRSActionPerformed
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
}//GEN-LAST:event_btnDepartemenRSActionPerformed

    private void btnDepartemenRSKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDepartemenRSKeyPressed
        
}//GEN-LAST:event_btnDepartemenRSKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(KodeDepartemen.getText().trim().equals("")||NamaDepartemen.getText().trim().equals("")){
            Valid.textKosong(KodeDepartemen,"Departemen/Organisasi");
        }else{
            try{
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                json = "{" +
                            "\"resourceType\": \"Organization\"," +
                            "\"active\": true," +
                            "\"identifier\": [" +
                                "{" +
                                    "\"use\": \"official\"," +
                                    "\"system\": \"http://sys-ids.kemkes.go.id/organization/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                    "\"value\": \""+KodeDepartemen.getText()+"\"" +
                                "}" +
                            "]," +
                            "\"type\": [" +
                                "{" +
                                    "\"coding\": [" +
                                        "{" +
                                            "\"system\": \"http://terminology.hl7.org/CodeSystem/organization-type\"," +
                                            "\"code\": \"dept\"," +
                                            "\"display\": \"Hospital Department\"" +
                                        "}" +
                                    "]" +
                                "}" +
                            "]," +
                            "\"name\": \""+NamaDepartemen.getText()+"\"," +
                            "\"telecom\": [" +
                                "{" +
                                    "\"system\": \"phone\"," +
                                    "\"value\": \""+akses.getkontakrs()+"\"," +
                                    "\"use\": \"work\"" +
                                "}," +
                                "{" +
                                    "\"system\": \"email\"," +
                                    "\"value\": \""+akses.getemailrs()+"\"," +
                                    "\"use\": \"work\"" +
                                "}," +
                                "{" +
                                    "\"system\": \"url\"," +
                                    "\"value\": \"www."+akses.getemailrs()+"\"," +
                                    "\"use\": \"work\"" +
                                "}" +
                            "]," +
                            "\"address\": [" +
                                "{" +
                                    "\"use\": \"work\"," +
                                    "\"type\": \"both\"," +
                                    "\"line\": [" +
                                        "\""+akses.getalamatrs()+"\"" +
                                    "]," +
                                    "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                    "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                    "\"country\": \"ID\"," +
                                    "\"extension\": [" +
                                        "{" +
                                            "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                            "\"extension\": [" +
                                                "{" +
                                                    "\"url\": \"province\"," +
                                                    "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"city\"," +
                                                    "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"district\"," +
                                                    "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                "}," +
                                                "{" +
                                                    "\"url\": \"village\"," +
                                                    "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                "}" +
                                            "]" +
                                        "}" +
                                    "]" +
                                "}" +
                            "]," +
                            "\"partOf\": {" +
                                "\"reference\": \"Organization/"+koneksiDB.IDSATUSEHAT()+"\"" +
                            "}" +
                        "}";
                System.out.println("URL : "+link+"/Organization");
                System.out.println("Request JSON : "+json);
                requestEntity = new HttpEntity(json,headers);
                json=api.getRest().exchange(link+"/Organization", HttpMethod.POST, requestEntity, String.class).getBody();
                System.out.println("Result JSON : "+json);
                root = mapper.readTree(json);
                response = root.path("id");
                if(!response.asText().equals("")){
                    if(Sequel.menyimpantf("satu_sehat_mapping_departemen","?,?","Kode Departemen",2,new String[]{
                            KodeDepartemen.getText(),response.asText()
                        })==true){
                        emptTeks();
                        tampil();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Gagal melakukan mapping organisasi ke server Satu Sehat Kemenkes");
                } 
            }catch(Exception e){
                System.out.println("Notifikasi Bridging : "+e);
                JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
            }               
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{Valid.pindah(evt,btnDepartemenRS, BtnBatal);}
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(KodeDepartemen.getText().trim().equals("")||NamaDepartemen.getText().trim().equals("")){
            Valid.textKosong(KodeDepartemen,"Departemen/Organisasi");
        }else{
            if(tbJnsPerawatan.getSelectedRow()>-1){
                try{
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                    json = "{" +
                                "\"resourceType\": \"Organization\"," +
                                "\"id\": \""+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString()+"\"," +
                                "\"active\": false," +
                                "\"identifier\": [" +
                                    "{" +
                                        "\"use\": \"official\"," +
                                        "\"system\": \"http://sys-ids.kemkes.go.id/organization/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                        "\"value\": \""+KodeDepartemen.getText()+"\"" +
                                    "}" +
                                "]," +
                                "\"type\": [" +
                                    "{" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/organization-type\"," +
                                                "\"code\": \"dept\"," +
                                                "\"display\": \"Hospital Department\"" +
                                            "}" +
                                        "]" +
                                    "}" +
                                "]," +
                                "\"name\": \""+NamaDepartemen.getText()+"\"," +
                                "\"telecom\": [" +
                                    "{" +
                                        "\"system\": \"phone\"," +
                                        "\"value\": \""+akses.getkontakrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"email\"," +
                                        "\"value\": \""+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"url\"," +
                                        "\"value\": \"www."+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}" +
                                "]," +
                                "\"address\": [" +
                                    "{" +
                                        "\"use\": \"work\"," +
                                        "\"type\": \"both\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}" +
                                "]," +
                                "\"partOf\": {" +
                                    "\"reference\": \"Organization/"+koneksiDB.IDSATUSEHAT()+"\"" +
                                "}" +
                            "}";
                    System.out.println("URL : "+link+"/Organization");
                    System.out.println("Request JSON : "+json);
                    requestEntity = new HttpEntity(json,headers);
                    json=api.getRest().exchange(link+"/Organization/"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                    System.out.println("Result JSON : "+json);
                    root = mapper.readTree(json);
                    response = root.path("id");
                    if(!response.asText().equals("")){
                        Valid.hapusTable(tabMode,KodeDepartemen,"satu_sehat_mapping_departemen","dep_id");
                        emptTeks();
                        tampil();
                    }else{
                        JOptionPane.showMessageDialog(null,"Gagal melakukan mapping organisasi ke server Satu Sehat Kemenkes");
                    } 
                }catch(Exception e){
                    System.out.println("Notifikasi Bridging : "+e);
                    JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                }  
            }                
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(KodeDepartemen.getText().trim().equals("")||NamaDepartemen.getText().trim().equals("")){
            Valid.textKosong(KodeDepartemen,"Departemen/Organisasi");
        }else{
            if(tbJnsPerawatan.getSelectedRow()>-1){
                try{
                    headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    headers.add("Authorization", "Bearer "+api.TokenSatuSehat());
                    json = "{" +
                                "\"resourceType\": \"Organization\"," +
                                "\"id\": \""+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString()+"\"," +
                                "\"active\": true," +
                                "\"identifier\": [" +
                                    "{" +
                                        "\"use\": \"official\"," +
                                        "\"system\": \"http://sys-ids.kemkes.go.id/organization/"+koneksiDB.IDSATUSEHAT()+"\"," +
                                        "\"value\": \""+KodeDepartemen.getText()+"\"" +
                                    "}" +
                                "]," +
                                "\"type\": [" +
                                    "{" +
                                        "\"coding\": [" +
                                            "{" +
                                                "\"system\": \"http://terminology.hl7.org/CodeSystem/organization-type\"," +
                                                "\"code\": \"dept\"," +
                                                "\"display\": \"Hospital Department\"" +
                                            "}" +
                                        "]" +
                                    "}" +
                                "]," +
                                "\"name\": \""+NamaDepartemen.getText()+"\"," +
                                "\"telecom\": [" +
                                    "{" +
                                        "\"system\": \"phone\"," +
                                        "\"value\": \""+akses.getkontakrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"email\"," +
                                        "\"value\": \""+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}," +
                                    "{" +
                                        "\"system\": \"url\"," +
                                        "\"value\": \"www."+akses.getemailrs()+"\"," +
                                        "\"use\": \"work\"" +
                                    "}" +
                                "]," +
                                "\"address\": [" +
                                    "{" +
                                        "\"use\": \"work\"," +
                                        "\"type\": \"both\"," +
                                        "\"line\": [" +
                                            "\""+akses.getalamatrs()+"\"" +
                                        "]," +
                                        "\"city\": \""+akses.getkabupatenrs()+"\"," +
                                        "\"postalCode\": \""+koneksiDB.KODEPOSSATUSEHAT()+"\"," +
                                        "\"country\": \"ID\"," +
                                        "\"extension\": [" +
                                            "{" +
                                                "\"url\": \"https://fhir.kemkes.go.id/r4/StructureDefinition/administrativeCode\"," +
                                                "\"extension\": [" +
                                                    "{" +
                                                        "\"url\": \"province\"," +
                                                        "\"valueCode\": \""+koneksiDB.PROPINSISATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"city\"," +
                                                        "\"valueCode\": \""+koneksiDB.KABUPATENSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"district\"," +
                                                        "\"valueCode\": \""+koneksiDB.KECAMATANSATUSEHAT()+"\"" +
                                                    "}," +
                                                    "{" +
                                                        "\"url\": \"village\"," +
                                                        "\"valueCode\": \""+koneksiDB.KELURAHANSATUSEHAT()+"\"" +
                                                    "}" +
                                                "]" +
                                            "}" +
                                        "]" +
                                    "}" +
                                "]," +
                                "\"partOf\": {" +
                                    "\"reference\": \"Organization/"+koneksiDB.IDSATUSEHAT()+"\"" +
                                "}" +
                            "}";
                    System.out.println("URL : "+link+"/Organization");
                    System.out.println("Request JSON : "+json);
                    requestEntity = new HttpEntity(json,headers);
                    json=api.getRest().exchange(link+"/Organization/"+tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString(), HttpMethod.PUT, requestEntity, String.class).getBody();
                    System.out.println("Result JSON : "+json);
                    root = mapper.readTree(json);
                    response = root.path("id");
                    if(!response.asText().equals("")){
                        if(Sequel.mengedittf("satu_sehat_mapping_departemen","dep_id=?","dep_id=?,id_organisasi_satusehat=?",3,new String[]{
                                KodeDepartemen.getText(),response.asText(),tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString()
                            })==true){
                            emptTeks();
                            tampil();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Gagal melakukan mapping organisasi ke server Satu Sehat Kemenkes");
                    } 
                }catch(Exception e){
                    System.out.println("Notifikasi Bridging : "+e);
                    JOptionPane.showMessageDialog(null,"Error Respon Satu Sehat Kemenkes : "+e);
                }  
            }                
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
                Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
                param.put("parameter","%"+TCari.getText().trim()+"%");
                Valid.MyReport("rptMapingOrganisasiSatuSehat.jasper","report","::[ Mapping Departemen/Organisasi Satu Sehat Kemenkes ]::",param);            
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
        emptTeks();
    }//GEN-LAST:event_formWindowOpened

    private void tbJnsPerawatanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbJnsPerawatanKeyReleased

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SatuSehatMapingOrganisasi dialog = new SatuSehatMapingOrganisasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox KodeDepartemen;
    private widget.Label LCount;
    private widget.TextBox NamaDepartemen;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Button btnDepartemenRS;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{
           ps=koneksi.prepareStatement(
                   "select satu_sehat_mapping_departemen.dep_id,departemen.nama,satu_sehat_mapping_departemen.id_organisasi_satusehat "+
                   "from satu_sehat_mapping_departemen inner join departemen on satu_sehat_mapping_departemen.dep_id=departemen.dep_id "+
                   (TCari.getText().equals("")?"":"where satu_sehat_mapping_departemen.dep_id like ? or departemen.nama like ? or "+
                   "satu_sehat_mapping_departemen.id_organisasi_satusehat like ? ")+" order by departemen.nama");
            try {
                if(!TCari.getText().equals("")){
                    ps.setString(1,"%"+TCari.getText()+"%");
                    ps.setString(2,"%"+TCari.getText()+"%");
                    ps.setString(3,"%"+TCari.getText()+"%");
                }
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        rs.getString("dep_id"),rs.getString("nama"),rs.getString("id_organisasi_satusehat")
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

    public void emptTeks() {
        KodeDepartemen.setText("");
        NamaDepartemen.setText("");
        KodeDepartemen.requestFocus();
    }

    private void getData() {
       if(tbJnsPerawatan.getSelectedRow()!= -1){
           KodeDepartemen.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
           NamaDepartemen.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
        }
    }
    
    public void isCek(){
        BtnSimpan.setEnabled(akses.getsatu_sehat_mapping_departemen());
        BtnHapus.setEnabled(akses.getsatu_sehat_mapping_departemen());
        BtnEdit.setEnabled(akses.getsatu_sehat_mapping_departemen());
        BtnPrint.setEnabled(akses.getsatu_sehat_mapping_departemen());
    }
    
    public JTable getTable(){
        return tbJnsPerawatan;
    }  
}

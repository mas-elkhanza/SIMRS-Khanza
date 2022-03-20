/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

/**
 *
 * @author dosen
 */
public final class BPJSCekReferensiJadwalHFIS extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private sekuel Sequel=new sekuel();
    private int i=0;
    private ApiMobileJKN api=new ApiMobileJKN();
    private String URL="",link="",utc="",requestJson="";
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private BPJSCekReferensiPoliHFIS poli=new BPJSCekReferensiPoliHFIS(null,false);
    private BPJSCekReferensiDokterHFIS dokter=new BPJSCekReferensiDokterHFIS(null,false);

    /** Creates new form DlgKamar
     * @param parent
     * @param modal */
    public BPJSCekReferensiJadwalHFIS(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(10,2);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new String[]{
            "No.","Kode Subspesialis","Nama Subspesialis","Kode Poli","Nama Poli","Kode Dokter","Nama Dokter","Hari","Nama Hari","Libur","Jadwal","Kapasitas"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbKamar.setModel(tabMode);

        //tbKamar.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbKamar.getBackground()));
        tbKamar.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbKamar.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 12; i++) {
            TableColumn column = tbKamar.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(180);
            }else if(i==3){
                column.setPreferredWidth(80);
            }else if(i==4){
                column.setPreferredWidth(180);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(180);
            }else if(i==7){
                column.setPreferredWidth(30);
            }else if(i==8){
                column.setPreferredWidth(70);
            }else if(i==9){
                column.setPreferredWidth(35);
            }else if(i==10){
                column.setPreferredWidth(70);
            }else if(i==11){
                column.setPreferredWidth(60);
            }
        }
        tbKamar.setDefaultRenderer(Object.class, new WarnaTable());
        
        poli.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(poli.getTable().getSelectedRow()!= -1){   
                    if(i==1){
                        KdPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                        NmPoli.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),2).toString());
                        KdPoli.requestFocus();
                    }else if(i==2){
                        KodePoliUpdate.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),1).toString());
                        NmPoliUpdate.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),2).toString());
                        KodeSubspesialis.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),3).toString());
                        NmSubspesialis.setText(poli.getTable().getValueAt(poli.getTable().getSelectedRow(),4).toString());
                        KodePoliUpdate.requestFocus();
                    }   
                }                  
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
        
        poli.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    poli.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){   
                    KodeDokterUpdate.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                    NmDokterUpdate.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),2).toString());
                    KodeDokterUpdate.requestFocus();
                }                  
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
        
        dokter.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    dokter.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        WindowUpdate.setSize(725,170);
        
        try {
            link=koneksiDB.URLAPIMOBILEJKN();
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

        WindowUpdate = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        BtnCloseIn5 = new widget.Button();
        BtnSimpanUpdate = new widget.Button();
        jLabel21 = new widget.Label();
        KodePoliUpdate = new widget.TextBox();
        NmPoliUpdate = new widget.TextBox();
        btnPoliklinikUpdate = new widget.Button();
        jLabel22 = new widget.Label();
        KodeDokterUpdate = new widget.TextBox();
        NmDokterUpdate = new widget.TextBox();
        btnDokterUpdate = new widget.Button();
        jLabel4 = new widget.Label();
        cmbHari = new widget.ComboBox();
        jLabel9 = new widget.Label();
        cmbJam1 = new widget.ComboBox();
        cmbMnt1 = new widget.ComboBox();
        jLabel11 = new widget.Label();
        cmbJam2 = new widget.ComboBox();
        cmbMnt2 = new widget.ComboBox();
        KodeSubspesialis = new widget.TextBox();
        NmSubspesialis = new widget.TextBox();
        jLabel23 = new widget.Label();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbKamar = new widget.Table();
        panelGlass6 = new widget.panelisi();
        jLabel19 = new widget.Label();
        KdPoli = new widget.TextBox();
        NmPoli = new widget.TextBox();
        BtnPoli = new widget.Button();
        jLabel20 = new widget.Label();
        Tanggal = new widget.Tanggal();
        BtnCari = new widget.Button();
        jLabel17 = new widget.Label();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        WindowUpdate.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowUpdate.setName("WindowUpdate"); // NOI18N
        WindowUpdate.setUndecorated(true);
        WindowUpdate.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Update/Tambahkan Jadwal HFIS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(null);

        BtnCloseIn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn5.setMnemonic('U');
        BtnCloseIn5.setText("Tutup");
        BtnCloseIn5.setToolTipText("Alt+U");
        BtnCloseIn5.setName("BtnCloseIn5"); // NOI18N
        BtnCloseIn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn5ActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnCloseIn5);
        BtnCloseIn5.setBounds(610, 120, 100, 30);

        BtnSimpanUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpanUpdate.setMnemonic('S');
        BtnSimpanUpdate.setText("Simpan");
        BtnSimpanUpdate.setToolTipText("Alt+S");
        BtnSimpanUpdate.setName("BtnSimpanUpdate"); // NOI18N
        BtnSimpanUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanUpdateActionPerformed(evt);
            }
        });
        internalFrame6.add(BtnSimpanUpdate);
        BtnSimpanUpdate.setBounds(20, 120, 100, 30);

        jLabel21.setText("Poliklinik :");
        jLabel21.setName("jLabel21"); // NOI18N
        internalFrame6.add(jLabel21);
        jLabel21.setBounds(0, 30, 75, 23);

        KodePoliUpdate.setEditable(false);
        KodePoliUpdate.setHighlighter(null);
        KodePoliUpdate.setName("KodePoliUpdate"); // NOI18N
        KodePoliUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodePoliUpdateKeyPressed(evt);
            }
        });
        internalFrame6.add(KodePoliUpdate);
        KodePoliUpdate.setBounds(79, 30, 65, 23);

        NmPoliUpdate.setEditable(false);
        NmPoliUpdate.setName("NmPoliUpdate"); // NOI18N
        internalFrame6.add(NmPoliUpdate);
        NmPoliUpdate.setBounds(146, 30, 151, 23);

        btnPoliklinikUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPoliklinikUpdate.setMnemonic('7');
        btnPoliklinikUpdate.setToolTipText("ALt+7");
        btnPoliklinikUpdate.setName("btnPoliklinikUpdate"); // NOI18N
        btnPoliklinikUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPoliklinikUpdateActionPerformed(evt);
            }
        });
        btnPoliklinikUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPoliklinikUpdateKeyPressed(evt);
            }
        });
        internalFrame6.add(btnPoliklinikUpdate);
        btnPoliklinikUpdate.setBounds(299, 30, 28, 23);

        jLabel22.setText("Dokter :");
        jLabel22.setName("jLabel22"); // NOI18N
        internalFrame6.add(jLabel22);
        jLabel22.setBounds(352, 30, 60, 23);

        KodeDokterUpdate.setEditable(false);
        KodeDokterUpdate.setHighlighter(null);
        KodeDokterUpdate.setName("KodeDokterUpdate"); // NOI18N
        KodeDokterUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeDokterUpdateKeyPressed(evt);
            }
        });
        internalFrame6.add(KodeDokterUpdate);
        KodeDokterUpdate.setBounds(416, 30, 72, 23);

        NmDokterUpdate.setEditable(false);
        NmDokterUpdate.setName("NmDokterUpdate"); // NOI18N
        internalFrame6.add(NmDokterUpdate);
        NmDokterUpdate.setBounds(490, 30, 185, 23);

        btnDokterUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnDokterUpdate.setMnemonic('7');
        btnDokterUpdate.setToolTipText("ALt+7");
        btnDokterUpdate.setName("btnDokterUpdate"); // NOI18N
        btnDokterUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDokterUpdateActionPerformed(evt);
            }
        });
        btnDokterUpdate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnDokterUpdateKeyPressed(evt);
            }
        });
        internalFrame6.add(btnDokterUpdate);
        btnDokterUpdate.setBounds(677, 30, 28, 23);

        jLabel4.setText("Hari Kerja :");
        jLabel4.setName("jLabel4"); // NOI18N
        internalFrame6.add(jLabel4);
        jLabel4.setBounds(0, 90, 75, 23);

        cmbHari.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Senin", "2. Selasa", "3. Rabu", "4. Kamis", "5. Jumat", "6. Sabtu", "7. Akhad", "8. Hari Libur Nasional" }));
        cmbHari.setName("cmbHari"); // NOI18N
        cmbHari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbHariKeyPressed(evt);
            }
        });
        internalFrame6.add(cmbHari);
        cmbHari.setBounds(79, 90, 248, 23);

        jLabel9.setText("Jam :");
        jLabel9.setName("jLabel9"); // NOI18N
        internalFrame6.add(jLabel9);
        jLabel9.setBounds(352, 60, 60, 23);

        cmbJam1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam1.setName("cmbJam1"); // NOI18N
        cmbJam1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJam1KeyPressed(evt);
            }
        });
        internalFrame6.add(cmbJam1);
        cmbJam1.setBounds(416, 60, 62, 23);

        cmbMnt1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt1.setName("cmbMnt1"); // NOI18N
        cmbMnt1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMnt1KeyPressed(evt);
            }
        });
        internalFrame6.add(cmbMnt1);
        cmbMnt1.setBounds(481, 60, 62, 23);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("s.d.");
        jLabel11.setName("jLabel11"); // NOI18N
        internalFrame6.add(jLabel11);
        jLabel11.setBounds(548, 60, 25, 23);

        cmbJam2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam2.setName("cmbJam2"); // NOI18N
        cmbJam2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJam2KeyPressed(evt);
            }
        });
        internalFrame6.add(cmbJam2);
        cmbJam2.setBounds(578, 60, 62, 23);

        cmbMnt2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt2.setName("cmbMnt2"); // NOI18N
        cmbMnt2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMnt2KeyPressed(evt);
            }
        });
        internalFrame6.add(cmbMnt2);
        cmbMnt2.setBounds(643, 60, 62, 23);

        KodeSubspesialis.setEditable(false);
        KodeSubspesialis.setHighlighter(null);
        KodeSubspesialis.setName("KodeSubspesialis"); // NOI18N
        KodeSubspesialis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KodeSubspesialisKeyPressed(evt);
            }
        });
        internalFrame6.add(KodeSubspesialis);
        KodeSubspesialis.setBounds(79, 60, 65, 23);

        NmSubspesialis.setEditable(false);
        NmSubspesialis.setName("NmSubspesialis"); // NOI18N
        internalFrame6.add(NmSubspesialis);
        NmSubspesialis.setBounds(146, 60, 181, 23);

        jLabel23.setText("Spesialis :");
        jLabel23.setName("jLabel23"); // NOI18N
        internalFrame6.add(jLabel23);
        jLabel23.setBounds(0, 60, 75, 23);

        WindowUpdate.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setIconImage(null);
        setIconImages(null);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pencarian Data Referensi Jadwal HFIS ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbKamar.setAutoCreateRowSorter(true);
        tbKamar.setName("tbKamar"); // NOI18N
        Scroll.setViewportView(tbKamar);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Poli :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(37, 23));
        panelGlass6.add(jLabel19);

        KdPoli.setEditable(false);
        KdPoli.setHighlighter(null);
        KdPoli.setName("KdPoli"); // NOI18N
        KdPoli.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass6.add(KdPoli);

        NmPoli.setEditable(false);
        NmPoli.setName("NmPoli"); // NOI18N
        NmPoli.setPreferredSize(new java.awt.Dimension(150, 23));
        panelGlass6.add(NmPoli);

        BtnPoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPoli.setMnemonic('3');
        BtnPoli.setToolTipText("ALt+3");
        BtnPoli.setName("BtnPoli"); // NOI18N
        BtnPoli.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnPoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPoliActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnPoli);

        jLabel20.setText("Tanggal :");
        jLabel20.setName("jLabel20"); // NOI18N
        jLabel20.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass6.add(jLabel20);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass6.add(Tanggal);

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
        panelGlass6.add(BtnCari);

        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(30, 23));
        panelGlass6.add(jLabel17);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Update");
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
        panelGlass6.add(BtnEdit);

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
        panelGlass6.add(BtnPrint);

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
        panelGlass6.add(BtnKeluar);

        internalFrame1.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

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
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            //TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            
            Sequel.queryu("truncate table temporary");
            int row=tabMode.getRowCount();
            for(int r=0;r<row;r++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(r,0).toString()+"','"+
                                tabMode.getValueAt(r,1).toString()+"','"+
                                tabMode.getValueAt(r,2).toString()+"','"+
                                tabMode.getValueAt(r,3).toString()+"','"+
                                tabMode.getValueAt(r,4).toString()+"','"+
                                tabMode.getValueAt(r,5).toString()+"','"+
                                tabMode.getValueAt(r,6).toString()+"','"+
                                tabMode.getValueAt(r,7).toString()+"','"+
                                tabMode.getValueAt(r,8).toString()+"','"+
                                tabMode.getValueAt(r,9).toString()+"','"+
                                tabMode.getValueAt(r,10).toString()+"','"+
                                tabMode.getValueAt(r,11).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','',''","Jadwal"); 
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("polirs",akses.getpropinsirs());
            //param.put("peserta","No.Peserta : "+NoKartu.getText()+" Nama Peserta : "+NamaPasien.getText());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptCariBPJSReferensiJadwalHFIS.jasper","report","[ Pencarian Referensi Jadwal HFIS ]",param);
            this.setCursor(Cursor.getDefaultCursor());
        }        
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPoliActionPerformed
        i=1;
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_BtnPoliActionPerformed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(KdPoli.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Silahkan pilih poli terlebih dahulu...!!");
            BtnPoli.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }   
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,BtnPrint);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        WindowUpdate.setLocationRelativeTo(internalFrame1);
        WindowUpdate.setVisible(true);
        btnPoliklinikUpdate.requestFocus();
    }//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnEditKeyPressed

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        WindowUpdate.dispose();
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    private void BtnSimpanUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanUpdateActionPerformed
        if(NmDokterUpdate.getText().trim().equals("")){
            Valid.textKosong(KodeDokterUpdate,"Dokter");
        }else if(NmPoliUpdate.getText().trim().equals("")){
            Valid.textKosong(KodePoliUpdate,"Poliklinik");
        }else{
            try {
                headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
                utc=String.valueOf(api.GetUTCdatetimeAsString());
                headers.add("x-timestamp",utc);
                headers.add("x-signature",api.getHmac(utc));
                headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
                requestJson ="{" +
                                "\"kodepoli\": \""+KodePoliUpdate.getText()+"\"," +
                                "\"kodesubspesialis\": \""+KodeSubspesialis.getText()+"\"," +
                                "\"kodedokter\": "+KodeDokterUpdate.getText()+"," +
                                "\"jadwal\": [" +
                                    "{" +
                                        "\"hari\": \""+cmbHari.getSelectedItem().toString().substring(0,1)+"\"," +
                                        "\"buka\": \""+cmbJam1.getSelectedItem().toString()+":"+cmbMnt1.getSelectedItem().toString()+"\"," +
                                        "\"tutup\": \""+cmbJam2.getSelectedItem().toString()+":"+cmbMnt2.getSelectedItem().toString()+"\"" +
                                    "}" +
                                "]" +
                             "}";
                requestEntity = new HttpEntity(requestJson,headers);
                URL = link+"/jadwaldokter/updatejadwaldokter";	
                System.out.println(URL);
                //System.out.println(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.POST, requestEntity, String.class).getBody());
                nameNode = root.path("metadata");
                if(nameNode.path("code").asText().equals("200")){
                    tampil();
                }else {
                    JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
                }   
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
                if(ex.toString().contains("UnknownHostException")){
                    JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanUpdateActionPerformed

    private void KodePoliUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodePoliUpdateKeyPressed
        
    }//GEN-LAST:event_KodePoliUpdateKeyPressed

    private void btnPoliklinikUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPoliklinikUpdateActionPerformed
        i=2;
        poli.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        poli.setLocationRelativeTo(internalFrame1);
        poli.setVisible(true);
    }//GEN-LAST:event_btnPoliklinikUpdateActionPerformed

    private void KodeDokterUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeDokterUpdateKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeDokterUpdateKeyPressed

    private void btnDokterUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDokterUpdateActionPerformed
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnDokterUpdateActionPerformed

    private void cmbHariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbHariKeyPressed
        Valid.pindah(evt, btnDokterUpdate, cmbJam1);
    }//GEN-LAST:event_cmbHariKeyPressed

    private void cmbJam1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJam1KeyPressed
        Valid.pindah(evt, cmbHari,cmbMnt1);
    }//GEN-LAST:event_cmbJam1KeyPressed

    private void cmbMnt1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMnt1KeyPressed
        Valid.pindah(evt, cmbJam1,cmbJam2);
    }//GEN-LAST:event_cmbMnt1KeyPressed

    private void cmbJam2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJam2KeyPressed
        Valid.pindah(evt, cmbMnt1,cmbMnt2);
    }//GEN-LAST:event_cmbJam2KeyPressed

    private void cmbMnt2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMnt2KeyPressed
       Valid.pindah(evt,cmbJam2,BtnSimpanUpdate);
    }//GEN-LAST:event_cmbMnt2KeyPressed

    private void btnPoliklinikUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPoliklinikUpdateKeyPressed
        Valid.pindah(evt, BtnSimpanUpdate, btnDokterUpdate);
    }//GEN-LAST:event_btnPoliklinikUpdateKeyPressed

    private void btnDokterUpdateKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnDokterUpdateKeyPressed
        Valid.pindah(evt, btnPoliklinikUpdate, cmbHari);
    }//GEN-LAST:event_btnDokterUpdateKeyPressed

    private void KodeSubspesialisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KodeSubspesialisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KodeSubspesialisKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            BPJSCekReferensiJadwalHFIS dialog = new BPJSCekReferensiJadwalHFIS(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn5;
    private widget.Button BtnEdit;
    private widget.Button BtnKeluar;
    private widget.Button BtnPoli;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpanUpdate;
    private widget.TextBox KdPoli;
    private widget.TextBox KodeDokterUpdate;
    private widget.TextBox KodePoliUpdate;
    private widget.TextBox KodeSubspesialis;
    private widget.TextBox NmDokterUpdate;
    private widget.TextBox NmPoli;
    private widget.TextBox NmPoliUpdate;
    private widget.TextBox NmSubspesialis;
    private widget.ScrollPane Scroll;
    private widget.Tanggal Tanggal;
    private javax.swing.JDialog WindowUpdate;
    private widget.Button btnDokterUpdate;
    private widget.Button btnPoliklinikUpdate;
    private widget.ComboBox cmbHari;
    private widget.ComboBox cmbJam1;
    private widget.ComboBox cmbJam2;
    private widget.ComboBox cmbMnt1;
    private widget.ComboBox cmbMnt2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame6;
    private widget.Label jLabel11;
    private widget.Label jLabel17;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel4;
    private widget.Label jLabel9;
    private widget.panelisi panelGlass6;
    private widget.Table tbKamar;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        try {
            headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("x-cons-id",koneksiDB.CONSIDAPIMOBILEJKN());
	    utc=String.valueOf(api.GetUTCdatetimeAsString());
	    headers.add("x-timestamp",utc);
	    headers.add("x-signature",api.getHmac(utc));
	    headers.add("user_key",koneksiDB.USERKEYAPIMOBILEJKN());
            requestEntity = new HttpEntity(headers);
            URL = link+"/jadwaldokter/kodepoli/"+KdPoli.getText()+"/tanggal/"+Valid.SetTgl(Tanggal.getSelectedItem()+"");	
            System.out.println(URL);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("metadata");
            if(nameNode.path("code").asText().equals("200")){
                Valid.tabelKosong(tabMode);
                response = mapper.readTree(api.Decrypt(root.path("response").asText(),utc));
                //response = root.path("response");
                if(response.isArray()){
                    i=1;
                    for(JsonNode list:response){
                        tabMode.addRow(new Object[]{
                            i+".",list.path("kodesubspesialis").asText(),list.path("namasubspesialis").asText(),
                            list.path("kodepoli").asText(),list.path("namapoli").asText(),
                            list.path("kodedokter").asText(),list.path("namadokter").asText(),
                            list.path("hari").asText(),list.path("namahari").asText(),
                            list.path("libur").asText(),list.path("jadwal").asText(),
                            list.path("kapasitaspasien").asText()
                        });
                        i++;
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server BPJS terputus...!");
            }
        }
    }    

    public JTable getTable(){
        return tbKamar;
    }
}

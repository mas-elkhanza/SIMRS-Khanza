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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fungsi.BackgroundMusic;
import fungsi.WarnaTable;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import fungsi.koneksiDB;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariPegawai;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import simrskhanza.DlgIGD;
import simrskhanza.DlgReg;

/**
 *
 * @author perpustakaan
 */
public final class SisruteRujukanMasukan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private int i=0,nilai_detik,rujukanbaru=0;
    private String pilihan="",alarm="",URL="",link="",norm="",statusreg="",statuspasien="",norujuk="",nol_detik,detik;
    private SisruteApi api=new SisruteApi();
    private BackgroundMusic music;
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private HttpHeaders headers;
    private HttpEntity requestEntity;
    private ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;
    private JsonNode nameNode;
    private JsonNode response;
    private boolean aktif=false;
    private String idrs="",requestJson="",No="",RMFaskes="",NamaPasien="",Kontak="",Alamat="",TempatLahir="",TglLahir="",
                JK="",NoKartuJKN="",NIK="",NoRujuk="",KodeAsal="",NamaFaskesAsal="",
                KodeTujuan="",NamaFaskesTujuan="",JenisRujukan="",Alasan="",AlasanLainnya="",Status="",
                TglRujuk="",DignosaRujuk="",AnamnesisPemeriksaanFisik="",Kesadaran="",
                Tensi="",Nadi="",Suhu="",Respirasi="",Nyeri="",KeadaanUmum="",Alergi="",
                Laboratorium="",Radiologi="",TerapiTindakan="",SttsPasien="",SttsRegistrasi="",NoRmRS="";
    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public SisruteRujukanMasukan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        WindowAmbilSampel.setSize(540,122);
        tabMode=new DefaultTableModel(null,new String[]{
                "No.","RM Faskes","Nama Pasien","Kontak","Alamat","Tempat Lahir","Tgl.Lahir",
                "J.K.","No.Kartu JKN","NIK","No.Rujuk","Kode Asal","Nama Faskes Asal",
                "Kode Tujuan","Nama Faskes Tujuan","Jenis Rujukan","Alasan","Alasan Lainnya","Status",
                "Tgl.Rujuk","Dignosa Rujuk","Anamnesis & Pemeriksaan Fisik","Kesadaran",
                "Tensi","Nadi","Suhu","Respirasi","Nyeri","Keadaan Umum","Alergi",
                "Laboratorium","Radiologi","Terapi/Tindakan","Stts.Pasien","Stts.Registrasi","No.RmRS"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbBangsal.setModel(tabMode);
        //tbBangsal.setDefaultRenderer(Object.class, new WarnaTable(jPanel2.getBackground(),tbBangsal.getBackground()));
        tbBangsal.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbBangsal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 36; i++) {
            TableColumn column = tbBangsal.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(35);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(160);
            }else if(i==3){
                column.setPreferredWidth(85);
            }else if(i==4){
                column.setPreferredWidth(160);
            }else if(i==5){
                column.setPreferredWidth(90);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(25);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(130);
            }else if(i==10){
                column.setPreferredWidth(110);
            }else if(i==11){
                column.setPreferredWidth(70);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(70);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(120);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(150);
            }else if(i==18){
                column.setPreferredWidth(80);
            }else if(i==19){
                column.setPreferredWidth(110);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(160);
            }else if(i==22){
                column.setPreferredWidth(70);
            }else if(i==23){
                column.setPreferredWidth(45);
            }else if(i==24){
                column.setPreferredWidth(40);
            }else if(i==25){
                column.setPreferredWidth(40);
            }else if(i==26){
                column.setPreferredWidth(55);
            }else if(i==27){
                column.setPreferredWidth(50);
            }else if(i==28){
                column.setPreferredWidth(140);
            }else if(i==29){
                column.setPreferredWidth(100);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(150);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(70);
            }else if(i==34){
                column.setPreferredWidth(100);
            }else if(i==35){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }
        }
        tbBangsal.setDefaultRenderer(Object.class, new WarnaTable());
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){                   
                    NoKTP.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),23).toString());
                    Nama.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                }   
                NoKTP.requestFocus();
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
        
        pegawai.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pegawai.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        try {
            link=koneksiDB.URLAPISISRUTE();
            alarm=koneksiDB.ALARMRSISRUTE();
            idrs=koneksiDB.IDSISRUTE();
        } catch (Exception e) {
            alarm="no";
            System.out.println("E : "+e);
        }
        
        if(alarm.equals("yes")){
            jam();
        }
        
        akses.setAktif(false);
    }    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        WindowAmbilSampel = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel4 = new widget.Label();
        AlasanBalasan = new widget.TextBox();
        CmbTerima = new widget.ComboBox();
        jLabel8 = new widget.Label();
        jLabel24 = new widget.Label();
        NoKTP = new widget.TextBox();
        Nama = new widget.TextBox();
        BtnPegawai = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbBangsal = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        jLabel11 = new widget.Label();
        Tanggal = new widget.Tanggal();
        jLabel12 = new widget.Label();
        cmbStatus = new widget.ComboBox();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        panelGlass5 = new widget.panelisi();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnAll = new widget.Button();
        BtnJawab = new widget.Button();
        BtnRegist = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        WindowAmbilSampel.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowAmbilSampel.setName("WindowAmbilSampel"); // NOI18N
        WindowAmbilSampel.setUndecorated(true);
        WindowAmbilSampel.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Balas Rujukan Masuk Sisrute ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(null);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('U');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+U");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(420, 72, 100, 30);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(420, 27, 100, 30);

        jLabel4.setText("Alasan :");
        jLabel4.setName("jLabel4"); // NOI18N
        internalFrame5.add(jLabel4);
        jLabel4.setBounds(0, 52, 60, 23);

        AlasanBalasan.setHighlighter(null);
        AlasanBalasan.setName("AlasanBalasan"); // NOI18N
        AlasanBalasan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AlasanBalasanKeyPressed(evt);
            }
        });
        internalFrame5.add(AlasanBalasan);
        AlasanBalasan.setBounds(65, 52, 323, 23);

        CmbTerima.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1. Diterima", "0. Tidak Diterima" }));
        CmbTerima.setName("CmbTerima"); // NOI18N
        CmbTerima.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbTerimaKeyPressed(evt);
            }
        });
        internalFrame5.add(CmbTerima);
        CmbTerima.setBounds(65, 22, 230, 23);

        jLabel8.setText("Status :");
        jLabel8.setName("jLabel8"); // NOI18N
        internalFrame5.add(jLabel8);
        jLabel8.setBounds(0, 22, 60, 23);

        jLabel24.setText("Petugas :");
        jLabel24.setName("jLabel24"); // NOI18N
        internalFrame5.add(jLabel24);
        jLabel24.setBounds(0, 82, 60, 23);

        NoKTP.setHighlighter(null);
        NoKTP.setName("NoKTP"); // NOI18N
        NoKTP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoKTPKeyPressed(evt);
            }
        });
        internalFrame5.add(NoKTP);
        NoKTP.setBounds(65, 82, 100, 23);

        Nama.setEditable(false);
        Nama.setName("Nama"); // NOI18N
        internalFrame5.add(Nama);
        Nama.setBounds(167, 82, 191, 23);

        BtnPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPegawai.setMnemonic('1');
        BtnPegawai.setToolTipText("ALt+1");
        BtnPegawai.setName("BtnPegawai"); // NOI18N
        BtnPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPegawaiActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnPegawai);
        BtnPegawai.setBounds(360, 82, 28, 23);

        WindowAmbilSampel.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowDeactivated(java.awt.event.WindowEvent evt) {
                formWindowDeactivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Rujukan Masuk Sisrute ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbBangsal.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        jPanel3.setBackground(new java.awt.Color(250, 250, 250));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel11.setText("Tanggal :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(54, 23));
        panelGlass8.add(jLabel11);

        Tanggal.setDisplayFormat("dd-MM-yyyy");
        Tanggal.setName("Tanggal"); // NOI18N
        Tanggal.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass8.add(Tanggal);

        jLabel12.setText("Stts.Reg :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass8.add(jLabel12);

        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Semua", "Sudah Teregistrasi", "Belum Teregistrasi" }));
        cmbStatus.setName("cmbStatus"); // NOI18N
        cmbStatus.setPreferredSize(new java.awt.Dimension(120, 23));
        panelGlass8.add(cmbStatus);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(67, 23));
        panelGlass8.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(230, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass8.add(TCari);

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
        panelGlass8.add(BtnCari);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass5.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(95, 23));
        panelGlass5.add(LCount);

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

        BtnJawab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/file-edit-16x16.png"))); // NOI18N
        BtnJawab.setMnemonic('J');
        BtnJawab.setText("Jawab");
        BtnJawab.setToolTipText("Alt+J");
        BtnJawab.setName("BtnJawab"); // NOI18N
        BtnJawab.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnJawab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJawabActionPerformed(evt);
            }
        });
        BtnJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnJawabKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnJawab);

        BtnRegist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add-file-16x16.png"))); // NOI18N
        BtnRegist.setMnemonic('G');
        BtnRegist.setText("Regist");
        BtnRegist.setToolTipText("Alt+G");
        BtnRegist.setName("BtnRegist"); // NOI18N
        BtnRegist.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRegist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRegistActionPerformed(evt);
            }
        });
        BtnRegist.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnRegistKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnRegist);

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
        panelGlass5.add(BtnPrint);

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

        jPanel3.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

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
        }else{Valid.pindah(evt,BtnKeluar,Tanggal);}
}//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));            
    akses.setAktif(false);
    tampil();
    this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnCariActionPerformed

private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR)); 
            tampil();
            this.setCursor(Cursor.getDefaultCursor());
        }else{
            //Valid.pindah(evt, TKd, BtnPrint);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnRegistActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRegistActionPerformed
        akses.setAktif(true);
        if(!No.equals("")){
            if(SttsRegistrasi.trim().equals("Sudah Teregistrasi")){
                JOptionPane.showMessageDialog(null,"Pasien sudah teregistrasi...!!");
                akses.setAktif(false);
            }else{
                try{
                    pilihan = (String)JOptionPane.showInputDialog(null,"Silahkan pilih cara registrasi..!!","Pilihan Registrasi",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Via Registrasi","Via IGD","Via Cek No.Kartu VClaim","Via Cek NIK VClaim","Via Cek Rujukan Kartu PCare di VClaim","Via Cek Rujukan Kartu RS di VClaim"},"Via Registrasi");
                    switch (pilihan) {
                        case "Via Registrasi":  
                            if(SttsPasien.equals("Lama")){
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgReg reg=new DlgReg(null,false);
                                reg.emptTeks();    
                                reg.isCek();
                                reg.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                                reg.setLocationRelativeTo(internalFrame1);
                                reg.SetPasien(NoRmRS,KodeAsal+NoRujuk,NamaFaskesAsal);
                                reg.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor()); 
                            }else{
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgReg reg=new DlgReg(null,false);
                                reg.emptTeks();    
                                reg.isCek();
                                reg.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                                reg.setLocationRelativeTo(internalFrame1);
                                reg.setVisible(true);
                                reg.setPasien(NamaPasien, Kontak, Alamat, TempatLahir, TglLahir, JK, NoKartuJKN, NIK,KodeAsal+NoRujuk,NamaFaskesAsal);
                                this.setCursor(Cursor.getDefaultCursor()); 
                            }
                            break;
                        case "Via IGD":
                            if(SttsPasien.equals("Lama")){
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgIGD reg=new DlgIGD(null,false);
                                reg.emptTeks();    
                                reg.isCek();
                                reg.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                                reg.setLocationRelativeTo(internalFrame1);
                                reg.SetPasien(NoRmRS,KodeAsal+NoRujuk,NamaFaskesAsal);
                                reg.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor()); 
                            }else{
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgIGD reg=new DlgIGD(null,false);
                                reg.emptTeks();    
                                reg.isCek();
                                reg.setSize(internalFrame1.getWidth()-20, internalFrame1.getHeight()-20);
                                reg.setLocationRelativeTo(internalFrame1);
                                reg.setVisible(true);
                                reg.setPasien(NamaPasien, Kontak, Alamat, TempatLahir, TglLahir, JK, NoKartuJKN, NIK,KodeAsal+NoRujuk,NamaFaskesAsal);
                                this.setCursor(Cursor.getDefaultCursor()); 
                            }
                            break;
                        case "Via Cek No.Kartu VClaim":
                            if(NoKartuJKN.equals("")){
                                Valid.textKosong(TCari,"No.Kartu JKN");
                            }else{
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                BPJSCekKartu form=new BPJSCekKartu(null,false);
                                form.isCek();
                                form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                                form.setLocationRelativeTo(internalFrame1);
                                form.SetNoKartu(NoKartuJKN);
                                form.SetNoRujuk(KodeAsal+NoRujuk);
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }                                
                            break;
                        case "Via Cek NIK VClaim":
                            if(NIK.equals("")){
                                Valid.textKosong(TCari,"No.KTP");
                            }else{
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                BPJSCekNIK2 form=new BPJSCekNIK2(null,false);
                                form.isCek();
                                form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                                form.setLocationRelativeTo(internalFrame1);
                                form.SetNoKTP(NIK);
                                form.SetNoRujuk(KodeAsal+NoRujuk);
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }     
                            break;
                        case "Via Cek Rujukan Kartu PCare di VClaim":
                            if(NoKartuJKN.equals("")){
                                Valid.textKosong(TCari,"No.Kartu JKN");
                            }else{
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                BPJSCekRujukanKartuPCare form=new BPJSCekRujukanKartuPCare(null,false);
                                form.isCek();
                                form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                                form.setLocationRelativeTo(internalFrame1);
                                form.SetNoKartu(NoKartuJKN);
                                form.SetNoRujuk(KodeAsal+NoRujuk);
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }    
                            break;
                        case "Via Cek Rujukan Kartu RS di VClaim":
                            if(NoKartuJKN.equals("")){
                                Valid.textKosong(TCari,"No.Kartu JKN");
                            }else{
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                BPJSCekRujukanKartuRS form=new BPJSCekRujukanKartuRS(null,false);
                                form.isCek();
                                form.setSize(internalFrame1.getWidth(), internalFrame1.getHeight());
                                form.setLocationRelativeTo(internalFrame1);
                                form.SetNoKartu(NoKartuJKN);
                                form.SetNoRujuk(KodeAsal+NoRujuk);
                                form.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            } 
                            break;
                        default:
                            akses.setAktif(false);
                            break;
                    }
                }catch(Exception e){
                    akses.setAktif(false);
                }
            }
        }else{            
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data rujukan...!!!!");
            akses.setAktif(false);
            TCari.requestFocus();
        }   
    }//GEN-LAST:event_BtnRegistActionPerformed

    private void BtnRegistKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnRegistKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnRegistActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnRegistKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

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
                tabMode.getValueAt(r,0).toString().replaceAll("'","`")+"','"+
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
                tabMode.getValueAt(r,34).toString().replaceAll("'","`")+"','',''","Rekap Harian Pengadaan Ipsrs"); 
            }
            
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptCariSisruteRujukanMasuk.jasper","report","[ Pencarian Rujukan Masuk Sisrute ]",param);
            this.setCursor(Cursor.getDefaultCursor());
        } 
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        cmbStatus.setSelectedIndex(0);
        akses.setAktif(false);
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnJawabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJawabActionPerformed
        akses.setAktif(true);
        if(!No.equals("")){
            if(NoRujuk.trim().equals("")){
                Valid.textKosong(TCari,"No.Rujukan");
            }else{ 
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                WindowAmbilSampel.setLocationRelativeTo(internalFrame1);
                WindowAmbilSampel.setVisible(true);
                CmbTerima.requestFocus();
                this.setCursor(Cursor.getDefaultCursor());
            }
        }else{  
            akses.setAktif(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data rujukan...!!!!");
            TCari.requestFocus();
        }  
    }//GEN-LAST:event_BtnJawabActionPerformed

    private void BtnJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnJawabKeyPressed

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

    private void tbBangsalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbBangsalMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbBangsalMouseClicked

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        akses.setAktif(false);
        WindowAmbilSampel.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        try {
            URL = link+"/rujukan/jawab/"+NoRujuk;	
            headers = new HttpHeaders();
            headers.add("X-cons-id",idrs);
	    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString())); 
	    headers.add("X-signature",api.getHmac()); 
	    headers.add("Content-type","application/json");
            requestJson ="{" +
                            "\"DITERIMA\":"+CmbTerima.getSelectedItem().toString().substring(0,1)+"," +
                            "\"KETERANGAN\":\""+AlasanBalasan.getText()+"\"," +
                            "\"PETUGAS\":{" +
                                "\"NIK\":\""+NoKTP.getText()+"\"," +
                                "\"NAMA\":\""+Nama.getText()+"\"" +
                            "}" +
                          "}";              
	    headers.add("Content-length",Integer.toString(requestJson.length())); 
            System.out.println(Integer.toString(requestJson.length()));
            System.out.println(requestJson);
	    requestEntity = new HttpEntity(requestJson,headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.PUT, requestEntity, String.class).getBody());
            JOptionPane.showMessageDialog(null,root.path("detail").asText());
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server Kemenkes terputus....!");
            }else if(ex.toString().contains("404")){
                JOptionPane.showMessageDialog(rootPane,"Tidak ditemukan....!");
            }else if(ex.toString().contains("422")){
                JOptionPane.showMessageDialog(rootPane,"Rujukan dengan nomor "+NoRujuk+" sudah direspon");
            }else if(ex.toString().contains("500")){
                JOptionPane.showMessageDialog(rootPane,"Server interenal error....!");
            }
        }

    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void AlasanBalasanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AlasanBalasanKeyPressed
        Valid.pindah(evt,CmbTerima,BtnPegawai);
    }//GEN-LAST:event_AlasanBalasanKeyPressed

    private void CmbTerimaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbTerimaKeyPressed
        Valid.pindah(evt,BtnCloseIn4,AlasanBalasan);
    }//GEN-LAST:event_CmbTerimaKeyPressed

    private void NoKTPKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoKTPKeyPressed
        
    }//GEN-LAST:event_NoKTPKeyPressed

    private void BtnPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPegawaiActionPerformed
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_BtnPegawaiActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        aktif=true;
    }//GEN-LAST:event_formWindowActivated

    private void formWindowDeactivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeactivated
        aktif=false;
    }//GEN-LAST:event_formWindowDeactivated

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            SisruteRujukanMasukan dialog = new SisruteRujukanMasukan(new javax.swing.JFrame(), true);
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
    private widget.TextBox AlasanBalasan;
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnJawab;
    private widget.Button BtnKeluar;
    private widget.Button BtnPegawai;
    private widget.Button BtnPrint;
    private widget.Button BtnRegist;
    private widget.Button BtnSimpan4;
    private widget.ComboBox CmbTerima;
    private widget.Label LCount;
    private widget.TextBox Nama;
    private widget.TextBox NoKTP;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.Tanggal Tanggal;
    private javax.swing.JDialog WindowAmbilSampel;
    private widget.ComboBox cmbStatus;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel24;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass8;
    private widget.Table tbBangsal;
    // End of variables declaration//GEN-END:variables

    public void tampil(){        
        try {
            Valid.tabelKosong(tabMode);
            URL = link+"/rujukan?tanggal="+Valid.SetTgl(Tanggal.getSelectedItem()+"");
            headers = new HttpHeaders();
	    headers.add("X-cons-id",idrs);
	    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString())); 
	    headers.add("X-signature",api.getHmac()); 
	    headers.add("Content-type","application/json");             
	    headers.add("Content-length",null); 
            requestEntity = new HttpEntity(headers);
            root = mapper.readTree(api.getRest().exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            nameNode = root.path("status");
            System.out.println("Result : "+root.path("status").asText());
            if(nameNode.asText().equals("200")){                
                response = root.path("data");
                if(response.isArray()){
                    i=1;
                    for(JsonNode list:response){
                        norujuk=Sequel.cariIsi("select no_rujuk from rujuk_masuk where no_rujuk=?",list.path("RUJUKAN").path("FASKES_ASAL").path("KODE").asText()+list.path("RUJUKAN").path("NOMOR").asText());
                        if(!norujuk.equals("")){
                            statusreg="Sudah Teregistrasi";
                        }else{
                            statusreg="Belum Teregistrasi";
                        }
                        norm=Sequel.cariIsi("select no_rkm_medis from pasien where no_ktp=?",list.path("PASIEN").path("NIK").asText());
                        statuspasien="Baru";
                        if(!norm.equals("")){
                            statuspasien="Lama";
                        }else{
                            norm=Sequel.cariIsi("select no_rkm_medis from pasien where no_peserta=?",list.path("PASIEN").path("NO_KARTU_JKN").asText());
                            if(!norm.equals("")){
                                statuspasien="Lama";
                            }
                        }
                        if(statusreg.contains(cmbStatus.getSelectedItem().toString().replaceAll("Semua",""))){
                            if(list.path("PASIEN").path("NAMA").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("PASIEN").path("NORM").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("PASIEN").path("ALAMAT").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("PASIEN").path("TANGGAL_LAHIR").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("PASIEN").path("NO_KARTU_JKN").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("PASIEN").path("NIK").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("RUJUKAN").path("FASKES_ASAL").path("NAMA").asText().toLowerCase().contains(TCari.getText().toLowerCase())||
                                    list.path("RUJUKAN").path("NOMOR").asText().toLowerCase().contains(TCari.getText().toLowerCase())){
                                tabMode.addRow(new Object[]{
                                    i+".",list.path("PASIEN").path("NORM").asText(),list.path("PASIEN").path("NAMA").asText(),
                                    list.path("PASIEN").path("KONTAK").asText(),list.path("PASIEN").path("ALAMAT").asText(),
                                    list.path("PASIEN").path("TEMPAT_LAHIR").asText(),list.path("PASIEN").path("TANGGAL_LAHIR").asText(),
                                    list.path("PASIEN").path("JENIS_KELAMIN").path("NAMA").asText().substring(0,1),
                                    list.path("PASIEN").path("NO_KARTU_JKN").asText(),list.path("PASIEN").path("NIK").asText(),
                                    list.path("RUJUKAN").path("NOMOR").asText(),list.path("RUJUKAN").path("FASKES_ASAL").path("KODE").asText(),
                                    list.path("RUJUKAN").path("FASKES_ASAL").path("NAMA").asText(),list.path("RUJUKAN").path("FASKES_TUJUAN").path("KODE").asText(),
                                    list.path("RUJUKAN").path("FASKES_TUJUAN").path("NAMA").asText(),list.path("RUJUKAN").path("JENIS_RUJUKAN").path("NAMA").asText(),
                                    list.path("RUJUKAN").path("ALASAN").asText(),list.path("RUJUKAN").path("ALASAN_LAINNYA").asText(),
                                    list.path("RUJUKAN").path("STATUS").path("NAMA").asText(),list.path("RUJUKAN").path("TANGGAL").asText(),
                                    list.path("RUJUKAN").path("DIAGNOSA").asText(),list.path("KONDISI_UMUM").path("ANAMNESIS_DAN_PEMERIKSAAN_FISIK").asText(),
                                    list.path("KONDISI_UMUM").path("KESADARAN").path("NAMA").asText(),list.path("KONDISI_UMUM").path("TEKANAN_DARAH").asText(),
                                    list.path("KONDISI_UMUM").path("FREKUENSI_NADI").asText(),list.path("KONDISI_UMUM").path("SUHU").asText(),
                                    list.path("KONDISI_UMUM").path("PERNAPASAN").asText(),list.path("KONDISI_UMUM").path("NYERI").path("NAMA").asText(),
                                    list.path("KONDISI_UMUM").path("KEADAAN_UMUM").asText(),list.path("KONDISI_UMUM").path("ALERGI").asText(),
                                    list.path("PENUNJANG").path("LABORATORIUM").asText(),list.path("PENUNJANG").path("RADIOLOGI").asText(),
                                    list.path("PENUNJANG").path("TERAPI_ATAU_TINDAKAN").asText(),statuspasien,statusreg,norm
                                });
                                i++;
                            }                                
                        }                            
                    }
                }    
                LCount.setText(""+tabMode.getRowCount());
            }else {
                JOptionPane.showMessageDialog(null,root.path("detail").asText());                 
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(rootPane,"Koneksi ke server Kemenkes terputus....!");
            }else if(ex.toString().contains("500")){
                JOptionPane.showMessageDialog(rootPane,"Server interenal error....!");
            }
        }
    }

    
    private void jam(){
        ActionListener taskPerformer = (ActionEvent e) -> {
            if(aktif==true){
                nol_detik = "";
                Date now = Calendar.getInstance().getTime();
                nilai_detik = now.getSeconds();
                if (nilai_detik <= 9) {
                    nol_detik = "0";
                }

                detik = nol_detik + Integer.toString(nilai_detik);
                if(detik.equals("05")){
                    if(akses.getAktif()==false){
                        rujukanbaru=0;
                        tampil();
                        for(i=0;i<tbBangsal.getRowCount();i++){
                            if(tbBangsal.getValueAt(i,18).toString().toLowerCase().contains("belum direspon")){
                                rujukanbaru++;
                            }
                        }

                        if(rujukanbaru>0){
                            try {
                                music = new BackgroundMusic("./suara/alarm.mp3");
                                music.start();
                            } catch (Exception ex) {
                                System.out.println(ex);
                            }
                        }                                                
                    }
                }
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    public void isCek(){
        if(akses.getjml2()>=1){            
            Sequel.cariIsi("select no_ktp from pegawai where nik=?",Nama,akses.getkode());
            BtnPegawai.setEnabled(false);
            Sequel.cariIsi("select nama from pegawai where nik=?",Nama,akses.getkode());
        }else{
            BtnPegawai.setEnabled(true);
        }    
    }
    
    private void getData() {
        if(tbBangsal.getSelectedRow()!= -1){
            No=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),0).toString();
            RMFaskes=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),1).toString();
            NamaPasien=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),2).toString();
            Kontak=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),3).toString();
            Alamat=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),4).toString();
            TempatLahir=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),5).toString();
            TglLahir=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),6).toString();            
            JK=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),7).toString();
            NoKartuJKN=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),8).toString();
            NIK=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),9).toString();
            NoRujuk=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),10).toString();
            KodeAsal=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),11).toString();
            NamaFaskesAsal=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),12).toString();            
            KodeTujuan=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),13).toString();
            NamaFaskesTujuan=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),14).toString();
            JenisRujukan=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),15).toString();
            Alasan=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),16).toString();
            AlasanLainnya=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),17).toString();
            Status=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),18).toString();            
            TglRujuk=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),19).toString();
            DignosaRujuk=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),20).toString();
            AnamnesisPemeriksaanFisik=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),21).toString();
            Kesadaran=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),22).toString();            
            Tensi=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),23).toString();
            Nadi=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),24).toString();
            Suhu=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),25).toString();
            Respirasi=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),26).toString();
            Nyeri=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),27).toString();
            KeadaanUmum=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),28).toString();
            Alergi=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),29).toString();            
            Laboratorium=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),30).toString();      
            Radiologi=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),31).toString();    
            TerapiTindakan=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),32).toString();
            SttsPasien=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),33).toString();
            SttsRegistrasi=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),34).toString();
            NoRmRS=tbBangsal.getValueAt(tbBangsal.getSelectedRow(),35).toString();
        }
    }

}

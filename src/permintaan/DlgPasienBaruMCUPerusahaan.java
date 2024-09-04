/*
  Dilarang keras memperjualbelikan/mengambil keuntungan dari Software 
  ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */
package permintaan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.validasi;
import fungsi.sekuel;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class DlgPasienBaruMCUPerusahaan extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private sekuel Sequel=new sekuel();
    private int i=0;
    private String status="",tahun="",awalantahun="",bulan="",awalanbulan="",posisitahun="",pengurutan="",kdkel="",kdkec="",kdkab="",kdprop="",
            kdbahasa="",kdcacatfisik="",kdsuku="";
    public String KodePerusahaan="",TanggalMCU="",KodeCaraBayar="";
    
    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgPasienBaruMCUPerusahaan(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tabMode=new DefaultTableModel(null,new Object[]{
                "P","No.Pengajuan","Nama Pasien","No.KTP","J.K","Tempat Lahir","Tgl.Lahir","Nama Ibu","Alamat","Kelurahan","Kecamatan","Kabupaten","Propinsi",
                "G.D.","Pekerjaan","Status Nikah","Agama/Kepercayaan","Tanggal MCU","No.Telp/No.HP","Umur","Pendidikan","P.J./Keluarga","Nama P.J./Keluarga",
                "Pekerjaan P.J/Keluarga","Alamat P.J./Keluarga","Kelurahan P.J./Keluarga","Kecamatan P.J./Keluarga","Kabupaten P.J./Keluarga",
                "Propinsi P.J./Keluarga","Suku Bangsa Pasien","Bahasa Pasien","Cacat Fisik","Email","NIP","Status"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Boolean.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,
                java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 35; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(100);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(120);
            }else if(i==4){
                column.setPreferredWidth(25);
            }else if(i==5){
                column.setPreferredWidth(100);
            }else if(i==6){
                column.setPreferredWidth(65);
            }else if(i==7){
                column.setPreferredWidth(150);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(100);
            }else if(i==12){
                column.setPreferredWidth(100);
            }else if(i==13){
                column.setPreferredWidth(30);
            }else if(i==14){
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(100);
            }else if(i==16){
                column.setPreferredWidth(110);
            }else if(i==17){
                column.setPreferredWidth(74);
            }else if(i==18){
                column.setPreferredWidth(120);
            }else if(i==19){
                column.setPreferredWidth(50);
            }else if(i==20){
                column.setPreferredWidth(65);
            }else if(i==21){
                column.setPreferredWidth(70);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(105);
            }else if(i==24){
                column.setPreferredWidth(200);
            }else if(i==25){
                column.setPreferredWidth(130);
            }else if(i==26){
                column.setPreferredWidth(130);
            }else if(i==27){
                column.setPreferredWidth(130);
            }else if(i==28){
                column.setPreferredWidth(130);
            }else if(i==29){
                column.setPreferredWidth(105);
            }else if(i==30){
                column.setPreferredWidth(100);
            }else if(i==31){
                column.setPreferredWidth(100);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(120);
            }else if(i==34){
                column.setPreferredWidth(115);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCari1ActionPerformed(null);
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCari1ActionPerformed(null);
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        BtnCari1ActionPerformed(null);
                    }
                }
            });
        }
        
        DTPDaftar.setDate(new Date());
        
        try {
            ps=koneksi.prepareStatement("select set_urut_no_rkm_medis.urutan,set_urut_no_rkm_medis.tahun,set_urut_no_rkm_medis.bulan,set_urut_no_rkm_medis.posisi_tahun_bulan from set_urut_no_rkm_medis");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    pengurutan=rs.getString("urutan");
                    tahun=rs.getString("tahun");
                    bulan=rs.getString("bulan");
                    posisitahun=rs.getString("posisi_tahun_bulan");
                }
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
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
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

        Popup = new javax.swing.JPopupMenu();
        ppPilih = new javax.swing.JMenuItem();
        ppBersihkan = new javax.swing.JMenuItem();
        DTPDaftar = new widget.Tanggal();
        NoRm = new widget.TextBox();
        TNo = new widget.TextBox();
        TNoBooking = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari1 = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();

        Popup.setName("Popup"); // NOI18N

        ppPilih.setBackground(new java.awt.Color(255, 255, 254));
        ppPilih.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppPilih.setForeground(new java.awt.Color(50, 50, 50));
        ppPilih.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppPilih.setText("Pilih Semua");
        ppPilih.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppPilih.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppPilih.setName("ppPilih"); // NOI18N
        ppPilih.setPreferredSize(new java.awt.Dimension(180, 25));
        ppPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppPilihActionPerformed(evt);
            }
        });
        Popup.add(ppPilih);

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 254));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(50, 50, 50));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Pilihan");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(180, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        DTPDaftar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01-09-2024" }));
        DTPDaftar.setDisplayFormat("dd-MM-yyyy");
        DTPDaftar.setName("DTPDaftar"); // NOI18N
        DTPDaftar.setOpaque(false);

        NoRm.setHighlighter(null);
        NoRm.setName("NoRm"); // NOI18N
        NoRm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRmKeyPressed(evt);
            }
        });

        TNo.setEditable(false);
        TNo.setBackground(new java.awt.Color(245, 250, 240));
        TNo.setHighlighter(null);
        TNo.setName("TNo"); // NOI18N
        TNo.setOpaque(true);

        TNoBooking.setEditable(false);
        TNoBooking.setBackground(new java.awt.Color(245, 250, 240));
        TNoBooking.setHighlighter(null);
        TNoBooking.setName("TNoBooking"); // NOI18N
        TNoBooking.setOpaque(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengajuan Booking Pasien Baru MCU Perusahaan/Instansi ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        scrollPane1.setComponentPopupMenu(Popup);
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
        tbDokter.setToolTipText("Silahkan pilih penilaian Ya / Tidak");
        tbDokter.setComponentPopupMenu(Popup);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelisi1.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnBatal.setMnemonic('H');
        BtnBatal.setText("Hapus");
        BtnBatal.setToolTipText("Alt+H");
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
        panelisi1.add(BtnBatal);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari1.setMnemonic('1');
        BtnCari1.setToolTipText("Alt+1");
        BtnCari1.setName("BtnCari1"); // NOI18N
        BtnCari1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari1ActionPerformed(evt);
            }
        });
        BtnCari1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCari1KeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari1);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(50, 23));
        panelisi1.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(45, 23));
        panelisi1.add(LCount);

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
        dispose();  
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){            
            dispose();              
        }else{Valid.pindah(evt,BtnSimpan,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){ 
            if(tbDokter.getValueAt(i,0).toString().equals("true")&&tbDokter.getValueAt(i,34).toString().equals("Menunggu Konfirmasi")){
                Sequel.queryu4("insert ignore into kelurahan values(?,?)",2,new String[]{"0",tbDokter.getValueAt(i,9).toString()});
                kdkel=Sequel.cariIsi("select kelurahan.kd_kel from kelurahan where kelurahan.nm_kel=?",tbDokter.getValueAt(i,9).toString());
                Sequel.queryu4("insert ignore into kecamatan values(?,?)",2,new String[]{"0",tbDokter.getValueAt(i,10).toString()});
                kdkec=Sequel.cariIsi("select kecamatan.kd_kec from kecamatan where kecamatan.nm_kec=?",tbDokter.getValueAt(i,10).toString());
                Sequel.queryu4("insert ignore into kabupaten values(?,?)",2,new String[]{"0",tbDokter.getValueAt(i,11).toString()});
                kdkab=Sequel.cariIsi("select kabupaten.kd_kab from kabupaten where kabupaten.nm_kab=?",tbDokter.getValueAt(i,11).toString());
                Sequel.queryu4("insert ignore into propinsi values(?,?)",2,new String[]{"0",tbDokter.getValueAt(i,12).toString()}); 
                kdprop=Sequel.cariIsi("select propinsi.kd_prop from propinsi where propinsi.nm_prop=?",tbDokter.getValueAt(i,12).toString());
                Sequel.queryu4("insert ignore into bahasa_pasien values(?,?)",2,new String[]{"0",tbDokter.getValueAt(i,30).toString()});
                kdbahasa=Sequel.cariIsi("select bahasa_pasien.id from propinsi where bahasa_pasien.nama_bahasa=?",tbDokter.getValueAt(i,30).toString());
                Sequel.queryu4("insert ignore into cacat_fisik values(?,?)",2,new String[]{"0",tbDokter.getValueAt(i,31).toString()});
                kdcacatfisik=Sequel.cariIsi("select cacat_fisik.id from propinsi where cacat_fisik.nama_cacat=?",tbDokter.getValueAt(i,31).toString());
                Sequel.queryu4("insert ignore into suku_bangsa values(?,?)",2,new String[]{"0",tbDokter.getValueAt(i,29).toString()});
                kdsuku=Sequel.cariIsi("select suku_bangsa.id from propinsi where suku_bangsa.nama_suku_bangsa=?",tbDokter.getValueAt(i,29).toString());
                autoNomor();
                if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",36,new String[]{
                    TNo.getText(),tbDokter.getValueAt(i,2).toString(),tbDokter.getValueAt(i,3).toString(),tbDokter.getValueAt(i,4).toString(),tbDokter.getValueAt(i,5).toString(),
                    tbDokter.getValueAt(i,6).toString(),tbDokter.getValueAt(i,7).toString(),tbDokter.getValueAt(i,8).toString(),tbDokter.getValueAt(i,13).toString(),tbDokter.getValueAt(i,14).toString(),
                    tbDokter.getValueAt(i,15).toString(),tbDokter.getValueAt(i,16).toString(),tbDokter.getValueAt(i,17).toString(),tbDokter.getValueAt(i,18).toString(),tbDokter.getValueAt(i,19).toString(),
                    tbDokter.getValueAt(i,20).toString(),tbDokter.getValueAt(i,21).toString(),tbDokter.getValueAt(i,22).toString(),KodeCaraBayar,"",kdkel,kdkec,kdkab,tbDokter.getValueAt(i,23).toString(),
                    tbDokter.getValueAt(i,24).toString(),tbDokter.getValueAt(i,25).toString(),tbDokter.getValueAt(i,26).toString(),tbDokter.getValueAt(i,27).toString(),KodePerusahaan,kdsuku,kdbahasa,
                    kdcacatfisik,tbDokter.getValueAt(i,32).toString(),tbDokter.getValueAt(i,33).toString(),kdprop,tbDokter.getValueAt(i,28).toString()
                })==false){
                    autoNomor();
                    if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",36,new String[]{
                        TNo.getText(),tbDokter.getValueAt(i,2).toString(),tbDokter.getValueAt(i,3).toString(),tbDokter.getValueAt(i,4).toString(),tbDokter.getValueAt(i,5).toString(),
                        tbDokter.getValueAt(i,6).toString(),tbDokter.getValueAt(i,7).toString(),tbDokter.getValueAt(i,8).toString(),tbDokter.getValueAt(i,13).toString(),tbDokter.getValueAt(i,14).toString(),
                        tbDokter.getValueAt(i,15).toString(),tbDokter.getValueAt(i,16).toString(),tbDokter.getValueAt(i,17).toString(),tbDokter.getValueAt(i,18).toString(),tbDokter.getValueAt(i,19).toString(),
                        tbDokter.getValueAt(i,20).toString(),tbDokter.getValueAt(i,21).toString(),tbDokter.getValueAt(i,22).toString(),KodeCaraBayar,"",kdkel,kdkec,kdkab,tbDokter.getValueAt(i,23).toString(),
                        tbDokter.getValueAt(i,24).toString(),tbDokter.getValueAt(i,25).toString(),tbDokter.getValueAt(i,26).toString(),tbDokter.getValueAt(i,27).toString(),KodePerusahaan,kdsuku,kdbahasa,
                        kdcacatfisik,tbDokter.getValueAt(i,32).toString(),tbDokter.getValueAt(i,33).toString(),kdprop,tbDokter.getValueAt(i,28).toString()
                    })==false){
                        autoNomor();
                        if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",36,new String[]{
                            TNo.getText(),tbDokter.getValueAt(i,2).toString(),tbDokter.getValueAt(i,3).toString(),tbDokter.getValueAt(i,4).toString(),tbDokter.getValueAt(i,5).toString(),
                            tbDokter.getValueAt(i,6).toString(),tbDokter.getValueAt(i,7).toString(),tbDokter.getValueAt(i,8).toString(),tbDokter.getValueAt(i,13).toString(),tbDokter.getValueAt(i,14).toString(),
                            tbDokter.getValueAt(i,15).toString(),tbDokter.getValueAt(i,16).toString(),tbDokter.getValueAt(i,17).toString(),tbDokter.getValueAt(i,18).toString(),tbDokter.getValueAt(i,19).toString(),
                            tbDokter.getValueAt(i,20).toString(),tbDokter.getValueAt(i,21).toString(),tbDokter.getValueAt(i,22).toString(),KodeCaraBayar,"",kdkel,kdkec,kdkab,tbDokter.getValueAt(i,23).toString(),
                            tbDokter.getValueAt(i,24).toString(),tbDokter.getValueAt(i,25).toString(),tbDokter.getValueAt(i,26).toString(),tbDokter.getValueAt(i,27).toString(),KodePerusahaan,kdsuku,kdbahasa,
                            kdcacatfisik,tbDokter.getValueAt(i,32).toString(),tbDokter.getValueAt(i,33).toString(),kdprop,tbDokter.getValueAt(i,28).toString()
                        })==false){
                            autoNomor();
                            if(Sequel.menyimpantf2("pasien","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rekam Medis Pasien",36,new String[]{
                                TNo.getText(),tbDokter.getValueAt(i,2).toString(),tbDokter.getValueAt(i,3).toString(),tbDokter.getValueAt(i,4).toString(),tbDokter.getValueAt(i,5).toString(),
                                tbDokter.getValueAt(i,6).toString(),tbDokter.getValueAt(i,7).toString(),tbDokter.getValueAt(i,8).toString(),tbDokter.getValueAt(i,13).toString(),tbDokter.getValueAt(i,14).toString(),
                                tbDokter.getValueAt(i,15).toString(),tbDokter.getValueAt(i,16).toString(),tbDokter.getValueAt(i,17).toString(),tbDokter.getValueAt(i,18).toString(),tbDokter.getValueAt(i,19).toString(),
                                tbDokter.getValueAt(i,20).toString(),tbDokter.getValueAt(i,21).toString(),tbDokter.getValueAt(i,22).toString(),KodeCaraBayar,"",kdkel,kdkec,kdkab,tbDokter.getValueAt(i,23).toString(),
                                tbDokter.getValueAt(i,24).toString(),tbDokter.getValueAt(i,25).toString(),tbDokter.getValueAt(i,26).toString(),tbDokter.getValueAt(i,27).toString(),KodePerusahaan,kdsuku,kdbahasa,
                                kdcacatfisik,tbDokter.getValueAt(i,32).toString(),tbDokter.getValueAt(i,33).toString(),kdprop,tbDokter.getValueAt(i,28).toString()
                            })==false){
                                autoNomor();
                            }else{
                                Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(booking_mcu_perusahaan.no_mcu,4),signed)),0)+1 from booking_mcu_perusahaan where booking_mcu_perusahaan.tanggal_mcu='"+tbDokter.getValueAt(i,17).toString()+"'","MCU",4,TNoBooking);
                                if(Sequel.menyimpantf2("booking_mcu_perusahaan","current_date(),current_time(),?,?,?,'Terdaftar',?","No.Booking",4,new String[]{
                                    TNo.getText(),tbDokter.getValueAt(i,17).toString(),TNoBooking.getText(),KodePerusahaan
                                })==true){
                                    if(Sequel.mengedittf2("booking_mcu_perusahaan_pasien_baru","no_pengajuan=?","status='Sudah Dikonfirmasi''",1,new String[]{
                                        tbDokter.getValueAt(i,1).toString()
                                    })==true){
                                        tabMode.removeRow(i);
                                        i--;
                                    }
                                }
                            }
                        }else{
                            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(booking_mcu_perusahaan.no_mcu,4),signed)),0)+1 from booking_mcu_perusahaan where booking_mcu_perusahaan.tanggal_mcu='"+tbDokter.getValueAt(i,17).toString()+"'","MCU",4,TNoBooking);
                            if(Sequel.menyimpantf2("booking_mcu_perusahaan","current_date(),current_time(),?,?,?,'Terdaftar',?","No.Booking",4,new String[]{
                                TNo.getText(),tbDokter.getValueAt(i,17).toString(),TNoBooking.getText(),KodePerusahaan
                            })==true){
                                if(Sequel.mengedittf2("booking_mcu_perusahaan_pasien_baru","no_pengajuan=?","status='Sudah Dikonfirmasi''",1,new String[]{
                                    tbDokter.getValueAt(i,1).toString()
                                })==true){
                                    tabMode.removeRow(i);
                                    i--;
                                }
                            }
                        }
                    }else{
                        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(booking_mcu_perusahaan.no_mcu,4),signed)),0)+1 from booking_mcu_perusahaan where booking_mcu_perusahaan.tanggal_mcu='"+tbDokter.getValueAt(i,17).toString()+"'","MCU",4,TNoBooking);
                        if(Sequel.menyimpantf2("booking_mcu_perusahaan","current_date(),current_time(),?,?,?,'Terdaftar',?","No.Booking",4,new String[]{
                            TNo.getText(),tbDokter.getValueAt(i,17).toString(),TNoBooking.getText(),KodePerusahaan
                        })==true){
                            if(Sequel.mengedittf2("booking_mcu_perusahaan_pasien_baru","no_pengajuan=?","status='Sudah Dikonfirmasi''",1,new String[]{
                                tbDokter.getValueAt(i,1).toString()
                            })==true){
                                tabMode.removeRow(i);
                                i--;
                            }
                        }
                    }
                }else{
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(booking_mcu_perusahaan.no_mcu,4),signed)),0)+1 from booking_mcu_perusahaan where booking_mcu_perusahaan.tanggal_mcu='"+tbDokter.getValueAt(i,17).toString()+"'","MCU",4,TNoBooking);
                    if(Sequel.menyimpantf2("booking_mcu_perusahaan","current_date(),current_time(),?,?,?,'Terdaftar',?","No.Booking",4,new String[]{
                        TNo.getText(),tbDokter.getValueAt(i,17).toString(),TNoBooking.getText(),KodePerusahaan
                    })==true){
                        if(Sequel.mengedittf2("booking_mcu_perusahaan_pasien_baru","no_pengajuan=?","status='Sudah Dikonfirmasi''",1,new String[]{
                            tbDokter.getValueAt(i,1).toString()
                        })==true){
                            tabMode.removeRow(i);
                            i--;
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnKeluar,BtnBatal);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCari1ActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari1.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnBatal.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

private void BtnCari1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari1ActionPerformed
    tampil();
}//GEN-LAST:event_BtnCari1ActionPerformed

private void BtnCari1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCari1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCari1ActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan, BtnKeluar);
        }
}//GEN-LAST:event_BtnCari1KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    for(i=0;i<tbDokter.getRowCount();i++){ 
        tbDokter.setValueAt(false,i,0);
    }
}//GEN-LAST:event_ppBersihkanActionPerformed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                if(tbDokter.getSelectedRow()!= -1){
                    if(tbDokter.getSelectedColumn()==2){
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),2).toString().equals("true")){
                            tbDokter.setValueAt(false,tbDokter.getSelectedRow(),3);
                        }
                    }
                    if(tbDokter.getSelectedColumn()==3){
                        if(tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString().equals("true")){
                            tbDokter.setValueAt(false,tbDokter.getSelectedRow(),2);
                        }
                    }
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){ 
            if(tbDokter.getValueAt(i,0).toString().equals("true")&&tbDokter.getValueAt(i,34).toString().equals("Menunggu Konfirmasi")){
                if(Sequel.meghapustf("booking_mcu_perusahaan_pasien_baru","no_pengajuan",tbDokter.getValueAt(i,1).toString())==true){
                    tabMode.removeRow(i);
                    i--;
                }
            }
        }
        
        LCount.setText(""+tabMode.getRowCount());
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
       Valid.pindah(evt,BtnSimpan,TCari); 
    }//GEN-LAST:event_BtnBatalKeyPressed

    private void ppPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppPilihActionPerformed
        for(i=0;i<tbDokter.getRowCount();i++){ 
            tbDokter.setValueAt(true,i,0);
        }
    }//GEN-LAST:event_ppPilihActionPerformed

    private void NoRmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRmKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoRmKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPasienBaruMCUPerusahaan dialog = new DlgPasienBaruMCUPerusahaan(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari1;
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.Tanggal DTPDaftar;
    private widget.Label LCount;
    private widget.TextBox NoRm;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.TextBox TNo;
    private widget.TextBox TNoBooking;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel7;
    private widget.Label label10;
    private widget.panelisi panelisi1;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppPilih;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                        "select booking_mcu_perusahaan_pasien_baru.no_pengajuan,booking_mcu_perusahaan_pasien_baru.nm_pasien,booking_mcu_perusahaan_pasien_baru.no_ktp,"+
                        "booking_mcu_perusahaan_pasien_baru.jk,booking_mcu_perusahaan_pasien_baru.tmp_lahir,booking_mcu_perusahaan_pasien_baru.tgl_lahir,booking_mcu_perusahaan_pasien_baru.nm_ibu,"+
                        "booking_mcu_perusahaan_pasien_baru.alamat,booking_mcu_perusahaan_pasien_baru.kelurahan,booking_mcu_perusahaan_pasien_baru.kecamatan,booking_mcu_perusahaan_pasien_baru.kabupaten,"+
                        "booking_mcu_perusahaan_pasien_baru.propinsi,booking_mcu_perusahaan_pasien_baru.gol_darah,booking_mcu_perusahaan_pasien_baru.pekerjaan,booking_mcu_perusahaan_pasien_baru.stts_nikah,"+
                        "booking_mcu_perusahaan_pasien_baru.agama,booking_mcu_perusahaan_pasien_baru.tgl_mcu,booking_mcu_perusahaan_pasien_baru.no_tlp,booking_mcu_perusahaan_pasien_baru.umur,"+
                        "booking_mcu_perusahaan_pasien_baru.pnd,booking_mcu_perusahaan_pasien_baru.keluarga,booking_mcu_perusahaan_pasien_baru.namakeluarga,booking_mcu_perusahaan_pasien_baru.pekerjaanpj,"+
                        "booking_mcu_perusahaan_pasien_baru.alamatpj,booking_mcu_perusahaan_pasien_baru.kelurahanpj,booking_mcu_perusahaan_pasien_baru.kecamatanpj,booking_mcu_perusahaan_pasien_baru.kabupatenpj,"+
                        "booking_mcu_perusahaan_pasien_baru.propinsipj,booking_mcu_perusahaan_pasien_baru.suku_bangsa,booking_mcu_perusahaan_pasien_baru.bahasa_pasien,booking_mcu_perusahaan_pasien_baru.cacat_fisik,"+
                        "booking_mcu_perusahaan_pasien_baru.email,booking_mcu_perusahaan_pasien_baru.nip,booking_mcu_perusahaan_pasien_baru.status from booking_mcu_perusahaan_pasien_baru "+
                        "where booking_mcu_perusahaan_pasien_baru.perusahaan_pasien=? and booking_mcu_perusahaan_pasien_baru.tgl_mcu=? order by booking_mcu_perusahaan_pasien_baru.nip");
            }else{
                ps=koneksi.prepareStatement(
                        "select booking_mcu_perusahaan_pasien_baru.no_pengajuan,booking_mcu_perusahaan_pasien_baru.nm_pasien,booking_mcu_perusahaan_pasien_baru.no_ktp,"+
                        "booking_mcu_perusahaan_pasien_baru.jk,booking_mcu_perusahaan_pasien_baru.tmp_lahir,booking_mcu_perusahaan_pasien_baru.tgl_lahir,booking_mcu_perusahaan_pasien_baru.nm_ibu,"+
                        "booking_mcu_perusahaan_pasien_baru.alamat,booking_mcu_perusahaan_pasien_baru.kelurahan,booking_mcu_perusahaan_pasien_baru.kecamatan,booking_mcu_perusahaan_pasien_baru.kabupaten,"+
                        "booking_mcu_perusahaan_pasien_baru.propinsi,booking_mcu_perusahaan_pasien_baru.gol_darah,booking_mcu_perusahaan_pasien_baru.pekerjaan,booking_mcu_perusahaan_pasien_baru.stts_nikah,"+
                        "booking_mcu_perusahaan_pasien_baru.agama,booking_mcu_perusahaan_pasien_baru.tgl_mcu,booking_mcu_perusahaan_pasien_baru.no_tlp,booking_mcu_perusahaan_pasien_baru.umur,"+
                        "booking_mcu_perusahaan_pasien_baru.pnd,booking_mcu_perusahaan_pasien_baru.keluarga,booking_mcu_perusahaan_pasien_baru.namakeluarga,booking_mcu_perusahaan_pasien_baru.pekerjaanpj,"+
                        "booking_mcu_perusahaan_pasien_baru.alamatpj,booking_mcu_perusahaan_pasien_baru.kelurahanpj,booking_mcu_perusahaan_pasien_baru.kecamatanpj,booking_mcu_perusahaan_pasien_baru.kabupatenpj,"+
                        "booking_mcu_perusahaan_pasien_baru.propinsipj,booking_mcu_perusahaan_pasien_baru.suku_bangsa,booking_mcu_perusahaan_pasien_baru.bahasa_pasien,booking_mcu_perusahaan_pasien_baru.cacat_fisik,"+
                        "booking_mcu_perusahaan_pasien_baru.email,booking_mcu_perusahaan_pasien_baru.nip,booking_mcu_perusahaan_pasien_baru.status from booking_mcu_perusahaan_pasien_baru "+
                        "where booking_mcu_perusahaan_pasien_baru.perusahaan_pasien=? and booking_mcu_perusahaan_pasien_baru.tgl_mcu=? and (booking_mcu_perusahaan_pasien_baru.no_pengajuan like ? or "+
                        "booking_mcu_perusahaan_pasien_baru.nm_pasien like ? or booking_mcu_perusahaan_pasien_baru.no_ktp like ? or booking_mcu_perusahaan_pasien_baru.status like ? or "+
                        "booking_mcu_perusahaan_pasien_baru.nip like ?) order by booking_mcu_perusahaan_pasien_baru.nip");
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,KodePerusahaan);
                    ps.setString(2,TanggalMCU);
                }else{
                    ps.setString(1,KodePerusahaan);
                    ps.setString(2,TanggalMCU);
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,"%"+TCari.getText()+"%");
                    ps.setString(5,"%"+TCari.getText()+"%");
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[]{
                        false,rs.getString("no_pengajuan"),rs.getString("nm_pasien"),rs.getString("no_ktp"),rs.getString("jk"),rs.getString("tmp_lahir"),
                        rs.getString("tgl_lahir"),rs.getString("nm_ibu"),rs.getString("alamat"),rs.getString("kelurahan"),rs.getString("kecamatan"),
                        rs.getString("kabupaten"),rs.getString("propinsi"),rs.getString("gol_darah"),rs.getString("pekerjaan"),rs.getString("stts_nikah"),
                        rs.getString("agama"),rs.getString("tgl_mcu"),rs.getString("no_tlp"),rs.getString("umur"),rs.getString("pnd"),rs.getString("keluarga"),
                        rs.getString("namakeluarga"),rs.getString("pekerjaanpj"),rs.getString("alamatpj"),rs.getString("kelurahanpj"),rs.getString("kecamatanpj"),
                        rs.getString("kabupatenpj"),rs.getString("propinsipj"),rs.getString("suku_bangsa"),rs.getString("bahasa_pasien"),rs.getString("cacat_fisik"),
                        rs.getString("email"),rs.getString("nip"),rs.getString("status")
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
            LCount.setText(""+tabMode.getRowCount());
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    private void autoNomor() {  
        if(tahun.equals("Yes")){
            awalantahun=DTPDaftar.getSelectedItem().toString().substring(8,10);
        }else{
            awalantahun="";
        }

        if(bulan.equals("Yes")){
            awalanbulan=DTPDaftar.getSelectedItem().toString().substring(3,5);
        }else{
            awalanbulan="";
        }

        if(posisitahun.equals("Depan")){
            switch (pengurutan) {
                case "Straight":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(set_no_rkm_medis.no_rkm_medis,6),signed)),0) from set_no_rkm_medis","",6,NoRm);
                    break;
                case "Terminal":
                    Valid.autoNomer4("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(RIGHT(set_no_rkm_medis.no_rkm_medis,6),5,2),SUBSTRING(RIGHT(set_no_rkm_medis.no_rkm_medis,6),3,2),SUBSTRING(RIGHT(set_no_rkm_medis.no_rkm_medis,6),1,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                    break;
                case "Middle":
                    Valid.autoNomer5("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(RIGHT(set_no_rkm_medis.no_rkm_medis,6),3,2),SUBSTRING(RIGHT(set_no_rkm_medis.no_rkm_medis,6),1,2),SUBSTRING(RIGHT(set_no_rkm_medis.no_rkm_medis,6),5,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                    break;
            }
        }else if(posisitahun.equals("Belakang")){
            switch (pengurutan) {
                case "Straight":
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(LEFT(set_no_rkm_medis.no_rkm_medis,6),signed)),0) from set_no_rkm_medis","",6,NoRm);
                    break;
                case "Terminal":
                    Valid.autoNomer4("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(LEFT(set_no_rkm_medis.no_rkm_medis,6),5,2),SUBSTRING(LEFT(set_no_rkm_medis.no_rkm_medis,6),3,2),SUBSTRING(LEFT(set_no_rkm_medis.no_rkm_medis,6),1,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                    break;
                case "Middle":
                    Valid.autoNomer5("select ifnull(MAX(CONVERT(CONCAT(SUBSTRING(LEFT(set_no_rkm_medis.no_rkm_medis,6),3,2),SUBSTRING(LEFT(set_no_rkm_medis.no_rkm_medis,6),1,2),SUBSTRING(LEFT(set_no_rkm_medis.no_rkm_medis,6),5,2)),signed)),0) from set_no_rkm_medis","",6,NoRm);
                    break;
            }            
        }

        if(posisitahun.equals("Depan")){
            TNo.setText(awalantahun+awalanbulan+NoRm.getText());
        }else if(posisitahun.equals("Belakang")){
            if(!(awalanbulan+awalantahun).equals("")){
                TNo.setText(NoRm.getText()+"-"+awalanbulan+awalantahun);
            }else{
                TNo.setText(NoRm.getText());
            }            
        }
    }
}

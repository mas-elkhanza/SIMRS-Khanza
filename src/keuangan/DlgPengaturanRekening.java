package keuangan;
import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author dosen
 */
public class DlgPengaturanRekening extends javax.swing.JDialog {
    private DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private String Tindakan_Ralan, Laborat_Ralan, Radiologi_Ralan, Obat_Ralan, Registrasi_Ralan, 
            Tambahan_Ralan, Potongan_Ralan, Tindakan_Ranap, Laborat_Ranap, Radiologi_Ranap, 
            Obat_Ranap, Registrasi_Ranap, Tambahan_Ranap, Potongan_Ranap, Retur_Obat_Ranap, 
            Resep_Pulang_Ranap, Kamar_Inap, Operasi_Ranap, Harian_Ranap, Pengadaan_Obat, 
            Pemesanan_Obat, Kontra_Pemesanan_Obat, Bayar_Pemesanan_Obat, Penjualan_Obat, 
            Piutang_Obat, Kontra_Piutang_Obat, Retur_Ke_Suplayer, Kontra_Retur_Ke_Suplayer, 
            Retur_Dari_pembeli, Kontra_Retur_Dari_Pembeli, 
            Retur_Piutang_Obat, Kontra_Retur_Piutang_Obat,Pengadaan_Ipsrs, Stok_Keluar_Ipsrs, 
            Kontra_Stok_Keluar_Ipsrs,Uang_Muka_Ranap,
            Piutang_Pasien_Ranap,Bayar_Piutang_Pasien,Service_Ranap,Pengambilan_Utd,
            Kontra_Pengambilan_Utd,Pengambilan_Penunjang_Utd,Kontra_Pengambilan_Penunjang_Utd,
            Operasi_Ralan,Penyerahan_Darah,Beban_Jasa_Medik_Dokter_Tindakan_Ralan,
            Utang_Jasa_Medik_Dokter_Tindakan_Ralan,Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,
            Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,Beban_KSO_Tindakan_Ralan, 
            Utang_KSO_Tindakan_Ralan,Beban_Jasa_Medik_Dokter_Laborat_Ralan, 
            Utang_Jasa_Medik_Dokter_Laborat_Ralan, Beban_Jasa_Medik_Petugas_Laborat_Ralan, 
            Utang_Jasa_Medik_Petugas_Laborat_Ralan, Beban_Kso_Laborat_Ralan, 
            Utang_Kso_Laborat_Ralan, HPP_Persediaan_Laborat_Rawat_Jalan, 
            Persediaan_BHP_Laborat_Rawat_Jalan,Beban_Jasa_Medik_Dokter_Radiologi_Ralan, 
            Utang_Jasa_Medik_Dokter_Radiologi_Ralan, Beban_Jasa_Medik_Petugas_Radiologi_Ralan, 
            Utang_Jasa_Medik_Petugas_Radiologi_Ralan, Beban_Kso_Radiologi_Ralan, Utang_Kso_Radiologi_Ralan, 
            HPP_Persediaan_Radiologi_Rawat_Jalan, Persediaan_BHP_Radiologi_Rawat_Jalan,
            HPP_Obat_Rawat_Jalan, Persediaan_Obat_Rawat_Jalan,Beban_Jasa_Medik_Dokter_Operasi_Ralan,
            Utang_Jasa_Medik_Dokter_Operasi_Ralan,Beban_Jasa_Medik_Paramedis_Operasi_Ralan,
            Utang_Jasa_Medik_Paramedis_Operasi_Ralan,HPP_Obat_Operasi_Ralan,Persediaan_Obat_Kamar_Operasi_Ralan,
            Suspen_Piutang_Tindakan_Ranap, Beban_Jasa_Medik_Dokter_Tindakan_Ranap, Utang_Jasa_Medik_Dokter_Tindakan_Ranap, 
            Beban_Jasa_Medik_Paramedis_Tindakan_Ranap, Utang_Jasa_Medik_Paramedis_Tindakan_Ranap, Beban_KSO_Tindakan_Ranap, 
            Utang_KSO_Tindakan_Ranap,Suspen_Piutang_Laborat_Ranap, Beban_Jasa_Medik_Dokter_Laborat_Ranap, 
            Utang_Jasa_Medik_Dokter_Laborat_Ranap, Beban_Jasa_Medik_Petugas_Laborat_Ranap, 
            Utang_Jasa_Medik_Petugas_Laborat_Ranap, Beban_Kso_Laborat_Ranap, Utang_Kso_Laborat_Ranap, 
            HPP_Persediaan_Laborat_Rawat_inap, Persediaan_BHP_Laborat_Rawat_Inap,
            Suspen_Piutang_Radiologi_Ranap, Beban_Jasa_Medik_Dokter_Radiologi_Ranap, 
            Utang_Jasa_Medik_Dokter_Radiologi_Ranap, Beban_Jasa_Medik_Petugas_Radiologi_Ranap, 
            Utang_Jasa_Medik_Petugas_Radiologi_Ranap, Beban_Kso_Radiologi_Ranap, Utang_Kso_Radiologi_Ranap, 
            HPP_Persediaan_Radiologi_Rawat_Inap, Persediaan_BHP_Radiologi_Rawat_Inap,HPP_Obat_Rawat_Inap,
            Persediaan_Obat_Rawat_Inap,Suspen_Piutang_Obat_Ranap,Suspen_Piutang_Operasi_Ranap, 
            Beban_Jasa_Medik_Dokter_Operasi_Ranap, Utang_Jasa_Medik_Dokter_Operasi_Ranap, 
            Beban_Jasa_Medik_Paramedis_Operasi_Ranap, Utang_Jasa_Medik_Paramedis_Operasi_Ranap, 
            HPP_Obat_Operasi_Ranap, Persediaan_Obat_Kamar_Operasi_Ranap,Stok_Keluar_Medis,
            Kontra_Stok_Keluar_Medis,HPP_Obat_Jual_Bebas,Persediaan_Obat_Jual_Bebas,
            Penerimaan_NonMedis,Kontra_Penerimaan_NonMedis,Bayar_Pemesanan_Non_Medis;
    private DlgRekeningTahun rekening=new DlgRekeningTahun(null,false);

    /** Creates new form DlgJadwal
     * @param parent
     * @param modal */
    public DlgPengaturanRekening(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Letak Akun Rekening","Kode Akun","Nama Akun","Tipe","Balance"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };

        tbPengaturan.setModel(tabMode);
        tbPengaturan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbPengaturan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 5; i++) {
            TableColumn column = tbPengaturan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(600);
            }else if(i==1){
                column.setPreferredWidth(90);
            }else if(i==2){
                column.setPreferredWidth(380);
            }else if(i==3){
                column.setPreferredWidth(40);
            }else if(i==4){
                column.setPreferredWidth(45);
            }
        }

        tbPengaturan.setDefaultRenderer(Object.class, new WarnaTable());
        
        rekening.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgPengaturanRekening")){
                    if(rekening.getTabel().getSelectedRow()!= -1){    
                        if(tbPengaturan.getSelectedColumn()==1){
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),1).toString(),tbPengaturan.getSelectedRow(),1);
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),2).toString(),tbPengaturan.getSelectedRow(),2);
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),3).toString(),tbPengaturan.getSelectedRow(),3);
                            tabMode.setValueAt(rekening.getTabel().getValueAt(rekening.getTabel().getSelectedRow(),4).toString(),tbPengaturan.getSelectedRow(),4);
                        }                    
                    }   
                    tbPengaturan.requestFocus();
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
        
        rekening.getTabel().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgPengaturanRekening")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        rekening.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        
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
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbPengaturan = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Pengaturan Rekening ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 56));
        panelGlass8.setLayout(null);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('U');
        BtnSimpan.setText("Update");
        BtnSimpan.setToolTipText("Alt+U");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
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
        BtnSimpan.setBounds(6, 10, 100, 30);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
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
        BtnKeluar.setBounds(109, 10, 100, 30);

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPengaturan.setToolTipText("Semua akun harus terisi");
        tbPengaturan.setName("tbPengaturan"); // NOI18N
        tbPengaturan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPengaturanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbPengaturan);

        TabRawat.addTab("Pengaturan Umum", Scroll);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        Tindakan_Ralan=tbPengaturan.getValueAt(0,1).toString();
        Beban_Jasa_Medik_Dokter_Tindakan_Ralan=tbPengaturan.getValueAt(1,1).toString();
        Utang_Jasa_Medik_Dokter_Tindakan_Ralan=tbPengaturan.getValueAt(2,1).toString();
        Beban_Jasa_Medik_Paramedis_Tindakan_Ralan=tbPengaturan.getValueAt(3,1).toString();
        Utang_Jasa_Medik_Paramedis_Tindakan_Ralan=tbPengaturan.getValueAt(4,1).toString();
        Beban_KSO_Tindakan_Ralan=tbPengaturan.getValueAt(5,1).toString();
        Utang_KSO_Tindakan_Ralan=tbPengaturan.getValueAt(6,1).toString();
        Laborat_Ralan=tbPengaturan.getValueAt(7,1).toString();
        Beban_Jasa_Medik_Dokter_Laborat_Ralan=tbPengaturan.getValueAt(8,1).toString();
        Utang_Jasa_Medik_Dokter_Laborat_Ralan=tbPengaturan.getValueAt(9,1).toString();
        Beban_Jasa_Medik_Petugas_Laborat_Ralan=tbPengaturan.getValueAt(10,1).toString();
        Utang_Jasa_Medik_Petugas_Laborat_Ralan=tbPengaturan.getValueAt(11,1).toString();
        Beban_Kso_Laborat_Ralan=tbPengaturan.getValueAt(12,1).toString();
        Utang_Kso_Laborat_Ralan=tbPengaturan.getValueAt(13,1).toString();
        HPP_Persediaan_Laborat_Rawat_Jalan=tbPengaturan.getValueAt(14,1).toString();
        Persediaan_BHP_Laborat_Rawat_Jalan=tbPengaturan.getValueAt(15,1).toString();
        Radiologi_Ralan=tbPengaturan.getValueAt(16,1).toString();
        Beban_Jasa_Medik_Dokter_Radiologi_Ralan=tbPengaturan.getValueAt(17,1).toString();
        Utang_Jasa_Medik_Dokter_Radiologi_Ralan=tbPengaturan.getValueAt(18,1).toString();
        Beban_Jasa_Medik_Petugas_Radiologi_Ralan=tbPengaturan.getValueAt(19,1).toString();
        Utang_Jasa_Medik_Petugas_Radiologi_Ralan=tbPengaturan.getValueAt(20,1).toString();
        Beban_Kso_Radiologi_Ralan=tbPengaturan.getValueAt(21,1).toString();
        Utang_Kso_Radiologi_Ralan=tbPengaturan.getValueAt(22,1).toString();
        HPP_Persediaan_Radiologi_Rawat_Jalan=tbPengaturan.getValueAt(23,1).toString();
        Persediaan_BHP_Radiologi_Rawat_Jalan=tbPengaturan.getValueAt(24,1).toString();
        Obat_Ralan=tbPengaturan.getValueAt(25,1).toString();
        HPP_Obat_Rawat_Jalan=tbPengaturan.getValueAt(26,1).toString();
        Persediaan_Obat_Rawat_Jalan=tbPengaturan.getValueAt(27,1).toString();
        Registrasi_Ralan=tbPengaturan.getValueAt(28,1).toString();
        Operasi_Ralan=tbPengaturan.getValueAt(29,1).toString();
        Beban_Jasa_Medik_Dokter_Operasi_Ralan=tbPengaturan.getValueAt(30,1).toString();
        Utang_Jasa_Medik_Dokter_Operasi_Ralan=tbPengaturan.getValueAt(31,1).toString();
        Beban_Jasa_Medik_Paramedis_Operasi_Ralan=tbPengaturan.getValueAt(32,1).toString();
        Utang_Jasa_Medik_Paramedis_Operasi_Ralan=tbPengaturan.getValueAt(33,1).toString();
        HPP_Obat_Operasi_Ralan=tbPengaturan.getValueAt(34,1).toString();
        Persediaan_Obat_Kamar_Operasi_Ralan=tbPengaturan.getValueAt(35,1).toString();
        Tambahan_Ralan=tbPengaturan.getValueAt(36,1).toString();
        Potongan_Ralan=tbPengaturan.getValueAt(37,1).toString();
        Suspen_Piutang_Tindakan_Ranap=tbPengaturan.getValueAt(38,1).toString();
        Tindakan_Ranap=tbPengaturan.getValueAt(39,1).toString();
        Beban_Jasa_Medik_Dokter_Tindakan_Ranap=tbPengaturan.getValueAt(40,1).toString();
        Utang_Jasa_Medik_Dokter_Tindakan_Ranap=tbPengaturan.getValueAt(41,1).toString(); 
        Beban_Jasa_Medik_Paramedis_Tindakan_Ranap=tbPengaturan.getValueAt(42,1).toString();
        Utang_Jasa_Medik_Paramedis_Tindakan_Ranap=tbPengaturan.getValueAt(43,1).toString();
        Beban_KSO_Tindakan_Ranap=tbPengaturan.getValueAt(44,1).toString(); 
        Utang_KSO_Tindakan_Ranap=tbPengaturan.getValueAt(45,1).toString(); 
        Suspen_Piutang_Laborat_Ranap=tbPengaturan.getValueAt(46,1).toString();
        Laborat_Ranap=tbPengaturan.getValueAt(47,1).toString();
        Beban_Jasa_Medik_Dokter_Laborat_Ranap=tbPengaturan.getValueAt(48,1).toString();
        Utang_Jasa_Medik_Dokter_Laborat_Ranap=tbPengaturan.getValueAt(49,1).toString();
        Beban_Jasa_Medik_Petugas_Laborat_Ranap=tbPengaturan.getValueAt(50,1).toString();
        Utang_Jasa_Medik_Petugas_Laborat_Ranap=tbPengaturan.getValueAt(51,1).toString();
        Beban_Kso_Laborat_Ranap=tbPengaturan.getValueAt(52,1).toString();
        Utang_Kso_Laborat_Ranap=tbPengaturan.getValueAt(53,1).toString();
        HPP_Persediaan_Laborat_Rawat_inap=tbPengaturan.getValueAt(54,1).toString();
        Persediaan_BHP_Laborat_Rawat_Inap=tbPengaturan.getValueAt(55,1).toString();        
        Suspen_Piutang_Radiologi_Ranap=tbPengaturan.getValueAt(56,1).toString();
        Radiologi_Ranap=tbPengaturan.getValueAt(57,1).toString();
        Beban_Jasa_Medik_Dokter_Radiologi_Ranap=tbPengaturan.getValueAt(58,1).toString(); 
        Utang_Jasa_Medik_Dokter_Radiologi_Ranap=tbPengaturan.getValueAt(59,1).toString();
        Beban_Jasa_Medik_Petugas_Radiologi_Ranap=tbPengaturan.getValueAt(60,1).toString();
        Utang_Jasa_Medik_Petugas_Radiologi_Ranap=tbPengaturan.getValueAt(61,1).toString();
        Beban_Kso_Radiologi_Ranap=tbPengaturan.getValueAt(62,1).toString();
        Utang_Kso_Radiologi_Ranap=tbPengaturan.getValueAt(63,1).toString();
        HPP_Persediaan_Radiologi_Rawat_Inap=tbPengaturan.getValueAt(64,1).toString();
        Persediaan_BHP_Radiologi_Rawat_Inap=tbPengaturan.getValueAt(65,1).toString();
        Suspen_Piutang_Obat_Ranap=tbPengaturan.getValueAt(66,1).toString();
        Obat_Ranap=tbPengaturan.getValueAt(67,1).toString();            
        HPP_Obat_Rawat_Inap=tbPengaturan.getValueAt(68,1).toString();  
        Persediaan_Obat_Rawat_Inap=tbPengaturan.getValueAt(69,1).toString();
        Registrasi_Ranap=tbPengaturan.getValueAt(70,1).toString();
        Service_Ranap=tbPengaturan.getValueAt(71,1).toString();
        Tambahan_Ranap=tbPengaturan.getValueAt(72,1).toString();
        Potongan_Ranap=tbPengaturan.getValueAt(73,1).toString();
        Retur_Obat_Ranap=tbPengaturan.getValueAt(74,1).toString();
        Resep_Pulang_Ranap=tbPengaturan.getValueAt(75,1).toString();
        Kamar_Inap=tbPengaturan.getValueAt(76,1).toString();
        Suspen_Piutang_Operasi_Ranap=tbPengaturan.getValueAt(77,1).toString();
        Operasi_Ranap=tbPengaturan.getValueAt(78,1).toString();
        Beban_Jasa_Medik_Dokter_Operasi_Ranap=tbPengaturan.getValueAt(79,1).toString();
        Utang_Jasa_Medik_Dokter_Operasi_Ranap=tbPengaturan.getValueAt(80,1).toString();
        Beban_Jasa_Medik_Paramedis_Operasi_Ranap=tbPengaturan.getValueAt(81,1).toString();
        Utang_Jasa_Medik_Paramedis_Operasi_Ranap=tbPengaturan.getValueAt(82,1).toString();
        HPP_Obat_Operasi_Ranap=tbPengaturan.getValueAt(83,1).toString();
        Persediaan_Obat_Kamar_Operasi_Ranap=tbPengaturan.getValueAt(84,1).toString();  
        Harian_Ranap=tbPengaturan.getValueAt(85,1).toString();
        Uang_Muka_Ranap=tbPengaturan.getValueAt(86,1).toString();
        Piutang_Pasien_Ranap=tbPengaturan.getValueAt(87,1).toString();
        Pengadaan_Obat=tbPengaturan.getValueAt(88,1).toString();
        Pemesanan_Obat=tbPengaturan.getValueAt(89,1).toString();
        Kontra_Pemesanan_Obat=tbPengaturan.getValueAt(90,1).toString();
        Bayar_Pemesanan_Obat=tbPengaturan.getValueAt(91,1).toString();
        Penjualan_Obat=tbPengaturan.getValueAt(92,1).toString();
        Piutang_Obat=tbPengaturan.getValueAt(93,1).toString();
        Kontra_Piutang_Obat=tbPengaturan.getValueAt(94,1).toString();
        Retur_Ke_Suplayer=tbPengaturan.getValueAt(95,1).toString();
        Kontra_Retur_Ke_Suplayer=tbPengaturan.getValueAt(96,1).toString();
        Retur_Dari_pembeli=tbPengaturan.getValueAt(97,1).toString();
        Kontra_Retur_Dari_Pembeli=tbPengaturan.getValueAt(98,1).toString();
        Retur_Piutang_Obat=tbPengaturan.getValueAt(99,1).toString();
        Kontra_Retur_Piutang_Obat=tbPengaturan.getValueAt(100,1).toString();
        Pengadaan_Ipsrs=tbPengaturan.getValueAt(101,1).toString();
        Stok_Keluar_Ipsrs=tbPengaturan.getValueAt(102,1).toString();
        Kontra_Stok_Keluar_Ipsrs=tbPengaturan.getValueAt(103,1).toString();
        Bayar_Piutang_Pasien=tbPengaturan.getValueAt(104,1).toString();
        Pengambilan_Utd=tbPengaturan.getValueAt(105,1).toString();
        Kontra_Pengambilan_Utd=tbPengaturan.getValueAt(106,1).toString();        
        Pengambilan_Penunjang_Utd=tbPengaturan.getValueAt(107,1).toString();
        Kontra_Pengambilan_Penunjang_Utd=tbPengaturan.getValueAt(108,1).toString();
        Penyerahan_Darah=tbPengaturan.getValueAt(109,1).toString();
        Stok_Keluar_Medis=tbPengaturan.getValueAt(110,1).toString();
        Kontra_Stok_Keluar_Medis=tbPengaturan.getValueAt(111,1).toString();
        HPP_Obat_Jual_Bebas=tbPengaturan.getValueAt(112,1).toString();
        Persediaan_Obat_Jual_Bebas=tbPengaturan.getValueAt(113,1).toString();
        Penerimaan_NonMedis=tbPengaturan.getValueAt(114,1).toString();
        Kontra_Penerimaan_NonMedis=tbPengaturan.getValueAt(115,1).toString();
        Bayar_Pemesanan_Non_Medis=tbPengaturan.getValueAt(116,1).toString();
        
        if(Tindakan_Ralan.equals("")||Laborat_Ralan.equals("")||Radiologi_Ralan.equals("")||Obat_Ralan.equals("")||
            Registrasi_Ralan.equals("")||Tambahan_Ralan.equals("")||Potongan_Ralan.equals("")||Tindakan_Ranap.equals("")||
            Laborat_Ranap.equals("")||Radiologi_Ranap.equals("")||
            Obat_Ranap.equals("")||Registrasi_Ranap.equals("")||Tambahan_Ranap.equals("")||Potongan_Ranap.equals("")||
            Retur_Obat_Ranap.equals("")||Resep_Pulang_Ranap.equals("")||Kamar_Inap.equals("")||Operasi_Ranap.equals("")||
            Harian_Ranap.equals("")||Uang_Muka_Ranap.equals("")||Piutang_Pasien_Ranap.equals("")||Pengadaan_Obat.equals("")||
            Pemesanan_Obat.equals("")||Kontra_Pemesanan_Obat.equals("")||Bayar_Pemesanan_Obat.equals("")||Penjualan_Obat.equals("")||
            Piutang_Obat.equals("")||Kontra_Piutang_Obat.equals("")||Retur_Ke_Suplayer.equals("")||Retur_Dari_pembeli.equals("")||
            Retur_Piutang_Obat.equals("")||Kontra_Retur_Piutang_Obat.equals("")||Pengadaan_Ipsrs.equals("")||
            Stok_Keluar_Ipsrs.equals("")||Kontra_Stok_Keluar_Ipsrs.equals("")||Bayar_Piutang_Pasien.equals("")||
            Kontra_Retur_Ke_Suplayer.equals("")||Kontra_Retur_Dari_Pembeli.equals("")||Service_Ranap.equals("")||
            Pengambilan_Utd.equals("")||Kontra_Pengambilan_Utd.equals("")||Pengambilan_Penunjang_Utd.equals("")||
            Kontra_Pengambilan_Penunjang_Utd.equals("")||Operasi_Ralan.equals("")||Penyerahan_Darah.equals("")||
            Beban_Jasa_Medik_Dokter_Tindakan_Ralan.equals("")||Utang_Jasa_Medik_Dokter_Tindakan_Ralan.equals("")||
            Beban_Jasa_Medik_Paramedis_Tindakan_Ralan.equals("")||Utang_Jasa_Medik_Paramedis_Tindakan_Ralan.equals("")||
            Beban_KSO_Tindakan_Ralan.equals("")||Utang_KSO_Tindakan_Ralan.equals("")||Beban_Jasa_Medik_Dokter_Laborat_Ralan.equals("")||
            Utang_Jasa_Medik_Dokter_Laborat_Ralan.equals("")||Beban_Jasa_Medik_Petugas_Laborat_Ralan.equals("")||
            Utang_Jasa_Medik_Petugas_Laborat_Ralan.equals("")||Beban_Kso_Laborat_Ralan.equals("")||Utang_Kso_Laborat_Ralan.equals("")||
            HPP_Persediaan_Laborat_Rawat_Jalan.equals("")||Persediaan_BHP_Laborat_Rawat_Jalan.equals("")||
            Beban_Jasa_Medik_Dokter_Radiologi_Ralan.equals("")||Utang_Jasa_Medik_Dokter_Radiologi_Ralan.equals("")||
            Beban_Jasa_Medik_Petugas_Radiologi_Ralan.equals("")||Utang_Jasa_Medik_Petugas_Radiologi_Ralan.equals("")||
            Beban_Kso_Radiologi_Ralan.equals("")||Utang_Kso_Radiologi_Ralan.equals("")||HPP_Persediaan_Radiologi_Rawat_Jalan.equals("")||
            Persediaan_BHP_Radiologi_Rawat_Jalan.equals("")||HPP_Obat_Rawat_Jalan.equals("")||Persediaan_Obat_Rawat_Jalan.equals("")||
            Beban_Jasa_Medik_Dokter_Operasi_Ralan.equals("")||Utang_Jasa_Medik_Dokter_Operasi_Ralan.equals("")||
            Beban_Jasa_Medik_Paramedis_Operasi_Ralan.equals("")||Utang_Jasa_Medik_Paramedis_Operasi_Ralan.equals("")||
            HPP_Obat_Operasi_Ralan.equals("")||Persediaan_Obat_Kamar_Operasi_Ralan.equals("")||Suspen_Piutang_Tindakan_Ranap.equals("")||
            Beban_Jasa_Medik_Dokter_Tindakan_Ranap.equals("")||Utang_Jasa_Medik_Dokter_Tindakan_Ranap.equals("")|| 
            Beban_Jasa_Medik_Paramedis_Tindakan_Ranap.equals("")||Utang_Jasa_Medik_Paramedis_Tindakan_Ranap.equals("")||
            Beban_KSO_Tindakan_Ranap.equals("")||Utang_KSO_Tindakan_Ranap.equals("")||Suspen_Piutang_Laborat_Ranap.equals("")||
            Beban_Jasa_Medik_Dokter_Laborat_Ranap.equals("")||Utang_Jasa_Medik_Dokter_Laborat_Ranap.equals("")||
            Beban_Jasa_Medik_Petugas_Laborat_Ranap.equals("")||Utang_Jasa_Medik_Petugas_Laborat_Ranap.equals("")||
            Beban_Kso_Laborat_Ranap.equals("")||Utang_Kso_Laborat_Ranap.equals("")||HPP_Persediaan_Laborat_Rawat_inap.equals("")||
            Persediaan_BHP_Laborat_Rawat_Inap.equals("")||Suspen_Piutang_Radiologi_Ranap.equals("")||
            Beban_Jasa_Medik_Dokter_Radiologi_Ranap.equals("")||Utang_Jasa_Medik_Dokter_Radiologi_Ranap.equals("")||
            Beban_Jasa_Medik_Petugas_Radiologi_Ranap.equals("")||Utang_Jasa_Medik_Petugas_Radiologi_Ranap.equals("")||
            Beban_Kso_Radiologi_Ranap.equals("")||Utang_Kso_Radiologi_Ranap.equals("")||HPP_Persediaan_Radiologi_Rawat_Inap.equals("")||
            Persediaan_BHP_Radiologi_Rawat_Inap.equals("")||HPP_Obat_Rawat_Inap.equals("")||Persediaan_Obat_Rawat_Inap.equals("")||
            Suspen_Piutang_Obat_Ranap.equals("")||Suspen_Piutang_Operasi_Ranap.equals("")||Beban_Jasa_Medik_Dokter_Operasi_Ranap.equals("")||
            Utang_Jasa_Medik_Dokter_Operasi_Ranap.equals("")||Beban_Jasa_Medik_Paramedis_Operasi_Ranap.equals("")||
            Utang_Jasa_Medik_Paramedis_Operasi_Ranap.equals("")||HPP_Obat_Operasi_Ranap.equals("")||Persediaan_Obat_Kamar_Operasi_Ranap.equals("")||
            Stok_Keluar_Medis.equals("")||Kontra_Stok_Keluar_Medis.equals("")||HPP_Obat_Jual_Bebas.equals("")||
            Persediaan_Obat_Jual_Bebas.equals("")||Penerimaan_NonMedis.equals("")||Kontra_Penerimaan_NonMedis.equals("")||
            Bayar_Pemesanan_Non_Medis.equals("")){
                JOptionPane.showMessageDialog(null,"Silahkan lengkapi seluruh data Akun...!!!!");
                tbPengaturan.requestFocus();
        }else{
            Sequel.queryu("delete from set_akun_ralan");
            Sequel.menyimpan("set_akun_ralan","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                Tindakan_Ralan,Beban_Jasa_Medik_Dokter_Tindakan_Ralan,Utang_Jasa_Medik_Dokter_Tindakan_Ralan,
                Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,
                Beban_KSO_Tindakan_Ralan,Utang_KSO_Tindakan_Ralan,
                Laborat_Ralan,Beban_Jasa_Medik_Dokter_Laborat_Ralan, 
                Utang_Jasa_Medik_Dokter_Laborat_Ralan, Beban_Jasa_Medik_Petugas_Laborat_Ralan, 
                Utang_Jasa_Medik_Petugas_Laborat_Ralan, Beban_Kso_Laborat_Ralan, 
                Utang_Kso_Laborat_Ralan, HPP_Persediaan_Laborat_Rawat_Jalan, 
                Persediaan_BHP_Laborat_Rawat_Jalan,Radiologi_Ralan,Beban_Jasa_Medik_Dokter_Radiologi_Ralan, 
                Utang_Jasa_Medik_Dokter_Radiologi_Ralan, Beban_Jasa_Medik_Petugas_Radiologi_Ralan, 
                Utang_Jasa_Medik_Petugas_Radiologi_Ralan, Beban_Kso_Radiologi_Ralan, Utang_Kso_Radiologi_Ralan, 
                HPP_Persediaan_Radiologi_Rawat_Jalan, Persediaan_BHP_Radiologi_Rawat_Jalan,
                Obat_Ralan,HPP_Obat_Rawat_Jalan,Persediaan_Obat_Rawat_Jalan,Registrasi_Ralan,Operasi_Ralan,
                Beban_Jasa_Medik_Dokter_Operasi_Ralan,Utang_Jasa_Medik_Dokter_Operasi_Ralan,
                Beban_Jasa_Medik_Paramedis_Operasi_Ralan,Utang_Jasa_Medik_Paramedis_Operasi_Ralan,
                HPP_Obat_Operasi_Ralan,Persediaan_Obat_Kamar_Operasi_Ralan,Tambahan_Ralan,
                Potongan_Ralan
            });
            Sequel.queryu("delete from set_akun_ranap");
            Sequel.menyimpan("set_akun_ranap","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,"
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",50,new String[]{
                Suspen_Piutang_Tindakan_Ranap, Tindakan_Ranap, Beban_Jasa_Medik_Dokter_Tindakan_Ranap, 
                Utang_Jasa_Medik_Dokter_Tindakan_Ranap, Beban_Jasa_Medik_Paramedis_Tindakan_Ranap, 
                Utang_Jasa_Medik_Paramedis_Tindakan_Ranap, Beban_KSO_Tindakan_Ranap, Utang_KSO_Tindakan_Ranap, 
                Suspen_Piutang_Laborat_Ranap, Laborat_Ranap, Beban_Jasa_Medik_Dokter_Laborat_Ranap, 
                Utang_Jasa_Medik_Dokter_Laborat_Ranap, Beban_Jasa_Medik_Petugas_Laborat_Ranap, 
                Utang_Jasa_Medik_Petugas_Laborat_Ranap, Beban_Kso_Laborat_Ranap, Utang_Kso_Laborat_Ranap, 
                HPP_Persediaan_Laborat_Rawat_inap, Persediaan_BHP_Laborat_Rawat_Inap, Suspen_Piutang_Radiologi_Ranap, 
                Radiologi_Ranap, Beban_Jasa_Medik_Dokter_Radiologi_Ranap, Utang_Jasa_Medik_Dokter_Radiologi_Ranap, 
                Beban_Jasa_Medik_Petugas_Radiologi_Ranap, Utang_Jasa_Medik_Petugas_Radiologi_Ranap, Beban_Kso_Radiologi_Ranap, 
                Utang_Kso_Radiologi_Ranap, HPP_Persediaan_Radiologi_Rawat_Inap, Persediaan_BHP_Radiologi_Rawat_Inap, 
                Suspen_Piutang_Obat_Ranap, Obat_Ranap, HPP_Obat_Rawat_Inap, Persediaan_Obat_Rawat_Inap, Registrasi_Ranap, 
                Service_Ranap, Tambahan_Ranap, Potongan_Ranap, Retur_Obat_Ranap, Resep_Pulang_Ranap, Kamar_Inap, 
                Suspen_Piutang_Operasi_Ranap, Operasi_Ranap, Beban_Jasa_Medik_Dokter_Operasi_Ranap, 
                Utang_Jasa_Medik_Dokter_Operasi_Ranap, Beban_Jasa_Medik_Paramedis_Operasi_Ranap, 
                Utang_Jasa_Medik_Paramedis_Operasi_Ranap, HPP_Obat_Operasi_Ranap, Persediaan_Obat_Kamar_Operasi_Ranap, 
                Harian_Ranap, Uang_Muka_Ranap, Piutang_Pasien_Ranap
            });
            Sequel.queryu("delete from set_akun");
            Sequel.menyimpan("set_akun","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",29,new String[]{
                Pengadaan_Obat,
                Pemesanan_Obat,Kontra_Pemesanan_Obat,Bayar_Pemesanan_Obat,Penjualan_Obat,Piutang_Obat,
                Kontra_Piutang_Obat,Retur_Ke_Suplayer,Kontra_Retur_Ke_Suplayer,Retur_Dari_pembeli,
                Kontra_Retur_Dari_Pembeli,Retur_Piutang_Obat,Kontra_Retur_Piutang_Obat,Pengadaan_Ipsrs,
                Stok_Keluar_Ipsrs,Kontra_Stok_Keluar_Ipsrs,Bayar_Piutang_Pasien,Pengambilan_Utd,
                Kontra_Pengambilan_Utd,Pengambilan_Penunjang_Utd,Kontra_Pengambilan_Penunjang_Utd,
                Penyerahan_Darah,Stok_Keluar_Medis,Kontra_Stok_Keluar_Medis,HPP_Obat_Jual_Bebas,
                Persediaan_Obat_Jual_Bebas,Penerimaan_NonMedis,Kontra_Penerimaan_NonMedis,
                Bayar_Pemesanan_Non_Medis
            });
            JOptionPane.showMessageDialog(null,"Proses selesai...!!!!");
            tampil();
        }
            
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void tbPengaturanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPengaturanKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                akses.setform("DlgPengaturanRekening");
                rekening.emptTeks();
                rekening.tampil();
                rekening.isCek();
                rekening.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                rekening.setLocationRelativeTo(internalFrame1);
                rekening.setVisible(true);
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                tabMode.setValueAt("",tbPengaturan.getSelectedRow(),1);
                tabMode.setValueAt("",tbPengaturan.getSelectedRow(),2);
            }
        }
    }//GEN-LAST:event_tbPengaturanKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgPengaturanRekening dialog = new DlgPengaturanRekening(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnSimpan;
    private widget.ScrollPane Scroll;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame1;
    private widget.panelisi panelGlass8;
    private widget.Table tbPengaturan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{    
            Tindakan_Ralan="";
            Laborat_Ralan="";
            Radiologi_Ralan="";
            Obat_Ralan="";
            Registrasi_Ralan="";
            Tambahan_Ralan="";
            Potongan_Ralan="";
            Tindakan_Ranap="";
            Laborat_Ranap="";
            Radiologi_Ranap="";
            Obat_Ranap="";
            Registrasi_Ranap="";
            Tambahan_Ranap="";
            Potongan_Ranap="";
            Retur_Obat_Ranap="";
            Resep_Pulang_Ranap="";
            Kamar_Inap="";
            Operasi_Ranap="";
            Harian_Ranap="";
            Uang_Muka_Ranap="";
            Piutang_Pasien_Ranap="";
            Pengadaan_Obat="";
            Pemesanan_Obat="";
            Kontra_Pemesanan_Obat="";
            Bayar_Pemesanan_Obat="";
            Penjualan_Obat="";
            Piutang_Obat="";
            Kontra_Piutang_Obat="";
            Retur_Ke_Suplayer="";
            Kontra_Retur_Ke_Suplayer="";
            Retur_Dari_pembeli="";
            Kontra_Retur_Dari_Pembeli="";
            Retur_Piutang_Obat="";
            Kontra_Retur_Piutang_Obat="";
            Pengadaan_Ipsrs="";
            Stok_Keluar_Ipsrs="";
            Kontra_Stok_Keluar_Ipsrs="";
            Bayar_Piutang_Pasien="";
            Service_Ranap="";
            Pengambilan_Utd="";
            Kontra_Pengambilan_Utd="";
            Pengambilan_Penunjang_Utd="";
            Kontra_Pengambilan_Penunjang_Utd="";
            Operasi_Ralan="";
            Penyerahan_Darah="";
            Beban_Jasa_Medik_Dokter_Tindakan_Ralan="";
            Utang_Jasa_Medik_Dokter_Tindakan_Ralan="";                    
            Beban_Jasa_Medik_Paramedis_Tindakan_Ralan="";
            Utang_Jasa_Medik_Paramedis_Tindakan_Ralan="";
            Beban_KSO_Tindakan_Ralan="";
            Utang_KSO_Tindakan_Ralan="";
            Beban_Jasa_Medik_Dokter_Laborat_Ralan="";
            Utang_Jasa_Medik_Dokter_Laborat_Ralan="";
            Beban_Jasa_Medik_Petugas_Laborat_Ralan="";
            Utang_Jasa_Medik_Petugas_Laborat_Ralan="";
            Beban_Kso_Laborat_Ralan="";
            Utang_Kso_Laborat_Ralan="";
            HPP_Persediaan_Laborat_Rawat_Jalan="";
            Persediaan_BHP_Laborat_Rawat_Jalan="";
            Beban_Jasa_Medik_Dokter_Radiologi_Ralan="";
            Utang_Jasa_Medik_Dokter_Radiologi_Ralan="";
            Beban_Jasa_Medik_Petugas_Radiologi_Ralan="";
            Utang_Jasa_Medik_Petugas_Radiologi_Ralan="";
            Beban_Kso_Radiologi_Ralan="";
            Utang_Kso_Radiologi_Ralan="";
            HPP_Persediaan_Radiologi_Rawat_Jalan="";
            Persediaan_BHP_Radiologi_Rawat_Jalan="";    
            HPP_Obat_Rawat_Jalan="";
            Persediaan_Obat_Rawat_Jalan="";
            Beban_Jasa_Medik_Dokter_Operasi_Ralan="";
            Utang_Jasa_Medik_Dokter_Operasi_Ralan="";
            Beban_Jasa_Medik_Paramedis_Operasi_Ralan="";
            Utang_Jasa_Medik_Paramedis_Operasi_Ralan="";
            HPP_Obat_Operasi_Ralan="";
            Persediaan_Obat_Kamar_Operasi_Ralan="";
            Suspen_Piutang_Tindakan_Ranap="";
            Beban_Jasa_Medik_Dokter_Tindakan_Ranap="";
            Utang_Jasa_Medik_Dokter_Tindakan_Ranap="";
            Beban_Jasa_Medik_Paramedis_Tindakan_Ranap="";
            Utang_Jasa_Medik_Paramedis_Tindakan_Ranap="";
            Beban_KSO_Tindakan_Ranap="";
            Utang_KSO_Tindakan_Ranap="";
            Suspen_Piutang_Laborat_Ranap="";
            Beban_Jasa_Medik_Dokter_Laborat_Ranap="";
            Utang_Jasa_Medik_Dokter_Laborat_Ranap="";
            Beban_Jasa_Medik_Petugas_Laborat_Ranap="";
            Utang_Jasa_Medik_Petugas_Laborat_Ranap="";
            Beban_Kso_Laborat_Ranap="";
            Utang_Kso_Laborat_Ranap="";
            HPP_Persediaan_Laborat_Rawat_inap="";
            Persediaan_BHP_Laborat_Rawat_Inap="";
            Suspen_Piutang_Radiologi_Ranap="";
            Beban_Jasa_Medik_Dokter_Radiologi_Ranap="";
            Utang_Jasa_Medik_Dokter_Radiologi_Ranap="";
            Beban_Jasa_Medik_Petugas_Radiologi_Ranap="";
            Utang_Jasa_Medik_Petugas_Radiologi_Ranap="";
            Beban_Kso_Radiologi_Ranap="";
            Utang_Kso_Radiologi_Ranap="";
            HPP_Persediaan_Radiologi_Rawat_Inap="";
            Persediaan_BHP_Radiologi_Rawat_Inap="";  
            HPP_Obat_Rawat_Inap="";
            Persediaan_Obat_Rawat_Inap="";
            Suspen_Piutang_Obat_Ranap="";
            Suspen_Piutang_Operasi_Ranap="";
            Beban_Jasa_Medik_Dokter_Operasi_Ranap="";
            Utang_Jasa_Medik_Dokter_Operasi_Ranap="";
            Beban_Jasa_Medik_Paramedis_Operasi_Ranap="";
            Utang_Jasa_Medik_Paramedis_Operasi_Ranap="";
            HPP_Obat_Operasi_Ranap="";
            Persediaan_Obat_Kamar_Operasi_Ranap="";
            HPP_Obat_Jual_Bebas="";
            Persediaan_Obat_Jual_Bebas="";
            Penerimaan_NonMedis="";
            Kontra_Penerimaan_NonMedis="";
            Bayar_Pemesanan_Non_Medis="";
            
            ps=koneksi.prepareStatement("select * from set_akun_ralan");
            try {
                rs=ps.executeQuery();
                if(rs.next()){
                    Tindakan_Ralan=rs.getString("Tindakan_Ralan");                    
                    Beban_Jasa_Medik_Dokter_Tindakan_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ralan");
                    Beban_Jasa_Medik_Paramedis_Tindakan_Ralan=rs.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ralan=rs.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ralan");
                    Beban_KSO_Tindakan_Ralan=rs.getString("Beban_KSO_Tindakan_Ralan");
                    Utang_KSO_Tindakan_Ralan=rs.getString("Utang_KSO_Tindakan_Ralan");
                    Beban_Jasa_Medik_Dokter_Laborat_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Laborat_Ralan");
                    Utang_Jasa_Medik_Dokter_Laborat_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Laborat_Ralan"); 
                    Beban_Jasa_Medik_Petugas_Laborat_Ralan=rs.getString("Beban_Jasa_Medik_Petugas_Laborat_Ralan");
                    Utang_Jasa_Medik_Petugas_Laborat_Ralan=rs.getString("Utang_Jasa_Medik_Petugas_Laborat_Ralan");
                    Beban_Kso_Laborat_Ralan=rs.getString("Beban_Kso_Laborat_Ralan");
                    Utang_Kso_Laborat_Ralan=rs.getString("Utang_Kso_Laborat_Ralan");
                    HPP_Persediaan_Laborat_Rawat_Jalan=rs.getString("HPP_Persediaan_Laborat_Rawat_Jalan");
                    Persediaan_BHP_Laborat_Rawat_Jalan=rs.getString("Persediaan_BHP_Laborat_Rawat_Jalan");
                    Beban_Jasa_Medik_Dokter_Radiologi_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ralan");
                    Utang_Jasa_Medik_Dokter_Radiologi_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ralan");
                    Beban_Jasa_Medik_Petugas_Radiologi_Ralan=rs.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ralan");
                    Utang_Jasa_Medik_Petugas_Radiologi_Ralan=rs.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ralan");
                    Beban_Kso_Radiologi_Ralan=rs.getString("Beban_Kso_Radiologi_Ralan");
                    Utang_Kso_Radiologi_Ralan=rs.getString("Utang_Kso_Radiologi_Ralan");
                    Operasi_Ralan=rs.getString("Operasi_Ralan");
                    HPP_Persediaan_Radiologi_Rawat_Jalan=rs.getString("HPP_Persediaan_Radiologi_Rawat_Jalan");
                    Persediaan_BHP_Radiologi_Rawat_Jalan=rs.getString("Persediaan_BHP_Radiologi_Rawat_Jalan");
                    HPP_Obat_Rawat_Jalan=rs.getString("HPP_Obat_Rawat_Jalan");
                    Persediaan_Obat_Rawat_Jalan=rs.getString("Persediaan_Obat_Rawat_Jalan");
                    Beban_Jasa_Medik_Dokter_Operasi_Ralan=rs.getString("Beban_Jasa_Medik_Dokter_Operasi_Ralan");
                    Utang_Jasa_Medik_Dokter_Operasi_Ralan=rs.getString("Utang_Jasa_Medik_Dokter_Operasi_Ralan");
                    Beban_Jasa_Medik_Paramedis_Operasi_Ralan=rs.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ralan");
                    Utang_Jasa_Medik_Paramedis_Operasi_Ralan=rs.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ralan");
                    HPP_Obat_Operasi_Ralan=rs.getString("HPP_Obat_Operasi_Ralan");
                    Persediaan_Obat_Kamar_Operasi_Ralan=rs.getString("Persediaan_Obat_Kamar_Operasi_Ralan");
                    Laborat_Ralan=rs.getString("Laborat_Ralan");
                    Radiologi_Ralan=rs.getString("Radiologi_Ralan");
                    Obat_Ralan=rs.getString("Obat_Ralan");
                    Registrasi_Ralan=rs.getString("Registrasi_Ralan");
                    Tambahan_Ralan=rs.getString("Tambahan_Ralan");
                    Potongan_Ralan=rs.getString("Potongan_Ralan");
                }                 
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }    
            
            ps=koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rs=ps.executeQuery();
                if(rs.next()){                    
                    Tindakan_Ranap=rs.getString("Tindakan_Ranap");
                    Laborat_Ranap=rs.getString("Laborat_Ranap");
                    Radiologi_Ranap=rs.getString("Radiologi_Ranap");
                    Obat_Ranap=rs.getString("Obat_Ranap");
                    Registrasi_Ranap=rs.getString("Registrasi_Ranap");
                    Tambahan_Ranap=rs.getString("Tambahan_Ranap");
                    Potongan_Ranap=rs.getString("Potongan_Ranap");
                    Retur_Obat_Ranap=rs.getString("Retur_Obat_Ranap");
                    Resep_Pulang_Ranap=rs.getString("Resep_Pulang_Ranap");
                    Kamar_Inap=rs.getString("Kamar_Inap");
                    Operasi_Ranap=rs.getString("Operasi_Ranap");
                    Harian_Ranap=rs.getString("Harian_Ranap");
                    Uang_Muka_Ranap=rs.getString("Uang_Muka_Ranap");
                    Piutang_Pasien_Ranap=rs.getString("Piutang_Pasien_Ranap"); 
                    Service_Ranap=rs.getString("Service_Ranap");
                    Suspen_Piutang_Tindakan_Ranap=rs.getString("Suspen_Piutang_Tindakan_Ranap");
                    Beban_Jasa_Medik_Dokter_Tindakan_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Utang_Jasa_Medik_Dokter_Tindakan_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Tindakan_Ranap");
                    Beban_Jasa_Medik_Paramedis_Tindakan_Ranap=rs.getString("Beban_Jasa_Medik_Paramedis_Tindakan_Ranap");
                    Utang_Jasa_Medik_Paramedis_Tindakan_Ranap=rs.getString("Utang_Jasa_Medik_Paramedis_Tindakan_Ranap");
                    Beban_KSO_Tindakan_Ranap=rs.getString("Beban_KSO_Tindakan_Ranap");
                    Utang_KSO_Tindakan_Ranap=rs.getString("Utang_KSO_Tindakan_Ranap");
                    Suspen_Piutang_Laborat_Ranap=rs.getString("Suspen_Piutang_Laborat_Ranap");
                    Beban_Jasa_Medik_Dokter_Laborat_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Laborat_Ranap");
                    Utang_Jasa_Medik_Dokter_Laborat_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Laborat_Ranap");
                    Beban_Jasa_Medik_Petugas_Laborat_Ranap=rs.getString("Beban_Jasa_Medik_Petugas_Laborat_Ranap");
                    Utang_Jasa_Medik_Petugas_Laborat_Ranap=rs.getString("Utang_Jasa_Medik_Petugas_Laborat_Ranap");
                    Beban_Kso_Laborat_Ranap=rs.getString("Beban_Kso_Laborat_Ranap");
                    Utang_Kso_Laborat_Ranap=rs.getString("Utang_Kso_Laborat_Ranap");
                    HPP_Persediaan_Laborat_Rawat_inap=rs.getString("HPP_Persediaan_Laborat_Rawat_inap");
                    Persediaan_BHP_Laborat_Rawat_Inap=rs.getString("Persediaan_BHP_Laborat_Rawat_Inap");
                    Suspen_Piutang_Radiologi_Ranap=rs.getString("Suspen_Piutang_Radiologi_Ranap");
                    Beban_Jasa_Medik_Dokter_Radiologi_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Radiologi_Ranap");
                    Utang_Jasa_Medik_Dokter_Radiologi_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Radiologi_Ranap");
                    Beban_Jasa_Medik_Petugas_Radiologi_Ranap=rs.getString("Beban_Jasa_Medik_Petugas_Radiologi_Ranap");
                    Utang_Jasa_Medik_Petugas_Radiologi_Ranap=rs.getString("Utang_Jasa_Medik_Petugas_Radiologi_Ranap");
                    Beban_Kso_Radiologi_Ranap=rs.getString("Beban_Kso_Radiologi_Ranap");
                    Utang_Kso_Radiologi_Ranap=rs.getString("Utang_Kso_Radiologi_Ranap");
                    HPP_Persediaan_Radiologi_Rawat_Inap=rs.getString("HPP_Persediaan_Radiologi_Rawat_Inap");
                    Persediaan_BHP_Radiologi_Rawat_Inap=rs.getString("Persediaan_BHP_Radiologi_Rawat_Inap");
                    HPP_Obat_Rawat_Inap=rs.getString("HPP_Obat_Rawat_Inap");
                    Persediaan_Obat_Rawat_Inap=rs.getString("Persediaan_Obat_Rawat_Inap");
                    Suspen_Piutang_Obat_Ranap=rs.getString("Suspen_Piutang_Obat_Ranap");
                    Suspen_Piutang_Operasi_Ranap=rs.getString("Suspen_Piutang_Operasi_Ranap");
                    Beban_Jasa_Medik_Dokter_Operasi_Ranap=rs.getString("Beban_Jasa_Medik_Dokter_Operasi_Ranap");
                    Utang_Jasa_Medik_Dokter_Operasi_Ranap=rs.getString("Utang_Jasa_Medik_Dokter_Operasi_Ranap");
                    Beban_Jasa_Medik_Paramedis_Operasi_Ranap=rs.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ranap");
                    Utang_Jasa_Medik_Paramedis_Operasi_Ranap=rs.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ranap");
                    HPP_Obat_Operasi_Ranap=rs.getString("HPP_Obat_Operasi_Ranap");
                    Persediaan_Obat_Kamar_Operasi_Ranap=rs.getString("Persediaan_Obat_Kamar_Operasi_Ranap");
                }            
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }    
            
            ps=koneksi.prepareStatement("select * from set_akun");
            try {
                rs=ps.executeQuery();
                if(rs.next()){                    
                    Pengadaan_Obat=rs.getString("Pengadaan_Obat");
                    Pemesanan_Obat=rs.getString("Pemesanan_Obat");
                    Kontra_Pemesanan_Obat=rs.getString("Kontra_Pemesanan_Obat");
                    Bayar_Pemesanan_Obat=rs.getString("Bayar_Pemesanan_Obat");
                    Penjualan_Obat=rs.getString("Penjualan_Obat");
                    Piutang_Obat=rs.getString("Piutang_Obat");
                    Kontra_Piutang_Obat=rs.getString("Kontra_Piutang_Obat");
                    Retur_Ke_Suplayer=rs.getString("Retur_Ke_Suplayer");
                    Kontra_Retur_Ke_Suplayer=rs.getString("Kontra_Retur_Ke_Suplayer");
                    Retur_Dari_pembeli=rs.getString("Retur_Dari_pembeli");
                    Kontra_Retur_Dari_Pembeli=rs.getString("Kontra_Retur_Dari_Pembeli");
                    Retur_Piutang_Obat=rs.getString("Retur_Piutang_Obat");
                    Kontra_Retur_Piutang_Obat=rs.getString("Kontra_Retur_Piutang_Obat");
                    Pengadaan_Ipsrs=rs.getString("Pengadaan_Ipsrs");
                    Stok_Keluar_Ipsrs=rs.getString("Stok_Keluar_Ipsrs");
                    Kontra_Stok_Keluar_Ipsrs=rs.getString("Kontra_Stok_Keluar_Ipsrs");
                    Bayar_Piutang_Pasien=rs.getString("Bayar_Piutang_Pasien");
                    Pengambilan_Utd=rs.getString("Pengambilan_Utd");
                    Kontra_Pengambilan_Utd=rs.getString("Kontra_Pengambilan_Utd");
                    Pengambilan_Penunjang_Utd=rs.getString("Pengambilan_Penunjang_Utd");
                    Kontra_Pengambilan_Penunjang_Utd=rs.getString("Kontra_Pengambilan_Penunjang_Utd");
                    Penyerahan_Darah=rs.getString("Penyerahan_Darah");
                    Stok_Keluar_Medis=rs.getString("Stok_Keluar_Medis");
                    Kontra_Stok_Keluar_Medis=rs.getString("Kontra_Stok_Keluar_Medis");
                    HPP_Obat_Jual_Bebas=rs.getString("HPP_Obat_Jual_Bebas");
                    Persediaan_Obat_Jual_Bebas=rs.getString("Persediaan_Obat_Jual_Bebas");
                    Penerimaan_NonMedis=rs.getString("Penerimaan_NonMedis");
                    Kontra_Penerimaan_NonMedis=rs.getString("Kontra_Penerimaan_NonMedis");
                    Bayar_Pemesanan_Non_Medis=rs.getString("Bayar_Pemesanan_Non_Medis");
                }               
            } catch (Exception e) {
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }    
            
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Tindakan pada menu Billing Rawat Jalan",Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Tindakan Rawat Jalan",Beban_Jasa_Medik_Dokter_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Tindakan Rawat Jalan",Utang_Jasa_Medik_Dokter_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Paramedis Tindakan Rawat Jalan",Beban_Jasa_Medik_Paramedis_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Paramedis Tindakan Rawat Jalan",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Tindakan Rawat Jalan",Beban_KSO_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_KSO_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_KSO_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_KSO_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Tindakan Rawat Jalan",Utang_KSO_Tindakan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_KSO_Tindakan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_KSO_Tindakan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_KSO_Tindakan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Laborat pada menu Billing Rawat Jalan",Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Laborat Rawat Jalan",Beban_Jasa_Medik_Dokter_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Laborat Rawat Jalan",Utang_Jasa_Medik_Dokter_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Petugas Laborat Rawat Jalan",Beban_Jasa_Medik_Petugas_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Petugas Laborat Rawat Jalan",Utang_Jasa_Medik_Petugas_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Laborat Rawat Jalan",Beban_Kso_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Kso_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Kso_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Kso_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Laborat Rawat Jalan",Utang_Kso_Laborat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Kso_Laborat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Kso_Laborat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Kso_Laborat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP BHP Laborat Rawat Jalan",HPP_Persediaan_Laborat_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_Jalan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan BHP Laborat Rawat Jalan",Persediaan_BHP_Laborat_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Jalan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Radiologi pada menu Billing Rawat Jalan",Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Radiologi Rawat Jalan",Beban_Jasa_Medik_Dokter_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Radiologi Rawat Jalan",Utang_Jasa_Medik_Dokter_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Petugas Radiologi Rawat Jalan",Beban_Jasa_Medik_Petugas_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Petugas Radiologi Rawat Jalan",Utang_Jasa_Medik_Petugas_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Radiologi Rawat Jalan",Beban_Kso_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Kso_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Kso_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Kso_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Radiologi Rawat Jalan",Utang_Kso_Radiologi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Kso_Radiologi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Kso_Radiologi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Kso_Radiologi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP BHP Radiologi Rawat Jalan",HPP_Persediaan_Radiologi_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Jalan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan BHP Radiologi Rawat Jalan",Persediaan_BHP_Radiologi_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Jalan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Obat pada menu Billing Rawat Jalan",Obat_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Obat_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Obat_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Obat_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Obat Rawat Jalan",HPP_Obat_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Obat_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Obat_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Obat_Rawat_Jalan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan Obat Rawat Jalan",Persediaan_Obat_Rawat_Jalan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_Obat_Rawat_Jalan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_Obat_Rawat_Jalan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_Obat_Rawat_Jalan)
            });            
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Registrasi pada menu Billing Rawat Jalan",Registrasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Registrasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Registrasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Registrasi_Ralan)
            });            
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Operasi pada menu Billing Rawat Jalan",Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Operasi_Ralan)
            });            
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Operasi Ralan",Beban_Jasa_Medik_Dokter_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Operasi Ralan",Utang_Jasa_Medik_Dokter_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ralan)
            });             
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Paramedis Operasi Ralan",Beban_Jasa_Medik_Paramedis_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Paramedis Operasi Ralan",Utang_Jasa_Medik_Paramedis_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ralan)
            });             
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Obat Operasi Ralan",HPP_Obat_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Obat_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Obat_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Obat_Operasi_Ralan)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan Obat Kamar Operasi Ralan",Persediaan_Obat_Kamar_Operasi_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ralan)
            }); 
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Tambahan Biaya pada menu Billing Rawat Jalan",Tambahan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tambahan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tambahan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tambahan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Potongan Biaya pada Billing menu Rawat Jalan",Potongan_Ralan,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Potongan_Ralan),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Potongan_Ralan),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Potongan_Ralan)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Tindakan Rawat Inap",Suspen_Piutang_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Tindakan Rawat Inap",Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Tindakan Ranap",Beban_Jasa_Medik_Dokter_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Tindakan Ranap",Utang_Jasa_Medik_Dokter_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Paramedis Tindakan Ranap",Beban_Jasa_Medik_Paramedis_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Paramedis Tindakan Ranap",Utang_Jasa_Medik_Paramedis_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Tindakan Ranap",Beban_KSO_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_KSO_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_KSO_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_KSO_Tindakan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Tindakan Ranap",Utang_KSO_Tindakan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_KSO_Tindakan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_KSO_Tindakan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_KSO_Tindakan_Ranap)
            });
            
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Laborat Ranap",Suspen_Piutang_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Laborat_Ranap)
            });            
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Laborat Rawat Inap",Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Laborat_Ranap)
            });            
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Laborat Ranap",Beban_Jasa_Medik_Dokter_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Laborat Ranap",Utang_Jasa_Medik_Dokter_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Petugas Laborat Ranap",Beban_Jasa_Medik_Petugas_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Petugas Laborat Ranap",Utang_Jasa_Medik_Petugas_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Laborat Ranap",Beban_Kso_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Kso_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Kso_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Kso_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Laborat Ranap",Utang_Kso_Laborat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Kso_Laborat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Kso_Laborat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Kso_Laborat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Persediaan Laborat Rawat Inap",HPP_Persediaan_Laborat_Rawat_inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Persediaan_Laborat_Rawat_inap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan BHP Laborat Rawat Inap",Persediaan_BHP_Laborat_Rawat_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_BHP_Laborat_Rawat_Inap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Radiologi Ranap",Suspen_Piutang_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Radiologi Rawat Inap",Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Radiologi Ranap",Beban_Jasa_Medik_Dokter_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Radiologi Ranap",Utang_Jasa_Medik_Dokter_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Petugas Radiologi Ranap",Beban_Jasa_Medik_Petugas_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Petugas_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Petugas Radiologi Ranap",Utang_Jasa_Medik_Petugas_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Petugas_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban KSO Radiologi Ranap",Beban_Kso_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Kso_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Kso_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Kso_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang KSO Radiologi Ranap",Utang_Kso_Radiologi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Kso_Radiologi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Kso_Radiologi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Kso_Radiologi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Persediaan Radiologi Rawat Inap",HPP_Persediaan_Radiologi_Rawat_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Persediaan_Radiologi_Rawat_Inap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan BHP Radiologi Rawat Inap",Persediaan_BHP_Radiologi_Rawat_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_BHP_Radiologi_Rawat_Inap)
            });
            
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Obat Ranap",Suspen_Piutang_Obat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Obat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Obat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Obat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Obat pada menu Billing Rawat Inap",Obat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Obat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Obat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Obat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Obat Rawat Inap",HPP_Obat_Rawat_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Obat_Rawat_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Obat_Rawat_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Obat_Rawat_Inap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan Obat Rawat Inap",Persediaan_Obat_Rawat_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_Obat_Rawat_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_Obat_Rawat_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_Obat_Rawat_Inap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Registrasi pada menu Billing Rawat Inap",Registrasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Registrasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Registrasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Registrasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Biaya Service pada menu Billing Rawat Inap",Service_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Service_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Service_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Service_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Tambahan Biaya pada menu Billing Rawat Inap",Tambahan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Tambahan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Tambahan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Tambahan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Potongan Biaya pada menu Billing Rawat Inap",Potongan_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Potongan_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Potongan_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Potongan_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Retur Obat pada menu Billing Rawat Inap",Retur_Obat_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Obat_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Obat_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Obat_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Resep Pulang pada menu Billing Rawat Inap",Resep_Pulang_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Resep_Pulang_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Resep_Pulang_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Resep_Pulang_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Kamar Inap pada menu Billing Rawat Inap",Kamar_Inap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kamar_Inap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kamar_Inap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kamar_Inap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Suspen Piutang Operasi Ranap",Suspen_Piutang_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Suspen_Piutang_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Suspen_Piutang_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Suspen_Piutang_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Operasi Rawat Inap",Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Dokter Operasi Ranap",Beban_Jasa_Medik_Dokter_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Dokter_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Dokter Operasi Ranap",Utang_Jasa_Medik_Dokter_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Dokter_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Beban Jasa Medik Paramedis Operasi Ranap",Beban_Jasa_Medik_Paramedis_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Beban_Jasa_Medik_Paramedis_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Utang Jasa Medik Paramedis Operasi Ranap",Utang_Jasa_Medik_Paramedis_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Utang_Jasa_Medik_Paramedis_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Obat Operasi Ranap",HPP_Obat_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Obat_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Obat_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Obat_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan Obat Kamar Operasi Ranap",Persediaan_Obat_Kamar_Operasi_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_Obat_Kamar_Operasi_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Harian Ranap pada menu Billing Rawat Inap",Harian_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Harian_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Harian_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Harian_Ranap)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Uang Muka Pasien pada Billing menu Rawat Inap",Uang_Muka_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Uang_Muka_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Uang_Muka_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Uang_Muka_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Piutang Pasien pada Billing menu Rawat Inap",Piutang_Pasien_Ranap,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Piutang_Pasien_Ranap),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Piutang_Pasien_Ranap),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Piutang_Pasien_Ranap)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Pengadaan Obat & BHP pada menu Pengadaan Obat & BHP",Pengadaan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pengadaan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pengadaan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pengadaan_Obat)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Penerimaan Obat & BHP pada menu Penerimaan Obat & BHP",Pemesanan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pemesanan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pemesanan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pemesanan_Obat)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Penerimaan Obat & BHP pada menu Penerimaan Obat & BHP",Kontra_Pemesanan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Pemesanan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Pemesanan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Pemesanan_Obat)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Bayar Pemesanan Obat/BHP pada menu Bayar Pesan Obat/BHP",Bayar_Pemesanan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Bayar_Pemesanan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Bayar_Pemesanan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Bayar_Pemesanan_Obat)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Penjualan Obat & BHP pada menu Penjualan Obat & BHP",Penjualan_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Penjualan_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Penjualan_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Penjualan_Obat)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Piutang Obat & BHP pada menu Piutang Obat & BHP",Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Piutang Obat & BHP pada menu Piutang Obat & BHP",Kontra_Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Retur Obat & BHP ke Suplier pada menu Retur Ke Suplier",Retur_Ke_Suplayer,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Ke_Suplayer),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Ke_Suplayer),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Ke_Suplayer)
            });
            tabMode.addRow(new Object[]{" [Debet] Kontra Akun Retur Obat & BHP ke Suplier pada menu Retur Ke Suplier",Kontra_Retur_Ke_Suplayer,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Ke_Suplayer),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Ke_Suplayer),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Ke_Suplayer)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Retur Obat & BHP dari Pasien/Pembeli pada menu Retur Dari Pembeli",Retur_Dari_pembeli,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Dari_pembeli),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Dari_pembeli),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Dari_pembeli)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Retur Obat & BHP dari Pasien/Pembeli pada menu Retur Dari Pembeli",Kontra_Retur_Dari_Pembeli,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Dari_Pembeli),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Dari_Pembeli),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Dari_Pembeli)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Retur Piutang Obat & BHP pada menu Retur Piutang Pembeli",Retur_Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Retur_Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Retur_Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Retur_Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Retur Piutang Obat & BHP pada menu Retur Piutang Pembeli",Kontra_Retur_Piutang_Obat,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Retur_Piutang_Obat),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Retur_Piutang_Obat),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Retur_Piutang_Obat)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Pengadaan Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Pengadaan Barang",Pengadaan_Ipsrs,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pengadaan_Ipsrs),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pengadaan_Ipsrs),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pengadaan_Ipsrs)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Stok Keluar Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Stok Keluar",Stok_Keluar_Ipsrs,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Stok_Keluar_Ipsrs)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Stok Keluar Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Stok Keluar",Kontra_Stok_Keluar_Ipsrs,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Stok_Keluar_Ipsrs),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Stok_Keluar_Ipsrs)
            });        
            tabMode.addRow(new Object[]{" [Kredit] Akun Pembayaran Piutang Pasien pada menu Piutang Pasien",Bayar_Piutang_Pasien,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Bayar_Piutang_Pasien),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Bayar_Piutang_Pasien),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Bayar_Piutang_Pasien)
            });  
            tabMode.addRow(new Object[]{" [Debet] Akun Pengambilan BHP Medis UTD pada menu Pengambilan BHP UTD",Pengambilan_Utd,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pengambilan_Utd),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pengambilan_Utd),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pengambilan_Utd)
            });  
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Pengambilan BHP Medis UTD pada menu Pengambilan BHP UTD",Kontra_Pengambilan_Utd,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Pengambilan_Utd),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Pengambilan_Utd),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Pengambilan_Utd)
            });  
            tabMode.addRow(new Object[]{" [Debet] Akun Pengambilan Barang Penunjang/Non Medis UTD pada menu Pengambilan Non Medis UTD",Pengambilan_Penunjang_Utd,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Pengambilan_Penunjang_Utd),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Pengambilan_Penunjang_Utd),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Pengambilan_Penunjang_Utd)
            });  
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Pengambilan Barang Penunjang/Non Medis UTD pada menu Pengambilan Non Medis UTD",Kontra_Pengambilan_Penunjang_Utd,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Pengambilan_Penunjang_Utd),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Pengambilan_Penunjang_Utd),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Pengambilan_Penunjang_Utd)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Pendapatan Penjualan Darah pada menu Penyerahan Darah",Penyerahan_Darah,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Penyerahan_Darah),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Penyerahan_Darah),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Penyerahan_Darah)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Stok Keluar Barang Medis (Obat, Alkes & BHP) pada menu Stok Keluar Medis",Stok_Keluar_Medis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Stok_Keluar_Medis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Stok_Keluar_Medis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Stok_Keluar_Medis)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Stok Keluar Barang Medis (Obat, Alkes & BHP) pada menu Stok Keluar Medis",Kontra_Stok_Keluar_Medis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Stok_Keluar_Medis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Stok_Keluar_Medis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Stok_Keluar_Medis)
            }); 
            tabMode.addRow(new Object[]{" [Debet] Akun HPP Obat Jual Bebas",HPP_Obat_Jual_Bebas,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",HPP_Obat_Jual_Bebas),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",HPP_Obat_Jual_Bebas),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",HPP_Obat_Jual_Bebas)
            });
            tabMode.addRow(new Object[]{" [Kredit] Akun Persediaan Obat Jual Bebas",Persediaan_Obat_Jual_Bebas,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Persediaan_Obat_Jual_Bebas),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Persediaan_Obat_Jual_Bebas),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Persediaan_Obat_Jual_Bebas)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Penerimaan Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Penerimaan Barang Non Medis",Penerimaan_NonMedis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Penerimaan_NonMedis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Penerimaan_NonMedis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Penerimaan_NonMedis)
            });
            tabMode.addRow(new Object[]{" [Kredit] Kontra Akun Penerimaan Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Penerimaan Barang Non Medis",Kontra_Penerimaan_NonMedis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Kontra_Penerimaan_NonMedis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Kontra_Penerimaan_NonMedis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Kontra_Penerimaan_NonMedis)
            });
            tabMode.addRow(new Object[]{" [Debet] Akun Bayar Pemesanan Barang Non Medis dan Penunjang ( Lab & RO ) pada menu Bayar Pesan Non Medis",Bayar_Pemesanan_Non_Medis,
                Sequel.cariIsi("select nm_rek from rekening where kd_rek=?",Bayar_Pemesanan_Non_Medis),
                Sequel.cariIsi("select tipe from rekening where kd_rek=?",Bayar_Pemesanan_Non_Medis),
                Sequel.cariIsi("select balance from rekening where kd_rek=?",Bayar_Pemesanan_Non_Medis)
            });
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    public void isCek(){
        BtnSimpan.setEnabled(akses.getpengaturan_rekening());
    }
        
    
}

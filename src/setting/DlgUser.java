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


package setting;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariDokter;
import simrskhanza.DlgCariPetugas;

/**
 *
 * @author perpustakaan
 */
public class DlgUser extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private String user="";
    private int i=0;

    /** Creates new form DlgUser
     * @param parent
     * @param modal */
    public DlgUser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(706,674);

        Object[] row={"ID User","Nama User","Password","[I]ICD 10","[I]Obat Penyakit","[C]Dokter","[A]Jadwal Praktek","[C]Petugas","[L]Pasien","[A]Registrasi","[A]Tindakan Ralan",
                    "[A]Kamar Inap","[A]Tindakan Ranap","[A]Operasi","[A]Rujukan Keluar","[A]Rujukan Masuk","[A]Beri Obat, Alkes & BHP","[A]Resep Pulang",
                    "[L]Pasien Meninggal","[A]Diet Pasien","[L]Kelahiran Bayi","[A]Periksa Lab","[A]Periksa Radiologi","[A]Kasir Ralan",
                    "[J]Deposit Pasien","[J]Piutang Pasien","[L]Peminjaman Berkas RM","[C]Barcode Presensi","[C]Presensi Harian","[C]Presensi Bulanan",
                    "[C]Pegawai Admin","[C]Pegawai User","[D]Suplier Obat/Alkes/BHP","[D]Satuan Barang","[D]Konversi Satuan","[D]Jenis Barang","[D]Obat, Alkes & BHP",
                    "[D]Stok Opname Apotek","[D]Stok Obat Pasien","[D]Pengadaan Obat, Alkes & BHP","[D]Pemesanan Obat, Alkes & BHP","[D]Penjualan Obat, Alkes & BHP","[D]Piutang Obat, Alkes & BHP",
                    "[D]Retur Ke Suplier","[D]Retur Dari Pembeli","[D]Retur Obat, Alkes & BHP Ranap","[D]Retur Piutang Pembeli","[D]Keuntungan Penjualan",
                    "[D]Keuntungan Beri Obat, Alkes & BHP","[D]Sirkulasi Obat, Alkes & BHP","[E]Barang Non Medis","[E]Pengadaan Barang Nonmedis","[E]Stok Keluar Non Medis",
                    "[E]Rekap Pengadaan Non Medis","[E]Rekap Stok Keluar Non Medis","[E]Biaya Pengadaan Non Medis","[F]Jenis Inventaris",
                    "[F]Kategori Inventaris","[F]Merk Inventaris","[F]Ruang Inventaris","[F]Produsen Inventaris","[F]Koleksi Inventaris",
                    "[F]Inventaris","[F]Sirkulasi Inventaris","[G]Jenis Parkir","[G]Parkir Masuk","[G]Parkir Keluar","[G]Rekap Parkir Harian",
                    "[G]Rekap Parkir Bulanan","[A]Informasi Kamar","[H]Harian Dokter Poli","[H]Obat Per Poli","[H]Obat Per Kamar",
                    "[H]Obat Per Dokter Ralan","[H]Obat Per Dokter Ranap","[H]Harian Dokter","[H]Bulanan Dokter","[H]Harian Paramedis",
                    "[H]Bulanan Paramedis","[H]Pembayaran Ralan","[H]Pembayaran Ranap","[H]Rekap Pembayaran Ralan","[H]Rekap Pembayaran Ranap",
                    "[H]Tagihan Masuk","[H]Tambahan Biaya","[H]Potongan Biaya","[A]Resep Obat","[L]Riwayat Perawatan","[I]Frekuensi Penyakit Ralan","[I]Frekuensi Penyakit Ranap",
                    "[J]Kamar","[J]Tarif Ralan","[J]Tarif Ranap","[J]Tarif Lab","[J]Tarif Radiologi","[J]Tarif Operasi","[J]Akun Rekening","[J]Rekening Tahun",
                    "[J]Posting Jurnal","[J]Buku Besar","[J]Cash Flow","[J]Keuangan","[J]Pengeluaran Harian","[N]Set P.J. Unit Penunjang","[N]Set Oto Lokasi","[N]Set Kamar Inap",
                    "[N]Set Embalase & Tuslah","[N]Tracer Login","[N]Display Antrian","[N]Set Harga Obat","[N]Set Penggunaan Tarif","[N]Set Oto Ralan","[N]Biaya Harian Kamar",
                    "[N]Biaya Masuk Sekali","[N]Set RM","[A]Billing Ralan","[A]Billing Ranap","[H]Detail JM Dokter","[A]IGD","[B]Barcode Ralan","[B]Barcode Ranap",
                    "[N]Set Harga Obat Ralan","[N]Set Harga Obat Ranap","[I]Penyakit AFP & PD3I","[I]Surveilans AFP & PD3I","[I]Surveilans Ralan","[L]Diagnosa Pasien",
                    "[I]Surveilans Ranap","[I]Pny.Tdk Menular Ranap","[I]Pny.Tdk Menular Ralan","[I]Kunjungan Ralan","[I]RL 3.2 Rawat Darurat","[I]RL 3.3 Gigi dan Mulut","[I]RL 3.7 Radiologi","[I]RL 3.8 Laboratorium","[H]Harian Dokter Ralan",
                    "[C]SMS Gateway","[C]Sidik Jari","[C]Jam Presensi","[C]Jadwal Pegawai","[G]Barcode Parkir","[N]Set Billing","[A]DPJP Ranap","[D]Mutasi Obat/Alkes/BHP","[I]RL 3.4 Kebidanan","[I]RL 3.6 Pembedahan",
                    "[H]Fee Visit Dokter","[H]Fee Bacaan EKG","[H]Fee Rujukan Rontgen","[H]Fee Rujukan Ranap","[H]Fee Periksa Ralan","[J]Akun Bayar","[J]Bayar Pesan Obat",
                    "[H]Obat Per Dokter Peresep","[E]Jenis Non Medis","[J]Pemasukkan Lain-Lain","[J]Pengaturan Rekening","[N]Closing Kasir","[N]Set Keterlambatan Presensi",
                    "[N]Set Harga Kamar","[N]Rekap Per Shift","[K]Cek NIK","[K]Cek No.Kartu","[K]Riwayat Peserta","[H]Obat Per Cara Bayar","[I]Kunjungan Ranap",
                    "[J]Bayar Piutang","[H]Payment Point","[K]Cek No.Rujukan Pcare","[I]ICD 9","[D]Darurat Stok","[L]Retensi Data R.M.","[C]Temporary Presensi",
                    "[J]Jurnal Harian","[D]Sirkulasi Obat, Alkes & BHP 2","[A]Edit Registrasi","[K]Referensi Diagnosa BPJS","[K]Referensi Poli BPJS","[D]Industri Farmasi",
                    "[H]Harian J.S.","[H]Bulanan J.S.","[H]Harian BHP Medis/Paket Obat","[H]Bulanan BHP Medis/Paket Obat","[J]Piutang Belum Lunas","[K]Referensi Faskes BPJS",
                    "[K]Data Bridging SEP BPJS","[D]Pengambilan BHP UTD","[J]Tarif UTD","[M]Pengambilan BHP Medis","[M]BHP Medis Rusak","[E]Pengambilan UTD","[M]Pengambilan BHP Non Medis",
                    "[M]BHP Non Medis Rusak","[E]Suplier Non Medis","[M]Donor Darah","[K]Monitoring Verifikasi Klaim","[M]Cekal Darah","[M]Komponen Darah","[M]Stok Darah","[M]Pemisahan Darah",
                    "[H]Harian Kamar","[J]Rincian Piutang Pasien","[D]Keuntungan Beri Obat, Alkes & BHP 2","[K]Reklasifikasi Ralan","[K]Reklasifikasi Ranap","[M]Penyerahan Darah",
                    "[J]Hutang Obat & BHP","[D]Riwayat Obat, Alkes & BHP","[I]Sensus Harian Poli","[I]RL 4A Sebab Morbiditas Ranap","[K]Referensi Kamar Aplicare","[K]Ketersediaan Kamar Aplicare",
                    "[K]Klaim Baru Otomatis INACBG","[K]Klaim Baru Manual INACBG","[K]Coder NIK INACBG","[L]Mutasi Berkas RM","[J]Akun Piutang","[H]Harian KSO","[H]Bulanan KSO",
                    "[H]Harian Menejemen","[H]Bulanan Menejemen","[K]Cek Eligibilitas Inhealth","[K]Referensi Ruang Rawat Inhealth","[K]Referensi Poli Inhealth","[K]Referensi Faskes Inhealth",
                    "[K]Data Bridging SJP Inhealth","[H]Piutang Ralan","[H]Piutang Ranap","[J]Piutang Per Cara Bayar","[I]Lama Pelayanan Ralan","[L]Catatan Pasien","[I]RL 4B Sebab Morbiditas Ralan",
                    "[I]RL 4A Morbiditas Ralan","[I]RL 4B Morbiditas Ralan","[L]Data HAIs","[I]Harian HAIs","[I]Bulanan HAIs","[I]Hitung BOR"
        };
        
        tabMode=new DefaultTableModel(null,row){
              @Override 
              public boolean isCellEditable(int rowIndex, int colIndex){
                  boolean a = true;
                    if ((colIndex==0)||(colIndex==1)||(colIndex==2)) {
                        a=false;
                    }
                    return a;
              }              
              Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class,java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class,
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, 
                java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbUser.setModel(tabMode);
        //tbJabatan.setDefaultRenderer(Object.class, new WarnaTable(Scroll.getBackground(),Color.GREEN));
        tbUser.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbUser.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 238;i++) {
            TableColumn column = tbUser.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(150);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(58);
            }else if(i==4){
                column.setPreferredWidth(90);
            }else if(i==5){
                column.setPreferredWidth(59);
            }else if(i==6){
                column.setPreferredWidth(100);
            }else if(i==7){
                column.setPreferredWidth(68);
            }else if(i==8){
                column.setPreferredWidth(59);
            }else if(i==9){
                column.setPreferredWidth(78);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(84);
            }else if(i==12){
                column.setPreferredWidth(103);
            }else if(i==13){
                column.setPreferredWidth(66);
            }else if(i==14){
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(100);
            }else if(i==16){
                column.setPreferredWidth(135);
            }else if(i==17){
                column.setPreferredWidth(94);
            }else if(i==18){
                column.setPreferredWidth(110);
            }else if(i==19){
                column.setPreferredWidth(82);
            }else if(i==20){
                column.setPreferredWidth(93);
            }else if(i==21){
                column.setPreferredWidth(82);
            }else if(i==22){
                column.setPreferredWidth(107);
            }else if(i==23){
                column.setPreferredWidth(81);
            }else if(i==24){
                column.setPreferredWidth(95);
            }else if(i==25){
                column.setPreferredWidth(95);
            }else if(i==26){
                column.setPreferredWidth(135);
            }else if(i==27){
                column.setPreferredWidth(110);
            }else if(i==28){
                column.setPreferredWidth(103);
            }else if(i==29){
                column.setPreferredWidth(108);
            }else if(i==30){
                column.setPreferredWidth(100);
            }else if(i==31){
                column.setPreferredWidth(92);
            }else if(i==32){
                column.setPreferredWidth(136);
            }else if(i==33){
                column.setPreferredWidth(98);
            }else if(i==34){
                column.setPreferredWidth(105);
            }else if(i==35){
                column.setPreferredWidth(87);
            }else if(i==36){
                column.setPreferredWidth(114);
            }else if(i==37){
                column.setPreferredWidth(127);
            }else if(i==38){
                column.setPreferredWidth(110);
            }else if(i==39){
                column.setPreferredWidth(170);
            }else if(i==40){
                column.setPreferredWidth(170);
            }else if(i==41){
                column.setPreferredWidth(163);
            }else if(i==42){
                column.setPreferredWidth(153);
            }else if(i==43){
                column.setPreferredWidth(101);
            }else if(i==44){
                column.setPreferredWidth(115);
            }else if(i==45){
                column.setPreferredWidth(178);
            }else if(i==229){
                column.setPreferredWidth(130);
            }else if(i==230){
                column.setPreferredWidth(105);
            }else if(i==231){
                column.setPreferredWidth(170);
            }else if(i==232){
                column.setPreferredWidth(138);
            }else if(i==233){
                column.setPreferredWidth(138);
            }else if(i==234){
                column.setPreferredWidth(78);
            }else if(i==235){
                column.setPreferredWidth(85);
            }else if(i==236){
                column.setPreferredWidth(88);
            }else if(i==237){
                column.setPreferredWidth(80);
            }else{
                column.setPreferredWidth(120);
            }
        }
        tbUser.setDefaultRenderer(Object.class, new WarnaTable());

        TKd.setDocument(new batasInput((byte)30).getKata(TKd));
        TPass.setDocument(new batasInput((byte)50).getKata(TPass));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));       
        
        dlgdokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dlgdokter.getTable().getSelectedRow()!= -1){
                    TKd.setText(dlgdokter.getTable().getValueAt(dlgdokter.getTable().getSelectedRow(),0).toString());
                    TNmUser.setText(dlgdokter.getTable().getValueAt(dlgdokter.getTable().getSelectedRow(),1).toString());
                    TPass.setText(dlgdokter.getTable().getValueAt(dlgdokter.getTable().getSelectedRow(),0).toString());
                }  
                TKd.requestFocus();
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
        
        dlgpetugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dlgpetugas.getTable().getSelectedRow()!= -1){                   
                    TKd.setText(dlgpetugas.getTable().getValueAt(dlgpetugas.getTable().getSelectedRow(),0).toString());
                    TNmUser.setText(dlgpetugas.getTable().getValueAt(dlgpetugas.getTable().getSelectedRow(),1).toString());
                    TPass.setText(dlgpetugas.getTable().getValueAt(dlgpetugas.getTable().getSelectedRow(),0).toString());
                }            
                TKd.requestFocus();
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

        TKd.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){

            @Override
            public void insertUpdate(DocumentEvent e) {
                isUser();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                isUser();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                isUser();
            }
        });
    }

    DlgCariDokter dlgdokter=new DlgCariDokter(null,false);
    DlgCariPetugas dlgpetugas=new DlgCariPetugas(null,false);

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do falseT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbUser = new widget.Table();
        panelGlass5 = new widget.panelisi();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TKd = new widget.TextBox();
        TPass = new widget.TextBox();
        BtnSeek = new widget.Button();
        BtnSeek1 = new widget.Button();
        TNmUser = new widget.TextBox();
        jPanel1 = new javax.swing.JPanel();
        panelGlass7 = new widget.panelisi();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        panelGlass6 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Setup User ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbUser.setAutoCreateRowSorter(true);
        tbUser.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbUser.setName("tbUser"); // NOI18N
        tbUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbUserMouseClicked(evt);
            }
        });
        tbUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbUserKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbUser);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 47));
        panelGlass5.setLayout(null);

        jLabel3.setText("Dokter/Petugas :");
        jLabel3.setName("jLabel3"); // NOI18N
        panelGlass5.add(jLabel3);
        jLabel3.setBounds(10, 12, 94, 23);

        jLabel4.setText("Password :");
        jLabel4.setName("jLabel4"); // NOI18N
        panelGlass5.add(jLabel4);
        jLabel4.setBounds(449, 12, 60, 23);

        TKd.setHighlighter(null);
        TKd.setName("TKd"); // NOI18N
        TKd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKdKeyPressed(evt);
            }
        });
        panelGlass5.add(TKd);
        TKd.setBounds(107, 12, 107, 23);

        TPass.setName("TPass"); // NOI18N
        TPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPassKeyPressed(evt);
            }
        });
        panelGlass5.add(TPass);
        TPass.setBounds(511, 12, 180, 23);

        BtnSeek.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek.setMnemonic('1');
        BtnSeek.setToolTipText("Alt+1");
        BtnSeek.setName("BtnSeek"); // NOI18N
        BtnSeek.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeekActionPerformed(evt);
            }
        });
        BtnSeek.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeekKeyPressed(evt);
            }
        });
        panelGlass5.add(BtnSeek);
        BtnSeek.setBounds(216, 12, 28, 23);

        BtnSeek1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSeek1.setMnemonic('2');
        BtnSeek1.setToolTipText("Alt+2");
        BtnSeek1.setName("BtnSeek1"); // NOI18N
        BtnSeek1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek1ActionPerformed(evt);
            }
        });
        BtnSeek1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek1KeyPressed(evt);
            }
        });
        panelGlass5.add(BtnSeek1);
        BtnSeek1.setBounds(245, 12, 28, 23);

        TNmUser.setEditable(false);
        TNmUser.setName("TNmUser"); // NOI18N
        TNmUser.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNmUserKeyPressed(evt);
            }
        });
        panelGlass5.add(TNmUser);
        TNmUser.setBounds(275, 12, 160, 23);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_START);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        jLabel6.setRequestFocusEnabled(false);
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(350, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BtnCariKeyReleased(evt);
            }
        });
        panelGlass7.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
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
        panelGlass7.add(BtnAll);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        panelGlass7.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass7.add(LCount);

        jPanel1.add(panelGlass7, java.awt.BorderLayout.PAGE_START);

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

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
        panelGlass6.add(BtnSimpan);

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
        panelGlass6.add(BtnBatal);

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
        panelGlass6.add(BtnHapus);

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
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
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

        jPanel1.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TKd.getText().trim().equals("")||TNmUser.getText().trim().equals("")){
            Valid.textKosong(TKd,"User");
        }else if(TPass.getText().trim().equals("")){
            Valid.textKosong(TPass,"Password");
        }else{
            if(Sequel.menyimpantf("user","AES_ENCRYPT('"+TKd.getText()+"','nur'),AES_ENCRYPT('"+TPass.getText()+"','windi'),'false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false'","User")==true){
                tampil();
                emptTeks();
            }            
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,TPass,BtnHapus);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TKd.requestFocus();
        }else if(TPass.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik data pada table untuk memilih...!!!!");
        }else if(! TPass.getText().trim().equals("")){
            Sequel.queryu("delete from user where id_user=AES_ENCRYPT('"+TKd.getText()+"','nur')");
            tampil();
            emptTeks();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnSimpan, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TKd.getText().trim().equals("")||TNmUser.getText().trim().equals("")){
            Valid.textKosong(TKd,"User");
        }else if(TPass.getText().trim().equals("")){
            Valid.textKosong(TPass,"Password");
        }else{
            i=tbUser.getSelectedRow();
            if(i!= -1){
                Sequel.mengedit("user","id_user=AES_ENCRYPT('"+tbUser.getValueAt(i,0).toString()+"','nur')",
                    "id_user=AES_ENCRYPT('"+TKd.getText()+"','nur'),"+
                    "password=AES_ENCRYPT('"+TPass.getText()+"','windi'),"+
                    "penyakit='"+tbUser.getValueAt(i,3).toString()+"', "+
                    "obat_penyakit='"+tbUser.getValueAt(i,4).toString()+"',"+
                    "dokter='"+tbUser.getValueAt(i,5).toString()+"',"+
                    "jadwal_praktek='"+tbUser.getValueAt(i,6).toString()+"',"+
                    "petugas='"+tbUser.getValueAt(i,7).toString()+"',"+
                    "pasien='"+tbUser.getValueAt(i,8).toString()+"',"+
                    "registrasi='"+tbUser.getValueAt(i,9).toString()+"',"+
                    "tindakan_ralan='"+tbUser.getValueAt(i,10).toString()+"',"+
                    "kamar_inap='"+tbUser.getValueAt(i,11).toString()+"',"+
                    "tindakan_ranap='"+tbUser.getValueAt(i,12).toString()+"',"+
                    "operasi='"+tbUser.getValueAt(i,13).toString()+"',"+
                    "rujukan_keluar='"+tbUser.getValueAt(i,14).toString()+"',"+
                    "rujukan_masuk='"+tbUser.getValueAt(i,15).toString()+"',"+
                    "beri_obat='"+tbUser.getValueAt(i,16).toString()+"',"+
                    "resep_pulang='"+tbUser.getValueAt(i,17).toString()+"',"+
                    "pasien_meninggal='"+tbUser.getValueAt(i,18).toString()+"',"+
                    "diet_pasien='"+tbUser.getValueAt(i,19).toString()+"',"+
                    "kelahiran_bayi='"+tbUser.getValueAt(i,20).toString()+"',"+
                    "periksa_lab='"+tbUser.getValueAt(i,21).toString()+"',"+
                    "periksa_radiologi='"+tbUser.getValueAt(i,22).toString()+"',"+
                    "kasir_ralan='"+tbUser.getValueAt(i,23).toString()+"',"+
                    "deposit_pasien='"+tbUser.getValueAt(i,24).toString()+"',"+
                    "piutang_pasien='"+tbUser.getValueAt(i,25).toString()+"',"+
                    "peminjaman_berkas='"+tbUser.getValueAt(i,26).toString()+"',"+
                    "barcode='"+tbUser.getValueAt(i,27).toString()+"',"+
                    "presensi_harian='"+tbUser.getValueAt(i,28).toString()+"',"+
                    "presensi_bulanan='"+tbUser.getValueAt(i,29).toString()+"',"+
                    "pegawai_admin='"+tbUser.getValueAt(i,30).toString()+"',"+
                    "pegawai_user='"+tbUser.getValueAt(i,31).toString()+"',"+
                    "suplier='"+tbUser.getValueAt(i,32).toString()+"',"+
                    "satuan_barang='"+tbUser.getValueAt(i,33).toString()+"',"+
                    "konversi_satuan='"+tbUser.getValueAt(i,34).toString()+"',"+
                    "jenis_barang='"+tbUser.getValueAt(i,35).toString()+"',"+
                    "obat='"+tbUser.getValueAt(i,36).toString()+"',"+
                    "stok_opname_obat='"+tbUser.getValueAt(i,37).toString()+"',"+
                    "stok_obat_pasien='"+tbUser.getValueAt(i,38).toString()+"',"+
                    "pengadaan_obat='"+tbUser.getValueAt(i,39).toString()+"',"+
                    "pemesanan_obat='"+tbUser.getValueAt(i,40).toString()+"',"+
                    "penjualan_obat='"+tbUser.getValueAt(i,41).toString()+"',"+
                    "piutang_obat='"+tbUser.getValueAt(i,42).toString()+"',"+
                    "retur_ke_suplier='"+tbUser.getValueAt(i,43).toString()+"',"+
                    "retur_dari_pembeli='"+tbUser.getValueAt(i,44).toString()+"',"+
                    "retur_obat_ranap='"+tbUser.getValueAt(i,45).toString()+"',"+
                    "retur_piutang_pasien='"+tbUser.getValueAt(i,46).toString()+"',"+
                    "keuntungan_penjualan='"+tbUser.getValueAt(i,47).toString()+"',"+
                    "keuntungan_beri_obat='"+tbUser.getValueAt(i,48).toString()+"',"+
                    "sirkulasi_obat='"+tbUser.getValueAt(i,49).toString()+"',"+
                    "ipsrs_barang='"+tbUser.getValueAt(i,50).toString()+"',"+
                    "ipsrs_pengadaan_barang='"+tbUser.getValueAt(i,51).toString()+"',"+
                    "ipsrs_stok_keluar='"+tbUser.getValueAt(i,52).toString()+"',"+
                    "ipsrs_rekap_pengadaan='"+tbUser.getValueAt(i,53).toString()+"',"+
                    "ipsrs_rekap_stok_keluar='"+tbUser.getValueAt(i,54).toString()+"',"+
                    "ipsrs_pengeluaran_harian='"+tbUser.getValueAt(i,55).toString()+"',"+
                    "inventaris_jenis='"+tbUser.getValueAt(i,56).toString()+"',"+
                    "inventaris_kategori='"+tbUser.getValueAt(i,57).toString()+"',"+
                    "inventaris_merk='"+tbUser.getValueAt(i,58).toString()+"',"+
                    "inventaris_ruang='"+tbUser.getValueAt(i,59).toString()+"',"+
                    "inventaris_produsen='"+tbUser.getValueAt(i,60).toString()+"',"+
                    "inventaris_koleksi='"+tbUser.getValueAt(i,61).toString()+"',"+
                    "inventaris_inventaris='"+tbUser.getValueAt(i,62).toString()+"',"+
                    "inventaris_sirkulasi='"+tbUser.getValueAt(i,63).toString()+"',"+
                    "parkir_jenis='"+tbUser.getValueAt(i,64).toString()+"',"+
                    "parkir_in='"+tbUser.getValueAt(i,65).toString()+"',"+
                    "parkir_out='"+tbUser.getValueAt(i,66).toString()+"',"+
                    "parkir_rekap_harian='"+tbUser.getValueAt(i,67).toString()+"',"+
                    "parkir_rekap_bulanan='"+tbUser.getValueAt(i,68).toString()+"',"+
                    "informasi_kamar='"+tbUser.getValueAt(i,69).toString()+"',"+
                    "harian_tindakan_poli='"+tbUser.getValueAt(i,70).toString()+"',"+
                    "obat_per_poli='"+tbUser.getValueAt(i,71).toString()+"',"+
                    "obat_per_kamar='"+tbUser.getValueAt(i,72).toString()+"',"+
                    "obat_per_dokter_ralan='"+tbUser.getValueAt(i,73).toString()+"',"+
                    "obat_per_dokter_ranap='"+tbUser.getValueAt(i,74).toString()+"',"+
                    "harian_dokter='"+tbUser.getValueAt(i,75).toString()+"',"+
                    "bulanan_dokter='"+tbUser.getValueAt(i,76).toString()+"',"+
                    "harian_paramedis='"+tbUser.getValueAt(i,77).toString()+"',"+
                    "bulanan_paramedis='"+tbUser.getValueAt(i,78).toString()+"',"+
                    "pembayaran_ralan='"+tbUser.getValueAt(i,79).toString()+"',"+
                    "pembayaran_ranap='"+tbUser.getValueAt(i,80).toString()+"',"+
                    "rekap_pembayaran_ralan='"+tbUser.getValueAt(i,81).toString()+"',"+
                    "rekap_pembayaran_ranap='"+tbUser.getValueAt(i,82).toString()+"',"+
                    "tagihan_masuk='"+tbUser.getValueAt(i,83).toString()+"',"+
                    "tambahan_biaya='"+tbUser.getValueAt(i,84).toString()+"',"+
                    "potongan_biaya='"+tbUser.getValueAt(i,85).toString()+"',"+
                    "resep_obat='"+tbUser.getValueAt(i,86).toString()+"',"+
                    "resume_pasien='"+tbUser.getValueAt(i,87).toString()+"',"+
                    "penyakit_ralan='"+tbUser.getValueAt(i,88).toString()+"',"+
                    "penyakit_ranap='"+tbUser.getValueAt(i,89).toString()+"',"+
                    "kamar='"+tbUser.getValueAt(i,90).toString()+"',"+
                    "tarif_ralan='"+tbUser.getValueAt(i,91).toString()+"',"+
                    "tarif_ranap='"+tbUser.getValueAt(i,92).toString()+"',"+
                    "tarif_lab='"+tbUser.getValueAt(i,93).toString()+"',"+
                    "tarif_radiologi='"+tbUser.getValueAt(i,94).toString()+"',"+
                    "tarif_operasi='"+tbUser.getValueAt(i,95).toString()+"',"+
                    "akun_rekening='"+tbUser.getValueAt(i,96).toString()+"',"+
                    "rekening_tahun='"+tbUser.getValueAt(i,97).toString()+"',"+
                    "posting_jurnal='"+tbUser.getValueAt(i,98).toString()+"',"+
                    "buku_besar='"+tbUser.getValueAt(i,99).toString()+"',"+
                    "cashflow='"+tbUser.getValueAt(i,100).toString()+"',"+
                    "keuangan='"+tbUser.getValueAt(i,101).toString()+"',"+
                    "pengeluaran='"+tbUser.getValueAt(i,102).toString()+"',"+
                    "setup_pjlab='"+tbUser.getValueAt(i,103).toString()+"',"+
                    "setup_otolokasi='"+tbUser.getValueAt(i,104).toString()+"',"+
                    "setup_jam_kamin='"+tbUser.getValueAt(i,105).toString()+"',"+
                    "setup_embalase='"+tbUser.getValueAt(i,106).toString()+"',"+
                    "tracer_login='"+tbUser.getValueAt(i,107).toString()+"',"+
                    "display='"+tbUser.getValueAt(i,108).toString()+"',"+
                    "set_harga_obat='"+tbUser.getValueAt(i,109).toString()+"',"+
                    "set_penggunaan_tarif='"+tbUser.getValueAt(i,110).toString()+"',"+
                    "set_oto_ralan='"+tbUser.getValueAt(i,111).toString()+"',"+
                    "biaya_harian='"+tbUser.getValueAt(i,112).toString()+"',"+
                    "biaya_masuk_sekali='"+tbUser.getValueAt(i,113).toString()+"',"+
                    "set_no_rm='"+tbUser.getValueAt(i,114).toString()+"',"+
                    "billing_ralan='"+tbUser.getValueAt(i,115).toString()+"',"+
                    "billing_ranap='"+tbUser.getValueAt(i,116).toString()+"',"+
                    "jm_ranap_dokter='"+tbUser.getValueAt(i,117).toString()+"',"+
                    "igd='"+tbUser.getValueAt(i,118).toString()+"',"+
                    "barcoderalan='"+tbUser.getValueAt(i,119).toString()+"',"+
                    "barcoderanap='"+tbUser.getValueAt(i,120).toString()+"',"+
                    "set_harga_obat_ralan='"+tbUser.getValueAt(i,121).toString()+"',"+
                    "set_harga_obat_ranap='"+tbUser.getValueAt(i,122).toString()+"',"+
                    "penyakit_pd3i='"+tbUser.getValueAt(i,123).toString()+"',"+
                    "surveilans_pd3i='"+tbUser.getValueAt(i,124).toString()+"',"+
                    "surveilans_ralan='"+tbUser.getValueAt(i,125).toString()+"',"+
                    "diagnosa_pasien='"+tbUser.getValueAt(i,126).toString()+"',"+
                    "surveilans_ranap='"+tbUser.getValueAt(i,127).toString()+"',"+
                    "pny_takmenular_ranap='"+tbUser.getValueAt(i,128).toString()+"',"+
                    "pny_takmenular_ralan='"+tbUser.getValueAt(i,129).toString()+"',"+
                    "kunjungan_ralan='"+tbUser.getValueAt(i,130).toString()+"',"+
                    "rl32='"+tbUser.getValueAt(i,131).toString()+"',"+
                    "rl33='"+tbUser.getValueAt(i,132).toString()+"',"+
                    "rl37='"+tbUser.getValueAt(i,133).toString()+"',"+
                    "rl38='"+tbUser.getValueAt(i,134).toString()+"',"+
                    "harian_tindakan_dokter='"+tbUser.getValueAt(i,135).toString()+"',"+
                    "sms='"+tbUser.getValueAt(i,136).toString()+"',"+
                    "sidikjari='"+tbUser.getValueAt(i,137).toString()+"',"+
                    "jam_masuk='"+tbUser.getValueAt(i,138).toString()+"',"+
                    "jadwal_pegawai='"+tbUser.getValueAt(i,139).toString()+"',"+
                    "parkir_barcode='"+tbUser.getValueAt(i,140).toString()+"',"+
                    "set_nota='"+tbUser.getValueAt(i,141).toString()+"',"+
                    "dpjp_ranap='"+tbUser.getValueAt(i,142).toString()+"',"+
                    "mutasi_barang='"+tbUser.getValueAt(i,143).toString()+"',"+
                    "rl34='"+tbUser.getValueAt(i,144).toString()+"',"+
                    "rl36='"+tbUser.getValueAt(i,145).toString()+"',"+
                    "fee_visit_dokter='"+tbUser.getValueAt(i,146).toString()+"',"+
                    "fee_bacaan_ekg='"+tbUser.getValueAt(i,147).toString()+"',"+
                    "fee_rujukan_rontgen='"+tbUser.getValueAt(i,148).toString()+"',"+
                    "fee_rujukan_ranap='"+tbUser.getValueAt(i,149).toString()+"',"+
                    "fee_ralan='"+tbUser.getValueAt(i,150).toString()+"',"+
                    "akun_bayar='"+tbUser.getValueAt(i,151).toString()+"',"+
                    "bayar_pemesanan_obat='"+tbUser.getValueAt(i,152).toString()+"',"+
                    "obat_per_dokter_peresep='"+tbUser.getValueAt(i,153).toString()+"',"+
                    "ipsrs_jenis_barang='"+tbUser.getValueAt(i,154).toString()+"',"+
                    "pemasukan_lain='"+tbUser.getValueAt(i,155).toString()+"',"+
                    "pengaturan_rekening='"+tbUser.getValueAt(i,156).toString()+"',"+
                    "closing_kasir='"+tbUser.getValueAt(i,157).toString()+"',"+
                    "keterlambatan_presensi='"+tbUser.getValueAt(i,158).toString()+"',"+
                    "set_harga_kamar='"+tbUser.getValueAt(i,159).toString()+"',"+
                    "rekap_per_shift='"+tbUser.getValueAt(i,160).toString()+"',"+
                    "bpjs_cek_nik='"+tbUser.getValueAt(i,161).toString()+"',"+
                    "bpjs_cek_kartu='"+tbUser.getValueAt(i,162).toString()+"',"+
                    "bpjs_cek_riwayat='"+tbUser.getValueAt(i,163).toString()+"',"+
                    "obat_per_cara_bayar='"+tbUser.getValueAt(i,164).toString()+"',"+
                    "kunjungan_ranap='"+tbUser.getValueAt(i,165).toString()+"',"+
                    "bayar_piutang='"+tbUser.getValueAt(i,166).toString()+"',"+
                    "payment_point='"+tbUser.getValueAt(i,167).toString()+"',"+
                    "bpjs_cek_nomor_rujukan='"+tbUser.getValueAt(i,168).toString()+"',"+
                    "icd9='"+tbUser.getValueAt(i,169).toString()+"',"+
                    "darurat_stok='"+tbUser.getValueAt(i,170).toString()+"',"+
                    "retensi_rm='"+tbUser.getValueAt(i,171).toString()+"',"+
                    "temporary_presensi='"+tbUser.getValueAt(i,172).toString()+"',"+
                    "jurnal_harian='"+tbUser.getValueAt(i,173).toString()+"',"+
                    "sirkulasi_obat2='"+tbUser.getValueAt(i,174).toString()+"',"+
                    "edit_registrasi='"+tbUser.getValueAt(i,175).toString()+"',"+
                    "bpjs_referensi_diagnosa='"+tbUser.getValueAt(i,176).toString()+"',"+
                    "bpjs_referensi_poli='"+tbUser.getValueAt(i,177).toString()+"',"+
                    "industrifarmasi='"+tbUser.getValueAt(i,178).toString()+"',"+
                    "harian_js='"+tbUser.getValueAt(i,179).toString()+"',"+
                    "bulanan_js='"+tbUser.getValueAt(i,180).toString()+"',"+
                    "harian_paket_bhp='"+tbUser.getValueAt(i,181).toString()+"',"+
                    "bulanan_paket_bhp='"+tbUser.getValueAt(i,182).toString()+"',"+
                    "piutang_pasien2='"+tbUser.getValueAt(i,183).toString()+"',"+
                    "bpjs_referensi_faskes='"+tbUser.getValueAt(i,184).toString()+"',"+
                    "bpjs_sep='"+tbUser.getValueAt(i,185).toString()+"',"+
                    "pengambilan_utd='"+tbUser.getValueAt(i,186).toString()+"',"+
                    "tarif_utd='"+tbUser.getValueAt(i,187).toString()+"',"+
                    "pengambilan_utd2='"+tbUser.getValueAt(i,188).toString()+"',"+
                    "utd_medis_rusak='"+tbUser.getValueAt(i,189).toString()+"',"+
                    "pengambilan_penunjang_utd='"+tbUser.getValueAt(i,190).toString()+"',"+
                    "pengambilan_penunjang_utd2='"+tbUser.getValueAt(i,191).toString()+"',"+
                    "utd_penunjang_rusak='"+tbUser.getValueAt(i,192).toString()+"',"+
                    "suplier_penunjang='"+tbUser.getValueAt(i,193).toString()+"',"+
                    "utd_donor='"+tbUser.getValueAt(i,194).toString()+"',"+
                    "bpjs_monitoring_klaim='"+tbUser.getValueAt(i,195).toString()+"',"+
                    "utd_cekal_darah='"+tbUser.getValueAt(i,196).toString()+"',"+
                    "utd_komponen_darah='"+tbUser.getValueAt(i,197).toString()+"',"+
                    "utd_stok_darah='"+tbUser.getValueAt(i,198).toString()+"',"+
                    "utd_pemisahan_darah='"+tbUser.getValueAt(i,199).toString()+"',"+
                    "harian_kamar='"+tbUser.getValueAt(i,200).toString()+"',"+
                    "rincian_piutang_pasien='"+tbUser.getValueAt(i,201).toString()+"',"+
                    "keuntungan_beri_obat_nonpiutang='"+tbUser.getValueAt(i,202).toString()+"',"+
                    "reklasifikasi_ralan='"+tbUser.getValueAt(i,203).toString()+"',"+
                    "reklasifikasi_ranap='"+tbUser.getValueAt(i,204).toString()+"',"+
                    "utd_penyerahan_darah='"+tbUser.getValueAt(i,205).toString()+"',"+
                    "hutang_obat='"+tbUser.getValueAt(i,206).toString()+"',"+
                    "riwayat_obat_alkes_bhp='"+tbUser.getValueAt(i,207).toString()+"',"+
                    "sensus_harian_poli='"+tbUser.getValueAt(i,208).toString()+"',"+
                    "rl4a='"+tbUser.getValueAt(i,209).toString()+"',"+
                    "aplicare_referensi_kamar='"+tbUser.getValueAt(i,210).toString()+"',"+
                    "aplicare_ketersediaan_kamar='"+tbUser.getValueAt(i,211).toString()+"',"+
                    "inacbg_klaim_baru_otomatis='"+tbUser.getValueAt(i,212).toString()+"',"+
                    "inacbg_klaim_baru_manual='"+tbUser.getValueAt(i,213).toString()+"',"+
                    "inacbg_coder_nik='"+tbUser.getValueAt(i,214).toString()+"',"+
                    "mutasi_berkas='"+tbUser.getValueAt(i,215).toString()+"',"+
                    "akun_piutang='"+tbUser.getValueAt(i,216).toString()+"',"+
                    "harian_kso='"+tbUser.getValueAt(i,217).toString()+"',"+
                    "bulanan_kso='"+tbUser.getValueAt(i,218).toString()+"',"+
                    "harian_menejemen='"+tbUser.getValueAt(i,219).toString()+"',"+
                    "bulanan_menejemen='"+tbUser.getValueAt(i,220).toString()+"',"+
                    "inhealth_cek_eligibilitas='"+tbUser.getValueAt(i,221).toString()+"',"+
                    "inhealth_referensi_jenpel_ruang_rawat='"+tbUser.getValueAt(i,222).toString()+"',"+
                    "inhealth_referensi_poli='"+tbUser.getValueAt(i,223).toString()+"',"+
                    "inhealth_referensi_faskes='"+tbUser.getValueAt(i,224).toString()+"',"+
                    "inhealth_sjp='"+tbUser.getValueAt(i,225).toString()+"',"+
                    "piutang_ralan='"+tbUser.getValueAt(i,226).toString()+"',"+
                    "piutang_ranap='"+tbUser.getValueAt(i,227).toString()+"',"+
                    "detail_piutang_penjab='"+tbUser.getValueAt(i,228).toString()+"',"+
                    "lama_pelayanan_ralan='"+tbUser.getValueAt(i,229).toString()+"',"+
                    "catatan_pasien='"+tbUser.getValueAt(i,230).toString()+"',"+
                    "rl4b='"+tbUser.getValueAt(i,231).toString()+"',"+
                    "rl4asebab='"+tbUser.getValueAt(i,232).toString()+"',"+
                    "rl4bsebab='"+tbUser.getValueAt(i,233).toString()+"',"+
                    "data_HAIs='"+tbUser.getValueAt(i,234).toString()+"',"+
                    "harian_HAIs='"+tbUser.getValueAt(i,235).toString()+"',"+
                    "bulanan_HAIs='"+tbUser.getValueAt(i,236).toString()+"',"+
                    "hitung_bor='"+tbUser.getValueAt(i,237).toString()+"'");
            }            
            tampil();
            emptTeks();
        }

}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            dispose();
        }else{Valid.pindah(evt,BtnEdit,BtnKeluar);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void TKdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
        Valid.pindah(evt,BtnSimpan,TPass);
}//GEN-LAST:event_TKdKeyPressed

    private void TPassKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPassKeyPressed
        Valid.pindah(evt,TKd,BtnSimpan);
}//GEN-LAST:event_TPassKeyPressed

    private void BtnSeekActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeekActionPerformed
        dlgdokter.emptTeks();
        dlgdokter.isCek();
        //dokter.setModal(true);
        dlgdokter.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
        dlgdokter.setLocationRelativeTo(internalFrame1);
        dlgdokter.setVisible(true);
}//GEN-LAST:event_BtnSeekActionPerformed

    private void BtnSeekKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeekKeyPressed
        Valid.pindah(evt,TKd,TPass);
}//GEN-LAST:event_BtnSeekKeyPressed

    private void BtnSeek1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek1ActionPerformed
        dlgpetugas.emptTeks();
        dlgpetugas.isCek();
        //petugas.setModal(true);
        dlgpetugas.setSize(internalFrame1.getWidth()-50,internalFrame1.getHeight()-50);
        dlgpetugas.setLocationRelativeTo(internalFrame1);
        dlgpetugas.setVisible(true);
}//GEN-LAST:event_BtnSeek1ActionPerformed

    private void BtnSeek1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek1KeyPressed
        Valid.pindah(evt,TKd,TPass);
}//GEN-LAST:event_BtnSeek1KeyPressed

    private void TNmUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNmUserKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TNmUserKeyPressed

    private void tbUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbUserMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbUserMouseClicked

    private void tbUserKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbUserKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbUserKeyPressed

private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TKd.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
            }
            Valid.MyReport("rptUser.jrxml","report","::[ Data User ]::",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc");
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus,BtnKeluar);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbUser.requestFocus();
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

    private void BtnCariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnCariKeyReleased

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TKd);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
    }//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
    }//GEN-LAST:event_BtnBatalKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgUser dialog = new DlgUser(new javax.swing.JFrame(), true);
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
    private widget.Button BtnSeek;
    private widget.Button BtnSeek1;
    private widget.Button BtnSimpan;
    private widget.Label LCount;
    private widget.ScrollPane Scroll;
    private widget.TextBox TCari;
    private widget.TextBox TKd;
    private widget.TextBox TNmUser;
    private widget.TextBox TPass;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel3;
    private widget.Label jLabel4;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private javax.swing.JPanel jPanel1;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.Table tbUser;
    // End of variables declaration//GEN-END:variables

    private void tampil() {        
        try{    
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement("select AES_DECRYPT(id_user,'nur'),AES_DECRYPT(password,'windi'),"+
                        "penyakit, obat_penyakit, dokter, jadwal_praktek, petugas, pasien, registrasi, tindakan_ralan,"+
                        "kamar_inap, tindakan_ranap, operasi, rujukan_keluar, rujukan_masuk, beri_obat, resep_pulang, "+
                        "pasien_meninggal, diet_pasien, kelahiran_bayi, periksa_lab, periksa_radiologi, kasir_ralan, "+
                        "deposit_pasien, piutang_pasien, peminjaman_berkas, barcode, presensi_harian, presensi_bulanan, "+
                        "pegawai_admin, pegawai_user, suplier, satuan_barang, konversi_satuan, jenis_barang, obat, "+
                        "stok_opname_obat, stok_obat_pasien, pengadaan_obat, pemesanan_obat, penjualan_obat, piutang_obat, "+
                        "retur_ke_suplier, retur_dari_pembeli, retur_obat_ranap, retur_piutang_pasien, keuntungan_penjualan, "+
                        "keuntungan_beri_obat, sirkulasi_obat, ipsrs_barang, ipsrs_pengadaan_barang, ipsrs_stok_keluar, "+
                        "ipsrs_rekap_pengadaan, ipsrs_rekap_stok_keluar, ipsrs_pengeluaran_harian, inventaris_jenis, "+
                        "inventaris_kategori, inventaris_merk, inventaris_ruang, inventaris_produsen, inventaris_koleksi,"+
                        "inventaris_inventaris, inventaris_sirkulasi, parkir_jenis, parkir_in, parkir_out, parkir_rekap_harian, "+
                        "parkir_rekap_bulanan, informasi_kamar, harian_tindakan_poli, obat_per_poli, obat_per_kamar, "+
                        "obat_per_dokter_ralan, obat_per_dokter_ranap, harian_dokter, bulanan_dokter, harian_paramedis,"+
                        "bulanan_paramedis, pembayaran_ralan, pembayaran_ranap, rekap_pembayaran_ralan, rekap_pembayaran_ranap,"+
                        "tagihan_masuk, tambahan_biaya, potongan_biaya, resep_obat, resume_pasien, penyakit_ralan, penyakit_ranap, "+
                        "kamar, tarif_ralan, tarif_ranap, tarif_lab, tarif_radiologi, tarif_operasi, akun_rekening, rekening_tahun, "+
                        "posting_jurnal, buku_besar, cashflow, keuangan, pengeluaran, setup_pjlab, setup_otolokasi, setup_jam_kamin, "+
                        "setup_embalase, tracer_login, display, set_harga_obat, set_penggunaan_tarif, set_oto_ralan, biaya_harian, "+
                        "biaya_masuk_sekali, set_no_rm, billing_ralan, billing_ranap, jm_ranap_dokter, igd, barcoderalan, barcoderanap, "+
                        "set_harga_obat_ralan,set_harga_obat_ranap,penyakit_pd3i,surveilans_pd3i,surveilans_ralan,diagnosa_pasien, "+
                        "surveilans_ranap,pny_takmenular_ranap,pny_takmenular_ralan,kunjungan_ralan,rl32,rl33,rl37,rl38,harian_tindakan_dokter,sms, "+
                        "sidikjari,jam_masuk,jadwal_pegawai,parkir_barcode,set_nota,dpjp_ranap,mutasi_barang,rl34,rl36,"+
                        "fee_visit_dokter,fee_bacaan_ekg,fee_rujukan_rontgen,fee_rujukan_ranap,fee_ralan,akun_bayar,bayar_pemesanan_obat,"+
                        "obat_per_dokter_peresep,ipsrs_jenis_barang,pemasukan_lain,pengaturan_rekening,closing_kasir,keterlambatan_presensi,"+
                        "set_harga_kamar,rekap_per_shift,bpjs_cek_nik,bpjs_cek_kartu,bpjs_cek_riwayat,obat_per_cara_bayar,kunjungan_ranap,"+
                        "bayar_piutang,payment_point,bpjs_cek_nomor_rujukan,icd9,darurat_stok,retensi_rm,temporary_presensi,jurnal_harian, "+
                        "sirkulasi_obat2,edit_registrasi,bpjs_referensi_diagnosa,bpjs_referensi_poli,industrifarmasi,harian_js,bulanan_js,"+
                        "harian_paket_bhp,bulanan_paket_bhp,piutang_pasien2,bpjs_referensi_faskes,bpjs_sep,pengambilan_utd,tarif_utd, "+
                        "pengambilan_utd2,utd_medis_rusak,pengambilan_penunjang_utd,pengambilan_penunjang_utd2,utd_penunjang_rusak,"+
                        "suplier_penunjang,utd_donor,bpjs_monitoring_klaim,utd_cekal_darah,utd_komponen_darah,utd_stok_darah, "+
                        "utd_pemisahan_darah,harian_kamar,rincian_piutang_pasien,keuntungan_beri_obat_nonpiutang,reklasifikasi_ralan, "+
                        "reklasifikasi_ranap,utd_penyerahan_darah,hutang_obat,riwayat_obat_alkes_bhp,sensus_harian_poli,rl4a,aplicare_referensi_kamar, "+
                        "aplicare_ketersediaan_kamar,inacbg_klaim_baru_otomatis,inacbg_klaim_baru_manual,inacbg_coder_nik,mutasi_berkas, "+
                        "akun_piutang,harian_kso,bulanan_kso,harian_menejemen,bulanan_menejemen,inhealth_cek_eligibilitas,inhealth_referensi_jenpel_ruang_rawat, "+
                        "inhealth_referensi_poli,inhealth_referensi_faskes,inhealth_sjp,piutang_ralan,piutang_ranap,detail_piutang_penjab, "+
                        "lama_pelayanan_ralan,catatan_pasien,rl4b,rl4asebab,rl4bsebab,data_HAIs,harian_HAIs,bulanan_HAIs,hitung_bor from user order by AES_DECRYPT(id_user,'nur')");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    user="";
                    user=Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString(1));
                    if(user.equals("")){
                        user=Sequel.cariIsi("select nama from petugas where nip=?",rs.getString(1));
                    }    
                    try {
                        if(rs.getString(1).toLowerCase().contains(TCari.getText().toLowerCase())||
                                user.toLowerCase().contains(TCari.getText().toLowerCase())){
                            tabMode.addRow(new Object[]{rs.getString(1),
                                           user,rs.getString(2),
                                           rs.getBoolean("penyakit"),
                                           rs.getBoolean("obat_penyakit"),
                                           rs.getBoolean("dokter"),
                                           rs.getBoolean("jadwal_praktek"),
                                           rs.getBoolean("petugas"),
                                           rs.getBoolean("pasien"),
                                           rs.getBoolean("registrasi"),
                                           rs.getBoolean("tindakan_ralan"),
                                           rs.getBoolean("kamar_inap"),
                                           rs.getBoolean("tindakan_ranap"),
                                           rs.getBoolean("operasi"),
                                           rs.getBoolean("rujukan_keluar"),
                                           rs.getBoolean("rujukan_masuk"),
                                           rs.getBoolean("beri_obat"),
                                           rs.getBoolean("resep_pulang"),
                                           rs.getBoolean("pasien_meninggal"),
                                           rs.getBoolean("diet_pasien"),
                                           rs.getBoolean("kelahiran_bayi"),
                                           rs.getBoolean("periksa_lab"),
                                           rs.getBoolean("periksa_radiologi"),
                                           rs.getBoolean("kasir_ralan"),
                                           rs.getBoolean("deposit_pasien"),
                                           rs.getBoolean("piutang_pasien"),
                                           rs.getBoolean("peminjaman_berkas"),
                                           rs.getBoolean("barcode"),
                                           rs.getBoolean("presensi_harian"),
                                           rs.getBoolean("presensi_bulanan"),
                                           rs.getBoolean("pegawai_admin"),
                                           rs.getBoolean("pegawai_user"),
                                           rs.getBoolean("suplier"),
                                           rs.getBoolean("satuan_barang"),
                                           rs.getBoolean("konversi_satuan"),
                                           rs.getBoolean("jenis_barang"),
                                           rs.getBoolean("obat"),
                                           rs.getBoolean("stok_opname_obat"),
                                           rs.getBoolean("stok_obat_pasien"),
                                           rs.getBoolean("pengadaan_obat"),
                                           rs.getBoolean("pemesanan_obat"),
                                           rs.getBoolean("penjualan_obat"),
                                           rs.getBoolean("piutang_obat"),
                                           rs.getBoolean("retur_ke_suplier"),
                                           rs.getBoolean("retur_dari_pembeli"),
                                           rs.getBoolean("retur_obat_ranap"),
                                           rs.getBoolean("retur_piutang_pasien"),
                                           rs.getBoolean("keuntungan_penjualan"),
                                           rs.getBoolean("keuntungan_beri_obat"),
                                           rs.getBoolean("sirkulasi_obat"),
                                           rs.getBoolean("ipsrs_barang"),
                                           rs.getBoolean("ipsrs_pengadaan_barang"),
                                           rs.getBoolean("ipsrs_stok_keluar"),
                                           rs.getBoolean("ipsrs_rekap_pengadaan"),
                                           rs.getBoolean("ipsrs_rekap_stok_keluar"),
                                           rs.getBoolean("ipsrs_pengeluaran_harian"),
                                           rs.getBoolean("inventaris_jenis"),
                                           rs.getBoolean("inventaris_kategori"),
                                           rs.getBoolean("inventaris_merk"),
                                           rs.getBoolean("inventaris_ruang"),
                                           rs.getBoolean("inventaris_produsen"),
                                           rs.getBoolean("inventaris_koleksi"),
                                           rs.getBoolean("inventaris_inventaris"),
                                           rs.getBoolean("inventaris_sirkulasi"),
                                           rs.getBoolean("parkir_jenis"),
                                           rs.getBoolean("parkir_in"),
                                           rs.getBoolean("parkir_out"),
                                           rs.getBoolean("parkir_rekap_harian"),
                                           rs.getBoolean("parkir_rekap_bulanan"),
                                           rs.getBoolean("informasi_kamar"),
                                           rs.getBoolean("harian_tindakan_poli"),
                                           rs.getBoolean("obat_per_poli"),
                                           rs.getBoolean("obat_per_kamar"),
                                           rs.getBoolean("obat_per_dokter_ralan"),
                                           rs.getBoolean("obat_per_dokter_ranap"),
                                           rs.getBoolean("harian_dokter"),
                                           rs.getBoolean("bulanan_dokter"),
                                           rs.getBoolean("harian_paramedis"),
                                           rs.getBoolean("bulanan_paramedis"),
                                           rs.getBoolean("pembayaran_ralan"),
                                           rs.getBoolean("pembayaran_ranap"),
                                           rs.getBoolean("rekap_pembayaran_ralan"),
                                           rs.getBoolean("rekap_pembayaran_ranap"),
                                           rs.getBoolean("tagihan_masuk"),
                                           rs.getBoolean("tambahan_biaya"),
                                           rs.getBoolean("potongan_biaya"),
                                           rs.getBoolean("resep_obat"),
                                           rs.getBoolean("resume_pasien"),
                                           rs.getBoolean("penyakit_ralan"),
                                           rs.getBoolean("penyakit_ranap"),
                                           rs.getBoolean("kamar"),
                                           rs.getBoolean("tarif_ralan"),
                                           rs.getBoolean("tarif_ranap"),
                                           rs.getBoolean("tarif_lab"),
                                           rs.getBoolean("tarif_radiologi"),
                                           rs.getBoolean("tarif_operasi"),
                                           rs.getBoolean("akun_rekening"),
                                           rs.getBoolean("rekening_tahun"),
                                           rs.getBoolean("posting_jurnal"),
                                           rs.getBoolean("buku_besar"),
                                           rs.getBoolean("cashflow"),
                                           rs.getBoolean("keuangan"),
                                           rs.getBoolean("pengeluaran"),
                                           rs.getBoolean("setup_pjlab"),
                                           rs.getBoolean("setup_otolokasi"),
                                           rs.getBoolean("setup_jam_kamin"),
                                           rs.getBoolean("setup_embalase"),
                                           rs.getBoolean("tracer_login"),
                                           rs.getBoolean("display"),
                                           rs.getBoolean("set_harga_obat"),
                                           rs.getBoolean("set_penggunaan_tarif"),
                                           rs.getBoolean("set_oto_ralan"),
                                           rs.getBoolean("biaya_harian"),
                                           rs.getBoolean("biaya_masuk_sekali"),
                                           rs.getBoolean("set_no_rm"),
                                           rs.getBoolean("billing_ralan"),
                                           rs.getBoolean("billing_ranap"),
                                           rs.getBoolean("jm_ranap_dokter"),
                                           rs.getBoolean("igd"),
                                           rs.getBoolean("barcoderalan"),
                                           rs.getBoolean("barcoderanap"),
                                           rs.getBoolean("set_harga_obat_ralan"),
                                           rs.getBoolean("set_harga_obat_ranap"),
                                           rs.getBoolean("penyakit_pd3i"),
                                           rs.getBoolean("surveilans_pd3i"),
                                           rs.getBoolean("surveilans_ralan"),
                                           rs.getBoolean("diagnosa_pasien"),
                                           rs.getBoolean("surveilans_ranap"),
                                           rs.getBoolean("pny_takmenular_ranap"),
                                           rs.getBoolean("pny_takmenular_ralan"),
                                           rs.getBoolean("kunjungan_ralan"),
                                           rs.getBoolean("rl32"),
                                           rs.getBoolean("rl33"),
                                           rs.getBoolean("rl37"),
                                           rs.getBoolean("rl38"),
                                           rs.getBoolean("harian_tindakan_dokter"),
                                           rs.getBoolean("sms"),
                                           rs.getBoolean("sidikjari"),
                                           rs.getBoolean("jam_masuk"),
                                           rs.getBoolean("jadwal_pegawai"),
                                           rs.getBoolean("parkir_barcode"),
                                           rs.getBoolean("set_nota"),
                                           rs.getBoolean("dpjp_ranap"),
                                           rs.getBoolean("mutasi_barang"),
                                           rs.getBoolean("rl34"),
                                           rs.getBoolean("rl36"),
                                           rs.getBoolean("fee_visit_dokter"),
                                           rs.getBoolean("fee_bacaan_ekg"),
                                           rs.getBoolean("fee_rujukan_rontgen"),
                                           rs.getBoolean("fee_rujukan_ranap"),
                                           rs.getBoolean("fee_ralan"),
                                           rs.getBoolean("akun_bayar"),
                                           rs.getBoolean("bayar_pemesanan_obat"),
                                           rs.getBoolean("obat_per_dokter_peresep"),
                                           rs.getBoolean("ipsrs_jenis_barang"),
                                           rs.getBoolean("pemasukan_lain"),
                                           rs.getBoolean("pengaturan_rekening"),
                                           rs.getBoolean("closing_kasir"),
                                           rs.getBoolean("keterlambatan_presensi"),
                                           rs.getBoolean("set_harga_kamar"),
                                           rs.getBoolean("rekap_per_shift"),
                                           rs.getBoolean("bpjs_cek_nik"),
                                           rs.getBoolean("bpjs_cek_kartu"),
                                           rs.getBoolean("bpjs_cek_riwayat"),
                                           rs.getBoolean("obat_per_cara_bayar"),
                                           rs.getBoolean("kunjungan_ranap"),
                                           rs.getBoolean("bayar_piutang"),
                                           rs.getBoolean("payment_point"),
                                           rs.getBoolean("bpjs_cek_nomor_rujukan"),
                                           rs.getBoolean("icd9"),
                                           rs.getBoolean("darurat_stok"),
                                           rs.getBoolean("retensi_rm"),
                                           rs.getBoolean("temporary_presensi"),
                                           rs.getBoolean("jurnal_harian"),
                                           rs.getBoolean("sirkulasi_obat2"),
                                           rs.getBoolean("edit_registrasi"),
                                           rs.getBoolean("bpjs_referensi_diagnosa"),
                                           rs.getBoolean("bpjs_referensi_poli"),
                                           rs.getBoolean("industrifarmasi"),
                                           rs.getBoolean("harian_js"),
                                           rs.getBoolean("bulanan_js"),
                                           rs.getBoolean("harian_paket_bhp"),
                                           rs.getBoolean("bulanan_paket_bhp"),
                                           rs.getBoolean("piutang_pasien2"),
                                           rs.getBoolean("bpjs_referensi_faskes"),
                                           rs.getBoolean("bpjs_sep"),
                                           rs.getBoolean("pengambilan_utd"),
                                           rs.getBoolean("tarif_utd"),
                                           rs.getBoolean("pengambilan_utd2"),
                                           rs.getBoolean("utd_medis_rusak"),
                                           rs.getBoolean("pengambilan_penunjang_utd"),
                                           rs.getBoolean("pengambilan_penunjang_utd2"),
                                           rs.getBoolean("utd_penunjang_rusak"),
                                           rs.getBoolean("suplier_penunjang"),
                                           rs.getBoolean("utd_donor"),
                                           rs.getBoolean("bpjs_monitoring_klaim"),
                                           rs.getBoolean("utd_cekal_darah"),
                                           rs.getBoolean("utd_komponen_darah"),
                                           rs.getBoolean("utd_stok_darah"),
                                           rs.getBoolean("utd_pemisahan_darah"),
                                           rs.getBoolean("harian_kamar"),
                                           rs.getBoolean("rincian_piutang_pasien"),
                                           rs.getBoolean("keuntungan_beri_obat_nonpiutang"),
                                           rs.getBoolean("reklasifikasi_ralan"),
                                           rs.getBoolean("reklasifikasi_ranap"),
                                           rs.getBoolean("utd_penyerahan_darah"),
                                           rs.getBoolean("hutang_obat"),
                                           rs.getBoolean("riwayat_obat_alkes_bhp"),
                                           rs.getBoolean("sensus_harian_poli"),
                                           rs.getBoolean("rl4a"),
                                           rs.getBoolean("aplicare_referensi_kamar"),
                                           rs.getBoolean("aplicare_ketersediaan_kamar"),
                                           rs.getBoolean("inacbg_klaim_baru_otomatis"),
                                           rs.getBoolean("inacbg_klaim_baru_manual"),
                                           rs.getBoolean("inacbg_coder_nik"),
                                           rs.getBoolean("mutasi_berkas"),
                                           rs.getBoolean("akun_piutang"),
                                           rs.getBoolean("harian_kso"),
                                           rs.getBoolean("bulanan_kso"),
                                           rs.getBoolean("harian_menejemen"),
                                           rs.getBoolean("bulanan_menejemen"),
                                           rs.getBoolean("inhealth_cek_eligibilitas"),
                                           rs.getBoolean("inhealth_referensi_jenpel_ruang_rawat"),
                                           rs.getBoolean("inhealth_referensi_poli"),
                                           rs.getBoolean("inhealth_referensi_faskes"),
                                           rs.getBoolean("inhealth_sjp"),
                                           rs.getBoolean("piutang_ralan"),
                                           rs.getBoolean("piutang_ranap"),
                                           rs.getBoolean("detail_piutang_penjab"),
                                           rs.getBoolean("lama_pelayanan_ralan"),
                                           rs.getBoolean("catatan_pasien"),
                                           rs.getBoolean("rl4b"),
                                           rs.getBoolean("rl4asebab"),
                                           rs.getBoolean("rl4bsebab"),
                                           rs.getBoolean("data_HAIs"),
                                           rs.getBoolean("harian_HAIs"),
                                           rs.getBoolean("bulanan_HAIs"),
                                           rs.getBoolean("hitung_bor")
                            });
                        }   
                    } catch (Exception e) {
                        tabMode.addRow(new Object[]{rs.getString(1),
                                           "Turn Out",rs.getString(2),
                                           rs.getBoolean("penyakit"),
                                           rs.getBoolean("obat_penyakit"),
                                           rs.getBoolean("dokter"),
                                           rs.getBoolean("jadwal_praktek"),
                                           rs.getBoolean("petugas"),
                                           rs.getBoolean("pasien"),
                                           rs.getBoolean("registrasi"),
                                           rs.getBoolean("tindakan_ralan"),
                                           rs.getBoolean("kamar_inap"),
                                           rs.getBoolean("tindakan_ranap"),
                                           rs.getBoolean("operasi"),
                                           rs.getBoolean("rujukan_keluar"),
                                           rs.getBoolean("rujukan_masuk"),
                                           rs.getBoolean("beri_obat"),
                                           rs.getBoolean("resep_pulang"),
                                           rs.getBoolean("pasien_meninggal"),
                                           rs.getBoolean("diet_pasien"),
                                           rs.getBoolean("kelahiran_bayi"),
                                           rs.getBoolean("periksa_lab"),
                                           rs.getBoolean("periksa_radiologi"),
                                           rs.getBoolean("kasir_ralan"),
                                           rs.getBoolean("deposit_pasien"),
                                           rs.getBoolean("piutang_pasien"),
                                           rs.getBoolean("peminjaman_berkas"),
                                           rs.getBoolean("barcode"),
                                           rs.getBoolean("presensi_harian"),
                                           rs.getBoolean("presensi_bulanan"),
                                           rs.getBoolean("pegawai_admin"),
                                           rs.getBoolean("pegawai_user"),
                                           rs.getBoolean("suplier"),
                                           rs.getBoolean("satuan_barang"),
                                           rs.getBoolean("konversi_satuan"),
                                           rs.getBoolean("jenis_barang"),
                                           rs.getBoolean("obat"),
                                           rs.getBoolean("stok_opname_obat"),
                                           rs.getBoolean("stok_obat_pasien"),
                                           rs.getBoolean("pengadaan_obat"),
                                           rs.getBoolean("pemesanan_obat"),
                                           rs.getBoolean("penjualan_obat"),
                                           rs.getBoolean("piutang_obat"),
                                           rs.getBoolean("retur_ke_suplier"),
                                           rs.getBoolean("retur_dari_pembeli"),
                                           rs.getBoolean("retur_obat_ranap"),
                                           rs.getBoolean("retur_piutang_pasien"),
                                           rs.getBoolean("keuntungan_penjualan"),
                                           rs.getBoolean("keuntungan_beri_obat"),
                                           rs.getBoolean("sirkulasi_obat"),
                                           rs.getBoolean("ipsrs_barang"),
                                           rs.getBoolean("ipsrs_pengadaan_barang"),
                                           rs.getBoolean("ipsrs_stok_keluar"),
                                           rs.getBoolean("ipsrs_rekap_pengadaan"),
                                           rs.getBoolean("ipsrs_rekap_stok_keluar"),
                                           rs.getBoolean("ipsrs_pengeluaran_harian"),
                                           rs.getBoolean("inventaris_jenis"),
                                           rs.getBoolean("inventaris_kategori"),
                                           rs.getBoolean("inventaris_merk"),
                                           rs.getBoolean("inventaris_ruang"),
                                           rs.getBoolean("inventaris_produsen"),
                                           rs.getBoolean("inventaris_koleksi"),
                                           rs.getBoolean("inventaris_inventaris"),
                                           rs.getBoolean("inventaris_sirkulasi"),
                                           rs.getBoolean("parkir_jenis"),
                                           rs.getBoolean("parkir_in"),
                                           rs.getBoolean("parkir_out"),
                                           rs.getBoolean("parkir_rekap_harian"),
                                           rs.getBoolean("parkir_rekap_bulanan"),
                                           rs.getBoolean("informasi_kamar"),
                                           rs.getBoolean("harian_tindakan_poli"),
                                           rs.getBoolean("obat_per_poli"),
                                           rs.getBoolean("obat_per_kamar"),
                                           rs.getBoolean("obat_per_dokter_ralan"),
                                           rs.getBoolean("obat_per_dokter_ranap"),
                                           rs.getBoolean("harian_dokter"),
                                           rs.getBoolean("bulanan_dokter"),
                                           rs.getBoolean("harian_paramedis"),
                                           rs.getBoolean("bulanan_paramedis"),
                                           rs.getBoolean("pembayaran_ralan"),
                                           rs.getBoolean("pembayaran_ranap"),
                                           rs.getBoolean("rekap_pembayaran_ralan"),
                                           rs.getBoolean("rekap_pembayaran_ranap"),
                                           rs.getBoolean("tagihan_masuk"),
                                           rs.getBoolean("tambahan_biaya"),
                                           rs.getBoolean("potongan_biaya"),
                                           rs.getBoolean("resep_obat"),
                                           rs.getBoolean("resume_pasien"),
                                           rs.getBoolean("penyakit_ralan"),
                                           rs.getBoolean("penyakit_ranap"),
                                           rs.getBoolean("kamar"),
                                           rs.getBoolean("tarif_ralan"),
                                           rs.getBoolean("tarif_ranap"),
                                           rs.getBoolean("tarif_lab"),
                                           rs.getBoolean("tarif_radiologi"),
                                           rs.getBoolean("tarif_operasi"),
                                           rs.getBoolean("akun_rekening"),
                                           rs.getBoolean("rekening_tahun"),
                                           rs.getBoolean("posting_jurnal"),
                                           rs.getBoolean("buku_besar"),
                                           rs.getBoolean("cashflow"),
                                           rs.getBoolean("keuangan"),
                                           rs.getBoolean("pengeluaran"),
                                           rs.getBoolean("setup_pjlab"),
                                           rs.getBoolean("setup_otolokasi"),
                                           rs.getBoolean("setup_jam_kamin"),
                                           rs.getBoolean("setup_embalase"),
                                           rs.getBoolean("tracer_login"),
                                           rs.getBoolean("display"),
                                           rs.getBoolean("set_harga_obat"),
                                           rs.getBoolean("set_penggunaan_tarif"),
                                           rs.getBoolean("set_oto_ralan"),
                                           rs.getBoolean("biaya_harian"),
                                           rs.getBoolean("biaya_masuk_sekali"),
                                           rs.getBoolean("set_no_rm"),
                                           rs.getBoolean("billing_ralan"),
                                           rs.getBoolean("billing_ranap"),
                                           rs.getBoolean("jm_ranap_dokter"),
                                           rs.getBoolean("igd"),
                                           rs.getBoolean("barcoderalan"),
                                           rs.getBoolean("barcoderanap"),
                                           rs.getBoolean("set_harga_obat_ralan"),
                                           rs.getBoolean("set_harga_obat_ranap"),
                                           rs.getBoolean("penyakit_pd3i"),
                                           rs.getBoolean("surveilans_pd3i"),
                                           rs.getBoolean("surveilans_ralan"),
                                           rs.getBoolean("diagnosa_pasien"),
                                           rs.getBoolean("surveilans_ranap"),
                                           rs.getBoolean("pny_takmenular_ranap"),
                                           rs.getBoolean("pny_takmenular_ralan"),
                                           rs.getBoolean("kunjungan_ralan"),
                                           rs.getBoolean("rl32"),
                                           rs.getBoolean("rl33"),
                                           rs.getBoolean("rl37"),
                                           rs.getBoolean("rl38"),
                                           rs.getBoolean("harian_tindakan_dokter"),
                                           rs.getBoolean("sms"),
                                           rs.getBoolean("sidikjari"),
                                           rs.getBoolean("jam_masuk"),
                                           rs.getBoolean("jadwal_pegawai"),
                                           rs.getBoolean("parkir_barcode"),
                                           rs.getBoolean("set_nota"),
                                           rs.getBoolean("dpjp_ranap"),
                                           rs.getBoolean("mutasi_barang"),
                                           rs.getBoolean("rl34"),
                                           rs.getBoolean("rl36"),
                                           rs.getBoolean("fee_visit_dokter"),
                                           rs.getBoolean("fee_bacaan_ekg"),
                                           rs.getBoolean("fee_rujukan_rontgen"),
                                           rs.getBoolean("fee_rujukan_ranap"),
                                           rs.getBoolean("fee_ralan"),
                                           rs.getBoolean("akun_bayar"),
                                           rs.getBoolean("bayar_pemesanan_obat"),
                                           rs.getBoolean("obat_per_dokter_peresep"),
                                           rs.getBoolean("ipsrs_jenis_barang"),
                                           rs.getBoolean("pemasukan_lain"),
                                           rs.getBoolean("pengaturan_rekening"),
                                           rs.getBoolean("closing_kasir"),
                                           rs.getBoolean("keterlambatan_presensi"),
                                           rs.getBoolean("set_harga_kamar"),
                                           rs.getBoolean("rekap_per_shift"),
                                           rs.getBoolean("bpjs_cek_nik"),
                                           rs.getBoolean("bpjs_cek_kartu"),
                                           rs.getBoolean("bpjs_cek_riwayat"),
                                           rs.getBoolean("obat_per_cara_bayar"),
                                           rs.getBoolean("kunjungan_ranap"),
                                           rs.getBoolean("bayar_piutang"),
                                           rs.getBoolean("payment_point"),
                                           rs.getBoolean("bpjs_cek_nomor_rujukan"),
                                           rs.getBoolean("icd9"),
                                           rs.getBoolean("darurat_stok"),
                                           rs.getBoolean("retensi_rm"),
                                           rs.getBoolean("temporary_presensi"),
                                           rs.getBoolean("jurnal_harian"),
                                           rs.getBoolean("sirkulasi_obat2"),
                                           rs.getBoolean("edit_registrasi"),
                                           rs.getBoolean("bpjs_referensi_diagnosa"),
                                           rs.getBoolean("bpjs_referensi_poli"),
                                           rs.getBoolean("industrifarmasi"),
                                           rs.getBoolean("harian_js"),
                                           rs.getBoolean("bulanan_js"),
                                           rs.getBoolean("harian_paket_bhp"),
                                           rs.getBoolean("bulanan_paket_bhp"),
                                           rs.getBoolean("piutang_pasien2"),
                                           rs.getBoolean("bpjs_referensi_faskes"),
                                           rs.getBoolean("bpjs_sep"),
                                           rs.getBoolean("pengambilan_utd"),
                                           rs.getBoolean("tarif_utd"),
                                           rs.getBoolean("pengambilan_utd2"),
                                           rs.getBoolean("utd_medis_rusak"),
                                           rs.getBoolean("pengambilan_penunjang_utd"),
                                           rs.getBoolean("pengambilan_penunjang_utd2"),
                                           rs.getBoolean("utd_penunjang_rusak"),
                                           rs.getBoolean("suplier_penunjang"),
                                           rs.getBoolean("utd_donor"),
                                           rs.getBoolean("bpjs_monitoring_klaim"),
                                           rs.getBoolean("utd_cekal_darah"),
                                           rs.getBoolean("utd_komponen_darah"),
                                           rs.getBoolean("utd_stok_darah"),
                                           rs.getBoolean("utd_pemisahan_darah"),
                                           rs.getBoolean("harian_kamar"),
                                           rs.getBoolean("rincian_piutang_pasien"),
                                           rs.getBoolean("keuntungan_beri_obat_nonpiutang"),
                                           rs.getBoolean("reklasifikasi_ralan"),
                                           rs.getBoolean("reklasifikasi_ranap"),
                                           rs.getBoolean("utd_penyerahan_darah"),
                                           rs.getBoolean("hutang_obat"),
                                           rs.getBoolean("riwayat_obat_alkes_bhp"),
                                           rs.getBoolean("sensus_harian_poli"),
                                           rs.getBoolean("rl4a"),
                                           rs.getBoolean("aplicare_referensi_kamar"),
                                           rs.getBoolean("aplicare_ketersediaan_kamar"),
                                           rs.getBoolean("inacbg_klaim_baru_otomatis"),
                                           rs.getBoolean("inacbg_klaim_baru_manual"),
                                           rs.getBoolean("inacbg_coder_nik"),
                                           rs.getBoolean("mutasi_berkas"),
                                           rs.getBoolean("akun_piutang"),
                                           rs.getBoolean("harian_kso"),
                                           rs.getBoolean("bulanan_kso"),
                                           rs.getBoolean("harian_menejemen"),
                                           rs.getBoolean("bulanan_menejemen"),
                                           rs.getBoolean("inhealth_cek_eligibilitas"),
                                           rs.getBoolean("inhealth_referensi_jenpel_ruang_rawat"),
                                           rs.getBoolean("inhealth_referensi_poli"),
                                           rs.getBoolean("inhealth_referensi_faskes"),
                                           rs.getBoolean("inhealth_sjp"),
                                           rs.getBoolean("piutang_ralan"),
                                           rs.getBoolean("piutang_ranap"),
                                           rs.getBoolean("detail_piutang_penjab"),
                                           rs.getBoolean("lama_pelayanan_ralan"),
                                           rs.getBoolean("catatan_pasien"),
                                           rs.getBoolean("rl4b"),
                                           rs.getBoolean("rl4asebab"),
                                           rs.getBoolean("rl4bsebab"),
                                           rs.getBoolean("data_HAIs"),
                                           rs.getBoolean("harian_HAIs"),
                                           rs.getBoolean("bulanan_HAIs"),
                                           rs.getBoolean("hitung_bor") 
                            });
                    }                                             
                 }
                LCount.setText(""+tabMode.getRowCount());
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
                        
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void isUser() {
        try{
            rs=koneksi.prepareStatement("select nm_dokter from dokter where kd_dokter='"+TKd.getText()+"'").executeQuery();
            if(rs.next()){
                TNmUser.setText(rs.getString(1));
            }else if(!rs.next()){
                rs=koneksi.prepareStatement("select nama from petugas where nip='"+TKd.getText()+"'").executeQuery();
                if(rs.next()){
                    TNmUser.setText(rs.getString(1));
                }else if(!rs.next()){
                    TNmUser.setText("");
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }

    public void emptTeks() {
        TKd.setText("");
        TPass.setText("");
        TKd.requestFocus();
    }

    private void getData() {
        i=tbUser.getSelectedRow();
        if(i!= -1){
            TKd.setText(tbUser.getValueAt(i,0).toString());
            TPass.setText(tbUser.getValueAt(i,2).toString());            
        }
    }

}

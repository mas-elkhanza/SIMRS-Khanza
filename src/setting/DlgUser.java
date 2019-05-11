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
import fungsi.akses;
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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;

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
    private String user="",jabatan="";
    private int i=0;

    /** Creates new form DlgUser
     * @param parent
     * @param modal */
    public DlgUser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"ID User","Nama User","Jabatan","Password","[I]ICD 10","[I]Obat Penyakit","[C]Dokter","[A]Jadwal Praktek","[C]Petugas","[L]Pasien","[A]Registrasi","[A]Tindakan Ralan",
                    "[A]Kamar Inap","[A]Tindakan Ranap","[A]Operasi","[A]Rujukan Keluar","[A]Rujukan Masuk","[A]Beri Obat, Alkes & BHP","[A]Resep Pulang",
                    "[L]Pasien Meninggal","[A]Diet Pasien","[L]Kelahiran Bayi","[A]Periksa Lab","[A]Periksa Radiologi","[A]Kasir Ralan",
                    "[J]Deposit Pasien","[J]Piutang Pasien","[L]Peminjaman Berkas RM","[C]Barcode Presensi","[C]Presensi Harian","[C]Presensi Bulanan",
                    "[C]Pegawai Admin","[C]Pegawai User","[D]Suplier Obat/Alkes/BHP","[D]Satuan Barang","[D]Konversi Satuan","[D]Jenis Obat/Alkes/BHP","[D]Obat, Alkes & BHP",
                    "[D]Stok Opname Apotek","[D]Stok Obat Pasien","[D]Pengadaan Obat, Alkes & BHP","[D]Penerimaan Obat, Alkes & BHP","[D]Penjualan Obat, Alkes & BHP","[D]Piutang Obat, Alkes & BHP",
                    "[D]Retur Ke Suplier","[D]Retur Dari Pembeli","[D]Retur Obat, Alkes & BHP Ranap","[D]Retur Piutang Pembeli","[D]Keuntungan Penjualan",
                    "[D]Keuntungan Beri Obat, Alkes & BHP","[D]Sirkulasi Obat, Alkes & BHP","[E]Barang Non Medis","[E]Pengadaan Barang Nonmedis","[E]Stok Keluar Non Medis",
                    "[E]Rekap Pengadaan Non Medis","[E]Rekap Stok Keluar Non Medis","[E]Biaya Pengadaan Non Medis","[F]Jenis Inventaris",
                    "[F]Kategori Inventaris","[F]Merk Inventaris","[F]Ruang Inventaris","[F]Produsen Inventaris","[F]Koleksi Inventaris",
                    "[F]Inventaris","[F]Sirkulasi Inventaris","[G]Jenis Parkir","[G]Parkir Masuk","[G]Parkir Keluar","[G]Rekap Parkir Harian",
                    "[G]Rekap Parkir Bulanan","[A]Informasi Kamar","[H]Harian Dokter Poli","[H]Obat Per Poli","[H]Obat Per Kamar",
                    "[H]Obat Per Dokter Ralan","[H]Obat Per Dokter Ranap","[H]Harian Dokter","[H]Bulanan Dokter","[H]Harian Paramedis",
                    "[H]Bulanan Paramedis","[H]Pembayaran Ralan","[H]Pembayaran Ranap","[H]Rekap Pembayaran Ralan","[H]Rekap Pembayaran Ranap",
                    "[H]Tagihan Masuk","[H]Tambahan Biaya","[H]Potongan Biaya","[A]No.Resep","[L]Riwayat Perawatan","[I]Frekuensi Penyakit Ralan","[I]Frekuensi Penyakit Ranap",
                    "[J]Kamar","[J]Tarif Ralan","[J]Tarif Ranap","[J]Tarif Lab","[J]Tarif Radiologi","[J]Tarif Operasi","[J]Akun Rekening","[J]Rekening Tahun",
                    "[J]Posting Jurnal","[J]Buku Besar","[J]Cash Flow","[J]Keuangan","[J]Pengeluaran Harian","[Q]Set P.J. Unit Penunjang","[Q]Set Oto Lokasi","[Q]Set Kamar Inap",
                    "[Q]Set Embalase & Tuslah","[Q]Tracer Login","[Q]Display Antrian Registrasi & Poli","[Q]Set Harga Obat","[Q]Set Penggunaan Tarif","[Q]Set Oto Ralan","[Q]Biaya Harian Kamar",
                    "[Q]Biaya Masuk Sekali","[Q]Set RM","[A]Billing Ralan","[A]Billing Ranap","[H]Detail JM Dokter","[A]IGD","[B]Barcode Ralan","[B]Barcode Ranap",
                    "[Q]Set Harga Obat Ralan","[Q]Set Harga Obat Ranap","[I]Penyakit AFP & PD3I","[I]Surveilans AFP & PD3I","[I]Surveilans Ralan","[L]Diagnosa Pasien",
                    "[I]Surveilans Ranap","[I]Pny.Tdk Menular Ranap","[I]Pny.Tdk Menular Ralan","[I]Kunjungan Ralan","[I]RL 3.2 Rawat Darurat","[I]RL 3.3 Gigi dan Mulut","[I]RL 3.7 Radiologi","[I]RL 3.8 Laboratorium","[H]Harian Dokter Ralan",
                    "[C]SMS Gateway","[C]Sidik Jari","[C]Jam Presensi","[C]Jadwal Pegawai","[G]Barcode Parkir","[Q]Set Billing","[A]DPJP Ranap","[D]Mutasi Obat/Alkes/BHP","[I]RL 3.4 Kebidanan","[I]RL 3.6 Pembedahan",
                    "[H]Fee Visit Dokter","[H]Fee Bacaan EKG","[H]Fee Rujukan Rontgen","[H]Fee Rujukan Ranap","[H]Fee Periksa Ralan","[J]Akun Bayar","[J]Bayar Pesan Obat",
                    "[H]Obat Per Dokter Peresep","[E]Jenis Non Medis","[J]Pemasukkan Lain-Lain","[J]Pengaturan Rekening","[Q]Closing Kasir","[Q]Set Keterlambatan Presensi",
                    "[Q]Set Harga Kamar","[H]Rekap Per Shift","[K]Cek NIK","[K]Cek No.Kartu","[K]Riwayat Rujukan PCare di VClaim","[H]Obat Per Cara Bayar","[I]Kunjungan Ranap",
                    "[J]Bayar Piutang","[H]Payment Point","[K]Cek No.Rujukan PCare di VClaim","[I]ICD 9","[D]Darurat Stok","[L]Retensi Data R.M.","[C]Temporary Presensi",
                    "[J]Jurnal Harian","[D]Sirkulasi Obat, Alkes & BHP 2","[A]Edit Registrasi","[K]Referensi Diagnosa VClaim","[K]Referensi Poli VClaim","[D]Industri Farmasi",
                    "[H]Harian J.S.","[H]Bulanan J.S.","[H]Harian BHP Medis/Paket Obat","[H]Bulanan BHP Medis/Paket Obat","[J]Piutang Belum Lunas","[K]Referensi Faskes VClaim",
                    "[K]Data Bridging SEP VClaim","[D]Pengambilan BHP UTD","[J]Tarif UTD","[M]Pengambilan BHP Medis","[M]BHP Medis Rusak","[E]Pengambilan UTD","[M]Pengambilan BHP Non Medis",
                    "[M]BHP Non Medis Rusak","[E]Suplier Non Medis","[M]Donor Darah","[K]Monitoring Verifikasi Klaim","[M]Cekal Darah","[M]Komponen Darah","[M]Stok Darah","[M]Pemisahan Darah",
                    "[H]Harian Kamar","[J]Rincian Piutang Pasien","[D]Keuntungan Beri Obat, Alkes & BHP 2","[K]Reklasifikasi Ralan","[K]Reklasifikasi Ranap","[M]Penyerahan Darah",
                    "[J]Hutang Obat & BHP","[D]Riwayat Obat, Alkes & BHP","[I]Sensus Harian Poli","[I]RL 4A Sebab Morbiditas Ranap","[K]Referensi Kamar Aplicare","[K]Ketersediaan Kamar Aplicare",
                    "[K]Klaim Baru Otomatis INACBG","[K]Klaim Baru Manual INACBG","[K]Coder NIK INACBG","[L]Mutasi Berkas RM","[J]Akun Piutang","[H]Harian KSO","[H]Bulanan KSO",
                    "[H]Harian Menejemen","[H]Bulanan Menejemen","[K]Cek Eligibilitas Inhealth","[K]Referensi Ruang Rawat Inhealth","[K]Referensi Poli Inhealth","[K]Referensi Faskes Inhealth",
                    "[K]Data Bridging SJP Inhealth","[H]Piutang Ralan","[H]Piutang Ranap","[J]Piutang Per Cara Bayar","[I]Lama Pelayanan Ralan","[L]Catatan Pasien","[I]RL 4B Sebab Morbiditas Ralan",
                    "[I]RL 4A Morbiditas Ralan","[I]RL 4B Morbiditas Ralan","[L]Data HAIs","[I]Harian HAIs","[I]Bulanan HAIs","[I]Hitung BOR","[L]Instansi/Perusahaan Pasien","[D]Resep Dokter",
                    "[I]Lama Pelayanan Apotek","[I]Hitung ALOS","[H]Detail Tindakan","[A]Rujukan Poli Internal","[H]Rekap Poli Anak","[N]Kunjungan Reg Per Poli","[N]Kunjungan Reg Per Dokter",
                    "[N]Kunjungan Reg Per Pekerjaan","[N]Kunjungan Reg Per Pendidikan","[N]Kunjungan Reg Per Tahun","[L]Berkas Digital Perawatan","[I]Pny Menular Ranap","[I]Pny Menular Ralan",
                    "[N]Kunjungan Reg Per Bulan","[N]Kunjungan Reg Per Tanggal","[N]Demografi Registrasi","[N]Reg Lama Per Tahun","[N]Reg Baru Per Tahun","[N]Reg Lama Per Bulan","[N]Reg Baru Per Bulan",
                    "[N]Reg Lama Per Tanggal","[N]Reg Baru Per Tanggal","[N]Batal Periksa Per Tahun","[N]Batal Periksa Per Bulan","[K]Referensi Diagnosa Pcare","[N]Batal Periksa Per Tanggal",
                    "[D]Kategori Obat/Alkes/BHP","[D]Golongan Obat/Alkes/BHP","[D]Obat/Alkes/BHP Per Tanggal","[D]Penjualan Bebas Per Tanggal","[K]Referensi Kesadaran Pcare","[I]Pembatalan Periksa Per Dokter",
                    "[H]Pembayaran Per Unit","[H]Rekap Pembayaran Per Unit","[N]Kunjungan Reg Per Cara Bayar","[E]Pengadaan Non Medis Per Tanggal","[E]Stok Keluar Non Medis Per Tanggal",
                    "[N]Kunjungan Ranap Per Tahun","[K]Cek Rujukan PCare","[N]Kunjungan Lab Ralan Per Tahun","[N]Kunjungan Rad Ralan Per Tahun","[I]Cek Entry Ralan","[K]Klaim Baru Manual INACBG 2",
                    "[D]Permintaan Obat & BHP","[D]Rekap Permintaan Obat & BHP","[D]Surat Pemesanan Obat & BHP","[E]Permintaan Barang Non Medis","[E]Rekap Permintaan Barang Non Medis",
                    "[E]Surat Pemesanan Barang Non Medis","[N]Kunjungan Per Perujuk","[K]Referensi Prosedur VClaim","[K]Referensi Kelas Rawat VClaim","[K]Referensi Dokter VClaim",
                    "[K]Referensi Spesialistik VClaim","[K]Referensi Ruang Rawat VClaim","[K]Referensi Cara Keluar VClaim","[K]Referensi Pasca Pulang VClaim","[H]Detail VK/OK","[A]Billing Parsial",
                    "[K]Cek No.Rujukan RS di VClaim","[K]Cek Rujukan Kartu PCare di VClaim","[K]Cek Rujukan Kartu RS di VClaim","[A]Akses Depo Obat/BHP","[K]Pembuatan Rujukan VClaim",
                    "[N]Kunjungan Lab Ralan Per Bulan","[D]Stok Keluar Medis","[N]Kunjungan Rad Ralan Per Bulan","[H]Detail JM Dokter 2","[L]Pengaduan/Chat","[N]Kunjungan Lab Ralan Per Tanggal",
                    "[N]Kunjungan Rad Ralan Per Tanggal","[I]Sensus Harian Ralan","[D]Metode Racik","[H]Pembayaran Per Akun Bayar","[D]Pengguna Obat/Alkes/BHP Resep","[D]Rekap Penerimaan Obat & BHP",
                    "[C]Master Berkas Pegawai","[C]Berkas Kepegawaian","[C]Riwayat Jabatan","[C]Riwayat Pendidikan","[C]Riwayat Naik Gaji","[C]Kegiatan Ilmiah & Pelatihan","[C]Riwayat Penghargaan",
                    "[C]Riwayat Penelitian","[E]Penerimaan Barang Non Medis","[J]Bayar Pesan Non Medis","[J]Hutang Barang Non Medis","[E]Rekap Penerimaan Non Medis","[I]Insiden Keselamatan",
                    "[L]Insiden Keselamatan Pasien","[N]Kejadian IKP Per Tahun","[N]Kejadian IKP Per Bulan","[N]Kejadian IKP Per Tanggal","[D]Riwayat Batch","[N]Kejadian IKP Per Jenis",
                    "[N]Kejadian IKP Per Dampak","[H]Piutang Per Akun Piutang","[N]Kunjungan Reg Per Agama","[N]Kunjungan Reg Per Umur","[L]Suku/Bangsa Pasien","[L]Bahasa Pasien","[L]Golongan TNI",
                    "[L]Satuan TNI","[L]Jabatan TNI","[L]Pangkat TNI","[L]Golongan POLRI","[L]Satuan POLRI","[L]Jabatan POLRI","[L]Pangkat POLRI","[L]Cacat Fisik","[N]Kunjungan Reg Per Suku/Bangsa",
                    "[N]Kunjungan Reg Per Bahasa","[A]Jadwal Operasi","[K]Mapping Poli VClaim","[N]Kunjungan Reg Per Cacat Fisik","[F]Barang CSSD","[K]SKDP BPJS","[A]Booking Registrasi",
                    "[K]Referensi Propinsi VClaim","[K]Referensi Kabupaten VClaim","[K]Referensi Kecamatan VClaim","[K]Referensi Dokter DPJP VClaim","[K]Riwayat Rujukan RS di VClaim",
                    "[K]Tanggal Rujukan di VClaim","[A]Permintaan Lab","[A]Permintaan Radiologi","[O]Indeks Surat","[O]Map Surat","[O]Almari Surat","[O]Rak Surat","[O]Ruang Surat",
                    "[O]Klasifikasi Surat","[O]Status Surat","[O]Sifat Surat","[O]Stts Balas Surat","[O]Surat Masuk","[K]Referensi Dokter PCare","[K]Referensi Poli PCare",
                    "[K]Referensi Provider PCare","[K]Referensi Stts Pulang PCare","[K]Referensi Spesialis PCare","[K]Referensi Subspesialis PCare","[K]Referensi Sarana PCare",
                    "[K]Referensi Khusus PCare","[K]Referensi Obat PCare","[K]Referensi Tindakan PCare","[K]Faskes Subspesialis PCare","[K]Faskes Alih Rawat PCare",
                    "[K]Faskes Thalasemia & Hemofili PCare","[K]Mapping Obat PCare","[K]Tarif Ralan RS & PCare","[K]Club Prolanis PCare","[K]Mapping Poli PCare",
                    "[K]Kegiatan Kelompok PCare","[K]Tarif Ranap RS & PCare","[K]Peserta Keg Kelompok PCare","[D]Sirkulasi Obat, Alkes & BHP 3","[K]Data Pendafataran PCare",
                    "[K]Mapping Dokter PCare","[I]Ranap Per Ruang","[I]Penyakit Ranap Per Cara Bayar","[I]Anggota Militer Dirawat","[Q]Set Input Parsial",
                    "[I]Lama Pelayanan Radiologi","[I]Lama Pelayanan Lab","[K]Cek Nomor SEP","[A]Catatan Dokter","[O]Surat Keluar","[D]Kegiatan Farmasi",
                    "[E]Stok Opname Non Medis","[E]Sirkulasi Non Medis","[I]Rekap Lab Per Tahun","[I]Perujuk Lab Per Tahun","[I]Rekap Radiologi Per Tahun",
                    "[I]Perujuk Radiologi Per Tahun","[I]Rekap Bulanan Porsi Diet","[I]Rekap Bulanan Macam Diet","[H]Payment Point 2","[H]Pembayaran Per Akun Bayar 2",
                    "[H]Hapus Nota Salah","[A]Asesmen Awal Rawat Inap","[L]HAIs Per Kamar/Bangsal","[D]PPN Obat","[J]Saldo Akun Per Bulan","[Q]Display Antrian Apotek",
                    "[K]Referensi Faskes Sisrute","[K]Referensi Alasan Rujuk Sisrute","[K]Referensi Diagnosa Sisrute","[K]Rujukan Masuk Sisrute","[K]Rujukan Keluar Sisrute",
                    "[K]Cek SKDP VClaim","[D]Data Batch","[I]Kunjungan Lab Ralan","[I]Kunjungan Lab Ranap","[I]Kunjungan Radiologi Ralan","[I]Kunjungan Radiologi Ranap",
                    "[K]Pemberian Obat PCare","[K]Pemberian Tindakan PCare","[H]Pembayaran Per Akun Bayar 3","[Q]Password Asuransi","[I]Data TB","[K]Ketersediaan Kamar SIRANAP",
                    "[N]Periode Laporan TB","[N]Rujukan TB","[N]Riwayat TB","[N]Tipe Diagnosis TB","[N]Status HIV TB","[N]Skoring Anak TB","[N]Konfirmasi Skoring 5 TB",
                    "[N]Konfirmasi Skoring 6 TB","[N]Sumber Obat TB","[N]Hasil Akhir Pengobatan TB","[N]Hasil Tes HIV TB","[D]Kadaluarsa Batch","[D]Sisa Stok",
                    "[D]Obat Per Resep","[F]Pemakaian Air PDAM","[F]Limbah Padat B3 Medis","[N]Pemakaian Air PDAM Per Tanggal","[N]Pemakaian Air PDAM Per Bulan",
                    "[N]Limbah B3 Medis Per Tanggal","[N]Limbah B3 Medis Per Bulan","[F]Limbah Padat Domestik","[N]Limbah Padat Domestik Per Tanggal",
                    "[N]Limbah Padat Domestik Per Bulan","[F]Mutu Air Limbah","[F]Pest Control","[P]Ruang Perpustakaan","[P]Kategori Koleksi","[P]Jenis Koleksi",
                    "[P]Pengarang/Penulis","[P]Penerbit Koleksi","[P]Koleksi Perpustakaan","[P]Inventaris Perpustakaan","[P]Pengaturan Peminjaman","[P]Denda Perpustakaan",
                    "[P]Anggota Perpustakaan","[P]Peminjaman Koleksi Perpustakaan","[P]Bayar Denda Perpustakaan","[P]Data Koleksi Ebook"
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
                java.lang.Object.class, java.lang.Object.class,java.lang.Object.class,java.lang.Object.class, java.lang.Boolean.class, java.lang.Boolean.class, 
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

        for (i = 0; i < 481;i++) {
            TableColumn column = tbUser.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(130);
            }else if(i==1){
                column.setPreferredWidth(180);
            }else if(i==2){
                column.setPreferredWidth(100);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(55);
            }else if(i==5){
                column.setPreferredWidth(88);
            }else if(i==6){
                column.setPreferredWidth(55);
            }else if(i==7){
                column.setPreferredWidth(95);
            }else if(i==8){
                column.setPreferredWidth(63);
            }else if(i==9){
                column.setPreferredWidth(53);
            }else if(i==10){
                column.setPreferredWidth(73);
            }else if(i==11){
                column.setPreferredWidth(95);
            }else if(i==12){
                column.setPreferredWidth(84);
            }else if(i==13){
                column.setPreferredWidth(103);
            }else if(i==14){
                column.setPreferredWidth(66);
            }else if(i==15){
                column.setPreferredWidth(100);
            }else if(i==16){
                column.setPreferredWidth(100);
            }else if(i==17){
                column.setPreferredWidth(135);
            }else if(i==18){
                column.setPreferredWidth(94);
            }else if(i==19){
                column.setPreferredWidth(110);
            }else if(i==20){
                column.setPreferredWidth(82);
            }else if(i==21){
                column.setPreferredWidth(93);
            }else if(i==22){
                column.setPreferredWidth(82);
            }else if(i==23){
                column.setPreferredWidth(107);
            }else if(i==24){
                column.setPreferredWidth(81);
            }else if(i==25){
                column.setPreferredWidth(95);
            }else if(i==26){
                column.setPreferredWidth(95);
            }else if(i==27){
                column.setPreferredWidth(135);
            }else if(i==28){
                column.setPreferredWidth(110);
            }else if(i==29){
                column.setPreferredWidth(103);
            }else if(i==30){
                column.setPreferredWidth(108);
            }else if(i==31){
                column.setPreferredWidth(100);
            }else if(i==32){
                column.setPreferredWidth(92);
            }else if(i==33){
                column.setPreferredWidth(136);
            }else if(i==34){
                column.setPreferredWidth(98);
            }else if(i==35){
                column.setPreferredWidth(105);
            }else if(i==36){
                column.setPreferredWidth(87);
            }else if(i==37){
                column.setPreferredWidth(114);
            }else if(i==38){
                column.setPreferredWidth(127);
            }else if(i==39){
                column.setPreferredWidth(110);
            }else if(i==40){
                column.setPreferredWidth(170);
            }else if(i==41){
                column.setPreferredWidth(170);
            }else if(i==42){
                column.setPreferredWidth(163);
            }else if(i==43){
                column.setPreferredWidth(153);
            }else if(i==44){
                column.setPreferredWidth(101);
            }else if(i==45){
                column.setPreferredWidth(115);
            }else if(i==46){
                column.setPreferredWidth(178);
            }else if(i==230){
                column.setPreferredWidth(125);
            }else if(i==231){
                column.setPreferredWidth(100);
            }else if(i==232){
                column.setPreferredWidth(165);
            }else if(i==233){
                column.setPreferredWidth(133);
            }else if(i==234){
                column.setPreferredWidth(133);
            }else if(i==235){
                column.setPreferredWidth(73);
            }else if(i==236){
                column.setPreferredWidth(80);
            }else if(i==237){
                column.setPreferredWidth(82);
            }else if(i==238){
                column.setPreferredWidth(75);
            }else if(i==239){
                column.setPreferredWidth(155);
            }else if(i==240){
                column.setPreferredWidth(90);
            }else if(i==241){
                column.setPreferredWidth(135);
            }else if(i==242){
                column.setPreferredWidth(80);
            }else if(i==243){
                column.setPreferredWidth(96);
            }else if(i==244){
                column.setPreferredWidth(120);
            }else if(i==245){
                column.setPreferredWidth(98);
            }else if(i==246){
                column.setPreferredWidth(133);
            }else if(i==247){
                column.setPreferredWidth(149);
            }else if(i==248){
                column.setPreferredWidth(165);
            }else if(i==249){
                column.setPreferredWidth(167);
            }else if(i==250){
                column.setPreferredWidth(147);
            }else if(i==251){
                column.setPreferredWidth(139);
            }else if(i==252){
                column.setPreferredWidth(110);
            }else if(i==253){
                column.setPreferredWidth(107);
            }else if(i==254){
                column.setPreferredWidth(142);
            }else if(i==255){
                column.setPreferredWidth(155);
            }else if(i==256){
                column.setPreferredWidth(120);
            }else if(i==257){
                column.setPreferredWidth(120);
            }else if(i==258){
                column.setPreferredWidth(116);
            }else if(i==259){
                column.setPreferredWidth(116);
            }else if(i==260){
                column.setPreferredWidth(113);
            }else if(i==261){
                column.setPreferredWidth(128);
            }else if(i==262){
                column.setPreferredWidth(125);
            }else if(i==263){
                column.setPreferredWidth(134);
            }else if(i==264){
                column.setPreferredWidth(130);
            }else if(i==265){
                column.setPreferredWidth(143);
            }else if(i==266){
                column.setPreferredWidth(142);
            }else if(i==267){
                column.setPreferredWidth(140);
            }else if(i==268){
                column.setPreferredWidth(144);
            }else if(i==269){
                column.setPreferredWidth(157);
            }else if(i==270){
                column.setPreferredWidth(160);
            }else if(i==271){
                column.setPreferredWidth(150);
            }else if(i==272){
                column.setPreferredWidth(165);
            }else if(i==273){
                column.setPreferredWidth(123);
            }else if(i==274){
                column.setPreferredWidth(154);
            }else if(i==275){
                column.setPreferredWidth(170);
            }else if(i==276){
                column.setPreferredWidth(186);
            }else if(i==277){
                column.setPreferredWidth(186);
            }else if(i==278){
                column.setPreferredWidth(159);
            }else if(i==279){
                column.setPreferredWidth(112);
            }else if(i==280){
                column.setPreferredWidth(175);
            }else if(i==281){
                column.setPreferredWidth(176);
            }else if(i==282){
                column.setPreferredWidth(94);
            }else if(i==283){
                column.setPreferredWidth(156);
            }else if(i==284){
                column.setPreferredWidth(134);
            }else if(i==285){
                column.setPreferredWidth(166);
            }else if(i==286){
                column.setPreferredWidth(164);
            }else if(i==287){
                column.setPreferredWidth(163);
            }else if(i==288){
                column.setPreferredWidth(196);
            }else if(i==289){
                column.setPreferredWidth(192);
            }else if(i==290){
                column.setPreferredWidth(131);
            }else if(i==291){
                column.setPreferredWidth(146);
            }else if(i==292){
                column.setPreferredWidth(162);
            }else if(i==293){
                column.setPreferredWidth(134);
            }else if(i==294){
                column.setPreferredWidth(154);
            }else if(i==295){
                column.setPreferredWidth(168);
            }else if(i==296){
                column.setPreferredWidth(158);
            }else if(i==297){
                column.setPreferredWidth(166);
            }else if(i==298){
                column.setPreferredWidth(80);
            }else if(i==299){
                column.setPreferredWidth(81);
            }else if(i==300){
                column.setPreferredWidth(158);
            }else if(i==301){
                column.setPreferredWidth(186);
            }else if(i==302){
                column.setPreferredWidth(170);
            }else if(i==303){
                column.setPreferredWidth(127);
            }else if(i==304){
                column.setPreferredWidth(150);
            }else if(i==305){
                column.setPreferredWidth(170);
            }else if(i==306){
                column.setPreferredWidth(105);
            }else if(i==307){
                column.setPreferredWidth(172);
            }else if(i==308){
                column.setPreferredWidth(108);
            }else if(i==309){
                column.setPreferredWidth(100);
            }else if(i==310){
                column.setPreferredWidth(182);
            }else if(i==311){
                column.setPreferredWidth(185);
            }else if(i==312){
                column.setPreferredWidth(116);
            }else if(i==313){
                column.setPreferredWidth(85);
            }else if(i==314){
                column.setPreferredWidth(158);
            }else if(i==315){
                column.setPreferredWidth(180);
            }else if(i==316){
                column.setPreferredWidth(168);
            }else if(i==317){
                column.setPreferredWidth(132);
            }else if(i==318){
                column.setPreferredWidth(120);
            }else if(i==319){
                column.setPreferredWidth(102);
            }else if(i==320){
                column.setPreferredWidth(114);
            }else if(i==321){
                column.setPreferredWidth(104);
            }else if(i==322){
                column.setPreferredWidth(150);
            }else if(i==323){
                column.setPreferredWidth(127);
            }else if(i==324){
                column.setPreferredWidth(108);
            }else if(i==325){
                column.setPreferredWidth(165);
            }else if(i==326){
                column.setPreferredWidth(130);
            }else if(i==327){
                column.setPreferredWidth(143);
            }else if(i==328){
                column.setPreferredWidth(160);
            }else if(i==329){
                column.setPreferredWidth(116);
            }else if(i==330){
                column.setPreferredWidth(152);
            }else if(i==331){
                column.setPreferredWidth(132);
            }else if(i==332){
                column.setPreferredWidth(128);
            }else if(i==333){
                column.setPreferredWidth(141);
            }else if(i==334){
                column.setPreferredWidth(90);
            }else if(i==335){
                column.setPreferredWidth(127);
            }else if(i==336){
                column.setPreferredWidth(140);
            }else if(i==337){
                column.setPreferredWidth(142);
            }else if(i==338){
                column.setPreferredWidth(148);
            }else if(i==339){
                column.setPreferredWidth(140);
            }else if(i==340){
                column.setPreferredWidth(115);
            }else if(i==341){
                column.setPreferredWidth(88);
            }else if(i==342){
                column.setPreferredWidth(85);
            }else if(i==343){
                column.setPreferredWidth(73);
            }else if(i==344){
                column.setPreferredWidth(79);
            }else if(i==345){
                column.setPreferredWidth(79);
            }else if(i==346){
                column.setPreferredWidth(96);
            }else if(i==347){
                column.setPreferredWidth(86);
            }else if(i==348){
                column.setPreferredWidth(90);
            }else if(i==349){
                column.setPreferredWidth(90);
            }else if(i==350){
                column.setPreferredWidth(70);
            }else if(i==351){
                column.setPreferredWidth(178);
            }else if(i==352){
                column.setPreferredWidth(150);
            }else if(i==353){
                column.setPreferredWidth(95);
            }else if(i==354){
                column.setPreferredWidth(130);
            }else if(i==355){
                column.setPreferredWidth(167);
            }else if(i==356){
                column.setPreferredWidth(83);
            }else if(i==357){
                column.setPreferredWidth(71);
            }else if(i==358){
                column.setPreferredWidth(109);
            }else if(i==359){
                column.setPreferredWidth(140);
            }else if(i==360){
                column.setPreferredWidth(155);
            }else if(i==361){
                column.setPreferredWidth(157);
            }else if(i==362){
                column.setPreferredWidth(163);
            }else if(i==363){
                column.setPreferredWidth(163);
            }else if(i==364){
                column.setPreferredWidth(144);
            }else if(i==365){
                column.setPreferredWidth(94);
            }else if(i==366){
                column.setPreferredWidth(120);
            }else if(i==367){
                column.setPreferredWidth(82);
            }else if(i==368){
                column.setPreferredWidth(70);
            }else if(i==369){
                column.setPreferredWidth(81);
            }else if(i==370){
                column.setPreferredWidth(69);
            }else if(i==371){
                column.setPreferredWidth(82);
            }else if(i==372){
                column.setPreferredWidth(96);
            }else if(i==373){
                column.setPreferredWidth(83);
            }else if(i==374){
                column.setPreferredWidth(73);
            }else if(i==375){
                column.setPreferredWidth(99);
            }else if(i==376){
                column.setPreferredWidth(80);
            }else if(i==377){
                column.setPreferredWidth(132);
            }else if(i==378){
                column.setPreferredWidth(117);
            }else if(i==379){
                column.setPreferredWidth(140);
            }else if(i==380){
                column.setPreferredWidth(155);
            }else if(i==381){
                column.setPreferredWidth(140);
            }else if(i==382){
                column.setPreferredWidth(158);
            }else if(i==383){
                column.setPreferredWidth(134);
            }else if(i==384){
                column.setPreferredWidth(134);
            }else if(i==385){
                column.setPreferredWidth(124);
            }else if(i==386){
                column.setPreferredWidth(145);
            }else if(i==387){
                column.setPreferredWidth(145);
            }else if(i==388){
                column.setPreferredWidth(139);
            }else if(i==389){
                column.setPreferredWidth(190);
            }else if(i==390){
                column.setPreferredWidth(119);
            }else if(i==391){
                column.setPreferredWidth(130);
            }else if(i==392){
                column.setPreferredWidth(113);
            }else if(i==393){
                column.setPreferredWidth(110);
            }else if(i==394){
                column.setPreferredWidth(142);
            }else if(i==395){
                column.setPreferredWidth(135);
            }else if(i==396){
                column.setPreferredWidth(158);
            }else if(i==397){
                column.setPreferredWidth(158);
            }else if(i==398){
                column.setPreferredWidth(145);
            }else if(i==399){
                column.setPreferredWidth(127);
            }else if(i==400){
                column.setPreferredWidth(103);
            }else if(i==401){
                column.setPreferredWidth(169);
            }else if(i==402){
                column.setPreferredWidth(131);
            }else if(i==403){
                column.setPreferredWidth(99);
            }else if(i==404){
                column.setPreferredWidth(142);
            }else if(i==405){
                column.setPreferredWidth(117);
            }else if(i==406){
                column.setPreferredWidth(92);
            }else if(i==407){
                column.setPreferredWidth(94);
            }else if(i==408){
                column.setPreferredWidth(80);
            }else if(i==409){
                column.setPreferredWidth(102);
            }else if(i==410){
                column.setPreferredWidth(137);
            }else if(i==411){
                column.setPreferredWidth(110);
            }else if(i==412){
                column.setPreferredWidth(120);
            }else if(i==413){
                column.setPreferredWidth(126);
            }else if(i==414){
                column.setPreferredWidth(147);
            }else if(i==415){
                column.setPreferredWidth(153);
            }else if(i==416){
                column.setPreferredWidth(137);
            }else if(i==417){
                column.setPreferredWidth(146);
            }else if(i==418){
                column.setPreferredWidth(98);
            }else if(i==419){
                column.setPreferredWidth(166);
            }else if(i==420){
                column.setPreferredWidth(106);
            }else if(i==421){
                column.setPreferredWidth(148);
            }else if(i==422){
                column.setPreferredWidth(135);
            }else if(i==423){
                column.setPreferredWidth(67);
            }else if(i==424){
                column.setPreferredWidth(120);
            }else if(i==425){
                column.setPreferredWidth(129);
            }else if(i==426){
                column.setPreferredWidth(139);
            }else if(i==427){
                column.setPreferredWidth(167);
            }else if(i==428){
                column.setPreferredWidth(149);
            }else if(i==429){
                column.setPreferredWidth(129);
            }else if(i==430){
                column.setPreferredWidth(129);
            }else if(i==431){
                column.setPreferredWidth(100);
            }else if(i==432){
                column.setPreferredWidth(75);
            }else if(i==433){
                column.setPreferredWidth(118);
            }else if(i==434){
                column.setPreferredWidth(123);
            }else if(i==435){
                column.setPreferredWidth(150);
            }else if(i==436){
                column.setPreferredWidth(152);
            }else if(i==437){
                column.setPreferredWidth(133);
            }else if(i==438){
                column.setPreferredWidth(154);
            }else if(i==439){
                column.setPreferredWidth(170);
            }else if(i==440){
                column.setPreferredWidth(113);
            }else if(i==441){
                column.setPreferredWidth(60);
            }else if(i==442){
                column.setPreferredWidth(168);
            }else if(i==443){
                column.setPreferredWidth(118);
            }else if(i==444){
                column.setPreferredWidth(78);
            }else if(i==445){
                column.setPreferredWidth(78);
            }else if(i==446){
                column.setPreferredWidth(110);
            }else if(i==447){
                column.setPreferredWidth(90);
            }else if(i==448){
                column.setPreferredWidth(102);
            }else if(i==449){
                column.setPreferredWidth(139);
            }else if(i==450){
                column.setPreferredWidth(139);
            }else if(i==451){
                column.setPreferredWidth(102);
            }else if(i==452){
                column.setPreferredWidth(152);
            }else if(i==453){
                column.setPreferredWidth(103);
            }else if(i==454){
                column.setPreferredWidth(107);
            }else if(i==455){
                column.setPreferredWidth(67);
            }else if(i==456){
                column.setPreferredWidth(98);
            }else if(i==457){
                column.setPreferredWidth(122);
            }else if(i==458){
                column.setPreferredWidth(133);
            }else if(i==459){
                column.setPreferredWidth(184);
            }else if(i==460){
                column.setPreferredWidth(172);
            }else if(i==461){
                column.setPreferredWidth(164);
            }else if(i==462){
                column.setPreferredWidth(152);
            }else if(i==463){
                column.setPreferredWidth(136);
            }else if(i==464){
                column.setPreferredWidth(197);
            }else if(i==465){
                column.setPreferredWidth(186);
            }else if(i==466){
                column.setPreferredWidth(101);
            }else if(i==467){
                column.setPreferredWidth(82);
            }else if(i==468){
                column.setPreferredWidth(121);
            }else if(i==469){
                column.setPreferredWidth(99);
            }else if(i==470){
                column.setPreferredWidth(83);
            }else if(i==471){
                column.setPreferredWidth(113);
            }else if(i==472){
                column.setPreferredWidth(99);
            }else if(i==473){
                column.setPreferredWidth(125);
            }else if(i==474){
                column.setPreferredWidth(140);
            }else if(i==475){
                column.setPreferredWidth(141);
            }else if(i==476){
                column.setPreferredWidth(122);
            }else if(i==477){
                column.setPreferredWidth(132);
            }else if(i==478){
                column.setPreferredWidth(188);
            }else if(i==479){
                column.setPreferredWidth(152);
            }else if(i==480){
                column.setPreferredWidth(114);
            }else{
                column.setPreferredWidth(130);
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

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Setup User ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

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
        jPanel1.setLayout(new java.awt.BorderLayout(0, 1));

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

        jPanel1.add(panelGlass6, java.awt.BorderLayout.CENTER);

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
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false'","User")==true){
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
                    "penyakit='"+tbUser.getValueAt(i,4).toString()+"', "+
                    "obat_penyakit='"+tbUser.getValueAt(i,5).toString()+"',"+
                    "dokter='"+tbUser.getValueAt(i,6).toString()+"',"+
                    "jadwal_praktek='"+tbUser.getValueAt(i,7).toString()+"',"+
                    "petugas='"+tbUser.getValueAt(i,8).toString()+"',"+
                    "pasien='"+tbUser.getValueAt(i,9).toString()+"',"+
                    "registrasi='"+tbUser.getValueAt(i,10).toString()+"',"+
                    "tindakan_ralan='"+tbUser.getValueAt(i,11).toString()+"',"+
                    "kamar_inap='"+tbUser.getValueAt(i,12).toString()+"',"+
                    "tindakan_ranap='"+tbUser.getValueAt(i,13).toString()+"',"+
                    "operasi='"+tbUser.getValueAt(i,14).toString()+"',"+
                    "rujukan_keluar='"+tbUser.getValueAt(i,15).toString()+"',"+
                    "rujukan_masuk='"+tbUser.getValueAt(i,16).toString()+"',"+
                    "beri_obat='"+tbUser.getValueAt(i,17).toString()+"',"+
                    "resep_pulang='"+tbUser.getValueAt(i,18).toString()+"',"+
                    "pasien_meninggal='"+tbUser.getValueAt(i,19).toString()+"',"+
                    "diet_pasien='"+tbUser.getValueAt(i,20).toString()+"',"+
                    "kelahiran_bayi='"+tbUser.getValueAt(i,21).toString()+"',"+
                    "periksa_lab='"+tbUser.getValueAt(i,22).toString()+"',"+
                    "periksa_radiologi='"+tbUser.getValueAt(i,23).toString()+"',"+
                    "kasir_ralan='"+tbUser.getValueAt(i,24).toString()+"',"+
                    "deposit_pasien='"+tbUser.getValueAt(i,25).toString()+"',"+
                    "piutang_pasien='"+tbUser.getValueAt(i,26).toString()+"',"+
                    "peminjaman_berkas='"+tbUser.getValueAt(i,27).toString()+"',"+
                    "barcode='"+tbUser.getValueAt(i,28).toString()+"',"+
                    "presensi_harian='"+tbUser.getValueAt(i,29).toString()+"',"+
                    "presensi_bulanan='"+tbUser.getValueAt(i,30).toString()+"',"+
                    "pegawai_admin='"+tbUser.getValueAt(i,31).toString()+"',"+
                    "pegawai_user='"+tbUser.getValueAt(i,32).toString()+"',"+
                    "suplier='"+tbUser.getValueAt(i,33).toString()+"',"+
                    "satuan_barang='"+tbUser.getValueAt(i,34).toString()+"',"+
                    "konversi_satuan='"+tbUser.getValueAt(i,35).toString()+"',"+
                    "jenis_barang='"+tbUser.getValueAt(i,36).toString()+"',"+
                    "obat='"+tbUser.getValueAt(i,37).toString()+"',"+
                    "stok_opname_obat='"+tbUser.getValueAt(i,38).toString()+"',"+
                    "stok_obat_pasien='"+tbUser.getValueAt(i,39).toString()+"',"+
                    "pengadaan_obat='"+tbUser.getValueAt(i,40).toString()+"',"+
                    "pemesanan_obat='"+tbUser.getValueAt(i,41).toString()+"',"+
                    "penjualan_obat='"+tbUser.getValueAt(i,42).toString()+"',"+
                    "piutang_obat='"+tbUser.getValueAt(i,43).toString()+"',"+
                    "retur_ke_suplier='"+tbUser.getValueAt(i,44).toString()+"',"+
                    "retur_dari_pembeli='"+tbUser.getValueAt(i,45).toString()+"',"+
                    "retur_obat_ranap='"+tbUser.getValueAt(i,46).toString()+"',"+
                    "retur_piutang_pasien='"+tbUser.getValueAt(i,47).toString()+"',"+
                    "keuntungan_penjualan='"+tbUser.getValueAt(i,48).toString()+"',"+
                    "keuntungan_beri_obat='"+tbUser.getValueAt(i,49).toString()+"',"+
                    "sirkulasi_obat='"+tbUser.getValueAt(i,50).toString()+"',"+
                    "ipsrs_barang='"+tbUser.getValueAt(i,51).toString()+"',"+
                    "ipsrs_pengadaan_barang='"+tbUser.getValueAt(i,52).toString()+"',"+
                    "ipsrs_stok_keluar='"+tbUser.getValueAt(i,53).toString()+"',"+
                    "ipsrs_rekap_pengadaan='"+tbUser.getValueAt(i,54).toString()+"',"+
                    "ipsrs_rekap_stok_keluar='"+tbUser.getValueAt(i,55).toString()+"',"+
                    "ipsrs_pengeluaran_harian='"+tbUser.getValueAt(i,56).toString()+"',"+
                    "inventaris_jenis='"+tbUser.getValueAt(i,57).toString()+"',"+
                    "inventaris_kategori='"+tbUser.getValueAt(i,58).toString()+"',"+
                    "inventaris_merk='"+tbUser.getValueAt(i,59).toString()+"',"+
                    "inventaris_ruang='"+tbUser.getValueAt(i,60).toString()+"',"+
                    "inventaris_produsen='"+tbUser.getValueAt(i,61).toString()+"',"+
                    "inventaris_koleksi='"+tbUser.getValueAt(i,62).toString()+"',"+
                    "inventaris_inventaris='"+tbUser.getValueAt(i,63).toString()+"',"+
                    "inventaris_sirkulasi='"+tbUser.getValueAt(i,64).toString()+"',"+
                    "parkir_jenis='"+tbUser.getValueAt(i,65).toString()+"',"+
                    "parkir_in='"+tbUser.getValueAt(i,66).toString()+"',"+
                    "parkir_out='"+tbUser.getValueAt(i,67).toString()+"',"+
                    "parkir_rekap_harian='"+tbUser.getValueAt(i,68).toString()+"',"+
                    "parkir_rekap_bulanan='"+tbUser.getValueAt(i,69).toString()+"',"+
                    "informasi_kamar='"+tbUser.getValueAt(i,70).toString()+"',"+
                    "harian_tindakan_poli='"+tbUser.getValueAt(i,71).toString()+"',"+
                    "obat_per_poli='"+tbUser.getValueAt(i,72).toString()+"',"+
                    "obat_per_kamar='"+tbUser.getValueAt(i,73).toString()+"',"+
                    "obat_per_dokter_ralan='"+tbUser.getValueAt(i,74).toString()+"',"+
                    "obat_per_dokter_ranap='"+tbUser.getValueAt(i,75).toString()+"',"+
                    "harian_dokter='"+tbUser.getValueAt(i,76).toString()+"',"+
                    "bulanan_dokter='"+tbUser.getValueAt(i,77).toString()+"',"+
                    "harian_paramedis='"+tbUser.getValueAt(i,78).toString()+"',"+
                    "bulanan_paramedis='"+tbUser.getValueAt(i,79).toString()+"',"+
                    "pembayaran_ralan='"+tbUser.getValueAt(i,80).toString()+"',"+
                    "pembayaran_ranap='"+tbUser.getValueAt(i,81).toString()+"',"+
                    "rekap_pembayaran_ralan='"+tbUser.getValueAt(i,82).toString()+"',"+
                    "rekap_pembayaran_ranap='"+tbUser.getValueAt(i,83).toString()+"',"+
                    "tagihan_masuk='"+tbUser.getValueAt(i,84).toString()+"',"+
                    "tambahan_biaya='"+tbUser.getValueAt(i,85).toString()+"',"+
                    "potongan_biaya='"+tbUser.getValueAt(i,86).toString()+"',"+
                    "resep_obat='"+tbUser.getValueAt(i,87).toString()+"',"+
                    "resume_pasien='"+tbUser.getValueAt(i,88).toString()+"',"+
                    "penyakit_ralan='"+tbUser.getValueAt(i,89).toString()+"',"+
                    "penyakit_ranap='"+tbUser.getValueAt(i,90).toString()+"',"+
                    "kamar='"+tbUser.getValueAt(i,91).toString()+"',"+
                    "tarif_ralan='"+tbUser.getValueAt(i,92).toString()+"',"+
                    "tarif_ranap='"+tbUser.getValueAt(i,93).toString()+"',"+
                    "tarif_lab='"+tbUser.getValueAt(i,94).toString()+"',"+
                    "tarif_radiologi='"+tbUser.getValueAt(i,95).toString()+"',"+
                    "tarif_operasi='"+tbUser.getValueAt(i,96).toString()+"',"+
                    "akun_rekening='"+tbUser.getValueAt(i,97).toString()+"',"+
                    "rekening_tahun='"+tbUser.getValueAt(i,98).toString()+"',"+
                    "posting_jurnal='"+tbUser.getValueAt(i,99).toString()+"',"+
                    "buku_besar='"+tbUser.getValueAt(i,100).toString()+"',"+
                    "cashflow='"+tbUser.getValueAt(i,101).toString()+"',"+
                    "keuangan='"+tbUser.getValueAt(i,102).toString()+"',"+
                    "pengeluaran='"+tbUser.getValueAt(i,103).toString()+"',"+
                    "setup_pjlab='"+tbUser.getValueAt(i,104).toString()+"',"+
                    "setup_otolokasi='"+tbUser.getValueAt(i,105).toString()+"',"+
                    "setup_jam_kamin='"+tbUser.getValueAt(i,106).toString()+"',"+
                    "setup_embalase='"+tbUser.getValueAt(i,107).toString()+"',"+
                    "tracer_login='"+tbUser.getValueAt(i,108).toString()+"',"+
                    "display='"+tbUser.getValueAt(i,109).toString()+"',"+
                    "set_harga_obat='"+tbUser.getValueAt(i,110).toString()+"',"+
                    "set_penggunaan_tarif='"+tbUser.getValueAt(i,111).toString()+"',"+
                    "set_oto_ralan='"+tbUser.getValueAt(i,112).toString()+"',"+
                    "biaya_harian='"+tbUser.getValueAt(i,113).toString()+"',"+
                    "biaya_masuk_sekali='"+tbUser.getValueAt(i,114).toString()+"',"+
                    "set_no_rm='"+tbUser.getValueAt(i,115).toString()+"',"+
                    "billing_ralan='"+tbUser.getValueAt(i,116).toString()+"',"+
                    "billing_ranap='"+tbUser.getValueAt(i,117).toString()+"',"+
                    "jm_ranap_dokter='"+tbUser.getValueAt(i,118).toString()+"',"+
                    "igd='"+tbUser.getValueAt(i,119).toString()+"',"+
                    "barcoderalan='"+tbUser.getValueAt(i,120).toString()+"',"+
                    "barcoderanap='"+tbUser.getValueAt(i,121).toString()+"',"+
                    "set_harga_obat_ralan='"+tbUser.getValueAt(i,122).toString()+"',"+
                    "set_harga_obat_ranap='"+tbUser.getValueAt(i,123).toString()+"',"+
                    "penyakit_pd3i='"+tbUser.getValueAt(i,124).toString()+"',"+
                    "surveilans_pd3i='"+tbUser.getValueAt(i,125).toString()+"',"+
                    "surveilans_ralan='"+tbUser.getValueAt(i,126).toString()+"',"+
                    "diagnosa_pasien='"+tbUser.getValueAt(i,127).toString()+"',"+
                    "surveilans_ranap='"+tbUser.getValueAt(i,128).toString()+"',"+
                    "pny_takmenular_ranap='"+tbUser.getValueAt(i,129).toString()+"',"+
                    "pny_takmenular_ralan='"+tbUser.getValueAt(i,130).toString()+"',"+
                    "kunjungan_ralan='"+tbUser.getValueAt(i,131).toString()+"',"+
                    "rl32='"+tbUser.getValueAt(i,132).toString()+"',"+
                    "rl33='"+tbUser.getValueAt(i,133).toString()+"',"+
                    "rl37='"+tbUser.getValueAt(i,134).toString()+"',"+
                    "rl38='"+tbUser.getValueAt(i,135).toString()+"',"+
                    "harian_tindakan_dokter='"+tbUser.getValueAt(i,136).toString()+"',"+
                    "sms='"+tbUser.getValueAt(i,137).toString()+"',"+
                    "sidikjari='"+tbUser.getValueAt(i,138).toString()+"',"+
                    "jam_masuk='"+tbUser.getValueAt(i,139).toString()+"',"+
                    "jadwal_pegawai='"+tbUser.getValueAt(i,140).toString()+"',"+
                    "parkir_barcode='"+tbUser.getValueAt(i,141).toString()+"',"+
                    "set_nota='"+tbUser.getValueAt(i,142).toString()+"',"+
                    "dpjp_ranap='"+tbUser.getValueAt(i,143).toString()+"',"+
                    "mutasi_barang='"+tbUser.getValueAt(i,144).toString()+"',"+
                    "rl34='"+tbUser.getValueAt(i,145).toString()+"',"+
                    "rl36='"+tbUser.getValueAt(i,146).toString()+"',"+
                    "fee_visit_dokter='"+tbUser.getValueAt(i,147).toString()+"',"+
                    "fee_bacaan_ekg='"+tbUser.getValueAt(i,148).toString()+"',"+
                    "fee_rujukan_rontgen='"+tbUser.getValueAt(i,149).toString()+"',"+
                    "fee_rujukan_ranap='"+tbUser.getValueAt(i,150).toString()+"',"+
                    "fee_ralan='"+tbUser.getValueAt(i,151).toString()+"',"+
                    "akun_bayar='"+tbUser.getValueAt(i,152).toString()+"',"+
                    "bayar_pemesanan_obat='"+tbUser.getValueAt(i,153).toString()+"',"+
                    "obat_per_dokter_peresep='"+tbUser.getValueAt(i,154).toString()+"',"+
                    "ipsrs_jenis_barang='"+tbUser.getValueAt(i,155).toString()+"',"+
                    "pemasukan_lain='"+tbUser.getValueAt(i,156).toString()+"',"+
                    "pengaturan_rekening='"+tbUser.getValueAt(i,157).toString()+"',"+
                    "closing_kasir='"+tbUser.getValueAt(i,158).toString()+"',"+
                    "keterlambatan_presensi='"+tbUser.getValueAt(i,159).toString()+"',"+
                    "set_harga_kamar='"+tbUser.getValueAt(i,160).toString()+"',"+
                    "rekap_per_shift='"+tbUser.getValueAt(i,161).toString()+"',"+
                    "bpjs_cek_nik='"+tbUser.getValueAt(i,162).toString()+"',"+
                    "bpjs_cek_kartu='"+tbUser.getValueAt(i,163).toString()+"',"+
                    "bpjs_cek_riwayat='"+tbUser.getValueAt(i,164).toString()+"',"+
                    "obat_per_cara_bayar='"+tbUser.getValueAt(i,165).toString()+"',"+
                    "kunjungan_ranap='"+tbUser.getValueAt(i,166).toString()+"',"+
                    "bayar_piutang='"+tbUser.getValueAt(i,167).toString()+"',"+
                    "payment_point='"+tbUser.getValueAt(i,168).toString()+"',"+
                    "bpjs_cek_nomor_rujukan='"+tbUser.getValueAt(i,169).toString()+"',"+
                    "icd9='"+tbUser.getValueAt(i,170).toString()+"',"+
                    "darurat_stok='"+tbUser.getValueAt(i,171).toString()+"',"+
                    "retensi_rm='"+tbUser.getValueAt(i,172).toString()+"',"+
                    "temporary_presensi='"+tbUser.getValueAt(i,173).toString()+"',"+
                    "jurnal_harian='"+tbUser.getValueAt(i,174).toString()+"',"+
                    "sirkulasi_obat2='"+tbUser.getValueAt(i,175).toString()+"',"+
                    "edit_registrasi='"+tbUser.getValueAt(i,176).toString()+"',"+
                    "bpjs_referensi_diagnosa='"+tbUser.getValueAt(i,177).toString()+"',"+
                    "bpjs_referensi_poli='"+tbUser.getValueAt(i,178).toString()+"',"+
                    "industrifarmasi='"+tbUser.getValueAt(i,179).toString()+"',"+
                    "harian_js='"+tbUser.getValueAt(i,180).toString()+"',"+
                    "bulanan_js='"+tbUser.getValueAt(i,181).toString()+"',"+
                    "harian_paket_bhp='"+tbUser.getValueAt(i,182).toString()+"',"+
                    "bulanan_paket_bhp='"+tbUser.getValueAt(i,183).toString()+"',"+
                    "piutang_pasien2='"+tbUser.getValueAt(i,184).toString()+"',"+
                    "bpjs_referensi_faskes='"+tbUser.getValueAt(i,185).toString()+"',"+
                    "bpjs_sep='"+tbUser.getValueAt(i,186).toString()+"',"+
                    "pengambilan_utd='"+tbUser.getValueAt(i,187).toString()+"',"+
                    "tarif_utd='"+tbUser.getValueAt(i,188).toString()+"',"+
                    "pengambilan_utd2='"+tbUser.getValueAt(i,189).toString()+"',"+
                    "utd_medis_rusak='"+tbUser.getValueAt(i,190).toString()+"',"+
                    "pengambilan_penunjang_utd='"+tbUser.getValueAt(i,191).toString()+"',"+
                    "pengambilan_penunjang_utd2='"+tbUser.getValueAt(i,192).toString()+"',"+
                    "utd_penunjang_rusak='"+tbUser.getValueAt(i,193).toString()+"',"+
                    "suplier_penunjang='"+tbUser.getValueAt(i,194).toString()+"',"+
                    "utd_donor='"+tbUser.getValueAt(i,195).toString()+"',"+
                    "bpjs_monitoring_klaim='"+tbUser.getValueAt(i,196).toString()+"',"+
                    "utd_cekal_darah='"+tbUser.getValueAt(i,197).toString()+"',"+
                    "utd_komponen_darah='"+tbUser.getValueAt(i,198).toString()+"',"+
                    "utd_stok_darah='"+tbUser.getValueAt(i,199).toString()+"',"+
                    "utd_pemisahan_darah='"+tbUser.getValueAt(i,200).toString()+"',"+
                    "harian_kamar='"+tbUser.getValueAt(i,201).toString()+"',"+
                    "rincian_piutang_pasien='"+tbUser.getValueAt(i,202).toString()+"',"+
                    "keuntungan_beri_obat_nonpiutang='"+tbUser.getValueAt(i,203).toString()+"',"+
                    "reklasifikasi_ralan='"+tbUser.getValueAt(i,204).toString()+"',"+
                    "reklasifikasi_ranap='"+tbUser.getValueAt(i,205).toString()+"',"+
                    "utd_penyerahan_darah='"+tbUser.getValueAt(i,206).toString()+"',"+
                    "hutang_obat='"+tbUser.getValueAt(i,207).toString()+"',"+
                    "riwayat_obat_alkes_bhp='"+tbUser.getValueAt(i,208).toString()+"',"+
                    "sensus_harian_poli='"+tbUser.getValueAt(i,209).toString()+"',"+
                    "rl4a='"+tbUser.getValueAt(i,210).toString()+"',"+
                    "aplicare_referensi_kamar='"+tbUser.getValueAt(i,211).toString()+"',"+
                    "aplicare_ketersediaan_kamar='"+tbUser.getValueAt(i,212).toString()+"',"+
                    "inacbg_klaim_baru_otomatis='"+tbUser.getValueAt(i,213).toString()+"',"+
                    "inacbg_klaim_baru_manual='"+tbUser.getValueAt(i,214).toString()+"',"+
                    "inacbg_coder_nik='"+tbUser.getValueAt(i,215).toString()+"',"+
                    "mutasi_berkas='"+tbUser.getValueAt(i,216).toString()+"',"+
                    "akun_piutang='"+tbUser.getValueAt(i,217).toString()+"',"+
                    "harian_kso='"+tbUser.getValueAt(i,218).toString()+"',"+
                    "bulanan_kso='"+tbUser.getValueAt(i,219).toString()+"',"+
                    "harian_menejemen='"+tbUser.getValueAt(i,220).toString()+"',"+
                    "bulanan_menejemen='"+tbUser.getValueAt(i,221).toString()+"',"+
                    "inhealth_cek_eligibilitas='"+tbUser.getValueAt(i,222).toString()+"',"+
                    "inhealth_referensi_jenpel_ruang_rawat='"+tbUser.getValueAt(i,223).toString()+"',"+
                    "inhealth_referensi_poli='"+tbUser.getValueAt(i,224).toString()+"',"+
                    "inhealth_referensi_faskes='"+tbUser.getValueAt(i,225).toString()+"',"+
                    "inhealth_sjp='"+tbUser.getValueAt(i,226).toString()+"',"+
                    "piutang_ralan='"+tbUser.getValueAt(i,227).toString()+"',"+
                    "piutang_ranap='"+tbUser.getValueAt(i,228).toString()+"',"+
                    "detail_piutang_penjab='"+tbUser.getValueAt(i,229).toString()+"',"+
                    "lama_pelayanan_ralan='"+tbUser.getValueAt(i,230).toString()+"',"+
                    "catatan_pasien='"+tbUser.getValueAt(i,231).toString()+"',"+
                    "rl4b='"+tbUser.getValueAt(i,232).toString()+"',"+
                    "rl4asebab='"+tbUser.getValueAt(i,233).toString()+"',"+
                    "rl4bsebab='"+tbUser.getValueAt(i,234).toString()+"',"+
                    "data_HAIs='"+tbUser.getValueAt(i,235).toString()+"',"+
                    "harian_HAIs='"+tbUser.getValueAt(i,236).toString()+"',"+
                    "bulanan_HAIs='"+tbUser.getValueAt(i,237).toString()+"',"+
                    "hitung_bor='"+tbUser.getValueAt(i,238).toString()+"',"+
                    "perusahaan_pasien='"+tbUser.getValueAt(i,239).toString()+"',"+
                    "resep_dokter='"+tbUser.getValueAt(i,240).toString()+"',"+
                    "lama_pelayanan_apotek='"+tbUser.getValueAt(i,241).toString()+"',"+
                    "hitung_alos='"+tbUser.getValueAt(i,242).toString()+"',"+
                    "detail_tindakan='"+tbUser.getValueAt(i,243).toString()+"',"+
                    "rujukan_poli_internal='"+tbUser.getValueAt(i,244).toString()+"',"+
                    "rekap_poli_anak='"+tbUser.getValueAt(i,245).toString()+"',"+
                    "grafik_kunjungan_poli='"+tbUser.getValueAt(i,246).toString()+"',"+
                    "grafik_kunjungan_perdokter='"+tbUser.getValueAt(i,247).toString()+"',"+
                    "grafik_kunjungan_perpekerjaan='"+tbUser.getValueAt(i,248).toString()+"',"+
                    "grafik_kunjungan_perpendidikan='"+tbUser.getValueAt(i,249).toString()+"',"+
                    "grafik_kunjungan_pertahun='"+tbUser.getValueAt(i,250).toString()+"',"+
                    "berkas_digital_perawatan='"+tbUser.getValueAt(i,251).toString()+"',"+
                    "penyakit_menular_ranap='"+tbUser.getValueAt(i,252).toString()+"',"+
                    "penyakit_menular_ralan='"+tbUser.getValueAt(i,253).toString()+"',"+
                    "grafik_kunjungan_perbulan='"+tbUser.getValueAt(i,254).toString()+"',"+
                    "grafik_kunjungan_pertanggal='"+tbUser.getValueAt(i,255).toString()+"',"+
                    "grafik_kunjungan_demografi='"+tbUser.getValueAt(i,256).toString()+"',"+
                    "grafik_kunjungan_statusdaftartahun='"+tbUser.getValueAt(i,257).toString()+"',"+
                    "grafik_kunjungan_statusdaftartahun2='"+tbUser.getValueAt(i,258).toString()+"',"+
                    "grafik_kunjungan_statusdaftarbulan='"+tbUser.getValueAt(i,259).toString()+"',"+
                    "grafik_kunjungan_statusdaftarbulan2='"+tbUser.getValueAt(i,260).toString()+"',"+
                    "grafik_kunjungan_statusdaftartanggal='"+tbUser.getValueAt(i,261).toString()+"',"+
                    "grafik_kunjungan_statusdaftartanggal2='"+tbUser.getValueAt(i,262).toString()+"',"+
                    "grafik_kunjungan_statusbataltahun='"+tbUser.getValueAt(i,263).toString()+"',"+
                    "grafik_kunjungan_statusbatalbulan='"+tbUser.getValueAt(i,264).toString()+"',"+
                    "pcare_cek_penyakit='"+tbUser.getValueAt(i,265).toString()+"',"+
                    "grafik_kunjungan_statusbataltanggal='"+tbUser.getValueAt(i,266).toString()+"',"+
                    "kategori_barang='"+tbUser.getValueAt(i,267).toString()+"',"+
                    "golongan_barang='"+tbUser.getValueAt(i,268).toString()+"',"+
                    "pemberian_obat_pertanggal='"+tbUser.getValueAt(i,269).toString()+"',"+
                    "penjualan_obat_pertanggal='"+tbUser.getValueAt(i,270).toString()+"',"+
                    "pcare_cek_kesadaran='"+tbUser.getValueAt(i,271).toString()+"',"+
                    "pembatalan_periksa_dokter='"+tbUser.getValueAt(i,272).toString()+"',"+
                    "pembayaran_per_unit='"+tbUser.getValueAt(i,273).toString()+"',"+
                    "rekap_pembayaran_per_unit='"+tbUser.getValueAt(i,274).toString()+"',"+
                    "grafik_kunjungan_percarabayar='"+tbUser.getValueAt(i,275).toString()+"',"+
                    "ipsrs_pengadaan_pertanggal='"+tbUser.getValueAt(i,276).toString()+"',"+
                    "ipsrs_stokkeluar_pertanggal='"+tbUser.getValueAt(i,277).toString()+"',"+
                    "grafik_kunjungan_ranaptahun='"+tbUser.getValueAt(i,278).toString()+"',"+
                    "pcare_cek_rujukan='"+tbUser.getValueAt(i,279).toString()+"',"+
                    "grafik_lab_ralantahun='"+tbUser.getValueAt(i,280).toString()+"',"+
                    "grafik_rad_ralantahun='"+tbUser.getValueAt(i,281).toString()+"',"+
                    "cek_entry_ralan='"+tbUser.getValueAt(i,282).toString()+"',"+
                    "inacbg_klaim_baru_manual2='"+tbUser.getValueAt(i,283).toString()+"',"+
                    "permintaan_medis='"+tbUser.getValueAt(i,284).toString()+"',"+
                    "rekap_permintaan_medis='"+tbUser.getValueAt(i,285).toString()+"',"+
                    "surat_pemesanan_medis='"+tbUser.getValueAt(i,286).toString()+"',"+
                    "permintaan_non_medis='"+tbUser.getValueAt(i,287).toString()+"',"+
                    "rekap_permintaan_non_medis='"+tbUser.getValueAt(i,288).toString()+"',"+
                    "surat_pemesanan_non_medis='"+tbUser.getValueAt(i,289).toString()+"',"+
                    "grafik_per_perujuk='"+tbUser.getValueAt(i,290).toString()+"',"+
                    "bpjs_cek_prosedur='"+tbUser.getValueAt(i,291).toString()+"',"+
                    "bpjs_cek_kelas_rawat='"+tbUser.getValueAt(i,292).toString()+"',"+
                    "bpjs_cek_dokter='"+tbUser.getValueAt(i,293).toString()+"',"+
                    "bpjs_cek_spesialistik='"+tbUser.getValueAt(i,294).toString()+"',"+
                    "bpjs_cek_ruangrawat='"+tbUser.getValueAt(i,295).toString()+"',"+
                    "bpjs_cek_carakeluar='"+tbUser.getValueAt(i,296).toString()+"',"+
                    "bpjs_cek_pasca_pulang='"+tbUser.getValueAt(i,297).toString()+"',"+
                    "detail_tindakan_okvk='"+tbUser.getValueAt(i,298).toString()+"',"+
                    "billing_parsial='"+tbUser.getValueAt(i,299).toString()+"',"+
                    "bpjs_cek_nomor_rujukan_rs='"+tbUser.getValueAt(i,300).toString()+"',"+
                    "bpjs_cek_rujukan_kartu_pcare='"+tbUser.getValueAt(i,301).toString()+"',"+
                    "bpjs_cek_rujukan_kartu_rs='"+tbUser.getValueAt(i,302).toString()+"',"+
                    "akses_depo_obat='"+tbUser.getValueAt(i,303).toString()+"',"+
                    "bpjs_rujukan_keluar='"+tbUser.getValueAt(i,304).toString()+"',"+
                    "grafik_lab_ralanbulan='"+tbUser.getValueAt(i,305).toString()+"',"+
                    "pengeluaran_stok_apotek='"+tbUser.getValueAt(i,306).toString()+"',"+
                    "grafik_rad_ralanbulan='"+tbUser.getValueAt(i,307).toString()+"',"+
                    "detailjmdokter2='"+tbUser.getValueAt(i,308).toString()+"',"+
                    "pengaduan_pasien='"+tbUser.getValueAt(i,309).toString()+"',"+
                    "grafik_lab_ralanhari='"+tbUser.getValueAt(i,310).toString()+"',"+
                    "grafik_rad_ralanhari='"+tbUser.getValueAt(i,311).toString()+"',"+
                    "sensus_harian_ralan='"+tbUser.getValueAt(i,312).toString()+"',"+
                    "metode_racik='"+tbUser.getValueAt(i,313).toString()+"',"+
                    "pembayaran_akun_bayar='"+tbUser.getValueAt(i,314).toString()+"',"+
                    "pengguna_obat_resep='"+tbUser.getValueAt(i,315).toString()+"',"+
                    "rekap_pemesanan='"+tbUser.getValueAt(i,316).toString()+"',"+
                    "master_berkas_pegawai='"+tbUser.getValueAt(i,317).toString()+"',"+
                    "berkas_kepegawaian='"+tbUser.getValueAt(i,318).toString()+"',"+
                    "riwayat_jabatan='"+tbUser.getValueAt(i,319).toString()+"',"+
                    "riwayat_pendidikan='"+tbUser.getValueAt(i,320).toString()+"',"+
                    "riwayat_naik_gaji='"+tbUser.getValueAt(i,321).toString()+"',"+
                    "kegiatan_ilmiah='"+tbUser.getValueAt(i,322).toString()+"',"+
                    "riwayat_penghargaan='"+tbUser.getValueAt(i,323).toString()+"',"+
                    "riwayat_penelitian='"+tbUser.getValueAt(i,324).toString()+"',"+
                    "penerimaan_non_medis='"+tbUser.getValueAt(i,325).toString()+"',"+
                    "bayar_pesan_non_medis='"+tbUser.getValueAt(i,326).toString()+"',"+
                    "hutang_barang_non_medis='"+tbUser.getValueAt(i,327).toString()+"',"+
                    "rekap_pemesanan_non_medis='"+tbUser.getValueAt(i,328).toString()+"',"+
                    "insiden_keselamatan='"+tbUser.getValueAt(i,329).toString()+"',"+
                    "insiden_keselamatan_pasien='"+tbUser.getValueAt(i,330).toString()+"',"+
                    "grafik_ikp_pertahun='"+tbUser.getValueAt(i,331).toString()+"',"+
                    "grafik_ikp_perbulan='"+tbUser.getValueAt(i,332).toString()+"',"+
                    "grafik_ikp_pertanggal='"+tbUser.getValueAt(i,333).toString()+"',"+
                    "riwayat_data_batch='"+tbUser.getValueAt(i,334).toString()+"',"+
                    "grafik_ikp_jenis='"+tbUser.getValueAt(i,335).toString()+"',"+
                    "grafik_ikp_dampak='"+tbUser.getValueAt(i,336).toString()+"',"+
                    "piutang_akun_piutang='"+tbUser.getValueAt(i,337).toString()+"',"+
                    "grafik_kunjungan_per_agama='"+tbUser.getValueAt(i,338).toString()+"',"+
                    "grafik_kunjungan_per_umur='"+tbUser.getValueAt(i,339).toString()+"',"+
                    "suku_bangsa='"+tbUser.getValueAt(i,340).toString()+"',"+
                    "bahasa_pasien='"+tbUser.getValueAt(i,341).toString()+"',"+
                    "golongan_tni='"+tbUser.getValueAt(i,342).toString()+"',"+
                    "satuan_tni='"+tbUser.getValueAt(i,343).toString()+"',"+
                    "jabatan_tni='"+tbUser.getValueAt(i,344).toString()+"',"+
                    "pangkat_tni='"+tbUser.getValueAt(i,345).toString()+"',"+
                    "golongan_polri='"+tbUser.getValueAt(i,346).toString()+"',"+
                    "satuan_polri='"+tbUser.getValueAt(i,347).toString()+"',"+
                    "jabatan_polri='"+tbUser.getValueAt(i,348).toString()+"',"+
                    "pangkat_polri='"+tbUser.getValueAt(i,349).toString()+"',"+
                    "cacat_fisik='"+tbUser.getValueAt(i,350).toString()+"',"+
                    "grafik_kunjungan_suku='"+tbUser.getValueAt(i,351).toString()+"',"+
                    "grafik_kunjungan_bahasa='"+tbUser.getValueAt(i,352).toString()+"',"+
                    "booking_operasi='"+tbUser.getValueAt(i,353).toString()+"',"+
                    "mapping_poli_bpjs='"+tbUser.getValueAt(i,354).toString()+"',"+
                    "grafik_kunjungan_per_cacat='"+tbUser.getValueAt(i,355).toString()+"',"+
                    "barang_cssd='"+tbUser.getValueAt(i,356).toString()+"',"+
                    "skdp_bpjs='"+tbUser.getValueAt(i,357).toString()+"',"+
                    "booking_registrasi='"+tbUser.getValueAt(i,358).toString()+"',"+
                    "bpjs_cek_propinsi='"+tbUser.getValueAt(i,359).toString()+"',"+
                    "bpjs_cek_kabupaten='"+tbUser.getValueAt(i,360).toString()+"',"+
                    "bpjs_cek_kecamatan='"+tbUser.getValueAt(i,361).toString()+"',"+
                    "bpjs_cek_dokterdpjp='"+tbUser.getValueAt(i,362).toString()+"',"+
                    "bpjs_cek_riwayat_rujukanrs='"+tbUser.getValueAt(i,363).toString()+"',"+
                    "bpjs_cek_tanggal_rujukan='"+tbUser.getValueAt(i,364).toString()+"',"+
                    "permintaan_lab='"+tbUser.getValueAt(i,365).toString()+"',"+
                    "permintaan_radiologi='"+tbUser.getValueAt(i,366).toString()+"',"+
                    "surat_indeks='"+tbUser.getValueAt(i,367).toString()+"',"+
                    "surat_map='"+tbUser.getValueAt(i,368).toString()+"',"+
                    "surat_almari='"+tbUser.getValueAt(i,369).toString()+"',"+
                    "surat_rak='"+tbUser.getValueAt(i,370).toString()+"',"+
                    "surat_ruang='"+tbUser.getValueAt(i,371).toString()+"',"+
                    "surat_klasifikasi='"+tbUser.getValueAt(i,372).toString()+"',"+
                    "surat_status='"+tbUser.getValueAt(i,373).toString()+"',"+
                    "surat_sifat='"+tbUser.getValueAt(i,374).toString()+"',"+
                    "surat_balas='"+tbUser.getValueAt(i,375).toString()+"',"+
                    "surat_masuk='"+tbUser.getValueAt(i,376).toString()+"',"+
                    "pcare_cek_dokter='"+tbUser.getValueAt(i,377).toString()+"',"+
                    "pcare_cek_poli='"+tbUser.getValueAt(i,378).toString()+"',"+
                    "pcare_cek_provider='"+tbUser.getValueAt(i,379).toString()+"',"+
                    "pcare_cek_statuspulang='"+tbUser.getValueAt(i,380).toString()+"',"+
                    "pcare_cek_spesialis='"+tbUser.getValueAt(i,381).toString()+"',"+
                    "pcare_cek_subspesialis='"+tbUser.getValueAt(i,382).toString()+"',"+
                    "pcare_cek_sarana='"+tbUser.getValueAt(i,383).toString()+"',"+
                    "pcare_cek_khusus='"+tbUser.getValueAt(i,384).toString()+"',"+
                    "pcare_cek_obat='"+tbUser.getValueAt(i,385).toString()+"',"+
                    "pcare_cek_tindakan='"+tbUser.getValueAt(i,386).toString()+"',"+
                    "pcare_cek_faskessubspesialis='"+tbUser.getValueAt(i,387).toString()+"',"+
                    "pcare_cek_faskesalihrawat='"+tbUser.getValueAt(i,388).toString()+"',"+
                    "pcare_cek_faskesthalasemia='"+tbUser.getValueAt(i,389).toString()+"',"+
                    "pcare_mapping_obat='"+tbUser.getValueAt(i,390).toString()+"',"+
                    "pcare_mapping_tindakan='"+tbUser.getValueAt(i,391).toString()+"',"+
                    "pcare_club_prolanis='"+tbUser.getValueAt(i,392).toString()+"',"+
                    "pcare_mapping_poli='"+tbUser.getValueAt(i,393).toString()+"',"+
                    "pcare_kegiatan_kelompok='"+tbUser.getValueAt(i,394).toString()+"',"+
                    "pcare_mapping_tindakan_ranap='"+tbUser.getValueAt(i,395).toString()+"',"+
                    "pcare_peserta_kegiatan_kelompok='"+tbUser.getValueAt(i,396).toString()+"',"+
                    "sirkulasi_obat3='"+tbUser.getValueAt(i,397).toString()+"',"+
                    "bridging_pcare_daftar='"+tbUser.getValueAt(i,398).toString()+"',"+
                    "pcare_mapping_dokter='"+tbUser.getValueAt(i,399).toString()+"',"+
                    "ranap_per_ruang='"+tbUser.getValueAt(i,400).toString()+"',"+
                    "penyakit_ranap_cara_bayar='"+tbUser.getValueAt(i,401).toString()+"',"+
                    "anggota_militer_dirawat='"+tbUser.getValueAt(i,402).toString()+"',"+
                    "set_input_parsial='"+tbUser.getValueAt(i,403).toString()+"',"+
                    "lama_pelayanan_radiologi='"+tbUser.getValueAt(i,404).toString()+"',"+
                    "lama_pelayanan_lab='"+tbUser.getValueAt(i,405).toString()+"',"+
                    "bpjs_cek_sep='"+tbUser.getValueAt(i,406).toString()+"',"+
                    "catatan_perawatan='"+tbUser.getValueAt(i,407).toString()+"',"+
                    "surat_keluar='"+tbUser.getValueAt(i,408).toString()+"',"+
                    "kegiatan_farmasi='"+tbUser.getValueAt(i,409).toString()+"',"+
                    "stok_opname_logistik='"+tbUser.getValueAt(i,410).toString()+"',"+
                    "sirkulasi_non_medis='"+tbUser.getValueAt(i,411).toString()+"',"+
                    "rekap_lab_pertahun='"+tbUser.getValueAt(i,412).toString()+"',"+
                    "perujuk_lab_pertahun='"+tbUser.getValueAt(i,413).toString()+"',"+
                    "rekap_radiologi_pertahun='"+tbUser.getValueAt(i,414).toString()+"',"+
                    "perujuk_radiologi_pertahun='"+tbUser.getValueAt(i,415).toString()+"',"+
                    "jumlah_porsi_diet='"+tbUser.getValueAt(i,416).toString()+"',"+
                    "jumlah_macam_diet='"+tbUser.getValueAt(i,417).toString()+"',"+
                    "payment_point2='"+tbUser.getValueAt(i,418).toString()+"',"+
                    "pembayaran_akun_bayar2='"+tbUser.getValueAt(i,419).toString()+"',"+
                    "hapus_nota_salah='"+tbUser.getValueAt(i,420).toString()+"',"+
                    "pengkajian_askep='"+tbUser.getValueAt(i,421).toString()+"',"+
                    "hais_perbangsal='"+tbUser.getValueAt(i,422).toString()+"',"+
                    "ppn_obat='"+tbUser.getValueAt(i,423).toString()+"',"+
                    "saldo_akun_perbulan='"+tbUser.getValueAt(i,424).toString()+"',"+
                    "display_apotek='"+tbUser.getValueAt(i,425).toString()+"',"+
                    "sisrute_referensi_faskes='"+tbUser.getValueAt(i,426).toString()+"',"+
                    "sisrute_referensi_alasanrujuk='"+tbUser.getValueAt(i,427).toString()+"',"+
                    "sisrute_referensi_diagnosa='"+tbUser.getValueAt(i,428).toString()+"',"+
                    "sisrute_rujukan_masuk='"+tbUser.getValueAt(i,429).toString()+"',"+
                    "sisrute_rujukan_keluar='"+tbUser.getValueAt(i,430).toString()+"',"+
                    "bpjs_cek_skdp='"+tbUser.getValueAt(i,431).toString()+"',"+
                    "data_batch='"+tbUser.getValueAt(i,432).toString()+"',"+
                    "kunjungan_permintaan_lab='"+tbUser.getValueAt(i,433).toString()+"',"+
                    "kunjungan_permintaan_lab2='"+tbUser.getValueAt(i,434).toString()+"',"+
                    "kunjungan_permintaan_radiologi='"+tbUser.getValueAt(i,435).toString()+"',"+
                    "kunjungan_permintaan_radiologi2='"+tbUser.getValueAt(i,436).toString()+"',"+
                    "pcare_pemberian_obat='"+tbUser.getValueAt(i,437).toString()+"',"+
                    "pcare_pemberian_tindakan='"+tbUser.getValueAt(i,438).toString()+"',"+
                    "pembayaran_akun_bayar3='"+tbUser.getValueAt(i,439).toString()+"',"+
                    "password_asuransi='"+tbUser.getValueAt(i,440).toString()+"',"+
                    "kemenkes_sitt='"+tbUser.getValueAt(i,441).toString()+"',"+
                    "siranap_ketersediaan_kamar='"+tbUser.getValueAt(i,442).toString()+"',"+
                    "grafik_tb_periodelaporan='"+tbUser.getValueAt(i,443).toString()+"',"+
                    "grafik_tb_rujukan='"+tbUser.getValueAt(i,444).toString()+"',"+
                    "grafik_tb_riwayat='"+tbUser.getValueAt(i,445).toString()+"',"+
                    "grafik_tb_tipediagnosis='"+tbUser.getValueAt(i,446).toString()+"',"+
                    "grafik_tb_statushiv='"+tbUser.getValueAt(i,447).toString()+"',"+
                    "grafik_tb_skoringanak='"+tbUser.getValueAt(i,448).toString()+"',"+
                    "grafik_tb_konfirmasiskoring5='"+tbUser.getValueAt(i,449).toString()+"',"+
                    "grafik_tb_konfirmasiskoring6='"+tbUser.getValueAt(i,450).toString()+"',"+
                    "grafik_tb_sumberobat='"+tbUser.getValueAt(i,451).toString()+"',"+
                    "grafik_tb_hasilakhirpengobatan='"+tbUser.getValueAt(i,452).toString()+"',"+
                    "grafik_tb_hasilteshiv='"+tbUser.getValueAt(i,453).toString()+"',"+
                    "kadaluarsa_batch='"+tbUser.getValueAt(i,454).toString()+"',"+
                    "sisa_stok='"+tbUser.getValueAt(i,455).toString()+"',"+
                    "obat_per_resep='"+tbUser.getValueAt(i,456).toString()+"',"+
                    "pemakaian_air_pdam='"+tbUser.getValueAt(i,457).toString()+"',"+
                    "limbah_b3_medis='"+tbUser.getValueAt(i,458).toString()+"',"+
                    "grafik_air_pdam_pertanggal='"+tbUser.getValueAt(i,459).toString()+"',"+
                    "grafik_air_pdam_perbulan='"+tbUser.getValueAt(i,460).toString()+"',"+
                    "grafik_limbahb3_pertanggal='"+tbUser.getValueAt(i,461).toString()+"',"+
                    "grafik_limbahb3_perbulan='"+tbUser.getValueAt(i,462).toString()+"',"+
                    "limbah_domestik='"+tbUser.getValueAt(i,463).toString()+"',"+
                    "grafik_limbahdomestik_pertanggal='"+tbUser.getValueAt(i,464).toString()+"',"+
                    "grafik_limbahdomestik_perbulan='"+tbUser.getValueAt(i,465).toString()+"',"+
                    "mutu_air_limbah='"+tbUser.getValueAt(i,466).toString()+"',"+
                    "pest_control='"+tbUser.getValueAt(i,467).toString()+"',"+
                    "ruang_perpustakaan='"+tbUser.getValueAt(i,468).toString()+"',"+
                    "kategori_perpustakaan='"+tbUser.getValueAt(i,469).toString()+"',"+
                    "jenis_perpustakaan='"+tbUser.getValueAt(i,470).toString()+"',"+
                    "pengarang_perpustakaan='"+tbUser.getValueAt(i,471).toString()+"',"+
                    "penerbit_perpustakaan='"+tbUser.getValueAt(i,472).toString()+"',"+
                    "koleksi_perpustakaan='"+tbUser.getValueAt(i,473).toString()+"',"+
                    "inventaris_perpustakaan='"+tbUser.getValueAt(i,474).toString()+"',"+
                    "set_peminjaman_perpustakaan='"+tbUser.getValueAt(i,475).toString()+"',"+
                    "denda_perpustakaan='"+tbUser.getValueAt(i,476).toString()+"',"+
                    "anggota_perpustakaan='"+tbUser.getValueAt(i,477).toString()+"',"+
                    "peminjaman_perpustakaan='"+tbUser.getValueAt(i,478).toString()+"',"+
                    "bayar_denda_perpustakaan='"+tbUser.getValueAt(i,479).toString()+"',"+
                    "ebook_perpustakaan='"+tbUser.getValueAt(i,480).toString()+"'");
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
        dlgdokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
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
        dlgpetugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
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
            }else if(evt.getKeyCode()==KeyEvent.VK_V){
                if(tbUser.getSelectedColumn()>4){
                    if(tbUser.getSelectedRow()!=-1){
                        if(tbUser.getValueAt(tbUser.getSelectedRow(),tbUser.getSelectedColumn()).toString().equals("false")){
                            tbUser.setValueAt(true,tbUser.getSelectedRow(),tbUser.getSelectedColumn());
                        }else{
                            tbUser.setValueAt(false,tbUser.getSelectedRow(),tbUser.getSelectedColumn());
                        }
                        
                    }
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
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());   
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
            }
            Valid.MyReport("rptUser.jasper","report","::[ Data User ]::",param);
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
                        "lama_pelayanan_ralan,catatan_pasien,rl4b,rl4asebab,rl4bsebab,data_HAIs,harian_HAIs,bulanan_HAIs,hitung_bor,perusahaan_pasien, "+
                        "resep_dokter,lama_pelayanan_apotek,hitung_alos,detail_tindakan,rujukan_poli_internal,rekap_poli_anak,grafik_kunjungan_poli, "+
                        "grafik_kunjungan_perdokter,grafik_kunjungan_perpekerjaan,grafik_kunjungan_perpendidikan,grafik_kunjungan_pertahun,"+
                        "berkas_digital_perawatan,penyakit_menular_ranap,penyakit_menular_ralan,grafik_kunjungan_perbulan,grafik_kunjungan_pertanggal, "+
                        "grafik_kunjungan_demografi,grafik_kunjungan_statusdaftartahun,grafik_kunjungan_statusdaftartahun2, "+
                        "grafik_kunjungan_statusdaftarbulan,grafik_kunjungan_statusdaftarbulan2,grafik_kunjungan_statusdaftartanggal,"+
                        "grafik_kunjungan_statusdaftartanggal2,grafik_kunjungan_statusbataltahun,grafik_kunjungan_statusbatalbulan,"+
                        "pcare_cek_penyakit,grafik_kunjungan_statusbataltanggal,kategori_barang,golongan_barang,pemberian_obat_pertanggal,"+
                        "penjualan_obat_pertanggal,pcare_cek_kesadaran,pembatalan_periksa_dokter,pembayaran_per_unit,rekap_pembayaran_per_unit, "+
                        "grafik_kunjungan_percarabayar,ipsrs_pengadaan_pertanggal,ipsrs_stokkeluar_pertanggal,grafik_kunjungan_ranaptahun,"+
                        "pcare_cek_rujukan,grafik_lab_ralantahun,grafik_rad_ralantahun,cek_entry_ralan,inacbg_klaim_baru_manual2,"+
                        "permintaan_medis,rekap_permintaan_medis,surat_pemesanan_medis,permintaan_non_medis,rekap_permintaan_non_medis, "+
                        "surat_pemesanan_non_medis,grafik_per_perujuk,bpjs_cek_prosedur,bpjs_cek_kelas_rawat,bpjs_cek_dokter, "+
                        "bpjs_cek_spesialistik,bpjs_cek_ruangrawat,bpjs_cek_carakeluar,bpjs_cek_pasca_pulang,detail_tindakan_okvk, "+
                        "billing_parsial,bpjs_cek_nomor_rujukan_rs,bpjs_cek_rujukan_kartu_pcare,bpjs_cek_rujukan_kartu_rs,akses_depo_obat,"+
                        "bpjs_rujukan_keluar,grafik_lab_ralanbulan,pengeluaran_stok_apotek,grafik_rad_ralanbulan,detailjmdokter2,"+
                        "pengaduan_pasien,grafik_lab_ralanhari,grafik_rad_ralanhari,sensus_harian_ralan,metode_racik,pembayaran_akun_bayar, "+
                        "pengguna_obat_resep,rekap_pemesanan,master_berkas_pegawai,berkas_kepegawaian,riwayat_jabatan,riwayat_pendidikan,"+
                        "riwayat_naik_gaji,kegiatan_ilmiah,riwayat_penghargaan,riwayat_penelitian,penerimaan_non_medis,bayar_pesan_non_medis,"+
                        "hutang_barang_non_medis,rekap_pemesanan_non_medis,insiden_keselamatan,insiden_keselamatan_pasien,grafik_ikp_pertahun,"+
                        "grafik_ikp_perbulan,grafik_ikp_pertanggal,riwayat_data_batch,grafik_ikp_jenis,grafik_ikp_dampak,"+
                        "piutang_akun_piutang,grafik_kunjungan_per_agama,grafik_kunjungan_per_umur,suku_bangsa,bahasa_pasien,"+
                        "golongan_tni,satuan_tni,jabatan_tni,pangkat_tni,golongan_polri,satuan_polri,jabatan_polri,pangkat_polri, "+
                        "cacat_fisik,grafik_kunjungan_suku,grafik_kunjungan_bahasa,booking_operasi,mapping_poli_bpjs,grafik_kunjungan_per_cacat, "+
                        "barang_cssd,skdp_bpjs,booking_registrasi,bpjs_cek_propinsi,bpjs_cek_kabupaten,bpjs_cek_kecamatan, "+
                        "bpjs_cek_dokterdpjp,bpjs_cek_riwayat_rujukanrs,bpjs_cek_tanggal_rujukan,permintaan_lab,permintaan_radiologi, "+
                        "surat_indeks,surat_map,surat_almari,surat_rak,surat_ruang,surat_klasifikasi,surat_status,surat_sifat,surat_balas,"+
                        "surat_masuk,pcare_cek_dokter,pcare_cek_poli,pcare_cek_provider,pcare_cek_statuspulang,pcare_cek_spesialis,"+
                        "pcare_cek_subspesialis,pcare_cek_sarana,pcare_cek_khusus,pcare_cek_obat,pcare_cek_tindakan,"+
                        "pcare_cek_faskessubspesialis,pcare_cek_faskesalihrawat,pcare_cek_faskesthalasemia,pcare_mapping_obat,"+
                        "pcare_mapping_tindakan,pcare_club_prolanis,pcare_mapping_poli,pcare_kegiatan_kelompok,pcare_mapping_tindakan_ranap,"+
                        "pcare_peserta_kegiatan_kelompok,sirkulasi_obat3,bridging_pcare_daftar,pcare_mapping_dokter,"+
                        "ranap_per_ruang,penyakit_ranap_cara_bayar,anggota_militer_dirawat,set_input_parsial,lama_pelayanan_radiologi, "+
                        "lama_pelayanan_lab,bpjs_cek_sep,catatan_perawatan,surat_keluar,kegiatan_farmasi,stok_opname_logistik,"+
                        "sirkulasi_non_medis,rekap_lab_pertahun,perujuk_lab_pertahun,rekap_radiologi_pertahun,perujuk_radiologi_pertahun,"+
                        "jumlah_porsi_diet,jumlah_macam_diet,payment_point2,pembayaran_akun_bayar2,hapus_nota_salah,"+
                        "pengkajian_askep,hais_perbangsal,ppn_obat,saldo_akun_perbulan,display_apotek,sisrute_referensi_faskes,"+
                        "sisrute_referensi_alasanrujuk,sisrute_referensi_diagnosa,sisrute_rujukan_masuk,sisrute_rujukan_keluar,"+
                        "bpjs_cek_skdp,data_batch,kunjungan_permintaan_lab,kunjungan_permintaan_lab2,kunjungan_permintaan_radiologi,"+
                        "kunjungan_permintaan_radiologi2,pcare_pemberian_obat,pcare_pemberian_tindakan,pembayaran_akun_bayar3,"+
                        "password_asuransi,kemenkes_sitt,siranap_ketersediaan_kamar,grafik_tb_periodelaporan,grafik_tb_rujukan,"+
                        "grafik_tb_riwayat,grafik_tb_tipediagnosis,grafik_tb_statushiv,grafik_tb_skoringanak,grafik_tb_konfirmasiskoring5,"+
                        "grafik_tb_konfirmasiskoring6,grafik_tb_sumberobat,grafik_tb_hasilakhirpengobatan,grafik_tb_hasilteshiv,"+
                        "kadaluarsa_batch,sisa_stok,obat_per_resep,pemakaian_air_pdam,limbah_b3_medis,grafik_air_pdam_pertanggal,"+
                        "grafik_air_pdam_perbulan,grafik_limbahb3_pertanggal,grafik_limbahb3_perbulan,limbah_domestik,"+
                        "grafik_limbahdomestik_pertanggal,grafik_limbahdomestik_perbulan,mutu_air_limbah,pest_control,ruang_perpustakaan,"+
                        "kategori_perpustakaan,jenis_perpustakaan,pengarang_perpustakaan,penerbit_perpustakaan,koleksi_perpustakaan,"+
                        "inventaris_perpustakaan,set_peminjaman_perpustakaan,denda_perpustakaan,anggota_perpustakaan,peminjaman_perpustakaan,"+
                        "bayar_denda_perpustakaan,ebook_perpustakaan from user order by AES_DECRYPT(id_user,'nur')");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    user="";
                    user=Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString(1));
                    jabatan=Sequel.cariIsi("select nm_sps from spesialis where kd_sps=?",Sequel.cariIsi("select kd_sps from dokter where kd_dokter=?",rs.getString(1)));
                    if(user.equals("")){
                        user=Sequel.cariIsi("select nama from petugas where nip=?",rs.getString(1));
                        jabatan=Sequel.cariIsi("select nm_jbtn from jabatan where kd_jbtn=?",Sequel.cariIsi("select kd_jbtn from petugas where nip=?",rs.getString(1)));
                    }    
                    try {
                        if(rs.getString(1).toLowerCase().contains(TCari.getText().toLowerCase())||
                                user.toLowerCase().contains(TCari.getText().toLowerCase())||
                                jabatan.toLowerCase().contains(TCari.getText().toLowerCase())){
                            tabMode.addRow(new Object[]{rs.getString(1),
                               user,jabatan,rs.getString(2),
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
                               rs.getBoolean("hitung_bor"),
                               rs.getBoolean("perusahaan_pasien"),
                               rs.getBoolean("resep_dokter"),
                               rs.getBoolean("lama_pelayanan_apotek"),
                               rs.getBoolean("hitung_alos"),
                               rs.getBoolean("detail_tindakan"),
                               rs.getBoolean("rujukan_poli_internal"),
                               rs.getBoolean("rekap_poli_anak"),
                               rs.getBoolean("grafik_kunjungan_poli"),
                               rs.getBoolean("grafik_kunjungan_perdokter"),
                               rs.getBoolean("grafik_kunjungan_perpekerjaan"),
                               rs.getBoolean("grafik_kunjungan_perpendidikan"),
                               rs.getBoolean("grafik_kunjungan_pertahun"),
                               rs.getBoolean("berkas_digital_perawatan"),
                               rs.getBoolean("penyakit_menular_ranap"),
                               rs.getBoolean("penyakit_menular_ralan"),
                               rs.getBoolean("grafik_kunjungan_perbulan"),
                               rs.getBoolean("grafik_kunjungan_pertanggal"),
                               rs.getBoolean("grafik_kunjungan_demografi"),
                               rs.getBoolean("grafik_kunjungan_statusdaftartahun"),
                               rs.getBoolean("grafik_kunjungan_statusdaftartahun2"),
                               rs.getBoolean("grafik_kunjungan_statusdaftarbulan"),
                               rs.getBoolean("grafik_kunjungan_statusdaftarbulan2"),
                               rs.getBoolean("grafik_kunjungan_statusdaftartanggal"),
                               rs.getBoolean("grafik_kunjungan_statusdaftartanggal2"),
                               rs.getBoolean("grafik_kunjungan_statusbataltahun"),
                               rs.getBoolean("grafik_kunjungan_statusbatalbulan"),
                               rs.getBoolean("pcare_cek_penyakit"),
                               rs.getBoolean("grafik_kunjungan_statusbataltanggal"),
                               rs.getBoolean("kategori_barang"),
                               rs.getBoolean("golongan_barang"),
                               rs.getBoolean("pemberian_obat_pertanggal"),
                               rs.getBoolean("penjualan_obat_pertanggal"),
                               rs.getBoolean("pcare_cek_kesadaran"),
                               rs.getBoolean("pembatalan_periksa_dokter"),
                               rs.getBoolean("pembayaran_per_unit"),
                               rs.getBoolean("rekap_pembayaran_per_unit"),
                               rs.getBoolean("grafik_kunjungan_percarabayar"),
                               rs.getBoolean("ipsrs_pengadaan_pertanggal"),
                               rs.getBoolean("ipsrs_stokkeluar_pertanggal"),
                               rs.getBoolean("grafik_kunjungan_ranaptahun"),
                               rs.getBoolean("pcare_cek_rujukan"),
                               rs.getBoolean("grafik_lab_ralantahun"),
                               rs.getBoolean("grafik_rad_ralantahun"),
                               rs.getBoolean("cek_entry_ralan"),
                               rs.getBoolean("inacbg_klaim_baru_manual2"),
                               rs.getBoolean("permintaan_medis"),
                               rs.getBoolean("rekap_permintaan_medis"),
                               rs.getBoolean("surat_pemesanan_medis"),
                               rs.getBoolean("permintaan_non_medis"),
                               rs.getBoolean("rekap_permintaan_non_medis"),
                               rs.getBoolean("surat_pemesanan_non_medis"),
                               rs.getBoolean("grafik_per_perujuk"),
                               rs.getBoolean("bpjs_cek_prosedur"),
                               rs.getBoolean("bpjs_cek_kelas_rawat"),
                               rs.getBoolean("bpjs_cek_dokter"),
                               rs.getBoolean("bpjs_cek_spesialistik"),
                               rs.getBoolean("bpjs_cek_ruangrawat"),
                               rs.getBoolean("bpjs_cek_carakeluar"),
                               rs.getBoolean("bpjs_cek_pasca_pulang"),
                               rs.getBoolean("detail_tindakan_okvk"),
                               rs.getBoolean("billing_parsial"),
                               rs.getBoolean("bpjs_cek_nomor_rujukan_rs"),
                               rs.getBoolean("bpjs_cek_rujukan_kartu_pcare"),
                               rs.getBoolean("bpjs_cek_rujukan_kartu_rs"),
                               rs.getBoolean("akses_depo_obat"),
                               rs.getBoolean("bpjs_rujukan_keluar"),
                               rs.getBoolean("grafik_lab_ralanbulan"),
                               rs.getBoolean("pengeluaran_stok_apotek"),
                               rs.getBoolean("grafik_rad_ralanbulan"),
                               rs.getBoolean("detailjmdokter2"),
                               rs.getBoolean("pengaduan_pasien"),
                               rs.getBoolean("grafik_lab_ralanhari"),
                               rs.getBoolean("grafik_rad_ralanhari"),
                               rs.getBoolean("sensus_harian_ralan"),
                               rs.getBoolean("metode_racik"),
                               rs.getBoolean("pembayaran_akun_bayar"),
                               rs.getBoolean("pengguna_obat_resep"),
                               rs.getBoolean("rekap_pemesanan"),
                               rs.getBoolean("master_berkas_pegawai"),
                               rs.getBoolean("berkas_kepegawaian"),
                               rs.getBoolean("riwayat_jabatan"),
                               rs.getBoolean("riwayat_pendidikan"),
                               rs.getBoolean("riwayat_naik_gaji"),
                               rs.getBoolean("kegiatan_ilmiah"),
                               rs.getBoolean("riwayat_penghargaan"),
                               rs.getBoolean("riwayat_penelitian"),
                               rs.getBoolean("penerimaan_non_medis"),
                               rs.getBoolean("bayar_pesan_non_medis"),
                               rs.getBoolean("hutang_barang_non_medis"),
                               rs.getBoolean("rekap_pemesanan_non_medis"),
                               rs.getBoolean("insiden_keselamatan"),
                               rs.getBoolean("insiden_keselamatan_pasien"),
                               rs.getBoolean("grafik_ikp_pertahun"),
                               rs.getBoolean("grafik_ikp_perbulan"),
                               rs.getBoolean("grafik_ikp_pertanggal"),
                               rs.getBoolean("riwayat_data_batch"),
                               rs.getBoolean("grafik_ikp_jenis"),
                               rs.getBoolean("grafik_ikp_dampak"),
                               rs.getBoolean("piutang_akun_piutang"),
                               rs.getBoolean("grafik_kunjungan_per_agama"),
                               rs.getBoolean("grafik_kunjungan_per_umur"),
                               rs.getBoolean("suku_bangsa"),
                               rs.getBoolean("bahasa_pasien"),
                               rs.getBoolean("golongan_tni"),
                               rs.getBoolean("satuan_tni"),
                               rs.getBoolean("jabatan_tni"),
                               rs.getBoolean("pangkat_tni"),
                               rs.getBoolean("golongan_polri"),
                               rs.getBoolean("satuan_polri"),
                               rs.getBoolean("jabatan_polri"),
                               rs.getBoolean("pangkat_polri"),
                               rs.getBoolean("cacat_fisik"),
                               rs.getBoolean("grafik_kunjungan_suku"),
                               rs.getBoolean("grafik_kunjungan_bahasa"),
                               rs.getBoolean("booking_operasi"),
                               rs.getBoolean("mapping_poli_bpjs"),
                               rs.getBoolean("grafik_kunjungan_per_cacat"),
                               rs.getBoolean("barang_cssd"),
                               rs.getBoolean("skdp_bpjs"),
                               rs.getBoolean("booking_registrasi"),
                               rs.getBoolean("bpjs_cek_propinsi"),
                               rs.getBoolean("bpjs_cek_kabupaten"),
                               rs.getBoolean("bpjs_cek_kecamatan"),
                               rs.getBoolean("bpjs_cek_dokterdpjp"),
                               rs.getBoolean("bpjs_cek_riwayat_rujukanrs"),
                               rs.getBoolean("bpjs_cek_tanggal_rujukan"),
                               rs.getBoolean("permintaan_lab"),
                               rs.getBoolean("permintaan_radiologi"),
                               rs.getBoolean("surat_indeks"),
                               rs.getBoolean("surat_map"),
                               rs.getBoolean("surat_almari"),
                               rs.getBoolean("surat_rak"),
                               rs.getBoolean("surat_ruang"),
                               rs.getBoolean("surat_klasifikasi"),
                               rs.getBoolean("surat_status"),
                               rs.getBoolean("surat_sifat"),
                               rs.getBoolean("surat_balas"),
                               rs.getBoolean("surat_masuk"),
                               rs.getBoolean("pcare_cek_dokter"),
                               rs.getBoolean("pcare_cek_poli"),
                               rs.getBoolean("pcare_cek_provider"),
                               rs.getBoolean("pcare_cek_statuspulang"),
                               rs.getBoolean("pcare_cek_spesialis"),
                               rs.getBoolean("pcare_cek_subspesialis"),
                               rs.getBoolean("pcare_cek_sarana"),
                               rs.getBoolean("pcare_cek_khusus"),
                               rs.getBoolean("pcare_cek_obat"),
                               rs.getBoolean("pcare_cek_tindakan"),
                               rs.getBoolean("pcare_cek_faskessubspesialis"),
                               rs.getBoolean("pcare_cek_faskesalihrawat"),
                               rs.getBoolean("pcare_cek_faskesthalasemia"),
                               rs.getBoolean("pcare_mapping_obat"),
                               rs.getBoolean("pcare_mapping_tindakan"),
                               rs.getBoolean("pcare_club_prolanis"),
                               rs.getBoolean("pcare_mapping_poli"),
                               rs.getBoolean("pcare_kegiatan_kelompok"),
                               rs.getBoolean("pcare_mapping_tindakan_ranap"),
                               rs.getBoolean("pcare_peserta_kegiatan_kelompok"),
                               rs.getBoolean("sirkulasi_obat3"),
                               rs.getBoolean("bridging_pcare_daftar"),
                               rs.getBoolean("pcare_mapping_dokter"),
                               rs.getBoolean("ranap_per_ruang"),
                               rs.getBoolean("penyakit_ranap_cara_bayar"),
                               rs.getBoolean("anggota_militer_dirawat"),
                               rs.getBoolean("set_input_parsial"),
                               rs.getBoolean("lama_pelayanan_radiologi"),
                               rs.getBoolean("lama_pelayanan_lab"),
                               rs.getBoolean("bpjs_cek_sep"),
                               rs.getBoolean("catatan_perawatan"),
                               rs.getBoolean("surat_keluar"),
                               rs.getBoolean("kegiatan_farmasi"),
                               rs.getBoolean("stok_opname_logistik"),
                               rs.getBoolean("sirkulasi_non_medis"),
                               rs.getBoolean("rekap_lab_pertahun"),
                               rs.getBoolean("perujuk_lab_pertahun"),
                               rs.getBoolean("rekap_radiologi_pertahun"),
                               rs.getBoolean("perujuk_radiologi_pertahun"),
                               rs.getBoolean("jumlah_porsi_diet"),
                               rs.getBoolean("jumlah_macam_diet"),
                               rs.getBoolean("payment_point2"),
                               rs.getBoolean("pembayaran_akun_bayar2"),
                               rs.getBoolean("hapus_nota_salah"),
                               rs.getBoolean("pengkajian_askep"),
                               rs.getBoolean("hais_perbangsal"),
                               rs.getBoolean("ppn_obat"),
                               rs.getBoolean("saldo_akun_perbulan"),
                               rs.getBoolean("display_apotek"),
                               rs.getBoolean("sisrute_referensi_faskes"),
                               rs.getBoolean("sisrute_referensi_alasanrujuk"),
                               rs.getBoolean("sisrute_referensi_diagnosa"),
                               rs.getBoolean("sisrute_rujukan_masuk"),
                               rs.getBoolean("sisrute_rujukan_keluar"),
                               rs.getBoolean("bpjs_cek_skdp"),
                               rs.getBoolean("data_batch"),
                               rs.getBoolean("kunjungan_permintaan_lab"),
                               rs.getBoolean("kunjungan_permintaan_lab2"),
                               rs.getBoolean("kunjungan_permintaan_radiologi"),
                               rs.getBoolean("kunjungan_permintaan_radiologi2"),
                               rs.getBoolean("pcare_pemberian_obat"),
                               rs.getBoolean("pcare_pemberian_tindakan"),
                               rs.getBoolean("pembayaran_akun_bayar3"),
                               rs.getBoolean("password_asuransi"),
                               rs.getBoolean("kemenkes_sitt"),
                               rs.getBoolean("siranap_ketersediaan_kamar"),
                               rs.getBoolean("grafik_tb_periodelaporan"),
                               rs.getBoolean("grafik_tb_rujukan"),
                               rs.getBoolean("grafik_tb_riwayat"),
                               rs.getBoolean("grafik_tb_tipediagnosis"),
                               rs.getBoolean("grafik_tb_statushiv"),
                               rs.getBoolean("grafik_tb_skoringanak"),
                               rs.getBoolean("grafik_tb_konfirmasiskoring5"),
                               rs.getBoolean("grafik_tb_konfirmasiskoring6"),
                               rs.getBoolean("grafik_tb_sumberobat"),
                               rs.getBoolean("grafik_tb_hasilakhirpengobatan"),
                               rs.getBoolean("grafik_tb_hasilteshiv"),
                               rs.getBoolean("kadaluarsa_batch"),
                               rs.getBoolean("sisa_stok"),
                               rs.getBoolean("obat_per_resep"),
                               rs.getBoolean("pemakaian_air_pdam"),
                               rs.getBoolean("limbah_b3_medis"),
                               rs.getBoolean("grafik_air_pdam_pertanggal"),
                               rs.getBoolean("grafik_air_pdam_perbulan"),
                               rs.getBoolean("grafik_limbahb3_pertanggal"),
                               rs.getBoolean("grafik_limbahb3_perbulan"),
                               rs.getBoolean("limbah_domestik"),
                               rs.getBoolean("grafik_limbahdomestik_pertanggal"),
                               rs.getBoolean("grafik_limbahdomestik_perbulan"),
                               rs.getBoolean("mutu_air_limbah"),
                               rs.getBoolean("pest_control"),
                               rs.getBoolean("ruang_perpustakaan"),
                               rs.getBoolean("kategori_perpustakaan"),
                               rs.getBoolean("jenis_perpustakaan"),
                               rs.getBoolean("pengarang_perpustakaan"),
                               rs.getBoolean("penerbit_perpustakaan"),
                               rs.getBoolean("koleksi_perpustakaan"),
                               rs.getBoolean("inventaris_perpustakaan"),
                               rs.getBoolean("set_peminjaman_perpustakaan"),
                               rs.getBoolean("denda_perpustakaan"),
                               rs.getBoolean("anggota_perpustakaan"),
                               rs.getBoolean("peminjaman_perpustakaan"),
                               rs.getBoolean("bayar_denda_perpustakaan"),
                               rs.getBoolean("ebook_perpustakaan")
                            });
                        }   
                    } catch (Exception e) {
                        tabMode.addRow(new Object[]{rs.getString(1),
                           "Turn Out","Jabatan",rs.getString(2),
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
                           rs.getBoolean("hitung_bor"),
                           rs.getBoolean("perusahaan_pasien"),
                           rs.getBoolean("resep_dokter"),
                           rs.getBoolean("lama_pelayanan_apotek"),
                           rs.getBoolean("hitung_alos"),
                           rs.getBoolean("detail_tindakan"),
                           rs.getBoolean("rujukan_poli_internal"),
                           rs.getBoolean("rekap_poli_anak"),
                           rs.getBoolean("grafik_kunjungan_poli"),
                           rs.getBoolean("grafik_kunjungan_perdokter"),
                           rs.getBoolean("grafik_kunjungan_perpekerjaan"),
                           rs.getBoolean("grafik_kunjungan_perpendidikan"),
                           rs.getBoolean("grafik_kunjungan_pertahun"),
                           rs.getBoolean("berkas_digital_perawatan"),
                           rs.getBoolean("penyakit_menular_ranap"),
                           rs.getBoolean("penyakit_menular_ralan"),
                           rs.getBoolean("grafik_kunjungan_perbulan"),
                           rs.getBoolean("grafik_kunjungan_pertanggal"),
                           rs.getBoolean("grafik_kunjungan_demografi"),
                           rs.getBoolean("grafik_kunjungan_statusdaftartahun"),
                           rs.getBoolean("grafik_kunjungan_statusdaftartahun2"),
                           rs.getBoolean("grafik_kunjungan_statusdaftarbulan"),
                           rs.getBoolean("grafik_kunjungan_statusdaftarbulan2"),
                           rs.getBoolean("grafik_kunjungan_statusdaftartanggal"),
                           rs.getBoolean("grafik_kunjungan_statusdaftartanggal2"),
                           rs.getBoolean("grafik_kunjungan_statusbataltahun"),
                           rs.getBoolean("grafik_kunjungan_statusbatalbulan"),
                           rs.getBoolean("pcare_cek_penyakit"),
                           rs.getBoolean("grafik_kunjungan_statusbataltanggal"),
                           rs.getBoolean("kategori_barang"),
                           rs.getBoolean("golongan_barang"),
                           rs.getBoolean("pemberian_obat_pertanggal"),
                           rs.getBoolean("penjualan_obat_pertanggal"),
                           rs.getBoolean("pcare_cek_kesadaran"),
                           rs.getBoolean("pembatalan_periksa_dokter"),
                           rs.getBoolean("pembayaran_per_unit"),
                           rs.getBoolean("rekap_pembayaran_per_unit"),
                           rs.getBoolean("grafik_kunjungan_percarabayar"),
                           rs.getBoolean("ipsrs_pengadaan_pertanggal"),
                           rs.getBoolean("ipsrs_stokkeluar_pertanggal"),
                           rs.getBoolean("grafik_kunjungan_ranaptahun"),
                           rs.getBoolean("pcare_cek_rujukan"),
                           rs.getBoolean("grafik_lab_ralantahun"),
                           rs.getBoolean("grafik_rad_ralantahun"),
                           rs.getBoolean("cek_entry_ralan"),
                           rs.getBoolean("inacbg_klaim_baru_manual2"),
                           rs.getBoolean("permintaan_medis"),
                           rs.getBoolean("rekap_permintaan_medis"),
                           rs.getBoolean("surat_pemesanan_medis"),
                           rs.getBoolean("permintaan_non_medis"),
                           rs.getBoolean("rekap_permintaan_non_medis"),
                           rs.getBoolean("surat_pemesanan_non_medis"),
                           rs.getBoolean("grafik_per_perujuk"),
                           rs.getBoolean("bpjs_cek_prosedur"),
                           rs.getBoolean("bpjs_cek_kelas_rawat"),
                           rs.getBoolean("bpjs_cek_dokter"),
                           rs.getBoolean("bpjs_cek_spesialistik"),
                           rs.getBoolean("bpjs_cek_ruangrawat"),
                           rs.getBoolean("bpjs_cek_carakeluar"),
                           rs.getBoolean("bpjs_cek_pasca_pulang"),
                           rs.getBoolean("detail_tindakan_okvk"),
                           rs.getBoolean("billing_parsial"),
                           rs.getBoolean("bpjs_cek_nomor_rujukan_rs"),
                           rs.getBoolean("bpjs_cek_rujukan_kartu_pcare"),
                           rs.getBoolean("bpjs_cek_rujukan_kartu_rs"),
                           rs.getBoolean("akses_depo_obat"),
                           rs.getBoolean("bpjs_rujukan_keluar"),
                           rs.getBoolean("grafik_lab_ralanbulan"),
                           rs.getBoolean("pengeluaran_stok_apotek"),
                           rs.getBoolean("grafik_rad_ralanbulan"),
                           rs.getBoolean("detailjmdokter2"),
                           rs.getBoolean("pengaduan_pasien"),
                           rs.getBoolean("grafik_lab_ralanhari"),
                           rs.getBoolean("grafik_rad_ralanhari") ,
                           rs.getBoolean("sensus_harian_ralan"),
                           rs.getBoolean("metode_racik"),
                           rs.getBoolean("pembayaran_akun_bayar"),
                           rs.getBoolean("pengguna_obat_resep"),
                           rs.getBoolean("rekap_pemesanan"),
                           rs.getBoolean("master_berkas_pegawai"),
                           rs.getBoolean("berkas_kepegawaian"),
                           rs.getBoolean("riwayat_jabatan"),
                           rs.getBoolean("riwayat_pendidikan"),
                           rs.getBoolean("riwayat_naik_gaji"),
                           rs.getBoolean("kegiatan_ilmiah"),
                           rs.getBoolean("riwayat_penghargaan"),
                           rs.getBoolean("riwayat_penelitian"),
                           rs.getBoolean("penerimaan_non_medis"),
                           rs.getBoolean("bayar_pesan_non_medis"),
                           rs.getBoolean("hutang_barang_non_medis"),
                           rs.getBoolean("rekap_pemesanan_non_medis"),
                           rs.getBoolean("insiden_keselamatan"),
                           rs.getBoolean("insiden_keselamatan_pasien"),
                           rs.getBoolean("grafik_ikp_pertahun"),
                           rs.getBoolean("grafik_ikp_perbulan"),
                           rs.getBoolean("grafik_ikp_pertanggal"),
                           rs.getBoolean("riwayat_data_batch"),
                           rs.getBoolean("grafik_ikp_jenis"),
                           rs.getBoolean("grafik_ikp_dampak"),
                           rs.getBoolean("piutang_akun_piutang"),
                           rs.getBoolean("grafik_kunjungan_per_agama"),
                           rs.getBoolean("grafik_kunjungan_per_umur"),
                           rs.getBoolean("suku_bangsa"),
                           rs.getBoolean("bahasa_pasien"),
                           rs.getBoolean("golongan_tni"),
                           rs.getBoolean("satuan_tni"),
                           rs.getBoolean("jabatan_tni"),
                           rs.getBoolean("pangkat_tni"),
                           rs.getBoolean("golongan_polri"),
                           rs.getBoolean("satuan_polri"),
                           rs.getBoolean("jabatan_polri"),
                           rs.getBoolean("pangkat_polri"),
                           rs.getBoolean("cacat_fisik"),
                           rs.getBoolean("grafik_kunjungan_suku"),
                           rs.getBoolean("grafik_kunjungan_bahasa"),
                           rs.getBoolean("booking_operasi"),
                           rs.getBoolean("mapping_poli_bpjs"),
                           rs.getBoolean("grafik_kunjungan_per_cacat"),
                           rs.getBoolean("barang_cssd"),
                           rs.getBoolean("skdp_bpjs"),
                           rs.getBoolean("booking_registrasi"),
                           rs.getBoolean("bpjs_cek_propinsi"),
                           rs.getBoolean("bpjs_cek_kabupaten"),
                           rs.getBoolean("bpjs_cek_kecamatan"),
                           rs.getBoolean("bpjs_cek_dokterdpjp"),
                           rs.getBoolean("bpjs_cek_riwayat_rujukanrs"),
                           rs.getBoolean("bpjs_cek_tanggal_rujukan"),
                           rs.getBoolean("permintaan_lab"),
                           rs.getBoolean("permintaan_radiologi"),
                           rs.getBoolean("surat_indeks"),
                           rs.getBoolean("surat_map"),
                           rs.getBoolean("surat_almari"),
                           rs.getBoolean("surat_rak"),
                           rs.getBoolean("surat_ruang"),
                           rs.getBoolean("surat_klasifikasi"),
                           rs.getBoolean("surat_status"),
                           rs.getBoolean("surat_sifat"),
                           rs.getBoolean("surat_balas"),
                           rs.getBoolean("surat_masuk"),
                           rs.getBoolean("pcare_cek_dokter"),
                           rs.getBoolean("pcare_cek_poli"),
                           rs.getBoolean("pcare_cek_provider"),
                           rs.getBoolean("pcare_cek_statuspulang"),
                           rs.getBoolean("pcare_cek_spesialis"),
                           rs.getBoolean("pcare_cek_subspesialis"),
                           rs.getBoolean("pcare_cek_sarana"),
                           rs.getBoolean("pcare_cek_khusus"),
                           rs.getBoolean("pcare_cek_obat"),
                           rs.getBoolean("pcare_cek_tindakan"),
                           rs.getBoolean("pcare_cek_faskessubspesialis"),
                           rs.getBoolean("pcare_cek_faskesalihrawat"),
                           rs.getBoolean("pcare_cek_faskesthalasemia"),
                           rs.getBoolean("pcare_mapping_obat"),
                           rs.getBoolean("pcare_mapping_tindakan"),
                           rs.getBoolean("pcare_club_prolanis"),
                           rs.getBoolean("pcare_mapping_poli"),
                           rs.getBoolean("pcare_kegiatan_kelompok"),
                           rs.getBoolean("pcare_mapping_tindakan_ranap"),
                           rs.getBoolean("pcare_peserta_kegiatan_kelompok"),
                           rs.getBoolean("sirkulasi_obat3"),
                           rs.getBoolean("bridging_pcare_daftar"),
                           rs.getBoolean("pcare_mapping_dokter"),
                           rs.getBoolean("ranap_per_ruang"),
                           rs.getBoolean("penyakit_ranap_cara_bayar"),
                           rs.getBoolean("anggota_militer_dirawat"),
                           rs.getBoolean("set_input_parsial"),
                           rs.getBoolean("lama_pelayanan_radiologi"),
                           rs.getBoolean("lama_pelayanan_lab"),
                           rs.getBoolean("bpjs_cek_sep"),
                           rs.getBoolean("catatan_perawatan"),
                           rs.getBoolean("surat_keluar"),
                           rs.getBoolean("kegiatan_farmasi"),
                           rs.getBoolean("stok_opname_logistik"),
                           rs.getBoolean("sirkulasi_non_medis"),
                           rs.getBoolean("rekap_lab_pertahun"),
                           rs.getBoolean("perujuk_lab_pertahun"),
                           rs.getBoolean("rekap_radiologi_pertahun"),
                           rs.getBoolean("perujuk_radiologi_pertahun"),
                           rs.getBoolean("jumlah_porsi_diet"),
                           rs.getBoolean("jumlah_macam_diet"),
                           rs.getBoolean("payment_point2"),
                           rs.getBoolean("pembayaran_akun_bayar2"),
                           rs.getBoolean("hapus_nota_salah"),
                           rs.getBoolean("pengkajian_askep"),
                           rs.getBoolean("hais_perbangsal"),
                           rs.getBoolean("ppn_obat"),
                           rs.getBoolean("saldo_akun_perbulan"),
                           rs.getBoolean("display_apotek"),
                           rs.getBoolean("sisrute_referensi_faskes"),
                           rs.getBoolean("sisrute_referensi_alasanrujuk"),
                           rs.getBoolean("sisrute_referensi_diagnosa"),
                           rs.getBoolean("sisrute_rujukan_masuk"),
                           rs.getBoolean("sisrute_rujukan_keluar"),
                           rs.getBoolean("bpjs_cek_skdp"),
                           rs.getBoolean("data_batch"),
                           rs.getBoolean("kunjungan_permintaan_lab"),
                           rs.getBoolean("kunjungan_permintaan_lab2"),
                           rs.getBoolean("kunjungan_permintaan_radiologi"),
                           rs.getBoolean("kunjungan_permintaan_radiologi2"),
                           rs.getBoolean("pcare_pemberian_obat"),
                           rs.getBoolean("pcare_pemberian_tindakan"),
                           rs.getBoolean("pembayaran_akun_bayar3"),
                           rs.getBoolean("password_asuransi"),
                           rs.getBoolean("kemenkes_sitt"),
                           rs.getBoolean("siranap_ketersediaan_kamar"),
                           rs.getBoolean("grafik_tb_periodelaporan"),
                           rs.getBoolean("grafik_tb_rujukan"),
                           rs.getBoolean("grafik_tb_riwayat"),
                           rs.getBoolean("grafik_tb_tipediagnosis"),
                           rs.getBoolean("grafik_tb_statushiv"),
                           rs.getBoolean("grafik_tb_skoringanak"),
                           rs.getBoolean("grafik_tb_konfirmasiskoring5"),
                           rs.getBoolean("grafik_tb_konfirmasiskoring6"),
                           rs.getBoolean("grafik_tb_sumberobat"),
                           rs.getBoolean("grafik_tb_hasilakhirpengobatan"),
                           rs.getBoolean("grafik_tb_hasilteshiv"),
                           rs.getBoolean("kadaluarsa_batch"),
                           rs.getBoolean("sisa_stok"),
                           rs.getBoolean("obat_per_resep"),
                           rs.getBoolean("pemakaian_air_pdam"),
                           rs.getBoolean("limbah_b3_medis"),
                           rs.getBoolean("grafik_air_pdam_pertanggal"),
                           rs.getBoolean("grafik_air_pdam_perbulan"),
                           rs.getBoolean("grafik_limbahb3_pertanggal"),
                           rs.getBoolean("grafik_limbahb3_perbulan"),
                           rs.getBoolean("limbah_domestik"),
                           rs.getBoolean("grafik_limbahdomestik_pertanggal"),
                           rs.getBoolean("grafik_limbahdomestik_perbulan"),
                           rs.getBoolean("mutu_air_limbah"),
                           rs.getBoolean("pest_control"),
                           rs.getBoolean("ruang_perpustakaan"),
                           rs.getBoolean("kategori_perpustakaan"),
                           rs.getBoolean("jenis_perpustakaan"),
                           rs.getBoolean("pengarang_perpustakaan"),
                           rs.getBoolean("penerbit_perpustakaan"),
                           rs.getBoolean("koleksi_perpustakaan"),
                           rs.getBoolean("inventaris_perpustakaan"),
                           rs.getBoolean("set_peminjaman_perpustakaan"),
                           rs.getBoolean("denda_perpustakaan"),
                           rs.getBoolean("anggota_perpustakaan"),
                           rs.getBoolean("peminjaman_perpustakaan"),
                           rs.getBoolean("bayar_denda_perpustakaan"),
                           rs.getBoolean("ebook_perpustakaan")
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
            if(rs!=null){
                rs.close();
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
            TPass.setText(tbUser.getValueAt(i,3).toString());            
        }
    }

}

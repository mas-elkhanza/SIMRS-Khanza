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
    private String user="",jabatan="",copyhakakses="",userdicopy="";
    private int i=0,barisdicopy=-1;
    private DlgUpdateUser personal=new DlgUpdateUser(null,false);

    /** Creates new form DlgUser
     * @param parent
     * @param modal */
    public DlgUser(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        Object[] row={"ID User","Nama User","Jabatan","Password","[I]ICD 10","[I]Obat Penyakit","[C]Dokter","[A]Jadwal Praktek","[C]Petugas","[L]Pasien","[A]Registrasi","[A]Tindakan Ralan",
                "[A]Rawat Inap","[A]Tindakan Ranap","[A]Operasi","[A]Rujukan Keluar","[A]Rujukan Masuk","[A]Beri Obat, Alkes & BHP","[A]Resep Pulang",
                "[L]Pasien Meninggal","[A]Diet Pasien","[L]Kelahiran Bayi","[A]Periksa Lab PK","[A]Periksa Radiologi","[A]Rawat Jalan",
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
                "[J]Posting Jurnal","[J]Buku Besar","[J]Cash Flow","[J]Keuangan","[J]Pengeluaran Harian","[S]Set P.J. Unit Penunjang","[S]Set Oto Lokasi","[S]Set Kamar Inap",
                "[S]Set Embalase & Tuslah","[S]Tracer Login","[S]Display Antrian Registrasi & Poli","[S]Set Harga Obat","[S]Set Penggunaan Tarif","[S]Set Oto Ralan","[S]Biaya Harian Kamar",
                "[S]Biaya Masuk Sekali","[S]Set RM","[A]Billing Ralan","[A]Billing Ranap","[H]Detail JM Dokter","[A]IGD","[B]Barcode Ralan","[B]Barcode Ranap",
                "[S]Set Harga Obat Ralan","[S]Set Harga Obat Ranap","[I]Penyakit AFP & PD3I","[I]Surveilans AFP & PD3I","[I]Surveilans Ralan","[L]Diagnosa Pasien",
                "[I]Surveilans Ranap","[I]Pny.Tdk Menular Ranap","[I]Pny.Tdk Menular Ralan","[I]Kunjungan Ralan","[I]RL 3.2 Rawat Darurat","[I]RL 3.3 Gigi dan Mulut","[I]RL 3.7 Radiologi","[I]RL 3.8 Laboratorium","[H]Harian Dokter Ralan",
                "[C]SMS Gateway","[C]Sidik Jari","[C]Jam Presensi","[C]Jadwal Pegawai","[G]Barcode Parkir","[S]Set Billing","[A]DPJP Ranap","[D]Mutasi Obat/Alkes/BHP","[I]RL 3.4 Kebidanan","[I]RL 3.6 Pembedahan",
                "[H]Fee Visit Dokter","[H]Fee Bacaan EKG","[H]Fee Rujukan Rontgen","[H]Fee Rujukan Ranap","[H]Fee Periksa Ralan","[J]Akun Bayar","[J]Bayar Pesan Obat",
                "[H]Obat Per Dokter Peresep","[E]Jenis Barang Non Medis","[J]Pemasukkan Lain-Lain","[J]Pengaturan Rekening","[S]Closing Kasir","[S]Set Keterlambatan Presensi",
                "[S]Set Harga Kamar","[H]Rekap Per Shift","[K]Cek NIK","[K]Cek No.Kartu","[K]Riwayat Rujukan PCare di VClaim","[H]Obat Per Cara Bayar","[I]Kunjungan Ranap",
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
                "[I]Lama Pelayanan Apotek","[I]Hitung ALOS","[H]Detail Tindakan","[A]Rujukan Poli Internal","[H]Rekap Poli Anak","[N]Registrasi Per Poli","[N]Registrasi Per Dokter",
                "[N]Registrasi Per Pekerjaan","[N]Registrasi Per Pendidikan","[N]Registrasi Per Tahun","[L]Berkas Digital Perawatan","[I]Pny Menular Ranap","[I]Pny Menular Ralan",
                "[N]Registrasi Per Bulan","[N]Registrasi Per Tanggal","[N]Demografi Registrasi","[N]Reg Lama Per Tahun","[N]Reg Baru Per Tahun","[N]Reg Lama Per Bulan","[N]Reg Baru Per Bulan",
                "[N]Reg Lama Per Tanggal","[N]Reg Baru Per Tanggal","[N]Batal Periksa Per Tahun","[N]Batal Periksa Per Bulan","[K]Referensi Diagnosa Pcare","[N]Batal Periksa Per Tanggal",
                "[D]Kategori Obat/Alkes/BHP","[D]Golongan Obat/Alkes/BHP","[D]Obat/Alkes/BHP Per Tanggal","[D]Penjualan Bebas Per Tanggal","[K]Referensi Kesadaran Pcare","[I]Pembatalan Periksa Per Dokter",
                "[H]Pembayaran Per Unit","[H]Rekap Pembayaran Per Unit","[N]Registrasi Per Cara Bayar","[E]Pengadaan Non Medis Per Tanggal","[E]Stok Keluar Non Medis Per Tanggal",
                "[N]Kunjungan Ranap Per Tahun","[K]Cek Rujukan PCare","[N]Kunjungan Lab Ralan Per Tahun","[N]Kunjungan Rad Ralan Per Tahun","[I]Cek Entry Ralan","[K]Klaim Baru Manual INACBG 2",
                "[D]Permintaan Obat & BHP","[D]Ringkasan Permintaan Obat & BHP","[D]Surat Pemesanan Obat & BHP","[E]Permintaan Barang Non Medis","[E]Ringkasan Permintaan Barang Non Medis",
                "[E]Surat Pemesanan Barang Non Medis","[N]Registrasi Per Perujuk","[K]Referensi Prosedur VClaim","[K]Referensi Kelas Rawat VClaim","[K]Referensi Dokter VClaim",
                "[K]Referensi Spesialistik VClaim","[K]Referensi Ruang Rawat VClaim","[K]Referensi Cara Keluar VClaim","[K]Referensi Pasca Pulang VClaim","[H]Detail VK/OK","[A]Billing Parsial",
                "[K]Cek No.Rujukan RS di VClaim","[K]Cek Rujukan Kartu PCare di VClaim","[K]Cek Rujukan Kartu RS di VClaim","[A]Akses Depo Obat/BHP","[K]Pembuatan Rujukan VClaim",
                "[N]Kunjungan Lab Ralan Per Bulan","[D]Stok Keluar Medis","[N]Kunjungan Rad Ralan Per Bulan","[H]Detail JM Dokter 2","[L]Pengaduan/Chat","[N]Kunjungan Lab Ralan Per Tanggal",
                "[N]Kunjungan Rad Ralan Per Tanggal","[I]Sensus Harian Ralan","[D]Metode Racik","[H]Pembayaran Per Akun Bayar","[D]Pengguna Obat/Alkes/BHP Resep","[D]Rekap Penerimaan Obat & BHP",
                "[C]Master Berkas Pegawai","[C]Berkas Kepegawaian","[C]Riwayat Jabatan","[C]Riwayat Pendidikan","[C]Riwayat Naik Gaji","[C]Kegiatan Ilmiah & Pelatihan","[C]Riwayat Penghargaan",
                "[C]Riwayat Penelitian","[E]Penerimaan Barang Non Medis","[J]Bayar Pesan Non Medis","[J]Hutang Barang Non Medis","[E]Rekap Penerimaan Non Medis","[I]Insiden Keselamatan",
                "[L]Insiden Keselamatan Pasien","[N]Kejadian IKP Per Tahun","[N]Kejadian IKP Per Bulan","[N]Kejadian IKP Per Tanggal","[D]Riwayat Batch","[N]Kejadian IKP Per Jenis",
                "[N]Kejadian IKP Per Dampak","[H]Piutang Per Akun Piutang","[N]Registrasi Per Agama","[N]Registrasi Per Umur","[L]Suku/Bangsa Pasien","[L]Bahasa Pasien","[L]Golongan TNI",
                "[L]Satuan TNI","[L]Jabatan TNI","[L]Pangkat TNI","[L]Golongan POLRI","[L]Satuan POLRI","[L]Jabatan POLRI","[L]Pangkat POLRI","[L]Cacat Fisik","[N]Registrasi Per Suku/Bangsa",
                "[N]Registrasi Per Bahasa","[A]Jadwal Operasi","[K]Mapping Poli VClaim","[N]Registrasi Per Cacat Fisik","[F]Barang CSSD","[O]Surat Kontrol","[A]Booking Registrasi",
                "[K]Referensi Propinsi VClaim","[K]Referensi Kabupaten VClaim","[K]Referensi Kecamatan VClaim","[K]Referensi Dokter DPJP VClaim","[K]Riwayat Rujukan RS di VClaim",
                "[K]Tanggal Rujukan di VClaim","[A]Permintaan Lab","[A]Permintaan Radiologi","[O]Indeks Surat","[O]Map Surat","[O]Almari Surat","[O]Rak Surat","[O]Ruang Surat",
                "[O]Klasifikasi Surat","[O]Status Surat","[O]Sifat Surat","[O]Stts Balas Surat","[O]Surat Masuk","[K]Referensi Dokter PCare","[K]Referensi Poli PCare",
                "[K]Referensi Provider PCare","[K]Referensi Stts Pulang PCare","[K]Referensi Spesialis PCare","[K]Referensi Subspesialis PCare","[K]Referensi Sarana PCare",
                "[K]Referensi Khusus PCare","[K]Referensi Obat PCare","[K]Referensi Tindakan PCare","[K]Faskes Subspesialis PCare","[K]Faskes Alih Rawat PCare",
                "[K]Faskes Thalasemia & Hemofili PCare","[K]Mapping Obat PCare","[K]Tarif Ralan RS & PCare","[K]Club Prolanis PCare","[K]Mapping Poli PCare",
                "[K]Kegiatan Kelompok PCare","[K]Tarif Ranap RS & PCare","[K]Peserta Keg Kelompok PCare","[D]Sirkulasi Obat, Alkes & BHP 3","[K]Data Pendafataran PCare",
                "[K]Mapping Dokter PCare","[I]Ranap Per Ruang","[I]Penyakit Ranap Per Cara Bayar","[I]Anggota TNI Dirawat","[S]Set Input Parsial",
                "[I]Lama Pelayanan Radiologi","[I]Lama Pelayanan Lab","[K]Cek Nomor SEP","[A]Catatan Dokter","[O]Surat Keluar","[D]Kegiatan Farmasi",
                "[E]Stok Opname Non Medis","[E]Sirkulasi Non Medis","[I]Rekap Lab Per Tahun","[I]Perujuk Lab Per Tahun","[I]Rekap Radiologi Per Tahun",
                "[I]Perujuk Radiologi Per Tahun","[I]Rekap Bulanan Porsi Diet","[I]Rekap Bulanan Macam Diet","[H]Payment Point 2","[H]Pembayaran Per Akun Bayar 2",
                "[H]Hapus Nota Salah","[A]Asesmen Awal Rawat Inap","[I]HAIs Per Kamar/Bangsal","[D]PPN Obat","[J]Saldo Akun Per Bulan","[S]Display Antrian Apotek",
                "[K]Referensi Faskes Sisrute","[K]Referensi Alasan Rujuk Sisrute","[K]Referensi Diagnosa Sisrute","[K]Rujukan Masuk Sisrute","[K]Rujukan Keluar Sisrute",
                "[K]Cek SKDP VClaim","[D]Data Batch","[I]Kunjungan Lab Ralan","[I]Kunjungan Lab Ranap","[I]Kunjungan Radiologi Ralan","[I]Kunjungan Radiologi Ranap",
                "[K]Pemberian Obat PCare","[K]Pemberian Tindakan PCare","[H]Pembayaran Per Akun Bayar 3","[S]Password BPJS","[I]Data TB","[K]Ketersediaan Kamar SIRANAP",
                "[N]Periode Laporan TB","[N]Rujukan TB","[N]Riwayat TB","[N]Tipe Diagnosis TB","[N]Status HIV TB","[N]Skoring Anak TB","[N]Konfirmasi Skoring 5 TB",
                "[N]Konfirmasi Skoring 6 TB","[N]Sumber Obat TB","[N]Hasil Akhir Pengobatan TB","[N]Hasil Tes HIV TB","[D]Kadaluarsa Batch","[D]Sisa Stok",
                "[D]Obat Per Resep","[F]Pemakaian Air PDAM","[F]Limbah Padat B3 Medis","[N]Pemakaian Air PDAM Per Tanggal","[N]Pemakaian Air PDAM Per Bulan",
                "[N]Limbah B3 Medis Per Tanggal","[N]Limbah B3 Medis Per Bulan","[F]Limbah Padat Domestik","[N]Limbah Padat Domestik Per Tanggal",
                "[N]Limbah Padat Domestik Per Bulan","[F]Mutu Air Limbah","[F]Pest Control","[P]Ruang Perpustakaan","[P]Kategori Koleksi","[P]Jenis Koleksi",
                "[P]Pengarang/Penulis","[P]Penerbit Koleksi","[P]Koleksi Perpustakaan","[P]Inventaris Perpustakaan","[P]Pengaturan Peminjaman","[P]Denda Perpustakaan",
                "[P]Anggota Perpustakaan","[P]Peminjaman Koleksi Perpustakaan","[P]Bayar Denda Perpustakaan","[P]Data Koleksi Ebook","[C]Jenis Cidera K3",
                "[C]Penyebab Kecelakaan K3","[C]Jenis Luka K3","[C]Lokasi Kejadian K3","[C]Dampak Cidera K3","[C]Jenis Pekerjaan K3","[C]Bagian Tubuh K3",
                "[C]Peristiwa K3","[N]K3 Per Tahun","[N]K3 Per Bulan","[N]K3 Per Tanggal","[N]K3 Per Jenis Cidera","[N]K3 Per Penyebab Kecelakaan","[N]K3 Per Jenis Luka",
                "[N]K3 Per Lokasi Kejadian","[N]K3 Per Dampak Cidera","[N]K3 Per Jenis Pekerjaan","[N]K3 Per Bagian Tubuh","[C]Jenis Cidera K3 Per Tahun",
                "[C]Penyebab Kecelakaan K3 Per Tahun","[C]Jenis Luka K3 Per Tahun","[C]Lokasi Kejadian K3 Per Tahun","[C]Dampak Cidera K3 Per Tahun",
                "[C]Jenis Pekerjaan K3 Per Tahun","[C]Bagian Tubuh K3 Per Tahun","[A]Skrining Rawat Jalan","[K]Histori Pelayanan BPJS","[I]Rekap Mutasi Berkas",
                "[I]Skrining Pernapasan Ralan Per Tahun","[D]Pengajuan Obat & BHP","[E]Pengajuan Barang Non Medis","[N]Kunjungan Ranap Per Bulan","[N]Kunjungan Ranap Per Tanggal",
                "[N]Kunjungan Ranap Per Ruang","[I]Masuk Ruang Per Tahun","[N]Pegawai Per Jenjang Jabatan","[N]Pegawai Per Bidang/Bagian","[N]Pegawai Per Departemen",
                "[N]Pegawai Per Pendidikan","[N]Pegawai Per Status WP","[N]Pegawai Per Status Kerja","[N]Status Pulang Ranap","[I]KIP Pasien Ranap","[I]KIP Pasien Ralan",
                "[K]Mapping Dokter DPJP VClaim","[L]Data Triase IGD","[L]Master Triase Skala 1","[L]Master Triase Skala 2","[L]Master Triase Skala 3","[L]Master Triase Skala 4",
                "[L]Master Triase Skala 5","[L]Master Triase Pemeriksaan","[L]Master Triase Macam Kasus","[I]Rekap Permintaan Diet","[I]Daftar Pasien Ranap",
                "[I]Daftar Pasien Ranap TNI","[F]Pengajuan Aset/Inventaris","[N]Item Apotek Per Jenis","[N]Item Apotek Per Kategori","[N]Item Apotek Per Golongan",
                "[N]Item Apotek Per Industri Farmasi","[D]10 Obat Terbanyak Poli","[N]Pengajuan Aset Per Urgensi","[N]Pengajuan Aset Per Status",
                "[N]Pengajuan Aset Per Departemen","[F]Rekap Pengajuan Aset Departemen","[N]Pegawai Per Kelompok Jabatan","[N]Pegawai Per Resiko Kerja",
                "[N]Pegawai Per Emergency Index","[N]Jumlah Inventaris Per Ruang","[I]Harian HAIs 2","[N]Jumlah Inventaris Per Jenis","[L]Resume Pasien",
                "[A]Perkiraan Biaya Ranap","[D]Rekap Obat Per Poli","[D]Rekap Obat Per Pasien","[F]Permintaan Perbaikan Inventaris","[N]Pasien HAIs Per Ruang",
                "[N]Pasien HAIs Per Bulan","[N]Laju HAIs VAP Per Ruang","[N]Laju HAIs IAD Per Ruang","[N]Laju HAIs Plebitis Per Ruang","[N]Laju HAIs ISK Per Ruang",
                "[N]Laju HAIs ILO Per Ruang","[N]Laju HAIs HAP Per Ruang","[K]Mapping Poli Inhealth","[K]Mapping Dokter Inhealth","[K]Tarif Ralan Inhealth",
                "[K]Tarif Ranap Inhealth","[K]Tarif Radiologi Inhealth","[K]Tarif Laborat Inhealth","[K]Tarif Operasi Inhealth","[D]Hibah Obat & BHP","[F]Asal Hibah",
                "[L]Asuhan Gizi","[K]Tagihan Inhealth","[D]Sirkulasi Obat, Alkes & BHP 4","[D]Sirkulasi Obat, Alkes & BHP 5","[E]Sirkulasi Non Medis 2",
                "[L]Monitoring Asuhan Gizi","[N]Penerimaan Obat, Alkes & BHP Per Bulan","[I]Rekap Kunjungan","[O]Surat Keterangan Sakit","[L]Penilaian Awal Keperawatan Ralan Umum",
                "[A]Permintaan Diet","[L]Master Masalah Keperawatan","[C]Pengajuan Cuti","[I]Kedatangan Pasien Per Jam","[M]Data Pendonor","[Q]Suplier Toko",
                "[Q]Jenis Barang Toko","[S]Set Harga Toko","[Q]Barang Toko","[J]Penagihan Piutang Pasien","[J]Akun Penagihan Piutang","[Q]Stok Opname Toko",
                "[Q]Riwayat Barang Toko","[Q]Surat Pemesanan Toko","[Q]Pengajuan Barang Toko","[Q]Penerimaan Barang Toko","[Q]Pengadaan Barang Toko","[Q]Hutang Toko",
                "[Q]Bayar Pesan Toko","[Q]Member Toko","[Q]Penjualan Toko","[I]Registrasi Poli Per Tanggal","[Q]Piutang Toko","[Q]Retur Ke Suplier Toko",
                "[E]Retur Ke Suplier Non Medis","[E]Riwayat Barang Non Medis","[K]Pasien Corona","[Q]Pendapatan Harian Toko","[K]Diagnosa Pasien Corona",
                "[K]Perawatan Pasien Corona","[L]Penilaian Awal Keperawatan Gigi","[L]Master Masalah Keperawatan Gigi","[Q]Bayar Piutang Toko","[Q]Piutang Harian Toko",
                "[Q]Penjualan Harian Toko","[A]Deteksi Dini Corona","[L]Penilaian Awal Keperawatan Ralan Kebidanan","[O]Pengumuman E-Pasien","[O]Surat Hamil","[J]Set Tarif Online",
                "[A]Booking Periksa","[Q]Sirkulasi Barang Toko","[Q]Retur Jual Toko","[Q]Retur Jual Piutang","[Q]Sirkulasi Barang Toko 2","[Q]Keuntungan Barang Toko",
                "[R]Ket Pengeluaran Penerima Dankes","[R]Ket Penghasilan Penerima Dankes","[R]Ukuran Rumah Penerima Dankes","[R]Dinding Rumah Penerima Dankes",
                "[R]Lantai Rumah Penerima Dankes","[R]Atap Rumah Penerima Dankes","[R]Kepemilikan Rumah Penerima Dankes","[R]Kamar Mandi Penerima Dankes",
                "[R]Dapur Rumah Penerima Dankes","[R]Kursi Rumah Penerima Dankes","[R]Kategori PHBS Penerima Dankes","[R]Elektronik Penerima Dankes",
                "[R]Ternak Penerima Dankes","[R]Jenis Simpanan Penerima Dankes","[L]Penilaian Awal Keperawatan Ralan Bayi/Anak","[R]Kategori Asnaf Penerima Dankes",
                "[L]Master Masalah Keperawatan Bayi/Anak","[L]Master Imunisasi","[R]Patologis Penerima Dankes","[K]Cek No.Kartu PCare","[O]Surat Bebas Narkoba",
                "[O]Surat Keterangan Covid","[F]Pemakaian Air Tanah","[N]Pemakaian Air Tanah Per Tanggal","[N]Pemakaian Air Tanah Per Bulan",
                "[I]Lama Pelayanan Poli","[L]Hemodialisa","[I]Laporan Tahunan IRJ","[N]Hemodialisa Per Tanggal","[N]Hemodialisa Per Bulan","[N]Hemodialisa Per Tahun",
                "[N]Pasien Meninggal Per Bulan","[F]Perbaikan Inventaris","[O]Surat Cuti Hamil","[D]Permintaan Stok Obat Pasien","[F]Pemeliharaan Inventaris",
                "[L]Klasifikasi Pasien Ranap","[I]Bulanan Klasifikasi Pasien Ranap","[I]Harian Klasifikasi Pasien Ranap","[I]Klasifikasi Pasien Per Ruang",
                "[L]SOAP Perawatan","[J]Klaim Rawat Jalan","[L]Skrining Gizi Lanjut","[I]Lama Penyiapan RM","[I]Dosis Radiologi","[I]Demografi Umur Kunjungan",
                "[S]Jam Diet Pasien","[J]RVP Piutang BPJS","[D]Verifikasi Penerimaan Obat/Alkes/BHP","[E]Verifikasi Penerimaan Non Medis","[A]Periksa Lab PA",
                "[D]Ringkasan Pengajuan Obat & BHP","[D]Ringkasan Pemesanan Obat & BHP","[D]Ringkasan Pengadaan Obat & BHP","[D]Ringkasan Penerimaan Obat & BHP",
                "[D]Ringkasan Hibah Obat & BHP","[D]Ringkasan Penjualan Obat & BHP","[D]Ringkasan Beri Obat & BHP","[D]Ringkasan Piutang Obat & BHP",
                "[D]Ringkasan Stok Keluar Obat & BHP","[D]Ringkasan Retur Suplier Obat & BHP","[D]Ringkasan Retur Pembeli Obat & BHP","[L]Penilaian Awal Keperawatan Ranap Kebidanan",
                "[E]Ringkasan Pengajuan Non Medis","[E]Ringkasan Pemesanan Non Medis","[E]Ringkasan Pengadaan Non Medis","[E]Ringkasan Penerimaan Non Medis",
                "[E]Ringkasan Stok Keluar Non Medis","[E]Ringkasan Retur Suplier Non Medis","[J]Penerimaan/Omset/Kas Masuk","[J]Validasi Penagihan Piutang",
                "[A]Permintaan Rawat Inap","[K]Referensi Diagnosa PRB VClaim","[K]Referensi Obat PRB VClaim","[K]Surat Kontrol VClaim","[D]Penggunaan BHP OK/VK",
                "[O]Surat Keterangan Rawat Inap","[O]Surat Keterangan Sehat","[J]Pendapatan Per Cara Bayar","[K]Host To Host Bank Jateng","[J]Pembayaran Bank Jateng",
                "[K]Surat PRI VClaim","[H]Ringkasan Tindakan","[I]Lama Pelayanan Pasien","[O]Surat Keterangan Sakit Pihak 2","[J]Titip Faktur/Tagihan Obat & BHP",
                "[K]Referensi Pendaftaran Mobile JKN","[K]Batal Pendaftaran Mobile JKN","[I]Lama Operasi","[N]Jumlah Inventaris Per Kategori","[N]Jumlah Inventaris Per Merk",
                "[N]Jumlah Inventaris Per Produsen","[J]Pengembalian Deposit Pasien","[J]Validasi Titip Faktur/Tagihan Obat & BHP","[J]Piutang Obat & BHP Belum Lunas",
                "[K]Integrasi BRI API","[F]Pengadaan Aset/Inventaris","[J]Akun Jenis Aset/Inventaris","[F]Suplier Aset/Inventaris","[F]Penerimaan Aset/Inventaris",
                "[J]Bayar Pesan Aset/Inventaris","[J]Hutang Aset/Inventaris","[F]Hibah Aset/Inventaris","[J]Titip Faktur/Tagihan Non Medis","[J]Validasi Titip Faktur/Tagihan Non Medis",
                "[J]Titip Faktur/Tagihan Aset/Inventaris","[J]Validasi Titip Faktur/Tagihan Aset/Inventaris","[E]Hibah Non Medis","[K]Referensi TACC PCare","[D]Resep Luar",
                "[O]Surat Bebas TBC","[O]Surat Keterangan Buta Warna","[O]Surat Bebas Tato","[O]Surat Kewaspadaan Kesehatan","[N]Porsi Diet Per Tanggal","[N]Porsi Diet Per Bulan",
                "[N]Porsi Diet Per Tahun","[N]Porsi Diet Per Ruang","[L]Penilaian Awal Medis Ralan Umum","[L]Master Masalah Keperawatan Mata","[L]Penilaian Awal Keperawatan Ralan Mata",
                "[L]Penilaian Awal Medis Ranap Umum","[L]Penilaian Awal Medis Ranap Kandungan","[L]Penilaian Awal Medis Ralan Kandungan","[L]Penilaian Awal Medis IGD",
                "[L]Penilaian Awal Medis Ralan Bayi/Anak","[K]Referensi Poli HFIS","[K]Referensi Dokter HFIS","[K]Referensi Jadwal HFIS","[L]Penilaian Awal Fisioterapi",
                "[K]Program PRB di VClaim","[K]Suplesi Jasa Raharja di VClaim","[K]Data Induk Kecelakaan VClaim","[K]Data SEP Internal VClaim","[K]Klaim Jaminan Jasa Raharja VClaim",
                "[K]Pasien Finger Print VClaim","[K]Rujukan Khusus VClaim","[F]Pemeliharaan Gedung","[N]Perbaikan Inventaris Per Tanggal","[N]Perbaikan Inventaris Per Bulan",
                "[N]Perbaikan Inventaris Per Tahun","[N]Perbaikan Inventaris Per Pelaksana & Status","[L]Penilaian MCU","[J]Peminjam Piutang","[J]Piutang Lain-lain",
                "[J]Asuransi/Askes/Jenis Bayar","[C]Audit Kepatuhan APD","[K]Task ID Mobile JKN","[J]Bayar Piutang Lain-lain","[H]Pembayaran Per Akun Bayar 4",
                "[D]Stok Akhir Farmasi Per Tanggal","[L]Riwayat Kamar Pasien","[L]Uji Fungsi/Prosedur KFR","[L]Hapus Berkas Digital Perawatan","[J]Kategori Pengeluaran Harian",
                "[J]Kategori Pemasukan Lain-lain","[H]Pembayaran Per Akun Bayar 5","[S]Ruang Operasi","[D]Telaah Resep & Obat","[H]Jasa Tindakan Pasien","[D]Permintaan Resep Pulang",
                "[H]Rekap JM Dokter","[I]Status Data RM","[A]Ubah Petugas Lab PK","[A]Ubah Petugas Lab PA","[A]Ubah Petugas Radiologi","[A]Gabung Nomor Rawat","[L]Gabungkan Data RM",
                "[D]Ringkasan Biaya Obat Pasien Per Tanggal","[L]Master Masalah Keperawatan IGD","[L]Penilaian Awal Keperawatan IGD","[K]Referensi DPHO Apotek BPJS",
                "[K]Referensi Poli Apotek BPJS","[J]Bayar JM Dokter","[K]Referensi Faskes Apotek BPJS","[K]Referensi Spesialistik Apotek BPJS","[J]Pembayaran BRIVA",
                "[L]Penilaian Awal Keperawatan Ranap Umum","[D]Nilai Penerimaan Vendor Farmasi Per Bulan","[J]Akun Bayar Hutang","[L]Master Rencana Keperawatan",
                "[I]Laporan Tahunan IGD","[D]Obat/Alkes/BHP Tidak Bergerak","[J]Ringkasan Hutang Vendor Farmasi","[E]Nilai Penerimaan Vendor Non Medis Per Bulan",
                "[J]Ringkasan Hutang Vendor Non Medis","[L]Master Rencana Keperawatan Bayi/Anak","[I]Anggota POLRI Dirawat","[I]Daftar Pasien Ranap POLRI","[L]SOAP Ralan Anggota POLRI",
                "[L]SOAP Ranap Anggota POLRI","[I]Laporan Penyakit POLRI","[I]Jumlah Pengunjung Ralan POLRI","[L]Catatan Observasi IGD","[L]Catatan Observasi Ranap",
                "[L]Catatan Observasi Ranap Kebidanan","[L]Catatan Observasi Ranap Post Partum","[L]Penilaian Awal Medis Ralan THT","[L]Penilaian Psikologi",
                "[C]Audit Cuci Tangan Medis","[C]Audit Pembuangan Limbah","[C]Ruang/Unit Audit Kepatuhan","[C]Audit Pembuangan Benda Tajam & Jarum",
                "[C]Audit Penanganan Darah","[C]Audit Pengelolaan Linen Kotor","[C]Audit Penempatan Pasien","[C]Audit Kamar Jenazah","[C]Audit Bundle IADP",
                "[C]Audit Bundle IDO","[C]Audit Fasilitas Kebersihan Tangan","[C]Audit Fasilitas APD","[C]Audit Pembuangan Limbah Cair Infeksius","[C]Audit Sterilisasi Alat",
                "[L]Penilaian Awal Medis Ralan Psikiatri","[O]Persetujuan/Penolakan Tindakan"
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

        for (i = 0; i < 846;i++) {
            TableColumn column = tbUser.getColumnModel().getColumn(i);
            switch (i) {
                case 0:
                    column.setPreferredWidth(130);
                    break;
                case 1:
                    column.setPreferredWidth(180);
                    break;
                case 2:
                    column.setPreferredWidth(100);
                    break;
                case 3:
                    column.setPreferredWidth(130);
                    break;
                case 4:
                    column.setPreferredWidth(55);
                    break;
                case 5:
                    column.setPreferredWidth(88);
                    break;
                case 6:
                    column.setPreferredWidth(55);
                    break;
                case 7:
                    column.setPreferredWidth(95);
                    break;
                case 8:
                    column.setPreferredWidth(63);
                    break;
                case 9:
                    column.setPreferredWidth(53);
                    break;
                case 10:
                    column.setPreferredWidth(73);
                    break;
                case 11:
                    column.setPreferredWidth(95);
                    break;
                case 12:
                    column.setPreferredWidth(84);
                    break;
                case 13:
                    column.setPreferredWidth(103);
                    break;
                case 14:
                    column.setPreferredWidth(66);
                    break;
                case 15:
                    column.setPreferredWidth(100);
                    break;
                case 16:
                    column.setPreferredWidth(100);
                    break;
                case 17:
                    column.setPreferredWidth(135);
                    break;
                case 18:
                    column.setPreferredWidth(94);
                    break;
                case 19:
                    column.setPreferredWidth(110);
                    break;
                case 20:
                    column.setPreferredWidth(82);
                    break;
                case 21:
                    column.setPreferredWidth(93);
                    break;
                case 22:
                    column.setPreferredWidth(82);
                    break;
                case 23:
                    column.setPreferredWidth(107);
                    break;
                case 24:
                    column.setPreferredWidth(81);
                    break;
                case 25:
                    column.setPreferredWidth(95);
                    break;
                case 26:
                    column.setPreferredWidth(95);
                    break;
                case 27:
                    column.setPreferredWidth(135);
                    break;
                case 28:
                    column.setPreferredWidth(110);
                    break;
                case 29:
                    column.setPreferredWidth(103);
                    break;
                case 30:
                    column.setPreferredWidth(108);
                    break;
                case 31:
                    column.setPreferredWidth(100);
                    break;
                case 32:
                    column.setPreferredWidth(92);
                    break;
                case 33:
                    column.setPreferredWidth(136);
                    break;
                case 34:
                    column.setPreferredWidth(98);
                    break;
                case 35:
                    column.setPreferredWidth(105);
                    break;
                case 36:
                    column.setPreferredWidth(87);
                    break;
                case 37:
                    column.setPreferredWidth(114);
                    break;
                case 38:
                    column.setPreferredWidth(127);
                    break;
                case 39:
                    column.setPreferredWidth(110);
                    break;
                case 40:
                    column.setPreferredWidth(170);
                    break;
                case 41:
                    column.setPreferredWidth(170);
                    break;
                case 42:
                    column.setPreferredWidth(163);
                    break;
                case 43:
                    column.setPreferredWidth(153);
                    break;
                case 44:
                    column.setPreferredWidth(101);
                    break;
                case 45:
                    column.setPreferredWidth(115);
                    break;
                case 46:
                    column.setPreferredWidth(178);
                    break;
                case 230:
                    column.setPreferredWidth(125);
                    break;
                case 231:
                    column.setPreferredWidth(100);
                    break;
                case 232:
                    column.setPreferredWidth(165);
                    break;
                case 233:
                    column.setPreferredWidth(133);
                    break;
                case 234:
                    column.setPreferredWidth(133);
                    break;
                case 235:
                    column.setPreferredWidth(73);
                    break;
                case 236:
                    column.setPreferredWidth(80);
                    break;
                case 237:
                    column.setPreferredWidth(82);
                    break;
                case 238:
                    column.setPreferredWidth(75);
                    break;
                case 239:
                    column.setPreferredWidth(155);
                    break;
                case 240:
                    column.setPreferredWidth(90);
                    break;
                case 241:
                    column.setPreferredWidth(135);
                    break;
                case 242:
                    column.setPreferredWidth(80);
                    break;
                case 243:
                    column.setPreferredWidth(96);
                    break;
                case 244:
                    column.setPreferredWidth(120);
                    break;
                case 245:
                    column.setPreferredWidth(98);
                    break;
                case 246:
                    column.setPreferredWidth(133);
                    break;
                case 247:
                    column.setPreferredWidth(149);
                    break;
                case 248:
                    column.setPreferredWidth(165);
                    break;
                case 249:
                    column.setPreferredWidth(167);
                    break;
                case 250:
                    column.setPreferredWidth(147);
                    break;
                case 251:
                    column.setPreferredWidth(139);
                    break;
                case 252:
                    column.setPreferredWidth(110);
                    break;
                case 253:
                    column.setPreferredWidth(107);
                    break;
                case 254:
                    column.setPreferredWidth(142);
                    break;
                case 255:
                    column.setPreferredWidth(155);
                    break;
                case 256:
                    column.setPreferredWidth(120);
                    break;
                case 257:
                    column.setPreferredWidth(120);
                    break;
                case 258:
                    column.setPreferredWidth(116);
                    break;
                case 259:
                    column.setPreferredWidth(116);
                    break;
                case 260:
                    column.setPreferredWidth(113);
                    break;
                case 261:
                    column.setPreferredWidth(128);
                    break;
                case 262:
                    column.setPreferredWidth(125);
                    break;
                case 263:
                    column.setPreferredWidth(134);
                    break;
                case 264:
                    column.setPreferredWidth(130);
                    break;
                case 265:
                    column.setPreferredWidth(143);
                    break;
                case 266:
                    column.setPreferredWidth(142);
                    break;
                case 267:
                    column.setPreferredWidth(140);
                    break;
                case 268:
                    column.setPreferredWidth(144);
                    break;
                case 269:
                    column.setPreferredWidth(157);
                    break;
                case 270:
                    column.setPreferredWidth(160);
                    break;
                case 271:
                    column.setPreferredWidth(150);
                    break;
                case 272:
                    column.setPreferredWidth(165);
                    break;
                case 273:
                    column.setPreferredWidth(123);
                    break;
                case 274:
                    column.setPreferredWidth(154);
                    break;
                case 275:
                    column.setPreferredWidth(170);
                    break;
                case 276:
                    column.setPreferredWidth(186);
                    break;
                case 277:
                    column.setPreferredWidth(186);
                    break;
                case 278:
                    column.setPreferredWidth(159);
                    break;
                case 279:
                    column.setPreferredWidth(112);
                    break;
                case 280:
                    column.setPreferredWidth(175);
                    break;
                case 281:
                    column.setPreferredWidth(176);
                    break;
                case 282:
                    column.setPreferredWidth(94);
                    break;
                case 283:
                    column.setPreferredWidth(156);
                    break;
                case 284:
                    column.setPreferredWidth(134);
                    break;
                case 285:
                    column.setPreferredWidth(166);
                    break;
                case 286:
                    column.setPreferredWidth(164);
                    break;
                case 287:
                    column.setPreferredWidth(163);
                    break;
                case 288:
                    column.setPreferredWidth(196);
                    break;
                case 289:
                    column.setPreferredWidth(192);
                    break;
                case 290:
                    column.setPreferredWidth(131);
                    break;
                case 291:
                    column.setPreferredWidth(146);
                    break;
                case 292:
                    column.setPreferredWidth(162);
                    break;
                case 293:
                    column.setPreferredWidth(134);
                    break;
                case 294:
                    column.setPreferredWidth(154);
                    break;
                case 295:
                    column.setPreferredWidth(168);
                    break;
                case 296:
                    column.setPreferredWidth(158);
                    break;
                case 297:
                    column.setPreferredWidth(166);
                    break;
                case 298:
                    column.setPreferredWidth(80);
                    break;
                case 299:
                    column.setPreferredWidth(81);
                    break;
                case 300:
                    column.setPreferredWidth(158);
                    break;
                case 301:
                    column.setPreferredWidth(186);
                    break;
                case 302:
                    column.setPreferredWidth(170);
                    break;
                case 303:
                    column.setPreferredWidth(127);
                    break;
                case 304:
                    column.setPreferredWidth(150);
                    break;
                case 305:
                    column.setPreferredWidth(170);
                    break;
                case 306:
                    column.setPreferredWidth(105);
                    break;
                case 307:
                    column.setPreferredWidth(172);
                    break;
                case 308:
                    column.setPreferredWidth(108);
                    break;
                case 309:
                    column.setPreferredWidth(100);
                    break;
                case 310:
                    column.setPreferredWidth(182);
                    break;
                case 311:
                    column.setPreferredWidth(185);
                    break;
                case 312:
                    column.setPreferredWidth(116);
                    break;
                case 313:
                    column.setPreferredWidth(85);
                    break;
                case 314:
                    column.setPreferredWidth(158);
                    break;
                case 315:
                    column.setPreferredWidth(180);
                    break;
                case 316:
                    column.setPreferredWidth(168);
                    break;
                case 317:
                    column.setPreferredWidth(132);
                    break;
                case 318:
                    column.setPreferredWidth(120);
                    break;
                case 319:
                    column.setPreferredWidth(102);
                    break;
                case 320:
                    column.setPreferredWidth(114);
                    break;
                case 321:
                    column.setPreferredWidth(104);
                    break;
                case 322:
                    column.setPreferredWidth(150);
                    break;
                case 323:
                    column.setPreferredWidth(127);
                    break;
                case 324:
                    column.setPreferredWidth(108);
                    break;
                case 325:
                    column.setPreferredWidth(165);
                    break;
                case 326:
                    column.setPreferredWidth(130);
                    break;
                case 327:
                    column.setPreferredWidth(143);
                    break;
                case 328:
                    column.setPreferredWidth(160);
                    break;
                case 329:
                    column.setPreferredWidth(116);
                    break;
                case 330:
                    column.setPreferredWidth(152);
                    break;
                case 331:
                    column.setPreferredWidth(132);
                    break;
                case 332:
                    column.setPreferredWidth(128);
                    break;
                case 333:
                    column.setPreferredWidth(141);
                    break;
                case 334:
                    column.setPreferredWidth(90);
                    break;
                case 335:
                    column.setPreferredWidth(127);
                    break;
                case 336:
                    column.setPreferredWidth(140);
                    break;
                case 337:
                    column.setPreferredWidth(142);
                    break;
                case 338:
                    column.setPreferredWidth(148);
                    break;
                case 339:
                    column.setPreferredWidth(140);
                    break;
                case 340:
                    column.setPreferredWidth(115);
                    break;
                case 341:
                    column.setPreferredWidth(88);
                    break;
                case 342:
                    column.setPreferredWidth(85);
                    break;
                case 343:
                    column.setPreferredWidth(73);
                    break;
                case 344:
                    column.setPreferredWidth(79);
                    break;
                case 345:
                    column.setPreferredWidth(79);
                    break;
                case 346:
                    column.setPreferredWidth(96);
                    break;
                case 347:
                    column.setPreferredWidth(86);
                    break;
                case 348:
                    column.setPreferredWidth(90);
                    break;
                case 349:
                    column.setPreferredWidth(90);
                    break;
                case 350:
                    column.setPreferredWidth(70);
                    break;
                case 351:
                    column.setPreferredWidth(178);
                    break;
                case 352:
                    column.setPreferredWidth(150);
                    break;
                case 353:
                    column.setPreferredWidth(95);
                    break;
                case 354:
                    column.setPreferredWidth(130);
                    break;
                case 355:
                    column.setPreferredWidth(167);
                    break;
                case 356:
                    column.setPreferredWidth(83);
                    break;
                case 357:
                    column.setPreferredWidth(71);
                    break;
                case 358:
                    column.setPreferredWidth(109);
                    break;
                case 359:
                    column.setPreferredWidth(140);
                    break;
                case 360:
                    column.setPreferredWidth(155);
                    break;
                case 361:
                    column.setPreferredWidth(157);
                    break;
                case 362:
                    column.setPreferredWidth(163);
                    break;
                case 363:
                    column.setPreferredWidth(163);
                    break;
                case 364:
                    column.setPreferredWidth(144);
                    break;
                case 365:
                    column.setPreferredWidth(94);
                    break;
                case 366:
                    column.setPreferredWidth(120);
                    break;
                case 367:
                    column.setPreferredWidth(82);
                    break;
                case 368:
                    column.setPreferredWidth(70);
                    break;
                case 369:
                    column.setPreferredWidth(81);
                    break;
                case 370:
                    column.setPreferredWidth(69);
                    break;
                case 371:
                    column.setPreferredWidth(82);
                    break;
                case 372:
                    column.setPreferredWidth(96);
                    break;
                case 373:
                    column.setPreferredWidth(83);
                    break;
                case 374:
                    column.setPreferredWidth(73);
                    break;
                case 375:
                    column.setPreferredWidth(99);
                    break;
                case 376:
                    column.setPreferredWidth(80);
                    break;
                case 377:
                    column.setPreferredWidth(132);
                    break;
                case 378:
                    column.setPreferredWidth(117);
                    break;
                case 379:
                    column.setPreferredWidth(140);
                    break;
                case 380:
                    column.setPreferredWidth(155);
                    break;
                case 381:
                    column.setPreferredWidth(140);
                    break;
                case 382:
                    column.setPreferredWidth(158);
                    break;
                case 383:
                    column.setPreferredWidth(134);
                    break;
                case 384:
                    column.setPreferredWidth(134);
                    break;
                case 385:
                    column.setPreferredWidth(124);
                    break;
                case 386:
                    column.setPreferredWidth(145);
                    break;
                case 387:
                    column.setPreferredWidth(145);
                    break;
                case 388:
                    column.setPreferredWidth(139);
                    break;
                case 389:
                    column.setPreferredWidth(190);
                    break;
                case 390:
                    column.setPreferredWidth(119);
                    break;
                case 391:
                    column.setPreferredWidth(130);
                    break;
                case 392:
                    column.setPreferredWidth(113);
                    break;
                case 393:
                    column.setPreferredWidth(110);
                    break;
                case 394:
                    column.setPreferredWidth(142);
                    break;
                case 395:
                    column.setPreferredWidth(135);
                    break;
                case 396:
                    column.setPreferredWidth(158);
                    break;
                case 397:
                    column.setPreferredWidth(158);
                    break;
                case 398:
                    column.setPreferredWidth(145);
                    break;
                case 399:
                    column.setPreferredWidth(127);
                    break;
                case 400:
                    column.setPreferredWidth(103);
                    break;
                case 401:
                    column.setPreferredWidth(169);
                    break;
                case 402:
                    column.setPreferredWidth(131);
                    break;
                case 403:
                    column.setPreferredWidth(99);
                    break;
                case 404:
                    column.setPreferredWidth(142);
                    break;
                case 405:
                    column.setPreferredWidth(117);
                    break;
                case 406:
                    column.setPreferredWidth(92);
                    break;
                case 407:
                    column.setPreferredWidth(94);
                    break;
                case 408:
                    column.setPreferredWidth(80);
                    break;
                case 409:
                    column.setPreferredWidth(102);
                    break;
                case 410:
                    column.setPreferredWidth(137);
                    break;
                case 411:
                    column.setPreferredWidth(110);
                    break;
                case 412:
                    column.setPreferredWidth(120);
                    break;
                case 413:
                    column.setPreferredWidth(126);
                    break;
                case 414:
                    column.setPreferredWidth(147);
                    break;
                case 415:
                    column.setPreferredWidth(153);
                    break;
                case 416:
                    column.setPreferredWidth(137);
                    break;
                case 417:
                    column.setPreferredWidth(146);
                    break;
                case 418:
                    column.setPreferredWidth(98);
                    break;
                case 419:
                    column.setPreferredWidth(166);
                    break;
                case 420:
                    column.setPreferredWidth(106);
                    break;
                case 421:
                    column.setPreferredWidth(148);
                    break;
                case 422:
                    column.setPreferredWidth(135);
                    break;
                case 423:
                    column.setPreferredWidth(67);
                    break;
                case 424:
                    column.setPreferredWidth(120);
                    break;
                case 425:
                    column.setPreferredWidth(129);
                    break;
                case 426:
                    column.setPreferredWidth(139);
                    break;
                case 427:
                    column.setPreferredWidth(167);
                    break;
                case 428:
                    column.setPreferredWidth(149);
                    break;
                case 429:
                    column.setPreferredWidth(129);
                    break;
                case 430:
                    column.setPreferredWidth(129);
                    break;
                case 431:
                    column.setPreferredWidth(100);
                    break;
                case 432:
                    column.setPreferredWidth(75);
                    break;
                case 433:
                    column.setPreferredWidth(118);
                    break;
                case 434:
                    column.setPreferredWidth(123);
                    break;
                case 435:
                    column.setPreferredWidth(150);
                    break;
                case 436:
                    column.setPreferredWidth(152);
                    break;
                case 437:
                    column.setPreferredWidth(133);
                    break;
                case 438:
                    column.setPreferredWidth(154);
                    break;
                case 439:
                    column.setPreferredWidth(170);
                    break;
                case 440:
                    column.setPreferredWidth(113);
                    break;
                case 441:
                    column.setPreferredWidth(60);
                    break;
                case 442:
                    column.setPreferredWidth(168);
                    break;
                case 443:
                    column.setPreferredWidth(118);
                    break;
                case 444:
                    column.setPreferredWidth(78);
                    break;
                case 445:
                    column.setPreferredWidth(78);
                    break;
                case 446:
                    column.setPreferredWidth(110);
                    break;
                case 447:
                    column.setPreferredWidth(90);
                    break;
                case 448:
                    column.setPreferredWidth(102);
                    break;
                case 449:
                    column.setPreferredWidth(139);
                    break;
                case 450:
                    column.setPreferredWidth(139);
                    break;
                case 451:
                    column.setPreferredWidth(102);
                    break;
                case 452:
                    column.setPreferredWidth(152);
                    break;
                case 453:
                    column.setPreferredWidth(103);
                    break;
                case 454:
                    column.setPreferredWidth(107);
                    break;
                case 455:
                    column.setPreferredWidth(67);
                    break;
                case 456:
                    column.setPreferredWidth(98);
                    break;
                case 457:
                    column.setPreferredWidth(122);
                    break;
                case 458:
                    column.setPreferredWidth(133);
                    break;
                case 459:
                    column.setPreferredWidth(184);
                    break;
                case 460:
                    column.setPreferredWidth(172);
                    break;
                case 461:
                    column.setPreferredWidth(164);
                    break;
                case 462:
                    column.setPreferredWidth(152);
                    break;
                case 463:
                    column.setPreferredWidth(136);
                    break;
                case 464:
                    column.setPreferredWidth(197);
                    break;
                case 465:
                    column.setPreferredWidth(186);
                    break;
                case 466:
                    column.setPreferredWidth(101);
                    break;
                case 467:
                    column.setPreferredWidth(82);
                    break;
                case 468:
                    column.setPreferredWidth(121);
                    break;
                case 469:
                    column.setPreferredWidth(99);
                    break;
                case 470:
                    column.setPreferredWidth(83);
                    break;
                case 471:
                    column.setPreferredWidth(113);
                    break;
                case 472:
                    column.setPreferredWidth(99);
                    break;
                case 473:
                    column.setPreferredWidth(125);
                    break;
                case 474:
                    column.setPreferredWidth(140);
                    break;
                case 475:
                    column.setPreferredWidth(141);
                    break;
                case 476:
                    column.setPreferredWidth(122);
                    break;
                case 477:
                    column.setPreferredWidth(132);
                    break;
                case 478:
                    column.setPreferredWidth(188);
                    break;
                case 479:
                    column.setPreferredWidth(152);
                    break;
                case 480:
                    column.setPreferredWidth(114);
                    break;
                case 481:
                    column.setPreferredWidth(97);
                    break;
                case 482:
                    column.setPreferredWidth(142);
                    break;
                case 483:
                    column.setPreferredWidth(87);
                    break;
                case 484:
                    column.setPreferredWidth(112);
                    break;
                case 485:
                    column.setPreferredWidth(111);
                    break;
                case 486:
                    column.setPreferredWidth(113);
                    break;
                case 487:
                    column.setPreferredWidth(103);
                    break;
                case 488:
                    column.setPreferredWidth(82);
                    break;
                case 489:
                    column.setPreferredWidth(86);
                    break;
                case 490:
                    column.setPreferredWidth(83);
                    break;
                case 491:
                    column.setPreferredWidth(95);
                    break;
                case 492:
                    column.setPreferredWidth(116);
                    break;
                case 493:
                    column.setPreferredWidth(161);
                    break;
                case 494:
                    column.setPreferredWidth(106);
                    break;
                case 495:
                    column.setPreferredWidth(131);
                    break;
                case 496:
                    column.setPreferredWidth(130);
                    break;
                case 497:
                    column.setPreferredWidth(132);
                    break;
                case 498:
                    column.setPreferredWidth(122);
                    break;
                case 499:
                    column.setPreferredWidth(149);
                    break;
                case 500:
                    column.setPreferredWidth(194);
                    break;
                case 501:
                    column.setPreferredWidth(139);
                    break;
                case 502:
                    column.setPreferredWidth(164);
                    break;
                case 503:
                    column.setPreferredWidth(163);
                    break;
                case 504:
                    column.setPreferredWidth(165);
                    break;
                case 505:
                    column.setPreferredWidth(155);
                    break;
                case 506:
                    column.setPreferredWidth(124);
                    break;
                case 507:
                    column.setPreferredWidth(132);
                    break;
                case 508:
                    column.setPreferredWidth(119);
                    break;
                case 509:
                    column.setPreferredWidth(200);
                    break;
                case 510:
                    column.setPreferredWidth(132);
                    break;
                case 511:
                    column.setPreferredWidth(162);
                    break;
                case 512:
                    column.setPreferredWidth(156);
                    break;
                case 513:
                    column.setPreferredWidth(168);
                    break;
                case 514:
                    column.setPreferredWidth(160);
                    break;
                case 515:
                    column.setPreferredWidth(135);
                    break;
                case 516:
                    column.setPreferredWidth(165);
                    break;
                case 517:
                    column.setPreferredWidth(155);
                    break;
                case 518:
                    column.setPreferredWidth(145);
                    break;
                case 519:
                    column.setPreferredWidth(138);
                    break;
                case 520:
                    column.setPreferredWidth(135);
                    break;
                case 521:
                    column.setPreferredWidth(143);
                    break;
                case 522:
                    column.setPreferredWidth(123);
                    break;
                case 523:
                    column.setPreferredWidth(104);
                    break;
                case 524:
                    column.setPreferredWidth(101);
                    break;
                case 525:
                    column.setPreferredWidth(162);
                    break;
                case 526:
                    column.setPreferredWidth(97);
                    break;
                case 527:
                    column.setPreferredWidth(123);
                    break;
                case 528:
                    column.setPreferredWidth(123);
                    break;
                case 529:
                    column.setPreferredWidth(123);
                    break;
                case 530:
                    column.setPreferredWidth(123);
                    break;
                case 531:
                    column.setPreferredWidth(123);
                    break;
                case 532:
                    column.setPreferredWidth(150);
                    break;
                case 533:
                    column.setPreferredWidth(154);
                    break;
                case 534:
                    column.setPreferredWidth(130);
                    break;
                case 535:
                    column.setPreferredWidth(119);
                    break;
                case 536:
                    column.setPreferredWidth(139);
                    break;
                case 537:
                    column.setPreferredWidth(150);
                    break;
                case 538:
                    column.setPreferredWidth(128);
                    break;
                case 539:
                    column.setPreferredWidth(144);
                    break;
                case 540:
                    column.setPreferredWidth(150);
                    break;
                case 541:
                    column.setPreferredWidth(184);
                    break;
                case 542:
                    column.setPreferredWidth(135);
                    break;
                case 543:
                    column.setPreferredWidth(157);
                    break;
                case 544:
                    column.setPreferredWidth(151);
                    break;
                case 545:
                    column.setPreferredWidth(180);
                    break;
                case 546:
                    column.setPreferredWidth(193);
                    break;
                case 547:
                    column.setPreferredWidth(174);
                    break;
                case 548:
                    column.setPreferredWidth(145);
                    break;
                case 549:
                    column.setPreferredWidth(169);
                    break;
                case 550:
                    column.setPreferredWidth(162);
                    break;
                case 551:
                    column.setPreferredWidth(86);
                    break;
                case 552:
                    column.setPreferredWidth(156);
                    break;
                case 553:
                    column.setPreferredWidth(95);
                    break;
                case 554:
                    column.setPreferredWidth(132);
                    break;
                case 555:
                    column.setPreferredWidth(119);
                    break;
                case 556:
                    column.setPreferredWidth(133);
                    break;
                case 557:
                    column.setPreferredWidth(180);
                    break;
                case 558:
                    column.setPreferredWidth(133);
                    break;
                case 559:
                    column.setPreferredWidth(129);
                    break;
                case 560:
                    column.setPreferredWidth(144);
                    break;
                case 561:
                    column.setPreferredWidth(142);
                    break;
                case 562:
                    column.setPreferredWidth(160);
                    break;
                case 563:
                    column.setPreferredWidth(140);
                    break;
                case 564:
                    column.setPreferredWidth(141);
                    break;
                case 565:
                    column.setPreferredWidth(144);
                    break;
                case 566:
                    column.setPreferredWidth(127);
                    break;
                case 567:
                    column.setPreferredWidth(141);
                    break;
                case 568:
                    column.setPreferredWidth(119);
                    break;
                case 569:
                    column.setPreferredWidth(122);
                    break;
                case 570:
                    column.setPreferredWidth(137);
                    break;
                case 571:
                    column.setPreferredWidth(128);
                    break;
                case 572:
                    column.setPreferredWidth(129);
                    break;
                case 573:
                    column.setPreferredWidth(110);
                    break;
                case 574:
                    column.setPreferredWidth(73);
                    break;
                case 575:
                    column.setPreferredWidth(77);
                    break;
                case 576:
                    column.setPreferredWidth(104);
                    break;
                case 577:
                    column.setPreferredWidth(162);
                    break;
                case 578:
                    column.setPreferredWidth(163);
                    break;
                case 579:
                    column.setPreferredWidth(124);
                    break;
                case 580:
                    column.setPreferredWidth(132);
                    break;
                case 581:
                    column.setPreferredWidth(220);
                    break;
                case 582:
                    column.setPreferredWidth(103);
                    break;
                case 583:
                    column.setPreferredWidth(135);
                    break;
                case 584:
                    column.setPreferredWidth(190);
                    break;
                case 585:
                    column.setPreferredWidth(101);
                    break;
                case 586:
                    column.setPreferredWidth(163);
                    break;
                case 587:
                    column.setPreferredWidth(96);
                    break;
                case 588:
                    column.setPreferredWidth(154);
                    break;
                case 589:
                    column.setPreferredWidth(95);
                    break;
                case 590:
                    column.setPreferredWidth(83);
                    break;
                case 591:
                    column.setPreferredWidth(111);
                    break;
                case 592:
                    column.setPreferredWidth(96);
                    break;
                case 593:
                    column.setPreferredWidth(83);
                    break;
                case 594:
                    column.setPreferredWidth(146);
                    break;
                case 595:
                    column.setPreferredWidth(138);
                    break;
                case 596:
                    column.setPreferredWidth(114);
                    break;
                case 597:
                    column.setPreferredWidth(125);
                    break;
                case 598:
                    column.setPreferredWidth(134);
                    break;
                case 599:
                    column.setPreferredWidth(138);
                    break;
                case 600:
                    column.setPreferredWidth(144);
                    break;
                case 601:
                    column.setPreferredWidth(140);
                    break;
                case 602:
                    column.setPreferredWidth(81);
                    break;
                case 603:
                    column.setPreferredWidth(105);
                    break;
                case 604:
                    column.setPreferredWidth(88);
                    break;
                case 605:
                    column.setPreferredWidth(97);
                    break;
                case 606:
                    column.setPreferredWidth(149);
                    break;
                case 607:
                    column.setPreferredWidth(86);
                    break;
                case 608:
                    column.setPreferredWidth(128);
                    break;
                case 609:
                    column.setPreferredWidth(153);
                    break;
                case 610:
                    column.setPreferredWidth(150);
                    break;
                case 611:
                    column.setPreferredWidth(90);
                    break;
                case 612:
                    column.setPreferredWidth(142);
                    break;
                case 613:
                    column.setPreferredWidth(139);
                    break;
                case 614:
                    column.setPreferredWidth(146);
                    break;
                case 615:
                    column.setPreferredWidth(181);
                    break;
                case 616:
                    column.setPreferredWidth(185);
                    break;
                case 617:
                    column.setPreferredWidth(116);
                    break;
                case 618:
                    column.setPreferredWidth(121);
                    break;
                case 619:
                    column.setPreferredWidth(132);
                    break;
                case 620:
                    column.setPreferredWidth(118);
                    break;
                case 621:
                    column.setPreferredWidth(177);
                    break;
                case 622:
                    column.setPreferredWidth(134);
                    break;
                case 623:
                    column.setPreferredWidth(79);
                    break;
                case 624:
                    column.setPreferredWidth(97);
                    break;
                case 625:
                    column.setPreferredWidth(98);
                    break;
                case 626:
                    column.setPreferredWidth(127);
                    break;
                case 627:
                    column.setPreferredWidth(99);
                    break;
                case 628:
                    column.setPreferredWidth(113);
                    break;
                case 629:
                    column.setPreferredWidth(136);
                    break;
                case 630:
                    column.setPreferredWidth(144);
                    break;
                case 631:
                    column.setPreferredWidth(189);
                    break;
                case 632:
                    column.setPreferredWidth(187);
                    break;
                case 633:
                    column.setPreferredWidth(180);
                    break;
                case 634:
                    column.setPreferredWidth(183);
                    break;
                case 635:
                    column.setPreferredWidth(176);
                    break;
                case 636:
                    column.setPreferredWidth(169);
                    break;
                case 637:
                    column.setPreferredWidth(205);
                    break;
                case 638:
                    column.setPreferredWidth(172);
                    break;
                case 639:
                    column.setPreferredWidth(175);
                    break;
                case 640:
                    column.setPreferredWidth(170);
                    break;
                case 641:
                    column.setPreferredWidth(178);
                    break;
                case 642:
                    column.setPreferredWidth(157);
                    break;
                case 643:
                    column.setPreferredWidth(142);
                    break;
                case 644:
                    column.setPreferredWidth(185);
                    break;
                case 645:
                    column.setPreferredWidth(174);
                    break;
                case 646:
                    column.setPreferredWidth(181);
                    break;
                case 647:
                    column.setPreferredWidth(214);
                    break;
                case 648:
                    column.setPreferredWidth(102);
                    break;
                case 649:
                    column.setPreferredWidth(154);
                    break;
                case 650:
                    column.setPreferredWidth(116);
                    break;
                case 651:
                    column.setPreferredWidth(124);
                    break;
                case 652:
                    column.setPreferredWidth(138);
                    break;
                case 653:
                    column.setPreferredWidth(124);
                    break;
                case 654:
                    column.setPreferredWidth(186);
                    break;
                case 655:
                    column.setPreferredWidth(174);
                    break;
                case 656:
                    column.setPreferredWidth(119);
                    break;
                case 657:
                    column.setPreferredWidth(80);
                    break;
                case 658:
                    column.setPreferredWidth(122);
                    break;
                case 659:
                    column.setPreferredWidth(143);
                    break;
                case 660:
                    column.setPreferredWidth(131);
                    break;
                case 661:
                    column.setPreferredWidth(134);
                    break;
                case 662:
                    column.setPreferredWidth(156);
                    break;
                case 663:
                    column.setPreferredWidth(121);
                    break;
                case 664:
                    column.setPreferredWidth(103);
                    break;
                case 665:
                    column.setPreferredWidth(164);
                    break;
                case 666:
                    column.setPreferredWidth(140);
                    break;
                case 667:
                    column.setPreferredWidth(137);
                    break;
                case 668:
                    column.setPreferredWidth(179);
                    break;
                case 669:
                    column.setPreferredWidth(172);
                    break;
                case 670:
                    column.setPreferredWidth(156);
                    break;
                case 671:
                    column.setPreferredWidth(102);
                    break;
                case 672:
                    column.setPreferredWidth(110);
                    break;
                case 673:
                    column.setPreferredWidth(112);
                    break;
                case 674:
                    column.setPreferredWidth(116);
                    break;
                case 675:
                    column.setPreferredWidth(93);
                    break;
                case 676:
                    column.setPreferredWidth(153);
                    break;
                case 677:
                    column.setPreferredWidth(98);
                    break;
                case 678:
                    column.setPreferredWidth(107);
                    break;
                case 679:
                    column.setPreferredWidth(209);
                    break;
                case 680:
                    column.setPreferredWidth(181);
                    break;
                case 681:
                    column.setPreferredWidth(94);
                    break;
                case 682:
                    column.setPreferredWidth(185);
                    break;
                case 683:
                    column.setPreferredWidth(190);
                    break;
                case 684:
                    column.setPreferredWidth(188);
                    break;
                case 685:
                    column.setPreferredWidth(192);
                    break;
                case 686:
                    column.setPreferredWidth(162);
                    break;
                case 687:
                    column.setPreferredWidth(182);
                    break;
                case 688:
                    column.setPreferredWidth(153);
                    break;
                case 689:
                    column.setPreferredWidth(171);
                    break;
                case 690:
                    column.setPreferredWidth(189);
                    break;
                case 691:
                    column.setPreferredWidth(198);
                    break;
                case 692:
                    column.setPreferredWidth(203);
                    break;
                case 693:
                    column.setPreferredWidth(180);
                    break;
                case 694:
                    column.setPreferredWidth(178);
                    break;
                case 695:
                    column.setPreferredWidth(183);
                    break;
                case 696:
                    column.setPreferredWidth(181);
                    break;
                case 697:
                    column.setPreferredWidth(185);
                    break;
                case 698:
                    column.setPreferredWidth(182);
                    break;
                case 699:
                    column.setPreferredWidth(191);
                    break;
                case 700:
                    column.setPreferredWidth(168);
                    break;
                case 701:
                    column.setPreferredWidth(152);
                    break;
                case 702:
                    column.setPreferredWidth(137);
                    break;
                case 703:
                    column.setPreferredWidth(176);
                    break;
                case 704:
                    column.setPreferredWidth(155);
                    break;
                case 705:
                    column.setPreferredWidth(123);
                    break;
                case 706:
                    column.setPreferredWidth(138);
                    break;
                case 707:
                    column.setPreferredWidth(167);
                    break;
                case 708:
                    column.setPreferredWidth(139);
                    break;
                case 709:
                    column.setPreferredWidth(153);
                    break;
                case 710:
                    column.setPreferredWidth(145);
                    break;
                case 711:
                    column.setPreferredWidth(142);
                    break;
                case 712:
                    column.setPreferredWidth(105);
                    break;
                case 713:
                    column.setPreferredWidth(119);
                    break;
                case 714:
                    column.setPreferredWidth(133);
                    break;
                case 715:
                    column.setPreferredWidth(173);
                    break;
                case 716:
                    column.setPreferredWidth(178);
                    break;
                case 717:
                    column.setPreferredWidth(187);
                    break;
                case 718:
                    column.setPreferredWidth(165);
                    break;
                case 719:
                    column.setPreferredWidth(85);
                    break;
                case 720:
                    column.setPreferredWidth(171);
                    break;
                case 721:
                    column.setPreferredWidth(154);
                    break;
                case 722:
                    column.setPreferredWidth(175);
                    break;
                case 723:
                    column.setPreferredWidth(163);
                    break;
                case 724:
                    column.setPreferredWidth(220);
                    break;
                case 725:
                    column.setPreferredWidth(180);
                    break;
                case 726:
                    column.setPreferredWidth(105);
                    break;
                case 727:
                    column.setPreferredWidth(153);
                    break;
                case 728:
                    column.setPreferredWidth(150);
                    break;
                case 729:
                    column.setPreferredWidth(134);
                    break;
                case 730:
                    column.setPreferredWidth(157);
                    break;
                case 731:
                    column.setPreferredWidth(157);
                    break;
                case 732:
                    column.setPreferredWidth(133);
                    break;
                case 733:
                    column.setPreferredWidth(127);
                    break;
                case 734:
                    column.setPreferredWidth(172);
                    break;
                case 735:
                    column.setPreferredWidth(214);
                    break;
                case 736:
                    column.setPreferredWidth(197);
                    break;
                case 737:
                    column.setPreferredWidth(239);
                    break;
                case 738:
                    column.setPreferredWidth(103);
                    break;
                case 739:
                    column.setPreferredWidth(130);
                    break;
                case 740:
                    column.setPreferredWidth(76);
                    break;
                case 741:
                    column.setPreferredWidth(102);
                    break;
                case 742:
                    column.setPreferredWidth(168);
                    break;
                case 743:
                    column.setPreferredWidth(107);
                    break;
                case 744:
                    column.setPreferredWidth(172);
                    break;
                case 745:
                    column.setPreferredWidth(129);
                    break;
                case 746:
                    column.setPreferredWidth(118);
                    break;
                case 747:
                    column.setPreferredWidth(121);
                    break;
                case 748:
                    column.setPreferredWidth(122);
                    break;
                case 749:
                    column.setPreferredWidth(189);
                    break;
                case 750:
                    column.setPreferredWidth(190);
                    break;
                case 751:
                    column.setPreferredWidth(217);
                    break;
                case 752:
                    column.setPreferredWidth(192);
                    break;
                case 753:
                    column.setPreferredWidth(214);
                    break;
                case 754:
                    column.setPreferredWidth(211);
                    break;
                case 755:
                    column.setPreferredWidth(145);
                    break;
                case 756:
                    column.setPreferredWidth(205);
                    break;
                case 757:
                    column.setPreferredWidth(115);
                    break;
                case 758:
                    column.setPreferredWidth(129);
                    break;
                case 759:
                    column.setPreferredWidth(131);
                    break;
                case 760:
                    column.setPreferredWidth(147);
                    break;
                case 761:
                    column.setPreferredWidth(135);
                    break;
                case 762:
                    column.setPreferredWidth(173);
                    break;
                case 763:
                    column.setPreferredWidth(170);
                    break;
                case 764:
                    column.setPreferredWidth(145);
                    break;
                case 765:
                    column.setPreferredWidth(196);
                    break;
                case 766:
                    column.setPreferredWidth(151);
                    break;
                case 767:
                    column.setPreferredWidth(135);
                    break;
                case 768:
                    column.setPreferredWidth(128);
                    break;
                case 769:
                    column.setPreferredWidth(183);
                    break;
                case 770:
                    column.setPreferredWidth(171);
                    break;
                case 771:
                    column.setPreferredWidth(174);
                    break;
                case 772:
                    column.setPreferredWidth(237);
                    break;
                case 773:
                    column.setPreferredWidth(90);
                    break;
                case 774:
                    column.setPreferredWidth(108);
                    break;
                case 775:
                    column.setPreferredWidth(102);
                    break;
                case 776:
                    column.setPreferredWidth(153);
                    break;
                case 777:
                    column.setPreferredWidth(126);
                    break;
                case 778:
                    column.setPreferredWidth(113);
                    break;
                case 779:
                    column.setPreferredWidth(132);
                    break;
                case 780:
                    column.setPreferredWidth(167);
                    break;
                case 781:
                    column.setPreferredWidth(174);
                    break;
                case 782:
                    column.setPreferredWidth(127);
                    break;
                case 783:
                    column.setPreferredWidth(137);
                    break;
                case 784:
                    column.setPreferredWidth(175);
                    break;
                case 785:
                    column.setPreferredWidth(160);
                    break;
                case 786:
                    column.setPreferredWidth(164);
                    break;
                case 787:
                    column.setPreferredWidth(167);
                    break;
                case 788:
                    column.setPreferredWidth(92);
                    break;
                case 789:
                    column.setPreferredWidth(125);
                    break;
                case 790:
                    column.setPreferredWidth(126);
                    break;
                case 791:
                    column.setPreferredWidth(147);
                    break;
                case 792:
                    column.setPreferredWidth(103);
                    break;
                case 793:
                    column.setPreferredWidth(94);
                    break;
                case 794:
                    column.setPreferredWidth(125);
                    break;
                case 795:
                    column.setPreferredWidth(125);
                    break;
                case 796:
                    column.setPreferredWidth(138);
                    break;
                case 797:
                    column.setPreferredWidth(129);
                    break;
                case 798:
                    column.setPreferredWidth(119);
                    break;
                case 799:
                    column.setPreferredWidth(224);
                    break;
                case 800:
                    column.setPreferredWidth(185);
                    break;
                case 801:
                    column.setPreferredWidth(181);
                    break;
                case 802:
                    column.setPreferredWidth(163);
                    break;
                case 803:
                    column.setPreferredWidth(153);
                    break;
                case 804:
                    column.setPreferredWidth(98);
                    break;
                case 805:
                    column.setPreferredWidth(168);
                    break;
                case 806:
                    column.setPreferredWidth(189);
                    break;
                case 807:
                    column.setPreferredWidth(114);
                    break;
                case 808:
                    column.setPreferredWidth(228);
                    break;
                case 809:
                    column.setPreferredWidth(234);
                    break;
                case 810:
                    column.setPreferredWidth(112);
                    break;
                case 811:
                    column.setPreferredWidth(165);
                    break;
                case 812:
                    column.setPreferredWidth(125);
                    break;
                case 813:
                    column.setPreferredWidth(175);
                    break;
                case 814:
                    column.setPreferredWidth(188);
                    break;
                case 815:
                    column.setPreferredWidth(244);
                    break;
                case 816:
                    column.setPreferredWidth(199);
                    break;
                case 817:
                    column.setPreferredWidth(218);
                    break;
                case 818:
                    column.setPreferredWidth(133);
                    break;
                case 819:
                    column.setPreferredWidth(152);
                    break;
                case 820:
                    column.setPreferredWidth(155);
                    break;
                case 821:
                    column.setPreferredWidth(159);
                    break;
                case 822:
                    column.setPreferredWidth(136);
                    break;
                case 823:
                    column.setPreferredWidth(179);
                    break;
                case 824:
                    column.setPreferredWidth(132);
                    break;
                case 825:
                    column.setPreferredWidth(145);
                    break;
                case 826:
                    column.setPreferredWidth(198);
                    break;
                case 827:
                    column.setPreferredWidth(207);
                    break;
                case 828:
                    column.setPreferredWidth(177);
                    break;
                case 829:
                    column.setPreferredWidth(111);
                    break;
                case 830:
                    column.setPreferredWidth(142);
                    break;
                case 831:
                    column.setPreferredWidth(153);
                    break;
                case 832:
                    column.setPreferredWidth(162);
                    break;
                case 833:
                    column.setPreferredWidth(224);
                    break;
                case 834:
                    column.setPreferredWidth(143);
                    break;
                case 835:
                    column.setPreferredWidth(170);
                    break;
                case 836:
                    column.setPreferredWidth(147);
                    break;
                case 837:
                    column.setPreferredWidth(125);
                    break;
                case 838:
                    column.setPreferredWidth(112);
                    break;
                case 839:
                    column.setPreferredWidth(106);
                    break;
                case 840:
                    column.setPreferredWidth(189);
                    break;
                case 841:
                    column.setPreferredWidth(117);
                    break;
                case 842:
                    column.setPreferredWidth(227);
                    break;
                case 843:
                    column.setPreferredWidth(123);
                    break;
                case 844:
                    column.setPreferredWidth(197);
                    break;
                case 845:
                    column.setPreferredWidth(183);
                    break;
                default:
                    column.setPreferredWidth(135);
                    break;
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
        
        personal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                tampil();
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCopyHakAkses = new javax.swing.JMenuItem();
        MnSetUser = new javax.swing.JMenuItem();
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

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCopyHakAkses.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCopyHakAkses.setForeground(new java.awt.Color(50, 50, 50));
        MnCopyHakAkses.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCopyHakAkses.setText("Copy Hak Akses");
        MnCopyHakAkses.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCopyHakAkses.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCopyHakAkses.setName("MnCopyHakAkses"); // NOI18N
        MnCopyHakAkses.setPreferredSize(new java.awt.Dimension(170, 26));
        MnCopyHakAkses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCopyHakAksesActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCopyHakAkses);

        MnSetUser.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSetUser.setForeground(new java.awt.Color(50, 50, 50));
        MnSetUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSetUser.setText("Set Personal");
        MnSetUser.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSetUser.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSetUser.setName("MnSetUser"); // NOI18N
        MnSetUser.setPreferredSize(new java.awt.Dimension(170, 26));
        MnSetUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSetUserActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSetUser);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Setup User ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbUser.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbUser.setComponentPopupMenu(jPopupMenu1);
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
        jLabel3.setBounds(5, 12, 94, 23);

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
        TKd.setBounds(102, 12, 112, 23);

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
        jLabel6.setPreferredSize(new java.awt.Dimension(65, 23));
        jLabel6.setRequestFocusEnabled(false);
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(355, 23));
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
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false',"+
                    "'false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false','false'","User")==true){
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
                    "ebook_perpustakaan='"+tbUser.getValueAt(i,480).toString()+"',"+
                    "jenis_cidera_k3rs='"+tbUser.getValueAt(i,481).toString()+"',"+
                    "penyebab_k3rs='"+tbUser.getValueAt(i,482).toString()+"',"+
                    "jenis_luka_k3rs='"+tbUser.getValueAt(i,483).toString()+"',"+
                    "lokasi_kejadian_k3rs='"+tbUser.getValueAt(i,484).toString()+"',"+
                    "dampak_cidera_k3rs='"+tbUser.getValueAt(i,485).toString()+"',"+
                    "jenis_pekerjaan_k3rs='"+tbUser.getValueAt(i,486).toString()+"',"+
                    "bagian_tubuh_k3rs='"+tbUser.getValueAt(i,487).toString()+"',"+
                    "peristiwa_k3rs='"+tbUser.getValueAt(i,488).toString()+"',"+
                    "grafik_k3_pertahun='"+tbUser.getValueAt(i,489).toString()+"',"+
                    "grafik_k3_perbulan='"+tbUser.getValueAt(i,490).toString()+"',"+
                    "grafik_k3_pertanggal='"+tbUser.getValueAt(i,491).toString()+"',"+
                    "grafik_k3_perjeniscidera='"+tbUser.getValueAt(i,492).toString()+"',"+
                    "grafik_k3_perpenyebab='"+tbUser.getValueAt(i,493).toString()+"',"+
                    "grafik_k3_perjenisluka='"+tbUser.getValueAt(i,494).toString()+"',"+
                    "grafik_k3_lokasikejadian='"+tbUser.getValueAt(i,495).toString()+"',"+
                    "grafik_k3_dampakcidera='"+tbUser.getValueAt(i,496).toString()+"',"+
                    "grafik_k3_perjenispekerjaan='"+tbUser.getValueAt(i,497).toString()+"',"+
                    "grafik_k3_perbagiantubuh='"+tbUser.getValueAt(i,498).toString()+"',"+
                    "jenis_cidera_k3rstahun='"+tbUser.getValueAt(i,499).toString()+"',"+
                    "penyebab_k3rstahun='"+tbUser.getValueAt(i,500).toString()+"',"+
                    "jenis_luka_k3rstahun='"+tbUser.getValueAt(i,501).toString()+"',"+
                    "lokasi_kejadian_k3rstahun='"+tbUser.getValueAt(i,502).toString()+"',"+
                    "dampak_cidera_k3rstahun='"+tbUser.getValueAt(i,503).toString()+"',"+
                    "jenis_pekerjaan_k3rstahun='"+tbUser.getValueAt(i,504).toString()+"',"+
                    "bagian_tubuh_k3rstahun='"+tbUser.getValueAt(i,505).toString()+"',"+
                    "sekrining_rawat_jalan='"+tbUser.getValueAt(i,506).toString()+"',"+
                    "bpjs_histori_pelayanan='"+tbUser.getValueAt(i,507).toString()+"',"+
                    "rekap_mutasi_berkas='"+tbUser.getValueAt(i,508).toString()+"',"+
                    "skrining_ralan_pernapasan_pertahun='"+tbUser.getValueAt(i,509).toString()+"',"+
                    "pengajuan_barang_medis='"+tbUser.getValueAt(i,510).toString()+"',"+
                    "pengajuan_barang_nonmedis='"+tbUser.getValueAt(i,511).toString()+"',"+
                    "grafik_kunjungan_ranapbulan='"+tbUser.getValueAt(i,512).toString()+"',"+
                    "grafik_kunjungan_ranaptanggal='"+tbUser.getValueAt(i,513).toString()+"',"+
                    "grafik_kunjungan_ranap_peruang='"+tbUser.getValueAt(i,514).toString()+"',"+
                    "kunjungan_bangsal_pertahun='"+tbUser.getValueAt(i,515).toString()+"',"+
                    "grafik_jenjang_jabatanpegawai='"+tbUser.getValueAt(i,516).toString()+"',"+
                    "grafik_bidangpegawai='"+tbUser.getValueAt(i,517).toString()+"',"+
                    "grafik_departemenpegawai='"+tbUser.getValueAt(i,518).toString()+"',"+
                    "grafik_pendidikanpegawai='"+tbUser.getValueAt(i,519).toString()+"',"+
                    "grafik_sttswppegawai='"+tbUser.getValueAt(i,520).toString()+"',"+
                    "grafik_sttskerjapegawai='"+tbUser.getValueAt(i,521).toString()+"',"+
                    "grafik_sttspulangranap='"+tbUser.getValueAt(i,522).toString()+"',"+
                    "kip_pasien_ranap='"+tbUser.getValueAt(i,523).toString()+"',"+
                    "kip_pasien_ralan='"+tbUser.getValueAt(i,524).toString()+"',"+
                    "bpjs_mapping_dokterdpjp='"+tbUser.getValueAt(i,525).toString()+"',"+
                    "data_triase_igd='"+tbUser.getValueAt(i,526).toString()+"',"+
                    "master_triase_skala1='"+tbUser.getValueAt(i,527).toString()+"',"+
                    "master_triase_skala2='"+tbUser.getValueAt(i,528).toString()+"',"+
                    "master_triase_skala3='"+tbUser.getValueAt(i,529).toString()+"',"+
                    "master_triase_skala4='"+tbUser.getValueAt(i,530).toString()+"',"+
                    "master_triase_skala5='"+tbUser.getValueAt(i,531).toString()+"',"+
                    "master_triase_pemeriksaan='"+tbUser.getValueAt(i,532).toString()+"',"+
                    "master_triase_macamkasus='"+tbUser.getValueAt(i,533).toString()+"',"+
                    "rekap_permintaan_diet='"+tbUser.getValueAt(i,534).toString()+"',"+
                    "daftar_pasien_ranap='"+tbUser.getValueAt(i,535).toString()+"',"+
                    "daftar_pasien_ranaptni='"+tbUser.getValueAt(i,536).toString()+"',"+
                    "pengajuan_asetinventaris='"+tbUser.getValueAt(i,537).toString()+"',"+
                    "item_apotek_jenis='"+tbUser.getValueAt(i,538).toString()+"',"+
                    "item_apotek_kategori='"+tbUser.getValueAt(i,539).toString()+"',"+
                    "item_apotek_golongan='"+tbUser.getValueAt(i,540).toString()+"',"+
                    "item_apotek_industrifarmasi='"+tbUser.getValueAt(i,541).toString()+"',"+
                    "10_obat_terbanyak_poli='"+tbUser.getValueAt(i,542).toString()+"',"+
                    "grafik_pengajuan_aset_urgensi='"+tbUser.getValueAt(i,543).toString()+"',"+
                    "grafik_pengajuan_aset_status='"+tbUser.getValueAt(i,544).toString()+"',"+
                    "grafik_pengajuan_aset_departemen='"+tbUser.getValueAt(i,545).toString()+"',"+
                    "rekap_pengajuan_aset_departemen='"+tbUser.getValueAt(i,546).toString()+"',"+
                    "grafik_kelompok_jabatanpegawai='"+tbUser.getValueAt(i,547).toString()+"',"+
                    "grafik_resiko_kerjapegawai='"+tbUser.getValueAt(i,548).toString()+"',"+
                    "grafik_emergency_indexpegawai='"+tbUser.getValueAt(i,549).toString()+"',"+
                    "grafik_inventaris_ruang='"+tbUser.getValueAt(i,550).toString()+"',"+
                    "harian_HAIs2='"+tbUser.getValueAt(i,551).toString()+"',"+
                    "grafik_inventaris_jenis='"+tbUser.getValueAt(i,552).toString()+"',"+
                    "data_resume_pasien='"+tbUser.getValueAt(i,553).toString()+"',"+
                    "perkiraan_biaya_ranap='"+tbUser.getValueAt(i,554).toString()+"',"+
                    "rekap_obat_poli='"+tbUser.getValueAt(i,555).toString()+"',"+
                    "rekap_obat_pasien='"+tbUser.getValueAt(i,556).toString()+"',"+
                    "permintaan_perbaikan_inventaris='"+tbUser.getValueAt(i,557).toString()+"',"+
                    "grafik_HAIs_pasienbangsal='"+tbUser.getValueAt(i,558).toString()+"',"+
                    "grafik_HAIs_pasienbulan='"+tbUser.getValueAt(i,559).toString()+"',"+
                    "grafik_HAIs_laju_vap='"+tbUser.getValueAt(i,560).toString()+"',"+
                    "grafik_HAIs_laju_iad='"+tbUser.getValueAt(i,561).toString()+"',"+
                    "grafik_HAIs_laju_pleb='"+tbUser.getValueAt(i,562).toString()+"',"+
                    "grafik_HAIs_laju_isk='"+tbUser.getValueAt(i,563).toString()+"',"+
                    "grafik_HAIs_laju_ilo='"+tbUser.getValueAt(i,564).toString()+"',"+
                    "grafik_HAIs_laju_hap='"+tbUser.getValueAt(i,565).toString()+"',"+
                    "inhealth_mapping_poli='"+tbUser.getValueAt(i,566).toString()+"',"+
                    "inhealth_mapping_dokter='"+tbUser.getValueAt(i,567).toString()+"',"+
                    "inhealth_mapping_tindakan_ralan='"+tbUser.getValueAt(i,568).toString()+"',"+
                    "inhealth_mapping_tindakan_ranap='"+tbUser.getValueAt(i,569).toString()+"',"+
                    "inhealth_mapping_tindakan_radiologi='"+tbUser.getValueAt(i,570).toString()+"',"+
                    "inhealth_mapping_tindakan_laborat='"+tbUser.getValueAt(i,571).toString()+"',"+
                    "inhealth_mapping_tindakan_operasi='"+tbUser.getValueAt(i,572).toString()+"',"+
                    "hibah_obat_bhp='"+tbUser.getValueAt(i,573).toString()+"',"+
                    "asal_hibah='"+tbUser.getValueAt(i,574).toString()+"',"+
                    "asuhan_gizi='"+tbUser.getValueAt(i,575).toString()+"',"+
                    "inhealth_kirim_tagihan='"+tbUser.getValueAt(i,576).toString()+"',"+
                    "sirkulasi_obat4='"+tbUser.getValueAt(i,577).toString()+"',"+
                    "sirkulasi_obat5='"+tbUser.getValueAt(i,578).toString()+"',"+
                    "sirkulasi_non_medis2='"+tbUser.getValueAt(i,579).toString()+"',"+
                    "monitoring_asuhan_gizi='"+tbUser.getValueAt(i,580).toString()+"',"+
                    "penerimaan_obat_perbulan='"+tbUser.getValueAt(i,581).toString()+"',"+
                    "rekap_kunjungan='"+tbUser.getValueAt(i,582).toString()+"',"+
                    "surat_sakit='"+tbUser.getValueAt(i,583).toString()+"',"+
                    "penilaian_awal_keperawatan_ralan='"+tbUser.getValueAt(i,584).toString()+"',"+
                    "permintaan_diet='"+tbUser.getValueAt(i,585).toString()+"',"+
                    "master_masalah_keperawatan='"+tbUser.getValueAt(i,586).toString()+"',"+
                    "pengajuan_cuti='"+tbUser.getValueAt(i,587).toString()+"',"+
                    "kedatangan_pasien='"+tbUser.getValueAt(i,588).toString()+"',"+
                    "utd_pendonor='"+tbUser.getValueAt(i,589).toString()+"',"+
                    "toko_suplier='"+tbUser.getValueAt(i,590).toString()+"',"+
                    "toko_jenis='"+tbUser.getValueAt(i,591).toString()+"',"+
                    "toko_set_harga='"+tbUser.getValueAt(i,592).toString()+"',"+
                    "toko_barang='"+tbUser.getValueAt(i,593).toString()+"',"+
                    "penagihan_piutang_pasien='"+tbUser.getValueAt(i,594).toString()+"',"+
                    "akun_penagihan_piutang='"+tbUser.getValueAt(i,595).toString()+"',"+
                    "stok_opname_toko='"+tbUser.getValueAt(i,596).toString()+"',"+
                    "toko_riwayat_barang='"+tbUser.getValueAt(i,597).toString()+"',"+
                    "toko_surat_pemesanan='"+tbUser.getValueAt(i,598).toString()+"',"+
                    "toko_pengajuan_barang='"+tbUser.getValueAt(i,599).toString()+"',"+
                    "toko_penerimaan_barang='"+tbUser.getValueAt(i,600).toString()+"',"+
                    "toko_pengadaan_barang='"+tbUser.getValueAt(i,601).toString()+"',"+
                    "toko_hutang='"+tbUser.getValueAt(i,602).toString()+"',"+
                    "toko_bayar_pemesanan='"+tbUser.getValueAt(i,603).toString()+"',"+
                    "toko_member='"+tbUser.getValueAt(i,604).toString()+"',"+
                    "toko_penjualan='"+tbUser.getValueAt(i,605).toString()+"',"+
                    "registrasi_poli_per_tanggal='"+tbUser.getValueAt(i,606).toString()+"',"+
                    "toko_piutang='"+tbUser.getValueAt(i,607).toString()+"',"+
                    "toko_retur_beli='"+tbUser.getValueAt(i,608).toString()+"',"+
                    "ipsrs_returbeli='"+tbUser.getValueAt(i,609).toString()+"',"+
                    "ipsrs_riwayat_barang='"+tbUser.getValueAt(i,610).toString()+"',"+
                    "pasien_corona='"+tbUser.getValueAt(i,611).toString()+"',"+
                    "toko_pendapatan_harian='"+tbUser.getValueAt(i,612).toString()+"',"+
                    "diagnosa_pasien_corona='"+tbUser.getValueAt(i,613).toString()+"',"+
                    "perawatan_pasien_corona='"+tbUser.getValueAt(i,614).toString()+"',"+
                    "penilaian_awal_keperawatan_gigi='"+tbUser.getValueAt(i,615).toString()+"',"+
                    "master_masalah_keperawatan_gigi='"+tbUser.getValueAt(i,616).toString()+"',"+
                    "toko_bayar_piutang='"+tbUser.getValueAt(i,617).toString()+"',"+
                    "toko_piutang_harian='"+tbUser.getValueAt(i,618).toString()+"',"+
                    "toko_penjualan_harian='"+tbUser.getValueAt(i,619).toString()+"',"+
                    "deteksi_corona='"+tbUser.getValueAt(i,620).toString()+"',"+
                    "penilaian_awal_keperawatan_kebidanan='"+tbUser.getValueAt(i,621).toString()+"',"+
                    "pengumuman_epasien='"+tbUser.getValueAt(i,622).toString()+"',"+
                    "surat_hamil='"+tbUser.getValueAt(i,623).toString()+"',"+
                    "set_tarif_online='"+tbUser.getValueAt(i,624).toString()+"',"+
                    "booking_periksa='"+tbUser.getValueAt(i,625).toString()+"',"+
                    "toko_sirkulasi='"+tbUser.getValueAt(i,626).toString()+"',"+
                    "toko_retur_jual='"+tbUser.getValueAt(i,627).toString()+"',"+
                    "toko_retur_piutang='"+tbUser.getValueAt(i,628).toString()+"',"+
                    "toko_sirkulasi2='"+tbUser.getValueAt(i,629).toString()+"',"+
                    "toko_keuntungan_barang='"+tbUser.getValueAt(i,630).toString()+"',"+
                    "zis_pengeluaran_penerima_dankes='"+tbUser.getValueAt(i,631).toString()+"',"+
                    "zis_penghasilan_penerima_dankes='"+tbUser.getValueAt(i,632).toString()+"',"+
                    "zis_ukuran_rumah_penerima_dankes='"+tbUser.getValueAt(i,633).toString()+"',"+
                    "zis_dinding_rumah_penerima_dankes='"+tbUser.getValueAt(i,634).toString()+"',"+
                    "zis_lantai_rumah_penerima_dankes='"+tbUser.getValueAt(i,635).toString()+"',"+
                    "zis_atap_rumah_penerima_dankes='"+tbUser.getValueAt(i,636).toString()+"',"+
                    "zis_kepemilikan_rumah_penerima_dankes='"+tbUser.getValueAt(i,637).toString()+"',"+
                    "zis_kamar_mandi_penerima_dankes='"+tbUser.getValueAt(i,638).toString()+"',"+
                    "zis_dapur_rumah_penerima_dankes='"+tbUser.getValueAt(i,639).toString()+"',"+
                    "zis_kursi_rumah_penerima_dankes='"+tbUser.getValueAt(i,640).toString()+"',"+
                    "zis_kategori_phbs_penerima_dankes='"+tbUser.getValueAt(i,641).toString()+"',"+
                    "zis_elektronik_penerima_dankes='"+tbUser.getValueAt(i,642).toString()+"',"+
                    "zis_ternak_penerima_dankes='"+tbUser.getValueAt(i,643).toString()+"',"+
                    "zis_jenis_simpanan_penerima_dankes='"+tbUser.getValueAt(i,644).toString()+"',"+
                    "penilaian_awal_keperawatan_anak='"+tbUser.getValueAt(i,645).toString()+"',"+
                    "zis_kategori_asnaf_penerima_dankes='"+tbUser.getValueAt(i,646).toString()+"',"+
                    "master_masalah_keperawatan_anak='"+tbUser.getValueAt(i,647).toString()+"',"+
                    "master_imunisasi='"+tbUser.getValueAt(i,648).toString()+"',"+
                    "zis_patologis_penerima_dankes='"+tbUser.getValueAt(i,649).toString()+"',"+
                    "pcare_cek_kartu='"+tbUser.getValueAt(i,650).toString()+"',"+
                    "surat_bebas_narkoba='"+tbUser.getValueAt(i,651).toString()+"',"+
                    "surat_keterangan_covid='"+tbUser.getValueAt(i,652).toString()+"',"+
                    "pemakaian_air_tanah='"+tbUser.getValueAt(i,653).toString()+"',"+
                    "grafik_air_tanah_pertanggal='"+tbUser.getValueAt(i,654).toString()+"',"+
                    "grafik_air_tanah_perbulan='"+tbUser.getValueAt(i,655).toString()+"',"+
                    "lama_pelayanan_poli='"+tbUser.getValueAt(i,656).toString()+"',"+
                    "hemodialisa='"+tbUser.getValueAt(i,657).toString()+"',"+
                    "laporan_tahunan_irj='"+tbUser.getValueAt(i,658).toString()+"',"+
                    "grafik_harian_hemodialisa='"+tbUser.getValueAt(i,659).toString()+"',"+
                    "grafik_bulanan_hemodialisa='"+tbUser.getValueAt(i,660).toString()+"',"+
                    "grafik_tahunan_hemodialisa='"+tbUser.getValueAt(i,661).toString()+"',"+
                    "grafik_bulanan_meninggal='"+tbUser.getValueAt(i,662).toString()+"',"+
                    "perbaikan_inventaris='"+tbUser.getValueAt(i,663).toString()+"',"+
                    "surat_cuti_hamil='"+tbUser.getValueAt(i,664).toString()+"',"+
                    "permintaan_stok_obat_pasien='"+tbUser.getValueAt(i,665).toString()+"',"+
                    "pemeliharaan_inventaris='"+tbUser.getValueAt(i,666).toString()+"',"+
                    "klasifikasi_pasien_ranap='"+tbUser.getValueAt(i,667).toString()+"',"+
                    "bulanan_klasifikasi_pasien_ranap='"+tbUser.getValueAt(i,668).toString()+"',"+
                    "harian_klasifikasi_pasien_ranap='"+tbUser.getValueAt(i,669).toString()+"',"+
                    "klasifikasi_pasien_perbangsal='"+tbUser.getValueAt(i,670).toString()+"',"+
                    "soap_perawatan='"+tbUser.getValueAt(i,671).toString()+"',"+
                    "klaim_rawat_jalan='"+tbUser.getValueAt(i,672).toString()+"',"+
                    "skrining_gizi='"+tbUser.getValueAt(i,673).toString()+"',"+
                    "lama_penyiapan_rm='"+tbUser.getValueAt(i,674).toString()+"',"+
                    "dosis_radiologi='"+tbUser.getValueAt(i,675).toString()+"',"+
                    "demografi_umur_kunjungan='"+tbUser.getValueAt(i,676).toString()+"',"+
                    "jam_diet_pasien='"+tbUser.getValueAt(i,677).toString()+"',"+
                    "rvu_bpjs='"+tbUser.getValueAt(i,678).toString()+"',"+
                    "verifikasi_penerimaan_farmasi='"+tbUser.getValueAt(i,679).toString()+"',"+
                    "verifikasi_penerimaan_logistik='"+tbUser.getValueAt(i,680).toString()+"',"+
                    "pemeriksaan_lab_pa='"+tbUser.getValueAt(i,681).toString()+"',"+
                    "ringkasan_pengajuan_obat='"+tbUser.getValueAt(i,682).toString()+"',"+
                    "ringkasan_pemesanan_obat='"+tbUser.getValueAt(i,683).toString()+"',"+
                    "ringkasan_pengadaan_obat='"+tbUser.getValueAt(i,684).toString()+"',"+
                    "ringkasan_penerimaan_obat='"+tbUser.getValueAt(i,685).toString()+"',"+
                    "ringkasan_hibah_obat='"+tbUser.getValueAt(i,686).toString()+"',"+
                    "ringkasan_penjualan_obat='"+tbUser.getValueAt(i,687).toString()+"',"+
                    "ringkasan_beri_obat='"+tbUser.getValueAt(i,688).toString()+"',"+
                    "ringkasan_piutang_obat='"+tbUser.getValueAt(i,689).toString()+"',"+
                    "ringkasan_stok_keluar_obat='"+tbUser.getValueAt(i,690).toString()+"',"+
                    "ringkasan_retur_suplier_obat='"+tbUser.getValueAt(i,691).toString()+"',"+
                    "ringkasan_retur_pembeli_obat='"+tbUser.getValueAt(i,692).toString()+"',"+
                    "penilaian_awal_keperawatan_ranapkebidanan='"+tbUser.getValueAt(i,693).toString()+"',"+
                    "ringkasan_pengajuan_nonmedis='"+tbUser.getValueAt(i,694).toString()+"',"+
                    "ringkasan_pemesanan_nonmedis='"+tbUser.getValueAt(i,695).toString()+"',"+
                    "ringkasan_pengadaan_nonmedis='"+tbUser.getValueAt(i,696).toString()+"',"+
                    "ringkasan_penerimaan_nonmedis='"+tbUser.getValueAt(i,697).toString()+"',"+
                    "ringkasan_stokkeluar_nonmedis='"+tbUser.getValueAt(i,698).toString()+"',"+
                    "ringkasan_returbeli_nonmedis='"+tbUser.getValueAt(i,699).toString()+"',"+
                    "omset_penerimaan='"+tbUser.getValueAt(i,700).toString()+"',"+
                    "validasi_penagihan_piutang='"+tbUser.getValueAt(i,701).toString()+"',"+
                    "permintaan_ranap='"+tbUser.getValueAt(i,702).toString()+"',"+
                    "bpjs_diagnosa_prb='"+tbUser.getValueAt(i,703).toString()+"',"+
                    "bpjs_obat_prb='"+tbUser.getValueAt(i,704).toString()+"',"+
                    "bpjs_surat_kontrol='"+tbUser.getValueAt(i,705).toString()+"',"+
                    "penggunaan_bhp_ok='"+tbUser.getValueAt(i,706).toString()+"',"+
                    "surat_keterangan_rawat_inap='"+tbUser.getValueAt(i,707).toString()+"',"+
                    "surat_keterangan_sehat='"+tbUser.getValueAt(i,708).toString()+"',"+
                    "pendapatan_per_carabayar='"+tbUser.getValueAt(i,709).toString()+"',"+
                    "akun_host_to_host_bank_jateng='"+tbUser.getValueAt(i,710).toString()+"',"+
                    "pembayaran_bank_jateng='"+tbUser.getValueAt(i,711).toString()+"',"+
                    "bpjs_surat_pri='"+tbUser.getValueAt(i,712).toString()+"',"+
                    "ringkasan_tindakan='"+tbUser.getValueAt(i,713).toString()+"',"+
                    "lama_pelayanan_pasien='"+tbUser.getValueAt(i,714).toString()+"',"+
                    "surat_sakit_pihak_2='"+tbUser.getValueAt(i,715).toString()+"',"+
                    "tagihan_hutang_obat='"+tbUser.getValueAt(i,716).toString()+"',"+
                    "referensi_mobilejkn_bpjs='"+tbUser.getValueAt(i,717).toString()+"',"+
                    "batal_pendaftaran_mobilejkn_bpjs='"+tbUser.getValueAt(i,718).toString()+"',"+
                    "lama_operasi='"+tbUser.getValueAt(i,719).toString()+"',"+
                    "grafik_inventaris_kategori='"+tbUser.getValueAt(i,720).toString()+"',"+
                    "grafik_inventaris_merk='"+tbUser.getValueAt(i,721).toString()+"',"+
                    "grafik_inventaris_produsen='"+tbUser.getValueAt(i,722).toString()+"',"+
                    "pengembalian_deposit_pasien='"+tbUser.getValueAt(i,723).toString()+"',"+
                    "validasi_tagihan_hutang_obat='"+tbUser.getValueAt(i,724).toString()+"',"+
                    "piutang_obat_belum_lunas='"+tbUser.getValueAt(i,725).toString()+"',"+
                    "integrasi_briapi='"+tbUser.getValueAt(i,726).toString()+"',"+
                    "pengadaan_aset_inventaris='"+tbUser.getValueAt(i,727).toString()+"',"+
                    "akun_aset_inventaris='"+tbUser.getValueAt(i,728).toString()+"',"+
                    "suplier_inventaris='"+tbUser.getValueAt(i,729).toString()+"',"+
                    "penerimaan_aset_inventaris='"+tbUser.getValueAt(i,730).toString()+"',"+
                    "bayar_pemesanan_iventaris='"+tbUser.getValueAt(i,731).toString()+"',"+
                    "hutang_aset_inventaris='"+tbUser.getValueAt(i,732).toString()+"',"+
                    "hibah_aset_inventaris='"+tbUser.getValueAt(i,733).toString()+"',"+
                    "titip_faktur_non_medis='"+tbUser.getValueAt(i,734).toString()+"',"+
                    "validasi_tagihan_non_medis='"+tbUser.getValueAt(i,735).toString()+"',"+
                    "titip_faktur_aset='"+tbUser.getValueAt(i,736).toString()+"',"+
                    "validasi_tagihan_aset='"+tbUser.getValueAt(i,737).toString()+"',"+
                    "hibah_non_medis='"+tbUser.getValueAt(i,738).toString()+"',"+
                    "pcare_alasan_tacc='"+tbUser.getValueAt(i,739).toString()+"',"+
                    "resep_luar='"+tbUser.getValueAt(i,740).toString()+"',"+
                    "surat_bebas_tbc='"+tbUser.getValueAt(i,741).toString()+"',"+
                    "surat_buta_warna='"+tbUser.getValueAt(i,742).toString()+"',"+
                    "surat_bebas_tato='"+tbUser.getValueAt(i,743).toString()+"',"+
                    "surat_kewaspadaan_kesehatan='"+tbUser.getValueAt(i,744).toString()+"',"+
                    "grafik_porsidiet_pertanggal='"+tbUser.getValueAt(i,745).toString()+"',"+
                    "grafik_porsidiet_perbulan='"+tbUser.getValueAt(i,746).toString()+"',"+
                    "grafik_porsidiet_pertahun='"+tbUser.getValueAt(i,747).toString()+"',"+
                    "grafik_porsidiet_perbangsal='"+tbUser.getValueAt(i,748).toString()+"',"+
                    "penilaian_awal_medis_ralan='"+tbUser.getValueAt(i,749).toString()+"',"+
                    "master_masalah_keperawatan_mata='"+tbUser.getValueAt(i,750).toString()+"',"+
                    "penilaian_awal_keperawatan_mata='"+tbUser.getValueAt(i,751).toString()+"',"+
                    "penilaian_awal_medis_ranap='"+tbUser.getValueAt(i,752).toString()+"',"+
                    "penilaian_awal_medis_ranap_kebidanan='"+tbUser.getValueAt(i,753).toString()+"',"+
                    "penilaian_awal_medis_ralan_kebidanan='"+tbUser.getValueAt(i,754).toString()+"',"+
                    "penilaian_awal_medis_igd='"+tbUser.getValueAt(i,755).toString()+"',"+
                    "penilaian_awal_medis_ralan_anak='"+tbUser.getValueAt(i,756).toString()+"',"+
                    "bpjs_referensi_poli_hfis='"+tbUser.getValueAt(i,757).toString()+"',"+
                    "bpjs_referensi_dokter_hfis='"+tbUser.getValueAt(i,758).toString()+"',"+
                    "bpjs_referensi_jadwal_hfis='"+tbUser.getValueAt(i,759).toString()+"',"+
                    "penilaian_fisioterapi='"+tbUser.getValueAt(i,760).toString()+"',"+
                    "bpjs_program_prb='"+tbUser.getValueAt(i,761).toString()+"',"+
                    "bpjs_suplesi_jasaraharja='"+tbUser.getValueAt(i,762).toString()+"',"+
                    "bpjs_data_induk_kecelakaan='"+tbUser.getValueAt(i,763).toString()+"',"+
                    "bpjs_sep_internal='"+tbUser.getValueAt(i,764).toString()+"',"+
                    "bpjs_klaim_jasa_raharja='"+tbUser.getValueAt(i,765).toString()+"',"+
                    "bpjs_daftar_finger_print='"+tbUser.getValueAt(i,766).toString()+"',"+
                    "bpjs_rujukan_khusus='"+tbUser.getValueAt(i,767).toString()+"',"+
                    "pemeliharaan_gedung='"+tbUser.getValueAt(i,768).toString()+"',"+
                    "grafik_perbaikan_inventaris_pertanggal='"+tbUser.getValueAt(i,769).toString()+"',"+
                    "grafik_perbaikan_inventaris_perbulan='"+tbUser.getValueAt(i,770).toString()+"',"+
                    "grafik_perbaikan_inventaris_pertahun='"+tbUser.getValueAt(i,771).toString()+"',"+
                    "grafik_perbaikan_inventaris_perpelaksana_status='"+tbUser.getValueAt(i,772).toString()+"',"+
                    "penilaian_mcu='"+tbUser.getValueAt(i,773).toString()+"',"+
                    "peminjam_piutang='"+tbUser.getValueAt(i,774).toString()+"',"+
                    "piutang_lainlain='"+tbUser.getValueAt(i,775).toString()+"',"+
                    "cara_bayar='"+tbUser.getValueAt(i,776).toString()+"',"+
                    "audit_kepatuhan_apd='"+tbUser.getValueAt(i,777).toString()+"',"+
                    "bpjs_task_id='"+tbUser.getValueAt(i,778).toString()+"',"+
                    "bayar_piutang_lain='"+tbUser.getValueAt(i,779).toString()+"',"+
                    "pembayaran_akun_bayar4='"+tbUser.getValueAt(i,780).toString()+"',"+
                    "stok_akhir_farmasi_pertanggal='"+tbUser.getValueAt(i,781).toString()+"',"+
                    "riwayat_kamar_pasien='"+tbUser.getValueAt(i,782).toString()+"',"+
                    "uji_fungsi_kfr='"+tbUser.getValueAt(i,783).toString()+"',"+
                    "hapus_berkas_digital_perawatan='"+tbUser.getValueAt(i,784).toString()+"',"+
                    "kategori_pengeluaran_harian='"+tbUser.getValueAt(i,785).toString()+"',"+
                    "kategori_pemasukan_lain='"+tbUser.getValueAt(i,786).toString()+"',"+
                    "pembayaran_akun_bayar5='"+tbUser.getValueAt(i,787).toString()+"',"+
                    "ruang_ok='"+tbUser.getValueAt(i,788).toString()+"',"+
                    "telaah_resep='"+tbUser.getValueAt(i,789).toString()+"',"+
                    "jasa_tindakan_pasien='"+tbUser.getValueAt(i,790).toString()+"',"+
                    "permintaan_resep_pulang='"+tbUser.getValueAt(i,791).toString()+"',"+
                    "rekap_jm_dokter='"+tbUser.getValueAt(i,792).toString()+"',"+
                    "status_data_rm='"+tbUser.getValueAt(i,793).toString()+"',"+
                    "ubah_petugas_lab_pk='"+tbUser.getValueAt(i,794).toString()+"',"+
                    "ubah_petugas_lab_pa='"+tbUser.getValueAt(i,795).toString()+"',"+
                    "ubah_petugas_radiologi='"+tbUser.getValueAt(i,796).toString()+"',"+
                    "gabung_norawat='"+tbUser.getValueAt(i,797).toString()+"',"+
                    "gabung_rm='"+tbUser.getValueAt(i,798).toString()+"',"+
                    "ringkasan_biaya_obat_pasien_pertanggal='"+tbUser.getValueAt(i,799).toString()+"',"+
                    "master_masalah_keperawatan_igd='"+tbUser.getValueAt(i,800).toString()+"',"+
                    "penilaian_awal_keperawatan_igd='"+tbUser.getValueAt(i,801).toString()+"',"+
                    "bpjs_referensi_dpho_apotek='"+tbUser.getValueAt(i,802).toString()+"',"+
                    "bpjs_referensi_poli_apotek='"+tbUser.getValueAt(i,803).toString()+"',"+
                    "bayar_jm_dokter='"+tbUser.getValueAt(i,804).toString()+"',"+
                    "bpjs_referensi_faskes_apotek='"+tbUser.getValueAt(i,805).toString()+"',"+
                    "bpjs_referensi_spesialistik_apotek='"+tbUser.getValueAt(i,806).toString()+"',"+
                    "pembayaran_briva='"+tbUser.getValueAt(i,807).toString()+"',"+
                    "penilaian_awal_keperawatan_ranap='"+tbUser.getValueAt(i,808).toString()+"',"+
                    "nilai_penerimaan_vendor_farmasi_perbulan='"+tbUser.getValueAt(i,809).toString()+"',"+
                    "akun_bayar_hutang='"+tbUser.getValueAt(i,810).toString()+"',"+
                    "master_rencana_keperawatan='"+tbUser.getValueAt(i,811).toString()+"',"+
                    "laporan_tahunan_igd='"+tbUser.getValueAt(i,812).toString()+"',"+
                    "obat_bhp_tidakbergerak='"+tbUser.getValueAt(i,813).toString()+"',"+
                    "ringkasan_hutang_vendor_farmasi='"+tbUser.getValueAt(i,814).toString()+"',"+
                    "nilai_penerimaan_vendor_nonmedis_perbulan='"+tbUser.getValueAt(i,815).toString()+"',"+
                    "ringkasan_hutang_vendor_nonmedis='"+tbUser.getValueAt(i,816).toString()+"',"+
                    "master_rencana_keperawatan_anak='"+tbUser.getValueAt(i,817).toString()+"',"+
                    "anggota_polri_dirawat='"+tbUser.getValueAt(i,818).toString()+"',"+
                    "daftar_pasien_ranap_polri='"+tbUser.getValueAt(i,819).toString()+"',"+
                    "soap_ralan_polri='"+tbUser.getValueAt(i,820).toString()+"',"+
                    "soap_ranap_polri='"+tbUser.getValueAt(i,821).toString()+"',"+
                    "laporan_penyakit_polri='"+tbUser.getValueAt(i,822).toString()+"',"+
                    "jumlah_pengunjung_ralan_polri='"+tbUser.getValueAt(i,823).toString()+"',"+
                    "catatan_observasi_igd='"+tbUser.getValueAt(i,824).toString()+"',"+
                    "catatan_observasi_ranap='"+tbUser.getValueAt(i,825).toString()+"',"+
                    "catatan_observasi_ranap_kebidanan='"+tbUser.getValueAt(i,826).toString()+"',"+
                    "catatan_observasi_ranap_postpartum='"+tbUser.getValueAt(i,827).toString()+"',"+
                    "penilaian_awal_medis_ralan_tht='"+tbUser.getValueAt(i,828).toString()+"',"+
                    "penilaian_psikologi='"+tbUser.getValueAt(i,829).toString()+"',"+
                    "audit_cuci_tangan_medis='"+tbUser.getValueAt(i,830).toString()+"',"+
                    "audit_pembuangan_limbah='"+tbUser.getValueAt(i,831).toString()+"',"+
                    "ruang_audit_kepatuhan='"+tbUser.getValueAt(i,832).toString()+"',"+
                    "audit_pembuangan_benda_tajam='"+tbUser.getValueAt(i,833).toString()+"',"+
                    "audit_penanganan_darah='"+tbUser.getValueAt(i,834).toString()+"',"+
                    "audit_pengelolaan_linen_kotor='"+tbUser.getValueAt(i,835).toString()+"',"+
                    "audit_penempatan_pasien='"+tbUser.getValueAt(i,836).toString()+"',"+
                    "audit_kamar_jenazah='"+tbUser.getValueAt(i,837).toString()+"',"+
                    "audit_bundle_iadp='"+tbUser.getValueAt(i,838).toString()+"',"+
                    "audit_bundle_ido='"+tbUser.getValueAt(i,839).toString()+"',"+
                    "audit_fasilitas_kebersihan_tangan='"+tbUser.getValueAt(i,840).toString()+"',"+
                    "audit_fasilitas_apd='"+tbUser.getValueAt(i,841).toString()+"',"+
                    "audit_pembuangan_limbah_cair_infeksius='"+tbUser.getValueAt(i,842).toString()+"',"+
                    "audit_sterilisasi_alat='"+tbUser.getValueAt(i,843).toString()+"',"+
                    "penilaian_awal_medis_ralan_psikiatri='"+tbUser.getValueAt(i,844).toString()+"',"+
                    "persetujuan_penolakan_tindakan='"+tbUser.getValueAt(i,845).toString()+"'");
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
            
            if(evt.getClickCount()==1){
                if(copyhakakses.equals("copy")){
                    if(userdicopy.equals(TKd.getText())){
                        JOptionPane.showMessageDialog(null,"Copy hak akses gagal karena user dicopy dan user tujuan yang dipilih sama..!!");
                        userdicopy="";
                        copyhakakses="";
                        barisdicopy=-1;
                    }else{
                        int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data copy hak aksesnya..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
                        if (reply == JOptionPane.YES_OPTION) {
                            try {
                                i=tbUser.getSelectedRow();
                                if(i!= -1){
                                    Sequel.mengedit("user","id_user=AES_ENCRYPT('"+tbUser.getValueAt(i,0).toString()+"','nur')",
                                        "id_user=AES_ENCRYPT('"+TKd.getText()+"','nur'),"+
                                        "password=AES_ENCRYPT('"+TPass.getText()+"','windi'),"+
                                        "penyakit='"+tbUser.getValueAt(barisdicopy,4).toString()+"', "+
                                        "obat_penyakit='"+tbUser.getValueAt(barisdicopy,5).toString()+"',"+
                                        "dokter='"+tbUser.getValueAt(barisdicopy,6).toString()+"',"+
                                        "jadwal_praktek='"+tbUser.getValueAt(barisdicopy,7).toString()+"',"+
                                        "petugas='"+tbUser.getValueAt(barisdicopy,8).toString()+"',"+
                                        "pasien='"+tbUser.getValueAt(barisdicopy,9).toString()+"',"+
                                        "registrasi='"+tbUser.getValueAt(barisdicopy,10).toString()+"',"+
                                        "tindakan_ralan='"+tbUser.getValueAt(barisdicopy,11).toString()+"',"+
                                        "kamar_inap='"+tbUser.getValueAt(barisdicopy,12).toString()+"',"+
                                        "tindakan_ranap='"+tbUser.getValueAt(barisdicopy,13).toString()+"',"+
                                        "operasi='"+tbUser.getValueAt(barisdicopy,14).toString()+"',"+
                                        "rujukan_keluar='"+tbUser.getValueAt(barisdicopy,15).toString()+"',"+
                                        "rujukan_masuk='"+tbUser.getValueAt(barisdicopy,16).toString()+"',"+
                                        "beri_obat='"+tbUser.getValueAt(barisdicopy,17).toString()+"',"+
                                        "resep_pulang='"+tbUser.getValueAt(barisdicopy,18).toString()+"',"+
                                        "pasien_meninggal='"+tbUser.getValueAt(barisdicopy,19).toString()+"',"+
                                        "diet_pasien='"+tbUser.getValueAt(barisdicopy,20).toString()+"',"+
                                        "kelahiran_bayi='"+tbUser.getValueAt(barisdicopy,21).toString()+"',"+
                                        "periksa_lab='"+tbUser.getValueAt(barisdicopy,22).toString()+"',"+
                                        "periksa_radiologi='"+tbUser.getValueAt(barisdicopy,23).toString()+"',"+
                                        "kasir_ralan='"+tbUser.getValueAt(barisdicopy,24).toString()+"',"+
                                        "deposit_pasien='"+tbUser.getValueAt(barisdicopy,25).toString()+"',"+
                                        "piutang_pasien='"+tbUser.getValueAt(barisdicopy,26).toString()+"',"+
                                        "peminjaman_berkas='"+tbUser.getValueAt(barisdicopy,27).toString()+"',"+
                                        "barcode='"+tbUser.getValueAt(barisdicopy,28).toString()+"',"+
                                        "presensi_harian='"+tbUser.getValueAt(barisdicopy,29).toString()+"',"+
                                        "presensi_bulanan='"+tbUser.getValueAt(barisdicopy,30).toString()+"',"+
                                        "pegawai_admin='"+tbUser.getValueAt(barisdicopy,31).toString()+"',"+
                                        "pegawai_user='"+tbUser.getValueAt(barisdicopy,32).toString()+"',"+
                                        "suplier='"+tbUser.getValueAt(barisdicopy,33).toString()+"',"+
                                        "satuan_barang='"+tbUser.getValueAt(barisdicopy,34).toString()+"',"+
                                        "konversi_satuan='"+tbUser.getValueAt(barisdicopy,35).toString()+"',"+
                                        "jenis_barang='"+tbUser.getValueAt(barisdicopy,36).toString()+"',"+
                                        "obat='"+tbUser.getValueAt(barisdicopy,37).toString()+"',"+
                                        "stok_opname_obat='"+tbUser.getValueAt(barisdicopy,38).toString()+"',"+
                                        "stok_obat_pasien='"+tbUser.getValueAt(barisdicopy,39).toString()+"',"+
                                        "pengadaan_obat='"+tbUser.getValueAt(barisdicopy,40).toString()+"',"+
                                        "pemesanan_obat='"+tbUser.getValueAt(barisdicopy,41).toString()+"',"+
                                        "penjualan_obat='"+tbUser.getValueAt(barisdicopy,42).toString()+"',"+
                                        "piutang_obat='"+tbUser.getValueAt(barisdicopy,43).toString()+"',"+
                                        "retur_ke_suplier='"+tbUser.getValueAt(barisdicopy,44).toString()+"',"+
                                        "retur_dari_pembeli='"+tbUser.getValueAt(barisdicopy,45).toString()+"',"+
                                        "retur_obat_ranap='"+tbUser.getValueAt(barisdicopy,46).toString()+"',"+
                                        "retur_piutang_pasien='"+tbUser.getValueAt(barisdicopy,47).toString()+"',"+
                                        "keuntungan_penjualan='"+tbUser.getValueAt(barisdicopy,48).toString()+"',"+
                                        "keuntungan_beri_obat='"+tbUser.getValueAt(barisdicopy,49).toString()+"',"+
                                        "sirkulasi_obat='"+tbUser.getValueAt(barisdicopy,50).toString()+"',"+
                                        "ipsrs_barang='"+tbUser.getValueAt(barisdicopy,51).toString()+"',"+
                                        "ipsrs_pengadaan_barang='"+tbUser.getValueAt(barisdicopy,52).toString()+"',"+
                                        "ipsrs_stok_keluar='"+tbUser.getValueAt(barisdicopy,53).toString()+"',"+
                                        "ipsrs_rekap_pengadaan='"+tbUser.getValueAt(barisdicopy,54).toString()+"',"+
                                        "ipsrs_rekap_stok_keluar='"+tbUser.getValueAt(barisdicopy,55).toString()+"',"+
                                        "ipsrs_pengeluaran_harian='"+tbUser.getValueAt(barisdicopy,56).toString()+"',"+
                                        "inventaris_jenis='"+tbUser.getValueAt(barisdicopy,57).toString()+"',"+
                                        "inventaris_kategori='"+tbUser.getValueAt(barisdicopy,58).toString()+"',"+
                                        "inventaris_merk='"+tbUser.getValueAt(barisdicopy,59).toString()+"',"+
                                        "inventaris_ruang='"+tbUser.getValueAt(barisdicopy,60).toString()+"',"+
                                        "inventaris_produsen='"+tbUser.getValueAt(barisdicopy,61).toString()+"',"+
                                        "inventaris_koleksi='"+tbUser.getValueAt(barisdicopy,62).toString()+"',"+
                                        "inventaris_inventaris='"+tbUser.getValueAt(barisdicopy,63).toString()+"',"+
                                        "inventaris_sirkulasi='"+tbUser.getValueAt(barisdicopy,64).toString()+"',"+
                                        "parkir_jenis='"+tbUser.getValueAt(barisdicopy,65).toString()+"',"+
                                        "parkir_in='"+tbUser.getValueAt(barisdicopy,66).toString()+"',"+
                                        "parkir_out='"+tbUser.getValueAt(barisdicopy,67).toString()+"',"+
                                        "parkir_rekap_harian='"+tbUser.getValueAt(barisdicopy,68).toString()+"',"+
                                        "parkir_rekap_bulanan='"+tbUser.getValueAt(barisdicopy,69).toString()+"',"+
                                        "informasi_kamar='"+tbUser.getValueAt(barisdicopy,70).toString()+"',"+
                                        "harian_tindakan_poli='"+tbUser.getValueAt(barisdicopy,71).toString()+"',"+
                                        "obat_per_poli='"+tbUser.getValueAt(barisdicopy,72).toString()+"',"+
                                        "obat_per_kamar='"+tbUser.getValueAt(barisdicopy,73).toString()+"',"+
                                        "obat_per_dokter_ralan='"+tbUser.getValueAt(barisdicopy,74).toString()+"',"+
                                        "obat_per_dokter_ranap='"+tbUser.getValueAt(barisdicopy,75).toString()+"',"+
                                        "harian_dokter='"+tbUser.getValueAt(barisdicopy,76).toString()+"',"+
                                        "bulanan_dokter='"+tbUser.getValueAt(barisdicopy,77).toString()+"',"+
                                        "harian_paramedis='"+tbUser.getValueAt(barisdicopy,78).toString()+"',"+
                                        "bulanan_paramedis='"+tbUser.getValueAt(barisdicopy,79).toString()+"',"+
                                        "pembayaran_ralan='"+tbUser.getValueAt(barisdicopy,80).toString()+"',"+
                                        "pembayaran_ranap='"+tbUser.getValueAt(barisdicopy,81).toString()+"',"+
                                        "rekap_pembayaran_ralan='"+tbUser.getValueAt(barisdicopy,82).toString()+"',"+
                                        "rekap_pembayaran_ranap='"+tbUser.getValueAt(barisdicopy,83).toString()+"',"+
                                        "tagihan_masuk='"+tbUser.getValueAt(barisdicopy,84).toString()+"',"+
                                        "tambahan_biaya='"+tbUser.getValueAt(barisdicopy,85).toString()+"',"+
                                        "potongan_biaya='"+tbUser.getValueAt(barisdicopy,86).toString()+"',"+
                                        "resep_obat='"+tbUser.getValueAt(barisdicopy,87).toString()+"',"+
                                        "resume_pasien='"+tbUser.getValueAt(barisdicopy,88).toString()+"',"+
                                        "penyakit_ralan='"+tbUser.getValueAt(barisdicopy,89).toString()+"',"+
                                        "penyakit_ranap='"+tbUser.getValueAt(barisdicopy,90).toString()+"',"+
                                        "kamar='"+tbUser.getValueAt(barisdicopy,91).toString()+"',"+
                                        "tarif_ralan='"+tbUser.getValueAt(barisdicopy,92).toString()+"',"+
                                        "tarif_ranap='"+tbUser.getValueAt(barisdicopy,93).toString()+"',"+
                                        "tarif_lab='"+tbUser.getValueAt(barisdicopy,94).toString()+"',"+
                                        "tarif_radiologi='"+tbUser.getValueAt(barisdicopy,95).toString()+"',"+
                                        "tarif_operasi='"+tbUser.getValueAt(barisdicopy,96).toString()+"',"+
                                        "akun_rekening='"+tbUser.getValueAt(barisdicopy,97).toString()+"',"+
                                        "rekening_tahun='"+tbUser.getValueAt(barisdicopy,98).toString()+"',"+
                                        "posting_jurnal='"+tbUser.getValueAt(barisdicopy,99).toString()+"',"+
                                        "buku_besar='"+tbUser.getValueAt(barisdicopy,100).toString()+"',"+
                                        "cashflow='"+tbUser.getValueAt(barisdicopy,101).toString()+"',"+
                                        "keuangan='"+tbUser.getValueAt(barisdicopy,102).toString()+"',"+
                                        "pengeluaran='"+tbUser.getValueAt(barisdicopy,103).toString()+"',"+
                                        "setup_pjlab='"+tbUser.getValueAt(barisdicopy,104).toString()+"',"+
                                        "setup_otolokasi='"+tbUser.getValueAt(barisdicopy,105).toString()+"',"+
                                        "setup_jam_kamin='"+tbUser.getValueAt(barisdicopy,106).toString()+"',"+
                                        "setup_embalase='"+tbUser.getValueAt(barisdicopy,107).toString()+"',"+
                                        "tracer_login='"+tbUser.getValueAt(barisdicopy,108).toString()+"',"+
                                        "display='"+tbUser.getValueAt(barisdicopy,109).toString()+"',"+
                                        "set_harga_obat='"+tbUser.getValueAt(barisdicopy,110).toString()+"',"+
                                        "set_penggunaan_tarif='"+tbUser.getValueAt(barisdicopy,111).toString()+"',"+
                                        "set_oto_ralan='"+tbUser.getValueAt(barisdicopy,112).toString()+"',"+
                                        "biaya_harian='"+tbUser.getValueAt(barisdicopy,113).toString()+"',"+
                                        "biaya_masuk_sekali='"+tbUser.getValueAt(barisdicopy,114).toString()+"',"+
                                        "set_no_rm='"+tbUser.getValueAt(barisdicopy,115).toString()+"',"+
                                        "billing_ralan='"+tbUser.getValueAt(barisdicopy,116).toString()+"',"+
                                        "billing_ranap='"+tbUser.getValueAt(barisdicopy,117).toString()+"',"+
                                        "jm_ranap_dokter='"+tbUser.getValueAt(barisdicopy,118).toString()+"',"+
                                        "igd='"+tbUser.getValueAt(barisdicopy,119).toString()+"',"+
                                        "barcoderalan='"+tbUser.getValueAt(barisdicopy,120).toString()+"',"+
                                        "barcoderanap='"+tbUser.getValueAt(barisdicopy,121).toString()+"',"+
                                        "set_harga_obat_ralan='"+tbUser.getValueAt(barisdicopy,122).toString()+"',"+
                                        "set_harga_obat_ranap='"+tbUser.getValueAt(barisdicopy,123).toString()+"',"+
                                        "penyakit_pd3i='"+tbUser.getValueAt(barisdicopy,124).toString()+"',"+
                                        "surveilans_pd3i='"+tbUser.getValueAt(barisdicopy,125).toString()+"',"+
                                        "surveilans_ralan='"+tbUser.getValueAt(barisdicopy,126).toString()+"',"+
                                        "diagnosa_pasien='"+tbUser.getValueAt(barisdicopy,127).toString()+"',"+
                                        "surveilans_ranap='"+tbUser.getValueAt(barisdicopy,128).toString()+"',"+
                                        "pny_takmenular_ranap='"+tbUser.getValueAt(barisdicopy,129).toString()+"',"+
                                        "pny_takmenular_ralan='"+tbUser.getValueAt(barisdicopy,130).toString()+"',"+
                                        "kunjungan_ralan='"+tbUser.getValueAt(barisdicopy,131).toString()+"',"+
                                        "rl32='"+tbUser.getValueAt(barisdicopy,132).toString()+"',"+
                                        "rl33='"+tbUser.getValueAt(barisdicopy,133).toString()+"',"+
                                        "rl37='"+tbUser.getValueAt(barisdicopy,134).toString()+"',"+
                                        "rl38='"+tbUser.getValueAt(barisdicopy,135).toString()+"',"+
                                        "harian_tindakan_dokter='"+tbUser.getValueAt(barisdicopy,136).toString()+"',"+
                                        "sms='"+tbUser.getValueAt(barisdicopy,137).toString()+"',"+
                                        "sidikjari='"+tbUser.getValueAt(barisdicopy,138).toString()+"',"+
                                        "jam_masuk='"+tbUser.getValueAt(barisdicopy,139).toString()+"',"+
                                        "jadwal_pegawai='"+tbUser.getValueAt(barisdicopy,140).toString()+"',"+
                                        "parkir_barcode='"+tbUser.getValueAt(barisdicopy,141).toString()+"',"+
                                        "set_nota='"+tbUser.getValueAt(barisdicopy,142).toString()+"',"+
                                        "dpjp_ranap='"+tbUser.getValueAt(barisdicopy,143).toString()+"',"+
                                        "mutasi_barang='"+tbUser.getValueAt(barisdicopy,144).toString()+"',"+
                                        "rl34='"+tbUser.getValueAt(barisdicopy,145).toString()+"',"+
                                        "rl36='"+tbUser.getValueAt(barisdicopy,146).toString()+"',"+
                                        "fee_visit_dokter='"+tbUser.getValueAt(barisdicopy,147).toString()+"',"+
                                        "fee_bacaan_ekg='"+tbUser.getValueAt(barisdicopy,148).toString()+"',"+
                                        "fee_rujukan_rontgen='"+tbUser.getValueAt(barisdicopy,149).toString()+"',"+
                                        "fee_rujukan_ranap='"+tbUser.getValueAt(barisdicopy,150).toString()+"',"+
                                        "fee_ralan='"+tbUser.getValueAt(barisdicopy,151).toString()+"',"+
                                        "akun_bayar='"+tbUser.getValueAt(barisdicopy,152).toString()+"',"+
                                        "bayar_pemesanan_obat='"+tbUser.getValueAt(barisdicopy,153).toString()+"',"+
                                        "obat_per_dokter_peresep='"+tbUser.getValueAt(barisdicopy,154).toString()+"',"+
                                        "ipsrs_jenis_barang='"+tbUser.getValueAt(barisdicopy,155).toString()+"',"+
                                        "pemasukan_lain='"+tbUser.getValueAt(barisdicopy,156).toString()+"',"+
                                        "pengaturan_rekening='"+tbUser.getValueAt(barisdicopy,157).toString()+"',"+
                                        "closing_kasir='"+tbUser.getValueAt(barisdicopy,158).toString()+"',"+
                                        "keterlambatan_presensi='"+tbUser.getValueAt(barisdicopy,159).toString()+"',"+
                                        "set_harga_kamar='"+tbUser.getValueAt(barisdicopy,160).toString()+"',"+
                                        "rekap_per_shift='"+tbUser.getValueAt(barisdicopy,161).toString()+"',"+
                                        "bpjs_cek_nik='"+tbUser.getValueAt(barisdicopy,162).toString()+"',"+
                                        "bpjs_cek_kartu='"+tbUser.getValueAt(barisdicopy,163).toString()+"',"+
                                        "bpjs_cek_riwayat='"+tbUser.getValueAt(barisdicopy,164).toString()+"',"+
                                        "obat_per_cara_bayar='"+tbUser.getValueAt(barisdicopy,165).toString()+"',"+
                                        "kunjungan_ranap='"+tbUser.getValueAt(barisdicopy,166).toString()+"',"+
                                        "bayar_piutang='"+tbUser.getValueAt(barisdicopy,167).toString()+"',"+
                                        "payment_point='"+tbUser.getValueAt(barisdicopy,168).toString()+"',"+
                                        "bpjs_cek_nomor_rujukan='"+tbUser.getValueAt(barisdicopy,169).toString()+"',"+
                                        "icd9='"+tbUser.getValueAt(barisdicopy,170).toString()+"',"+
                                        "darurat_stok='"+tbUser.getValueAt(barisdicopy,171).toString()+"',"+
                                        "retensi_rm='"+tbUser.getValueAt(barisdicopy,172).toString()+"',"+
                                        "temporary_presensi='"+tbUser.getValueAt(barisdicopy,173).toString()+"',"+
                                        "jurnal_harian='"+tbUser.getValueAt(barisdicopy,174).toString()+"',"+
                                        "sirkulasi_obat2='"+tbUser.getValueAt(barisdicopy,175).toString()+"',"+
                                        "edit_registrasi='"+tbUser.getValueAt(barisdicopy,176).toString()+"',"+
                                        "bpjs_referensi_diagnosa='"+tbUser.getValueAt(barisdicopy,177).toString()+"',"+
                                        "bpjs_referensi_poli='"+tbUser.getValueAt(barisdicopy,178).toString()+"',"+
                                        "industrifarmasi='"+tbUser.getValueAt(barisdicopy,179).toString()+"',"+
                                        "harian_js='"+tbUser.getValueAt(barisdicopy,180).toString()+"',"+
                                        "bulanan_js='"+tbUser.getValueAt(barisdicopy,181).toString()+"',"+
                                        "harian_paket_bhp='"+tbUser.getValueAt(barisdicopy,182).toString()+"',"+
                                        "bulanan_paket_bhp='"+tbUser.getValueAt(barisdicopy,183).toString()+"',"+
                                        "piutang_pasien2='"+tbUser.getValueAt(barisdicopy,184).toString()+"',"+
                                        "bpjs_referensi_faskes='"+tbUser.getValueAt(barisdicopy,185).toString()+"',"+
                                        "bpjs_sep='"+tbUser.getValueAt(barisdicopy,186).toString()+"',"+
                                        "pengambilan_utd='"+tbUser.getValueAt(barisdicopy,187).toString()+"',"+
                                        "tarif_utd='"+tbUser.getValueAt(barisdicopy,188).toString()+"',"+
                                        "pengambilan_utd2='"+tbUser.getValueAt(barisdicopy,189).toString()+"',"+
                                        "utd_medis_rusak='"+tbUser.getValueAt(barisdicopy,190).toString()+"',"+
                                        "pengambilan_penunjang_utd='"+tbUser.getValueAt(barisdicopy,191).toString()+"',"+
                                        "pengambilan_penunjang_utd2='"+tbUser.getValueAt(barisdicopy,192).toString()+"',"+
                                        "utd_penunjang_rusak='"+tbUser.getValueAt(barisdicopy,193).toString()+"',"+
                                        "suplier_penunjang='"+tbUser.getValueAt(barisdicopy,194).toString()+"',"+
                                        "utd_donor='"+tbUser.getValueAt(barisdicopy,195).toString()+"',"+
                                        "bpjs_monitoring_klaim='"+tbUser.getValueAt(barisdicopy,196).toString()+"',"+
                                        "utd_cekal_darah='"+tbUser.getValueAt(barisdicopy,197).toString()+"',"+
                                        "utd_komponen_darah='"+tbUser.getValueAt(barisdicopy,198).toString()+"',"+
                                        "utd_stok_darah='"+tbUser.getValueAt(barisdicopy,199).toString()+"',"+
                                        "utd_pemisahan_darah='"+tbUser.getValueAt(barisdicopy,200).toString()+"',"+
                                        "harian_kamar='"+tbUser.getValueAt(barisdicopy,201).toString()+"',"+
                                        "rincian_piutang_pasien='"+tbUser.getValueAt(barisdicopy,202).toString()+"',"+
                                        "keuntungan_beri_obat_nonpiutang='"+tbUser.getValueAt(barisdicopy,203).toString()+"',"+
                                        "reklasifikasi_ralan='"+tbUser.getValueAt(barisdicopy,204).toString()+"',"+
                                        "reklasifikasi_ranap='"+tbUser.getValueAt(barisdicopy,205).toString()+"',"+
                                        "utd_penyerahan_darah='"+tbUser.getValueAt(barisdicopy,206).toString()+"',"+
                                        "hutang_obat='"+tbUser.getValueAt(barisdicopy,207).toString()+"',"+
                                        "riwayat_obat_alkes_bhp='"+tbUser.getValueAt(barisdicopy,208).toString()+"',"+
                                        "sensus_harian_poli='"+tbUser.getValueAt(barisdicopy,209).toString()+"',"+
                                        "rl4a='"+tbUser.getValueAt(barisdicopy,210).toString()+"',"+
                                        "aplicare_referensi_kamar='"+tbUser.getValueAt(barisdicopy,211).toString()+"',"+
                                        "aplicare_ketersediaan_kamar='"+tbUser.getValueAt(barisdicopy,212).toString()+"',"+
                                        "inacbg_klaim_baru_otomatis='"+tbUser.getValueAt(barisdicopy,213).toString()+"',"+
                                        "inacbg_klaim_baru_manual='"+tbUser.getValueAt(barisdicopy,214).toString()+"',"+
                                        "inacbg_coder_nik='"+tbUser.getValueAt(barisdicopy,215).toString()+"',"+
                                        "mutasi_berkas='"+tbUser.getValueAt(barisdicopy,216).toString()+"',"+
                                        "akun_piutang='"+tbUser.getValueAt(barisdicopy,217).toString()+"',"+
                                        "harian_kso='"+tbUser.getValueAt(barisdicopy,218).toString()+"',"+
                                        "bulanan_kso='"+tbUser.getValueAt(barisdicopy,219).toString()+"',"+
                                        "harian_menejemen='"+tbUser.getValueAt(barisdicopy,220).toString()+"',"+
                                        "bulanan_menejemen='"+tbUser.getValueAt(barisdicopy,221).toString()+"',"+
                                        "inhealth_cek_eligibilitas='"+tbUser.getValueAt(barisdicopy,222).toString()+"',"+
                                        "inhealth_referensi_jenpel_ruang_rawat='"+tbUser.getValueAt(barisdicopy,223).toString()+"',"+
                                        "inhealth_referensi_poli='"+tbUser.getValueAt(barisdicopy,224).toString()+"',"+
                                        "inhealth_referensi_faskes='"+tbUser.getValueAt(barisdicopy,225).toString()+"',"+
                                        "inhealth_sjp='"+tbUser.getValueAt(barisdicopy,226).toString()+"',"+
                                        "piutang_ralan='"+tbUser.getValueAt(barisdicopy,227).toString()+"',"+
                                        "piutang_ranap='"+tbUser.getValueAt(barisdicopy,228).toString()+"',"+
                                        "detail_piutang_penjab='"+tbUser.getValueAt(barisdicopy,229).toString()+"',"+
                                        "lama_pelayanan_ralan='"+tbUser.getValueAt(barisdicopy,230).toString()+"',"+
                                        "catatan_pasien='"+tbUser.getValueAt(barisdicopy,231).toString()+"',"+
                                        "rl4b='"+tbUser.getValueAt(barisdicopy,232).toString()+"',"+
                                        "rl4asebab='"+tbUser.getValueAt(barisdicopy,233).toString()+"',"+
                                        "rl4bsebab='"+tbUser.getValueAt(barisdicopy,234).toString()+"',"+
                                        "data_HAIs='"+tbUser.getValueAt(barisdicopy,235).toString()+"',"+
                                        "harian_HAIs='"+tbUser.getValueAt(barisdicopy,236).toString()+"',"+
                                        "bulanan_HAIs='"+tbUser.getValueAt(barisdicopy,237).toString()+"',"+
                                        "hitung_bor='"+tbUser.getValueAt(barisdicopy,238).toString()+"',"+
                                        "perusahaan_pasien='"+tbUser.getValueAt(barisdicopy,239).toString()+"',"+
                                        "resep_dokter='"+tbUser.getValueAt(barisdicopy,240).toString()+"',"+
                                        "lama_pelayanan_apotek='"+tbUser.getValueAt(barisdicopy,241).toString()+"',"+
                                        "hitung_alos='"+tbUser.getValueAt(barisdicopy,242).toString()+"',"+
                                        "detail_tindakan='"+tbUser.getValueAt(barisdicopy,243).toString()+"',"+
                                        "rujukan_poli_internal='"+tbUser.getValueAt(barisdicopy,244).toString()+"',"+
                                        "rekap_poli_anak='"+tbUser.getValueAt(barisdicopy,245).toString()+"',"+
                                        "grafik_kunjungan_poli='"+tbUser.getValueAt(barisdicopy,246).toString()+"',"+
                                        "grafik_kunjungan_perdokter='"+tbUser.getValueAt(barisdicopy,247).toString()+"',"+
                                        "grafik_kunjungan_perpekerjaan='"+tbUser.getValueAt(barisdicopy,248).toString()+"',"+
                                        "grafik_kunjungan_perpendidikan='"+tbUser.getValueAt(barisdicopy,249).toString()+"',"+
                                        "grafik_kunjungan_pertahun='"+tbUser.getValueAt(barisdicopy,250).toString()+"',"+
                                        "berkas_digital_perawatan='"+tbUser.getValueAt(barisdicopy,251).toString()+"',"+
                                        "penyakit_menular_ranap='"+tbUser.getValueAt(barisdicopy,252).toString()+"',"+
                                        "penyakit_menular_ralan='"+tbUser.getValueAt(barisdicopy,253).toString()+"',"+
                                        "grafik_kunjungan_perbulan='"+tbUser.getValueAt(barisdicopy,254).toString()+"',"+
                                        "grafik_kunjungan_pertanggal='"+tbUser.getValueAt(barisdicopy,255).toString()+"',"+
                                        "grafik_kunjungan_demografi='"+tbUser.getValueAt(barisdicopy,256).toString()+"',"+
                                        "grafik_kunjungan_statusdaftartahun='"+tbUser.getValueAt(barisdicopy,257).toString()+"',"+
                                        "grafik_kunjungan_statusdaftartahun2='"+tbUser.getValueAt(barisdicopy,258).toString()+"',"+
                                        "grafik_kunjungan_statusdaftarbulan='"+tbUser.getValueAt(barisdicopy,259).toString()+"',"+
                                        "grafik_kunjungan_statusdaftarbulan2='"+tbUser.getValueAt(barisdicopy,260).toString()+"',"+
                                        "grafik_kunjungan_statusdaftartanggal='"+tbUser.getValueAt(barisdicopy,261).toString()+"',"+
                                        "grafik_kunjungan_statusdaftartanggal2='"+tbUser.getValueAt(barisdicopy,262).toString()+"',"+
                                        "grafik_kunjungan_statusbataltahun='"+tbUser.getValueAt(barisdicopy,263).toString()+"',"+
                                        "grafik_kunjungan_statusbatalbulan='"+tbUser.getValueAt(barisdicopy,264).toString()+"',"+
                                        "pcare_cek_penyakit='"+tbUser.getValueAt(barisdicopy,265).toString()+"',"+
                                        "grafik_kunjungan_statusbataltanggal='"+tbUser.getValueAt(barisdicopy,266).toString()+"',"+
                                        "kategori_barang='"+tbUser.getValueAt(barisdicopy,267).toString()+"',"+
                                        "golongan_barang='"+tbUser.getValueAt(barisdicopy,268).toString()+"',"+
                                        "pemberian_obat_pertanggal='"+tbUser.getValueAt(barisdicopy,269).toString()+"',"+
                                        "penjualan_obat_pertanggal='"+tbUser.getValueAt(barisdicopy,270).toString()+"',"+
                                        "pcare_cek_kesadaran='"+tbUser.getValueAt(barisdicopy,271).toString()+"',"+
                                        "pembatalan_periksa_dokter='"+tbUser.getValueAt(barisdicopy,272).toString()+"',"+
                                        "pembayaran_per_unit='"+tbUser.getValueAt(barisdicopy,273).toString()+"',"+
                                        "rekap_pembayaran_per_unit='"+tbUser.getValueAt(barisdicopy,274).toString()+"',"+
                                        "grafik_kunjungan_percarabayar='"+tbUser.getValueAt(barisdicopy,275).toString()+"',"+
                                        "ipsrs_pengadaan_pertanggal='"+tbUser.getValueAt(barisdicopy,276).toString()+"',"+
                                        "ipsrs_stokkeluar_pertanggal='"+tbUser.getValueAt(barisdicopy,277).toString()+"',"+
                                        "grafik_kunjungan_ranaptahun='"+tbUser.getValueAt(barisdicopy,278).toString()+"',"+
                                        "pcare_cek_rujukan='"+tbUser.getValueAt(barisdicopy,279).toString()+"',"+
                                        "grafik_lab_ralantahun='"+tbUser.getValueAt(barisdicopy,280).toString()+"',"+
                                        "grafik_rad_ralantahun='"+tbUser.getValueAt(barisdicopy,281).toString()+"',"+
                                        "cek_entry_ralan='"+tbUser.getValueAt(barisdicopy,282).toString()+"',"+
                                        "inacbg_klaim_baru_manual2='"+tbUser.getValueAt(barisdicopy,283).toString()+"',"+
                                        "permintaan_medis='"+tbUser.getValueAt(barisdicopy,284).toString()+"',"+
                                        "rekap_permintaan_medis='"+tbUser.getValueAt(barisdicopy,285).toString()+"',"+
                                        "surat_pemesanan_medis='"+tbUser.getValueAt(barisdicopy,286).toString()+"',"+
                                        "permintaan_non_medis='"+tbUser.getValueAt(barisdicopy,287).toString()+"',"+
                                        "rekap_permintaan_non_medis='"+tbUser.getValueAt(barisdicopy,288).toString()+"',"+
                                        "surat_pemesanan_non_medis='"+tbUser.getValueAt(barisdicopy,289).toString()+"',"+
                                        "grafik_per_perujuk='"+tbUser.getValueAt(barisdicopy,290).toString()+"',"+
                                        "bpjs_cek_prosedur='"+tbUser.getValueAt(barisdicopy,291).toString()+"',"+
                                        "bpjs_cek_kelas_rawat='"+tbUser.getValueAt(barisdicopy,292).toString()+"',"+
                                        "bpjs_cek_dokter='"+tbUser.getValueAt(barisdicopy,293).toString()+"',"+
                                        "bpjs_cek_spesialistik='"+tbUser.getValueAt(barisdicopy,294).toString()+"',"+
                                        "bpjs_cek_ruangrawat='"+tbUser.getValueAt(barisdicopy,295).toString()+"',"+
                                        "bpjs_cek_carakeluar='"+tbUser.getValueAt(barisdicopy,296).toString()+"',"+
                                        "bpjs_cek_pasca_pulang='"+tbUser.getValueAt(barisdicopy,297).toString()+"',"+
                                        "detail_tindakan_okvk='"+tbUser.getValueAt(barisdicopy,298).toString()+"',"+
                                        "billing_parsial='"+tbUser.getValueAt(barisdicopy,299).toString()+"',"+
                                        "bpjs_cek_nomor_rujukan_rs='"+tbUser.getValueAt(barisdicopy,300).toString()+"',"+
                                        "bpjs_cek_rujukan_kartu_pcare='"+tbUser.getValueAt(barisdicopy,301).toString()+"',"+
                                        "bpjs_cek_rujukan_kartu_rs='"+tbUser.getValueAt(barisdicopy,302).toString()+"',"+
                                        "akses_depo_obat='"+tbUser.getValueAt(barisdicopy,303).toString()+"',"+
                                        "bpjs_rujukan_keluar='"+tbUser.getValueAt(barisdicopy,304).toString()+"',"+
                                        "grafik_lab_ralanbulan='"+tbUser.getValueAt(barisdicopy,305).toString()+"',"+
                                        "pengeluaran_stok_apotek='"+tbUser.getValueAt(barisdicopy,306).toString()+"',"+
                                        "grafik_rad_ralanbulan='"+tbUser.getValueAt(barisdicopy,307).toString()+"',"+
                                        "detailjmdokter2='"+tbUser.getValueAt(barisdicopy,308).toString()+"',"+
                                        "pengaduan_pasien='"+tbUser.getValueAt(barisdicopy,309).toString()+"',"+
                                        "grafik_lab_ralanhari='"+tbUser.getValueAt(barisdicopy,310).toString()+"',"+
                                        "grafik_rad_ralanhari='"+tbUser.getValueAt(barisdicopy,311).toString()+"',"+
                                        "sensus_harian_ralan='"+tbUser.getValueAt(barisdicopy,312).toString()+"',"+
                                        "metode_racik='"+tbUser.getValueAt(barisdicopy,313).toString()+"',"+
                                        "pembayaran_akun_bayar='"+tbUser.getValueAt(barisdicopy,314).toString()+"',"+
                                        "pengguna_obat_resep='"+tbUser.getValueAt(barisdicopy,315).toString()+"',"+
                                        "rekap_pemesanan='"+tbUser.getValueAt(barisdicopy,316).toString()+"',"+
                                        "master_berkas_pegawai='"+tbUser.getValueAt(barisdicopy,317).toString()+"',"+
                                        "berkas_kepegawaian='"+tbUser.getValueAt(barisdicopy,318).toString()+"',"+
                                        "riwayat_jabatan='"+tbUser.getValueAt(barisdicopy,319).toString()+"',"+
                                        "riwayat_pendidikan='"+tbUser.getValueAt(barisdicopy,320).toString()+"',"+
                                        "riwayat_naik_gaji='"+tbUser.getValueAt(barisdicopy,321).toString()+"',"+
                                        "kegiatan_ilmiah='"+tbUser.getValueAt(barisdicopy,322).toString()+"',"+
                                        "riwayat_penghargaan='"+tbUser.getValueAt(barisdicopy,323).toString()+"',"+
                                        "riwayat_penelitian='"+tbUser.getValueAt(barisdicopy,324).toString()+"',"+
                                        "penerimaan_non_medis='"+tbUser.getValueAt(barisdicopy,325).toString()+"',"+
                                        "bayar_pesan_non_medis='"+tbUser.getValueAt(barisdicopy,326).toString()+"',"+
                                        "hutang_barang_non_medis='"+tbUser.getValueAt(barisdicopy,327).toString()+"',"+
                                        "rekap_pemesanan_non_medis='"+tbUser.getValueAt(barisdicopy,328).toString()+"',"+
                                        "insiden_keselamatan='"+tbUser.getValueAt(barisdicopy,329).toString()+"',"+
                                        "insiden_keselamatan_pasien='"+tbUser.getValueAt(barisdicopy,330).toString()+"',"+
                                        "grafik_ikp_pertahun='"+tbUser.getValueAt(barisdicopy,331).toString()+"',"+
                                        "grafik_ikp_perbulan='"+tbUser.getValueAt(barisdicopy,332).toString()+"',"+
                                        "grafik_ikp_pertanggal='"+tbUser.getValueAt(barisdicopy,333).toString()+"',"+
                                        "riwayat_data_batch='"+tbUser.getValueAt(barisdicopy,334).toString()+"',"+
                                        "grafik_ikp_jenis='"+tbUser.getValueAt(barisdicopy,335).toString()+"',"+
                                        "grafik_ikp_dampak='"+tbUser.getValueAt(barisdicopy,336).toString()+"',"+
                                        "piutang_akun_piutang='"+tbUser.getValueAt(barisdicopy,337).toString()+"',"+
                                        "grafik_kunjungan_per_agama='"+tbUser.getValueAt(barisdicopy,338).toString()+"',"+
                                        "grafik_kunjungan_per_umur='"+tbUser.getValueAt(barisdicopy,339).toString()+"',"+
                                        "suku_bangsa='"+tbUser.getValueAt(barisdicopy,340).toString()+"',"+
                                        "bahasa_pasien='"+tbUser.getValueAt(barisdicopy,341).toString()+"',"+
                                        "golongan_tni='"+tbUser.getValueAt(barisdicopy,342).toString()+"',"+
                                        "satuan_tni='"+tbUser.getValueAt(barisdicopy,343).toString()+"',"+
                                        "jabatan_tni='"+tbUser.getValueAt(barisdicopy,344).toString()+"',"+
                                        "pangkat_tni='"+tbUser.getValueAt(barisdicopy,345).toString()+"',"+
                                        "golongan_polri='"+tbUser.getValueAt(barisdicopy,346).toString()+"',"+
                                        "satuan_polri='"+tbUser.getValueAt(barisdicopy,347).toString()+"',"+
                                        "jabatan_polri='"+tbUser.getValueAt(barisdicopy,348).toString()+"',"+
                                        "pangkat_polri='"+tbUser.getValueAt(barisdicopy,349).toString()+"',"+
                                        "cacat_fisik='"+tbUser.getValueAt(barisdicopy,350).toString()+"',"+
                                        "grafik_kunjungan_suku='"+tbUser.getValueAt(barisdicopy,351).toString()+"',"+
                                        "grafik_kunjungan_bahasa='"+tbUser.getValueAt(barisdicopy,352).toString()+"',"+
                                        "booking_operasi='"+tbUser.getValueAt(barisdicopy,353).toString()+"',"+
                                        "mapping_poli_bpjs='"+tbUser.getValueAt(barisdicopy,354).toString()+"',"+
                                        "grafik_kunjungan_per_cacat='"+tbUser.getValueAt(barisdicopy,355).toString()+"',"+
                                        "barang_cssd='"+tbUser.getValueAt(barisdicopy,356).toString()+"',"+
                                        "skdp_bpjs='"+tbUser.getValueAt(barisdicopy,357).toString()+"',"+
                                        "booking_registrasi='"+tbUser.getValueAt(barisdicopy,358).toString()+"',"+
                                        "bpjs_cek_propinsi='"+tbUser.getValueAt(barisdicopy,359).toString()+"',"+
                                        "bpjs_cek_kabupaten='"+tbUser.getValueAt(barisdicopy,360).toString()+"',"+
                                        "bpjs_cek_kecamatan='"+tbUser.getValueAt(barisdicopy,361).toString()+"',"+
                                        "bpjs_cek_dokterdpjp='"+tbUser.getValueAt(barisdicopy,362).toString()+"',"+
                                        "bpjs_cek_riwayat_rujukanrs='"+tbUser.getValueAt(barisdicopy,363).toString()+"',"+
                                        "bpjs_cek_tanggal_rujukan='"+tbUser.getValueAt(barisdicopy,364).toString()+"',"+
                                        "permintaan_lab='"+tbUser.getValueAt(barisdicopy,365).toString()+"',"+
                                        "permintaan_radiologi='"+tbUser.getValueAt(barisdicopy,366).toString()+"',"+
                                        "surat_indeks='"+tbUser.getValueAt(barisdicopy,367).toString()+"',"+
                                        "surat_map='"+tbUser.getValueAt(barisdicopy,368).toString()+"',"+
                                        "surat_almari='"+tbUser.getValueAt(barisdicopy,369).toString()+"',"+
                                        "surat_rak='"+tbUser.getValueAt(barisdicopy,370).toString()+"',"+
                                        "surat_ruang='"+tbUser.getValueAt(barisdicopy,371).toString()+"',"+
                                        "surat_klasifikasi='"+tbUser.getValueAt(barisdicopy,372).toString()+"',"+
                                        "surat_status='"+tbUser.getValueAt(barisdicopy,373).toString()+"',"+
                                        "surat_sifat='"+tbUser.getValueAt(barisdicopy,374).toString()+"',"+
                                        "surat_balas='"+tbUser.getValueAt(barisdicopy,375).toString()+"',"+
                                        "surat_masuk='"+tbUser.getValueAt(barisdicopy,376).toString()+"',"+
                                        "pcare_cek_dokter='"+tbUser.getValueAt(barisdicopy,377).toString()+"',"+
                                        "pcare_cek_poli='"+tbUser.getValueAt(barisdicopy,378).toString()+"',"+
                                        "pcare_cek_provider='"+tbUser.getValueAt(barisdicopy,379).toString()+"',"+
                                        "pcare_cek_statuspulang='"+tbUser.getValueAt(barisdicopy,380).toString()+"',"+
                                        "pcare_cek_spesialis='"+tbUser.getValueAt(barisdicopy,381).toString()+"',"+
                                        "pcare_cek_subspesialis='"+tbUser.getValueAt(barisdicopy,382).toString()+"',"+
                                        "pcare_cek_sarana='"+tbUser.getValueAt(barisdicopy,383).toString()+"',"+
                                        "pcare_cek_khusus='"+tbUser.getValueAt(barisdicopy,384).toString()+"',"+
                                        "pcare_cek_obat='"+tbUser.getValueAt(barisdicopy,385).toString()+"',"+
                                        "pcare_cek_tindakan='"+tbUser.getValueAt(barisdicopy,386).toString()+"',"+
                                        "pcare_cek_faskessubspesialis='"+tbUser.getValueAt(barisdicopy,387).toString()+"',"+
                                        "pcare_cek_faskesalihrawat='"+tbUser.getValueAt(barisdicopy,388).toString()+"',"+
                                        "pcare_cek_faskesthalasemia='"+tbUser.getValueAt(barisdicopy,389).toString()+"',"+
                                        "pcare_mapping_obat='"+tbUser.getValueAt(barisdicopy,390).toString()+"',"+
                                        "pcare_mapping_tindakan='"+tbUser.getValueAt(barisdicopy,391).toString()+"',"+
                                        "pcare_club_prolanis='"+tbUser.getValueAt(barisdicopy,392).toString()+"',"+
                                        "pcare_mapping_poli='"+tbUser.getValueAt(barisdicopy,393).toString()+"',"+
                                        "pcare_kegiatan_kelompok='"+tbUser.getValueAt(barisdicopy,394).toString()+"',"+
                                        "pcare_mapping_tindakan_ranap='"+tbUser.getValueAt(barisdicopy,395).toString()+"',"+
                                        "pcare_peserta_kegiatan_kelompok='"+tbUser.getValueAt(barisdicopy,396).toString()+"',"+
                                        "sirkulasi_obat3='"+tbUser.getValueAt(barisdicopy,397).toString()+"',"+
                                        "bridging_pcare_daftar='"+tbUser.getValueAt(barisdicopy,398).toString()+"',"+
                                        "pcare_mapping_dokter='"+tbUser.getValueAt(barisdicopy,399).toString()+"',"+
                                        "ranap_per_ruang='"+tbUser.getValueAt(barisdicopy,400).toString()+"',"+
                                        "penyakit_ranap_cara_bayar='"+tbUser.getValueAt(barisdicopy,401).toString()+"',"+
                                        "anggota_militer_dirawat='"+tbUser.getValueAt(barisdicopy,402).toString()+"',"+
                                        "set_input_parsial='"+tbUser.getValueAt(barisdicopy,403).toString()+"',"+
                                        "lama_pelayanan_radiologi='"+tbUser.getValueAt(barisdicopy,404).toString()+"',"+
                                        "lama_pelayanan_lab='"+tbUser.getValueAt(barisdicopy,405).toString()+"',"+
                                        "bpjs_cek_sep='"+tbUser.getValueAt(barisdicopy,406).toString()+"',"+
                                        "catatan_perawatan='"+tbUser.getValueAt(barisdicopy,407).toString()+"',"+
                                        "surat_keluar='"+tbUser.getValueAt(barisdicopy,408).toString()+"',"+
                                        "kegiatan_farmasi='"+tbUser.getValueAt(barisdicopy,409).toString()+"',"+
                                        "stok_opname_logistik='"+tbUser.getValueAt(barisdicopy,410).toString()+"',"+
                                        "sirkulasi_non_medis='"+tbUser.getValueAt(barisdicopy,411).toString()+"',"+
                                        "rekap_lab_pertahun='"+tbUser.getValueAt(barisdicopy,412).toString()+"',"+
                                        "perujuk_lab_pertahun='"+tbUser.getValueAt(barisdicopy,413).toString()+"',"+
                                        "rekap_radiologi_pertahun='"+tbUser.getValueAt(barisdicopy,414).toString()+"',"+
                                        "perujuk_radiologi_pertahun='"+tbUser.getValueAt(barisdicopy,415).toString()+"',"+
                                        "jumlah_porsi_diet='"+tbUser.getValueAt(barisdicopy,416).toString()+"',"+
                                        "jumlah_macam_diet='"+tbUser.getValueAt(barisdicopy,417).toString()+"',"+
                                        "payment_point2='"+tbUser.getValueAt(barisdicopy,418).toString()+"',"+
                                        "pembayaran_akun_bayar2='"+tbUser.getValueAt(barisdicopy,419).toString()+"',"+
                                        "hapus_nota_salah='"+tbUser.getValueAt(barisdicopy,420).toString()+"',"+
                                        "pengkajian_askep='"+tbUser.getValueAt(barisdicopy,421).toString()+"',"+
                                        "hais_perbangsal='"+tbUser.getValueAt(barisdicopy,422).toString()+"',"+
                                        "ppn_obat='"+tbUser.getValueAt(barisdicopy,423).toString()+"',"+
                                        "saldo_akun_perbulan='"+tbUser.getValueAt(barisdicopy,424).toString()+"',"+
                                        "display_apotek='"+tbUser.getValueAt(barisdicopy,425).toString()+"',"+
                                        "sisrute_referensi_faskes='"+tbUser.getValueAt(barisdicopy,426).toString()+"',"+
                                        "sisrute_referensi_alasanrujuk='"+tbUser.getValueAt(barisdicopy,427).toString()+"',"+
                                        "sisrute_referensi_diagnosa='"+tbUser.getValueAt(barisdicopy,428).toString()+"',"+
                                        "sisrute_rujukan_masuk='"+tbUser.getValueAt(barisdicopy,429).toString()+"',"+
                                        "sisrute_rujukan_keluar='"+tbUser.getValueAt(barisdicopy,430).toString()+"',"+
                                        "bpjs_cek_skdp='"+tbUser.getValueAt(barisdicopy,431).toString()+"',"+
                                        "data_batch='"+tbUser.getValueAt(barisdicopy,432).toString()+"',"+
                                        "kunjungan_permintaan_lab='"+tbUser.getValueAt(barisdicopy,433).toString()+"',"+
                                        "kunjungan_permintaan_lab2='"+tbUser.getValueAt(barisdicopy,434).toString()+"',"+
                                        "kunjungan_permintaan_radiologi='"+tbUser.getValueAt(barisdicopy,435).toString()+"',"+
                                        "kunjungan_permintaan_radiologi2='"+tbUser.getValueAt(barisdicopy,436).toString()+"',"+
                                        "pcare_pemberian_obat='"+tbUser.getValueAt(barisdicopy,437).toString()+"',"+
                                        "pcare_pemberian_tindakan='"+tbUser.getValueAt(barisdicopy,438).toString()+"',"+
                                        "pembayaran_akun_bayar3='"+tbUser.getValueAt(barisdicopy,439).toString()+"',"+
                                        "password_asuransi='"+tbUser.getValueAt(barisdicopy,440).toString()+"',"+
                                        "kemenkes_sitt='"+tbUser.getValueAt(barisdicopy,441).toString()+"',"+
                                        "siranap_ketersediaan_kamar='"+tbUser.getValueAt(barisdicopy,442).toString()+"',"+
                                        "grafik_tb_periodelaporan='"+tbUser.getValueAt(barisdicopy,443).toString()+"',"+
                                        "grafik_tb_rujukan='"+tbUser.getValueAt(barisdicopy,444).toString()+"',"+
                                        "grafik_tb_riwayat='"+tbUser.getValueAt(barisdicopy,445).toString()+"',"+
                                        "grafik_tb_tipediagnosis='"+tbUser.getValueAt(barisdicopy,446).toString()+"',"+
                                        "grafik_tb_statushiv='"+tbUser.getValueAt(barisdicopy,447).toString()+"',"+
                                        "grafik_tb_skoringanak='"+tbUser.getValueAt(barisdicopy,448).toString()+"',"+
                                        "grafik_tb_konfirmasiskoring5='"+tbUser.getValueAt(barisdicopy,449).toString()+"',"+
                                        "grafik_tb_konfirmasiskoring6='"+tbUser.getValueAt(barisdicopy,450).toString()+"',"+
                                        "grafik_tb_sumberobat='"+tbUser.getValueAt(barisdicopy,451).toString()+"',"+
                                        "grafik_tb_hasilakhirpengobatan='"+tbUser.getValueAt(barisdicopy,452).toString()+"',"+
                                        "grafik_tb_hasilteshiv='"+tbUser.getValueAt(barisdicopy,453).toString()+"',"+
                                        "kadaluarsa_batch='"+tbUser.getValueAt(barisdicopy,454).toString()+"',"+
                                        "sisa_stok='"+tbUser.getValueAt(barisdicopy,455).toString()+"',"+
                                        "obat_per_resep='"+tbUser.getValueAt(barisdicopy,456).toString()+"',"+
                                        "pemakaian_air_pdam='"+tbUser.getValueAt(barisdicopy,457).toString()+"',"+
                                        "limbah_b3_medis='"+tbUser.getValueAt(barisdicopy,458).toString()+"',"+
                                        "grafik_air_pdam_pertanggal='"+tbUser.getValueAt(barisdicopy,459).toString()+"',"+
                                        "grafik_air_pdam_perbulan='"+tbUser.getValueAt(barisdicopy,460).toString()+"',"+
                                        "grafik_limbahb3_pertanggal='"+tbUser.getValueAt(barisdicopy,461).toString()+"',"+
                                        "grafik_limbahb3_perbulan='"+tbUser.getValueAt(barisdicopy,462).toString()+"',"+
                                        "limbah_domestik='"+tbUser.getValueAt(barisdicopy,463).toString()+"',"+
                                        "grafik_limbahdomestik_pertanggal='"+tbUser.getValueAt(barisdicopy,464).toString()+"',"+
                                        "grafik_limbahdomestik_perbulan='"+tbUser.getValueAt(barisdicopy,465).toString()+"',"+
                                        "mutu_air_limbah='"+tbUser.getValueAt(barisdicopy,466).toString()+"',"+
                                        "pest_control='"+tbUser.getValueAt(barisdicopy,467).toString()+"',"+
                                        "ruang_perpustakaan='"+tbUser.getValueAt(barisdicopy,468).toString()+"',"+
                                        "kategori_perpustakaan='"+tbUser.getValueAt(barisdicopy,469).toString()+"',"+
                                        "jenis_perpustakaan='"+tbUser.getValueAt(barisdicopy,470).toString()+"',"+
                                        "pengarang_perpustakaan='"+tbUser.getValueAt(barisdicopy,471).toString()+"',"+
                                        "penerbit_perpustakaan='"+tbUser.getValueAt(barisdicopy,472).toString()+"',"+
                                        "koleksi_perpustakaan='"+tbUser.getValueAt(barisdicopy,473).toString()+"',"+
                                        "inventaris_perpustakaan='"+tbUser.getValueAt(barisdicopy,474).toString()+"',"+
                                        "set_peminjaman_perpustakaan='"+tbUser.getValueAt(barisdicopy,475).toString()+"',"+
                                        "denda_perpustakaan='"+tbUser.getValueAt(barisdicopy,476).toString()+"',"+
                                        "anggota_perpustakaan='"+tbUser.getValueAt(barisdicopy,477).toString()+"',"+
                                        "peminjaman_perpustakaan='"+tbUser.getValueAt(barisdicopy,478).toString()+"',"+
                                        "bayar_denda_perpustakaan='"+tbUser.getValueAt(barisdicopy,479).toString()+"',"+
                                        "ebook_perpustakaan='"+tbUser.getValueAt(barisdicopy,480).toString()+"',"+
                                        "jenis_cidera_k3rs='"+tbUser.getValueAt(barisdicopy,481).toString()+"',"+
                                        "penyebab_k3rs='"+tbUser.getValueAt(barisdicopy,482).toString()+"',"+
                                        "jenis_luka_k3rs='"+tbUser.getValueAt(barisdicopy,483).toString()+"',"+
                                        "lokasi_kejadian_k3rs='"+tbUser.getValueAt(barisdicopy,484).toString()+"',"+
                                        "dampak_cidera_k3rs='"+tbUser.getValueAt(barisdicopy,485).toString()+"',"+
                                        "jenis_pekerjaan_k3rs='"+tbUser.getValueAt(barisdicopy,486).toString()+"',"+
                                        "bagian_tubuh_k3rs='"+tbUser.getValueAt(barisdicopy,487).toString()+"',"+
                                        "peristiwa_k3rs='"+tbUser.getValueAt(barisdicopy,488).toString()+"',"+
                                        "grafik_k3_pertahun='"+tbUser.getValueAt(barisdicopy,489).toString()+"',"+
                                        "grafik_k3_perbulan='"+tbUser.getValueAt(barisdicopy,490).toString()+"',"+
                                        "grafik_k3_pertanggal='"+tbUser.getValueAt(barisdicopy,491).toString()+"',"+
                                        "grafik_k3_perjeniscidera='"+tbUser.getValueAt(barisdicopy,492).toString()+"',"+
                                        "grafik_k3_perpenyebab='"+tbUser.getValueAt(barisdicopy,493).toString()+"',"+
                                        "grafik_k3_perjenisluka='"+tbUser.getValueAt(barisdicopy,494).toString()+"',"+
                                        "grafik_k3_lokasikejadian='"+tbUser.getValueAt(barisdicopy,495).toString()+"',"+
                                        "grafik_k3_dampakcidera='"+tbUser.getValueAt(barisdicopy,496).toString()+"',"+
                                        "grafik_k3_perjenispekerjaan='"+tbUser.getValueAt(barisdicopy,497).toString()+"',"+
                                        "grafik_k3_perbagiantubuh='"+tbUser.getValueAt(barisdicopy,498).toString()+"',"+
                                        "jenis_cidera_k3rstahun='"+tbUser.getValueAt(barisdicopy,499).toString()+"',"+
                                        "penyebab_k3rstahun='"+tbUser.getValueAt(barisdicopy,500).toString()+"',"+
                                        "jenis_luka_k3rstahun='"+tbUser.getValueAt(barisdicopy,501).toString()+"',"+
                                        "lokasi_kejadian_k3rstahun='"+tbUser.getValueAt(barisdicopy,502).toString()+"',"+
                                        "dampak_cidera_k3rstahun='"+tbUser.getValueAt(barisdicopy,503).toString()+"',"+
                                        "jenis_pekerjaan_k3rstahun='"+tbUser.getValueAt(barisdicopy,504).toString()+"',"+
                                        "bagian_tubuh_k3rstahun='"+tbUser.getValueAt(barisdicopy,505).toString()+"',"+
                                        "sekrining_rawat_jalan='"+tbUser.getValueAt(barisdicopy,506).toString()+"',"+
                                        "bpjs_histori_pelayanan='"+tbUser.getValueAt(barisdicopy,507).toString()+"',"+
                                        "rekap_mutasi_berkas='"+tbUser.getValueAt(barisdicopy,508).toString()+"',"+
                                        "skrining_ralan_pernapasan_pertahun='"+tbUser.getValueAt(barisdicopy,509).toString()+"',"+
                                        "pengajuan_barang_medis='"+tbUser.getValueAt(barisdicopy,510).toString()+"',"+
                                        "pengajuan_barang_nonmedis='"+tbUser.getValueAt(barisdicopy,511).toString()+"',"+
                                        "grafik_kunjungan_ranapbulan='"+tbUser.getValueAt(barisdicopy,512).toString()+"',"+
                                        "grafik_kunjungan_ranaptanggal='"+tbUser.getValueAt(barisdicopy,513).toString()+"',"+
                                        "grafik_kunjungan_ranap_peruang='"+tbUser.getValueAt(barisdicopy,514).toString()+"',"+
                                        "kunjungan_bangsal_pertahun='"+tbUser.getValueAt(barisdicopy,515).toString()+"',"+
                                        "grafik_jenjang_jabatanpegawai='"+tbUser.getValueAt(barisdicopy,516).toString()+"',"+
                                        "grafik_bidangpegawai='"+tbUser.getValueAt(barisdicopy,517).toString()+"',"+
                                        "grafik_departemenpegawai='"+tbUser.getValueAt(barisdicopy,518).toString()+"',"+
                                        "grafik_pendidikanpegawai='"+tbUser.getValueAt(barisdicopy,519).toString()+"',"+
                                        "grafik_sttswppegawai='"+tbUser.getValueAt(barisdicopy,520).toString()+"',"+
                                        "grafik_sttskerjapegawai='"+tbUser.getValueAt(barisdicopy,521).toString()+"',"+
                                        "grafik_sttspulangranap='"+tbUser.getValueAt(barisdicopy,522).toString()+"',"+
                                        "kip_pasien_ranap='"+tbUser.getValueAt(barisdicopy,523).toString()+"',"+
                                        "kip_pasien_ralan='"+tbUser.getValueAt(barisdicopy,524).toString()+"',"+
                                        "bpjs_mapping_dokterdpjp='"+tbUser.getValueAt(barisdicopy,525).toString()+"',"+
                                        "data_triase_igd='"+tbUser.getValueAt(barisdicopy,526).toString()+"',"+
                                        "master_triase_skala1='"+tbUser.getValueAt(barisdicopy,527).toString()+"',"+
                                        "master_triase_skala2='"+tbUser.getValueAt(barisdicopy,528).toString()+"',"+
                                        "master_triase_skala3='"+tbUser.getValueAt(barisdicopy,529).toString()+"',"+
                                        "master_triase_skala4='"+tbUser.getValueAt(barisdicopy,530).toString()+"',"+
                                        "master_triase_skala5='"+tbUser.getValueAt(barisdicopy,531).toString()+"',"+
                                        "master_triase_pemeriksaan='"+tbUser.getValueAt(barisdicopy,532).toString()+"',"+
                                        "master_triase_macamkasus='"+tbUser.getValueAt(barisdicopy,533).toString()+"',"+
                                        "rekap_permintaan_diet='"+tbUser.getValueAt(barisdicopy,534).toString()+"',"+
                                        "daftar_pasien_ranap='"+tbUser.getValueAt(barisdicopy,535).toString()+"',"+
                                        "daftar_pasien_ranaptni='"+tbUser.getValueAt(barisdicopy,536).toString()+"',"+
                                        "pengajuan_asetinventaris='"+tbUser.getValueAt(barisdicopy,537).toString()+"',"+
                                        "item_apotek_jenis='"+tbUser.getValueAt(barisdicopy,538).toString()+"',"+
                                        "item_apotek_kategori='"+tbUser.getValueAt(barisdicopy,539).toString()+"',"+
                                        "item_apotek_golongan='"+tbUser.getValueAt(barisdicopy,540).toString()+"',"+
                                        "item_apotek_industrifarmasi='"+tbUser.getValueAt(barisdicopy,541).toString()+"',"+
                                        "10_obat_terbanyak_poli='"+tbUser.getValueAt(barisdicopy,542).toString()+"',"+
                                        "grafik_pengajuan_aset_urgensi='"+tbUser.getValueAt(barisdicopy,543).toString()+"',"+
                                        "grafik_pengajuan_aset_status='"+tbUser.getValueAt(barisdicopy,544).toString()+"',"+
                                        "grafik_pengajuan_aset_departemen='"+tbUser.getValueAt(barisdicopy,545).toString()+"',"+
                                        "rekap_pengajuan_aset_departemen='"+tbUser.getValueAt(barisdicopy,546).toString()+"',"+
                                        "grafik_kelompok_jabatanpegawai='"+tbUser.getValueAt(barisdicopy,547).toString()+"',"+
                                        "grafik_resiko_kerjapegawai='"+tbUser.getValueAt(barisdicopy,548).toString()+"',"+
                                        "grafik_emergency_indexpegawai='"+tbUser.getValueAt(barisdicopy,549).toString()+"',"+
                                        "grafik_inventaris_ruang='"+tbUser.getValueAt(barisdicopy,550).toString()+"',"+
                                        "harian_HAIs2='"+tbUser.getValueAt(barisdicopy,551).toString()+"',"+
                                        "grafik_inventaris_jenis='"+tbUser.getValueAt(barisdicopy,552).toString()+"',"+
                                        "data_resume_pasien='"+tbUser.getValueAt(barisdicopy,553).toString()+"',"+
                                        "perkiraan_biaya_ranap='"+tbUser.getValueAt(barisdicopy,554).toString()+"',"+
                                        "rekap_obat_poli='"+tbUser.getValueAt(barisdicopy,555).toString()+"',"+
                                        "rekap_obat_pasien='"+tbUser.getValueAt(barisdicopy,556).toString()+"',"+
                                        "permintaan_perbaikan_inventaris='"+tbUser.getValueAt(barisdicopy,557).toString()+"',"+
                                        "grafik_HAIs_pasienbangsal='"+tbUser.getValueAt(barisdicopy,558).toString()+"',"+
                                        "grafik_HAIs_pasienbulan='"+tbUser.getValueAt(barisdicopy,559).toString()+"',"+
                                        "grafik_HAIs_laju_vap='"+tbUser.getValueAt(barisdicopy,560).toString()+"',"+
                                        "grafik_HAIs_laju_iad='"+tbUser.getValueAt(barisdicopy,561).toString()+"',"+
                                        "grafik_HAIs_laju_pleb='"+tbUser.getValueAt(barisdicopy,562).toString()+"',"+
                                        "grafik_HAIs_laju_isk='"+tbUser.getValueAt(barisdicopy,563).toString()+"',"+
                                        "grafik_HAIs_laju_ilo='"+tbUser.getValueAt(barisdicopy,564).toString()+"',"+
                                        "grafik_HAIs_laju_hap='"+tbUser.getValueAt(barisdicopy,565).toString()+"',"+
                                        "inhealth_mapping_poli='"+tbUser.getValueAt(barisdicopy,566).toString()+"',"+
                                        "inhealth_mapping_dokter='"+tbUser.getValueAt(barisdicopy,567).toString()+"',"+
                                        "inhealth_mapping_tindakan_ralan='"+tbUser.getValueAt(barisdicopy,568).toString()+"',"+
                                        "inhealth_mapping_tindakan_ranap='"+tbUser.getValueAt(barisdicopy,569).toString()+"',"+
                                        "inhealth_mapping_tindakan_radiologi='"+tbUser.getValueAt(barisdicopy,570).toString()+"',"+
                                        "inhealth_mapping_tindakan_laborat='"+tbUser.getValueAt(barisdicopy,571).toString()+"',"+
                                        "inhealth_mapping_tindakan_operasi='"+tbUser.getValueAt(barisdicopy,572).toString()+"',"+
                                        "hibah_obat_bhp='"+tbUser.getValueAt(barisdicopy,573).toString()+"',"+
                                        "asal_hibah='"+tbUser.getValueAt(barisdicopy,574).toString()+"',"+
                                        "asuhan_gizi='"+tbUser.getValueAt(barisdicopy,575).toString()+"',"+
                                        "inhealth_kirim_tagihan='"+tbUser.getValueAt(barisdicopy,576).toString()+"',"+
                                        "sirkulasi_obat4='"+tbUser.getValueAt(barisdicopy,577).toString()+"',"+
                                        "sirkulasi_obat5='"+tbUser.getValueAt(barisdicopy,578).toString()+"',"+
                                        "sirkulasi_non_medis2='"+tbUser.getValueAt(barisdicopy,579).toString()+"',"+
                                        "monitoring_asuhan_gizi='"+tbUser.getValueAt(barisdicopy,580).toString()+"',"+
                                        "penerimaan_obat_perbulan='"+tbUser.getValueAt(barisdicopy,581).toString()+"',"+
                                        "rekap_kunjungan='"+tbUser.getValueAt(barisdicopy,582).toString()+"',"+
                                        "surat_sakit='"+tbUser.getValueAt(barisdicopy,583).toString()+"',"+
                                        "penilaian_awal_keperawatan_ralan='"+tbUser.getValueAt(barisdicopy,584).toString()+"',"+
                                        "permintaan_diet='"+tbUser.getValueAt(barisdicopy,585).toString()+"',"+
                                        "master_masalah_keperawatan='"+tbUser.getValueAt(barisdicopy,586).toString()+"',"+
                                        "pengajuan_cuti='"+tbUser.getValueAt(barisdicopy,587).toString()+"',"+
                                        "kedatangan_pasien='"+tbUser.getValueAt(barisdicopy,588).toString()+"',"+
                                        "utd_pendonor='"+tbUser.getValueAt(barisdicopy,589).toString()+"',"+
                                        "toko_suplier='"+tbUser.getValueAt(barisdicopy,590).toString()+"',"+
                                        "toko_jenis='"+tbUser.getValueAt(barisdicopy,591).toString()+"',"+
                                        "toko_set_harga='"+tbUser.getValueAt(barisdicopy,592).toString()+"',"+
                                        "toko_barang='"+tbUser.getValueAt(barisdicopy,593).toString()+"',"+
                                        "penagihan_piutang_pasien='"+tbUser.getValueAt(barisdicopy,594).toString()+"',"+
                                        "akun_penagihan_piutang='"+tbUser.getValueAt(barisdicopy,595).toString()+"',"+
                                        "stok_opname_toko='"+tbUser.getValueAt(barisdicopy,596).toString()+"',"+
                                        "toko_riwayat_barang='"+tbUser.getValueAt(barisdicopy,597).toString()+"',"+
                                        "toko_surat_pemesanan='"+tbUser.getValueAt(barisdicopy,598).toString()+"',"+
                                        "toko_pengajuan_barang='"+tbUser.getValueAt(barisdicopy,599).toString()+"',"+
                                        "toko_penerimaan_barang='"+tbUser.getValueAt(barisdicopy,600).toString()+"',"+
                                        "toko_pengadaan_barang='"+tbUser.getValueAt(barisdicopy,601).toString()+"',"+
                                        "toko_hutang='"+tbUser.getValueAt(barisdicopy,602).toString()+"',"+
                                        "toko_bayar_pemesanan='"+tbUser.getValueAt(barisdicopy,603).toString()+"',"+
                                        "toko_member='"+tbUser.getValueAt(barisdicopy,604).toString()+"',"+
                                        "toko_penjualan='"+tbUser.getValueAt(barisdicopy,605).toString()+"',"+
                                        "registrasi_poli_per_tanggal='"+tbUser.getValueAt(barisdicopy,606).toString()+"',"+
                                        "toko_piutang='"+tbUser.getValueAt(barisdicopy,607).toString()+"',"+
                                        "toko_retur_beli='"+tbUser.getValueAt(barisdicopy,608).toString()+"',"+
                                        "ipsrs_returbeli='"+tbUser.getValueAt(barisdicopy,609).toString()+"',"+
                                        "ipsrs_riwayat_barang='"+tbUser.getValueAt(barisdicopy,610).toString()+"',"+
                                        "pasien_corona='"+tbUser.getValueAt(barisdicopy,611).toString()+"',"+
                                        "toko_pendapatan_harian='"+tbUser.getValueAt(barisdicopy,612).toString()+"',"+
                                        "diagnosa_pasien_corona='"+tbUser.getValueAt(barisdicopy,613).toString()+"',"+
                                        "perawatan_pasien_corona='"+tbUser.getValueAt(barisdicopy,614).toString()+"',"+
                                        "penilaian_awal_keperawatan_gigi='"+tbUser.getValueAt(barisdicopy,615).toString()+"',"+
                                        "master_masalah_keperawatan_gigi='"+tbUser.getValueAt(barisdicopy,616).toString()+"',"+
                                        "toko_bayar_piutang='"+tbUser.getValueAt(barisdicopy,617).toString()+"',"+
                                        "toko_piutang_harian='"+tbUser.getValueAt(barisdicopy,618).toString()+"',"+
                                        "toko_penjualan_harian='"+tbUser.getValueAt(barisdicopy,619).toString()+"',"+
                                        "deteksi_corona='"+tbUser.getValueAt(barisdicopy,620).toString()+"',"+
                                        "penilaian_awal_keperawatan_kebidanan='"+tbUser.getValueAt(barisdicopy,621).toString()+"',"+
                                        "pengumuman_epasien='"+tbUser.getValueAt(barisdicopy,622).toString()+"',"+
                                        "surat_hamil='"+tbUser.getValueAt(barisdicopy,623).toString()+"',"+
                                        "set_tarif_online='"+tbUser.getValueAt(barisdicopy,624).toString()+"',"+
                                        "booking_periksa='"+tbUser.getValueAt(barisdicopy,625).toString()+"',"+
                                        "toko_sirkulasi='"+tbUser.getValueAt(barisdicopy,626).toString()+"',"+
                                        "toko_retur_jual='"+tbUser.getValueAt(barisdicopy,627).toString()+"',"+
                                        "toko_retur_piutang='"+tbUser.getValueAt(barisdicopy,628).toString()+"',"+
                                        "toko_sirkulasi2='"+tbUser.getValueAt(barisdicopy,629).toString()+"',"+
                                        "toko_keuntungan_barang='"+tbUser.getValueAt(barisdicopy,630).toString()+"',"+
                                        "zis_pengeluaran_penerima_dankes='"+tbUser.getValueAt(barisdicopy,631).toString()+"',"+
                                        "zis_penghasilan_penerima_dankes='"+tbUser.getValueAt(barisdicopy,632).toString()+"',"+
                                        "zis_ukuran_rumah_penerima_dankes='"+tbUser.getValueAt(barisdicopy,633).toString()+"',"+
                                        "zis_dinding_rumah_penerima_dankes='"+tbUser.getValueAt(barisdicopy,634).toString()+"',"+
                                        "zis_lantai_rumah_penerima_dankes='"+tbUser.getValueAt(barisdicopy,635).toString()+"',"+
                                        "zis_atap_rumah_penerima_dankes='"+tbUser.getValueAt(barisdicopy,636).toString()+"',"+
                                        "zis_kepemilikan_rumah_penerima_dankes='"+tbUser.getValueAt(barisdicopy,637).toString()+"',"+
                                        "zis_kamar_mandi_penerima_dankes='"+tbUser.getValueAt(barisdicopy,638).toString()+"',"+
                                        "zis_dapur_rumah_penerima_dankes='"+tbUser.getValueAt(barisdicopy,639).toString()+"',"+
                                        "zis_kursi_rumah_penerima_dankes='"+tbUser.getValueAt(barisdicopy,640).toString()+"',"+
                                        "zis_kategori_phbs_penerima_dankes='"+tbUser.getValueAt(barisdicopy,641).toString()+"',"+
                                        "zis_elektronik_penerima_dankes='"+tbUser.getValueAt(barisdicopy,642).toString()+"',"+
                                        "zis_ternak_penerima_dankes='"+tbUser.getValueAt(barisdicopy,643).toString()+"',"+
                                        "zis_jenis_simpanan_penerima_dankes='"+tbUser.getValueAt(barisdicopy,644).toString()+"',"+
                                        "penilaian_awal_keperawatan_anak='"+tbUser.getValueAt(barisdicopy,645).toString()+"',"+
                                        "zis_kategori_asnaf_penerima_dankes='"+tbUser.getValueAt(barisdicopy,646).toString()+"',"+
                                        "master_masalah_keperawatan_anak='"+tbUser.getValueAt(barisdicopy,647).toString()+"',"+
                                        "master_imunisasi='"+tbUser.getValueAt(barisdicopy,648).toString()+"',"+
                                        "zis_patologis_penerima_dankes='"+tbUser.getValueAt(barisdicopy,649).toString()+"',"+
                                        "pcare_cek_kartu='"+tbUser.getValueAt(barisdicopy,650).toString()+"',"+
                                        "surat_bebas_narkoba='"+tbUser.getValueAt(barisdicopy,651).toString()+"',"+
                                        "surat_keterangan_covid='"+tbUser.getValueAt(barisdicopy,652).toString()+"',"+
                                        "pemakaian_air_tanah='"+tbUser.getValueAt(barisdicopy,653).toString()+"',"+
                                        "grafik_air_tanah_pertanggal='"+tbUser.getValueAt(barisdicopy,654).toString()+"',"+
                                        "grafik_air_tanah_perbulan='"+tbUser.getValueAt(barisdicopy,655).toString()+"',"+
                                        "lama_pelayanan_poli='"+tbUser.getValueAt(barisdicopy,656).toString()+"',"+
                                        "hemodialisa='"+tbUser.getValueAt(barisdicopy,657).toString()+"',"+
                                        "laporan_tahunan_irj='"+tbUser.getValueAt(barisdicopy,658).toString()+"',"+
                                        "grafik_harian_hemodialisa='"+tbUser.getValueAt(barisdicopy,659).toString()+"',"+
                                        "grafik_bulanan_hemodialisa='"+tbUser.getValueAt(barisdicopy,660).toString()+"',"+
                                        "grafik_tahunan_hemodialisa='"+tbUser.getValueAt(barisdicopy,661).toString()+"',"+
                                        "grafik_bulanan_meninggal='"+tbUser.getValueAt(barisdicopy,662).toString()+"',"+
                                        "perbaikan_inventaris='"+tbUser.getValueAt(barisdicopy,663).toString()+"',"+
                                        "surat_cuti_hamil='"+tbUser.getValueAt(barisdicopy,664).toString()+"',"+
                                        "permintaan_stok_obat_pasien='"+tbUser.getValueAt(barisdicopy,665).toString()+"',"+
                                        "pemeliharaan_inventaris='"+tbUser.getValueAt(barisdicopy,666).toString()+"',"+
                                        "klasifikasi_pasien_ranap='"+tbUser.getValueAt(barisdicopy,667).toString()+"',"+
                                        "bulanan_klasifikasi_pasien_ranap='"+tbUser.getValueAt(barisdicopy,668).toString()+"',"+
                                        "harian_klasifikasi_pasien_ranap='"+tbUser.getValueAt(barisdicopy,669).toString()+"',"+
                                        "klasifikasi_pasien_perbangsal='"+tbUser.getValueAt(barisdicopy,670).toString()+"',"+
                                        "soap_perawatan='"+tbUser.getValueAt(barisdicopy,671).toString()+"',"+
                                        "klaim_rawat_jalan='"+tbUser.getValueAt(barisdicopy,672).toString()+"',"+
                                        "skrining_gizi='"+tbUser.getValueAt(barisdicopy,673).toString()+"',"+
                                        "lama_penyiapan_rm='"+tbUser.getValueAt(barisdicopy,674).toString()+"',"+
                                        "dosis_radiologi='"+tbUser.getValueAt(barisdicopy,675).toString()+"',"+
                                        "demografi_umur_kunjungan='"+tbUser.getValueAt(barisdicopy,676).toString()+"',"+
                                        "jam_diet_pasien='"+tbUser.getValueAt(barisdicopy,677).toString()+"',"+
                                        "rvu_bpjs='"+tbUser.getValueAt(barisdicopy,678).toString()+"',"+
                                        "verifikasi_penerimaan_farmasi='"+tbUser.getValueAt(barisdicopy,679).toString()+"',"+
                                        "verifikasi_penerimaan_logistik='"+tbUser.getValueAt(barisdicopy,680).toString()+"',"+
                                        "pemeriksaan_lab_pa='"+tbUser.getValueAt(barisdicopy,681).toString()+"',"+
                                        "ringkasan_pengajuan_obat='"+tbUser.getValueAt(barisdicopy,682).toString()+"',"+
                                        "ringkasan_pemesanan_obat='"+tbUser.getValueAt(barisdicopy,683).toString()+"',"+
                                        "ringkasan_pengadaan_obat='"+tbUser.getValueAt(barisdicopy,684).toString()+"',"+
                                        "ringkasan_penerimaan_obat='"+tbUser.getValueAt(barisdicopy,685).toString()+"',"+
                                        "ringkasan_hibah_obat='"+tbUser.getValueAt(barisdicopy,686).toString()+"',"+
                                        "ringkasan_penjualan_obat='"+tbUser.getValueAt(barisdicopy,687).toString()+"',"+
                                        "ringkasan_beri_obat='"+tbUser.getValueAt(barisdicopy,688).toString()+"',"+
                                        "ringkasan_piutang_obat='"+tbUser.getValueAt(barisdicopy,689).toString()+"',"+
                                        "ringkasan_stok_keluar_obat='"+tbUser.getValueAt(barisdicopy,690).toString()+"',"+
                                        "ringkasan_retur_suplier_obat='"+tbUser.getValueAt(barisdicopy,691).toString()+"',"+
                                        "ringkasan_retur_pembeli_obat='"+tbUser.getValueAt(barisdicopy,692).toString()+"',"+
                                        "penilaian_awal_keperawatan_ranapkebidanan='"+tbUser.getValueAt(barisdicopy,693).toString()+"',"+
                                        "ringkasan_pengajuan_nonmedis='"+tbUser.getValueAt(barisdicopy,694).toString()+"',"+
                                        "ringkasan_pemesanan_nonmedis='"+tbUser.getValueAt(barisdicopy,695).toString()+"',"+
                                        "ringkasan_pengadaan_nonmedis='"+tbUser.getValueAt(barisdicopy,696).toString()+"',"+
                                        "ringkasan_penerimaan_nonmedis='"+tbUser.getValueAt(barisdicopy,697).toString()+"',"+
                                        "ringkasan_stokkeluar_nonmedis='"+tbUser.getValueAt(barisdicopy,698).toString()+"',"+
                                        "ringkasan_returbeli_nonmedis='"+tbUser.getValueAt(barisdicopy,699).toString()+"',"+
                                        "omset_penerimaan='"+tbUser.getValueAt(barisdicopy,700).toString()+"',"+
                                        "validasi_penagihan_piutang='"+tbUser.getValueAt(barisdicopy,701).toString()+"',"+
                                        "permintaan_ranap='"+tbUser.getValueAt(barisdicopy,702).toString()+"',"+
                                        "bpjs_diagnosa_prb='"+tbUser.getValueAt(barisdicopy,703).toString()+"',"+
                                        "bpjs_obat_prb='"+tbUser.getValueAt(barisdicopy,704).toString()+"',"+
                                        "bpjs_surat_kontrol='"+tbUser.getValueAt(barisdicopy,705).toString()+"',"+
                                        "penggunaan_bhp_ok='"+tbUser.getValueAt(barisdicopy,706).toString()+"',"+
                                        "surat_keterangan_rawat_inap='"+tbUser.getValueAt(barisdicopy,707).toString()+"',"+
                                        "surat_keterangan_sehat='"+tbUser.getValueAt(barisdicopy,708).toString()+"',"+
                                        "pendapatan_per_carabayar='"+tbUser.getValueAt(barisdicopy,709).toString()+"',"+
                                        "akun_host_to_host_bank_jateng='"+tbUser.getValueAt(barisdicopy,710).toString()+"',"+
                                        "pembayaran_bank_jateng='"+tbUser.getValueAt(barisdicopy,711).toString()+"',"+
                                        "bpjs_surat_pri='"+tbUser.getValueAt(barisdicopy,712).toString()+"',"+
                                        "ringkasan_tindakan='"+tbUser.getValueAt(barisdicopy,713).toString()+"',"+
                                        "lama_pelayanan_pasien='"+tbUser.getValueAt(barisdicopy,714).toString()+"',"+
                                        "surat_sakit_pihak_2='"+tbUser.getValueAt(barisdicopy,715).toString()+"',"+
                                        "tagihan_hutang_obat='"+tbUser.getValueAt(barisdicopy,716).toString()+"',"+
                                        "referensi_mobilejkn_bpjs='"+tbUser.getValueAt(barisdicopy,717).toString()+"',"+
                                        "batal_pendaftaran_mobilejkn_bpjs='"+tbUser.getValueAt(barisdicopy,718).toString()+"',"+
                                        "lama_operasi='"+tbUser.getValueAt(barisdicopy,719).toString()+"',"+
                                        "grafik_inventaris_kategori='"+tbUser.getValueAt(barisdicopy,720).toString()+"',"+
                                        "grafik_inventaris_merk='"+tbUser.getValueAt(barisdicopy,721).toString()+"',"+
                                        "grafik_inventaris_produsen='"+tbUser.getValueAt(barisdicopy,722).toString()+"',"+
                                        "pengembalian_deposit_pasien='"+tbUser.getValueAt(barisdicopy,723).toString()+"',"+
                                        "validasi_tagihan_hutang_obat='"+tbUser.getValueAt(barisdicopy,724).toString()+"',"+
                                        "piutang_obat_belum_lunas='"+tbUser.getValueAt(barisdicopy,725).toString()+"',"+
                                        "integrasi_briapi='"+tbUser.getValueAt(barisdicopy,726).toString()+"',"+
                                        "pengadaan_aset_inventaris='"+tbUser.getValueAt(barisdicopy,727).toString()+"',"+
                                        "akun_aset_inventaris='"+tbUser.getValueAt(barisdicopy,728).toString()+"',"+
                                        "suplier_inventaris='"+tbUser.getValueAt(barisdicopy,729).toString()+"',"+
                                        "penerimaan_aset_inventaris='"+tbUser.getValueAt(barisdicopy,730).toString()+"',"+
                                        "bayar_pemesanan_iventaris='"+tbUser.getValueAt(barisdicopy,731).toString()+"',"+
                                        "hutang_aset_inventaris='"+tbUser.getValueAt(barisdicopy,732).toString()+"',"+
                                        "hibah_aset_inventaris='"+tbUser.getValueAt(barisdicopy,733).toString()+"',"+
                                        "titip_faktur_non_medis='"+tbUser.getValueAt(barisdicopy,734).toString()+"',"+
                                        "validasi_tagihan_non_medis='"+tbUser.getValueAt(barisdicopy,735).toString()+"',"+
                                        "titip_faktur_aset='"+tbUser.getValueAt(barisdicopy,736).toString()+"',"+
                                        "validasi_tagihan_aset='"+tbUser.getValueAt(barisdicopy,737).toString()+"',"+
                                        "hibah_non_medis='"+tbUser.getValueAt(barisdicopy,738).toString()+"',"+
                                        "pcare_alasan_tacc='"+tbUser.getValueAt(barisdicopy,739).toString()+"',"+
                                        "resep_luar='"+tbUser.getValueAt(barisdicopy,740).toString()+"',"+
                                        "surat_bebas_tbc='"+tbUser.getValueAt(barisdicopy,741).toString()+"',"+
                                        "surat_buta_warna='"+tbUser.getValueAt(barisdicopy,742).toString()+"',"+
                                        "surat_bebas_tato='"+tbUser.getValueAt(barisdicopy,743).toString()+"',"+
                                        "surat_kewaspadaan_kesehatan='"+tbUser.getValueAt(barisdicopy,744).toString()+"',"+
                                        "grafik_porsidiet_pertanggal='"+tbUser.getValueAt(barisdicopy,745).toString()+"',"+
                                        "grafik_porsidiet_perbulan='"+tbUser.getValueAt(barisdicopy,746).toString()+"',"+
                                        "grafik_porsidiet_pertahun='"+tbUser.getValueAt(barisdicopy,747).toString()+"',"+
                                        "grafik_porsidiet_perbangsal='"+tbUser.getValueAt(barisdicopy,748).toString()+"',"+
                                        "penilaian_awal_medis_ralan='"+tbUser.getValueAt(barisdicopy,749).toString()+"',"+
                                        "master_masalah_keperawatan_mata='"+tbUser.getValueAt(barisdicopy,750).toString()+"',"+
                                        "penilaian_awal_keperawatan_mata='"+tbUser.getValueAt(barisdicopy,751).toString()+"',"+
                                        "penilaian_awal_medis_ranap='"+tbUser.getValueAt(barisdicopy,752).toString()+"',"+
                                        "penilaian_awal_medis_ranap_kebidanan='"+tbUser.getValueAt(barisdicopy,753).toString()+"',"+
                                        "penilaian_awal_medis_ralan_kebidanan='"+tbUser.getValueAt(barisdicopy,754).toString()+"',"+
                                        "penilaian_awal_medis_igd='"+tbUser.getValueAt(barisdicopy,755).toString()+"',"+
                                        "penilaian_awal_medis_ralan_anak='"+tbUser.getValueAt(barisdicopy,756).toString()+"',"+
                                        "bpjs_referensi_poli_hfis='"+tbUser.getValueAt(barisdicopy,757).toString()+"',"+
                                        "bpjs_referensi_dokter_hfis='"+tbUser.getValueAt(barisdicopy,758).toString()+"',"+
                                        "bpjs_referensi_jadwal_hfis='"+tbUser.getValueAt(barisdicopy,759).toString()+"',"+
                                        "penilaian_fisioterapi='"+tbUser.getValueAt(barisdicopy,760).toString()+"',"+
                                        "bpjs_program_prb='"+tbUser.getValueAt(barisdicopy,761).toString()+"',"+
                                        "bpjs_suplesi_jasaraharja='"+tbUser.getValueAt(barisdicopy,762).toString()+"',"+
                                        "bpjs_data_induk_kecelakaan='"+tbUser.getValueAt(barisdicopy,763).toString()+"',"+
                                        "bpjs_sep_internal='"+tbUser.getValueAt(barisdicopy,764).toString()+"',"+
                                        "bpjs_klaim_jasa_raharja='"+tbUser.getValueAt(barisdicopy,765).toString()+"',"+
                                        "bpjs_daftar_finger_print='"+tbUser.getValueAt(barisdicopy,766).toString()+"',"+
                                        "bpjs_rujukan_khusus='"+tbUser.getValueAt(barisdicopy,767).toString()+"',"+
                                        "pemeliharaan_gedung='"+tbUser.getValueAt(barisdicopy,768).toString()+"',"+
                                        "grafik_perbaikan_inventaris_pertanggal='"+tbUser.getValueAt(barisdicopy,769).toString()+"',"+
                                        "grafik_perbaikan_inventaris_perbulan='"+tbUser.getValueAt(barisdicopy,770).toString()+"',"+
                                        "grafik_perbaikan_inventaris_pertahun='"+tbUser.getValueAt(barisdicopy,771).toString()+"',"+
                                        "grafik_perbaikan_inventaris_perpelaksana_status='"+tbUser.getValueAt(barisdicopy,772).toString()+"',"+
                                        "penilaian_mcu='"+tbUser.getValueAt(barisdicopy,773).toString()+"',"+
                                        "peminjam_piutang='"+tbUser.getValueAt(barisdicopy,774).toString()+"',"+
                                        "piutang_lainlain='"+tbUser.getValueAt(barisdicopy,775).toString()+"',"+
                                        "cara_bayar='"+tbUser.getValueAt(barisdicopy,776).toString()+"',"+
                                        "audit_kepatuhan_apd='"+tbUser.getValueAt(barisdicopy,777).toString()+"',"+
                                        "bpjs_task_id='"+tbUser.getValueAt(barisdicopy,778).toString()+"',"+
                                        "bayar_piutang_lain='"+tbUser.getValueAt(barisdicopy,779).toString()+"',"+
                                        "pembayaran_akun_bayar4='"+tbUser.getValueAt(barisdicopy,780).toString()+"',"+
                                        "stok_akhir_farmasi_pertanggal='"+tbUser.getValueAt(barisdicopy,781).toString()+"',"+
                                        "riwayat_kamar_pasien='"+tbUser.getValueAt(barisdicopy,782).toString()+"',"+
                                        "uji_fungsi_kfr='"+tbUser.getValueAt(barisdicopy,783).toString()+"',"+
                                        "hapus_berkas_digital_perawatan='"+tbUser.getValueAt(barisdicopy,784).toString()+"',"+
                                        "kategori_pengeluaran_harian='"+tbUser.getValueAt(barisdicopy,785).toString()+"',"+
                                        "kategori_pemasukan_lain='"+tbUser.getValueAt(barisdicopy,786).toString()+"',"+
                                        "pembayaran_akun_bayar5='"+tbUser.getValueAt(barisdicopy,787).toString()+"',"+
                                        "ruang_ok='"+tbUser.getValueAt(barisdicopy,788).toString()+"',"+
                                        "telaah_resep='"+tbUser.getValueAt(barisdicopy,789).toString()+"',"+
                                        "jasa_tindakan_pasien='"+tbUser.getValueAt(barisdicopy,790).toString()+"',"+
                                        "permintaan_resep_pulang='"+tbUser.getValueAt(barisdicopy,791).toString()+"',"+
                                        "rekap_jm_dokter='"+tbUser.getValueAt(barisdicopy,792).toString()+"',"+
                                        "status_data_rm='"+tbUser.getValueAt(barisdicopy,793).toString()+"',"+
                                        "ubah_petugas_lab_pk='"+tbUser.getValueAt(barisdicopy,794).toString()+"',"+
                                        "ubah_petugas_lab_pa='"+tbUser.getValueAt(barisdicopy,795).toString()+"',"+
                                        "ubah_petugas_radiologi='"+tbUser.getValueAt(barisdicopy,796).toString()+"',"+
                                        "gabung_norawat='"+tbUser.getValueAt(barisdicopy,797).toString()+"',"+
                                        "gabung_rm='"+tbUser.getValueAt(barisdicopy,798).toString()+"',"+
                                        "ringkasan_biaya_obat_pasien_pertanggal='"+tbUser.getValueAt(barisdicopy,799).toString()+"',"+
                                        "master_masalah_keperawatan_igd='"+tbUser.getValueAt(barisdicopy,800).toString()+"',"+
                                        "penilaian_awal_keperawatan_igd='"+tbUser.getValueAt(barisdicopy,801).toString()+"',"+
                                        "bpjs_referensi_dpho_apotek='"+tbUser.getValueAt(barisdicopy,802).toString()+"',"+
                                        "bpjs_referensi_poli_apotek='"+tbUser.getValueAt(barisdicopy,803).toString()+"',"+
                                        "bayar_jm_dokter='"+tbUser.getValueAt(barisdicopy,804).toString()+"',"+
                                        "bpjs_referensi_faskes_apotek='"+tbUser.getValueAt(barisdicopy,805).toString()+"',"+
                                        "bpjs_referensi_spesialistik_apotek='"+tbUser.getValueAt(barisdicopy,806).toString()+"',"+
                                        "pembayaran_briva='"+tbUser.getValueAt(barisdicopy,807).toString()+"',"+
                                        "penilaian_awal_keperawatan_ranap='"+tbUser.getValueAt(barisdicopy,808).toString()+"',"+
                                        "nilai_penerimaan_vendor_farmasi_perbulan='"+tbUser.getValueAt(barisdicopy,809).toString()+"',"+
                                        "akun_bayar_hutang='"+tbUser.getValueAt(barisdicopy,810).toString()+"',"+
                                        "master_rencana_keperawatan='"+tbUser.getValueAt(barisdicopy,811).toString()+"',"+
                                        "laporan_tahunan_igd='"+tbUser.getValueAt(barisdicopy,812).toString()+"',"+
                                        "obat_bhp_tidakbergerak='"+tbUser.getValueAt(barisdicopy,813).toString()+"',"+
                                        "ringkasan_hutang_vendor_farmasi='"+tbUser.getValueAt(barisdicopy,814).toString()+"',"+
                                        "nilai_penerimaan_vendor_nonmedis_perbulan='"+tbUser.getValueAt(barisdicopy,815).toString()+"',"+
                                        "ringkasan_hutang_vendor_nonmedis='"+tbUser.getValueAt(barisdicopy,816).toString()+"',"+
                                        "master_rencana_keperawatan_anak='"+tbUser.getValueAt(barisdicopy,817).toString()+"',"+
                                        "anggota_polri_dirawat='"+tbUser.getValueAt(barisdicopy,818).toString()+"',"+
                                        "daftar_pasien_ranap_polri='"+tbUser.getValueAt(barisdicopy,819).toString()+"',"+
                                        "soap_ralan_polri='"+tbUser.getValueAt(barisdicopy,820).toString()+"',"+
                                        "soap_ranap_polri='"+tbUser.getValueAt(barisdicopy,821).toString()+"',"+
                                        "laporan_penyakit_polri='"+tbUser.getValueAt(barisdicopy,822).toString()+"',"+
                                        "jumlah_pengunjung_ralan_polri='"+tbUser.getValueAt(barisdicopy,823).toString()+"',"+
                                        "catatan_observasi_igd='"+tbUser.getValueAt(barisdicopy,824).toString()+"',"+
                                        "catatan_observasi_ranap='"+tbUser.getValueAt(barisdicopy,825).toString()+"',"+
                                        "catatan_observasi_ranap_kebidanan='"+tbUser.getValueAt(barisdicopy,826).toString()+"',"+
                                        "catatan_observasi_ranap_postpartum='"+tbUser.getValueAt(barisdicopy,827).toString()+"',"+
                                        "penilaian_awal_medis_ralan_tht='"+tbUser.getValueAt(barisdicopy,828).toString()+"',"+
                                        "penilaian_psikologi='"+tbUser.getValueAt(barisdicopy,829).toString()+"',"+
                                        "audit_cuci_tangan_medis='"+tbUser.getValueAt(barisdicopy,830).toString()+"',"+
                                        "audit_pembuangan_limbah='"+tbUser.getValueAt(barisdicopy,831).toString()+"',"+
                                        "ruang_audit_kepatuhan='"+tbUser.getValueAt(barisdicopy,832).toString()+"',"+
                                        "audit_pembuangan_benda_tajam='"+tbUser.getValueAt(barisdicopy,833).toString()+"',"+
                                        "audit_penanganan_darah='"+tbUser.getValueAt(barisdicopy,834).toString()+"',"+
                                        "audit_pengelolaan_linen_kotor='"+tbUser.getValueAt(barisdicopy,835).toString()+"',"+
                                        "audit_penempatan_pasien='"+tbUser.getValueAt(barisdicopy,836).toString()+"',"+
                                        "audit_kamar_jenazah='"+tbUser.getValueAt(barisdicopy,837).toString()+"',"+
                                        "audit_bundle_iadp='"+tbUser.getValueAt(barisdicopy,838).toString()+"',"+
                                        "audit_bundle_ido='"+tbUser.getValueAt(barisdicopy,839).toString()+"',"+
                                        "audit_fasilitas_kebersihan_tangan='"+tbUser.getValueAt(barisdicopy,840).toString()+"',"+
                                        "audit_fasilitas_apd='"+tbUser.getValueAt(barisdicopy,841).toString()+"',"+
                                        "audit_pembuangan_limbah_cair_infeksius='"+tbUser.getValueAt(barisdicopy,842).toString()+"',"+
                                        "audit_sterilisasi_alat='"+tbUser.getValueAt(barisdicopy,843).toString()+"',"+
                                        "penilaian_awal_medis_ralan_psikiatri='"+tbUser.getValueAt(barisdicopy,844).toString()+"',"+
                                        "persetujuan_penolakan_tindakan='"+tbUser.getValueAt(barisdicopy,845).toString()+"'");
                                }    
                                userdicopy="";
                                copyhakakses="";
                                barisdicopy=-1;
                                tampil();
                            } catch (Exception e) {
                                userdicopy="";
                                barisdicopy=-1;
                                copyhakakses="";
                            }
                        }else{
                            userdicopy="";
                            barisdicopy=-1;
                            copyhakakses="";
                        }
                    }
                }
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
            Sequel.queryu("delete from temporary where temp37='"+akses.getalamatip()+"'");
            int row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'"+i+"','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','"+akses.getalamatip()+"'","Data User"); 
            }
            Valid.MyReportqry("rptUser.jasper","report","::[ Data User ]::","select * from temporary where temporary.temp37='"+akses.getalamatip()+"' order by temporary.no",param);
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

    private void MnCopyHakAksesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCopyHakAksesActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data user...!!!!");
            TCari.requestFocus();
        }else if(TKd.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data user yang mau dicopy hak aksesnya...!!!");
            tbUser.requestFocus();
        }else{
            copyhakakses="copy";
            userdicopy=TKd.getText();
            if(tbUser.getSelectedRow()!= -1){
                barisdicopy=tbUser.getSelectedRow();
            }
            JOptionPane.showMessageDialog(null,"Silahkan pilih user tujuan..!!"); 
        }
    }//GEN-LAST:event_MnCopyHakAksesActionPerformed

    private void MnSetUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSetUserActionPerformed
        if(tbUser.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            personal.isUser(TKd.getText(),TNmUser.getText(),TPass.getText());
            personal.setSize(460,this.getHeight()-50);
            personal.setLocationRelativeTo(internalFrame1);
            personal.setAlwaysOnTop(false);
            personal.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());   
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan pilih dulu nama user..!!");
        }   
    }//GEN-LAST:event_MnSetUserActionPerformed

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
    private javax.swing.JMenuItem MnCopyHakAkses;
    private javax.swing.JMenuItem MnSetUser;
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
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass5;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private widget.Table tbUser;
    // End of variables declaration//GEN-END:variables

    private void tampil() {        
        try{    
            Valid.tabelKosong(tabMode);
            ps=koneksi.prepareStatement(
                "select AES_DECRYPT(user.id_user,'nur'),AES_DECRYPT(user.password,'windi'),user.penyakit,user.obat_penyakit,user.dokter,"+
                "user.jadwal_praktek,user.petugas,user.pasien,user.registrasi,user.tindakan_ralan,user.kamar_inap,user.tindakan_ranap,user.operasi,"+
                "user.rujukan_keluar,user.rujukan_masuk,user.beri_obat,user.resep_pulang,user.pasien_meninggal,user.diet_pasien,user.kelahiran_bayi,"+
                "user.periksa_lab,user.periksa_radiologi,user.kasir_ralan,user.deposit_pasien,user.piutang_pasien,user.peminjaman_berkas,user.barcode,"+
                "user.presensi_harian,user.presensi_bulanan,user.pegawai_admin,user.pegawai_user,user.suplier,user.satuan_barang,user.konversi_satuan,"+
                "user.jenis_barang,user.obat,user.stok_opname_obat,user.stok_obat_pasien,user.pengadaan_obat,user.pemesanan_obat,user.penjualan_obat,"+
                "user.piutang_obat,user.retur_ke_suplier,user.retur_dari_pembeli,user.retur_obat_ranap,user.retur_piutang_pasien,user.keuntungan_penjualan,"+
                "user.keuntungan_beri_obat,user.sirkulasi_obat,user.ipsrs_barang,user.ipsrs_pengadaan_barang,user.ipsrs_stok_keluar,user.ipsrs_rekap_pengadaan,"+
                "user.ipsrs_rekap_stok_keluar,user.ipsrs_pengeluaran_harian,user.inventaris_jenis,user.inventaris_kategori,user.inventaris_merk,"+
                "user.inventaris_ruang,user.inventaris_produsen,user.inventaris_koleksi,user.inventaris_inventaris,user.inventaris_sirkulasi,user.parkir_jenis,"+
                "user.parkir_in,user.parkir_out,user.parkir_rekap_harian,user.parkir_rekap_bulanan,user.informasi_kamar,user.harian_tindakan_poli,"+
                "user.obat_per_poli,user.obat_per_kamar,user.obat_per_dokter_ralan,user.obat_per_dokter_ranap,user.harian_dokter,user.bulanan_dokter,"+
                "user.harian_paramedis,user.bulanan_paramedis,user.pembayaran_ralan,user.pembayaran_ranap,user.rekap_pembayaran_ralan,user.rekap_pembayaran_ranap,"+
                "user.tagihan_masuk,user.tambahan_biaya,user.potongan_biaya,user.resep_obat,user.resume_pasien,user.penyakit_ralan,user.penyakit_ranap,user.kamar,"+
                "user.tarif_ralan,user.tarif_ranap,user.tarif_lab,user.tarif_radiologi,user.tarif_operasi,user.akun_rekening,user.rekening_tahun,"+
                "user.posting_jurnal,user.buku_besar,user.cashflow,user.keuangan,user.pengeluaran,user.setup_pjlab,user.setup_otolokasi,user.setup_jam_kamin,"+
                "user.setup_embalase,user.tracer_login,user.display,user.set_harga_obat,user.set_penggunaan_tarif,user.set_oto_ralan,user.biaya_harian,"+
                "user.biaya_masuk_sekali,user.set_no_rm,user.billing_ralan,user.billing_ranap,user.jm_ranap_dokter,user.igd,user.barcoderalan,user.barcoderanap,"+
                "user.set_harga_obat_ralan,user.set_harga_obat_ranap,user.penyakit_pd3i,user.surveilans_pd3i,user.surveilans_ralan,user.diagnosa_pasien,"+
                "user.surveilans_ranap,user.pny_takmenular_ranap,user.pny_takmenular_ralan,user.kunjungan_ralan,user.rl32,user.rl33,user.rl37,user.rl38,"+
                "user.harian_tindakan_dokter,user.sms,user.sidikjari,user.jam_masuk,user.jadwal_pegawai,user.parkir_barcode,user.set_nota,user.dpjp_ranap,"+
                "user.mutasi_barang,user.rl34,user.rl36,user.fee_visit_dokter,user.fee_bacaan_ekg,user.fee_rujukan_rontgen,user.fee_rujukan_ranap,"+
                "user.fee_ralan,user.akun_bayar,user.bayar_pemesanan_obat,user.obat_per_dokter_peresep,user.ipsrs_jenis_barang,user.pemasukan_lain,"+
                "user.pengaturan_rekening,user.closing_kasir,user.keterlambatan_presensi,user.set_harga_kamar,user.rekap_per_shift,user.bpjs_cek_nik,"+
                "user.bpjs_cek_kartu,user.bpjs_cek_riwayat,user.obat_per_cara_bayar,user.kunjungan_ranap,user.bayar_piutang,user.payment_point,"+
                "user.bpjs_cek_nomor_rujukan,user.icd9,user.darurat_stok,user.retensi_rm,user.temporary_presensi,user.jurnal_harian,user.sirkulasi_obat2,"+
                "user.edit_registrasi,user.bpjs_referensi_diagnosa,user.bpjs_referensi_poli,user.industrifarmasi,user.harian_js,user.bulanan_js,"+
                "user.harian_paket_bhp,user.bulanan_paket_bhp,user.piutang_pasien2,user.bpjs_referensi_faskes,user.bpjs_sep,user.pengambilan_utd,"+
                "user.tarif_utd,user.pengambilan_utd2,user.utd_medis_rusak,user.pengambilan_penunjang_utd,user.pengambilan_penunjang_utd2,user.utd_penunjang_rusak,"+
                "user.suplier_penunjang,user.utd_donor,user.bpjs_monitoring_klaim,user.utd_cekal_darah,user.utd_komponen_darah,user.utd_stok_darah,"+
                "user.utd_pemisahan_darah,user.harian_kamar,user.rincian_piutang_pasien,user.keuntungan_beri_obat_nonpiutang,user.reklasifikasi_ralan,"+
                "user.reklasifikasi_ranap,user.utd_penyerahan_darah,user.hutang_obat,user.riwayat_obat_alkes_bhp,user.sensus_harian_poli,user.rl4a,"+
                "user.aplicare_referensi_kamar,user.aplicare_ketersediaan_kamar,user.inacbg_klaim_baru_otomatis,user.inacbg_klaim_baru_manual,"+
                "user.inacbg_coder_nik,user.mutasi_berkas,user.akun_piutang,user.harian_kso,user.bulanan_kso,user.harian_menejemen,user.bulanan_menejemen,"+
                "user.inhealth_cek_eligibilitas,user.inhealth_referensi_jenpel_ruang_rawat,user.inhealth_referensi_poli,user.inhealth_referensi_faskes,"+
                "user.inhealth_sjp,user.piutang_ralan,user.piutang_ranap,user.detail_piutang_penjab,user.lama_pelayanan_ralan,user.catatan_pasien,"+
                "user.rl4b,user.rl4asebab,user.rl4bsebab,user.data_HAIs,user.harian_HAIs,user.bulanan_HAIs,user.hitung_bor,user.perusahaan_pasien,"+
                "user.resep_dokter,user.lama_pelayanan_apotek,user.hitung_alos,user.detail_tindakan,user.rujukan_poli_internal,user.rekap_poli_anak,"+
                "user.grafik_kunjungan_poli,user.grafik_kunjungan_perdokter,user.grafik_kunjungan_perpekerjaan,user.grafik_kunjungan_perpendidikan,"+
                "user.grafik_kunjungan_pertahun,user.berkas_digital_perawatan,user.penyakit_menular_ranap,user.penyakit_menular_ralan,user.grafik_kunjungan_perbulan,"+
                "user.grafik_kunjungan_pertanggal,user.grafik_kunjungan_demografi,user.grafik_kunjungan_statusdaftartahun,user.grafik_kunjungan_statusdaftartahun2,"+
                "user.grafik_kunjungan_statusdaftarbulan,user.grafik_kunjungan_statusdaftarbulan2,user.grafik_kunjungan_statusdaftartanggal,"+
                "user.grafik_kunjungan_statusdaftartanggal2,user.grafik_kunjungan_statusbataltahun,user.grafik_kunjungan_statusbatalbulan,user.pcare_cek_penyakit,"+
                "user.grafik_kunjungan_statusbataltanggal,user.kategori_barang,user.golongan_barang,user.pemberian_obat_pertanggal,user.penjualan_obat_pertanggal,"+
                "user.pcare_cek_kesadaran,user.pembatalan_periksa_dokter,user.pembayaran_per_unit,user.rekap_pembayaran_per_unit,user.grafik_kunjungan_percarabayar,"+
                "user.ipsrs_pengadaan_pertanggal,user.ipsrs_stokkeluar_pertanggal,user.grafik_kunjungan_ranaptahun,user.pcare_cek_rujukan,user.grafik_lab_ralantahun,"+
                "user.grafik_rad_ralantahun,user.cek_entry_ralan,user.inacbg_klaim_baru_manual2,user.permintaan_medis,user.rekap_permintaan_medis,"+
                "user.surat_pemesanan_medis,user.permintaan_non_medis,user.rekap_permintaan_non_medis,user.surat_pemesanan_non_medis,user.grafik_per_perujuk,"+
                "user.bpjs_cek_prosedur,user.bpjs_cek_kelas_rawat,user.bpjs_cek_dokter,user.bpjs_cek_spesialistik,user.bpjs_cek_ruangrawat,user.bpjs_cek_carakeluar,"+
                "user.bpjs_cek_pasca_pulang,user.detail_tindakan_okvk,user.billing_parsial,user.bpjs_cek_nomor_rujukan_rs,user.bpjs_cek_rujukan_kartu_pcare,"+
                "user.bpjs_cek_rujukan_kartu_rs,user.akses_depo_obat,user.bpjs_rujukan_keluar,user.grafik_lab_ralanbulan,user.pengeluaran_stok_apotek,"+
                "user.grafik_rad_ralanbulan,user.detailjmdokter2,user.pengaduan_pasien,user.grafik_lab_ralanhari,user.grafik_rad_ralanhari,user.sensus_harian_ralan,"+
                "user.metode_racik,user.pembayaran_akun_bayar,user.pengguna_obat_resep,user.rekap_pemesanan,user.master_berkas_pegawai,user.berkas_kepegawaian,"+
                "user.riwayat_jabatan,user.riwayat_pendidikan,user.riwayat_naik_gaji,user.kegiatan_ilmiah,user.riwayat_penghargaan,user.riwayat_penelitian,"+
                "user.penerimaan_non_medis,user.bayar_pesan_non_medis,user.hutang_barang_non_medis,user.rekap_pemesanan_non_medis,user.insiden_keselamatan,"+
                "user.insiden_keselamatan_pasien,user.grafik_ikp_pertahun,user.grafik_ikp_perbulan,user.grafik_ikp_pertanggal,user.riwayat_data_batch,"+
                "user.grafik_ikp_jenis,user.grafik_ikp_dampak,user.piutang_akun_piutang,user.grafik_kunjungan_per_agama,user.grafik_kunjungan_per_umur,"+
                "user.suku_bangsa,user.bahasa_pasien,user.golongan_tni,user.satuan_tni,user.jabatan_tni,user.pangkat_tni,user.golongan_polri,user.satuan_polri,"+
                "user.jabatan_polri,user.pangkat_polri,user.cacat_fisik,user.grafik_kunjungan_suku,user.grafik_kunjungan_bahasa,user.booking_operasi,"+
                "user.mapping_poli_bpjs,user.grafik_kunjungan_per_cacat,user.barang_cssd,user.skdp_bpjs,user.booking_registrasi,user.bpjs_cek_propinsi,"+
                "user.bpjs_cek_kabupaten,user.bpjs_cek_kecamatan,user.bpjs_cek_dokterdpjp,user.bpjs_cek_riwayat_rujukanrs,user.bpjs_cek_tanggal_rujukan,"+
                "user.permintaan_lab,user.permintaan_radiologi,user.surat_indeks,user.surat_map,user.surat_almari,user.surat_rak,user.surat_ruang,"+
                "user.surat_klasifikasi,user.surat_status,user.surat_sifat,user.surat_balas,user.surat_masuk,user.pcare_cek_dokter,user.pcare_cek_poli,"+
                "user.pcare_cek_provider,user.pcare_cek_statuspulang,user.pcare_cek_spesialis,user.pcare_cek_subspesialis,user.pcare_cek_sarana,user.pcare_cek_khusus,"+
                "user.pcare_cek_obat,user.pcare_cek_tindakan,user.pcare_cek_faskessubspesialis,user.pcare_cek_faskesalihrawat,user.pcare_cek_faskesthalasemia,"+
                "user.pcare_mapping_obat,user.pcare_mapping_tindakan,user.pcare_club_prolanis,user.pcare_mapping_poli,user.pcare_kegiatan_kelompok,"+
                "user.pcare_mapping_tindakan_ranap,user.pcare_peserta_kegiatan_kelompok,user.sirkulasi_obat3,user.bridging_pcare_daftar,user.pcare_mapping_dokter,"+
                "user.ranap_per_ruang,user.penyakit_ranap_cara_bayar,user.anggota_militer_dirawat,user.set_input_parsial,user.lama_pelayanan_radiologi,"+
                "user.lama_pelayanan_lab,user.bpjs_cek_sep,user.catatan_perawatan,user.surat_keluar,user.kegiatan_farmasi,user.stok_opname_logistik,"+
                "user.sirkulasi_non_medis,user.rekap_lab_pertahun,user.perujuk_lab_pertahun,user.rekap_radiologi_pertahun,user.perujuk_radiologi_pertahun,"+
                "user.jumlah_porsi_diet,user.jumlah_macam_diet,user.payment_point2,user.pembayaran_akun_bayar2,user.hapus_nota_salah,user.pengkajian_askep,"+
                "user.hais_perbangsal,user.ppn_obat,user.saldo_akun_perbulan,user.display_apotek,user.sisrute_referensi_faskes,user.sisrute_referensi_alasanrujuk,"+
                "user.sisrute_referensi_diagnosa,user.sisrute_rujukan_masuk,user.sisrute_rujukan_keluar,user.bpjs_cek_skdp,user.data_batch,user.kunjungan_permintaan_lab,"+
                "user.kunjungan_permintaan_lab2,user.kunjungan_permintaan_radiologi,user.kunjungan_permintaan_radiologi2,user.pcare_pemberian_obat,"+
                "user.pcare_pemberian_tindakan,user.pembayaran_akun_bayar3,user.password_asuransi,user.kemenkes_sitt,user.siranap_ketersediaan_kamar,"+
                "user.grafik_tb_periodelaporan,user.grafik_tb_rujukan,user.grafik_tb_riwayat,user.grafik_tb_tipediagnosis,user.grafik_tb_statushiv,"+
                "user.grafik_tb_skoringanak,user.grafik_tb_konfirmasiskoring5,user.grafik_tb_konfirmasiskoring6,user.grafik_tb_sumberobat,"+
                "user.grafik_tb_hasilakhirpengobatan,user.grafik_tb_hasilteshiv,user.kadaluarsa_batch,user.sisa_stok,user.obat_per_resep,user.pemakaian_air_pdam,"+
                "user.limbah_b3_medis,user.grafik_air_pdam_pertanggal,user.grafik_air_pdam_perbulan,user.grafik_limbahb3_pertanggal,user.grafik_limbahb3_perbulan,"+
                "user.limbah_domestik,user.grafik_limbahdomestik_pertanggal,user.grafik_limbahdomestik_perbulan,user.mutu_air_limbah,user.pest_control,"+
                "user.ruang_perpustakaan,user.kategori_perpustakaan,user.jenis_perpustakaan,user.pengarang_perpustakaan,user.penerbit_perpustakaan,"+
                "user.koleksi_perpustakaan,user.inventaris_perpustakaan,user.set_peminjaman_perpustakaan,user.denda_perpustakaan,user.anggota_perpustakaan,"+
                "user.peminjaman_perpustakaan,user.bayar_denda_perpustakaan,user.ebook_perpustakaan,user.jenis_cidera_k3rs,user.penyebab_k3rs,user.jenis_luka_k3rs,"+
                "user.lokasi_kejadian_k3rs,user.dampak_cidera_k3rs,user.jenis_pekerjaan_k3rs,user.bagian_tubuh_k3rs,user.peristiwa_k3rs,user.grafik_k3_pertahun,"+
                "user.grafik_k3_perbulan,user.grafik_k3_pertanggal,user.grafik_k3_perjeniscidera,user.grafik_k3_perpenyebab,user.grafik_k3_perjenisluka,"+
                "user.grafik_k3_lokasikejadian,user.grafik_k3_dampakcidera,user.grafik_k3_perjenispekerjaan,user.grafik_k3_perbagiantubuh,user.jenis_cidera_k3rstahun,"+
                "user.penyebab_k3rstahun,user.jenis_luka_k3rstahun,user.lokasi_kejadian_k3rstahun,user.dampak_cidera_k3rstahun,user.jenis_pekerjaan_k3rstahun,"+
                "user.bagian_tubuh_k3rstahun,user.sekrining_rawat_jalan,user.bpjs_histori_pelayanan,user.rekap_mutasi_berkas,user.skrining_ralan_pernapasan_pertahun,"+
                "user.pengajuan_barang_medis,user.pengajuan_barang_nonmedis,user.grafik_kunjungan_ranapbulan,user.grafik_kunjungan_ranaptanggal,"+
                "user.grafik_kunjungan_ranap_peruang,user.kunjungan_bangsal_pertahun,user.grafik_jenjang_jabatanpegawai,user.grafik_bidangpegawai,"+
                "user.grafik_departemenpegawai,user.grafik_pendidikanpegawai,user.grafik_sttswppegawai,user.grafik_sttskerjapegawai,user.grafik_sttspulangranap,"+
                "user.kip_pasien_ranap,user.kip_pasien_ralan,user.bpjs_mapping_dokterdpjp,user.data_triase_igd,user.master_triase_skala1,user.master_triase_skala2,"+
                "user.master_triase_skala3,user.master_triase_skala4,user.master_triase_skala5,user.master_triase_pemeriksaan,user.master_triase_macamkasus,"+
                "user.rekap_permintaan_diet,user.daftar_pasien_ranap,user.daftar_pasien_ranaptni,user.pengajuan_asetinventaris,user.item_apotek_jenis,"+
                "user.item_apotek_kategori,user.item_apotek_golongan,user.item_apotek_industrifarmasi,user.10_obat_terbanyak_poli,user.grafik_pengajuan_aset_urgensi,"+
                "user.grafik_pengajuan_aset_status,user.grafik_pengajuan_aset_departemen,user.rekap_pengajuan_aset_departemen,user.grafik_kelompok_jabatanpegawai,"+
                "user.grafik_resiko_kerjapegawai,user.grafik_emergency_indexpegawai,user.grafik_inventaris_ruang,user.harian_HAIs2,user.grafik_inventaris_jenis,"+
                "user.data_resume_pasien,user.perkiraan_biaya_ranap,user.rekap_obat_poli,user.rekap_obat_pasien,user.permintaan_perbaikan_inventaris,"+
                "user.grafik_HAIs_pasienbangsal,user.grafik_HAIs_pasienbulan,user.grafik_HAIs_laju_vap,user.grafik_HAIs_laju_iad,user.grafik_HAIs_laju_pleb,"+
                "user.grafik_HAIs_laju_isk,user.grafik_HAIs_laju_ilo,user.grafik_HAIs_laju_hap,user.inhealth_mapping_poli,user.inhealth_mapping_dokter,"+
                "user.inhealth_mapping_tindakan_ralan,user.inhealth_mapping_tindakan_ranap,user.inhealth_mapping_tindakan_radiologi,user.inhealth_mapping_tindakan_laborat,"+
                "user.inhealth_mapping_tindakan_operasi,user.hibah_obat_bhp,user.asal_hibah,user.asuhan_gizi,user.inhealth_kirim_tagihan,user.sirkulasi_obat4,"+
                "user.sirkulasi_obat5,user.sirkulasi_non_medis2,user.monitoring_asuhan_gizi,user.penerimaan_obat_perbulan,user.rekap_kunjungan,user.surat_sakit,"+
                "user.penilaian_awal_keperawatan_ralan,user.permintaan_diet,user.master_masalah_keperawatan,user.pengajuan_cuti,user.kedatangan_pasien,user.utd_pendonor,"+
                "user.toko_suplier,user.toko_jenis,user.toko_set_harga,user.toko_barang,user.penagihan_piutang_pasien,user.akun_penagihan_piutang,user.stok_opname_toko,"+
                "user.toko_riwayat_barang,user.toko_surat_pemesanan,user.toko_pengajuan_barang,user.toko_penerimaan_barang,user.toko_pengadaan_barang,user.toko_hutang,"+
                "user.toko_bayar_pemesanan,user.toko_member,user.toko_penjualan,user.registrasi_poli_per_tanggal,user.toko_piutang,user.toko_retur_beli,user.ipsrs_returbeli,"+
                "user.ipsrs_riwayat_barang,user.pasien_corona,user.toko_pendapatan_harian,user.diagnosa_pasien_corona,user.perawatan_pasien_corona,"+
                "user.penilaian_awal_keperawatan_gigi,user.master_masalah_keperawatan_gigi,user.toko_bayar_piutang,user.toko_piutang_harian,user.toko_penjualan_harian,"+
                "user.deteksi_corona,user.penilaian_awal_keperawatan_kebidanan,user.pengumuman_epasien,user.surat_hamil,user.set_tarif_online,user.booking_periksa,"+
                "user.toko_sirkulasi,user.toko_retur_jual,user.toko_retur_piutang,user.toko_sirkulasi2,user.toko_keuntungan_barang,user.zis_pengeluaran_penerima_dankes,"+
                "user.zis_penghasilan_penerima_dankes,user.zis_ukuran_rumah_penerima_dankes,user.zis_dinding_rumah_penerima_dankes,user.zis_lantai_rumah_penerima_dankes,"+
                "user.zis_atap_rumah_penerima_dankes,user.zis_kepemilikan_rumah_penerima_dankes,user.zis_kamar_mandi_penerima_dankes,user.zis_dapur_rumah_penerima_dankes,"+
                "user.zis_kursi_rumah_penerima_dankes,user.zis_kategori_phbs_penerima_dankes,user.zis_elektronik_penerima_dankes,user.zis_ternak_penerima_dankes,"+
                "user.zis_jenis_simpanan_penerima_dankes,user.penilaian_awal_keperawatan_anak,user.zis_kategori_asnaf_penerima_dankes,user.master_masalah_keperawatan_anak,"+
                "user.master_imunisasi,user.zis_patologis_penerima_dankes,user.pcare_cek_kartu,user.surat_bebas_narkoba,user.surat_keterangan_covid,user.pemakaian_air_tanah,"+
                "user.grafik_air_tanah_pertanggal,user.grafik_air_tanah_perbulan,user.lama_pelayanan_poli,user.hemodialisa,user.laporan_tahunan_irj,"+
                "user.grafik_harian_hemodialisa,user.grafik_bulanan_hemodialisa,user.grafik_tahunan_hemodialisa,user.grafik_bulanan_meninggal,user.perbaikan_inventaris,"+
                "user.surat_cuti_hamil,user.permintaan_stok_obat_pasien,user.pemeliharaan_inventaris,user.klasifikasi_pasien_ranap,user.bulanan_klasifikasi_pasien_ranap,"+
                "user.harian_klasifikasi_pasien_ranap,user.klasifikasi_pasien_perbangsal,user.soap_perawatan,user.klaim_rawat_jalan,user.skrining_gizi,"+
                "user.lama_penyiapan_rm,user.dosis_radiologi,user.demografi_umur_kunjungan,user.jam_diet_pasien,user.rvu_bpjs,user.verifikasi_penerimaan_farmasi,"+
                "user.verifikasi_penerimaan_logistik,user.pemeriksaan_lab_pa,user.ringkasan_pengajuan_obat,user.ringkasan_pemesanan_obat,user.ringkasan_pengadaan_obat,"+
                "user.ringkasan_penerimaan_obat,user.ringkasan_hibah_obat,user.ringkasan_penjualan_obat,user.ringkasan_beri_obat,user.ringkasan_piutang_obat,"+
                "user.ringkasan_stok_keluar_obat,user.ringkasan_retur_suplier_obat,user.ringkasan_retur_pembeli_obat,user.penilaian_awal_keperawatan_ranapkebidanan,"+
                "user.ringkasan_pengajuan_nonmedis,user.ringkasan_pemesanan_nonmedis,user.ringkasan_pengadaan_nonmedis,user.ringkasan_penerimaan_nonmedis,"+
                "user.ringkasan_stokkeluar_nonmedis,user.ringkasan_returbeli_nonmedis,user.omset_penerimaan,user.validasi_penagihan_piutang,user.permintaan_ranap,"+
                "user.bpjs_diagnosa_prb,user.bpjs_obat_prb,user.bpjs_surat_kontrol,user.penggunaan_bhp_ok,user.surat_keterangan_rawat_inap,user.surat_keterangan_sehat,"+
                "user.pendapatan_per_carabayar,user.akun_host_to_host_bank_jateng,user.pembayaran_bank_jateng,user.bpjs_surat_pri,user.ringkasan_tindakan,"+
                "user.lama_pelayanan_pasien,user.surat_sakit_pihak_2,user.tagihan_hutang_obat,user.referensi_mobilejkn_bpjs,user.batal_pendaftaran_mobilejkn_bpjs,"+
                "user.lama_operasi,user.grafik_inventaris_kategori,user.grafik_inventaris_merk,user.grafik_inventaris_produsen,user.pengembalian_deposit_pasien,"+
                "user.validasi_tagihan_hutang_obat,user.piutang_obat_belum_lunas,user.integrasi_briapi,user.pengadaan_aset_inventaris,user.akun_aset_inventaris,"+
                "user.suplier_inventaris,user.penerimaan_aset_inventaris,user.bayar_pemesanan_iventaris,user.hutang_aset_inventaris,user.hibah_aset_inventaris,"+
                "user.titip_faktur_non_medis,user.validasi_tagihan_non_medis,user.titip_faktur_aset,user.validasi_tagihan_aset,user.hibah_non_medis,"+
                "user.pcare_alasan_tacc,user.resep_luar,user.surat_bebas_tbc,user.surat_buta_warna,user.surat_bebas_tato,user.surat_kewaspadaan_kesehatan,"+
                "user.grafik_porsidiet_pertanggal,user.grafik_porsidiet_perbulan,user.grafik_porsidiet_pertahun,user.grafik_porsidiet_perbangsal,"+
                "user.penilaian_awal_medis_ralan,user.master_masalah_keperawatan_mata,user.penilaian_awal_keperawatan_mata,user.penilaian_awal_medis_ranap,"+
                "user.penilaian_awal_medis_ranap_kebidanan,user.penilaian_awal_medis_ralan_kebidanan,user.penilaian_awal_medis_igd,user.penilaian_awal_medis_ralan_anak,"+
                "user.bpjs_referensi_poli_hfis,user.bpjs_referensi_dokter_hfis,user.bpjs_referensi_jadwal_hfis,user.penilaian_fisioterapi,user.bpjs_program_prb,"+
                "user.bpjs_suplesi_jasaraharja,user.bpjs_data_induk_kecelakaan,user.bpjs_sep_internal,user.bpjs_klaim_jasa_raharja,user.bpjs_daftar_finger_print,"+
                "user.bpjs_rujukan_khusus,user.pemeliharaan_gedung,user.grafik_perbaikan_inventaris_pertanggal,user.grafik_perbaikan_inventaris_perbulan,"+
                "user.grafik_perbaikan_inventaris_pertahun,user.grafik_perbaikan_inventaris_perpelaksana_status,user.penilaian_mcu,user.peminjam_piutang,"+
                "user.piutang_lainlain,user.cara_bayar,user.audit_kepatuhan_apd,user.bpjs_task_id,user.bayar_piutang_lain,user.pembayaran_akun_bayar4,"+
                "user.stok_akhir_farmasi_pertanggal,user.riwayat_kamar_pasien,user.uji_fungsi_kfr,user.hapus_berkas_digital_perawatan,user.kategori_pengeluaran_harian,"+
                "user.kategori_pemasukan_lain,user.pembayaran_akun_bayar5,user.ruang_ok,user.telaah_resep,user.jasa_tindakan_pasien,user.permintaan_resep_pulang,"+
                "user.rekap_jm_dokter,user.status_data_rm,user.ubah_petugas_lab_pk,user.ubah_petugas_lab_pa,user.ubah_petugas_radiologi,user.gabung_norawat,"+
                "user.gabung_rm,user.ringkasan_biaya_obat_pasien_pertanggal,user.master_masalah_keperawatan_igd,user.penilaian_awal_keperawatan_igd,"+
                "user.bpjs_referensi_dpho_apotek,user.bpjs_referensi_poli_apotek,user.bayar_jm_dokter,user.bpjs_referensi_faskes_apotek,user.bpjs_referensi_spesialistik_apotek,"+
                "user.pembayaran_briva,user.penilaian_awal_keperawatan_ranap,user.nilai_penerimaan_vendor_farmasi_perbulan,user.akun_bayar_hutang,user.master_rencana_keperawatan,"+
                "user.laporan_tahunan_igd,user.obat_bhp_tidakbergerak,user.ringkasan_hutang_vendor_farmasi,user.nilai_penerimaan_vendor_nonmedis_perbulan,"+
                "user.ringkasan_hutang_vendor_nonmedis,user.master_rencana_keperawatan_anak,user.anggota_polri_dirawat,user.daftar_pasien_ranap_polri,user.soap_ralan_polri,"+
                "user.soap_ranap_polri,user.laporan_penyakit_polri,user.jumlah_pengunjung_ralan_polri,user.catatan_observasi_igd,user.catatan_observasi_ranap,"+
                "user.catatan_observasi_ranap_kebidanan,user.catatan_observasi_ranap_postpartum,user.penilaian_awal_medis_ralan_tht,user.penilaian_psikologi,"+
                "user.audit_cuci_tangan_medis,user.audit_pembuangan_limbah,user.ruang_audit_kepatuhan,user.audit_pembuangan_benda_tajam,user.audit_penanganan_darah,"+
                "user.audit_pengelolaan_linen_kotor,user.audit_penempatan_pasien,user.audit_kamar_jenazah,user.audit_bundle_iadp,user.audit_bundle_ido,"+
                "user.audit_fasilitas_kebersihan_tangan,user.audit_fasilitas_apd,user.audit_pembuangan_limbah_cair_infeksius,user.audit_sterilisasi_alat,"+
                "user.penilaian_awal_medis_ralan_psikiatri,user.persetujuan_penolakan_tindakan from user order by AES_DECRYPT(user.id_user,'nur')");
            try {
                rs=ps.executeQuery();
                while(rs.next()){
                    user="";
                    user=Sequel.cariIsi("select dokter.nm_dokter from dokter where dokter.kd_dokter=?",rs.getString(1));
                    jabatan=Sequel.cariIsi("select spesialis.nm_sps from spesialis where spesialis.kd_sps=?",Sequel.cariIsi("select dokter.kd_sps from dokter where dokter.kd_dokter=?",rs.getString(1)));
                    if(user.equals("")){    
                        user=Sequel.cariIsi("select petugas.nama from petugas where petugas.nip=?",rs.getString(1));
                        jabatan=Sequel.cariIsi("select jabatan.nm_jbtn from jabatan where jabatan.kd_jbtn=?",Sequel.cariIsi("select petugas.kd_jbtn from petugas where petugas.nip=?",rs.getString(1)));
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
                               rs.getBoolean("ebook_perpustakaan"),
                               rs.getBoolean("jenis_cidera_k3rs"),
                               rs.getBoolean("penyebab_k3rs"),
                               rs.getBoolean("jenis_luka_k3rs"),
                               rs.getBoolean("lokasi_kejadian_k3rs"),
                               rs.getBoolean("dampak_cidera_k3rs"),
                               rs.getBoolean("jenis_pekerjaan_k3rs"),
                               rs.getBoolean("bagian_tubuh_k3rs"),
                               rs.getBoolean("peristiwa_k3rs"),
                               rs.getBoolean("grafik_k3_pertahun"),
                               rs.getBoolean("grafik_k3_perbulan"),
                               rs.getBoolean("grafik_k3_pertanggal"),
                               rs.getBoolean("grafik_k3_perjeniscidera"),
                               rs.getBoolean("grafik_k3_perpenyebab"),
                               rs.getBoolean("grafik_k3_perjenisluka"),
                               rs.getBoolean("grafik_k3_lokasikejadian"),
                               rs.getBoolean("grafik_k3_dampakcidera"),
                               rs.getBoolean("grafik_k3_perjenispekerjaan"),
                               rs.getBoolean("grafik_k3_perbagiantubuh"),
                               rs.getBoolean("jenis_cidera_k3rstahun"),
                               rs.getBoolean("penyebab_k3rstahun"),
                               rs.getBoolean("jenis_luka_k3rstahun"),
                               rs.getBoolean("lokasi_kejadian_k3rstahun"),
                               rs.getBoolean("dampak_cidera_k3rstahun"),
                               rs.getBoolean("jenis_pekerjaan_k3rstahun"),
                               rs.getBoolean("bagian_tubuh_k3rstahun"),
                               rs.getBoolean("sekrining_rawat_jalan"),
                               rs.getBoolean("bpjs_histori_pelayanan"),
                               rs.getBoolean("rekap_mutasi_berkas"),
                               rs.getBoolean("skrining_ralan_pernapasan_pertahun"),
                               rs.getBoolean("pengajuan_barang_medis"),
                               rs.getBoolean("pengajuan_barang_nonmedis"),
                               rs.getBoolean("grafik_kunjungan_ranapbulan"),
                               rs.getBoolean("grafik_kunjungan_ranaptanggal"),
                               rs.getBoolean("grafik_kunjungan_ranap_peruang"),
                               rs.getBoolean("kunjungan_bangsal_pertahun"),
                               rs.getBoolean("grafik_jenjang_jabatanpegawai"),
                               rs.getBoolean("grafik_bidangpegawai"),
                               rs.getBoolean("grafik_departemenpegawai"),
                               rs.getBoolean("grafik_pendidikanpegawai"),
                               rs.getBoolean("grafik_sttswppegawai"),
                               rs.getBoolean("grafik_sttskerjapegawai"),
                               rs.getBoolean("grafik_sttspulangranap"),
                               rs.getBoolean("kip_pasien_ranap"),
                               rs.getBoolean("kip_pasien_ralan"),
                               rs.getBoolean("bpjs_mapping_dokterdpjp"),
                               rs.getBoolean("data_triase_igd"),
                               rs.getBoolean("master_triase_skala1"),
                               rs.getBoolean("master_triase_skala2"),
                               rs.getBoolean("master_triase_skala3"),
                               rs.getBoolean("master_triase_skala4"),
                               rs.getBoolean("master_triase_skala5"),
                               rs.getBoolean("master_triase_pemeriksaan"),
                               rs.getBoolean("master_triase_macamkasus"),
                               rs.getBoolean("rekap_permintaan_diet"),
                               rs.getBoolean("daftar_pasien_ranap"),
                               rs.getBoolean("daftar_pasien_ranaptni"),
                               rs.getBoolean("pengajuan_asetinventaris"),
                               rs.getBoolean("item_apotek_jenis"),
                               rs.getBoolean("item_apotek_kategori"),
                               rs.getBoolean("item_apotek_golongan"),
                               rs.getBoolean("item_apotek_industrifarmasi"),
                               rs.getBoolean("10_obat_terbanyak_poli"),
                               rs.getBoolean("grafik_pengajuan_aset_urgensi"),
                               rs.getBoolean("grafik_pengajuan_aset_status"),
                               rs.getBoolean("grafik_pengajuan_aset_departemen"),
                               rs.getBoolean("rekap_pengajuan_aset_departemen"),
                               rs.getBoolean("grafik_kelompok_jabatanpegawai"),
                               rs.getBoolean("grafik_resiko_kerjapegawai"),
                               rs.getBoolean("grafik_emergency_indexpegawai"),
                               rs.getBoolean("grafik_inventaris_ruang"),
                               rs.getBoolean("harian_HAIs2"),
                               rs.getBoolean("grafik_inventaris_jenis"),
                               rs.getBoolean("data_resume_pasien"),
                               rs.getBoolean("perkiraan_biaya_ranap"),
                               rs.getBoolean("rekap_obat_poli"),
                               rs.getBoolean("rekap_obat_pasien"),
                               rs.getBoolean("permintaan_perbaikan_inventaris"),
                               rs.getBoolean("grafik_HAIs_pasienbangsal"),
                               rs.getBoolean("grafik_HAIs_pasienbulan"),
                               rs.getBoolean("grafik_HAIs_laju_vap"),
                               rs.getBoolean("grafik_HAIs_laju_iad"),
                               rs.getBoolean("grafik_HAIs_laju_pleb"),
                               rs.getBoolean("grafik_HAIs_laju_isk"),
                               rs.getBoolean("grafik_HAIs_laju_ilo"),
                               rs.getBoolean("grafik_HAIs_laju_hap"),
                               rs.getBoolean("inhealth_mapping_poli"),
                               rs.getBoolean("inhealth_mapping_dokter"),
                               rs.getBoolean("inhealth_mapping_tindakan_ralan"),
                               rs.getBoolean("inhealth_mapping_tindakan_ranap"),
                               rs.getBoolean("inhealth_mapping_tindakan_radiologi"),
                               rs.getBoolean("inhealth_mapping_tindakan_laborat"),
                               rs.getBoolean("inhealth_mapping_tindakan_operasi"),
                               rs.getBoolean("hibah_obat_bhp"),
                               rs.getBoolean("asal_hibah"),
                               rs.getBoolean("asuhan_gizi"),
                               rs.getBoolean("inhealth_kirim_tagihan"),
                               rs.getBoolean("sirkulasi_obat4"),
                               rs.getBoolean("sirkulasi_obat5"),
                               rs.getBoolean("sirkulasi_non_medis2"),
                               rs.getBoolean("monitoring_asuhan_gizi"),
                               rs.getBoolean("penerimaan_obat_perbulan"),
                               rs.getBoolean("rekap_kunjungan"),
                               rs.getBoolean("surat_sakit"),
                               rs.getBoolean("penilaian_awal_keperawatan_ralan"),
                               rs.getBoolean("permintaan_diet"),
                               rs.getBoolean("master_masalah_keperawatan"),
                               rs.getBoolean("pengajuan_cuti"),
                               rs.getBoolean("kedatangan_pasien"),
                               rs.getBoolean("utd_pendonor"),
                               rs.getBoolean("toko_suplier"),
                               rs.getBoolean("toko_jenis"),
                               rs.getBoolean("toko_set_harga"),
                               rs.getBoolean("toko_barang"),
                               rs.getBoolean("penagihan_piutang_pasien"),
                               rs.getBoolean("akun_penagihan_piutang"),
                               rs.getBoolean("stok_opname_toko"),
                               rs.getBoolean("toko_riwayat_barang"),
                               rs.getBoolean("toko_surat_pemesanan"),
                               rs.getBoolean("toko_pengajuan_barang"),
                               rs.getBoolean("toko_penerimaan_barang"),
                               rs.getBoolean("toko_pengadaan_barang"),
                               rs.getBoolean("toko_hutang"),
                               rs.getBoolean("toko_bayar_pemesanan"),
                               rs.getBoolean("toko_member"),
                               rs.getBoolean("toko_penjualan"),
                               rs.getBoolean("registrasi_poli_per_tanggal"),
                               rs.getBoolean("toko_piutang"),
                               rs.getBoolean("toko_retur_beli"),
                               rs.getBoolean("ipsrs_returbeli"),
                               rs.getBoolean("ipsrs_riwayat_barang"),
                               rs.getBoolean("pasien_corona"),
                               rs.getBoolean("toko_pendapatan_harian"),
                               rs.getBoolean("diagnosa_pasien_corona"),
                               rs.getBoolean("perawatan_pasien_corona"),
                               rs.getBoolean("penilaian_awal_keperawatan_gigi"),
                               rs.getBoolean("master_masalah_keperawatan_gigi"),
                               rs.getBoolean("toko_bayar_piutang"),
                               rs.getBoolean("toko_piutang_harian"),
                               rs.getBoolean("toko_penjualan_harian"),
                               rs.getBoolean("deteksi_corona"),
                               rs.getBoolean("penilaian_awal_keperawatan_kebidanan"),
                               rs.getBoolean("pengumuman_epasien"),
                               rs.getBoolean("surat_hamil"),
                               rs.getBoolean("set_tarif_online"),
                               rs.getBoolean("booking_periksa"),
                               rs.getBoolean("toko_sirkulasi"),
                               rs.getBoolean("toko_retur_jual"),
                               rs.getBoolean("toko_retur_piutang"),
                               rs.getBoolean("toko_sirkulasi2"),
                               rs.getBoolean("toko_keuntungan_barang"),
                               rs.getBoolean("zis_pengeluaran_penerima_dankes"),
                               rs.getBoolean("zis_penghasilan_penerima_dankes"),
                               rs.getBoolean("zis_ukuran_rumah_penerima_dankes"),
                               rs.getBoolean("zis_dinding_rumah_penerima_dankes"),
                               rs.getBoolean("zis_lantai_rumah_penerima_dankes"),
                               rs.getBoolean("zis_atap_rumah_penerima_dankes"),
                               rs.getBoolean("zis_kepemilikan_rumah_penerima_dankes"),
                               rs.getBoolean("zis_kamar_mandi_penerima_dankes"),
                               rs.getBoolean("zis_dapur_rumah_penerima_dankes"),
                               rs.getBoolean("zis_kursi_rumah_penerima_dankes"),
                               rs.getBoolean("zis_kategori_phbs_penerima_dankes"),
                               rs.getBoolean("zis_elektronik_penerima_dankes"),
                               rs.getBoolean("zis_ternak_penerima_dankes"),
                               rs.getBoolean("zis_jenis_simpanan_penerima_dankes"),
                               rs.getBoolean("penilaian_awal_keperawatan_anak"),
                               rs.getBoolean("zis_kategori_asnaf_penerima_dankes"),
                               rs.getBoolean("master_masalah_keperawatan_anak"),
                               rs.getBoolean("master_imunisasi"),
                               rs.getBoolean("zis_patologis_penerima_dankes"),
                               rs.getBoolean("pcare_cek_kartu"),
                               rs.getBoolean("surat_bebas_narkoba"),
                               rs.getBoolean("surat_keterangan_covid"),
                               rs.getBoolean("pemakaian_air_tanah"),
                               rs.getBoolean("grafik_air_tanah_pertanggal"),
                               rs.getBoolean("grafik_air_tanah_perbulan"),
                               rs.getBoolean("lama_pelayanan_poli"),
                               rs.getBoolean("hemodialisa"),
                               rs.getBoolean("laporan_tahunan_irj"),
                               rs.getBoolean("grafik_harian_hemodialisa"),
                               rs.getBoolean("grafik_bulanan_hemodialisa"),
                               rs.getBoolean("grafik_tahunan_hemodialisa"),
                               rs.getBoolean("grafik_bulanan_meninggal"),
                               rs.getBoolean("perbaikan_inventaris"),
                               rs.getBoolean("surat_cuti_hamil"),
                               rs.getBoolean("permintaan_stok_obat_pasien"),
                               rs.getBoolean("pemeliharaan_inventaris"),
                               rs.getBoolean("klasifikasi_pasien_ranap"),
                               rs.getBoolean("bulanan_klasifikasi_pasien_ranap"),
                               rs.getBoolean("harian_klasifikasi_pasien_ranap"),
                               rs.getBoolean("klasifikasi_pasien_perbangsal"),
                               rs.getBoolean("soap_perawatan"),
                               rs.getBoolean("klaim_rawat_jalan"),
                               rs.getBoolean("skrining_gizi"),
                               rs.getBoolean("lama_penyiapan_rm"),
                               rs.getBoolean("dosis_radiologi"),
                               rs.getBoolean("demografi_umur_kunjungan"),
                               rs.getBoolean("jam_diet_pasien"),
                               rs.getBoolean("rvu_bpjs"),
                               rs.getBoolean("verifikasi_penerimaan_farmasi"),
                               rs.getBoolean("verifikasi_penerimaan_logistik"),
                               rs.getBoolean("pemeriksaan_lab_pa"),
                               rs.getBoolean("ringkasan_pengajuan_obat"),
                               rs.getBoolean("ringkasan_pemesanan_obat"),
                               rs.getBoolean("ringkasan_pengadaan_obat"),
                               rs.getBoolean("ringkasan_penerimaan_obat"),
                               rs.getBoolean("ringkasan_hibah_obat"),
                               rs.getBoolean("ringkasan_penjualan_obat"),
                               rs.getBoolean("ringkasan_beri_obat"),
                               rs.getBoolean("ringkasan_piutang_obat"),
                               rs.getBoolean("ringkasan_stok_keluar_obat"),
                               rs.getBoolean("ringkasan_retur_suplier_obat"),
                               rs.getBoolean("ringkasan_retur_pembeli_obat"),
                               rs.getBoolean("penilaian_awal_keperawatan_ranapkebidanan"),
                               rs.getBoolean("ringkasan_pengajuan_nonmedis"),
                               rs.getBoolean("ringkasan_pemesanan_nonmedis"),
                               rs.getBoolean("ringkasan_pengadaan_nonmedis"),
                               rs.getBoolean("ringkasan_penerimaan_nonmedis"),
                               rs.getBoolean("ringkasan_stokkeluar_nonmedis"),
                               rs.getBoolean("ringkasan_returbeli_nonmedis"),
                               rs.getBoolean("omset_penerimaan"),
                               rs.getBoolean("validasi_penagihan_piutang"),
                               rs.getBoolean("permintaan_ranap"),
                               rs.getBoolean("bpjs_diagnosa_prb"),
                               rs.getBoolean("bpjs_obat_prb"),
                               rs.getBoolean("bpjs_surat_kontrol"),
                               rs.getBoolean("penggunaan_bhp_ok"),
                               rs.getBoolean("surat_keterangan_rawat_inap"),
                               rs.getBoolean("surat_keterangan_sehat"),
                               rs.getBoolean("pendapatan_per_carabayar"),
                               rs.getBoolean("akun_host_to_host_bank_jateng"),
                               rs.getBoolean("pembayaran_bank_jateng"),
                               rs.getBoolean("bpjs_surat_pri"),
                               rs.getBoolean("ringkasan_tindakan"),
                               rs.getBoolean("lama_pelayanan_pasien"),
                               rs.getBoolean("surat_sakit_pihak_2"),
                               rs.getBoolean("tagihan_hutang_obat"),
                               rs.getBoolean("referensi_mobilejkn_bpjs"),
                               rs.getBoolean("batal_pendaftaran_mobilejkn_bpjs"),
                               rs.getBoolean("lama_operasi"),
                               rs.getBoolean("grafik_inventaris_kategori"),
                               rs.getBoolean("grafik_inventaris_merk"),
                               rs.getBoolean("grafik_inventaris_produsen"),
                               rs.getBoolean("pengembalian_deposit_pasien"),
                               rs.getBoolean("validasi_tagihan_hutang_obat"),
                               rs.getBoolean("piutang_obat_belum_lunas"),
                               rs.getBoolean("integrasi_briapi"),
                               rs.getBoolean("pengadaan_aset_inventaris"),
                               rs.getBoolean("akun_aset_inventaris"),
                               rs.getBoolean("suplier_inventaris"),
                               rs.getBoolean("penerimaan_aset_inventaris"),
                               rs.getBoolean("bayar_pemesanan_iventaris"),
                               rs.getBoolean("hutang_aset_inventaris"),
                               rs.getBoolean("hibah_aset_inventaris"),
                               rs.getBoolean("titip_faktur_non_medis"),
                               rs.getBoolean("validasi_tagihan_non_medis"),
                               rs.getBoolean("titip_faktur_aset"),
                               rs.getBoolean("validasi_tagihan_aset"),
                               rs.getBoolean("hibah_non_medis"),
                               rs.getBoolean("pcare_alasan_tacc"),
                               rs.getBoolean("resep_luar"),
                               rs.getBoolean("surat_bebas_tbc"),
                               rs.getBoolean("surat_buta_warna"),
                               rs.getBoolean("surat_bebas_tato"),
                               rs.getBoolean("surat_kewaspadaan_kesehatan"),
                               rs.getBoolean("grafik_porsidiet_pertanggal"),
                               rs.getBoolean("grafik_porsidiet_perbulan"),
                               rs.getBoolean("grafik_porsidiet_pertahun"),
                               rs.getBoolean("grafik_porsidiet_perbangsal"),
                               rs.getBoolean("penilaian_awal_medis_ralan"),
                               rs.getBoolean("master_masalah_keperawatan_mata"),
                               rs.getBoolean("penilaian_awal_keperawatan_mata"),
                               rs.getBoolean("penilaian_awal_medis_ranap"),
                               rs.getBoolean("penilaian_awal_medis_ranap_kebidanan"),
                               rs.getBoolean("penilaian_awal_medis_ralan_kebidanan"),
                               rs.getBoolean("penilaian_awal_medis_igd"),
                               rs.getBoolean("penilaian_awal_medis_ralan_anak"),
                               rs.getBoolean("bpjs_referensi_poli_hfis"),
                               rs.getBoolean("bpjs_referensi_dokter_hfis"),
                               rs.getBoolean("bpjs_referensi_jadwal_hfis"),
                               rs.getBoolean("penilaian_fisioterapi"),
                               rs.getBoolean("bpjs_program_prb"),
                               rs.getBoolean("bpjs_suplesi_jasaraharja"),
                               rs.getBoolean("bpjs_data_induk_kecelakaan"),
                               rs.getBoolean("bpjs_sep_internal"),
                               rs.getBoolean("bpjs_klaim_jasa_raharja"),
                               rs.getBoolean("bpjs_daftar_finger_print"),
                               rs.getBoolean("bpjs_rujukan_khusus"),
                               rs.getBoolean("pemeliharaan_gedung"),
                               rs.getBoolean("grafik_perbaikan_inventaris_pertanggal"),
                               rs.getBoolean("grafik_perbaikan_inventaris_perbulan"),
                               rs.getBoolean("grafik_perbaikan_inventaris_pertahun"),
                               rs.getBoolean("grafik_perbaikan_inventaris_perpelaksana_status"),
                               rs.getBoolean("penilaian_mcu"),
                               rs.getBoolean("peminjam_piutang"),
                               rs.getBoolean("piutang_lainlain"),
                               rs.getBoolean("cara_bayar"),
                               rs.getBoolean("audit_kepatuhan_apd"),
                               rs.getBoolean("bpjs_task_id"),
                               rs.getBoolean("bayar_piutang_lain"),
                               rs.getBoolean("pembayaran_akun_bayar4"),
                               rs.getBoolean("stok_akhir_farmasi_pertanggal"),
                               rs.getBoolean("riwayat_kamar_pasien"),
                               rs.getBoolean("uji_fungsi_kfr"),
                               rs.getBoolean("hapus_berkas_digital_perawatan"),
                               rs.getBoolean("kategori_pengeluaran_harian"),
                               rs.getBoolean("kategori_pemasukan_lain"),
                               rs.getBoolean("pembayaran_akun_bayar5"),
                               rs.getBoolean("ruang_ok"),
                               rs.getBoolean("telaah_resep"),
                               rs.getBoolean("jasa_tindakan_pasien"),
                               rs.getBoolean("permintaan_resep_pulang"),
                               rs.getBoolean("rekap_jm_dokter"),
                               rs.getBoolean("status_data_rm"),
                               rs.getBoolean("ubah_petugas_lab_pk"),
                               rs.getBoolean("ubah_petugas_lab_pa"),
                               rs.getBoolean("ubah_petugas_radiologi"),
                               rs.getBoolean("gabung_norawat"),
                               rs.getBoolean("gabung_rm"),
                               rs.getBoolean("ringkasan_biaya_obat_pasien_pertanggal"),
                               rs.getBoolean("master_masalah_keperawatan_igd"),
                               rs.getBoolean("penilaian_awal_keperawatan_igd"),
                               rs.getBoolean("bpjs_referensi_dpho_apotek"),
                               rs.getBoolean("bpjs_referensi_poli_apotek"),
                               rs.getBoolean("bayar_jm_dokter"),
                               rs.getBoolean("bpjs_referensi_faskes_apotek"),
                               rs.getBoolean("bpjs_referensi_spesialistik_apotek"),
                               rs.getBoolean("pembayaran_briva"),
                               rs.getBoolean("penilaian_awal_keperawatan_ranap"),
                               rs.getBoolean("nilai_penerimaan_vendor_farmasi_perbulan"),
                               rs.getBoolean("akun_bayar_hutang"),
                               rs.getBoolean("master_rencana_keperawatan"),
                               rs.getBoolean("laporan_tahunan_igd"),
                               rs.getBoolean("obat_bhp_tidakbergerak"),
                               rs.getBoolean("ringkasan_hutang_vendor_farmasi"),
                               rs.getBoolean("nilai_penerimaan_vendor_nonmedis_perbulan"),
                               rs.getBoolean("ringkasan_hutang_vendor_nonmedis"),
                               rs.getBoolean("master_rencana_keperawatan_anak"),
                               rs.getBoolean("anggota_polri_dirawat"),
                               rs.getBoolean("daftar_pasien_ranap_polri"),
                               rs.getBoolean("soap_ralan_polri"),
                               rs.getBoolean("soap_ranap_polri"),
                               rs.getBoolean("laporan_penyakit_polri"),
                               rs.getBoolean("jumlah_pengunjung_ralan_polri"),
                               rs.getBoolean("catatan_observasi_igd"),
                               rs.getBoolean("catatan_observasi_ranap"),
                               rs.getBoolean("catatan_observasi_ranap_kebidanan"),
                               rs.getBoolean("catatan_observasi_ranap_postpartum"),
                               rs.getBoolean("penilaian_awal_medis_ralan_tht"),
                               rs.getBoolean("penilaian_psikologi"),
                               rs.getBoolean("audit_cuci_tangan_medis"),
                               rs.getBoolean("audit_pembuangan_limbah"),
                               rs.getBoolean("ruang_audit_kepatuhan"),
                               rs.getBoolean("audit_pembuangan_benda_tajam"),
                               rs.getBoolean("audit_penanganan_darah"),
                               rs.getBoolean("audit_pengelolaan_linen_kotor"),
                               rs.getBoolean("audit_penempatan_pasien"),
                               rs.getBoolean("audit_kamar_jenazah"),
                               rs.getBoolean("audit_bundle_iadp"),
                               rs.getBoolean("audit_bundle_ido"),
                               rs.getBoolean("audit_fasilitas_kebersihan_tangan"),
                               rs.getBoolean("audit_fasilitas_apd"),
                               rs.getBoolean("audit_pembuangan_limbah_cair_infeksius"),
                               rs.getBoolean("audit_sterilisasi_alat"),
                               rs.getBoolean("penilaian_awal_medis_ralan_psikiatri"),
                               rs.getBoolean("persetujuan_penolakan_tindakan")
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
                           rs.getBoolean("ebook_perpustakaan"),
                           rs.getBoolean("jenis_cidera_k3rs"),
                           rs.getBoolean("penyebab_k3rs"),
                           rs.getBoolean("jenis_luka_k3rs"),
                           rs.getBoolean("lokasi_kejadian_k3rs"),
                           rs.getBoolean("dampak_cidera_k3rs"),
                           rs.getBoolean("jenis_pekerjaan_k3rs"),
                           rs.getBoolean("bagian_tubuh_k3rs"),
                           rs.getBoolean("peristiwa_k3rs"),
                           rs.getBoolean("grafik_k3_pertahun"),
                           rs.getBoolean("grafik_k3_perbulan"),
                           rs.getBoolean("grafik_k3_pertanggal"),
                           rs.getBoolean("grafik_k3_perjeniscidera"),
                           rs.getBoolean("grafik_k3_perpenyebab"),
                           rs.getBoolean("grafik_k3_perjenisluka"),
                           rs.getBoolean("grafik_k3_lokasikejadian"),
                           rs.getBoolean("grafik_k3_dampakcidera"),
                           rs.getBoolean("grafik_k3_perjenispekerjaan"),
                           rs.getBoolean("grafik_k3_perbagiantubuh"),
                           rs.getBoolean("jenis_cidera_k3rstahun"),
                           rs.getBoolean("penyebab_k3rstahun"),
                           rs.getBoolean("jenis_luka_k3rstahun"),
                           rs.getBoolean("lokasi_kejadian_k3rstahun"),
                           rs.getBoolean("dampak_cidera_k3rstahun"),
                           rs.getBoolean("jenis_pekerjaan_k3rstahun"),
                           rs.getBoolean("bagian_tubuh_k3rstahun"),
                           rs.getBoolean("sekrining_rawat_jalan"),
                           rs.getBoolean("bpjs_histori_pelayanan"),
                           rs.getBoolean("rekap_mutasi_berkas"),
                           rs.getBoolean("skrining_ralan_pernapasan_pertahun"),
                           rs.getBoolean("pengajuan_barang_medis"),
                           rs.getBoolean("pengajuan_barang_nonmedis"),
                           rs.getBoolean("grafik_kunjungan_ranapbulan"),
                           rs.getBoolean("grafik_kunjungan_ranaptanggal"),
                           rs.getBoolean("grafik_kunjungan_ranap_peruang"),
                           rs.getBoolean("kunjungan_bangsal_pertahun"),
                           rs.getBoolean("grafik_jenjang_jabatanpegawai"),
                           rs.getBoolean("grafik_bidangpegawai"),
                           rs.getBoolean("grafik_departemenpegawai"),
                           rs.getBoolean("grafik_pendidikanpegawai"),
                           rs.getBoolean("grafik_sttswppegawai"),
                           rs.getBoolean("grafik_sttskerjapegawai"),
                           rs.getBoolean("grafik_sttspulangranap"),
                           rs.getBoolean("kip_pasien_ranap"),
                           rs.getBoolean("kip_pasien_ralan"),
                           rs.getBoolean("bpjs_mapping_dokterdpjp"),
                           rs.getBoolean("data_triase_igd"),
                           rs.getBoolean("master_triase_skala1"),
                           rs.getBoolean("master_triase_skala2"),
                           rs.getBoolean("master_triase_skala3"),
                           rs.getBoolean("master_triase_skala4"),
                           rs.getBoolean("master_triase_skala5"),
                           rs.getBoolean("master_triase_pemeriksaan"),
                           rs.getBoolean("master_triase_macamkasus"),
                           rs.getBoolean("rekap_permintaan_diet"),
                           rs.getBoolean("daftar_pasien_ranap"),
                           rs.getBoolean("daftar_pasien_ranaptni"),
                           rs.getBoolean("pengajuan_asetinventaris"),
                           rs.getBoolean("item_apotek_jenis"),
                           rs.getBoolean("item_apotek_kategori"),
                           rs.getBoolean("item_apotek_golongan"),
                           rs.getBoolean("item_apotek_industrifarmasi"),
                           rs.getBoolean("10_obat_terbanyak_poli"),
                           rs.getBoolean("grafik_pengajuan_aset_urgensi"),
                           rs.getBoolean("grafik_pengajuan_aset_status"),
                           rs.getBoolean("grafik_pengajuan_aset_departemen"),
                           rs.getBoolean("rekap_pengajuan_aset_departemen"),
                           rs.getBoolean("grafik_kelompok_jabatanpegawai"),
                           rs.getBoolean("grafik_resiko_kerjapegawai"),
                           rs.getBoolean("grafik_emergency_indexpegawai"),
                           rs.getBoolean("grafik_inventaris_ruang"),
                           rs.getBoolean("harian_HAIs2"),
                           rs.getBoolean("grafik_inventaris_jenis"),
                           rs.getBoolean("data_resume_pasien"),
                           rs.getBoolean("perkiraan_biaya_ranap"),
                           rs.getBoolean("rekap_obat_poli"),
                           rs.getBoolean("rekap_obat_pasien"),
                           rs.getBoolean("permintaan_perbaikan_inventaris"),
                           rs.getBoolean("grafik_HAIs_pasienbangsal"),
                           rs.getBoolean("grafik_HAIs_pasienbulan"),
                           rs.getBoolean("grafik_HAIs_laju_vap"),
                           rs.getBoolean("grafik_HAIs_laju_iad"),
                           rs.getBoolean("grafik_HAIs_laju_pleb"),
                           rs.getBoolean("grafik_HAIs_laju_isk"),
                           rs.getBoolean("grafik_HAIs_laju_ilo"),
                           rs.getBoolean("grafik_HAIs_laju_hap"),
                           rs.getBoolean("inhealth_mapping_poli"),
                           rs.getBoolean("inhealth_mapping_dokter"),
                           rs.getBoolean("inhealth_mapping_tindakan_ralan"),
                           rs.getBoolean("inhealth_mapping_tindakan_ranap"),
                           rs.getBoolean("inhealth_mapping_tindakan_radiologi"),
                           rs.getBoolean("inhealth_mapping_tindakan_laborat"),
                           rs.getBoolean("inhealth_mapping_tindakan_operasi"),
                           rs.getBoolean("hibah_obat_bhp"),
                           rs.getBoolean("asal_hibah"),
                           rs.getBoolean("asuhan_gizi"),
                           rs.getBoolean("inhealth_kirim_tagihan"),
                           rs.getBoolean("sirkulasi_obat4"),
                           rs.getBoolean("sirkulasi_obat5"),
                           rs.getBoolean("sirkulasi_non_medis2"),
                           rs.getBoolean("monitoring_asuhan_gizi"),
                           rs.getBoolean("penerimaan_obat_perbulan"),
                           rs.getBoolean("rekap_kunjungan"),
                           rs.getBoolean("surat_sakit"),
                           rs.getBoolean("penilaian_awal_keperawatan_ralan"),
                           rs.getBoolean("permintaan_diet"),
                           rs.getBoolean("master_masalah_keperawatan"),
                           rs.getBoolean("pengajuan_cuti"),
                           rs.getBoolean("kedatangan_pasien"),
                           rs.getBoolean("utd_pendonor"),
                           rs.getBoolean("toko_suplier"),
                           rs.getBoolean("toko_jenis"),
                           rs.getBoolean("toko_set_harga"),
                           rs.getBoolean("toko_barang"),
                           rs.getBoolean("penagihan_piutang_pasien"),
                           rs.getBoolean("akun_penagihan_piutang"),
                           rs.getBoolean("stok_opname_toko"),
                           rs.getBoolean("toko_riwayat_barang"),
                           rs.getBoolean("toko_surat_pemesanan"),
                           rs.getBoolean("toko_pengajuan_barang"),
                           rs.getBoolean("toko_penerimaan_barang"),
                           rs.getBoolean("toko_pengadaan_barang"),
                           rs.getBoolean("toko_hutang"),
                           rs.getBoolean("toko_bayar_pemesanan"),
                           rs.getBoolean("toko_member"),
                           rs.getBoolean("toko_penjualan"),
                           rs.getBoolean("registrasi_poli_per_tanggal"),
                           rs.getBoolean("toko_piutang"),
                           rs.getBoolean("toko_retur_beli"),
                           rs.getBoolean("ipsrs_returbeli"),
                           rs.getBoolean("ipsrs_riwayat_barang"),
                           rs.getBoolean("pasien_corona"),
                           rs.getBoolean("toko_pendapatan_harian"),
                           rs.getBoolean("diagnosa_pasien_corona"),
                           rs.getBoolean("perawatan_pasien_corona"),
                           rs.getBoolean("penilaian_awal_keperawatan_gigi"),
                           rs.getBoolean("master_masalah_keperawatan_gigi"),
                           rs.getBoolean("toko_bayar_piutang"),
                           rs.getBoolean("toko_piutang_harian"),
                           rs.getBoolean("toko_penjualan_harian"),
                           rs.getBoolean("deteksi_corona"),
                           rs.getBoolean("penilaian_awal_keperawatan_kebidanan"),
                           rs.getBoolean("pengumuman_epasien"),
                           rs.getBoolean("surat_hamil"),
                           rs.getBoolean("set_tarif_online"),
                           rs.getBoolean("booking_periksa"),
                           rs.getBoolean("toko_sirkulasi"),
                           rs.getBoolean("toko_retur_jual"),
                           rs.getBoolean("toko_retur_piutang"),
                           rs.getBoolean("toko_sirkulasi2"),
                           rs.getBoolean("toko_keuntungan_barang"),
                           rs.getBoolean("zis_pengeluaran_penerima_dankes"),
                           rs.getBoolean("zis_penghasilan_penerima_dankes"),
                           rs.getBoolean("zis_ukuran_rumah_penerima_dankes"),
                           rs.getBoolean("zis_dinding_rumah_penerima_dankes"),
                           rs.getBoolean("zis_lantai_rumah_penerima_dankes"),
                           rs.getBoolean("zis_atap_rumah_penerima_dankes"),
                           rs.getBoolean("zis_kepemilikan_rumah_penerima_dankes"),
                           rs.getBoolean("zis_kamar_mandi_penerima_dankes"),
                           rs.getBoolean("zis_dapur_rumah_penerima_dankes"),
                           rs.getBoolean("zis_kursi_rumah_penerima_dankes"),
                           rs.getBoolean("zis_kategori_phbs_penerima_dankes"),
                           rs.getBoolean("zis_elektronik_penerima_dankes"),
                           rs.getBoolean("zis_ternak_penerima_dankes"),
                           rs.getBoolean("zis_jenis_simpanan_penerima_dankes"),
                           rs.getBoolean("penilaian_awal_keperawatan_anak"),
                           rs.getBoolean("zis_kategori_asnaf_penerima_dankes"),
                           rs.getBoolean("master_masalah_keperawatan_anak"),
                           rs.getBoolean("master_imunisasi"),
                           rs.getBoolean("zis_patologis_penerima_dankes"),
                           rs.getBoolean("pcare_cek_kartu"),
                           rs.getBoolean("surat_bebas_narkoba"),
                           rs.getBoolean("surat_keterangan_covid"),
                           rs.getBoolean("pemakaian_air_tanah"),
                           rs.getBoolean("grafik_air_tanah_pertanggal"),
                           rs.getBoolean("grafik_air_tanah_perbulan"),
                           rs.getBoolean("lama_pelayanan_poli"),
                           rs.getBoolean("hemodialisa"),
                           rs.getBoolean("laporan_tahunan_irj"),
                           rs.getBoolean("grafik_harian_hemodialisa"),
                           rs.getBoolean("grafik_bulanan_hemodialisa"),
                           rs.getBoolean("grafik_tahunan_hemodialisa"),
                           rs.getBoolean("grafik_bulanan_meninggal"),
                           rs.getBoolean("perbaikan_inventaris"),
                           rs.getBoolean("surat_cuti_hamil"),
                           rs.getBoolean("permintaan_stok_obat_pasien"),
                           rs.getBoolean("pemeliharaan_inventaris"),
                           rs.getBoolean("klasifikasi_pasien_ranap"),
                           rs.getBoolean("bulanan_klasifikasi_pasien_ranap"),
                           rs.getBoolean("harian_klasifikasi_pasien_ranap"),
                           rs.getBoolean("klasifikasi_pasien_perbangsal"),
                           rs.getBoolean("soap_perawatan"),
                           rs.getBoolean("klaim_rawat_jalan"),
                           rs.getBoolean("skrining_gizi"),
                           rs.getBoolean("lama_penyiapan_rm"),
                           rs.getBoolean("dosis_radiologi"),
                           rs.getBoolean("demografi_umur_kunjungan"),
                           rs.getBoolean("jam_diet_pasien"),
                           rs.getBoolean("rvu_bpjs"),
                           rs.getBoolean("verifikasi_penerimaan_farmasi"),
                           rs.getBoolean("verifikasi_penerimaan_logistik"),
                           rs.getBoolean("pemeriksaan_lab_pa"),
                           rs.getBoolean("ringkasan_pengajuan_obat"),
                           rs.getBoolean("ringkasan_pemesanan_obat"),
                           rs.getBoolean("ringkasan_pengadaan_obat"),
                           rs.getBoolean("ringkasan_penerimaan_obat"),
                           rs.getBoolean("ringkasan_hibah_obat"),
                           rs.getBoolean("ringkasan_penjualan_obat"),
                           rs.getBoolean("ringkasan_beri_obat"),
                           rs.getBoolean("ringkasan_piutang_obat"),
                           rs.getBoolean("ringkasan_stok_keluar_obat"),
                           rs.getBoolean("ringkasan_retur_suplier_obat"),
                           rs.getBoolean("ringkasan_retur_pembeli_obat"),
                           rs.getBoolean("penilaian_awal_keperawatan_ranapkebidanan"),
                           rs.getBoolean("ringkasan_pengajuan_nonmedis"),
                           rs.getBoolean("ringkasan_pemesanan_nonmedis"),
                           rs.getBoolean("ringkasan_pengadaan_nonmedis"),
                           rs.getBoolean("ringkasan_penerimaan_nonmedis"),
                           rs.getBoolean("ringkasan_stokkeluar_nonmedis"),
                           rs.getBoolean("ringkasan_returbeli_nonmedis"),
                           rs.getBoolean("omset_penerimaan"),
                           rs.getBoolean("validasi_penagihan_piutang"),
                           rs.getBoolean("permintaan_ranap"),
                           rs.getBoolean("bpjs_diagnosa_prb"),
                           rs.getBoolean("bpjs_obat_prb"),
                           rs.getBoolean("bpjs_surat_kontrol"),
                           rs.getBoolean("penggunaan_bhp_ok"),
                           rs.getBoolean("surat_keterangan_rawat_inap"),
                           rs.getBoolean("surat_keterangan_sehat"),
                           rs.getBoolean("pendapatan_per_carabayar"),
                           rs.getBoolean("akun_host_to_host_bank_jateng"),
                           rs.getBoolean("pembayaran_bank_jateng"),
                           rs.getBoolean("bpjs_surat_pri"),
                           rs.getBoolean("ringkasan_tindakan"),
                           rs.getBoolean("lama_pelayanan_pasien"),
                           rs.getBoolean("surat_sakit_pihak_2"),
                           rs.getBoolean("tagihan_hutang_obat"),
                           rs.getBoolean("referensi_mobilejkn_bpjs"),
                           rs.getBoolean("batal_pendaftaran_mobilejkn_bpjs"),
                           rs.getBoolean("lama_operasi"),
                           rs.getBoolean("grafik_inventaris_kategori"),
                           rs.getBoolean("grafik_inventaris_merk"),
                           rs.getBoolean("grafik_inventaris_produsen"),
                           rs.getBoolean("pengembalian_deposit_pasien"),
                           rs.getBoolean("validasi_tagihan_hutang_obat"),
                           rs.getBoolean("piutang_obat_belum_lunas"),
                           rs.getBoolean("integrasi_briapi"),
                           rs.getBoolean("pengadaan_aset_inventaris"),
                           rs.getBoolean("akun_aset_inventaris"),
                           rs.getBoolean("suplier_inventaris"),
                           rs.getBoolean("penerimaan_aset_inventaris"),
                           rs.getBoolean("bayar_pemesanan_iventaris"),
                           rs.getBoolean("hutang_aset_inventaris"),
                           rs.getBoolean("hibah_aset_inventaris"),
                           rs.getBoolean("titip_faktur_non_medis"),
                           rs.getBoolean("validasi_tagihan_non_medis"),
                           rs.getBoolean("titip_faktur_aset"),
                           rs.getBoolean("validasi_tagihan_aset"),
                           rs.getBoolean("hibah_non_medis"),
                           rs.getBoolean("pcare_alasan_tacc"),
                           rs.getBoolean("resep_luar"),
                           rs.getBoolean("surat_bebas_tbc"),
                           rs.getBoolean("surat_buta_warna"),
                           rs.getBoolean("surat_bebas_tato"),
                           rs.getBoolean("surat_kewaspadaan_kesehatan"),
                           rs.getBoolean("grafik_porsidiet_pertanggal"),
                           rs.getBoolean("grafik_porsidiet_perbulan"),
                           rs.getBoolean("grafik_porsidiet_pertahun"),
                           rs.getBoolean("grafik_porsidiet_perbangsal"),
                           rs.getBoolean("penilaian_awal_medis_ralan"),
                           rs.getBoolean("master_masalah_keperawatan_mata"),
                           rs.getBoolean("penilaian_awal_keperawatan_mata"),
                           rs.getBoolean("penilaian_awal_medis_ranap"),
                           rs.getBoolean("penilaian_awal_medis_ranap_kebidanan"),
                           rs.getBoolean("penilaian_awal_medis_ralan_kebidanan"),
                           rs.getBoolean("penilaian_awal_medis_igd"),
                           rs.getBoolean("penilaian_awal_medis_ralan_anak"),
                           rs.getBoolean("bpjs_referensi_poli_hfis"),
                           rs.getBoolean("bpjs_referensi_dokter_hfis"),
                           rs.getBoolean("bpjs_referensi_jadwal_hfis"),
                           rs.getBoolean("penilaian_fisioterapi"),
                           rs.getBoolean("bpjs_program_prb"),
                           rs.getBoolean("bpjs_suplesi_jasaraharja"),
                           rs.getBoolean("bpjs_data_induk_kecelakaan"),
                           rs.getBoolean("bpjs_sep_internal"),
                           rs.getBoolean("bpjs_klaim_jasa_raharja"),
                           rs.getBoolean("bpjs_daftar_finger_print"),
                           rs.getBoolean("bpjs_rujukan_khusus"),
                           rs.getBoolean("pemeliharaan_gedung"),
                           rs.getBoolean("grafik_perbaikan_inventaris_pertanggal"),
                           rs.getBoolean("grafik_perbaikan_inventaris_perbulan"),
                           rs.getBoolean("grafik_perbaikan_inventaris_pertahun"),
                           rs.getBoolean("grafik_perbaikan_inventaris_perpelaksana_status"),
                           rs.getBoolean("penilaian_mcu"),
                           rs.getBoolean("peminjam_piutang"),
                           rs.getBoolean("piutang_lainlain"),
                           rs.getBoolean("cara_bayar"),
                           rs.getBoolean("audit_kepatuhan_apd"),
                           rs.getBoolean("bpjs_task_id"),
                           rs.getBoolean("bayar_piutang_lain"),
                           rs.getBoolean("pembayaran_akun_bayar4"),
                           rs.getBoolean("stok_akhir_farmasi_pertanggal"),
                           rs.getBoolean("riwayat_kamar_pasien"),
                           rs.getBoolean("uji_fungsi_kfr"),
                           rs.getBoolean("hapus_berkas_digital_perawatan"),
                           rs.getBoolean("kategori_pengeluaran_harian"),
                           rs.getBoolean("kategori_pemasukan_lain"),
                           rs.getBoolean("pembayaran_akun_bayar5"),
                           rs.getBoolean("ruang_ok"),
                           rs.getBoolean("telaah_resep"),
                           rs.getBoolean("jasa_tindakan_pasien"),
                           rs.getBoolean("permintaan_resep_pulang"),
                           rs.getBoolean("rekap_jm_dokter"),
                           rs.getBoolean("status_data_rm"),
                           rs.getBoolean("ubah_petugas_lab_pk"),
                           rs.getBoolean("ubah_petugas_lab_pa"),
                           rs.getBoolean("ubah_petugas_radiologi"),
                           rs.getBoolean("gabung_norawat"),
                           rs.getBoolean("gabung_rm"),
                           rs.getBoolean("ringkasan_biaya_obat_pasien_pertanggal"),
                           rs.getBoolean("master_masalah_keperawatan_igd"),
                           rs.getBoolean("penilaian_awal_keperawatan_igd"),
                           rs.getBoolean("bpjs_referensi_dpho_apotek"),
                           rs.getBoolean("bpjs_referensi_poli_apotek"),
                           rs.getBoolean("bayar_jm_dokter"),
                           rs.getBoolean("bpjs_referensi_faskes_apotek"),
                           rs.getBoolean("bpjs_referensi_spesialistik_apotek"),
                           rs.getBoolean("pembayaran_briva"),
                           rs.getBoolean("penilaian_awal_keperawatan_ranap"),
                           rs.getBoolean("nilai_penerimaan_vendor_farmasi_perbulan"),
                           rs.getBoolean("akun_bayar_hutang"),
                           rs.getBoolean("master_rencana_keperawatan"),
                           rs.getBoolean("laporan_tahunan_igd"),
                           rs.getBoolean("obat_bhp_tidakbergerak"),
                           rs.getBoolean("ringkasan_hutang_vendor_farmasi"),
                           rs.getBoolean("nilai_penerimaan_vendor_nonmedis_perbulan"),
                           rs.getBoolean("ringkasan_hutang_vendor_nonmedis"),
                           rs.getBoolean("master_rencana_keperawatan_anak"),
                           rs.getBoolean("anggota_polri_dirawat"),
                           rs.getBoolean("daftar_pasien_ranap_polri"),
                           rs.getBoolean("soap_ralan_polri"),
                           rs.getBoolean("soap_ranap_polri"),
                           rs.getBoolean("laporan_penyakit_polri"),
                           rs.getBoolean("jumlah_pengunjung_ralan_polri"),
                           rs.getBoolean("catatan_observasi_igd"),
                           rs.getBoolean("catatan_observasi_ranap"),
                           rs.getBoolean("catatan_observasi_ranap_kebidanan"),
                           rs.getBoolean("catatan_observasi_ranap_postpartum"),
                           rs.getBoolean("penilaian_awal_medis_ralan_tht"),
                           rs.getBoolean("penilaian_psikologi"),
                           rs.getBoolean("audit_cuci_tangan_medis"),
                           rs.getBoolean("audit_pembuangan_limbah"),
                           rs.getBoolean("ruang_audit_kepatuhan"),
                           rs.getBoolean("audit_pembuangan_benda_tajam"),
                           rs.getBoolean("audit_penanganan_darah"),
                           rs.getBoolean("audit_pengelolaan_linen_kotor"),
                           rs.getBoolean("audit_penempatan_pasien"),
                           rs.getBoolean("audit_kamar_jenazah"),
                           rs.getBoolean("audit_bundle_iadp"),
                           rs.getBoolean("audit_bundle_ido"),
                           rs.getBoolean("audit_fasilitas_kebersihan_tangan"),
                           rs.getBoolean("audit_fasilitas_apd"),
                           rs.getBoolean("audit_pembuangan_limbah_cair_infeksius"),
                           rs.getBoolean("audit_sterilisasi_alat"),
                           rs.getBoolean("penilaian_awal_medis_ralan_psikiatri"),
                           rs.getBoolean("persetujuan_penolakan_tindakan")
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

-- phpMyAdmin SQL Dump
-- version 3.1.3.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Waktu pembuatan: 13. Juli 2012 jam 21:03
-- Versi Server: 5.1.33
-- Versi PHP: 5.2.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `smkreport`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `artikel`
--

CREATE TABLE IF NOT EXISTS `artikel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `judul` varchar(255) DEFAULT NULL,
  `isi` text,
  `post` datetime DEFAULT NULL,
  `pengirim` varchar(50) DEFAULT NULL,
  `page` enum('artikel','home','kontak') DEFAULT NULL,
  `foto` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data untuk tabel `artikel`
--

INSERT INTO `artikel` (`id`, `judul`, `isi`, `post`, `pengirim`, `page`, `foto`) VALUES
(1, 'Sistem Informasi Akademik', 'Creted by Khanza.Soft Media', '2012-02-02 00:00:00', 'Admin', 'home', 'upload/');

-- --------------------------------------------------------

--
-- Struktur dari tabel `bidang_studi`
--

CREATE TABLE IF NOT EXISTS `bidang_studi` (
  `kd_bidang` char(5) NOT NULL,
  `bidang_studi_keahlian` varchar(100) DEFAULT NULL,
  `program_studi_keahlian` varchar(100) DEFAULT NULL,
  `kompetensi_keahlian` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`kd_bidang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `bidang_studi`
--

INSERT INTO `bidang_studi` (`kd_bidang`, `bidang_studi_keahlian`, `program_studi_keahlian`, `kompetensi_keahlian`) VALUES
('00001', 'Teknologi Informasi Dan Komunikasi', 'TEKNIK KOMPUTER DAN INFORMATIK', 'REKAYASA PERANGKAT LUNAK'),
('00002', 'KOMPUTERISASI AKUNTANSI', 'KEUANGAN', 'AKUNTANSI KEUANGAN'),
('787', '78khj', '676', '67'),
('ewrwe', 'ewr', 'ewrewr', 'ewrr'),
('K002', 'fagsfas', 'aaaa', 'wwww');

-- --------------------------------------------------------

--
-- Struktur dari tabel `catatan`
--

CREATE TABLE IF NOT EXISTS `catatan` (
  `thn_ajar` year(4) NOT NULL DEFAULT '0000',
  `semester` enum('1','2','3','4','5','6','7','8') NOT NULL DEFAULT '1',
  `nis` varchar(15) NOT NULL DEFAULT '',
  `catatan` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`thn_ajar`,`semester`,`nis`),
  KEY `nis` (`nis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `catatan`
--

INSERT INTO `catatan` (`thn_ajar`, `semester`, `nis`, `catatan`) VALUES
(2010, '1', '000000000000001', 'SENG SREGEP YOO LEEE'),
(2012, '1', '000000000000001', 'sds ds sd sds d sd sdsdsdsdsdsd sdsd'),
(2012, '1', '000000000000002', 'sdf dfdsf dsdf  dsfdsf df dsfsdfdsf fdsf'),
(2012, '2', '000000000000001', 'qwqw qwq qwq'),
(2012, '2', '000000000000002', 't t t t t t t t t t t t t t t');

-- --------------------------------------------------------

--
-- Struktur dari tabel `guru`
--

CREATE TABLE IF NOT EXISTS `guru` (
  `nip` varchar(25) NOT NULL DEFAULT '',
  `nama` varchar(100) DEFAULT NULL,
  `jk` enum('L','P') DEFAULT NULL,
  `agama` enum('Islam','Kristen','Katolik','Hindu','Budha','Konghucu','-') DEFAULT NULL,
  `tmp_lahir` varchar(20) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `alamat` varchar(100) DEFAULT NULL,
  `no_telp` varchar(13) DEFAULT NULL,
  `photo` varchar(500) NOT NULL,
  PRIMARY KEY (`nip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `guru`
--

INSERT INTO `guru` (`nip`, `nama`, `jk`, `agama`, `tmp_lahir`, `tgl_lahir`, `alamat`, `no_telp`, `photo`) VALUES
('001', 'WINDIARTO', 'L', 'Islam', 'KLATEN', '1988-02-03', 'RT 03/RW 03 KECAMATAN KLATEN UTARA KLATEN JAWA TEN', '049540354935', 'pages/pegawai/DSC01295.JPG'),
('213', '21323', 'L', 'Islam', '1233', '1997-05-16', '123213', '21323', 'pages/pegawai/'),
('34', '34', 'L', 'Islam', '324', '0000-00-00', '234', '234', 'pages/pegawai/');

-- --------------------------------------------------------

--
-- Struktur dari tabel `isi_kelas`
--

CREATE TABLE IF NOT EXISTS `isi_kelas` (
  `thn_ajar` year(4) NOT NULL DEFAULT '0000',
  `kd_kelas` varchar(7) NOT NULL DEFAULT '',
  `nis` varchar(15) NOT NULL DEFAULT '',
  PRIMARY KEY (`thn_ajar`,`nis`),
  KEY `kd_kelas` (`kd_kelas`),
  KEY `nis` (`nis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `isi_kelas`
--

INSERT INTO `isi_kelas` (`thn_ajar`, `kd_kelas`, `nis`) VALUES
(2010, 'K000001', '000000000000001'),
(2012, 'K000001', '000000000000001'),
(2012, 'K000001', '000000000000002'),
(2012, 'K000002', '21323');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kelas`
--

CREATE TABLE IF NOT EXISTS `kelas` (
  `kd_kelas` varchar(7) NOT NULL DEFAULT '',
  `nm_kelas` varchar(100) DEFAULT NULL,
  `kd_bidang` char(5) NOT NULL DEFAULT '',
  PRIMARY KEY (`kd_kelas`),
  KEY `kd_bidang` (`kd_bidang`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kelas`
--

INSERT INTO `kelas` (`kd_kelas`, `nm_kelas`, `kd_bidang`) VALUES
('K000001', 'KELAS 1 TIK', '00001'),
('K000002', 'KELAS 1 KA', '00002');

-- --------------------------------------------------------

--
-- Struktur dari tabel `kepribadian`
--

CREATE TABLE IF NOT EXISTS `kepribadian` (
  `thn_ajar` year(4) NOT NULL DEFAULT '0000',
  `semester` enum('1','2','3','4','5','6','7','8') NOT NULL DEFAULT '1',
  `sikap` enum('KEJUJURAN','KETERTIBAN','KEDISIPLINAN','TANGGUNG JAWAB') NOT NULL DEFAULT 'KEJUJURAN',
  `predikat` enum('BAIK','CUKUP','KURANG') DEFAULT NULL,
  `nis` varchar(15) NOT NULL DEFAULT '',
  PRIMARY KEY (`thn_ajar`,`semester`,`sikap`,`nis`),
  KEY `nis` (`nis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `kepribadian`
--

INSERT INTO `kepribadian` (`thn_ajar`, `semester`, `sikap`, `predikat`, `nis`) VALUES
(2010, '1', 'KEJUJURAN', 'BAIK', '000000000000001'),
(2010, '1', 'KEDISIPLINAN', 'BAIK', '000000000000001'),
(2010, '2', 'KETERTIBAN', 'BAIK', '000000000000001'),
(2012, '1', 'KEJUJURAN', 'BAIK', '000000000000002'),
(2012, '1', 'KETERTIBAN', 'BAIK', '000000000000001'),
(2012, '1', 'KEDISIPLINAN', 'BAIK', '000000000000002'),
(2012, '2', 'KEJUJURAN', 'BAIK', '000000000000001'),
(2012, '2', 'KEJUJURAN', 'BAIK', '000000000000002'),
(2012, '2', 'KEDISIPLINAN', 'KURANG', '000000000000001');

-- --------------------------------------------------------

--
-- Struktur dari tabel `mapel`
--

CREATE TABLE IF NOT EXISTS `mapel` (
  `kd_mapel` varchar(10) NOT NULL DEFAULT '',
  `nm_mapel` varchar(100) DEFAULT NULL,
  `jenis` enum('Normatif','Adaptif','Produktif','Mulok') DEFAULT NULL,
  `kkm` double DEFAULT NULL,
  `deskripsi_kemajuan` varchar(300) NOT NULL,
  PRIMARY KEY (`kd_mapel`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `mapel`
--

INSERT INTO `mapel` (`kd_mapel`, `nm_mapel`, `jenis`, `kkm`, `deskripsi_kemajuan`) VALUES
('M000000001', 'PENDIDIKAN AGAMA ISLAM', 'Normatif', 8, ''),
('M000000002', 'PENDIDIKAN KEWARGANEGARAAN', 'Normatif', 8, 'hmmmm gitu deeh'),
('M000000003', 'BAHSA INDOENSIA', 'Normatif', 8, 'sdrdsfdsf'),
('M000000004', 'SENI BUDAYA', 'Normatif', 8, ''),
('M000000005', 'MATEMATIKA', 'Adaptif', 8, ''),
('M000000007', 'FISIKA', 'Adaptif', 8, ''),
('M000000008', 'KIMIA', 'Adaptif', 8, ''),
('M000000009', 'MENGOPERASIKAN SISTEM OPERASI', 'Produktif', 8, ''),
('M000000010', 'MENGINSTAL SOFTWARE', 'Produktif', 8, ''),
('M000000011', 'MENGUBAH KONFIGURASI SOFTWARE', 'Produktif', 8, ''),
('M000000012', 'KONVERSI DATA LEVEEL 1', 'Produktif', 8, ''),
('M000000013', 'WEB DESIGN', 'Produktif', 7, ''),
('M000000014', 'BAHASA JAWA', 'Mulok', 7, ''),
('M000000015', 'AQIDAH', '', 6, ''),
('M000000016', 'AKLAQ', '', 6, ''),
('M000000017', 'KEMUHAMADIAYAHAN', '', 6, ''),
('M000000018', 'BAHSA ARAB', '', 7, ''),
('M00000006', 'ILMU PENGETAHUAN ALAM', 'Adaptif', 8, ''),
('wqe', 'qwe', 'Normatif', 0, 'we');

-- --------------------------------------------------------

--
-- Struktur dari tabel `nilai_mapel`
--

CREATE TABLE IF NOT EXISTS `nilai_mapel` (
  `thn_ajar` year(4) DEFAULT NULL,
  `semester` enum('1','2','3','4','5','6','7','8') DEFAULT NULL,
  `kd_mapel` varchar(10) DEFAULT NULL,
  `angka` double DEFAULT NULL,
  `predikat` enum('Amat Baik','Baik','Cukup','Kurang','Kompeten','Belum Kompeten') DEFAULT NULL,
  `nis` varchar(15) DEFAULT NULL,
  KEY `kd_mapel` (`kd_mapel`),
  KEY `nis` (`nis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `nilai_mapel`
--

INSERT INTO `nilai_mapel` (`thn_ajar`, `semester`, `kd_mapel`, `angka`, `predikat`, `nis`) VALUES
(2012, '1', 'M000000014', 7, 'Baik', '000000000000001'),
(2012, '1', 'M000000014', 7, 'Baik', '000000000000002'),
(2012, '1', 'M000000003', 9, 'Amat Baik', '000000000000001'),
(2012, '1', 'M000000003', 7, 'Baik', '000000000000002'),
(2012, '1', 'M000000012', 9, 'Amat Baik', '000000000000001'),
(2012, '1', 'M000000012', 6, 'Cukup', '000000000000002'),
(2012, '2', 'M000000012', 6, 'Cukup', '000000000000001'),
(2012, '2', 'M000000012', 9, 'Amat Baik', '000000000000002'),
(2012, '1', 'M000000016', 7, 'Amat Baik', '000000000000001'),
(2012, '1', 'M000000016', 7, 'Amat Baik', '000000000000002'),
(2012, '1', 'M00000006', 9, 'Amat Baik', '000000000000001'),
(2012, '1', 'M00000006', 9, 'Amat Baik', '000000000000002');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pengembangan_diri`
--

CREATE TABLE IF NOT EXISTS `pengembangan_diri` (
  `thn_ajar` year(4) NOT NULL DEFAULT '0000',
  `semester` enum('1','2','3','4','5','6','7','8') NOT NULL DEFAULT '1',
  `nis` varchar(15) NOT NULL DEFAULT '',
  `program_pengembangan` varchar(50) NOT NULL DEFAULT '',
  `predikat` enum('BAIK','CUKUP','KURANG') DEFAULT NULL,
  PRIMARY KEY (`thn_ajar`,`semester`,`nis`,`program_pengembangan`),
  KEY `nis` (`nis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pengembangan_diri`
--

INSERT INTO `pengembangan_diri` (`thn_ajar`, `semester`, `nis`, `program_pengembangan`, `predikat`) VALUES
(2012, '1', '000000000000001', 'asasyu', 'CUKUP'),
(2012, '1', '000000000000001', 'Pecak Silat', 'BAIK'),
(2012, '1', '000000000000002', 'erewr', 'BAIK'),
(2012, '1', '000000000000002', 'ewrerr', 'BAIK'),
(2012, '2', '000000000000001', 'asass', 'BAIK');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pernyataan`
--

CREATE TABLE IF NOT EXISTS `pernyataan` (
  `thn_ajar` year(4) NOT NULL DEFAULT '0000',
  `semester` enum('1','2','3','4','5','6','7','8') NOT NULL DEFAULT '1',
  `nis` varchar(15) NOT NULL DEFAULT '',
  `pernyataan` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`thn_ajar`,`semester`,`nis`),
  KEY `nis` (`nis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pernyataan`
--

INSERT INTO `pernyataan` (`thn_ajar`, `semester`, `nis`, `pernyataan`) VALUES
(2010, '1', '000000000000001', 'DADI CAH OJO NGEYEL WAE'),
(2012, '1', '000000000000001', 'Lulus Coy'),
(2012, '1', '000000000000002', 'Siap Lulus');

-- --------------------------------------------------------

--
-- Struktur dari tabel `prakerin`
--

CREATE TABLE IF NOT EXISTS `prakerin` (
  `thn_ajar` year(4) DEFAULT NULL,
  `semester` enum('1','2','3','4','5','6','7','8') DEFAULT NULL,
  `nis` varchar(15) DEFAULT NULL,
  `nama_du` varchar(60) DEFAULT NULL,
  `alamat_du` varchar(60) DEFAULT NULL,
  `waktu_pelaksanaan` varchar(40) DEFAULT NULL,
  `nilai` varchar(10) DEFAULT NULL,
  `predikat` varchar(15) DEFAULT NULL,
  KEY `nis` (`nis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `prakerin`
--

INSERT INTO `prakerin` (`thn_ajar`, `semester`, `nis`, `nama_du`, `alamat_du`, `waktu_pelaksanaan`, `nilai`, `predikat`) VALUES
(2010, '1', '000000000000001', 'TOKO SABAR', 'JL MAJU MUNDUR', 'SEWULAN', 'A', '8'),
(2010, '1', '000000000000002', 'OASAS', 'ASAS', 'ASAS', 'ASA', 'ASAS');

-- --------------------------------------------------------

--
-- Struktur dari tabel `presensi`
--

CREATE TABLE IF NOT EXISTS `presensi` (
  `thn_ajar` year(4) NOT NULL DEFAULT '0000',
  `semester` enum('1','2','3','4','5','6','7','8') NOT NULL DEFAULT '1',
  `nis` varchar(15) NOT NULL DEFAULT '',
  `keterangan` enum('SAKIT','IZIN','TANPA KETERANGAN') NOT NULL DEFAULT 'SAKIT',
  `jml_hadir` int(11) DEFAULT NULL,
  PRIMARY KEY (`thn_ajar`,`semester`,`nis`,`keterangan`),
  KEY `nis` (`nis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `presensi`
--

INSERT INTO `presensi` (`thn_ajar`, `semester`, `nis`, `keterangan`, `jml_hadir`) VALUES
(2010, '1', '000000000000001', 'SAKIT', 1),
(2010, '1', '000000000000001', 'IZIN', 0),
(2010, '1', '000000000000001', 'TANPA KETERANGAN', 1),
(2010, '2', '000000000000001', 'SAKIT', 4),
(2012, '1', '000000000000001', 'SAKIT', 3),
(2012, '1', '000000000000001', 'IZIN', 2),
(2012, '1', '000000000000002', 'IZIN', 2),
(2012, '1', '21323', 'IZIN', 3),
(2012, '1', '21323', 'TANPA KETERANGAN', 2),
(2012, '2', '000000000000001', 'SAKIT', 2),
(2012, '2', '000000000000002', 'IZIN', 2),
(2012, '2', '21323', 'SAKIT', 2),
(2012, '2', '21323', 'IZIN', 2);

-- --------------------------------------------------------

--
-- Struktur dari tabel `sesion`
--

CREATE TABLE IF NOT EXISTS `sesion` (
  `user` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `sesion`
--

INSERT INTO `sesion` (`user`) VALUES
('ADMIN');

-- --------------------------------------------------------

--
-- Struktur dari tabel `siswa`
--

CREATE TABLE IF NOT EXISTS `siswa` (
  `nis` varchar(15) NOT NULL DEFAULT '',
  `nama` varchar(100) DEFAULT NULL,
  `jk` enum('L','P') DEFAULT NULL,
  `tmp_lahir` varchar(25) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `agama` varchar(15) DEFAULT NULL,
  `alamat` varchar(100) DEFAULT NULL,
  `sekolah_asal` varchar(100) DEFAULT NULL,
  `alamat_sekolah_asal` varchar(100) DEFAULT NULL,
  `tahun_ijazah` year(4) DEFAULT NULL,
  `nomer_ijazah` varchar(20) DEFAULT NULL,
  `kelas_diterima` varchar(50) DEFAULT NULL,
  `tgl_diterima` date DEFAULT NULL,
  `nm_ayah` varchar(40) DEFAULT NULL,
  `nm_ibu` varchar(40) DEFAULT NULL,
  `alamat_ortu` varchar(60) DEFAULT NULL,
  `pekerjaan_ortu` varchar(30) DEFAULT NULL,
  `nm_wali` varchar(40) DEFAULT NULL,
  `almat_wali` varchar(60) DEFAULT NULL,
  `pekerjaan_wali` varchar(30) DEFAULT NULL,
  `photo` varchar(500) NOT NULL,
  PRIMARY KEY (`nis`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `siswa`
--

INSERT INTO `siswa` (`nis`, `nama`, `jk`, `tmp_lahir`, `tgl_lahir`, `agama`, `alamat`, `sekolah_asal`, `alamat_sekolah_asal`, `tahun_ijazah`, `nomer_ijazah`, `kelas_diterima`, `tgl_diterima`, `nm_ayah`, `nm_ibu`, `alamat_ortu`, `pekerjaan_ortu`, `nm_wali`, `almat_wali`, `pekerjaan_wali`, `photo`) VALUES
('000000000000001', 'JUMINTEN', 'L', 'JOGJA', '2010-04-22', 'ISLAM', 'JOGJA\r\n', 'JOGJA\r\n', 'JOGJA\r\n', 2000, '12121212121212', 'KELAS 1A TIK', '2010-04-22', 'MULYANTO', 'PAINEM', 'JOGJA\r\n', 'BURUH', 'DARJO', 'JOGJA\r\n', 'BURUH', 'pages/murid/'),
('000000000000002', 'JUMADI', 'L', 'BANTUL', '1999-04-22', 'ISLAM', 'JOGJA', 'JOGJA', 'JOGJA', 2005, '2392389U8724324', 'KELAS 1 A TIK', '2010-04-22', 'DIMEJO', 'SUMINI', 'JOGJA', 'BURUH', 'PAINO', 'JOGJA', 'BURUH', 'pages/murid/'),
('21323', '213213', 'L', '213213', '0000-00-00', 'Islam', '213213', '213213', '213213', 0000, '23', '123', '2003-03-02', '213', '213213', '213', '213213', '21323', '213213', '21323', 'pages/murid/DSC01295.JPG');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `nip` varchar(25) NOT NULL,
  `usere` varchar(600) NOT NULL DEFAULT '',
  `passwordte` varchar(600) DEFAULT NULL,
  `type` enum('ADMIN','PEGAWAI','DOSEN','OPERATOR') NOT NULL,
  KEY `nip` (`nip`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`nip`, `usere`, `passwordte`, `type`) VALUES
('ADMIN', 'admin', '‘àiˆªH2šHYb9àŠB', 'ADMIN');

-- --------------------------------------------------------

--
-- Struktur dari tabel `wali_kelas`
--

CREATE TABLE IF NOT EXISTS `wali_kelas` (
  `thn_ajar` year(4) NOT NULL DEFAULT '0000',
  `kd_kelas` varchar(7) NOT NULL DEFAULT '',
  `nip` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`thn_ajar`,`kd_kelas`),
  KEY `nip` (`nip`),
  KEY `kd_kelas` (`kd_kelas`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `wali_kelas`
--

INSERT INTO `wali_kelas` (`thn_ajar`, `kd_kelas`, `nip`) VALUES
(2012, 'K000001', '001'),
(2012, 'K000002', '001'),
(2011, 'K000001', '213'),
(2011, 'K000002', '213'),
(2010, 'K000001', '34');

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `catatan`
--
ALTER TABLE `catatan`
  ADD CONSTRAINT `catatan_ibfk_1` FOREIGN KEY (`nis`) REFERENCES `siswa` (`nis`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `isi_kelas`
--
ALTER TABLE `isi_kelas`
  ADD CONSTRAINT `isi_kelas_ibfk_1` FOREIGN KEY (`kd_kelas`) REFERENCES `kelas` (`kd_kelas`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `isi_kelas_ibfk_2` FOREIGN KEY (`nis`) REFERENCES `siswa` (`nis`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `kelas`
--
ALTER TABLE `kelas`
  ADD CONSTRAINT `kelas_ibfk_1` FOREIGN KEY (`kd_bidang`) REFERENCES `bidang_studi` (`kd_bidang`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `kepribadian`
--
ALTER TABLE `kepribadian`
  ADD CONSTRAINT `kepribadian_ibfk_1` FOREIGN KEY (`nis`) REFERENCES `siswa` (`nis`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `nilai_mapel`
--
ALTER TABLE `nilai_mapel`
  ADD CONSTRAINT `nilai_mapel_ibfk_1` FOREIGN KEY (`kd_mapel`) REFERENCES `mapel` (`kd_mapel`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `nilai_mapel_ibfk_2` FOREIGN KEY (`nis`) REFERENCES `siswa` (`nis`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `pengembangan_diri`
--
ALTER TABLE `pengembangan_diri`
  ADD CONSTRAINT `pengembangan_diri_ibfk_1` FOREIGN KEY (`nis`) REFERENCES `siswa` (`nis`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `pernyataan`
--
ALTER TABLE `pernyataan`
  ADD CONSTRAINT `pernyataan_ibfk_1` FOREIGN KEY (`nis`) REFERENCES `siswa` (`nis`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `prakerin`
--
ALTER TABLE `prakerin`
  ADD CONSTRAINT `prakerin_ibfk_1` FOREIGN KEY (`nis`) REFERENCES `siswa` (`nis`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `presensi`
--
ALTER TABLE `presensi`
  ADD CONSTRAINT `presensi_ibfk_1` FOREIGN KEY (`nis`) REFERENCES `siswa` (`nis`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Ketidakleluasaan untuk tabel `wali_kelas`
--
ALTER TABLE `wali_kelas`
  ADD CONSTRAINT `wali_kelas_ibfk_1` FOREIGN KEY (`nip`) REFERENCES `guru` (`nip`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `wali_kelas_ibfk_2` FOREIGN KEY (`kd_kelas`) REFERENCES `kelas` (`kd_kelas`) ON DELETE CASCADE ON UPDATE CASCADE;

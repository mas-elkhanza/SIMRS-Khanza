-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jul 03, 2024 at 05:10 AM
-- Server version: 10.4.20-MariaDB
-- PHP Version: 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sik_bridging_lab`
--

-- --------------------------------------------------------

--
-- Table structure for table `detail_hasil_lab`
--

CREATE TABLE `detail_hasil_lab` (
  `noorder` varchar(15) NOT NULL,
  `kd_jenis_prw` varchar(15) NOT NULL,
  `id_template` int(11) NOT NULL,
  `nilai` varchar(60) NOT NULL,
  `nilai_rujukan` varchar(30) NOT NULL,
  `keterangan` varchar(60) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `detail_permintaan_lab`
--

CREATE TABLE `detail_permintaan_lab` (
  `noorder` varchar(15) NOT NULL,
  `kd_jenis_prw` varchar(15) NOT NULL,
  `id_template` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detail_permintaan_lab`
--

INSERT INTO `detail_permintaan_lab` (`noorder`, `kd_jenis_prw`, `id_template`) VALUES
('PL202004050001', '101-K.3', 473),
('PL202004050001', '101-K.3', 482),
('PL202004050001', '101-K.3', 483),
('PL202004050001', '101-K.3', 484),
('PL202004050001', '101-K.3', 487),
('PL202004050001', '101-K.3', 2195),
('PL202004050001', '101-K.3', 2196),
('PL202004050003', '102-K.2', 665),
('PL202004050003', '102-K.2', 666),
('PL202004050003', '102-K.2', 667),
('PL202004050003', '102-K.2', 671),
('PL202004050003', '102-K.2', 673),
('PL202004050003', '102-K.2', 675);

-- --------------------------------------------------------

--
-- Table structure for table `LIS_ORDER`
--

CREATE TABLE `LIS_ORDER` (
  `ID` bigint(20) NOT NULL,
  `MESSAGE_DT` datetime DEFAULT NULL,
  `ORDER_CONTROL` enum('NW','RP','CA') DEFAULT NULL,
  `PID` varchar(15) DEFAULT NULL,
  `PNAME` varchar(40) DEFAULT NULL,
  `ADDRESS1` varchar(100) DEFAULT NULL,
  `ADDRESS2` varchar(100) DEFAULT NULL,
  `ADDRESS3` varchar(100) DEFAULT NULL,
  `ADDRESS4` varchar(100) DEFAULT NULL,
  `PTYPE` enum('IN','OP') DEFAULT NULL,
  `BIRTH_DT` date DEFAULT NULL,
  `SEX` enum('1','2') DEFAULT NULL,
  `ONO` varchar(17) DEFAULT NULL,
  `REQUEST_DT` datetime DEFAULT NULL,
  `SOURCE` varchar(80) DEFAULT NULL,
  `CLINICIAN` varchar(80) DEFAULT NULL,
  `ROOM_NO` varchar(15) DEFAULT NULL,
  `PRIORITY` enum('R','U') DEFAULT NULL,
  `COMMENT` varchar(80) DEFAULT NULL,
  `VISITNO` varchar(17) DEFAULT NULL,
  `ORDER_TESTID` varchar(2000) DEFAULT NULL,
  `FLAG` enum('0','1') DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `LIS_ORDER`
--

INSERT INTO `LIS_ORDER` (`ID`, `MESSAGE_DT`, `ORDER_CONTROL`, `PID`, `PNAME`, `ADDRESS1`, `ADDRESS2`, `ADDRESS3`, `ADDRESS4`, `PTYPE`, `BIRTH_DT`, `SEX`, `ONO`, `REQUEST_DT`, `SOURCE`, `CLINICIAN`, `ROOM_NO`, `PRIORITY`, `COMMENT`, `VISITNO`, `ORDER_TESTID`, `FLAG`) VALUES
(11, '2020-02-13 07:41:41', 'NW', '0100100', 'MARYANA OLPAH', 'JL.DURIAN RT 02', '-', '-', '-', 'OP', '2016-02-04', '1', 'PL201909020001', '2019-09-02 08:56:53', 'INT^INT Poli Penyakit Dalam', 'D0000010^dr. ADHI MP PULUNGAN', 'INT', 'R', '', '2019/09/02/000001', '2197~2196~2195~2194~487~486~473', '0'),
(12, '2020-02-13 07:41:46', 'NW', '0100100', 'MARYANA OLPAH', 'JL.DURIAN RT 02', '-', '-', '-', 'OP', '2016-02-04', '1', 'PL201909160002', '2019-09-16 15:14:07', 'OBG^OBG Poli Obstetri/Gyn.', 'D0000052^dr. DEXA RIVANDI', 'OBG', 'R', '', '2019/09/16/000004', '2197~2196~2195~2194~487~473', '0'),
(13, '2020-02-13 07:41:58', 'NW', '222342', 'SANTRI LIRBOYO', '', '-', '-', '-', 'IN', '2019-09-02', '1', 'PL201909070001', '2019-09-07 15:36:22', 'B0003^Kamar Bersalin', 'D0000044^dr. DWI KRISNAWATI', 'KB2', 'R', '', '2019/09/02/000003', '2197~2196~2195~2194~473', '0'),
(15, '2020-02-13 07:43:38', 'NW', '222342', 'SANTRI LIRBOYO', '', '-', '-', '-', 'IN', '2019-09-02', '1', 'PL201909080001', '2019-09-08 11:52:05', 'B0003^Kamar Bersalin', 'D0000044^dr. DWI KRISNAWATI', 'KB2', 'R', '', '2019/09/02/000003', '2197~2194~473', '0'),
(16, '2020-02-13 07:44:00', 'NW', '222342', 'SANTRI LIRBOYO', '', '-', '-', '-', 'IN', '2019-09-02', '1', 'PL201910020002', '2019-10-02 11:40:43', 'B0003^Kamar Bersalin', 'D0000044^dr. DWI KRISNAWATI', 'KB2', 'R', '', '2019/09/02/000003', '2362~2197~2196~2195~2194~487~486~484~483~482~481~477~476~475~474~473', '0'),
(17, '2020-02-13 07:44:07', 'NW', '231004', 'Candra Dewi', 'ALAMAT', '-', '-', '-', 'IN', '1988-01-28', '2', 'PL201909270002', '2019-09-27 08:48:10', 'B0006^Kamar Kelas I', 'D0000048^dr. HANY MUSLIHA', 'I A6', 'R', '', '2019/09/25/000005', '2197~2196~2195~2194~487~486~484~483~482~481~477~476~475~473', '0');

-- --------------------------------------------------------

--
-- Table structure for table `LIS_ORDER_DETAIL`
--

CREATE TABLE `LIS_ORDER_DETAIL` (
  `ID` bigint(20) NOT NULL,
  `MESSAGE_DT` datetime DEFAULT NULL,
  `ORDER_CONTROL` enum('NW','RP','CA') DEFAULT NULL,
  `PID` varchar(15) DEFAULT NULL,
  `PNAME` varchar(40) DEFAULT NULL,
  `ADDRESS1` varchar(100) DEFAULT NULL,
  `ADDRESS2` varchar(100) DEFAULT NULL,
  `ADDRESS3` varchar(100) DEFAULT NULL,
  `ADDRESS4` varchar(100) DEFAULT NULL,
  `PTYPE` enum('IN','OP') DEFAULT NULL,
  `BIRTH_DT` date DEFAULT NULL,
  `SEX` enum('1','2') DEFAULT NULL,
  `ONO` varchar(17) DEFAULT NULL,
  `REQUEST_DT` datetime DEFAULT NULL,
  `SOURCE` varchar(80) DEFAULT NULL,
  `CLINICIAN` varchar(80) DEFAULT NULL,
  `ROOM_NO` varchar(15) DEFAULT NULL,
  `PRIORITY` enum('R','U') DEFAULT NULL,
  `COMMENT` varchar(80) DEFAULT NULL,
  `VISITNO` varchar(17) DEFAULT NULL,
  `ORDER_TESTID` varchar(2000) DEFAULT NULL,
  `FLAG` enum('0','1') DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `permintaan_lab`
--

CREATE TABLE `permintaan_lab` (
  `noorder` varchar(15) NOT NULL,
  `no_rawat` varchar(17) NOT NULL,
  `no_rkm_medis` varchar(15) NOT NULL,
  `nm_pasien` varchar(40) NOT NULL,
  `email` varchar(50) NOT NULL,
  `jk` enum('L','P') DEFAULT NULL,
  `tmp_lahir` varchar(15) DEFAULT NULL,
  `tgl_lahir` date DEFAULT NULL,
  `alamat` varchar(200) DEFAULT NULL,
  `tgl_permintaan` date NOT NULL,
  `jam_permintaan` time NOT NULL,
  `kode_dokter_perujuk` varchar(20) NOT NULL,
  `dokter_perujuk` varchar(50) NOT NULL,
  `status` enum('ralan','ranap') NOT NULL,
  `kode_ruang` varchar(20) NOT NULL,
  `nama_ruang` varchar(50) NOT NULL,
  `kode_carabayar` varchar(20) NOT NULL,
  `nama_carabayar` varchar(50) NOT NULL,
  `informasi_tambahan` varchar(60) NOT NULL,
  `diagnosa_klinis` varchar(80) NOT NULL,
  `status_ambil` enum('0','1') NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `permintaan_lab`
--

INSERT INTO `permintaan_lab` (`noorder`, `no_rawat`, `no_rkm_medis`, `nm_pasien`, `email`, `jk`, `tmp_lahir`, `tgl_lahir`, `alamat`, `tgl_permintaan`, `jam_permintaan`, `kode_dokter_perujuk`, `dokter_perujuk`, `status`, `kode_ruang`, `nama_ruang`, `kode_carabayar`, `nama_carabayar`, `informasi_tambahan`, `diagnosa_klinis`, `status_ambil`) VALUES
('PL202004050001', '2020/04/05/000001', '020324', 'AGUS BUDIYONO', '-', 'L', '-', '1986-12-17', 'GRESIK, SEKAPUK, SEKAPUK, GRESIK, JAWA TIMUR', '2020-04-05', '07:01:45', 'D0000045', 'dr. ADHARI AJI PURNOMO', 'ralan', 'INT', 'INT Poli Penyakit Dalam', 'A08', 'Asuransi BNI Life', '1', '2', '0'),
('PL202004050003', '2020/03/26/000002', '020323', 'SAIFUL UMAM', '-', 'L', '-', '1986-01-10', 'ALAMAT, -, -, -, -', '2020-04-05', '09:14:01', 'D0000041', 'dr. BAMBANG KURNIAWAN, Sp.OG', 'ranap', 'I A1', 'Kamar Kelas I', 'A65', 'BPJS', '1', '2', '0');

-- --------------------------------------------------------

--
-- Table structure for table `RESDT`
--

CREATE TABLE `RESDT` (
  `ID` bigint(20) NOT NULL,
  `ONO` varchar(17) NOT NULL,
  `TEST_CD` char(20) NOT NULL,
  `TEST_NM` varchar(50) DEFAULT NULL,
  `DATA_TYP` enum('NM','ST','FT') DEFAULT NULL,
  `RESULT_VALUE` varchar(40) DEFAULT NULL,
  `RESULT_FT` varchar(8192) DEFAULT NULL,
  `UNIT` varchar(15) DEFAULT NULL,
  `FLAG` enum('L','H','LL','HH') DEFAULT NULL,
  `REF_RANGE` varchar(50) DEFAULT NULL,
  `STATUS` enum('D','F') DEFAULT NULL,
  `TEST_COMMENT` varchar(300) DEFAULT NULL,
  `VALIDATE_BY` varchar(60) DEFAULT NULL,
  `VALIDATE_ON` datetime DEFAULT NULL,
  `DISP_SEQ` varchar(15) DEFAULT NULL,
  `ORDER_TESTID` varchar(6) DEFAULT NULL,
  `ORDER_TESTNM` varchar(30) DEFAULT NULL,
  `TEST_GROUP` varchar(20) DEFAULT NULL,
  `ITEM_PARENT` varchar(6) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `RESHD`
--

CREATE TABLE `RESHD` (
  `ID` bigint(20) NOT NULL,
  `PID` varchar(15) DEFAULT NULL,
  `APID` varchar(16) DEFAULT NULL,
  `PNAME` varchar(40) DEFAULT NULL,
  `ONO` varchar(17) NOT NULL,
  `LNO` varchar(20) DEFAULT NULL,
  `REQUEST_DT` datetime DEFAULT NULL,
  `SOURCE_CD` varchar(6) DEFAULT NULL,
  `SOURCE_NM` varchar(50) DEFAULT NULL,
  `CLINICIAN_CD` varchar(20) DEFAULT NULL,
  `CLINICIAN_NM` varchar(50) DEFAULT NULL,
  `PRIORITY` enum('R','U') DEFAULT NULL,
  `COMMENT` varchar(80) DEFAULT NULL,
  `VISITNO` varchar(17) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detail_hasil_lab`
--
ALTER TABLE `detail_hasil_lab`
  ADD PRIMARY KEY (`noorder`,`kd_jenis_prw`,`id_template`);

--
-- Indexes for table `detail_permintaan_lab`
--
ALTER TABLE `detail_permintaan_lab`
  ADD PRIMARY KEY (`noorder`,`kd_jenis_prw`,`id_template`);

--
-- Indexes for table `LIS_ORDER`
--
ALTER TABLE `LIS_ORDER`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `ONO` (`ONO`);

--
-- Indexes for table `LIS_ORDER_DETAIL`
--
ALTER TABLE `LIS_ORDER_DETAIL`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `ONO` (`ONO`);

--
-- Indexes for table `permintaan_lab`
--
ALTER TABLE `permintaan_lab`
  ADD PRIMARY KEY (`noorder`);

--
-- Indexes for table `RESDT`
--
ALTER TABLE `RESDT`
  ADD PRIMARY KEY (`ID`,`ONO`,`TEST_CD`),
  ADD UNIQUE KEY `ONO` (`ONO`,`TEST_CD`);

--
-- Indexes for table `RESHD`
--
ALTER TABLE `RESHD`
  ADD PRIMARY KEY (`ID`,`ONO`),
  ADD UNIQUE KEY `ONO` (`ONO`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `LIS_ORDER`
--
ALTER TABLE `LIS_ORDER`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `LIS_ORDER_DETAIL`
--
ALTER TABLE `LIS_ORDER_DETAIL`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `RESDT`
--
ALTER TABLE `RESDT`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `RESHD`
--
ALTER TABLE `RESHD`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

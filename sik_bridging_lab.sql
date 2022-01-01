-- MariaDB dump 10.19  Distrib 10.4.20-MariaDB, for osx10.10 (x86_64)
--
-- Host: localhost    Database: sik_bridging_lab
-- ------------------------------------------------------
-- Server version	10.4.20-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `HisToLisDetail`
--

DROP TABLE IF EXISTS `HisToLisDetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HisToLisDetail` (
  `RowID` int(11) NOT NULL AUTO_INCREMENT,
  `HisToLisHeaderID` int(11) NOT NULL,
  `HisKodeTest` varchar(50) NOT NULL,
  `Cito` char(1) NOT NULL,
  `tgl_insert` datetime DEFAULT NULL,
  `Qty` int(11) DEFAULT NULL,
  PRIMARY KEY (`RowID`)
) ENGINE=MyISAM AUTO_INCREMENT=33 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HisToLisDetail`
--

LOCK TABLES `HisToLisDetail` WRITE;
/*!40000 ALTER TABLE `HisToLisDetail` DISABLE KEYS */;
INSERT INTO `HisToLisDetail` VALUES (1,5,'665','N','2021-10-05 00:00:00',0),(2,5,'666','N','2021-10-05 00:00:00',0),(3,5,'667','N','2021-10-05 00:00:00',0),(4,5,'668','N','2021-10-05 00:00:00',0),(5,5,'669','N','2021-10-05 00:00:00',0),(6,5,'670','N','2021-10-05 00:00:00',0),(7,5,'671','N','2021-10-05 00:00:00',0),(8,5,'672','N','2021-10-05 00:00:00',0),(9,5,'673','N','2021-10-05 00:00:00',0),(10,5,'674','N','2021-10-05 00:00:00',0),(11,5,'675','N','2021-10-05 00:00:00',0),(12,5,'2198','N','2021-10-05 00:00:00',0),(13,5,'2199','N','2021-10-05 00:00:00',0),(14,5,'2200','N','2021-10-05 00:00:00',0),(15,5,'2201','N','2021-10-05 00:00:00',0),(16,5,'2202','N','2021-10-05 00:00:00',0),(17,6,'665','N','2021-10-14 00:00:00',0),(18,6,'666','N','2021-10-14 00:00:00',0),(19,6,'667','N','2021-10-14 00:00:00',0),(20,6,'668','N','2021-10-14 00:00:00',0),(21,6,'669','N','2021-10-14 00:00:00',0),(22,6,'670','N','2021-10-14 00:00:00',0),(23,6,'671','N','2021-10-14 00:00:00',0),(24,6,'672','N','2021-10-14 00:00:00',0),(25,6,'673','N','2021-10-14 00:00:00',0),(26,6,'674','N','2021-10-14 00:00:00',0),(27,6,'675','N','2021-10-14 00:00:00',0),(28,6,'2198','N','2021-10-14 00:00:00',0),(29,6,'2199','N','2021-10-14 00:00:00',0),(30,6,'2200','N','2021-10-14 00:00:00',0),(31,6,'2201','N','2021-10-14 00:00:00',0),(32,6,'2202','N','2021-10-14 00:00:00',0);
/*!40000 ALTER TABLE `HisToLisDetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HisToLisHeader`
--

DROP TABLE IF EXISTS `HisToLisHeader`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HisToLisHeader` (
  `RowID` int(11) NOT NULL AUTO_INCREMENT,
  `HisNoReg` varchar(50) NOT NULL,
  `HisNoOrder` varchar(50) NOT NULL,
  `Ipop` varchar(50) NOT NULL,
  `NoRM` varchar(50) NOT NULL,
  `NamaPasien` varchar(200) NOT NULL,
  `JenisKelamin` char(10) DEFAULT NULL,
  `TglLahir` datetime DEFAULT NULL,
  `AlamatPasien` varchar(200) DEFAULT NULL,
  `KodeRuang` varchar(50) DEFAULT NULL,
  `Ruangan` varchar(200) DEFAULT NULL,
  `KodeDokterKlinisi` varchar(100) DEFAULT NULL,
  `NamaDokterKlinisi` varchar(100) DEFAULT NULL,
  `KodeDokter` varchar(50) DEFAULT NULL,
  `DokterKodePJ` varchar(100) DEFAULT NULL,
  `Diagnosa` varchar(200) DEFAULT NULL,
  `JenisPasien` varchar(50) DEFAULT NULL,
  `Kelas` varchar(50) DEFAULT NULL,
  `flag_lempar` char(1) DEFAULT NULL,
  `DetailCount` int(11) DEFAULT NULL,
  `copyorder` varchar(1) DEFAULT NULL,
  `NoSample` varchar(50) DEFAULT NULL,
  `NamaDokterPJ` varchar(200) DEFAULT NULL,
  `Action` char(10) NOT NULL,
  `UnitPengirim` varchar(50) DEFAULT NULL,
  `Rujukan` char(10) NOT NULL,
  `Status` char(10) NOT NULL,
  `HisTanggalKirim` datetime NOT NULL,
  `LisTanggalTerima` datetime DEFAULT NULL,
  PRIMARY KEY (`RowID`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HisToLisHeader`
--

LOCK TABLES `HisToLisHeader` WRITE;
/*!40000 ALTER TABLE `HisToLisHeader` DISABLE KEYS */;
INSERT INTO `HisToLisHeader` VALUES (5,'2021/10/05/000002','PL202110050001','Rawat Jalan','000001','WINDIARTO','L','1988-02-24 00:00:00',', -, -, -, -','INT','INT Poli Penyakit Dalam','D0000002','dr. Aisyah','D0000002','dr. Aisyah','-','UMUM','Ralan','0',16,'0','PL202110050001','dr. Aisyah','A','INT Poli Penyakit Dalam','N','0','2021-10-05 00:00:00','2021-10-05 00:00:00'),(6,'2021/09/22/000001','PL202110140001','Rawat Inap','-','-','L','2021-09-22 00:00:00',', -, -, -, -','K1','Kamar Kelas I','D0000002','dr. Aisyah','D0000002','dr. Aisyah','-','UMUM','Kelas 1','0',16,'0','PL202110140001','dr. Aisyah','A','K1.01','N','0','2021-10-14 00:00:00','2021-10-14 00:00:00');
/*!40000 ALTER TABLE `HisToLisHeader` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LIS_ORDER`
--

DROP TABLE IF EXISTS `LIS_ORDER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LIS_ORDER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `FLAG` enum('0','1') DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ONO` (`ONO`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LIS_ORDER`
--

LOCK TABLES `LIS_ORDER` WRITE;
/*!40000 ALTER TABLE `LIS_ORDER` DISABLE KEYS */;
INSERT INTO `LIS_ORDER` VALUES (11,'2020-02-13 07:41:41','NW','0100100','MARYANA OLPAH','JL.DURIAN RT 02','-','-','-','OP','2016-02-04','1','PL201909020001','2019-09-02 08:56:53','INT^INT Poli Penyakit Dalam','D0000010^dr. ADHI MP PULUNGAN','INT','R','','2019/09/02/000001','2197~2196~2195~2194~487~486~473','0'),(12,'2020-02-13 07:41:46','NW','0100100','MARYANA OLPAH','JL.DURIAN RT 02','-','-','-','OP','2016-02-04','1','PL201909160002','2019-09-16 15:14:07','OBG^OBG Poli Obstetri/Gyn.','D0000052^dr. DEXA RIVANDI','OBG','R','','2019/09/16/000004','2197~2196~2195~2194~487~473','0'),(13,'2020-02-13 07:41:58','NW','222342','SANTRI LIRBOYO','','-','-','-','IN','2019-09-02','1','PL201909070001','2019-09-07 15:36:22','B0003^Kamar Bersalin','D0000044^dr. DWI KRISNAWATI','KB2','R','','2019/09/02/000003','2197~2196~2195~2194~473','0'),(15,'2020-02-13 07:43:38','NW','222342','SANTRI LIRBOYO','','-','-','-','IN','2019-09-02','1','PL201909080001','2019-09-08 11:52:05','B0003^Kamar Bersalin','D0000044^dr. DWI KRISNAWATI','KB2','R','','2019/09/02/000003','2197~2194~473','0'),(16,'2020-02-13 07:44:00','NW','222342','SANTRI LIRBOYO','','-','-','-','IN','2019-09-02','1','PL201910020002','2019-10-02 11:40:43','B0003^Kamar Bersalin','D0000044^dr. DWI KRISNAWATI','KB2','R','','2019/09/02/000003','2362~2197~2196~2195~2194~487~486~484~483~482~481~477~476~475~474~473','0'),(17,'2020-02-13 07:44:07','NW','231004','Candra Dewi','ALAMAT','-','-','-','IN','1988-01-28','2','PL201909270002','2019-09-27 08:48:10','B0006^Kamar Kelas I','D0000048^dr. HANY MUSLIHA','I A6','R','','2019/09/25/000005','2197~2196~2195~2194~487~486~484~483~482~481~477~476~475~473','0');
/*!40000 ALTER TABLE `LIS_ORDER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LIS_ORDER_DETAIL`
--

DROP TABLE IF EXISTS `LIS_ORDER_DETAIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LIS_ORDER_DETAIL` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `FLAG` enum('0','1') DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ONO` (`ONO`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LIS_ORDER_DETAIL`
--

LOCK TABLES `LIS_ORDER_DETAIL` WRITE;
/*!40000 ALTER TABLE `LIS_ORDER_DETAIL` DISABLE KEYS */;
/*!40000 ALTER TABLE `LIS_ORDER_DETAIL` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESDT`
--

DROP TABLE IF EXISTS `RESDT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESDT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `ITEM_PARENT` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`ID`,`ONO`,`TEST_CD`),
  UNIQUE KEY `ONO` (`ONO`,`TEST_CD`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESDT`
--

LOCK TABLES `RESDT` WRITE;
/*!40000 ALTER TABLE `RESDT` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESDT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RESHD`
--

DROP TABLE IF EXISTS `RESHD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RESHD` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `VISITNO` varchar(17) DEFAULT NULL,
  PRIMARY KEY (`ID`,`ONO`),
  UNIQUE KEY `ONO` (`ONO`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RESHD`
--

LOCK TABLES `RESHD` WRITE;
/*!40000 ALTER TABLE `RESHD` DISABLE KEYS */;
/*!40000 ALTER TABLE `RESHD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detail_hasil_lab`
--

DROP TABLE IF EXISTS `detail_hasil_lab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detail_hasil_lab` (
  `noorder` varchar(15) NOT NULL,
  `kd_jenis_prw` varchar(15) NOT NULL,
  `id_template` int(11) NOT NULL,
  `nilai` varchar(60) NOT NULL,
  `nilai_rujukan` varchar(30) NOT NULL,
  `keterangan` varchar(60) NOT NULL,
  PRIMARY KEY (`noorder`,`kd_jenis_prw`,`id_template`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detail_hasil_lab`
--

LOCK TABLES `detail_hasil_lab` WRITE;
/*!40000 ALTER TABLE `detail_hasil_lab` DISABLE KEYS */;
/*!40000 ALTER TABLE `detail_hasil_lab` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detail_permintaan_lab`
--

DROP TABLE IF EXISTS `detail_permintaan_lab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detail_permintaan_lab` (
  `noorder` varchar(15) NOT NULL,
  `kd_jenis_prw` varchar(15) NOT NULL,
  `id_template` int(11) NOT NULL,
  PRIMARY KEY (`noorder`,`kd_jenis_prw`,`id_template`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detail_permintaan_lab`
--

LOCK TABLES `detail_permintaan_lab` WRITE;
/*!40000 ALTER TABLE `detail_permintaan_lab` DISABLE KEYS */;
INSERT INTO `detail_permintaan_lab` VALUES ('PL202004050001','101-K.3',473),('PL202004050001','101-K.3',482),('PL202004050001','101-K.3',483),('PL202004050001','101-K.3',484),('PL202004050001','101-K.3',487),('PL202004050001','101-K.3',2195),('PL202004050001','101-K.3',2196),('PL202004050003','102-K.2',665),('PL202004050003','102-K.2',666),('PL202004050003','102-K.2',667),('PL202004050003','102-K.2',671),('PL202004050003','102-K.2',673),('PL202004050003','102-K.2',675);
/*!40000 ALTER TABLE `detail_permintaan_lab` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permintaan_lab`
--

DROP TABLE IF EXISTS `permintaan_lab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
  PRIMARY KEY (`noorder`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permintaan_lab`
--

LOCK TABLES `permintaan_lab` WRITE;
/*!40000 ALTER TABLE `permintaan_lab` DISABLE KEYS */;
INSERT INTO `permintaan_lab` VALUES ('PL202004050001','2020/04/05/000001','020324','AGUS BUDIYONO','-','L','-','1986-12-17','GRESIK, SEKAPUK, SEKAPUK, GRESIK, JAWA TIMUR','2020-04-05','07:01:45','D0000045','dr. ADHARI AJI PURNOMO','ralan','INT','INT Poli Penyakit Dalam','A08','Asuransi BNI Life','1','2'),('PL202004050003','2020/03/26/000002','020323','SAIFUL UMAM','-','L','-','1986-01-10','ALAMAT, -, -, -, -','2020-04-05','09:14:01','D0000041','dr. BAMBANG KURNIAWAN, Sp.OG','ranap','I A1','Kamar Kelas I','A65','BPJS','1','2');
/*!40000 ALTER TABLE `permintaan_lab` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-19 23:13:54

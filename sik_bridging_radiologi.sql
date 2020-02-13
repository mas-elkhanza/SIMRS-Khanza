-- MySQL dump 10.16  Distrib 10.1.37-MariaDB, for osx10.10 (x86_64)
--
-- Host: localhost    Database: sik_bridging_radiologi
-- ------------------------------------------------------
-- Server version	10.1.37-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `order_in`
--

DROP TABLE IF EXISTS `order_in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_in` (
  `tanggal_order` datetime NOT NULL,
  `no_rm` varchar(150) NOT NULL,
  `no_rontgen` varchar(17) NOT NULL,
  `no_register` varchar(17) NOT NULL,
  `nama_pasien` varchar(40) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `kelamin` enum('M','F') NOT NULL,
  `dokter_pengirim` varchar(50) NOT NULL,
  `dokter_radiolog` varchar(50) NOT NULL,
  `asal_ruangan` varchar(50) NOT NULL,
  `kode_tindakan` varchar(15) NOT NULL,
  `tindakan_radiologi` varchar(80) NOT NULL,
  `jasa_sarana` double NOT NULL,
  `bhp` double NOT NULL,
  `tarif_perujuk` double NOT NULL,
  `jasa_dokter_radiolog` double NOT NULL,
  `jasa_petugas_radiologi` double NOT NULL,
  `kso` double NOT NULL,
  `menejemen` double NOT NULL,
  `total_biaya` double NOT NULL,
  `informasi_tambahan` varchar(100) NOT NULL,
  `diagnosa_klinis` varchar(150) NOT NULL,
  `statusupdate` enum('0','1','2') NOT NULL,
  PRIMARY KEY (`no_rontgen`,`kode_tindakan`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_in`
--

LOCK TABLES `order_in` WRITE;
/*!40000 ALTER TABLE `order_in` DISABLE KEYS */;
INSERT INTO `order_in` VALUES ('2019-09-27 09:42:39','231004','201909270002','2019/09/25/000005','231004 Candra Dewi','1988-01-28','F','','dr. FERRY MULYADI, Sp.A, M.Kes','','ICU-01','THORAX AP/PA',98000,0,30000,45000,7000,0,0,180000,'','','0'),('2019-09-27 09:41:45','231003','201909270001','2019/09/27/000001','231003 FEBRI ARUNG RAGA','2003-02-08','M','dr. AZIS ARI WIBOWO','dr. FERRY MULYADI, Sp.A, M.Kes','Ilmu Penyakit Dalam','ICU-01','THORAX AP/PA',98000,0,30000,45000,7000,0,0,180000,'','','0'),('2019-09-02 08:57:58','0100100','201909020001','2019/09/02/000001','0100100 MARYANA OLPAH','2016-02-04','M','','dr. FERRY MULYADI, Sp.A, M.Kes','','ICU-01','THORAX AP/PA',98000,0,30000,45000,7000,0,0,180000,'','','0');
/*!40000 ALTER TABLE `order_in` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_out`
--

DROP TABLE IF EXISTS `order_out`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_out` (
  `tanggal_order` datetime DEFAULT NULL,
  `no_rm` varchar(15) DEFAULT NULL,
  `no_rontgen` varchar(17) NOT NULL,
  `no_register` varchar(17) DEFAULT NULL,
  `nama_pasien` varchar(40) DEFAULT NULL,
  `expertise_finding` text,
  `expertise_conclusion` text,
  `expertise_bookmark` text,
  `dokter_radiolog` varchar(50) DEFAULT NULL,
  `link_ris` varchar(100) DEFAULT NULL,
  `link_synapse` varchar(100) DEFAULT NULL,
  `link_mobility` varchar(100) DEFAULT NULL,
  `kode_tindakan` varchar(15) NOT NULL,
  `tindakan_radiologi` varchar(80) DEFAULT NULL,
  `proyeksi` varchar(50) DEFAULT NULL,
  `kV` varchar(10) DEFAULT NULL,
  `mAS` varchar(10) DEFAULT NULL,
  `FFD` varchar(10) DEFAULT NULL,
  `BSF` varchar(10) DEFAULT NULL,
  `inak` varchar(10) DEFAULT NULL,
  `jml_penyinaran` varchar(10) DEFAULT NULL,
  `dosis` varchar(20) DEFAULT NULL,
  `statusupdate` enum('0','1','2') NOT NULL,
  PRIMARY KEY (`no_rontgen`,`kode_tindakan`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_out`
--

LOCK TABLES `order_out` WRITE;
/*!40000 ALTER TABLE `order_out` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_out` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-13  9:28:20

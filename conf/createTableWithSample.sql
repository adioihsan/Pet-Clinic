-- MariaDB dump 10.19  Distrib 10.4.18-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: pet_clinic
-- ------------------------------------------------------
-- Server version	10.4.18-MariaDB

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
-- Table structure for table `action`
--

DROP TABLE IF EXISTS `action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action` (
  `actionId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`actionId`)
) ENGINE=InnoDB AUTO_INCREMENT=21007 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action`
--

LOCK TABLES `action` WRITE;
/*!40000 ALTER TABLE `action` DISABLE KEYS */;
INSERT INTO `action` VALUES (21000,'Vaksin Strain 19',50000),(21001,'Vaksin Rabies',50000),(21002,'Konsultasi',25000),(21003,'Grooming Kutu',100000),(21004,'Grooming Perawatan',75000),(21005,'Pembersihan Mulut',65000),(21006,'Cabut Gigi',15000);
/*!40000 ALTER TABLE `action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actionsdata`
--

DROP TABLE IF EXISTS `actionsdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actionsdata` (
  `medicRecordId` int(11) DEFAULT NULL,
  `actionId` int(11) DEFAULT NULL,
  `description` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  KEY `actionsData1_FK` (`actionId`),
  KEY `actionsData2_FK` (`medicRecordId`),
  CONSTRAINT `actionsData1_FK` FOREIGN KEY (`actionId`) REFERENCES `action` (`actionId`) ON DELETE CASCADE,
  CONSTRAINT `actionsData2_FK` FOREIGN KEY (`medicRecordId`) REFERENCES `medicrecord` (`medicRecordId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actionsdata`
--

LOCK TABLES `actionsdata` WRITE;
/*!40000 ALTER TABLE `actionsdata` DISABLE KEYS */;
INSERT INTO `actionsdata` VALUES (10000,21005,'Pembersihan Kecil'),(10001,21004,'Perwatan Biasa');
/*!40000 ALTER TABLE `actionsdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guestbook`
--

DROP TABLE IF EXISTS `guestbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guestbook` (
  `petId` int(11) DEFAULT NULL,
  `visitTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guestbook`
--

LOCK TABLES `guestbook` WRITE;
/*!40000 ALTER TABLE `guestbook` DISABLE KEYS */;
INSERT INTO `guestbook` VALUES (2520,'2021-06-17 00:00:00'),(2521,'2021-06-17 00:00:00'),(2523,'2021-06-17 00:13:57'),(2525,'2021-06-17 00:26:05'),(2526,'2021-06-18 00:31:00'),(2529,'2021-06-21 00:36:03'),(2521,'2021-06-22 19:17:32'),(2526,'2021-06-22 19:41:36');
/*!40000 ALTER TABLE `guestbook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `invoiceId` int(11) NOT NULL AUTO_INCREMENT,
  `petId` int(11) DEFAULT NULL,
  `medicRecordId` int(11) DEFAULT NULL,
  `totalAmount` double DEFAULT NULL,
  `createDate` datetime DEFAULT NULL,
  `userId` int(11) DEFAULT NULL,
  PRIMARY KEY (`invoiceId`),
  KEY `invoice_FK_1` (`userId`),
  KEY `invoice_FK_T1` (`petId`),
  KEY `invoice_FK` (`medicRecordId`),
  CONSTRAINT `invoice_FK` FOREIGN KEY (`medicRecordId`) REFERENCES `medicrecord` (`medicRecordId`) ON DELETE SET NULL,
  CONSTRAINT `invoice_FK_T1` FOREIGN KEY (`petId`) REFERENCES `pet` (`petId`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=21214 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoicesdata`
--

DROP TABLE IF EXISTS `invoicesdata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoicesdata` (
  `invoiceId` int(11) DEFAULT NULL,
  `item` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `itemType` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `priceTotal` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  KEY `invoicesData_FK` (`invoiceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoicesdata`
--

LOCK TABLES `invoicesdata` WRITE;
/*!40000 ALTER TABLE `invoicesdata` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoicesdata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicine` (
  `medicineId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `unit` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `inStock` int(11) DEFAULT NULL,
  `outStock` int(11) DEFAULT NULL,
  `buyPrice` double DEFAULT NULL,
  `sellPrice` double DEFAULT NULL,
  `fill` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `expired` date DEFAULT NULL,
  PRIMARY KEY (`medicineId`)
) ENGINE=InnoDB AUTO_INCREMENT=508 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
INSERT INTO `medicine` VALUES (500,'Intertim LA',50,'Botol',50,0,11000,11500,'60ml','2023-12-03'),(501,'Vet-Oxy SB',65,'Botol',65,0,29000,29500,'50ml','2023-12-03'),(502,'Primacat',49,'Botol',50,1,33000,33500,'30ml','2023-10-02'),(503,'Animalcylin',25,'Botol',25,0,15000,16000,'50gr','2023-10-02'),(504,'iCat Anti Flu Formula',39,'Botol',40,1,20000,20500,'5ml','2023-10-02'),(505,'Detick',40,'Pcs/Botol',40,0,22000,22500,'2ml','2023-10-02'),(506,'Paracetamol',120,'Strip',1,1,4500,4500,'4.0','2025-12-30'),(507,'Doparmin',100,'Strip',NULL,NULL,14000,14000,'5g','2026-06-18');
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicrecord`
--

DROP TABLE IF EXISTS `medicrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicrecord` (
  `petId` int(11) DEFAULT NULL,
  `anamnesis` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `diagnosis` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `recordDate` datetime DEFAULT NULL,
  `veterinarianId` int(11) DEFAULT NULL,
  `medicRecordId` int(11) NOT NULL AUTO_INCREMENT,
  `petWeight` double DEFAULT NULL,
  `status` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`medicRecordId`),
  KEY `medicRecord_FKT_1` (`petId`),
  KEY `medicRecord_FK` (`veterinarianId`),
  CONSTRAINT `medicRecord_FK` FOREIGN KEY (`veterinarianId`) REFERENCES `veterinarian` (`veterinarianId`) ON DELETE SET NULL,
  CONSTRAINT `medicRecord_FKT_1` FOREIGN KEY (`petId`) REFERENCES `pet` (`petId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10002 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicrecord`
--

LOCK TABLES `medicrecord` WRITE;
/*!40000 ALTER TABLE `medicrecord` DISABLE KEYS */;
INSERT INTO `medicrecord` VALUES (2530,'Malam Makan ','Deman karean tumbuh gigi','2021-06-17 00:00:00',10,10000,0.5,'Sudah Bayar'),(2520,'Malas','Kegemukan','2021-06-17 00:00:00',11,10001,6,'Belum Ada Pembayaran');
/*!40000 ALTER TABLE `medicrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pet`
--

DROP TABLE IF EXISTS `pet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pet` (
  `petId` int(11) NOT NULL AUTO_INCREMENT,
  `petOwnerId` int(11) NOT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `kind` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `race` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `color` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `photo` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`petId`),
  KEY `pet_FK` (`petOwnerId`),
  CONSTRAINT `pet_FK` FOREIGN KEY (`petOwnerId`) REFERENCES `petowner` (`petOwnerId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2532 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet`
--

LOCK TABLES `pet` WRITE;
/*!40000 ALTER TABLE `pet` DISABLE KEYS */;
INSERT INTO `pet` VALUES (2520,5000,'Lily','2008-09-01','Betina','Kucing','Persia','Abu abu','2520Lily.jpg','2021-04-22 17:00:00'),(2521,5001,'Bruno','2019-05-08','Jantan','Anjing','Alaskan Malamute','Belang orange dan putih','2521Bruno.jpg','2021-05-04 17:00:00'),(2522,5002,'Butterball','2018-09-11','Betina','Kucing','Anggora','Putih','2522Butterball.png','2021-05-23 17:00:00'),(2523,5003,'Waba','2012-09-24','Jantan','Anjing','Akita','Belang orange dan putih','2523Waba.jpg','2021-06-02 17:00:00'),(2524,5004,'Yogini','2011-02-02','Jantan','Kucing','Siamese','Belang putih dan coklat','2524Yogini.jpg','2021-06-07 17:00:00'),(2525,5005,'Coco','2017-07-08','Jantan','Anjing','Begle','Belang hitam,putih dan orange','2525Coco.png','2021-06-11 17:00:00'),(2526,5006,'Grace','2010-03-04','Betina','Kucing','Ragdoll','Belang putih abu abu','2526Grace.jpg','2021-06-12 17:00:00'),(2527,5007,'Homey','2010-03-27','Jantan','Anjing','Boxer','Belang hitam, putih dan orange','2527Homey.png','2021-06-13 17:00:00'),(2528,5008,'Marble','2014-08-28','Betina','Kucing','Mainecoon','Belang hitam, putih dan abu abu','2528Marble.jpg','2021-06-13 17:00:00'),(2529,5008,'Meowy','2012-05-01','Laki laki','Anjing','Cihuahua','Belang orange dan putih','2529Meowy.jpg','2021-06-14 17:00:00'),(2530,5009,'Luna','2018-01-03','Betina','Hamster','Lokal','Orage dan Putih','2530Luna.png','2021-06-16 15:38:59'),(2531,5010,'Sapina','2015-01-08','Betina','Sapi','Ausi','Coklat Muda','2531Sapina.png','2021-06-22 12:49:19');
/*!40000 ALTER TABLE `pet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `petkind`
--

DROP TABLE IF EXISTS `petkind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `petkind` (
  `petKindId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`petKindId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `petkind`
--

LOCK TABLES `petkind` WRITE;
/*!40000 ALTER TABLE `petkind` DISABLE KEYS */;
INSERT INTO `petkind` VALUES (1,'Kucing'),(2,'Anjing'),(3,'Kelinci'),(4,'Hamster'),(5,'Burung'),(7,'Sapi'),(8,'Tupai');
/*!40000 ALTER TABLE `petkind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `petowner`
--

DROP TABLE IF EXISTS `petowner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `petowner` (
  `petOwnerId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastName` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phoneNumber` double DEFAULT NULL,
  `address` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `photo` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`petOwnerId`)
) ENGINE=InnoDB AUTO_INCREMENT=5011 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `petowner`
--

LOCK TABLES `petowner` WRITE;
/*!40000 ALTER TABLE `petowner` DISABLE KEYS */;
INSERT INTO `petowner` VALUES (5000,'Alfa','Rizki','1999-03-11','Perempuan',6282356985214,'Sunrise Garden Complex No.8','5000Alfa.jpg','2021-04-22 17:00:00'),(5001,'Anggoro ','Putra','2001-11-01','Laki laki',6283612365680,'Jakarta Industrial Estate','5001Anggoro .png','2021-05-04 17:00:00'),(5002,'Akifah','Nailah','2004-08-05','Perempuan',6286958694455,'Jl.S.Wiryopranoto No.37','5002Akifah.jpg','2021-05-23 17:00:00'),(5003,'Asher','Agus','1999-09-01','Laki laki',6286356231658,'Menara Sudirman, 3rd Floor','5003Asher.png','2021-06-02 17:00:00'),(5004,'Alayya','Akifah','1967-06-02','Perempuan',6286513256978,'Jl.Letjen.TB.Simatupang No.1','5004Alayya.jpg','2021-06-07 17:00:00'),(5005,'Rajendra','Helmi','1987-09-15','Laki laki',6286590874512,'Jl.Pemuda No.296, Jakarta Timur','5005Rajendra.png','2021-06-11 17:00:00'),(5006,'Alesa','Zahra','1967-09-03','Perempuan',6289652316582,'Glodok Jaya Complex No.90-91','5006Alesa.jpg','2021-06-12 17:00:00'),(5007,'Binara','Arju','1988-09-04','Laki laki',6285665263580,'Wisma Bakrie,4th Floor, Jl.HR','5007Binara.jpg','2021-06-13 17:00:00'),(5008,'Balqis','Fairuz','1997-08-04','Perempuan',6285474123580,'Wisma Staco, 10th Floor, Jl.Casablanca','5008Balqis.jpg','2021-06-13 17:00:00'),(5009,'Handoko','Purnomo','1987-01-09','Laki-Laki',21335672,'Jl. Beringin Kemerdekaan 32 Padang Barat','5009Handoko.png','2021-06-16 15:38:59'),(5010,'Sutomo','Ardiano','1986-01-09','Laki-Laki',6281345673321,'Jl. suka maharja no 35 padang  barat','5010Sutomo.png','2021-06-22 12:49:19');
/*!40000 ALTER TABLE `petowner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prescription` (
  `medicRecordId` int(11) DEFAULT NULL,
  `medicineId` int(11) DEFAULT NULL,
  `description` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  KEY `prescription_FK2` (`medicRecordId`),
  KEY `prescription_FK` (`medicineId`),
  CONSTRAINT `prescription_FK` FOREIGN KEY (`medicineId`) REFERENCES `medicine` (`medicineId`) ON DELETE CASCADE,
  CONSTRAINT `prescription_FK2` FOREIGN KEY (`medicRecordId`) REFERENCES `medicrecord` (`medicRecordId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
INSERT INTO `prescription` VALUES (10000,506,'1X sehari hancur dan taburkan ke makanan',1),(10000,504,'2x Sehari',1),(10001,502,'Minum 2X Sehari',1);
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER medicineOutStock
AFTER INSERT
ON prescription FOR EACH ROW
update medicine SET outStock = outStock+new.amount , stock = stock - new.amount where medicineId = new.medicineId */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER medicineInStock
AFTER DELETE
ON prescription FOR EACH ROW
UPDATE medicine SET outStock = outStock-old.amount , stock = stock + old.amount where medicineId = old.medicineId */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `firstname` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastname` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hash` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `type` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `salt` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phoneNumber` double DEFAULT NULL,
  `privilege` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `photo` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('-','-','Admin','44b92eeedce72008e306dcae98121a56','Super Admin',1000,'[B@adf2cfc',0,'11111111','1000Admin.png');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `veterinarian`
--

DROP TABLE IF EXISTS `veterinarian`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `veterinarian` (
  `veterinarianId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastName` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(25) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `specialist` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phoneNumber` double DEFAULT NULL,
  `address` text COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `photo` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`veterinarianId`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `veterinarian`
--

LOCK TABLES `veterinarian` WRITE;
/*!40000 ALTER TABLE `veterinarian` DISABLE KEYS */;
INSERT INTO `veterinarian` VALUES (10,'Gustaf','Lin','Drh','Ilmu Bedah Hewan','1972-02-02','Laki laki',62833265476,'Jl.Raya Utan Kayu No.105 B','10Gustaf.jpeg'),(11,'Amy','Christina','Drh','Parasitologi Klinik Veteriner','1980-04-05','Perempuan',62838269483,'Jl.Batu Jajar No.11A','11Amy.jpeg'),(12,'Ayu','Carissa','Drh','Obstetri dan Ginekologi Hewan','1978-10-01','Perempuan',6285623385,'Jl.Raya Bogor No.17','12Ayu.png'),(13,'Daniel','Elias','Drh','Ilmu Kesehatan Kepala dan Leher Hewan','1968-05-05','Laki laki',62823585485,'Jl.Cempaka Baru No.26','13Daniel.jpeg'),(14,'Iwan','Graham','Drh','Ilmu Perilaku Hewan','1969-03-15','Laki laki',62846352487,'Jl.Pelepah Kuning 2','14Iwan.png');
/*!40000 ALTER TABLE `veterinarian` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-30 10:40:10

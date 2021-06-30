-- MariaDB dump 10.19  Distrib 10.5.10-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: pet_clinic
-- ------------------------------------------------------
-- Server version	10.5.10-MariaDB

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

CREATE DATABASE IF NOT EXISTS pet_clinic;
USE pet_clinic;
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
/*!40000 ALTER TABLE `action` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `actionsData`
--

DROP TABLE IF EXISTS `actionsData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `actionsData` (
  `medicRecordId` int(11) DEFAULT NULL,
  `actionId` int(11) DEFAULT NULL,
  `description` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  KEY `actionsData1_FK` (`actionId`),
  KEY `actionsData2_FK` (`medicRecordId`),
  CONSTRAINT `actionsData1_FK` FOREIGN KEY (`actionId`) REFERENCES `action` (`actionId`) ON DELETE CASCADE,
  CONSTRAINT `actionsData2_FK` FOREIGN KEY (`medicRecordId`) REFERENCES `medicRecord` (`medicRecordId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `actionsData`
--

LOCK TABLES `actionsData` WRITE;
/*!40000 ALTER TABLE `actionsData` DISABLE KEYS */;
/*!40000 ALTER TABLE `actionsData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guestBook`
--

DROP TABLE IF EXISTS `guestBook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guestBook` (
  `petId` int(11) DEFAULT NULL,
  `visitTime` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guestBook`
--

LOCK TABLES `guestBook` WRITE;
/*!40000 ALTER TABLE `guestBook` DISABLE KEYS */;
/*!40000 ALTER TABLE `guestBook` ENABLE KEYS */;
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
  CONSTRAINT `invoice_FK` FOREIGN KEY (`medicRecordId`) REFERENCES `medicRecord` (`medicRecordId`) ON DELETE SET NULL,
  CONSTRAINT `invoice_FK_T1` FOREIGN KEY (`petId`) REFERENCES `pet` (`petId`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=21215 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoicesData`
--

DROP TABLE IF EXISTS `invoicesData`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoicesData` (
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
-- Dumping data for table `invoicesData`
--

LOCK TABLES `invoicesData` WRITE;
/*!40000 ALTER TABLE `invoicesData` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoicesData` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicRecord`
--

DROP TABLE IF EXISTS `medicRecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `medicRecord` (
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
-- Dumping data for table `medicRecord`
--

LOCK TABLES `medicRecord` WRITE;
/*!40000 ALTER TABLE `medicRecord` DISABLE KEYS */;
/*!40000 ALTER TABLE `medicRecord` ENABLE KEYS */;
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
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
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
  CONSTRAINT `pet_FK` FOREIGN KEY (`petOwnerId`) REFERENCES `petOwner` (`petOwnerId`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2532 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pet`
--

LOCK TABLES `pet` WRITE;
/*!40000 ALTER TABLE `pet` DISABLE KEYS */;
/*!40000 ALTER TABLE `pet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `petKind`
--

DROP TABLE IF EXISTS `petKind`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `petKind` (
  `petKindId` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`petKindId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `petKind`
--

LOCK TABLES `petKind` WRITE;
/*!40000 ALTER TABLE `petKind` DISABLE KEYS */;
/*!40000 ALTER TABLE `petKind` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `petOwner`
--

DROP TABLE IF EXISTS `petOwner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `petOwner` (
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
-- Dumping data for table `petOwner`
--

LOCK TABLES `petOwner` WRITE;
/*!40000 ALTER TABLE `petOwner` DISABLE KEYS */;
/*!40000 ALTER TABLE `petOwner` ENABLE KEYS */;
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
  CONSTRAINT `prescription_FK2` FOREIGN KEY (`medicRecordId`) REFERENCES `medicRecord` (`medicRecordId`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`k3`@`localhost`*/ /*!50003 TRIGGER medicineOutStock
AFTER INSERT
ON prescription FOR EACH ROW
UPDATE petclinic.medicine SET outStock = outStock+new.amount , stock = stock - new.amount where medicineId = new.medicineId */;;
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
/*!50003 SET collation_connection  = utf8mb4_unicode_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'IGNORE_SPACE,STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`k3`@`localhost`*/ /*!50003 TRIGGER medicineInStock
AFTER DELETE 
ON prescription FOR EACH ROW
UPDATE petclinic.medicine SET outStock = outStock-old.amount , stock = stock + old.amount where medicineId = old.medicineId */;;
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
) ENGINE=InnoDB AUTO_INCREMENT=1011 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
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

-- Dump completed on 2021-06-29 22:50:29

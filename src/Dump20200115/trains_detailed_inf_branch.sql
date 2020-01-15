-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: trains
-- ------------------------------------------------------
-- Server version	5.5.55

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
-- Table structure for table `detailed_inf_branch`
--

DROP TABLE IF EXISTS `detailed_inf_branch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `detailed_inf_branch` (
  `id_detailed_inf_branch` int(11) NOT NULL AUTO_INCREMENT,
  `station_serial_number` int(11) DEFAULT NULL,
  `time_from_previous` time DEFAULT NULL,
  `id_station` int(11) DEFAULT NULL,
  `id_branch` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_detailed_inf_branch`),
  KEY `id_branch2_idx` (`id_branch`),
  KEY `id_station2_idx` (`id_station`),
  CONSTRAINT `id_branch2` FOREIGN KEY (`id_branch`) REFERENCES `branch_line` (`id_branch_line`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_station2` FOREIGN KEY (`id_station`) REFERENCES `station` (`id_station`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detailed_inf_branch`
--

LOCK TABLES `detailed_inf_branch` WRITE;
/*!40000 ALTER TABLE `detailed_inf_branch` DISABLE KEYS */;
INSERT INTO `detailed_inf_branch` VALUES (57,1,'00:00:00',31,21),(58,2,'00:05:00',32,21),(59,3,'00:06:00',33,21),(60,4,'00:07:00',34,21),(61,1,'00:00:00',35,22),(62,2,'00:04:00',36,22),(63,3,'00:05:00',37,22),(64,4,'00:06:00',38,22),(67,1,'00:00:00',24,24),(68,2,'00:06:00',25,24),(69,3,'00:07:00',28,24),(70,4,'00:06:00',29,24),(71,5,'00:05:00',30,24);
/*!40000 ALTER TABLE `detailed_inf_branch` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-15 22:14:14

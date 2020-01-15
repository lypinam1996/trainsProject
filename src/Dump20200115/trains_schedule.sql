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
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `schedule` (
  `id_schedule` int(11) NOT NULL AUTO_INCREMENT,
  `id_train` int(11) DEFAULT NULL,
  `id_branch` int(11) DEFAULT NULL,
  `departure_time` time DEFAULT NULL,
  `id_first_station` int(11) DEFAULT NULL,
  `id_last_station` int(11) DEFAULT NULL,
  `prohibitPurchase` date DEFAULT NULL,
  PRIMARY KEY (`id_schedule`),
  KEY `fk_train_idx` (`id_train`),
  KEY `fk_branch_idx` (`id_branch`),
  KEY `fk_station_f_idx` (`id_first_station`),
  KEY `fk_station_l_idx` (`id_last_station`),
  CONSTRAINT `fk_branch` FOREIGN KEY (`id_branch`) REFERENCES `branch_line` (`id_branch_line`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_schedule_1` FOREIGN KEY (`id_train`) REFERENCES `train` (`id_train`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_station_f` FOREIGN KEY (`id_first_station`) REFERENCES `station` (`id_station`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_station_l` FOREIGN KEY (`id_last_station`) REFERENCES `station` (`id_station`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES (4,6,21,'08:00:00',31,34,NULL),(5,7,21,'09:00:00',31,33,NULL),(6,8,22,'11:00:00',35,38,NULL),(7,6,22,'12:00:00',35,37,NULL),(9,8,22,'18:00:00',36,38,NULL),(10,7,22,'19:00:00',37,38,NULL),(12,4,24,'20:00:00',24,30,NULL);
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-15 22:14:11

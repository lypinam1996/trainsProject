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
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `id_ticket` int(11) NOT NULL AUTO_INCREMENT,
  `id_passanger` int(11) DEFAULT NULL,
  `id_schedule` int(11) DEFAULT NULL,
  `id_first_station` int(11) DEFAULT NULL,
  `id_last_station` int(11) DEFAULT NULL,
  `departure_time` time DEFAULT NULL,
  `arrival_time` time DEFAULT NULL,
  `seat` int(11) DEFAULT NULL,
  `journey_time` time DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  PRIMARY KEY (`id_ticket`),
  KEY `fk_passanger2_idx` (`id_passanger`),
  KEY `fk_schedule_idx` (`id_schedule`),
  KEY `fk_first_station_idx` (`id_first_station`),
  KEY `fk_last_station_idx` (`id_last_station`),
  CONSTRAINT `fk_first_station` FOREIGN KEY (`id_first_station`) REFERENCES `station` (`id_station`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_last_station` FOREIGN KEY (`id_last_station`) REFERENCES `station` (`id_station`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_passanger2` FOREIGN KEY (`id_passanger`) REFERENCES `passanger` (`id_passanger`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_schedule` FOREIGN KEY (`id_schedule`) REFERENCES `schedule` (`id_schedule`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-15 22:14:24

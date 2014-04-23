CREATE DATABASE  IF NOT EXISTS `kingsnthings` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `kingsnthings`;
-- MySQL dump 10.13  Distrib 5.6.13, for osx10.6 (i386)
--
-- Host: 127.0.0.1    Database: kingsnthings
-- ------------------------------------------------------
-- Server version	5.6.13

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
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game` (
  `game_id` int(11) NOT NULL AUTO_INCREMENT,
  `startedDate` datetime DEFAULT NULL,
  `state` blob,
  `created_from_game_lobby_id` varchar(100) DEFAULT NULL,
  `active` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=427 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_chat`
--

DROP TABLE IF EXISTS `game_chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game_chat` (
  `game_chat_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `message` text NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`game_chat_id`),
  KEY `game_chat_ibfk_1_idx` (`game_id`),
  KEY `game_chat_ibfk2_idx` (`user_id`),
  CONSTRAINT `game_chat_ibfk2` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `game_chat_ibfk_1` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_chat`
--

LOCK TABLES `game_chat` WRITE;
/*!40000 ALTER TABLE `game_chat` DISABLE KEYS */;
/*!40000 ALTER TABLE `game_chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game_user`
--

DROP TABLE IF EXISTS `game_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game_user` (
  `game_user_id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`game_user_id`),
  UNIQUE KEY `game_user_index_un_1` (`game_id`,`user_id`),
  KEY `game_user_user_ibfk_idx` (`user_id`),
  CONSTRAINT `game_user_game_ibfk` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `game_user_user_ibfk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=405 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game_user`
--

LOCK TABLES `game_user` WRITE;
/*!40000 ALTER TABLE `game_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `game_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sent_message`
--

DROP TABLE IF EXISTS `sent_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sent_message` (
  `sent_message_id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` varchar(150) NOT NULL,
  `sent_date` datetime(6) NOT NULL,
  `json` text,
  `type` varchar(45) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `game_id` int(11) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  `queued` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`sent_message_id`),
  UNIQUE KEY `ibindex_sent_message_un` (`message_id`,`user_id`),
  KEY `ibfk_sent_message_game_idx` (`game_id`),
  KEY `ibfk_sent_message_user_idx` (`user_id`),
  CONSTRAINT `ibfk_sent_message_game` FOREIGN KEY (`game_id`) REFERENCES `game` (`game_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ibfk_sent_message_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7697 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sent_message`
--

LOCK TABLES `sent_message` WRITE;
/*!40000 ALTER TABLE `sent_message` DISABLE KEYS */;
/*!40000 ALTER TABLE `sent_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `host_name` varchar(45) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `created_datetime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_update` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1019 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'devin',NULL,'192.168.0.19',3004,'2014-01-16 15:26:13','2014-04-06 06:58:35'),(3,NULL,NULL,NULL,NULL,'2014-01-16 15:36:33','2014-01-16 15:36:33'),(4,NULL,NULL,NULL,NULL,'2014-01-16 15:36:37','2014-01-16 15:36:37'),(5,NULL,NULL,NULL,NULL,'2014-01-16 15:49:09','2014-01-16 15:49:09'),(6,'game',NULL,NULL,NULL,'2014-01-16 21:27:36','2014-01-16 21:27:36'),(7,'d2','porn','0.0.0.0',3004,'2014-01-16 21:30:20','2014-01-26 20:55:07'),(8,'d3','porn','192.168.0.14',3004,'2014-01-16 21:35:39','2014-01-26 20:45:55'),(9,'gasme','porn','localhost',5,'2014-01-19 19:01:12','2014-01-19 19:01:12'),(10,'test','test','192.168.43.38',3004,'2014-01-21 18:15:57','2014-02-11 01:51:59'),(16,'t1','t','192.168.0.15',3004,'2014-01-27 22:04:36','2014-04-03 23:33:07'),(17,'i','r','0.0.0.0',3004,'2014-01-27 22:22:16','2014-03-27 14:43:37'),(1000,'user1','password','172.17.55.217',3004,'2014-02-11 00:06:13','2014-04-01 14:29:44'),(1001,'user2','password','172.17.55.217',3004,'2014-02-11 00:06:13','2014-04-01 14:11:08'),(1002,'user3','password','192.168.43.201',3004,'2014-02-11 00:06:13','2014-02-11 01:51:22'),(1003,'user4','password','172.17.54.252',3004,'2014-02-11 00:06:13','2014-03-27 16:46:18'),(1004,NULL,NULL,NULL,NULL,'2014-02-25 16:02:39','2014-02-25 16:02:39'),(1005,NULL,NULL,NULL,NULL,'2014-02-25 16:03:52','2014-02-25 16:03:52'),(1006,NULL,NULL,NULL,NULL,'2014-02-25 16:04:22','2014-02-25 16:04:22'),(1008,NULL,NULL,NULL,NULL,'2014-02-25 16:19:25','2014-02-25 16:19:25'),(1009,NULL,NULL,NULL,NULL,'2014-02-25 16:19:58','2014-02-25 16:19:58'),(1010,NULL,NULL,NULL,NULL,'2014-02-25 16:20:22','2014-02-25 16:20:22'),(1011,NULL,NULL,NULL,NULL,'2014-02-25 21:22:08','2014-02-25 21:22:08'),(1012,NULL,NULL,NULL,NULL,'2014-02-25 21:24:34','2014-02-25 21:24:34'),(1013,NULL,NULL,NULL,NULL,'2014-02-25 21:28:01','2014-02-25 21:28:01'),(1014,NULL,NULL,NULL,NULL,'2014-02-25 21:33:38','2014-02-25 21:33:38'),(1015,NULL,NULL,NULL,NULL,'2014-02-25 21:34:09','2014-02-25 21:34:09'),(1016,NULL,NULL,NULL,NULL,'2014-02-25 21:35:09','2014-02-25 21:35:09'),(1017,NULL,NULL,NULL,NULL,'2014-02-27 21:42:55','2014-02-27 21:42:55'),(1018,'john','dingbat','0.0.0.0',3004,'2014-03-27 14:42:54','2014-03-27 14:42:54');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-06 10:04:40

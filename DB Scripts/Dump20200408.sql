-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: pay_my_buddy
-- ------------------------------------------------------
-- Server version	5.7.29-0ubuntu0.18.04.1

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
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_user_id` int(11) DEFAULT NULL,
  `fk_account_type_id` int(11) DEFAULT NULL,
  `fk_account_status_id` int(11) DEFAULT NULL,
  `fk_connection_id` int(11) DEFAULT NULL,
  `current_balance` float NOT NULL,
  PRIMARY KEY (`account_id`),
  KEY `user_fk_user_id_idx` (`fk_user_id`),
  KEY `account_type_fk_account_type_id_idx` (`fk_account_type_id`),
  KEY `account_status_fk_account_status_id_idx` (`fk_account_status_id`),
  KEY `connection_fk_connection_id_idx` (`fk_connection_id`),
  CONSTRAINT `account_fk_account_status_id` FOREIGN KEY (`fk_account_status_id`) REFERENCES `account_status` (`account_status_id`) ON DELETE CASCADE,
  CONSTRAINT `account_fk_account_type_id` FOREIGN KEY (`fk_account_type_id`) REFERENCES `account_type` (`account_type_id`) ON DELETE CASCADE,
  CONSTRAINT `account_fk_connection_id` FOREIGN KEY (`fk_connection_id`) REFERENCES `connection` (`connection_id`) ON DELETE CASCADE,
  CONSTRAINT `account_fk_user_id` FOREIGN KEY (`fk_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (22,22,1,1,22,99.25),(23,23,1,1,23,199.75),(24,24,1,1,24,100),(25,25,2,1,25,1);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_status`
--

DROP TABLE IF EXISTS `account_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_status` (
  `account_status_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_status` varchar(45) NOT NULL,
  PRIMARY KEY (`account_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_status`
--

LOCK TABLES `account_status` WRITE;
/*!40000 ALTER TABLE `account_status` DISABLE KEYS */;
INSERT INTO `account_status` VALUES (1,'Active'),(2,'Inactive'),(3,'NotYetActivated'),(4,'Deactivated');
/*!40000 ALTER TABLE `account_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account_type`
--

DROP TABLE IF EXISTS `account_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_type` (
  `account_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_type` varchar(45) NOT NULL,
  PRIMARY KEY (`account_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_type`
--

LOCK TABLES `account_type` WRITE;
/*!40000 ALTER TABLE `account_type` DISABLE KEYS */;
INSERT INTO `account_type` VALUES (1,'Regular'),(2,'Company');
/*!40000 ALTER TABLE `account_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `connection`
--

DROP TABLE IF EXISTS `connection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `connection` (
  `connection_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_connection_type_id` int(11) DEFAULT NULL,
  `fk_user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`connection_id`),
  KEY `connection_type_fk_connection_type_id_idx` (`fk_connection_type_id`),
  KEY `connection_fk_user_id_idx` (`fk_user_id`),
  CONSTRAINT `connection_fk_connection_type_id` FOREIGN KEY (`fk_connection_type_id`) REFERENCES `connection_type` (`connection_type_id`) ON DELETE CASCADE,
  CONSTRAINT `connection_fk_user_id` FOREIGN KEY (`fk_user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `connection`
--

LOCK TABLES `connection` WRITE;
/*!40000 ALTER TABLE `connection` DISABLE KEYS */;
INSERT INTO `connection` VALUES (22,1,22),(23,1,23),(24,1,24),(25,2,25);
/*!40000 ALTER TABLE `connection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `connection_list_element`
--

DROP TABLE IF EXISTS `connection_list_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `connection_list_element` (
  `connection_list_element_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_account_id` int(11) DEFAULT NULL,
  `fk_connection_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`connection_list_element_id`),
  KEY `connection_list_element_fk_account_id_idx` (`fk_account_id`),
  KEY `connection_list_element_fk_connection_id_idx` (`fk_connection_id`),
  CONSTRAINT `connection_list_element_fk_account_id` FOREIGN KEY (`fk_account_id`) REFERENCES `account` (`account_id`) ON DELETE CASCADE,
  CONSTRAINT `connection_list_element_fk_connection_id` FOREIGN KEY (`fk_connection_id`) REFERENCES `connection` (`connection_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `connection_list_element`
--

LOCK TABLES `connection_list_element` WRITE;
/*!40000 ALTER TABLE `connection_list_element` DISABLE KEYS */;
INSERT INTO `connection_list_element` VALUES (2,22,23),(3,22,24),(5,23,22);
/*!40000 ALTER TABLE `connection_list_element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `connection_type`
--

DROP TABLE IF EXISTS `connection_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `connection_type` (
  `connection_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `connection_type` varchar(45) NOT NULL,
  PRIMARY KEY (`connection_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `connection_type`
--

LOCK TABLES `connection_type` WRITE;
/*!40000 ALTER TABLE `connection_type` DISABLE KEYS */;
INSERT INTO `connection_type` VALUES (1,'Regular'),(2,'Company');
/*!40000 ALTER TABLE `connection_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iban`
--

DROP TABLE IF EXISTS `iban`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iban` (
  `iban_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_account_id` int(11) DEFAULT NULL,
  `iban` varchar(45) NOT NULL,
  PRIMARY KEY (`iban_id`),
  KEY `iban_fk_account_id_idx` (`fk_account_id`),
  CONSTRAINT `iban_fk_account_id` FOREIGN KEY (`fk_account_id`) REFERENCES `account` (`account_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iban`
--

LOCK TABLES `iban` WRITE;
/*!40000 ALTER TABLE `iban` DISABLE KEYS */;
INSERT INTO `iban` VALUES (3,22,'GB123456');
/*!40000 ALTER TABLE `iban` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'Company'),(2,'Admin'),(3,'Regular');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction` (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_transaction_type_id` int(11) DEFAULT NULL,
  `fk_account_id` int(11) DEFAULT NULL,
  `fk_connection_id` int(11) DEFAULT NULL,
  `fk_iban_id` int(11) DEFAULT NULL,
  `senders_balance_before_transaction` float DEFAULT NULL,
  `receivers_balance_before_transaction` float DEFAULT NULL,
  `money_variation` float NOT NULL,
  `made_at` datetime NOT NULL,
  `origin` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `transaction_type_id_idx` (`fk_transaction_type_id`),
  KEY `connection_fk_connection_id_idx` (`fk_connection_id`),
  KEY `transaction_fk_account_id_idx` (`fk_account_id`),
  KEY `transaction_fk_iban_id_idx` (`fk_iban_id`),
  CONSTRAINT `transaction_fk_account_id` FOREIGN KEY (`fk_account_id`) REFERENCES `account` (`account_id`) ON DELETE CASCADE,
  CONSTRAINT `transaction_fk_connection_id` FOREIGN KEY (`fk_connection_id`) REFERENCES `connection` (`connection_id`) ON DELETE CASCADE,
  CONSTRAINT `transaction_fk_iban_id` FOREIGN KEY (`fk_iban_id`) REFERENCES `iban` (`iban_id`) ON DELETE CASCADE,
  CONSTRAINT `transaction_fk_transaction_type_id` FOREIGN KEY (`fk_transaction_type_id`) REFERENCES `transaction_type` (`transaction_type_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (11,2,22,NULL,NULL,100,0,100,'2020-04-02 21:24:39','Bank Account','TopUp'),(12,2,22,NULL,NULL,200,0,100,'2020-04-02 21:24:47','Credit Card','TopUp'),(13,3,22,NULL,3,300,0,50,'2020-04-02 21:54:11',NULL,'Withdrawal'),(14,1,22,23,NULL,250,100,100,'2020-04-02 22:18:32',NULL,'Gift'),(15,3,22,NULL,3,149.5,0,50,'2020-04-03 14:39:23',NULL,'Withdrawal'),(16,1,23,22,NULL,200,99.5,50,'2020-04-03 14:41:57',NULL,'Birthday Gift'),(17,1,22,23,NULL,149.5,149.75,50,'2020-04-08 01:15:14',NULL,'Shoes');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_type`
--

DROP TABLE IF EXISTS `transaction_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction_type` (
  `transaction_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_type` varchar(45) NOT NULL,
  PRIMARY KEY (`transaction_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_type`
--

LOCK TABLES `transaction_type` WRITE;
/*!40000 ALTER TABLE `transaction_type` DISABLE KEYS */;
INSERT INTO `transaction_type` VALUES (1,'Regular'),(2,'TopUp'),(3,'Withdrawal');
/*!40000 ALTER TABLE `transaction_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `fk_role_id` int(11) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `password` tinytext NOT NULL,
  `created_at` datetime NOT NULL,
  `last_updated_at` datetime DEFAULT NULL,
  `active` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `role_fk_role_id_idx` (`fk_role_id`),
  CONSTRAINT `user_fk_role_id` FOREIGN KEY (`fk_role_id`) REFERENCES `role` (`role_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (22,3,'111@111.com','$2a$10$O4dIbG/qY8v.Sh1AsmANfuZnl7uP3RUELyLLIHfbXmZlAA6fXPGLS','2020-04-01 22:26:26',NULL,1),(23,3,'123@123.com','$2a$10$lp9SwxFamwOWsj5lezkGzOorvzfaYytdgqrck3OO4J7DiYLHj6OMu','2020-04-01 22:26:35',NULL,1),(24,3,'222@222.com','$2a$10$mSzI4jtm6d.fYRQiUySeLOEFCoTynszL7JDdqKcfQS3Bxq4qS1RQu','2020-04-01 22:26:42',NULL,1),(25,1,'000@000.com','$2y$12$/tOHObCFEU3XHPNdmRvTOOsZGUtogAfcQRROQy9B30znvufiKmCOm','2020-04-01 22:26:42',NULL,1);
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

-- Dump completed on 2020-04-08  1:17:47

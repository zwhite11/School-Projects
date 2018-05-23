-- MySQL dump 10.13  Distrib 5.7.18, for osx10.11 (x86_64)
--
-- Host: localhost    Database: pizzapolis
-- ------------------------------------------------------
-- Server version	5.7.18

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
-- Table structure for table `toppings`
--

DROP TABLE IF EXISTS `toppings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `toppings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `pizza_coverage` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `second` (`name`,`pizza_coverage`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toppings`
--

LOCK TABLES `toppings` WRITE;
/*!40000 ALTER TABLE `toppings` DISABLE KEYS */;
INSERT INTO `toppings` VALUES (1,'Pepperoni',2,'Left'),(2,'Pepperoni',2,'Whole'),(3,'Pepperoni',2,'Right'),(4,'Sausage',2,'Left'),(5,'Sausage',2,'Whole'),(6,'Sausage',2,'Right'),(7,'Chicken',2,'Left'),(8,'Chicken',2,'Whole'),(9,'Chicken',2,'Right'),(10,'Shrimp',2,'Left'),(11,'Shrimp',2,'Whole'),(12,'Shrimp',2,'Right'),(13,'Ground Beef',2,'Left'),(14,'Ground Beef',2,'Whole'),(15,'Ground Beef',2,'Right'),(16,'Bacon',2,'Left'),(17,'Bacon',2,'Whole'),(18,'Bacon',2,'Right'),(19,'Ham',2,'Left'),(20,'Ham',2,'Whole'),(21,'Ham',2,'Right'),(22,'Onions',1,'Left'),(23,'Onions',1,'Whole'),(24,'Onions',1,'Right'),(25,'Tomatoes',1,'Left'),(26,'Tomatoes',1,'Whole'),(27,'Tomatoes',1,'Right'),(28,'Mushrooms',1,'Left'),(29,'Mushrooms',1,'Whole'),(30,'Mushrooms',1,'Right'),(31,'Pineapple',1,'Left'),(32,'Pineapple',1,'Whole'),(33,'Pineapple',1,'Right'),(34,'Jalapenos',1,'Left'),(35,'Jalapenos',1,'Whole'),(36,'Jalapenos',1,'Right'),(37,'Green Peppers',1,'Left'),(38,'Green Peppers',1,'Whole'),(39,'Green Peppers',1,'Right'),(40,'Banana Peppers',1,'Left'),(41,'Banana Peppers',1,'Whole'),(42,'Banana Peppers',1,'Right'),(43,'Spinach',1,'Left'),(44,'Spinach',1,'Whole'),(45,'Spinach',1,'Right'),(46,'Olives',1,'Left'),(47,'Olives',1,'Whole'),(48,'Olives',1,'Right'),(49,'Extra Cheese',1,'Left'),(50,'Extra Cheese',1,'Whole'),(51,'Extra Cheese',1,'Right');
/*!40000 ALTER TABLE `toppings` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-09 19:36:19

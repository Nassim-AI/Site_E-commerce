-- MySQL dump 10.13  Distrib 8.0.34, for macos13 (arm64)
--
-- Host: localhost    Database: website_db
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `c_order`
--

DROP TABLE IF EXISTS `c_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `c_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `customer_name` varchar(255) DEFAULT NULL,
  `order_date` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_price` double NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `c_order`
--

LOCK TABLES `c_order` WRITE;
/*!40000 ALTER TABLE `c_order` DISABLE KEYS */;
INSERT INTO `c_order` VALUES (3,'Nassim','2024-09-30 14:30:00.000000','Livrée',49.99,NULL),(6,'Nom du client','2024-10-06 23:58:00.562000','En traitement',28.6,NULL),(7,'Nom du client','2024-10-07 00:09:24.441000','Annulée',4.5,NULL),(8,'ISSAD','2024-10-07 00:15:11.047000','En traitement',10,NULL),(9,'ISSAD','2024-10-07 00:17:19.328000','En traitement',14.7,NULL),(10,'Dupont','2024-10-07 09:52:57.837000','En traitement',7.7,NULL),(11,'Dupont','2024-10-07 09:53:22.981000','En traitement',3.2,NULL),(12,'Dupont','2024-10-07 09:56:49.719000','En traitement',7.7,NULL),(13,'Dupont','2024-10-07 09:58:57.363000','En traitement',4.5,NULL),(14,'Dupont','2024-10-07 09:59:33.746000','En traitement',4.5,NULL),(15,'ISSAD','2024-10-07 10:04:18.247000','En traitement',7.8,NULL),(16,'Dupont','2024-10-07 10:11:28.827000','En traitement',17.7,NULL),(17,'ISSAD','2024-10-07 10:28:13.134000','En traitement',5.5,NULL),(18,'ISSAD','2024-10-07 10:28:55.050000','En traitement',5.5,NULL),(19,'ISSAD','2024-10-07 12:21:20.343000','En traitement',129.9,NULL),(20,'ISSAD','2024-10-07 12:30:00.177000','En traitement',126.5,NULL),(21,'ISSAD','2024-10-07 12:30:51.261000','En traitement',7.6,NULL),(22,'ISSAD','2024-10-07 13:06:13.527000','En traitement',7.6,NULL),(23,'ISSAD','2024-10-07 13:06:37.910000','En traitement',7.6,NULL),(24,'ISSAD','2024-10-07 13:07:43.280000','En traitement',7.6,NULL),(25,'ISSAD','2024-10-07 14:35:50.268000','Livrée',9,NULL),(26,'ISSAD','2024-10-07 15:00:54.946000','En traitement',9,NULL),(27,'ISSAD','2024-10-07 15:02:34.077000','En traitement',9,NULL),(28,'ISSAD','2024-10-07 15:10:02.358000','En traitement',9,NULL),(29,'ISSAD','2024-10-07 15:23:02.696000','En traitement',9,NULL),(30,'ISSAD','2024-10-07 15:25:23.315000','En traitement',9,NULL),(31,'ISSAD','2024-10-07 15:26:08.292000','En traitement',11,NULL),(32,'ISSAD','2024-10-07 16:01:43.243000','En traitement',7.7,NULL),(33,'ISSAD','2024-10-07 16:13:35.469000','En traitement',4.5,NULL),(34,'ISSAD','2024-10-07 16:14:08.300000','Annulée',16.4,NULL),(35,'ISSAD','2024-10-07 16:22:19.724000','En traitement',9,NULL),(36,'ISSAD','2024-10-07 18:05:07.868000','En traitement',9.8,NULL),(37,'ISSAD','2024-10-07 18:06:23.169000','En traitement',11,NULL),(38,'ISSAD','2024-10-07 18:06:58.132000','En traitement',12,NULL),(39,'ISSAD','2024-10-07 18:09:29.366000','En traitement',9.8,NULL),(40,'ISSAD','2024-10-07 18:32:16.819000','En traitement',9.8,NULL),(41,'ISSAD','2024-10-07 18:34:06.939000','Annulée',8,NULL),(42,'ISSAD','2024-10-07 20:07:06.106000','En traitement',11.2,NULL),(43,'ISSAD','2024-10-07 20:19:41.884000','En traitement',13.8,NULL),(44,'ISSAD','2024-10-07 20:22:39.904000','Annulée',10,NULL),(45,'ISSAD','2024-10-08 00:43:32.359000','Annulée',8,NULL),(46,'ISSAD','2024-10-09 10:12:12.504000','En traitement',4.9,NULL),(47,'Dupont','2024-10-09 10:16:29.472000','En traitement',6,NULL),(48,'Dupont','2024-10-09 10:33:55.602000','En traitement',5.5,NULL),(49,'Dupont','2024-10-09 10:35:53.485000','En traitement',3.2,NULL),(50,'Dupont','2024-10-09 10:36:08.167000','En traitement',4.9,NULL),(51,'Hugo','2024-10-09 12:24:33.545000','En traitement',4.5,NULL),(52,'Hugo','2024-10-09 12:55:34.030000','Annulée',4.5,5),(53,'Nom du client','2024-10-09 17:33:37.942000','En traitement',0,NULL),(54,'Nom du client','2024-10-09 17:33:38.944000','En traitement',0,NULL),(55,'ISSAD','2024-10-09 17:34:39.211000','En traitement',6.4,1),(56,'ISSAD','2024-10-09 17:34:48.227000','En traitement',0,1),(57,'ISSAD','2024-10-09 17:37:52.800000','En traitement',3.2,1),(58,'ISSAD','2024-10-09 18:11:44.869000','En traitement',3.2,1),(59,'ISSAD','2024-10-09 18:23:30.564000','En traitement',3.2,1),(60,'ISSAD','2024-10-09 18:23:33.181000','En traitement',0,1),(61,'ISSAD','2024-10-09 18:23:34.963000','En traitement',0,1),(62,'ISSAD','2024-10-09 18:23:35.666000','En traitement',0,1),(63,'ISSAD','2024-10-09 18:23:45.098000','En traitement',6.4,1),(64,'ISSAD','2024-10-09 18:46:44.040000','En traitement',7,1),(65,'ISSAD','2024-10-09 18:47:04.826000','En traitement',6.4,1),(66,'ISSAD','2024-10-09 18:47:45.980000','En traitement',3.8,1),(67,'ISSAD','2024-10-09 18:47:53.658000','En traitement',3.8,1),(68,'ISSAD','2024-10-10 12:15:50.796000','En traitement',6.5,1),(69,'ISSAD','2024-10-10 12:53:18.379000','En traitement',5.4,1),(70,'ISSAD','2024-10-10 13:21:45.427000','En traitement',27.6,1),(71,'ISSAD','2024-10-10 14:37:20.488000','En traitement',19.5,1),(72,'ISSAD','2024-10-10 14:38:00.034000','En traitement',4.9,1),(73,'ISSAD','2024-10-10 14:56:27.560000','En traitement',6.5,1),(74,'ISSAD','2024-10-12 20:53:28.495000','Annulée',11.8,1);
/*!40000 ALTER TABLE `c_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_product`
--

DROP TABLE IF EXISTS `order_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_product` (
  `order_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  KEY `FKhnfgqyjx3i80qoymrssls3kno` (`product_id`),
  KEY `FKmc7ij2hxssp56xaa8m8bd59y7` (`order_id`),
  CONSTRAINT `FKhnfgqyjx3i80qoymrssls3kno` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKmc7ij2hxssp56xaa8m8bd59y7` FOREIGN KEY (`order_id`) REFERENCES `c_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_product`
--

LOCK TABLES `order_product` WRITE;
/*!40000 ALTER TABLE `order_product` DISABLE KEYS */;
INSERT INTO `order_product` VALUES (3,1),(6,1),(6,13),(6,14),(6,15),(6,16),(7,1),(8,1),(8,14),(9,13),(9,14),(9,16),(10,13),(10,1),(11,13),(12,1),(12,13),(13,1),(14,1),(15,18),(15,17),(16,1),(16,13),(16,14),(17,14),(18,14),(19,13),(19,18),(19,16),(19,17),(19,1),(19,14),(20,14),(21,18),(22,18),(23,18),(24,18),(25,1),(26,1),(27,1),(28,1),(29,1),(30,1),(31,14),(32,13),(32,1),(33,1),(34,15),(34,14),(34,16),(35,1),(36,15),(37,14),(38,16),(39,15),(40,15),(41,17),(42,17),(42,13),(43,15),(43,17),(44,17),(44,16),(45,17),(46,15),(47,16),(48,14),(49,13),(50,15),(51,1),(52,1),(53,1),(54,1),(55,13),(57,13),(58,13),(59,13),(63,13),(64,18),(64,13),(65,13),(66,18),(67,18),(68,1),(69,13),(70,13),(70,1),(70,15),(71,1),(72,15),(73,1),(74,15),(74,1);
/*!40000 ALTER TABLE `order_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `stock` int NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,NULL,'Croissant Caramel',6.9,15,'image_croissant_caramel.png'),(13,NULL,'Éclair au Chocolat',5.4,16,'image_eclair_chocolat.png'),(14,NULL,'Opéra',8.5,20,'image_opera.png'),(15,NULL,'Tarte aux Fraises',4.9,17,'image_tarte_fraises.png'),(16,NULL,'Tarte Tatin',6,20,'image_tarte_tatin.png'),(17,NULL,'Paris-Brest',4,20,'image_paris_brest.png'),(18,NULL,'Mille-Feuille',7,20,'image_mille_feuille.png');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `rating` int NOT NULL,
  `reviewer_name` varchar(255) DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKiyof1sindb9qiqr9o8npj8klt` (`product_id`),
  CONSTRAINT `FKiyof1sindb9qiqr9o8npj8klt` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'Super produit, très savoureux !',5,'Marie',1),(2,'Super produit, très savoureux !',6,'Marie',1),(3,'Incroyable',4,'Paul',1),(4,'C\'était délicieux',4,'Marie',1),(5,'C\'était mauvais',1,'Nassssssim',18),(6,'C\'est super mauvais',1,'Nassssssim',1),(7,'C\'est très bon',5,'Nassssssim',1);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `transaction_date` datetime(6) DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmpc8o6ot0vc8343p4r7br9c40` (`order_id`),
  CONSTRAINT `FKmpc8o6ot0vc8343p4r7br9c40` FOREIGN KEY (`order_id`) REFERENCES `c_order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,50,'Nassim','validée','2024-09-30 14:30:00.000000',NULL),(2,50,'Nassim','validée','2024-09-30 14:30:00.000000',3),(3,49.99,'Nassim','validée','2024-09-30 14:30:00.000000',3),(4,60,'Nassim','validée','2024-10-01 16:30:00.000000',3);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateur` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `adresse` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `mot_de_passe` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateur`
--

LOCK TABLES `utilisateur` WRITE;
/*!40000 ALTER TABLE `utilisateur` DISABLE KEYS */;
INSERT INTO `utilisateur` VALUES (1,'17 Rue de Rivoli, 75004 Paris, France','nassim.issad@gmail.com','12345','ISSAD','Nassssssim'),(2,'11 rue des Fleurs, Paris','marie.dupont@gmail.com','password123','Dupont','Marie'),(3,'41 tres  Pontault','test@hotmail.fr','1234','Cmoi','ok'),(4,'41 rue faubourg','testons@hotmail.fr','testons','tes','tons'),(5,'15 Rue de Crimée, 75019 Paris, France','Victorhugo@gmail.com','victor','Hugo','Victor'),(6,'12 Rue du Président Édouard Herriot, 69001 Lyon, France','Charles@gmail.com','charles','Baudelaire','Charles');
/*!40000 ALTER TABLE `utilisateur` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-14 19:06:37

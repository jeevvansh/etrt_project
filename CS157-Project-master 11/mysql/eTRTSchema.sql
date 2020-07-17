-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: 127.0.0.1    Database: eTRTSchema
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `Cities`
--

DROP TABLE IF EXISTS `Cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Cities` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `State` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `StateID_idx` (`State`),
  CONSTRAINT `StateID` FOREIGN KEY (`State`) REFERENCES `states` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cities`
--

LOCK TABLES `Cities` WRITE;
/*!40000 ALTER TABLE `Cities` DISABLE KEYS */;
INSERT INTO `Cities` VALUES (1,'Albany',NULL),(2,'Annapolis',20),(3,'Atlanta',10),(4,'Augusta',19),(5,'Austin',43),(6,'Baton Rouge',18),(7,'Bismarck',34),(8,'Boise',12),(9,'Boston',21),(10,'Carson City',28),(11,'Chareston',33),(12,'Cheyenne',50),(13,'Chicago',13),(14,'Columbia',40),(15,'Columbus',35),(16,'Concord',29),(17,'Denver',6),(18,'Des Moines',15),(19,'Dover',8),(20,'Frankfort',17),(21,'Harrisburg',38),(22,'Hartford',7),(23,'Helena',26),(24,'Honolulu',11),(25,'Indianapolis',14),(26,'Jackson',24),(27,'Jefferson City',25),(28,'Juneau',2),(29,'Lansing',22),(30,'Lincoln',27),(31,'Little Rock',4),(32,'Los Angeles',5),(33,'Madison',49),(34,'Montgomery',1),(35,'Montpelier',45),(36,'Nashville',42),(37,'New York',32),(38,'Oklahoma City',36),(39,'Olympia',47),(40,'Phoenix',3),(41,'Pierre',41),(42,'Providence',39),(43,'Raleigh',33),(44,'Richmond',46),(45,'Sacramento',5),(46,'Saint Paul',23),(47,'Salem',37),(48,'Salt Lake City',44),(49,'San Diego',5),(50,'San Jose',5),(51,'Santa Fe',31),(52,'Springfield',13),(53,'Tallahassee',9),(54,'Tampa',9),(55,'Topeka',16),(56,'Trenton',30),(57,'Tulsa',36),(58,'Washington D.C.',20),(59,'Orlando',NULL);
/*!40000 ALTER TABLE `Cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Countries`
--

DROP TABLE IF EXISTS `Countries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Countries` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Abbreviation` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Countries`
--

LOCK TABLES `Countries` WRITE;
/*!40000 ALTER TABLE `Countries` DISABLE KEYS */;
INSERT INTO `Countries` VALUES (1,'United States of America','USA');
/*!40000 ALTER TABLE `Countries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Educational_Degrees`
--

DROP TABLE IF EXISTS `Educational_Degrees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Educational_Degrees` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Educational_Degrees`
--

LOCK TABLES `Educational_Degrees` WRITE;
/*!40000 ALTER TABLE `Educational_Degrees` DISABLE KEYS */;
INSERT INTO `Educational_Degrees` VALUES (0,'N/A'),(1,'Less Than High School or GED'),(2,'High School or GED'),(3,'Some College'),(4,'Associate\'s Degree'),(5,'Bachelor\'s Degree'),(6,'Some Graduate School'),(7,'Graduate or Professional Degree'),(8,'San Jose State University');
/*!40000 ALTER TABLE `Educational_Degrees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Occupations`
--

DROP TABLE IF EXISTS `Occupations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Occupations` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Occupations`
--

LOCK TABLES `Occupations` WRITE;
/*!40000 ALTER TABLE `Occupations` DISABLE KEYS */;
INSERT INTO `Occupations` VALUES (0,'N/A'),(1,'Student'),(2,'Programmer');
/*!40000 ALTER TABLE `Occupations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Patients`
--

DROP TABLE IF EXISTS `Patients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Patients` (
  `THCNumber` int(11) NOT NULL,
  `Date` date NOT NULL,
  `FirstName` varchar(45) NOT NULL,
  `MiddleName` varchar(45) DEFAULT NULL,
  `LastName` varchar(45) NOT NULL,
  `DOB` date NOT NULL,
  `Gender` varchar(6) NOT NULL,
  `Phone` varchar(11) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `StreetAddress` varchar(45) NOT NULL,
  `Zip` int(11) NOT NULL,
  `Photo` varchar(45) NOT NULL,
  `SSID` varchar(45) NOT NULL,
  `Insurance` varchar(45) DEFAULT NULL,
  `Occupation` int(11) DEFAULT NULL,
  `WorkStatus` int(11) DEFAULT NULL,
  `EducationalDegree` int(11) DEFAULT NULL,
  `TOnset` varchar(45) DEFAULT NULL,
  `TEtiology` varchar(45) DEFAULT NULL,
  `HOnset` varchar(45) DEFAULT NULL,
  `HEtiology` varchar(45) DEFAULT NULL,
  `Comments` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`THCNumber`),
  UNIQUE KEY `THCNumber_UNIQUE` (`THCNumber`),
  UNIQUE KEY `SSID_UNIQUE` (`SSID`),
  KEY `EducationalDegreeID_idx` (`EducationalDegree`),
  KEY `OccupationID_idx` (`Occupation`),
  KEY `WorkStatus_idx` (`WorkStatus`),
  KEY `ZipcodeID_idx` (`Zip`),
  CONSTRAINT `EducationalDegreeID` FOREIGN KEY (`EducationalDegree`) REFERENCES `educational_degrees` (`ID`),
  CONSTRAINT `OccupationID` FOREIGN KEY (`Occupation`) REFERENCES `occupations` (`ID`),
  CONSTRAINT `WorkStatusID` FOREIGN KEY (`WorkStatus`) REFERENCES `work_statuses` (`ID`),
  CONSTRAINT `ZipcodeID` FOREIGN KEY (`Zip`) REFERENCES `zipcodes` (`Zipcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Patients`
--

LOCK TABLES `Patients` WRITE;
/*!40000 ALTER TABLE `Patients` DISABLE KEYS */;
INSERT INTO `Patients` VALUES (1,'2019-11-20','Nick','Michael','Fulton','1998-09-14','Male','8587761407','nickfulton98@gmail.com','123 Test Ave',95112,'1.png','123456789','Geico',1,3,5,'N/A','N/A','N/A','N/A','Testing.'),(2,'2019-11-26','Stacy','T','Tester','2003-02-02','Female','123421-2413','stacy@gmail.com','123 Testing Ave',95112,'src/images/2.png','8473829384','Insurance',0,0,0,'','','','','');
/*!40000 ALTER TABLE `Patients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `States`
--

DROP TABLE IF EXISTS `States`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `States` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Abbreviation` varchar(10) NOT NULL,
  `Country` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `CountryID_idx` (`Country`),
  CONSTRAINT `CountryID` FOREIGN KEY (`Country`) REFERENCES `countries` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `States`
--

LOCK TABLES `States` WRITE;
/*!40000 ALTER TABLE `States` DISABLE KEYS */;
INSERT INTO `States` VALUES (1,'Alabama','AL',1),(2,'Alaska','AK',1),(3,'Arizona','AZ',1),(4,'Arkansas','AR',1),(5,'California','CA',1),(6,'Colorado','CO',1),(7,'Connecticut','CT',1),(8,'Delaware','DE',1),(9,'Florida','FL',1),(10,'Georgia','GA',1),(11,'Hawaii','HI',1),(12,'Idaho','ID',1),(13,'Illinois','IL',1),(14,'Indiana','IN',1),(15,'Iowa','IA',1),(16,'Kansas','KS',1),(17,'Kentucky','KY',1),(18,'Louisiana','LA',1),(19,'Maine','ME',1),(20,'Maryland','MD',1),(21,'Massachusetts','MA',1),(22,'Michigan','MI',1),(23,'Minnesota','MN',1),(24,'Mississippi','MS',1),(25,'Missouri','MO',1),(26,'Montana','MT',1),(27,'Nebraska','NE',1),(28,'Nevada','NV',1),(29,'New Hampshire','NH',1),(30,'New Jersey','NJ',1),(31,'New Mexico','NM',1),(32,'New York','NY',1),(33,'North Carolina','NC',1),(34,'North Dakota','ND',1),(35,'Ohio','OH',1),(36,'Oklahoma','OK',1),(37,'Oregon','OR',1),(38,'Pensylvania','PA',1),(39,'Rhode Island','RI',1),(40,'South Carolina','SC',1),(41,'South Dakota','SD',1),(42,'Tennessee','TN',1),(43,'Texas','TX',1),(44,'Utah','UT',1),(45,'Vermont','VT',1),(46,'Virginia','VA',1),(47,'Washington','WA',1),(48,'West Virginia','WV',1),(49,'Wisconsin','WI',1),(50,'Wyoming','WY',1);
/*!40000 ALTER TABLE `States` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Visits`
--

DROP TABLE IF EXISTS `Visits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Visits` (
  `VisitID` int(11) NOT NULL,
  `Date` date NOT NULL,
  `THCNumber` int(11) NOT NULL,
  `VisitSequence` int(11) NOT NULL,
  `ProblemRank` varchar(3) NOT NULL,
  `Category` int(11) NOT NULL,
  `Protocol` int(11) NOT NULL,
  `FU` varchar(45) DEFAULT NULL,
  `Instrument` varchar(3) DEFAULT NULL,
  `REM` tinyint(4) NOT NULL,
  `Comments` varchar(150) DEFAULT NULL,
  `NextVisit` date NOT NULL,
  PRIMARY KEY (`VisitID`),
  UNIQUE KEY `VisitID_UNIQUE` (`VisitID`),
  KEY `THCID_idx` (`THCNumber`),
  CONSTRAINT `THCID` FOREIGN KEY (`THCNumber`) REFERENCES `patients` (`THCNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Visits`
--

LOCK TABLES `Visits` WRITE;
/*!40000 ALTER TABLE `Visits` DISABLE KEYS */;
INSERT INTO `Visits` VALUES (1,'2019-11-24',1,1,'3',3,3,'T','T',1,'Test.','2019-12-29');
/*!40000 ALTER TABLE `Visits` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Work_Statuses`
--

DROP TABLE IF EXISTS `Work_Statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Work_Statuses` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) NOT NULL,
  `Abbreviation` varchar(45) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Work_Statuses`
--

LOCK TABLES `Work_Statuses` WRITE;
/*!40000 ALTER TABLE `Work_Statuses` DISABLE KEYS */;
INSERT INTO `Work_Statuses` VALUES (0,'N/A','NA'),(1,'Unemployed','U'),(2,'Part-Time','PT'),(3,'Full-Time','FT'),(4,'Self-Employed','SE'),(5,'Independent Contractor','IC'),(6,'Other','O'),(7,'Hard Life','HL');
/*!40000 ALTER TABLE `Work_Statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Zipcodes`
--

DROP TABLE IF EXISTS `Zipcodes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Zipcodes` (
  `Zipcode` int(11) NOT NULL,
  `City` int(11) DEFAULT NULL,
  PRIMARY KEY (`Zipcode`),
  KEY `CityID_idx` (`City`),
  CONSTRAINT `CityID` FOREIGN KEY (`City`) REFERENCES `cities` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Zipcodes`
--

LOCK TABLES `Zipcodes` WRITE;
/*!40000 ALTER TABLE `Zipcodes` DISABLE KEYS */;
INSERT INTO `Zipcodes` VALUES (12084,1),(12202,1),(21401,2),(30301,3),(30805,4),(73301,5),(70802,6),(58501,7),(83701,8),(2101,9),(89403,10),(29401,11),(82001,12),(60007,13),(29044,14),(43004,15),(94518,16),(80014,17),(50047,18),(19901,19),(60423,20),(17025,21),(6101,22),(59601,23),(96795,24),(46077,25),(39056,26),(65043,27),(99801,28),(48864,29),(95648,30),(72002,31),(90001,32),(53558,33),(36043,34),(5601,35),(37080,36),(10001,37),(73008,38),(98501,39),(85001,40),(57501,41),(2860,42),(27513,43),(94707,44),(94203,45),(55101,46),(97301,47),(84044,48),(92127,49),(95112,50),(87501,51),(65619,52),(32301,53),(33601,54),(66546,55),(8601,56),(74008,57),(20001,58);
/*!40000 ALTER TABLE `Zipcodes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-26 14:01:24

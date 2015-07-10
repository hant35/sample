-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 112.78.4.239    Database: bow
-- ------------------------------------------------------
-- Server version	5.1.73

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
-- Dumping data for table `field`
--

LOCK TABLES `field` WRITE;
/*!40000 ALTER TABLE `field` DISABLE KEYS */;
/*!40000 ALTER TABLE `field` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `field_section_relation`
--

LOCK TABLES `field_section_relation` WRITE;
/*!40000 ALTER TABLE `field_section_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `field_section_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `field_section_relation_in_template`
--

LOCK TABLES `field_section_relation_in_template` WRITE;
/*!40000 ALTER TABLE `field_section_relation_in_template` DISABLE KEYS */;
INSERT INTO `field_section_relation_in_template` VALUES (1,1,1,NULL),(2,2,1,NULL),(3,3,1,NULL),(4,4,2,NULL),(5,5,2,NULL),(6,6,2,NULL),(7,7,2,NULL),(8,8,3,NULL),(9,9,3,NULL),(10,10,3,NULL),(11,11,3,NULL),(12,12,3,NULL),(13,13,3,NULL),(14,14,3,NULL),(15,15,3,NULL),(16,16,3,NULL),(17,17,3,NULL),(18,18,3,NULL),(19,19,3,NULL),(20,20,4,NULL),(21,21,4,NULL),(22,22,4,NULL),(23,23,4,NULL),(24,24,4,NULL),(25,25,4,NULL),(26,26,4,NULL),(27,27,4,NULL),(28,28,4,NULL),(29,29,4,NULL),(30,30,4,NULL),(31,31,4,NULL),(32,32,4,NULL),(33,33,4,NULL),(34,34,4,NULL),(35,35,4,NULL),(36,36,4,NULL),(37,37,4,NULL),(38,38,4,NULL),(39,39,4,NULL),(40,40,5,NULL),(41,41,5,NULL),(42,42,5,NULL),(43,43,5,NULL),(44,44,6,NULL),(45,45,6,NULL),(46,46,6,NULL),(47,47,6,NULL),(48,48,6,NULL),(49,49,6,NULL),(50,50,6,NULL),(51,51,6,NULL),(52,52,6,NULL),(53,53,6,NULL),(54,54,6,NULL),(55,55,6,NULL),(56,56,6,NULL),(57,57,6,NULL),(58,58,6,NULL),(59,59,6,NULL),(60,60,6,NULL),(61,61,6,NULL),(62,62,6,NULL),(63,63,6,NULL),(64,64,6,NULL),(65,65,6,NULL),(66,66,6,NULL),(67,67,6,NULL),(68,68,6,NULL),(69,69,6,NULL),(70,70,6,NULL),(71,71,6,NULL),(72,72,6,NULL),(73,73,6,NULL),(74,74,6,NULL),(75,75,6,NULL),(76,76,6,NULL),(77,77,6,NULL),(78,78,6,NULL),(79,79,6,NULL),(80,80,6,NULL),(81,81,6,NULL),(82,82,6,NULL),(83,83,6,NULL),(84,84,6,NULL),(85,85,6,NULL),(86,86,6,NULL),(87,87,6,NULL),(88,88,6,NULL),(89,89,6,NULL),(90,90,6,NULL),(91,91,6,NULL),(92,92,6,NULL),(93,93,6,NULL),(94,94,6,NULL),(95,95,6,NULL),(96,96,6,NULL),(97,97,6,NULL),(98,98,6,NULL),(99,99,6,NULL),(100,100,6,NULL),(101,101,6,NULL),(102,102,6,NULL),(103,103,7,NULL),(104,104,7,NULL),(105,105,7,NULL),(106,106,7,NULL),(107,107,7,NULL),(108,108,7,NULL),(109,109,7,NULL),(110,110,7,NULL),(111,111,7,NULL),(112,112,8,NULL),(113,113,8,NULL),(114,114,8,NULL),(115,115,8,NULL),(116,116,8,NULL),(117,117,8,NULL),(118,118,8,NULL),(119,119,8,NULL),(120,120,8,NULL),(121,121,8,NULL),(122,122,9,NULL),(123,123,10,NULL),(124,124,10,NULL),(125,125,10,NULL),(126,126,10,NULL),(127,127,10,NULL),(128,128,10,NULL),(129,129,10,NULL),(130,130,10,NULL),(131,131,10,NULL),(132,132,10,NULL),(133,133,10,NULL),(134,134,10,NULL),(135,135,10,NULL),(136,136,10,NULL),(137,137,10,NULL),(138,138,10,NULL),(139,139,10,NULL),(140,140,10,NULL),(141,141,10,NULL),(142,142,10,NULL),(143,143,10,NULL),(144,144,10,NULL),(145,145,10,NULL),(146,146,10,NULL),(147,147,10,NULL),(148,148,10,NULL),(149,149,10,NULL),(150,150,10,NULL),(151,151,10,NULL),(152,152,1,NULL),(153,153,10,NULL),(154,154,10,NULL),(155,155,10,NULL),(156,156,5,NULL),(157,157,5,NULL),(158,158,5,NULL),(159,159,4,NULL),(160,160,4,NULL),(161,161,9,NULL),(162,162,10,NULL),(163,163,10,NULL),(164,164,4,NULL);
/*!40000 ALTER TABLE `field_section_relation_in_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `field_template`
--

LOCK TABLES `field_template` WRITE;
/*!40000 ALTER TABLE `field_template` DISABLE KEYS */;
INSERT INTO `field_template` VALUES (1,'','Consultant Name','Consultant Name','consultant-name'),(2,'','Email Address','Email Address','consultant-email-address'),(3,'','Mobile Number','Mobile Number','consultant-mobile-number'),(4,'','Company Name','Company Name','advertiser-company-name'),(5,'','Listing ID','Listing ID','advertiser-listing-id'),(6,'','Company Address','Company Address','advertiser-company-address'),(7,'','Company PIC','Company PIC','advertiser-company-pic'),(8,'','Advertising Solution Subscribed','Advertising Solution Subscribed','advertising-solution-subscribed'),(9,'','Google AdWords Search & Mobile','Google AdWords Search & Mobile','step-3-google-adwords-search-mobile'),(10,'','Google Display Network','Google Display Network','step-3-google-display-network'),(11,'','Yahoo! Search','Yahoo! Search','step-3-yahoo-search'),(12,'','Yahoo! Display','Yahoo! Display','step-3-yahoo-sisplay'),(13,'','YouTube Ads','YouTube Ads','step-3-youtube-sds'),(14,'','Facebook Ads','Facebook Ads','step-3-facebook-ads'),(15,'','Pan 360°','Pan 360°','step-3-pan-360'),(16,'','Alibaba.com','Alibaba.com','step-3-alibaba'),(17,'','Super Pages','Super Pages','step-3-super-pages'),(18,'','Previous Contract Value','Previous Contract Value','previous-contract-value'),(19,'','Website Visit','Website Visit','website-visit'),(20,'','Campaign Click Through Rate','Campaign Click Through Rate','campaign-click-through-rate'),(21,'','Improve Website Content','Improve Website Content','improve-website-content'),(22,'','Add On Relevant Product Pictures','Add On Relevant Product Pictures','add-on-relevant-product-pictures'),(23,'','Add On Product Description','Add On Product Description','add-on-product-description'),(24,'','Include Product Price','Include Product Price','include-product-price'),(25,'','Include Product Specification','Include Product Specification','include-product-specification'),(26,'','Include Company Details','Include Company Details','include-company-details'),(27,'','Highlight Unique Selling Point','Highlight Unique Selling Point','highlight-unique-selling-point'),(28,'','Include Certification For Better Credential','Include Certification For Better Credential','include-certification-for-better-credential'),(29,'','Enhance Website Picture Quality','Enhance Website Picture Quality','enhance-website-picture-quality'),(30,'','Improve Website Maintenance','Improve Website Maintenance','improve-website-maintenance'),(31,'','Frequently Update Your Website','Frequently Update Your Website','frequently-update-your-website'),(32,'','Timely Response To Enquiry','Timely Response To Enquiry','timely-response-to-enquiry'),(33,'','Monitor Effectiveness','Monitor Effectiveness','monitor-effectiveness'),(34,'','Tracking On Calls & Wall In','Tracking On Calls & Wall In','tracking-on-calls-wall-in'),(35,'','Improve Brand Awareness','Improve Brand Awareness','improve-brand-awareness'),(36,'','Explore Display Network','Explore Display Network','explore-display-network'),(37,'','Enhance Visibility','Enhance Visibility','extend-your-marketing-plan-por-longer-visibil'),(38,'','Extend Your Marketing Plan For Longer Visibil','Extend Your Marketing Plan For Longer Visibil','extend-your-marketing-plan-for-longer-visibil'),(39,'','Any additional comment(s)','Any additional comment(s)','any-additional-comments'),(40,'',NULL,'','step-5-a'),(41,'',NULL,'','step-5-b'),(42,'',NULL,'','step-5-d'),(43,'',NULL,'','step-5-h'),(44,'','Geographic Malaysia','Geographic MalaysiaGeographic Malaysia','geographical-area'),(45,'','Kuala Lumpur','Kuala Lumpur','language-advertiser-target'),(46,'','Selangor','Selangor','profession-advertiser-target'),(47,'','Kedah ','Kedah ','age-advertiser-target'),(48,'','Penang','Penang','gender-advertiser-target'),(49,'','Ipoh ','','ipoh'),(50,'',NULL,'','perlis'),(51,'',NULL,'','pahang'),(52,'',NULL,'','kelantan'),(53,'',NULL,'','negeri-sembilan'),(54,'',NULL,'','melaka'),(55,'',NULL,'','johor'),(56,'',NULL,'','sabah'),(57,'',NULL,'','sarawak'),(58,'','Geographic Thailand','Geographic Thailand','geographic-thailand'),(59,'','Geographic Singapore','','geographic-singapore'),(60,'','Geographic Indonesia','','geographic-indonesia'),(61,'','Geographic Others','','others-geographical-text'),(62,'','Accounting','Accounting','accounting'),(63,'','Agriculture','Agriculture','agriculture'),(64,'','Advertising','Advertising','advertising'),(65,'','Apparel & Accessories','Apparel & Accessories','apparel-accessories'),(66,'','Automotive','Automotive','automotive'),(67,'','Banking','Banking','banking'),(68,'','Broadcasting','Broadcasting','broadcasting'),(69,'','Brokerage','Brokerage','brokerage'),(70,'','Biotecnology','Biotecnology','biotecnology'),(71,'','Cargo Handling','Cargo Handling','cargo-handling'),(72,'','Chemical','Chemical','chemical'),(73,'','Computer','Computer','computer'),(74,'','Consulting','Consulting','consulting'),(75,'','Consumer Products','Consumer Products','consumer-products'),(76,'','Cosmetics','Cosmetics','cosmetics'),(77,'','Department Stores','Department Stores','department-stores'),(78,'','Education','Education','education'),(79,'','Electronics','Electronics','electronics'),(80,'','Entertainment & Leisure','Entertainment & Leisure','entertainment-leisure'),(81,'','Financial','Financial ','financial'),(82,'','Food, Beverage & Tobacco','Food, Beverage & Tobacco','food-beverage-tobacco'),(83,'','Healthcare','Healthcare','healthcare'),(84,'','Manufacturing ','','manufacturing '),(85,'','Real Estate','Real Estate','real-estate'),(86,'','Retail & Wholesale','Retail & Wholesale','retail-wholesale'),(87,'','Service','Service','service'),(88,'','Telecommunications','Telecommunications','telecommunications'),(89,'','Building & Construction','Building & Construction','building-construction'),(90,'','Hotel & Restaurant','Hotel & Restaurant','hotel-restaurant'),(91,'','','','others-profession-text'),(92,'','Language English','','language-english'),(93,'','Language Maylay','Language Maylay','language-maylay'),(94,'','Language Chinese','','language-chinese'),(95,'','Language Tamil','Language Tamil','language-tamil'),(96,'','Language ','','others-languages-text'),(97,'','18-29','','age-18-29'),(98,'','30-49','','age-30-49'),(99,'','50-64','','age-50-64'),(100,'','Others','','others-age-text'),(101,'',NULL,'','others-gender-text'),(102,'','Gender Male','','male'),(103,'','Gender Female','','female'),(104,'','Increase Website Traffic','Increase Website Traffic','advertising-objective'),(105,'','Increase Brand Awareness','Increase Brand Awareness','increase-brand-awareness'),(106,'','Increase Online Enquiry Submission','Increase Online Enquiry Submission','increase-online-enquiry-submission'),(107,'','Increase Store Visits','Increase Store Visits','increase-store-visits'),(108,'','Increase Phone Calls','Increase Phone Calls','increase-phone-calls'),(109,'','Increase Online Purchase','Increase Online Purchase','increase-online-purchase'),(110,'','Retargeting Visitors','Retargeting Visitors','retargeting-visitors'),(111,'','Increase Geographical Coverage','Increase Geographical Coverage','increase-geographical-coverage'),(112,'','Advertising Solution Rrecommend','','advertising-solution-recommend'),(113,'',NULL,'','step-8-google-adwords-search-mobile'),(114,'',NULL,'','step-8-google-display-network'),(115,'',NULL,'','step-8-yahoo-search'),(116,'',NULL,'','step-8-yahoo-sisplay'),(117,'',NULL,'','step-8-youtube-sds'),(118,'',NULL,'','step-8-facebook-ads'),(119,'',NULL,'','step-8-pan-360'),(120,'',NULL,'','step-8-alibaba'),(121,'',NULL,'','step-8-super-pages'),(122,'','Proposed Keyword','Proposed Keyword','proposed-keyword'),(123,'','PanPages','','panpages-option-a-input'),(124,'','Google Search & Mobile','','google-search-mobile-option-a-input'),(125,'','Facebook Ads','Facebook Ads','facebook-ads-option-a-input'),(126,'','Alibaba.com','Alibaba.com','alibaba-com-option-a-input'),(127,'','Management Fee','Management Fee','management-fee-option-a-input'),(128,'','Sub total','Sub total','subtotal-option-a'),(129,'','6% Gov Tax','6% Gov Tax','gov-tax-option-a'),(130,'','Grand Total','Grand Total','grand-total-option-a'),(131,'','Google Display Network','','google-display-network-option-a-input'),(132,'','Yahoo! Search','','yahoo-search-option-a-input'),(133,'','Yahoo! Display','','yahoo-display-option-a-input'),(134,'','YouTube Ads','','youtube-ads-option-a-input'),(135,'','Facebook Marketing','','facebook-marketing-option-a-input'),(136,'','Pan 360°','','pan-360-option-a-input'),(137,'','Others','','others-option-a-input'),(138,'',NULL,'','panpages-option-b-input'),(139,'',NULL,'','google-search-mobile-option-b-input'),(140,'',NULL,'','facebook-ads-option-b-input'),(141,'',NULL,'','alibaba-com-option-b-input'),(142,'',NULL,'','management-fee-option-b'),(143,'',NULL,'','subtotal-option-b'),(144,'',NULL,'','gov-tax-option-b'),(145,'',NULL,'','grand-total-option-b'),(146,'',NULL,'','google-display-network-option-b-input'),(147,'',NULL,'','yahoo-search-option-b-input'),(148,'',NULL,'','yahoo-display-option-b-input'),(149,'',NULL,'','facebook-marketing-option-b-input'),(150,'',NULL,'','pan-360-option-b-input'),(151,'',NULL,'','others-option-b-input'),(152,'','Survey Date','Survey Date','survey-date'),(153,'',NULL,'','super-pages-option-b-input'),(154,'',NULL,'','super-pages-option-ab-input'),(155,'',NULL,'','youtube-ads-option-b-input'),(156,'','Estimated monthly investment','Estimated monthly investment','est-monthly-investment'),(157,'','Total estimated impressions','Total estimated impressions','total-est-impressions'),(158,'','Estimated clicks on CTR','Estimated clicks on CTR','est-clicks-on-ctr'),(159,'','Enhance Visibility','Enhance Visibility','enhance-visibility'),(160,'','Extend Your Marketing Plan For Longer Visibil','Extend Your Marketing Plan For Longer Visibil','extend-your-marketing-plan-for-longer-visibil'),(161,'','Uploaded file name','Uploaded file name','uploaded-file-name'),(162,'','Total campaign budget','Total campaign budget','total-campaign-budget'),(163,'',NULL,'','management-fee-option-a'),(164,'',NULL,'','extend-your-marketing-plan-for-longer-visibility');
/*!40000 ALTER TABLE `field_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `section`
--

LOCK TABLES `section` WRITE;
/*!40000 ALTER TABLE `section` DISABLE KEYS */;
/*!40000 ALTER TABLE `section` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `section_survey_relation`
--

LOCK TABLES `section_survey_relation` WRITE;
/*!40000 ALTER TABLE `section_survey_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `section_survey_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `section_survey_relation_in_template`
--

LOCK TABLES `section_survey_relation_in_template` WRITE;
/*!40000 ALTER TABLE `section_survey_relation_in_template` DISABLE KEYS */;
INSERT INTO `section_survey_relation_in_template` VALUES (1,1,1,NULL),(2,2,1,NULL),(3,3,1,NULL),(4,4,1,NULL),(5,5,1,NULL),(6,6,1,NULL),(7,7,1,NULL),(8,8,1,NULL),(9,9,1,NULL),(10,10,1,NULL);
/*!40000 ALTER TABLE `section_survey_relation_in_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `section_template`
--

LOCK TABLES `section_template` WRITE;
/*!40000 ALTER TABLE `section_template` DISABLE KEYS */;
INSERT INTO `section_template` VALUES (1,'Section 1',NULL,'Section 1','section-1'),(2,'Section 2',NULL,'Section 2','section-2'),(3,'Section 3',NULL,'Section 3','section-3'),(4,'Section 4',NULL,'Section 4','section-4'),(5,'Section 5',NULL,'Section 5','section-5'),(6,'Section 6',NULL,'Section 6','section-6'),(7,'Section 7',NULL,'Section 7','section-7'),(8,'Section 8',NULL,'Section 8','section-8'),(9,'Section 9',NULL,'Section 9','section-9'),(10,'Section 10',NULL,'Section 10','section-10');
/*!40000 ALTER TABLE `section_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `survey`
--

LOCK TABLES `survey` WRITE;
/*!40000 ALTER TABLE `survey` DISABLE KEYS */;
/*!40000 ALTER TABLE `survey` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `survey_template`
--

LOCK TABLES `survey_template` WRITE;
/*!40000 ALTER TABLE `survey_template` DISABLE KEYS */;
INSERT INTO `survey_template` VALUES (1,'PANPAGES PROPOSAL SURVEY','PANPAGES PROPOSAL SURVEY',NULL,'proposal-survey');
/*!40000 ALTER TABLE `survey_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'admin','admin','$2a$10$s4pl.H43L56C2tGVt05L3.scqaiFJ8QRfxrm9R6bBZ1pN5S74edM2',1,NULL,'');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (3,'ROLE_ADMIN',NULL);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_role_relation`
--

LOCK TABLES `user_role_relation` WRITE;
/*!40000 ALTER TABLE `user_role_relation` DISABLE KEYS */;
INSERT INTO `user_role_relation` VALUES (2,2,3);
/*!40000 ALTER TABLE `user_role_relation` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-05-26 21:47:39

-- MySQL dump 10.13  Distrib 5.7.24, for osx11.1 (x86_64)
--
-- Host: localhost    Database: cu_pin_v2
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Current Database: `cu_pin_v2`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `cu_pin_v2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `cu_pin_v2`;

--
-- Table structure for table `clients_sync`
--

DROP TABLE IF EXISTS `clients_sync`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clients_sync` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `client_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status` bigint NOT NULL,
  `sync_date` datetime DEFAULT NULL,
  `reset_date` datetime DEFAULT NULL,
  `client_class` bigint NOT NULL,
  `customer_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `partner_id` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `fee_code` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `client_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients_sync`
--

LOCK TABLES `clients_sync` WRITE;
/*!40000 ALTER TABLE `clients_sync` DISABLE KEYS */;
INSERT INTO `clients_sync` VALUES (1,'testdrive@cmfb.com',1,'2025-02-17 00:00:00',NULL,0,NULL,'12345678',NULL,'S');
/*!40000 ALTER TABLE `clients_sync` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `e_seq`
--

DROP TABLE IF EXISTS `e_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `e_seq` (
  `TID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `SEQ_CODE` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `LAST_SEQ` bigint DEFAULT NULL,
  `LENGTH` int DEFAULT NULL,
  PRIMARY KEY (`TID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `e_seq`
--

LOCK TABLES `e_seq` WRITE;
/*!40000 ALTER TABLE `e_seq` DISABLE KEYS */;
INSERT INTO `e_seq` VALUES (1,'COL',3,8),(4,'001',8,10);
/*!40000 ALTER TABLE `e_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `failed_login_history`
--

DROP TABLE IF EXISTS `failed_login_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `failed_login_history` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `failed_count` bigint DEFAULT '0',
  `profile_id` bigint DEFAULT NULL,
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `action_info` varchar(240) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `log_date` datetime DEFAULT NULL,
  `email_address` varchar(70) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `mobile_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `action_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `failed_login_history`
--

LOCK TABLES `failed_login_history` WRITE;
/*!40000 ALTER TABLE `failed_login_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `failed_login_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `failed_login_info`
--

DROP TABLE IF EXISTS `failed_login_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `failed_login_info` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `failed_count` bigint DEFAULT '0',
  `profile_id` bigint DEFAULT NULL,
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `action_date` date DEFAULT NULL,
  `action_date_time` datetime DEFAULT NULL,
  `email_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `mobile_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `failed_login_info`
--

LOCK TABLES `failed_login_info` WRITE;
/*!40000 ALTER TABLE `failed_login_info` DISABLE KEYS */;
INSERT INTO `failed_login_info` VALUES (1,1,2,'shaguy null taysay','2025-02-18','2025-02-18 20:26:20','taysaycoding@gmail.com','+23480254000000'),(2,2,2,'shaguy null taysay','2025-02-19','2025-02-19 08:31:55','taysaycoding@gmail.com','+23480254000000'),(3,2,2,'shaguy null taysay','2025-02-22','2025-02-22 10:29:07','taysaycoding@gmail.com','+23480254000000');
/*!40000 ALTER TABLE `failed_login_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp_log`
--

DROP TABLE IF EXISTS `otp_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `otp_log` (
  `TID` bigint NOT NULL AUTO_INCREMENT,
  `profile_code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `otp` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `mode` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 'E',
  `token_dest` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `login_status` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`TID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp_log`
--

LOCK TABLES `otp_log` WRITE;
/*!40000 ALTER TABLE `otp_log` DISABLE KEYS */;
INSERT INTO `otp_log` VALUES (1,'+23480254000000','3233','2025-02-17 19:25:41',NULL,NULL,'2025-02-19 06:17:04','USED','M','taysaycoding@gmail.com',0);
/*!40000 ALTER TABLE `otp_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `otp_log_seq`
--

DROP TABLE IF EXISTS `otp_log_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `otp_log_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `otp_log_seq`
--

LOCK TABLES `otp_log_seq` WRITE;
/*!40000 ALTER TABLE `otp_log_seq` DISABLE KEYS */;
/*!40000 ALTER TABLE `otp_log_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment_beneficiary_log`
--

DROP TABLE IF EXISTS `payment_beneficiary_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_beneficiary_log` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `authorized_by_str` varchar(100) DEFAULT NULL,
  `authorized_by` bigint DEFAULT NULL,
  `authorized_date` datetime DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_by_str` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `beneficiary_address` varchar(240) DEFAULT NULL,
  `beneficiary_contact_person` varchar(100) DEFAULT NULL,
  `beneficiary_contact_person_email` varchar(100) DEFAULT NULL,
  `beneficiary_contact_person_mobile` varchar(30) DEFAULT NULL,
  `business_desc` varchar(100) DEFAULT NULL,
  `status` varchar(40) DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_by_str` varchar(100) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `beneficiary_name` varchar(100) DEFAULT NULL,
  `beneficiary_desc` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment_beneficiary_log`
--

LOCK TABLES `payment_beneficiary_log` WRITE;
/*!40000 ALTER TABLE `payment_beneficiary_log` DISABLE KEYS */;
INSERT INTO `payment_beneficiary_log` VALUES (1,NULL,1,'2025-02-20 18:44:13',1,'taysaycoding@gmail.com','2025-02-20 18:10:50','1 demo road','Tested tester testeex','tx@x.com','+2348036533888',NULL,'DELETED',1,'taysaycoding@gmail.com','2025-02-20 18:44:21','Landmark University','Post Grad School Fee'),(2,NULL,0,NULL,1,'taysaycoding@gmail.com','2025-02-20 18:16:10','1 demo road','Tested tester testee','tx@x.com','+2348036533888',NULL,'INACTIVE',0,NULL,NULL,'Covenant University','School'),(3,NULL,0,NULL,1,'taysaycoding@gmail.com','2025-03-02 20:12:42','1 demo road','Tested tester testng','tx@x.com','+2348036533888',NULL,'INACTIVE',0,NULL,NULL,'Cruise University','School'),(4,NULL,0,NULL,1,'taysaycoding@gmail.com','2025-03-02 20:22:00','1 demo road','Tested tester testng','tx@x.com','+2348036533888',NULL,'INACTIVE',0,NULL,NULL,'Kaleb University','School');
/*!40000 ALTER TABLE `payment_beneficiary_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments_log`
--

DROP TABLE IF EXISTS `payments_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payments_log` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `amount` double DEFAULT NULL,
  `amount_flex` bit(1) DEFAULT NULL,
  `authorized_by_str` varchar(100) DEFAULT NULL,
  `authorized_by` bigint DEFAULT NULL,
  `authorized_date` datetime DEFAULT NULL,
  `beneficiary_id` bigint DEFAULT NULL,
  `beneficiary_name` varchar(100) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_by_str` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `in_tax` bit(1) DEFAULT NULL,
  `min_amount` double DEFAULT NULL,
  `payee_id` varchar(50) DEFAULT NULL,
  `payee_id_label` varchar(30) DEFAULT NULL,
  `payment_desc` varchar(100) DEFAULT NULL,
  `product_id` varchar(50) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `tax_amount` double DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_by_str` varchar(100) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `fee` double DEFAULT NULL,
  `custom_response_param_1` varchar(100) DEFAULT NULL,
  `custom_response_param_2` varchar(100) DEFAULT NULL,
  `payment_collection_account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `payment_collection_account_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments_log`
--

LOCK TABLES `payments_log` WRITE;
/*!40000 ALTER TABLE `payments_log` DISABLE KEYS */;
INSERT INTO `payments_log` VALUES (1,20000,_binary '',NULL,1,'2025-02-20 21:42:32',1,'Landmark University',1,'taysaycoding@gmail.com','2025-02-20 21:28:27',NULL,50,NULL,'Matric No:','Post grad School Fee','School Fee','ACTIVE',0,1,'taysaycoding@gmail.com','2025-02-20 21:41:42',50,NULL,NULL,NULL,NULL),(2,20000,_binary '\0',NULL,0,NULL,1,'Landmark University',1,'taysaycoding@gmail.com','2025-03-02 18:53:35',_binary '\0',50,NULL,'Matric No:','School Fee','00000002','INACTIVE',0,0,NULL,NULL,0,NULL,NULL,'1234567890','Landmark school Fee'),(3,20000,_binary '\0',NULL,0,NULL,1,'Landmark University',1,'taysaycoding@gmail.com','2025-03-04 18:36:46',_binary '\0',50,NULL,'Matric No:','Post School Fee','00000003','INACTIVE',0,0,NULL,NULL,0,NULL,NULL,'1234567890','Landmark school Fee');
/*!40000 ALTER TABLE `payments_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profiles_logs`
--

DROP TABLE IF EXISTS `profiles_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `profiles_logs` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT NULL,
  `email_address` varchar(100) DEFAULT NULL,
  `first_name` varchar(80) DEFAULT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `last_name` varchar(80) DEFAULT NULL,
  `last_update_date` datetime DEFAULT NULL,
  `login_status` int DEFAULT NULL,
  `middle_name` varchar(80) DEFAULT NULL,
  `mobile_no` varchar(20) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `till_account` varchar(20) DEFAULT NULL,
  `user_role` int DEFAULT NULL,
  `user_role_str` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profiles_logs`
--

LOCK TABLES `profiles_logs` WRITE;
/*!40000 ALTER TABLE `profiles_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `profiles_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles_info`
--

DROP TABLE IF EXISTS `roles_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles_info` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `role_desc` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `role_code` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `status` bigint DEFAULT NULL,
  `role_prefix` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `updated_by_str` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_by_str` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status_str` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `for_admin_dashboard` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `approved_by` bigint DEFAULT '0',
  `approved_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles_info`
--

LOCK TABLES `roles_info` WRITE;
/*!40000 ALTER TABLE `roles_info` DISABLE KEYS */;
INSERT INTO `roles_info` VALUES (1,'ADMINISTRATOR','System Admin','01','2025-01-01 12:34:34',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL),(2,'AUTHORIZER','System Authorizer','02','2025-01-01 12:34:34',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL),(3,'TELLER','Teller','03','2025-01-01 12:34:34',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL);
/*!40000 ALTER TABLE `roles_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `last_updated_date` datetime DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_data`
--

DROP TABLE IF EXISTS `sys_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_data` (
  `TID` bigint NOT NULL AUTO_INCREMENT,
  `PARAM_NAME` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `PARAM_VALUE` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `STS` bigint DEFAULT NULL,
  `CREATED_BY` bigint DEFAULT NULL,
  `CREATED_DATE` datetime DEFAULT NULL,
  `AUTH_BY` bigint DEFAULT '0',
  `AUTH_DATE` datetime DEFAULT NULL,
  `OPR_COMMENT` varchar(240) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `last_updated_by` bigint DEFAULT '0',
  `auth_by_str` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `created_by_str` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `last_updated_by_str` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status_str` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `last_updated_date` date DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`TID`),
  KEY `STS` (`STS`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_data`
--

LOCK TABLES `sys_data` WRITE;
/*!40000 ALTER TABLE `sys_data` DISABLE KEYS */;
INSERT INTO `sys_data` VALUES (1,'DEFAULT-OTP-LENGTH','4',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(2,'CHANNEL','WEB',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(3,'MAX-ALLOWED-FAILED-LOGIN-ATTEMPTS','5',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(4,'SHORT-NAME','CMFB',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(5,'TITLE','Notification Email',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(6,'SUPPORT-TEAM','e-business Team',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(7,'WELCOME-BANNER','welcome things',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(8,'WWW','https://covenantmfb.com.ng/',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(9,'SUPPORT-LINES','e-business@covenantmfb.com.ng, +234-1-4545571, +234-815-736-4348',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(10,'MAIL-TABLE-BG-COLOR','37ff33',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(11,'MAIL-TABLE-HD-COLOR','0b7609',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(12,'MAIL-TABLE-BORDER-COLOR','0b7609',1,1,'2025-01-01 12:30:10',0,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,'ACTIVE'),(13,'TEST','PASSED',NULL,NULL,'2025-02-19 08:01:47',NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'2025-02-19','INACTIVE'),(14,'CU-USERNAME','CMFByb2w',NULL,NULL,'2025-02-19 08:01:47',0,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'2025-02-19','INACTIVE'),(15,'CU-PASSWORD','yxVO7Fcb',NULL,NULL,'2025-02-19 08:01:47',0,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'2025-02-19','INACTIVE'),(16,'CU-TENANTCODE','CU01',NULL,NULL,'2025-02-19 08:01:47',0,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'2025-02-19','INACTIVE'),(17,'SYS_IV','+!rtmoputy*%1234',NULL,NULL,'2025-02-19 08:01:47',0,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'2025-02-19','INACTIVE'),(18,'SYS_KEY','+!r:moputy*%0000',NULL,NULL,'2025-02-19 08:01:47',0,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL,'2025-02-19','INACTIVE');
/*!40000 ALTER TABLE `sys_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trx_log`
--

DROP TABLE IF EXISTS `trx_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trx_log` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `authorized_date` datetime DEFAULT NULL,
  `authorized_by` bigint DEFAULT NULL,
  `authorized_by_str` varchar(100) DEFAULT NULL,
  `biller_id` varchar(100) DEFAULT NULL,
  `cba_status` varchar(20) DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_by_str` varchar(100) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `customer_address` varchar(200) DEFAULT NULL,
  `customer_fullname` varchar(100) DEFAULT NULL,
  `customer_email` varchar(70) DEFAULT NULL,
  `customer_mobile` varchar(30) DEFAULT NULL,
  `dest_account` varchar(20) DEFAULT NULL,
  `dest_account2` varchar(100) DEFAULT NULL,
  `DEST_BANK` varchar(100) DEFAULT NULL,
  `DEST_BANK_NAME` varchar(100) DEFAULT NULL,
  `EXT_TRANS_NO` varchar(100) DEFAULT NULL,
  `ext_response_code` varchar(100) DEFAULT NULL,
  `ext_response_desc` varchar(100) DEFAULT NULL,
  `partner_resp` varchar(100) DEFAULT NULL,
  `payer_id` varchar(20) DEFAULT NULL,
  `payment_code` varchar(100) DEFAULT NULL,
  `payment_fee` decimal(10,0) DEFAULT NULL,
  `payment_reference` varchar(100) DEFAULT NULL,
  `beneficiary_name` varchar(100) DEFAULT NULL,
  `OUR_TRANSID` varchar(100) DEFAULT NULL,
  `REQX_STR` varchar(240) DEFAULT NULL,
  `resp_code` varchar(10) DEFAULT NULL,
  `RESP_STR` text,
  `src_bank_code` varchar(10) DEFAULT NULL,
  `src_account` varchar(10) DEFAULT NULL,
  `src_account_name` varchar(100) DEFAULT NULL,
  `SRC_BANK` varchar(10) DEFAULT NULL,
  `tax_amount` double DEFAULT NULL,
  `tenant_code` varchar(10) DEFAULT NULL,
  `trans_desc` varchar(100) DEFAULT NULL,
  `TRX_STATUS` varchar(10) DEFAULT NULL,
  `TRANS_TYPE_STR` varchar(100) DEFAULT NULL,
  `transaction_amount` double DEFAULT NULL,
  `transaction_id` varchar(50) DEFAULT NULL,
  `transaction_reference` varchar(100) DEFAULT NULL,
  `TRX_FEE` double DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_by_str` varchar(100) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `custom_response_param_1` varchar(100) DEFAULT NULL,
  `custom_response_param_2` varchar(100) DEFAULT NULL,
  `partner_response_code` varchar(10) DEFAULT NULL,
  `product_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trx_log`
--

LOCK TABLES `trx_log` WRITE;
/*!40000 ALTER TABLE `trx_log` DISABLE KEYS */;
INSERT INTO `trx_log` VALUES (1,NULL,0,NULL,NULL,NULL,1,'taysaycoding@gmail.com','2025-02-20 22:40:32','1 demo drive ','TESTA TEST','taysaycoding@gmail.com','+2348036533999',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'21AB028501',NULL,50,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'2025-02-20/00000001/TESTA TEST/1.0',NULL,NULL,1,'0000000001',NULL,50,0,NULL,NULL,NULL,NULL,NULL,NULL),(2,NULL,0,NULL,NULL,NULL,1,'taysaycoding@gmail.com','2025-02-20 22:44:28','1 demo drive ','TESTA TEST','taysaycoding@gmail.com','+2348036533999',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'21AB028501',NULL,50,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'2025-02-20/00000001/TESTA TEST/1.0',NULL,NULL,1,'0000000002',NULL,50,0,NULL,NULL,NULL,NULL,NULL,NULL),(3,NULL,0,NULL,NULL,NULL,1,'taysaycoding@gmail.com','2025-02-20 22:48:06','1 demo drive ','TESTA TEST','taysaycoding@gmail.com','+2348036533999','NA','NA',NULL,NULL,NULL,NULL,NULL,'transaction failed','21AB028501',NULL,50,NULL,NULL,NULL,NULL,NULL,'ValidateCheckResponse[payparameter=ValidateCheckPaymentParam[responsecode=4, responsename=transaction failed, passcode=null, accountsettlement=null]]',NULL,NULL,NULL,NULL,0,NULL,'2025-02-20/00000001/TESTA TEST/1.0',NULL,NULL,1,'0000000003',NULL,50,0,NULL,'2025-02-20 22:48:11',NULL,NULL,NULL,NULL),(4,NULL,0,NULL,NULL,NULL,1,'taysaycoding@gmail.com','2025-02-20 22:54:51','1 demo drive ','TESTA TEST','taysaycoding@gmail.com','+2348036533999','0520000322','',NULL,NULL,NULL,NULL,NULL,'Proceed to PIN','21AB028501','$2y$11$h7VfGfSQ0UlG1zJQFyl4eu3P0h8wEjHOzblglUk6lBW1PJFbt6so6',50,NULL,NULL,NULL,NULL,NULL,'ValidateCheckResponse[payparameter=ValidateCheckPaymentParam[responsecode=09, responsename=Proceed to PIN, passcode=$2y$11$h7VfGfSQ0UlG1zJQFyl4eu3P0h8wEjHOzblglUk6lBW1PJFbt6so6, accountsettlement=ValidateCheckAccountSettlementObj[accountnumber1=0520000322, accamount1=-49, accountnumber2=, accmount2=50]]]',NULL,NULL,NULL,NULL,0,NULL,'2025-02-20/School Fee/TESTA TEST/1.0',NULL,NULL,1,'0000000004',NULL,50,0,NULL,'2025-02-20 22:54:59',NULL,NULL,NULL,NULL),(5,NULL,0,NULL,NULL,NULL,1,'taysaycoding@gmail.com','2025-02-20 23:02:36','1 demo drive ','TESTA TEST','taysaycoding@gmail.com','+2348036533999','0520000322','',NULL,NULL,NULL,NULL,NULL,'Proceed to PIN','21AB028501','$2y$11$M3ChYq0opPUrHcEvWrz3ceAoXSK4U/59TjN7bGVk.6sih7/6TRP6.',50,NULL,NULL,NULL,NULL,NULL,'ValidateCheckResponse[payparameter=ValidateCheckPaymentParam[responsecode=09, responsename=Proceed to PIN, passcode=$2y$11$M3ChYq0opPUrHcEvWrz3ceAoXSK4U/59TjN7bGVk.6sih7/6TRP6., accountsettlement=ValidateCheckAccountSettlementObj[accountnumber1=0520000322, accamount1=-49, accountnumber2=, accmount2=50]]]',NULL,NULL,NULL,NULL,0,NULL,'2025-02-21/School Fee/TESTA TEST/1.0',NULL,NULL,1,'0000000005',NULL,50,0,NULL,'2025-02-20 23:02:44',NULL,NULL,NULL,NULL),(6,NULL,0,NULL,NULL,NULL,1,'taysay@us.com','2025-02-26 11:13:48','1 demo rord Lagos','TANKO KEY','taysay@us.com','+2348036533888','NA','NA',NULL,NULL,NULL,NULL,NULL,'Invalid Payerid','201A000',NULL,50,NULL,'Landmark University',NULL,NULL,NULL,'ValidateCheckResponse[payparameter=ValidateCheckPaymentParam[responsecode=10, responsename=Invalid Payerid, passcode=null, accountsettlement=null]]',NULL,NULL,NULL,NULL,100,NULL,'2025-02-26/School Fee/TANKO KEY/3000.0',NULL,NULL,3000,'0000000006',NULL,50,0,NULL,'2025-02-26 11:13:56',NULL,NULL,NULL,'School Fee'),(7,NULL,0,NULL,NULL,NULL,1,'taysay@us.com','2025-02-26 11:15:45','1 demo rord Lagos','TANKO KEY','taysay@us.com','+2348036533888','0520000322','',NULL,NULL,NULL,NULL,NULL,'Proceed to PIN','21AB028501','$2y$11$SZia26.BhFcSI.CPAFxj5uFdXQ/wizuBfe7xwzCck7ocDyUHR/.Na',50,NULL,'Landmark University',NULL,NULL,NULL,'ValidateCheckResponse[payparameter=ValidateCheckPaymentParam[responsecode=09, responsename=Proceed to PIN, passcode=$2y$11$SZia26.BhFcSI.CPAFxj5uFdXQ/wizuBfe7xwzCck7ocDyUHR/.Na, accountsettlement=ValidateCheckAccountSettlementObj[accountnumber1=0520000322, accamount1=2950, accountnumber2=, accmount2=50]]]',NULL,NULL,NULL,NULL,100,NULL,'2025-02-26/School Fee/TANKO KEY/3000.0',NULL,NULL,3000,'0000000007',NULL,50,0,NULL,'2025-02-26 11:15:53',NULL,NULL,NULL,'School Fee'),(8,NULL,0,NULL,NULL,NULL,1,'taysay@us.com','2025-02-26 11:23:10','1 demo rord Lagos','TANKO KEY','taysay@us.com','+2348036533888','0520000322','',NULL,NULL,NULL,NULL,NULL,'Proceed to PIN','21AB028501','$2y$11$FwTsKSgthzJUcygRwsT0OOTXgEYWhVGsQBHW0UgPq48rt4EdqaCX6',50,NULL,'Landmark University',NULL,NULL,NULL,'ValidateCheckResponse[payparameter=ValidateCheckPaymentParam[responsecode=09, responsename=Proceed to PIN, passcode=$2y$11$FwTsKSgthzJUcygRwsT0OOTXgEYWhVGsQBHW0UgPq48rt4EdqaCX6, accountsettlement=ValidateCheckAccountSettlementObj[accountnumber1=0520000322, accamount1=-49, accountnumber2=, accmount2=50]]]',NULL,NULL,NULL,NULL,50,NULL,'2025-02-26/School Fee/TANKO KEY/1.0','ACTIVE',NULL,1,'0000000008',NULL,50,0,NULL,'2025-03-02 16:31:41','999010708907','99901076869019681473',NULL,'School Fee');
/*!40000 ALTER TABLE `trx_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_logs`
--

DROP TABLE IF EXISTS `user_logs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_logs` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) DEFAULT NULL,
  `middle_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `mobile_no` varchar(50) DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `last_update_date` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  `pin_change` int DEFAULT '0',
  `status_str` varchar(30) DEFAULT NULL,
  `login_status` bigint DEFAULT NULL,
  `email_address` varchar(100) DEFAULT NULL,
  `till_account` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `user_role` int DEFAULT NULL,
  `user_role_str` varchar(100) DEFAULT NULL,
  `full_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_logs`
--

LOCK TABLES `user_logs` WRITE;
/*!40000 ALTER TABLE `user_logs` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_logs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_logs_seq`
--

DROP TABLE IF EXISTS `user_logs_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_logs_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_logs_seq`
--

LOCK TABLES `user_logs_seq` WRITE;
/*!40000 ALTER TABLE `user_logs_seq` DISABLE KEYS */;
INSERT INTO `user_logs_seq` VALUES (101);
/*!40000 ALTER TABLE `user_logs_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profile`
--

DROP TABLE IF EXISTS `user_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profile` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `middle_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `last_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `mobile_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `account_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `email_address` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `login_status` int NOT NULL DEFAULT '0',
  `status` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `last_update_date` datetime DEFAULT NULL,
  `account_tag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `bvn` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `kyc` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `status_str` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `device_fingerprint` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `pin_change` int DEFAULT '0',
  `account_type` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `full_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `user_role` bigint DEFAULT '0',
  `user_role_str` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `till_account` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  PRIMARY KEY (`tid`),
  KEY `last_name_idx` (`last_name`) USING BTREE,
  KEY `mobile_no_idx` (`mobile_no`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profile`
--

LOCK TABLES `user_profile` WRITE;
/*!40000 ALTER TABLE `user_profile` DISABLE KEYS */;
INSERT INTO `user_profile` VALUES (1,'Toyo','M','Mada','+23480254000000',NULL,NULL,NULL,'taysaycoding@gmail.com',0,'0',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL,1,'Administrator','1234567890');
/*!40000 ALTER TABLE `user_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_profiles`
--

DROP TABLE IF EXISTS `user_profiles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_profiles` (
  `tid` bigint NOT NULL AUTO_INCREMENT,
  `email_address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_profiles`
--

LOCK TABLES `user_profiles` WRITE;
/*!40000 ALTER TABLE `user_profiles` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_profiles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-06 10:45:29

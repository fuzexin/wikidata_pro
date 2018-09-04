/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50615
Source Host           : localhost:3306
Source Database       : wikidata

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2018-09-04 14:06:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for aliases
-- ----------------------------
DROP TABLE IF EXISTS `aliases`;
CREATE TABLE `aliases` (
  `entity_id` varchar(10) NOT NULL,
  `language` varchar(5) NOT NULL,
  `content` varchar(255) NOT NULL,
  KEY `entity_id_Btree_index` (`entity_id`) USING BTREE,
  KEY `content_Btree_index` (`content`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for descriptions
-- ----------------------------
DROP TABLE IF EXISTS `descriptions`;
CREATE TABLE `descriptions` (
  `entity_id` varchar(10) NOT NULL,
  `language` varchar(5) NOT NULL,
  `content` text NOT NULL,
  KEY `entityID_Btree_index` (`entity_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for entity
-- ----------------------------
DROP TABLE IF EXISTS `entity`;
CREATE TABLE `entity` (
  `id` varchar(10) NOT NULL,
  `type` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for labels
-- ----------------------------
DROP TABLE IF EXISTS `labels`;
CREATE TABLE `labels` (
  `entity_id` varchar(10) DEFAULT NULL,
  `language` varchar(5) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  KEY `entityID_Btree_index` (`entity_id`) USING BTREE,
  KEY `content_Btree_index` (`content`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for property
-- ----------------------------
DROP TABLE IF EXISTS `property`;
CREATE TABLE `property` (
  `property_id` varchar(10) NOT NULL,
  `entity_id` varchar(10) DEFAULT NULL,
  `valueType` varchar(20) DEFAULT NULL,
  `value` text,
  KEY `entityID_Btree_index` (`entity_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

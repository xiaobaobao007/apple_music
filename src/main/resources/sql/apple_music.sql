/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50617
Source Host           : localhost:3306
Source Database       : apple_music

Target Server Type    : MYSQL
Target Server Version : 50617
File Encoding         : 65001

Date: 2018-10-29 10:56:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_class
-- ----------------------------
DROP TABLE IF EXISTS `tb_class`;
CREATE TABLE `tb_class` (
  `code` int(11) NOT NULL AUTO_INCREMENT,
  `classname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`code`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_class
-- ----------------------------
INSERT INTO `tb_class` VALUES ('1', '复古');
INSERT INTO `tb_class` VALUES ('2', '经典');
INSERT INTO `tb_class` VALUES ('3', '欢快');

-- ----------------------------
-- Table structure for tb_music
-- ----------------------------
DROP TABLE IF EXISTS `tb_music`;
CREATE TABLE `tb_music` (
  `id` int(11) NOT NULL,
  `clicks` int(11) DEFAULT NULL,
  `image` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `number` int(11) DEFAULT NULL,
  `singer` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `src` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `classs_id` int(11) DEFAULT NULL,
  `clloect` int(11) NOT NULL,
  KEY `FKiuhqqwt78xil0pq55r6v83n99` (`classs_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_music
-- ----------------------------
INSERT INTO `tb_music` VALUES ('1', '4266', '只要平凡.jpg', '只要平凡', '101', '张杰', '只要平凡.mp3', '2', '0');
INSERT INTO `tb_music` VALUES ('8', '16833', '那吾克热.jpg', '那吾克热', '109', '那午', '那吾克热.mp3', '3', '0');
INSERT INTO `tb_music` VALUES ('9', '14842', '玉洞仙源.jpg', '玉洞仙源', '132', '许巍', '玉洞仙源.mp3', '1', '0');
INSERT INTO `tb_music` VALUES ('10', '11850', '演员.jpg', '演员', '145', '薛之谦', '演员.mp3', '3', '0');
INSERT INTO `tb_music` VALUES ('4', '18370', '凌晨三点.jpg', '凌晨三点', '163', '陈子说', '凌晨三点.mp3', '1', '0');
INSERT INTO `tb_music` VALUES ('5', '6831', '温柔.jpg', '温柔', '164', '江哲', '温柔.mp3', '2', '0');
INSERT INTO `tb_music` VALUES ('3', '8629', '无问.jpg', '无问', '243', '毛不宜', '无问.mp3', '1', '0');
INSERT INTO `tb_music` VALUES ('2', '3840', '不哭.jpg', '不哭', '425', '乾丰', '不哭.mp3', '1', '0');
INSERT INTO `tb_music` VALUES ('6', '29311', '房东的猫.jpg', '房东的猫', '532', '郑雪玲', '房东的猫.mp3', '3', '0');
INSERT INTO `tb_music` VALUES ('11', '21910', '乌云中.jpg', '乌云中', '743', '张毅', '乌云中.mp3', '2', '0');

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES ('1', 'ROLE_ADMIN');
INSERT INTO `tb_role` VALUES ('2', 'ROLE_SUPER');
INSERT INTO `tb_role` VALUES ('3', 'ROLE_VIP');
INSERT INTO `tb_role` VALUES ('4', 'ROLE_OTHER');

-- ----------------------------
-- Table structure for tb_sug
-- ----------------------------
DROP TABLE IF EXISTS `tb_sug`;
CREATE TABLE `tb_sug` (
  `id` int(11) NOT NULL,
  `suggest` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `tel` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_sug
-- ----------------------------
INSERT INTO `tb_sug` VALUES ('1', '3', '3', '166');
INSERT INTO `tb_sug` VALUES ('2', '', 'tesy', 'testtest');
INSERT INTO `tb_sug` VALUES ('3', '111', '111', '1111');

-- ----------------------------
-- Table structure for tb_us
-- ----------------------------
DROP TABLE IF EXISTS `tb_us`;
CREATE TABLE `tb_us` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `downloadnums` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_us
-- ----------------------------
INSERT INTO `tb_us` VALUES ('1', '1', '$2a$10$f0OXxTkpJcWvbswnZsV7EOqyeTp4OFmgCSyLaCHVMNb8os.PIH4Bi', '管理员', '-1');
INSERT INTO `tb_us` VALUES ('2', '2', '$2a$10$f0OXxTkpJcWvbswnZsV7EOqyeTp4OFmgCSyLaCHVMNb8os.PIH4Bi', '黄金会员', '-1');
INSERT INTO `tb_us` VALUES ('3', '3', '$2a$10$f0OXxTkpJcWvbswnZsV7EOqyeTp4OFmgCSyLaCHVMNb8os.PIH4Bi', '白银会员', '0');
INSERT INTO `tb_us` VALUES ('4', 'visitor', '$2a$10$f0OXxTkpJcWvbswnZsV7EOqyeTp4OFmgCSyLaCHVMNb8os.PIH4Bi', '游客', '99');
INSERT INTO `tb_us` VALUES ('7', '123', '$2a$10$Q1vAa3ox6YOzE6ewzTbIOOl6MuK4nN.zIg9mJYnkSD/x406/7avwe', '123', '0');
INSERT INTO `tb_us` VALUES ('6', '123qew', '$2a$10$9WoKkYvx.dwfISraw1WfRe6fZWmn0cz4LATkIxre0xr9aaiF8MVLW', 'qwe', '0');
INSERT INTO `tb_us` VALUES ('5', 'bmy', '$2a$10$K9FR4ONJdG/ovHWzmqsrIuUfo7oxe9l0xOaUhYBtM1Y8Pd4QdOBM6', 'bmy', '998');
INSERT INTO `tb_us` VALUES ('18', '123456', '$2a$10$heGRu3DUNla8/4GfwuMBb.dLevscl.foYzAd/l/h6K8.hpdfRXc/C', '小虎', '0');

-- ----------------------------
-- Table structure for tb_user_music
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_music`;
CREATE TABLE `tb_user_music` (
  `users_id` int(11) NOT NULL,
  `musics_id` int(11) NOT NULL,
  KEY `FKia3p2xf4xymy2lb0pj5oxgqtr` (`musics_id`),
  KEY `FKew4rdx5fswpaaeh7qmp4b3jh6` (`users_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_user_music
-- ----------------------------
INSERT INTO `tb_user_music` VALUES ('3', '11');
INSERT INTO `tb_user_music` VALUES ('3', '4');
INSERT INTO `tb_user_music` VALUES ('3', '8');
INSERT INTO `tb_user_music` VALUES ('2', '6');
INSERT INTO `tb_user_music` VALUES ('2', '11');
INSERT INTO `tb_user_music` VALUES ('1', '6');
INSERT INTO `tb_user_music` VALUES ('1', '11');

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FKea2ootw6b6bb0xt3ptl28bymv` (`role_id`),
  KEY `FKeqxgld75xukv4jcqhn2nm28g2` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES ('1', '1');
INSERT INTO `tb_user_role` VALUES ('2', '2');
INSERT INTO `tb_user_role` VALUES ('3', '3');
INSERT INTO `tb_user_role` VALUES ('4', '4');
INSERT INTO `tb_user_role` VALUES ('12', '3');
INSERT INTO `tb_user_role` VALUES ('14', '3');
INSERT INTO `tb_user_role` VALUES ('18', '3');
INSERT INTO `tb_user_role` VALUES ('5', '1');
INSERT INTO `tb_user_role` VALUES ('6', '3');
INSERT INTO `tb_user_role` VALUES ('7', '3');

/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50704
Source Host           : 127.0.0.1:3306
Source Database       : mybatis-dev

Target Server Type    : MYSQL
Target Server Version : 50704
File Encoding         : 65001

Date: 2016-05-13 10:47:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'Jack', '123', 'jack@qq.com');
INSERT INTO `users` VALUES ('11', 'Jack', '123', 'jack@qq.com');
INSERT INTO `users` VALUES ('14', 'Jack', '123', 'jack@qq.com');
INSERT INTO `users` VALUES ('15', 'Jack', '123', 'jack@qq.com');
INSERT INTO `users` VALUES ('16', 'Jack', '123', 'jack@qq.com');
INSERT INTO `users` VALUES ('17', 'Jack', '123', 'jack@qq.com');
INSERT INTO `users` VALUES ('18', 'Jack', '123', 'jack@qq.com');
INSERT INTO `users` VALUES ('19', 'Jack', '123', 'jack@qq.com');
INSERT INTO `users` VALUES ('20', 'Jack', '123', 'jack@gmail.com');
INSERT INTO `users` VALUES ('21', 'Jack', '123', 'jack@gmail.com');
INSERT INTO `users` VALUES ('22', 'Jack', '123', 'jack@gmail.com');

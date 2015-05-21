/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50623
 Source Host           : localhost
 Source Database       : springsecurityoauth2server

 Target Server Type    : MySQL
 Target Server Version : 50623
 File Encoding         : utf-8

 Date: 05/21/2015 20:27:09 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `access_token_validity` int(255) NOT NULL,
  `refresh_token_validity` int(255) NOT NULL,
  `additional_information` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`resource_ids`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `oauth_client_details`
-- ----------------------------
BEGIN;
INSERT INTO `oauth_client_details` VALUES ('oauth/client', 'test', 'test', 'read,write', 'password,authorization_code,refresh_token', 'http://test.com', 'ROLE_CLIENT', '40000', '50000', '{\"desc\":\"eeee\"}', 'true');
COMMIT;

-- ----------------------------
--  Table structure for `resc`
-- ----------------------------
DROP TABLE IF EXISTS `resc`;
CREATE TABLE `resc` (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `res_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `res_string` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `priority` int(11) DEFAULT NULL,
  `desc` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `resc`
-- ----------------------------
BEGIN;
INSERT INTO `resc` VALUES ('1', '', 'URL', '/admin.jsp', '1', ''), ('2', '', 'URL', '/**', '2', ''), ('3', '', 'URL', '/login.jsp*', '1', '');
COMMIT;

-- ----------------------------
--  Table structure for `resc_role`
-- ----------------------------
DROP TABLE IF EXISTS `resc_role`;
CREATE TABLE `resc_role` (
  `resc_id` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`resc_id`,`role_id`),
  KEY `fk_resc_role_role` (`role_id`),
  CONSTRAINT `fk_resc_role_resc` FOREIGN KEY (`resc_id`) REFERENCES `resc` (`id`),
  CONSTRAINT `fk_resc_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `resc_role`
-- ----------------------------
BEGIN;
INSERT INTO `resc_role` VALUES ('1', '1'), ('2', '1'), ('2', '2'), ('3', '3');
COMMIT;

-- ----------------------------
--  Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `desc` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `role`
-- ----------------------------
BEGIN;
INSERT INTO `role` VALUES ('1', 'ROLE_ADMIN', '管理员角色'), ('2', 'ROLE_USER', '用户角色'), ('3', 'IS_AUTHENTICATED_ANONYMOUSLY', 'anonymous');
COMMIT;

-- ----------------------------
--  Table structure for `users`
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `desc` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `users`
-- ----------------------------
BEGIN;
INSERT INTO `users` VALUES ('1', 'admin', 'admin', '1', '管理员ceb4f32325eda6142bd65215f4c0f371'), ('2', 'user', '47a733d60998c719cf3526ae7d106d13', '1', '用户');
COMMIT;

-- ----------------------------
--  Table structure for `users_role`
-- ----------------------------
DROP TABLE IF EXISTS `users_role`;
CREATE TABLE `users_role` (
  `user_id` int(11) NOT NULL DEFAULT '0',
  `role_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_users_role_role` (`role_id`),
  CONSTRAINT `fk_users_role_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_users_role_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Records of `users_role`
-- ----------------------------
BEGIN;
INSERT INTO `users_role` VALUES ('1', '1'), ('1', '2'), ('2', '2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;

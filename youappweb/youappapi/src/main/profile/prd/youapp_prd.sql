/*
Navicat MySQL Data Transfer

Source Server         : YouAPP
Source Server Version : 50630
Source Host           : localhost:3306
Source Database       : youapp

Target Server Type    : MYSQL
Target Server Version : 50630
File Encoding         : 65001

Date: 2016-09-13 13:11:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for app_meta
-- ----------------------------
DROP TABLE IF EXISTS `app_meta`;
CREATE TABLE `app_meta` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `APP_ACTIVE` varchar(36) DEFAULT NULL,
  `APP_COMP_NAME` varchar(36) DEFAULT NULL,
  `APP_DESC` varchar(2000) DEFAULT NULL,
  `APP_HOST` varchar(36) DEFAULT NULL,
  `APP_JARURL` varchar(2000) DEFAULT NULL,
  `APP_NAME` varchar(36) DEFAULT NULL,
  `APP_UNIQUE` varchar(36) DEFAULT NULL,
  `APP_VERSION` varchar(36) DEFAULT NULL,
  `DEPLOY_TYPE` varchar(36) DEFAULT NULL,
  `FRIENDLY_URL` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for bill
-- ----------------------------
DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `BILL_NAME` varchar(36) DEFAULT NULL,
  `BILL_TIME` datetime DEFAULT NULL,
  `BILL_TYPE` varchar(36) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `MALL_NAME` varchar(36) DEFAULT NULL,
  `MONEY` double DEFAULT NULL,
  `USER_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for good
-- ----------------------------
DROP TABLE IF EXISTS `good`;
CREATE TABLE `good` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `BILL_ID` varchar(36) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `GOOD_NAME` varchar(36) DEFAULT NULL,
  `GOOD_TYPE` varchar(36) DEFAULT NULL,
  `MONEY` double DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `GROUP_CODE` varchar(36) DEFAULT NULL,
  `GROUP_NAME` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CODE` varchar(36) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `NAME` varchar(36) DEFAULT NULL,
  `PID` varchar(36) DEFAULT NULL,
  `SEQUENCE` int(11) DEFAULT NULL,
  `URL` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menu_group
-- ----------------------------
DROP TABLE IF EXISTS `menu_group`;
CREATE TABLE `menu_group` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `GROUP_ID` varchar(36) DEFAULT NULL,
  `MENU_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menu_role
-- ----------------------------
DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `MENU_ID` varchar(36) DEFAULT NULL,
  `ROLE_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for param_code
-- ----------------------------
DROP TABLE IF EXISTS `param_code`;
CREATE TABLE `param_code` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CODE` varchar(36) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `NAME` varchar(36) DEFAULT NULL,
  `TYPE` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for param_type
-- ----------------------------
DROP TABLE IF EXISTS `param_type`;
CREATE TABLE `param_type` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CODE` varchar(36) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `NAME` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resources
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `CACHED` varchar(36) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `FRIENDLY_URL` varchar(36) DEFAULT NULL,
  `URL` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resources_groups
-- ----------------------------
DROP TABLE IF EXISTS `resources_groups`;
CREATE TABLE `resources_groups` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `ENABLE` varchar(36) DEFAULT NULL,
  `GROUP_ID` varchar(36) DEFAULT NULL,
  `RESOURCE_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resources_roles
-- ----------------------------
DROP TABLE IF EXISTS `resources_roles`;
CREATE TABLE `resources_roles` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `ENABLE` varchar(36) DEFAULT NULL,
  `RESOURCE_ID` varchar(36) DEFAULT NULL,
  `ROLE_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `ROLE_CODE` varchar(36) DEFAULT NULL,
  `ROLE_NAME` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for roles_groups
-- ----------------------------
DROP TABLE IF EXISTS `roles_groups`;
CREATE TABLE `roles_groups` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `GROUP_ID` varchar(36) DEFAULT NULL,
  `ROLE_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for url_mapping_meta
-- ----------------------------
DROP TABLE IF EXISTS `url_mapping_meta`;
CREATE TABLE `url_mapping_meta` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `APP_ID` varchar(36) DEFAULT NULL,
  `URL` varchar(36) DEFAULT NULL,
  `URL_ACTIVE` varchar(36) DEFAULT NULL,
  `URL_DESC` varchar(2000) DEFAULT NULL,
  `URL_NAME` varchar(36) DEFAULT NULL,
  `URL_TYPE` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `PASSWORD` varchar(36) DEFAULT NULL,
  `REGISTER_TIME` datetime DEFAULT NULL,
  `STATUS` varchar(36) DEFAULT NULL,
  `USERNAME` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users_extend
-- ----------------------------
DROP TABLE IF EXISTS `users_extend`;
CREATE TABLE `users_extend` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `NATURE_NAME` varchar(36) DEFAULT NULL,
  `USER_ID` varchar(36) DEFAULT NULL,
  `USER_IMAGE` varchar(36) DEFAULT NULL,
  `USER_NAME` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users_groups
-- ----------------------------
DROP TABLE IF EXISTS `users_groups`;
CREATE TABLE `users_groups` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `GROUP_ID` varchar(36) DEFAULT NULL,
  `USER_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for users_roles
-- ----------------------------
DROP TABLE IF EXISTS `users_roles`;
CREATE TABLE `users_roles` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `ROLE_ID` varchar(36) DEFAULT NULL,
  `USER_ID` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user_tracker
-- ----------------------------
DROP TABLE IF EXISTS `user_tracker`;
CREATE TABLE `user_tracker` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `IP` varchar(36) DEFAULT NULL,
  `LOGIN_CLIENT` varchar(36) DEFAULT NULL,
  `LOGIN_TIME` datetime DEFAULT NULL,
  `USERID` varchar(36) DEFAULT NULL,
  `USERNAME` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_param
-- ----------------------------
DROP TABLE IF EXISTS `sys_param`;
CREATE TABLE `sys_param` (
  `ID` varchar(36) NOT NULL,
  `CREATEID` varchar(36) DEFAULT NULL,
  `CREATETIME` datetime DEFAULT NULL,
  `DELETED` varchar(36) DEFAULT NULL,
  `UPDATEID` varchar(36) DEFAULT NULL,
  `UPDATETIME` datetime DEFAULT NULL,
  `VERSION` int(11) DEFAULT NULL,
  `_CODE` varchar(36) DEFAULT NULL,
  `DESCRIPTION` varchar(2000) DEFAULT NULL,
  `_VALUE` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
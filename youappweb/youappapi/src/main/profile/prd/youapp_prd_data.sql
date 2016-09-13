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
-- Table data for ...
-- ----------------------------

-- ----------------------------
-- Records of param_code
-- ----------------------------
INSERT INTO `param_code` VALUES ('098fdc5c-a84c-4b08-a59e-d6c978b1b2da', 'def-user-id-as-no-user-session', '2016-09-13 13:42:46', 'N', 'def-user-id-as-no-user-session', '2016-09-13 13:42:46', '1', 'RESTRICT', '受限的', '受限的', 'USER_STATUS');
INSERT INTO `param_code` VALUES ('5ef189c3-55e2-4bf3-852b-91053d63e014', 'def-user-id-as-no-user-session', '2016-09-13 13:42:06', 'N', 'def-user-id-as-no-user-session', '2016-09-13 13:42:06', '1', 'INACTIVE', '未激活', '未激活', 'USER_STATUS');
INSERT INTO `param_code` VALUES ('7f96b685-7a34-44a1-86a7-a0444d4d0dc3', 'def-user-id-as-no-user-session', '2016-09-13 13:40:21', 'N', 'def-user-id-as-no-user-session', '2016-09-13 13:40:21', '1', 'N', '未删除', '未删除', 'IS_DELETED');
INSERT INTO `param_code` VALUES ('d7a75d5a-8354-4e3a-b471-9f89505a76b2', 'def-user-id-as-no-user-session', '2016-09-13 13:41:46', 'N', 'def-user-id-as-no-user-session', '2016-09-13 13:41:46', '1', 'ACTIVE', '激活中', '激活中', 'USER_STATUS');
INSERT INTO `param_code` VALUES ('e81da134-c67f-4012-afeb-5c153b16d585', 'def-user-id-as-no-user-session', '2016-09-13 13:40:08', 'N', 'def-user-id-as-no-user-session', '2016-09-13 13:40:08', '1', 'Y', '已删除', '已删除', 'IS_DELETED');



-- ----------------------------
-- Records of param_type
-- ----------------------------
INSERT INTO `param_type` VALUES ('19cbc949-8da5-4d20-b46c-f39ed5c8b413', 'def-user-id-as-no-user-session', '2016-09-13 13:41:29', 'N', 'def-user-id-as-no-user-session', '2016-09-13 13:41:29', '1', 'USER_STATUS', '用户状态', '用户状态');
INSERT INTO `param_type` VALUES ('bf9ef3e5-1ba6-4d9f-bbb7-cf4072bcf070', 'def-user-id-as-no-user-session', '2016-09-13 13:39:43', 'N', 'def-user-id-as-no-user-session', '2016-09-13 13:39:43', '1', 'IS_DELETED', '是否删除', '是否删除');



-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('ff6788c1-7971-11e6-a821-00ff5eca04ae', 'YouAPP', '2016-09-13 13:22:14', 'N', 'YouAPP', '2016-09-13 13:22:14', '1', '所有没有其他角色的用户自动并入此角色', 'DEFAULT', '默认角色');
INSERT INTO `roles` VALUES ('ff726de2-7971-11e6-a821-00ff5eca04ae', 'YouAPP', '2016-09-13 13:22:14', 'N', 'YouAPP', '2016-09-13 13:22:14', '1', '此角色用户拥有最高权限', 'ADMIN', '管理员角色');



-- ----------------------------
-- Records of groups
-- ----------------------------
INSERT INTO `groups` VALUES ('ff79c0fd-7971-11e6-a821-00ff5eca04ae', 'YouAPP', '2016-09-13 13:22:14', 'N', 'YouAPP', '2016-09-13 13:22:14', '1', '不在其他组的用户自动并入此组', 'DEFAULT', '默认组');
INSERT INTO `groups` VALUES ('ff838e70-7971-11e6-a821-00ff5eca04ae', 'YouAPP', '2016-09-13 13:22:14', 'N', 'YouAPP', '2016-09-13 13:22:14', '1', '此组用户拥有最高权限', 'ADMIN', '管理员组');














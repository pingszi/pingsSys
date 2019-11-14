/*
 Navicat Premium Data Transfer

 Source Server         : sswms
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : www.pingssys.com:31001
 Source Schema         : pings_sys

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 14/11/2019 10:52:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_who` int(11) NOT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `edit_who` int(11) NULL DEFAULT NULL,
  `edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门编码',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `parent_id` int(11) NOT NULL DEFAULT -1 COMMENT '父节点，-1表示顶层节点',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_dept_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1, 1, '2019-01-30 15:24:52', NULL, '2019-01-30 15:34:14', 'xx_org', 'xx组织', NULL, -1);
INSERT INTO `sys_dept` VALUES (2, 1, '2019-01-30 15:31:27', 1, '2019-02-22 10:38:27', 'xx_dept', 'xx部门', 'xx', 1);

-- ----------------------------
-- Table structure for sys_right
-- ----------------------------
DROP TABLE IF EXISTS `sys_right`;
CREATE TABLE `sys_right`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_who` int(11) NOT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `edit_who` int(11) NULL DEFAULT NULL,
  `edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限编码',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `parent_id` int(11) NOT NULL DEFAULT -1 COMMENT '父节点，-1表示顶层节点',
  `type` tinyint(1) NOT NULL DEFAULT 3 COMMENT '类型，1：系统，2：菜单，3：权限',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_rights_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_right
-- ----------------------------
INSERT INTO `sys_right` VALUES (1, 1, '2019-01-30 11:00:37', 1, '2019-03-08 09:47:43', 'sys:user:list', '查看用户', '查看用户', 14, 3);
INSERT INTO `sys_right` VALUES (2, 1, '2019-02-22 14:37:54', 1, '2019-03-11 03:00:08', 'sys:user:save', '保存用户', '保存用户', 14, 3);
INSERT INTO `sys_right` VALUES (3, 1, '2019-02-22 14:38:12', NULL, '2019-03-08 08:22:03', 'sys:user:delete', '删除用户', NULL, 14, 3);
INSERT INTO `sys_right` VALUES (4, 1, '2019-02-22 14:38:47', NULL, '2019-03-08 08:22:06', 'sys:dept:save', '保存部门', NULL, 15, 3);
INSERT INTO `sys_right` VALUES (5, 1, '2019-02-22 14:39:14', NULL, '2019-03-08 08:22:09', 'sys:dept:delete', '删除部门', NULL, 15, 3);
INSERT INTO `sys_right` VALUES (6, 1, '2019-02-22 14:39:29', NULL, '2019-03-08 08:22:12', 'sys:dept:list', '查看部门', NULL, 15, 3);
INSERT INTO `sys_right` VALUES (7, 1, '2019-03-08 05:57:01', NULL, '2019-03-08 08:22:14', 'sys:role:save', '保存角色', NULL, 16, 3);
INSERT INTO `sys_right` VALUES (8, 1, '2019-03-08 05:57:01', NULL, '2019-03-08 08:22:16', 'sys:role:delete', '删除角色', NULL, 16, 3);
INSERT INTO `sys_right` VALUES (9, 1, '2019-03-08 05:57:01', NULL, '2019-03-08 08:22:19', 'sys:role:list', '查看角色', NULL, 16, 3);
INSERT INTO `sys_right` VALUES (10, 1, '2019-03-08 05:57:01', NULL, '2019-03-08 08:22:23', 'sys:right:save', '保存权限', NULL, 17, 3);
INSERT INTO `sys_right` VALUES (11, 1, '2019-03-08 05:57:01', NULL, '2019-03-08 08:22:25', 'sys:right:delete', '删除权限', NULL, 17, 3);
INSERT INTO `sys_right` VALUES (12, 1, '2019-03-08 05:57:01', NULL, '2019-03-08 08:22:28', 'sys:right:list', '查看权限', NULL, 17, 3);
INSERT INTO `sys_right` VALUES (13, 1, '2019-03-08 16:19:13', 1, NULL, 'sys', '系统管理', NULL, -1, 1);
INSERT INTO `sys_right` VALUES (14, 1, '2019-03-08 08:20:28', NULL, NULL, 'sys:user', '用户管理', NULL, 13, 2);
INSERT INTO `sys_right` VALUES (15, 1, '2019-03-08 08:21:02', NULL, NULL, 'sys:dept', '部门管理', NULL, 13, 2);
INSERT INTO `sys_right` VALUES (16, 1, '2019-03-08 08:21:21', NULL, NULL, 'sys:role', '角色管理', NULL, 13, 2);
INSERT INTO `sys_right` VALUES (17, 1, '2019-03-08 08:21:40', NULL, NULL, 'sys:right', '权限管理', NULL, 13, 2);
INSERT INTO `sys_right` VALUES (19, 1, '2019-03-11 03:15:39', NULL, NULL, 'sys:user:allotRole', '分配角色', NULL, 14, 3);
INSERT INTO `sys_right` VALUES (20, 1, '2019-03-13 01:40:40', NULL, NULL, 'sys:role:allotRight', '分配权限', NULL, 16, 3);
INSERT INTO `sys_right` VALUES (21, 1, '2019-03-08 16:19:13', 1, NULL, 'bill', '账单管理', NULL, -1, 1);
INSERT INTO `sys_right` VALUES (22, 1, '2019-03-26 07:27:08', NULL, NULL, 'bill:basData', '基础数据管理', NULL, 21, 2);
INSERT INTO `sys_right` VALUES (23, 1, '2019-01-30 11:00:37', 1, '2019-03-26 07:28:59', 'bill:basData:list', '查看基础数据', '', 22, 3);
INSERT INTO `sys_right` VALUES (24, 1, '2019-02-22 14:37:54', 1, '2019-03-26 07:28:57', 'bill:basData:save', '保存基础数据', '', 22, 3);
INSERT INTO `sys_right` VALUES (25, 1, '2019-02-22 14:38:12', NULL, '2019-03-08 08:22:03', 'bill:basData:delete', '删除基础数据', NULL, 22, 3);
INSERT INTO `sys_right` VALUES (26, 1, '2019-03-28 02:40:04', NULL, '2019-03-28 03:10:37', 'bill:debt', '欠款单管理', NULL, 21, 2);
INSERT INTO `sys_right` VALUES (27, 1, '2019-01-30 11:00:37', 1, '2019-03-28 03:10:53', 'bill:debt:list', '查看欠款单', '', 26, 3);
INSERT INTO `sys_right` VALUES (28, 1, '2019-02-22 14:37:54', 1, '2019-03-28 03:10:58', 'bill:debt:save', '保存欠款单', '', 26, 3);
INSERT INTO `sys_right` VALUES (29, 1, '2019-02-22 14:38:12', NULL, '2019-03-28 03:11:02', 'bill:debt:delete', '删除欠款单', NULL, 26, 3);
INSERT INTO `sys_right` VALUES (30, 1, '2019-03-28 02:40:04', NULL, '2019-03-28 03:11:13', 'bill:debtRefund', '还款单管理', NULL, 21, 2);
INSERT INTO `sys_right` VALUES (31, 1, '2019-01-30 11:00:37', 1, '2019-03-28 03:11:25', 'bill:debtRefund:list', '查看还款单', '', 30, 3);
INSERT INTO `sys_right` VALUES (32, 1, '2019-02-22 14:37:54', 1, '2019-03-28 03:11:23', 'bill:debtRefund:save', '保存还款单', '', 30, 3);
INSERT INTO `sys_right` VALUES (33, 1, '2019-02-22 14:38:12', NULL, '2019-03-28 03:11:21', 'bill:debtRefund:delete', '删除还款单', NULL, 30, 3);
INSERT INTO `sys_right` VALUES (34, 1, '2019-03-28 02:40:04', NULL, NULL, 'bill:expenseDetails', '消费明细管理', NULL, 21, 2);
INSERT INTO `sys_right` VALUES (35, 1, '2019-01-30 11:00:37', 1, '2019-03-26 07:28:59', 'bill:expenseDetails:list', '查看消费明细', '', 34, 3);
INSERT INTO `sys_right` VALUES (36, 1, '2019-02-22 14:37:54', 1, '2019-03-26 07:28:57', 'bill:expenseDetails:save', '保存消费明细', '', 34, 3);
INSERT INTO `sys_right` VALUES (37, 1, '2019-02-22 14:38:12', NULL, '2019-03-28 02:44:43', 'bill:expenseDetails:delete', '删除消费明细', NULL, 34, 3);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_who` int(11) NOT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `edit_who` int(11) NULL DEFAULT NULL,
  `edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_role_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 1, '2019-01-28 14:37:08', 1, '2019-03-08 07:36:32', 'admin', '超级管理员', '超级管理员');
INSERT INTO `sys_role` VALUES (2, 1, '2019-01-30 11:06:16', 1, '2019-03-08 07:35:52', 'user', '普通用户', '普通用户');

-- ----------------------------
-- Table structure for sys_role_right
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_right`;
CREATE TABLE `sys_role_right`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_who` int(11) NOT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `edit_who` int(11) NULL DEFAULT NULL,
  `edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  `right_id` int(11) NOT NULL COMMENT '权限编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 169 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_right
-- ----------------------------
INSERT INTO `sys_role_right` VALUES (161, 1, '2019-04-10 05:49:53', NULL, NULL, 2, 1);
INSERT INTO `sys_role_right` VALUES (162, 1, '2019-04-10 05:49:53', NULL, NULL, 2, 6);
INSERT INTO `sys_role_right` VALUES (163, 1, '2019-04-10 05:49:53', NULL, NULL, 2, 9);
INSERT INTO `sys_role_right` VALUES (164, 1, '2019-04-10 05:49:53', NULL, NULL, 2, 12);
INSERT INTO `sys_role_right` VALUES (165, 1, '2019-04-10 05:49:53', NULL, NULL, 2, 23);
INSERT INTO `sys_role_right` VALUES (166, 1, '2019-04-10 05:49:53', NULL, NULL, 2, 27);
INSERT INTO `sys_role_right` VALUES (167, 1, '2019-04-10 05:49:53', NULL, NULL, 2, 31);
INSERT INTO `sys_role_right` VALUES (168, 1, '2019-04-10 05:49:53', NULL, NULL, 2, 35);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_who` int(11) NOT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `edit_who` int(11) NULL DEFAULT NULL,
  `edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `user_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `password` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `sex` tinyint(4) NOT NULL DEFAULT 0 COMMENT '性别：0男性，1女性',
  `age` tinyint(4) NULL DEFAULT NULL COMMENT '年龄',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门编号',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `enabled` tinyint(4) NOT NULL DEFAULT 1 COMMENT '启用：0未启用，1已启用',
  `del_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标记：0未删除，1已删除',
  `login_ip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录IP',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_user_user_name`(`user_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 1, '2019-01-08 10:01:28', 1, '2019-03-28 03:17:24', 'pings', 'pings', 'e10adc3949ba59abbe56e057f20f883e', 0, 22, 2, '13333333333', '2019-01-17', 1, 0, NULL);
INSERT INTO `sys_user` VALUES (2, 1, '2019-01-30 11:05:21', 1, '2019-03-29 07:46:54', 'test', 'test', 'e10adc3949ba59abbe56e057f20f883e', 1, 18, 2, '11111111111', '2019-02-07', 1, 0, NULL);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `add_who` int(11) NOT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `edit_who` int(11) NULL DEFAULT NULL,
  `edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (17, 1, '2019-03-13 00:54:44', NULL, NULL, 1, 1);
INSERT INTO `sys_user_role` VALUES (32, 1, '2019-04-10 05:50:34', NULL, NULL, 2, 2);

SET FOREIGN_KEY_CHECKS = 1;

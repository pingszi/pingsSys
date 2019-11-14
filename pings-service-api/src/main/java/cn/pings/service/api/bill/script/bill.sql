/*
 Navicat Premium Data Transfer

 Source Server         : sswms
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : www.pingssys.com:31001
 Source Schema         : pings_bill

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 14/11/2019 10:52:21
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bill_bas_data
-- ----------------------------
DROP TABLE IF EXISTS `bill_bas_data`;
CREATE TABLE `bill_bas_data`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sort` int(11) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `add_who` int(11) NULL DEFAULT NULL,
  `edit_who` int(11) NULL DEFAULT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `type_desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `bill_bas_data_code_d7e750c1_uniq`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bill_bas_data
-- ----------------------------
INSERT INTO `bill_bas_data` VALUES (1, 'DEBT_STATUS_A', '未偿还', 'DEBT_STATUS', 1, NULL, NULL, NULL, '2018-04-09 00:00:00', '2018-04-09 00:00:00', '欠款状态');
INSERT INTO `bill_bas_data` VALUES (2, 'DEBT_STATUS_B', '部分偿还', 'DEBT_STATUS', 2, NULL, NULL, NULL, '2018-04-09 00:00:00', '2018-04-09 00:00:00', '欠款状态');
INSERT INTO `bill_bas_data` VALUES (3, 'DEBT_STATUS_C', '已偿还', 'DEBT_STATUS', 3, NULL, NULL, NULL, '2018-04-09 00:00:00', '2018-04-09 00:00:00', '欠款状态');
INSERT INTO `bill_bas_data` VALUES (5, 'EXPENSE_TYPE_A', '餐饮', 'EXPENSE', 1, NULL, NULL, NULL, '2018-04-10 00:00:00', '2018-04-10 00:00:00', '消费类型');
INSERT INTO `bill_bas_data` VALUES (6, 'EXPENSE_TYPE_B', '零食', 'EXPENSE', 2, NULL, NULL, NULL, '2018-04-10 00:00:00', '2018-04-10 00:00:00', '消费类型');
INSERT INTO `bill_bas_data` VALUES (7, 'EXPENSE_TYPE_C', '交通', 'EXPENSE', 3, NULL, NULL, NULL, '2018-04-10 00:00:00', '2018-04-10 00:00:00', '消费类型');
INSERT INTO `bill_bas_data` VALUES (8, 'EXPENSE_TYPE_D', '家居', 'EXPENSE', 4, NULL, NULL, NULL, '2018-04-10 00:00:00', '2018-04-10 00:00:00', '消费类型');
INSERT INTO `bill_bas_data` VALUES (9, 'EXPENSE_TYPE_E', '宝宝', 'EXPENSE', 5, NULL, NULL, NULL, '2018-04-12 00:00:00', '2018-04-13 00:00:00', '消费类型');
INSERT INTO `bill_bas_data` VALUES (10, 'EXPENSE_TYPE_F', '通信', 'EXPENSE', 6, NULL, NULL, NULL, '2018-05-03 00:00:00', '2018-05-03 00:00:00', '消费类型');
INSERT INTO `bill_bas_data` VALUES (11, 'EXPENSE_TYPE_G', '其它', 'EXPENSE', 7, '', NULL, 1, '2018-05-08 00:00:00', '2019-03-26 08:39:24', '消费类型');

-- ----------------------------
-- Table structure for bill_debt
-- ----------------------------
DROP TABLE IF EXISTS `bill_debt`;
CREATE TABLE `bill_debt`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `value` decimal(8, 2) NOT NULL,
  `refund_date` date NULL DEFAULT NULL,
  `add_who` int(11) NULL DEFAULT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `edit_who` int(11) NULL DEFAULT NULL,
  `edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `status_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bill_bus_debt_status_id_7a9837a7_fk_bill_bas_data_id`(`status_id`) USING BTREE,
  CONSTRAINT `bill_bus_debt_status_id_7a9837a7_fk_bill_bas_data_id` FOREIGN KEY (`status_id`) REFERENCES `bill_bas_data` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bill_debt
-- ----------------------------
INSERT INTO `bill_debt` VALUES (1, '测试', 10000.00, '2017-01-01', NULL, '2018-04-09 00:00:00', NULL, '2018-04-09 00:00:00', 3);
INSERT INTO `bill_debt` VALUES (21, '欠款项1', 8888.88, '2019-03-20', 1, '2019-03-28 06:47:07', 1, '2019-03-28 06:47:15', 2);

-- ----------------------------
-- Table structure for bill_debt_refund
-- ----------------------------
DROP TABLE IF EXISTS `bill_debt_refund`;
CREATE TABLE `bill_debt_refund`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `value` decimal(8, 2) NOT NULL,
  `refund_date` date NOT NULL,
  `add_who` int(11) NULL DEFAULT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `edit_who` int(11) NULL DEFAULT NULL,
  `edit_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `debt_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bill_bus_debt_refund_debt_id_0c18fc13_fk_bill_bus_debt_id`(`debt_id`) USING BTREE,
  CONSTRAINT `bill_bus_debt_refund_debt_id_0c18fc13_fk_bill_bus_debt_id` FOREIGN KEY (`debt_id`) REFERENCES `bill_debt` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 55 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bill_debt_refund
-- ----------------------------
INSERT INTO `bill_debt_refund` VALUES (53, '测试还款1', 98.88, '2019-03-25', 1, '2019-03-28 09:08:36', 1, '2019-03-28 09:08:50', 21);

-- ----------------------------
-- Table structure for bill_expense_details
-- ----------------------------
DROP TABLE IF EXISTS `bill_expense_details`;
CREATE TABLE `bill_expense_details`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `value` decimal(8, 2) NOT NULL,
  `expense_date` date NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `add_who` int(11) NULL DEFAULT NULL,
  `add_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `edit_who` int(11) NULL DEFAULT NULL,
  `edit_time` datetime(0) NULL DEFAULT NULL,
  `type_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bill_bus_expense_details_type_id_1c4d0264_fk_bill_bas_data_id`(`type_id`) USING BTREE,
  CONSTRAINT `bill_bus_expense_details_type_id_1c4d0264_fk_bill_bas_data_id` FOREIGN KEY (`type_id`) REFERENCES `bill_bas_data` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 669 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bill_expense_details
-- ----------------------------
INSERT INTO `bill_expense_details` VALUES (666, '测试1', 6.00, '2019-03-20', NULL, NULL, '2019-03-20 00:00:00', NULL, '2019-03-20 00:00:00', 5);
INSERT INTO `bill_expense_details` VALUES (667, '测试2', 100.00, '2019-03-19', NULL, NULL, '2019-03-20 00:00:00', NULL, '2019-03-20 00:00:00', 7);

SET FOREIGN_KEY_CHECKS = 1;

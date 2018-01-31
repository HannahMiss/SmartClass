/*
 Navicat Premium Data Transfer

 Source Server         : MobileClassDB
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : smartclasstest

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 31/01/2018 20:46:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '内部id',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `timerCreated` datetime(0) NULL DEFAULT NULL,
  `timerModified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1001 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES (1000, 'admin', '123456', '2018-01-22 16:15:47', '2018-01-22 16:16:19');

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `courseName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `teacherId` smallint(6) NOT NULL,
  `checkinFlag` tinyint(1) NOT NULL,
  `answerFlag` tinyint(1) NOT NULL,
  `timeCreated` datetime(0) NULL DEFAULT NULL,
  `timeModified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tid`(`teacherId`) USING BTREE,
  CONSTRAINT `tid` FOREIGN KEY (`teacherId`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1067 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1059, '计算机网络d班', 1030, 0, 0, NULL, NULL);
INSERT INTO `course` VALUES (1063, '计算机网络a班', 1030, 0, 0, NULL, NULL);
INSERT INTO `course` VALUES (1065, '计算机网络b班', 1030, 0, 0, NULL, NULL);
INSERT INTO `course` VALUES (1066, '计算机网络c班', 1030, 0, 0, NULL, NULL);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `timerCreated` datetime(0) NULL DEFAULT NULL,
  `timerModified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `studentcode`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1026 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1023, '10001', '小王', '123456', NULL, NULL);
INSERT INTO `student` VALUES (1024, '10002', '小王2', '123456', NULL, NULL);
INSERT INTO `student` VALUES (1025, '10003', '老王3', '123456', NULL, NULL);

-- ----------------------------
-- Table structure for student_course
-- ----------------------------
DROP TABLE IF EXISTS `student_course`;
CREATE TABLE `student_course`  (
  `studentId` int(10) NOT NULL,
  `courseId` smallint(6) NOT NULL,
  PRIMARY KEY (`studentId`, `courseId`) USING BTREE,
  INDEX `couseid`(`courseId`) USING BTREE,
  CONSTRAINT `course_id` FOREIGN KEY (`courseId`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `student_id` FOREIGN KEY (`studentId`) REFERENCES `student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_course
-- ----------------------------
INSERT INTO `student_course` VALUES (1023, 1065);
INSERT INTO `student_course` VALUES (1024, 1066);
INSERT INTO `student_course` VALUES (1025, 1066);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `timeCreated` datetime(0) NULL DEFAULT NULL,
  `timeModified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `tcode`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1032 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1030, '20171007', 'dai', '111100', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;

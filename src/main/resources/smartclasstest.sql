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

 Date: 21/02/2018 16:33:38
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
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`  (
  `courseId` smallint(6) NOT NULL,
  `studentCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `opt` smallint(6) NOT NULL COMMENT '0:代表未选择；1,2,3,4分别代表A,B,C,D',
  PRIMARY KEY (`courseId`, `studentCode`) USING BTREE,
  INDEX `sCode`(`studentCode`) USING BTREE,
  CONSTRAINT `cId` FOREIGN KEY (`courseId`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sCode` FOREIGN KEY (`studentCode`) REFERENCES `student` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for communication
-- ----------------------------
DROP TABLE IF EXISTS `communication`;
CREATE TABLE `communication`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `courseId` smallint(6) NOT NULL,
  `studentCode` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `flag` tinyint(4) NOT NULL COMMENT '0.代表这条记录是问题。1.代表这条记录是反馈。',
  `descr` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `timeCreated` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `answered` tinyint(4) NOT NULL COMMENT '0:代表未回答。1:代表问题已经回答',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `comcid`(`courseId`) USING BTREE,
  INDEX `comscode`(`studentCode`) USING BTREE,
  CONSTRAINT `comcid` FOREIGN KEY (`courseId`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comscode` FOREIGN KEY (`studentCode`) REFERENCES `student` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` smallint(6) NOT NULL AUTO_INCREMENT,
  `courseName` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `teacherId` smallint(6) NOT NULL,
  `checkinFlag` tinyint(1) NOT NULL,
  `checkinTime` smallint(6) NOT NULL COMMENT '签到次数。',
  `answerFlag` tinyint(1) NOT NULL,
  `timeCreated` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `timeModified` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `checkinDate` date NOT NULL COMMENT '签到的日期',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `tid`(`teacherId`) USING BTREE,
  CONSTRAINT `tid` FOREIGN KEY (`teacherId`) REFERENCES `teacher` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1072 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for signinfo
-- ----------------------------
DROP TABLE IF EXISTS `signinfo`;
CREATE TABLE `signinfo`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `courseId` smallint(6) NOT NULL,
  `studentCode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `times` smallint(6) NOT NULL COMMENT '签到次数：从1开始',
  `timeCreated` datetime(0) NOT NULL COMMENT '签到的时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `signcid`(`courseId`) USING BTREE,
  INDEX `signscode`(`studentCode`) USING BTREE,
  CONSTRAINT `signcid` FOREIGN KEY (`courseId`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `signscode` FOREIGN KEY (`studentCode`) REFERENCES `student` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 1035 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

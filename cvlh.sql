/*
Navicat MySQL Data Transfer

Source Server         : hzau
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : cvlh

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2017-02-25 11:04:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for douban_item_comment
-- ----------------------------
DROP TABLE IF EXISTS `douban_item_comment`;
CREATE TABLE `douban_item_comment` (
  `comment_id` varchar(8) NOT NULL,
  `username` varchar(30) NOT NULL,
  `star` int(1) DEFAULT NULL,
  `upvote` int(6) DEFAULT NULL,
  `downvote` int(6) DEFAULT NULL,
  `commentDate` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `content` varchar(255) DEFAULT NULL,
  `digest` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

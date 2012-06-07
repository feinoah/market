/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.241
Source Server Version : 50162
Source Host           : 192.168.0.241:3306
Source Database       : market

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2012-06-07 18:21:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_account_info`
-- ----------------------------
DROP TABLE IF EXISTS `t_account_info`;
CREATE TABLE `t_account_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL COMMENT '邮箱',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(4) NOT NULL DEFAULT '0' COMMENT '性别 0：女 1：男 2：保密',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `photo` varchar(200) DEFAULT NULL COMMENT '头像',
  `balance` double DEFAULT '0' COMMENT '余额',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证',
  `office_phone` varchar(18) DEFAULT NULL COMMENT '办公电话',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `address` varchar(200) DEFAULT NULL COMMENT '联系地址',
  `last_login_ip` varchar(18) DEFAULT NULL COMMENT '最后登录Ip',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态：0 停用；0x1 启用；0x2：禁止登录（密码错误次数过多，一定时间内禁止登录）；0x4：封号',
  `update_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_t_account_info_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_account_info
-- ----------------------------
INSERT INTO `t_account_info` VALUES ('1', 'test@test.com', '11111', 'test', '1', '2012-05-22', null, '0', null, null, null, null, null, null, null, '1', '2012-05-22 16:47:10', '2012-05-22 10:21:03');
INSERT INTO `t_account_info` VALUES ('2', 'test2@test.com', '11111', 'test2', '0', null, null, '0', null, null, null, null, null, null, null, '1', '2012-05-22 16:47:05', '2012-05-22 11:19:22');
INSERT INTO `t_account_info` VALUES ('3', 'dfg@jkhg.com', 'fcaab47bdc1445ab75dddc6aaac4b18e', 'dfbb', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-04 12:02:06', '2012-06-04 12:02:06');
INSERT INTO `t_account_info` VALUES ('5', '1234@1234.com', 'd01e82888af005f9ec28736bc66ad931', 'qerty', '2', null, null, '0', null, null, null, null, null, '127.0.0.1', '2012-06-07 18:04:15', '1', '2012-06-07 18:04:15', '2012-06-05 09:43:55');
INSERT INTO `t_account_info` VALUES ('6', 'teste@ttt.com', 'edd8ffc3175223e569f99d72674e5aef', 'ddddddddd', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-05 11:44:51', '2012-06-05 11:44:51');
INSERT INTO `t_account_info` VALUES ('7', 'gerger@123.com', '608e4ab304531ecda3415d729cc8c51f', 'dfgewg', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-05 11:47:15', '2012-06-05 11:47:15');
INSERT INTO `t_account_info` VALUES ('8', '123@123.com', '5dc85bf1a3761c21f80a7012264ac4f1', '1234', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-05 11:55:03', '2012-06-05 11:55:03');
INSERT INTO `t_account_info` VALUES ('9', 'test4@test.com', 'a63706544e8573c60c1ea5a0b9fc149a', 'test4', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-06 10:45:31', '2012-06-06 10:45:31');
INSERT INTO `t_account_info` VALUES ('10', 'test5@test.com', '6ba50c4bbbd214aec4b5f2f5cf601da2', 'test5', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-06 10:46:58', '2012-06-06 10:46:58');
INSERT INTO `t_account_info` VALUES ('11', 'test6@test.com', '69af769255ae7d6450dd41bbfc1aa636', 'test6@test.com', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-06 10:50:26', '2012-06-06 10:50:26');
INSERT INTO `t_account_info` VALUES ('12', 'test7@test.com', 'a4fd2f36577177e5e411e31a5447e885', 'test7@test.com', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-06 11:04:11', '2012-06-06 11:04:11');
INSERT INTO `t_account_info` VALUES ('13', 'test8@test.com', '3b55305200aa3b843dd6b0c7793ea963', 'test8@test.com', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-06 11:06:40', '2012-06-06 11:06:40');
INSERT INTO `t_account_info` VALUES ('14', 'test9@test.com', 'a82186e2e49c4723032a896bbc2b2c58', 'test9@test.com', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-06 11:09:04', '2012-06-06 11:09:04');
INSERT INTO `t_account_info` VALUES ('15', 'test10@test.com', '2c763827a451cb15f6aa8b1241b1cd80', 'test10@test.com', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-06 11:10:05', '2012-06-06 11:10:05');
INSERT INTO `t_account_info` VALUES ('16', 'test11@test.com', '01599c367c6ceb8d17556f93d9bb2755', 'test11@test.com', '2', null, null, '0', null, null, null, null, null, '127.0.0.1', '2012-06-07 15:15:22', '1', '2012-06-07 15:15:25', '2012-06-06 11:12:07');
INSERT INTO `t_account_info` VALUES ('17', 'test12@test.com', 'e53b4846b2b263d24abc60a93e3a6c1a', 'test12@test.com', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-06 11:16:55', '2012-06-06 11:16:55');
INSERT INTO `t_account_info` VALUES ('18', 'test13@test.com', '8bb62161846c64646f970ab0a5ab6c15', 'test13@test.com', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-06 11:20:16', '2012-06-06 11:20:16');
INSERT INTO `t_account_info` VALUES ('19', 'test14@test.com', 'f763e0bbbe433578ede51f18f75af485', 'test14@test.com', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-06 11:29:48', '2012-06-06 11:29:48');
INSERT INTO `t_account_info` VALUES ('20', 'test15@test.com', 'd050725f3e37266077e1f2083128f124', 'test15@test.com', '2', null, null, '0', null, null, null, null, null, null, null, '1', '2012-06-06 11:50:00', '2012-06-06 11:50:00');
INSERT INTO `t_account_info` VALUES ('21', 'xiegc@carit.com.cn', 'eb4475fa6839e3253bc5ade5bbdd2a72', '谢庚才', '2', null, null, '0', null, null, null, null, null, '127.0.0.1', '2012-06-07 09:03:46', '1', '2012-06-07 09:03:46', '2012-06-06 16:34:00');

-- ----------------------------
-- Table structure for `t_app_catalog`
-- ----------------------------
DROP TABLE IF EXISTS `t_app_catalog`;
CREATE TABLE `t_app_catalog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL COMMENT '名称',
  `en_name` varchar(20) NOT NULL,
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  `en_description` varchar(100) DEFAULT NULL,
  `display_index` int(11) NOT NULL COMMENT '显示顺序',
  `status` tinyint(4) NOT NULL COMMENT '状态：0 停用， 1 启用',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_app_catalog
-- ----------------------------
INSERT INTO `t_app_catalog` VALUES ('2', '车载', 'car', '车载应用 导航  地方', 'car navigater 爱的', '6', '1', '2012-05-20 12:25:46', '2012-06-05 15:32:29');
INSERT INTO `t_app_catalog` VALUES ('23', '游戏', 'game', '游戏', 'game', '2', '1', '2012-05-30 15:06:58', '2012-05-30 15:06:58');
INSERT INTO `t_app_catalog` VALUES ('25', '娱乐', 'recreation', '娱乐', 'recreation', '4', '1', '2012-05-30 15:11:54', '2012-05-30 15:11:54');
INSERT INTO `t_app_catalog` VALUES ('27', '工具', 'TOOLS', '工具', 'TOOLS', '4', '1', '2012-06-06 14:15:47', '2012-06-06 14:15:47');

-- ----------------------------
-- Table structure for `t_app_comment`
-- ----------------------------
DROP TABLE IF EXISTS `t_app_comment`;
CREATE TABLE `t_app_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用Id',
  `user_id` int(11) NOT NULL COMMENT '用户Id',
  `grade` int(11) DEFAULT NULL COMMENT '评分：1~5分',
  `comment` varchar(200) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态：0 停用， 1 启用',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_t_user_comment_app_id` (`app_id`) USING BTREE,
  KEY `idx_t_user_comment_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_app_comment
-- ----------------------------
INSERT INTO `t_app_comment` VALUES ('1', '1', '5', '6', 'sfghhjj', '1', '2012-06-07 14:01:58', '2012-06-07 14:01:58');
INSERT INTO `t_app_comment` VALUES ('2', '1', '5', '6', 'yhvh', '1', '2012-06-07 14:03:08', '2012-06-07 14:03:08');
INSERT INTO `t_app_comment` VALUES ('3', '1', '5', '6', 'dcbnn', '1', '2012-06-07 14:06:01', '2012-06-07 14:06:01');
INSERT INTO `t_app_comment` VALUES ('4', '1', '5', '4', 'dcbbn', '1', '2012-06-07 14:23:11', '2012-06-07 14:23:11');
INSERT INTO `t_app_comment` VALUES ('5', '1', '5', '4', 'dcgh', '1', '2012-06-07 14:53:14', '2012-06-07 14:53:14');
INSERT INTO `t_app_comment` VALUES ('6', '1', '5', '6', 'fvn', '1', '2012-06-07 14:59:50', '2012-06-07 14:59:50');
INSERT INTO `t_app_comment` VALUES ('7', '1', '5', '2', 'gbnnn', '1', '2012-06-07 15:01:55', '2012-06-07 15:01:55');
INSERT INTO `t_app_comment` VALUES ('8', '1', '5', '6', 'fbnn', '1', '2012-06-07 15:04:10', '2012-06-07 15:04:10');
INSERT INTO `t_app_comment` VALUES ('9', '1', '5', '6', 'vvbjjj', '1', '2012-06-07 15:06:54', '2012-06-07 15:06:54');
INSERT INTO `t_app_comment` VALUES ('10', '1', '5', '6', 'gbbn', '1', '2012-06-07 15:10:43', '2012-06-07 15:10:43');
INSERT INTO `t_app_comment` VALUES ('11', '1', '5', '6', 'c+nnmm', '1', '2012-06-07 15:12:12', '2012-06-07 15:12:12');
INSERT INTO `t_app_comment` VALUES ('12', '1', '5', '4', 'dcbbn', '1', '2012-06-07 15:13:56', '2012-06-07 15:13:56');
INSERT INTO `t_app_comment` VALUES ('13', '1', '5', '6', 'xcvhh', '1', '2012-06-07 15:15:27', '2012-06-07 15:15:27');
INSERT INTO `t_app_comment` VALUES ('14', '1', '5', '6', 'fcbnnkk', '1', '2012-06-07 15:16:07', '2012-06-07 15:16:07');
INSERT INTO `t_app_comment` VALUES ('15', '1', '5', '6', 'dcnn', '1', '2012-06-07 15:17:20', '2012-06-07 15:17:20');
INSERT INTO `t_app_comment` VALUES ('16', '1', '5', '6', 'cbbkk', '1', '2012-06-07 15:18:16', '2012-06-07 15:18:16');
INSERT INTO `t_app_comment` VALUES ('17', '1', '5', '6', 'dvb', '1', '2012-06-07 15:18:51', '2012-06-07 15:18:51');
INSERT INTO `t_app_comment` VALUES ('18', '1', '5', '8', 'cvbbn', '1', '2012-06-07 15:19:15', '2012-06-07 15:19:15');
INSERT INTO `t_app_comment` VALUES ('19', '1', '5', '6', 'dfbnnm', '1', '2012-06-07 15:21:57', '2012-06-07 15:21:57');
INSERT INTO `t_app_comment` VALUES ('20', '1', '5', '6', 'gfdr', '1', '2012-06-07 15:22:58', '2012-06-07 15:22:58');
INSERT INTO `t_app_comment` VALUES ('21', '1', '5', '6', 'scvb', '1', '2012-06-07 15:40:22', '2012-06-07 15:40:22');
INSERT INTO `t_app_comment` VALUES ('22', '1', '5', '6', 'dfb', '1', '2012-06-07 15:41:28', '2012-06-07 15:41:28');
INSERT INTO `t_app_comment` VALUES ('23', '1', '5', '6', 'gghbn', '1', '2012-06-07 15:57:25', '2012-06-07 15:57:25');
INSERT INTO `t_app_comment` VALUES ('24', '1', '5', '6', 'dfbbn', '1', '2012-06-07 16:08:01', '2012-06-07 16:08:01');
INSERT INTO `t_app_comment` VALUES ('25', '1', '5', '6', 'dfvb.', '1', '2012-06-07 16:08:51', '2012-06-07 16:08:51');
INSERT INTO `t_app_comment` VALUES ('26', '1', '5', '6', 'cbnnkk', '1', '2012-06-07 16:15:31', '2012-06-07 16:15:31');
INSERT INTO `t_app_comment` VALUES ('27', '1', '5', '4', 'efvb', '1', '2012-06-07 16:15:57', '2012-06-07 16:15:57');
INSERT INTO `t_app_comment` VALUES ('28', '1', '5', '6', 'dcbb', '1', '2012-06-07 16:17:21', '2012-06-07 16:17:21');
INSERT INTO `t_app_comment` VALUES ('29', '1', '5', '6', 'dfhjj', '1', '2012-06-07 16:18:02', '2012-06-07 16:18:02');
INSERT INTO `t_app_comment` VALUES ('30', '1', '5', '6', 'rcbnnn', '1', '2012-06-07 16:22:24', '2012-06-07 16:22:24');
INSERT INTO `t_app_comment` VALUES ('31', '1', '5', '6', 'dcvb', '1', '2012-06-07 16:26:23', '2012-06-07 16:26:23');
INSERT INTO `t_app_comment` VALUES ('32', '1', '5', '8', 'ghhgff', '1', '2012-06-07 16:33:12', '2012-06-07 16:33:12');
INSERT INTO `t_app_comment` VALUES ('33', '1', '5', '4', 'dvbjm', '1', '2012-06-07 16:35:41', '2012-06-07 16:35:41');

-- ----------------------------
-- Table structure for `t_app_download_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_app_download_log`;
CREATE TABLE `t_app_download_log` (
  `id` int(11) NOT NULL,
  `account_id` int(11) NOT NULL COMMENT '帐号Id',
  `app_id` int(11) NOT NULL COMMENT '应用Id',
  `download_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_app_down_log_account_id` (`account_id`),
  KEY `idx_app_down_log_app_id` (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_app_download_log
-- ----------------------------

-- ----------------------------
-- Table structure for `t_app_version_file`
-- ----------------------------
DROP TABLE IF EXISTS `t_app_version_file`;
CREATE TABLE `t_app_version_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用Id',
  `version` varchar(10) NOT NULL COMMENT '版本',
  `size` varchar(10) NOT NULL COMMENT '应用大小',
  `file_path` varchar(100) NOT NULL COMMENT '应用程序路径',
  `new_features` varchar(500) DEFAULT NULL COMMENT '新功能',
  `en_new_features` varchar(500) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态：0 停用， 1 启用',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_app_version_file
-- ----------------------------
INSERT INTO `t_app_version_file` VALUES ('1', '1', '1', '', 'apks/1111_614704814284313.apk', 'dggggggggg', 'ddddddddddddddd', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('2', '2', '1', '', 'apks/1234_711821344343469.apk', 'dgdgdsgd', 'agfdgafg', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('3', '2', '2', '', 'apks/1234_711864737136519.apk', 'dgagdgfdgdfh', 'daghahafhg', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('4', '2', '3', '', 'apks/1234_711876813878233.apk', 'dga', '3654', '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('5', '3', '1', '', 'apks/2222222222_614497885253412.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('6', '4', '', '', 'apks/2222_614731261646763.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('7', '5', '1', '', 'apks/22_614661134354973.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('8', '5', '2', '', 'apks/22_614661134354973.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('9', '5', '3', '', 'apks/22_614661134354973.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('10', '5', '4', '', 'apks/22_614661134354973.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('11', '5', '5', '', 'apks/22_614661134354973.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('12', '6', '1', '', 'apks/ad_612874194963298.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('13', '6', '2', '', 'apks/ad_612874194963298.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('14', '6', '3', '', 'apks/ad_612874194963298.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('15', '7', '1', '', 'apks/adf_2.6_195602929682216.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('16', '7', '2', '', 'apks/adf_2.6_195602929682216.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('17', '8', '1', '15M', 'apks/AngryBirds_2.0_110234698004946.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('18', '8', '2', '15M', 'apks/AngryBirds_2.0_110234698004946.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('19', '9', '1', '', 'apks/bb_613003026111525.apk3086668.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('20', '9', '2', '', 'apks/bb_613003026111525.apk3086668.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('21', '9', '3', '', 'apks/bb_613003026111525.apk3086668.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('22', '9', '4', '', 'apks/bb_613003026111525.apk3086668.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('23', '10', '1', '', 'apks/adf_2.6_195602929682216.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('24', '10', '2', '', 'apks/adf_2.6_195602929682216.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_app_version_file` VALUES ('25', '10', '3', '', 'apks/adf_2.6_195602929682216.apk', null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');

-- ----------------------------
-- Table structure for `t_application`
-- ----------------------------
DROP TABLE IF EXISTS `t_application`;
CREATE TABLE `t_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(50) NOT NULL COMMENT '应用名称',
  `en_name` varchar(50) NOT NULL COMMENT '显示名称',
  `version` varchar(10) DEFAULT NULL COMMENT '版本',
  `developer` varchar(100) DEFAULT NULL COMMENT '开发商',
  `big_icon` varchar(100) DEFAULT NULL COMMENT '大图表',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `catalog_id` int(11) NOT NULL COMMENT '应用类型',
  `size` varchar(10) DEFAULT NULL COMMENT '应用大小',
  `app_file_path` varchar(100) DEFAULT NULL COMMENT '应用程序路径',
  `platform` varchar(20) NOT NULL COMMENT '应用平台',
  `support_languages` int(11) DEFAULT '0' COMMENT '语言支持:0 中文；1 英语；2 中英双语',
  `price` double DEFAULT '0' COMMENT '应用价格（0为免费）',
  `down_count` int(11) DEFAULT '0' COMMENT '下载次数',
  `app_level` int(11) DEFAULT '0' COMMENT '应用分级',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `en_description` varchar(500) DEFAULT NULL,
  `en_permission_desc` varchar(500) DEFAULT NULL,
  `permission_desc` varchar(500) DEFAULT NULL COMMENT '权限描述',
  `images` varchar(500) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态：0 停用， 1 启用',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_application
-- ----------------------------
INSERT INTO `t_application` VALUES ('1', '1111', '1111', '1', '1111', null, null, '2', '4', 'apks/1111_614704814284313.apk', 'android 1.5', '0', '0', '0', '1', '11111111', '11111111111', '11111111', '111111111', null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('2', '1234', '1234', '3.0', '1234', null, null, '2', '4', 'apks/1234_711876813878233.apk', 'android 1.6', '0', '0', '0', '1', '1231312', '2122113222', '1231565', '12312312', null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('3', '22222222', '22222222', '3', '3333', null, null, '2', null, 'apks/2222222222_614497885253412.apk', '', '0', '0', '0', '3', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-06-06 15:00:10');
INSERT INTO `t_application` VALUES ('4', '2222', '2222', '2', '22222', null, null, '25', null, 'apks/2222_614731261646763.apk', '', '0', '0', '0', '1', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-06-06 15:00:30');
INSERT INTO `t_application` VALUES ('5', '22', '22', '2', '222222', null, null, '4', null, 'apks/22_614661134354973.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('6', 'ad', 'ad', '2', 'ad', null, null, '25', null, 'apks/ad_612874194963298.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('7', 'adf', 'adf', '2.6', 'adf', null, null, '0', null, 'apks/adf_2.6_195602929682216.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('8', 'AngryBirds', 'AngryBirds', '2.0', 'AngryBirds', null, null, '25', null, 'apks/AngryBirds_2.0_110234698004946.apk', '', '0', '0', '0', '1', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-06-06 15:01:09');
INSERT INTO `t_application` VALUES ('9', 'bb', 'bb', '2.6', 'bb', null, null, '0', null, 'apks/bb_613003026111525.apk3086668.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('10', 'feng', 'feng', '2', 'fe', null, null, '0', null, 'apks/adf_2.6_195602929682216.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('11', 'one', 'one', '1', 'one', null, null, '2', null, 'apks/first one_612257998373383.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('12', 'gabe', 'gabe', '1', 'gadg', null, null, '25', null, 'apks/gabe_612934052714399.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('13', 'GPSTEST', 'GPSTEST', 'V2.3', 'GPSTEST', 'icons/GPSTEST_261648145226297.png', 'icons/GPSTEST_261648145226297.png', '0', null, 'apks/GPSTEST_V_2.3_195120658603526.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('14', 'jack', 'jack', '1', 'jack', null, null, '0', null, 'apks/jack_627414450853504.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('15', 'qidao', 'qidao', 'V2.0', 'qidao', null, null, '0', null, 'apks/qidao_608991699409862.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('16', 'sff sadfs', 'sff sadfs', '2', null, null, null, '0', null, 'apks/sff sadfs_613069686337991.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('17', '新浪微博', 'Sina Weibo', '3.0', '新浪科技', null, null, '25', '4M', 'apks/Sina Weibo_2.6_195401399047199.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('18', 'Test', 'test', null, null, null, null, '0', null, 'apks/TEST_623845313279334.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('19', 'tate', 'tate', '2.3', null, null, null, '0', null, 'apks/tete_2.3_195073044423275.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('20', 'xinlang', 'xinlang', '2', 'xinlang', null, null, '0', null, 'apks/xinlang_608840954201601.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');
INSERT INTO `t_application` VALUES ('21', 'first one', 'first one', '1', null, null, null, '0', null, 'apks/first one_612257998373383.apk', '', '0', '0', '0', '0', null, null, null, null, null, '1', '2012-05-08 11:53:35', '2012-05-08 11:53:35');

-- ----------------------------
-- Table structure for `t_base_field`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_field`;
CREATE TABLE `t_base_field` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field` varchar(20) NOT NULL,
  `field_name` varchar(30) NOT NULL COMMENT '字段中文名称',
  `field_value` int(11) NOT NULL COMMENT '字段值',
  `display_value` varchar(20) NOT NULL COMMENT '显示值',
  `enabled` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否启用 1：启用；0：停用',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sort` int(11) NOT NULL COMMENT '排序',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_t_base_field_field` (`field`),
  UNIQUE KEY `idx_t_base_field_field_name` (`field_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_base_field
-- ----------------------------

-- ----------------------------
-- Table structure for `t_base_module`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_module`;
CREATE TABLE `t_base_module` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(30) NOT NULL COMMENT '模块名称',
  `module_url` varchar(100) DEFAULT NULL COMMENT '模块链接',
  `parent_id` int(11) DEFAULT NULL COMMENT '父模块Id',
  `level` int(11) NOT NULL,
  `expanded` tinyint(4) NOT NULL DEFAULT '1' COMMENT '展开状态(1:展开;0:收缩)',
  `iconCss` varchar(100) DEFAULT NULL COMMENT '图标或者样式',
  `display` tinyint(4) NOT NULL COMMENT '是否显示 0:否 1:是',
  `display_index` int(11) NOT NULL COMMENT '排序',
  `information` varchar(200) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `t_base_module_name` (`module_name`),
  KEY `t_base_module_url` (`module_url`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_base_module
-- ----------------------------
INSERT INTO `t_base_module` VALUES ('1', '系统管理', '/admin', '0', '0', '1', null, '1', '1', null, '2012-05-23 12:00:34', '2012-05-13 22:09:40');
INSERT INTO `t_base_module` VALUES ('2', '权限管理', null, '1', '1', '1', null, '1', '2', null, '2012-05-15 11:10:36', '2012-05-13 22:11:04');
INSERT INTO `t_base_module` VALUES ('3', '角色管理', '/admin/permission/role', '2', '2', '1', null, '1', '3', null, '2012-05-23 12:00:34', '2012-05-13 22:15:47');
INSERT INTO `t_base_module` VALUES ('4', '编辑角色', '/admin/permission/role/save', '3', '3', '1', null, '0', '5', null, '2012-05-23 12:00:34', '2012-05-13 22:16:36');
INSERT INTO `t_base_module` VALUES ('5', '删除角色', '/admin/permission/role/delete', '3', '3', '1', null, '0', '6', null, '2012-05-23 12:00:34', '2012-05-13 22:17:18');
INSERT INTO `t_base_module` VALUES ('6', '查询角色', '/admin/permission/role/query', '3', '3', '1', null, '0', '4', null, '2012-05-23 12:00:34', '2012-05-13 22:20:28');
INSERT INTO `t_base_module` VALUES ('7', '用户管理', '/admin/permission/user', '2', '2', '1', null, '1', '10', null, '2012-05-23 12:00:34', '2012-05-13 22:21:14');
INSERT INTO `t_base_module` VALUES ('8', '编辑用户', '/admin/permission/user/save', '7', '3', '1', null, '0', '12', null, '2012-05-23 12:00:34', '2012-05-13 22:22:12');
INSERT INTO `t_base_module` VALUES ('9', '删除用户', '/admin/permission/user/delete', '7', '3', '1', null, '0', '13', null, '2012-05-23 12:00:34', '2012-05-13 22:22:56');
INSERT INTO `t_base_module` VALUES ('10', '查询用户', '/admin/permission/user/query', '7', '3', '1', null, '0', '11', null, '2012-05-23 12:00:34', '2012-05-13 22:23:32');
INSERT INTO `t_base_module` VALUES ('11', '模块管理', '/admin/permission/module', '2', '2', '1', null, '1', '16', null, '2012-05-23 12:00:34', '2012-05-13 22:24:05');
INSERT INTO `t_base_module` VALUES ('12', '编辑模块', '/admin/permission/module/save', '11', '3', '1', null, '0', '18', null, '2012-05-23 12:00:34', '2012-05-13 22:24:47');
INSERT INTO `t_base_module` VALUES ('13', '删除模块', '/admin/permission/module/delete', '11', '3', '1', null, '0', '19', null, '2012-05-23 12:00:34', '2012-05-13 22:25:27');
INSERT INTO `t_base_module` VALUES ('14', '查询模块', '/admin/permission/module/query', '11', '3', '1', null, '0', '17', null, '2012-05-23 12:00:34', '2012-05-14 21:50:02');
INSERT INTO `t_base_module` VALUES ('15', 'Market管理', null, '1', '1', '1', null, '1', '25', 'Market管理', '2012-05-17 10:36:21', '2012-05-17 10:36:21');
INSERT INTO `t_base_module` VALUES ('16', '查询所有模块', '/admin/permission/module/queryAll', '11', '3', '1', null, '0', '20', null, '2012-05-23 12:00:34', '2012-05-16 00:23:31');
INSERT INTO `t_base_module` VALUES ('17', '应用管理', '/admin/app/application', '15', '2', '1', null, '1', '30', '应用管理', '2012-05-23 12:00:34', '2012-05-18 17:21:53');
INSERT INTO `t_base_module` VALUES ('18', '查询应用', '/admin/app/application/query', '17', '3', '1', null, '0', '31', '查询应用', '2012-05-23 12:00:34', '2012-05-20 11:05:09');
INSERT INTO `t_base_module` VALUES ('19', '编辑应用', '/admin/app/application/save', '17', '3', '1', null, '0', '32', '编辑应用', '2012-05-23 12:00:34', '2012-05-20 11:06:44');
INSERT INTO `t_base_module` VALUES ('20', '删除应用', '/admin/app/application/delete', '17', '3', '1', null, '0', '33', '删除应用', '2012-05-23 12:00:34', '2012-05-20 11:08:54');
INSERT INTO `t_base_module` VALUES ('21', '分类管理', '/admin/app/catalog', '15', '2', '1', null, '1', '26', '应用分类管理', '2012-05-23 12:00:34', '2012-05-20 11:09:48');
INSERT INTO `t_base_module` VALUES ('22', '查询分类', '/admin/app/catalog/query', '21', '3', '1', null, '0', '27', '查询应用分类', '2012-05-23 12:00:34', '2012-05-20 11:40:08');
INSERT INTO `t_base_module` VALUES ('23', '编辑分类', '/admin/app/catalog/save', '21', '3', '1', null, '0', '28', '编辑应用分类', '2012-05-23 12:00:34', '2012-05-20 11:41:14');
INSERT INTO `t_base_module` VALUES ('24', '删除分类', '/admin/app/catalog/delete', '21', '3', '1', null, '0', '29', '删除应用分类', '2012-05-23 12:00:34', '2012-05-20 11:41:55');
INSERT INTO `t_base_module` VALUES ('25', '评论管理', '/admin/app/comment', '15', '2', '1', null, '1', '39', '应用评论管理', '2012-05-23 12:00:34', '2012-05-21 11:33:13');
INSERT INTO `t_base_module` VALUES ('26', '查询评论', '/admin/app/comment/query', '25', '3', '1', null, '0', '40', '查询评论图片', '2012-05-23 12:00:34', '2012-05-21 11:33:59');
INSERT INTO `t_base_module` VALUES ('27', '编辑评论', '/admin/app/comment/save', '25', '3', '1', null, '0', '41', '编辑评论', '2012-05-23 12:00:34', '2012-05-21 11:34:35');
INSERT INTO `t_base_module` VALUES ('28', '删除评论', '/admin/app/comment/delete', '25', '3', '1', null, '0', '42', '删除评论', '2012-05-23 12:00:34', '2012-05-21 11:35:18');
INSERT INTO `t_base_module` VALUES ('29', '查看图片', '/admin/app/comment/view', '25', '3', '1', null, '0', '43', '查看评论', '2012-05-23 12:00:34', '2012-05-21 11:35:47');
INSERT INTO `t_base_module` VALUES ('30', '版本管理', '/admin/app/version', '15', '2', '1', null, '1', '34', '应用版本管理', '2012-05-30 12:12:40', '2012-05-21 11:36:39');
INSERT INTO `t_base_module` VALUES ('31', '查询版本', '/admin/app/version/query', '30', '3', '1', null, '0', '35', '查询版本', '2012-05-23 12:00:34', '2012-05-21 11:37:08');
INSERT INTO `t_base_module` VALUES ('32', '编辑版本', '/admin/app/version/save', '30', '3', '1', null, '0', '36', '编辑版本', '2012-05-23 12:00:34', '2012-05-21 11:37:39');
INSERT INTO `t_base_module` VALUES ('33', '删除版本', '/admin/app/version/delete', '30', '3', '1', null, '0', '37', '删除版本', '2012-05-23 12:00:34', '2012-05-21 11:38:16');
INSERT INTO `t_base_module` VALUES ('34', '查看版本', '/admin/app/version/view', '30', '3', '1', null, '0', '38', '查看版本', '2012-05-23 12:00:34', '2012-05-21 11:38:49');
INSERT INTO `t_base_module` VALUES ('35', '帐号管理', '/admin/app/account', '1', '1', '1', null, '1', '42', '帐号管理', '2012-05-23 12:00:34', '2012-05-22 11:06:15');
INSERT INTO `t_base_module` VALUES ('36', '查询帐号', '/admin/app/account/query', '35', '2', '1', null, '0', '43', '查询帐号', '2012-05-23 12:00:34', '2012-05-22 11:07:08');
INSERT INTO `t_base_module` VALUES ('37', '编辑帐号', '/admin/app/account/save', '35', '2', '1', null, '0', '44', '编辑帐号', '2012-05-23 12:00:34', '2012-05-22 11:07:50');
INSERT INTO `t_base_module` VALUES ('38', '封号', '/admin/app/account/lock', '35', '2', '1', null, '0', '45', '封号帐号', '2012-05-23 12:00:34', '2012-05-22 11:08:33');
INSERT INTO `t_base_module` VALUES ('39', '解封', '/admin/app/account/unlock', '35', '2', '1', null, '0', '46', '解封帐号', '2012-05-23 12:00:34', '2012-05-22 11:09:38');
INSERT INTO `t_base_module` VALUES ('40', '下载记录', '/admin/app/download', '15', '3', '1', null, '1', '47', '应用下载记录', '2012-06-02 02:06:06', '2012-05-22 11:37:44');
INSERT INTO `t_base_module` VALUES ('41', '查询下载记录', '/admin/app/download/query', '40', '3', '1', null, '0', '48', '查询下载记录', '2012-06-02 02:06:10', '2012-05-22 11:38:50');
INSERT INTO `t_base_module` VALUES ('42', '查询平台用户', '/admin/app/account/all', '25', '3', '1', null, '0', '50', '查询所有帐号信息（评论管理中查询下拉框）', '2012-05-29 15:20:14', '2012-05-29 15:20:10');
INSERT INTO `t_base_module` VALUES ('43', '所有应用', '/admin/app/application/all', '15', '2', '1', null, '0', '49', '所有应用', '2012-05-29 15:54:33', '2012-05-29 15:24:04');

-- ----------------------------
-- Table structure for `t_base_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_role`;
CREATE TABLE `t_base_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_desc` varchar(200) DEFAULT NULL COMMENT '角色描述',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_t_base_role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_base_role
-- ----------------------------
INSERT INTO `t_base_role` VALUES ('1', 'Administrator', '超级管理员', '2012-05-18 22:01:19', '2012-05-18 22:01:19');
INSERT INTO `t_base_role` VALUES ('2', 'Market管理员', 'Market管理员', '2012-05-30 14:54:00', '2012-05-30 14:54:00');
INSERT INTO `t_base_role` VALUES ('3', '测试', '测试', '2012-05-17 09:19:33', '2012-05-17 09:19:33');
INSERT INTO `t_base_role` VALUES ('5', '模块管理员', '模块管理员', '2012-05-18 13:01:54', '2012-05-18 13:01:54');

-- ----------------------------
-- Table structure for `t_base_role_module`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_role_module`;
CREATE TABLE `t_base_role_module` (
  `role_id` int(11) NOT NULL COMMENT '角色Ie',
  `module_id` int(11) NOT NULL COMMENT '模块Id',
  PRIMARY KEY (`role_id`,`module_id`),
  KEY `fk_module_id` (`module_id`),
  CONSTRAINT `fk_module_id` FOREIGN KEY (`module_id`) REFERENCES `t_base_module` (`id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_base_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_base_role_module
-- ----------------------------
INSERT INTO `t_base_role_module` VALUES ('1', '1');
INSERT INTO `t_base_role_module` VALUES ('2', '1');
INSERT INTO `t_base_role_module` VALUES ('3', '1');
INSERT INTO `t_base_role_module` VALUES ('5', '1');
INSERT INTO `t_base_role_module` VALUES ('1', '2');
INSERT INTO `t_base_role_module` VALUES ('3', '2');
INSERT INTO `t_base_role_module` VALUES ('5', '2');
INSERT INTO `t_base_role_module` VALUES ('1', '3');
INSERT INTO `t_base_role_module` VALUES ('3', '3');
INSERT INTO `t_base_role_module` VALUES ('1', '4');
INSERT INTO `t_base_role_module` VALUES ('3', '4');
INSERT INTO `t_base_role_module` VALUES ('1', '5');
INSERT INTO `t_base_role_module` VALUES ('3', '5');
INSERT INTO `t_base_role_module` VALUES ('1', '6');
INSERT INTO `t_base_role_module` VALUES ('3', '6');
INSERT INTO `t_base_role_module` VALUES ('1', '7');
INSERT INTO `t_base_role_module` VALUES ('3', '7');
INSERT INTO `t_base_role_module` VALUES ('1', '8');
INSERT INTO `t_base_role_module` VALUES ('3', '8');
INSERT INTO `t_base_role_module` VALUES ('1', '9');
INSERT INTO `t_base_role_module` VALUES ('3', '9');
INSERT INTO `t_base_role_module` VALUES ('1', '10');
INSERT INTO `t_base_role_module` VALUES ('3', '10');
INSERT INTO `t_base_role_module` VALUES ('1', '11');
INSERT INTO `t_base_role_module` VALUES ('3', '11');
INSERT INTO `t_base_role_module` VALUES ('5', '11');
INSERT INTO `t_base_role_module` VALUES ('1', '12');
INSERT INTO `t_base_role_module` VALUES ('3', '12');
INSERT INTO `t_base_role_module` VALUES ('5', '12');
INSERT INTO `t_base_role_module` VALUES ('1', '13');
INSERT INTO `t_base_role_module` VALUES ('3', '13');
INSERT INTO `t_base_role_module` VALUES ('5', '13');
INSERT INTO `t_base_role_module` VALUES ('1', '14');
INSERT INTO `t_base_role_module` VALUES ('3', '14');
INSERT INTO `t_base_role_module` VALUES ('5', '14');
INSERT INTO `t_base_role_module` VALUES ('1', '15');
INSERT INTO `t_base_role_module` VALUES ('2', '15');
INSERT INTO `t_base_role_module` VALUES ('3', '15');
INSERT INTO `t_base_role_module` VALUES ('1', '16');
INSERT INTO `t_base_role_module` VALUES ('3', '16');
INSERT INTO `t_base_role_module` VALUES ('5', '16');
INSERT INTO `t_base_role_module` VALUES ('1', '17');
INSERT INTO `t_base_role_module` VALUES ('2', '17');
INSERT INTO `t_base_role_module` VALUES ('1', '18');
INSERT INTO `t_base_role_module` VALUES ('2', '18');
INSERT INTO `t_base_role_module` VALUES ('1', '19');
INSERT INTO `t_base_role_module` VALUES ('2', '19');
INSERT INTO `t_base_role_module` VALUES ('1', '20');
INSERT INTO `t_base_role_module` VALUES ('2', '20');
INSERT INTO `t_base_role_module` VALUES ('1', '21');
INSERT INTO `t_base_role_module` VALUES ('2', '21');
INSERT INTO `t_base_role_module` VALUES ('1', '22');
INSERT INTO `t_base_role_module` VALUES ('2', '22');
INSERT INTO `t_base_role_module` VALUES ('1', '23');
INSERT INTO `t_base_role_module` VALUES ('2', '23');
INSERT INTO `t_base_role_module` VALUES ('1', '24');
INSERT INTO `t_base_role_module` VALUES ('2', '24');
INSERT INTO `t_base_role_module` VALUES ('1', '25');
INSERT INTO `t_base_role_module` VALUES ('2', '25');
INSERT INTO `t_base_role_module` VALUES ('1', '26');
INSERT INTO `t_base_role_module` VALUES ('2', '26');
INSERT INTO `t_base_role_module` VALUES ('1', '27');
INSERT INTO `t_base_role_module` VALUES ('2', '27');
INSERT INTO `t_base_role_module` VALUES ('1', '28');
INSERT INTO `t_base_role_module` VALUES ('2', '28');
INSERT INTO `t_base_role_module` VALUES ('1', '29');
INSERT INTO `t_base_role_module` VALUES ('2', '29');
INSERT INTO `t_base_role_module` VALUES ('1', '30');
INSERT INTO `t_base_role_module` VALUES ('2', '30');
INSERT INTO `t_base_role_module` VALUES ('1', '31');
INSERT INTO `t_base_role_module` VALUES ('2', '31');
INSERT INTO `t_base_role_module` VALUES ('1', '32');
INSERT INTO `t_base_role_module` VALUES ('2', '32');
INSERT INTO `t_base_role_module` VALUES ('1', '33');
INSERT INTO `t_base_role_module` VALUES ('2', '33');
INSERT INTO `t_base_role_module` VALUES ('1', '34');
INSERT INTO `t_base_role_module` VALUES ('2', '34');
INSERT INTO `t_base_role_module` VALUES ('1', '35');
INSERT INTO `t_base_role_module` VALUES ('1', '36');
INSERT INTO `t_base_role_module` VALUES ('1', '37');
INSERT INTO `t_base_role_module` VALUES ('1', '38');
INSERT INTO `t_base_role_module` VALUES ('1', '39');
INSERT INTO `t_base_role_module` VALUES ('1', '40');
INSERT INTO `t_base_role_module` VALUES ('2', '40');
INSERT INTO `t_base_role_module` VALUES ('1', '41');
INSERT INTO `t_base_role_module` VALUES ('2', '41');
INSERT INTO `t_base_role_module` VALUES ('1', '42');
INSERT INTO `t_base_role_module` VALUES ('2', '42');
INSERT INTO `t_base_role_module` VALUES ('1', '43');
INSERT INTO `t_base_role_module` VALUES ('2', '43');

-- ----------------------------
-- Table structure for `t_base_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_user`;
CREATE TABLE `t_base_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(30) NOT NULL COMMENT '邮箱',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `gender` tinyint(4) NOT NULL COMMENT '性别 0：保密；1：男；2：女',
  `update_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` tinyint(4) NOT NULL COMMENT '状态：0 停用；0x1 启用；0x2：禁止登录（密码错误次数过多，一定时间内禁止登录）',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `last_login_ip` varchar(18) DEFAULT NULL COMMENT '最后登录Ip',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '最后登录时间',
  `office_phone` varchar(18) DEFAULT NULL COMMENT '办公电话',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_t_base_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_base_user
-- ----------------------------
INSERT INTO `t_base_user` VALUES ('1', 'admin@admin.com', '7d2331557888b9c2d51ee1cb44df0ef2', '系统管理员', '系统管理员', '0', '2012-06-07 17:16:37', '2012-05-13 15:19:44', '1', null, '127.0.0.1', '2012-06-07 17:16:34', null, '0');
INSERT INTO `t_base_user` VALUES ('2', 'lanb@carit.com.cn', '5887e361c81bbf2ec1c428426cc0a734', '兰斌', '兰斌', '1', '2012-06-07 09:28:12', '2012-05-30 14:45:42', '1', '应用管理员', '127.0.0.1', '2012-06-07 09:28:12', '', '');

-- ----------------------------
-- Table structure for `t_base_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_base_user_role`;
CREATE TABLE `t_base_user_role` (
  `user_id` int(11) NOT NULL COMMENT '用户Id',
  `role_id` int(11) NOT NULL COMMENT '角色Id',
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_user_role_id` (`role_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_base_user` (`id`),
  CONSTRAINT `fk_user_role_id` FOREIGN KEY (`role_id`) REFERENCES `t_base_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_base_user_role
-- ----------------------------
INSERT INTO `t_base_user_role` VALUES ('1', '1');
INSERT INTO `t_base_user_role` VALUES ('2', '2');
INSERT INTO `t_base_user_role` VALUES ('1', '5');

-- ----------------------------
-- View structure for `v_app_catalog_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_catalog_cn`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`localhost` SQL SECURITY DEFINER VIEW `v_app_catalog_cn` AS select `t_app_catalog`.`id` AS `id`,`t_app_catalog`.`name` AS `name`,`t_app_catalog`.`description` AS `description` from `t_app_catalog` ;

-- ----------------------------
-- View structure for `v_app_catalog_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_catalog_en`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`localhost` SQL SECURITY DEFINER VIEW `v_app_catalog_en` AS select `t_app_catalog`.`id` AS `id`,`t_app_catalog`.`en_name` AS `name`,`t_app_catalog`.`en_description` AS `description` from `t_app_catalog` ;

-- ----------------------------
-- View structure for `v_app_download_log_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_download_log_cn`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`localhost` SQL SECURITY DEFINER VIEW `v_app_download_log_cn` AS select `a`.`account_id` AS `account_id`,`a`.`app_id` AS `app_id`,`b`.`app_name` AS `app_name` from (`t_app_download_log` `a` left join `t_application` `b` on((`a`.`app_id` = `b`.`id`))) ;

-- ----------------------------
-- View structure for `v_app_download_log_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_download_log_en`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`localhost` SQL SECURITY DEFINER VIEW `v_app_download_log_en` AS select `a`.`account_id` AS `account_id`,`a`.`app_id` AS `app_id`,`b`.`en_name` AS `app_name` from (`t_app_download_log` `a` left join `t_application` `b` on((`a`.`app_id` = `b`.`id`))) ;

-- ----------------------------
-- View structure for `v_app_version_file_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_version_file_cn`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`localhost` SQL SECURITY DEFINER VIEW `v_app_version_file_cn` AS select `t_app_version_file`.`id` AS `id`,`t_app_version_file`.`version` AS `version`,`t_app_version_file`.`size` AS `size`,`t_app_version_file`.`file_path` AS `file_path`,`t_app_version_file`.`new_features` AS `new_features` from `t_app_version_file` ;

-- ----------------------------
-- View structure for `v_app_version_file_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_version_file_en`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`localhost` SQL SECURITY DEFINER VIEW `v_app_version_file_en` AS select `t_app_version_file`.`id` AS `id`,`t_app_version_file`.`version` AS `version`,`t_app_version_file`.`size` AS `size`,`t_app_version_file`.`file_path` AS `file_path`,`t_app_version_file`.`en_new_features` AS `new_features` from `t_app_version_file` ;

-- ----------------------------
-- View structure for `v_application_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_application_cn`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`localhost` SQL SECURITY DEFINER VIEW `v_application_cn` AS select `a`.`id` AS `id`,`a`.`app_name` AS `app_name`,`a`.`icon` AS `icon`,`a`.`big_icon` AS `big_icon`,`a`.`developer` AS `developer`,`a`.`version` AS `version`,`a`.`icon` AS `t_application`,`b`.`name` AS `catalog_name`,`a`.`catalog_id` AS `catalog_id`,`a`.`size` AS `size`,`a`.`app_file_path` AS `app_file_path`,`a`.`platform` AS `platform`,`a`.`support_languages` AS `support_languages`,`a`.`price` AS `price`,`a`.`down_count` AS `down_count`,`a`.`app_level` AS `app_level`,`a`.`description` AS `description`,`a`.`permission_desc` AS `permission_desc`,`a`.`images` AS `images`,`a`.`update_time` AS `update_time` from (`t_application` `a` left join `t_app_catalog` `b` on((`a`.`catalog_id` = `b`.`id`))) ;

-- ----------------------------
-- View structure for `v_application_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_application_en`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`localhost` SQL SECURITY DEFINER VIEW `v_application_en` AS select `a`.`id` AS `id`,`a`.`en_name` AS `app_name`,`a`.`icon` AS `icon`,`a`.`big_icon` AS `big_icon`,`a`.`developer` AS `developer`,`a`.`version` AS `version`,`a`.`icon` AS `t_application`,`b`.`en_name` AS `catalog_name`,`a`.`catalog_id` AS `catalog_id`,`a`.`size` AS `size`,`a`.`app_file_path` AS `app_file_path`,`a`.`platform` AS `platform`,`a`.`support_languages` AS `support_languages`,`a`.`price` AS `price`,`a`.`down_count` AS `down_count`,`a`.`app_level` AS `app_level`,`a`.`en_description` AS `description`,`a`.`en_permission_desc` AS `permission_desc`,`a`.`images` AS `images`,`a`.`update_time` AS `update_time` from (`t_application` `a` left join `t_app_catalog` `b` on((`a`.`catalog_id` = `b`.`id`))) ;

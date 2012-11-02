/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.241
Source Server Version : 50162
Source Host           : 192.168.0.241:3306
Source Database       : market

Target Server Type    : MYSQL
Target Server Version : 50162
File Encoding         : 65001

Date: 2012-07-18 15:49:16
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
  `thumb_photo` varchar(200) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;


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
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `t_app_developer`
-- ----------------------------
DROP TABLE IF EXISTS `t_app_developer`;
CREATE TABLE `t_app_developer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '开发者名称',
  `website` varchar(100) DEFAULT NULL COMMENT '开发者网站',
  `email` varchar(100) DEFAULT NULL COMMENT '联系邮箱',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `status` int(11) NOT NULL COMMENT '状态',
  `create_time` datetime NOT NULL DEFAULT '2012-06-20 00:00:00',
  `update_time` datetime NOT NULL DEFAULT '2012-06-20 00:00:00',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_t_app_developer_name` (`name`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_app_developer
-- ----------------------------
INSERT INTO `t_app_developer` VALUES ('1', '上海大智慧软件', 'http://www.dzhihui.cn/', 'dzh@dzhihui.cn', '上海大智慧软件', '1', '2012-06-29 15:57:30', '2012-06-29 15:57:30');
INSERT INTO `t_app_developer` VALUES ('2', '新浪科技', '', '', '新浪科技', '1', '2012-06-29 15:57:43', '2012-06-29 15:57:43');
INSERT INTO `t_app_developer` VALUES ('3', 'Google.INC', '', '', 'Google.INC', '1', '2012-06-29 15:58:11', '2012-06-29 15:58:11');
INSERT INTO `t_app_developer` VALUES ('4', '腾讯科技', '', '', '腾讯科技', '1', '2012-06-29 15:58:31', '2012-06-29 15:58:31');
INSERT INTO `t_app_developer` VALUES ('5', '开卷有益工作室', '', '', '开卷有益工作室', '1', '2012-06-29 15:58:46', '2012-06-29 15:58:46');
INSERT INTO `t_app_developer` VALUES ('6', '科大讯飞', '', '', '科大讯飞', '1', '2012-06-29 15:58:55', '2012-06-29 15:58:55');
INSERT INTO `t_app_developer` VALUES ('7', '蘑菇网', '', '', '蘑菇网', '1', '2012-06-29 15:59:05', '2012-06-29 15:59:05');
INSERT INTO `t_app_developer` VALUES ('8', 'Skype', '', '', 'Skype', '1', '2012-06-29 16:01:13', '2012-06-29 16:01:13');
INSERT INTO `t_app_developer` VALUES ('9', 'Rovio', '', '', 'Rovio', '0', '2012-06-29 16:01:25', '2012-06-29 17:12:48');
INSERT INTO `t_app_developer` VALUES ('10', 'fishing', '', '', 'fishing', '1', '2012-06-29 16:01:37', '2012-06-29 16:01:37');
INSERT INTO `t_app_developer` VALUES ('11', 'Johospace', '', '', 'Johospace', '1', '2012-06-29 16:01:51', '2012-06-29 16:01:51');
INSERT INTO `t_app_developer` VALUES ('12', 'Maxthon', '', '', 'Maxthon', '1', '2012-06-29 16:02:00', '2012-06-29 16:02:00');
INSERT INTO `t_app_developer` VALUES ('13', 'Chartcross Limited', '', '', 'Chartcross Limited', '1', '2012-06-29 16:02:16', '2012-06-29 16:02:16');
INSERT INTO `t_app_developer` VALUES ('15', '中通福瑞', 'http://www.android-in-car.com', 'huangjp@carit.com.cn', '', '1', '2012-07-12 15:26:52', '2012-07-12 15:26:52');

-- ----------------------------
-- Table structure for `t_app_download_log`
-- ----------------------------
DROP TABLE IF EXISTS `t_app_download_log`;
CREATE TABLE `t_app_download_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL COMMENT '帐号Id',
  `app_id` int(11) NOT NULL COMMENT '应用Id',
  `version` varchar(50) DEFAULT NULL,
  `download_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `idx_app_down_log_account_id` (`account_id`),
  KEY `idx_app_down_log_app_id` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=93 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `t_app_version_file`
-- ----------------------------
DROP TABLE IF EXISTS `t_app_version_file`;
CREATE TABLE `t_app_version_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_id` int(11) NOT NULL COMMENT '应用Id',
  `version` varchar(50) DEFAULT NULL,
  `size` varchar(10) NOT NULL COMMENT '应用大小',
  `file_path` varchar(100) NOT NULL COMMENT '应用程序路径',
  `new_features` varchar(2000) DEFAULT NULL,
  `en_new_features` varchar(2000) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态：0 停用， 1 启用',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `t_application`
-- ----------------------------
DROP TABLE IF EXISTS `t_application`;
CREATE TABLE `t_application` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `app_name` varchar(50) NOT NULL COMMENT '应用名称',
  `en_name` varchar(50) NOT NULL COMMENT '显示名称',
  `version` varchar(50) DEFAULT NULL,
  `developer` int(11) NOT NULL COMMENT '开发商',
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
  `description` varchar(2000) DEFAULT NULL,
  `en_description` varchar(2000) DEFAULT NULL,
  `en_permission_desc` varchar(2000) DEFAULT NULL,
  `permission_desc` varchar(2000) DEFAULT NULL,
  `features` varchar(2000) DEFAULT NULL,
  `en_features` varchar(2000) DEFAULT NULL,
  `images` varchar(500) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态：0 停用， 1 启用',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_t_application_name` (`app_name`),
  UNIQUE KEY `idx_t_application_e_name` (`en_name`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8;

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_base_field
-- ----------------------------
INSERT INTO `t_base_field` VALUES ('1', 'gender', '性别', '0', '女', '1', '2012-06-10 16:11:57', '2012-06-10 16:11:47', '1');
INSERT INTO `t_base_field` VALUES ('2', 'gender', '性别', '1', '男', '1', '2012-06-10 16:16:37', '2012-06-10 16:16:32', '2');
INSERT INTO `t_base_field` VALUES ('3', 'gender', '性别', '2', '保密', '1', '2012-06-10 16:17:00', '2012-06-10 16:16:55', '3');
INSERT INTO `t_base_field` VALUES ('4', 'platform', '支持平台', '1', 'Android 1.5', '1', '2012-06-10 16:30:05', '2012-06-10 16:24:01', '4');
INSERT INTO `t_base_field` VALUES ('5', 'platform', '支持平台', '2', 'Android 1.6', '1', '2012-06-10 16:30:08', '2012-06-10 16:24:50', '5');
INSERT INTO `t_base_field` VALUES ('6', 'platform', '支持平台', '3', 'Android 2.0', '1', '2012-06-10 16:30:12', '2012-06-10 16:25:31', '6');
INSERT INTO `t_base_field` VALUES ('7', 'platform', '支持平台', '4', 'Android 2.1', '1', '2012-06-10 16:30:15', '2012-06-10 16:26:17', '7');
INSERT INTO `t_base_field` VALUES ('8', 'platform', '支持平台', '5', 'Android 2.2', '1', '2012-06-10 16:30:17', '2012-06-10 16:27:48', '8');
INSERT INTO `t_base_field` VALUES ('9', 'platform', '支持平台', '6', 'Android 2.3', '1', '2012-06-10 16:30:19', '2012-06-10 16:29:17', '9');
INSERT INTO `t_base_field` VALUES ('10', 'platform', '支持平台', '7', 'Android 4.0', '1', '2012-06-10 16:30:22', '2012-06-10 16:29:55', '10');
INSERT INTO `t_base_field` VALUES ('11', 'status', '状态', '0', '停用', '1', '2012-06-10 20:13:49', '2012-06-10 20:13:46', '11');
INSERT INTO `t_base_field` VALUES ('12', 'status', '状态', '1', '启用', '1', '2012-06-10 20:14:12', '2012-06-10 20:14:08', '12');
INSERT INTO `t_base_field` VALUES ('13', 'account_status', '状态', '0', '停用', '1', '2012-06-10 21:19:12', '2012-06-10 21:19:09', '13');
INSERT INTO `t_base_field` VALUES ('14', 'account_status', '状态', '1', '启用', '1', '2012-06-10 20:15:14', '2012-06-10 20:15:09', '14');
INSERT INTO `t_base_field` VALUES ('15', 'account_status', '状态', '2', '锁定', '1', '2012-06-10 20:16:55', '2012-06-10 20:16:49', '15');
INSERT INTO `t_base_field` VALUES ('16', 'suppor_language', '支持语言', '0', '简体中文', '1', '2012-06-10 20:35:35', '2012-06-10 20:35:31', '16');
INSERT INTO `t_base_field` VALUES ('17', 'suppor_language', '支持语言', '1', '繁体中文', '1', '2012-06-10 20:36:21', '2012-06-10 20:36:14', '17');
INSERT INTO `t_base_field` VALUES ('18', 'suppor_language', '支持语言', '2', '英文', '1', '2012-06-10 20:36:42', '2012-06-10 20:36:38', '18');
INSERT INTO `t_base_field` VALUES ('19', 'suppor_language', '支持语言', '3', '多语言', '1', '2012-06-10 20:37:31', '2012-06-10 20:37:26', '19');
INSERT INTO `t_base_field` VALUES ('20', 'app_status', '应用状态', '0', '停用', '1', '2012-06-14 10:08:05', '2012-06-14 10:08:05', '1');
INSERT INTO `t_base_field` VALUES ('21', 'app_status', '应用状态', '1', '启用', '1', '2012-06-14 10:08:58', '2012-06-14 10:08:58', '2');
INSERT INTO `t_base_field` VALUES ('22', 'app_status', '应用状态', '2', '推荐', '1', '2012-06-14 10:09:22', '2012-06-14 10:09:22', '3');

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
  UNIQUE KEY `t_base_module_name` (`module_name`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8;

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
INSERT INTO `t_base_module` VALUES ('15', '字典管理', '/admin/permission/field', '2', '2', '1', null, '1', '20', null, '2012-06-10 15:15:53', '2012-06-10 15:15:35');
INSERT INTO `t_base_module` VALUES ('16', '查询字段', '/admin/permission/field/query', '15', '3', '1', null, '0', '21', null, '2012-06-10 16:19:22', '2012-06-10 16:19:20');
INSERT INTO `t_base_module` VALUES ('17', '编辑字段', '/admin/permission/field/save', '15', '3', '1', null, '0', '22', null, '2012-06-10 16:20:24', '2012-06-10 16:20:20');
INSERT INTO `t_base_module` VALUES ('18', '删除字段', '/admin/permission/field/delete', '15', '3', '1', null, '0', '23', null, '2012-06-10 16:21:12', '2012-06-10 16:21:09');
INSERT INTO `t_base_module` VALUES ('101', 'Market管理', null, '1', '1', '1', null, '1', '111', 'Market管理', '2012-06-10 15:11:21', '2012-05-17 10:36:21');
INSERT INTO `t_base_module` VALUES ('102', '查询所有模块', '/admin/permission/module/queryAll', '101', '3', '1', null, '0', '106', null, '2012-06-10 15:18:03', '2012-05-16 00:23:31');
INSERT INTO `t_base_module` VALUES ('103', '应用管理', '/admin/app/application', '101', '2', '1', null, '1', '116', '应用管理', '2012-06-10 15:20:54', '2012-05-18 17:21:53');
INSERT INTO `t_base_module` VALUES ('104', '查询应用', '/admin/app/application/query', '103', '3', '1', null, '0', '117', '查询应用', '2012-06-10 15:21:28', '2012-05-20 11:05:09');
INSERT INTO `t_base_module` VALUES ('105', '编辑应用', '/admin/app/application/save', '103', '3', '1', null, '0', '118', '编辑应用', '2012-06-10 15:21:31', '2012-05-20 11:06:44');
INSERT INTO `t_base_module` VALUES ('106', '删除应用', '/admin/app/application/delete', '103', '3', '1', null, '0', '119', '删除应用', '2012-06-10 15:21:35', '2012-05-20 11:08:54');
INSERT INTO `t_base_module` VALUES ('107', '分类管理', '/admin/app/catalog', '101', '2', '1', null, '1', '112', '应用分类管理', '2012-06-10 15:18:29', '2012-05-20 11:09:48');
INSERT INTO `t_base_module` VALUES ('108', '查询分类', '/admin/app/catalog/query', '107', '3', '1', null, '0', '113', '查询应用分类', '2012-06-10 15:18:34', '2012-05-20 11:40:08');
INSERT INTO `t_base_module` VALUES ('109', '编辑分类', '/admin/app/catalog/save', '107', '3', '1', null, '0', '114', '编辑应用分类', '2012-06-10 15:18:38', '2012-05-20 11:41:14');
INSERT INTO `t_base_module` VALUES ('110', '删除分类', '/admin/app/catalog/delete', '107', '3', '1', null, '0', '115', '删除应用分类', '2012-06-10 15:18:42', '2012-05-20 11:41:55');
INSERT INTO `t_base_module` VALUES ('111', '评论管理', '/admin/app/comment', '101', '2', '1', null, '1', '125', '应用评论管理', '2012-06-10 15:28:29', '2012-05-21 11:33:13');
INSERT INTO `t_base_module` VALUES ('112', '查询评论', '/admin/app/comment/query', '111', '3', '1', null, '0', '126', '查询评论图片', '2012-06-10 15:28:58', '2012-05-21 11:33:59');
INSERT INTO `t_base_module` VALUES ('113', '编辑评论', '/admin/app/comment/save', '111', '3', '1', null, '0', '127', '编辑评论', '2012-06-10 15:29:01', '2012-05-21 11:34:35');
INSERT INTO `t_base_module` VALUES ('114', '删除评论', '/admin/app/comment/delete', '111', '3', '1', null, '0', '128', '删除评论', '2012-06-10 15:29:02', '2012-05-21 11:35:18');
INSERT INTO `t_base_module` VALUES ('115', '查看评论', '/admin/app/comment/view', '111', '3', '1', null, '0', '129', '查看评论', '2012-06-10 15:29:08', '2012-05-21 11:35:47');
INSERT INTO `t_base_module` VALUES ('116', '版本管理', '/admin/app/version', '101', '2', '1', null, '1', '120', '应用版本管理', '2012-06-10 15:29:19', '2012-05-21 11:36:39');
INSERT INTO `t_base_module` VALUES ('117', '查询版本', '/admin/app/version/query', '116', '3', '1', null, '0', '121', '查询版本', '2012-06-10 15:29:55', '2012-05-21 11:37:08');
INSERT INTO `t_base_module` VALUES ('118', '编辑版本', '/admin/app/version/save', '116', '3', '1', null, '0', '122', '编辑版本', '2012-06-10 15:29:58', '2012-05-21 11:37:39');
INSERT INTO `t_base_module` VALUES ('119', '删除版本', '/admin/app/version/delete', '116', '3', '1', null, '0', '123', '删除版本', '2012-06-10 15:30:00', '2012-05-21 11:38:16');
INSERT INTO `t_base_module` VALUES ('120', '查看版本', '/admin/app/version/view', '116', '3', '1', null, '0', '124', '查看版本', '2012-06-10 15:30:05', '2012-05-21 11:38:49');
INSERT INTO `t_base_module` VALUES ('121', '帐号管理', '/admin/app/account', '101', '1', '1', null, '1', '128', '帐号管理模块', '2012-06-10 15:30:17', '2012-06-08 23:52:50');
INSERT INTO `t_base_module` VALUES ('122', '查询帐号', '/admin/app/account/query', '121', '2', '1', null, '0', '129', '查询帐号', '2012-06-10 15:30:44', '2012-05-22 11:07:08');
INSERT INTO `t_base_module` VALUES ('123', '编辑帐号', '/admin/app/account/save', '121', '2', '1', null, '0', '130', '编辑帐号', '2012-06-10 15:30:46', '2012-05-22 11:07:50');
INSERT INTO `t_base_module` VALUES ('124', '封号', '/admin/app/account/lock', '121', '2', '1', null, '0', '131', '封号帐号', '2012-06-10 15:30:48', '2012-05-22 11:08:33');
INSERT INTO `t_base_module` VALUES ('125', '解封', '/admin/app/account/unlock', '121', '2', '1', null, '0', '132', '解封帐号', '2012-06-10 15:30:50', '2012-05-22 11:09:38');
INSERT INTO `t_base_module` VALUES ('126', '查询平台用户', '/admin/app/account/all', '121', '3', '1', null, '0', '133', '查询所有帐号信息（评论管理中查询下拉框）', '2012-06-29 14:54:09', '2012-05-29 15:20:10');
INSERT INTO `t_base_module` VALUES ('127', '下载记录', '/admin/app/download', '101', '2', '1', null, '1', '140', '应用下载记录', '2012-07-16 17:30:47', '2012-05-22 11:37:44');
INSERT INTO `t_base_module` VALUES ('128', '查询下载记录', '/admin/app/download/query', '127', '3', '1', null, '0', '134', '查询下载记录', '2012-06-10 15:31:26', '2012-05-22 11:38:50');
INSERT INTO `t_base_module` VALUES ('129', '开发者管理', '/admin/app/developer', '101', '2', '1', null, '1', '135', '开发者管理', '2012-06-29 14:54:36', '2012-06-29 14:53:09');
INSERT INTO `t_base_module` VALUES ('130', '编辑开发者', '/admin/app/developer/save', '129', '3', '1', null, '0', '136', '编辑开发者', '2012-06-29 14:55:59', '2012-06-29 14:55:11');
INSERT INTO `t_base_module` VALUES ('131', '查看开发者', '/admin/app/developer/view', '129', '3', '1', null, '0', '137', '查看开发者', '2012-06-29 14:56:02', '2012-06-29 14:55:57');
INSERT INTO `t_base_module` VALUES ('132', '删除开发者', '/admin/app/developer/delete', '129', '3', '1', null, '0', '140', '删除开发者', '2012-06-29 14:57:09', '2012-06-29 14:57:11');
INSERT INTO `t_base_module` VALUES ('133', '查询开发者', '/admin/app/developer/query', '129', '3', '1', null, '0', '136', '查询', '2012-06-29 14:58:07', '2012-06-29 14:58:10');
INSERT INTO `t_base_module` VALUES ('134', '消息管理', '/admin/app/sysmessage', '101', '2', '1', null, '1', '145', '消息管理', '2012-07-16 17:31:24', '2012-07-16 17:30:24');
INSERT INTO `t_base_module` VALUES ('135', '查询消息', '/admin/app/sysmessage/query', '134', '3', '1', null, '0', '146', '查询消息', '2012-07-16 17:32:46', '2012-07-16 17:31:14');
INSERT INTO `t_base_module` VALUES ('136', '编辑消息', '/admin/app/sysmessage/save', '134', '3', '1', null, '0', '147', '编辑消息', '2012-07-16 17:33:27', '2012-07-16 17:31:55');
INSERT INTO `t_base_module` VALUES ('137', '查看消息', '/admin/app/sysmessage/view', '134', '3', '1', null, '0', '148', '查看消息', '2012-07-16 17:33:55', '2012-07-16 17:33:07');
INSERT INTO `t_base_module` VALUES ('138', '删除消息', '/admin/app/sysmessage/delete', '134', '3', '1', null, '0', '149', '删除消息', '2012-07-16 17:34:38', '2012-07-16 17:33:50');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_base_role
-- ----------------------------
INSERT INTO `t_base_role` VALUES ('1', 'Administrator', '超级管理员', '2012-07-16 17:43:17', '2012-07-16 17:43:17');
INSERT INTO `t_base_role` VALUES ('2', 'Market管理员', 'Market管理员', '2012-07-18 10:52:48', '2012-07-18 10:52:48');
INSERT INTO `t_base_role` VALUES ('6', '测试部', '测试项目', '2012-07-18 09:58:41', '2012-07-18 09:58:41');

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
INSERT INTO `t_base_role_module` VALUES ('6', '1');
INSERT INTO `t_base_role_module` VALUES ('1', '2');
INSERT INTO `t_base_role_module` VALUES ('1', '3');
INSERT INTO `t_base_role_module` VALUES ('1', '4');
INSERT INTO `t_base_role_module` VALUES ('1', '5');
INSERT INTO `t_base_role_module` VALUES ('1', '6');
INSERT INTO `t_base_role_module` VALUES ('1', '7');
INSERT INTO `t_base_role_module` VALUES ('1', '8');
INSERT INTO `t_base_role_module` VALUES ('1', '9');
INSERT INTO `t_base_role_module` VALUES ('1', '10');
INSERT INTO `t_base_role_module` VALUES ('1', '11');
INSERT INTO `t_base_role_module` VALUES ('1', '12');
INSERT INTO `t_base_role_module` VALUES ('1', '13');
INSERT INTO `t_base_role_module` VALUES ('1', '14');
INSERT INTO `t_base_role_module` VALUES ('1', '15');
INSERT INTO `t_base_role_module` VALUES ('1', '16');
INSERT INTO `t_base_role_module` VALUES ('1', '17');
INSERT INTO `t_base_role_module` VALUES ('1', '18');
INSERT INTO `t_base_role_module` VALUES ('1', '101');
INSERT INTO `t_base_role_module` VALUES ('2', '101');
INSERT INTO `t_base_role_module` VALUES ('6', '101');
INSERT INTO `t_base_role_module` VALUES ('1', '102');
INSERT INTO `t_base_role_module` VALUES ('2', '102');
INSERT INTO `t_base_role_module` VALUES ('6', '102');
INSERT INTO `t_base_role_module` VALUES ('1', '103');
INSERT INTO `t_base_role_module` VALUES ('2', '103');
INSERT INTO `t_base_role_module` VALUES ('6', '103');
INSERT INTO `t_base_role_module` VALUES ('1', '104');
INSERT INTO `t_base_role_module` VALUES ('2', '104');
INSERT INTO `t_base_role_module` VALUES ('6', '104');
INSERT INTO `t_base_role_module` VALUES ('1', '105');
INSERT INTO `t_base_role_module` VALUES ('2', '105');
INSERT INTO `t_base_role_module` VALUES ('6', '105');
INSERT INTO `t_base_role_module` VALUES ('1', '106');
INSERT INTO `t_base_role_module` VALUES ('2', '106');
INSERT INTO `t_base_role_module` VALUES ('6', '106');
INSERT INTO `t_base_role_module` VALUES ('1', '107');
INSERT INTO `t_base_role_module` VALUES ('2', '107');
INSERT INTO `t_base_role_module` VALUES ('6', '107');
INSERT INTO `t_base_role_module` VALUES ('1', '108');
INSERT INTO `t_base_role_module` VALUES ('2', '108');
INSERT INTO `t_base_role_module` VALUES ('6', '108');
INSERT INTO `t_base_role_module` VALUES ('1', '109');
INSERT INTO `t_base_role_module` VALUES ('2', '109');
INSERT INTO `t_base_role_module` VALUES ('6', '109');
INSERT INTO `t_base_role_module` VALUES ('1', '110');
INSERT INTO `t_base_role_module` VALUES ('2', '110');
INSERT INTO `t_base_role_module` VALUES ('6', '110');
INSERT INTO `t_base_role_module` VALUES ('1', '111');
INSERT INTO `t_base_role_module` VALUES ('2', '111');
INSERT INTO `t_base_role_module` VALUES ('6', '111');
INSERT INTO `t_base_role_module` VALUES ('1', '112');
INSERT INTO `t_base_role_module` VALUES ('2', '112');
INSERT INTO `t_base_role_module` VALUES ('6', '112');
INSERT INTO `t_base_role_module` VALUES ('1', '113');
INSERT INTO `t_base_role_module` VALUES ('2', '113');
INSERT INTO `t_base_role_module` VALUES ('6', '113');
INSERT INTO `t_base_role_module` VALUES ('1', '114');
INSERT INTO `t_base_role_module` VALUES ('2', '114');
INSERT INTO `t_base_role_module` VALUES ('6', '114');
INSERT INTO `t_base_role_module` VALUES ('1', '115');
INSERT INTO `t_base_role_module` VALUES ('2', '115');
INSERT INTO `t_base_role_module` VALUES ('6', '115');
INSERT INTO `t_base_role_module` VALUES ('1', '116');
INSERT INTO `t_base_role_module` VALUES ('2', '116');
INSERT INTO `t_base_role_module` VALUES ('6', '116');
INSERT INTO `t_base_role_module` VALUES ('1', '117');
INSERT INTO `t_base_role_module` VALUES ('2', '117');
INSERT INTO `t_base_role_module` VALUES ('6', '117');
INSERT INTO `t_base_role_module` VALUES ('1', '118');
INSERT INTO `t_base_role_module` VALUES ('2', '118');
INSERT INTO `t_base_role_module` VALUES ('6', '118');
INSERT INTO `t_base_role_module` VALUES ('1', '119');
INSERT INTO `t_base_role_module` VALUES ('2', '119');
INSERT INTO `t_base_role_module` VALUES ('6', '119');
INSERT INTO `t_base_role_module` VALUES ('1', '120');
INSERT INTO `t_base_role_module` VALUES ('2', '120');
INSERT INTO `t_base_role_module` VALUES ('6', '120');
INSERT INTO `t_base_role_module` VALUES ('1', '121');
INSERT INTO `t_base_role_module` VALUES ('2', '121');
INSERT INTO `t_base_role_module` VALUES ('6', '121');
INSERT INTO `t_base_role_module` VALUES ('1', '122');
INSERT INTO `t_base_role_module` VALUES ('2', '122');
INSERT INTO `t_base_role_module` VALUES ('6', '122');
INSERT INTO `t_base_role_module` VALUES ('1', '123');
INSERT INTO `t_base_role_module` VALUES ('2', '123');
INSERT INTO `t_base_role_module` VALUES ('6', '123');
INSERT INTO `t_base_role_module` VALUES ('1', '124');
INSERT INTO `t_base_role_module` VALUES ('2', '124');
INSERT INTO `t_base_role_module` VALUES ('6', '124');
INSERT INTO `t_base_role_module` VALUES ('1', '125');
INSERT INTO `t_base_role_module` VALUES ('2', '125');
INSERT INTO `t_base_role_module` VALUES ('6', '125');
INSERT INTO `t_base_role_module` VALUES ('1', '126');
INSERT INTO `t_base_role_module` VALUES ('2', '126');
INSERT INTO `t_base_role_module` VALUES ('6', '126');
INSERT INTO `t_base_role_module` VALUES ('1', '127');
INSERT INTO `t_base_role_module` VALUES ('2', '127');
INSERT INTO `t_base_role_module` VALUES ('6', '127');
INSERT INTO `t_base_role_module` VALUES ('1', '128');
INSERT INTO `t_base_role_module` VALUES ('2', '128');
INSERT INTO `t_base_role_module` VALUES ('6', '128');
INSERT INTO `t_base_role_module` VALUES ('1', '129');
INSERT INTO `t_base_role_module` VALUES ('2', '129');
INSERT INTO `t_base_role_module` VALUES ('6', '129');
INSERT INTO `t_base_role_module` VALUES ('1', '130');
INSERT INTO `t_base_role_module` VALUES ('2', '130');
INSERT INTO `t_base_role_module` VALUES ('6', '130');
INSERT INTO `t_base_role_module` VALUES ('1', '131');
INSERT INTO `t_base_role_module` VALUES ('2', '131');
INSERT INTO `t_base_role_module` VALUES ('6', '131');
INSERT INTO `t_base_role_module` VALUES ('1', '132');
INSERT INTO `t_base_role_module` VALUES ('2', '132');
INSERT INTO `t_base_role_module` VALUES ('6', '132');
INSERT INTO `t_base_role_module` VALUES ('1', '133');
INSERT INTO `t_base_role_module` VALUES ('2', '133');
INSERT INTO `t_base_role_module` VALUES ('6', '133');
INSERT INTO `t_base_role_module` VALUES ('1', '134');
INSERT INTO `t_base_role_module` VALUES ('2', '134');
INSERT INTO `t_base_role_module` VALUES ('6', '134');
INSERT INTO `t_base_role_module` VALUES ('1', '135');
INSERT INTO `t_base_role_module` VALUES ('2', '135');
INSERT INTO `t_base_role_module` VALUES ('6', '135');
INSERT INTO `t_base_role_module` VALUES ('1', '136');
INSERT INTO `t_base_role_module` VALUES ('2', '136');
INSERT INTO `t_base_role_module` VALUES ('6', '136');
INSERT INTO `t_base_role_module` VALUES ('1', '137');
INSERT INTO `t_base_role_module` VALUES ('2', '137');
INSERT INTO `t_base_role_module` VALUES ('6', '137');
INSERT INTO `t_base_role_module` VALUES ('1', '138');
INSERT INTO `t_base_role_module` VALUES ('2', '138');
INSERT INTO `t_base_role_module` VALUES ('6', '138');

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
  UNIQUE KEY `idx_t_base_user_email` (`email`),
  UNIQUE KEY `idx_t_base_nick_name` (`nick_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_base_user
-- ----------------------------
INSERT INTO `t_base_user` VALUES ('1', 'admin@admin.com', 'fd8922228b92a18e611567b3195126ac', '系统管理员', '系统管理员', '1', '2012-07-18 15:15:16', '2012-05-13 15:19:44', '1', '反反复复反反复复反反复复分', '127.0.0.1', '2012-07-18 15:14:30', null, '13186884444');

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

-- ----------------------------
-- Table structure for `t_sys_message`
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_message`;
CREATE TABLE `t_sys_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL COMMENT '账号Id',
  `catalog` int(11) NOT NULL COMMENT '消息类别：0 系统发送；1 应用升级',
  `title` varchar(100) NOT NULL,
  `content` varchar(800) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '状态：0 未读；1 已读；',
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_t_sys_message_account_id` (`account_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `t_obd_data`
-- ----------------------------
DROP TABLE IF EXISTS `t_obd_data`;
CREATE TABLE `t_obd_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `value_1` int(11) NOT NULL COMMENT '发动机负荷计算值',
  `value_2` int(11) NOT NULL COMMENT '发动机冷却液温度',
  `value_3` int(11) NOT NULL COMMENT '发动机转速',
  `value_4` int(11) NOT NULL COMMENT '里程',
  `value_5` int(11) NOT NULL COMMENT '车速',
  `value_6` int(11) NOT NULL COMMENT '进气温度',
  `value_7` int(11) NOT NULL COMMENT '空气流量',
  `value_8` int(11) NOT NULL COMMENT '节气门绝对值',
  `value_9` int(11) NOT NULL COMMENT '车辆电瓶电压',
  `value_10` int(11) NOT NULL COMMENT '环境温度',
  `value_11` int(11) NOT NULL COMMENT '长期燃油修正',
  `value_12` int(11) NOT NULL COMMENT '气缸1点火提前角',
  `value_13` int(11) NOT NULL COMMENT '进气歧管绝对压力',
  `value_14` int(11) NOT NULL COMMENT '车辆装配的OBD类型',
  `value_15` int(11) NOT NULL COMMENT '瞬时油耗（按小时计量）',
  `value_16` int(11) NOT NULL COMMENT '瞬时油耗（按百公里计量）',
  `value_17` int(11) DEFAULT NULL COMMENT '当前里程',
  `value_18` int(11) DEFAULT NULL COMMENT '保养里程',
  `value_19` int(11) DEFAULT NULL COMMENT '保养天数',
  `date` datetime NOT NULL COMMENT '数据传送日期',
  `device_id` varchar(32) NOT NULL COMMENT '设备Id',
  `location` varchar(100) NOT NULL COMMENT '设备所在坐标',
  `error` varchar(1000) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT '2012-05-08 11:53:35' COMMENT '插入时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
-- ----------------------------
-- Records of t_sys_message
-- ----------------------------

-- ----------------------------
-- View structure for `v_app_catalog_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_catalog_cn`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_app_catalog_cn` AS select `t`.`id` AS `id`,`t`.`name` AS `name`,`t`.`description` AS `description` from `t_app_catalog` `t` where (`t`.`status` > 0) ;

-- ----------------------------
-- View structure for `v_app_catalog_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_catalog_en`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_app_catalog_en` AS select `t`.`id` AS `id`,`t`.`en_name` AS `name`,`t`.`en_description` AS `description` from `t_app_catalog` `t` where (`t`.`status` > 0) ;

-- ----------------------------
-- View structure for `v_app_download_log_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_download_log_cn`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`localhost` SQL SECURITY DEFINER VIEW `v_app_download_log_cn` AS select `a`.`account_id` AS `account_id`,`a`.`app_id` AS `app_id`,`a`.`version` AS `version`,`b`.`app_name` AS `app_name`,`a`.`download_time` AS `download_time` from (`t_app_download_log` `a` left join `t_application` `b` on((`a`.`app_id` = `b`.`id`))) where (`a`.`id` = (select max(`t_app_download_log`.`id`) from `t_app_download_log` where (`t_app_download_log`.`app_id` = `a`.`app_id`) group by `a`.`app_id`)) ;

-- ----------------------------
-- View structure for `v_app_download_log_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_download_log_en`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`localhost` SQL SECURITY DEFINER VIEW `v_app_download_log_en` AS select `a`.`account_id` AS `account_id`,`a`.`app_id` AS `app_id`,`a`.`version` AS `version`,`b`.`en_name` AS `app_name`,`a`.`download_time` AS `download_time` from (`t_app_download_log` `a` left join `t_application` `b` on((`a`.`app_id` = `b`.`id`))) where (`a`.`id` = (select max(`t_app_download_log`.`id`) from `t_app_download_log` where (`t_app_download_log`.`app_id` = `a`.`app_id`) group by `a`.`app_id`)) ;

-- ----------------------------
-- View structure for `v_app_version_file_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_version_file_cn`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_app_version_file_cn` AS select `t`.`id` AS `id`,`t`.`version` AS `version`,`t`.`size` AS `size`,`t`.`file_path` AS `file_path`,`t`.`new_features` AS `new_features` from `t_app_version_file` `t` where (`t`.`status` > 0) ;

-- ----------------------------
-- View structure for `v_app_version_file_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_version_file_en`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_app_version_file_en` AS select `t`.`id` AS `id`,`t`.`version` AS `version`,`t`.`size` AS `size`,`t`.`file_path` AS `file_path`,`t`.`en_new_features` AS `new_features` from `t_app_version_file` `t` where (`t`.`status` > 0) ;

-- ----------------------------
-- View structure for `v_application_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_application_cn`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`%` SQL SECURITY DEFINER VIEW `v_application_cn` AS select `a`.`id` AS `id`,`a`.`app_name` AS `app_name`,`a`.`icon` AS `icon`,`a`.`big_icon` AS `big_icon`,`g`.`name` AS `developer`,`g`.`website` AS `website`,`g`.`email` AS `email`,`a`.`version` AS `version`,`b`.`name` AS `catalog_name`,`a`.`catalog_id` AS `catalog_id`,`a`.`size` AS `size`,`a`.`app_file_path` AS `app_file_path`,`f`.`display_value` AS `platform`,`h`.`display_value` AS `support_languages`,`a`.`price` AS `price`,`a`.`down_count` AS `down_count`,`a`.`app_level` AS `app_level`,`a`.`description` AS `description`,`a`.`permission_desc` AS `permission_desc`,`a`.`features` AS `features`,`a`.`images` AS `images`,`a`.`update_time` AS `update_time`,`a`.`status` AS `status` from ((((`t_application` `a` left join `t_app_catalog` `b` on((`a`.`catalog_id` = `b`.`id`))) left join `t_base_field` `f` on((`a`.`platform` = `f`.`field_value`))) left join `t_base_field` `h` on((`a`.`support_languages` = `h`.`field_value`))) left join `t_app_developer` `g` on((`a`.`developer` = `g`.`id`))) where ((`a`.`status` > 0) and (`f`.`field` = 'platform') and (`h`.`field` = 'suppor_language')) ;

-- ----------------------------
-- View structure for `v_application_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_application_en`;
CREATE ALGORITHM=UNDEFINED DEFINER=`carit`@`%` SQL SECURITY DEFINER VIEW `v_application_en` AS select `a`.`id` AS `id`,`a`.`en_name` AS `app_name`,`a`.`icon` AS `icon`,`a`.`big_icon` AS `big_icon`,`g`.`name` AS `developer`,`g`.`website` AS `website`,`g`.`email` AS `email`,`a`.`version` AS `version`,`b`.`en_name` AS `catalog_name`,`a`.`catalog_id` AS `catalog_id`,`a`.`size` AS `size`,`a`.`app_file_path` AS `app_file_path`,`f`.`display_value` AS `platform`,`h`.`display_value` AS `support_languages`,`a`.`price` AS `price`,`a`.`down_count` AS `down_count`,`a`.`app_level` AS `app_level`,`a`.`en_description` AS `description`,`a`.`en_permission_desc` AS `permission_desc`,`a`.`en_features` AS `features`,`a`.`images` AS `images`,`a`.`update_time` AS `update_time`,`a`.`status` AS `status` from ((((`t_application` `a` left join `t_app_catalog` `b` on((`a`.`catalog_id` = `b`.`id`))) left join `t_base_field` `f` on((`a`.`platform` = `f`.`field_value`))) left join `t_base_field` `h` on((`a`.`support_languages` = `h`.`field_value`))) left join `t_app_developer` `g` on((`a`.`developer` = `g`.`id`))) where ((`a`.`status` > 0) and (`f`.`field` = 'platform') and (`h`.`field` = 'suppor_language')) ;

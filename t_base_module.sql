/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50524
Source Host           : localhost:3306
Source Database       : market

Target Server Type    : MYSQL
Target Server Version : 50524
File Encoding         : 65001

Date: 2012-06-02 02:53:02
*/

SET FOREIGN_KEY_CHECKS=0;

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

-- ----------------------------
-- View structure for `v_application_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_application_cn`;
CREATE  VIEW `v_application_cn` AS select `a`.`id` AS `id`,`a`.`app_name` AS `app_name`,`a`.`icon` AS `icon`,`a`.`big_icon` AS `big_icon`,`a`.`developer` AS `developer`,`a`.`version` AS `version`,`b`.`name` AS `catalog_name`,`a`.`catalog_id` AS `catalog_id`,`a`.`size` AS `size`,`a`.`app_file_path` AS `app_file_path`,`f`.`display_value` AS `platform`,`h`.`display_value` AS `support_languages`,`a`.`price` AS `price`,`a`.`down_count` AS `down_count`,`a`.`app_level` AS `app_level`,`a`.`description` AS `description`,`a`.`permission_desc` AS `permission_desc`,`a`.`features` AS `features`,`a`.`images` AS `images`,`a`.`update_time` AS `update_time` from (((`t_application` `a` left join `t_app_catalog` `b` on((`a`.`catalog_id` = `b`.`id`))) left join `t_base_field` `f` on((`a`.`platform` = `f`.`field_value`))) left join `t_base_field` `h` on((`a`.`support_languages` = `h`.`field_value`))) where ((`a`.`status` > 0) and (`f`.`field` = 'platform') and (`h`.`field` = 'suppor_language')) ;

-- ----------------------------
-- View structure for `v_application_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_application_en`;
CREATE  VIEW `v_application_en` AS select `a`.`id` AS `id`,`a`.`en_name` AS `app_name`,`a`.`icon` AS `icon`,`a`.`big_icon` AS `big_icon`,`a`.`developer` AS `developer`,`a`.`version` AS `version`,`b`.`en_name` AS `catalog_name`,`a`.`catalog_id` AS `catalog_id`,`a`.`size` AS `size`,`a`.`app_file_path` AS `app_file_path`,`f`.`display_value` AS `platform`,`h`.`display_value` AS `support_languages`,`a`.`price` AS `price`,`a`.`down_count` AS `down_count`,`a`.`app_level` AS `app_level`,`a`.`en_description` AS `description`,`a`.`en_permission_desc` AS `permission_desc`,`a`.`en_features` AS `features`,`a`.`images` AS `images`,`a`.`update_time` AS `update_time` from (((`t_application` `a` left join `t_app_catalog` `b` on((`a`.`catalog_id` = `b`.`id`))) left join `t_base_field` `f` on((`a`.`platform` = `f`.`field_value`))) left join `t_base_field` `h` on((`a`.`support_languages` = `h`.`field_value`))) where ((`a`.`status` > 0) and (`f`.`field` = 'platform') and (`h`.`field` = 'suppor_language')) ;

-- ----------------------------
-- View structure for `v_app_catalog_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_catalog_cn`;
CREATE  VIEW `v_app_catalog_cn` AS select `t`.`id` AS `id`,`t`.`name` AS `name`,`t`.`description` AS `description` from `t_app_catalog` `t` where (`t`.`status` > 0) ;

-- ----------------------------
-- View structure for `v_app_catalog_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_catalog_en`;
CREATE  VIEW `v_app_catalog_en` AS select `t`.`id` AS `id`,`t`.`en_name` AS `name`,`t`.`en_description` AS `description` from `t_app_catalog` `t` where (`t`.`status` > 0) ;

-- ----------------------------
-- View structure for `v_app_download_log_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_download_log_cn`;
CREATE  VIEW `v_app_download_log_cn` AS select `a`.`account_id` AS `account_id`,`a`.`app_id` AS `app_id`,`b`.`app_name` AS `app_name` from (`t_app_download_log` `a` left join `t_application` `b` on((`a`.`app_id` = `b`.`id`))) ;

-- ----------------------------
-- View structure for `v_app_download_log_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_download_log_en`;
CREATE  VIEW `v_app_download_log_en` AS select `a`.`account_id` AS `account_id`,`a`.`app_id` AS `app_id`,`b`.`en_name` AS `app_name` from (`t_app_download_log` `a` left join `t_application` `b` on((`a`.`app_id` = `b`.`id`))) ;

-- ----------------------------
-- View structure for `v_app_version_file_cn`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_version_file_cn`;
CREATE  VIEW `v_app_version_file_cn` AS select `t`.`id` AS `id`,`t`.`version` AS `version`,`t`.`size` AS `size`,`t`.`file_path` AS `file_path`,`t`.`new_features` AS `new_features` from `t_app_version_file` `t` where (`t`.`status` > 0) ;

-- ----------------------------
-- View structure for `v_app_version_file_en`
-- ----------------------------
DROP VIEW IF EXISTS `v_app_version_file_en`;
CREATE  VIEW `v_app_version_file_en` AS select `t`.`id` AS `id`,`t`.`version` AS `version`,`t`.`size` AS `size`,`t`.`file_path` AS `file_path`,`t`.`en_new_features` AS `new_features` from `t_app_version_file` `t` where (`t`.`status` > 0) ;

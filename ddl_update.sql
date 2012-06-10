ALTER TABLE `t_base_user`
ADD UNIQUE INDEX `idx_t_base_nick_name` (`nick_name`) ;

ALTER TABLE `t_base_module`
DROP INDEX `t_base_module_url`;

ALTER TABLE `t_base_field`
DROP INDEX `idx_t_base_field_field_name`;

ALTER TABLE `t_base_field`
DROP INDEX `idx_t_base_field_field`;

ALTER TABLE `t_base_field`
DROP INDEX `idx_t_base_field_field`,
ADD INDEX `idx_t_base_field_field` (`field`) ;

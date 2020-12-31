INSERT INTO `linggongyun_v3`.`tb_industry` (`id`, `industry_type`, `one_level`, `create_date`, `update_date`) VALUES ('1', '咨询服务', NULL, NULL, NULL);
INSERT INTO `linggongyun_v3`.`tb_industry` (`id`, `industry_type`, `one_level`, `create_date`, `update_date`) VALUES ('2', '企业服务', NULL, NULL, NULL);
INSERT INTO `linggongyun_v3`.`tb_industry` (`id`, `industry_type`, `one_level`, `create_date`, `update_date`) VALUES ('3', '房地产投资咨询', '1', NULL, NULL);
INSERT INTO `linggongyun_v3`.`tb_industry` (`id`, `industry_type`, `one_level`, `create_date`, `update_date`) VALUES ('4', '企业管理咨询', '1', NULL, NULL);
INSERT INTO `linggongyun_v3`.`tb_industry` (`id`, `industry_type`, `one_level`, `create_date`, `update_date`) VALUES ('5', '云软件服务', '2', NULL, NULL);
INSERT INTO `linggongyun_v3`.`tb_industry` (`id`, `industry_type`, `one_level`, `create_date`, `update_date`) VALUES ('6', '测量勘察服务', '2', NULL, NULL);
INSERT INTO `linggongyun_v3`.`tb_industry` (`id`, `industry_type`, `one_level`, `create_date`, `update_date`) VALUES ('7', '建设工程监理服务', '2', NULL, NULL);



INSERT INTO `linggongyun_v3`.`tb_invoice_catalog` (`id`, `service_type`, `service_content`, `billing_category`, `create_date`, `update_date`) VALUES ('123', '技术服务', '指气象服务、地震服务、海洋服务、测绘服务、城市规划、环境与生态监测服务等专项技术服务。', '现代服务*技术服务', '2020-12-15 16:19:12', '2020-12-15 16:19:15');
INSERT INTO `linggongyun_v3`.`tb_invoice_catalog` (`id`, `service_type`, `service_content`, `billing_category`, `create_date`, `update_date`) VALUES ('1307971755501895682', '技术咨询', '提供信息、建议、策划、顾问等服务的活动，包括软件、技术等方面的咨询；对技术项目提供可行性论证、技术预测和测试、技术培训、专题技术调查、分析报告和专业知识咨询等。', '研发技术服务＊技术咨询', '2020-12-15 16:19:06', '2020-12-15 16:19:10');
INSERT INTO `linggongyun_v3`.`tb_invoice_catalog` (`id`, `service_type`, `service_content`, `billing_category`, `create_date`, `update_date`) VALUES ('1335419348957016066', '测试', 's测试内容', '开票类目', '2020-12-06 11:02:38', '2020-12-06 11:02:38');


INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('1', 'home_page', '首页', NULL, '0', '2020-09-12 09:14:42', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('2', 'task_management', '任务管理', NULL, '0', '2020-09-12 09:15:35', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('3', 'payment_management', '支付管理', NULL, '0', '2020-09-12 09:16:24', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('4', 'invoice_management', '发票管理', NULL, '0', '2020-09-12 09:19:05', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('5', 'maker_management', '创客管理', NULL, '0', '2020-09-12 09:19:59', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('6', 'customer_profile', '客户基本资料', NULL, '0', '2020-09-12 09:21:10', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('7', 'authority_management', '权限管理', NULL, '0', '2020-09-12 09:22:09', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('8', 'gcandsub_pay', '总包+分包支付', '3', '0', '2020-09-12 09:28:59', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('9', 'crowd_sourcing_pay', '众包支付', '3', '0', '2020-09-12 09:29:38', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('10', 'gcandsub_invoice', '总包+分包发票', '4', '0', '2020-09-12 09:31:20', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('11', 'crowd_sourcing_invoice', '众包发票', '4', '0', '2020-09-12 09:31:48', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('12', 'merchant_management', '商户管理', NULL, '1', '2020-10-10 17:33:58', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('13', '\r\nservice_provider_management', '服务商管理', NULL, '1', '2020-10-10 17:34:44', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('14', 'regulator_management', '监督部门管理', NULL, '1', '2020-10-10 17:35:52', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('15', '\r\norganization_structure', '组织结构', NULL, '1', '2020-10-10 17:37:06', NULL);
INSERT INTO `linggongyun_v3`.`tb_menu` (`id`, `menu_name`, `menu_zhname`, `menu_parent`, `is_merchant`, `create_date`, `update_date`) VALUES ('16', 'applets_settings', '小程序设置', NULL, '1', '2020-10-10 17:38:20', NULL);


INSERT INTO `linggongyun_v3`.`tb_managers` (`id`, `parent_id`, `role_name`, `real_name`, `user_name`, `user_dept`, `user_post`, `mobile_code`, `pass_word`, `user_desc`, `paas_name`, `user_sign`, `status`, `user_head`, `create_date`, `update_date`) VALUES ('1308716289793970178', NULL, 'admin', '张三', 'zhangshang', NULL, NULL, '17777882233', 'esdafwb45cffe084dd3d20d928bee85e7b0f21', NULL, NULL, '2', '0', NULL, '2020-09-23 18:34:13', '2020-09-23 18:34:13');

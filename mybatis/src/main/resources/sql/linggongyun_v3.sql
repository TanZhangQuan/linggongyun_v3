/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : linggongyun_v3

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-02-02 20:07:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_address`
-- ----------------------------
DROP TABLE IF EXISTS `tb_address`;
CREATE TABLE `tb_address` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_id` varchar(50) NOT NULL COMMENT '公司id',
  `link_name` varchar(50) NOT NULL COMMENT '联系人',
  `link_mobile` varchar(50) NOT NULL COMMENT '联系电话',
  `address_name` varchar(100) NOT NULL COMMENT '详细地址',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0为启用，1为停用',
  `is_not` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否默认：0为默认，1为不默认',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='地址表';

-- ----------------------------
-- Records of tb_address
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_agent`
-- ----------------------------
DROP TABLE IF EXISTS `tb_agent`;
CREATE TABLE `tb_agent` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `managers_id` varchar(50) NOT NULL COMMENT '对应的管理人员',
  `sales_man_id` varchar(50) NOT NULL COMMENT '所属业务员(tb_managers表的ID)',
  `company_address` varchar(200) NOT NULL DEFAULT '' COMMENT '代理商地址',
  `link_man` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人',
  `link_mobile` varchar(50) NOT NULL DEFAULT '' COMMENT '代理商电话',
  `contract_file` varchar(500) NOT NULL DEFAULT '' COMMENT '加盟合同URL',
  `agent_name` varchar(50) NOT NULL DEFAULT '' COMMENT '代理商名称',
  `agent_status` tinyint(1) DEFAULT NULL COMMENT '0可用 1禁用',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`managers_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理商信息';

-- ----------------------------
-- Records of tb_agent
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_applet_banner`
-- ----------------------------
DROP TABLE IF EXISTS `tb_applet_banner`;
CREATE TABLE `tb_applet_banner` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `serial_number` int(6) DEFAULT NULL COMMENT '序号',
  `picture` varchar(255) DEFAULT '' COMMENT '图片',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小程序轮播图';

-- ----------------------------
-- Records of tb_applet_banner
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_applet_faq`
-- ----------------------------
DROP TABLE IF EXISTS `tb_applet_faq`;
CREATE TABLE `tb_applet_faq` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `serial_number` int(6) DEFAULT NULL COMMENT '序号',
  `title` varchar(255) DEFAULT '' COMMENT '标题',
  `content` varchar(1000) DEFAULT '' COMMENT '内容',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `serial_number` (`serial_number`) USING BTREE COMMENT '序号唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小程序常见问题';

-- ----------------------------
-- Records of tb_applet_faq
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_applet_other_info`
-- ----------------------------
DROP TABLE IF EXISTS `tb_applet_other_info`;
CREATE TABLE `tb_applet_other_info` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `entry_name` varchar(255) DEFAULT '' COMMENT '项目名称',
  `content` varchar(1000) DEFAULT '' COMMENT '内容',
  `type` varchar(255) NOT NULL DEFAULT '' COMMENT '类型',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小程序其他问题';

-- ----------------------------
-- Records of tb_applet_other_info
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_application_payment`
-- ----------------------------
DROP TABLE IF EXISTS `tb_application_payment`;
CREATE TABLE `tb_application_payment` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `invoice_application_id` varchar(50) NOT NULL DEFAULT '' COMMENT '开票申请id',
  `payment_order_id` varchar(50) NOT NULL DEFAULT '' COMMENT '支付id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_application_payment
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_common_message`
-- ----------------------------
DROP TABLE IF EXISTS `tb_common_message`;
CREATE TABLE `tb_common_message` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `message` varchar(500) NOT NULL DEFAULT '' COMMENT '消息',
  `message_status` varchar(50) NOT NULL DEFAULT '0' COMMENT '消息状态：0未读，1已读',
  `no_order` varchar(50) NOT NULL DEFAULT '' COMMENT '支付订单号（可以是连连支付的订单号）',
  `send_user_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '发送方ID(0为系统消息)',
  `send_user_type` varchar(50) NOT NULL DEFAULT '0' COMMENT '发送消息的用户类型:0创客，1商户，2平台',
  `receive_user_id` varchar(50) NOT NULL DEFAULT '' COMMENT '接收方ID',
  `receive_user_type` varchar(50) NOT NULL DEFAULT '0' COMMENT '接收方用户类型:0创客，1商户，2平台',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息表';

-- ----------------------------
-- Records of tb_common_message
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_company_info`
-- ----------------------------
DROP TABLE IF EXISTS `tb_company_info`;
CREATE TABLE `tb_company_info` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `sales_man_id` varchar(50) NOT NULL COMMENT '业务员ID(tb_managers表中为业务员的ID)',
  `agent_id` varchar(50) NOT NULL DEFAULT '' COMMENT '代理商ID(可以为空,tb_managers表中为代理商的ID)',
  `company_s_name` varchar(20) NOT NULL DEFAULT '' COMMENT '公司的简称',
  `company_logo` varchar(50) NOT NULL DEFAULT '' COMMENT '公司logo',
  `company_size` varchar(50) NOT NULL DEFAULT '' COMMENT '公司规模',
  `company_provice` varchar(50) NOT NULL DEFAULT '' COMMENT '公司所属省份',
  `company_city` varchar(50) NOT NULL DEFAULT '' COMMENT '公司所属城市',
  `company_man` varchar(20) NOT NULL COMMENT '公司的法定人',
  `company_man_id_card` varchar(100) NOT NULL DEFAULT '' COMMENT '公司的法定人的身份证',
  `business_license` varchar(250) NOT NULL COMMENT '公司的营业执照',
  `company_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '公司的简介',
  `company_name` varchar(50) NOT NULL COMMENT '公司全称',
  `address_and_telephone` varchar(100) NOT NULL DEFAULT '' COMMENT '公司地址及电话',
  `bank_name` varchar(50) NOT NULL COMMENT '开户行名称',
  `bank_code` varchar(100) NOT NULL COMMENT '银行卡号',
  `title_of_account` varchar(50) NOT NULL COMMENT '账户名称',
  `bank_and_account` varchar(100) NOT NULL DEFAULT '' COMMENT '开户行及账号',
  `registered_capital` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '注册资本',
  `company_create_date` date DEFAULT NULL COMMENT '公司的成立时间',
  `member_id` varchar(50) NOT NULL DEFAULT '' COMMENT '网商银行会员号',
  `sub_account_no` varchar(50) NOT NULL DEFAULT '' COMMENT '网商银行子账户(智能试别码)',
  `bank_id` varchar(50) NOT NULL DEFAULT '' COMMENT '网商银行银行卡号',
  `link_man` varchar(50) NOT NULL DEFAULT '' COMMENT '公司联系人',
  `link_mobile` varchar(50) NOT NULL DEFAULT '' COMMENT '公司联系电话',
  `contract` varchar(100) NOT NULL DEFAULT '' COMMENT '加盟合同地址',
  `credit_code` varchar(50) NOT NULL COMMENT '统一的社会信用代码',
  `audit_status` tinyint(1) DEFAULT '0' COMMENT '0为审核，1审核',
  `company_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '公司状态0正常',
  `pay_pwd` varchar(50) NOT NULL COMMENT '支付密码',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`company_name`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`credit_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司信息';

-- ----------------------------
-- Records of tb_company_info
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_company_invoice_info`
-- ----------------------------
DROP TABLE IF EXISTS `tb_company_invoice_info`;
CREATE TABLE `tb_company_invoice_info` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_id` varchar(50) NOT NULL DEFAULT '' COMMENT '公司ID',
  `company_name` varchar(50) NOT NULL DEFAULT '' COMMENT '公司全称',
  `address_and_telephone` varchar(100) NOT NULL DEFAULT '' COMMENT '地址电话',
  `bank_and_account` varchar(100) NOT NULL DEFAULT '' COMMENT '开户行及账号',
  `tax_code` varchar(50) NOT NULL DEFAULT '' COMMENT '纳税识别号',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司的开票信息';

-- ----------------------------
-- Records of tb_company_invoice_info
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_company_ladder_service`
-- ----------------------------
DROP TABLE IF EXISTS `tb_company_ladder_service`;
CREATE TABLE `tb_company_ladder_service` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_tax_id` varchar(50) NOT NULL COMMENT '给商户的服务商ID',
  `start_money` decimal(12,2) NOT NULL COMMENT '开始的金额',
  `end_money` decimal(12,2) NOT NULL COMMENT '结束的金额',
  `service_charge` decimal(12,2) NOT NULL COMMENT '服务费（如7.5，不需把百分数换算成小数）',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`company_tax_id`,`start_money`,`end_money`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商给商户的梯度价';

-- ----------------------------
-- Records of tb_company_ladder_service
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_company_tax`
-- ----------------------------
DROP TABLE IF EXISTS `tb_company_tax`;
CREATE TABLE `tb_company_tax` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_id` varchar(50) NOT NULL,
  `tax_id` varchar(50) NOT NULL,
  `charge_status` tinyint(1) DEFAULT '0' COMMENT '费用类型0为一口价，1为梯度价',
  `service_charge` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '一口价的服务费(如果为梯度价这为空)',
  `package_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '合作类型',
  `bank_code` varchar(50) NOT NULL DEFAULT '' COMMENT '银行账号',
  `bank_name` varchar(80) NOT NULL DEFAULT '' COMMENT '开户行(银行名称)',
  `contract` varchar(250) NOT NULL DEFAULT '' COMMENT '合作合同地址',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`company_id`,`tax_id`,`package_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公司与服务商的合作信息表';

-- ----------------------------
-- Records of tb_company_tax
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_company_tax_pay_method`
-- ----------------------------
DROP TABLE IF EXISTS `tb_company_tax_pay_method`;
CREATE TABLE `tb_company_tax_pay_method` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_id` varchar(50) NOT NULL COMMENT '商户ID',
  `tax_id` varchar(50) NOT NULL COMMENT '服务商ID',
  `package_type` varchar(50) NOT NULL COMMENT '合作类型',
  `payment_method` varchar(50) NOT NULL COMMENT '交易方式',
  `bool_enable` bit(1) NOT NULL COMMENT '是否启用',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`company_id`,`tax_id`,`package_type`,`payment_method`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户服务商支付方式';

-- ----------------------------
-- Records of tb_company_tax_pay_method
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_company_unionpay`
-- ----------------------------
DROP TABLE IF EXISTS `tb_company_unionpay`;
CREATE TABLE `tb_company_unionpay` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_id` varchar(50) NOT NULL COMMENT '商户ID',
  `tax_unionpay_id` varchar(50) NOT NULL COMMENT '服务商银联ID',
  `uid` varchar(50) NOT NULL COMMENT '会员标识',
  `sub_account_code` varchar(50) NOT NULL COMMENT '子账户账号',
  `sub_account_name` varchar(50) NOT NULL COMMENT '子账号户名',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`company_id`,`tax_unionpay_id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`uid`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k3` (`sub_account_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户银联信息表';

-- ----------------------------
-- Records of tb_company_unionpay
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_company_worker`
-- ----------------------------
DROP TABLE IF EXISTS `tb_company_worker`;
CREATE TABLE `tb_company_worker` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `worker_id` varchar(50) NOT NULL COMMENT '创客id',
  `company_id` varchar(50) NOT NULL COMMENT '商户ID',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`worker_id`,`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户公司所拥有的创客';

-- ----------------------------
-- Records of tb_company_worker
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_crowd_sourcing_application`
-- ----------------------------
DROP TABLE IF EXISTS `tb_crowd_sourcing_application`;
CREATE TABLE `tb_crowd_sourcing_application` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `payment_order_many_id` varchar(50) NOT NULL DEFAULT '' COMMENT '众包支付id',
  `invoice_catalog_type` varchar(50) NOT NULL DEFAULT '' COMMENT '开票类目',
  `application_address_id` varchar(50) NOT NULL DEFAULT '' COMMENT '申请地址 对应地址表id',
  `application_date` datetime DEFAULT NULL COMMENT '申请时间',
  `application_state` tinyint(1) DEFAULT NULL COMMENT '申请状态：0.未申请；1.申请中；2.已拒绝；3.已开票，4未开票',
  `application_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '申请说明',
  `audit_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '审核说明',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='众包开票申请';

-- ----------------------------
-- Records of tb_crowd_sourcing_application
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_crowd_sourcing_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `tb_crowd_sourcing_invoice`;
CREATE TABLE `tb_crowd_sourcing_invoice` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `payment_order_many_id` varchar(50) NOT NULL DEFAULT '' COMMENT '众包支付id未申请就使用',
  `application_id` varchar(50) NOT NULL DEFAULT '' COMMENT '申请id',
  `invoice_code` varchar(200) NOT NULL DEFAULT '' COMMENT '开票代码KP+0000',
  `invoice_print_date` datetime DEFAULT NULL COMMENT '开票时间',
  `invoice_number` varchar(200) NOT NULL DEFAULT '' COMMENT '发票数字',
  `invoice_code_no` varchar(200) NOT NULL DEFAULT '' COMMENT '发票代码',
  `invoice_print_person` varchar(200) NOT NULL DEFAULT '' COMMENT '购买方',
  `invoice_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '开票金额',
  `invoice_catalog_id` varchar(50) NOT NULL DEFAULT '' COMMENT '开票类目',
  `invoice_url` varchar(500) NOT NULL DEFAULT '' COMMENT '发票Url',
  `tax_receipt_url` varchar(500) NOT NULL DEFAULT '' COMMENT '税票Url',
  `express_sheet_no` varchar(100) NOT NULL DEFAULT '' COMMENT '快递单号',
  `express_company_name` varchar(50) NOT NULL DEFAULT '' COMMENT '快递公司',
  `express_update_datetime` datetime DEFAULT NULL COMMENT '快递更新时间',
  `express_update_person` varchar(50) NOT NULL DEFAULT '' COMMENT '快递更新人员',
  `express_update_person_tel` varchar(50) NOT NULL DEFAULT '' COMMENT '快递更新人员电话',
  `invoice_desc` varchar(200) NOT NULL DEFAULT '' COMMENT '开票说明',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_crowd_sourcing_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_dict`
-- ----------------------------
DROP TABLE IF EXISTS `tb_dict`;
CREATE TABLE `tb_dict` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `parent_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '父主键',
  `code` varchar(50) NOT NULL COMMENT '字典码',
  `dict_key` varchar(50) NOT NULL COMMENT '字典值',
  `dict_value` varchar(50) NOT NULL COMMENT '字典名称',
  `sort` int(5) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '字典备注',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`code`,`dict_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='字典表';

-- ----------------------------
-- Records of tb_dict
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `tb_invoice`;
CREATE TABLE `tb_invoice` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `application_id` varchar(50) NOT NULL DEFAULT '' COMMENT '申请开票id',
  `invoice_code` varchar(50) NOT NULL DEFAULT '' COMMENT '发票编号，FP+0000',
  `invoice_print_date` datetime DEFAULT NULL COMMENT '开票时间',
  `invoice_number` varchar(50) NOT NULL DEFAULT '' COMMENT '发票数字',
  `invoice_code_no` varchar(50) NOT NULL DEFAULT '' COMMENT '发票代码',
  `invoice_print_person` varchar(100) NOT NULL DEFAULT '' COMMENT '开票人(销售方)',
  `application_invoice_person` varchar(50) NOT NULL DEFAULT '' COMMENT '申请开票人(购买方)',
  `invoice_numbers` int(5) NOT NULL DEFAULT '0' COMMENT '发票张数',
  `invoice_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '发票金额',
  `invoice_catalog` varchar(200) NOT NULL DEFAULT '' COMMENT '开票类目',
  `invoice_url` varchar(200) NOT NULL DEFAULT '' COMMENT '发票地址',
  `tax_receipt_url` varchar(200) NOT NULL DEFAULT '' COMMENT '税票地址',
  `is_not_total` tinyint(1) DEFAULT NULL COMMENT '是否为门征单开，0汇总代开，1门征单开',
  `express_sheet_no` varchar(100) NOT NULL DEFAULT '' COMMENT '快递单号',
  `express_company_name` varchar(100) NOT NULL DEFAULT '' COMMENT '快递公司',
  `express_update_datetime` datetime DEFAULT NULL COMMENT '快递更新时间',
  `express_update_person` varchar(100) NOT NULL DEFAULT '' COMMENT '快递更新人员',
  `express_update_person_tel` varchar(100) NOT NULL DEFAULT '' COMMENT '快递更新人员电话',
  `invoice_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '开票说明',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='总包发票';

-- ----------------------------
-- Records of tb_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_invoice_application`
-- ----------------------------
DROP TABLE IF EXISTS `tb_invoice_application`;
CREATE TABLE `tb_invoice_application` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `application_date` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `application_person` varchar(50) NOT NULL DEFAULT '' COMMENT '申请人',
  `invoice_total_amount` varchar(50) NOT NULL DEFAULT '' COMMENT '开票总额',
  `invoice_catalog_type` varchar(200) NOT NULL DEFAULT '' COMMENT '开票类目',
  `application_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '申请说明',
  `application_address` varchar(50) NOT NULL DEFAULT '' COMMENT '地址',
  `application_state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '申请状态：0.未申请；1.申请中；2.已拒绝；3.已开票，4未开票',
  `application_handle_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '处理说明',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_invoice_application
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_invoice_catalog`
-- ----------------------------
DROP TABLE IF EXISTS `tb_invoice_catalog`;
CREATE TABLE `tb_invoice_catalog` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `service_type` varchar(255) NOT NULL DEFAULT '' COMMENT '服务类型',
  `service_content` varchar(255) NOT NULL DEFAULT '' COMMENT '具体服务内容',
  `billing_category` varchar(255) NOT NULL DEFAULT '' COMMENT '开票类目',
  `is_not` bigint(1) NOT NULL DEFAULT '0' COMMENT '是否为系统默认',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_invoice_catalog
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_invoice_ladder_price`
-- ----------------------------
DROP TABLE IF EXISTS `tb_invoice_ladder_price`;
CREATE TABLE `tb_invoice_ladder_price` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `tax_id` varchar(50) NOT NULL DEFAULT '' COMMENT '服务商ID',
  `tax_package_id` varchar(50) NOT NULL DEFAULT '' COMMENT '合作类型ID',
  `start_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '开始的金额',
  `end_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '结束的金额',
  `packaeg_status` tinyint(1) DEFAULT NULL COMMENT '0分包汇总代开，1分包单人单开，2众包单人单开',
  `status` tinyint(1) DEFAULT NULL COMMENT '0月度，1季度',
  `rate` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '服务费（如7.5，不需把百分数换算成小数）',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`tax_id`,`start_money`,`end_money`,`packaeg_status`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商发票税率梯度价';

-- ----------------------------
-- Records of tb_invoice_ladder_price
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_invoice_list`
-- ----------------------------
DROP TABLE IF EXISTS `tb_invoice_list`;
CREATE TABLE `tb_invoice_list` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `invoice_id` varchar(50) NOT NULL DEFAULT '' COMMENT '总包发票',
  `maker_total_invoice_id` varchar(50) NOT NULL DEFAULT '' COMMENT '汇总代开发票id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_invoice_list
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_invoice_payment_inventory`
-- ----------------------------
DROP TABLE IF EXISTS `tb_invoice_payment_inventory`;
CREATE TABLE `tb_invoice_payment_inventory` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `invoice_id` varchar(50) NOT NULL DEFAULT '' COMMENT '发票id',
  `payment_inventory_id` varchar(50) NOT NULL DEFAULT '' COMMENT '支付明细id',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='发票支付清单关联表';

-- ----------------------------
-- Records of tb_invoice_payment_inventory
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_lianlianpay`
-- ----------------------------
DROP TABLE IF EXISTS `tb_lianlianpay`;
CREATE TABLE `tb_lianlianpay` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_id` varchar(50) NOT NULL COMMENT '商户的企业ID',
  `oid_partner` varchar(50) NOT NULL COMMENT '连连商户号',
  `private_key` varchar(1000) NOT NULL COMMENT '连连商户号的私钥',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商户的企业连连支付账户信息表';

-- ----------------------------
-- Records of tb_lianlianpay
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_lianlianpay_tax`
-- ----------------------------
DROP TABLE IF EXISTS `tb_lianlianpay_tax`;
CREATE TABLE `tb_lianlianpay_tax` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `tax_id` varchar(50) NOT NULL COMMENT '商户的企业ID',
  `oid_partner` varchar(50) NOT NULL COMMENT '连连商户号',
  `private_key` varchar(1000) NOT NULL COMMENT '连连商户号的私钥',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`tax_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商连连支付账户信息表';

-- ----------------------------
-- Records of tb_lianlianpay_tax
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_linkman`
-- ----------------------------
DROP TABLE IF EXISTS `tb_linkman`;
CREATE TABLE `tb_linkman` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_id` varchar(50) NOT NULL DEFAULT '' COMMENT '商户ID',
  `link_name` varchar(20) NOT NULL DEFAULT '' COMMENT '联系人',
  `link_mobile` varchar(50) NOT NULL DEFAULT '' COMMENT '联系电话',
  `post` varchar(50) NOT NULL DEFAULT '' COMMENT '职位',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `is_not` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否默认：0为默认，1为不默认',
  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '0为启用，1为停用',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人表';

-- ----------------------------
-- Records of tb_linkman
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_maker_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `tb_maker_invoice`;
CREATE TABLE `tb_maker_invoice` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `payment_inventory_id` varchar(50) NOT NULL DEFAULT '' COMMENT '创客支付ID',
  `invoice_type_no` varchar(50) NOT NULL DEFAULT '' COMMENT '发票代码',
  `invoice_serial_no` varchar(50) NOT NULL DEFAULT '' COMMENT '发票号码',
  `maker_voice_get_date_time` datetime DEFAULT NULL COMMENT '发票开具日期',
  `invoice_category` varchar(50) NOT NULL DEFAULT '' COMMENT '服务名称',
  `total_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '开票金额',
  `tax_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '税额合计',
  `ivoice_person` varchar(50) NOT NULL DEFAULT '' COMMENT '开票人',
  `sale_company` varchar(50) NOT NULL DEFAULT '' COMMENT '销售方名称',
  `help_make_organation_name` varchar(50) NOT NULL DEFAULT '' COMMENT '代开机关名称',
  `help_make_company` varchar(50) NOT NULL DEFAULT '' COMMENT '代开企业名称',
  `help_make_tax_no` varchar(50) NOT NULL DEFAULT '' COMMENT '代开企业税号',
  `maker_voice_url` varchar(200) NOT NULL DEFAULT '' COMMENT '发票URL',
  `maker_tax_url` varchar(200) NOT NULL DEFAULT '' COMMENT '分包税票',
  `maker_voice_upload_date_time` datetime DEFAULT NULL COMMENT '发票上传日期',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_maker_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_maker_total_invoice`
-- ----------------------------
DROP TABLE IF EXISTS `tb_maker_total_invoice`;
CREATE TABLE `tb_maker_total_invoice` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `invoice_type_no` varchar(100) NOT NULL DEFAULT '' COMMENT '发票代码',
  `invoice_serial_no` varchar(100) NOT NULL DEFAULT '' COMMENT '发票号码',
  `invoice_date` datetime DEFAULT NULL COMMENT '开票日期',
  `invoice_category` varchar(200) NOT NULL DEFAULT '' COMMENT '服务类型',
  `total_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '价税合计',
  `tax_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '税额总价',
  `invoice_person` varchar(50) NOT NULL DEFAULT '' COMMENT '开票人',
  `sale_company` varchar(50) NOT NULL DEFAULT '' COMMENT '销售方名称',
  `maker_invoice_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '开票说明',
  `maker_invoice_url` varchar(500) NOT NULL DEFAULT '' COMMENT '分包发票url',
  `maker_tax_url` varchar(300) NOT NULL DEFAULT '' COMMENT '分包完税证明URL',
  `maker_voice_upload_date_time` datetime DEFAULT NULL COMMENT '发票上传日期',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_maker_total_invoice
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_managers`
-- ----------------------------
DROP TABLE IF EXISTS `tb_managers`;
CREATE TABLE `tb_managers` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `parent_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '判断是否为子账号0则不是',
  `role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `real_name` varchar(20) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '登录用户名',
  `user_dept` varchar(20) NOT NULL DEFAULT '' COMMENT '部门',
  `user_post` varchar(20) NOT NULL DEFAULT '' COMMENT '岗位',
  `mobile_code` varchar(50) NOT NULL DEFAULT '' COMMENT '手机号',
  `pass_word` varchar(50) NOT NULL DEFAULT '' COMMENT '登录密码',
  `user_desc` varchar(200) NOT NULL DEFAULT '' COMMENT '用户的相关说明',
  `paas_name` varchar(50) NOT NULL DEFAULT '' COMMENT '平台名称',
  `user_sign` tinyint(1) DEFAULT NULL COMMENT '1渠道商，2业务员，3管理员',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0正常，1停用',
  `user_head` varchar(100) NOT NULL DEFAULT '' COMMENT '头像',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

-- ----------------------------
-- Records of tb_managers
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_menu`
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `menu_name` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单的名称',
  `menu_zhname` varchar(50) NOT NULL DEFAULT '' COMMENT '中文',
  `menu_parent` varchar(50) NOT NULL DEFAULT '0' COMMENT '菜单的父菜单',
  `is_merchant` tinyint(1) DEFAULT NULL COMMENT '是否为商户的权限 0是 1不是 2平台',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_merchant`
-- ----------------------------
DROP TABLE IF EXISTS `tb_merchant`;
CREATE TABLE `tb_merchant` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `parent_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '等为主账号时为0，为子账号时是主账号的id',
  `company_id` varchar(50) NOT NULL COMMENT '公司ID（用来关联公司获取所属公司的信息）',
  `company_name` varchar(50) NOT NULL DEFAULT '' COMMENT '商户公司名称',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `pass_word` varchar(100) NOT NULL DEFAULT '' COMMENT '登录密码',
  `login_mobile` varchar(50) NOT NULL DEFAULT '' COMMENT '登录时用的手机号码',
  `head_portrait` varchar(500) NOT NULL DEFAULT '' COMMENT '头像',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '商户状态0正常，1停用',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`user_name`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`login_mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户信息';

-- ----------------------------
-- Records of tb_merchant
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_object_menu`
-- ----------------------------
DROP TABLE IF EXISTS `tb_object_menu`;
CREATE TABLE `tb_object_menu` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `menu_id` varchar(50) NOT NULL DEFAULT '' COMMENT '菜单ID',
  `object_user_id` varchar(50) NOT NULL DEFAULT '' COMMENT '用户',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_object_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_payment_history`
-- ----------------------------
DROP TABLE IF EXISTS `tb_payment_history`;
CREATE TABLE `tb_payment_history` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `trade_no` varchar(50) NOT NULL COMMENT '支付订单号',
  `outer_trade_no` varchar(50) NOT NULL DEFAULT '' COMMENT '第三方订单号',
  `order_type` varchar(50) NOT NULL COMMENT '交易类型',
  `order_id` varchar(50) NOT NULL DEFAULT '' COMMENT '交易类型ID',
  `payment_method` varchar(50) NOT NULL COMMENT '交易方式',
  `trade_object` varchar(50) NOT NULL COMMENT '交易对象类型',
  `trade_object_id` varchar(50) NOT NULL COMMENT '交易对象ID',
  `amount` decimal(12,2) NOT NULL COMMENT '交易金额',
  `trade_status` varchar(50) NOT NULL COMMENT '交易结果',
  `trade_fail_reason` varchar(100) NOT NULL DEFAULT '' COMMENT '交易失败原因',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易记录';

-- ----------------------------
-- Records of tb_payment_history
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_payment_inventory`
-- ----------------------------
DROP TABLE IF EXISTS `tb_payment_inventory`;
CREATE TABLE `tb_payment_inventory` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `payment_order_id` varchar(50) NOT NULL COMMENT '支付单ID',
  `worker_id` varchar(50) NOT NULL COMMENT '创客ID',
  `worker_name` varchar(50) NOT NULL DEFAULT '' COMMENT '创客姓名',
  `mobile_code` varchar(50) NOT NULL DEFAULT '' COMMENT '创客电话',
  `id_card_code` varchar(50) NOT NULL DEFAULT '' COMMENT '创客身份证号码',
  `bank_name` varchar(50) NOT NULL DEFAULT '' COMMENT '开户行',
  `bank_code` varchar(50) NOT NULL DEFAULT '' COMMENT '创客的银行账号',
  `task_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '任务金额',
  `real_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '创客的实际到手的金额',
  `service_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '服务费',
  `composite_tax` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '服务税率',
  `tax_rate` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '纳税率',
  `tax_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '纳税金额',
  `merchant_payment_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '商户支付金额',
  `attestation` tinyint(1) NOT NULL DEFAULT '0' COMMENT '实名认证状态(0未认证，1已认证)',
  `payment_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '支付状态：-1支付失败，0未支付，1支付成功',
  `package_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0总包，1众包',
  `trade_no` varchar(50) NOT NULL COMMENT '订单号',
  `trade_fail_reason` varchar(100) NOT NULL DEFAULT '' COMMENT '交易失败原因',
    `agent_difference` decimal(15,2) DEFAULT '0.00' COMMENT '代理商差额佣金',
  `salesman_difference` decimal(15,2) DEFAULT '0.00' COMMENT '业务员差额佣金'
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付清单明细';

-- ----------------------------
-- Records of tb_payment_inventory
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_payment_order`
-- ----------------------------
DROP TABLE IF EXISTS `tb_payment_order`;
CREATE TABLE `tb_payment_order` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_id` varchar(50) NOT NULL DEFAULT '' COMMENT '商户的公司ID',
  `company_s_name` varchar(50) NOT NULL DEFAULT '' COMMENT '商户的公司简称',
  `real_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '商户支付总金额',
  `service_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总服务费',
  `worker_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '付给创客的金额(分包总金额)',
  `tax_id` varchar(50) NOT NULL DEFAULT '' COMMENT '平台服务商ID',
  `platform_service_provider` varchar(50) NOT NULL DEFAULT '' COMMENT '平台服务商名称',
  `company_contract` varchar(500) NOT NULL DEFAULT '' COMMENT '项目合同（存储位置）',
  `payment_inventory` varchar(500) NOT NULL DEFAULT '' COMMENT '支付清单（存储位置）',
  `turnkey_project_payment` varchar(500) NOT NULL DEFAULT '' COMMENT '总包支付回单（存储位置）',
  `subpackage_payment` varchar(500) NOT NULL DEFAULT '' COMMENT '分包支付回单（存储位置）',
  `is_invoice` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否申请总包开票：0为申请,1已申请',
  `is_subpackage` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否分包开票0，为开票 1，已开票',
  `is_not_invoice` tinyint(1) DEFAULT NULL COMMENT '是否开票',
  `task_id` varchar(50) NOT NULL DEFAULT '' COMMENT '关联的任务(可以不关联)',
  `task_name` varchar(50) NOT NULL DEFAULT '' COMMENT '任务名称',
  `acceptance_certificate` varchar(100) NOT NULL DEFAULT '' COMMENT '支付验收单（存储位置）',
  `tax_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0商户承担，1创客承担，2商户创客共同承担',
  `composite_tax` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '综合税率',
  `merchant_tax` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '商户承担的税率的百分比（如50，不需要把百分比换算成小数）',
  `recevice_tax` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '创客承担的税率的百分比（如50，不需要把百分比换算成小数）',
  `payment_mode` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0线下支付',
  `payment_order_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '支付订单的状态-1支付失败,0申请中，1待支付，2已支付，3已确认收款,4支付中（可能支付失败，用来避免重复支付）5已驳回 6已完成',
  `merchant_id` varchar(50) NOT NULL DEFAULT '' COMMENT '支付人ID',
  `payment_date` datetime DEFAULT NULL COMMENT '支付时间',
  `trade_no` varchar(50) NOT NULL COMMENT '订单号',
  `reasons_for_rejection` varchar(100) NOT NULL DEFAULT '' COMMENT '驳回理由',
  `trade_fail_reason` varchar(100) NOT NULL DEFAULT '' COMMENT '交易失败原因',
    `total_agent_difference` decimal(15,2) DEFAULT '0.00' COMMENT '代理商差额佣金',
  `total_salesman_difference` decimal(15,2) DEFAULT '0.00' COMMENT '业务员差额佣金',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付单信息';

-- ----------------------------
-- Records of tb_payment_order
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_payment_order_many`
-- ----------------------------
DROP TABLE IF EXISTS `tb_payment_order_many`;
CREATE TABLE `tb_payment_order_many` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `company_id` varchar(50) NOT NULL COMMENT '商户的公司ID',
  `company_s_name` varchar(50) NOT NULL DEFAULT '' COMMENT '商户的公司简称',
  `real_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
  `service_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总服务费',
  `tax_id` varchar(50) NOT NULL DEFAULT '' COMMENT '平台服务商ID',
  `platform_service_provider` varchar(50) NOT NULL DEFAULT '' COMMENT '平台服务商',
  `company_contract` varchar(500) NOT NULL DEFAULT '' COMMENT '项目合同（存储位置）',
  `payment_inventory` varchar(500) NOT NULL DEFAULT '' COMMENT '支付清单（存储位置）',
  `many_payment` varchar(500) NOT NULL DEFAULT '' COMMENT '众包支付回单（存储位置）',
  `task_id` varchar(50) NOT NULL DEFAULT '' COMMENT '关联的任务(可以不关联)',
  `task_name` varchar(50) NOT NULL DEFAULT '' COMMENT '任务名称',
  `acceptance_certificate` varchar(500) NOT NULL DEFAULT '' COMMENT '支付验收单（存储位置）',
  `tax_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0商户承担，1创客承担，2商户创客共同承担',
  `composite_tax` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '综合税率',
  `merchant_tax` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '商户承担的税率的百分比（如50，不需要把百分比换算成小数）',
  `recevice_tax` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '创客承担的税率的百分比（如50，不需要把百分比换算成小数）',
  `payment_mode` tinyint(1) NOT NULL DEFAULT '0' COMMENT '支付方式：0线下支付',
  `payment_order_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '支付订单的状态 0申请中，1待支付，2支付中，3已完成，4已取消',
  `merchant_id` varchar(50) NOT NULL DEFAULT '' COMMENT '付款的商户ID',
  `payment_date` datetime DEFAULT NULL COMMENT '支付时间',
  `is_application` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否申请,0未申请，1已申请',
  `is_not_invoice` tinyint(1) DEFAULT NULL COMMENT '是否开票',
  `trade_no` varchar(50) NOT NULL COMMENT '订单号',
  `reasons_for_rejection` varchar(100) NOT NULL DEFAULT '' COMMENT '驳回理由',
  `trade_fail_reason` varchar(100) NOT NULL DEFAULT '' COMMENT '交易失败原因',
    `total_agent_difference` decimal(15,2) DEFAULT '0.00' COMMENT '代理商差额佣金',
  `total_salesman_difference` decimal(15,2) DEFAULT '0.00' COMMENT '业务员差额佣金',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`trade_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='众包支付单信息';

-- ----------------------------
-- Records of tb_payment_order_many
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_regulator`
-- ----------------------------
DROP TABLE IF EXISTS `tb_regulator`;
CREATE TABLE `tb_regulator` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `regulator_name` varchar(100) NOT NULL COMMENT '监管部门名称',
  `address` varchar(200) NOT NULL COMMENT '地址',
  `linkman` varchar(20) NOT NULL COMMENT '联系人',
  `link_mobile` varchar(30) NOT NULL COMMENT '联系电话',
  `user_name` varchar(50) NOT NULL COMMENT '登录账号',
  `pass_word` varchar(100) NOT NULL COMMENT '登录密码',
  `head_portrait` varchar(500) NOT NULL DEFAULT '' COMMENT '头像',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0启用，1停用',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监管部门';

-- ----------------------------
-- Records of tb_regulator
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_regulator_tax`
-- ----------------------------
DROP TABLE IF EXISTS `tb_regulator_tax`;
CREATE TABLE `tb_regulator_tax` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `tax_id` varchar(50) NOT NULL COMMENT '服务商ID',
  `regulator_id` varchar(50) NOT NULL COMMENT '监管部门ID',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '状态 0开启监管，1关闭监管',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='监管部门监管的服务商';

-- ----------------------------
-- Records of tb_regulator_tax
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_task`
-- ----------------------------
DROP TABLE IF EXISTS `tb_task`;
CREATE TABLE `tb_task` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `merchant_id` varchar(50) NOT NULL DEFAULT '' COMMENT '商户ID(用来判断是哪个商户发布的任务)',
  `invoice_id` varchar(50) NOT NULL DEFAULT '' COMMENT '任务的发票信息',
  `task_code` varchar(200) NOT NULL DEFAULT '' COMMENT '任务编号',
  `task_name` varchar(200) NOT NULL DEFAULT '' COMMENT '任务名称',
  `task_desc` varchar(500) NOT NULL DEFAULT '' COMMENT '任务说明',
  `task_illustration` varchar(200) NOT NULL DEFAULT '' COMMENT '任务说明图文',
  `task_cost_min` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '最小费用',
  `task_cost_max` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '最大费用',
  `task_skill` varchar(255) NOT NULL DEFAULT '' COMMENT '创客所需技能',
  `upper_limit` int(5) NOT NULL DEFAULT '0' COMMENT '任务所需人数',
  `cooperate_mode` varchar(50) NOT NULL COMMENT '合作类型 0总包+分包  1众包',
  `task_mode` varchar(50) NOT NULL COMMENT '任务模式 0派单，1抢单，2混合',
  `state` tinyint(1) NOT NULL DEFAULT '0' COMMENT '任务状态,0发布中,1已关单,2验收中,3已完毕,4已作废',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务表';

-- ----------------------------
-- Records of tb_task
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_tax`
-- ----------------------------
DROP TABLE IF EXISTS `tb_tax`;
CREATE TABLE `tb_tax` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `tax_name` varchar(50) NOT NULL COMMENT '公司全称',
  `credit_code` varchar(50) NOT NULL COMMENT '统一社会信用代码',
  `tax_man` varchar(50) NOT NULL COMMENT '法定人',
  `tax_address` varchar(100) NOT NULL DEFAULT '' COMMENT '公司详细地址',
  `tax_create_date` date DEFAULT NULL COMMENT '公司成立时间',
  `link_man` varchar(50) NOT NULL COMMENT '联系人',
  `link_position` varchar(50) NOT NULL COMMENT '联系人职位',
  `link_mobile` varchar(50) NOT NULL COMMENT '联系人手机号',
  `link_email` varchar(50) NOT NULL COMMENT '联系人邮箱',
  `invoice_enterprise_name` varchar(50) NOT NULL COMMENT '开票资料-公司名称',
  `invoice_tax_no` varchar(50) NOT NULL COMMENT '开票资料-纳税识别号',
  `invoice_address_phone` varchar(100) NOT NULL COMMENT '开票资料-地址电话',
  `invoice_bank_name_account` varchar(100) NOT NULL COMMENT '开票资料-开户银行及账号',
  `title_of_account` varchar(50) NOT NULL COMMENT '收款单位名称',
  `bank_code` varchar(50) NOT NULL COMMENT '收款单位账号',
  `bank_name` varchar(50) NOT NULL COMMENT '开户行名称',
  `receipt_name` varchar(50) NOT NULL COMMENT '收件人',
  `receipt_phone` varchar(50) NOT NULL COMMENT '收件人手机号',
  `receipt_address` varchar(50) NOT NULL COMMENT '开户行名称',
  `business_license` varchar(500) NOT NULL COMMENT '营业执照',
  `tax_man_idcard` varchar(500) NOT NULL COMMENT '法人身份证',
  `join_contract` varchar(500) NOT NULL COMMENT '平台加盟合同',
  `member_id` varchar(50) NOT NULL DEFAULT '' COMMENT '网商银行会员号',
  `sub_account_no` varchar(50) NOT NULL DEFAULT '' COMMENT '网商银行子账户唯一识别码',
  `tax_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '公司状态 0正常，1停用',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`tax_name`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`credit_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务商公司信息';

-- ----------------------------
-- Records of tb_tax
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_tax_package`
-- ----------------------------
DROP TABLE IF EXISTS `tb_tax_package`;
CREATE TABLE `tb_tax_package` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `tax_id` varchar(50) NOT NULL DEFAULT '' COMMENT '服务商',
  `tax_price` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '一口价综合服务税率',
  `package_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0总包，1众包',
  `support_category` varchar(2000) NOT NULL DEFAULT '' COMMENT '支持的类目ID 逗号分隔 全量更新',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`tax_id`,`package_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='服务商的总包众包信息';

-- ----------------------------
-- Records of tb_tax_package
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_tax_unionpay`
-- ----------------------------
DROP TABLE IF EXISTS `tb_tax_unionpay`;
CREATE TABLE `tb_tax_unionpay` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `tax_id` varchar(50) NOT NULL COMMENT '服务商ID',
  `unionpay_bank_type` varchar(50) NOT NULL COMMENT '银行类型',
  `pfmpubkey` varchar(1000) NOT NULL COMMENT '平台公钥',
  `prikey` varchar(1000) NOT NULL COMMENT '合作方私钥',
  `merchno` varchar(50) NOT NULL COMMENT '商户号',
  `acctno` varchar(50) NOT NULL COMMENT '平台帐户账号',
  `clear_no` varchar(50) NOT NULL COMMENT '清分子账户',
  `service_charge_no` varchar(50) NOT NULL COMMENT '手续费子账户',
  `bool_enable` bit(1) NOT NULL COMMENT '是否启用',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`tax_id`,`unionpay_bank_type`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`merchno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商银联信息表';

-- ----------------------------
-- Records of tb_tax_unionpay
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_tax_worker`
-- ----------------------------
DROP TABLE IF EXISTS `tb_tax_worker`;
CREATE TABLE `tb_tax_worker` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `parent_id` varchar(50) NOT NULL DEFAULT '0' COMMENT '主账号时为0，为子账号时是主账号的id',
  `tax_id` varchar(50) NOT NULL COMMENT '服务商ID',
  `user_name` varchar(50) NOT NULL COMMENT '用户名',
  `role_name` varchar(50) NOT NULL DEFAULT '' COMMENT '角色名称',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `pass_word` varchar(100) NOT NULL COMMENT '登录密码',
  `login_mobile` varchar(50) NOT NULL COMMENT '登录时用的手机号码',
  `head_portrait` varchar(500) NOT NULL DEFAULT '' COMMENT '头像',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '商户状态 0正常，1停用',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`user_name`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k2` (`login_mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商户信息';

-- ----------------------------
-- Records of tb_tax_worker
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_worker`
-- ----------------------------
DROP TABLE IF EXISTS `tb_worker`;
CREATE TABLE `tb_worker` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `wx_id` varchar(50) NOT NULL DEFAULT '' COMMENT '微信关联ID(openid)',
  `wx_name` varchar(50) NOT NULL DEFAULT '' COMMENT '微信名称',
  `worker_name` varchar(200) NOT NULL DEFAULT '' COMMENT '创客姓名',
  `account_name` varchar(50) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `worker_sex` tinyint(1) NOT NULL DEFAULT '0' COMMENT '创客性别',
  `mobile_code` varchar(50) NOT NULL DEFAULT '' COMMENT '创客电话号码',
  `idcard_code` varchar(50) NOT NULL DEFAULT '' COMMENT '创客身份证',
  `bank_name` varchar(50) NOT NULL DEFAULT '' COMMENT '开户行',
  `bank_code` varchar(50) NOT NULL DEFAULT '' COMMENT '银行卡号',
  `member_id` varchar(50) NOT NULL DEFAULT '' COMMENT '网商银行会员号。',
  `sub_account_no` varchar(50) NOT NULL DEFAULT '' COMMENT '子账户（智能识别码）',
  `bank_id` varchar(50) NOT NULL DEFAULT '' COMMENT '银行卡id，银行卡在交易见证平台的绑卡id',
  `worker_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '注册状态（0小程序端注册，1商户导入，2导入支付清单时注册）',
  `attestation` tinyint(1) NOT NULL DEFAULT '0' COMMENT '实名认证状态（0未认证，1已认证）',
  `idcard_front` varchar(200) NOT NULL DEFAULT '' COMMENT '身份证正面',
  `idcard_back` varchar(200) NOT NULL DEFAULT '' COMMENT '身份证反面',
  `attestation_video` varchar(200) NOT NULL DEFAULT '' COMMENT '认证视频',
  `user_name` varchar(20) NOT NULL DEFAULT '' COMMENT '登录用户名',
  `user_pwd` varchar(50) NOT NULL DEFAULT '' COMMENT '登陆密码',
  `skill` varchar(100) NOT NULL DEFAULT '' COMMENT '主要技能',
  `agreement_sign` tinyint(1) NOT NULL DEFAULT '0' COMMENT '加盟合同(签约状态0未签约，1签约中,2签约成功，3签约失败)',
  `business_license` varchar(500) NOT NULL DEFAULT '' COMMENT '营业执照',
  `credit_code` varchar(100) NOT NULL DEFAULT '' COMMENT '社会统一代码',
  `agreement_url` varchar(500) NOT NULL DEFAULT '' COMMENT '合同地址',
  `head_portraits` varchar(500) NOT NULL DEFAULT '' COMMENT '头像',
  `picture` varchar(500) NOT NULL DEFAULT '' COMMENT '照片',
  `contract_num` varchar(500) NOT NULL DEFAULT '' COMMENT '合同编号',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='创客表';

-- ----------------------------
-- Records of tb_worker
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_worker_bank`
-- ----------------------------
DROP TABLE IF EXISTS `tb_worker_bank`;
CREATE TABLE `tb_worker_bank` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `worker_id` varchar(50) NOT NULL DEFAULT '' COMMENT '创客ID',
  `real_name` varchar(50) NOT NULL DEFAULT '' COMMENT '户名',
  `bank_name` varchar(50) NOT NULL DEFAULT '' COMMENT '开户行',
  `bank_code` varchar(50) NOT NULL DEFAULT '' COMMENT '银行卡号',
  `sort` tinyint(1) NOT NULL DEFAULT '0' COMMENT '优先度（越小越先）',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='创客绑定的银行卡号';

-- ----------------------------
-- Records of tb_worker_bank
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_worker_task`
-- ----------------------------
DROP TABLE IF EXISTS `tb_worker_task`;
CREATE TABLE `tb_worker_task` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `worker_id` varchar(50) NOT NULL COMMENT '创客id',
  `task_id` varchar(50) NOT NULL COMMENT '任务id',
  `task_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除 0表示接单成功，1表示被剔除',
  `get_type` tinyint(1) NOT NULL COMMENT '获得方式 0抢单获得；1派单获得',
  `achievement_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '工作成果说明',
  `achievement_files` varchar(100) NOT NULL DEFAULT '' COMMENT '工作成果附件,可以多个文件',
  `achievement_date` datetime DEFAULT NULL COMMENT '提交工作成果日期',
  `check_money` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '验收金额',
  `check_person` varchar(100) NOT NULL DEFAULT '' COMMENT '验收人员',
  `check_date` datetime DEFAULT NULL COMMENT '验收日期',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '创客完成状态 0进行中 1已完成 2已失效 3已提交 4已验收',
  `arrange_person` varchar(100) NOT NULL DEFAULT '' COMMENT '派单人员',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `update_date` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


//新加的表
DROP TABLE IF EXISTS `tb_achievement_statistics`;
CREATE TABLE `tb_achievement_statistics` (
  `id` varchar(36) NOT NULL,
  `object_id` varchar(36) NOT NULL COMMENT '对象id,区分代理商和业务员',
  `object_type` int(11) NOT NULL DEFAULT '0' COMMENT '0代表业务，1代表代理商',
  `merchant_count` int(11) NOT NULL DEFAULT '0' COMMENT '直属商户数',
  `merchant_business` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '直属商户流水',
  `agent_count` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '下级代理数',
  `agent_business` decimal(15,2) NOT NULL DEFAULT '0.00' COMMENT '下级代理流水',
  `merchant_commission` decimal(15,2) DEFAULT '0.00' COMMENT '直属商户流水提成',
  `agent_commission` decimal(15,2) DEFAULT '0.00' COMMENT '代理商流水提成',
  `merchant_difference` decimal(15,2) DEFAULT '0.00' COMMENT '商户差价提成',
  `agent_difference` decimal(15,2) DEFAULT '0.00' COMMENT '代理商差价提成',
  `total_commission` decimal(15,2) DEFAULT '0.00' COMMENT '总提成',
  `agent_rate` decimal(15,2) DEFAULT '0.00' COMMENT '代理商流水提成汇率',
  `merchant_rate` decimal(15,2) DEFAULT '0.00' COMMENT '直属商户流水提成汇率',
  `settlement_state` int(1) NOT NULL DEFAULT '0' COMMENT '结算状态 0 未结算，1已结算',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tb_agent_ladder_service`;
CREATE TABLE `tb_agent_ladder_service` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `agent_tax_id` varchar(36) DEFAULT NULL COMMENT '给代理商的服务商ID',
  `start_money` decimal(18,2) DEFAULT NULL COMMENT '开始的金额',
  `end_money` decimal(18,2) DEFAULT NULL COMMENT '结束的金额',
  `service_charge` decimal(18,2) DEFAULT NULL COMMENT '服务费（如7.5，不需把百分数换算成小数）',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务商给代理商的梯度价';

DROP TABLE IF EXISTS `tb_agent_tax`;
CREATE TABLE `tb_agent_tax` (
  `id` varchar(50) NOT NULL COMMENT '主键',
  `tax_id` varchar(36) NOT NULL,
  `agent_id` varchar(36) NOT NULL,
  `charge_status` int(2) DEFAULT '0' COMMENT '费用类型0为一口价，1为梯度价',
  `service_charge` decimal(18,2) DEFAULT NULL COMMENT '一口价的服务费(如果为梯度价这为空)',
  `package_status` int(2) NOT NULL DEFAULT '0' COMMENT '合作类型',
  `contract` varchar(250) DEFAULT NULL COMMENT '合作合同地址',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_icr1qhlwx3lsd0terqn7w65k1` (`tax_id`,`agent_id`,`package_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代理商和服务商的合作信息表';

DROP TABLE IF EXISTS `tb_commission_proportion`;
CREATE TABLE `tb_commission_proportion` (
  `id` varchar(36) NOT NULL,
  `object_id` varchar(36) NOT NULL COMMENT '对象id,区分代理商和业务员',
  `object_type` int(11) NOT NULL DEFAULT '0' COMMENT '0代表业务，1代表代理商',
  `customer_type` int(11) NOT NULL DEFAULT '0' COMMENT '0代表直客，1代表代理商的',
  `start_money` decimal(15,2) NOT NULL COMMENT '区间开始金额',
  `end_money` decimal(15,2) NOT NULL COMMENT '区间结束金额',
  `commission_rate` decimal(15,2) NOT NULL COMMENT '纳税率',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_worker_task
-- ----------------------------

-- ----------------------------
-- View structure for `tb_merchant_payment_list`
-- ----------------------------
DROP VIEW IF EXISTS `tb_merchant_payment_list`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`%` SQL SECURITY DEFINER VIEW `tb_merchant_payment_list` AS (select `b`.`id` AS `payment_order_id`,`b`.`company_id` AS `company_id`,`b`.`company_s_name` AS `merchant_name`,`b`.`platform_service_provider` AS `tax_name`,(case when isnull(`b`.`id`) then 1 else 0 end) AS `package_status`,`b`.`real_money` AS `real_money`,`b`.`is_invoice` AS `is_invoice`,`b`.`payment_date` AS `payment_date` from (`tb_payment_order` `b` left join `tb_merchant` `a` on((`a`.`company_id` = `b`.`company_id`)))) union (select `b`.`id` AS `payment_order_id`,`b`.`company_id` AS `company_id`,`b`.`company_s_name` AS `merchant_name`,`b`.`platform_service_provider` AS `tax_name`,(case when isnull(`b`.`id`) then 0 else 1 end) AS `package_status`,`b`.`real_money` AS `real_money`,`b`.`is_application` AS `is_invoice`,`b`.`payment_date` AS `payment_date` from (`tb_payment_order_many` `b` left join `tb_merchant` `a` on((`a`.`company_id` = `b`.`company_id`)))) ;

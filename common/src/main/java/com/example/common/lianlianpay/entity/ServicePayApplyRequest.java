/**
 * Copyright © 2004-2019 LianlianPay.All Rights Reserved.
 */
/**
 * Copyright (c) 2013-2019 All Rights Reserved.
 */
package com.example.common.lianlianpay.entity;


/**
 * 代发申请请求参数
 * 
 * @author wuliang
 * @version $Id: ServicePayApplyRequest.java, v 0.1 2019年8月15日 下午5:37:03 wuliang Exp $
 */
public class ServicePayApplyRequest extends BaseRequestBean {

    /**  */
    private static final long serialVersionUID = -1050535728269142461L;

    /**
     * 商户唯一订单号（非空）
     */
    private String order_no;

    /**
     * 商户订单时间,格式：YYYYMMDDHHMMSS
     */
    private String order_time;

    /**
     * 订单说明，透传返回
     */
    private String order_info;

    /**
     * 服务商编号，连薪分配给服务商企业的唯一编号
     */
    private String service_partner;

    /**
     * 个人姓名
     */
    private String staff_name;

    /**
     * 证件类型
     */
    private String cert_type;

    /**
     * 证件号码
     */
    private String cert_no;

    /**
     * 个人手机号
     */
    private String telephone;

    /**
     * 银行卡号
     */
    private String salary_card_no;

    /**
     * 资金类型（税前：PRE_TAX 税后：AFTER_TAX）
     */
    private String salary_type;

    /**
     * 原始金额，单位元，最多支持两位小数
     */
    private String salary_amount;

    /**
     * 资金用途，在个人银行账单上显示
     */
    private String purpose;

    /**
     * 工资单明细 JSON格式，展示给个人工资单明细记录，如：{“基本工资”：“1000”}
     */
    private String salary_bill_json;

    /**
     * 异步通知地址,商户用于接收发薪结果通知
     */
    private String notify_url;

    /**
     * 电子回单编号 申请生成电子回单时，可根据此回单编号汇总订单，同一编号在一张回单上
     */
    private String receipt_no;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getOrder_info() {
        return order_info;
    }

    public void setOrder_info(String order_info) {
        this.order_info = order_info;
    }

    public String getService_partner() {
        return service_partner;
    }

    public void setService_partner(String service_partner) {
        this.service_partner = service_partner;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getCert_type() {
        return cert_type;
    }

    public void setCert_type(String cert_type) {
        this.cert_type = cert_type;
    }

    public String getCert_no() {
        return cert_no;
    }

    public void setCert_no(String cert_no) {
        this.cert_no = cert_no;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSalary_card_no() {
        return salary_card_no;
    }

    public void setSalary_card_no(String salary_card_no) {
        this.salary_card_no = salary_card_no;
    }

    public String getSalary_type() {
        return salary_type;
    }

    public void setSalary_type(String salary_type) {
        this.salary_type = salary_type;
    }

    public String getSalary_amount() {
        return salary_amount;
    }

    public void setSalary_amount(String salary_amount) {
        this.salary_amount = salary_amount;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getSalary_bill_json() {
        return salary_bill_json;
    }

    public void setSalary_bill_json(String salary_bill_json) {
        this.salary_bill_json = salary_bill_json;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(String receipt_no) {
        this.receipt_no = receipt_no;
    }

    /**
     * @see com.lianlianpay.mpay.salary.demo.request.base.BaseRequestBean#validateLogic()
     */
    @Override
    public String validateLogic() {
        return null;
    }

}

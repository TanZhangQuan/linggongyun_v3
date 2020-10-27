package com.example.merchant.dto.myBank;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 单笔提现到卡
 */
@Data
public class CashWithdrawalToCardDto {

    /**
     * 合作方业务平台订单号
     * 合作方提交的交易号，对于合作方全局唯一
     */
    @NotBlank(message = "outer_trade_no不能为空")
    private String outer_trade_no;
    /**
     * 合作方业务平台用户ID
     */
    @NotBlank(message = "uid不能为空")
    private String uid;

    /**
     * 外部机构订单号，合作方对接出款渠道使用的提现订单号。若出款渠道是网商银行，则此处填写与outer_trade_no保持一致。
     */
    @NotBlank(message = "outer_inst_order_no不能为空")
    private String outer_inst_order_no;

    /**
     * 平台专属出款渠道编码，该栏位的可选列表由网商银行小二根据平台递交的申请表分配并反馈。
     * 编码规则：出款渠道编码+5位序号。如：出款渠道为网商，网商分配反馈的编码则可以是MYBANK00097，
     * 具体编码以小二反馈信息为准。
     */
    @NotBlank(message = "white_channel_code不能为空")
    private String white_channel_code;

    /**
     * 账户类型,会员提现，暂只支持BASIC
     */
    @NotBlank(message = "account_type不能为空")
    private String account_type;

    /**
     * 银行卡号。明文
     * 提现到银行卡此项为银行卡号；提现到支付宝为支付宝账号
     */
    @NotBlank(message = "bank_account_no不能为空")
    private String bank_account_no;

    /**
     * 户名
     * 银行卡户名或者支付宝户名
     */
    @NotBlank(message = "account_name不能为空")
    private String account_name;

    /**
     * 1、提现到银行卡时可空
     * 2、提现到支付宝时必填为ALIPAY
     */
    private String bank_code;

    /**
     * 银行名称
     */
    private String bank_name;

    /**
     * 银行分支行号
     * 提现到银行卡时，根据卡属性card_attribute 对公不可空，对私可空；
     * 提现到支付宝为空
     */
    private String bank_line_no;

    /**
     * 支行名称
     * 提现到银行卡时，根据卡属性card_attribute 对公不可空，对私可空；
     * 提现到支付宝为空
     */
    private String bank_branch;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 卡类型:
     * DC   借记
     * CC   贷记（信用卡）
     */
    @NotBlank(message = "card_type不能为空")
    private String card_type;

    /**
     * 卡属性:
     * C    对私
     * B    对公
     */
    @NotBlank(message = "card_attribute不能为空")
    private String card_attribute;

    /**
     * 提现金额。金额必须不大于账户可用余额
     */
    @NotNull(message = "amount不能为空")
    private BigDecimal amount;

    /**
     * 服务器异步通知页面路径。
     * 对应异步通知的“提现状态变更通知（单笔）”
     */
    @NotBlank(message = "notify_url不能为空")
    private String notify_url;

    /**
     * 手续费信息，json格式。buyerFee即
     * 收取会员提现手续费。
     */
    private String fee_info;

    /**
     * 预留字段，不填
     */
    private String is_web_access;

    /**
     * 预留字段，不填（账户标识）
     */
    private String account_identity;

    /**
     * 预留字段，不填
     */
    private String product_code;

    /**
     * 预留字段，不填（卡支付属性）
     */
    private String pay_attribute;





}

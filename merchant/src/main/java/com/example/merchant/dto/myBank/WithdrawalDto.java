package com.example.merchant.dto.myBank;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 单笔提现
 */
@Data
public class WithdrawalDto {

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
     * 合作方对接出款渠道使用的提现订单号。若出款渠道是网商银行，则此处填写与outer_trade_no保持一致。
     */
    @NotBlank(message = "outer_inst_order_no不能为空")
    private String outer_inst_order_no;

    /**
     * 账户类型,会员提现
     * 不支持平台方账户类型提现。
     */
    @NotBlank(message = "account_type不能为空")
    private String account_type;

    /**
     * 会员在云资金平台绑定绑卡id，即绑定银行卡接口或者绑定支付宝接口返回的bank_id。
     */
    @NotBlank(message = "bank_id不能为空")
    private String bank_id;

    /**
     * 提现金额。金额必须不大于账户可用余额
     */
    @NotBlank(message = "amount不能为空")
    private String amount;

    /**
     * 服务器异步通知页面路径。
     *
     * 对应异步通知的“提现状态变更通知（单笔）”
     */
    @NotBlank(message = "notify_url不能为空")
    private String notify_url;

    /**
     * 手续费信息，json格式。buyerFee即
     *
     * 收取会员提现手续费。
     */
    @NotBlank(message = "fee_info不能为空")
    private String fee_info;
}

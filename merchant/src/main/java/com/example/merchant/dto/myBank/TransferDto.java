package com.example.merchant.dto.myBank;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 转账入账
 */
@Data
public class TransferDto {

    /**
     * 合作方业务平台订单号
     * 合作方提交的交易号，对于合作方全局唯一
     */
    @NotBlank(message = "outer_trade_no不能为空")
    private String outer_trade_no;

    /**
     * 入款用户Id,金额增加方的用户ID（UID）或会员ID（内部会员ID）
     */
    @NotBlank(message = "fundin_uid不能为空")
    private String fundin_uid;

    /**
     * 入款账户类型。金额增加的账户的账户类型
     */
    @NotBlank(message = "fundin_account_type不能为空")
    private String fundin_account_type;

    /**
     * 用户Id,金额减少方的用户ID（UID），或会员ID（内部会员ID）
     */
    @NotBlank(message = "fundout_uid不能为空")
    private String fundout_uid;

    /**
     * 账户类型,金额减少的账户的账户类型
     */
    @NotBlank(message = "fundout_account_type不能为空")
    private String fundout_account_type;

    /**
     * 转账金额。必须大于0
     */
    @NotNull(message = "transfer_amount不能为空")
    @Min(value = 0, message = "必须大于0")
    private BigDecimal transfer_amount;

    /**
     * 服务器异步通知页面路径。支付平台服务器主动通知业务平台里指定的页面http路径。
     * <p>
     * 对应异步通知的“交易处理结果通知”
     */
    private String notify_url;

    /**
     * 预留字段，不必填
     */
    private String is_web_access;

    /**
     * 预留字段，不必填
     */
    private String transfer_type;

    /**
     * 预留字段，不必填（金额减少的账户的账户标识）
     */
    private String fundout_account_identity;

    /**
     * 预留字段，不必填（金额增加的账户的账户标识）
     */
    private String fundin_account_identity;

    /**
     * 预留字段，不必填
     */
    private String product_code;

}

package com.example.common.mybank.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("转账入账")
public class TradeTransfer extends BaseRequest {
    @ApiModelProperty(value = "合作方业务平台订单号", required = true)
    private String outer_trade_no;

    @ApiModelProperty(value = "入款用户Id,金额增加方的用户ID（UID）或会员ID（内部会员ID）", required = true)
    private String fundin_uid;

    @ApiModelProperty(value = "入款账户类型", example = "BASIC", required = true)
    private String fundin_account_type;

    @ApiModelProperty(value = "用户Id,金额减少方的用户ID（UID），或会员ID（内部会员ID）", required = true)
    private String fundout_uid;

    @ApiModelProperty(value = "账户类型", example = "BASIC", required = true)
    private String fundout_account_type;

    @ApiModelProperty(value = "转账金额", required = true)
    private BigDecimal transfer_amount;

    @ApiModelProperty(value = "服务器异步通知页面路径", hidden = true, required = true)
    private String notify_url;
}

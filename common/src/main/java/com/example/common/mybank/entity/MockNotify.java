package com.example.common.mybank.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@ApiModel("来账通知模拟回调")
@Data
public class MockNotify extends BaseRequest {
    @ApiModelProperty(value = "付款方卡号")
    private String payer_card_no;

    @ApiModelProperty(value = "付款方户名")
    private String payer_card_name;

    @ApiModelProperty(value = "收款方卡号", required = true)
    @NotBlank(message = "请输入收款方卡号")
    private String payee_card_no;

    @ApiModelProperty(value = "收款方户名", required = true)
    @NotBlank(message = "请输入收款方户名")
    private String payee_card_name;

    @ApiModelProperty(value = "汇入金额（单位：分），默认100分，即：1元", example = "100")
    private BigDecimal amount;

    @ApiModelProperty(value = "支付备注")
    private String payer_remark;

    @ApiModelProperty(value = "通知地址/不用填", hidden = true)
    private String notify_url;

}

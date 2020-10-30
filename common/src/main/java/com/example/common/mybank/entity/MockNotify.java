package com.example.common.mybank.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@ApiModel("来账通知模拟回调")
@ToString
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


    public String getPayer_card_no() {
        return payer_card_no;
    }

    public void setPayer_card_no(String payer_card_no) {
        this.payer_card_no = payer_card_no;
    }

    public String getPayer_card_name() {
        return payer_card_name;
    }

    public void setPayer_card_name(String payer_card_name) {
        this.payer_card_name = payer_card_name;
    }

    public String getPayee_card_no() {
        return payee_card_no;
    }

    public void setPayee_card_no(String payee_card_no) {
        this.payee_card_no = payee_card_no;
    }

    public String getPayee_card_name() {
        return payee_card_name;
    }

    public void setPayee_card_name(String payee_card_name) {
        this.payee_card_name = payee_card_name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayer_remark() {
        return payer_remark;
    }

    public void setPayer_remark(String payer_remark) {
        this.payer_remark = payer_remark;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

}

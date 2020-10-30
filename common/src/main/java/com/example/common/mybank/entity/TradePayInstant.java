package com.example.common.mybank.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@ApiModel("即时交易入账")
public class TradePayInstant extends BaseRequest {
    @ApiModelProperty(value = "卖家账户类型", required = true, example = "BASIC")
    @NotBlank(message = "请输入卖家账户类型")
    private String account_type;

    @ApiModelProperty(value = "商品描述")
    private String body;

    @ApiModelProperty(value = "买家在业务平台的ID（UID）。匿名支付场景下，此栏位填写anonymous", required = true)
    @NotBlank(message = "请输入买家在业务平台的ID")
    private String buyer_id;

    @ApiModelProperty(value = "用户在业务平台下单的时候的ip地址，公网IP，不是内网IP-用于风控校验")
    private String buyer_ip;

    @ApiModelProperty(value = "折扣支付方式，无折扣则该栏位无需填写。 格式为JsonList，具体说明见下方接口参数补充说明", required = false, example = "{discount_type:\"allowancePay\",instCode: \"DISCOUNTPAY\",amountInfo: \"25.00\"}")
    private String discount_pay_method;

    @ApiModelProperty(value = "手续费信息，无手续费则该栏位无需填写", example = "{\"sellerFee\":\"0.5\",\"buyerFee\":\"0.3\"}")
    private String fee_info;

    @ApiModelProperty(value = "合作方平台自接入款渠道发起的支付，上送支付渠道的支付订单号。若为pay_method余额支付，则该栏位可空。该订单号编码规则银行不做要求，以合作方平台与入款渠道约定规则为准")
    private String outer_inst_order_no;

    @ApiModelProperty(value = "合作方业务平台订单号", required = true)
    @NotBlank(message = "请输入订单号")
    private String outer_trade_no;

    @ApiModelProperty(value = "支付方式，格式为Json，具体说明见下方接口参数补充说明", required = true, example = "{\"pay_method\":\"QPAY\",\"amount\":\"0.3\",\"memo\":\"ALIPAY,C,DC,   N6228480210599399511\",\"extension\":\"\"}")
    @NotBlank(message = "请输入支付方式")
    private String pay_method;

    @ApiModelProperty(value = "商品单价", required = true, example = "115.00")
    @NotNull(message = "请输入商品单价")
    private BigDecimal price;

    @ApiModelProperty(value = "商品的数量", required = true, example = "2")
    @NotBlank(message = "请输入商品的数量")
    private Integer quantity;

    @ApiModelProperty(value = "同步返回地址,不用填", hidden = true)
    private String return_url;

    @ApiModelProperty(value = "卖家在业务平台的用户ID", required = true)
    @NotBlank(message = "请输入卖家在业务平台的用户ID")
    private String seller_id;

    @ApiModelProperty(value = "商品的标题/交易标题/订单标题/订单关键字等", required = true)
    @NotBlank(message = "请输入商品的标题")
    private String subject;

    @ApiModelProperty(value = "交易金额=（商品单价×商品数量）", required = true)
    @NotNull(message = "请输入交易金额")
    private BigDecimal total_amount;

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getBuyer_ip() {
        return buyer_ip;
    }

    public void setBuyer_ip(String buyer_ip) {
        this.buyer_ip = buyer_ip;
    }

    public String getDiscount_pay_method() {
        return discount_pay_method;
    }

    public void setDiscount_pay_method(String discount_pay_method) {
        this.discount_pay_method = discount_pay_method;
    }

    public String getFee_info() {
        return fee_info;
    }

    public void setFee_info(String fee_info) {
        this.fee_info = fee_info;
    }

    public String getOuter_inst_order_no() {
        return outer_inst_order_no;
    }

    public void setOuter_inst_order_no(String outer_inst_order_no) {
        this.outer_inst_order_no = outer_inst_order_no;
    }

    public String getOuter_trade_no() {
        return outer_trade_no;
    }

    public void setOuter_trade_no(String outer_trade_no) {
        this.outer_trade_no = outer_trade_no;
    }

    public String getPay_method() {
        return pay_method;
    }

    public void setPay_method(String pay_method) {
        this.pay_method = pay_method;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }
}

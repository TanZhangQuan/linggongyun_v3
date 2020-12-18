package com.example.common.mybank.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
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

}

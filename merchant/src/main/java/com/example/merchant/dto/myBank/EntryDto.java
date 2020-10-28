package com.example.merchant.dto.myBank;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 及时入账
 */
@Data
public class EntryDto {

    /**
     * 合作方业务平台订单号 合作方提交的交易号，对于合作方全局唯一
     */
    @NotBlank(message = "outer_trade_no不能为空")
    private String outer_trade_no;

    /**
     * 合作方平台自接入款渠道发起的支付，上送支付渠道的支付订单号。
     * 若为pay_method余额支付，则该栏位可空。该订单号编码规则银行不做要求，以合作方平台与入款渠道约定规则为准。
     */
    private String outer_inst_order_no;

    /**
     * 平台专属入款渠道编码，pay_method非余额支付方式时必填。
     * 该栏位的可选列表由网商银行小二根据平台递交的申请表分配并反馈。
     * 编码规则：入款渠道编码+5位序号。如：入款渠道为ALIPAY，网商分配反馈的编码则可以是ALIPAY00012，具体编码以小二反馈信息为准。
     */
    private String white_channel_code;

    /**
     * 买家在业务平台的ID（UID）。匿名支付场景下，此栏位填写anonymous
     */
    @NotBlank(message = "buyer_id不能为空")
    private String buyer_id;

    /**
     * 用户在业务平台下单的时候的ip地址，公网IP，不是内网IP
     */
    private String buyer_ip;

    /**
     * 支付方式，格式为Json，具体说明见下方接口参数补充说明
     */
    @NotBlank(message = "pay_method不能为空")
    private String pay_method;

    /**
     * 折扣支付方式，无折扣则该栏位无需填写。 格式为JsonList，具体说明见下方接口参数补充说明。
     */
    private String discount_pay_method;

    /**
     * 手续费信息，无手续费则该栏位无需填写。说明：
     * 如果只收买家手续费则卖家不填，如：
     * {"buyerFee":"0.3"}
     * sellerFee:卖家手续费
     * buyerFee：买家手续费
     */
    private String fee_info;

    /**
     * 商品的标题/交易标题/订单标题/订单关键字等。
     */
    @NotBlank(message = "subject不能为空")
    private String subject;

    /**
     * 商品单价。单位为：RMB Yuan。取值范围为[0.01，1000000.00]，精确到小数点后两位。
     */
    @NotBlank(message = "price不能为空")
    private String price;

    /**
     * 商品的数量。
     */
    @NotBlank(message = "quantity不能为空")
    private String quantity;

    /**
     * 交易金额=（商品单价×商品数量）。卖家实际扣款和卖家实际收款金额计算规则请参考接口参数补充说明。
     */
    @NotBlank(message = "total_amount不能为空")
    private String total_amount;

    /**
     * 交易金额分润账号集，接口参数补充说明
     */
    private String royalty_parameters;

    /**
     * 卖家在业务平台的用户ID（UID）
     */
    @NotBlank(message = "seller_id不能为空")
    private String seller_id;

    /**
     * 卖家账户类型
     */
    @NotBlank(message = "account_type不能为空")
    private String account_type;

    /**
     * 商品描述。对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加
     */
    private String body;

    /**
     * 商品展示URL。收银台页面上，商品展示的超链接
     */
    private String show_url;

    /**
     * 服务器异步通知页面路径。该接口同步只受理请求，交易见证平台
     * 订单成功后会主动通知商户业务平台里指定的页面http路径。对应异步通知的“交易状态变更通知”。
     */
    private String notify_url;

    /**
     * 具体交易发生的店铺名称
     */
    private String shop_name;
}
